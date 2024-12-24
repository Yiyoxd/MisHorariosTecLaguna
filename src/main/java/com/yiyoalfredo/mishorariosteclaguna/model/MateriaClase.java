package com.yiyoalfredo.mishorariosteclaguna.model;

import java.time.DayOfWeek;

public class MateriaClase {
    private final Materia materia;
    private final Horario horario[];
    private String maestro;

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

    public String getMaestro() {
        return maestro;
    }

    public void setMaestro(String maestro) {
        this.maestro = maestro;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String formatoLinea = "%-12s: %-20s%n"; // Formato para alinear los nombres y valores

        sb.append(String.format(formatoLinea, "Materia", materia.getNombre()));
        sb.append(String.format(formatoLinea, "Grupo", materia.getClave()));

        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
        for (int i = 0; i < dias.length; i++) {
            String horarioInfo = horario[i] != null
                    ? horario[i].getHoraInicio() + "-" + horario[i].getHoraFin() + " / " + horario[i].getAula()
                    : "- /";
            sb.append(String.format(formatoLinea, dias[i], horarioInfo));
        }

        sb.append(String.format(formatoLinea, "Catedrático", maestro != null ? maestro : "MAESTRO POR ASIGNAR"));
        return sb.toString();
    }


}
