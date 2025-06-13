package br.upf.projetofotografia.facade;

import br.upf.projetofotografia.entity.FotoEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class FotoFacade {

    @PersistenceContext(unitName = "ProjetofotografiaPU")
    private EntityManager em;

    public void create(FotoEntity entity) {
        em.persist(entity);
    }

    public void edit(FotoEntity entity) {
        em.merge(entity);
    }

    public void remove(FotoEntity entity) {
        em.remove(em.merge(entity));
    }

    public FotoEntity find(Object id) {
        return em.find(FotoEntity.class, id);
    }

    public List<FotoEntity> findAll() {
        return em.createQuery("SELECT f FROM FotoEntity f", FotoEntity.class).getResultList();
    }
}
