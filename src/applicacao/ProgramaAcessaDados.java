package applicacao;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import modelo.dao.FabricaDao;
import modelo.dao.VendedorDao;
import modelo.entidades.Departamento;
import modelo.entidades.Vendedor;

public class ProgramaAcessaDados {

	public static void main(String[] args) {
	// Os itens abaixo foram retirados depois dos testes, aqui no comeco eu incluia u mn registro novo .
		
		Scanner sc = new Scanner(System.in); 
		
	  System.out.println("=== TESTE 0 : vendedor findbyId ====");
	   
	   Departamento objteste = new Departamento(1, "Livros");
	   
	   // a data de nasciment ele pegou a data do dia pra nao ter de ficar criando data
	   Vendedor vendteste = new Vendedor(21,"Beto", "beto@gmail.com", new Date(), 3000.0, objteste);
	   System.out.println(vendteste);    
	   
		
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
       // aqui nao declarto aqui toa a variavel lista porque ja foi declarada mais acima e a esta usando de novo
	   lista =  vendedorDao.findAll();   
       for (Vendedor obj : lista) {
    	   System.out.println(obj);    
    	      
       }
	   
       System.out.println("\n=== TESTE 4 : vendedor insert ====");
       // insere um registro, usa a variavel  departamento ja declarada 
       Vendedor novoVendedor = new Vendedor(null,"Greg", "greg@gmail.com", new Date()  ,4000.0, departamento );
       vendedorDao.insert(novoVendedor);
       System.out.println("Inserido! Novo Id = " +novoVendedor.getId());
       
       
       
       System.out.println("\n=== TESTE 5 : vendedor update  ====");
       //vai no vendedor 1 e troca o nome dele 
       vendedor = vendedorDao.findById(1);
       vendedor.setNome("Martha Waine");
       vendedorDao.update(vendedor);
       System.out.println("Update completo");
       
       System.out.println("\n=== TESTE 6 : vendedor delete ====");
       System.out.println("Digite o Id para o teste de exclusao");
       int id = sc.nextInt();
       vendedorDao.deleteById(id);
       System.out.println("Delete completed");
       
       sc.close();
       
       
       
	}
    
	
}
 