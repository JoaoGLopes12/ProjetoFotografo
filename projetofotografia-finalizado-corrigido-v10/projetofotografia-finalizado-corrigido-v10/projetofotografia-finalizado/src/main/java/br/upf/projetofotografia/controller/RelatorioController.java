package br.upf.projetofotografia.controller;

import br.upf.projetofotografia.facade.RelatorioFacade;
import jakarta.inject.Inject;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class RelatorioController {

    @Inject
    private RelatorioFacade relatorioFacade;

    private String tipoRelatorio;

    public String getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(String tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    public void gerarRelatorio() {
        relatorioFacade.gerarRelatorio(tipoRelatorio);
    }
}