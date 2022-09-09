/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dynamic;

import data.crudUnidades;
import entity.Unidades;
import entity.usuario;
import java.util.ArrayList;

/**
 *
 * @author esdra
 */
public class unidadesDy {
 public static ArrayList<Unidades> getAllUnidades(usuario currentUsser){
     crudUnidades crud = new crudUnidades();
     return crud.getAllUnidades(currentUsser);
 }   
}
