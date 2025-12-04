package hn.com.tigo.comodatos.service;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.comodatos.entities.BillingEntity;
import hn.com.tigo.comodatos.models.BillingModel;
import hn.com.tigo.comodatos.repositories.IBillingRepository;
import hn.com.tigo.comodatos.services.interfaces.IBillingService;

@Service
public class BillingServiceImpl implements IBillingService {

	private IBillingRepository billingRepository;

	public BillingServiceImpl(IBillingRepository billingRepository) {
		super();
		this.billingRepository = billingRepository;

	}

	@Override
	public BillingModel getBillingByNumberDei(String numberDei) {
		BillingEntity entity = this.billingRepository.getBillingByNumberDei(numberDei);
		if (entity == null)
			throw new BadRequestException(String.format("No records found for Number DEI %s", numberDei));
		return entity.entityToModel();
	}

}
