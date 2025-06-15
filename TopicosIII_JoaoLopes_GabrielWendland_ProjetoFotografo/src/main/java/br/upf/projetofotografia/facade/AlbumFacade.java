package br.upf.projetofotografia.facade;

import br.upf.projetofotografia.entity.AlbumEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class AlbumFacade extends AbstractFacade<AlbumEntity> {

    @PersistenceContext(unitName = "ProjetofotografiaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlbumFacade() {
        super(AlbumEntity.class);
    }

    // Método adicional para buscar álbuns por fotógrafo
    public java.util.List<AlbumEntity> findByFotografo(Integer fotografoId) {
        return em.createQuery("SELECT a FROM AlbumEntity a WHERE a.fotografo.id = :fotografoId", AlbumEntity.class)
                .setParameter("fotografoId", fotografoId)
                .getResultList();
    }

    // Método adicional para buscar álbuns por título
    public java.util.List<AlbumEntity> findByTitulo(String titulo) {
        return em.createQuery("SELECT a FROM AlbumEntity a WHERE LOWER(a.titulo) LIKE LOWER(:titulo)", AlbumEntity.class)
                .setParameter("titulo", "%" + titulo + "%")
                .getResultList();
    }
    
}
