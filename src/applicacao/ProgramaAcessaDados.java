package applicacao;

import java.util.List;

import modelo.dao.FabricaDao;
import modelo.dao.VendedorDao;
import modelo.entidades.Departamento;
import modelo.entidades.Vendedor;

public class ProgramaAcessaDados {

	public static void main(String[] args) {
	// Os itens abaixo foram retirados depois dos testes, aqui no comeco eu incluia u mn registro novo . 	
	/*	
	   Departamento obj = new Departamento(1, "Livros");
	   
	   // a data de nasciment ele pegou a data do dia pra nao ter de ficar criando data
	   Vendedor vendedor = new Vendedor(21,"Beto", "beto@gmail.com", new Date(), 3000.0, obj);
	  */
		
	   VendedorDao vendedorDao = FabricaDao.createVendedorDao();
	   
	   System.out.println("=== TESTE 1 : vendedor findbyId ====");
	   Vendedor vendedor = vendedorDao.findById(3);	   
	   System.out.println(vendedor);    
	   
	   System.out.println("=== TESTE 2 : vendedor findbyDepartment ====");
	   //faz a chamada do departamento para pegar o codigo do departamento 
	   Departamento departamento = new Departamento(2,null); 
       List<Vendedor> lista =  vendedorDao.findByDepartment(departamento);   
       for (Vendedor obj : lista) {
    	   System.out.println(obj);    
    	      
       }
	   	
       System.out.println("=== TESTE 3 : vendedor findAll ====");
       // aqui nao declara a variavel lista porque ja foi declarada mais acima e a esta usando de novo
	   lista =  vendedorDao.findAll();   
       for (Vendedor obj : lista) {
    	   System.out.println(obj);    
    	      
       }
	   
	}
    
	
}
 