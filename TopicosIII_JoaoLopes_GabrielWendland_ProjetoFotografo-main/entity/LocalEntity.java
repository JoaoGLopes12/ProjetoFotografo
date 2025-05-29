package br.upf.projetojfprimefaces.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "local")
public class LocalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @OneToMany(mappedBy = "local")
    private List<FotoEntity> fotos;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        if (!(obj instanceof LocalEntity)) return false;
        LocalEntity other = (LocalEntity) obj;
        return (this.id != null && this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "br.upf.projetojfprimefaces.entity.LocalEntity[ id=" + id + " ]";
    }
}
