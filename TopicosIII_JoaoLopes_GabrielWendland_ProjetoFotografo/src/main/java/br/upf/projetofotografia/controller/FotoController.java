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
            recarregarListas();
        } catch (Exception e) {
            System.err.println("Erro ao recarregar listas: " + e.getMessage());
            list = new ArrayList<>();
            generos = new ArrayList<>();
            locais = new ArrayList<>();
            fotografos = new ArrayList<>();
        }
    }

    public void salvar() {
        System.out.println("SALVAR FOI CHAMADO!");

        try {
            // Validações básicas
            if (entity.getTitulo() == null || entity.getTitulo().trim().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Título é obrigatório!"));
                return;
            }

            if (entity.getDescricao() == null || entity.getDescricao().trim().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Descrição é obrigatória!"));
                return;
            }

            // Processamento do arquivo
            if (uploadedFile != null && uploadedFile.getFileName() != null && !uploadedFile.getFileName().isEmpty()) {
                entity.setArquivo(uploadedFile.getFileName());
                System.out.println("Arquivo sendo salvo: " + uploadedFile.getFileName());
            } else if (entity.getId() == null) {
                // Apenas obrigatório para novas fotos
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Arquivo é obrigatório!"));
                return;
            }

            // Configuração das entidades relacionadas
            if (generoId != null) {
                GeneroEntity genero = generoFacade.find(generoId);
                entity.setGenero(genero);
            }
            if (localId != null) {
                LocalEntity local = localFacade.find(localId);
                entity.setLocal(local);
            }
            if (fotografoId != null) {
                FotografoEntity fotografo = fotografoFacade.find(fotografoId);
                entity.setFotografo(fotografo);
            }

            // Salvar ou atualizar
            if (entity.getId() == null) {
                fotoFacade.create(entity);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Foto cadastrada com sucesso!"));
                System.out.println("Foto cadastrada com sucesso!");
            } else {
                fotoFacade.edit(entity);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Foto atualizada com sucesso!"));
                System.out.println("Foto atualizada com sucesso!");
            }

            limparFormulario();
            recarregarListas();

        } catch (Exception e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar: " + e.getMessage()));
        }
    }

    public void excluir() {
        try {
            if (selected != null) {
                fotoFacade.remove(selected);
                recarregarListas();
                selected = null;
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Foto excluída com sucesso!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Nenhuma foto selecionada para exclusão!"));
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao excluir: " + e.getMessage()));
        }
    }

    public void novo() {
        limparFormulario();
        System.out.println("Novo formulário inicializado");
    }

    public void editar(FotoEntity foto) {
        try {
            if (foto != null) {
                this.entity = new FotoEntity();
                this.entity.setId(foto.getId());
                this.entity.setTitulo(foto.getTitulo());
                this.entity.setDescricao(foto.getDescricao());
                this.entity.setArquivo(foto.getArquivo());
                this.entity.setGenero(foto.getGenero());
                this.entity.setLocal(foto.getLocal());
                this.entity.setFotografo(foto.getFotografo());
                
                // Correção dos castings
                this.generoId = (foto.getGenero() != null) ? foto.getGenero().getId() : null;
                this.localId = (foto.getLocal() != null) ? foto.getLocal().getId() : null;
                this.fotografoId = (foto.getFotografo() != null) ? foto.getFotografo().getId() : null;
                
                uploadedFile = null;
                System.out.println("Editando foto ID: " + foto.getId());
            }
        } catch (Exception e) {
            System.err.println("Erro ao preparar edição: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao preparar edição: " + e.getMessage()));
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            System.out.println("UPLOAD RECEBIDO: " + uploadedFile.getFileName());
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload", "Arquivo carregado: " + uploadedFile.getFileName()));
        } catch (Exception e) {
            System.err.println("Erro no upload: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro no upload: " + e.getMessage()));
        }
    }

    public void cancelar() {
        limparFormulario();
        System.out.println("Operação cancelada");
    }

    private void recarregarListas() {
        try {
            list = fotoFacade != null ? fotoFacade.findAll() : new ArrayList<>();
            generos = generoFacade != null ? generoFacade.findAll() : new ArrayList<>();
            locais = localFacade != null ? localFacade.findAll() : new ArrayList<>();
            fotografos = fotografoFacade != null ? fotografoFacade.findAll() : new ArrayList<>();
            System.out.println("Listas recarregadas - Fotos: " + list.size());
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<>();
            generos = new ArrayList<>();
            locais = new ArrayList<>();
            fotografos = new ArrayList<>();
        }
    }

    private void limparFormulario() {
        entity = new FotoEntity();
        generoId = null;
        localId = null;
        fotografoId = null;
        uploadedFile = null;
        selected = null;
    }

    // Getters e Setters
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