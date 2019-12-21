import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private JPanel panel1;
    private JLabel title;
    private JList simsList;
    private JList antsList;
    private JButton addSim;
    private JButton delSim;
    private JButton addAnt;
    private JButton delAnt;
    private JButton toggleSim;
    private JButton toggleFourmi;
    private JButton simSettings;
    private JButton antSettings;

    private SimSettings simsSettingsMake = new SimSettings();
    private SimSettings simsSettingsEdit = new SimSettings();

    private AntSettings antSettingsMake;
    private AntSettings antSettingsEdit;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);


        Affichage.afficherSimulations();
        Utils.pause(500, 0);

        while (Simulation.simulations.size() == 0) { Utils.pause(0,0); }

        while (Simulation.simulations.size() != 0) {
            for (Simulation sim : Simulation.simulations)
                if (sim.isActive())
                    for (int s = 0; s < sim.getPlateau().getMaxStepVelocity(); s++)
                        for (Fourmi f : sim.getPlateau().getFourmis())
                            if (f != null && f.isActive() && s < f.getStepVelocity())
                                sim.getPlateau().bougeFourmi(f);

            Affichage.afficherSimulations();
            Utils.pause(5, 1);
        }
    }

    public Main() {

        Timer updater = new Timer();
        updater.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                updateLists();
            }
        }, 0, 100);
        updater.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                updateButtons();
            }
        }, 0, 1000);

        addSim.addActionListener(e -> simsSettingsMake.pop());
        delSim.addActionListener(e -> {if (!simsList.isSelectionEmpty()) {
            Simulation sim = Simulation.simulations.get(simsList.getSelectedIndex());
            sim.delete();

            simsList.clearSelection();
            updateLists();


        }});

        addAnt.addActionListener(e -> {
            if (!simsList.isSelectionEmpty() && simsList.getModel().getSize() < Simulation.simulations.get(simsList.getSelectedIndex()).getPlateau().getFourmis().length)
                (antSettingsMake = new AntSettings(Simulation.simulations.get(simsList.getSelectedIndex()))).pop();
        });
        delAnt.addActionListener(e -> {
            if (!simsList.isSelectionEmpty() && !antsList.isSelectionEmpty()) {

                int simIndex = simsList.getSelectedIndex();
                Simulation sim = Simulation.simulations.get(simIndex);
                sim.getPlateau().getFourmis()[antsList.getSelectedIndex()] = null;

                simsList.clearSelection();
                updateLists();
                simsList.setSelectedIndex(simIndex);
            }

        });
        antSettings.addActionListener(e -> {
            if (!simsList.isSelectionEmpty() && !antsList.isSelectionEmpty()) (antSettingsEdit = new AntSettings(Simulation.simulations.get(simsList.getSelectedIndex()).getPlateau().getFourmis()[antsList.getSelectedIndex()])).pop();

        });

        simSettings.addActionListener(e ->   {if (!simsList.isSelectionEmpty()) (simsSettingsEdit = new SimSettings(Simulation.simulations.get(simsList.getSelectedIndex()))).pop();});
        toggleSim.addActionListener(e -> {

            if (simsList.isSelectionEmpty()) return;

            Simulation sim = Simulation.simulations.get(simsList.getSelectedIndex());
            if (sim.isActive()) {

                toggleSim.setText(">");
                sim.stop();
            } else {

                toggleSim.setText("| |");
                sim.start();
            }
        });

        toggleFourmi.addActionListener(e -> {

            if (simsList.isSelectionEmpty() || antsList.isSelectionEmpty()) return;

            Simulation sim = Simulation.simulations.get(simsList.getSelectedIndex());
            Fourmi f = sim.getPlateau().getFourmis()[antsList.getSelectedIndex()];

            if (f == null) return;

            if (f.isActive()){

                toggleFourmi.setText(">");
                f.stop();
            } else {

                toggleFourmi.setText("| |");
                f.start();
            }
        });

        simsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JList list = (JList)e.getSource();
                if (e.getClickCount() == 2) {

                    int index = list.locationToIndex(e.getPoint());
                    Simulation sim = Simulation.simulations.get(index);
                    sim.getAffichage().setVisible(true);
                }

                updateButtons();
            }
        });
        antsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateButtons();
            }
        });
    }


    public void updateButtons() {

        if (!simsList.isSelectionEmpty()) {
            int currentIndex = simsList.getSelectedIndex();
            Simulation currentSim = Simulation.simulations.get(currentIndex);

            if (currentSim != null && currentSim.isActive())
                toggleSim.setText("| |");
            else
                toggleSim.setText(">");

            if (!antsList.isSelectionEmpty()) {
                int currentAIndex = antsList.getSelectedIndex();
                Fourmi currentAnt = currentSim.getPlateau().getFourmis()[currentAIndex];

                if (currentAnt != null && currentAnt.isActive())
                    toggleFourmi.setText("| |");
                else
                    toggleFourmi.setText(">");

            }
        }
    }

    public void updateLists() {

        int selected = simsList.getSelectedIndex();

        String[] sims = new String[Simulation.simulations.size()];
        for (int i = 0; i < sims.length; ++i)
            sims[i] = Simulation.simulations.get(i).toString();

        simsList.setListData(sims);
        simsList.setSelectedIndex(selected);


        if (!simsList.isSelectionEmpty()) {
            selected = antsList.getSelectedIndex();

            Simulation current = Simulation.simulations.get(simsList.getSelectedIndex());
            String[] fourms = new String[current.getPlateau().getFourmis().length];

            for (int i = 0; i < fourms.length; ++i)
                if (current.getPlateau().getFourmis()[i] != null)
                    fourms[i] = current.getPlateau().getFourmis()[i].toString();

            antsList.setListData(fourms);
            antsList.setSelectedIndex(selected);
        } else {

            antsList.setListData(new Object[] {});
        }
    }
}
