package org.semki.dutchtreat.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.semki.dutchtreat.entity.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EventoDAOImpl implements EventoDAO {
	
	@Autowired
	private final SessionFactory sessionFactory;
	
	@Autowired
	public EventoDAOImpl(SessionFactory session)
	{
		this.sessionFactory = session;
	}
	
	//TODO move to basic DAO
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> safeCastList(List rawList) {
		return (List<T>) rawList;
	}
	
	/* (non-Javadoc)
	 * @see org.semki.dutchtreat.DAO.EventoDAO#list()
	 */
	@Override
	public List<Evento> list(){
		List<Evento> listEvents = safeCastList(sessionFactory.getCurrentSession()
                .createCriteria(Evento.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list());
 
        return listEvents;
	}
	
	/* (non-Javadoc)
	 * @see org.semki.dutchtreat.DAO.EventoDAO#saveOrUpdate(org.semki.dutchtreat.entity.Evento)
	 */
	@Override
	@Transactional
	public void save(Evento e)
	{
		//sessionFactory.getCurrentSession().saveOrUpdate(e);
		
		Session se = sessionFactory.getCurrentSession();
		se.save(e);
	}
	
	@Override
	@Transactional
	public void update(Evento e) {
		Session se = sessionFactory.getCurrentSession();
		se.update(e);
	};
	
	/* (non-Javadoc)
	 * @see org.semki.dutchtreat.DAO.EventoDAO#delte(int)
	 */
	@Override
	@Transactional
	public void delete(int id)
	{
		Evento ev = new Evento();
		ev.setId(id);
		
		sessionFactory.getCurrentSession().delete(ev);
	}
	
	/* (non-Javadoc)
	 * @see org.semki.dutchtreat.DAO.EventoDAO#get(int)
	 */
	@Override
	@Transactional
	public Evento get(int id)
	{
		Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(Evento.class)
		.add(Restrictions.eq("id", id));
		
	 	List<Evento> result = safeCastList(cr.list());
	 	
	 	
	 	if ((result!=null)&&(!result.isEmpty()))
	 	{
	 		return result.get(0);
	 	}
	 	
		return null;
	}

}
