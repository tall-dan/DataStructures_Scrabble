package scrabble;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.io.IOException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.Font;
import java.util.ArrayList;

/**
 * Provides a simple GUI for interacting with a Scrabble object
 * 
 * @author Amanda Stephan, modified by Claude Anderson. Major refactoring by
 *         Matt Boutell and Claude Anderson, April 2011.
 * 
 *         Modified by team 23 consisting of Ethan Veatch, Daniel Schepers, and
 *         Christopher A Good
 */

public class ScrabbleFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_DICTIONARY = "scrabbleDictionaries/dictionary01.sd";
	private Scrabble currentScrabble;
	private ScrabbleDictionary dictionary;

	/**
	 * Panel that buttons are displayed on
	 */
	protected JPanel jContentPane = null;

	/** Color for a Triple Word Score space */
	public static final Color tripleWordScoreColor = new Color(255, 0, 0);
	/** Color for a Triple Letter Score space */
	public static final Color tripleLetterScoreColor = new Color(35, 35, 255); // @jve:decl-index=0:
	/** Color for a Double Word Score space */
	public static final Color doubleWordScoreColor = new Color(255, 153, 153); // @jve:decl-index=0:
	/** Color for a Double Letter Score space */
	public static final Color doubleLetterScoreColor = new Color(153, 153, 255);
	/** Color for a tile space in user's hand. */
	public static final Color tileColor = new Color(255, 204, 102);

	private UserHandPanel userHand = null;
	private ScrabbleBoard scrabbleBoard = null;

	private JLabel tripleWordScoreLabel = null;
	private JLabel doubleWordScoreLabel = null;
	private JLabel tripleLetterScoreLabel = null;
	private JLabel doubleLetterScoreLabel = null;
	private JButton stepOnceButton = null;
	private JButton playGameButton = null;
	private JButton quitButton = null;
	private JButton loadGameButton = null;
	private JLabel scoreLabel = null;
	private JLabel curScoreLabel = null;
	private JLabel scoreTurnLabel = null;
	private JLabel scoreThisTurnLabel = null;
	private JButton doneButton;
	private JButton letMePlay;
	/**
	 * the last game state
	 */
	protected GameState lastGameState;
	private JButton revertButton;
	private JButton exchangeButton;
	private JButton newGameButton;

	/**
	 * if word played is vertical or horizontal
	 */
	static boolean isVertical = false;
	/**
	 * are we exchanging characters?
	 */
	protected static boolean exchanging = false;
	/**
	 * the mouse listener for the game
	 */
	protected static MouseListener clicker = new clicker();
	/**
	 * if the last word has been counted
	 */
	protected static boolean hasBeenScored;
	/**
	 * Has a move been started?
	 */
	protected static boolean startedPlay = false;

	/**
	 * Create the scrabble GUI, loading a new standard game.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			new ScrabbleFrame().setVisible(true);

		} catch (Exception e) {
			System.out.println("Cannot create initial game");
			e.printStackTrace();
		}
	}

	/**
	 * Is this the first word added to the board?
	 */
	protected static boolean firstWord = true;

	/**
	 * A default constructor that will create an empty default board with a
	 * randomly-ordered tilebag (once you have implemented shuffling)
	 * 
	 * @throws Exception
	 *             If any method it calls threw an exception. Could make this
	 *             more robust.
	 */
	public ScrabbleFrame() throws Exception {
		super();
		// ScrabbleComponent component = new ScrabbleComponent();
		// add(component);
		this.dictionary = new ScrabbleDictionary(
				ScrabbleFrame.DEFAULT_DICTIONARY);
		this.currentScrabble = new Scrabble(this.dictionary);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initializeGuiComponents();
		connectGUItoGsme(this.currentScrabble);
	}

	/**
	 * Creates the GUI components, but does not give them any info about a
	 * particular Scrabble game.
	 */
	private void initializeGuiComponents() {
		// 
		this.setSize(540, 665); // Resized for extra buttons
		this.setTitle("Scrabble Game");
		this.setContentPane(getJContentPane());
	}

	/**
	 * Adds information fomr the state of the Scrabble game to this GUI.
	 * 
	 * @param sc
	 *            the Scrabble game to connect.
	 */
	private void connectGUItoGsme(Scrabble sc) {
		this.jContentPane.remove(this.scrabbleBoard);
		this.scrabbleBoard = new ScrabbleBoard(sc.getBoardConfiguration(), sc
				.getBoardSize());
		this.jContentPane.add(this.scrabbleBoard, null);

		this.jContentPane.remove(this.userHand);
		System.out.println(sc.getTilesInHand());
		this.userHand = new UserHandPanel(sc.getTilesInHand(), sc
				.getMaxHandSize());
		this.jContentPane.add(this.userHand, null);
		updateBoardFromGameState(sc.getCurrentGameState());
	}

	private void initializeGUIFromFile() {
		JFileChooser chooser = new JFileChooser();
		String filePath;
		chooser.setCurrentDirectory(new java.io.File("scrabbleFiles/"));
		chooser.setDialogTitle("Please select a test file.");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			filePath = chooser.getSelectedFile().getPath();
			try {
				if (filePath.contains(".scrabble")) {
					this.currentScrabble = new Scrabble(this.dictionary,
							filePath);
					if (this.currentScrabble != null) {
						this.playGameButton.setEnabled(true);
						this.stepOnceButton.setEnabled(true);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();

			}
			if (this.currentScrabble != null) {
				connectGUItoGsme(this.currentScrabble);
			}
		}
	}

	// Currently does not call gs.lastWordPlayed. You may want to fix that.

	/**
	 * Updates the board from the given GameState object.
	 * 
	 * @param gs
	 */
	protected void updateBoardFromGameState(GameState gs) {
		if (gs != null) {
			ScrabbleBoard.changeCharactersOnBoard(gs.getBoardChars());
			UserHandPanel.changeTilesInHand(gs.getTilesInHand());
			this.curScoreLabel.setText(gs.getTotalScore() + "");
			this.scoreThisTurnLabel.setText(gs.getScoreThisPlay() + "");
			this.repaint();
		} else {
			System.out.println("GameState to display is invalid");
		}
	}

	/**
	 * This method initializes the play for me. The method length is neccesary
	 * due to multiple cases and all work being contained. While it could be
	 * refactored, it is not useful to the point of the method
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getPlayForMe() {
		if (this.stepOnceButton == null) {
			this.stepOnceButton = new JButton();
			this.stepOnceButton.setText("Play For Me");
			this.stepOnceButton.setSize(new Dimension(143, 50));
			this.stepOnceButton.setMnemonic(KeyEvent.VK_UNDEFINED);
			this.stepOnceButton.setLocation(new Point(30, 400));
			this.stepOnceButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (!ScrabbleFrame.exchanging
									&& !ScrabbleFrame.startedPlay
									&& !ScrabbleFrame.firstWord) {
								ScrabbleFrame.startedPlay = true;
								GameState gs = ScrabbleFrame.this.currentScrabble
										.playOnce();
								if (gs == null) {
									JOptionPane.showMessageDialog(null,
											"No play can be made.",
											"Game Over",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									updateBoardFromGameState(gs);
									ScrabbleFrame.startedPlay = false;
									for (int i = 0; i < 15; i++) {
										for (int j = 0; j < 15; j++) {
											if (Scrabble.boardPieces[i][j]
													.isOccupied()) {
												ScrabbleBoard
														.setBackgroundAtPosition(
																i, j,
																new Color(0, 0,
																		0, 0));

											}
										}
									}
									for (BoardSpace space : Scrabble.newLettersPlayed) {
										ScrabbleBoard
												.setBackgroundAtPosition(space
														.getxPos(), space
														.getyPos(), Color.green);
									}
									Scrabble.newLettersPlayed.clear();
								}
							} else if (!ScrabbleFrame.exchanging
									&& !ScrabbleFrame.startedPlay) {

								ScrabbleFrame.startedPlay = true;
								ScrabbleFrame.firstWord = false;
								ScrabbleFrame.this.currentScrabble
										.playFirstTimeComputer();
								for (BoardSpace space : Scrabble.newLettersPlayed) {
									ScrabbleBoard.setBackgroundAtPosition(space
											.getxPos(), space.getyPos(),
											Color.green);
								}
								Scrabble.newLettersPlayed.clear();

								ScrabbleFrame.this.currentScrabble
										.evaluateScore();
								if (Scrabble.tilesInHand.size() == 0) {
									Scrabble.scoreThisTurn += 50;
									Scrabble.totalScore += 50;
								}
								// makes sure there are enough tiles in the bag
								// to
								// display. If not, display the most it can
								int numberOfTilesToDraw = Math.min(
										ScrabbleFrame.this.currentScrabble
												.getTileBag().size(),
										Scrabble.DEFAULT_MAX_HAND_SIZE
												- Scrabble.tilesInHand.size());
								for (int i = 0; i < numberOfTilesToDraw; i++) {
									ScrabbleFrame.this.currentScrabble
											.drawTile();
								}
								ScrabbleFrame.hasBeenScored = true;
								ScrabbleFrame.firstWord = false;
								ScrabbleFrame.startedPlay = false;
								updateBoardFromGameState(ScrabbleFrame.this.currentScrabble
										.getCurrentGameState());
							}
						}
					});
		}
		return this.stepOnceButton;
	}

	/**
	 * This method initializes playGameButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getPlayGameButton() {
		if (this.playGameButton == null) {
			this.playGameButton = new JButton();
			// playGameButton.setFont(new Font(null, Font.PLAIN, 10));
			this.playGameButton.setLayout(new BorderLayout(0, 0));
			this.playGameButton.setText("Play Rest Of Game");
			this.playGameButton.setEnabled(true);
			this.playGameButton.setSize(new Dimension(143, 50));
			this.playGameButton.setLocation(new Point(193, 400));
			this.playGameButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							ArrayList<GameState> states = ScrabbleFrame.this.currentScrabble
									.playGame();
							if (states == null) {
								System.out
										.println("ArrayList of GameStates is null");
							} else {
								updateBoardFromGameState(states.get(states
										.size() - 1));
							}
						}
					});
		}
		return this.playGameButton;
	}

	/**
	 * This method initializes quitButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getQuitButton() {
		if (this.quitButton == null) {
			this.quitButton = new JButton();
			this.quitButton.setText("Quit");
			this.quitButton.setSize(new Dimension(143, 50));
			this.quitButton.setLocation(new Point(356, 550));
			this.quitButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.exit(0);
						}
					});
		}
		return this.quitButton;
	}

	/**
	 * This method initializes loadGameButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getLoadGameButton() {
		if (this.loadGameButton == null) {
			this.loadGameButton = new JButton();
			this.loadGameButton.setText("Load Game");
			this.loadGameButton.setSize(new Dimension(143, 50));
			this.loadGameButton.setLocation(new Point(30, 550));
			this.loadGameButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							initializeGUIFromFile();
						}
					});
		}
		return this.loadGameButton;
	}

	// Creates the button and listener for exchange
	private JButton getExchangeButton() {
		if (this.exchangeButton == null) {
			this.exchangeButton = new JButton();
			this.exchangeButton.setText("Exchange Tiles");
			this.exchangeButton.setSize(new Dimension(143, 50));
			this.exchangeButton.setLocation(new Point(193, 475));
			this.exchangeButton
					.addActionListener(new java.awt.event.ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (!ScrabbleFrame.exchanging
									&& !ScrabbleFrame.startedPlay) {
								Scrabble.toExchange.clear();
								ScrabbleFrame.exchanging = true;
								ScrabbleFrame.this.doneButton
										.setText("Done Exchanging");
								ScrabbleFrame.this.jContentPane
										.addMouseListener(ScrabbleFrame.clicker);

							}
						}

					});
		}
		return this.exchangeButton;
	}

	private JButton getLetMePlayButton() {
		if (this.letMePlay == null) {
			this.letMePlay = new JButton();
			this.letMePlay.setText("Let Me Play");
			this.letMePlay.setSize(new Dimension(143, 50));
			this.letMePlay.setLocation(new Point(356, 400));
			this.letMePlay.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (!ScrabbleFrame.startedPlay & !ScrabbleFrame.exchanging) {
						ScrabbleFrame.this.doneButton.setText("Done Playing");
						ScrabbleFrame.startedPlay = true;
						ScrabbleFrame.hasBeenScored = false;
						Scrabble.lastLettersPlayed = new ArrayList<BoardSpace>(
								Scrabble.lettersPlayed);
						Scrabble.lettersPlayed.clear();
						ScrabbleFrame.this.lastGameState = new GameState(
								ScrabbleFrame.this.currentScrabble
										.getCurrentGameState());
						ScrabbleFrame.this.jContentPane
								.addMouseListener(ScrabbleFrame.clicker);
					}
				}
			});
		}
		return this.letMePlay;
	}

	// 

	/**
	 * 
	 *Creates the button and listener for done, and starts the process of
	 * checking input
	 * 
	 * @return
	 */
	protected JButton getDoneButton() {
		if (this.doneButton == null) {
			this.doneButton = new JButton();
			this.doneButton.setText("Done");
			this.doneButton.setSize(new Dimension(143, 50));
			this.doneButton.setLocation(new Point(30, 475));
			this.doneButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					ScrabbleFrame.this.doneButton.setText("Done");
					if (!ScrabbleFrame.exchanging && ScrabbleFrame.startedPlay) {
						// If the play is a legal play continue
						if (ScrabbleFrame.this.currentScrabble.checkLegality()) {
							// Changes the color of tiles that are over a turn
							// old
							for (int i = 0; i < 15; i++) {
								for (int j = 0; j < 15; j++) {
									if (Scrabble.boardPieces[i][j].isOccupied()) {
										ScrabbleBoard.setBackgroundAtPosition(
												i, j, new Color(0, 0, 0, 0));
									}
								}
							}
							for (BoardSpace space : Scrabble.newLettersPlayed) {
								ScrabbleBoard.setBackgroundAtPosition(space
										.getxPos(), space.getyPos(),
										Color.green);
							}
							// for (BoardSpace space :
							// Scrabble.lastLettersPlayed) {
							// ScrabbleBoard.setBackgroundAtPosition(space
							// .getxPos(), space.getyPos(), new Color(
							// 0, 0, 0, 0));
							// }
							Scrabble.newLettersPlayed.clear();
							ScrabbleFrame.this.currentScrabble.evaluateScore();
							// makes sure there are enough tiles in the bag to
							// display. If not, display the most it can
							int numberOfTilesToDraw = Math.min(
									ScrabbleFrame.this.currentScrabble
											.getTileBag().size(),
									Scrabble.DEFAULT_MAX_HAND_SIZE
											- Scrabble.tilesInHand.size());
							for (int i = 0; i < numberOfTilesToDraw; i++) {
								ScrabbleFrame.this.currentScrabble.drawTile();
							}
							updateBoardFromGameState(ScrabbleFrame.this.currentScrabble
									.getCurrentGameState());
							ScrabbleFrame.hasBeenScored = true;
							ScrabbleFrame.firstWord = false;
						} else {
							// If the play is not legal revert the play
							Scrabble.newLettersPlayed.clear();
							ScrabbleFrame.this.currentScrabble
									.reverseGameState(ScrabbleFrame.this.lastGameState);
							updateBoardFromGameState(ScrabbleFrame.this.lastGameState);
						}
						ScrabbleFrame.startedPlay = false;
						// Remove the mouse listener so that you can not
						// make more moves
						ScrabbleFrame.this.jContentPane
								.removeMouseListener(ScrabbleFrame.clicker);
					} else if (ScrabbleFrame.exchanging) {

						ScrabbleFrame.this.currentScrabble
								.returnTiles(Scrabble.toExchange);
						updateBoardFromGameState(ScrabbleFrame.this.currentScrabble
								.getCurrentGameState());
						ScrabbleFrame.this.jContentPane
								.removeMouseListener(ScrabbleFrame.clicker);
						ScrabbleFrame.exchanging = false;
					}
				}

			});
		}
		return this.doneButton;
	}

	// Sets up the button and listener for revert
	private JButton getRevertButton() {
		if (this.revertButton == null) {
			this.revertButton = new JButton();
			this.revertButton.setText("Undo");
			this.revertButton.setSize(new Dimension(143, 50));
			this.revertButton.setLocation(356, 475);
			this.revertButton.addActionListener(new ActionListener() {

				// reverts to the last play
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!hasBeenScored && !ScrabbleFrame.exchanging) {
						ScrabbleFrame.this.currentScrabble
								.reverseGameState(ScrabbleFrame.this.lastGameState);
						updateBoardFromGameState(ScrabbleFrame.this.currentScrabble
								.getCurrentGameState());
						Scrabble.newLettersPlayed.clear();
						// Simulate a done click
						ScrabbleFrame.this.doneButton.doClick();
					}
				}
			});
		}
		return this.revertButton;
	}

	private JButton getNewGameButton() {
		if (this.newGameButton == null) {
			this.newGameButton = new JButton();
			this.newGameButton.setText("New Game");
			this.newGameButton.setSize(new Dimension(143, 50));
			this.newGameButton.setLocation(193, 550);
			this.newGameButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String newGame = "scrabbleFiles/NewGame.scrabble";

					if (newGame.contains(".scrabble")) {
						try {
							ScrabbleFrame.this.currentScrabble = new Scrabble(
									ScrabbleFrame.this.dictionary, newGame);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

					if (ScrabbleFrame.this.currentScrabble != null) {
						connectGUItoGsme(ScrabbleFrame.this.currentScrabble);
					}
					ScrabbleFrame.this.currentScrabble.shuffle();
					ScrabbleFrame.this.currentScrabble
							.returnTiles(new ArrayList<Character>(
									ScrabbleFrame.this.currentScrabble
											.getTilesInHand()));
					ScrabbleFrame.this
							.updateBoardFromGameState(ScrabbleFrame.this.currentScrabble
									.getCurrentGameState());
				}
			});
		}
		return this.newGameButton;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (this.jContentPane == null) {
			this.scoreThisTurnLabel = new JLabel();
			this.scoreThisTurnLabel.setText("");
			this.scoreThisTurnLabel.setSize(new Dimension(150, 50));
			this.scoreThisTurnLabel
					.setHorizontalAlignment(SwingConstants.CENTER);
			this.scoreThisTurnLabel.setFont(new Font("Dialog", Font.BOLD, 36));
			this.scoreThisTurnLabel.setLocation(new Point(345, 125));
			this.scoreTurnLabel = new JLabel();
			this.scoreTurnLabel.setText("Score This Turn:");
			this.scoreTurnLabel.setSize(new Dimension(150, 30));
			this.scoreTurnLabel.setFont(new Font("Dialog", Font.BOLD, 18));
			this.scoreTurnLabel.setLocation(new Point(345, 95));
			this.curScoreLabel = new JLabel();
			this.curScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
			this.curScoreLabel.setFont(new Font("Dialog", Font.BOLD, 36));
			this.curScoreLabel.setSize(new Dimension(150, 44));
			this.curScoreLabel.setLocation(new Point(345, 45));
			this.curScoreLabel.setText("");
			this.scoreLabel = new JLabel();
			this.scoreLabel.setFont(new Font("Dialog", Font.BOLD, 24));
			this.scoreLabel.setSize(new Dimension(150, 30));
			this.scoreLabel.setLocation(new Point(345, 15));
			this.scoreLabel.setText("Total Score:");
			this.doubleLetterScoreLabel = new JLabel();
			this.doubleLetterScoreLabel.setText("Double Letter Score");
			this.doubleLetterScoreLabel.setSize(new Dimension(150, 25));
			this.doubleLetterScoreLabel.setBackground(doubleLetterScoreColor);
			this.doubleLetterScoreLabel
					.setHorizontalAlignment(SwingConstants.CENTER);
			this.doubleLetterScoreLabel.setLocation(new Point(345, 285));
			this.doubleLetterScoreLabel.setOpaque(true);
			this.tripleLetterScoreLabel = new JLabel();
			this.tripleLetterScoreLabel.setText("Triple Letter Score");
			this.tripleLetterScoreLabel.setSize(new Dimension(150, 25));
			this.tripleLetterScoreLabel.setBackground(tripleLetterScoreColor);
			this.tripleLetterScoreLabel
					.setHorizontalAlignment(SwingConstants.CENTER);
			this.tripleLetterScoreLabel.setLocation(new Point(345, 250));
			this.tripleLetterScoreLabel.setOpaque(true);
			this.doubleWordScoreLabel = new JLabel();
			this.doubleWordScoreLabel.setPreferredSize(new Dimension(150, 60));
			this.doubleWordScoreLabel.setLocation(new Point(345, 215));
			this.doubleWordScoreLabel.setSize(new Dimension(150, 25));
			this.doubleWordScoreLabel.setBackground(doubleWordScoreColor);
			this.doubleWordScoreLabel
					.setHorizontalAlignment(SwingConstants.CENTER);
			this.doubleWordScoreLabel.setText("Double Word Score");
			this.doubleWordScoreLabel.setOpaque(true);
			this.tripleWordScoreLabel = new JLabel();
			this.tripleWordScoreLabel.setText("Triple Word Score");
			this.tripleWordScoreLabel.setSize(new Dimension(150, 25));
			this.tripleWordScoreLabel.setBackground(tripleWordScoreColor);
			this.tripleWordScoreLabel
					.setHorizontalAlignment(SwingConstants.CENTER);
			this.tripleWordScoreLabel.setLocation(new Point(345, 180));
			this.tripleWordScoreLabel.setOpaque(true);
			this.jContentPane = new JPanel();
			this.jContentPane.setLayout(null);
			this.scrabbleBoard = new ScrabbleBoard();
			this.userHand = new UserHandPanel();
			this.jContentPane.add(this.scrabbleBoard, null);
			this.jContentPane.add(this.userHand, null);
			this.jContentPane.add(this.tripleWordScoreLabel, null);
			this.jContentPane.add(this.doubleWordScoreLabel, null);
			this.jContentPane.add(this.tripleLetterScoreLabel, null);
			this.jContentPane.add(this.doubleLetterScoreLabel, null);
			this.jContentPane.add(getPlayForMe(), null);
			this.jContentPane.add(getPlayGameButton(), null);
			this.jContentPane.add(getQuitButton(), null);
			this.jContentPane.add(getLoadGameButton(), null);
			this.jContentPane.add(this.scoreLabel, null);
			this.jContentPane.add(this.curScoreLabel, null);
			this.jContentPane.add(this.scoreTurnLabel, null);
			this.jContentPane.add(this.scoreThisTurnLabel, null);
			this.jContentPane.add(getLetMePlayButton(), null);
			this.jContentPane.add(getDoneButton(), null);
			this.jContentPane.add(getRevertButton(), null);
			this.jContentPane.add(getExchangeButton(), null);
			this.jContentPane.add(getNewGameButton(), null);

		}
		return this.jContentPane;
	}
}

