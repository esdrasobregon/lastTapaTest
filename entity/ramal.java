/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author esdra
 */
public class ramal {

    int idramal;
    String nombre;
    boolean estado;

    public ramal() {
    }

    public ramal(int idramal, String nombre, boolean estado) {
        this.idramal = idramal;
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getIdramal() {
        return idramal;
    }

    public void setIdramal(int idramal) {
        this.idramal = idramal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
