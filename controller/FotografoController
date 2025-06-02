package br.upf.projetofotografia.controller;

import br.upf.projetofotografia.entity.FotografoEntity;
import br.upf.projetofotografia.facade.FotografoFacade;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class FotografoController implements Serializable {

    @EJB
    private FotografoFacade facade;

    private FotografoEntity entity;
    private FotografoEntity selected;
    private List<FotografoEntity> list;

    @PostConstruct
    public void init() {
        entity = new FotografoEntity();
        list = facade.findAll();
    }

    public void salvar() {
        if (entity.getId() == null) {
            facade.create(entity);
        } else {
            facade.edit(entity);
        }
        entity = new FotografoEntity();
        list = facade.findAll();
    }

    public void excluir() {
        if (selected != null) {
            facade.remove(selected);
            list = facade.findAll();
            selected = null;
        }
    }

    public void novo() {
        entity = new FotografoEntity();
    }

    public FotografoEntity getEntity() { return entity; }
    public void setEntity(FotografoEntity entity) { this.entity = entity; }

    public FotografoEntity getSelected() { return selected; }
    public void setSelected(FotografoEntity selected) { this.selected = selected; }

    public List<FotografoEntity> getList() { return list; }
}
