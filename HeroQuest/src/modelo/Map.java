/*
           .--.              .--.
          : (\ ". _......_ ." /) :
           '.    `        `    .'
            /'   _        _   `\
           /     0}      {0     \
          |       /      \       |
          |     /'        `\     |
           \   | .  .==.  . |   /
            '._ \.' \__/ './ _.'
            /  ``'._-''-_.'``  \
                    `--`
                    
 	Essa classe est� sendo observada pelo Polar Warbear
 */
package modelo;

import modelo.Corridor;
import modelo.Door;
import modelo.Position;
import modelo.Room;
import modelo.Wall;

public class Map {
	
	protected Position[][] positions; 

	protected int numberOfRows;
	protected int numberOfCollumns;
	
	
	public Map(HeroQuest jogo) {
		this.numberOfRows = 27;
		this.numberOfCollumns = 50;
		positions = new Position[numberOfRows][numberOfCollumns];
		generateCleanMap();
		generateCorridors();
		generateRooms();
		generateWalls();		
		generateDoors(jogo);
		generateTraps();
		generateTreasures();
	}
	
	public Position getPosition(int row, int collumn) {
		return positions[row][collumn]; 
	}
	
	public void generateCleanMap() {
		System.out.println("Debug: initializing map");
		for(int i = 0; i < numberOfRows; i++)
			for(int j = 0; j < numberOfCollumns; j++) {
				positions[i][j] = new Position(i,j);
			}
	}
	
	public void generateWalls() {
		System.out.println("Debug: initializing walls");
		for (int i = 3; i < 24; i++) { //Certo
			positions[i][3] = new Wall(i,3); // 1
		}
		for (int i = 3; i < 47; i++) { //Certo
			positions[23][i] = new Wall(23,i); // 2
		}
		for (int i = 3; i < 24; i++) { //Certo, certo
			positions[i][46] = new Wall(i,46); //3
			positions[3][i] = new Wall (3,i); // 7
		}
		for (int i = 26; i < 47; i++) { //Certo, certo
			positions[3][i] = new Wall(3,i); //4
			positions[12][i] = new Wall(12,i); // 12
		}
		for (int i = 3; i < 47; i++) { //Certo
			positions[18][i] = new Wall(18,i); //6
		}
		for (int i = 3; i < 19; i++) { //Certo, certo, certo, certo
			positions[i][23] = new Wall(i, 23);
			positions[i][26] = new Wall(i,26); // 5
			positions[i][15] = new Wall(i,15); // 8
			positions[i][37] = new Wall(i,37); //13
		}
		for (int i = 3; i < 24; i++) { //Certo
			positions[12][i] = new Wall(12,i); // 9
		}
		for (int i = 18; i < 24; i++) { //Certo, certo
			positions[i][18] = new Wall(i,18); //10
			positions[i][31] = new Wall(i,31); // 11
		}
		for (int i = 0; i < 50; i++) { //Certo, certo
			positions[0][i] = new Wall(0,i); // 14
			positions[26][i] = new Wall(26,i); // 16
		}
		for (int i = 0; i < 27; i++) { //Certo, certo
			positions[i][0] = new Wall(i,0); // 17
			positions[i][49] = new Wall(i,49); // 18
		}
		for (int i = 1; i < 3; i++) { //Certo, certo
			positions[i][15] = new Wall(i,15);
			positions[i][37] = new Wall(i,37);
		}
		for (int i = 24; i < 26; i++) { //Certo
			positions[3][i] = new Wall(3,i);
		}
		System.out.println("Debug: Walls initialized");
	}

