package com.trabajointegrador.microserviciousuario.repository;

import com.trabajointegrador.microserviciousuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
