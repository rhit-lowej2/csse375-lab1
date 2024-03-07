package mainApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * Class: LoadListener
 * 
 * @author AJ Hedden Alex Brickley Hollin Glaze
 *         This class is what we use to take care of the user loading a new file
 *          once they run ChromosomeViewer. The user can click the load button and then
 *          select and text file consisting of 1's and 0's to then create a green/black
 *          chromosome.
 */
public class LoadListener implements ActionListener {

    ChromosomeComponent compo;
    String filePath;
    String filename;
    JFileChooser fileChooser;

    public void main() {
    }

    public LoadListener(ChromosomeComponent compo) {
        this.compo = compo;
        fileChooser = new JFileChooser();
    }

    private void readFile(String filePath) throws IOException, FileNotFoundException {
        if (filePath != null) {
            FileReader f1 = new FileReader(filePath);
            Scanner scanner = new Scanner(f1);
            while (scanner.hasNext()) {
                try {
                    String line = scanner.nextLine();
                    int[] genetics = new int[line.length()];
                    for (int i = 0; i < line.length(); i++) {
                        genetics[i] = Integer.parseInt("" + line.charAt(i));
                    }
                    compo.setGenes(genetics);
                    compo.writeFile(filename);
                } catch (InputMismatchException e) {
                    System.err.println("File not found! Please select a (.txt) file");
                    e.printStackTrace();
                }
            }
            scanner.close();
            f1.close();
        }
    }
    //Read file and give information to ChromosomeComponent

    @Override
    public void actionPerformed(ActionEvent e) {
        fileChooser.setCurrentDirectory(new File("src\\mainApp\\TextFiles"));
        fileChooser.setDialogTitle("Select a File");

        int end = fileChooser.showOpenDialog(new JFrame());

        if (end == JFileChooser.APPROVE_OPTION) {
            this.filePath = fileChooser.getSelectedFile().getPath();
            this.filename = fileChooser.getSelectedFile().getName();
        }
        try {
            readFile(filePath);
        } catch (FileNotFoundException e1) {
            System.err.println("File " + filename + " not found");
            e1.printStackTrace();
        } catch (IOException e2) {
            System.err.println("Error closing file");
            e2.printStackTrace();
        }
    }
    //Load file and deal with exceptions
}
