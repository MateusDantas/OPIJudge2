package com.opijudge.models;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.opijudge.models.util.HibernateUtil;

public class Entity {

	public boolean saveToDatabase() {

		Session session = null;
		try {

			session = getSession();
			session.beginTransaction();
			session.saveOrUpdate(this);
			session.getTransaction().commit();
			session.flush();
			
		} catch (Exception ex) {

			ex.printStackTrace();
			return false;
		} finally {
			
			if (session != null)
				session.close();
		}

		
		return true;
	}

	public boolean deleteFromDatabase() {
		
		Session session = null;
		
		try {
			
			session = getSession();
			session.beginTransaction();
			session.delete(this);
			session.getTransaction().commit();
		} catch (Exception e) {
			
			return false;
		} finally {
			
			if (session != null)
				session.close();
		}
		
		return true;
	}

	public Object getById(long id) {
		
		Session session = null;
		try {
			session = getSession();
			session.beginTransaction();
			Object object = (Object) session.get(this.getClass(), id);
			session.getTransaction().commit();
			
			return object;
		} catch (Exception e) {
			
			return null;
		} finally {
			
			if (session != null)
				session.close();
		}
		
	}
	
	public <T> List<?> getAllByProperty(HashMap <String, T> mapKeys) {
		
		Session session = null;
		try {
			session = getSession();
			session.beginTransaction();
			
			Criteria criteria = session.createCriteria(this.getClass());
		
			for (String key : mapKeys.keySet()) {
				criteria.add(Restrictions.eq(key, mapKeys.get(key)));
			}
			System.out.printf("Criteria = %s\n", criteria.toString());
			
			return (List<?>) criteria.list();
		
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		} finally {
			
			if (session != null)
				session.close();
		}
	}
	
	public Session getSession() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		return session;
	}
}
