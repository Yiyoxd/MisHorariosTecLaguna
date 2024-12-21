package com.yiyoalfredo.mishorariosteclaguna.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Horario {
    private final DayOfWeek dia;
    private final LocalTime horaInicio;
    private final LocalTime horaFin;
    final String aula;

    public Horario(DayOfWeek dia, LocalTime horaInicio, LocalTime horaFin, String aula) {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.aula = aula;
    }

    public DayOfWeek getDia() {
        return dia;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public String getAula() {
        return aula;
    }
}
