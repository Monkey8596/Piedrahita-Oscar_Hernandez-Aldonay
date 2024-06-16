package com.backend.ClinicaOdontologica.dto.entrada;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class TurnoEntradaDto {

    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "El campo no puede ser nulo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime fechaYHora;

    @NotNull(message = "El campo no puede ser nulo")
    @Valid
    private Long odontologoSalidaDto;

    @NotNull(message = "El campo no puede ser nulo")
    @Valid
    private Long pacienteSalidaDto;

    public TurnoEntradaDto() {
    }

    public TurnoEntradaDto(LocalDateTime fechaYHora, Long odontologoSalidaDto, Long pacienteSalidaDto) {
        this.fechaYHora = fechaYHora;
        this.odontologoSalidaDto = odontologoSalidaDto;
        this.pacienteSalidaDto = pacienteSalidaDto;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public Long getOdontologoSalidaDto() {
        return odontologoSalidaDto;
    }

    public void setOdontologoSalidaDto(Long odontologoSalidaDto) {
        this.odontologoSalidaDto = odontologoSalidaDto;
    }

    public Long getPacienteSalidaDto() {
        return pacienteSalidaDto;
    }

    public void setPacienteSalidaDto(Long pacienteSalidaDto) {
        this.pacienteSalidaDto = pacienteSalidaDto;
    }
}
