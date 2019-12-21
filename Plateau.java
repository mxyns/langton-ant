import java.util.ArrayList;

public class Plateau {

	private boolean[][] monde; // tableau 2D representant les cases du plateau selon la convention blanc=false et noir=true
	private int[][] colors;
	private Fourmi[] f; // instance de la classe Fourmi se deplacant sur le plateau
	private float decay = 1e-2f;
	private float brightnessThreshold = .1f;

	public Plateau(int w, int h) {
		this.monde = new boolean[h][w];
		this.colors = new int[h][w];
		this.f = new Fourmi[10];

		System.out.println(this.toString());
	}
	public Plateau(int w, int h, int fSize) {
		this.monde = new boolean[h][w];
		this.colors = new int[h][w];
		this.f = new Fourmi[fSize];
	}
	public Plateau(int w, int h, int fSize, float decay, float brightnessThreshold) {
		this.monde = new boolean[h][w];
		this.colors = new int[h][w];
		this.decay = decay;
		if(this.getDecayRate() != 0) this.brightnessThreshold = brightnessThreshold;
		this.f = new Fourmi[fSize];

		System.out.println(this.toString());
	}

    // Accesseurs
    public int getTaille(){
        return this.monde.length*this.monde[0].length;
    }
    public int getWidth() {
		return this.monde[0].length;
	}
	public int getHeight() {
		return this.monde.length;
	}
    public boolean[][] getEtat(){
        return this.monde;
    }
    public void setFourmis(Fourmi[] fs) {
		this.f = fs;
	}
    public Fourmi getFourmi(){
        return this.f[0];
    }
    public Fourmi[] getFourmis() {
		return this.f;
	}
	public int getFourmisCount() {

		int c=0;
		for (Fourmi f : f)
			if (f != null)
				++c;
		return c;
	}
	public int getMaxStepVelocity() {
		int m = 1;
		for (Fourmi f : this.f)
			if(f != null && m < f.getStepVelocity())
				m = f.getStepVelocity();

		return m;
	}
	public int getMaxVelocity() {
		int m = 1;
		for (Fourmi f : this.f)
			if(f != null && m < f.getVelocity())
				m = f.getVelocity();

		return m;
	}
    public boolean getCase(Fourmi f) {
		return this.monde[f.getLigne()][f.getColonne()];
	}
	public int[][] getColors() {
		return this.colors;
	}
	public float getDecayRate() {
		return this.decay;
	}
	public float getBrightnessThreshold() {
		return this.brightnessThreshold;
	}
    public void setDecayRate(float decay) { this.decay = decay;}
    public void setBrightnessThreshold(float brght) { this.brightnessThreshold = brght;}

    public void addFourmi(Fourmi f) {
		for(int i = 0; i<this.f.length; i++) {
			if(this.f[i] == null) {
				this.f[i] = f;
				break;
			}
		}
	}

	/**
	* Mets a jour le plateau apres une iteration
	*/
    public void bougeFourmi(Fourmi f){
			boolean caz = getCase(f);
			f.tourner(caz);

		for(int v = 0; v < f.getVelocity(); v++) {
			switchCase(f);
			f.avance();
				collide(f);

			if(f.isOut(this)) f.getIn(this);
		}
    }

    private void collide(Fourmi f) {
		for (Fourmi fourmi : this.f) {
			if(f != fourmi && f.collidesWith(fourmi)) f.onCollideWith(fourmi);
		}
	}

    private void switchCase(Fourmi f) {
		this.monde[f.getLigne()][f.getColonne()] = !getCase(f); //noire
		this.colors[f.getLigne()][f.getColonne()] = f.getColor().getRGB();
	}

	public String toString() {
		int b=0;
		for(Fourmi a : this.f) {
			if (a != null) b++;
		}
		return "Plateau: taille="+this.getTaille()+", "
				+b+"/"+this.getFourmis().length+", \n"
				+"decay="+(this.getDecayRate() == 0 ? "none": this.getDecayRate())+",\n"
				+" brightnessThreshold="+(this.getDecayRate() == 0 ? "none": this.getBrightnessThreshold())+"\n"
				+" maxStepVelocity="+this.getMaxStepVelocity()+"\n"
				+" maxVelocity="+this.getMaxVelocity();
	}
}
