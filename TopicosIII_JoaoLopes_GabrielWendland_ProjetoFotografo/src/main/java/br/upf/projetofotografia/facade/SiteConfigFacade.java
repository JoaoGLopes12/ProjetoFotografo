
package br.upf.projetofotografia.facade;

import br.upf.projetofotografia.entity.SiteConfig;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class SiteConfigFacade {

    @PersistenceContext
    private EntityManager em;

    // Method to list all site configurations
    public List<SiteConfig> listar() {
        return em.createQuery("SELECT s FROM SiteConfig s", SiteConfig.class).getResultList();
    }

    // Method to save a new site configuration
    public void salvar(SiteConfig siteConfig) {
        em.persist(siteConfig);
    }

    // Method to update an existing site configuration
    public void atualizar(SiteConfig siteConfig) {
        em.merge(siteConfig);
    }

    // Method to delete a site configuration
    public void excluir(SiteConfig siteConfig) {
        em.remove(em.merge(siteConfig));
    }
}
