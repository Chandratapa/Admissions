package com.promed.admission.repository.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "gender_types")
@Data
public class GenderTypes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "gender_type")
	private String genderType;
	
	@OneToMany(mappedBy = "gender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Admission> admissions = new ArrayList<>();


}
