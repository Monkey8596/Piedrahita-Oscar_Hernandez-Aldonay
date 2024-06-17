package com.backend.ClinicaOdontologica.controller;


import com.backend.ClinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.ClinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.ClinicaOdontologica.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("turnos")
@CrossOrigin
public class TurnoController {

    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    //POST
    @PostMapping("/registrar")
    public ResponseEntity<TurnoSalidaDto> registartTurno(@RequestBody @Valid TurnoEntradaDto turnoEntradaDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(turnoService.registrarTurno(turnoEntradaDto), HttpStatus.CREATED);
    }

    // GET
    @GetMapping("/listar")
    public ResponseEntity<List<TurnoSalidaDto>> listarTurnos(){
        return new ResponseEntity<>(turnoService.listarTurnos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public  ResponseEntity <TurnoSalidaDto> buscarTurnoPorId(@PathVariable Long id){
        return new ResponseEntity<>(turnoService.buscarTurnoPorId(id), HttpStatus.OK);
    }

    //PUT
    @PutMapping("/actualizar/{id}")
    public  ResponseEntity<TurnoSalidaDto> actualizarTurno(@RequestBody @Valid TurnoEntradaDto
                                                                         turnoEntradaDto, @PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(turnoService.actualizarTurno(turnoEntradaDto, id),HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarTurno(@RequestParam Long id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return new ResponseEntity<>("Turno eliminado correctamente", HttpStatus.NO_CONTENT);
    }
}
