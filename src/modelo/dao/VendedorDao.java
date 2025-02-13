package modelo.dao;

import java.util.List;

import modelo.entidades.Vendedor;

public interface VendedorDao {
  
	
	// Vai fazer a inserção, update  e exclusao no banco de dados 	
    void insert(Vendedor obj);
    void update(Vendedor obj);
    void deleteById(Integer id);   
   
    // a operacao de findbyId vai consultar no banco de dados um objeto com o ID fornecido e entao retorna  departamento 
    Vendedor findById(Integer id);
    
    //para implementar um findAll vai usar uma lista 
    List<Vendedor> findAll();
    
   
}
