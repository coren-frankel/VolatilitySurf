package com.volatilitysurf.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.volatilitysurf.models.Option;
import com.volatilitysurf.models.Stock;

@Repository
public interface OptionRepository extends CrudRepository<Option,Long> {
	@SuppressWarnings("unchecked")
	Option save(Option o);
	List<Option> findByStock(Stock stock);
}
