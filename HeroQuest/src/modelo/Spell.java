package modelo;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Spell implements Jogada {
	
	private static final long serialVersionUID = -5887762451618737398L;
	protected String name;
	protected Status deliveredStatus;
	protected int deliveredDamage;
	
	public Spell(String nome, Status status, int damage) {
		this.name = nome;
		this.deliveredStatus = status;
		this.deliveredDamage = damage;
	}

	public int getDamage() {
		return this.deliveredDamage;
	}

	public Status getStatus() {
		return this.deliveredStatus;
	}

	public String getNome() {
		return this.name;
	}

}