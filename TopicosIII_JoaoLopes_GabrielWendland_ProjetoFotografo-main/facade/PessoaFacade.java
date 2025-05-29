package br.upf.projetojfprimefaces.facade;

import br.upf.projetojfprimefaces.entity.PessoaEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PessoaFacade {

    @PersistenceContext(unitName = "projetojfprimefacesPU")
    private EntityManager em;

    public void create(PessoaEntity pessoa) {
        em.persist(pessoa);
    }

    public void edit(PessoaEntity pessoa) {
        em.merge(pessoa);
    }

    public void remove(PessoaEntity pessoa) {
        em.remove(em.merge(pessoa));
    }

    public PessoaEntity find(Long id) {
        return em.find(PessoaEntity.class, id);
    }

    public List<PessoaEntity> findAll() {
        TypedQuery<PessoaEntity> query = em.createQuery("SELECT p FROM PessoaEntity p", PessoaEntity.class);
        return query.getResultList();
    }

    public PessoaEntity findByEmail(String email) {
        try {
            TypedQuery<PessoaEntity> query = em.createQuery("SELECT p FROM PessoaEntity p WHERE p.email = :email", PessoaEntity.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
