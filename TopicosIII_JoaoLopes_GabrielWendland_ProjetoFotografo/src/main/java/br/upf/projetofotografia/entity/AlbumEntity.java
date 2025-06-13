package br.upf.projetofotografia.entity;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_album")
public class AlbumEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alb_id")
    private Integer id;

    @Column(name = "alb_titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "alb_descricao", length = 255)
    private String descricao;

    @Column(name = "alb_data_criacao")
    private LocalDate dataCriacao;

    @ManyToOne
    @JoinColumn(name = "fot_id", nullable = false)
    private FotografoEntity fotografo;

    public AlbumEntity() {
        this.dataCriacao = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public FotografoEntity getFotografo() {
        return fotografo;
    }

    public void setFotografo(FotografoEntity fotografo) {
        this.fotografo = fotografo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AlbumEntity)) {
            return false;
        }
        AlbumEntity other = (AlbumEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.upf.projetofotografia.entity.AlbumEntity[ id=" + id + " ]";
    }

}


