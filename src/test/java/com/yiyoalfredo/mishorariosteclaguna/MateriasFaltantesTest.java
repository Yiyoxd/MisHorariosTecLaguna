package com.yiyoalfredo.mishorariosteclaguna;

import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;
import com.yiyoalfredo.mishorariosteclaguna.service.AlumnoService;
import com.yiyoalfredo.mishorariosteclaguna.service.KardexParser;
import com.yiyoalfredo.mishorariosteclaguna.service.MateriaService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

public class MateriasFaltantesTest {
    private static final int TOTAL_CREDITOS = 260;
    private static final int PORCENTAJE_60 = (int)(0.6 * TOTAL_CREDITOS);
    private static final int PORCENTAJE_70 = (int)(0.7 * TOTAL_CREDITOS);

    public static void main(String[] args) throws IOException {
        String ruta = "src/main/resources/SISTEMAS.JSON";
        MateriaService ms = new MateriaService();
        ms.cargarMateriasDesdeArchivo(ruta);
        List<Materia> materiasSistemas = ms.obtenerMaterias();

        KardexParser kp = new KardexParser();
        Alumno alu = kp.parseKardex("C:\\Users\\yiyoa\\Downloads\\Yiyo\\frmKardex.aspx.html");
        Map<String, Materia> faltantes = obtenerMateriasFaltantes(alu.getMateriasCursadas(), materiasSistemas);
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

    private static Map<String, Materia> obtenerMateriasFaltantes(List<String> cursadas, List<Materia> total) {
        Map<String, Materia> faltantes = new HashMap<String, Materia>();
        for (Materia m : total) {
            faltantes.put(m.getClave(), m);
        }

        int creditosAlumno = 0;
        for (String m : cursadas) {
            creditosAlumno += faltantes.get(m).getCreditos();
            faltantes.remove(m);
        }

        if (creditosAlumno >= PORCENTAJE_60) {
            faltantes.remove("P60");
        }

        if (creditosAlumno >= PORCENTAJE_70) {
            faltantes.remove("P70");
        }

        return faltantes;
    }
}
