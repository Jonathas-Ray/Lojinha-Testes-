package org.example.demon.io.usuarioModule;

import java.util.Optional;

import org.example.demon.io.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);

}
