package org.example.demon.io.models;

import java.util.ArrayList;
import java.util.List;

import org.example.demon.io.commonModule.enums.TipoUsuario;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String email;
    private String senha;

    private boolean ativo = true;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    @JsonManagedReference
    @OneToMany(mappedBy = "comprador", cascade = CascadeType.ALL)
    private List<Compra> compras = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
    private List<Produto> produtosVendendo = new ArrayList<>();

    public Usuario(String nome, String email, TipoUsuario tipo) {
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public List<Produto> getProdutosVendendo() {
        return produtosVendendo;
    }

    public void setProdutosVendendo(List<Produto> produtosVendendo) {
        this.produtosVendendo = produtosVendendo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}