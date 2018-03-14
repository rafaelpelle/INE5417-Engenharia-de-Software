package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JOptionPane;

import exceptions.PositionNotEmptyException;

public class HeroQuest {

	protected Map map;
	protected AtorJogador atorJogador;
	protected AtorClientServer atorClienteServidor;
	protected Player localPlayer;
	protected Adventurer localAdventurer;
	protected Zargon localZargon;
	protected ArrayList<Player> players;
	protected ArrayList<Creature> creatureQueue;
	protected ArrayList<Door> doors;
	protected boolean zargonAvailable;
	protected boolean barbarianAvailable;
	protected boolean wizardAvailable;
	protected boolean elfAvailable;
	protected boolean dwarfAvailable;
	protected boolean connected;
	protected boolean inProgress;
	
// ---------------------------------------------------------------------------------------------------- //	
// ----------------------------------------- CONSTRUCTORS --------------------------------------------- //
	
	public HeroQuest(AtorJogador ator) {
		this.map = null;
		this.atorJogador = ator;
		this.atorClienteServidor = new AtorClientServer(this);
		this.localPlayer = null;
		this.localAdventurer = null;
		this.localZargon = null;
		this.players = new ArrayList<Player>();
		this.creatureQueue = new ArrayList<Creature>();
		this.doors = new ArrayList<Door>();
		this.zargonAvailable = true;
		this.barbarianAvailable = true;
		this.wizardAvailable = true;
		this.elfAvailable = true;
		this.dwarfAvailable = true;
		this.connected = false;
		this.inProgress = false;
	}

// ----------------------------------------- CONSTRUCTORS --------------------------------------------- //
// ---------------------------------------------------------------------------------------------------- //
// -------------------------------------- GETTERS AND SETTERS ----------------------------------------- //
	private Door getDoorByID(int doorID) {
		for(int i = 0; i < this.doors.size(); i++) {
			if (this.doors.get(i).getID() == doorID)
				return this.doors.get(i);
		}
		return null;
	}
	
	public Creature getCreatureOfTheTurn() {
		return this.creatureQueue.get(0);
	}
	
	private Position getNewPosition(Directions direction, int row, int column) throws PositionNotEmptyException {
		Position newPosition = null;
		switch(direction) {
			case DOWN:
				newPosition = this.map.getPosition(row+1, column);
				break;
			case UP:
				newPosition = this.map.getPosition(row-1, column);
				break;
			case LEFT:
				newPosition = this.map.getPosition(row, column-1);
				break;
			case RIGHT:
				newPosition = this.map.getPosition(row, column+1);
				break;
		}
		if (newPosition.getCreature() != null || newPosition instanceof Wall || (newPosition instanceof Door && !((Door) newPosition).getPortaEstaAberta())) {
			throw new PositionNotEmptyException();
		} else {
			return newPosition;
		}
	}
	
	public ArrayList<Creature> getCreatureQueue() {
		return this.creatureQueue;
	}
	
	public void setZargonAvailable(boolean b) {
		this.zargonAvailable = b;
	}

	public void setBarbarianAvailable(boolean b) {
		this.barbarianAvailable = b;
	}

	public void setWizardAvailable(boolean b) {
		this.wizardAvailable = b;		
	}
	
	public void setElfAvailable(boolean b) {
		this.elfAvailable = b;
	}

	public void setDwarfAvailable(boolean b) {
		this.dwarfAvailable = b;
	}
	
	public boolean getZargonAvailable() {
		return this.zargonAvailable;
	}

	public boolean getBarbarianAvailable() {
		return this.barbarianAvailable;
	}

	public boolean getWizardAvailable() {
		return this.wizardAvailable;
	}

	public boolean getElfAvailable() {
		return this.elfAvailable;
	}

	public boolean getDwarfAvailable() {
		return this.dwarfAvailable;
	}
	
