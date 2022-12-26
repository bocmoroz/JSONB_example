package com.test.Superkassa_test.config;

import com.test.Superkassa_test.entity.SKEntity;
import com.test.Superkassa_test.entity.SKObj;
import com.test.Superkassa_test.repository.SKRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SKEntityConfig {

    @Bean
    CommandLineRunner commandLineRunner(SKRepository skRepository) {
        return args -> {

            SKEntity skEntity1 = new SKEntity(
                    new SKObj(0d));

            SKEntity skEntity2 = new SKEntity(
                    new SKObj(57d));

            SKEntity skEntity3 = new SKEntity(
                    new SKObj(100d));

            skRepository.saveAll(
                    Arrays.asList(skEntity1, skEntity2, skEntity3));

        };

    }
}
