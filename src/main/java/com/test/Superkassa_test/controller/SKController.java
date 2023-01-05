package com.test.Superkassa_test.controller;


import com.test.Superkassa_test.dto.ResponseDto;
import com.test.Superkassa_test.dto.SKEntityDto;
import com.test.Superkassa_test.dto.UpdateRequestDto;
import com.test.Superkassa_test.entity.SKEntity;
import com.test.Superkassa_test.entity.SKObj;
import com.test.Superkassa_test.exception.EntityValidationException;
import com.test.Superkassa_test.service.IntermService;
import com.test.Superkassa_test.service.SKService;
import com.test.Superkassa_test.validation.EntityRequestValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SKController {

    private final SKService skService;
    private final IntermService intermService;
    private final EntityRequestValidationService requestValidationService;

    @Autowired
    public SKController(SKService skService, IntermService intermService, EntityRequestValidationService requestValidationService) {
        this.skService = skService;
        this.intermService = intermService;
        this.requestValidationService = requestValidationService;
    }

    @PostMapping(path = "/modify")
    public ResponseEntity<ResponseDto<SKEntityDto>> modifyEntity(@RequestBody UpdateRequestDto updateRequestDto) {

        ResponseDto<SKEntityDto> responseDto;

        try {
            requestValidationService.validateEntityModifyRequest(updateRequestDto.getId(), updateRequestDto.getAdd());
            SKEntity updatedSKEntity = intermService.modify(updateRequestDto.getId(), updateRequestDto.getAdd());
            SKEntityDto updatedSKEntityDto = SKEntityDto.create(updatedSKEntity);
            responseDto = new ResponseDto<>(0, "Значение энтити успешно обновлено!", updatedSKEntityDto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (EntityValidationException e) {
            responseDto = new ResponseDto<>(1, e.getMessage(), null);
            return new ResponseEntity<>(responseDto, HttpStatus.I_AM_A_TEAPOT);
        } catch (CannotCreateTransactionException e) {
            responseDto = new ResponseDto<>(1,
                    "Превышено максимальное число одновременных запросов, повторите позже!", null);
            return new ResponseEntity<>(responseDto, HttpStatus.I_AM_A_TEAPOT);
        } catch (Exception e) {
            responseDto = new ResponseDto<>(2, "Внутренняя ошибка, энтити не обновлен!", null);
            return new ResponseEntity<>(responseDto, HttpStatus.I_AM_A_TEAPOT);
        }

    }

    @PostMapping(path = "/add")
    public ResponseEntity<ResponseDto<SKEntityDto>> addNewEntity(@RequestBody SKEntityDto skEntityDto) {

        ResponseDto<SKEntityDto> responseDto;

        try {
            requestValidationService.validateEntityAddRequest(skEntityDto.getCurrent());
            SKEntity entity = new SKEntity(new SKObj(skEntityDto.getCurrent()));
            SKEntity addedEntity = skService.addNewSKEntity(entity);
            SKEntityDto addedSKObjDto = SKEntityDto.create(addedEntity);
            responseDto = new ResponseDto<>(0, "Энтити успешно добавлен!", addedSKObjDto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (EntityValidationException e) {
            responseDto = new ResponseDto<>(1, e.getMessage(), null);
            return new ResponseEntity<>(responseDto, HttpStatus.I_AM_A_TEAPOT);
        } catch (Exception e) {
            responseDto = new ResponseDto<>(2, "Внутренняя ошибка, энтити не добавлен!", null);
            return new ResponseEntity<>(responseDto, HttpStatus.I_AM_A_TEAPOT);
        }

    }

}
