package modelo;

import java.io.Serializable;

/**
 * Clase Pagina - COMPOSICIÓN Una página NO puede existir sin un libro
 */
public class Pagina implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    private int numeroPagina;
    private String contenido;

    // Constructor
    public Pagina(int numeroPagina, String contenido) {
        this.numeroPagina = numeroPagina;
        this.contenido = contenido;
    }

    // Getters
    public int getNumeroPagina() {
        return numeroPagina;
    }

    public String getContenido() {
        return contenido;
    }

    // Setters
    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    // Método toString
    @Override
    public String toString() {
        return "Página " + numeroPagina + ": " + contenido;
    }
}
