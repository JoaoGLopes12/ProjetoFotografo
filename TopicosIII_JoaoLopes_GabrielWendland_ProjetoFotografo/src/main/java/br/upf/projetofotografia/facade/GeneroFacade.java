
package br.upf.projetofotografia.facade;

import br.upf.projetofotografia.entity.GeneroEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class GeneroFacade {

    @PersistenceContext
    private EntityManager em;

    // Method to list all genres
    public List<GeneroEntity> listar() {
        return em.createQuery("SELECT g FROM GeneroEntity g", GeneroEntity.class).getResultList();
    }

    // Method to save a new genre
    public void salvar(GeneroEntity genero) {
        em.persist(genero);
    }

    // Method to update an existing genre
    public void atualizar(GeneroEntity genero) {
        em.merge(genero);
    }

    // Method to delete a genre
    public void excluir(GeneroEntity genero) {
        em.remove(em.merge(genero));
    }

    // Method to find all genres
    public List<GeneroEntity> findAll() {
        return em.createQuery("SELECT g FROM GeneroEntity g", GeneroEntity.class).getResultList();
    }

    // Method to find a genre by ID
    public GeneroEntity find(Integer id) {
        return em.find(GeneroEntity.class, id);
    }
}
