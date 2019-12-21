import java.util.ArrayList;
import java.util.List;

public class Simulation {

	static List<Simulation> simulations = new ArrayList<>();

	private Affichage affichage;
	private Plateau plateau;
	private boolean active = true;
	private int id;

	public Simulation(Plateau p, int id) {

		affichage = new Affichage(p, id);
		plateau = p;
	}

	public Plateau getPlateau() {

		return plateau;
	}

	public void setPlateau(Plateau plateau) {

		this.plateau = plateau;
	}

	public Affichage getAffichage() {

		return affichage;
	}

	public void setAffichage(Affichage affichage) {

		this.affichage = affichage;
	}

	public boolean delete() {

		getAffichage().dispose();
		return simulations.remove(this);
	}

	public boolean isActive() {

		return active;
	}

	public void stop() {

		active = false;
	}

	public void start() {

		active = true;
	}

	public static void make(Plateau p) {

		Simulation sim = new Simulation(p, simulations.size()+1);

		simulations.add(sim);
	}

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}
}

