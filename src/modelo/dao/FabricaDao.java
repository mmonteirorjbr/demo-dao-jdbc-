package modelo.dao;

import db.DB;
import modelo.dao.impl.VendedorDaoJdbc;

public class FabricaDao {

	
	   // Essa classe vai ter oeracoes estaticas para referenciar o DAO
	   // nome original DAOFactory
	   // criar vendedorDAO retorna VendedorDAO
	
	
	   // esse metodo retorna retorna um tipo da interface  mas internamente vai instanciar uma implementacao
       //  isso e macete pra n a precisa expor a implementacao deixando somente a interface
	   // ai na aplicacao ele pode acrescentar uma instanciacao do VendedorDAO
	
	   public static   VendedorDao createVendedorDao() {
	       return new   VendedorDaoJdbc(DB.getConnection()) ;
	       
	         
	       
    }	   
}
