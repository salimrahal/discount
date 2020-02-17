package com.retail.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bill")
@NoArgsConstructor
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "bill_number")
	private String billNumber;
	
	@Column(name = "date_bill")
	private Date dateBill;
	
	/*before discount*/
	@Column(name = "amount_total")
	private double amountTotal;
	
	@Column(name = "amount_discount")
	private double amountDiscount;
	
	@Column(name = "amount_payable")
	private double amountNetPayable;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
	private Customer customer;
	
	// a bill includes many  product line
	@OneToMany(mappedBy = "bill", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
		      CascadeType.REFRESH })
	private List<ProductLine> productLines = new ArrayList<>();

	public Bill(String number,
			double amountNetPayable, Customer customer, List<ProductLine> products) {
		super();
		this.billNumber = number;
		this.amountNetPayable = amountNetPayable;
		this.customer = customer;
		this.productLines = products;
	}
	
}
