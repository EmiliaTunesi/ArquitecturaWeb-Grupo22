package com.trabajointegrador.microserviciousuario.repository;

import com.trabajointegrador.microserviciousuario.entity.UsuarioCuenta;
import com.trabajointegrador.microserviciousuario.entity.UsuarioCuentaid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioCuentaRepository extends JpaRepository<UsuarioCuenta, UsuarioCuentaid> {
}
