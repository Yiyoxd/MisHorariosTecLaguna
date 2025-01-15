package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.model.MateriaHorario;
import static com.yiyoalfredo.mishorariosteclaguna.service.BusquedaHorario.CREDITOS_MAXIMOS;
import java.util.*;

public class SortingsService {
    private SortingsService() {}

    public static List<List<MateriaHorario>> orderByCreditos(List<List<MateriaHorario>> horariosMaterias) {
        List<List<MateriaHorario>>[] buckets = new ArrayList[CREDITOS_MAXIMOS + 1];
        Arrays.setAll(buckets, i -> new ArrayList<>());
        for (List<MateriaHorario> horarios : horariosMaterias) {
            int sumaCreditos = horarios.stream().mapToInt(m -> m.getMateria().getCreditos()).sum();
            buckets[sumaCreditos].add(horarios);
        }

        List<List<MateriaHorario>> sorted = new ArrayList<>(horariosMaterias.size());
        for (int i = 0; i <= CREDITOS_MAXIMOS; i++) {
            sorted.addAll(buckets[i]);
        }

        return sorted;
    }
}
