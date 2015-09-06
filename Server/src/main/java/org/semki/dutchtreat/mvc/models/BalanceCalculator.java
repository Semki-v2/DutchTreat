package org.semki.dutchtreat.mvc.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.semki.dutchtreat.DAO.PurchasesDAO;
import org.semki.dutchtreat.DAO.TransferDAO;
import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.entity.Participant;
import org.semki.dutchtreat.entity.Purchase;
import org.semki.dutchtreat.entity.PurchaseConsumer;
import org.semki.dutchtreat.entity.Transfer;
import org.semki.dutchtreat.mvc.dto.CalculationRowDTO;
import org.semki.dutchtreat.mvc.dto.ParticipantBalanceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Service
public class BalanceCalculator {

	@Autowired
	private PurchasesDAO purchasesDAO;

	@Autowired
	private TransferDAO transferDAO;

	private Participant participant;

	private Evento evento;

	private List<CalcultionRow> calculationRows = new ArrayList<>();

	public static BalanceCalculator CreateByParticipant(Participant participant) {
		BalanceCalculator calculator = new BalanceCalculator();
		calculator.participant = participant;
		calculator.evento = participant.getEvento();
		return calculator;
	}

	public BalanceCalculator() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public void calculate() {
		loadPurchases();
		loadTransfers();
	}

	public ParticipantBalanceDTO ToParticipantBalanceDTO() {
		ParticipantBalanceDTO dto = new ParticipantBalanceDTO();
		dto.records = new ArrayList<>();
		dto.totalBalance = BigDecimal.ZERO;
		for (CalcultionRow calcultionRow : calculationRows) {
			dto.records.add(CalculationRowDTO.convertToDTO(calcultionRow));
			dto.totalBalance = dto.totalBalance.add(calcultionRow.amount);
		}
		return dto;
	}

	private void loadTransfers() {
		List<Transfer> transfers = transferDAO.getByEvent(evento);
		for (Transfer transfer : transfers) {
			if (transfer.getSender() == participant) {
				calculationRows.add(makeRow(transfer.getSender(), transfer.getAmount(), String.format("Отправитель: %s (%s -> %s)",
						transfer.getDescription(), transfer.getSender().getName(), transfer.getReceiver().getName())));
			}
			if (transfer.getReceiver() == participant) {
				calculationRows.add(makeRow(transfer.getSender(), transfer.getAmount().multiply(BigDecimal.valueOf(-1)),
						String.format("Получатель: %s (%s -> %s)", transfer.getDescription(), transfer.getSender().getName(),
								transfer.getReceiver().getName())));
			}
		}
	}

	private void loadPurchases() {
		List<Purchase> purchases = purchasesDAO.getByEvent(evento);
		for (Purchase purchase : purchases) {
			if (purchase.getPurchaseConsumers().isEmpty()) {
				continue;
			}
			
			for (PurchaseConsumer purchaseConsumer : purchase.getPurchaseConsumers()) {
				if (purchaseConsumer.getConsumer() == participant) {
					BigDecimal amount = purchase.getAmount().divide(BigDecimal.valueOf(purchase.getPurchaseConsumers().size()),2,RoundingMode.HALF_UP)
							.multiply(BigDecimal.valueOf(-1));
					calculationRows.add(makeRow(purchase.getBuyer(), amount, "Потребление: " + purchase.getDescription()));
					break;
				}
			}
			
			if (purchase.getBuyer() == participant) {
				calculationRows.add(makeRow(purchase.getBuyer(), purchase.getAmount(), "Покупка: " + purchase.getDescription()));
			}
		}
	}

	private CalcultionRow makeRow(Participant initiator, BigDecimal amount, String description) {
		return new CalcultionRow(initiator, amount, description);
	}

}
