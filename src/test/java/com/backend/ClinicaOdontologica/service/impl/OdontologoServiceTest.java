package com.backend.ClinicaOdontologica.service.impl;

import com.backend.ClinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.ClinicaOdontologica.dto.salida.OdontologoSalidaDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class OdontologoServiceTest {

    @Autowired
    private  OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarUnOdontologoDeNombrePedritoYOtroDeNombreMarta() {
        // Datos de prueba
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("1234", "Pedrito", "Fernandez");
        OdontologoEntradaDto odontologoEntradaDto2 = new OdontologoEntradaDto("1145", "Marta", "Sanchez");

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registarOdontologo(odontologoEntradaDto);
        OdontologoSalidaDto odontologoSalidaDto2 = odontologoService.registarOdontologo(odontologoEntradaDto2);

        assertNotNull(odontologoEntradaDto);
        assertNotNull(odontologoSalidaDto.getId());
        assertEquals("1234", odontologoEntradaDto.getMatricula());
        assertEquals("Pedrito", odontologoEntradaDto.getNombre());
        assertNotEquals("Rodriguez", odontologoEntradaDto.getApellido());
    }

    @Test
    @Order(2)
    void deberiaModificarElNombreYApellidoDelOdontologoConID1() {
        Long idOdontologo = 1L;
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("1234", "Jorgito", "Lopez");
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.actualizarOdontologo(odontologoEntradaDto, idOdontologo);

        assertNotNull(odontologoSalidaDto);
        assertEquals(idOdontologo, odontologoSalidaDto.getId());
        assertEquals("1234", odontologoSalidaDto.getMatricula());
        assertEquals("Jorgito", odontologoSalidaDto.getNombre());
        assertEquals("Lopez", odontologoSalidaDto.getApellido());
    }

    @Test
    @Order(3)
    void deberiaDevolverUnaListaDeOdontologosExistentes(){
        List<OdontologoSalidaDto> listadoDeOdontologos = odontologoService.listarOdontologos();
        assertNotNull(listadoDeOdontologos, "La lista de odontólogos no debería ser nula");
    }


}