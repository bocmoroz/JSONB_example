package com.test.Superkassa_test.validation;

import com.test.Superkassa_test.exception.EntityValidationException;
import org.springframework.stereotype.Component;

@Component
public class EntityRequestValidationService {

    public void validateEntityAddRequest(Double number) {

        if (number == null) {
            throw new EntityValidationException("Неправильный запрос на добавление!");
        }
    }

    public void validateEntityModifyRequest(Long id, Double add) {

        if (id == null || id <= 0 || add == null || add < 0) {
            throw new EntityValidationException("Неправильный запрос на обновление!");
        }
    }
}
