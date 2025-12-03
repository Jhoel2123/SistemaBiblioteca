package persistencia;

import modelo.Biblioteca;
import java.io.*;

/**
 * Clase GestorDatos - Maneja la persistencia de datos Guarda y carga la
 * biblioteca completa en un archivo Utiliza serialización de objetos
 */
public class GestorDatos {

    // Nombre del archivo donde se guardarán los datos
    private static final String ARCHIVO_DATOS = "biblioteca_data.dat";

    /**
     * Guarda la biblioteca completa en un archivo
     *
     * @param biblioteca La biblioteca a guardar
     * @return true si se guardó correctamente, false en caso contrario
     */
    public static boolean guardarBiblioteca(Biblioteca biblioteca) {
        try {
            // Crear el flujo de salida
            FileOutputStream fileOut = new FileOutputStream(ARCHIVO_DATOS);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // Escribir el objeto Biblioteca
            out.writeObject(biblioteca);

            // Cerrar los flujos
            out.close();
            fileOut.close();

            System.out.println("✓ Datos guardados correctamente en: " + ARCHIVO_DATOS);
            return true;

        } catch (IOException e) {
            System.err.println("✗ Error al guardar los datos: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Carga la biblioteca desde un archivo
     *
     * @return La biblioteca cargada, o null si no existe o hay error
     */
    public static Biblioteca cargarBiblioteca() {
        File archivo = new File(ARCHIVO_DATOS);

        // Verificar si el archivo existe
        if (!archivo.exists()) {
            System.out.println("ℹ No existe archivo de datos previo. Se creará uno nuevo.");
            return null;
        }

        try {
            // Crear el flujo de entrada
            FileInputStream fileIn = new FileInputStream(ARCHIVO_DATOS);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // Leer el objeto Biblioteca
            Biblioteca biblioteca = (Biblioteca) in.readObject();

            // Cerrar los flujos
            in.close();
            fileIn.close();

            System.out.println("✓ Datos cargados correctamente desde: " + ARCHIVO_DATOS);
            return biblioteca;

        } catch (IOException e) {
            System.err.println("✗ Error al cargar los datos: " + e.getMessage());
            e.printStackTrace();
            return null;

        } catch (ClassNotFoundException e) {
            System.err.println("✗ Error: Clase no encontrada al cargar datos: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verifica si existe un archivo de datos previo
     *
     * @return true si existe, false en caso contrario
     */
    public static boolean existenDatosPrevios() {
        File archivo = new File(ARCHIVO_DATOS);
        return archivo.exists();
    }

    /**
     * Elimina el archivo de datos (útil para reiniciar el sistema)
     *
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public static boolean eliminarDatos() {
        File archivo = new File(ARCHIVO_DATOS);

        if (archivo.exists()) {
            boolean eliminado = archivo.delete();
            if (eliminado) {
                System.out.println("✓ Datos eliminados correctamente");
            } else {
                System.err.println("✗ No se pudieron eliminar los datos");
            }
            return eliminado;
        } else {
            System.out.println("ℹ No hay datos para eliminar");
            return true;
        }
    }

    /**
     * Crea una biblioteca con datos de ejemplo
     *
     * @return Una biblioteca con datos iniciales
     */
    public static Biblioteca crearBibliotecaConDatosEjemplo() {
        Biblioteca biblioteca = new Biblioteca("Biblioteca Central UMSA");

        // Agregar libros de ejemplo
        String[] paginasJava = {
            "Capítulo 1: Introducción a Java",
            "Capítulo 2: Programación Orientada a Objetos",
            "Capítulo 3: Herencia y Polimorfismo"
        };
        biblioteca.agregarLibro(new modelo.Libro("Programación en Java", "978-1234567890", paginasJava));

        String[] paginasAlgoritmos = {
            "Capítulo 1: Algoritmos de Búsqueda",
            "Capítulo 2: Algoritmos de Ordenamiento",
            "Capítulo 3: Complejidad Algorítmica"
        };
        biblioteca.agregarLibro(new modelo.Libro("Estructuras de Datos", "978-0987654321", paginasAlgoritmos));

        // Agregar autores de ejemplo
        biblioteca.agregarAutor(new modelo.Autor("Herbert Schildt", "Estados Unidos"));
        biblioteca.agregarAutor(new modelo.Autor("Robert Sedgewick", "Estados Unidos"));

        // Agregar estudiantes de ejemplo
        biblioteca.agregarEstudiante(new modelo.Estudiante("202101234", "Juan Pérez"));
        biblioteca.agregarEstudiante(new modelo.Estudiante("202105678", "María García"));

        System.out.println("✓ Biblioteca creada con datos de ejemplo");
        return biblioteca;
    }
}
