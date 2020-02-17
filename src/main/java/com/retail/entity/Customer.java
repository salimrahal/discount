package com.retail.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.retail.entity.model.CustomerType;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "customer")
@NoArgsConstructor
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "customer_since_date")
	private Date customerSinceDate;
	
	@Column(name = "customer_type")
    @Enumerated(EnumType.STRING)
	private CustomerType customerType;
	
	@OneToMany(mappedBy = "customer", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
		      CascadeType.REFRESH })
	private List<Bill> bills;
	
	
	public Customer(String name, Date customerSinceDate, CustomerType customerType) {
		super();
		this.name = name;
		this.customerSinceDate = customerSinceDate;
		this.customerType = customerType;
	}
   
}