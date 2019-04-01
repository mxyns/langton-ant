import java.util.ArrayList;

public class Plateau {
	
	private boolean[][] monde; // tableau 2D representant les cases du plateau selon la convention blanc=false et noir=true
	private int[][] colors;
	private Fourmi[] f; // instance de la classe Fourmi se deplacant sur le plateau
	
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
    public Fourmi getFourmi(){
        return this.f[0];
    }
    public Fourmi[] getFourmis() {
		return this.f;
	}
    public boolean getCase(Fourmi f) {
		return this.monde[f.getLigne()][f.getColonne()];
	}
	public int[][] getColors() {
		return this.colors;
	}
    
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
        
        if(f.isOut(this)) {
			f.getIn(this);
		}
        
        boolean caz = getCase(f);
        f.tourner(caz);
        
        switchCase(f);
        
        f.avance(caz);
        
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
		return "Plateau: taille="+this.getTaille()+", "+b+"/"+this.getFourmis().length;
	}
}
