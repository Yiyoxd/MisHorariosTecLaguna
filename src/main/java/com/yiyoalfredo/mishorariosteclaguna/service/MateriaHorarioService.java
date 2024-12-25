package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.model.Carrera;
import com.yiyoalfredo.mishorariosteclaguna.model.Horario;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;
import com.yiyoalfredo.mishorariosteclaguna.model.MateriaHorario;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class MateriaHorarioService {

    public List<MateriaHorario> cargarMateriasDesdeHTML(String htmlFilePath, Carrera carrera) throws IOException {
        Document doc = Jsoup.parse(new File(htmlFilePath), "UTF-8");
        List<MateriaHorario> materiasClase = new ArrayList<>();
        Map<String, Materia> materias = MateriaCarreraCache.getMapMaterias(carrera);

        Elements rows = doc.select("table tbody tr");
        for (int i = 1; i < rows.size(); i++) {
            Elements cells = rows.get(i).select("td");
            String grupo = cells.get(1).text().trim();
            String clave = grupo.substring(0, 3);

            Materia materia = materias.get(clave);
            Horario[] horarios = extractHorarios(cells);
            String maestro = cells.get(7).text().trim();

            MateriaHorario materiaHorario = new MateriaHorario(materia, horarios, maestro, grupo);
            materiasClase.add(materiaHorario);
        }

        return materiasClase;
    }

    private Horario[] extractHorarios(Elements cells) {
        Horario[] horarios = new Horario[5];
        for (int i = 0; i < 5; i++) {
            String text = cells.get(i + 2).text().trim();
            Horario horario = parseHorario(Horario.WEEK[i], text);
            horarios[i] = horario;
        }

        return horarios;
    }

    private Horario parseHorario(DayOfWeek dia, String texto) {
        if (texto.isEmpty() || !texto.contains("/")) {
            return null;
        }

        String[] partes = texto.split("/");
        String[] horas = partes[0].split("-");

        try {
            LocalTime horaInicio = LocalTime.parse(horas[0].trim() + ":00");
            LocalTime horaFin = LocalTime.parse(horas[1].trim() + ":00");
            String aula = partes[1].trim();
            return new Horario(dia, horaInicio, horaFin, aula);
        } catch (Exception e) {
            return null;
        }
    }
}
