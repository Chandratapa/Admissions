package com.promed.admission.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.promed.admission.model.AdmissionModel;
import com.promed.admission.model.ResponseModel;
import com.promed.admission.services.AdmissionService;
import com.promed.admission.utilities.AdmissionConstants;

@SpringBootTest
public class AdmissionControllerTest {

    @InjectMocks
    AdmissionController admissionController;

    @Mock
    AdmissionService admissionService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(admissionController);
    }

    @Test
    public void testGetAdmissionList() {
        List<AdmissionModel> list = new ArrayList<>();
        list.add(new AdmissionModel());
        when(admissionService.getAdmissionList()).thenReturn(list);

        ResponseEntity<List<AdmissionModel>> response = admissionController.getAdmissionList();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(1);
    }

    @Test
    public void testAddAdmission() {
        AdmissionModel model = new AdmissionModel();
        model.setId(BigDecimal.ONE);
        model.setSystem(AdmissionConstants.INTERNAL);
        model.setAction(AdmissionConstants.CREATE);
        ResponseModel resModel = new ResponseModel();

        when(admissionService.addAdmission(model)).thenReturn(resModel);

        ResponseEntity<ResponseModel> response = admissionController.addAdmission(model);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(resModel);
    }

    @Test
    public void testUpdateAdmission() {
        AdmissionModel model = new AdmissionModel();
        model.setId(BigDecimal.ONE);
        model.setAction(AdmissionConstants.UPDATE);
        ResponseModel resModel = new ResponseModel();

        when(admissionService.updateAdmission(model)).thenReturn(resModel);

        ResponseEntity<ResponseModel> response = admissionController.updateAdmission(model);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(resModel);
    }

    @Test
    public void testAddAdmissionExternal() {
        AdmissionModel model = new AdmissionModel();
        model.setId(BigDecimal.ONE);
        model.setSystem(AdmissionConstants.EXTTERNAL);
        model.setAction(AdmissionConstants.CREATE);
        ResponseModel resModel = new ResponseModel();

        when(admissionService.addAdmission(model)).thenReturn(resModel);

        ResponseEntity<ResponseModel> response = admissionController.addAdmissionExternal(model);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(resModel);
    }

    @Test
    public void testDeleteAdmission() {
        BigDecimal id = BigDecimal.ONE;
        ResponseModel resModel = new ResponseModel();

        when(admissionService.deleteAdmission(id)).thenReturn(resModel);

        ResponseEntity<ResponseModel> response = admissionController.deleteAdmissionExternal(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(resModel);
    }
}