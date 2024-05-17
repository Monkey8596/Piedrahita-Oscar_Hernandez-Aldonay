package com.backend.parcial.repository.impl;

import com.backend.parcial.dbconnection.H2Connection;
import com.backend.parcial.entity.Odontologo;
import com.backend.parcial.repository.IDao;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {

    private final Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);

    @Override
    public Odontologo registrar(Odontologo odontologo) {

        Connection connection = null;
        Odontologo odontologoRegistrado = null;
        final String query = "INSERT INTO ODONTOLOGO (MATRICULA,NOMBRE,APELLIDO) VALUES (?, ?, ?)";


        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            odontologoRegistrado = new Odontologo(odontologo.getMatricula(),odontologo.getNombre(),odontologo.getApellido());

            while (resultSet.next()){
                odontologoRegistrado.setId(resultSet.getLong("id"));
            }
            connection.commit();
            LOGGER.info("Se ha registrado el Odontologo: " + odontologoRegistrado);

        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    LOGGER.info("Tuvimos un problema");
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                } catch (SQLException exception) {
                    LOGGER.error(exception.getMessage());
                    exception.printStackTrace();
                }
            }
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("No se pudo cerrar la conexion: " + ex.getMessage());
            }
        }

        return odontologoRegistrado;
 }


    @Override
    public List<Odontologo> listarTodos() {

        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();
        final String queryListado = "SELECT * FROM ODONTOLOGO";

        try{
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(queryListado);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                Odontologo paciente = new Odontologo(resultSet.getLong("id"),resultSet.getString("matricula") ,resultSet.getString("nombre"), resultSet.getString("apellido"));;
                odontologos.add(paciente);
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("Ha ocurrido un error al intentar cerrar la bdd. " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        LOGGER.info("Listado de todos los odontologos: " + odontologos);

        return odontologos;
    }
}
