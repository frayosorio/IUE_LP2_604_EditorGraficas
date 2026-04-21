package servicios;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

import javax.swing.JPanel;

import com.fasterxml.jackson.core.type.TypeReference;

import modelos.Linea;
import modelos.Nodo;
import modelos.Ovalo;
import modelos.Rectangulo;
import modelos.TipoTrazo;
import modelos.Trazo;
import modelos.TrazoDTO;

public class Dibujo {

    // ********** Variables y Metodos de Clase **********

    private Nodo cabeza;
    private Nodo nodoSeleccionado;

    public Dibujo() {
        cabeza = null;
    }

    public void agregar(Trazo trazo, Color color) {
        Nodo nuevo = new Nodo(trazo, color);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            // recorrer la lista hasta el ultimo nodo
            var apuntador = cabeza;
            while (apuntador.siguiente != null) {
                apuntador = apuntador.siguiente;
            }
            apuntador.siguiente = nuevo;
        }
    }

    public void dibujar(JPanel pnl, boolean seleccionando) {
        limpiarPanel(pnl);
        var g = pnl.getGraphics();
        var apuntador = cabeza;
        while (apuntador != null) {
            apuntador.getTrazo().dibujar(g, apuntador.getColor(), seleccionando && apuntador == nodoSeleccionado);
            apuntador = apuntador.siguiente;
        }
    }

    public void desdeJSON(String nombreArchivo) {
        List<TrazoDTO> trazos = Archivo.leerJson(nombreArchivo, new TypeReference<List<TrazoDTO>>() {
        });

        cabeza = null;
        for (TrazoDTO trazoDTO : trazos) {
            Trazo trazo = null;
            switch (TipoTrazo.valueOf(trazoDTO.getTipo())) {
                case LINEA:
                    trazo = new Linea(trazoDTO.getX1(), trazoDTO.getY1(), trazoDTO.getX2(), trazoDTO.getY2());
                    break;
                case RECTANGULO:
                    trazo = new Rectangulo(trazoDTO.getX1(), trazoDTO.getY1(), trazoDTO.getX2(), trazoDTO.getY2());
                    break;
                case OVALO:
                    trazo = new Ovalo(trazoDTO.getX1(), trazoDTO.getY1(), trazoDTO.getX2(), trazoDTO.getY2());
                    break;
            }
            if (trazo != null) {
                agregar(trazo, new Color(trazoDTO.getRed(), trazoDTO.getGreen(), trazoDTO.getBlue()));
            }
        }

    }

    public boolean guardarJSON(String nombreArchivo) {
        List<TrazoDTO> trazos = new ArrayList<>();

        var apuntador = cabeza;
        while (apuntador != null) {
            trazos.add(apuntador.toDTO());
            apuntador = apuntador.siguiente;
        }
        return Archivo.guardarJson(nombreArchivo, trazos);

    }

    public boolean seleccionar(int x, int y) {
        nodoSeleccionado = null;
        var apuntador = cabeza;
        while (apuntador != null) {
            if (apuntador.getTrazo().cercano(x, y)) {
                nodoSeleccionado = apuntador;
                return true;
            }
            apuntador = apuntador.siguiente;
        }
        return false;
    }

    // ********** Metodos Estaticos **********

    public static void limpiarPanel(JPanel pnl) {
        Graphics g = pnl.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, pnl.getWidth(), pnl.getHeight());
    }

}
