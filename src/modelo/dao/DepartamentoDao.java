package modelo.dao;

import java.util.List;

import modelo.entidades.Departamento;

public interface DepartamentoDao {
	
 // Vai fazer a inserção, update  e exclusao no banco de dados 	
    void insert(Departamento obj);
    void update(Departamento obj);
    void deleteById(Integer id);
   
    // a operacao de findbyId vai consultar no banco de dados um objeto com o ID fornecido e entao retorna  departamento 
    Departamento findById(Integer id);
    
    //para implementar um findAll vai usar uma lista 
    List<Departamento> findAll();
     
    
}
