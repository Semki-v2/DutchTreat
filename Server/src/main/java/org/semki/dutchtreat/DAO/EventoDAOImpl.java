package org.semki.dutchtreat.DAO;

import org.semki.dutchtreat.entity.Evento;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EventoDAOImpl extends BaseDAOImpl<Evento> implements EventoDAO {
	public EventoDAOImpl() {
		super(Evento.class);
	}
}
