package br.upf.projetofotografia.controller;

import br.upf.projetofotografia.entity.FotografoEntity;
import br.upf.projetofotografia.facade.FotografoFacade;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    private FotografoFacade fotografoFacade;

    private FotografoEntity fotografo = new FotografoEntity();

    public FotografoEntity getFotografo() {
        return fotografo;
    }

    public void setFotografo(FotografoEntity fotografo) {
        this.fotografo = fotografo;
    }

    public String validarLogin() {
        FotografoEntity usuario = fotografoFacade.findByEmailAndSenha(fotografo.getEmail(), fotografo.getSenha());
        if (usuario != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fotografoLogado", usuario);
            return "/admin/foto.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login inválido", "Email ou senha incorretos"));
            return null;
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }
}
