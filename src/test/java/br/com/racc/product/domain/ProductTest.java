package br.com.racc.product.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.com.racc.exception.BusinessException;

public class ProductTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testProduct() throws Exception {
		Product product = new ProductDataBuilder().build();
		assertThat(ProductDataBuilder.ID, equalTo(product.getId()));
		assertThat(ProductDataBuilder.NAME, equalTo(product.getName()));
		assertThat(ProductDataBuilder.PRICE, equalTo(product.getPrice()));
	}

	@Test
	public void testIsAvailable() throws Exception {
		Product product = new ProductDataBuilder().build();
		assertTrue(product.isAvailable());
	}

	@Test
	public void testIsNotAvailable() throws Exception {
		Product product = new ProductDataBuilder().withStock(0l).build();
		assertFalse(product.isAvailable());
	}

	@Test
	public void testCalculateTotalAmount() throws Exception {
		Product product = new ProductDataBuilder().build();
		BigDecimal totalAmount = ProductDataBuilder.PRICE.multiply(new BigDecimal(5));
		assertThat(totalAmount, equalTo(product.calculateTotalAmount(5l)));
	}

	@Test
	public void testSell() throws Exception {
		Product product = new ProductDataBuilder().build();
		product.sell(8l);
		assertThat(2l, equalTo(product.getStock()));
	}
	
	@Test
	public void testSellWithoutStock() throws Exception {
		Product product = new ProductDataBuilder().build();
		
		exception.expect(BusinessException.class);
		exception.expectMessage("Quantity not available.");
		
		product.sell(20l);
	}
}
