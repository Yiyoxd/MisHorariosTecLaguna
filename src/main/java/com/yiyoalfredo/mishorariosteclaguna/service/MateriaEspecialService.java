package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.model.Carrera;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MateriaEspecialService {
    private static final Set<Materia> materiasEspeciales;

    static {
        materiasEspeciales = new HashSet<Materia>();
        cargarMateriasEspeciales();
    }

    private static void cargarMateriasEspeciales() {
        String ruta = "src/main/resources/materias_especiales.txt";
        File file = new File(ruta);
        FileReader fr;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader br = new BufferedReader(fr);
        br.lines().forEach(line -> {
            Materia materia = MateriaCarreraCache.getMateria(line, Carrera.INGENIERIA_EN_SISTEMAS_COMPUTACIONALES);
            materiasEspeciales.add(materia);
        });
    }

    public static boolean esMateriaEspecial(String claveMateria) {
        Materia materia = MateriaCarreraCache.getMateria(claveMateria, Carrera.INGENIERIA_EN_SISTEMAS_COMPUTACIONALES);
        return materiasEspeciales.contains(materia);
    }

    public static boolean esMateriaEspecial(Materia materia) {
        return materiasEspeciales.contains(materia);
    }

    public static void main(String[] args) {
        System.out.println("xd");
    }
}