	public void generateRooms() {
		System.out.println("Debug: Initializing rooms");
		for (int i = 4; i < 12; i++) {
			for (int j = 4; j < 15; j++) {
				positions[i][j] = new Room(i,j);
			}
			for (int j = 16; j < 23; j++) {
				positions[i][j] = new Room(i,j);
			}
			for (int j = 27; j < 37; j++) {
				positions[i][j] = new Room(i,j);
			}
			for (int j = 38; j < 46; j++) {
				positions[i][j] = new Room(i,j);
			}
		}
		for (int i = 13; i < 18; i++) {
			for(int j = 4; j < 15; j++) {
				positions[i][j] = new Room(i,j);
			}
			for (int j = 16; j < 23; j++) {
				positions[i][j] = new Room(i,j);
			}
			for (int j = 27; j < 37; j++) {
				positions[i][j] = new Room(i,j);
			}
			for (int j = 38; j < 46; j++) {
				positions[i][j] = new Room(i,j);
			}
		}
		for (int i = 19; i < 23; i++) {
			for (int j = 4; j < 18; j++) {
				positions[i][j] = new Room(i,j);
			}
			for (int j = 19; j < 31; j++) {
				positions[i][j] = new Room(i,j);
			}
			for (int j = 32; j < 46; j++) {
				positions[i][j] = new Room(i,j);
			}
		}
		System.out.println("Debug: rooms initialized");
	}

	public void generateCorridors() {
		System.out.println("Debug: initializing corridors");
		for (int i = 1; i < 26; i++) {
			for (int j = 1; j < 3; j++) {
				positions[i][j] = new Corridor(i,j);
			}
			for (int j = 47; j < 49; j++) {
				positions[i][j] = new Corridor(i,j);
			}
		}
		for (int i = 24; i < 26; i++) {
			for(int j = 3; j < 47; j++) {
				positions[i][j] = new Corridor(i,j);
			}
		}
		for (int i = 1; i < 3; i++) {
			for (int j = 3; j < 47; j++) {
				positions[i][j] = new Corridor(i,j);
			}
		}
		for (int i = 4; i < 18; i++) {
			for(int j = 24; j < 26; j++ ) {
				positions[i][j] = new Corridor(i,j);
			}
		}
		System.out.println("Debug: corridors initialized");
	}
	
	public void generateDoors(HeroQuest jogo) {
		positions[4][3] = new Door(4,3,43);
		positions[23][4] = new Door(23,4,234);
		positions[3][19] = new Door(3,19,319);
		positions[15][15] = new Door(15,15,1515);
		positions[12][19] = new Door(12,19,1219);
		positions[19][18] = new Door(19,18,1918);
		positions[3][30] = new Door(3,30,330);
		positions[6][46] = new Door(6,46,646); // porta secreta
		positions[12][31] = new Door(12,31,1231);
		positions[12][42] = new Door(12,42,1242);
		positions[17][37] = new Door(17,37,1737);
		positions[18][38] = new Door(18,38,1838);
		positions[22][31] = new Door(22,31,2231);
		jogo.doors.add((Door)positions[4][3]);
		jogo.doors.add((Door)positions[23][4]);
		jogo.doors.add((Door)positions[3][19]);
		jogo.doors.add((Door)positions[15][15]);
		jogo.doors.add((Door)positions[12][19]);
		jogo.doors.add((Door)positions[19][18]);
		jogo.doors.add((Door)positions[3][30]);
		jogo.doors.add((Door)positions[6][46]);
		jogo.doors.add((Door)positions[12][31]);
		jogo.doors.add((Door)positions[12][42]);
		jogo.doors.add((Door)positions[17][37]);
		jogo.doors.add((Door)positions[18][38]);
		jogo.doors.add((Door)positions[22][31]);
		
	}

	public void generateTraps() {
		for (int i = 27; i < 37; i++) { //Certo
			positions[7][i].setTrap(new Pit());
		}
		for (int i = 19; i < 23; i++) { //Certo
			positions[i][24].setTrap(new Spear());
		}
		for (int i = 16; i < 23; i+=2) { //Certo
			positions[7][i].setTrap(new FallingRock());
		}
		for (int i = 17; i < 22; i+=2) { //Certo
			positions[8][i].setTrap(new FallingRock());
		}
		positions[15][16].setTrap(new Spear());
		positions[17][36].setTrap(new Spear());
		positions[24][29].setTrap(new FallingRock());
		positions[25][29].setTrap(new FallingRock());
	}
	
	public void generateTreasures() {
		for (int i = 38; i < 45; i+=2) {
			positions[4][i].setTreasure(new Treasure(50));
		}
	}
	
	public void atualizarPosicao(Position novaPosicao, int linha, int coluna) {
		this.positions[linha][coluna] = novaPosicao;
	}
}