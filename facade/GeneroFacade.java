package br.upf.projetofotografia.facade;

import br.upf.projetofotografia.entity.GeneroEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class GeneroFacade {

    @PersistenceContext(unitName = "ProjetofotografiaPU")
    private EntityManager em;

    public void create(GeneroEntity entity) {
        em.persist(entity);
    }

    public void edit(GeneroEntity entity) {
        em.merge(entity);
    }

    public void remove(GeneroEntity entity) {
        em.remove(em.merge(entity));
    }

    public GeneroEntity find(Object id) {
        return em.find(GeneroEntity.class, id);
    }

    public List<GeneroEntity> findAll() {
        return em.createQuery("SELECT g FROM GeneroEntity g", GeneroEntity.class).getResultList();
    }
}
