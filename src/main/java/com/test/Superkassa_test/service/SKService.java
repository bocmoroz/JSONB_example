package com.test.Superkassa_test.service;

import com.test.Superkassa_test.entity.SKEntity;
import com.test.Superkassa_test.entity.SKObj;
import com.test.Superkassa_test.exception.EntityValidationException;
import com.test.Superkassa_test.repository.SKRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SKService {

    private final SKRepository skRepository;

    @Autowired
    public SKService(SKRepository skRepository) {
        this.skRepository = skRepository;
    }

    @Transactional
    public SKEntity modify(Long id, Double add) {
        List<SKEntity> skEntities = skRepository.findEntityById(id);
        if (skEntities.isEmpty()) {
            throw new EntityValidationException("Энтити с id " + id + " не существует!");
        }

        SKEntity skEntity = skEntities.get(0);

        SKObj skObj = skEntity.getSkObj();

        skObj.setCurrent(skObj.getCurrent() + add);

        skEntity.setSkObj(skObj);

        skRepository.save(skEntity);

        return skEntity;

    }

    public SKEntity addNewSKEntity(SKEntity entity) {
        skRepository.save(entity);

        return entity;
    }
}
