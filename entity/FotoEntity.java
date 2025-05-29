package br.upf.projetojfprimefaces.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "foto")
public class FotoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false, length = 255)
    private String arquivo;  // caminho do arquivo ou nome do arquivo (pode adaptar conforme necessidade)

    // Relacionamentos
    @ManyToOne(optional = false)
    @JoinColumn(name = "fotografo_id", referencedColumnName = "id")
    private FotografoEntity fotografo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "genero_id", referencedColumnName = "id")
    private GeneroEntity genero;

    @ManyToOne(optional = false)
    @JoinColumn(name = "local_id", referencedColumnName = "id")
    private LocalEntity local;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public FotografoEntity getFotografo() {
        return fotografo;
    }

    public void setFotografo(FotografoEntity fotografo) {
        this.fotografo = fotografo;
    }

    public GeneroEntity getGenero() {
        return genero;
    }

    public void setGenero(GeneroEntity genero) {
        this.genero = genero;
    }

    public LocalEntity getLocal() {
        return local;
    }

    public void setLocal(LocalEntity local) {
        this.local = local;
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FotoEntity)) return false;
        FotoEntity other = (FotoEntity) obj;
        return (this.id != null && this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "br.upf.projetojfprimefaces.entity.FotoEntity[ id=" + id + " ]";
    }
}
