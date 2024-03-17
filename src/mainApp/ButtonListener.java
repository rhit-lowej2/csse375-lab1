package mainApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener{

	private FileHelper f;
	String type;
	
	public ButtonListener(ChromosomeComponent component, String type) {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(type.compareTo("Save") == 0) {
			f.saveFile();
		}else {
			f.readFile();
		}
	}
	

}
