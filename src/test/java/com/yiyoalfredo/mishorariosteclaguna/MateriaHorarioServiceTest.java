package com.yiyoalfredo.mishorariosteclaguna;

import com.yiyoalfredo.mishorariosteclaguna.model.Carrera;
import com.yiyoalfredo.mishorariosteclaguna.model.MateriaHorario;
import com.yiyoalfredo.mishorariosteclaguna.service.MateriaCarreraCache;
import com.yiyoalfredo.mishorariosteclaguna.service.MateriaHorarioCache;
import com.yiyoalfredo.mishorariosteclaguna.service.MateriaHorarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class MateriaHorarioServiceTest {
    private static final String HTML_PATH = "src/main/resources/horarios_carrera/INGENIERIA EN SISTEMAS COMPUTACIONALES.html";

    @Test
    public void cargarHorariosDesdeHTML() throws IOException {
        MateriaHorarioService materiaHorarioService = new MateriaHorarioService();
        List<MateriaHorario> materiasClase = materiaHorarioService.cargarMateriasDesdeHTML(HTML_PATH, Carrera.INGENIERIA_EN_SISTEMAS_COMPUTACIONALES);
        Assertions.assertFalse(materiasClase.isEmpty());
    }

    @Test
    public void validarAsociacionMateriasYGrupos() throws IOException {
        MateriaHorarioService materiaHorarioService = new MateriaHorarioService();
        List<MateriaHorario> materiasClase = materiaHorarioService.cargarMateriasDesdeHTML(HTML_PATH, Carrera.INGENIERIA_EN_SISTEMAS_COMPUTACIONALES);


        for (MateriaHorario materiaHorario : materiasClase) {
            String grupo = materiaHorario.getMateria().getClave();
            String claveEsperada = grupo.substring(0, 3);

            boolean claveEncontrada = MateriaCarreraCache.getListMaterias(
                    Carrera.INGENIERIA_EN_SISTEMAS_COMPUTACIONALES)
                    .stream()
                    .anyMatch(materia -> materia.getClave().equals(claveEsperada));

            Assertions.assertTrue(claveEncontrada);
        }
    }

    @Test
    public void imprimirMateriasConHorarios() throws IOException {
        List<MateriaHorario> materiasClase = MateriaHorarioCache.getListMateriasCopy(Carrera.INGENIERIA_EN_SISTEMAS_COMPUTACIONALES);
        materiasClase.sort((a, b) -> a.getMateria().getNombre().compareTo(b.getMateria().getNombre()));
        materiasClase.forEach(System.out::println);
    }
}
