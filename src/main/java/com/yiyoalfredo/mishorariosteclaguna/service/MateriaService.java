package com.yiyoalfredo.mishorariosteclaguna.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MateriaService {
    private List<Materia> materias;
    private static final Map<String, List<Materia>> cache = new HashMap<>();

    public MateriaService() {
        this.materias = new ArrayList<>();
    }

    /**
     * Carga las materias desde un JSON en formato de cadena.
     *
     * @param json JSON que representa una lista de materias.
     */
    private void cargarMateriasDesdeJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Materia> materiasCargadas = mapper.readValue(json, new TypeReference<List<Materia>>() {});
            materias.addAll(materiasCargadas);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar las materias desde el JSON", e);
        }
    }

    /**
     * Carga las materias desde un archivo JSON con soporte para caché.
     *
     * @param rutaArchivo Ruta al archivo JSON.
     */
    public void cargarMateriasDesdeArchivo(String rutaArchivo) {
        if (cache.containsKey(rutaArchivo)) {
            materias = cache.get(rutaArchivo); // Usar datos cacheados
            return;
        }

        try {
            String jsonContenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            ObjectMapper mapper = new ObjectMapper();
            List<Materia> materiasCargadas = mapper.readValue(jsonContenido, new TypeReference<List<Materia>>() {});
            cache.put(rutaArchivo, materiasCargadas);
            materias = new ArrayList<>(materiasCargadas);
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
        return materias;
    }

    /**
     * Limpia la caché para un archivo específico o toda la caché si el argumento es null.
     *
     * @param rutaArchivo Ruta al archivo JSON o null para limpiar toda la caché.
     */
    public static void limpiarCache(String rutaArchivo) {
        cache.remove(rutaArchivo);
    }
}
