package dev.lbd.biblioteca.modulos.author.enums;

public enum SexAuthor {
    MALE("Masculino"),
    FEMALE("Feminino");

    private final String label;

    SexAuthor(String label) { this.label = label; }

    public String getLabel() { return label; }
}
