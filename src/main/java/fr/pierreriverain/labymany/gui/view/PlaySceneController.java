package fr.pierreriverain.labymany.gui.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import fr.pierreriverain.keyboard.KeyboardScreen;
import fr.pierreriverain.keyboard.PlayerControls;
import fr.pierreriverain.maze.Maze;
import fr.pierreriverain.maze.MazeCellDoor;
import fr.pierreriverain.maze.MazeCursor;
import fr.pierreriverain.maze.MazeGenerationEvent;
import fr.pierreriverain.maze.MazeGenerationListener;
import fr.pierreriverain.maze.MazePathfinding;
import fr.pierreriverain.maze.MazePictureGenerator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * Cette classe permet de gérer l'interfance graphique du jeu avec des animations d'apparition et de disparitions sur les composants graphique de cette scène.
 * @author Pierre Riverain
 */
public class PlaySceneController extends PlayerControls {
	
	/**
	 * Ce composant graphique permet d'afficher la taille du labyrinthe en mode de jeu ou un message.
	 */
	@FXML
	private Label headerLabel;
	
	/**
	 * Ce composant graphique permet d'afficher le labyrinthe.
	 */
	@FXML
	private ImageView mazeView;
	
	/**
	 * Ce composant graphique permet d'obtenir une solution du labyrinthe.
	 */
	@FXML
	private Button solutionButton;
	
	/**
	 * Cet entier défini le nombre de colonnes du labyrinthe. 
	 */
	private int nbrColumns = 1;
	
	/**
	 * Cet entier défini le nombre de lignes du labyrinthe.
	 */
	private int nbrRows = 1;
	
	/**
	 * Cet entier contient la taille de l'axe X des cellules du labyrinthe dans l'image <code>mazeImage</code>.
	 */
	private int mazeImageCellSizeX = 0;
	
	/**
	 * Cet entier contient la taille de l'axe Y des cellules du labyrinthe dans l'image <code>mazeImage</code>.
	 */
	private int mazeImageCellSizeY = 0;
	
	/**
	 * Cet objet représente l'image du labyrinthe.
	 */
	private BufferedImage mazeImage = null;
	
	/**
	 * Cet objet représente et génère le labyrinthe.
	 */
	private Maze maze;
	
	/**
	 * Cet objet représente le thread de génération du labyrinthe.
	 */
	private Thread mazeGeneratorThread;
	
	/**
	 * Ce booléen indique si Labymany est en mode de jeu (<code>true</code>) ou non (<code>false</code>).
	 */
	private boolean isGameMode = false;
	
	/**
	 * Cet objet permet de générer l'image du labyrinthe.
	 */
	private MazePictureGenerator mazePictureGenerator = new MazePictureGenerator();
	
	public PlaySceneController() {	
		super();
	}

