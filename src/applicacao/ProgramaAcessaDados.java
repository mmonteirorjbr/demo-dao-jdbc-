package applicacao;

import modelo.dao.FabricaDao;
import modelo.dao.VendedorDao;
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
	   
	   Vendedor vendedor = vendedorDao.findById(3);
	   
	   System.out.println(vendedor);    
	    // parei no video 19 na hroa de fazer o commit 17:00mins
	   // limpar a branch no githubb e comecar de novo   
	   
	   
	}
    
	
}
 