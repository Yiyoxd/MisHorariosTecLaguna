package com.yiyoalfredo.mishorariosteclaguna.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Alumno {
    private String nombre;
    private String numControl;
    private Carrera carrera;
    private List<String> materiasCursadas;

    public Alumno() {
        this.materiasCursadas = new ArrayList<>();
    }

    @JsonCreator
    public Alumno(
            @JsonProperty("nombre") String nombre,
            @JsonProperty("numControl") String numControl,
            @JsonProperty("carrera") Carrera carrera,
            @JsonProperty("materiasCursadas") List<String> materiasCursadas) {
        this.nombre = nombre;
        this.numControl = numControl;
        this.carrera = carrera;
        this.materiasCursadas = materiasCursadas == null ? new ArrayList<>() : materiasCursadas;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public List<String> getMateriasCursadas() {
        return new ArrayList<>(materiasCursadas);
    }

    public void setMateriasCursadas(List<String> materiasCursadas) {
        this.materiasCursadas = materiasCursadas == null ? new ArrayList<>() : materiasCursadas;
    }

    @Override
    public String toString() {
        return numControl + " " + nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Alumno other)) return false;
        return Objects.equals(numControl, other.numControl) &&
                Objects.equals(nombre, other.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numControl, nombre);
    }
}