	public void insertCreatureIntoQueue(Creature creature) {
		int index = this.creatureQueue.size();
		this.creatureQueue.add(index, creature);
	}
	
	public Creature removeCreatureFromQueue() {
		return this.creatureQueue.remove(0);
	}

	public void insertPlayerIntoQueue(Player player) {
		int index = this.players.size();
		this.players.add(index, player);
	}
	
	public Player removePlayerFromQueue() {
		return this.players.remove(0);
	}
	
	public boolean getConnected() {
		return this.connected;
	}

	public void setConnected(boolean value) {
		this.connected = value;
	}

	public boolean getInProgress() {
		return this.inProgress;
	}
	
	public void setInProgress(boolean value) {
		this.inProgress = value;
	}
	
	public Creature getCreatureFromQueue(int creatureID) {
		Creature creature = null;
		for(int i=0; i<this.creatureQueue.size(); i++) {
			if (this.creatureQueue.get(i).getID() == creatureID) {
				creature = this.creatureQueue.get(i);
			}
		}
		return creature;
	}
	
	public Player getPlayerOfTheTurn() {
		return this.players.get(0);
	}
	
	public Player getLocalPlayer() {
		return this.localPlayer;
	}

	public void setLocalPlayer(Player localPlayer) {
		this.localPlayer = localPlayer;
	}

	public Creature getLastCreatureFromQueue() {
		int lastIndex = this.creatureQueue.size()-1;
		return this.creatureQueue.get(lastIndex);
	}

	public Position getPositionOfTheMap(int i, int j) {
		return this.map.getPosition(i, j);
	}
// -------------------------------------- GETTERS AND SETTERS ----------------------------------------- //
// ---------------------------------------------------------------------------------------------------- //
// --------------------------------------- AUXILIARY METHODS ------------------------------------------ //
	
	public void closeGame() {
		System.exit(0);
	}

	public void startMatch(int numberOfPlayers) {
		this.atorClienteServidor.iniciarPartida(numberOfPlayers);
	}
	
	public void creatureInPosition(Creature creature, int row, int column) {
		this.map.positions[row][column].setCreature(creature);
		creature.setCurrentPosition(this.getPositionOfTheMap(row, column));
	}
	
	
	// TODO criaturas mortas podem ficar na creatureQueue, serão removidas apenas da classe Zargon
	private boolean checkIfPlayerOfTheTurn() {
		int localCreatureID, creatureOfTheTurnID;
		creatureOfTheTurnID = this.getCreatureOfTheTurn().getID();
		if (this.localAdventurer != null) {
			localCreatureID = this.localAdventurer.getPlayableCharacter().getID();
			return localCreatureID == creatureOfTheTurnID;
		} else {
			return this.localZargon.checkIfMonsterExist(creatureOfTheTurnID);
		}
	}
	
	
	// TODO - só é usado no Ataque - Ataque deve usar getPossiveisAlvos()
	private boolean verificaSeDistanciaPossivel(Position pos1, Position pos2) {
		int linhaAtacante, colunaAtacante, linhaAlvo, colunaAlvo;
		linhaAtacante = pos1.getRow();
		colunaAtacante = pos1.getColumn();
		linhaAlvo = pos2.getRow();
		colunaAlvo = pos2.getColumn();
		return (linhaAtacante == linhaAlvo && colunaAtacante == colunaAlvo -1) ||
				(linhaAtacante == linhaAlvo && colunaAtacante == colunaAlvo +1) ||
				(colunaAtacante == colunaAlvo && linhaAtacante == linhaAlvo -1) ||
				(colunaAtacante == colunaAlvo && linhaAtacante == linhaAlvo +1);
	}
	 
