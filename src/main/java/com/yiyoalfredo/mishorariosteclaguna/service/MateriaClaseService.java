package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.model.Horario;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;
import com.yiyoalfredo.mishorariosteclaguna.model.MateriaClase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class MateriaClaseService {

    private static final Map<String, List<MateriaClase>> cache = new HashMap<>();

    public MateriaClaseService() {
    }

    public List<MateriaClase> cargarMateriasDesdeHTML(String htmlFilePath) throws IOException {
        if (cache.containsKey(htmlFilePath)) {
            return new ArrayList<>(cache.get(htmlFilePath));
        }

        Document doc = Jsoup.parse(new File(htmlFilePath), "UTF-8");
        List<MateriaClase> materiasClase = new ArrayList<>();
        Elements rows = doc.select("table tbody tr");

        for (Element row : rows) {
            Elements cells = row.select("td");
            if (cells.size() < 8) continue; // Asegurarse de que hay suficientes columnas

            String nombreMateria = cells.get(0).text().trim();
            String grupo = cells.get(1).text().trim();
            String maestro = cells.get(7).text().trim();

            Materia materia = new Materia(nombreMateria, grupo, new ArrayList<>(), 0);
            MateriaClase materiaClase = new MateriaClase(materia);
            materiaClase.setMaestro(maestro);

            Map<DayOfWeek, String> horarios = extractHorarios(cells);

            for (Map.Entry<DayOfWeek, String> entry : horarios.entrySet()) {
                Horario horario = parseHorario(entry);
                if (horario != null) {
                    materiaClase.agregarHorario(horario);
                }
            }

            materiasClase.add(materiaClase);
        }

        cache.put(htmlFilePath, materiasClase); // Guardar en caché
        return materiasClase;
    }

    private Map<DayOfWeek, String> extractHorarios(Elements cells) {
        Map<DayOfWeek, String> horarios = new HashMap<>();
        horarios.put(DayOfWeek.MONDAY, cells.get(2).text().trim());
        horarios.put(DayOfWeek.TUESDAY, cells.get(3).text().trim());
        horarios.put(DayOfWeek.WEDNESDAY, cells.get(4).text().trim());
        horarios.put(DayOfWeek.THURSDAY, cells.get(5).text().trim());
        horarios.put(DayOfWeek.FRIDAY, cells.get(6).text().trim());
        return horarios;
    }

    private Horario parseHorario(Map.Entry<DayOfWeek, String> entry) {
        String horarioString = entry.getValue();
        if (horarioString.isEmpty() || !horarioString.contains("/")) {
            return null;
        }

        String[] partes = horarioString.split("/");
        String[] horas = partes[0].split("-");

        try {
            LocalTime horaInicio = LocalTime.parse(horas[0].trim() + ":00");
            LocalTime horaFin = LocalTime.parse(horas[1].trim() + ":00");
            String aula = partes[1].trim();
            return new Horario(entry.getKey(), horaInicio, horaFin, aula);
        } catch (Exception e) {
            return null;
        }
    }

    public static void limpiarCache(String htmlFilePath) {
        cache.remove(htmlFilePath);
    }
}
