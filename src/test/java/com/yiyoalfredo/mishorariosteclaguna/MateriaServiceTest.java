package com.yiyoalfredo.mishorariosteclaguna;

import com.yiyoalfredo.mishorariosteclaguna.model.Carrera;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;
import com.yiyoalfredo.mishorariosteclaguna.service.MateriaCarreraCache;
import com.yiyoalfredo.mishorariosteclaguna.service.MateriaHorarioCache;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MateriaServiceTest {

    @Test
    public void cache() {
        List<Materia> materias1 = MateriaCarreraCache.getListMaterias(Carrera.INGENIERIA_EN_SISTEMAS_COMPUTACIONALES);
        List<Materia> materias2 = MateriaCarreraCache.getListMaterias(Carrera.INGENIERIA_EN_SISTEMAS_COMPUTACIONALES);

        materias1.forEach(materias -> {
            System.out.println(materias);
            System.out.println("-----------------------------------");
        });

        assertEquals(true, materias1 == materias2);
    }
}