	/**
	 * Ce muttateur permet d'établir la taille de démarrage quand l'utilisateur clique sur l'un des boutons de la scène <code>SelectDifficultyScene</code>.
	 * @param nbrColumns Le nombre de colonnes que doit avoir le labyrinthe au démarrage.
	 * @param nbrRows Le nombre de lignes que doit avoir le labyrinthe au démarrage.
	 * @throws IllegalArgumentException Cette exception est levée si le nombre de colonnes ou de lignes sont inférieure à 2.
	 */
	public void setMazeSize(int nbrColumns, int nbrRows) {
		if (nbrColumns < 4) {
			throw new IllegalArgumentException("Le nombre de colonnes ne peut pas être inférieure à 4.");
		}
		if (nbrRows < 4) {
			throw new IllegalArgumentException("Le nombre de lignes ne peut pas être inférieure à 4.");
		}
		this.nbrColumns = nbrColumns;
		this.nbrRows = nbrRows;
		mazePictureGenerator = new MazePictureGenerator();
		mazePictureGenerator.setBackgroudColor(Color.WHITE);
		mazePictureGenerator.setWallsColor(Color.BLACK);
		
		if ((256 / nbrColumns) >= 8) {
			mazePictureGenerator.setCellSizeX(256 / nbrColumns);
			mazeView.setFitWidth(256);
		} else {
			mazePictureGenerator.setCellSizeX(16);
			mazeView.setFitWidth(this.nbrColumns * 8);
		}
		
		if ((256 / nbrRows) >= 8) {
			mazePictureGenerator.setCellSizeY(256 / nbrRows);
			mazeView.setFitHeight(256);
		} else {
			mazePictureGenerator.setCellSizeY(16);
			mazeView.setFitHeight(this.nbrRows * 8);
		}
		
		maze = new Maze(this.nbrColumns, this.nbrRows);
		maze.setDelayAnimation(start.getSettings().getMazeGenerationDelayAnimation());
		maze.setMazeGeneratorCursor(start.getSettings().getGeneratorCursor());
		maze.addOnGenerationMazeListener(new MazeGenerationListener() {
			
			@Override
			public void onGeneration(MazeGenerationEvent mazeGenerationEvent) {
				Platform.runLater(() -> {
					try {
						createAndShowMazePicture(mazeGenerationEvent.getMazeGenerationCursor());
					} catch (IOException e) {
						start.showError(e, "L'erreur suivante s'est produite pendant la génération et l'affichage de l'image du labyrinthe. Désolé pour le désagrément encouru : ");
					}
					
					if (mazeGenerationEvent.isGenerated()) {
						try {
							mazeGeneratorThread.join();
						} catch (InterruptedException e) {
							start.showError(e, "L'erreur suivante s'est produite pendant l'arrêt du processus de génération du labyrinthe. Désolé pour le désagrément encouru : ");
						}
						hideHeaderLabel(0, 1000, (actionEvent) -> {
							headerLabel.setText("A vous de jouer ! (Labyrinthe "+ nbrRows+" X "+nbrColumns+")");
							showHeaderLabel(1000, 1000);
							showSolutionButton(1000, 1000);
						});
						
						initializeGameMode();
					}
				});
				
			}
		});
		
		mazePictureGenerator.setMaze(maze);
		maze.initialize();
	}
	
	/**
	 * Cet accesseur permet d'obtenir le nombre de colonnes du labyritnhe.
	 * @return Le nombre actuel de colonnes.
	 */
	public int getNbrColumns() {
		return nbrColumns;
	}

	/**
	 * Cet accesseur permet d'obtenir le nombre de lignes du labyrinthe.
	 * @return Le nombre actuel de lignes.
	 */
	public int getNbrRows() {
		return nbrRows;
	}

	/**
	 * Cette méthode permet d'initialiser l'opacité des composants graphique à 0. Ceci permet d'exécuter les animations d'apparition dans des bonnes conditions.
	 */
	@FXML
	private void initialize() {
		headerLabel.setOpacity(0.00d);
		mazeView.setOpacity(0.00d);
		solutionButton.setOpacity(0.00d);
	}
	
	/**
	 * Cette méthode est affiché lorsque l'utilisateur appuis sur le bouton <code>solutionButton</code>. Elle affiche au joueur la solution en affichant temporairement le chemin quelques secondes tant que le
	 * joueur n'a pas déplacé le pion.
	 */
	@FXML
	private void onSolutionButtonClicked() {
		
		Thread mazePathfindingThread = new Thread(() -> {
			try {
				MazePathfinding mazePathfinding = new MazePathfinding(maze, start.getSettings().getPlayerCursor(), start.getSettings().getPlayerWinCursor());
				
				mazePathfinding.generateMazePath();
				
				MazeCursor[] mazePathArray = new MazeCursor[mazePathfinding.getMazePath().size()];
				BufferedImage bi = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
				Graphics g = bi.getGraphics();
				g.setColor(Color.CYAN);
				g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
				for (int i = 0; i < mazePathfinding.getMazePath().size(); i++) {
					MazeCursor mazeCursor = mazePathfinding.getMazePath().get(i);
					
					if(!start.getSettings().getPlayerCursor().isSameCoordinates(mazeCursor) && !start.getSettings().getPlayerWinCursor().isSameCoordinates(mazeCursor)) {
						mazeCursor.setMazeCursorPicture(bi);
					}
					
					mazePathArray[i] = mazeCursor;
				}
				createAndShowMazePicture(mazePathArray);
			} catch (Exception e) {
				start.showError(e, "L'erreur suivante s'est produite pendant l'affichage de la solution. Désolé pour le désagrément encouru : ");
			}
		}, "Maze pathfinding thread");
		
		mazePathfindingThread.start();
	}
	
