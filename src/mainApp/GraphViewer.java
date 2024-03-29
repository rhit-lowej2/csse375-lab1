package mainApp;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

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
		graphViewerMain();
	}

    Dimension dimension = new Dimension();
    private double rate = 0.5;
    private int popSize;
    private int genSize;
    private JButton start;
    private JButton close;

    private Timer t;
    private boolean progRunning = false;
    private  int DELAY = 5;

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
    
    private void graphViewerMain() {

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
        this.genFrame = new JFrame();
        this.start = new JButton("Start");
        this.close = new JButton("Close");

        JCheckBox crossover = new JCheckBox("Crossover");
        JCheckBox terminate = new JCheckBox("Terminate");
        genFrame.setSize(1000, 700);
        genFrame.setTitle("Generation frame");
        genFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        genFrame.add(genComp);

        frame.setTitle("Fitness over Generations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(graphComp);
        frame.add(panel, BorderLayout.SOUTH);

        JTextField eliteRate = makeLabel("Elitism%", 3, "1");

        JTextField rate = makeLabel("Mutate rate: ", 5, "1");

        JTextField popSize = makeLabel("Population Size: ", 5, "100");

        JTextField genSize = makeLabel("Generations: ", 5, "150");

        JButton clear = new JButton("Clear");
        this.t = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                graphComp.repaint();
                frame.repaint();
            }
        });

        String[] selections = { "truncation", "Roulette Wheel", "Rank", "Milestone 4" };
        JComboBox<String> dropdown = new JComboBox<String>(selections);
        String[] fitnesses = { "Smiley", "Slime", "All Green", "Jack", "twenty 1s", "Clock", "All", "miniJack" };
        JComboBox<String> fitDrop = new JComboBox<String>(fitnesses);

        panel.add(start);
        panel.add(close);
        panel.add(clear);
        panel.add(dropdown);
        panel.add(crossover);
        panel.add(terminate);
        panel.add(fitDrop);
        dimension.setSize(1300.0, 800.0);
        frame.setSize(dimension);
        genFrame.setVisible(true);

        start.addActionListener((e) -> {
            genFrame.setVisible(true);
            try {
                this.currSelection = "" + dropdown.getSelectedItem();
                this.rate = Double.parseDouble(rate.getText());
                this.popSize = Integer.parseInt(popSize.getText());
                this.genSize = Integer.parseInt(genSize.getText());
                this.fitnessMethod = factory.getFitnessMethod("" + fitDrop.getSelectedItem());
                this.fitnessMethod.setClock(clock);

                if (this.genSize < 10) {
                    throw new IllegalArgumentException("Please enter 10 or more generations");
                }
                graphComp.compProp(this.rate, this.popSize, this.genSize, fitnessMethod);
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
                if (this.currSelection.charAt(0) == 't') {
                    graphComp.randomize(this.rate, this.popSize, this.genSize,
                            Double.parseDouble(eliteRate.getText()), fitnessMethod);
                    genComp.randomize(this.rate, "t", this.genSize, this.popSize,
                            Double.parseDouble(eliteRate.getText()), fitnessMethod);
                } else if (this.currSelection.charAt(1) == 'o') {
                    graphComp.roulette(this.rate, this.popSize, this.genSize,
                            Double.parseDouble(eliteRate.getText()), fitnessMethod);
                    genComp.randomize(this.rate, "ro", this.genSize, this.popSize,
                            Double.parseDouble(eliteRate.getText()), fitnessMethod);
                } else if (this.currSelection.charAt(1) == 'a') {
                    graphComp.ranking(this.rate, this.popSize, this.genSize,
                            Double.parseDouble(eliteRate.getText()), fitnessMethod);
                    genComp.randomize(this.rate, "la", this.genSize, this.popSize,
                            Double.parseDouble(eliteRate.getText()), fitnessMethod);
                } else if (this.currSelection.charAt(0) == 'M') {
                    System.out.println(currSelection);
                    StringCompareFitnessMethod debug = new StringCompareFitnessMethod(FitnessMethod.GREEN);
                    debug.setDebug(true);
                    graphComp.dancingQueen(this.popSize, this.genSize, debug);
                }
                graphComp.nextGen();
                t.start();

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            }
            currentGenerationIndex = 0;
            t.restart();
        });
        //Listen for start

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
    }
    //Start the program
}
