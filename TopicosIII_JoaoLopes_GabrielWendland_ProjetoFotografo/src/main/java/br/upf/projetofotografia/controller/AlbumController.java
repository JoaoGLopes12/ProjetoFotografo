package br.upf.projetofotografia.controller;

import br.upf.projetofotografia.entity.AlbumEntity;
import br.upf.projetofotografia.entity.FotografoEntity;
import br.upf.projetofotografia.facade.AlbumFacade;
import br.upf.projetofotografia.facade.FotografoFacade;
import java.io.Serializable;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class AlbumController implements Serializable {

    @EJB
    private AlbumFacade albumFacade;

    @EJB
    private FotografoFacade fotografoFacade;

    private AlbumEntity entity;
    private List<AlbumEntity> list;
    private Integer fotografoId;

    @PostConstruct
    public void init() {
        entity = new AlbumEntity();
        list = albumFacade.findAll();
    }

    public void novo() {
        entity = new AlbumEntity();
        fotografoId = null;
    }

    public void salvar() {
        if (fotografoId != null) {
            FotografoEntity fotografo = fotografoFacade.find(fotografoId);
            entity.setFotografo(fotografo);
        }
        if (entity.getId() == null) {
            albumFacade.save(entity); // Corrigido para save
        } else {
            albumFacade.edit(entity);
        }
        list = albumFacade.findAll();
        entity = new AlbumEntity(); // Limpa o formulário
        fotografoId = null;
    }

    public void editar(AlbumEntity album) {
        this.entity = album;
        if (album.getFotografo() != null) {
            fotografoId = album.getFotografo().getId();
        }
    }

    public void excluir() {
        albumFacade.remove(entity);
        list = albumFacade.findAll();
        entity = new AlbumEntity(); // Limpa a entidade após exclusão
        fotografoId = null;
    }

    public AlbumEntity getEntity() {
        return entity;
    }

    public void setEntity(AlbumEntity entity) {
        this.entity = entity;
    }

    public List<AlbumEntity> getList() {
        return list;
    }

    public void setList(List<AlbumEntity> list) {
        this.list = list;
    }

    public Integer getFotografoId() {
        return fotografoId;
    }

    public void setFotografoId(Integer fotografoId) {
        this.fotografoId = fotografoId;
    }

    public List<FotografoEntity> getFotografos() {
        return fotografoFacade.findAll();
    }
}


