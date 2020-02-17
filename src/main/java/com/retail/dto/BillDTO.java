package com.retail.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.retail.entity.Bill;
import com.retail.entity.Customer;
import com.retail.entity.ProductLine;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillDTO {

	private Integer id;
	private String billNumber;
	private Date dateBill;
	/*before discount*/
	private BigDecimal amountTotal;
	private BigDecimal amountDiscount;
	private BigDecimal amountNetPayable;
	
	private Integer customerId;
	
	private List<ProductLine> productLines;

	public BillDTO(Integer id, String billNumber, Date dateBill,
			 Integer customerId, List<ProductLine> productLines) {
		super();
		this.id = id;
		this.billNumber = billNumber;
		this.dateBill = dateBill;
		this.customerId = customerId;
		this.productLines = productLines;
	}
	
}
