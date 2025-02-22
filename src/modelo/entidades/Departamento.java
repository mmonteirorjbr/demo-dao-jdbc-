package modelo.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Departamento  implements Serializable {

	// Ele da o aviso em amarelo e ai vc escoilhe pra incluir o default version para resolver
	private static final long serialVersionUID = 1L;
	
	// Para fazer com que o nosso objeto seja gravado em arquivo e tambem trafegado em rede(Nao entendi bem 
	   // o que isso significa) precisa do objeto serialize
	
	   private Integer id;
	   private String nome;
	   
	   public Departamento() {
		   
	   }

	public Departamento(Integer id, String nome) {
	
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Departamento [id=" + id + ", nome=" + nome + "]";
	}
	   
	   
}
