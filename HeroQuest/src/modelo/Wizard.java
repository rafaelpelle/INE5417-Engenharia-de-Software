package modelo;

import java.util.*;

public class Wizard extends PlayableCharacter {

	private static final long serialVersionUID = 7405881828653705414L;
	protected ArrayList<Spell> spells;

	public Wizard() {
		super(4, 6, 1, 2);
		this.spells = new ArrayList<Spell>();
	}

	public ArrayList<Spell> getSpells() {
		return this.spells;
	}
	
	public void createSpells() {
		Spell swift_Wind = new Spell("Swift Wind", Status.AGILITY_UP, 0);
		Spell tempest = new Spell("Tempest", Status.CURSED, 0);
		Spell ball_of_Flame = new Spell("Ball of Flame", null, 2);
		Spell courage = new Spell("Courage", Status.COURAGE, 0);
		Spell fire_of_Wrath = new Spell("Fire of Wrath", null, 1);
		Spell sleep = new Spell("Sleep", Status.SLEEPING, 0);
		Spell water_of_Healing = new Spell("Water of Healing", null, 4);
		this.spells.add(swift_Wind);
		this.spells.add(tempest);
		this.spells.add(ball_of_Flame);
		this.spells.add(courage);
		this.spells.add(fire_of_Wrath);
		this.spells.add(sleep);
		this.spells.add(water_of_Healing);
	}
	
	public void removeSpellFromBook(Spell magia) {
		this.spells.remove(magia);
	}
}