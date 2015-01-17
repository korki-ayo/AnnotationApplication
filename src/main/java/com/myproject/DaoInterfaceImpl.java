package com.myproject;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;

@Repository
public class DaoInterfaceImpl implements DaoInterface{

	@Autowired
	private SessionFactory sessionFactory;

	public DaoInterfaceImpl(){}
	public DaoInterfaceImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<User> list(){
		List<User> listUser  = (List<User>) sessionFactory.getCurrentSession().
									createCriteria(User.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return listUser;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public User findByLogin(String login){
		String query = "from User where login=?";
		List<User> listUser = sessionFactory.getCurrentSession().createQuery(query).setParameter(0, login).list();
		
		if(listUser.size() > 0)
			return listUser.get(0);
		else
			return null;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public User findById(String id){
		String query = "from User where id=?";
		List<User> listUser = sessionFactory.getCurrentSession().createQuery(query).setParameter(0, id).list();
		
		if(listUser.size() > 0)
			return listUser.get(0);
		else
			return null;
	}

	@Override
	@Transactional
	public void saveUser(User user){
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	@Transactional
	public void updateUser(User user){
		sessionFactory.getCurrentSession().update(user);
	}

}