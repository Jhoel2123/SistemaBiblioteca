package modelo;

import java.io.Serializable;

/**
 * Clase Autor - AGREGACIÓN El autor puede existir independientemente de la
 * Biblioteca Se registra en la biblioteca pero no depende de ella
 */
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    private String nombre;
    private String nacionalidad;

    // Constructor
    public Autor(String nombre, String nacionalidad) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    // Método toString
    @Override
    public String toString() {
        return nombre + " (" + nacionalidad + ")";
    }
}
