/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoclub;

import java.io.*;
import java.util.ArrayList;
import java.time.LocalDate;
/**
 *
 * @author shdez
 */
public class ControladorSocio {
    private ArrayList<Socio> listaSocios;
    private final String RUTA_ARCHIVO = "socios.txt";

    public ControladorSocio() {
        listaSocios = new ArrayList<>();
        cargarDesdeArchivo(); // Leer los datos al iniciar el programa
    }

    private void cargarDesdeArchivo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public ArrayList<Socio> getListaSocios() {
    return listaSocios;
}

    public void guardarEnArchivo() {
    // El "try-with-resources" cierra automáticamente el archivo al terminar
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
        for (Socio s : listaSocios) {
            String linea = s.getIdSocio() + "|" +
                           s.getNombre() + "|" +
                           s.getMembresia() + "|" +
                           s.getCost() + "|" +
                           s.getFechaIngreso() + "|" +
                           s.isEstaActivo();
            bw.write(linea);
            bw.newLine();
        }
    } catch (IOException e) {
        System.err.println("Error al guardar: " + e.getMessage());
    }
}
  
}
