package br.com.racc.product.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.racc.product.domain.Product;

public class ProductDAOImpl implements ProductDAO {

	@PersistenceContext(unitName = "H2PersistenceUnit")
	EntityManager entityManager;

	@Override
	public Product save(Product product) {
		entityManager.persist(product);
		entityManager.flush();
		entityManager.refresh(product);
		return product;
	}

	@Override
	public Product findById(Long productID) {
		return entityManager.find(Product.class, productID);
	}
}
