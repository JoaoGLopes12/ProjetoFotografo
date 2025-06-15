package br.upf.projetofotografia.controller;

import br.upf.projetofotografia.entity.AlbumEntity;
import br.upf.projetofotografia.entity.FotografoEntity;
import br.upf.projetofotografia.facade.AlbumFacade;
import br.upf.projetofotografia.facade.FotografoFacade;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AlbumController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private AlbumFacade albumFacade;
    
    @EJB
    private FotografoFacade fotografoFacade;

    private List<AlbumEntity> albums;
    private AlbumEntity selectedAlbum;
    private AlbumEntity albumToDelete;
    private List<FotografoEntity> fotografos;
    private Integer selectedFotografoId;

    @PostConstruct
    public void init() {
        loadAlbums();
        loadFotografos();
        selectedAlbum = new AlbumEntity();
    }

    public void loadAlbums() {
        try {
            albums = albumFacade.findAll();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar álbuns: " + e.getMessage());
        }
    }

    public void loadFotografos() {
        try {
            fotografos = fotografoFacade.findAll();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar fotógrafos: " + e.getMessage());
        }
    }

    public void novoAlbum() {
        selectedAlbum = new AlbumEntity();
        selectedFotografoId = null;
    }

    public void salvarAlbum() {
        try {
            // Buscar o fotógrafo pelo ID selecionado
            if (selectedFotografoId != null) {
                FotografoEntity fotografo = fotografoFacade.find(selectedFotografoId);
                selectedAlbum.setFotografo(fotografo);
            }
            
            if (selectedAlbum.getId() == null) {
                albumFacade.create(selectedAlbum);
                addMessage(FacesMessage.SEVERITY_INFO, "Álbum criado com sucesso!");
            } else {
                albumFacade.edit(selectedAlbum);
                addMessage(FacesMessage.SEVERITY_INFO, "Álbum atualizado com sucesso!");
            }
            loadAlbums();
            selectedAlbum = new AlbumEntity();
            selectedFotografoId = null;
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar álbum: " + e.getMessage());
        }
    }

    public void editarAlbum(AlbumEntity album) {
        selectedAlbum = album;
        selectedFotografoId = album.getFotografo() != null ? album.getFotografo().getId() : null;
    }

    public void prepararExclusao(AlbumEntity album) {
        albumToDelete = album;
    }

    public void excluirAlbum() {
        try {
            if (albumToDelete != null) {
                albumFacade.remove(albumToDelete);
                addMessage(FacesMessage.SEVERITY_INFO, "Álbum excluído com sucesso!");
                loadAlbums();
                albumToDelete = null;
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir álbum: " + e.getMessage());
        }
    }

    public void cancelar() {
        selectedAlbum = new AlbumEntity();
        selectedFotografoId = null;
    }

    private void addMessage(FacesMessage.Severity severity, String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, null));
    }

    // Getters e Setters
    public List<AlbumEntity> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumEntity> albums) {
        this.albums = albums;
    }

    public AlbumEntity getSelectedAlbum() {
        return selectedAlbum;
    }

    public void setSelectedAlbum(AlbumEntity selectedAlbum) {
        this.selectedAlbum = selectedAlbum;
    }

    public AlbumEntity getAlbumToDelete() {
        return albumToDelete;
    }

    public void setAlbumToDelete(AlbumEntity albumToDelete) {
        this.albumToDelete = albumToDelete;
    }

    public List<FotografoEntity> getFotografos() {
        return fotografos;
    }

    public void setFotografos(List<FotografoEntity> fotografos) {
        this.fotografos = fotografos;
    }

    public Integer getSelectedFotografoId() {
        return selectedFotografoId;
    }

    public void setSelectedFotografoId(Integer selectedFotografoId) {
        this.selectedFotografoId = selectedFotografoId;
    }
}