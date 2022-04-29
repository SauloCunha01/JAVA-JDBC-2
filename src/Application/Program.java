/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import model.entities.Department;

/**
 *
 * @author Saulo
 */
public class Program {
    public static void main(String[]args){
        Department obj = new Department(1,"Books");
        System.out.println(obj);
    }
}
