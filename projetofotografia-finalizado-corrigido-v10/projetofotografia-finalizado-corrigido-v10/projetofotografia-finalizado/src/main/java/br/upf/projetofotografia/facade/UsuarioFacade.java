
package br.upf.projetofotografia.facade;

import br.upf.projetofotografia.entity.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UsuarioFacade {

    @PersistenceContext
    private EntityManager em;

    // Method to list all users
    public List<Usuario> listar() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    // Method to save a new user
    public void salvar(Usuario usuario) {
        em.persist(usuario);
    }

    // Method to update an existing user
    public void atualizar(Usuario usuario) {
        em.merge(usuario);
    }

    // Method to delete a user
    public void excluir(Usuario usuario) {
        em.remove(em.merge(usuario));
    }
}
