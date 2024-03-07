package mainApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JFileChooser;

/**
 * Class: SaveListener
 * 
 * @author AJ Hedden Alex Brickley Hollin Glaze
 *         This class allows the user to save whatever chromosome they have been working with/mutating.
 *         The user will click the save button and can name the text file whatever they wish.
 *         Saving this file will store a text file consisting of 1's and 0's as defined by the colors/genes
 *         in the chromosome on screen. These files can then be reaccessed and edited later if the user desires.
 */
public class SaveListener implements ActionListener {
    ChromosomeComponent component;
    JFileChooser chooser;
    String filename;
    File file;
    FileWriter newFile;
    int[] contents;

    public SaveListener(ChromosomeComponent component) {
        this.component = component;
        chooser = new JFileChooser();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        chooser.setCurrentDirectory(new java.io.File("src\\mainApp\\TextFiles"));
        chooser.setDialogTitle("Save the file");

        int end = chooser.showSaveDialog(new JFrame());

        if (end == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            filename = file.getName();

            try {
                newFile = new FileWriter(file);
                PrintWriter writer = new PrintWriter(file);
                contents = component.getGenes();
                for (int i = 0; i < contents.length; i++) {
                    writer.print(contents[i]);
                }
                writer.close();
            } catch (IOException ea) {
                System.out.println("Error: Your file can not be saved.");
                ea.printStackTrace();
            }
        }
    }
    //Save file and deal with exceptions

}
