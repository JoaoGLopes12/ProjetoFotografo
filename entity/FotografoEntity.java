package br.upf.projetojfprimefaces.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "fotografo")
public class FotografoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 64)  // exemplo para senha hash
    private String senha;

    // Relacionamento 1:N com Foto
    @OneToMany(mappedBy = "fotografo", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
        if (!(obj instanceof FotografoEntity)) return false;
        FotografoEntity other = (FotografoEntity) obj;
        return (this.id != null && this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "br.upf.projetojfprimefaces.entity.FotografoEntity[ id=" + id + " ]";
    }
}
