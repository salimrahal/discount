package com.retail.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.retail.dto.BillDTO;
import com.retail.dto.CustomerDTO;
import com.retail.dto.NetPaymentDTO;
import com.retail.entity.Customer;
import com.retail.entity.ProductLine;
import com.retail.entity.model.CustomerType;
import com.retail.entity.model.ProductType;
import com.retail.repository.BillRepository;
import com.retail.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BillService {
	private static final Integer CUSTOMER_MIN_YEAR = 2;
	private static final Double CUSTOMER_MORE_2_YEARS_DISC_RATE = 0.05;
	private static final Double EMPLOYEE_DISC_RATE = 0.3;
	private static final Double AFFILIATE_DISC_RATE = 0.1;
	private static final Double EVERY_100_DISC_AMOUNT = 5.0;
	
    private final  BillRepository billRepository;
	private  final CustomerService customerService;
    
	@Autowired
    public BillService( BillRepository billRepository, CustomerService customerService)
    {
		this.billRepository = billRepository;
		this.customerService = customerService;
    	
    }
	
	public NetPaymentDTO computeNetPayable(BillDTO billDto) throws NotFoundException {
		log.info(" processing ProductLine {}", billDto.getProductLines().size());
		NetPaymentDTO netPayable =  this.computeNetPayableCore(billDto);
		return netPayable;	
	}

	public NetPaymentDTO computeNetPayableCore(BillDTO billDto) throws NotFoundException {
		//fetch customer by ID
		CustomerDTO cust = customerService.findById(billDto.getCustomerId());
		List<ProductLine> prodL = billDto.getProductLines();
		log.info(" processing ProductLine {}", prodL.size());
		Double netPayable = 0.0;
		CustomerType custType = cust.getCustomerType();
		Double netPayableFirstLevel = 0.0;
		Double netPayPercBased = 0.0;
		
		for (ProductLine l : prodL) {
			BigDecimal amountLine = l.getAmountPerLine();
			log.info(" amount per line {}", amountLine);
			if (l.getProductType().equals(ProductType.GROCERIES)) {
				log.info(" case of groceries skip discount and add line amount: {} ",l.getDesc());
				// No percentage based discount on groceries
				netPayableFirstLevel = netPayableFirstLevel + amountLine.doubleValue();
			} else {
				// apply percentage based discount on NON groceries
			    netPayPercBased = computedNetPayPercBased(custType, cust, amountLine.doubleValue());
				netPayableFirstLevel = netPayableFirstLevel + netPayPercBased;
				log.info(" netPayableFirstLevel for non groceries / per line {}", netPayableFirstLevel);
			}

		} // end of product line loop

		//compute the total payable of all items before discount
		BigDecimal totalPayable = prodL.stream().map(ProductLine::getAmountPerLine).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		// apply 5% on each 100$ of the first discounted total
		Double disc = this.computeDiscPer100(netPayableFirstLevel);
		netPayable = netPayableFirstLevel - disc;
		
		NetPaymentDTO resp = NetPaymentDTO.builder().netPayable(netPayable).totalPayable(totalPayable.doubleValue()).build();
		return resp;
	}


	public Double computedNetPayPercBased(CustomerType custType, CustomerDTO cust, Double amountLine) {
		Double netPayableLine = 0.0;
		switch (custType) {
		case EMPLOYEE:
			netPayableLine = amountLine * (1- EMPLOYEE_DISC_RATE);
			break;
		case AFFILIATE:
			netPayableLine = amountLine * (1- AFFILIATE_DISC_RATE);
			break;
		// check customer start date since more than 2 years
		case CUSTOMER:
			Date dateStart = cust.getCustomerSinceDate();
			long diffInMillies = Math.abs(new Date().getTime() - dateStart.getTime());
			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			Double year =  (diff / 365.0);
			if (year > CUSTOMER_MIN_YEAR) {
				netPayableLine = amountLine * (1-CUSTOMER_MORE_2_YEARS_DISC_RATE);
			}
		}
		return netPayableLine;
	}

	public Double computeDiscPer100(Double amtTotal) {
		Double number = amtTotal / 100;
		Double discAmount = number * EVERY_100_DISC_AMOUNT;
		return discAmount;

	}

}
