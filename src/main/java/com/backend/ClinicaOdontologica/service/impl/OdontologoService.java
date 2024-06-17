package com.backend.ClinicaOdontologica.service.impl;

import com.backend.ClinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.ClinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.ClinicaOdontologica.entity.Odontologo;
import com.backend.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.ClinicaOdontologica.repository.OdontologoRepository;
import com.backend.ClinicaOdontologica.service.IOdontologoService;
import com.backend.ClinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {

    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private final OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OdontologoSalidaDto registarOdontologo(OdontologoEntradaDto odontologoEntradaDto) {

        LOGGER.info("OdontologoEntradaDto: " + JsonPrinter.toString(odontologoEntradaDto));

        Odontologo odontologo = modelMapper.map(odontologoEntradaDto, Odontologo.class);
        LOGGER.info("OdontologoEntidad: " + JsonPrinter.toString(odontologo));

        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoRepository.save(odontologo),
                OdontologoSalidaDto.class);

        LOGGER.info("OdontologoSalidaDto: " + JsonPrinter.toString(odontologoSalidaDto));
        return odontologoSalidaDto;
    }

    @Override
    public List<OdontologoSalidaDto> listarOdontologos() {

        List<OdontologoSalidaDto> odontologos = odontologoRepository.findAll()
                .stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los Odontologos: {}", JsonPrinter.toString(odontologos));

        return odontologos;
    }

    @Override
    public OdontologoSalidaDto buscarOdontologoPorId(Long id) {

        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        OdontologoSalidaDto odontologoEncontrado = null;

        if (odontologoBuscado != null){
            odontologoEncontrado = modelMapper.map(odontologoBuscado,OdontologoSalidaDto.class);
            LOGGER.info("Odontologo encontrado: {}", JsonPrinter.toString(odontologoEncontrado));

        }else
            LOGGER.error("No se ha encontrado el odontologo con id {}", id);

        return odontologoEncontrado;
    }

    @Override
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException{

        if (buscarOdontologoPorId(id) != null){
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el odontologo con el id {}", id);

        } else {
            throw new ResourceNotFoundException("No existe registro de odontologo con id " + id);
        }
    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id) {

        Odontologo odontologoAActualizar = odontologoRepository.findById(id).orElse(null);
        OdontologoSalidaDto odontologoSalidaDto = null;

        if (odontologoAActualizar != null){

            modelMapper.map(odontologoEntradaDto, odontologoAActualizar);

            odontologoRepository.save(odontologoAActualizar);
            odontologoSalidaDto = modelMapper.map(odontologoAActualizar, OdontologoSalidaDto.class);

            LOGGER.warn("Odontologo actualizado {}",JsonPrinter.toString(odontologoSalidaDto));

        } else {

            LOGGER.error("No fue posible actualizar el odontologo porque no se encuentra" +
                    " en la dase de datos");
        }

        return odontologoSalidaDto;
    }
}

