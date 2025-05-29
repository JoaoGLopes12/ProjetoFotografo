//tetse
package br.upf.projetojfprimefaces.controller;

import br.upf.projetojfprimefaces.entity.PessoaEntity;
import br.upf.projetojfprimefaces.facade.PessoaFacade;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

@Named
@SessionScoped
public class LoginController implements Serializable {

    private String email;
    private String senha;

    private PessoaEntity pessoaLogada;

    @Inject
    private PessoaFacade pessoaFacade;

    public String login() {
        PessoaEntity pessoa = pessoaFacade.findByEmail(email);
        if (pessoa != null && pessoa.getSenha().equals(senha)) {
            pessoaLogada = pessoa;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("pessoaLogada", pessoaLogada);
            return "areaAdministrativa.xhtml?faces-redirect=true";
        } else {
            // Aqui você pode adicionar mensagem de erro para o usuário
            FacesContext.getCurrentInstance().addMessage(null, 
                new jakarta.faces.application.FacesMessage("Email ou senha inválidos."));
            return "login.xhtml";
        }
    }

    public String logout() {
        pessoaLogada = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml?faces-redirect=true";
    }

    // Getters e Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PessoaEntity getPessoaLogada() {
        return pessoaLogada;
    }

    public void setPessoaLogada(PessoaEntity pessoaLogada) {
        this.pessoaLogada = pessoaLogada;
    }
}
