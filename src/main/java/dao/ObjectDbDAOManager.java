package dao;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Event;
import domain.Question;

public class ObjectDbDAOManager implements DAOManager{
	protected  static EntityManager  db;
	protected  EntityManagerFactory emf;
	private ConfigXML c=ConfigXML.getInstance();
	
	@Override
	public void open() {
		System.out.println("Opening DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}

	@Override
	public void close() {
		db.close();
		System.out.println("DataBase closed");
		
	}

	@Override
	public Dao<Event> getDAOEvent() {
		return new ObjectDbEventDAO();
	}

	@Override
	public Dao<Question> getDaoQuestion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openTransaction() {
		db.getTransaction().begin();
	}

	@Override
	public void commit() {
		db.getTransaction().commit();
		
	}

	@Override
	public void emptyDB() {
		String fileName=c.getDbFilename();
		File f=new File(fileName);
		f.delete();
		File f2=new File(c.getDbFilename()+"$");
		f2.delete();
		System.out.println("DataBase removed");		
	}

}
