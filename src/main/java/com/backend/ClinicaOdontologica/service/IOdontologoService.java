package com.backend.ClinicaOdontologica.service;

import com.backend.ClinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.ClinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.ClinicaOdontologica.exceptions.ResourceNotFoundException;


import java.util.List;

public interface IOdontologoService {

    OdontologoSalidaDto registarOdontologo(OdontologoEntradaDto odontologoEntradaDto);
    List<OdontologoSalidaDto> listarOdontologos();
    OdontologoSalidaDto buscarOdontologoPorId(Long id);
    void eliminarOdontologo(Long id) throws ResourceNotFoundException;
    OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id);
}
