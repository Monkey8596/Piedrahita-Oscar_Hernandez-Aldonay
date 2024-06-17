package com.backend.ClinicaOdontologica.service;

import com.backend.ClinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.ClinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.ClinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.ClinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.ClinicaOdontologica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ITurnoService {

    TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) throws ResourceNotFoundException;
    List<TurnoSalidaDto> listarTurnos();
    TurnoSalidaDto buscarTurnoPorId(Long id);

    void eliminarTurno(Long id) throws ResourceNotFoundException;
    TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id) throws ResourceNotFoundException;
}