/**
 * @author stephaap
 * 
 */
class ScrabbleBoard extends JPanel {

	private static final long serialVersionUID = 1L;
	/**
	 * the labels of the board
	 */
	static JLabel[][] boardLabels;

	/**
	 * Creates a default Scrabble board
	 */
	public ScrabbleBoard() {
		this(Scrabble.DEFAULT_BOARD_LAYOUT, Scrabble.DEFAULT_BOARD_SIZE);
	}

	/**
	 * Creates a new ScrabbleBoard dependent on the size and layout passed in.
	 * 
	 * @param boardLayout
	 *            - the layout of the special spaces on the board (Tripe Word
	 *            Score, Double Letter Score, etc).
	 * @param boardSize
	 *            - size of the board (must be odd)
	 */
	public ScrabbleBoard(char[][] boardLayout, int boardSize) {
		super();
		this.setSize(300, 300);
		this.setLocation(30, 15);
		GridLayout layout = new GridLayout(boardSize, boardSize);
		this.setLayout(layout);
		LineBorder border = new LineBorder(Color.black, 1);
		ScrabbleBoard.boardLabels = new JLabel[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				ScrabbleBoard.boardLabels[i][j] = new JLabel();
				ScrabbleBoard.boardLabels[i][j].setOpaque(true);
				ScrabbleBoard.boardLabels[i][j]
						.setHorizontalAlignment(SwingConstants.CENTER);
				ScrabbleBoard.boardLabels[i][j].setBorder(border);
				if (boardLayout == null) {
					// do nothing
				} else if (boardLayout[i][j] == 'T') {
					ScrabbleBoard.boardLabels[i][j]
							.setBackground(ScrabbleFrame.tripleWordScoreColor);
				} else if (boardLayout[i][j] == 't') {
					ScrabbleBoard.boardLabels[i][j]
							.setBackground(ScrabbleFrame.tripleLetterScoreColor);
				} else if (boardLayout[i][j] == 'D' || boardLayout[i][j] == '*') {
					ScrabbleBoard.boardLabels[i][j]
							.setBackground(ScrabbleFrame.doubleWordScoreColor);
				} else if (boardLayout[i][j] == 'd') {
					ScrabbleBoard.boardLabels[i][j]
							.setBackground(ScrabbleFrame.doubleLetterScoreColor);
				}
				this.add(ScrabbleBoard.boardLabels[i][j]);
			}
		}
	}

	/**
	 * Find which square the user clicked on
	 * 
	 * @param x
	 * @param y
	 */
	static void clickScrabbleBoard(int x, int y) {

		int boardWidth = 300;
		int numberOfPieces = 15;
		int pieceWidth = boardWidth / numberOfPieces;
		int xIndex = x / pieceWidth;
		int yIndex = y / pieceWidth;
		Scrabble.setBoardSpot(xIndex, yIndex);

	}

	/**
	 * change the background of played pieces
	 * 
	 * @param x
	 * @param y
	 * @param color
	 */
	static void setBackgroundAtPosition(int x, int y, Color color) {

		ScrabbleBoard.boardLabels[x][y].setBackground(color);

	}

	/**
	 * Updates the JLabels that contain the characters to be displayed.
	 * 
	 * @param boardChars
	 *            - character arrangement on the board
	 */
	static void changeCharactersOnBoard(char[][] boardChars) {
		for (int i = 0; i < boardChars.length; i++) {
			if (boardChars.length != boardChars[i].length) {
				try {
					throw new Exception("Jagged Array");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				for (int j = 0; j < boardChars[i].length; j++) {
					if (boardChars[i][j] != 'b')
						ScrabbleBoard.boardLabels[i][j]
								.setText(boardChars[i][j] + "");
					else {
						ScrabbleBoard.boardLabels[i][j].setText("_");
					}
				}
			}
		}
	}

}