	/**
	 * Cette méthode permet de générer le labyrinthe dans un thread parallèle.
	 */
	private void generateMazeInAParallelProcess() {

		mazeGeneratorThread = new Thread(() -> {
			maze.generate();
		}, "Maze Generator Thread");
		mazeGeneratorThread.start();
	}
	
	/**
	 * Cette méthode permet de générer le labyrinthe.
	 */
	public void generateMaze() {
		hideHeaderLabel(0, 1000, (actionEvent) -> {
			headerLabel.setText("Génération du labyrinthe "+nbrRows+" X "+nbrColumns+" en cours...");
			showHeaderLabel(1000, 1000);
		});
		
		generateMazeInAParallelProcess();
	}
	
	/**
	 * Cette méthode permet de générer une image à partir de l'état de <code>maze</code> en appelant la méthode<code>PlaySceneController.createMazePictures(maze, cellSizeX, cellSizeY, backgroundColor, linesColor, mazeCursors)</code>, et de l'afficher sur <code>mazeView</code>.
	 * @param mazeCursors Les curseurs à afficher sur l'image du labyrinthe
	 * @throws IOException Cette exception est levée si l'afichage du labyrinthe ne s'est pas correctement déroulé.
	 * @see fr.pierreriverain.maze.MazeCursor
	 */
	private void createAndShowMazePicture(MazeCursor... mazeCursors) throws IOException {
		mazePictureGenerator.getListOfMazeCursors().clear();
		mazePictureGenerator.getListOfMazeCursors().addAll(Arrays.asList(mazeCursors));
		
		Image mazePicture = SwingFXUtils.toFXImage(mazePictureGenerator.generateMazePicture(), null);
		mazeView.setImage(mazePicture);
	}
	
	/**
	 * Cette méthode permet d'initialiser l'application pour le mode de jeu (après la génération du labyrinthe).
	 */
	private void initializeGameMode() {
		try {

			MazePathfinding mazePathfinding = new MazePathfinding(maze, start.getSettings().getPlayerCursor(), start.getSettings().getPlayerWinCursor());
			
			start.getSettings().getPlayerCursor().setMazeCursorPosX(start.getSettings().getGeneratorCursor().getMazeCursorPosX());
			start.getSettings().getPlayerCursor().setMazeCursorPosY(start.getSettings().getGeneratorCursor().getMazeCursorPosY());
			
			do {
				
				do {
					
					start.getSettings().getPlayerWinCursor().setMazeCursorPosX(new Random().nextInt(maze.getMazeCells().length));
					start.getSettings().getPlayerWinCursor().setMazeCursorPosY(new Random().nextInt(maze.getMazeCells()[0].length));
					
				} while (!maze.getMazeCells()[start.getSettings().getPlayerWinCursor().getMazeCursorPosX()][start.getSettings().getPlayerWinCursor().getMazeCursorPosY()].isEnabled());
				
			} while (mazePathfinding.generateMazePath().getMazePath().size() < (maze.getSizeOfLongerPath() / 4) * 3);
			
			if (!KeyboardScreen.containsNativeKeyListener(this)) {
				
				KeyboardScreen.addNativeKeyListener(this);
			}
			
			isGameMode = true;
			
			createAndShowMazePicture(start.getSettings().getPlayerWinCursor(), start.getSettings().getPlayerCursor());
			
		} catch (Exception e) {
			start.showError(e, "L'erreur suivante s'est produite pendant l'initialisation du mode jeu. Désolé pour le désagrément encouru :");
			e.printStackTrace();
		}
	}
	
