package modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Vendedor implements Serializable{

	
	
	private static final long serialVersionUID = 1L;
	// ao colocar o "implements Serializable" ele deu o aviso amarelo , cliquei nele e
	// e esacolhi o add serial ID alguam coisa, e a 1a opcao
	
	private Integer id;
	private String nome;
	private String email;
	private Date nascimento;
	private double salarioBase;
	
	// Cria uma variavel/campo Departamento do tipo departamento
	private Departamento departamento;
	
	//construtores
	
	public Vendedor() {
	}

	public Vendedor(Integer id, String nome, String email, Date nascimento, double salarioBase,
			Departamento departamento) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.nascimento = nascimento;
		this.salarioBase = salarioBase;
		this.departamento = departamento;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public double getSalarioBase() {
		return salarioBase;
	}

	public void setSalarioBase(double salarioBase) {
		this.salarioBase = salarioBase;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
    // ao usar o "generate hash cod and equals" ele marcou apenas o ID e desmarcou os outros campos. 
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
		Vendedor other = (Vendedor) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
} 
