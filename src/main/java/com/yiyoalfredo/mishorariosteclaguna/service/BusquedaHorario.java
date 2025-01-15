package com.yiyoalfredo.mishorariosteclaguna.service;
import com.yiyoalfredo.mishorariosteclaguna.model.*;

import java.time.LocalTime;
import java.util.*;

public class BusquedaHorario {
    private final Alumno alumno;
    private List<MateriaHorario> materiasABuscar;

    public BusquedaHorario(Alumno alumno) {
        this.alumno = alumno;
        Set<Materia> materiasFaltantes = new HashSet<>(AlumnoService.getListMateriasFaltantes(alumno));
        this.materiasABuscar = materiasABuscar(materiasFaltantes);
        Collections.sort(this.materiasABuscar);
    }

    private void generarPrecombinaciones (
            List<List<MateriaHorario>> combinaciones,
            Set<Materia> actualSet,
            List<MateriaHorario> actualList,
            int index,
            int total
    ) {
        if (total == 0) {
            combinaciones.add(new ArrayList<>(actualList));
            return;
        }

        for (int i = index; i < materiasABuscar.size(); i++) {
            MateriaHorario horarioMateria = materiasABuscar.get(i);
            Materia materia = horarioMateria.getMateria();

            if (actualSet.contains(materia) || chocan(actualList, horarioMateria)) {
                continue;
            }

            actualList.add(horarioMateria);
            actualSet.add(materia);

            generarPrecombinaciones (
                    combinaciones,
                    actualSet,
                    actualList,
                    i + 1,
                    total - 1
            );

            actualSet.remove(materia);
            actualList.removeLast();
        }
    }

    private List<List<MateriaHorario>> posiblesHorariosConPrecarga(List<List<MateriaHorario>> precombinaciones, int creditosMinimos, int creditosMaximos) {
        List<List<MateriaHorario>> combinaciones = new ArrayList<>();
        for (List<MateriaHorario> precombinacion : precombinaciones) {
            List<Materia> listaActual =  precombinacion.stream().map(MateriaHorario::getMateria).toList();
            Set<Materia> actualSet = new HashSet<>(listaActual);
            int creditosActuales = listaActual.stream().mapToInt(Materia::getCreditos).sum();
            generarCombinaciones(combinaciones, actualSet, precombinacion, 0, creditosMinimos, creditosMaximos, creditosActuales);
        }

        return combinaciones;
    }

    private void imprimirHorarios(List<List<MateriaHorario>> horarios) {
        for (List<MateriaHorario> actualList : horarios) {
            StringBuilder sb = new StringBuilder();
            int sum = actualList.stream().mapToInt(e -> e.getMateria().getCreditos()).sum();
            actualList.forEach(e -> {
                /*
                sb.append(e.getMateria().getNombre()).append("\n");
                sb.append(e.getMaestro()).append("\n");
                sb.append(e.getHorario()[0].getHoraInicio()).append(" - ").append(e.getHorario()[0].getHoraFin()).append("\n");
                 */
                sb.append(e.toString());
                sb.append("-------------------------------------------------------\n");
            });
            sb.append(sum).append("\n\n\n\n\n");
            System.out.println(sb.toString());
        }
    }

    public List<List<MateriaHorario>> posiblesHorarios(
            int creditosMinimos, int creditosMaximos,
            int horaEntrada, int horaSalida,
            List<String> maestrosAEvitar,
            List<Materia> materiasATomar
    ) {
        List<MateriaHorario> copyMaterias = materiasABuscar;
        final Set<String> maestrosAEvitarSet = new HashSet<>(maestrosAEvitar);
        final Set<Materia> materiasATomarSet = new HashSet<>(materiasATomar);

        materiasABuscar = filtroHorarios(materiasATomarSet, maestrosAEvitarSet, horaEntrada, horaSalida);
        List<List<MateriaHorario>> precombinaciones = new ArrayList<>();
        generarPrecombinaciones(precombinaciones, new HashSet<>(), new ArrayList<>(), 0, materiasATomar.size());

        materiasABuscar = copyMaterias.stream().filter(
                e -> !maestrosAEvitarSet.contains(e.getMaestro())
                && !materiasATomarSet.contains(e.getMateria())
        ).toList();
        List<List<MateriaHorario>> posiblesHorarios = posiblesHorariosConPrecarga(precombinaciones, creditosMinimos, creditosMaximos);
        posiblesHorarios.forEach(Collections::sort);

        materiasABuscar = copyMaterias;
        return posiblesHorarios;
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
        generarCombinaciones(combinaciones, new HashSet<>(), new ArrayList<>(), 0, creditosMinimos, creditosMaximos, 0);
        return combinaciones;
    }

