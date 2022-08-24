package com.volatilitysurf.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.volatilitysurf.models.Option;

@Repository
public interface OptionRepository extends CrudRepository<Option,Long> {
	@SuppressWarnings("unchecked")
	Option save(Option o);
}
