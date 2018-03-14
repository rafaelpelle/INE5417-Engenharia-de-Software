package modelo;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Trap implements Jogada {

	private static final long serialVersionUID = 8734364272715828524L;
	protected int deliveredDamage;
	protected boolean visible;

	public void makeTrapVisible() {
		this.visible = true;
	}

	public int getDeliveredDamage() {
		if (!visible) {
			return this.deliveredDamage;
		}
		return 0;
	}
	
	public boolean getVisible() {
		return this.visible;
	}

}