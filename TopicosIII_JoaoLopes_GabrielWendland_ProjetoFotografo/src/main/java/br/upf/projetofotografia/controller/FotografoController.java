package br.upf.projetofotografia.controller;

import br.upf.projetofotografia.entity.FotografoEntity;
import br.upf.projetofotografia.facade.FotografoFacade;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("fotografoController")
@SessionScoped
public class FotografoController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private FotografoFacade fotografoFacade;
    
    private List<FotografoEntity> list;
    private FotografoEntity entity;
    private FotografoEntity selected;

    @PostConstruct
    public void init() {
        System.out.println("=== INICIALIZANDO CONTROLLER ===");
        if (fotografoFacade == null) {
            System.out.println("ERRO: fotografoFacade é null!");
        } else {
            System.out.println("fotografoFacade injetado com sucesso");
        }
        carregarLista();
    }

    private void carregarLista() {
        try {
            System.out.println("=== CARREGANDO LISTA ===");
            if (fotografoFacade != null) {
                list = fotografoFacade.findAll();
                if (list == null) {
                    list = new ArrayList<>();
                }
                System.out.println("Lista carregada com " + list.size() + " fotógrafos");
            } else {
                System.out.println("fotografoFacade é null, criando lista vazia");
                list = new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println("ERRO ao carregar lista: " + e.getMessage());
            e.printStackTrace();
            list = new ArrayList<>();
            addMessage("Erro ao carregar lista de fotógrafos: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void novo() {
        System.out.println("=== MÉTODO NOVO CHAMADO ===");
        entity = new FotografoEntity();
        System.out.println("Nova entidade criada");
        addMessage("Formulário preparado para novo fotógrafo", FacesMessage.SEVERITY_INFO);
    }

    public void salvar() {
        System.out.println("=== MÉTODO SALVAR CHAMADO ===");
        
        if (entity == null) {
            System.out.println("ERRO: entity é null");
            addMessage("Erro: dados não encontrados", FacesMessage.SEVERITY_ERROR);
            return;
        }

        if (fotografoFacade == null) {
            System.out.println("ERRO: fotografoFacade é null");
            addMessage("Erro: serviço não disponível", FacesMessage.SEVERITY_ERROR);
            return;
        }

        try {
            System.out.println("Dados da entidade:");
            System.out.println("ID: " + entity.getId());
            System.out.println("Nome: " + entity.getNome());
            System.out.println("Email: " + entity.getEmail());
            
            if (entity.getId() == null) {
                // Novo fotógrafo
                System.out.println("Salvando NOVO fotógrafo");
                fotografoFacade.create(entity);
                addMessage("Fotógrafo cadastrado com sucesso!", FacesMessage.SEVERITY_INFO);
            } else {
                // Editando fotógrafo existente
                System.out.println("Atualizando fotógrafo existente");
                fotografoFacade.edit(entity);
                addMessage("Fotógrafo atualizado com sucesso!", FacesMessage.SEVERITY_INFO);
            }
            
            carregarLista();
            entity = null;
            selected = null;
            System.out.println("Operação concluída com sucesso");
            
        } catch (Exception e) {
            System.out.println("ERRO ao salvar: " + e.getMessage());
            e.printStackTrace();
            
            String mensagem = "Erro ao salvar fotógrafo";
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("email")) {
                mensagem = "Este email já está sendo usado por outro fotógrafo";
            }
            addMessage(mensagem, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void excluir() {
        System.out.println("=== MÉTODO EXCLUIR CHAMADO ===");
        
        if (selected == null) {
            System.out.println("ERRO: Nenhum fotógrafo selecionado");
            addMessage("Selecione um fotógrafo para excluir", FacesMessage.SEVERITY_WARN);
            return;
        }

        if (fotografoFacade == null) {
            System.out.println("ERRO: fotografoFacade é null");
            addMessage("Erro: serviço não disponível", FacesMessage.SEVERITY_ERROR);
            return;
        }

        try {
            System.out.println("Excluindo fotógrafo ID: " + selected.getId() + ", Nome: " + selected.getNome());
            fotografoFacade.remove(selected);
            addMessage("Fotógrafo excluído com sucesso!", FacesMessage.SEVERITY_INFO);
            carregarLista();
            selected = null;
            System.out.println("Fotógrafo excluído com sucesso");
            
        } catch (Exception e) {
            System.out.println("ERRO ao excluir: " + e.getMessage());
            e.printStackTrace();
            addMessage("Erro ao excluir fotógrafo: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar() {
        System.out.println("=== MÉTODO EDITAR CHAMADO ===");
        
        if (selected == null) {
            System.out.println("ERRO: Nenhum fotógrafo selecionado");
            addMessage("Selecione um fotógrafo para editar", FacesMessage.SEVERITY_WARN);
            return;
        }
        
        // Cria uma cópia do objeto selecionado para edição
        entity = new FotografoEntity();
        entity.setId(selected.getId());
        entity.setNome(selected.getNome());
        entity.setEmail(selected.getEmail());
        entity.setSenha(selected.getSenha());
        
        System.out.println("Preparando edição - ID: " + entity.getId() + ", Nome: " + entity.getNome());
        addMessage("Fotógrafo selecionado para edição", FacesMessage.SEVERITY_INFO);
    }

    // Método para teste
    public void teste() {
        System.out.println("=== TESTE DO CONTROLLER ===");
        System.out.println("Controller funcionando!");
        System.out.println("fotografoFacade: " + (fotografoFacade != null ? "OK" : "NULL"));
        System.out.println("list: " + (list != null ? list.size() + " itens" : "NULL"));
        addMessage("Controller está funcionando! Facade: " + (fotografoFacade != null ? "OK" : "NULL"), FacesMessage.SEVERITY_INFO);
    }

    private void addMessage(String message, FacesMessage.Severity severity) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            context.addMessage(null, new FacesMessage(severity, message, null));
        }
        System.out.println("MENSAGEM [" + severity + "]: " + message);
    }

    // Getters e Setters
    public List<FotografoEntity> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setList(List<FotografoEntity> list) {
        this.list = list;
    }

    public FotografoEntity getEntity() {
        return entity;
    }

    public void setEntity(FotografoEntity entity) {
        this.entity = entity;
    }

    public FotografoEntity getSelected() {
        return selected;
    }

    public void setSelected(FotografoEntity selected) {
        this.selected = selected;
        System.out.println("SELECIONADO: " + (selected != null ? "ID: " + selected.getId() + ", Nome: " + selected.getNome() : "null"));
    }
}