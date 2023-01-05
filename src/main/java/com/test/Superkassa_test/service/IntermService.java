package com.test.Superkassa_test.service;

import com.test.Superkassa_test.entity.SKEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class IntermService {

    private final SKService skService;

    @Autowired
    public IntermService(SKService skService) {
        this.skService = skService;
    }

    @Transactional
    public SKEntity modify(Long id, Double add) {
        try {
            return skService.modify(id, add);
        } catch (ObjectOptimisticLockingFailureException e) {
            return this.modify(id, add);
        }

    }
}
