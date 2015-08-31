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
	
	/* (non-Javadoc)
	 * @see org.semki.dutchtreat.DAO.EventoDAO#list()
	 */
	@Override
	public List<Evento> list(){
		@SuppressWarnings("unchecked")
        List<Evento> listEvents = (List<Evento>) sessionFactory.getCurrentSession()
                .createCriteria(Evento.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listEvents;
	}
	
	/* (non-Javadoc)
	 * @see org.semki.dutchtreat.DAO.EventoDAO#saveOrUpdate(org.semki.dutchtreat.entity.Evento)
	 */
	@Override
	@Transactional
	public void saveOrUpdate(Evento e)
	{
		//sessionFactory.getCurrentSession().saveOrUpdate(e);
		
		Session se = sessionFactory.getCurrentSession();
		
		se.saveOrUpdate(e);
	}
	
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
		.add(Restrictions.eq("ID", id));
		
	 	@SuppressWarnings("unchecked")
		List<Evento> result = ((List<Evento>)cr.list());
	 	
	 	
	 	if ((result!=null)&&(!result.isEmpty()))
	 	{
	 		return result.get(0);
	 	}
	 	
		return null;
	}

}