	private boolean verificaSePertoDaPorta(PlayableCharacter character, Door porta) {
		int linhaAventureiro, colunaAventureiro, linhaPorta, colunaPorta;
		linhaAventureiro = character.getCurrentPosition().getRow();
		colunaAventureiro = character.getCurrentPosition().getColumn();
		linhaPorta = porta.getRow();
		colunaPorta = porta.getColumn();
		return (linhaAventureiro == linhaPorta && colunaAventureiro == colunaPorta -1) ||
			   (linhaAventureiro == linhaPorta && colunaAventureiro == colunaPorta +1) ||
			   (colunaAventureiro == colunaPorta && linhaAventureiro == linhaPorta -1) ||
			   (colunaAventureiro == colunaPorta && linhaAventureiro == linhaPorta +1);
	}

	private ArrayList<Creature> getPossiveisAlvos(int area, Position pos) {
		ArrayList<Creature> possiveisAlvos = new ArrayList<Creature>();
		int linha = pos.getRow();
		int coluna = pos.getColumn();
		for (int i=linha-area; i<=linha+area; i++) {
			for (int j=coluna-area; j<=coluna+area; j++) {
				if(i >= 0 && j >= 0) {
					Position posicao = this.map.getPosition(i, j);
					if (posicao.getCreature() != null) {
						possiveisAlvos.add(posicao.getCreature());
					}
				}
			}
		}
		return possiveisAlvos;
	}

	private int calcularDanoDoAtaque(Creature atacante, Creature alvo) {
		int atkDiceAmount, defDiceAmount, damage, defence, probabilidade, result;
		boolean hit;
		damage = 0;
		defence = 0;
		atkDiceAmount = atacante.getAttackDiceAmount();
		defDiceAmount = alvo.getDefenceDiceAmount();
		probabilidade = 2;
		for (int i = 1; i <= atkDiceAmount; i++) {
			hit = new Random().nextInt(probabilidade) == 0;
			if (hit) {
				damage++;
			}
		}
		if (alvo instanceof Monster) {
			probabilidade = 6;
		} else {
			probabilidade = 3;
		}
		for (int i = 1; i <= defDiceAmount; i++) {
			hit = new Random().nextInt(probabilidade) == 0;
			if (hit) {
				defence++;
			}
		}
		result = damage - defence;
		if (result >= 0) {
			return result;
		} else {
			return 0;
		}
	}

	private boolean calcularSucessoDaMagia(Creature caster, Creature target, Spell spell) {
		int dano, probabilidade;
		boolean success, defendeu;
		dano = spell.getDamage();
		success = true;
		if (dano < 0) {
			probabilidade = 3;
			defendeu = new Random().nextInt(probabilidade) == 0;
			if (defendeu) {
				success = false;
			}
		}
		return success;
	}

	private void enviarLance(Lance lance) {
		this.atorClienteServidor.enviarJogada(lance);
	}

	private void sortCreatureQueueByID() {
		Collections.sort(this.creatureQueue);
	}

	private void killCreature(int creatureID) {
		for (int i=0; i<this.creatureQueue.size(); i++) {
			Creature criatura = this.creatureQueue.get(i);
			if (criatura.getID() == creatureID) {
				Position pos = criatura.getCurrentPosition();
				pos.removeCreature();
				this.map.atualizarPosicao(pos, pos.getRow(), pos.getColumn());
				this.creatureQueue.remove(criatura);
				this.atorJogador.atualizarInterfaceGrafica();
			}
		}
	}


	private boolean verificarCondicoesDeVitoria() {
		boolean vitoria = false;
		for(int i=0; i < this.creatureQueue.size(); i++) {
			if (this.creatureQueue.get(i).getID() > 19 && this.creatureQueue.get(i).getID() < 24) {
				Position pos = this.creatureQueue.get(i).getCurrentPosition();
				int linha = pos.getRow();
				int coluna = pos.getColumn();
				if (linha==24 && coluna==24 || linha==24 && coluna==25 || linha==25 && coluna==24 || linha==25 && coluna==25) {
					vitoria = true;
				}
			}
		}
		return vitoria;
	}

