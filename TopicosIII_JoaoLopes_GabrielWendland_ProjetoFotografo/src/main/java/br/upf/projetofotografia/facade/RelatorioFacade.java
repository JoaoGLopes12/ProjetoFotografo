
package br.upf.projetofotografia.facade;

import jakarta.ejb.Stateless;

@Stateless
public class RelatorioFacade {

    // Method to generate a report based on a given parameter
    public void gerarRelatorio(String parametro) {
        // Implement the logic for generating a report based on the provided parameter
        System.out.println("Relatório gerado com o parâmetro: " + parametro);
    }
}
