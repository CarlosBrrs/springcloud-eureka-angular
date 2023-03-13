package com.cbarrios.microservicio.app.respuestas;

import com.cbarrios.microservicio.app.respuestas.models.repository.RespuestaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = RespuestaRepository.class)
//Porque la entidad esta en otro paquete, hay que indicar explicitamente que escanee ahi. Y como usa entities del propio micro, hay que ser explicitos ya que se sobreescribió
/*@EntityScan(
		{"com.cbarrios.microservicio.commons.models.entity",
		 "com.cbarrios.microservicio.app.respuestas.models.entity"}
)*/
public class MicroservicioRespuestasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioRespuestasApplication.class, args);
	}

}
