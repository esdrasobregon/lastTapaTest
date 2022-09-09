/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author esdra
 */
public class usuario {
    String msqlUsser, contrasenya;

    public usuario(String msqlUsser, String contrasenya) {
        this.msqlUsser = msqlUsser;
        this.contrasenya = contrasenya;
    }

    public usuario() {
    }

    public String getMsqlUsser() {
        return msqlUsser;
    }

    public void setMsqlUsser(String msqlUsser) {
        this.msqlUsser = msqlUsser;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
    
}
