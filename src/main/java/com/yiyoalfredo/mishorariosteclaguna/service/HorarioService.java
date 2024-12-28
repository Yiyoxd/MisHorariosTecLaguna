package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.model.Horario;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class HorarioService {
    private HorarioService() {}

    public static boolean chocan(Horario horario1, Horario horario2) {
        if (horario1 == null || horario2 == null) {
            return false;
        }

        LocalTime inicio1 = horario1.getHoraInicio();
        LocalTime fin1 = horario1.getHoraFin();
        LocalTime inicio2 = horario2.getHoraInicio();
        LocalTime fin2 = horario2.getHoraFin();

        return (inicio1.isBefore(fin2) && fin1.isAfter(inicio2));
    }


    public static boolean chocan(Horario[] horario1, Horario[] horario2) {
        if (horario1 == null || horario2 == null) {
            return false;
        }

        for (int i = 0; i < horario1.length; i++) {
            if (chocan(horario1[i], horario2[i])) {
                return true;
            }
        }

        return false;
    }
}
