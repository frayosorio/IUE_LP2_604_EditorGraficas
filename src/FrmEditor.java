import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import modelos.Linea;
import modelos.Ovalo;
import modelos.Rectangulo;
import modelos.Trazo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.awt.event.MouseEvent;

public class FrmEditor extends JFrame {

    private JButton btnCargar, btnGuardar, btnEliminar, btnSeleccionar, btnDibujar;
    private JComboBox cmbTipo, cmbColorR, cmbColorG, cmbColorB;
    private JLabel lblColor;
    private JToolBar tbEditor;
    private JPanel pnlGrafica;

    Estado estado;
    int x;
    int y;
    Color color;
    String nombreArchivo = "";

    public FrmEditor() {

        tbEditor = new JToolBar();
        btnCargar = new JButton();
        btnGuardar = new JButton();
        btnSeleccionar = new JButton();
        btnEliminar = new JButton();
        btnDibujar = new JButton();
        cmbTipo = new JComboBox();
        cmbColorR = new JComboBox();
        cmbColorG = new JComboBox();
        cmbColorB = new JComboBox();
        lblColor = new JLabel("     ");

        pnlGrafica = new JPanel();

        setSize(700, 500);
        setTitle("Editor de gráficas");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnCargar.setIcon(new ImageIcon(getClass().getResource("/iconos/AbrirArchivos.png")));
        btnCargar.setToolTipText("Agregar");
        btnCargar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cargarDibujo();
            }
        });
        tbEditor.add(btnCargar);

        btnGuardar.setIcon(new ImageIcon(getClass().getResource("/iconos/Guardar.png")));
        btnGuardar.setToolTipText("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                guardarDibujo();
            }
        });
        tbEditor.add(btnGuardar);

        cmbTipo.setModel(
                new DefaultComboBoxModel(TipoTrazo.values()));
        tbEditor.add(cmbTipo);

        for (int i = 0; i < 256; i++) {
            cmbColorR.addItem(i);
            cmbColorG.addItem(i);
            cmbColorB.addItem(i);
        }
        cmbColorR.setSelectedIndex(255);
        cmbColorG.setSelectedIndex(255);
        cmbColorB.setSelectedIndex(255);
        lblColor.setOpaque(true);
        lblColor.setBorder(new LineBorder(Color.black, 2));

        cmbColorR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setColor();
            }
        });
        cmbColorG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setColor();
            }
        });
        cmbColorB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setColor();
            }
        });

        tbEditor.add(cmbColorR);
        tbEditor.add(cmbColorG);
        tbEditor.add(cmbColorB);
        tbEditor.add(lblColor);

        btnSeleccionar.setIcon(new ImageIcon(getClass().getResource("/iconos/Seleccionar.png")));
        btnSeleccionar.setToolTipText("Agregar");
        btnSeleccionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                seleccionarTrazo();
            }
        });
        tbEditor.add(btnSeleccionar);

        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/iconos/Eliminar.png")));
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                eliminarTrazo();
            }
        });
        tbEditor.add(btnEliminar);

        btnDibujar.setIcon(new ImageIcon(getClass().getResource("/iconos/Dibujar.png")));
        btnDibujar.setToolTipText("Dibujar");
        btnDibujar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dibujar();
            }
        });
        tbEditor.add(btnDibujar);

        pnlGrafica.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                pnlGraficaMouseClicked(evt);
            }
        });
        pnlGrafica.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent evt) {
                pnlGraficaMouseMoved(evt);
            }
        });

        pnlGrafica.setPreferredSize(new Dimension(300, 200));

        getContentPane().add(tbEditor, BorderLayout.NORTH);
        getContentPane().add(pnlGrafica, BorderLayout.CENTER);

        estado = Estado.NADA;
        color = Color.white;

        // ejecutar algo despues de que la ventana se despliegue
        addWindowListener(new WindowAdapter() {

            public void windowOpened(WindowEvent e) {
                Dibujo.limpiarPanel(pnlGrafica);
                setColor();
            }
        });

    }

    private void setColor() {
        color = new Color(cmbColorR.getSelectedIndex(), cmbColorG.getSelectedIndex(),
                cmbColorB.getSelectedIndex());
        lblColor.setBackground(color);
    }

    private void cargarDibujo() {

    }

    private void guardarDibujo() {

    }

    private void seleccionarTrazo() {

    }

    private void eliminarTrazo() {

    }

    private void dibujar() {

    }

    private void pnlGraficaMouseClicked(MouseEvent evt) {

        if (estado == Estado.NADA) {
            x = evt.getX();
            y = evt.getY();
            estado = Estado.TRAZANDO;

        } else if (estado == Estado.TRAZANDO) {
            Dibujo.limpiarPanel(pnlGrafica);
            var g = pnlGrafica.getGraphics();
            Trazo trazo = null;
            switch ((TipoTrazo) cmbTipo.getSelectedItem()) {
                case LINEA:
                    trazo = new Linea(x, y, evt.getX(), evt.getY());
                    break;
                case RECTANGULO:
                    trazo = new Rectangulo(x, y, evt.getX(), evt.getY());
                    break;
                case OVALO:
                    trazo = new Ovalo(x, y, evt.getX(), evt.getY());
                    break;
            }
            if (trazo != null) {
                trazo.dibujar(g, color);
            }
            estado = Estado.NADA;
        }
        System.out.println("x1=" + x + ", y1=" + y);
    }

    private void pnlGraficaMouseMoved(MouseEvent evt) {

    }

}
