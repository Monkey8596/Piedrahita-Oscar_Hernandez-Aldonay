package com.backend.ClinicaOdontologica.entity;

import java.time.LocalDateTime;

public class Turno {
    private Long id;
    private LocalDateTime fechaYHora;
    private Odontologo odontologo;
    private Paciente paciente;


    public Turno(LocalDateTime fechaYHora, Odontologo odontologo, Paciente paciente) {

        this.fechaYHora = fechaYHora;
        this.odontologo = odontologo;
        this.paciente = paciente;
    }

    public Turno(Long id, LocalDateTime fechaYHora, Odontologo odontologo, Paciente paciente) {
        this.id = id;
        this.fechaYHora = fechaYHora;
        this.odontologo = odontologo;
        this.paciente = paciente;
    }

    public Long getId() {
        return id;
    }



    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
