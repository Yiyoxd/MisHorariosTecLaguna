package com.yiyoalfredo.mishorariosteclaguna.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Horario {
    public static final DayOfWeek[] WEEK = {
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY
    };

    private final DayOfWeek dia;
    private final LocalTime horaInicio;
    private final LocalTime horaFin;
    private final String aula;

    @JsonCreator
    public Horario(
            @JsonProperty("dia") DayOfWeek dia,
            @JsonProperty("horaInicio") LocalTime horaInicio,
            @JsonProperty("horaFin") LocalTime horaFin,
            @JsonProperty("aula") String aula) {
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

    public String toString() {
        return horaInicio + ":" + horaFin + "/" + aula;
    }
}
