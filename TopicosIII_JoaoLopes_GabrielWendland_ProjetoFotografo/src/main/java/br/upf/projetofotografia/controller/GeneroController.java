package br.upf.projetofotografia.controller;

import br.upf.projetofotografia.entity.GeneroEntity;
import br.upf.projetofotografia.facade.GeneroFacade;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.model.DataModel;
import jakarta.faces.model.ListDataModel;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named
@RequestScoped
public class GeneroController {

    @Inject
    private GeneroFacade generoFacade;

    private GeneroEntity entity = new GeneroEntity();
    private GeneroEntity selected;
    private DataModel<GeneroEntity> generos;

    // Getter para 'generos'
    public DataModel<GeneroEntity> getGeneros() {
        if (generos == null) {
            generos = new ListDataModel<>(generoFacade.listar());
        }
        return generos;
    }

    // Getter e Setter para a entidade 'entity'
    public GeneroEntity getEntity() {
        return entity;
    }

    public void setEntity(GeneroEntity entity) {
        this.entity = entity;
    }

    // Getter e Setter para o 'selected'
    public GeneroEntity getSelected() {
        return selected;
    }

    public void setSelected(GeneroEntity selected) {
        this.selected = selected;
        System.out.println("Selecionado: " + (selected != null ? selected.getDescricao() : "null"));
    }

    // Método para salvar ou editar um gênero
    public String salvar() {
        try {
            if (entity.getId() == null) {
                generoFacade.salvar(entity);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Gênero salvo com sucesso!"));
            } else {
                generoFacade.atualizar(entity);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Gênero atualizado com sucesso!"));
            }
            recarregarGeneros();
            novo(); // Limpa o formulário após salvar
            return null; // Permanece na mesma página
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar gênero: " + e.getMessage()));
            return null;
        }
    }

    // Método para excluir um gênero
    public void excluir() {
        try {
            if (selected != null) {
                System.out.println("Excluindo gênero: " + selected.getDescricao());
                generoFacade.excluir(selected);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Gênero '" + selected.getDescricao() + "' excluído com sucesso!"));
                recarregarGeneros();
                selected = null; // Limpa a seleção
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Nenhum gênero selecionado para exclusão!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao excluir gênero: " + e.getMessage()));
        }
    }

    // Método para iniciar um novo gênero
    public void novo() {
        entity = new GeneroEntity();
        selected = null;
    }

    // Método para preparar edição
    public void prepararEdicao() {
        if (selected != null) {
            // Copia os dados do item selecionado para a entidade de edição
            entity = new GeneroEntity();
            entity.setId(selected.getId());
            entity.setDescricao(selected.getDescricao());
            System.out.println("Preparando edição para: " + selected.getDescricao());
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Nenhum gênero selecionado para edição!"));
        }
    }

    // Método para recarregar a lista de gêneros
    private void recarregarGeneros() {
        generos = null;
    }
}