package mainApp;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.event.ActionEvent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * Class: ChromosomeViewer
 * 
 * @author AJ Hedden Alex Brickley Hollin Glaze
 *         This class is used primarily in milestone 1, this makes the frame,
 *         panel, and
 *         buttons for an individual
 *         chromosome and when run will give you the option to load, save, and
 *         mutate a
 *         chromosome.
 *
 */
public class ChromosomeViewer {
	
	public ChromosomeViewer() {
		viewerMain();
	}
	
	Dimension alpha = new Dimension();

	JFrame frame;

	private void viewerMain() {

		final Dimension FRAMESIZE = new Dimension(500, 500);
		JPanel panel = new JPanel();

		ChromosomeComponent chromosomeComp = new ChromosomeComponent();
		this.frame = new JFrame();

		MouseListener clicker = new ClickListener(chromosomeComp);
		frame.setSize(FRAMESIZE);
		frame.setTitle("Chromosome");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(chromosomeComp);

		JButton load = new JButton("Load");
		JButton save = new JButton("Save");
		JButton mutate = new JButton("Mutate");
		JButton undo = new JButton("Undo");
		JTextField input = new JTextField(5);
		alpha.setSize(500.0, 500.0);
		frame.setSize(alpha);
		panel.add(load);
		panel.add(save);
		panel.add(mutate);
		panel.add(undo);
		panel.add(input);

		frame.add(panel, BorderLayout.SOUTH);

		input.setText("1.0");
		input.setToolTipText("Mutate Rate: N%");

		ButtonListener forced = new ButtonListener(chromosomeComp, "Load");
		load.addActionListener(forced);
		forced.actionPerformed(null);
		mutate.addActionListener((e) -> {
			try {
				chromosomeComp.mutate(Double.parseDouble(input.getText()), Math.random());
			} catch (NumberFormatException broke) {
				System.out.println("Error : please enter a double value");
				broke.printStackTrace();
			}
		});
		undo.addActionListener((e) -> {
			try {
				chromosomeComp.undo();
			} catch (Exception ex) {
				System.out.println("Error : couldn't undo");
			}
		});

		chromosomeComp.addMouseListener(clicker);

		save.addActionListener(new ButtonListener(chromosomeComp, "Save"));
		alpha.setSize(501.0, 501.0);
		frame.setSize(alpha);
	}

	public static void main(String[] args) {

		ChromosomeViewer viewer = new ChromosomeViewer();

	}

	private  int DELAY = 50;
	Timer t = new Timer(DELAY, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			frame.repaint();

		}
	});
}