package modelos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Rectangulo extends Trazo {

    public Rectangulo(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void dibujar(Graphics g, Color color, boolean seleccionando) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(seleccionando ? 4 : 1));
        g2.drawRect(getXMinimo(), getYMinimo(), getAncho(), getAlto());
    }

    @Override
    public boolean cercano(int x, int y) {
        int minX = Math.min(getX1(), getX2());
        int minY = Math.min(getY1(), getY2());
        int maxX = Math.max(getX1(), getX2());
        int maxY = Math.max(getY1(), getY2());

        return esCercanoALinea(x, y, minX, minY, maxX, minY) ||
                esCercanoALinea(x, y, maxX, minY, maxX, maxY) ||
                esCercanoALinea(x, y, minX, maxY, maxX, maxY) ||
                esCercanoALinea(x, y, minX, minY, minX, maxY);
    }

    @Override
    public String getTipo() {
        return TipoTrazo.RECTANGULO.name();
    }

}
