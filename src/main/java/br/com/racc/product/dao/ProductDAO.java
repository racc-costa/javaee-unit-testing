package br.com.racc.product.dao;

import br.com.racc.product.domain.Product;

public interface ProductDAO {

	public Product save(Product product);

	public Product findById(Long productID);
}
