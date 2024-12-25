package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.model.Carrera;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;

import java.io.File;
import java.util.*;

public class MateriaCarreraCache {
    private static final Map<Carrera, List<Materia>> LIST_CARRERAS;
    private static final Map<Carrera, Map<String, Materia>> MAP_CARRERAS;

    private MateriaCarreraCache() {}

    static {
        LIST_CARRERAS = new HashMap<>();
        MAP_CARRERAS = new HashMap<>();
        agregarCarreras("src/main/resources/materias");
    }

    private static void agregarCarreras(String ruta) {
        File directorio = new File(ruta);
        File[] archivos = directorio.listFiles();
        for (File archivo : archivos) {
            String rutaArchivo = archivo.getPath();
            agregarCarrera(rutaArchivo);
        }
    }

    private static void agregarCarrera(String ruta) {
        MateriaCarreraService ms = new MateriaCarreraService();
        ms.cargarMateriasDesdeArchivo(ruta);
        List<Materia> materias = ms.obtenerMaterias();
        Carrera carrera = obtenerCarrera(ruta);
        agregarCache(carrera, materias);
    }

    private static void agregarCache(Carrera carrera, List<Materia> materias) {
        List<Materia> listMateriasInmodificable = Collections.unmodifiableList(materias);
        LIST_CARRERAS.put(carrera, listMateriasInmodificable);

        Map<String, Materia> mapaMateriasInmodificable = Collections.unmodifiableMap(MAP_CARRERAS.get(carrera));
        MAP_CARRERAS.put(carrera, mapaMateriasInmodificable);
    }

    private static Carrera obtenerCarrera(String ruta) {
        File archivo = new File(ruta);
        String nombre = archivo.getName();
        Carrera carrera = Carrera.getCarrera(nombre);
        return carrera;
    }

    public static List<Materia> getListMaterias(Carrera carrera) {
        return LIST_CARRERAS.get(carrera);
    }

    public static Map<String, Materia> getMapMaterias(Carrera carrera) {
        return MAP_CARRERAS.get(carrera);
    }

    public static List<Materia> getListMateriasCopy(Carrera carrera) {
        return new ArrayList<>(LIST_CARRERAS.get(carrera));
    }

    public static Map<String, Materia> getMapMateriasCopy(Carrera carrera) {
        return new HashMap<>(MAP_CARRERAS.get(carrera));
    }
}
