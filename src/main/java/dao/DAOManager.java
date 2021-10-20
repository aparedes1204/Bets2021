package dao;

import domain.Event;
import domain.Question;

public interface DAOManager {
	public void open();
	public void close();
	public void openTransaction();
	public void commit();;
	public void emptyDB();
	public Dao<Event> getDAOEvent();
	public Dao<Question> getDaoQuestion();
	

}
