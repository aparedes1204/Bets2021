package dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import domain.Event;
import domain.Question;

public class ObjectDbQuestionDAO implements Dao<Question>{
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	
	public ObjectDbQuestionDAO(EntityManager db, EntityManagerFactory emf) {
		super();
		this.db = db;
		this.emf = emf;
	}
	@Override
	public Optional<Question> get(Object id) {
		Integer i=(Integer)id;
		Question q = db.find(Question.class, i);
		return Optional.of(q);
	}

	@Override
	public List<Question> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Question t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Question t, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Question t) {
		// TODO Auto-generated method stub
		
	}

}
