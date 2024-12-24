package com.yiyoalfredo.mishorariosteclaguna.model;

public enum Carrera {
    INGENIERIA_EN_SISTEMAS_COMPUTACIONALES;

    @Override
    public String toString() {
        return switch (this) {
            case INGENIERIA_EN_SISTEMAS_COMPUTACIONALES -> "INGENIERIA EN SISTEMAS COMPUTACIONALES";
        };
    }
}