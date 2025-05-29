package br.upf.projetojfprimefaces.bean;

import br.upf.projetojfprimefaces.entity.GeneroEntity;
import br.upf.projetojfprimefaces.facade.GeneroFacade;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class GeneroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private GeneroFacade generoFacade;

    private GeneroEntity generoSelecionado;
    private List<GeneroEntity> listaGeneros;

    @PostConstruct
    public void init() {
        listaGeneros = generoFacade.findAll();
        generoSelecionado = new GeneroEntity();
    }

    // Métodos para CRUD

    public void salvar() {
        if (generoSelecionado.getId() == null) {
            generoFacade.create(generoSelecionado);
        } else {
            generoFacade.edit(generoSelecionado);
        }
        limpar();
        atualizarLista();
    }

    public void editar(GeneroEntity genero) {
        this.generoSelecionado = genero;
    }

    public void remover(GeneroEntity genero) {
        generoFacade.remove(genero);
        atualizarLista();
    }

    public void limpar() {
        generoSelecionado = new GeneroEntity();
    }

    public void atualizarLista() {
        listaGeneros = generoFacade.findAll();
    }

    // Getters e Setters

    public GeneroEntity getGeneroSelecionado() {
        return generoSelecionado;
    }

    public void setGeneroSelecionado(GeneroEntity generoSelecionado) {
        this.generoSelecionado = generoSelecionado;
    }

    public List<GeneroEntity> getListaGeneros() {
        return listaGeneros;
    }

    public void setListaGeneros(List<GeneroEntity> listaGeneros) {
        this.listaGeneros = listaGeneros;
    }
}