/**
 * @author stephaap
 * 
 */
class UserHandPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * The labels of the user hand
	 */
	static JLabel[] userHandLabels;

	/**
	 * This is the default constructor
	 */
	public UserHandPanel() {
		this(null, Scrabble.DEFAULT_MAX_HAND_SIZE);
	}

	/**
	 * Creates a new JPanel that contains the JLabels to be displayed to
	 * represent the user's hand.
	 * 
	 * @param tiles
	 *            - tiles held by user
	 * @param maxTilesInHand
	 *            - maximum number of tiles user can hold at a time
	 */
	public UserHandPanel(ArrayList<Character> tiles, int maxTilesInHand) {
		super();
		this.setSize(new Dimension(475, 60)); // CWA: If this is to be at a
		// fixed location,
		this.setLocation(new Point(30, 325)); // probably should be above the
		// board instead of
		GridLayout layout = new GridLayout(); // below it, to allow for
		// different board sizes.
		layout.setColumns(maxTilesInHand); // if below the board, it would be
		// better to calculate
		layout.setRows(1); // the location.
		layout.setHgap(20); // Most of the numbers here should be variables or
		layout.setVgap(15); // named constants, instead of magic numbers.
		this.setLayout(layout);

		UserHandPanel.userHandLabels = new JLabel[maxTilesInHand];
		for (int i = 0; i < UserHandPanel.userHandLabels.length; i++) {
			if (tiles != null && i < tiles.size()) {
				if (tiles.get(i) == 'b') {
					UserHandPanel.userHandLabels[i] = new JLabel(" ");
				} else {
					UserHandPanel.userHandLabels[i] = new JLabel(tiles.get(i)
							+ "");
				}
				UserHandPanel.userHandLabels[i].setVisible(true);
			} else {
				UserHandPanel.userHandLabels[i] = new JLabel();
				UserHandPanel.userHandLabels[i].setVisible(false);
			}
			UserHandPanel.userHandLabels[i]
					.setBackground(ScrabbleFrame.tileColor);
			UserHandPanel.userHandLabels[i].setOpaque(true);
			UserHandPanel.userHandLabels[i]
					.setHorizontalAlignment(SwingConstants.CENTER);
			UserHandPanel.userHandLabels[i].setFont(new Font("Dialog",
					Font.BOLD, 24));

			this.add(UserHandPanel.userHandLabels[i]);
		}
	}

	// Takes a click position, 0 based, and finds the character in the position
	/**
	 * Find which tile the user clicked
	 * 
	 * @param x
	 * @param y
	 */
	static void clickUserHandPanel(int x, int y) {
		int width = 475;
		int numberOfTiles = 7;
		int emptySpaceWidth = 20;
		int tileWidth = (width - ((numberOfTiles - 1) * emptySpaceWidth))
				/ numberOfTiles;
		for (int i = 0; i < numberOfTiles; i++) {
			if (x > i * (tileWidth + emptySpaceWidth)
					&& x < i * (tileWidth + emptySpaceWidth) + tileWidth) {
				Scrabble.setClickTile(UserHandPanel.userHandLabels[i].getText()
						.charAt(0));
			}
		}
	}

	/**
	 * Updates the JLabels that display the tiles in the user's hand.
	 * 
	 * @param tiles
	 *            - tiles currently held by user
	 */
	static void changeTilesInHand(ArrayList<Character> tiles) {
		for (int i = 0; i < UserHandPanel.userHandLabels.length; i++) {
			if (i < tiles.size()) {
				UserHandPanel.userHandLabels[i].setText(tiles.get(i) + "");
				UserHandPanel.userHandLabels[i].setVisible(true);
			} else {
				UserHandPanel.userHandLabels[i].setText("");
				UserHandPanel.userHandLabels[i].setVisible(false);
			}
		}

	}
}
