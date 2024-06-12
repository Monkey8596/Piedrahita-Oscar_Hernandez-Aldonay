package com.backend.ClinicaOdontologica.controller;

import com.backend.ClinicaOdontologica.entity.Paciente;
import com.backend.ClinicaOdontologica.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pacientes")

public class PacienteController {

    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //POST
    @PostMapping("/registrar")
    public Paciente registartPaciente(@RequestBody Paciente paciente){
        return pacienteService.registrarPaciente(paciente);
    }

    // GET
    @GetMapping("/listar")
    public List<Paciente> listarPacientes(){
        return pacienteService.listarPacientes();
    }

}