	private void encerramentoDaPartida() {
		boolean aventureirosVivos = this.verificarSeJogadoresVivos();
		if (aventureirosVivos) {
			boolean condicoesCumpridas = this.verificarCondicoesDeVitoria();
			if (condicoesCumpridas) {
				this.atorJogador.anunciarVitoriaDosJogadores();
			}
		} else {
			this.atorJogador.anunciarVitoriaDoZargon();
			this.closeGame();
		}
	}

	private boolean verificarSeJogadoresVivos() {
		int qtdCriaturas = this.creatureQueue.size();
		for(int i=0; i<qtdCriaturas; i++) {
			Creature criatura = this.creatureQueue.get(i);
			if (criatura instanceof Barbarian || criatura instanceof Wizard || criatura instanceof Elf || criatura instanceof Dwarf) {
				return true;
			}
		}
		return false;
	}

	private Player criarJogador(String idJogador) {
		return new Player(idJogador);
	}

// --------------------------------------- AUXILIARY METHODS ------------------------------------------ //	
// ---------------------------------------------------------------------------------------------------- //	
// ----------------------------------------- LOCAL ACTIONS -------------------------------------------- //
	
	public void mostrarInformacoes(int creatureID) {
		Creature criatura = this.getCreatureFromQueue(creatureID);
		int body = criatura.getBody();
		int mind = criatura.getMind();
		int movement = criatura.getMovement();
		Status status = criatura.getStatus();
		this.atorJogador.mostrarInformacoes(body, mind, movement, status);
	}
	
	public void mostrarInventario() {
		if (localAdventurer.getPlayableCharacter() != null) {
			PlayableCharacter character = localAdventurer.getPlayableCharacter();
			int gold = character.getGold();
			this.atorJogador.mostrarInventario(gold);
		} else {
			this.atorJogador.reportarErro("Zargon n�o carrega gold");
		}
	}
	
// ----------------------------------------- LOCAL ACTIONS -------------------------------------------- //
// ---------------------------------------------------------------------------------------------------- //	
// -----------------------------------------  MAKE A MOVE  -------------------------------------------- //
	
	public void iniciarNovaPartida(int posicao) {
		this.setInProgress(true);
		this.map = new Map(this);
		String idJogador = this.atorJogador.informarNomeJogador();
		Player player = this.criarJogador(idJogador);
		this.setLocalPlayer(player);
		Lance lance = new Lance();
		lance.setType(TipoLance.ENVIAR_PLAYER);
		lance.setPlayer(player);
		this.tratarLance(lance);
		this.enviarLance(lance);
		this.atorJogador.atualizarInterfaceGrafica();
	}
	
	public void selecionarPersonagem() {
		boolean disponivel = false;
		int resultado = this.atorJogador.mostrarOsCincoPersonagens();
		Zargon playerZ = new Zargon(this);
		Adventurer playerA = new Adventurer();
		PlayableCharacter character;
		Lance lance = new Lance();
		lance.setType(TipoLance.SELECIONAR_PERSONAGEM);
		lance.setValue(resultado);
		switch(resultado) {
			case 0:
				disponivel = this.getZargonAvailable();
				break;
			case 1:
				disponivel = this.getBarbarianAvailable();
				break;
			case 2:
				disponivel = this.getWizardAvailable();
				break;
			case 3:
				disponivel = this.getElfAvailable();
				break;
			case 4:
				disponivel = this.getDwarfAvailable();
				break;
			default:
				this.atorJogador.reportarErro("Erro na seleção de personagem.");
				break;
		}
		if (disponivel) {
			switch(resultado) {
			case 0:
				this.localZargon = playerZ;
				lance.setZargon(playerZ);
				break;
			case 1:
				character = new Barbarian();
				character.setID(20);
				playerA.setPlayableCharacter(character);
				this.localAdventurer = playerA;
				lance.setAdventurer(playerA);
				break;
			case 2:
				character = new Wizard();
				character.setID(21);
				playerA.setPlayableCharacter(character);
				this.localAdventurer = playerA;
				lance.setAdventurer(playerA);
				break;
			case 3:
				character = new Elf();
				character.setID(22);
				playerA.setPlayableCharacter(character);
				this.localAdventurer = playerA;
				lance.setAdventurer(playerA);
				break;
			case 4:
				character = new Dwarf();
				character.setID(23);
				playerA.setPlayableCharacter(character);
				this.localAdventurer = playerA;
				lance.setAdventurer(playerA);
				break;
			default:
				this.atorJogador.reportarErro("Erro na seleção de personagem.");
				break;
			}
			this.tratarLance(lance);
			this.enviarLance(lance);
		} else {
			this.atorJogador.reportarErro("Personagem não disponível");
		}
	}
	
