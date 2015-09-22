package org.semki.dutchtreat.mvc.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.semki.dutchtreat.DAO.ParticipantDAO;
import org.semki.dutchtreat.DAO.PurchasesDAO;
import org.semki.dutchtreat.DAO.TransferDAO;
import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.entity.Participant;
import org.semki.dutchtreat.entity.Purchase;
import org.semki.dutchtreat.entity.PurchaseConsumer;
import org.semki.dutchtreat.entity.Transfer;
import org.semki.dutchtreat.mvc.dto.BalanceSummaryRowDTO;
import org.semki.dutchtreat.mvc.dto.CalculationRowDTO;
import org.semki.dutchtreat.mvc.dto.EventSummaryBalanceDTO;
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

	private Evento evento;

	@Autowired
	private ParticipantDAO participantDAO;

	public static BalanceCalculator CreateByEvent(Evento event) {
		BalanceCalculator calculator = new BalanceCalculator();
		calculator.evento = event;
		return calculator;
	}

	public BalanceCalculator() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public List<CalculationRow> calculateByParticipant(Participant participant) {
		List<CalculationRow> calculationRows = new ArrayList<>();
		loadPurchases(participant, calculationRows);
		loadTransfers(participant, calculationRows);
		return calculationRows;
	}

	public ParticipantBalanceDTO ToParticipantBalanceDTO(Participant participant) {
		List<CalculationRow> calculationRows = calculateByParticipant(participant);
		
		ParticipantBalanceDTO dto = new ParticipantBalanceDTO();
		dto.records = new ArrayList<>();
		dto.totalBalance = getTotalSum(calculationRows);
		for (CalculationRow CalculationRow : calculationRows) {
			dto.records.add(CalculationRowDTO.convertToDTO(CalculationRow));
		}
		return dto;
	}
	
	private BigDecimal getTotalSum(List<CalculationRow> calculationRows) {
		BigDecimal totalSum = BigDecimal.ZERO;
		for (CalculationRow CalculationRow : calculationRows) {
			totalSum = totalSum.add(CalculationRow.amount);
		}
		return totalSum;
	}

	private void loadTransfers(Participant participant, List<CalculationRow> calculationRows) {
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

	private void loadPurchases(Participant participant, List<CalculationRow> calculationRows) {
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

	private CalculationRow makeRow(Participant initiator, BigDecimal amount, String description) {
		return new CalculationRow(initiator, amount, description);
	}

	public EventSummaryBalanceDTO ToSummaryBalanceDTO() {
		EventSummaryBalanceDTO dto = new EventSummaryBalanceDTO();
		dto.balanceRows = new LinkedList<>();
		for (Participant participant : participantDAO.getByEvent(evento)) {
			List<CalculationRow> calculationRows = calculateByParticipant(participant);
			
			BigDecimal totalSum = getTotalSum(calculationRows);
			BalanceSummaryRowDTO rowDTO = BalanceSummaryRowDTO.convertToDTO(participant, totalSum);
			dto.balanceRows.add(rowDTO);
		}
		return dto;
	}
}
