package modelo;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Lance implements Jogada {

	private static final long serialVersionUID = -3032555320746456877L;
	protected TipoLance type;
	protected int value;
	protected Spell spell;
	protected Creature target;
	protected PlayableCharacter caster;
	protected int objectID;
	protected Position source;
	protected Position destination;
	protected Player player;
	protected Adventurer adventurer;
	protected Zargon zargon;
	

	public TipoLance getType() {
		return this.type;
	}

	public Creature getTarget() {
		return this.target;
	}

	public int getValue() {
		return this.value;
	}

	public Spell getSpell() {
		return this.spell;
	}

	public int getObjectID() {
		return this.objectID;
	}

	public Position getSource() {
		return this.source;
	}

	public Position getDestination() {
		return this.destination;
	}

	public PlayableCharacter getCaster() {
		return this.caster;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setType(TipoLance type) {
		this.type = type;
	}

	public void setObjectID(int id) {
		this.objectID = id;
	}

	public void setTarget(Creature target) {
		this.target = target;
	}

	public void setSource(Position source) {
		this.source = source;
	}

	public void setDestination(Position destination) {
		this.destination = destination;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setSpell(Spell spell) {
		this.spell = spell;
	}

	public void setCaster(PlayableCharacter caster) {
		this.caster = caster;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setAdventurer(Adventurer player) {
		this.adventurer = player;
	}
	
	public void setZargon(Zargon player) {
		this.zargon = player;
	}

	public Adventurer getAdventurer() {
		return this.adventurer;
	}
	
	public Zargon getZargon() {
		return this.zargon;
	}
}