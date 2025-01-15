package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.model.Carrera;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;
import com.yiyoalfredo.mishorariosteclaguna.model.MateriaHorario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MateriaHorarioCache {
    private static final Map<Carrera, List<MateriaHorario>> LIST_CARRERAS;
    private static final Map<Carrera, Map<Materia, List<MateriaHorario>>> MAP_CARRERAS;
    private static final String RUTA = "src/main/resources/horarios_carrera";

    private MateriaHorarioCache() {}

    static {
        LIST_CARRERAS = new HashMap<>();
        MAP_CARRERAS = new HashMap<>();
        agregarHorariosFromWeb();
        agregarCarreras();
    }

    private static void agregarHorariosFromWeb() {
        for (Carrera carrera : Carrera.values()) {
            String nombreSimpe = carrera.getSimpleName();
            String html = DataExtractService.fetchHtmlForCareer(nombreSimpe);

            if (html != null && html.contains("Grupo")) {
                String nombreArchivo = RUTA + "/" + carrera.toString() + ".html";
                File archivo = new File(nombreArchivo);

                try (FileWriter writer = new FileWriter(archivo)) {
                    writer.write(html);
                } catch (IOException e) {
                    System.err.println("Error al escribir el archivo " + nombreArchivo);
                    e.printStackTrace();
                }
            }
        }
    }

    private static void agregarCarreras() {
        File directorio = new File(RUTA);
        File[] archivos = directorio.listFiles();
        for (File archivo : archivos) {
            String rutaArchivo = archivo.getPath();
            agregarCarrera(rutaArchivo);
        }
    }

    private static void agregarCarrera(String ruta) {
        MateriaHorarioService service = new MateriaHorarioService();
        Carrera carrera = obtenerCarrera(ruta);
        List<MateriaHorario> materias = service.cargarMateriasDesdeHTML(ruta, carrera);
        agregarCache(carrera, materias);
    }

    private static void agregarCache(Carrera carrera, List<MateriaHorario> materias) {
        List<MateriaHorario> listMateriasInmodificable = Collections.unmodifiableList(materias);
        LIST_CARRERAS.put(carrera, listMateriasInmodificable);

        Map<Materia, List<MateriaHorario>> mapaMaterias = new HashMap<>();
        for (MateriaHorario materiaHorario : materias) {
            List<MateriaHorario> horarios = mapaMaterias.computeIfAbsent(materiaHorario.getMateria(), k -> new ArrayList<>());
            horarios.add(materiaHorario);
        }
        mapaMaterias = Collections.unmodifiableMap(mapaMaterias);
        MAP_CARRERAS.put(carrera, mapaMaterias);
    }

    private static Carrera obtenerCarrera(String ruta) {
        File archivo = new File(ruta);
        String nombre = archivo.getName().replaceAll("(?i)\\.html" ,"").trim();
        return Carrera.getCarrera(nombre);
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
