package dev.lbd.biblioteca.modulos.rent.enums;

public enum StatusEnum {
    OCURRING("Alugado "),
    DELAYED("Atrasado"),
    FINISHED("Devolução concluída");

    private final String label;

    StatusEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
