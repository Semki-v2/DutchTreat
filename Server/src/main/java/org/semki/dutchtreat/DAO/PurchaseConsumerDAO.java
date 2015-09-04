package org.semki.dutchtreat.DAO;

import java.util.List;

import org.semki.dutchtreat.entity.Purchase;
import org.semki.dutchtreat.entity.PurchaseConsumer;

public interface PurchaseConsumerDAO extends BaseDAO<PurchaseConsumer> {

	List<PurchaseConsumer> getByPurchase(Purchase purchase);

}
