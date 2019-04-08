import java.awt.Color;
import java.util.Random;

public class Fourmi {
	
    private int l; // indice de la ligne qu'occupe la fourmi dans le monde
    private int c; // indice de la colonne qu'occupe la fourmi dans le monde
    private int d; // direction de la fourmi : 0 vers le haut, 1 vers la droite, 2 vers le bas, 3 vers la gauche
    private int[] vel; // vel[0] rectilign velocity, vel[1] step velocity (step/gen)
    private boolean coll = false;
	private Color cl; // couleur fourmi
	
	// Constructeur complet
	public Fourmi(int x, int y, int d) {
		this.l = y;
		this.c = x;
		this.d = d;
		this.vel = new int[] {1,1};
		setRandomColor();
	}
	public Fourmi(int x, int y, int d, int[] vel, boolean coll) {
		this.l = x;
		this.c = y;
		this.d = d;
		this.coll = coll;
		this.vel = vel;
		setRandomColor();
	}
	public Fourmi(Plateau p, int d, int[] vel, boolean coll) {
		this.l = p.getHeight()/2;
		this.c = p.getWidth()/2;
		this.d = d;
		this.coll = coll;
		this.vel = vel;
		setRandomColor();
	}
	public Fourmi(Plateau p, boolean r, int[] vel, boolean coll) {
		this(p,0,vel, coll);
		if(r) {
			this.c = (int)(p.getWidth()*Math.random());
			this.l = (int)(p.getHeight()*Math.random());
			this.d = (int)(4*Math.random());
		}
	}
	
    // Accesseurs
	public int getLigne(){
        return this.l;
    }
    public int getColonne(){
        return this.c;
    }
	public int getDirection(){
        return this.d;
    }
    public Color getColor() {
		return this.cl;
	}
	public void setColor(Color c) {
		this.cl = c;
	}
	public void setRandomColor() {
		this.cl = new Color((int)(Math.random() * 0x1000000)).brighter();
	}
	public int getStepVelocity() {
		return this.vel[1];
	}
	public int getVelocity() {
		return this.vel[0];
	}
	public void setStepVelocity(int aVel) {
		this.vel[1] = aVel;
	}
	public void setVelocity(int vel) {
		this.vel[0] = vel;
	}
    public boolean doesCollide() {
		return this.coll;
	}
    /**
    * Deplacement de la fourmie
    * @param c Vaut true si la case ou se situe la fourmi avant le deplacement est noire (false = blanche)
    */
	public void avance(){
        switch(this.d) {
			case 0: {
				this.c--;
				break;
			}
			case 1: {
				this.l++;
				break;
			}
			case 2: {
				this.c++;
				break;
			}
			default: {
				this.l--;
				break;
			}
		}
	}
	
	public void tourner(boolean c) {
		this.d += (c ? 1 : 3);
		this.d = this.d%4;
	}
	
	public boolean collidesWith(Fourmi f) {
		if(this == null || f == null) return false;
		return (this.doesCollide() && f.doesCollide() && this.c == f.getColonne() && this.l == f.getLigne());
	}
	
	public void onCollideWith(Fourmi coll) {
		if(!coll.doesCollide()) return;
		tourner(true);
		setRandomColor();
		coll.setRandomColor();
	}
	
	public boolean isOut(Plateau p) {
		int w = p.getWidth();
		int h = p.getHeight();
		return (this.c < 0 || this.c >= w || this.l < 0 || this.l >= h);
	}
	
	public void getIn(Plateau p) {
		int w = p.getWidth();
		int h = p.getHeight();
		if (this.l < 0)
			this.l = h-1;
		else if (this.c < 0)
			this.c = w-1;
		else if (this.l >= h)
			this.l = 0;
		else if (this.c >= w)
			this.c = 0;
	}
	
	public String toString() {
		return "Fourmi: x="+this.c+", "+this.l+", d="+this.d;
	}
}

