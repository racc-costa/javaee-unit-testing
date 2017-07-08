package br.com.racc.product.domain;

import java.math.BigDecimal;

public class ProductDataBuilder {
	public static Long ID = 100123l;
	public static String NAME = "Ale";
	public static BigDecimal PRICE = new BigDecimal("8.25");
	public static Long STOCK = 10l; 

	private Long id = ID;
	private String name = NAME;
	private BigDecimal price = PRICE;
	private Long stock = STOCK;

	public ProductDataBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public ProductDataBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public ProductDataBuilder withPrice(BigDecimal price) {
		this.price = price;
		return this;
	}
	
	public ProductDataBuilder withStock(Long stock) {
		this.stock = stock;
		return this;
	}

	public Product build() {
		Product product = new Product(id, name, price);
		product.updateStock(stock);
		return product;
	}
}