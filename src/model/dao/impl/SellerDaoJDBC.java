/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao.impl;

import java.util.List;
import model.dao.SellerDao;
import model.entities.Seller;

/**
 *
 * @author Saulo
 */
public class SellerDaoJDBC implements SellerDao {
    //Classe que implementa o SellerDao e será responsavel por instanciar nossos dados
    
    /*
    IMPORTANTE: CRIAMOS A DaoFactory para instanciar os DAOS, para não precisar-mos
    instanciar nas classes principais 
    EX: SellerDao sellerDao = new SellerDao(); ==> SellerDao sellerDao = DaoFactory.creatSellerDao();
    */
    
    @Override
    public void insert(Seller obj) {
       
    }

    @Override
    public void update(Seller obj) {
    
    }

    @Override
    public void deletById(Integer id) {
   
    }

    @Override
    public Seller findById(Integer id) {
    return null;
    }

    @Override
    public List<Seller> findAll() {
      
        return null;
    }
    
}
