package modelo;

import java.util.Random;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Creature implements Jogada, Comparable<Creature> {

	private static final long serialVersionUID = -2186498705939032580L;
	protected boolean turn;
	protected int body;
	protected int mind;
	protected int movement;
	protected Status status;
	protected int attackDiceAmount;
	protected int defenceDiceAmount;
	protected Position currentPosition;
	protected int id;
	
	public Creature(int body, int mind, int atk, int def) {
		this.turn = false;
		this.body = body;
		this.mind = mind;
		this.status = Status.NEUTRAL;
		this.attackDiceAmount = atk;
		this.defenceDiceAmount = def;
	}

	public int getMovement() {
		return this.movement;
	}

	public Position getCurrentPosition() {
		return this.currentPosition;
	}

	public void decreaseBody(int value) {
		this.body -= value;
	}

	public void increaseBody(int value) {
		this.body += value;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getMind() {
		return this.mind;
	}

	public int getBody() {
		return this.body;
	}

	public int getID() {
		return this.id;
	}

	public int getAttackDiceAmount() {
		return this.attackDiceAmount;
	}

	public int getDefenceDiceAmount() {
		return this.defenceDiceAmount;
	}

	public void setAttackDiceAmount(int diceAmount) {
		this.attackDiceAmount = diceAmount;
	}

	public void setDefenceDiceAmount(int diceAmount) {
		this.defenceDiceAmount = diceAmount;
	}
	
	public void setMovement() {
		Random rand = new Random();
		int movement = rand.nextInt(12 - 2 + 1) + 2;
		this.movement = movement;
	}

	public void setCurrentPosition(Position novaPosicao) {
		this.currentPosition = novaPosicao;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void decreaseMovement() {
		this.movement -= 1;
	}

	@Override
	public int compareTo(Creature arg0) {
		if (this.getID() < arg0.getID()) {
			return -1;
		}
		if (this.getID() > arg0.getID()) {
			return 1;
		}
		return 0;
	}

	public void usarMagia(Spell magia) {
		this.mind--;
		this.removeSpellFromBook(magia);
	}

	public void removeSpellFromBook(Spell magia) {
	}
}