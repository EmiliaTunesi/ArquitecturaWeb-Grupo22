package com.trabajointegrador.microserviciousuario.repository;

import com.trabajointegrador.microserviciousuario.entity.UsuarioCuenta;
import com.trabajointegrador.microserviciousuario.entity.UsuarioCuentaid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioCuentaRepository extends JpaRepository<UsuarioCuenta, UsuarioCuentaid> {
    List<UsuarioCuenta> findByCuentaId(Long cuentaId);
}
