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
    java.io.File archivo = new java.io.File(RUTA_ARCHIVO);
    if (!archivo.exists()) {
        return; // Si el archivo no existe todavía, no hay nada que cargar
    }

    try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(archivo))) {
        String linea;
        listaSocios.clear(); // Limpiamos la lista antes de cargar para evitar duplicados

        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split("\\|");
            if (partes.length == 6) {
                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                String membresia = partes[2];
                double costo = Double.parseDouble(partes[3]);
                java.time.LocalDate fecha = java.time.LocalDate.parse(partes[4]);
                boolean activo = Boolean.parseBoolean(partes[5]);

                Socio socio = new Socio(id, nombre, membresia, costo, fecha, activo);
                listaSocios.add(socio);
            }
        }
    } catch (java.io.IOException e) {
        System.err.println("Error al cargar los datos del archivo: " + e.getMessage());
    }
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
    public void importarDesdeCSV(java.io.File archivoCSV) throws java.io.IOException {
    try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(archivoCSV))) {
        String linea;
        // Si tu CSV tiene encabezados (ej: ID,Nombre,Membresia...), lee la primera línea para saltarla:
        // br.readLine(); 

        while ((linea = br.readLine()) != null) {
            // Dividimos por comas
            String[] partes = linea.split(",");
            if (partes.length == 6) {
                int id = Integer.parseInt(partes[0].trim());
                String nombre = partes[1].trim();
                String membresia = partes[2].trim();
                double costo = Double.parseDouble(partes[3].trim());
                java.time.LocalDate fecha = java.time.LocalDate.parse(partes[4].trim());
                boolean activo = Boolean.parseBoolean(partes[5].trim());

                // Creamos el socio y lo agregamos a la lista interna
                Socio socio = new Socio(id, nombre, membresia, costo, fecha, activo);
                listaSocios.add(socio);
            }
        }
        // Una vez importados todos, los escribimos de golpe en tu socios.txt para mantener la persistencia
        guardarEnArchivo();
        
    }
    
}
    public void generarReportePDF(String rutaDestino) throws Exception {
    com.itextpdf.text.Document documento = new com.itextpdf.text.Document();
    com.itextpdf.text.pdf.PdfWriter.getInstance(documento, new java.io.FileOutputStream(rutaDestino));
    documento.open();
    
    // Fuentes estandarizadas de iText
    com.itextpdf.text.Font fuenteTitulo = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 16, com.itextpdf.text.Font.BOLD, com.itextpdf.text.BaseColor.BLUE);
    com.itextpdf.text.Font fuenteSub = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 11, com.itextpdf.text.Font.ITALIC);
    com.itextpdf.text.Font fuenteCabecera = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 10, com.itextpdf.text.Font.BOLD, com.itextpdf.text.BaseColor.WHITE);
    com.itextpdf.text.Font fuenteCuerpo = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 10, com.itextpdf.text.Font.NORMAL);

    // Encabezado
    documento.add(new com.itextpdf.text.Paragraph("GYMFLOW - REPORTE OFICIAL", fuenteTitulo));
    documento.add(new com.itextpdf.text.Paragraph("Fecha: " + java.time.LocalDate.now() + " | Total Socios: " + listaSocios.size(), fuenteSub));
    documento.add(com.itextpdf.text.Chunk.NEWLINE);

    // Tabla PDF (6 columnas coordinadas con tu interfaz)
    com.itextpdf.text.pdf.PdfPTable tabla = new com.itextpdf.text.pdf.PdfPTable(6);
    tabla.setWidthPercentage(100);

    String[] cabeceras = {"ID", "Nombre", "Membresía", "Costo", "Ingreso", "Estado"};
    for (String c : cabeceras) {
        com.itextpdf.text.pdf.PdfPCell celda = new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase(c, fuenteCabecera));
        celda.setBackgroundColor(com.itextpdf.text.BaseColor.DARK_GRAY);
        celda.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        tabla.addCell(celda);
    }

    // Inyección de registros de socios
    for (Socio s : listaSocios) {
        tabla.addCell(new com.itextpdf.text.Phrase(String.valueOf(s.getIdSocio()), fuenteCuerpo));
        tabla.addCell(new com.itextpdf.text.Phrase(s.getNombre(), fuenteCuerpo));
        tabla.addCell(new com.itextpdf.text.Phrase(s.getMembresia(), fuenteCuerpo));
        tabla.addCell(new com.itextpdf.text.Phrase("$" + s.getCosto(), fuenteCuerpo)); // Ajusta a getCost() si no lo cambiaste
        tabla.addCell(new com.itextpdf.text.Phrase(s.getFechaIngreso().toString(), fuenteCuerpo));
        
    }
    
    documento.add(tabla);
    documento.close();
}
}
  

