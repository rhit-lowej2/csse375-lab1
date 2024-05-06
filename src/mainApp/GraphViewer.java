package mainApp;

import javax.swing.*;

import java.awt.event.ActionEvent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;


/**
 * Class: GraphViewer
 * 
 * @author AJ Hedden Alex Brickley Hollin Glaze
 *         This class is essentially our main method, just about everything
 *         can be accessed from here as we have our two main components defined here. 
 *          GenerationComponent and GraphComponent are our two 'powerhouse' classes that
 *          take care of a lot of the heavylifting and larger calculations.
 */

public class GraphViewer {
	
	public GraphViewer() {
	}

    Dimension dimension = new Dimension();
    private int genSize;
    private JButton start;
    private JButton close;

    private Timer t;
    private boolean progRunning = false;
    private static final int DELAY = 5;

    private int currentGenerationIndex = 0;

    private GraphComponent graphComp = new GraphComponent();
    private GenerationComponent genComp = new GenerationComponent();
    private String currSelection = "truncation";
    private Clock clock = new Clock();
    private FitnessMethodFactory factory = new FitnessMethodFactory();
    private FitnessMethod fitnessMethod = factory.getFitnessMethod("Smiley");
    JFrame frame;
    JFrame genFrame;
    JPanel panel = new JPanel();

    JTextField rateField, eliteRateField, popSizeField, genSizeField;
    JCheckBox crossover, terminate;
    JComboBox<String> dropdown, fitDrop;
    int errorCount = 0;

    public void run() {

        this.t = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentGenerationIndex < genSize) {
                    graphComp.drawGeneration(currentGenerationIndex);
                    genComp.drawGeneration(currentGenerationIndex);
                    currentGenerationIndex++;
                } else {
                    t.stop();
                    start.setText("Start");
                    progRunning = false;
                }
            }
        });

//        t = new Timer(DELAY, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (currentGenerationIndex < genSize) {
//                    graphComp.drawGeneration(currentGenerationIndex);
//                    currentGenerationIndex++;
//                } else {
//                    t.stop();
//                }
//            }
//        });
        //Start timer

        this.frame = new JFrame();
        genFrame = new JFrame();
        this.start = new JButton("Start");
        this.close = new JButton("Close");

        crossover = new JCheckBox("Crossover");
        terminate = new JCheckBox("Terminate");
        genFrame.setSize(1000, 700);
        genFrame.setTitle("Generation frame");
        genFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        genFrame.add(genComp);

        frame.setTitle("Fitness over Generations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(graphComp);
        frame.add(panel, BorderLayout.SOUTH);

        eliteRateField = makeLabel("Elitism%", 3, "1");
        eliteRateField.setToolTipText("Elitism: N% (Enter a numeric value between 0 and 100)");
        rateField = makeLabel("Mutate rate: ", 5, "1");
        rateField.setToolTipText("Mutate Rate: N% (Enter a numeric value between 0 and 100)");

        popSizeField = makeLabel("Population Size: ", 5, "100");
        popSizeField.setToolTipText("Enter an integer > 0");
        genSizeField = makeLabel("Generations: ", 5, "150");
        genSizeField.setToolTipText("Enter an integer > 10");

        JButton clear = new JButton("Clear");
        clear.setToolTipText("Clears the graph");

        JButton help = new JButton("Help");
        help.addActionListener((e) -> {
            JOptionPane.showMessageDialog(frame,
                    "Elitism: what percent of the population is included directly in the next generation\n" +
                    "Mutation Rate: the chance of each cell being mutated\n" +
                    "Population Size: how large the entire population is\n" +
                    "Generations: # of generations to run\n");
        });
        this.t = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                graphComp.repaint();
                frame.repaint();
            }
        });

        String[] selections = { "truncation", "Roulette Wheel", "Rank", "Milestone 4" };
        dropdown = new JComboBox<String>(selections);
        String[] fitnesses = { "Smiley", "Slime", "All Green", "Jack", "twenty 1s", "Clock", "All", "miniJack" };
        fitDrop = new JComboBox<String>(fitnesses);

        panel.add(start);
        panel.add(close);
        panel.add(clear);
        panel.add(dropdown);
        panel.add(crossover);
        panel.add(terminate);
        panel.add(fitDrop);
        panel.add(help);
        dimension.setSize(1300.0, 800.0);
        frame.setSize(dimension);
        genFrame.setVisible(true);

        start.addActionListener((e) -> {
            genFrame.setVisible(true);
            startButtonPressed(e);
            t.restart();
        });
        //Listen for start

    }

    public void startButtonPressed(ActionEvent e) {
        try {
            this.currSelection = "" + dropdown.getSelectedItem();
            this.genSize = getGenSize();
            this.fitnessMethod = factory.getFitnessMethod("" + fitDrop.getSelectedItem());
            this.fitnessMethod.setClock(clock);
            GenParams params = getParams();
            validateInputs(frame, params, genSize);
            if (this.genSize < 10) {
                throw new IllegalArgumentException("Please enter 10 or more generations");
            }
            graphComp.compProp(params.rate, params.popSize, this.genSize, fitnessMethod);
            graphComp.startDrawing();

            if (crossover.isSelected()) {
                genComp.cross();
                graphComp.crossover();
            }

            if (!progRunning) {
                progRunning = true;
            }

            if (terminate.isSelected()) {
                genComp.term();
            }
            Generation g = new Generation(null, getParams(), fitnessMethod);
            graphComp.addGeneration(g,  this.genSize);
            genComp.randomize(this.genSize, g);
            graphComp.nextGen();
            t.start();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "All inputs should be valid numbers");
            errorCount++;
        } catch (Exception ex) {
            errorCount++;
//            System.out.println("custom error should be displayed");
        }
        currentGenerationIndex = 0;
    }
    public int getGenSize() throws NumberFormatException {
        return Integer.parseInt(genSizeField.getText());
    }

    public GenParams getParams() {
        String selection = "";
        if (this.currSelection.charAt(0) == 't') {
            graphComp.clear();
            selection = "t";
        } else if (this.currSelection.charAt(1) == 'o') {
            selection = "ro";
        } else if (this.currSelection.charAt(1) == 'a') {
            selection = "la";
        } else if (this.currSelection.charAt(0) == 'M') {
            selection = "d";
        }
        return new GenParams(Double.parseDouble(rateField.getText()),
                Integer.parseInt(popSizeField.getText()),
                selection,
                Double.parseDouble(eliteRateField.getText()));

    }
    public void validateInputs(JFrame frame, GenParams params, int genSize) throws Exception {
        StringBuilder sb = new StringBuilder();
        if (params.rate < 0 || params.rate > 100) {
            sb.append("mutation rate should be a numeric value between 0 and 100\n");
        }
        if (params.popSize < 100) {
            sb.append("population should be at least 100 \n");
        }
        if (genSize < 10){
            sb.append("generation size should be at least 10\n");
        }
        if (sb.toString().equals("")) {
            return;
        }
        JOptionPane.showMessageDialog(frame, sb.toString());
        throw new Exception();
    }
    public JTextField makeLabel(String labelText, int textFieldNum, String text) {
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField(textFieldNum);
        textField.setText(text);
        panel.add(label);
        panel.add(textField);
        return textField;
    }
    
    public static void main(String[] args) {
        GraphViewer viewer = new GraphViewer();
        viewer.run();
    }
    //Start the program
}
