package com.promed.admission.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.promed.admission.model.AdmissionModel;
import com.promed.admission.model.ResponseModel;
import com.promed.admission.repository.AdmissionRepository;
import com.promed.admission.repository.CategoryTypesRepository;
import com.promed.admission.repository.GenderTypesRepository;
import com.promed.admission.repository.entities.Admission;
import com.promed.admission.repository.entities.CategoryTypes;
import com.promed.admission.repository.entities.GenderTypes;
import com.promed.admission.utilities.AdmissionConstants;
import com.promed.admission.utilities.AdmissionUtilities;

public class AdmissionServiceTest {

    @Mock
    private AdmissionRepository admissionRepo;

    @Mock
    private CategoryTypesRepository categoryTypesRepo;

    @Mock
    private GenderTypesRepository genderTypesRepo;

    @InjectMocks
    private AdmissionService admissionService;
    
    private List<GenderTypes> genderList;
    private List<CategoryTypes> catTypeList;
    private Admission admissionAdd;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        genderList =  new ArrayList<>();
        GenderTypes gender = new GenderTypes();
        gender.setGenderType("Female");
        gender.setId(1);
        genderList.add(gender);
        gender = new GenderTypes();
        gender.setGenderType("Male");
        gender.setId(2);
        genderList.add(gender);
        
        catTypeList = new ArrayList<>();
        CategoryTypes catType = new CategoryTypes();
        catType.setId(1);
        catType.setCategoryType("Normal");
        catTypeList.add(catType);
        
        catType = new CategoryTypes();
        catType.setId(2);
        catType.setCategoryType("Inpatient");
        catTypeList.add(catType);
        
        admissionAdd = new Admission();
        admissionAdd.setId(BigDecimal.ONE);
        admissionAdd.setName("John Doe");
        admissionAdd.setDateOfAdmission(new Timestamp(System.currentTimeMillis()));
        admissionAdd.setCategory(catTypeList.get(0));
        admissionAdd.setGender(genderList.get(0));
        admissionAdd.setDateOfBirth(AdmissionUtilities.parseStringToTimestamp("05/04/1975",
				AdmissionConstants.DATE_OF_BIRTH_FORMAT));
        admissionAdd.setSystem(AdmissionConstants.EXTTERNAL);
    }

    @Test
    public void testGetAdmissionList() {
        List<Admission> admissions = new ArrayList<>();
    
        Admission admission = new Admission();
        admission.setId(BigDecimal.ONE);
        admission.setName("John Doe");
        admission.setDateOfAdmission(new Timestamp(System.currentTimeMillis()));
        admission.setCategory(catTypeList.get(0));
        admission.setGender(genderList.get(0));
        admission.setDateOfBirth(AdmissionUtilities.parseStringToTimestamp("05/04/1975",
				AdmissionConstants.DATE_OF_BIRTH_FORMAT));
        admissions.add(admission);
        when(admissionRepo.findAll()).thenReturn(admissions);

        List<AdmissionModel> admissionModels = admissionService.getAdmissionList();

        assertEquals(1, admissionModels.size());
        AdmissionModel admissionModel = admissionModels.get(0);
        assertEquals(admission.getId(), admissionModel.getId());
        assertEquals(admission.getName(), admissionModel.getName());
        assertEquals(AdmissionUtilities.parseDateTimeToString(admission.getDateOfAdmission(), AdmissionConstants.ADMISSION_FORMAT), admissionModel.getDateOfAdmission());
        assertEquals(admission.getCategory().getId(), admissionModel.getCategoryId());
        assertEquals(admission.getGender().getId(), admissionModel.getGenderId());
        assertEquals(admission.getDateOfBirth(), AdmissionUtilities.parseStringToTimestamp("05/04/1975",
				AdmissionConstants.DATE_OF_BIRTH_FORMAT));
    }

    @Test
    public void testAddAdmission() {
        AdmissionModel admissionModel = new AdmissionModel();
        admissionModel.setName("John Doe");
        admissionModel.setCategoryId(1);;
        admissionModel.setGenderId(1);
        admissionModel.setDateOfBirth("05/05/1975");
        admissionModel.setSystem(AdmissionConstants.INTERNAL);
        admissionModel.setAction(AdmissionConstants.CREATE);
        
        Admission admission = new Admission();
        admission.setId(BigDecimal.ONE);
        admission.setName("John Doe");
        admission.setDateOfAdmission(new Timestamp(System.currentTimeMillis()));
        admission.setCategory(catTypeList.get(0));
        admission.setGender(genderList.get(0));
        admission.setDateOfBirth(AdmissionUtilities.parseStringToTimestamp("05/05/1975",
				AdmissionConstants.DATE_OF_BIRTH_FORMAT));
        admission.setSystem(AdmissionConstants.INTERNAL);
        
 
        
        admissionModel.setDateOfAdmission(AdmissionUtilities.parseDateTimeToString(admissionAdd.getDateOfAdmission(), AdmissionConstants.ADMISSION_FORMAT));
       
        when(categoryTypesRepo.findAll()).thenReturn(catTypeList);
        when(genderTypesRepo.findAll()).thenReturn(genderList);
        when(admissionRepo.save(any(Admission.class))).thenReturn(admission);
        
        ResponseModel responseModel = admissionService.addAdmission(admissionModel);
       

        assertEquals(admissionModel.getName(), responseModel.getAdmission().getName());
        assertEquals(admissionModel.getCategory(), responseModel.getAdmission().getCategory());
        assertEquals(admissionModel.getGender(), responseModel.getAdmission().getGender());
        assertEquals(admissionModel.getDateOfBirth(), responseModel.getAdmission().getDateOfBirth());
        assertNotNull(responseModel.getAdmission().getId());
    }
//
//    @Test
//    void testUpdateAdmission() {
//        Admission admission = new Admission();
//        admission.setId(BigDecimal.ONE);
//        admission.setName("
//
//}
}