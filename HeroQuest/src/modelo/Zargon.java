package modelo;

import java.util.ArrayList;

public class Zargon extends Player {

	private static final long serialVersionUID = 2709851702481435202L;
	protected ArrayList<Monster> monsters;
	
	public Zargon(HeroQuest heroQuest) {
		this.monsters = new ArrayList<Monster>();
		this.monsters = this.createMonsters(heroQuest);
	}
	
	public Monster getMonster(int index) {
		return this.monsters.get(index);
	}
	
	public ArrayList<Monster> getMonsters() {
		return this.monsters;
	}
	
	public ArrayList<Monster> createMonsters(HeroQuest jogo) {
		Goblin goblin1 = new Goblin();
		goblin1.setID(1);
		jogo.creatureInPosition(goblin1, 11, 19);
		this.monsters.add(goblin1);
		
		Goblin goblin2 = new Goblin();
		goblin2.setID(2);
		jogo.creatureInPosition(goblin2, 11, 31);
		this.monsters.add(goblin2);
		
		Zombie zombie1 = new Zombie();
		zombie1.setID(3);
		jogo.creatureInPosition(zombie1, 13, 7);
		this.monsters.add(zombie1);
		
		Zombie zombie2 = new Zombie();
		zombie2.setID(4);
		jogo.creatureInPosition(zombie2, 16, 7);
		this.monsters.add(zombie2);
		
		Skeleton skeleton1 = new Skeleton();
		skeleton1.setID(5);
		jogo.creatureInPosition(skeleton1, 14, 8);
		this.monsters.add(skeleton1);
		
		Skeleton skeleton2 = new Skeleton();
		skeleton2.setID(6);
		jogo.creatureInPosition(skeleton2, 15, 8);
		this.monsters.add(skeleton2);
		
		Skeleton skeleton3 = new Skeleton();
		skeleton3.setID(7);
		jogo.creatureInPosition(skeleton3, 21, 35);
		this.monsters.add(skeleton3);
		
		Skeleton skeleton4 = new Skeleton();
		skeleton4.setID(8);
		jogo.creatureInPosition(skeleton4, 21, 37);
		this.monsters.add(skeleton4);
		
		Mummy mummy1 = new Mummy();
		mummy1.setID(9);
		jogo.creatureInPosition(mummy1, 21, 36);
		this.monsters.add(mummy1);
		
		Mummy mummy2 = new Mummy();
		mummy2.setID(10);
		jogo.creatureInPosition(mummy2, 21, 38);
		this.monsters.add(mummy2);
		
		Fimir fimir1 = new Fimir();
		fimir1.setID(11);
		jogo.creatureInPosition(fimir1, 19, 21);
		this.monsters.add(fimir1);
		
		Fimir fimir2 = new Fimir();
		fimir2.setID(12);
		jogo.creatureInPosition(fimir2, 21, 21);
		this.monsters.add(fimir2);
		
		Fimir fimir3 = new Fimir();
		fimir3.setID(13);
		jogo.creatureInPosition(fimir3, 20, 28);
		this.monsters.add(fimir3);
		
		Fimir fimir4 = new Fimir();
		fimir4.setID(14);
		jogo.creatureInPosition(fimir4, 22, 28);
		this.monsters.add(fimir4);
		
		ChaosWarrior chaos_Warrior1 = new ChaosWarrior();
		chaos_Warrior1.setID(15);
		jogo.creatureInPosition(chaos_Warrior1, 13, 38);
		this.monsters.add(chaos_Warrior1);
		
		ChaosWarrior chaos_Warrior2 = new ChaosWarrior();
		chaos_Warrior2.setID(16);
		jogo.creatureInPosition(chaos_Warrior2, 13, 45);
		this.monsters.add(chaos_Warrior2);
		
		ChaosWarrior chaos_Warrior3 = new ChaosWarrior();
		chaos_Warrior3.setID(17);
		jogo.creatureInPosition(chaos_Warrior3, 24, 46);
		this.monsters.add(chaos_Warrior3);
		
		ChaosWarrior chaos_Warrior4 = new ChaosWarrior();
		chaos_Warrior4.setID(18);
		jogo.creatureInPosition(chaos_Warrior4, 25, 46);
		this.monsters.add(chaos_Warrior4);
		
		PolarWarbear polar_Warbear = new PolarWarbear();
		polar_Warbear.setID(19);
		jogo.creatureInPosition(polar_Warbear, 8, 42);
		this.monsters.add(polar_Warbear);
		
		return this.monsters;
	}

	public boolean checkIfMonsterExist(int creatureID) {
		for (Creature monster : this.monsters) {
			if (monster.getID() == creatureID)
				return true;
		}
		return false;
	}
}