package hn.com.tigo.jteller.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hn.com.tigo.jteller.entities.BillingEntity;
import hn.com.tigo.jteller.respositories.IBillingRepository;
import hn.com.tigo.jteller.services.interfaces.IBillingService;

@Service
public class BillingServiceImpl implements IBillingService{

	@Value("${BILLING_STATUSES}")
	private String billingStatuses;

	// Props
	private IBillingRepository billingRepository; 
	
	// Constructor
	public BillingServiceImpl(IBillingRepository billingRepository) {
		super();
		this.billingRepository = billingRepository;
	}


	/**
	 * Método que obtiene la factura por el valor AcctCode
	 * 
	 */
	@Override
	public BillingEntity getBillingByAcctCode(String acctCode) {
	
		List<BillingEntity> list =  this.billingRepository.getBillingEntityByAcctCode(acctCode);
		
		return list.isEmpty() == true ? null : list.get(0);
	}


	@Override
	public BillingEntity getByIdAndStatus(Long id, Long status) {
		// Convertir la cadena de estados de la factura a una lista de Long
		List<Long> statuses = Arrays.stream(billingStatuses.split(","))
				.map(Long::parseLong)
				.collect(Collectors.toList());

		BillingEntity entity = this.billingRepository.getByIdAndStatus(id, statuses);

		if (entity == null) {
			throw new BadRequestException(String.format("The invoice with id %s does not have an allowed status", id));
		}

		return entity;
	}

}
