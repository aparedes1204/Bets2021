package dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import configuration.UtilDate;
import domain.Event;

@SuppressWarnings("hiding")
public class ObjectDbEventDAO implements Dao<Event> {
	protected  EntityManager  db;
	
	@Override
	public Optional<Event> get(Object id) {
		db=ObjectDbDAOManager.db;
		// TODO Auto-generated method stub
		Integer i=(Integer)id;
		Event ev = db.find(Event.class, i);
		return Optional.of(ev);
		
	}

	@Override
	public List<Event> getAll() {
		db=ObjectDbDAOManager.db;

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Event t) {
		db=ObjectDbDAOManager.db;

		db.persist(t);
		
	}

	@Override
	public void update(Event t, String[] params) {
		db=ObjectDbDAOManager.db;

		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Event t) {
		db=ObjectDbDAOManager.db;

		// TODO Auto-generated method stub
		
	}
	

	
	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		db=ObjectDbDAOManager.db;

		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
	 	 for (Event ev:events){
	 	   System.out.println(ev.toString());		 
		   res.add(ev);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		db=ObjectDbDAOManager.db;

		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2",Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
	 	   System.out.println(d.toString());		 
		   res.add(d);
		  }
	 	return res;
	}

}
