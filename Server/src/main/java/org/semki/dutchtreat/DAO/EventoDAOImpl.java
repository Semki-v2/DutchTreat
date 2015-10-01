package org.semki.dutchtreat.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.utils.ObjectsSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EventoDAOImpl extends BaseDAOImpl<Evento> implements EventoDAO {
	public EventoDAOImpl() {
		super(Evento.class);
	}
	
	public List<Evento> getRestrictedEventos(Long account_id)
	{
		Criteria cr = createCriteria();
		
		cr.createCriteria("accessAccounts","acc");
		
		//cr.createAlias("account_event_access.event_id","acc");
		cr.add(Restrictions.eq("acc.id", account_id));		
				
		return ObjectsSupport.safeCastList(cr.list());
	}
}
