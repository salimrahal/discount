package com.retail.entity.model;

public enum ProductType {
	GROCERIES(0), CLOTHES(1), OTHER(2);

	ProductType(int value) {
		this.value = value;
	}

	private int value;

	public int getValue() {
		return value;
	}
}
