package modelo;

public class Door extends Position {

	private static final long serialVersionUID = -4132807911449425844L;
	protected boolean open;
	private int id;
	
	public Door(int row, int column, int id) {
		super(row, column);
		this.id = id;
	}

	public boolean getPortaEstaAberta() {
		return this.open;
	}

	public void abrirPorta() {
		this.open = true;
	}

	public int getID() {
		return id;
	}

}