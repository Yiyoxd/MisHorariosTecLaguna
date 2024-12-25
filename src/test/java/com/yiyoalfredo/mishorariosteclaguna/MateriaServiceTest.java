package com.yiyoalfredo.mishorariosteclaguna;

import com.yiyoalfredo.mishorariosteclaguna.service.MateriaCarreraService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MateriaServiceTest {

    @Test
    public void testCargarMateriasDesdeArchivoJson() {
        // Ruta del archivo JSON
        String rutaArchivo = "src/test/resources/materias.json";
        // Inicializa el servicio y carga los datos
        MateriaCarreraService materiaCarreraService = new MateriaCarreraService();
        materiaCarreraService.cargarMateriasDesdeArchivo(rutaArchivo);

        // Obtiene las materias cargadas
        materiaCarreraService.obtenerMaterias().forEach(materia -> {
            System.out.println(materia);
            System.out.println("-----------------------------------");
        });
    }
}
