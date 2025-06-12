
package br.upf.projetofotografia.controller;

import br.upf.projetofotografia.entity.FotoEntity;
import br.upf.projetofotografia.entity.GeneroEntity;
import br.upf.projetofotografia.entity.LocalEntity;
import br.upf.projetofotografia.entity.FotografoEntity;
import br.upf.projetofotografia.facade.FotoFacade;
import br.upf.projetofotografia.facade.GeneroFacade;
import br.upf.projetofotografia.facade.LocalFacade;
import br.upf.projetofotografia.facade.FotografoFacade;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.event.FileUploadEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class FotoController implements Serializable {

    @EJB
    private FotoFacade fotoFacade;

    @EJB
    private GeneroFacade generoFacade;

    @EJB
    private LocalFacade localFacade;

    @EJB
    private FotografoFacade fotografoFacade;

    private FotoEntity entity;
    private FotoEntity selected;
    private List<FotoEntity> list;

    private List<GeneroEntity> generos;
    private List<LocalEntity> locais;
    private List<FotografoEntity> fotografos;

    private UploadedFile uploadedFile;

    private Integer generoId;
    private Integer localId;
    private Integer fotografoId;

    @PostConstruct
    public void init() {
        try {
            entity = new FotoEntity();
            list = fotoFacade != null ? fotoFacade.findAll() : new ArrayList<>();
            generos = generoFacade != null ? generoFacade.findAll() : new ArrayList<>();
            locais = localFacade != null ? localFacade.findAll() : new ArrayList<>();
            fotografos = fotografoFacade != null ? fotografoFacade.findAll() : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<>();
            generos = new ArrayList<>();
            locais = new ArrayList<>();
            fotografos = new ArrayList<>();
        }
    }

    public void salvar() {
        System.out.println("SALVAR FOI CHAMADO!");

        try {
            if (uploadedFile != null && uploadedFile.getFileName() != null && !uploadedFile.getFileName().isEmpty()) {
                entity.setArquivo(uploadedFile.getFileName());
                System.out.println("Arquivo sendo salvo: " + uploadedFile.getFileName());
            } else {
                System.out.println("Nenhum arquivo enviado.");
            }

            if (generoId != null) {
                entity.setGenero(generoFacade.find(generoId));
            }
            if (localId != null) {
                entity.setLocal(localFacade.find(localId));
            }
            if (fotografoId != null) {
                entity.setFotografo(fotografoFacade.find(fotografoId));
            }

            if (entity.getId() == null) {
                fotoFacade.create(entity);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Foto cadastrada com sucesso!"));
                System.out.println("Foto cadastrada com sucesso!");
            } else {
                fotoFacade.edit(entity);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Foto atualizada com sucesso!"));
                System.out.println("Foto atualizada com sucesso!");
            }

            entity = new FotoEntity();
            generoId = null;
            localId = null;
            fotografoId = null;
            uploadedFile = null;
            list = fotoFacade.findAll();

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", e.getMessage()));
        }
    }

    public void excluir() {
        try {
            if (selected != null) {
                fotoFacade.remove(selected);
                list = fotoFacade.findAll();
                selected = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Foto excluída com sucesso."));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", e.getMessage()));
        }
    }

    public void novo() {
        entity = new FotoEntity();
        generoId = null;
        localId = null;
        fotografoId = null;
        entity.setGenero(new GeneroEntity());
        entity.setLocal(new LocalEntity());
        entity.setFotografo(new FotografoEntity());
        uploadedFile = null;
    }

    public void editar(FotoEntity foto) {
        this.entity = foto;
        this.generoId = (foto.getGenero() != null) ? foto.getGenero().getId().intValue() : null;
        this.localId = (foto.getLocal() != null) ? foto.getLocal().getId() : null;
        this.fotografoId = (foto.getFotografo() != null) ? foto.getFotografo().getId() : null;
        uploadedFile = null;
    }

    public void handleFileUpload(FileUploadEvent event) {
        this.uploadedFile = event.getFile();
        System.out.println("UPLOAD RECEBIDO: " + uploadedFile.getFileName());
    }

    public FotoEntity getEntity() {
        return entity;
    }

    public void setEntity(FotoEntity entity) {
        this.entity = entity;
    }

    public FotoEntity getSelected() {
        return selected;
    }

    public void setSelected(FotoEntity selected) {
        this.selected = selected;
    }

    public List<FotoEntity> getList() {
        return list;
    }

    public List<GeneroEntity> getGeneros() {
        return generos;
    }

    public List<LocalEntity> getLocais() {
        return locais;
    }

    public List<FotografoEntity> getFotografos() {
        return fotografos;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Integer getGeneroId() {
        return generoId;
    }

    public void setGeneroId(Integer generoId) {
        this.generoId = generoId;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    public Integer getFotografoId() {
        return fotografoId;
    }

    public void setFotografoId(Integer fotografoId) {
        this.fotografoId = fotografoId;
    }
}
