package com.backend.ClinicaOdontologica.service;

import com.backend.ClinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.ClinicaOdontologica.dto.salida.OdontologoSalidaDto;


import java.util.List;

public interface IOdontologoService {

    OdontologoSalidaDto registarOdontologo(OdontologoEntradaDto odontologoEntradaDto);
    List<OdontologoSalidaDto> listarOdontologos();
    OdontologoSalidaDto buscarOdontologoPorId(Long id);
    void eliminarOdontologo(Long id);
    OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id);
}
