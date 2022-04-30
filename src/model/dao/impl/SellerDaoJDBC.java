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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "INSERT INTO seller " +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
             st.setString(2, obj.getEmail());
             st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
             st.setDouble(4, obj.getBaseSalary());
             st.setInt(5, obj.getDepartment().getId());
          int rowsAffected =  st.executeUpdate();
          if(rowsAffected > 0){
              ResultSet rs = st.getGeneratedKeys();
              if(rs.next()){
                  int id  = rs.getInt(1);
                  obj.setId(id);
              }
              DB.closeResultSet(rs);
          }else{
              throw  new DBException("Erro! nenhuma linha afetada");
          }
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }finally{
            DB.closeStatement(st);
           
        }
    }

    @Override
    public void update(Seller obj) {
   PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                "UPDATE seller " +
                "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                "WHERE Id = ?");
             st.setString(1, obj.getName());
             st.setString(2, obj.getEmail());
             st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
             st.setDouble(4, obj.getBaseSalary());
             st.setInt(5, obj.getDepartment().getId());
             st.setInt(6, obj.getId());
             
             st.executeUpdate();
          
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }finally{
            DB.closeStatement(st);
        }
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
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                "SELECT seller.*,department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "ORDER BY Name");
           
            
            rs = st.executeQuery();
            /*
            apenas foi mudada a QUERY
             */
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while(rs.next()){
              Department dep = map.get(rs.getInt("DepartmentId"));
              
              if(dep == null){
                  dep = instantiateDepartment(rs);
                  map.put(rs.getInt("DepartmentId"), dep);
              }
              Seller obj = instantiateSeller(rs, dep);
              list.add(obj);
            }
              return list;
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
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

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
            "SELECT seller.*,department.Name as DepName " +
            "FROM seller INNER JOIN department " +
            "ON seller.DepartmentId = department.Id " +
            "WHERE DepartmentId = ? " +
            "ORDER BY Name");
            st.setInt(1, department.getId());
            rs = st.executeQuery();
            /*
            O resultado pode ser mais de um, então utilizamos While
            então criaremos uma lista de resultados
             */
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while(rs.next()){
              Department dep = map.get(rs.getInt("DepartmentId"));
              
              if(dep == null){
                  dep = instantiateDepartment(rs);
                  map.put(rs.getInt("DepartmentId"), dep);
              }
              Seller obj = instantiateSeller(rs, dep);
              list.add(obj);
            }
              return list;
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    
    }
    
}
