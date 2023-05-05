package com.promed.admission.model;

import java.util.Map;

import lombok.Data;

@Data
public class ResponseModel {
	
	
	private String successMessage;
	private AdmissionModel admission;
	private String errorMessage;
	private Map<String, String> errors;

}
