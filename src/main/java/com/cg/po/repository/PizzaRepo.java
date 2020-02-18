package com.cg.po.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.po.model.Pizzas;

@Repository
public interface PizzaRepo extends JpaRepository<Pizzas, String> {
	
	@Query(value="Select price from pizzas where type=?1 ", nativeQuery = true)
	Double getPizzaTypePrice(String pizzaType);

	@Query(value="Select price from pizzas where type=?1 ", nativeQuery = true)
	Double getPizzaSizePrice(String pizzaSize);

	@Query(value="Select price from pizzas where type=?1 ", nativeQuery = true)
	Double getPizzaCrustPrice(String pizzaCrust);

	@Query(value="Select price from pizzas where type=?1 ", nativeQuery = true)
	Double getPizzaToppingPrice(String pizzaTopping);
	
	

}
