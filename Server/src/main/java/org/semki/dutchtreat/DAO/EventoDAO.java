package org.semki.dutchtreat.DAO;

import java.util.List;

import org.semki.dutchtreat.entity.Evento;

public interface EventoDAO {

	public abstract List<Evento> list();

	public abstract void save(Evento e);
	
	public abstract void update(Evento e);

	public abstract void delete(int id);

	public abstract Evento get(int id);

}