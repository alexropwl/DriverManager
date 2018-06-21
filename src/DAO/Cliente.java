package DAO;

public class Cliente {

	private int cpf;
	private String nome;
	private String email;
	
	public Cliente(int cpf, String nome, String email) {
	super();

	this.cpf = cpf;
	this.nome = nome;
	this.email = email;
		
		
		
	}
	
	@Override
	public  String toString() {
		
		return "Cliente [Cpf="+cpf+", Nome= '"+nome+"', Email = '"+email+"']";
		
		
	}
	
	public int getCpf() {
		return cpf;
	}
	public void setCpf(int cpf) {
		this.cpf = cpf;
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
	
	





}
