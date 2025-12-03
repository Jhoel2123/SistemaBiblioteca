package modelo;

import java.io.Serializable;

/**
 * Clase Estudiante - AGREGACIÓN El estudiante puede existir independientemente
 * de la Biblioteca Representa a los usuarios que solicitan préstamos
 */
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    private String codigo;
    private String nombre;

    // Constructor
    public Estudiante(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    // Getters
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    // Setters
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método toString
    @Override
    public String toString() {
        return nombre + " - " + codigo;
    }
}
