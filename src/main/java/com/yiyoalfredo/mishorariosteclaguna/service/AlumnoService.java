package com.yiyoalfredo.mishorariosteclaguna.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlumnoService {
    private final ObjectMapper mapper;

    public AlumnoService() {
        this.mapper = new ObjectMapper();
    }

    public Alumno cargarAlumnoDesdeArchivo(String rutaArchivo) {
        try {
            return mapper.readValue(new File(rutaArchivo), Alumno.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar el archivo JSON: " + rutaArchivo, e);
        }
    }

    public int getCreditos() {
        int creditos = 0;
        for (Materia m : new Alumno().getMateriasCursadas()) {
            creditos += m.getCreditos();
        }

        return 0;
    }

    public static Map<String, Materia> getMapMateriasFaltantes(Alumno alu) {
        List<Materia> cursadas = alu.getMateriasCursadas();
        Map<String, Materia> todas = MateriaCarreraCache.getMapMateriasCopy(alu.getCarrera());

        for (Materia materia : cursadas) {
            todas.remove(materia.getClave());
        }

        return todas;
    }

    public static List<Materia> getListMateriasFaltantes(Alumno alu) {
        List<Materia> cursadas = alu.getMateriasCursadas();
        Map<String, Materia> todas = MateriaCarreraCache.getMapMateriasCopy(alu.getCarrera());

        for (Materia materia : cursadas) {
            todas.remove(alu.getCarrera());
        }

        return new ArrayList<>(todas.values());
    }
}
