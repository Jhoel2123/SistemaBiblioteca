package modelo;

import java.io.Serializable;

/**
 * Clase Horario - COMPOSICIÓN El horario NO puede existir sin la Biblioteca Se
 * crea cuando se crea la biblioteca
 */
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    private String diasApertura;
    private String horaApertura;
    private String horaCierre;

    // Constructor
    public Horario(String diasApertura, String horaApertura, String horaCierre) {
        this.diasApertura = diasApertura;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    // Getters
    public String getDiasApertura() {
        return diasApertura;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    // Setters
    public void setDiasApertura(String diasApertura) {
        this.diasApertura = diasApertura;
    }

    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

    // Método toString
    @Override
    public String toString() {
        return diasApertura + ": " + horaApertura + " - " + horaCierre;
    }
}
