package com.backend.ClinicaOdontologica.entity;

import javax.persistence.*;


@Entity
@Table(name = "ODONTOLOGOS")
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(length = 20)
    private String Matricula;

    @Column(length = 50)
    private String nombre;

    @Column(length = 50)
    private String apellido;

    public Odontologo() {

    }

    public Odontologo(String matricula, String nombre, String apellido) {
            Matricula = matricula;
            this.nombre = nombre;
            this.apellido = apellido;
        }

        public Odontologo(Long id, String matricula, String nombre, String apellido) {
            this.id = id;
            Matricula = matricula;
            this.nombre = nombre;
            this.apellido = apellido;
        }

    public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getMatricula() {
            return Matricula;
        }

        public void setMatricula(String matricula) {
            Matricula = matricula;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

}
