package com.backend.ClinicaOdontologica;

import com.backend.ClinicaOdontologica.entity.Odontologo;
import com.backend.ClinicaOdontologica.repository.impl.OdontologoDaoEnMemoria;
import com.backend.ClinicaOdontologica.repository.impl.OdontologoDaoH2;
import com.backend.ClinicaOdontologica.service.impl.OdontologoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ClinicaOdontologicaApplicationTests {

	private OdontologoService odontologoService;

	@Test
	void registraUnOdontologoYdevuelveeElID(){

		odontologoService = new OdontologoService(new OdontologoDaoH2());
		Odontologo odontologo = new Odontologo("ZZZ123","Fausto","Dominguez");

		Odontologo odontologoRegistrado = odontologoService.registrarOdontologo(odontologo);

		assertNotNull(odontologoRegistrado.getId());

	}

	@Test
	void devuelveListaNoVaciaDeOdontologos(){
		odontologoService = new OdontologoService(new OdontologoDaoH2());
		assertFalse(odontologoService.listarOdontologos().isEmpty());
	}



	@Test
	void registraUnOdontologoYdevuelveeElIDEnMemoria(){

		odontologoService = new OdontologoService(new OdontologoDaoEnMemoria());
		Odontologo odontologo = new Odontologo("TTT789","Memoria","Mora");

		Odontologo odontologoRegistrado = odontologoService.registrarOdontologo(odontologo);

		assertNotNull(odontologoRegistrado.getId());

	}

	@Test
	void devuelveListaNoVaciaDeOdontologosEnMemoria(){
		odontologoService = new OdontologoService(new OdontologoDaoEnMemoria());
		assertFalse(odontologoService.listarOdontologos().isEmpty());
	}

}
