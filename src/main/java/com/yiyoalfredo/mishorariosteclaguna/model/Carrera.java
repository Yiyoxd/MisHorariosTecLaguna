package com.yiyoalfredo.mishorariosteclaguna.model;

public enum Carrera {
    INGENIERIA_EN_SISTEMAS_COMPUTACIONALES;

    @Override
    public String toString() {
        return this.name().replaceAll("_", " ");
    }

    public static Carrera getCarrera(String carrera) {
        carrera = carrera.toUpperCase();
        return switch (carrera) {
            case "INGENIERIA EN SISTEMAS COMPUTACIONALES" -> INGENIERIA_EN_SISTEMAS_COMPUTACIONALES;
            default -> null;
        };
    }
}