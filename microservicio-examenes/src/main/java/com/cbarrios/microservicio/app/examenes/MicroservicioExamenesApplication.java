package com.cbarrios.microservicio.app.examenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.cbarrios.microservicio.commons.models.entity"}) //Porque la entidad esta en otro paquete, hay que indicar explicitamente que escanee ahi
public class MicroservicioExamenesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioExamenesApplication.class, args);
    }

}
