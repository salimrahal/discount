package com.retail.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.retail.entity.model.ProductType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "product_line")
@NoArgsConstructor
public class ProductLine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "desc")
	private String desc;
	
	@Column(name = "product_type")
	@Enumerated(EnumType.STRING)
	private ProductType productType;
	
	@Column(name = "amount_line")
	private BigDecimal amountPerLine;

	// a ProductLine  is included in one  bill
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_id")
	private Bill bill;

	public ProductLine(String desc, ProductType productType,BigDecimal amountPerLine) {
		super();
		this.desc = desc;
		this.productType = productType;
		this.amountPerLine = amountPerLine;
	}

}
