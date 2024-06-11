package com.backend.ClinicaOdontologica.repository.impl;

import com.backend.ClinicaOdontologica.entity.Odontologo;
import com.backend.ClinicaOdontologica.repository.IDao;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoEnMemoria implements IDao <Odontologo> {

    private final Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);
    List<Odontologo> listaOdontologos = new ArrayList<>();


    public OdontologoDaoEnMemoria() {
        Odontologo odontologo = new Odontologo(1L,"WWW222","Pablo","Neruda");
        Odontologo odontologo2 = new Odontologo(2L,"YYY222","Cacd Rmilo","Zapata");

        listaOdontologos.add(odontologo);
        listaOdontologos.add(odontologo2);
    }

    @Override
    public Odontologo registrar(Odontologo odontologo) {

        Long id = Long.valueOf(listaOdontologos.size() + 1);

        Odontologo OdontologoRegistrado = new Odontologo(id,odontologo.getMatricula(), odontologo.getNombre(), odontologo.getApellido());
        LOGGER.info("Se ha registrado el Odontologo En Memoria: " + OdontologoRegistrado);
        return OdontologoRegistrado;
    }

    @Override
    public List<Odontologo> listarTodos() {

        LOGGER.info("Listado de todos los odontologos en Memoria: " + listaOdontologos);
        return listaOdontologos;
    }
}
