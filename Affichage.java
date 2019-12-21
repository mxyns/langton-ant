import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Gestionnaire d'affichage pour la fourmi de Langton (issue de l'affichage du "Jeu de la vie")
 * @author Jean-Francois TREGOUET
 */
public class Affichage extends JFrame {
    private static Affichage world = null;
    private PanneauGrille pg;
    
    private static Affichage worlds[] = null;

    private Affichage(Plateau p) {
        super("Fourmi de Langton");
        pg = new PanneauGrille(p);
        setContentPane(pg);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    Affichage(Plateau p, int id) {
        super("Fourmi de Langton #"+id);
        pg = new PanneauGrille(p);
        setContentPane(pg);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }
    /**
     * Affiche un monde.
     * @param p le monde à afficher
     */
    public static void afficherPlateau(Plateau p) {
        if (world==null)
            world = new Affichage(p);
        world.pg.p = p;
        world.repaint();
    }
    public static void afficherPlateaux(Plateau[] p) {
		if(worlds == null) {
			worlds = new Affichage[p.length];
			for (int i = 0; i < worlds.length; i++) {
				worlds[i] = new Affichage(p[i], i);
			}
		}
		for(int i = 0; i < worlds.length; i++) {
			worlds[i].pg.p = p[i];
			worlds[i].repaint();
		}
	}

	public static void afficherSimulations() {

        List<Simulation> sims = Simulation.simulations;

        for (Simulation sim : sims) {

            if (sim.isActive()) {
                sim.getAffichage().pg.p = sim.getPlateau();
                sim.getAffichage().repaint();
            }
        }
	}
    
    /**
     * Calcul la resolution la plus appropriee a la taille du monde de
     * facon a ce que la fenetre occupe 80% de la hauteur ou de la
     * largeur de la zone utile de l'ecran
     * de l'ecran.
     * @param monde le monde à afficher
     */
    private static int calcRes(boolean[][] monde) {
        final double p = .9; // pourcentage de la zone utile a occuper
        int resC;
        Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds(); // Taille zone utile
        resC = Math.min((int)(bounds.height*p)/monde.length,(int)(bounds.width*p)/monde[0].length);
        resC = Math.max(1,resC); // valeur planche de 1
        return resC;
    }
    
    class PanneauGrille extends JPanel {
        private int res;
        private boolean[][] monde;
        private BufferedImage worldImage;
        public Plateau p;
        public PanneauGrille(Plateau monP) {
            p = monP;
            monde = p.getEtat();
            res = Affichage.calcRes(monde);
            worldImage = new BufferedImage(res*p.getWidth(),res*p.getHeight(),BufferedImage.TYPE_INT_RGB);
            setPreferredSize(new Dimension(res * p.getWidth(), res * p.getHeight()));
        }
        private void dessineMonde(Graphics g) {
            int nbL = p.getHeight();
            int nbC = p.getWidth();
            // couleur de fond
            g.setColor(Color.BLACK);
            g.fillRect(0,0,res*nbC,res*nbL);
            // cellules
            g.setColor(Color.BLACK);
            for (int i = 0; i < nbL; i++)
                for (int j = 0; j < nbC; j++)
                    if (monde[i][j]){
						Color c = new Color(p.getColors()[i][j]);
						g.setColor(c.brighter());
						g.fillRect(res*j,res*i,res,res);
						if(Math.random() < p.getDecayRate()) {
							float b = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null)[2];
							p.getColors()[i][j] = c.darker().getRGB();
							if(b < p.getBrightnessThreshold()) p.getEtat()[i][j] = false;
						}
					}
            // fourmi
            for (Fourmi f : p.getFourmis()) {
				if(f == null) continue;
				g.setColor(f.getColor());
				g.fillRect(f.getColonne()*res,f.getLigne()*res,res,res);
			}
        }
        public void paint(Graphics g) {
            Graphics gw = worldImage.getGraphics();
            dessineMonde(gw);
            g.drawImage(worldImage,0,0,null);
        }
    }

    public static Affichage[] getWorlds() { return worlds; }
}       

