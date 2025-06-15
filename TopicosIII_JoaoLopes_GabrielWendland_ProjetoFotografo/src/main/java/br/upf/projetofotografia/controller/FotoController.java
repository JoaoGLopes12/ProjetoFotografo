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

    // Mantido como Long para compatibilidade com GeneroEntity.getId()
    private Long generoId;
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

    public String salvar() {
        System.out.println("SALVAR FOI CHAMADO!");
        System.out.println("Título: " + entity.getTitulo());
        System.out.println("Descrição: " + entity.getDescricao());
        System.out.println("GeneroId: " + generoId);
        System.out.println("LocalId: " + localId);
        System.out.println("FotografoId: " + fotografoId);

        try {
            // Validações básicas
            if (entity.getTitulo() == null || entity.getTitulo().trim().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Título é obrigatório"));
                return null;
            }
            
            if (entity.getDescricao() == null || entity.getDescricao().trim().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Descrição é obrigatória"));
                return null;
            }

            // Tratamento do arquivo
            if (uploadedFile != null && uploadedFile.getFileName() != null && !uploadedFile.getFileName().isEmpty()) {
                entity.setArquivo(uploadedFile.getFileName());
                System.out.println("Arquivo sendo salvo: " + uploadedFile.getFileName());
            } else {
                System.out.println("Nenhum arquivo enviado.");
                if (entity.getId() == null) { // Apenas para novos registros
                    entity.setArquivo("");
                }
            }

            // Buscar e setar as entidades relacionadas
            if (generoId != null && generoId > 0) {
                GeneroEntity genero = generoFacade.find(generoId);
                if (genero != null) {
                    entity.setGenero(genero);
                    System.out.println("Gênero encontrado: " + genero.getDescricao());
                } else {
                    System.out.println("Gênero não encontrado com ID: " + generoId);
                }
            } else {
                entity.setGenero(null);
            }
            
            if (localId != null && localId > 0) {
                LocalEntity local = localFacade.find(localId);
                if (local != null) {
                    entity.setLocal(local);
                    System.out.println("Local encontrado: " + local.getNome());
                } else {
                    System.out.println("Local não encontrado com ID: " + localId);
                }
            } else {
                entity.setLocal(null);
            }
            
            if (fotografoId != null && fotografoId > 0) {
                FotografoEntity fotografo = fotografoFacade.find(fotografoId);
                if (fotografo != null) {
                    entity.setFotografo(fotografo);
                    System.out.println("Fotógrafo encontrado: " + fotografo.getNome());
                } else {
                    System.out.println("Fotógrafo não encontrado com ID: " + fotografoId);
                }
            } else {
                entity.setFotografo(null);
            }

            // Salvar a entidade
            if (entity.getId() == null) {
                System.out.println("Criando nova foto...");
                fotoFacade.create(entity);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Foto cadastrada com sucesso!"));
                System.out.println("Foto cadastrada com sucesso!");
            } else {
                System.out.println("Editando foto existente...");
                fotoFacade.edit(entity);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Foto atualizada com sucesso!"));
                System.out.println("Foto atualizada com sucesso!");
            }

            // Limpar formulário e atualizar lista
            novo();
            list = fotoFacade.findAll();
            selected = null; // Limpar seleção
            
            return null; // Retorna null para permanecer na mesma página

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERRO NO SALVAMENTO: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", 
                "Erro: " + e.getMessage()));
            return null;
        }
    }

    public void excluir() {
        try {
            if (selected != null) {
                System.out.println("Excluindo foto com ID: " + selected.getId());
                fotoFacade.remove(selected);
                list = fotoFacade.findAll();
                selected = null;
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Foto excluída com sucesso."));
                System.out.println("Foto excluída com sucesso!");
            } else {
                System.out.println("Nenhuma foto selecionada para exclusão");
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Nenhuma foto selecionada para exclusão."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERRO NA EXCLUSÃO: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir", e.getMessage()));
        }
    }

    public void novo() {
        System.out.println("Novo() chamado");
        entity = new FotoEntity();
        generoId = null;
        localId = null;
        fotografoId = null;
        uploadedFile = null;
    }

    public void prepararEdicao() {
        if (selected != null) {
            System.out.println("Preparando edição da foto com ID: " + selected.getId());
            // Cria uma nova instância para evitar problemas de referência
            this.entity = new FotoEntity();
            this.entity.setId(selected.getId());
            this.entity.setTitulo(selected.getTitulo());
            this.entity.setDescricao(selected.getDescricao());
            this.entity.setArquivo(selected.getArquivo());
            this.entity.setGenero(selected.getGenero());
            this.entity.setLocal(selected.getLocal());
            this.entity.setFotografo(selected.getFotografo());
            
            // Definir os IDs para os selects
            this.generoId = (selected.getGenero() != null) ? selected.getGenero().getId() : null;
            this.localId = (selected.getLocal() != null) ? selected.getLocal().getId() : null;
            this.fotografoId = (selected.getFotografo() != null) ? selected.getFotografo().getId() : null;
            uploadedFile = null;
            
            System.out.println("Dados carregados para edição:");
            System.out.println("Título: " + entity.getTitulo());
            System.out.println("GeneroId: " + generoId);
            System.out.println("LocalId: " + localId);
            System.out.println("FotografoId: " + fotografoId);
        } else {
            System.out.println("Nenhuma foto selecionada para edição");
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        this.uploadedFile = event.getFile();
        System.out.println("UPLOAD RECEBIDO: " + uploadedFile.getFileName());
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", 
            "Arquivo '" + uploadedFile.getFileName() + "' foi carregado."));
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

    public Long getGeneroId() {
        return generoId;
    }

    public void setGeneroId(Long generoId) {
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