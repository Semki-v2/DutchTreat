package org.semki.dutchtreat.DAO;

import java.util.List;

import org.semki.dutchtreat.entity.PersistentEntity;

public interface BaseDAO<Entity extends PersistentEntity> {
	
	List<Entity> list();

	void save(Entity e);
	
	void update(Entity e);

	void delete(Long id);

	Entity get(Long id);

	Entity find(Long id);

	void deleteAll();

	void saveOrUpdate(Entity obj);
}
