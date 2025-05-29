package br.upf.projetojfprimefaces.facade;

import br.upf.projetojfprimefaces.entity.GeneroEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class GeneroFacade extends AbstractFacade<GeneroEntity> {

    @PersistenceContext(unitName = "SeuPU")
    private EntityManager em;

    public GeneroFacade() {
        super(GeneroEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
