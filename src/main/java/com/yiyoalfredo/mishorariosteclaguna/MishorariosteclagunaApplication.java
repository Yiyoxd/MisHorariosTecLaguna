package com.yiyoalfredo.mishorariosteclaguna;

import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;
import com.yiyoalfredo.mishorariosteclaguna.model.MateriaHorario;
import com.yiyoalfredo.mishorariosteclaguna.service.BusquedaHorario;
import com.yiyoalfredo.mishorariosteclaguna.service.KardexParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MishorariosteclagunaApplication {

    public static void main(String[] args) {
        //SpringApplication.run(MishorariosteclagunaApplication.class, args);
        Alumno alumno = new KardexParser().parseKardexFromWeb("22130550", "wb4727lag");
        BusquedaHorario busquedaHorario = new BusquedaHorario(alumno);
        List<List<MateriaHorario>> horarios = busquedaHorario.posiblesHorarios(0, 36, 0, 20);
        System.out.println(horarios.size());

        long timep = System.currentTimeMillis();
        busquedaHorario = new BusquedaHorario(new KardexParser().parseKardexFromWeb("22130550", "wb4727lag"));
        horarios = busquedaHorario.posiblesHorarios(0, 36, 0, 20);
        long time2 = System.currentTimeMillis();
        System.out.println((time2 - timep) / 1000.0 + " segundos");
    }

}
