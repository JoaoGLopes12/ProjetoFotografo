package br.upf.projetofotografia.controller;

import br.upf.projetofotografia.entity.SiteConfig;
import br.upf.projetofotografia.facade.SiteConfigFacade;
import jakarta.inject.Inject;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class ConfigController {

    @Inject
    private SiteConfigFacade siteConfigFacade;

    private SiteConfig siteConfig = new SiteConfig();

    public SiteConfig getSiteConfig() {
        return siteConfig;
    }

    public void setSiteConfig(SiteConfig siteConfig) {
        this.siteConfig = siteConfig;
    }

    public void salvar() {
        siteConfigFacade.salvar(siteConfig);
    }
}