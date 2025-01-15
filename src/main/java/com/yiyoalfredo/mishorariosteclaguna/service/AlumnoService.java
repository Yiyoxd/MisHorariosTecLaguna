package com.yiyoalfredo.mishorariosteclaguna.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiyoalfredo.mishorariosteclaguna.dto.AlumnoDto;
import com.yiyoalfredo.mishorariosteclaguna.dto.CredencialesDTO;
import com.yiyoalfredo.mishorariosteclaguna.dto.MateriaDto;
import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AlumnoService {
    private static final int PORCENTAJE_60 = 156;
    private static final int PORCENTAJE_70 = 182;
    private final ObjectMapper mapper;

    public AlumnoService() {
        this.mapper = new ObjectMapper();
    }

    public Alumno cargarAlumnoDesdeArchivo(String rutaArchivo) {
        try {
            return mapper.readValue(new File(rutaArchivo), Alumno.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar el archivo JSON: " + rutaArchivo, e);
        }
    }

    public Alumno cargarAlumnoDesdeCredenciales(String matricula, String pass) {
        Alumno alumno = new KardexParser().parseKardexFromWeb(matricula, pass);
        return alumno;
    }

    public AlumnoDto cargarAlumnoDtoDesdeCredenciales(CredencialesDTO credenciales) {
        Alumno alumno = cargarAlumnoDesdeCredenciales(credenciales);
        return new AlumnoDto(alumno);
    }

    public Alumno cargarAlumnoDesdeCredenciales(CredencialesDTO credenciales) {
        String pass = credenciales.pass();
        String matricula = credenciales.matricula();
        return cargarAlumnoDesdeCredenciales(matricula, pass);
    }

    public static int getCreditos(Alumno alu) {
        int creditos = 0;
        for (Materia m : alu.getMateriasCursadas()) {
            creditos += m.getCreditos();
        }

        return creditos;
    }

    public static Map<String, Materia> getMapMateriasFaltantes(Alumno alu) {
        List<Materia> cursadas = alu.getMateriasCursadas();
        Map<String, Materia> todas = MateriaCarreraCache.getMapMateriasCopy(alu.getCarrera());

        for (Materia materia : cursadas) {
            todas.remove(materia.getClave());
        }

        return todas;
    }

    public static List<Materia> getListMateriasFaltantes(Alumno alu) {
        Map<String, Materia> todas = getMapMateriasFaltantes(alu);

        return new ArrayList<>(todas.values());
    }

    public List<MateriaDto> getMateriasFaltantesDto(Alumno alu) {
        return alu.getMateriasFaltantes()
                .stream()
                .map(MateriaDto::new)
                .toList();
    }

    public static void addMateriasEspeciales(Alumno alu) {
        List<Materia> materias = alu.getMateriasCursadas();
        materias.add(MateriaCarreraCache.getMateria("T11", alu.getCarrera()));

        int creditos = alu.getCreditosTotales();
        if (creditos >= PORCENTAJE_60) {
            Materia materia = MateriaCarreraCache.getMateria("P60", alu.getCarrera());
            materias.add(materia);
        }
        if (creditos >= PORCENTAJE_70) {
            Materia materia = MateriaCarreraCache.getMateria("P70", alu.getCarrera());
            materias.add(materia);
        }
    }
}
