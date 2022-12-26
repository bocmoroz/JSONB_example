package com.test.Superkassa_test;

import com.test.Superkassa_test.controller.SKController;
import com.test.Superkassa_test.dto.ResponseDto;
import com.test.Superkassa_test.dto.SKEntityDto;
import com.test.Superkassa_test.dto.UpdateRequestDto;
import com.test.Superkassa_test.entity.SKEntity;
import com.test.Superkassa_test.entity.SKObj;
import com.test.Superkassa_test.validation.EntityRequestValidationService;
import com.test.Superkassa_test.repository.SKRepository;
import com.test.Superkassa_test.service.SKService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@SpringBootTest(classes = {SKController.class, SKService.class})
@RunWith(SpringRunner.class)
class SuperkassaTestApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private SKController skController;

    @Autowired
    private SKService skService;

    @MockBean
    private EntityRequestValidationService entityRequestValidationService;

    @MockBean
    private SKRepository skRepository;

    @Test
    public void addNewEntityTestSuccess() {

        SKEntity skEntity = new SKEntity();
        skEntity.setSkObj(new SKObj(50D));

        ResponseEntity<ResponseDto<SKEntityDto>> response = skController.addNewEntity(SKEntityDto.create(skEntity));

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(0, response.getBody().getStatus());
        Assert.assertEquals("Энтити успешно добавлен!", response.getBody().getMessage());
        Assert.assertEquals(response.getBody().getBody(), SKEntityDto.create(skEntity));

    }

    @Test
    public void modifyEntityTestSuccess() {

        SKEntity skEntity = new SKEntity();
        skEntity.setSkObj(new SKObj(50D));

        Long id = 1L;
        Double add = 56D;

        Mockito.when(skRepository.findProductById(id)).thenReturn(Collections.singletonList(skEntity));

        UpdateRequestDto updateRequestDto = new UpdateRequestDto(id, add);

        ResponseEntity<ResponseDto<SKEntityDto>> response = skController.modifyEntity(updateRequestDto);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(0, response.getBody().getStatus());
        Assert.assertEquals("Значение энтити успешно обновлено!", response.getBody().getMessage());
        Assert.assertEquals(response.getBody().getBody().getCurrent(), (Double) 106D);

    }

    @Test
    public void modifyEntityTestFail() {

        Long id = 1L;
        Double add = 56D;

        Mockito.when(skRepository.findProductById(id)).thenThrow(new RuntimeException("Внутренняя ошибка"));

        UpdateRequestDto updateRequestDto = new UpdateRequestDto(id, add);

        ResponseEntity<ResponseDto<SKEntityDto>> response = skController.modifyEntity(updateRequestDto);

        Assert.assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(2, response.getBody().getStatus());
        Assert.assertEquals("Внутренняя ошибка, энтити не обновлен!", response.getBody().getMessage());
        Assert.assertNull(response.getBody().getBody());

    }

}
