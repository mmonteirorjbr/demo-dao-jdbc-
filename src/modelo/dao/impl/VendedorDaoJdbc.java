package modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
	}

	@Override
	public void update(Vendedor obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

}
