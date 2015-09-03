package org.semki.dutchtreat.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.Restrictions;
import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.entity.Transfer;
import org.semki.dutchtreat.utils.ObjectsSupport;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class TransferDAOImpl extends BaseDAOImpl<Transfer> implements TransferDAO {

	public TransferDAOImpl() {
		super(Transfer.class);
	}
	
	@Override
	public List<Transfer> getByEvent(Evento en) {
		return ObjectsSupport.safeCastList(createCriteria().add(Restrictions.eq("evento", en)).list());
	}
	
}
