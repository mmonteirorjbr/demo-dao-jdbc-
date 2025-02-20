package modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import modelo.dao.VendedorDao;
import modelo.entidades.Departamento;
import modelo.entidades.Vendedor;

public class VendedorDaoJdbc implements  VendedorDao{
	
	   private  Connection conn;
	   
    
	   public  VendedorDaoJdbc(Connection conn) {
			 // o This.conn recebe o conn que chegou como parametrro
		   this.conn = conn;
	   }
	   
	@Override
	public void insert(Vendedor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					" INSERT INTO seller "
				+   " (Name, Email,BirthDate, BaseSalary, DepartmentID) "
				+   " VALUES "
				+   " (?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS); // Retorna a chave gerada com o ID do registro.
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getNascimento().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5,obj.getDepartamento().getId());

			int linhasRetornadas = st.executeUpdate();
			  
			if (linhasRetornadas >0 ) {
				ResultSet rs = st.getGeneratedKeys();
				if ( rs.next()) {
					int id = rs.getInt(1);
					// insere o ID gerado no obj para que fique populado com o ID dele.
					obj.setId(id);
				}
				// fecha p rs pgp aqui mesmo pq nao vai usar de novo, nao precisa esperar o finally
				DB.closeResultSet(rs);
			}
			else {
				 throw new  DbException("Erro ineperado! Nehuma linha retornada!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());			
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Vendedor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					" UPDATE seller "
				+   " SET Name =? , Email = ? ,BirthDate = ? , BaseSalary =? , DepartmentID = ?  "
				+   " WHERE Id = ?  ");
				
				
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getNascimento().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5,obj.getDepartamento().getId());
            st.setInt(6,obj.getId());   
			