	public void movimentar(Directions direcao) {
		boolean daVez = this.checkIfPlayerOfTheTurn();
		if (daVez) {
			Creature criatura = this.getCreatureOfTheTurn();
			int movimento = criatura.getMovement();
			if (movimento > 0) {
				Position posicaoAtual = criatura.getCurrentPosition();
				int linha = posicaoAtual.getRow();
				int coluna = posicaoAtual.getColumn();
				Position novaPosicao;
				try {
					novaPosicao = this.getNewPosition(direcao, linha, coluna);
				} catch (PositionNotEmptyException e) {
					novaPosicao = posicaoAtual;
				}
				Lance lance = new Lance();
				lance.setType(TipoLance.MOVIMENTO);
				lance.setTarget(criatura);
				lance.setSource(posicaoAtual);
				lance.setDestination(novaPosicao);
				this.tratarLance(lance);
				this.enviarLance(lance);
			} else {
				this.atorJogador.reportarErro("Você não tem movimento suficiente nessa rodada.");
			}
		} else {
			this.atorJogador.reportarErro("Não é o jogador da vez.");
		}
	}
	
	public void abrirPorta(int idPorta) {
		Creature criatura = this.getCreatureOfTheTurn();
		boolean daVez = this.checkIfPlayerOfTheTurn();
		if (daVez && (criatura instanceof Barbarian || criatura instanceof Wizard ||
		    criatura instanceof Elf || criatura instanceof Dwarf)) {
			Door porta = this.getDoorByID(idPorta);
			boolean perto = this.verificaSePertoDaPorta((PlayableCharacter) criatura, porta);
			if (perto) {
				boolean aberta = porta.getPortaEstaAberta();
				if (aberta) {
					this.atorJogador.reportarErro("A porta já está aberta");
				} else {
					Lance lance = new Lance();
					lance.setType(TipoLance.ABRIR_PORTA);
					lance.setObjectID(idPorta);
					this.tratarLance(lance);
					this.enviarLance(lance);
				}
			} else {
				this.atorJogador.reportarErro("Não está perto da porta.");
			}
		} else {
			this.atorJogador.reportarErro("Não é jogador da vez ou seu personagem é um monstro.");
		}
	}
	
	public void atacar() {
		boolean daVez = this.checkIfPlayerOfTheTurn();
		if (daVez) {
			Creature atacante = this.getCreatureOfTheTurn();
			Position posicaoAtacante = atacante.getCurrentPosition();
			ArrayList<Creature> possiveisAlvos = this.getPossiveisAlvos(1, posicaoAtacante);
			Creature alvo = this.atorJogador.selecionarAlvo(possiveisAlvos);
			Position posicaoAlvo = alvo.getCurrentPosition();
			boolean possivel = this.verificaSeDistanciaPossivel(posicaoAtacante, posicaoAlvo);
			if (possivel) {
				int dano = this.calcularDanoDoAtaque(atacante, alvo);
				Lance lance = new Lance();
				lance.setType(TipoLance.ATAQUE);
				lance.setValue(dano);
				lance.setTarget(alvo);
				this.tratarLance(lance);
				this.enviarLance(lance);
			} else {
				this.atorJogador.reportarErro("Não é possível atacar um alvo tão distante.");
			}
		}
	}
	
