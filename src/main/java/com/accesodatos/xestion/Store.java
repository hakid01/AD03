/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.xestion;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author erifc
 */
public class Store implements Serializable{

    private String name;
    private String province;
    private String town;
    private LinkedList<Item> items;
    private LinkedList<Employee> employees;
    private HashMap<Employee, Integer> hoursEmployee;

    public Store() {
    }

    public Store(String name,String province, String town) {
        this.name = name;
        this.province = province;
        this.town = town;
        this.items = new LinkedList<>();
        this.employees = new LinkedList<>();
    }

    public Store(String name,String province, String town, LinkedList<Item> items, LinkedList<Employee> employees) {
        this.name = name;
        this.province = province;
        this.town = town;
        this.items = items;
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }

    public LinkedList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(LinkedList<Employee> employees) {
        this.employees = employees;
    }

    public HashMap<Employee, Integer> getHoursEmployee() {
        return hoursEmployee;
    }

    public void setHoursEmployee(HashMap<Employee, Integer> hoursEmployee) {
        this.hoursEmployee = hoursEmployee;
    }

    @Override
    public String toString() {
        return "Tienda: " + name + ", ciudad: " + town + '.';
    }
    
}