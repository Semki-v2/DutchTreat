package org.semki.dutchtreat.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.Restrictions;
import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.entity.Purchase;
import org.semki.dutchtreat.utils.ObjectsSupport;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class PurchasesDAOImpl extends BaseDAOImpl<Purchase> implements PurchasesDAO {

	public PurchasesDAOImpl() {
		super(Purchase.class);
	}

	@Override
	public List<Purchase> getByEvent(Evento evento) {
		return ObjectsSupport.safeCastList(createCriteria().add(Restrictions.eq("evento", evento)).list());
	}

}
