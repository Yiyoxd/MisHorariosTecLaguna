package com.yiyoalfredo.mishorariosteclaguna.model;

public enum Carrera {
    INGENIERIA_EN_SISTEMAS_COMPUTACIONALES;

    @Override
    public String toString() {
        return switch (this) {
            case INGENIERIA_EN_SISTEMAS_COMPUTACIONALES -> "INGENIERIA EN SISTEMAS COMPUTACIONALES";
        };
    }

    public static Carrera getCarrera(String carrera) {
        carrera = carrera.toUpperCase();
        return switch (carrera) {
            case "INGENIERIA EN SISTEMAS COMPUTACIONALES" -> INGENIERIA_EN_SISTEMAS_COMPUTACIONALES;
            default -> null;
        };
    }
}