package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.conexion;

public class marca_crud {
    DefaultTableModel tabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int Fila, int Colum) {
            return false;
        }
    };
    conexion conexion = new conexion();
    Connection cc = conexion.conectado();
    PreparedStatement preparedStatement = null;
    Statement st = null;
    ResultSet result = null;

    public void llenarComboboxMarca(JComboBox cbox_marca) {

//Creamos objeto tipo Connection    
        PreparedStatement pst = null;

//Creamos la Consulta SQL
        String SSQL = "SELECT mar_descripcion FROM tbl_marca";

//Establecemos bloque try-catch-finally
        try {

            //Establecemos conexión con la BD 
            //Preparamos la consulta SQL
            pst = cc.prepareStatement(SSQL);
            //Ejecutamos la consulta
            result = pst.executeQuery();

            //LLenamos nuestro ComboBox
            cbox_marca.addItem("Seleccione...");

            while (result.next()) {

                cbox_marca.addItem(result.getString("mar_descripcion"));

            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e);

        } finally {

            if (cc != null) {

                try {

                    cc.close();
                    result.close();

                    cc = null;
                    result = null;

                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(null, ex);

                }

            }

        }

    }

    public void mostrarDatosConTableModel( JTable jTable1 ) {
        tabla.addColumn("ID");
        tabla.addColumn("Descripcion");
        jTable1.setModel(tabla);
        String datos[] = new String[2];    //Variable que almacena los datos de la consulta
        String sql = "select * from tbl_marca";  //Consulta sql
        try {
            preparedStatement = cc.prepareStatement(sql);
            ResultSet resultado = preparedStatement.executeQuery();  //Linea que ejecuta la consulta sql y almacena los datos en resultado

            while (resultado.next()) {                                    //Bucle que recorre la consulta obtenida
                datos[0] = resultado.getString("id_marca");
                datos[1] = resultado.getString("mar_descripcion");
                tabla.addRow(datos);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los Datos\n" + ex);
        }
    }
}
