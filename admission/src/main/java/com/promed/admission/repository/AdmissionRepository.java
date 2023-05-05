package com.promed.admission.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.promed.admission.repository.entities.Admission;
@Repository
public interface AdmissionRepository extends CrudRepository<Admission, BigDecimal> {
	
	public List<Admission> findAll();
	
	
	
	 

}
