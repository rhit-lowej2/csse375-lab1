package mainApp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class HomeScreen {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton graphButton = new JButton("Generation");
    JButton chromosomeButton = new JButton("Chromosome");
    
    public HomeScreen() {
        frame.setTitle("Home");
        frame.setSize(400, 72);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel, BorderLayout.NORTH);
        panel.add(chromosomeButton);
        panel.add(graphButton);
        graphButton.addActionListener((e) -> {
        	frame.hide();
        	GraphViewer g = new GraphViewer();
        });
        chromosomeButton.addActionListener((e) -> {
        	frame.hide();
        	ChromosomeViewer g = new ChromosomeViewer();
        });
    }
    
    public static void main(String[] args) {
    	HomeScreen h = new HomeScreen();
    }
}
