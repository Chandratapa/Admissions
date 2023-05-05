package com.promed.admission.repository.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Admission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigDecimal id;
	@Column(name = "date_of_admission", nullable = false)
	private Timestamp dateOfAdmission;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "date_of_birth", nullable = false)
	private Timestamp dateOfBirth;
	@OneToOne
    @JoinColumn(name = "gender", referencedColumnName = "id")
	private GenderTypes gender;
	@OneToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
	private CategoryTypes category;
	@Column(name = "system", nullable = false)
	private String system;
	@Column(name = "source_system_name")
	private String sourceSystemName;

}
