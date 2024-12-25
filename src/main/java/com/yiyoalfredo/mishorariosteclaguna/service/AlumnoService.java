package com.yiyoalfredo.mishorariosteclaguna.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;

import java.io.File;
import java.io.IOException;

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
}
