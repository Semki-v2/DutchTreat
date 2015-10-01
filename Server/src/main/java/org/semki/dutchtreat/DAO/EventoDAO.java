package org.semki.dutchtreat.DAO;

import java.util.List;

import org.semki.dutchtreat.entity.Evento;

public interface EventoDAO extends BaseDAO<Evento> {

	List<Evento> getRestrictedEventos(Long account_id);
}