package com.cbarrios.microservicio.app.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
//Porque la entidad esta en otro paquete, hay que indicar explicitamente que escanee ahi. Y como usa entities del propio micro, hay que ser explicitos ya que se sobreescribió
@EntityScan(
		{"com.cbarrios.microservicio.commons.models.entity",
		"com.cbarrios.microservicio.app.cursos.models.entity"}
)
public class MicroservicioCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioCursosApplication.class, args);
	}

}
