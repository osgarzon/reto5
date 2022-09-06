package com.reto5.controlador;

import com.reto5.modelo.Producto;
import com.reto5.modelo.ProductoRepositorio;
import com.reto5.vista.GUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ProductoControlador implements ActionListener {

    ProductoRepositorio repositorio;
    GUI gui;
    DefaultTableModel defaulttablemodel;

    public ProductoControlador() {
        super();
    }

    public ProductoControlador(ProductoRepositorio repositorio, GUI gui) {
        this.repositorio = repositorio;
        this.gui = gui;
        agregarEventos();
        gui.setVisible(true);
        actializarTabla();
    }

    private void limpiar() {
        gui.getTfcodigo().setText("");
        gui.getTfnombre().setText("");
        gui.getTfprecio().setText("");
        gui.getTfinventario().setText("");
    }

    private static Boolean esNumero(String str) {
        try {
            double numero = Double.parseDouble(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private Boolean ValidarFormulario() {
        if (gui.getTfnombre().getText().equals("") || gui.getTfprecio().getText().equals("")
                || gui.getTfinventario().getText().equals("")) {
            return false;
        }

        if (!esNumero(gui.getTfprecio().getText()) || !esNumero(gui.getTfinventario().getText())) {
            return false;
        }
        return true;
    }

    private void agregarEventos() {
        gui.getBtnagregar().addActionListener(this);
        gui.getBtnactualizar().addActionListener(this);
        gui.getBtnborrar().addActionListener(this);
        gui.getBtninforme().addActionListener(this);
        gui.getTabla().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                llenarCampos(mouseEvent);
            }
        });
    }

    private void llenarCampos(MouseEvent mouseEvent) {
        JTable targuet = (JTable) mouseEvent.getSource();
        gui.getTfcodigo().setText(gui.getTabla().getModel().getValueAt(targuet.getSelectedRow(), 0).toString());
        gui.getTfnombre().setText(gui.getTabla().getModel().getValueAt(targuet.getSelectedRow(), 1).toString());
        gui.getTfprecio().setText(gui.getTabla().getModel().getValueAt(targuet.getSelectedRow(), 2).toString());
        gui.getTfinventario().setText(gui.getTabla().getModel().getValueAt(targuet.getSelectedRow(), 3).toString());
    }

    private void actializarTabla() {
        String titulos[] = new String[]{"Código", "Nombre", "Precio", "Inventario"};
        defaulttablemodel = new DefaultTableModel(titulos, 0);
        List<Producto> listaProductos = (List<Producto>) repositorio.findAll();
        for (Producto producto : listaProductos) {
            defaulttablemodel.addRow(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getPrecio(), producto.getInventario()});
        }
        gui.getTabla().setModel(defaulttablemodel);
        gui.getTabla().setPreferredSize(new Dimension(350, defaulttablemodel.getRowCount() * 16));
        gui.getTabla().getColumnModel().getColumn(0).setMaxWidth(0);
        gui.getTabla().getColumnModel().getColumn(0).setMinWidth(0);
        gui.getTabla().getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        gui.getTabla().getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        gui.getjScrollPane1().setViewportView(gui.getTabla());
    }

    public void agregarProducto() {
        try {
            if (ValidarFormulario()) {
                Producto prod = new Producto(
                        gui.getTfnombre().getText(),
                        Double.parseDouble(gui.getTfprecio().getText()),
                        Integer.parseInt(gui.getTfinventario().getText())
                );
                repositorio.save(prod);
                JOptionPane.showMessageDialog(null, "Producto agregado con exito", "Producto", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "ERROR, el producto no se agrego", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR, el producto no se agrego", "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            limpiar();
            actializarTabla();
        }
    }

    public void actualizarProducto() {
        try {
            if (ValidarFormulario()) {
                Producto prod = new Producto(
                        Integer.parseInt(gui.getTfcodigo().getText()),
                        gui.getTfnombre().getText(),
                        Double.parseDouble(gui.getTfprecio().getText()),
                        Integer.parseInt(gui.getTfinventario().getText())
                );
                repositorio.save(prod);
                JOptionPane.showMessageDialog(null, "Producto actualizado con exito", "Producto", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "ERROR, el producto no se actualizo", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR, el producto no se actualizo", "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            limpiar();
            actializarTabla();
        }
    }

    public void borrarProducto(){
        try {
            if (ValidarFormulario()) {
                Producto prod = new Producto(
                        Integer.parseInt(gui.getTfcodigo().getText()),
                        gui.getTfnombre().getText(),
                        Double.parseDouble(gui.getTfprecio().getText()),
                        Integer.parseInt(gui.getTfinventario().getText())
                );
                repositorio.delete(prod);
                JOptionPane.showMessageDialog(null, "Producto borrado con exito", "Producto", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "ERROR, el producto no se borro", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR, el producto no se borro", "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            limpiar();
            actializarTabla();
        }
    }
    
    public void informe(){
        JOptionPane.showMessageDialog(null, 
            "Producto precion mayor: "+repositorio.getProductoPrecioMayor()+"\n"+
            "Producto precion menor: "+repositorio.getProductoPrecioMenor()+"\n"+
            "Promedio Precio: "+repositorio.getPromedioPrecio()+"\n"+
            "Valor del inventario: "+repositorio.getTotalInventario(),
            "Informe",
            JOptionPane.INFORMATION_MESSAGE
                );
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== gui.getBtnagregar()){
            agregarProducto();
        }
        if (e.getSource()== gui.getBtnactualizar()){
            actualizarProducto();
        }
        if (e.getSource()== gui.getBtnborrar()){
            borrarProducto();
        }
        if(e.getSource()== gui.getBtninforme()){
            informe();
        }
    }

}
