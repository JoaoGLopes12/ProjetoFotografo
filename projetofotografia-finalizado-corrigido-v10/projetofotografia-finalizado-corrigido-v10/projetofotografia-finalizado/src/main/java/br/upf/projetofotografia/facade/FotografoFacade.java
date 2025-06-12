package br.upf.projetofotografia.facade;

import br.upf.projetofotografia.entity.FotografoEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class FotografoFacade {

    @PersistenceContext(unitName = "ProjetofotografiaPU")
    private EntityManager em;

    public void create(FotografoEntity entity) {
        em.persist(entity);
    }

    public void edit(FotografoEntity entity) {
        em.merge(entity);
    }

    public void remove(FotografoEntity entity) {
        em.remove(em.merge(entity));
    }

    public FotografoEntity find(Object id) {
        return em.find(FotografoEntity.class, id);
    }

    public List<FotografoEntity> findAll() {
        return em.createQuery("SELECT f FROM FotografoEntity f", FotografoEntity.class).getResultList();
    }

    public FotografoEntity findByEmailAndSenha(String email, String senha) {
        try {
            return em.createQuery("SELECT f FROM FotografoEntity f WHERE f.email = :email AND f.senha = :senha", FotografoEntity.class)
                .setParameter("email", email)
                .setParameter("senha", senha)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
