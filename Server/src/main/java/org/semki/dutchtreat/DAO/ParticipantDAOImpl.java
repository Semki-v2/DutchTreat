package org.semki.dutchtreat.DAO;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.entity.Participant;
import org.semki.dutchtreat.utils.ObjectsSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ParticipantDAOImpl extends BaseDAOImpl<Participant> implements ParticipantDAO {

	public ParticipantDAOImpl() {
		super(Participant.class);
	}

	@Override
	public List<Participant> getByEvent(Evento en) {
		return ObjectsSupport.safeCastList(createCriteria().add(Restrictions.eq("evento", en)).list());
	}

	@Override
	public Participant getByEventAndName(Evento event, String name) {
		return (Participant) createCriteria().add(Restrictions.eq("evento", event)).add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override
	public void deleteByEvent(Evento event) {
		List<Participant> participants = getByEvent(event);
		for (Participant participant : participants) {
			delete(participant.getId());
		}
	}

}
