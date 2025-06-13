package br.upf.projetofotografia.controller;

import br.upf.projetofotografia.entity.Usuario;
import br.upf.projetofotografia.facade.UsuarioFacade;
import jakarta.inject.Inject;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class UsuarioController {

    @Inject
    private UsuarioFacade usuarioFacade;

    private Usuario usuario = new Usuario();

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void salvar() {
        usuarioFacade.salvar(usuario);
    }
}