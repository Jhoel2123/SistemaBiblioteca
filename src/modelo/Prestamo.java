package modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase Prestamo - ASOCIACIÓN Relaciona un Estudiante con un Libro Ambos
 * (Estudiante y Libro) existen independientemente del préstamo
 */
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    private String fechaPrestamo;
    private String fechaDevolucion;
    private Estudiante estudiante; // ASOCIACIÓN
    private Libro libro; // ASOCIACIÓN

    // Constructor
    public Prestamo(Estudiante estudiante, Libro libro) {
        this.estudiante = estudiante;
        this.libro = libro;

        // Establecer fecha de préstamo (hoy)
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.fechaPrestamo = sdf.format(new Date());

        // Establecer fecha de devolución (7 días después)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        this.fechaDevolucion = sdf.format(cal.getTime());
    }

    // Getters
    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Libro getLibro() {
        return libro;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    // Setters
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    // Método toString
    @Override
    public String toString() {
        return estudiante.getNombre() + " - " + libro.getTitulo()
                + " (Devolución: " + fechaDevolucion + ")";
    }
}
