/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao.impl;

import db.DB;
import db.DBException;
import java.util.List;
import model.dao.SellerDao;
import model.entities.Seller;
import java.sql.*;
import model.entities.Department;
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
      private Connection conn;
    public SellerDaoJDBC(Connection conn) {
       this.conn = conn;
    }

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
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
            "SELECT seller.*,department.Name as DepName " +
            "FROM seller INNER JOIN department " +
            "ON seller.DepartmentId = department.Id " +
            "WHERE seller.Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            //rs.next testa se veio algum resultado, rs = 0 inicialmente
            /* */
            if(rs.next()){
              Department dep = instantiateDepartment(rs);
              
              Seller obj = instantiateSeller(rs, dep);
              return obj;
            }
              return null;
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
      
    }

    @Override
    public List<Seller> findAll() {
      
        return null;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
               Department dep = new Department();
              dep.setId(rs.getInt("DepartmentId"));
              dep.setName(rs.getString("DepName"));  
              return dep;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
              Seller obj = new Seller();
              obj.setId(rs.getInt("Id"));
              obj.setName(rs.getString("Name"));
              obj.setEmail(rs.getString("Email"));
              obj.setBaseSalary(rs.getDouble("BaseSalary"));
              obj.setBirthDate(rs.getDate("BirthDate"));
              obj.setDepartment(dep); 
              return obj;
    }
    
}
