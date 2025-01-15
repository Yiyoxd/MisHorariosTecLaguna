package com.yiyoalfredo.mishorariosteclaguna;

import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;
import com.yiyoalfredo.mishorariosteclaguna.model.Carrera;
import com.yiyoalfredo.mishorariosteclaguna.service.KardexParser;
import com.yiyoalfredo.mishorariosteclaguna.service.MateriaHorarioCache;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            MateriaHorarioCache.getListMaterias(Carrera.INGENIERIA_EN_SISTEMAS_COMPUTACIONALES)
                    .stream().sorted().forEach(System.out::println);
            Alumno alumno = new KardexParser().parseKardexFromWeb("Aqui la matricula", "Aqui la pass");
            System.out.println("Nombre del Alumno: " + alumno.getNombre());
            System.out.println("Número de Control: " + alumno.getNumControl());
            System.out.println("Materias Cursadas: " + alumno.getMateriasCursadas());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
