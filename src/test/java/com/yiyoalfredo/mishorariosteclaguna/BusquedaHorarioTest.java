package com.yiyoalfredo.mishorariosteclaguna;

import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;
import com.yiyoalfredo.mishorariosteclaguna.model.Horario;
import com.yiyoalfredo.mishorariosteclaguna.model.MateriaHorario;
import com.yiyoalfredo.mishorariosteclaguna.service.BusquedaHorario;
import com.yiyoalfredo.mishorariosteclaguna.service.KardexParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BusquedaHorarioTest {
    @Test
    public void testSize() {
        Alumno alumno1;
        try {
            alumno1 = new KardexParser().parseKardex("C:\\Users\\yiyoa\\Downloads\\Yiyo\\frmKardex.aspx.html");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BusquedaHorario busquedaHorario = new BusquedaHorario(alumno1);
        List<List<MateriaHorario>> horarios = busquedaHorario.posiblesHorarios(36, 36, 7, 16);
        System.out.println("\n\n\n\n\n" + horarios.size() + "\n\n\n\n");

//        List<List<MateriaHorario>> horariosFiltrados = filtro(horarios, 7, 13);
//        System.out.println("\n\n\n\n\n" + horariosFiltrados.size() + "\n\n\n\n");
//        System.out.println(horariosFiltrados);

        /*
        for (int i = 0; i < horarios.size(); i++) {
            List<MateriaHorario> materias = horarios.get(i);
            for (int j = 0; j < materias.size(); j++) {
                System.out.println(materias.get(j));
            }
            System.out.println("\n\n");
        }

         */

        //Assertions.assertFalse(horarios.isEmpty());
    }
}
