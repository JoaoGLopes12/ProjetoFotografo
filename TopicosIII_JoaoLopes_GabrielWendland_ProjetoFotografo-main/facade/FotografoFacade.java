package br.upf.projetojfprimefaces.facade;

import br.upf.projetojfprimefaces.entity.FotografoEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class FotografoFacade extends AbstractFacade<FotografoEntity> {

    @PersistenceContext(unitName = "SeuPU") // substitua "SeuPU" pelo nome correto da sua persistence unit
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FotografoFacade() {
        super(FotografoEntity.class);
    }

    public FotografoEntity findByEmailAndSenha(String email, String senha) {
        try {
            return em.createQuery(
                "SELECT f FROM FotografoEntity f WHERE f.email = :email AND f.senha = :senha", FotografoEntity.class)
                .setParameter("email", email)
                .setParameter("senha", senha)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
