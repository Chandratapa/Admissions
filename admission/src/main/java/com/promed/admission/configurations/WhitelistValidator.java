package com.promed.admission.configurations;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class WhitelistValidator implements ConstraintValidator<Whitelist, String> {

    private String regex;

    @Override
    public void initialize(Whitelist constraintAnnotation) {
        this.regex = constraintAnnotation.regex();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
         
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches();
    }
}
