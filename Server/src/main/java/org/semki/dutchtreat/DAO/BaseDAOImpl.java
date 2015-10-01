package org.semki.dutchtreat.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.semki.dutchtreat.entity.PersistentEntity;
import org.semki.dutchtreat.utils.ObjectsSupport;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDAOImpl<Entity extends PersistentEntity> implements BaseDAO<Entity> {
	protected Class<Entity> type;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	public BaseDAOImpl(Class<Entity> type) {
		this.type = type;
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected Criteria createCriteria() {
		return getSession().createCriteria(type);
	}
	
	@Override
	public void save(Entity obj) {
		getSession().save(obj);
	}

	@Override
	public List<Entity> list() {
		return ObjectsSupport.safeCastList(getSession().createCriteria(type).list());
	}

	@Override
	public Entity get(Long id) {
		Entity obj = find(id);
		if (obj == null) {
			throw new ObjectNotFoundException(id, type.getSimpleName());
		}
		return obj;
	}

	@Override
	public void delete(Long id) {
		Object obj = getSession().load(type, id);
		if (obj == null) {
			throw new ObjectNotFoundException(id, type.getSimpleName());
		}
		getSession().delete(obj);
	}

	@Override
	public void deleteAll() {
		getSession().createQuery("delete from " + type.getSimpleName()).executeUpdate();
	}

	@Override
	public void saveOrUpdate(Entity obj) {
		getSession().saveOrUpdate(obj);
	}

	@Override
	public void update(Entity obj) {
		getSession().update(obj);
	}

	@Override
	public Entity find(Long id) {
		return type.cast(getSession().byId(type).load(id));
	}
	
	public Entity first(List<Entity> list)
	{
		if (list.size()>0)
		{
			return list.get(0);
		}
		else
		{
			return null;
		}
	}
}
