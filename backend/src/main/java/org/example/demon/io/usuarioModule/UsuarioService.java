package org.example.demon.io.usuarioModule;

import org.example.demon.io.commonModule.LoginDto;
import org.example.demon.io.models.Usuario;
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
            throw new RuntimeException("Este e-mail já está sendo utilizado!");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTipo(dto.getTipo());
        // Criptografa a senha antes de persistir no banco
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        
        usuarioRepository.save(usuario);
    }

    public void verificarLogin(LoginDto dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (!usuario.isAtivo()) {
            throw new RuntimeException("Esta conta foi desativada.");
        }

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta!");
        }
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário inexistente: " + email));
    }

    public void deletar(LoginDto dto) {
        verificarLogin(dto);
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail()).get();
        usuario.setAtivo(false); // Mantém o registro no banco, mas impede o login
        usuarioRepository.save(usuario);
    }
}