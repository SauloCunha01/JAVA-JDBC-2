/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import java.util.Date;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author Saulo
 */
public class Program {
    public static void main(String[]args){
        Department obj = new Department(1,"Books");
        System.out.println(obj);
        Seller seller = new Seller(21,"bob","bob@gmail.com", new Date(), 3000.0,obj );
        System.out.println(seller);
    }
}
