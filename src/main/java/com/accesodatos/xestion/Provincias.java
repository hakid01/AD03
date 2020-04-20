/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.xestion;

import java.util.LinkedList;

/**
 *
 * @author erifc
 */
public class Provincias {
    private LinkedList<Province> provincias = new LinkedList<>();

    public Provincias() {
    }
    
    public Provincias(LinkedList<Province> provinces){
        this.provincias = provinces;
    }

    public LinkedList<Province> getProvincias() {
        return provincias;
    }

    public void setProvincias(LinkedList<Province> provincias) {
        this.provincias = provincias;
    }

    @Override
    public String toString() {
        return "Provincies{" + "provinces=" + provincias + '}';
    }
    
}
