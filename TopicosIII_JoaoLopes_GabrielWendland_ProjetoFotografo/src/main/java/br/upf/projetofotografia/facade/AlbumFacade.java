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

}


