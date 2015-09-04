package org.semki.dutchtreat.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.Restrictions;
import org.semki.dutchtreat.entity.Purchase;
import org.semki.dutchtreat.entity.PurchaseConsumer;
import org.semki.dutchtreat.utils.ObjectsSupport;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class PurchaseConsumerDAOImpl extends BaseDAOImpl<PurchaseConsumer> implements PurchaseConsumerDAO {

	public PurchaseConsumerDAOImpl() {
		super(PurchaseConsumer.class);
	}

	@Override
	public List<PurchaseConsumer> getByPurchase(Purchase purchase) {
		return ObjectsSupport.safeCastList(createCriteria().add(Restrictions.eq("purchase", purchase)).list());
	}

}
