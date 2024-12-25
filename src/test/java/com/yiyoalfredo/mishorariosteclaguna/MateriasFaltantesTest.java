package com.yiyoalfredo.mishorariosteclaguna;

import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;
import com.yiyoalfredo.mishorariosteclaguna.service.AlumnoService;
import com.yiyoalfredo.mishorariosteclaguna.service.KardexParser;
import com.yiyoalfredo.mishorariosteclaguna.service.MateriaCarreraCache;

import java.io.IOException;
import java.util.*;

public class MateriasFaltantesTest {
    private static final int TOTAL_CREDITOS = 260;
    private static final int PORCENTAJE_60 = (int)(0.6 * TOTAL_CREDITOS);
    private static final int PORCENTAJE_70 = (int)(0.7 * TOTAL_CREDITOS);

    public static void main(String[] args) throws IOException {
        Alumno alu = new KardexParser().parseKardex("C:\\Users\\yiyoa\\Downloads\\Yiyo\\frmKardex.aspx.html");
        Map<String, Materia> faltantes = AlumnoService.getMapMateriasFaltantes(alu);

        System.out.println("Materias que se pueden cargar :");
        for (var materia : faltantes.entrySet()) {
            boolean esPosible = true;
            for (String prerequisito : materia.getValue().getPrerequisitos()) {
                if (faltantes.containsKey(prerequisito)) {
                    esPosible = false;
                    break;
                }
            }
            if (esPosible) {
                System.out.println(materia.getValue());
                System.out.println("-------------------------------- \n");
            }
        }
    }
}
