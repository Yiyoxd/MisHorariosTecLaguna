package com.yiyoalfredo.mishorariosteclaguna.model;

import java.time.DayOfWeek;

public class MateriaClase {
    private final Materia materia;
    private final Horario horario[];

    public MateriaClase(Materia materia) {
        this.materia = materia;
        horario = new Horario[5];
    }

    public Materia getMateria() {
        return materia;
    }

    public Horario[] getHorario() {
        return horario;
    }

    public void agregarHorario(Horario horario) {
        int index = getIndexDayOfWeek(horario.getDia());
        this.horario[index] = horario;
    }

    private static int getIndexDayOfWeek(DayOfWeek day) {
        return switch (day) {
            case MONDAY -> 0;
            case TUESDAY -> 1;
            case WEDNESDAY -> 2;
            case THURSDAY -> 3;
            case FRIDAY -> 4;
            default -> -1;
        };
    }
}