	public void usarMagia() {
		boolean daVez = this.checkIfPlayerOfTheTurn();
		if (daVez) {
			Creature atacante = this.getCreatureOfTheTurn();
			if (atacante instanceof Wizard) {
				ArrayList<Spell> magiasDisponiveis = ((Wizard) atacante).getSpells();
				int mind = atacante.getMind();
				if (mind > 0) {
					Spell magia = this.atorJogador.selecionarMagia(magiasDisponiveis);
					Position posicaoAtual = atacante.getCurrentPosition();
					ArrayList<Creature> possiveisAlvos = this.getPossiveisAlvos(2, posicaoAtual);
					Creature alvo = this.atorJogador.selecionarAlvo(possiveisAlvos);
					boolean sucesso = this.calcularSucessoDaMagia(atacante, alvo, magia);
					if (sucesso) {
						Lance lance = new Lance();
						lance.setType(TipoLance.MAGIA);
						lance.setSpell(magia);
						lance.setTarget(alvo);
						this.tratarLance(lance);
						this.enviarLance(lance);
					}
				} else {
					this.atorJogador.reportarErro("Mind insuficiente.");
				}
			} else if (atacante instanceof Elf) {
				ArrayList<Spell> magiasDisponiveis = ((Elf) atacante).getSpells();
				int mind = atacante.getMind();
				if (mind > 0) {
					Spell magia = this.atorJogador.selecionarMagia(magiasDisponiveis);
					Position posicaoAtual = atacante.getCurrentPosition();
					ArrayList<Creature> possiveisAlvos = this.getPossiveisAlvos(2, posicaoAtual);
					Creature alvo = this.atorJogador.selecionarAlvo(possiveisAlvos);
					boolean sucesso = this.calcularSucessoDaMagia(atacante, alvo, magia);
					if (sucesso) {
						Lance lance = new Lance();
						lance.setType(TipoLance.MAGIA);
						lance.setSpell(magia);
						lance.setTarget(alvo);
						this.tratarLance(lance);
						this.enviarLance(lance);
					}
				} else {
					this.atorJogador.reportarErro("Mind insuficiente.");
				}
			} else {
				this.atorJogador.reportarErro("Seu personagem não usa magia.");
			}
		} else {
			this.atorJogador.reportarErro("Não é o jogador da vez.");
		}
	}
	
	public void procurarArmadilha() {
		boolean daVez = this.checkIfPlayerOfTheTurn();
		if (daVez) {
			Creature criatura = this.getCreatureOfTheTurn();
			Position posicao = criatura.getCurrentPosition();
			Lance lance = new Lance();
			lance.setType(TipoLance.PROCURAR_ARMADILHA);
			lance.setSource(posicao);
			tratarLance(lance);
			enviarLance(lance);
		} else {
			this.atorJogador.reportarErro("Não é jogador da vez.");
		}
	}
	
	public void procurarTesouro() {
		Creature caster = this.getCreatureOfTheTurn();
		boolean daVez = this.checkIfPlayerOfTheTurn();
		if (daVez && (caster instanceof Barbarian || caster instanceof Wizard || caster instanceof Elf || caster instanceof Dwarf)) {
			Position source = caster.getCurrentPosition();
			Lance lance = new Lance();
			lance.setType(TipoLance.PROCURAR_TESOURO);
			lance.setCaster((PlayableCharacter) caster);
			lance.setSource(source);
			this.tratarLance(lance);
			this.enviarLance(lance);
		} else {
			this.atorJogador.reportarErro("Não é jogador da vez ou é o Zargon.");
		}
	}
	
