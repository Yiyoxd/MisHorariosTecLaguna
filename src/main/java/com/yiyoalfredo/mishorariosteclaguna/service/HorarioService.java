package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.model.Horario;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class HorarioService {
    private HorarioService() {}

    public static boolean chocan(Horario horario1, Horario horario2) {
        LocalTime horarioInicio1 = horario1.getHoraFin();
        LocalTime horarioFin2 = horario2.getHoraInicio();
        if (horarioInicio1.isAfter(horarioFin2) || horarioInicio1.equals(horarioFin2)) {
            return false;
        }

        LocalTime horarioFin1 = horario1.getHoraFin();
        LocalTime horarioInicio2 = horario2.getHoraInicio();
        if (horarioFin1.isBefore(horarioInicio2) || horarioFin2.equals(horarioInicio1)) {
            return false;
        }

        return true;
    }
}
