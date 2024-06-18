package com.backend.ClinicaOdontologica.service.impl;

import com.backend.ClinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.ClinicaOdontologica.dto.salida.DomicilioSalidaDto;
import com.backend.ClinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.ClinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.ClinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.ClinicaOdontologica.entity.Odontologo;
import com.backend.ClinicaOdontologica.entity.Paciente;
import com.backend.ClinicaOdontologica.entity.Turno;
import com.backend.ClinicaOdontologica.exceptions.BadRequestException;
import com.backend.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.ClinicaOdontologica.repository.OdontologoRepository;
import com.backend.ClinicaOdontologica.repository.PacienteRepository;
import com.backend.ClinicaOdontologica.repository.TurnoRepository;
import com.backend.ClinicaOdontologica.service.ITurnoService;
import com.backend.ClinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private final TurnoRepository turnoRepository;
    private final OdontologoService odontologoService;
    private final PacienteService pacienteService;

    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;


    public TurnoService(TurnoRepository turnoRepository, OdontologoService odontologoService, PacienteService pacienteService, PacienteRepository pacienteRepository, OdontologoRepository odontologoRepository, ModelMapper modelMapper, EntityManager entityManager) {
        this.turnoRepository = turnoRepository;
        this.entityManager = entityManager;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException {

        Turno turnoRegistrado = new Turno();

        turnoRegistrado.setFechaYHora(turnoEntradaDto.getFechaYHora());

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorId(turnoEntradaDto.getOdontologoId());
        PacienteSalidaDto pacienteSalidaDto = pacienteService.buscarPacientePorId(turnoEntradaDto.getPacienteId());

        TurnoSalidaDto turnoSalidaDto = null;

        if (odontologoSalidaDto == null && pacienteSalidaDto == null) {

            throw new BadRequestException("El odontologo y el paciente no existen");

        } else if (odontologoSalidaDto == null) {

            throw new BadRequestException("El odontologo no existe");

        } else if (pacienteSalidaDto == null) {

            throw new BadRequestException("El paciente no existe");

        }

            LOGGER.info("TurnoEntradaDto: " + JsonPrinter.toString(turnoEntradaDto));

            Odontologo odontologo = modelMapper.map(odontologoSalidaDto, Odontologo.class);
            LOGGER.info("OdontologoEntidad: " + JsonPrinter.toString(odontologo));

            Paciente paciente = modelMapper.map(pacienteSalidaDto, Paciente.class);
            LOGGER.info("PacienteEntidad: " + JsonPrinter.toString(paciente));

            odontologo = entityManager.merge(odontologo);
            paciente = entityManager.merge(paciente);

            turnoRegistrado.setOdontologo(odontologo);
            turnoRegistrado.setPaciente(paciente);

            Turno turnoGuardado = turnoRepository.save(turnoRegistrado);
            turnoSalidaDto = convertToDto(turnoGuardado);
            LOGGER.info("Turno guardado con éxito: " + JsonPrinter.toString(turnoGuardado));


        return turnoSalidaDto;

    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {

        List<Turno> turnos = turnoRepository.findAll();
        LOGGER.info("Listado de todos los turnos: {}" + JsonPrinter.toString(turnos));

        return turnos.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turno = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if (turno != null){
            turnoEncontrado = convertToDto(turno);
            LOGGER.info("Turno encontrado con éxito: " + JsonPrinter.toString(turno));

        } else {
            LOGGER.error("No se ha encontrado el turno con id {}", id);
        }

        return turnoEncontrado;
    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException{

        Turno turno = turnoRepository.findById(id).orElse(null);

        if (buscarTurnoPorId(id) != null){

            turno.setPaciente(null);
            turno.setOdontologo(null);
            turnoRepository.save(turno);
            turnoRepository.deleteById(id);

            LOGGER.warn("Se ha eliminado el turno con el id {}", id);

        } else {
            throw new ResourceNotFoundException("No existe registro del turno con id " + id);
        }

    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id){
        
        Turno turnoExistente = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoSalidaDto = null;

        if (turnoExistente != null) {

            OdontologoSalidaDto odontologo = odontologoService.buscarOdontologoPorId(turnoEntradaDto.getOdontologoId());
            PacienteSalidaDto paciente = pacienteService.buscarPacientePorId(turnoEntradaDto.getPacienteId());

            turnoExistente.setOdontologo(modelMapper.map(odontologo, Odontologo.class));
            turnoExistente.setPaciente(modelMapper.map(paciente, Paciente.class));

            turnoExistente.setFechaYHora(turnoEntradaDto.getFechaYHora());

            turnoExistente = turnoRepository.save(turnoExistente);

            turnoSalidaDto = convertToDto(turnoExistente);

            LOGGER.warn("Turno actualizado: {}" + JsonPrinter.toString(turnoExistente));

        } else {

            LOGGER.error("No fue posible actualizar el turno porque no se encuentra" +
                    " en la dase de datos");
        }

        return turnoSalidaDto;

    }

    private TurnoSalidaDto convertToDto(Turno turno) {
        
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turno, TurnoSalidaDto.class);
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(turno.getOdontologo(), OdontologoSalidaDto.class);
        DomicilioSalidaDto domicilioSalidaDto = modelMapper.map(turno.getPaciente().getDomicilio(), DomicilioSalidaDto.class);
        PacienteSalidaDto pacienteSalidaDto = modelMapper.map(turno.getPaciente(), PacienteSalidaDto.class);
        pacienteSalidaDto.setDomicilioSalidaDto(domicilioSalidaDto);

        turnoSalidaDto.setOdontologoSalidaDto(odontologoSalidaDto);
        turnoSalidaDto.setPacienteSalidaDto(pacienteSalidaDto);

        return turnoSalidaDto;
    }

}


