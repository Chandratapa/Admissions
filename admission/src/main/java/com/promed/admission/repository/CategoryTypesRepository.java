package com.promed.admission.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.promed.admission.repository.entities.CategoryTypes;
@Repository
public interface CategoryTypesRepository extends CrudRepository<CategoryTypes, Integer> {
	
	public List<CategoryTypes> findAll();

}
