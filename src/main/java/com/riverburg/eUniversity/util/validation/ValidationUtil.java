package com.riverburg.eUniversity.util.validation;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
@AllArgsConstructor
public class ValidationUtil {

    private final Validator validator;

    public <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            violations.forEach(v -> sb.append(v.getMessage())
                    .append(System.getProperty("line.separator")));

            throw RestException.of(ErrorConstant.INVALID_PARAMETERS, sb.toString());
        }
    }
}
