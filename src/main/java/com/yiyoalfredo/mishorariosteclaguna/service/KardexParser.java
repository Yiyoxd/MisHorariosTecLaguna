package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;
import com.yiyoalfredo.mishorariosteclaguna.model.Carrera;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KardexParser {
    public Alumno parseKardex(File file) throws IOException {
        Document document = Jsoup.parse(file, "UTF-8");

        String nombre = extractNombre(document);
        Carrera carrera = extractCarrera(document);
        List<Materia> materiasCursadas = extractMaterias(document, carrera);

        return new Alumno(nombre, "", carrera, materiasCursadas);
    }

    public Alumno parseKardex(String rutaArchivo) throws IOException {
        File file = new File(rutaArchivo);
        return parseKardex(file);
    }

    private String extractNombre(Document document) {
        Element nombreElement = document.getElementById("MainContent_lblNombre");
        return nombreElement != null ? nombreElement.text().trim() : "Nombre desconocido";
    }

    private Carrera extractCarrera(Document document) {
        Element carreraElement = document.getElementById("MainContent_lblCarrera");
        if (carreraElement != null) {
            String carreraTexto = carreraElement.text().trim();
            return Carrera.getCarrera(carreraTexto);
        }
        return null;
    }

    private List<Materia> extractMaterias(Document document, Carrera carrera) {
        Map<String, Materia> materiasCarrera = MateriaHorarioCache.getMapMaterias(carrera);
        List<Materia> materias = new ArrayList<>();
        Element table = document.getElementById("MainContent_GridView1");

        if (table != null) {
            Elements rows = table.select("tr");
            for (Element row : rows) {
                Elements cols = row.select("td");
                if (cols.size() >= 3) {
                    String clave = cols.get(0).text().trim();
                    Materia materia = materiasCarrera.get(clave);
                    materias.add(materia);
                }
            }
        }

        return materias;
    }
}
