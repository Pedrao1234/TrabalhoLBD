package dev.lbd.biblioteca.modulos.bookAuthor.enums;

public enum StatusAuthor {
    PRIMARY("Principal"),
    SECUNDARY("Secundario");

    private final String label;

    StatusAuthor(String label) { this.label = label; }

    public String getLabel() { return label; }
}
