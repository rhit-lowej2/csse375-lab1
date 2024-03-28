package mainApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileHelper {
    private ChromosomeComponent component;
    private String filePath;
    private String filename;
    private JFileChooser fileChooser;
    private File file;
    private int[] contents;
    
	public FileHelper(ChromosomeComponent component) {
        this.component = component;
        fileChooser = new JFileChooser();
	}
	
	public void readFile() {
        fileChooser.setCurrentDirectory(new File("src\\mainApp\\TextFiles"));
        fileChooser.setDialogTitle("Select a File");

        int end = fileChooser.showOpenDialog(new JFrame());

        if (end == JFileChooser.APPROVE_OPTION) {
            this.filePath = fileChooser.getSelectedFile().getPath();
            this.filename = fileChooser.getSelectedFile().getName();
        }
        try {
            parseFile(filePath);
        } catch (FileNotFoundException e1) {
            System.err.println("File " + filename + " not found");
            e1.printStackTrace();
        } catch (IOException e2) {
            System.err.println("Error closing file");
            e2.printStackTrace();
        }
	}
	
	public void saveFile() {
        fileChooser.setCurrentDirectory(new java.io.File("src\\mainApp\\TextFiles"));
        fileChooser.setDialogTitle("Save the file");

        int end = fileChooser.showSaveDialog(new JFrame());

        if (end == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            filename = file.getName();

            try {
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
	
    private void parseFile(String filePath) throws IOException, FileNotFoundException {
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
                    component.setGenes(genetics);
                    component.writeFile(filename);
                } catch (InputMismatchException e) {
                    System.err.println("File not found! Please select a (.txt) file");
                    e.printStackTrace();
                }
            }
            scanner.close();
            f1.close();
        }
    }
}
