package com.yiyoalfredo.mishorariosteclaguna;

import com.yiyoalfredo.mishorariosteclaguna.model.MateriaClase;
import com.yiyoalfredo.mishorariosteclaguna.service.MateriaClaseService;
import com.yiyoalfredo.mishorariosteclaguna.service.MateriaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MateriaClaseServiceTest {

    private static final String JSON_PATH = "C:/Users/yiyoa/Documents/mishorariosteclaguna/src/test/resources/materias.JSON";
    private static final String HTML_PATH = "C:/Users/yiyoa/Documents/mishorariosteclaguna/src/test/resources/Materias Cursando.html";

    @Test
    public void cargarMateriasDesdeArchivo() throws IOException {
        MateriaService materiaService = new MateriaService();
        materiaService.cargarMateriasDesdeArchivo(JSON_PATH);
        Assertions.assertFalse(materiaService.obtenerMaterias().isEmpty());
    }

    @Test
    public void cargarHorariosDesdeHTML() throws IOException {
        MateriaClaseService materiaClaseService = new MateriaClaseService();
        List<MateriaClase> materiasClase = materiaClaseService.cargarMateriasDesdeHTML(HTML_PATH);
        Assertions.assertFalse(materiasClase.isEmpty());
    }

    @Test
    public void validarAsociacionMateriasYGrupos() throws IOException {
        MateriaService materiaService = new MateriaService();
        MateriaClaseService materiaClaseService = new MateriaClaseService();
        materiaService.cargarMateriasDesdeArchivo(JSON_PATH);
        List<MateriaClase> materiasClase = materiaClaseService.cargarMateriasDesdeHTML(HTML_PATH);

        for (MateriaClase materiaClase : materiasClase) {
            String grupo = materiaClase.getMateria().getClave();
            String claveEsperada = grupo.substring(0, 3);

            boolean claveEncontrada = materiaService.obtenerMaterias().stream()
                    .anyMatch(materia -> materia.getClave().equals(claveEsperada));

            Assertions.assertTrue(claveEncontrada);
        }
    }

    @Test
    public void imprimirMateriasConHorarios() throws IOException {
        MateriaService materiaService = new MateriaService();
        MateriaClaseService materiaClaseService = new MateriaClaseService();
        materiaService.cargarMateriasDesdeArchivo(JSON_PATH);
        List<MateriaClase> materiasClase = materiaClaseService.cargarMateriasDesdeHTML(HTML_PATH);

        System.out.println("sjkldncvskjdnfjksdnfjnswdjfnslmdflksdmfñkmsdñlk");
        materiasClase.forEach(System.out::println);
    }
}
