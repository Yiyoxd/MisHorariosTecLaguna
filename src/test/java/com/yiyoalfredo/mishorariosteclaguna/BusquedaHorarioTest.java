package com.yiyoalfredo.mishorariosteclaguna;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yiyoalfredo.mishorariosteclaguna.excepcion.LoginException;
import com.yiyoalfredo.mishorariosteclaguna.model.*;
import com.yiyoalfredo.mishorariosteclaguna.service.BusquedaHorario;
import com.yiyoalfredo.mishorariosteclaguna.service.KardexParser;
import com.yiyoalfredo.mishorariosteclaguna.service.MateriaCarreraCache;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BusquedaHorarioTest {
    private static final String MATRICULA = "jeje";
    private static final String PASS = "JEJE";

    @Test
    public void testSize() {
        long startTime = System.currentTimeMillis();
        var horarios = xd();
        long endTime = System.currentTimeMillis();
        System.out.println(horarios.size() + " combinaciones encontradas en " + periodo(startTime, endTime));

        long timep = System.currentTimeMillis();
        BusquedaHorario busquedaHorario = new BusquedaHorario(new KardexParser().parseKardexFromWeb(MATRICULA, PASS));
        horarios = busquedaHorario.posiblesHorarios(0, 36);
        long time2 = System.currentTimeMillis();
        System.out.println(horarios.size() + " combinaciones encontradas en " + periodo(timep, time2));
    }

    private void imprimirHorarios(List<List<MateriaHorario>> horarios) {
        for (List<MateriaHorario> actualList : horarios) {
            int sum = actualList.stream().mapToInt(e -> e.getMateria().getCreditos()).sum();
            actualList.forEach(e -> {
                System.out.println(e);
                System.out.println("---------------------------------------\n");
            });
            System.out.println("\n\n\n\n\n");
        }
    }

    @Test
    public void combinacionesConPrecarga()  {
        xd();
        long timep = System.currentTimeMillis();
        var xd = xd();
        long time2 = System.currentTimeMillis();
        System.out.println((time2 - timep) / 1000.0 + " segundos");
        imprimirHorarios(xd);
        guardarSoloGruposJSON(xd);
    }

    public List<List<MateriaHorario>> xd(){
        Alumno alu = new KardexParser().parseKardexFromWeb(MATRICULA, PASS);

        List<String> maestrosAEvitar = List.of("DE AVILA SANCHEZ RICARDO");
        Map<String, Materia> materiasCarrera = MateriaCarreraCache.getMapMaterias(alu.getCarrera());
        Materia materia1 = materiasCarrera.get("A17");
        Materia materia3 = materiasCarrera.get("E16");
        Materia materia4 = materiasCarrera.get("A18");
        Materia materia5 = materiasCarrera.get("F16");
        List<Materia> materiasATomar = List.of(materia1, materia3, materia4, materia5);
        materiasATomar = new ArrayList<>();
        BusquedaHorario busquedaHorario = new BusquedaHorario(alu);
        var materias = busquedaHorario.posiblesHorarios(0, 36, 0, 20, maestrosAEvitar, materiasATomar);
        return materias;
    }

    public void guardarJSON(List<List<MateriaHorario>> horariosDeMaterias) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("horarios.json"), horariosDeMaterias);
            System.out.println("Archivo JSON guardado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarSoloGruposJSON(List<List<MateriaHorario>> horariosDeMaterias) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            List<List<String>> grupos = horariosDeMaterias.stream()
                    .map(materias -> materias.stream()
                            .map(MateriaHorario::getGrupo)
                            .toList()
                    )
                    .toList();

            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("grupos.json"), grupos);
            System.out.println("Archivo JSON con grupos guardado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar los grupos en el archivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String periodo(long inicio, long fin) {
        double total = (fin - inicio) / 1000.0;
        return String.format("%.2f", total) + " segundos";
    }
}
