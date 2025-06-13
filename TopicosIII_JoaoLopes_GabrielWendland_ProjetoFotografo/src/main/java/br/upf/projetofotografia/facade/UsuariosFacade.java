
package br.upf.projetofotografia.facade;

import br.upf.projetofotografia.entity.UsuarioEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UsuariosFacade {

    @PersistenceContext
    private EntityManager em;  // EntityManager to manage persistence

    // Method to list all users
    public List<UsuarioEntity> listar() {
        return em.createQuery("SELECT u FROM UsuarioEntity u", UsuarioEntity.class).getResultList();
    }

    // Method to save a new user
    public void salvar(UsuarioEntity usuario) {
        em.persist(usuario);  // Persist new user
    }

    // Method to update an existing user
    public void atualizar(UsuarioEntity usuario) {
        em.merge(usuario);  // Update user
    }

    // Method to delete a user
    public void excluir(UsuarioEntity usuario) {
        em.remove(em.merge(usuario));  // Remove user
    }
}
