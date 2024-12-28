package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.model.Carrera;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;
import com.yiyoalfredo.mishorariosteclaguna.model.MateriaHorario;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MateriaHorarioCache {
    private static final Map<Carrera, List<MateriaHorario>> LIST_CARRERAS;
    private static final Map<Carrera, Map<Materia, List<MateriaHorario>>> MAP_CARRERAS;

    private MateriaHorarioCache() {}

    static {
        LIST_CARRERAS = new HashMap<>();
        MAP_CARRERAS = new HashMap<>();
        agregarCarreras("src/main/resources/horarios_carrera");
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
        MateriaHorarioService service = new MateriaHorarioService();
        Carrera carrera = obtenerCarrera(ruta);
        try {
            List<MateriaHorario> materias = service.cargarMateriasDesdeHTML(ruta, carrera);
            agregarCache(carrera, materias);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void agregarCache(Carrera carrera, List<MateriaHorario> materias) {
        List<MateriaHorario> listMateriasInmodificable = Collections.unmodifiableList(materias);
        LIST_CARRERAS.put(carrera, listMateriasInmodificable);

        Map<Materia, List<MateriaHorario>> mapaMaterias = new HashMap<>();
        for (MateriaHorario materiaHorario : materias) {
            List<MateriaHorario> horarios = mapaMaterias.get(materiaHorario.getMateria());
            if (horarios == null) {
                horarios = new ArrayList<>();
                mapaMaterias.put(materiaHorario.getMateria(), horarios);
            }
            horarios.add(materiaHorario);
        }
        mapaMaterias = Collections.unmodifiableMap(mapaMaterias);
        MAP_CARRERAS.put(carrera, mapaMaterias);
    }

    private static Carrera obtenerCarrera(String ruta) {
        File archivo = new File(ruta);
        String nombre = archivo.getName().replaceAll("(?i)\\.html" ,"").trim();;
        Carrera carrera = Carrera.getCarrera(nombre);
        return carrera;
    }

    public static List<MateriaHorario> getListMaterias(Carrera carrera) {
        return LIST_CARRERAS.get(carrera);
    }

    public static Map<Materia, List<MateriaHorario>> getMapMaterias(Carrera carrera) {
        return MAP_CARRERAS.get(carrera);
    }

    public static List<MateriaHorario> getListMateriasCopy(Carrera carrera) {
        return new ArrayList<>(LIST_CARRERAS.get(carrera));
    }

    public static Map<Materia, List<MateriaHorario>> getMapMateriasCopy(Carrera carrera) {
        return new HashMap<>(MAP_CARRERAS.get(carrera));
    }
}
