/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.xestion;

/**
 *
 * @author erifc
 */
public class Province {

    private String id;
    private String nome;

    public Province() {
    }

    public Province(String id, String name) {
        this.id = id;
        this.nome = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Province{" + "id=" + id + ", nome=" + nome + '}';
    }

}
