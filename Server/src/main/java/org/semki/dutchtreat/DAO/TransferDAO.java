package org.semki.dutchtreat.DAO;

import java.util.List;

import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.entity.Transfer;

public interface TransferDAO extends BaseDAO<Transfer> {

	List<Transfer> getByEvent(Evento en);

}
