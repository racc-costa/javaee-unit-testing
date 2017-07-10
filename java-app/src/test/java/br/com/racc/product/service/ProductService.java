package br.com.racc.product.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.racc.product.dao.ProductDAO;
import br.com.racc.product.domain.Product;

@Stateless
@LocalBean
public class ProductService {

	@Inject
	@Dependent
	private ProductDAO productDAO;

	public Product findById(Long productID) {
		return productDAO.findById(productID);
	}

	public List<Product> getAll() {
		return productDAO.getAll();
	}
}
