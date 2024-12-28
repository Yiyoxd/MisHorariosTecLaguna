package com.yiyoalfredo.mishorariosteclaguna.model;

public class MateriaHorario implements Comparable<MateriaHorario> {
    private static final String MAESTRO_DEFAULT = "MAESTRO POR ASIGNAR";
    private final Materia materia;
    private final Horario horario[];
    private final String maestro;
    private final String grupo;

    public MateriaHorario(Materia materia, Horario[] horario, String maestro, String grupo) {
        this.materia = materia;
        this.horario = horario;
        this.maestro = maestro;
        this.grupo = grupo;
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

    public String getGrupo() {
        return grupo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String formatoLinea = "%-12s: %-20s%n"; // Formato para alinear los nombres y valores

        sb.append(String.format(formatoLinea, "Materia", materia.getNombre()));
        sb.append(String.format(formatoLinea, "Grupo", grupo));

        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
        for (int i = 0; i < dias.length; i++) {
            String horarioInfo = horario[i] != null
                    ? horario[i].getHoraInicio() + "-" + horario[i].getHoraFin() + " / " + horario[i].getAula()
                    : "- /";
            sb.append(String.format(formatoLinea, dias[i], horarioInfo));
        }

        sb.append(String.format(formatoLinea, "Catedrático", maestro != null ? maestro : MAESTRO_DEFAULT));
        return sb.toString();
    }

    public int compareTo(MateriaHorario o) {
        if (this.horario[0] == null) {
            return -1;
        }
        return this.horario[0].getHoraFin().compareTo(o.horario[0].getHoraFin());
    }
}
