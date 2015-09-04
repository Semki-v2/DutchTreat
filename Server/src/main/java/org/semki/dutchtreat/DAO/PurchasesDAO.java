package org.semki.dutchtreat.DAO;

import java.util.List;

import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.entity.Purchase;

public interface PurchasesDAO extends BaseDAO<Purchase> {

	List<Purchase> getByEvent(Evento evento);

}
