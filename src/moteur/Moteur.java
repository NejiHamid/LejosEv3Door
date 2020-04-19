package moteur;


import lejos.robotics.RegulatedMotor;

public class Moteur {

	private RegulatedMotor moteur;

	public Moteur(RegulatedMotor moteur) {
		this.moteur =moteur;
 		moteur.setSpeed(20);
	}

	public RegulatedMotor getMoteur() {
		return moteur;
	}

	public void setMotor(RegulatedMotor moteur) {
		this.moteur = moteur;
	}

	public void pousser() {
		moteur.backward();
	}
	
	public void tirer() {
		moteur.forward();
	}
	
	
	public void arreter() {
		moteur.stop();
	}
	
}
