package br.upf.projetojfprimefaces.facade;

import br.upf.projetojfprimefaces.entity.FotoEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class FotoFacade extends AbstractFacade<FotoEntity> {

    @PersistenceContext(unitName = "SeuPU")
    private EntityManager em;

    public FotoFacade() {
        super(FotoEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
