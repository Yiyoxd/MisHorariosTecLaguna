package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KardexParser {

    /**
     * Parsea un archivo HTML para crear un objeto Alumno con su información y materias cursadas.
     *
     * @param file Archivo HTML del kardex.
     * @return Objeto Alumno con los datos extraídos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public Alumno parseKardex(File file) throws IOException {
        Document document = Jsoup.parse(file, "UTF-8");

        // Extraer el nombre del alumno
        String nombre = extractNombre(document);

        // Extraer las materias cursadas
        List<String> materiasCursadas = extractMaterias(document);

        // Retornar el objeto Alumno
        return new Alumno(nombre, "Desconocido", materiasCursadas);
    }

    /**
     * Parsea un archivo HTML desde una ruta para crear un objeto Alumno.
     *
     * @param rutaArchivo Ruta al archivo HTML.
     * @return Objeto Alumno con los datos extraídos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public Alumno parseKardex(String rutaArchivo) throws IOException {
        // Convierte la ruta en un objeto File y llama al método existente
        File file = new File(rutaArchivo);
        return parseKardex(file);
    }

    /**
     * Extrae el nombre del alumno desde el documento HTML.
     *
     * @param document Documento HTML.
     * @return Nombre del alumno o "Desconocido" si no se encuentra.
     */
    private String extractNombre(Document document) {
        Element nombreElement = document.getElementById("MainContent_lblNombre");
        return nombreElement != null ? nombreElement.text() : "Desconocido";
    }

    /**
     * Extrae las claves de las materias cursadas desde el documento HTML.
     *
     * @param document Documento HTML.
     * @return Lista de claves de materias cursadas.
     */
    private List<String> extractMaterias(Document document) {
        List<String> materias = new ArrayList<>();
        Element table = document.getElementById("MainContent_GridView1");

        if (table != null) {
            Elements rows = table.select("tr");
            for (Element row : rows) {
                Elements cols = row.select("td");
                if (cols.size() >= 1) {
                    materias.add(cols.get(0).text()); // Agregar clave de la materia
                }
            }
        }

        return materias;
    }
}
