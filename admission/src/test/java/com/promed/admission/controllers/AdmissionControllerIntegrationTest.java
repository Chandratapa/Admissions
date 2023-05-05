package com.promed.admission.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.promed.admission.model.AdmissionModel;
import com.promed.admission.model.ResponseModel;
import com.promed.admission.services.AdmissionService;
import com.promed.admission.utilities.AdmissionConstants;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AdmissionController.class)
public class AdmissionControllerIntegrationTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private AdmissionService admissionService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testGetAdmissionList() throws Exception {
		AdmissionModel admission1 = new AdmissionModel();
		admission1.setId(BigDecimal.valueOf(1));
		admission1.setName("Admission1");

		AdmissionModel admission2 = new AdmissionModel();
		admission2.setId(BigDecimal.valueOf(2));
		admission2.setName("Admission2");

		List<AdmissionModel> admissionList = Arrays.asList(admission1, admission2);

		Mockito.when(admissionService.getAdmissionList()).thenReturn(admissionList);

		mockMvc.perform(get("/promed/api/v1/admissions").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("Admission1"))).andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].name", is("Admission2")));
	}

	@Test
	public void testAddAdmission() throws Exception {
		AdmissionModel admission = new AdmissionModel();
		admission.setName("Admission");
		admission.setDateOfBirth("10/02/1965");
		admission.setCategoryId(1);
		admission.setGenderId(1);
		admission.setSystem(AdmissionConstants.INTERNAL);
		admission.setAction(AdmissionConstants.CREATE);

		ResponseModel response = new ResponseModel();
		response.setSuccessMessage("Admission created successfully");

		response.setAdmission(admission);

		Mockito.when(admissionService.addAdmission(Mockito.any(AdmissionModel.class))).thenReturn(response);

		mockMvc.perform(post("/promed/api/v1/admission").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(admission))).andExpect(status().isOk())
				.andExpect(jsonPath("$.successMessage", is("Admission created successfully")))
				.andExpect(jsonPath("$.admission.name", is("Admission")));
	}

	@Test
	public void testAddAdmissionParametersNotValid() throws Exception {
		AdmissionModel admission = new AdmissionModel();
		// dob missing
		admission.setName("Admission");
		admission.setCategoryId(1);
		admission.setGenderId(1);
		admission.setSystem(AdmissionConstants.INTERNAL);
		admission.setAction(AdmissionConstants.CREATE);

		ResponseModel response = new ResponseModel();
		response.setErrorMessage(AdmissionConstants.ERROR_MESSAGE_PARAMS);

		Mockito.when(admissionService.addAdmission(Mockito.any(AdmissionModel.class))).thenReturn(response);

		mockMvc.perform(post("/promed/api/v1/admission").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(admission))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessage", is(AdmissionConstants.ERROR_MESSAGE_PARAMS)));
	}

	@Test
	public void testUpdateAdmission() throws Exception {
		AdmissionModel admission = new AdmissionModel();
		admission.setId(BigDecimal.valueOf(1));
		admission.setName("Admission");
		admission.setDateOfBirth("10/02/1965");
		admission.setCategoryId(1);
		admission.setGenderId(1);
		admission.setSystem(AdmissionConstants.INTERNAL);
		admission.setAction(AdmissionConstants.UPDATE);

		ResponseModel response = new ResponseModel();

		response.setSuccessMessage("Admission updated successfully");
		response.setAdmission(admission);

		Mockito.when(admissionService.addAdmission(Mockito.any(AdmissionModel.class))).thenReturn(response);

		mockMvc.perform(post("/promed/api/v1/admission").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(admission))).andExpect(status().isOk())
				.andExpect(jsonPath("$.successMessage", is(response.getSuccessMessage())))
				.andExpect(jsonPath("$.admission.name", is("Admission")));

	}
}
