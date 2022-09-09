/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dynamic;

import data.crudRamal;
import entity.ramal;
import entity.usuario;
import java.util.ArrayList;

/**
 *
 * @author esdra
 */
public class ramalDy {
    public static ArrayList<ramal> getAllRamales(usuario currentUsser){
        return crudRamal.getAllRamales(currentUsser);
    }
}
