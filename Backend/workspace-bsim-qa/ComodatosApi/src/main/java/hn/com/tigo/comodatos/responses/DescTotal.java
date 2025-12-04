package hn.com.tigo.comodatos.responses;

import java.io.Serializable;
import java.math.BigDecimal;

public class DescTotal  {
	
	private BigDecimal DESCTOTAL;

	public DescTotal(BigDecimal dESCTOTAL) {
		DESCTOTAL = dESCTOTAL;
	}
	public BigDecimal getDescTotal() {
		return DESCTOTAL;
	}
	public void setDescTotal(BigDecimal descTotal) {
		this.DESCTOTAL = descTotal;
	}
}
