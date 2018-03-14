package modelo;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Position implements Jogada {

	private static final long serialVersionUID = -2914404127760458829L;
	protected Trap trap;
	protected Creature creature;
	protected int row;
	protected int column;
	protected Treasure treasure;
	
	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}

	
	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}

	public void removeCreature() {
		this.creature = null;
	}

	public void setCreature(Creature criatura) {
		this.creature = criatura;
	}

	public void setTrap(Trap trap) {
		this.trap = trap;
	}
	
	public Trap getTrap() {
		return this.trap;
	}

	public Creature getCreature() {
		return this.creature;
	}

	public void makeTrapVisible() {
		this.trap.makeTrapVisible();
	}

	public Treasure getTreasure() {
		return this.treasure;
	}


	public void setTreasure(Treasure treasure) {
		this.treasure = treasure;
	}

}