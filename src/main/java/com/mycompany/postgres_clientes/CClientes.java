/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.postgres_clientes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author suyan
 */
public class CClientes {
    String NOMBRE,RAZON,NIT;
    int codigo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getRAZON() {
        return RAZON;
    }

    public void setRAZON(String RAZON) {
        this.RAZON = RAZON;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }
    public void MostrarClientes(JTable paramTablaTotalClientes){
        DBConnection objetoConexion=new DBConnection();
        DefaultTableModel modelo=new DefaultTableModel();
        String sql="SELECT *FROM CLIENTES;";
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Nit");
        modelo.addColumn("Razon");
        paramTablaTotalClientes.setModel(modelo);
        String[] datos=new String[4];
        Statement stmt;
        try{
            stmt=objetoConexion.establecerConexion().createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(4);
                datos[3]=rs.getString(3);
                
                modelo.addRow(datos);
            }
            paramTablaTotalClientes.setModel(modelo);
            
        }catch(Exception e){
          JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());

        }
        
    }
    public void insertarCliente(JTextField paramNombres, JTextField paramRazon,JTextField paramNit){
        setNOMBRE(paramNombres.getText());
        setRAZON(paramRazon.getText());
        setNIT(paramNit.getText());
        DBConnection objetoConexion= new DBConnection();
        String insert="""
                      CREATE OR REPLACE PROCEDURE INSERT_CLIENTE(
                       \tIN FULL_NAME VARCHAR(10),
                      \tIN NIT_CLIENT VARCHAR(20),
                      \tIN RAZON_CLIENT VARCHAR(10)
                      )
                      LANGUAGE plpgsql
                      AS $$
                      BEGIN 
                      \tINSERT INTO CLIENTES (NOMBRE, NIT, RAZON)
                      \tVALUES (FULL_NAME, NIT_CLIENT, RAZON_CLIENT);
                      END;
                      $$;""";
        
        try{
            CallableStatement cs=objetoConexion.establecerConexion().prepareCall(insert);
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se ha creado el procedimiento insertar correctamente");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        String call="CALL INSERT_CLIENTE(?,?,?);";
        try{
            CallableStatement cs=objetoConexion.establecerConexion().prepareCall(call);
            cs.setString(1,getNOMBRE());
            cs.setString(3,getRAZON());
            cs.setString(2,getNIT());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se ha llamado el procedimiento correctamente");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    public void SeleccionarCliente(JTable paramTablaCliente,JTextField paramCodigo,JTextField paramNombre, JTextField paramRazon,JTextField paramNit){
        try{
           int fila=paramTablaCliente.getSelectedRow();
           if(fila>=0){
               paramCodigo.setText(paramTablaCliente.getValueAt(fila,0).toString());
               paramNombre.setText(paramTablaCliente.getValueAt(fila,1).toString());
               paramRazon.setText(paramTablaCliente.getValueAt(fila,3).toString());
               paramNit.setText(paramTablaCliente.getValueAt(fila,2).toString()); 
           }else{
               JOptionPane.showMessageDialog(null, "fila no seleccionada");
           }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    public void ActualizarCliente(JTextField paramCodigo,JTextField paramNombres, JTextField paramRazon,JTextField paramNit){
        
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        setNOMBRE(paramNombres.getText());
        setRAZON(paramRazon.getText());
        setNIT(paramNit.getText());
        DBConnection objetoConexion= new DBConnection();
        String update="""
                      CREATE OR REPLACE PROCEDURE UPDATE_CLIENTE(
                      \tIN ID INT,
                      \tIN FULL_NAME VARCHAR(10),
                      \tIN NIT_CLIENT VARCHAR(20),
                      \tIN RAZON_CLIENT VARCHAR(10)
                      )
                       LANGUAGE plpgsql
                       AS $$
                       BEGIN 
                       \tUPDATE CLIENTES
                      \tSET NOMBRE=FULL_NAME,
                      \t\tNIT=NIT_CLIENT,
                      \t\tRAZON=RAZON_CLIENT
                      \tWHERE ID_CLIENTE=ID;
                       END;
                       $$;""";
        try{
            CallableStatement cs=objetoConexion.establecerConexion().prepareCall(update);
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se ha creado el procedimiento");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        
        String call=" CALL UPDATE_CLIENTE(?,?,?,?);";
        try{
            CallableStatement cs=objetoConexion.establecerConexion().prepareCall(call);
            cs.setString(2,getNOMBRE());
            cs.setString(4  ,getRAZON());
            cs.setString(3,getNIT());
            cs.setInt(1,getCodigo());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se ha llamado el procedimiento");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    public void EliminarCliente(JTextField paramCodigo){
        
        setCodigo(Integer.parseInt(paramCodigo.getText()));
       
        DBConnection objetoConexion= new DBConnection();
        String delete="""
                      CREATE OR REPLACE PROCEDURE DELETE_CLIENTE(
                      \tIN ID INT
                      )
                       LANGUAGE plpgsql
                       AS $$
                       BEGIN 
                       \tDELETE FROM CLIENTES
                      \tWHERE ID_CLIENTE=ID;
                       END;
                       $$;""";
        try{
            CallableStatement cs=objetoConexion.establecerConexion().prepareCall(delete);
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se ha creado el procedimiento correctamente");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        String call="CALL DELETE_CLIENTE(?);";
        try{
            CallableStatement cs=objetoConexion.establecerConexion().prepareCall(call);
            cs.setInt(1,getCodigo());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se ha llamado el procedimiento correctamente");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