	/**
	 * Cette méthode permet d'analyser si le pion du joueur est sur la case d'arrivée.
	 */
	private void checkIfPlayerWin() {
		if (start.getSettings().getPlayerCursor().getMazeCursorPosX() == start.getSettings().getPlayerWinCursor().getMazeCursorPosX() && start.getSettings().getPlayerCursor().getMazeCursorPosY() == start.getSettings().getPlayerWinCursor().getMazeCursorPosY()) {
			
			maze = null;
			KeyboardScreen.removeNativeKeyListener(this);
			
			EventHandler<ActionEvent> actionOnAnimationFinished = (actionEvent) -> {
				try {
					start.showWinMenuScene();
					start.animateWinMenuScene();
				} catch (IOException e) {
					start.showError(e, "L'erreur suivante s'est produite pendant le chargement de l'écran de victoire. Désolé pour le désagrément encouru : ");
				}
			};
				
			hideHeaderLabel(0, 1000);
			hideSolutionButton(0, 1000);
			hideMazeView(1000, 1000, actionOnAnimationFinished);
		}
	}
	
	/**
	 * Cette méthode est appelée quand la touche pour déplacer le pion du joueur vers le bas, à été frappée.
	 */
	@Override
	public void onKeyPlayDownPressed() {
		if (maze.getMazeCells()[start.getSettings().getPlayerCursor().getMazeCursorPosX()][start.getSettings().getPlayerCursor().getMazeCursorPosY()].getDoorsOpened().contains(MazeCellDoor.SOUTH)) {
			try {
				start.getSettings().getPlayerCursor().setMazeCursorPosY(start.getSettings().getPlayerCursor().getMazeCursorPosY() + 1);
				createAndShowMazePicture(start.getSettings().getPlayerWinCursor(), start.getSettings().getPlayerCursor());
				checkIfPlayerWin();
			} catch (IOException e) {
				start.showError(e, "L'erreur suivante s'est produite pendant la génération et l'affichage de la nouvelle position. Désolé pour le désagrément encouru :");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Cette méthode est appelée quand la touche pour déplacer le pion du joueur vers la gauche, à été frappée.
	 */
	@Override
	public void onKeyPlayLeftPressed() {
		if (maze.getMazeCells()[start.getSettings().getPlayerCursor().getMazeCursorPosX()][start.getSettings().getPlayerCursor().getMazeCursorPosY()].getDoorsOpened().contains(MazeCellDoor.WEST)) {
			try {
				start.getSettings().getPlayerCursor().setMazeCursorPosX(start.getSettings().getPlayerCursor().getMazeCursorPosX() - 1);
				createAndShowMazePicture(start.getSettings().getPlayerWinCursor(), start.getSettings().getPlayerCursor());
				checkIfPlayerWin();
			} catch (IOException e) {
				start.showError(e, "L'erreur suivante s'est produite pendant la génération et l'affichage de la nouvelle position. Désolé pour le désagrément encouru :");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Cette méthode est appelée quand la touche pour déplacer le pion du joueur vers la droite, à été frappée.
	 */
	@Override
	public void onKeyPlayRightPressed() {
		if (maze.getMazeCells()[start.getSettings().getPlayerCursor().getMazeCursorPosX()][start.getSettings().getPlayerCursor().getMazeCursorPosY()].getDoorsOpened().contains(MazeCellDoor.EAST)) {
			try {
				start.getSettings().getPlayerCursor().setMazeCursorPosX(start.getSettings().getPlayerCursor().getMazeCursorPosX() + 1);
				createAndShowMazePicture(start.getSettings().getPlayerWinCursor(), start.getSettings().getPlayerCursor());
				checkIfPlayerWin();
			} catch (IOException e) {
				start.showError(e, "L'erreur suivante s'est produite pendant la génération et l'affichage de la nouvelle position. Désolé pour le désagrément encouru :");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Cette méthode est appelée quand la touche pour déplacer le pion du joueur vers le haut, à été frappée.
	 */
	@Override
	public void onKeyPlayUpPressed() {
		if (maze.getMazeCells()[start.getSettings().getPlayerCursor().getMazeCursorPosX()][start.getSettings().getPlayerCursor().getMazeCursorPosY()].getDoorsOpened().contains(MazeCellDoor.NORTH)) {
			try {
				start.getSettings().getPlayerCursor().setMazeCursorPosY(start.getSettings().getPlayerCursor().getMazeCursorPosY() - 1);
				createAndShowMazePicture(start.getSettings().getPlayerWinCursor(), start.getSettings().getPlayerCursor());
				checkIfPlayerWin();
			} catch (IOException e) {
				start.showError(e, "L'erreur suivante s'est produite pendant la génération et l'affichage de la nouvelle position. Désolé pour le désagrément encouru :");
				e.printStackTrace();
			}
		}
	}
	
	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>solutionButton</code>. Cette méthode est équivalent à <code>showSolutionButton(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showSolutionButton(double delay, double duration) {
		showSolutionButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>solutionButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showSolutionButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showSolutionButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(solutionButton.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(solutionButton.opacityProperty(), 1.00d)));
		showSolutionButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showSolutionButtonAnimation.setOnFinished(eventOnFinished);
		}
		showSolutionButtonAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>solutionButton</code>. Cette méthode est équivalent à <code>showSolutionButton(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideSolutionButton(double delay, double duration) {
		hideSolutionButton(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>solutionButton</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideSolutionButton(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideSolutionButtonAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(solutionButton.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(solutionButton.opacityProperty(), 0.00d)));
		hideSolutionButtonAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideSolutionButtonAnimation.setOnFinished(eventOnFinished);
		}
		hideSolutionButtonAnimation.play();
	}
	
	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>headerLabel</code>. Cette méthode est équivalent à <code>showHeaderLabel(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void showHeaderLabel(double delay, double duration) {
		showHeaderLabel(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>headerLabel</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showHeaderLabel(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showHeaderLabelAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(headerLabel.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(headerLabel.opacityProperty(), 1.00d)));
		showHeaderLabelAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showHeaderLabelAnimation.setOnFinished(eventOnFinished);
		}
		showHeaderLabelAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>headerLabel</code>. Cette méthode est équivalent à <code>showHeaderLabel(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideHeaderLabel(double delay, double duration) {
		hideHeaderLabel(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>headerLabel</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideHeaderLabel(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideHeaderLabelAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(headerLabel.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(headerLabel.opacityProperty(), 0.00d)));
		hideHeaderLabelAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideHeaderLabelAnimation.setOnFinished(eventOnFinished);
		}
		hideHeaderLabelAnimation.play();
	}
	
	/**
		 * Cette méthode déclenche l'animation d'apparition de <code>mazeView</code>. Cette méthode est équivalent à <code>showMazeView(double,double,null)</code>.
		 * @param delay Délai avant le déclenchement de l'animation.
		 * @param duration Durée de l'animation.
		 */
	public void showMazeView(double delay, double duration) {
		showMazeView(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation d'apparition de <code>mazeView</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void showMazeView(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline showMazeViewAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(mazeView.opacityProperty(), 0.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(mazeView.opacityProperty(), 1.00d)));
		showMazeViewAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			showMazeViewAnimation.setOnFinished(eventOnFinished);
		}
		showMazeViewAnimation.play();
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>mazeView</code>. Cette méthode est équivalent à <code>showMazeView(double,double,null)</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 */
	public void hideMazeView(double delay, double duration) {
		hideMazeView(delay, duration, null);
	}

	/**
	 * Cette méthode déclenche l'animation de disparition de <code>mazeView</code>.
	 * @param delay Délai avant le déclenchement de l'animation.
	 * @param duration Durée de l'animation.
	 * @param eventOnFinished Action à effectuer quand l'animation est terminé.
	 */
	public void hideMazeView(double delay, double duration, EventHandler<ActionEvent> eventOnFinished) {
		Timeline hideMazeViewAnimation = new Timeline(30,
				new KeyFrame(Duration.ZERO, new KeyValue(mazeView.opacityProperty(), 1.00d)),
				new KeyFrame(new Duration(duration), new KeyValue(mazeView.opacityProperty(), 0.00d)));
		hideMazeViewAnimation.setDelay(new Duration(delay));
		if (eventOnFinished != null) {
			hideMazeViewAnimation.setOnFinished(eventOnFinished);
		}
		hideMazeViewAnimation.play();
	}
}
