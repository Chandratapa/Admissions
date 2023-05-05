package com.promed.admission.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.promed.admission.configurations.Whitelist;
import com.promed.admission.repository.entities.Admission;
import com.promed.admission.utilities.AdmissionConstants;
import com.promed.admission.utilities.AdmissionUtilities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdmissionModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal id;
	
	@Whitelist(regex="^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$", message = "Pattern should match the format dd/MM/yyyy HH:mm")
	private String dateOfAdmission;
	@NotEmpty
	@Whitelist(regex="^[a-zA-Z\s]+$", message = "Should contain only letters")
	private String name;
	@NotEmpty
	@Whitelist(regex="^\\d{2}/\\d{2}/\\d{4}$", message = "Pattern should match the format dd/MM/yyyy")
	private String dateOfBirth;
	@Whitelist(regex="^[a-zA-Z]+$", message = "Should contain only letters")
	private String gender;
	@Whitelist(regex="^[a-zA-Z]+$", message = "Should contain only letters")
	private String category;
	@NotNull
	private Integer categoryId;
	@NotNull
	private Integer genderId;
	private String system;
	private String sourceSystemName;
	@JsonIgnore
	private String action;
	
	
	
	
	public static AdmissionModel fromEntity(Admission entity) {
		AdmissionModel dto = new AdmissionModel();
		dto.setId(entity.getId());
        dto.setDateOfAdmission(AdmissionUtilities.parseDateTimeToString(entity.getDateOfAdmission(), AdmissionConstants.ADMISSION_FORMAT));
        dto.setName(entity.getName());
        dto.setDateOfBirth(AdmissionUtilities.parseDateTimeToString(entity.getDateOfBirth(),AdmissionConstants.DATE_OF_BIRTH_FORMAT));
        dto.setCategory(entity.getCategory().getCategoryType());
        dto.setGender(entity.getGender().getGenderType());
        dto.setCategoryId(entity.getCategory().getId());
        dto.setGenderId(entity.getGender().getId());
        dto.setSystem(entity.getSystem());
        if(AdmissionConstants.EXTTERNAL.equals(entity.getSystem())) {
        dto.setSourceSystemName(entity.getSourceSystemName());
        }
        
        return dto;
    }
	

}
