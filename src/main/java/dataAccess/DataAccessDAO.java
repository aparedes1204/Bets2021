package dataAccess;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;



import configuration.ConfigXML;
import configuration.UtilDate;
import dao.DAOManager;
import dao.Dao;
import dao.ObjectDbEventDAO;
import domain.Event;
import domain.Question;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to a daoManager Database
 */
public class DataAccessDAO implements DataAccessInterface   {
	
	private DAOManager daoManager;


	ConfigXML c=ConfigXML.getInstance();

	public DataAccessDAO(DAOManager daoManager)  {

		this.daoManager=daoManager;

		System.out.println("Creating DataAccessDAO instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());
		System.out.println(" DataManager instance =>"+daoManager);

		//open(initializeMode);

		/*if (initializeMode)
			initializeDB();
		 */
	}



	/* (non-Javadoc)
	 * @see dataAccess.DataAccess#initializeDB()
	 */	
	@Override
	public void initializeDB(){
		ObjectDbEventDAO eventDAO=(ObjectDbEventDAO)daoManager.getDAOEvent();

		daoManager.openTransaction();
		try {


			Calendar today = Calendar.getInstance();

			int month=today.get(Calendar.MONTH);
			month+=1;
			int year=today.get(Calendar.YEAR);
			if (month==12) { month=0; year+=1;}  

			Event ev1=new Event(1, "Atlético-Athletic", UtilDate.newDate(year,month,17));
			Event ev2=new Event(2, "Eibar-Barcelona", UtilDate.newDate(year,month,17));
			Event ev3=new Event(3, "Getafe-Celta", UtilDate.newDate(year,month,17));
			Event ev4=new Event(4, "Alavés-Deportivo", UtilDate.newDate(year,month,17));
			Event ev5=new Event(5, "Español-Villareal", UtilDate.newDate(year,month,17));
			Event ev6=new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year,month,17));
			Event ev7=new Event(7, "Malaga-Valencia", UtilDate.newDate(year,month,17));
			Event ev8=new Event(8, "Girona-Leganés", UtilDate.newDate(year,month,17));
			Event ev9=new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year,month,17));
			Event ev10=new Event(10, "Betis-Real Madrid", UtilDate.newDate(year,month,17));

			Event ev11=new Event(11, "Atletico-Athletic", UtilDate.newDate(year,month,1));
			Event ev12=new Event(12, "Eibar-Barcelona", UtilDate.newDate(year,month,1));
			Event ev13=new Event(13, "Getafe-Celta", UtilDate.newDate(year,month,1));
			Event ev14=new Event(14, "Alavés-Deportivo", UtilDate.newDate(year,month,1));
			Event ev15=new Event(15, "Español-Villareal", UtilDate.newDate(year,month,1));
			Event ev16=new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year,month,1));


			Event ev17=new Event(17, "Málaga-Valencia", UtilDate.newDate(year,month,28));
			Event ev18=new Event(18, "Girona-Leganés", UtilDate.newDate(year,month,28));
			Event ev19=new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year,month,28));
			Event ev20=new Event(20, "Betis-Real Madrid", UtilDate.newDate(year,month,28));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1=ev1.addQuestion("¿Quién ganará el partido?",1);
				q2=ev1.addQuestion("¿Quién meterá el primer gol?",2);
				q3=ev11.addQuestion("¿Quién ganará el partido?",1);
				q4=ev11.addQuestion("¿Cuántos goles se marcarán?",2);
				q5=ev17.addQuestion("¿Quién ganará el partido?",1);
				q6=ev17.addQuestion("¿Habrá goles en la primera parte?",2);
			}
			else if (Locale.getDefault().equals(new Locale("en"))) {
				q1=ev1.addQuestion("Who will win the match?",1);
				q2=ev1.addQuestion("Who will score first?",2);
				q3=ev11.addQuestion("Who will win the match?",1);
				q4=ev11.addQuestion("How many goals will be scored in the match?",2);
				q5=ev17.addQuestion("Who will win the match?",1);
				q6=ev17.addQuestion("Will there be goals in the first half?",2);
			}			
			else {
				q1=ev1.addQuestion("Zeinek irabaziko du partidua?",1);
				q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?",2);
				q3=ev11.addQuestion("Zeinek irabaziko du partidua?",1);
				q4=ev11.addQuestion("Zenbat gol sartuko dira?",2);
				q5=ev17.addQuestion("Zeinek irabaziko du partidua?",1);
				q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2);

			}


			eventDAO.save(ev1);
			eventDAO.save(ev2);
			eventDAO.save(ev3);
			eventDAO.save(ev4);
			eventDAO.save(ev5);
			eventDAO.save(ev6);
			eventDAO.save(ev7);
			eventDAO.save(ev8);
			eventDAO.save(ev9);
			eventDAO.save(ev10);
			eventDAO.save(ev11);
			eventDAO.save(ev12);
			eventDAO.save(ev13);
			eventDAO.save(ev14);
			eventDAO.save(ev15);
			eventDAO.save(ev16);
			eventDAO.save(ev17);
			eventDAO.save(ev18);
			eventDAO.save(ev19);
			eventDAO.save(ev20);			

			daoManager.commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see dataAccess.DataAccess#createQuestion(domain.Event, java.lang.String, float)
	 */
	@Override
	public Question createQuestion(Event event, String question, float betMinimum) throws  QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question+" betMinimum="+betMinimum);


		Dao<Event> eventDAO=daoManager.getDAOEvent();


		Optional<Event> ev = eventDAO.get(event.getEventNumber());

		if (ev.get().DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		daoManager.openTransaction();
		Question q = ev.get().addQuestion(question, betMinimum);
		//db.persist(q);
		eventDAO.save(ev.get()); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		daoManager.commit();
		return q;

	}

	/* (non-Javadoc)
	 * @see dataAccess.DataAccess#getEvents(java.util.Date)
	 */
	@Override
	public Vector<Event> getEvents(Date date) {
		//Dao<Event> eventDAO=daoManager.getDAOEvent();

		ObjectDbEventDAO eventDAO=(ObjectDbEventDAO)daoManager.getDAOEvent();
		return eventDAO.getEvents(date);

	}

	/* (non-Javadoc)
	 * @see dataAccess.DataAccess#getEventsMonth(java.util.Date)
	 */
	@Override
	public Vector<Date> getEventsMonth(Date date) {
		//Dao<Event> eventDAO=daoManager.getDAOEvent();

		ObjectDbEventDAO eventDAO=(ObjectDbEventDAO)daoManager.getDAOEvent();
		return eventDAO.getEventsMonth(date);

	}

	/* (non-Javadoc)
	 * @see dataAccess.DataAccess#emptyDatabase()
	 */
	@Override
	public void emptyDatabase () {
		daoManager.emptyDB();


	}
	/* (non-Javadoc)
	 * @see dataAccess.DataAccess#open()
	 */
	@Override
	public void open(){

		daoManager.open();

	}
	/* (non-Javadoc)
	 * @see dataAccess.DataAccess#existQuestion(domain.Event, java.lang.String)
	 */
	@Override
	public boolean existQuestion(Event event, String question) {
		Dao<Event> eventDAO=daoManager.getDAOEvent();

		System.out.println(">> DataAccess: existQuestion=> event= "+event+" question= "+question);

		Optional<Event> ev=eventDAO.get(event.getEventNumber());
		if (ev.isPresent()) return ev.get().DoesQuestionExists(question);
		else return false;

	}
	/* (non-Javadoc)
	 * @see dataAccess.DataAccess#close()
	 */
	@Override
	public void close(){
		daoManager.close();
	}

}
