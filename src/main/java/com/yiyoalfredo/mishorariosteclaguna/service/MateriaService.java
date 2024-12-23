package com.yiyoalfredo.mishorariosteclaguna.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MateriaService {

    private final List<Materia> materias;

    public MateriaService() {
        this.materias = new ArrayList<>();
    }

    /**
     * Carga las materias desde un JSON en formato de cadena.
     *
     * @param json JSON que representa una lista de materias.
     */
    public void cargarMateriasDesdeJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Materia> materiasCargadas = mapper.readValue(json, new TypeReference<List<Materia>>() {});
            materias.addAll(materiasCargadas);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar las materias desde el JSON", e);
        }
    }

    /**
     * Carga las materias desde un archivo JSON.
     *
     * @param rutaArchivo Ruta al archivo JSON.
     */
    public void cargarMateriasDesdeArchivo(String rutaArchivo) {
        try {
            String jsonContenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            cargarMateriasDesdeJson(jsonContenido);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar las materias desde el archivo: " + rutaArchivo, e);
        }
    }

    /**
     * Obtiene todas las materias cargadas.
     *
     * @return Lista de materias cargadas.
     */
    public List<Materia> obtenerMaterias() {
        return new ArrayList<>(materias); // Devuelve una copia para proteger la lista interna
    }
}
