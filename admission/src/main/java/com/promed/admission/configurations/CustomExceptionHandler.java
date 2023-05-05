package com.promed.admission.configurations;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.promed.admission.model.ResponseModel;
import com.promed.admission.utilities.AdmissionConstants;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	
	public ResponseEntity<ResponseModel> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		ResponseModel model = new ResponseModel();
		model.setErrorMessage(AdmissionConstants.ERROR_MESSAGE_PARAMS);
		model.setErrors(errors);
		return new ResponseEntity<ResponseModel>(model, HttpStatus.BAD_REQUEST);
	}
}
