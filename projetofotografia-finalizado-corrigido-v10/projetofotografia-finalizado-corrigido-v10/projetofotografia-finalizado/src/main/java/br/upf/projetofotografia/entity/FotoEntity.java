package br.upf.projetofotografia.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "foto")
public class FotoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;
    private String descricao;
    private String arquivo;

    @ManyToOne
    @JoinColumn(name = "genero_id")
    private GeneroEntity genero;

    @ManyToOne
    @JoinColumn(name = "local_id")
    private LocalEntity local;

    @ManyToOne
    @JoinColumn(name = "fotografo_id")
    private FotografoEntity fotografo;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getArquivo() { return arquivo; }
    public void setArquivo(String arquivo) { this.arquivo = arquivo; }

    public GeneroEntity getGenero() { return genero; }
    public void setGenero(GeneroEntity genero) { this.genero = genero; }

    public LocalEntity getLocal() { return local; }
    public void setLocal(LocalEntity local) { this.local = local; }

    public FotografoEntity getFotografo() { return fotografo; }
    public void setFotografo(FotografoEntity fotografo) { this.fotografo = fotografo; }
}
