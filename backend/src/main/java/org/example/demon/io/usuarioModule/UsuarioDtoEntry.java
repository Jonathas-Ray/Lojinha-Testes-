package org.example.demon.io.usuarioModule;

import org.example.demon.io.commonModule.enums.TipoUsuario;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioDtoEntry {
    private String nome;
    private String email;
    private TipoUsuario tipo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    public UsuarioDtoEntry() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
