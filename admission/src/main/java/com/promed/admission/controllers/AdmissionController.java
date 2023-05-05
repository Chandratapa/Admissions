package com.promed.admission.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.promed.admission.model.AdmissionModel;
import com.promed.admission.model.ResponseModel;
import com.promed.admission.services.AdmissionService;
import com.promed.admission.utilities.AdmissionConstants;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/promed")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "Authorization", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, maxAge = 3600)

public class AdmissionController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdmissionController.class);
	
	@Autowired AdmissionService admissionService;
	
	@GetMapping(value = "/api/v1/admissions", produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AdmissionModel>> getAdmissionList() {
		logger.info("Getting admission list");
		List<AdmissionModel> list = admissionService.getAdmissionList();
		return new ResponseEntity<List<AdmissionModel>>(list,HttpStatus.OK);
	}
	
	@PostMapping(value = "/api/v1/admission", consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<ResponseModel> addAdmission(@RequestBody @Valid AdmissionModel model) {
		model.setSystem(AdmissionConstants.INTERNAL);
		model.setAction(AdmissionConstants.CREATE);
		logger.info("Creating admission");
		ResponseModel resModel = admissionService.addAdmission(model);
		return new ResponseEntity<ResponseModel>(resModel,HttpStatus.OK);
	}
	
	@PutMapping(value = "/api/v1/admission", consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<ResponseModel> updateAdmission(@RequestBody @Valid AdmissionModel model) {
		model.setAction(AdmissionConstants.UPDATE);
		logger.info("Updating admission");
		ResponseModel resModel = admissionService.updateAdmission(model);
		return new ResponseEntity<ResponseModel>(resModel,HttpStatus.OK);
	}
	
	@PostMapping(value = "/api/v1/ext/admission", consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<ResponseModel> addAdmissionExternal(@RequestBody @Valid AdmissionModel model) {
		model.setSystem(AdmissionConstants.EXTTERNAL);
		model.setAction(AdmissionConstants.CREATE);
		logger.info("Creating admission for External");
		ResponseModel resModel = admissionService.addAdmission(model);
		return new ResponseEntity<ResponseModel>(resModel,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/api/v1/admission" )
	public ResponseEntity<ResponseModel> deleteAdmissionExternal(@RequestParam @NotNull BigDecimal id) {
		logger.info("Deleting admission record");
		ResponseModel resModel = admissionService.deleteAdmission(id);
		return new ResponseEntity<ResponseModel>(resModel,HttpStatus.OK);
	}


}
