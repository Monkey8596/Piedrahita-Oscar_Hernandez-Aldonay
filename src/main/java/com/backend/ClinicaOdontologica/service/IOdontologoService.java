package com.backend.ClinicaOdontologica.service;

import com.backend.ClinicaOdontologica.entity.Odontologo;

import java.util.List;

public interface IOdontologoService {

    Odontologo registrarOdontologo(Odontologo odontologo);
    List<Odontologo> listarOdontologos();
}
