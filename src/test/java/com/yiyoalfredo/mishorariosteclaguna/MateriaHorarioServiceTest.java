package com.yiyoalfredo.mishorariosteclaguna;

import com.yiyoalfredo.mishorariosteclaguna.model.Carrera;
import com.yiyoalfredo.mishorariosteclaguna.model.MateriaHorario;
import com.yiyoalfredo.mishorariosteclaguna.service.MateriaHorarioService;
import com.yiyoalfredo.mishorariosteclaguna.service.MateriaCarreraService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class MateriaHorarioServiceTest {

    private static final String JSON_PATH = "C:/Users/yiyoa/Documents/mishorariosteclaguna/src/test/resources/materias.JSON";
    private static final String HTML_PATH = "C:/Users/yiyoa/Documents/mishorariosteclaguna/src/test/resources/Materias Cursando.html";

    @Test
    public void cargarMateriasDesdeArchivo() throws IOException {
        MateriaCarreraService materiaCarreraService = new MateriaCarreraService();
        materiaCarreraService.cargarMateriasDesdeArchivo(JSON_PATH);
        Assertions.assertFalse(materiaCarreraService.obtenerMaterias().isEmpty());
    }

    @Test
    public void cargarHorariosDesdeHTML() throws IOException {
        MateriaHorarioService materiaHorarioService = new MateriaHorarioService();
        List<MateriaHorario> materiasClase = materiaHorarioService.cargarMateriasDesdeHTML(HTML_PATH, Carrera.INGENIERIA_EN_SISTEMAS_COMPUTACIONALES);
        Assertions.assertFalse(materiasClase.isEmpty());
    }

    @Test
    public void validarAsociacionMateriasYGrupos() throws IOException {
        MateriaCarreraService materiaCarreraService = new MateriaCarreraService();
        MateriaHorarioService materiaHorarioService = new MateriaHorarioService();
        materiaCarreraService.cargarMateriasDesdeArchivo(JSON_PATH);
        List<MateriaHorario> materiasClase = materiaHorarioService.cargarMateriasDesdeHTML(HTML_PATH, Carrera.INGENIERIA_EN_SISTEMAS_COMPUTACIONALES);

        for (MateriaHorario materiaHorario : materiasClase) {
            String grupo = materiaHorario.getMateria().getClave();
            String claveEsperada = grupo.substring(0, 3);

            boolean claveEncontrada = materiaCarreraService.obtenerMaterias().stream()
                    .anyMatch(materia -> materia.getClave().equals(claveEsperada));

            Assertions.assertTrue(claveEncontrada);
        }
    }

    @Test
    public void imprimirMateriasConHorarios() throws IOException {
        MateriaCarreraService materiaCarreraService = new MateriaCarreraService();
        MateriaHorarioService materiaHorarioService = new MateriaHorarioService();
        materiaCarreraService.cargarMateriasDesdeArchivo(JSON_PATH);
        List<MateriaHorario> materiasClase = materiaHorarioService.cargarMateriasDesdeHTML(HTML_PATH, Carrera.INGENIERIA_EN_SISTEMAS_COMPUTACIONALES);

        System.out.println("sjkldncvskjdnfjksdnfjnswdjfnslmdflksdmfñkmsdñlk");
        materiasClase.forEach(System.out::println);
    }
}
