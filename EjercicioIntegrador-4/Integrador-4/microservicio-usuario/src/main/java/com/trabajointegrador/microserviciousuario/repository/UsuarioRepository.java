package com.trabajointegrador.microserviciousuario.repository;

import com.trabajointegrador.microserviciousuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByActivo(Boolean activo);

}
