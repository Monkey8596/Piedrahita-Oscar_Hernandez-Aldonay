package com.backend.ClinicaOdontologica.repository;

import java.util.List;

public interface IDao <T>{
    T registrar(T t);
    T buscarPorId(Long id);
    List<T> listarTodos();
}
