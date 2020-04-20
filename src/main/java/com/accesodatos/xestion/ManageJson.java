/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.xestion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erifc
 */
public class ManageJson {

    
    public Object jsonToObj(String pathFile, Class objClass) {

        try {
            File file = getFileFromResources(pathFile);
            FileReader fr = new FileReader(file, StandardCharsets.UTF_8);//utf8 para no tener problemas con ñ o tildes
            BufferedReader br = new BufferedReader(fr);

            //Leer linea a linea
            StringBuilder jsonBuiler = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                jsonBuiler.append(linea).append("\n");
            }

            br.close();

            //Construimos el string con todas las lineas leídas
            String json = jsonBuiler.toString();
            System.out.println("archivo json: " + json);

            //Pasamos json a clase Java
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(json, objClass);

        } catch (FileNotFoundException ex) {
            System.err.println("Error al intentar abrir el archivo");
        } catch (IOException ex) {
            System.err.println("Error IOException");
        }
        return null;
    }

    public void saveObjToJson(Object obj, File pathFile) {
        // Convertimos objeto a JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String newJson = gson.toJson(obj);

        // Escribimos JSON en fichero
        try {
            FileWriter fr = new FileWriter(pathFile);
            BufferedWriter bw = new BufferedWriter(fr);

            bw.write(newJson);
            bw.close();

        } catch (IOException ex) {
            System.err.println("Error IOException");
        }
    }

    private File getFileFromResources(String fileName) {

        File jsonFile = null;

        try {
            InputStream input = getClass().getResourceAsStream(fileName);
            System.out.println("input: " + input);

            byte[] buffer = new byte[input.available()];
            input.read(buffer);

            jsonFile = new File("newData.json");
            OutputStream outStream = new FileOutputStream(jsonFile);
            outStream.write(buffer);
        } catch (IOException ex) {
            Logger.getLogger(ManageJson.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("file: " + jsonFile);
            return jsonFile;
        }
    }
}
