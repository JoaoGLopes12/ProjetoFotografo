
package br.upf.projetojfprimefaces.enumeration;

public enum UFEnum {
    
    RS("Rio Grande do Sul"),
    SC("Santa Catarina"),
    SP("São Paulo");

    private final String value;

    private UFEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
}
