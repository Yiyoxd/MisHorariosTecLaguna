package com.yiyoalfredo.mishorariosteclaguna;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;
import com.yiyoalfredo.mishorariosteclaguna.service.MateriaService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MateriaServiceTest {

    @Test
    public void testCargarMateriasDesdeJson() throws Exception {
        // JSON de prueba
        String json = """
        [
            {
                "nombre": "FUNDAMENTOS DE BASES DE DATOS",
                "clave": "D13",
                "prerequisitos": ["B12"],
                "creditos": 5
            },
            {
                "nombre": "INVESTIGACIÓN DE OPERACIONES",
                "clave": "E13",
                "prerequisitos": ["E12", "F12"],
                "creditos": 4
            },
            {
                "nombre": "FÍSICA GENERAL",
                "clave": "F13",
                "prerequisitos": [],
                "creditos": 5
            }
        ]
        """;

        // Inicializa el servicio
        MateriaService materiaService = new MateriaService();

        // Carga las materias desde el JSON
        materiaService.cargarMateriasDesdeJson(json);

        // Verifica los resultados
        List<Materia> materiasCargadas = materiaService.obtenerMaterias();
        assertEquals(3, materiasCargadas.size(), "El número de materias cargadas no es correcto");

        // Verifica los datos de la primera materia
        Materia primeraMateria = materiasCargadas.get(0);
        assertEquals("FUNDAMENTOS DE BASES DE DATOS", primeraMateria.getNombre());
        assertEquals("D13", primeraMateria.getClave());
        assertEquals(1, primeraMateria.getPrerequisitos().size());
        assertEquals(5, primeraMateria.getCreditos());
    }

    @Test
    public void testCargarMateriasDesdeArchivoJson() {
        // Ruta del archivo JSON
        String rutaArchivo = "src/test/resources/materias.json";

        try {
            // Lee el contenido del archivo JSON
            String jsonContenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));

            // Inicializa el servicio y carga los datos
            MateriaService materiaService = new MateriaService();
            materiaService.cargarMateriasDesdeJson(jsonContenido);

            // Obtiene las materias cargadas
            materiaService.obtenerMaterias().forEach(materia -> {
                System.out.println(materia);
                System.out.println("-----------------------------------");
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
