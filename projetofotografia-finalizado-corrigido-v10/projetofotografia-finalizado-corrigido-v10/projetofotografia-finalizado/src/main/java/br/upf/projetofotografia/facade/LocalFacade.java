package br.upf.projetofotografia.facade;

import br.upf.projetofotografia.entity.LocalEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class LocalFacade {

    @PersistenceContext(unitName = "ProjetofotografiaPU")
    private EntityManager em;

    public void create(LocalEntity entity) {
        em.persist(entity);
    }

    public void edit(LocalEntity entity) {
        em.merge(entity);
    }

    public void remove(LocalEntity entity) {
        em.remove(em.merge(entity));
    }

    public LocalEntity find(Object id) {
        return em.find(LocalEntity.class, id);
    }

    public List<LocalEntity> findAll() {
        return em.createQuery("SELECT l FROM LocalEntity l", LocalEntity.class).getResultList();
    }
}