            st.executeUpdate();
			  
		}	
		catch (SQLException e) {
			throw new DbException(e.getMessage());			
		}
		finally {
			DB.closeStatement(st);
		}
	
	}

	@Override
	public void deleteById(Integer id) {
		//incializa o statement sem valor
	 PreparedStatement st= null;	
	 try {
		 st= conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
		 st.setInt(1, id );
		 st.executeUpdate();
	 }
	 catch (SQLException e ) { 
		 throw new DbException(e.getMessage());
	 }
	}

	@Override
	public Vendedor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
	     // nao preciso criar uma conexao com o banco aqui porque o nosso DAO vai ter uma dependencia com a conexao
		// vou usar o bjeto conn criado la em cima
		try {
			//Inciia o prepareStament - st
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					+ " FROM seller INNER JOIN department" 
					+ "	 ON seller.departmentID = department.Id"
					+ " WHERE seller.Id = ?");
			// para fazer a pesquisa vai passar como parametro o Id que foi recebido na chamada
			// e em seguida executa a query
			st.setInt(1,  id);
			rs = st.executeQuery();
			// se o retonar false , retorna nulo, se de verdadeiro ai puxa os dados 
			if (rs.next()) {
				 //versao original antes de reutilizar a instanciacao no video 20. o codigo foi transferido
				//para o metodo instanciarDepartamento mais abaixo
				 //Aqui nós instanciamos o departamnto e setamos os valores dele
				/* Departamento dep = new Departamento();
				dep .setId(rs.getInt("DepartmentId")); // Puxa o dados usando o nome da coluna no banco
				dep.setNome(rs.getString("DepName"));
				*/ 
				
				Departamento dep = instanciarDepartamento(rs); 
				Vendedor obj = instanciarVendedor(rs,dep);
				return obj;
			}
			return null;
		}
		catch(SQLException e) {
			 throw new DbException(e.getMessage());
		}
		finally {
			 // nao fechou as conexoes porque o mesmo objeto DAO pode servir para mais de uma operacao
			// mas tem d efehcar os recursos de acesso ao banco pra esse item
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}	
	}

	private Vendedor instanciarVendedor(ResultSet rs, Departamento dep) throws SQLException {
		Vendedor obj = new Vendedor();
		obj.setId(rs.getInt("Id"));
		obj.setNome(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setSalarioBase(rs.getDouble("BaseSalary"));
		obj.setNascimento(rs.getDate("BirthDate"));
		
		// aqui ao inves de pegar o valor do campo estou pegando o objeto dep que e o registro 
		// da tabela department associada				
		obj.setDepartamento(dep);
		return obj;
		
		
	
	}

	private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
		 Departamento dep = new Departamento();
		dep .setId(rs.getInt("DepartmentId")); // Puxa o dados usando o nome da coluna no banco
		dep.setNome(rs.getString("DepName"));
	return dep;
	}

	@Override
	public List<Vendedor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
	     // nao preciso criar uma conexao com o banco aqui porque o nosso DAO vai ter uma dependencia com a conexao
		// vou usar o bjeto conn criado la em cima
		try {
			//Inciia o prepareStament - st
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					+ " FROM seller INNER JOIN department " 
					+ "	ON seller.departmentID = department.Id "
					+ " ORDER BY  Name");
			
			
			rs = st.executeQuery();
			
			//Declara a lista que vai receber os dados
			List<Vendedor> lista = new ArrayList<>();
			
           //para evitar a repeticao do departamento ele vai usar o map que é um mapa de chave  e valor 
			
			Map<Integer, Departamento> map = new HashMap<>();
			
			// se o retonar false , retorna nulo, se de verdadeiro ai puxa os dados
			// no vendedor foi um if porque ele retorna um registro. no caso do departamento pode retornar mais de 
			// um registro entao faz um while
			
			// 
			while  (rs.next()) {
				 //versao original antes de reutilizar a instanciacao no video 20. o codigo foi transferido
				//para o metodo instanciarDepartamento mais abaixo
				 //Aqui nós instanciamos o departamnto e setamos os valores dele
				/* Departamento dep = new Departamento();
				dep .setId(rs.getInt("DepartmentId")); // Puxa o dados usando o nome da coluna no banco
				dep.setNome(rs.getString("DepName"));
				*/ 
				
				
				// antes de instanciar vai verificar se o departamento ja existe no map
				// faz a pesquisa com o registro corrente da lista vendo se o departamento ja existe
				Departamento dep = map.get(rs.getInt("DepartmentId"));

				
				//se nao existe faz a instanciacao do departamento
				if (dep == null)  {
					dep = instanciarDepartamento(rs);
					// ai incluir o departamento no mapa
					map.put(rs.getInt("DepartmentId"), dep);
					
				}
				 
				Vendedor obj = instanciarVendedor(rs,dep);
				lista.add(obj);
			}
			return lista;
		}
		catch(SQLException e) {
			 throw new DbException(e.getMessage());
		}
		finally {
			 // nao fechou as conexoes porque o mesmo objeto DAO pode servir para mais de uma operacao
			// mas tem d efehcar os recursos de acesso ao banco pra esse item
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}	

	}

	@Override
	public List<Vendedor> findByDepartment(Departamento departamento) {
		PreparedStatement st = null;
		ResultSet rs = null;
	     // nao preciso criar uma conexao com o banco aqui porque o nosso DAO vai ter uma dependencia com a conexao
		// vou usar o bjeto conn criado la em cima
		try {
			//Inciia o prepareStament - st
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					+ " FROM seller INNER JOIN department " 
					+ "	ON seller.departmentID = department.Id "
					+ " WHERE DepartmentId = ?"
					+ " ORDER BY  Name");
			
			// para fazer a pesquisa vai passar como parametro o Id que foi recebido na chamada
			// e em seguida executa a query
			st.setInt(1,  departamento.getId());
			rs = st.executeQuery();
			
			//Declara a lista que vai receber os dados
			List<Vendedor> lista = new ArrayList<>();
			
            //para evitar a repeticao do departamento ele vai usar o map que é um mapa de chave  e valor 
			
			Map<Integer, Departamento> map = new HashMap<>();
			
			// se o retonar false , retorna nulo, se de verdadeiro ai puxa os dados
			// no vendedor foi um if porque ele retorna um registro. no caso do departamento pode retornar mais de 
			// um registro entao faz um while
			
			// 
			while  (rs.next()) {
				 //versao original antes de reutilizar a instanciacao no video 20. o codigo foi transferido
				//para o metodo instanciarDepartamento mais abaixo
				 //Aqui nós instanciamos o departamnto e setamos os valores dele
				/* Departamento dep = new Departamento();
				dep .setId(rs.getInt("DepartmentId")); // Puxa o dados usando o nome da coluna no banco
				dep.setNome(rs.getString("DepName"));
				*/ 
				
				
				// antes de instanciar vai verificar se o departamento ja existe no map
				// faz a pesquisa com o registro corrente da lista vendo se o departamento ja existe
				Departamento dep = map.get(rs.getInt("DepartmentId"));

				
				//se nao existe faz a instanciacao do departamento
				if (dep == null)  {
					dep = instanciarDepartamento(rs);
					// ai incluir o departamento no mapa
					map.put(rs.getInt("DepartmentId"), dep);
					
				}
				 
				Vendedor obj = instanciarVendedor(rs,dep);
				lista.add(obj);
			}
			return lista;
		}
		catch(SQLException e) {
			 throw new DbException(e.getMessage());
		}
		finally {
			 // nao fechou as conexoes porque o mesmo objeto DAO pode servir para mais de uma operacao
			// mas tem d efehcar os recursos de acesso ao banco pra esse item
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}	
		
	}

}
