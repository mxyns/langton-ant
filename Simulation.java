public class Simulation {
	
	public static void main (String args[]) {
        Plateau p = new Plateau(1000, 1000,500);
			for(int i = 0; i<p.getFourmis().length; i++) {
				p.addFourmi(new Fourmi(p,true));
			}
			
        
		Affichage.afficherPlateau(p);
		System.out.println(p);
        pause(500,0);
        
		for(int i=0; i< 1000000; i++){
			
			for(Fourmi f : p.getFourmis()) {
				if(f != null) p.bougeFourmi(f);
			}
			//System.out.println(f);
			Affichage.afficherPlateau(p);
            pause(0,1);
		}
	}

    /**
    * Effectue une pause lors de l'execution du programme.
    * @param ms Duree de la pause en millisecondes
    */
    public static void pause(int ms, int nano) {
        try{
            Thread.sleep(ms, nano);
        }catch(InterruptedException e){}
    }
}

