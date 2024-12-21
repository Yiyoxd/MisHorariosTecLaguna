package com.yiyoalfredo.mishorariosteclaguna.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;

import java.util.ArrayList;
import java.util.List;

public class MateriaService {
    private final List<Materia> materias = new ArrayList<>();

    public void cargarMateriasDesdeJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Materia> cargadas = mapper.readValue(json, new TypeReference<>() {
            });
            materias.addAll(cargadas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Materia> obtenerMaterias() {
        return new ArrayList<>(materias);
    }
}
