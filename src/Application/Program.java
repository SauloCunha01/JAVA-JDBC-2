/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import java.util.Date;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.SellerDao;
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
                System.out.println("___________________Teste1_____________________");
                SellerDao sellerDao = DaoFactory.createSellerDao();
                Seller seller2 = sellerDao.findById(3);
                System.out.println(seller2);
                System.out.println("___________________Teste2_____________________");
                Department department = new Department(2, null);
                List<Seller> list = sellerDao.findByDepartment(department);
                for(Seller obj2 : list){
                    System.out.println(obj2);
                }
                
                System.out.println("___________________Teste3_____________________");
                list = sellerDao.findAll();
                for(Seller obj2 : list){
                    System.out.println(obj2);
                }
                System.out.println("___________________Teste4_____________________");
                Seller newSeller = new Seller(null, "Greg", "greg@hotmail.com", new Date(), 4000.0, department);
                sellerDao.insert(newSeller);
                System.out.println("Inserido! novo Id = "+newSeller.getId());
    }
}
