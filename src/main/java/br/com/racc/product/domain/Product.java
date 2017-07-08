package br.com.racc.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.com.racc.exception.BusinessException;

@Entity
@Table(name = "PRODUCT")
@Cacheable
@Cache(region = "domainCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRD_ID")
	private Long id;

	@Column(name = "PRD_NAME", nullable = false, columnDefinition = "VARCHAR(60)")
	private String name;

	@Column(name = "PRD_PRICE", nullable = false, columnDefinition = "NUMBER(6,2)")
	private BigDecimal price;

	@Column(name = "PRD_STOCK", nullable = false, columnDefinition = "NUMBER(6)")
	private Long stock;

	protected Product() {

	}

	public Product(Long id, String name, BigDecimal price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Long getStock() {
		return stock;
	}

	public void updateStock(Long stock) {
		if (this.stock == null) {
			this.stock = 0l;
		}
		this.stock += stock;
	}

	public boolean isAvailable() {
		return (this.stock != null && this.stock > 0);
	}

	public BigDecimal calculateTotalAmount(Long quantity) throws BusinessException {
		if (this.stock < quantity) {
			throw new BusinessException("Quantity not available.");
		}

		return this.price.multiply(BigDecimal.valueOf(quantity));
	}

	public void sell(Long quantity) throws BusinessException {
		if (this.stock < quantity) {
			throw new BusinessException("Quantity not available.");
		}

		this.stock = this.stock - quantity;
	}
}