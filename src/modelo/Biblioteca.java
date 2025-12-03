package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Biblioteca - Clase principal del sistema Implementa las tres
 * relaciones: - COMPOSICIÓN con Horario (lo crea internamente) - AGREGACIÓN con
 * Libro, Autor, Estudiante (existen independientemente) - Gestiona Préstamos
 * (ASOCIACIÓN entre Estudiante y Libro)
 */
public class Biblioteca implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    private String nombre;
    private List<Libro> libros; // AGREGACIÓN
    private List<Autor> autores; // AGREGACIÓN
    private List<Estudiante> estudiantes; // AGREGACIÓN
    private List<Prestamo> prestamosActivos;
    private Horario horario; // COMPOSICIÓN

    // Constructor
    public Biblioteca(String nombre) {
        this.nombre = nombre;
        this.libros = new ArrayList<>();
        this.autores = new ArrayList<>();
        this.estudiantes = new ArrayList<>();
        this.prestamosActivos = new ArrayList<>();

        // COMPOSICIÓN: La biblioteca CREA su horario
        this.horario = new Horario("Lunes a Viernes", "08:00", "20:00");
    }

    // ========== MÉTODOS DE GESTIÓN DE LIBROS ==========
    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public void eliminarLibro(Libro libro) {
        libros.remove(libro);
    }

    public Libro buscarLibroPorTitulo(String titulo) {
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        return null;
    }

    // ========== MÉTODOS DE GESTIÓN DE AUTORES ==========
    public void agregarAutor(Autor autor) {
        autores.add(autor);
    }

    public void eliminarAutor(Autor autor) {
        autores.remove(autor);
    }

    // ========== MÉTODOS DE GESTIÓN DE ESTUDIANTES ==========
    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    public void eliminarEstudiante(Estudiante estudiante) {
        estudiantes.remove(estudiante);
    }

    public Estudiante buscarEstudiantePorCodigo(String codigo) {
        for (Estudiante est : estudiantes) {
            if (est.getCodigo().equalsIgnoreCase(codigo)) {
                return est;
            }
        }
        return null;
    }

    // ========== MÉTODOS DE GESTIÓN DE PRÉSTAMOS ==========
    public void prestarLibro(Estudiante estudiante, Libro libro) {
        Prestamo prestamo = new Prestamo(estudiante, libro);
        prestamosActivos.add(prestamo);
    }

    public void devolverLibro(Prestamo prestamo) {
        prestamosActivos.remove(prestamo);
    }

    // ========== MÉTODOS DE CIERRE ==========
    public void cerrarBiblioteca() {
        // Al cerrar la biblioteca, se eliminan los préstamos
        // pero los libros, autores y estudiantes siguen existiendo
        prestamosActivos.clear();
        System.out.println("Biblioteca cerrada. Préstamos eliminados.");
    }

    // ========== GETTERS ==========
    public String getNombre() {
        return nombre;
    }

    public Horario getHorario() {
        return horario;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public List<Prestamo> getPrestamos() {
        return prestamosActivos;
    }

    // ========== SETTERS ==========
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    // ========== MÉTODO PARA MOSTRAR ESTADÍSTICAS ==========
    public String obtenerEstadisticas() {
        return "Biblioteca: " + nombre + "\n"
                + "Libros: " + libros.size() + "\n"
                + "Autores: " + autores.size() + "\n"
                + "Estudiantes: " + estudiantes.size() + "\n"
                + "Préstamos activos: " + prestamosActivos.size() + "\n"
                + "Horario: " + horario.toString();
    }
}
