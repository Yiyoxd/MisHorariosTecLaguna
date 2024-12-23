package com.yiyoalfredo.mishorariosteclaguna;

import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;
import com.yiyoalfredo.mishorariosteclaguna.service.KardexParser;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            // Ruta al archivo HTML del kardex
            File file = new File("C:/Users/yiyoa/Downloads/frmKardex.aspx.html");

            KardexParser parser = new KardexParser();
            Alumno alumno = parser.parseKardex(file);

            System.out.println("Nombre del Alumno: " + alumno.getNombre());
            System.out.println("Número de Control: " + alumno.getNumControl());
            System.out.println("Materias Cursadas: " + alumno.getMateriasCursadas());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
