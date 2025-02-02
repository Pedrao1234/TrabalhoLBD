package dev.lbd.biblioteca.modulos.book.enums;

public enum StatusBook {
    AVAILABLE("Disponível"),
    UNAVAILABLE("Indisponível");

    private final String label;

    StatusBook(String label) { this.label = label; }

    public String getLabel() { return label; }
}