    private void generarCombinaciones(
            List<List<MateriaHorario>> combinaciones,
            Set<Materia> actualSet,
            List<MateriaHorario> actualList,
            int inicio,
            int creditosMinimos,
            int creditosMaximos,
            int creditosActuales
    ) {
        if (creditosActuales > creditosMaximos) {
            return;
        }

        if (creditosActuales >= creditosMinimos) {
            combinaciones.add(new ArrayList<>(actualList));
        }

        for (int i = inicio; i < materiasABuscar.size(); i++) {
            MateriaHorario horarioMateria = materiasABuscar.get(i);
            Materia materia = horarioMateria.getMateria();

            if (actualSet.contains(materia) || (!actualList.isEmpty() && chocan(actualList, horarioMateria))) {
                continue;
            }

            actualList.add(horarioMateria);
            actualSet.add(materia);

            generarCombinaciones (
                    combinaciones,
                    actualSet,
                    actualList,
                    i + 1,
                    creditosMinimos,
                    creditosMaximos,
                    creditosActuales + materia.getCreditos()
            );

            actualSet.remove(materia);
            actualList.removeLast();
        }
    }

    private List<MateriaHorario> materiasABuscar(Set<Materia> materiasFaltantes) {
        List<MateriaHorario> materiasABuscar = new ArrayList<>(materiasFaltantes.size());
        Map<Materia, List<MateriaHorario>> horariosDisponibles = MateriaHorarioCache.getMapMaterias(alumno.getCarrera());
        Map<String, Materia> materiasCarrera = MateriaCarreraCache.getMapMaterias(alumno.getCarrera());

        for (Materia materia : materiasFaltantes) {
            if (!MateriaEspecialService.esMateriaEspecial(materia)
                    && cumplePrerequisitos(materiasFaltantes, materia.getPrerequisitos(), materiasCarrera)
            ) {
                List<MateriaHorario> materiasHorario = horariosDisponibles.get(materia);
                materiasABuscar.addAll(materiasHorario);
            }
        }

        return materiasABuscar;
    }

    private static boolean cumplePrerequisitos(Set<Materia> materiasFaltantes, List<String> prerequisitos, Map<String, Materia> materiasCarrera) {
        for (String prerequisito : prerequisitos) {
            Materia materia = materiasCarrera.get(prerequisito);
            if (materiasFaltantes.contains(materia)) {
                return false;
            }
        }

        return true;
    }

    private static boolean chocan(List<MateriaHorario> actuales, MateriaHorario materiaHorario) {
        for (MateriaHorario materiaGuardada : actuales) {
            if (chocan(materiaGuardada, materiaHorario )) {
                return true;
            }
        }

        return false;
    }

    private static boolean chocan(MateriaHorario materia1, MateriaHorario materia2) {
        Horario[] horario1 = materia1.getHorario();
        Horario[] horario2 = materia2.getHorario();
        return HorarioService.chocan(horario1, horario2);
    }

    private static List<MateriaHorario> filtroHorarios(List<MateriaHorario> horarios, int min, int max) {
        LocalTime minTime = LocalTime.of(min, 0);
        LocalTime maxTime = LocalTime.of(max, 0);

        return horarios.stream().filter(e -> estaEntre(minTime, maxTime, e.getHorario())).toList();
    }

    private List<MateriaHorario> filtroHorarios(Set<Materia> obligatorias, Set<String> maestrosAEvitar, int minHora, int maxHora) {
        List<MateriaHorario> materiasFiltradas = materiasABuscar.stream().filter (
                e -> !maestrosAEvitar.contains(e.getMaestro())
                && obligatorias.contains(e.getMateria())
        ).toList();
        return filtroHorarios(materiasFiltradas, minHora, maxHora);
    }

    private static boolean estaEntre(LocalTime min, LocalTime max, Horario[] horario) {
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
