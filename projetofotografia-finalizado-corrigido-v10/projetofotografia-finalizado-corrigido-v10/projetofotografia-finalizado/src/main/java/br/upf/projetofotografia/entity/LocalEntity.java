package br.upf.projetofotografia.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "local")
public class LocalEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
