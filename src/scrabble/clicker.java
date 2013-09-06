package scrabble;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The mouse listener for the game
 * 
 * @author Chris Good, Ethan Veatch, Dan Schepers Created May 16, 2011.
 */
public class clicker implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		// Sets up click for exchanging
		if (ScrabbleFrame.exchanging) {
			int xClick = e.getX();
			int yClick = e.getY();
			if (xClick > 30 && xClick < 30 + 475) {
				if (yClick > 325 && yClick < 325 + 60) {
					UserHandPanel.clickUserHandPanel(xClick - 30, yClick - 325);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Not used
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Not used
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// If not in the process of exchanging get the positions and 0 base for
		// clicking the user hand panel
		if (!ScrabbleFrame.exchanging) {
			int xClick = e.getX();
			int yClick = e.getY();
			if (xClick > 30 && xClick < 30 + 475) {
				if (yClick > 325 && yClick < 325 + 60) {
					UserHandPanel.clickUserHandPanel(xClick - 30, yClick - 325);
				}
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Send the place released 0 based to ScrabbleBoard to be used
		int xClick = e.getX();
		int yClick = e.getY();
		if (xClick > 30 && xClick < 30 + 300) {
			if (yClick > 15 && yClick < 15 + 300) {
				ScrabbleBoard.clickScrabbleBoard(xClick - 30, yClick - 15);
			}
		}
	}

}
