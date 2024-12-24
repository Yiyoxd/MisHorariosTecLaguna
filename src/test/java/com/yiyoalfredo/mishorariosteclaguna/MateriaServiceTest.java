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
    public void testCargarMateriasDesdeArchivoJson() {
        // Ruta del archivo JSON
        String rutaArchivo = "src/test/resources/materias.json";
        // Inicializa el servicio y carga los datos
        MateriaService materiaService = new MateriaService();
        materiaService.cargarMateriasDesdeArchivo(rutaArchivo);

        // Obtiene las materias cargadas
        materiaService.obtenerMaterias().forEach(materia -> {
            System.out.println(materia);
            System.out.println("-----------------------------------");
        });
    }
}
