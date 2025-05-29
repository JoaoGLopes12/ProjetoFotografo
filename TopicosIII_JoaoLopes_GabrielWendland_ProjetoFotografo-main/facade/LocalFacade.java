package br.upf.projetojfprimefaces.facade;

import br.upf.projetojfprimefaces.entity.LocalEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class LocalFacade extends AbstractFacade<LocalEntity> {

    @PersistenceContext(unitName = "SeuPU")
    private EntityManager em;

    public LocalFacade() {
        super(LocalEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
