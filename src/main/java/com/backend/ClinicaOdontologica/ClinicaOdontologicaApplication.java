package com.backend.ClinicaOdontologica;

import com.backend.ClinicaOdontologica.repository.dbconnection.H2Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ClinicaOdontologicaApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(ClinicaOdontologicaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ClinicaOdontologicaApplication.class, args);
		H2Connection.ejecutarScriptInicial();
		LOGGER.info("Running");
	}

	@GetMapping("/hola")

	public String saludar(){
		return  " Hola saluda el cosito";
	}

}