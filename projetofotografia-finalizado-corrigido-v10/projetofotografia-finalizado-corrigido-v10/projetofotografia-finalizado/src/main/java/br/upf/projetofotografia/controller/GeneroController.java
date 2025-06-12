package br.upf.projetofotografia.controller;

import br.upf.projetofotografia.entity.GeneroEntity;
import br.upf.projetofotografia.facade.GeneroFacade;
import jakarta.inject.Inject;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.model.DataModel;
import jakarta.faces.model.ListDataModel;

@RequestScoped
public class GeneroController {

    @Inject
    private GeneroFacade generoFacade;  // Injetando a fachada diretamente

    private GeneroEntity entity = new GeneroEntity();  // A entidade usada no formulário
    private GeneroEntity selected;  // Gênero selecionado para edição

    private DataModel<GeneroEntity> generos;  // A lista de gêneros

    // Getter para 'generos'
    public DataModel<GeneroEntity> getGeneros() {
        if (generos == null) {
            generos = new ListDataModel<>(generoFacade.listar());  // Usando a fachada para listar os gêneros
        }
        return generos;
    }

    // Getter e Setter para a entidade 'entity' (usado no formulário de cadastro/edição)
    public GeneroEntity getEntity() {
        return entity;
    }

    public void setEntity(GeneroEntity selected) {
        this.selected = selected;
        if (selected != null) {
            this.entity = selected;  // Carrega os dados do gênero selecionado para edição
        }
    }

    // Getter e Setter para o 'selected' (usado para referência ao gênero selecionado)
    public GeneroEntity getSelected() {
        return selected;
    }

    public void setSelected(GeneroEntity selected) {
        this.selected = selected;
    }

    // Método para salvar ou editar um gênero
    public void salvar() {
        if (entity.getId() == null) {
            generoFacade.salvar(entity);  // Salva um novo gênero
        } else {
            generoFacade.atualizar(entity);  // Atualiza um gênero existente
        }
    }

    // Método para excluir um gênero
    public void excluir() {
        generoFacade.excluir(selected);  // Exclui o gênero selecionado
    }

    // Método para iniciar um novo gênero
    public void novo() {
        entity = new GeneroEntity();  // Cria uma nova instância de GeneroEntity
    }
}
