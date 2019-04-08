public class Simulation {
	
	public static void main (String args[]) {
		Plateau[] ps = new Plateau[1];
		for(int i = 0; i < ps.length; i++) {
			ps[i] = new Plateau(300,300,5,1e-3f,.1f);
			
			if (i==0) {
				for(int j = 0; j<ps[i].getFourmis().length; j++) {
					ps[i].addFourmi(new Fourmi(ps[i],true, new int[] {(int)(3*Math.random()+1),1},true));
				}		
			} else ps[i].setFourmis(ps[0].getFourmis());
			
			System.out.println(ps[i]);
		}
			
        
		Affichage.afficherPlateaux(ps);
        pause(500,0);
        
		for(int g=0; g < 1000000; g++) {
			for (Plateau p : ps)
				for(int s = 0; s < p.getMaxStepVelocity(); s++)
					for(Fourmi f : p.getFourmis())
						if(f != null && s < f.getStepVelocity())
							p.bougeFourmi(f);
								
			Affichage.afficherPlateaux(ps);
            pause(5,1);
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

