package com.backend.ClinicaOdontologica.service.impl;

import com.backend.ClinicaOdontologica.dto.entrada.DomicilioEntradaDto;
import com.backend.ClinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.ClinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.ClinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.ClinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.ClinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.ClinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.ClinicaOdontologica.exceptions.BadRequestException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private PacienteService pacienteService;


    @Test
    @Order(1)
    void deberiaRegistrarseUnTurnoConFechaEspecifica_RetornarSuId_Y_VerificarQueLaFechaRegistradaSeaCorrecta() throws BadRequestException {

        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("1214", "Benito", "Diaz");
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registarOdontologo(odontologoEntradaDto);

        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Daniela", "Mendoza", 4235, LocalDate.of(2024, 6, 22),
                new DomicilioEntradaDto("Calle Girasoles", 378, "Nochebuena", "La Paz"));
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);


        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2024, 6, 20, 10, 30), odontologoSalidaDto.getId(), pacienteSalidaDto.getId());
        TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);

        assertNotNull(turnoSalidaDto);
        assertNotNull(turnoSalidaDto.getId());
        assertEquals(LocalDateTime.of(2024, 6, 20, 10, 30), turnoSalidaDto.getFechaYHora());
    }

    @Test
    @Order(2)
    void deberiaEliminarseTurnoConId1() {
        assertDoesNotThrow(() -> turnoService.eliminarTurno(1L));
    }

    @Test
    @Order(3)
    void deberiaDevolverUnaListaVaciaDeTurnos() {
        List<TurnoSalidaDto> listadoDeTurnos = turnoService.listarTurnos();
        assertTrue(listadoDeTurnos.isEmpty());
    }
}