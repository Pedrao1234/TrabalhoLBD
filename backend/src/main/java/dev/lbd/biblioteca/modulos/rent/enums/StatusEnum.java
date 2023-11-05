package dev.lbd.biblioteca.modulos.rent.enums;

public enum StatusEnum {
    OPEN("Aberto "),
    CLOSED("Fechado");

    private final String label;

    StatusEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
