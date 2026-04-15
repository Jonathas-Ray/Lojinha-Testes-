package org.example.demon.io.usuarioModule;

import org.example.demon.io.commonModule.LoginDto;
import org.example.demon.io.models.Usuario;
import org.example.demon.io.usuarioModule.UsuarioDtoEntry;
import org.example.demon.io.usuarioModule.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void salvar(UsuarioDtoEntry dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Este e-mail já está cadastrado!");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTipo(dto.getTipo());
        // Encriptando a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        
        usuarioRepository.save(usuario);
    }

    public void verificarLogin(LoginDto dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (!usuario.isAtivo()) {
            throw new RuntimeException("Esta conta está desativada.");
        }

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta!");
        }
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o e-mail: " + email));
    }

    public void deletar(LoginDto dto) {
        // Verifica se a senha está correta antes de permitir a desativação
        verificarLogin(dto);
        
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail()).get();
        usuario.setAtivo(false); // Deleção lógica
        usuarioRepository.save(usuario);
    }
}