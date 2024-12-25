package com.yiyoalfredo.mishorariosteclaguna.model;

public class MateriaHorario {
    private final Materia materia;
    private final Horario horario[];
    private final String maestro;

    public MateriaHorario(Materia materia, Horario[] horario, String maestro) {
        this.materia = materia;
        this.horario = horario;
        this.maestro = maestro;
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
