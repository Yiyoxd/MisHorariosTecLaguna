package com.yiyoalfredo.mishorariosteclaguna.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yiyoalfredo.mishorariosteclaguna.model.Materia;

import java.util.Objects;

public class MateriaDto {
    private final String nombre;
    private final String clave;

    public MateriaDto(Materia materia) {
        this(materia.getNombre(), materia.getClave());
    }

    @JsonCreator
    public MateriaDto(
            @JsonProperty("nombre") String nombre,
            @JsonProperty("clave") String clave) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.clave = Objects.requireNonNull(clave, "La clave no puede ser nula");
    }

    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }
}
