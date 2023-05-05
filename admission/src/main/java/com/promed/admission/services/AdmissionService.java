package com.promed.admission.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

@Component
public class AdmissionService {

	@Autowired
	AdmissionRepository admissionRepo;

	@Autowired
	CategoryTypesRepository categoryTypesRepo;

	@Autowired
	GenderTypesRepository genderTypesRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(AdmissionService.class);

	public List<AdmissionModel> getAdmissionList() {
		try {
			List<Admission> addmissions = admissionRepo.findAll();
			List<AdmissionModel> addmissionList = new ArrayList<>();
			addmissionList = addmissions.stream().map(AdmissionModel::fromEntity).collect(Collectors.toList());
			logger.info("Admission size list "+addmissionList.size());
			return addmissionList;
		} catch (Exception ex) {
			logger.warn(ex.getMessage());
			return new ArrayList<>();
		}

	}

	public ResponseModel addAdmission(AdmissionModel model) {
		ResponseModel resModel = new ResponseModel();
		Map<String, String> errors = new HashMap<>();
		resModel.setErrors(errors);
		try {

			Admission entity = new Admission();
			validateInputValues(resModel, model, entity); // validate category id , gender id and dob.
			if (!resModel.getErrors().isEmpty()) { // if any validation fails return
				resModel.setErrorMessage("Invalid input parameters");
				return resModel;
			}
			entity.setDateOfAdmission(new Timestamp(System.currentTimeMillis()));// taking current time while crating a
																					// new record
			entity.setName(model.getName().trim());
			entity.setSystem(model.getSystem());
			entity = admissionRepo.save(entity);
			model = AdmissionModel.fromEntity(entity);
			resModel.setSuccessMessage("Successfully saved an entity");
			resModel.setAdmission(model);
			logger.info("Admission created successfully "+model.getId());
			return resModel;

		} catch (Exception ex) {
			logger.warn(ex.getMessage());
			resModel.setErrorMessage("Error while saving an entity");
			return resModel;
		}

	}

	public ResponseModel updateAdmission(AdmissionModel model) {
		ResponseModel resModel = new ResponseModel();
		Map<String, String> errors = new HashMap<>();
		resModel.setErrors(errors);
		try {

			Admission entity = new Admission();
			entity = checkValidId(resModel, model, entity); // validate id is present or not.
			if (null != entity.getId()) { // check id existing or not
				model.setSystem(entity.getSystem());
				validateInputValues(resModel, model, entity); // validate category id , gender id and dob and set it.
			}
			if (!resModel.getErrors().isEmpty()) { // if any validation fails return
				resModel.setErrorMessage(AdmissionConstants.ERROR_MESSAGE_PARAMS);
				return resModel;
			}
			entity.setName(model.getName().trim());
			entity = admissionRepo.save(entity);
			model = AdmissionModel.fromEntity(entity);
			resModel.setSuccessMessage("Successfully updated an entity");
			resModel.setAdmission(model);
			logger.info("Admission update successfully "+model.getId());
			return resModel;

		} catch (Exception ex) {
			logger.warn(ex.getMessage());
			resModel.setErrorMessage("Error while updating an entity");
			return resModel;
		}

	}

	public ResponseModel deleteAdmission(BigDecimal id) {
		ResponseModel resModel = new ResponseModel();
		Map<String, String> errors = new HashMap<>();
		resModel.setErrors(errors);
		try {
			Admission entity = new Admission();
			AdmissionModel model = new AdmissionModel();
			model.setId(id);
			entity = checkValidId(resModel, model, entity);
			if (null != entity.getId()) {
				admissionRepo.delete(entity);
			} else if (!resModel.getErrors().isEmpty()) { // if any validation fails return
				resModel.setErrorMessage(AdmissionConstants.ERROR_MESSAGE_PARAMS);
				return resModel;
			}
			resModel.setSuccessMessage("Successfully deleted the admission record");
			logger.info("Admission deleted successfully");
			return resModel;
		} catch (Exception ex) {
			logger.warn(ex.getMessage());
			resModel.setErrorMessage("Unsuccessfull while deleting the admission record");
			return resModel;

		}
	}

	private void validateInputValues(ResponseModel resModel, AdmissionModel model, Admission entity) {
		String errorMessage = "";
		if (AdmissionConstants.EXTTERNAL.equals(model.getSystem())
				&& AdmissionConstants.CREATE.equals(model.getAction())) {
			if (null == model.getSourceSystemName() || model.getSourceSystemName().isBlank()) {
				resModel.getErrors().put("sourceSystemName", "Source System Name cannot be blank"); // this check is for
																									// external system
																									// name while
																									// creating
				return;
			} else {
				entity.setSourceSystemName(model.getSourceSystemName());

			}
		}

		if (!(AdmissionConstants.EXTTERNAL.equals(model.getSystem())
				&& AdmissionConstants.UPDATE.equals(model.getAction()))) { // checking whether its external and update
																			// // action ->cant edit category
			errorMessage = settingCategoryTypeEntity(resModel, model, entity);
			if (!errorMessage.isBlank()) {
				resModel.getErrors().put("categoryId", errorMessage);
				return;
			}
		}
		errorMessage = settingGenderTypeEntity(model, entity);
		if (!errorMessage.isBlank()) {
			resModel.getErrors().put("genderId", errorMessage);
			return;

		}
		Timestamp dob = AdmissionUtilities.parseStringToTimestamp(model.getDateOfBirth(),
				AdmissionConstants.DATE_OF_BIRTH_FORMAT);

		errorMessage = checkDateOfBirthFuture(dob);
		if (!errorMessage.isBlank()) {
			resModel.getErrors().put("dateOfBirth", errorMessage);
			return;

		} else {
			entity.setDateOfBirth(dob);
		}

	}

	private String settingCategoryTypeEntity(ResponseModel resModel, AdmissionModel model, Admission entity) {
		List<CategoryTypes> list = categoryTypesRepo.findAll();

		list = list.stream().filter(type -> {
			return type.getId() == model.getCategoryId();
		}).collect(Collectors.toList());
		if (list.isEmpty()) {
			return "Invalid Category Id passed";
		} else {
			model.setCategory(list.get(0).getCategoryType());
			entity.setCategory(list.get(0));
		}
		return "";

	}

	private String settingGenderTypeEntity(AdmissionModel model, Admission entity) {
		List<GenderTypes> list = genderTypesRepo.findAll();

		list = list.stream().filter(type -> {
			return type.getId() == model.getGenderId();
		}).collect(Collectors.toList());

		if (list.isEmpty()) {
			return "Invalid Gender Id passed";
		} else {
			model.setGender(list.get(0).getGenderType());
			entity.setGender(list.get(0));
		}
		return "";

	}

	private String checkDateOfBirthFuture(Timestamp dob) {
		return dob.toLocalDateTime().toLocalDate().isAfter(LocalDate.now()) ? "Date of Birth cannot be in future" : "";
	}

	private Admission checkValidId(ResponseModel resModel, AdmissionModel model, Admission entity) {
		Optional<Admission> admission = admissionRepo.findById(model.getId());
		if (!admission.isPresent() || admission.get()==null || model.getId() == null || model.getId().equals(new BigDecimal(0))) {
			resModel.getErrors().put("id", "Id cannot be null or invalid");
		} else { // if given id is valid
			return admission.get();
		}
		return entity;

	}

}
