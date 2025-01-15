package com.yiyoalfredo.mishorariosteclaguna.service;

import com.yiyoalfredo.mishorariosteclaguna.model.MateriaHorario;
import java.util.*;

public class SortingsService {
    private SortingsService() {}

    public static List<List<MateriaHorario>> orderByCreditos(List<List<MateriaHorario>> horariosMaterias) {
        Map<Integer, List<List<MateriaHorario>>> buckets = new HashMap<>();
        int maxSum = 0;

        for (List<MateriaHorario> horarios : horariosMaterias) {
            int sumaCreditos = horarios.stream().mapToInt(m -> m.getMateria().getCreditos()).sum();
            maxSum = Math.max(maxSum, sumaCreditos);

            List<List<MateriaHorario>> bucket = buckets.computeIfAbsent(sumaCreditos, k -> new ArrayList<>());
            bucket.add(horarios);
        }

        List<List<MateriaHorario>> sorted = new ArrayList<>(horariosMaterias.size());
        List<List<MateriaHorario>> defaultList = new ArrayList<>();
        for (int i = 0; i <= maxSum; i++) {
            List<List<MateriaHorario>> bucket = buckets.getOrDefault(i, defaultList);
            sorted.addAll(bucket);
        }

        return sorted;
    }
}