	public void finalizarJogada() {
		boolean daVez = this.checkIfPlayerOfTheTurn();
		if (daVez) {
				Lance lance = new Lance();
				lance.setType(TipoLance.FINALIZAR_JOGADA);
				this.tratarLance(lance);
				this.enviarLance(lance);
		} else {
			this.atorJogador.reportarErro("N�o � o jogador da vez.");
		}
	}
	
// -----------------------------------------  MAKE A MOVE  ------------------------------------------ //
// -------------------------------------------------------------------------------------------------- //
// ----------------------------------------  MOVE HANDLING ------------------------------------------ //
	
	public void tratarLance(Lance lance) {
		TipoLance tipo = lance.getType();
		switch (tipo) {
		case ENVIAR_PLAYER:
			this.tratarEnviarPlayer(lance);
			break;
		case SELECIONAR_PERSONAGEM:
			this.tratarSelecionarPersonagem(lance);
			break;
		case MOVIMENTO: 
			this.tratarMovimento(lance);
			break;
		case ABRIR_PORTA: 
			this.tratarAbrirPorta(lance);
			break;
		case ATAQUE: 
			this.tratarAtaque(lance);
			this.tratarFinalizarJogada(lance);
			break;
		case MAGIA:
			this.tratarMagia(lance);
			this.tratarFinalizarJogada(lance);
			break;
		case PROCURAR_ARMADILHA:
			this.tratarProcurarArmadilha(lance);
			break;
		case PROCURAR_TESOURO:
			this.tratarProcurarTesouro(lance);
			break;
		case FINALIZAR_JOGADA: 
			this.tratarFinalizarJogada(lance);
			break;
		}
		
		this.atorJogador.atualizarInterfaceGrafica();
	}
	
	private void tratarEnviarPlayer(Lance lance) {
		Player player = lance.getPlayer();
		this.insertPlayerIntoQueue(player);
	}

