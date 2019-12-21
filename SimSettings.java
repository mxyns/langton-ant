import javax.swing.*;

public class SimSettings {

    private JLabel heightLabel;
    private JLabel widthLabel;
    private JLabel antCountLabel;
    private JLabel brightnessLabel;
    private JLabel decayLabel;

    private JPanel mainPanel;
    private JSlider decaySlider;
    private JSlider brightnessSlider;
    private JFormattedTextField widthField;
    private JFormattedTextField heightField;
    private JButton confirm;
    private JSpinner antCountSpinner;
    private JLabel title;

    private JFrame frame;

    public SimSettings() {

        frame = new JFrame("Make Sim");
        final SimSettings launcher = this;
        frame.setContentPane(launcher.mainPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();

        confirm.setText("make 'em run");
        widthField.setEnabled(true);
        heightField.setEnabled(true);

        System.out.println(Simulation.simulations == null);
        System.out.println(launcher.widthField.getValue());

        launcher.confirm.addActionListener(e -> {

            Integer width = null, height = null, ants = null;
            float decay = 0, brightness = 0;
            try {
                width = Integer.parseInt(launcher.widthField.getText().replaceAll(" ",""));
                height = Integer.parseInt(launcher.heightField.getText().replaceAll(" ", ""));
                ants = (int) launcher.antCountSpinner.getValue();
                decay = (float) launcher.decaySlider.getValue() / decaySlider.getMaximum();
                brightness = launcher.brightnessSlider.getValue() / (100f*brightnessSlider.getMaximum());
            } catch (Exception ex) {ex.printStackTrace();}

            if (width != null && height != null && ants != null) {
                Plateau p = new Plateau(width, height, ants, decay, brightness);

                Simulation.make(p);

                for (int j = 0; j < p.getFourmis().length; j++) {
                    p.addFourmi(new Fourmi(p, true, new int[] { (int) (3 * Math.random() + 1), 1 }, true));
                }

                System.out.println(p);
                hide();
            }
        });
    }
    public SimSettings(Simulation sim) {

        frame = new JFrame("Edit sim: " + sim.getId());
        final SimSettings launcher = this;
        frame.setContentPane(launcher.mainPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();

        confirm.setText("Save it");
        widthField.setEnabled(false);
        heightField.setEnabled(false);

        System.out.println(Simulation.simulations == null);
        System.out.println(launcher.widthField.getValue());

        decaySlider.setValue((int)(decaySlider.getMaximum()*sim.getPlateau().getDecayRate()));
        brightnessSlider.setValue((int)(100f*brightnessSlider.getMaximum()*sim.getPlateau().getBrightnessThreshold()));
        widthField.setValue(String.valueOf(sim.getPlateau().getWidth()));
        heightField.setValue(String.valueOf(sim.getPlateau().getHeight()));
        antCountSpinner.setValue(sim.getPlateau().getFourmis().length);

        launcher.confirm.addActionListener(e -> {


            Integer width = null, height = null, ants = null;
            float decay = 0, brightness = 0;
            try {
                width = Integer.parseInt(launcher.widthField.getText());
                height = Integer.parseInt(launcher.heightField.getText());
                ants = (int) launcher.antCountSpinner.getValue();
                decay = (float) launcher.decaySlider.getValue() / (100f*decaySlider.getMaximum());
                brightness = launcher.brightnessSlider.getValue() / brightnessSlider.getMaximum();
            } catch (Exception ex) {ex.printStackTrace();}

            if (width != null && height != null && ants != null) {

                sim.stop();

                sim.getPlateau().setBrightnessThreshold(brightness);
                sim.getPlateau().setDecayRate(decay);
                Fourmi[] f = new Fourmi[ants];

                for (int i = 0; i<f.length && i<sim.getPlateau().getFourmis().length; ++i)
                    f[i] = sim.getPlateau().getFourmis()[i];

                sim.getPlateau().setFourmis(f);

                sim.start();
            }
        });
    }

    public void pop() {

        frame.setVisible(true);
    }

    public void hide() {

        frame.setVisible(false);
    }
}
