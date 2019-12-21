public class Utils {



    /**
     * Effectue une pause lors de l'execution du programme.
     *
     * @param ms Duree de la pause en millisecondes
     */
    public static void pause(int ms, int nano) {

        try {
            Thread.sleep(ms, nano);
        } catch (InterruptedException e) {}
    }
}
