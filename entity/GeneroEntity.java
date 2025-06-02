package br.upf.projetofotografia.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "genero")
public class GeneroEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descricao;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
