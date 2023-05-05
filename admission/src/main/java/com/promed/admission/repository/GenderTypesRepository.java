package com.promed.admission.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.promed.admission.repository.entities.GenderTypes;
@Repository
public interface GenderTypesRepository extends CrudRepository<GenderTypes,Integer> {
	public List<GenderTypes> findAll();

}