	private void tratarSelecionarPersonagem(Lance lance) {
		PlayableCharacter character;
		Adventurer playerA;
		int personagem = lance.getValue();
		switch(personagem) {
			case 0:
				Zargon playerZ = lance.getZargon();
				for (int i=0; i<playerZ.getMonsters().size(); i++) {
					this.insertCreatureIntoQueue(playerZ.getMonster(i));
				}
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerZ);
				this.setZargonAvailable(false);
				break;
			case 1:
				playerA = lance.getAdventurer();
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerA);
				character = playerA.getPlayableCharacter();
				this.insertCreatureIntoQueue(character);
				this.creatureInPosition(character, 1, 24);
				this.setBarbarianAvailable(false);
				break;
			case 2:
				playerA = lance.getAdventurer();
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerA);
				character = playerA.getPlayableCharacter();
				((Wizard) character).createSpells();
				this.insertCreatureIntoQueue(character);
				this.creatureInPosition(character, 1, 25);
				this.setWizardAvailable(false);
				break;
			case 3:
				playerA = lance.getAdventurer();
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerA);
				character = playerA.getPlayableCharacter();
				((Elf) character).createSpells();
				this.insertCreatureIntoQueue(character);
				this.creatureInPosition(character, 2, 24);
				this.setElfAvailable(false);
				break;
			case 4:
				playerA = lance.getAdventurer();
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerA);
				character = playerA.getPlayableCharacter();
				this.insertCreatureIntoQueue(character);
				this.creatureInPosition(character, 2, 25);
				this.setDwarfAvailable(false);
				break;
			default:
				this.atorJogador.reportarErro("Erro na seleção de personagem.");
				break;
		}
		this.sortCreatureQueueByID();
		this.atorJogador.exibirCriaturas();
	}
	
	private void tratarMovimento(Lance lance) {
		Position posicaoAtual = lance.getSource();
		Position novaPosicao = lance.getDestination();
		Creature criatura = lance.getTarget();
		posicaoAtual.removeCreature();
		novaPosicao.setCreature(criatura);
		int linha = novaPosicao.getRow();
		int coluna = novaPosicao.getColumn();
		this.map.atualizarPosicao(novaPosicao, linha, coluna);
		linha = posicaoAtual.getRow();
		coluna = posicaoAtual.getColumn();
		this.map.atualizarPosicao(posicaoAtual, linha, coluna);
		criatura.setCurrentPosition(novaPosicao);
		criatura.decreaseMovement();
		Trap trap = novaPosicao.getTrap();
		if(trap != null) {
			int dano = trap.getDeliveredDamage();
			criatura.decreaseBody(dano);
			boolean visivel = trap.getVisible();
			if (!visivel) {
				this.atorJogador.mostrarAcaoTrap(dano, criatura);
				trap.makeTrapVisible();
			}
			int body = criatura.getBody();
			if (body <= 0) {
				int id = criatura.getID();
				this.atorJogador.anunciarMorteDesafortunada(criatura);
				this.killCreature(id);
			}
		}
	}

	private void tratarAbrirPorta(Lance lance) {
		int idPorta = lance.getObjectID();
		Door porta = this.getDoorByID(idPorta);
		porta.abrirPorta();
	}
	
	private void tratarAtaque(Lance lance) {
		Creature alvo = lance.getTarget();
		int dano = lance.getValue();
		alvo.decreaseBody(dano);
		int body = alvo.getBody();
		this.atorJogador.mostrarDano(alvo, dano);
		if (body <= 0) {
			this.atorJogador.anunciarMorteDeCriatura(alvo);
			int idAlvo = alvo.getID();
			this.killCreature(idAlvo);
		}
	}

	private void tratarMagia(Lance lance) {
		Spell magia = lance.getSpell();
		Creature alvo = lance.getTarget();
		int dano = magia.getDamage();
		Status status = magia.getStatus();
		alvo.setStatus(status);
		this.getCreatureOfTheTurn().usarMagia(magia);
		this.atorJogador.anunciarUsoDeMagia(this.getCreatureOfTheTurn(), magia, alvo, dano, status);
		this.atorJogador.exibirCriaturas();
		alvo.decreaseBody(dano);
		int body = alvo.getBody();
		if (body <= 0) {
			this.atorJogador.anunciarMorteDeCriatura(alvo);
			int id = alvo.getID();
			this.killCreature(id);
		}
	}
	
	private void tratarProcurarArmadilha(Lance lance) {
		Position posicaoAtual = lance.getSource();
		int linha = posicaoAtual.getRow();
		int coluna = posicaoAtual.getColumn();
		for (int i=linha-2; i<=linha+2; i++) {
			for (int j=coluna-2; j<=coluna+2; j++) {
				posicaoAtual = this.map.getPosition(i, j);
				if(posicaoAtual.getTrap() != null) {
					posicaoAtual.makeTrapVisible();
				}
			}
		}
	}

	private void tratarProcurarTesouro(Lance lance) {
		Position posicaoAtual = lance.getSource();
		int linha = posicaoAtual.getRow();
		int coluna = posicaoAtual.getColumn();
		PlayableCharacter character = lance.getCaster();
		for (int i=linha-2; i<=linha+2; i++) {
			for (int j=coluna-2; j<=coluna+2; j++) {
				posicaoAtual = this.map.getPosition(i, j);
				Treasure tesouro = posicaoAtual.getTreasure();
				if (tesouro != null) {
					int gold = tesouro.getGoldAmount();
					tesouro.setGoldAmount(0);
					character.increaseGold(gold);
					JOptionPane.showMessageDialog(null, "O jogador"+character.getClass().getSimpleName()+" encontrou algumas moedas de ouro.");
				}
			}
		}
	}

	private void tratarFinalizarJogada(Lance lance) {
		Creature criatura;
		Creature creature = this.removeCreatureFromQueue();
		this.insertCreatureIntoQueue(creature);
		criatura = this.getCreatureOfTheTurn();
		criatura.setMovement();
		this.encerramentoDaPartida();
	}
// ----------------------------------------  MOVE HANDLING ------------------------------------------ //
// ---------------------------------------------------------------------------------------------------- //
}
