package org.semki.dutchtreat.DAO;

import java.util.List;

import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.entity.Participant;

public interface ParticipantDAO extends BaseDAO<Participant> {

	List<Participant> getByEvent(Evento en);

}
