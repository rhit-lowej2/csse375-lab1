package mainApp;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

/**
 * Class: ClickListener
 * 
 * @author AJ Hedden Alex Brickley Hollin Glaze
 *         This class is used primarily in milestone 1 to detect where exactly
 *         the user is clicking on the screen
 *         If the user clicks on a gene of a chromosome, it will switch colors
 *         and update the gene in chromosome.
 *
 */
public class ClickListener implements MouseListener {

	private ChromosomeComponent component;

	public ClickListener(ChromosomeComponent component) {
		this.component = component;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			component.changeColor(e.getX(), e.getY());
			component.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}