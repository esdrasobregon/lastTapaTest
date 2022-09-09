/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author esdra
 */
public class tarifa {
    int idtarifa;
    Date fechaInicio, fechaFin;
    double valor;
    int ramal_idramal;

    public tarifa() {
    }

    public tarifa(int idtarifa, Date fechaInicio, Date fechaFin, double valor, int ramal_idramal) {
        this.idtarifa = idtarifa;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.valor = valor;
        this.ramal_idramal = ramal_idramal;
    }

    public int getIdtarifa() {
        return idtarifa;
    }

    public void setIdtarifa(int idtarifa) {
        this.idtarifa = idtarifa;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getRamal_idramal() {
        return ramal_idramal;
    }

    public void setRamal_idramal(int ramal_idramal) {
        this.ramal_idramal = ramal_idramal;
    }
    
    
}
