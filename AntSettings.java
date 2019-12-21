import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AntSettings {

    private JPanel mainPanel;
    private JLabel speedLabel;
    private JLabel brightnessLabel;
    private JSlider speedSlider;
    private JSlider stepSpeedslider;
    private JLabel xLabel;
    private JFormattedTextField xField;
    private JLabel yLabel;
    private JFormattedTextField yField;
    private JLabel directionLabel;
    private JButton confirm;
    private JComboBox direction;
    private JCheckBox xrng;
    private JCheckBox yrng;
    private JCheckBox collides;
    private JLabel title;
    private JSlider rSlide;
    private JPanel colorPrev;
    private JSlider gSlide;
    private JSlider bSlide;
    private JFrame frame;

    private ChangeListener sliderChangeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {

            colorPrev.setBackground(new Color(rSlide.getValue(), gSlide.getValue(), bSlide.getValue()));
        }
    };

    private AntSettings() {

        frame = new JFrame("Ant breeder");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();

        rSlide.addChangeListener(sliderChangeListener);
        gSlide.addChangeListener(sliderChangeListener);
        bSlide.addChangeListener(sliderChangeListener);

        colorPrev.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {
                    Color c = new Color((int) (Math.random() * 0x1000000)).brighter();
                    rSlide.setValue(c.getRed());
                    gSlide.setValue(c.getGreen());
                    bSlide.setValue(c.getBlue());
                }

            }
        });

    }

    public AntSettings(Simulation simulation) {

        this();

        xrng.addActionListener(e -> xField.setEnabled(!xrng.isSelected()));
        yrng.addActionListener(e -> yField.setEnabled(!yrng.isSelected()));

        confirm.addActionListener(e -> {

            Plateau p = simulation.getPlateau();
            if (p.getFourmisCount() >= p.getFourmis().length) return;


            Integer x = null, y = null;

            if (xrng.isSelected()) x = (int) (Math.random() * p.getWidth());
            else
                try {
                    x = Integer.parseInt(xField.getText().replaceAll(" ", ""));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return;
                }

            if (yrng.isSelected()) y = (int) (Math.random() * p.getHeight());
            else
                try {
                    y = Integer.parseInt(yField.getText().replaceAll(" ", ""));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return;
                }

            System.out.println(x);
            System.out.println(y);
            Fourmi fourmi = new Fourmi(Math.abs(x) % p.getWidth(), Math.abs(y) % p.getHeight(), direction.getSelectedIndex(), new int[] { speedSlider.getValue(), stepSpeedslider.getValue() }, collides.isSelected());
            fourmi.setColor(new Color(rSlide.getValue(), gSlide.getValue(), bSlide.getValue()));

            p.addFourmi(fourmi);
        });
    }

    public AntSettings(Fourmi fourmi) {

        this();

        if (fourmi == null) return;

        frame.setTitle("Ant mutator");
        confirm.setText("mutate it");

        xrng.setEnabled(false);
        yrng.setEnabled(false);
        xField.setEnabled(false);
        yField.setEnabled(false);

        direction.setSelectedIndex(fourmi.getDirection());
        speedSlider.setValue(fourmi.getVelocity());
        stepSpeedslider.setValue(fourmi.getStepVelocity());
        collides.setSelected(fourmi.doesCollide());
        rSlide.setValue(fourmi.getColor().getRed());
        gSlide.setValue(fourmi.getColor().getGreen());
        bSlide.setValue(fourmi.getColor().getBlue());

        confirm.addActionListener(e -> {

            fourmi.setDirection(direction.getSelectedIndex());
            fourmi.setVelocity(speedSlider.getValue());
            fourmi.setStepVelocity(stepSpeedslider.getValue());
            fourmi.setCollisions(collides.isSelected());
            fourmi.setColor(new Color(rSlide.getValue(), gSlide.getValue(), bSlide.getValue()));
        });
    }


    public void pop() {

        frame.setVisible(true);
    }

    public void hide() {

        frame.setVisible(false);
    }
}
