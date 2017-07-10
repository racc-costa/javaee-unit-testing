package br.com.racc.product.dao;

import java.util.List;

import br.com.racc.product.domain.Product;

public interface ProductDAO {

	Product save(Product product);

	Product findById(Long productID);

	List<Product> getAll();
}
