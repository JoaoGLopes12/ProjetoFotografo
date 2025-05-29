package br.upf.projetojfprimefaces.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "genero")
public class GeneroEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String descricao;

    @OneToMany(mappedBy = "genero")
    private List<FotoEntity> fotos;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<FotoEntity> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotoEntity> fotos) {
        this.fotos = fotos;
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GeneroEntity)) return false;
        GeneroEntity other = (GeneroEntity) obj;
        return (this.id != null && this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "br.upf.projetojfprimefaces.entity.GeneroEntity[ id=" + id + " ]";
    }
}
