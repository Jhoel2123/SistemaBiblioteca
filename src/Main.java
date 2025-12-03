import vista.VentanaPrincipal;
import javax.swing.*;

/**
 * Clase principal del Sistema de Biblioteca UMSA
 * Punto de entrada de la aplicación
 */
public class Main {
    
    public static void main(String[] args) {
        // Configurar el Look and Feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo establecer el Look and Feel del sistema");
        }
        
        // Ejecutar la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}