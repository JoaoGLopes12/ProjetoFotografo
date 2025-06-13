package br.upf.projetofotografia.controller;

import br.upf.projetofotografia.entity.LocalEntity;
import br.upf.projetofotografia.facade.LocalFacade;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.util.List;

@Named
@RequestScoped
public class LocalController {

    @Inject
    private LocalFacade localFacade;

    private LocalEntity entity = new LocalEntity();
    private LocalEntity selected;
    private List<LocalEntity> locais;

    // Getter para 'locais'
    public List<LocalEntity> getLocais() {
        if (locais == null) {
            locais = localFacade.findAll();
        }
        return locais;
    }

    public void setLocais(List<LocalEntity> locais) {
        this.locais = locais;
    }

    // Getter e Setter para a entidade 'entity'
    public LocalEntity getEntity() {
        return entity;
    }

    public void setEntity(LocalEntity entity) {
        this.entity = entity;
    }

    // Getter e Setter para o 'selected'
    public LocalEntity getSelected() {
        return selected;
    }

    public void setSelected(LocalEntity selected) {
        this.selected = selected;
        System.out.println("Selecionado: " + (selected != null ? selected.getNome() : "null"));
    }

    // Método para salvar ou editar um local
    public String salvar() {
        try {
            if (entity.getId() == null) {
                localFacade.create(entity);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Local salvo com sucesso!"));
            } else {
                localFacade.edit(entity);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Local atualizado com sucesso!"));
            }
            recarregarLocais();
            novo(); // Limpa o formulário após salvar
            return null; // Permanece na mesma página
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar local: " + e.getMessage()));
            return null;
        }
    }

    // Método para excluir um local
    public void excluir() {
        try {
            if (selected != null) {
                System.out.println("Excluindo local: " + selected.getNome());
                localFacade.remove(selected);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Local '" + selected.getNome() + "' excluído com sucesso!"));
                recarregarLocais();
                selected = null; // Limpa a seleção
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Nenhum local selecionado para exclusão!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao excluir local: " + e.getMessage()));
        }
    }

    // Método para iniciar um novo local
    public void novo() {
        entity = new LocalEntity();
        selected = null;
    }

    // Método para preparar edição
    public void prepararEdicao() {
        if (selected != null) {
            // Copia os dados do item selecionado para a entidade de edição
            entity = new LocalEntity();
            entity.setId(selected.getId());
            entity.setNome(selected.getNome());
            System.out.println("Preparando edição para: " + selected.getNome());
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Nenhum local selecionado para edição!"));
        }
    }

    // Método para recarregar a lista de locais
    private void recarregarLocais() {
        locais = null;
    }
}