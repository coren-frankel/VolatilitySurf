package com.volatilitysurf.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.volatilitysurf.models.Stock;
@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
	@SuppressWarnings("unchecked")
	Stock save(Stock s);
	Optional<Stock> findBySymbol(String symbol);
}
