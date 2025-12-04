package hn.com.tigo.jteller.services.interfaces;


import hn.com.tigo.jteller.entities.BillingEntity;

public interface IBillingService {

    BillingEntity getBillingByAcctCode(String acctCode);

    BillingEntity getByIdAndStatus(Long id, Long status);
}
