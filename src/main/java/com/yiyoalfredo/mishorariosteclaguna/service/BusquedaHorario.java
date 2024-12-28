package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;
import com.yiyoalfredo.mishorariosteclaguna.model.Horario;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;
import com.yiyoalfredo.mishorariosteclaguna.model.MateriaHorario;

import java.time.LocalTime;
import java.util.*;

public class BusquedaHorario {
    private final Alumno alumno;
    private final Set<Materia> materiasFaltantes;
    private List<MateriaHorario> materiasABuscar;
    private final Map<Materia, List<MateriaHorario>> horariosDisponibles;

    public BusquedaHorario(Alumno alumno) {
        this.alumno = alumno;
        this.materiasFaltantes = new HashSet(AlumnoService.getListMateriasFaltantes(alumno));
        this.materiasABuscar = materiasABuscar(materiasFaltantes);
        this.materiasABuscar.sort(MateriaHorario::compareTo);
        this.horariosDisponibles = MateriaHorarioCache.getMapMaterias(alumno.getCarrera());
    }

    public List<List<MateriaHorario>> posiblesHorarios(int creditosMinimos, int creditosMaximos, int horaEntrada, int horaSalida) {
        List<MateriaHorario> copyMaterias = materiasABuscar;
        materiasABuscar = filtroHorarios(materiasABuscar, horaEntrada, horaSalida);
        List<List<MateriaHorario>> posiblesHorarios = posiblesHorarios(creditosMinimos, creditosMaximos);
        materiasABuscar = copyMaterias;
        return posiblesHorarios;
    }

    public List<List<MateriaHorario>> posiblesHorarios(int creditosMinimos, int creditosMaximos) {
        List<List<MateriaHorario>> combinaciones = new ArrayList<>();
        generarCombinaciones(combinaciones, new HashSet<>(), new ArrayList<>(), 0, creditosMaximos - creditosMinimos, creditosMaximos);
        return combinaciones;
    }

    private void generarCombinaciones(
            List<List<MateriaHorario>> combinaciones,
            Set<Materia> actualSet,
            List<MateriaHorario> actualList,
            int inicio,
            int minimo,
            int actuales
    ) {
        if (actuales < 0) {
            return;
        }
        if (actuales <= minimo) {
            combinaciones.add(new ArrayList<>(actualList));
        }

        for (int i = inicio; i < materiasABuscar.size(); i++) {
            MateriaHorario horarioMateria = materiasABuscar.get(i);
            Materia materia = horarioMateria.getMateria();
            if (actualSet.contains(materia) || chocan(actualList, horarioMateria)) {
                continue;
            }
            actualList.add(horarioMateria);
            actualSet.add(materia);
            generarCombinaciones(combinaciones, actualSet, actualList, inicio + 1, minimo, actuales - materia.getCreditos());
            actualSet.remove(materia);
            actualList.removeLast();
        }
    }

    private List<MateriaHorario> materiasABuscar(Set<Materia> materiasFaltantes) {
        List<MateriaHorario> materiasABuscar = new ArrayList<>(materiasFaltantes.size());
        Map<Materia, List<MateriaHorario>> horariosDisponibles = MateriaHorarioCache.getMapMaterias(alumno.getCarrera());
        Map<String, Materia> materiasCarrera = MateriaCarreraCache.getMapMaterias(alumno.getCarrera());

        for (Materia materia : materiasFaltantes) {
            if (!MateriaEspecialService.esMateriaEspecial(materia) && cumplePrerequisitos(materiasFaltantes, materia.getPrerequisitos(), materiasCarrera)) {
                List<MateriaHorario> materiaHorario = horariosDisponibles.get(materia);
                materiasABuscar.addAll(materiaHorario);
            }
        }

        return materiasABuscar;
    }

    boolean cumplePrerequisitos(Set<Materia> materiasFaltantes, List<String> prerequisitos, Map<String, Materia> materiasCarrera) {
        for (String prerequisito : prerequisitos) {
            Materia materia = materiasCarrera.get(prerequisito);
            if (materiasFaltantes.contains(materia)) {
                return false;
            }
        }

        return true;
    }

    private boolean chocan(List<MateriaHorario> actuales, MateriaHorario materiaHorario) {
        for (MateriaHorario materiaGuardada : actuales) {
            if (chocan(materiaGuardada, materiaHorario )) {
                return true;
            }
        }

        return false;
    }

    private boolean chocan(MateriaHorario materia1, MateriaHorario materia2) {
        Horario[] horario1 = materia1.getHorario();
        Horario[] horario2 = materia2.getHorario();
        return HorarioService.chocan(horario1, horario2);
    }

    private List<MateriaHorario> filtroHorarios(List<MateriaHorario> horarios, int min, int max) {
        LocalTime minTime = LocalTime.of(min, 0);
        LocalTime maxTime = LocalTime.of(max, 0);

        return horarios.stream().filter(e -> estaEntre(minTime, maxTime, e.getHorario())).toList();
    }

    private boolean estaEntre(LocalTime min, LocalTime max, Horario[] horario) {
        for (Horario hr : horario) {
            if (hr == null) continue;
            LocalTime inicio = hr.getHoraInicio();
            LocalTime fin = hr.getHoraFin();
            if (inicio.isBefore(min) || fin.isAfter(max)) {
                return false;
            }
        }

        return true;
    }
}
