package com.test.Superkassa_test.dto;

import com.test.Superkassa_test.entity.SKEntity;
import lombok.Data;

@Data
public class SKEntityDto {

    private Double current;

    public static SKEntityDto create(SKEntity skEntity) {
        SKEntityDto skEntityDto = new SKEntityDto();

        skEntityDto.setCurrent(skEntity.getSkObj().getCurrent());

        return skEntityDto;
    }

}
