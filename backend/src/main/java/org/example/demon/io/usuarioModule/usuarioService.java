package org.example.demon.io.usuarioModule;

import java.util.Optional;

import org.example.demon.io.models.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

public class usuarioService {
    UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public void salvar(UsuarioDtoEntry dto) {
        Optional<Usuario> emailExistente = usuarioRepository.findByEmail(dto.getEmail());

        if (emailExistente.isPresent()) {
            throw new RuntimeException("O EMAIL JÁ EXISTE");
        }
        if (dto.getTipo().equals("ADMIN")) {
            throw new RuntimeException("O USUARIO NÃO TEM PERMISSAO PARA CRIAR ADMIN");
        }
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.getNome());
        novoUsuario.setEmail(dto.getEmail());
        novoUsuario.setTipo(dto.getTipo());

        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
        novoUsuario.setSenha(senhaCriptografada);
    }

    public boolean verificarLogin(LoginDto dto) {
        Usuario emailExistente = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("EMAIL NÃO CADASTRADO"));
        boolean senhaVerificar = passwordEncoder.matches(dto.getSenha(), emailExistente.getSenha());

        if (!emailExistente.isAtivo()) {
            throw new RuntimeException("CONTA BLOQUEADA");
        }

        if (!senhaVerificar) {
            throw new RuntimeException("SENHA INCORRETA!");
        }
        return true;
    }

    public void deletar(LoginDto dto) {
        Usuario emailExistente = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("EMAIL INCORRETO"));
        boolean senhaVerificar = passwordEncoder.matches(dto.getSenha(), emailExistente.getSenha());
        if (!senhaVerificar) {
            throw new RuntimeException("SENHA INCORRETA!");
        }
        emailExistente.setAtivo(false);
    }

    public void atualizarEmail(String novoEmail, LoginDto dto) {
        Usuario emailExistente = usuarioRepository.findByEmail(dto.getEmail()).get();

        if (usuarioRepository.findByEmail(novoEmail).isPresent()) {
            throw new RuntimeException("O NOVO EMAIL JÁ ESTÁ SENDO UTILIZADO!");
        }

        if (verificarLogin(dto)) {
            emailExistente.setEmail(novoEmail);
            usuarioRepository.save(emailExistente);
        }
    }

    @Transactional
    public void atualizarSenha(String novaSenha, LoginDto dto) {
        Usuario emailExistente = usuarioRepository.findByEmail(dto.getEmail()).get();

        if (verificarLogin(dto)) {
            String senhaCriptografada = passwordEncoder.encode(novaSenha);
            emailExistente.setSenha(senhaCriptografada);
            usuarioRepository.save(emailExistente);
        }
    }
}
