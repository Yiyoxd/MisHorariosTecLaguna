package com.yiyoalfredo.mishorariosteclaguna.model;
import java.util.List;

public class Materia {
    private String nombre;
    private String clave;
    private int creditos;
    private List<String> prerequisitos;

    public Materia() {}

    public Materia(String nombre, String clave, List<String> prerequisitos, int creditos) {
        this.nombre = nombre;
        this.clave = clave;
        this.prerequisitos = prerequisitos;
        this.creditos = creditos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public List<String> getPrerequisitos() {
        return prerequisitos;
    }

    public void setPrerequisitos(List<String> prerequisitos) {
        this.prerequisitos = prerequisitos;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Clave: ").append(clave).append("\n");
        sb.append("Creditos: ").append(creditos).append("\n");
        sb.append("Prerequisitos: ").append(prerequisitos ).append("\n");

        return sb.toString();
    }

    @Override
    public int hashCode() {
        return nombre.hashCode() + clave.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Materia other)) {
            return false;
        }

        return nombre.equals(other.nombre) && clave.equals(other.clave);
    }
}
