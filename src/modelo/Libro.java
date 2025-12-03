package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Libro - COMPOSICIÓN con Pagina El libro CREA sus propias páginas (no
 * pueden existir sin el libro) AGREGACIÓN con Biblioteca (puede existir fuera
 * de la biblioteca)
 */
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    private String titulo;
    private String isbn;
    private List<Pagina> paginas; // COMPOSICIÓN

    // Constructor
    public Libro(String titulo, String isbn, String[] contenidoPaginas) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.paginas = new ArrayList<>();

        // COMPOSICIÓN: El libro CREA sus páginas internamente
        for (int i = 0; i < contenidoPaginas.length; i++) {
            this.paginas.add(new Pagina(i + 1, contenidoPaginas[i]));
        }
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public List<Pagina> getPaginas() {
        return paginas;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    // Método para agregar página
    public void agregarPagina(Pagina pagina) {
        this.paginas.add(pagina);
    }

    // Método toString
    @Override
    public String toString() {
        return titulo + " (ISBN: " + isbn + ")";
    }
}
