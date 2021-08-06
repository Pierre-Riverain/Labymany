package fr.pierreriverain.maze;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Cette classe permet de générer un labyrinthe à travers la méthode statique <code></code> et contient toutes les informations de ce dernier.
 * @author Pierre Riverain
 *
 */

import net.sf.saxon.expr.instruct.Copy;
public class Maze implements Cloneable, Serializable {

	/**
	 * Ce tableau à deux dimensions contient toutes les informations de chaques cellules. La première dimension du tableau correspond à l'axe X soit aux colonnes.
	 * La seconde dimension du tableau, quant à elle, correspond à l'axe Y soit aux lignes. Les informations de chaques cellules sont regroupé dans des objets de type <code>Cell</code>.
	 */
	private MazeCell[][] tab_mazeCells;
	
	/**
	 * Ce tableau à deux dimension permet de définir la forme du labyrinthe.
	 */
	private boolean[][] mazeForm;
	
	/**
	 * Cette liste privé et statique contient tous les écouteurs.
	 */
	private ArrayList<MazeGenerationListener> listOfOnGenerationMazeListeners = new ArrayList<MazeGenerationListener>();
	
	/**
	 * Cet attribut contient le nombre totale de colonnes du labyrinthe.
	 */
	private int columns = 1;
	
	/**
	 * Cet attribut contient le nombre totale de lignes du labyrinthe.
	 */
	private int rows = 1;
	
	/**
	 * Cet attribut privé contient le délai de pause entre chaque étape de la génération du labyrinthe pour l'animation.
	 */
	private int delayAnimation = 0;
	
	/**
	 * Cet entier indique la taille du chemin le plus long. Cette valeur sert pour placer correctement l'arrivée par rapport au pion du joueur.
	 */
	private int sizeOfLongerPath = 0;
	
	/**
	 * Cet entier indique le nombre de cellule active du labyrinthe. Cette valeur permet à l'intelligence artificielle de savoir sur combiens de threads elle doit s'exécuter.
	 */
	private int numberOfCellActivated = 0;
	
	/**
	 * Cet attribut privé permet de savoir si le labyrinthe est modifiable (qui n'est pas généré ou réinitialisé).
	 */
	private boolean editable = true;
	
	/**
	 * Cet attribut privé permet de savoir si le labyrinthe est initializé ou réinitializé.
	 */
	private boolean initialized = true;
	
	/**
	 * Cet attribut privé permet de savoir si le labyrinthe est généré ou non.
	 */
	private boolean generated = false;
	
	/**
	 * Cet objet représente le curseur de génération et contient toutes les informations le concernant. Il sert pour la génération du labyrinthe.
	 */
	private MazeCursor mazeGeneratorCursor;
	
	/**
	 * Ce constructeur permet de définir le nombre de colonnes et de lignes dès la création du labyrinthe. La forme du labyrinthe est par défaut rectangulaire et le curseur de génération est par défaut.
	 * @param columns Nombre de colonnes.
	 * @param rows Nombre de lignes.
	 */
	public Maze(int columns, int rows) {
		this(columns, rows, null, null);
	}
	
	/**
	 * Ce constructeur permet de définir le nombre de colonnes, de lignes, et le curseur de génération dès la création du labyrinthe. La forme du labyrinthe est par défaut rectangulaire.
	 * @param columns Nombre de colonnes
	 * @param rows Nombre de lignes
	 * @param mazeGeneratorCursor Le curseur de génération (copié s'il n'est pas nulle, par défaut sinon).
	 */
	public Maze(int columns, int rows, MazeCursor mazeGeneratorCursor) {
		this(columns, rows, null, mazeGeneratorCursor);
	}
	
	/**
	 * Ce constructeur permet de définir le nombre de colonnes, le nombre de lignes et la forme du labyrinthe dès la création du labyrinthe. Le curseur de génération est par défaut.
	 * @param columns Nombre de colonnes
	 * @param rows Nombre de lignes
	 * @param mazeForm Tableau de booléen à deux dimensions.
	 */
	public Maze(int columns, int rows, boolean[][] mazeForm) {
		this(columns, rows, mazeForm, null);
	}
	/**
	 * Ce constructeur permet de définir le nombre de colonnes, le nombre de lignes, la forme du labyrinthe et le curseur de génération dès la création du labyrinthe.
	 * @param columns Nombres de colonnes
	 * @param rows Nombre de lignes
	 * @param mazeForm Tableau de boléen à deux dimensions.
	 * @param mazeGeneratorCursor Le curseur de génération (copié s'il n'est pas nulle, par défaut sinon).
	 */
	public Maze(int columns, int rows, boolean[][] mazeForm, MazeCursor mazeGeneratorCursor) {
		super();
		if (columns < 4) {
			throw new IllegalArgumentException("Le nombre de colonnes doit être au minimum 1, mais il est à " + columns);
		}
		if (rows < 4) {
			throw new IllegalArgumentException("Le nombre de lignes doit être au minimum 1 mais il est à " + rows);
		}
		if (mazeForm == null) {
			mazeForm = new boolean[columns][rows];
			for (int x = 0; x < columns; x++) {
				for (int y = 0; y < rows; y++) {
					mazeForm[x][y] = true;
				}
			}
		}
		
		if (mazeGeneratorCursor == null) {
			this.mazeGeneratorCursor = new MazeCursor();
		} else {
			this.mazeGeneratorCursor = mazeGeneratorCursor;
		}

		editable = true;
		initialized = true;
		
		this.columns = columns;
		this.rows = rows;
		setMazeForm(mazeForm);
	}
	
	/**
	 * Ce muttateur permet d'obtenir la forme du labyrinthe sous un tableau de booléen à deux dimensions. La première dimension du tableau correspond à l'axe X soit aux colonnes.
	 * La seconde dimension du tableau, quant à elle, correspond à l'axe Y soit aux lignes.
	 * @return tableau de booléen à deux dimensions permettant de définir la forme du labyrinthe.
	 */
	public boolean[][] getMazeForm() {
		return mazeForm;
	}
	
	/**
	 * Ce muttateur permet de définir la forme du labyrinthe. Cette méthode doit être appelé avant la méthode <code>initialize()</code> pour être pris en compte.
	 * @param mazeForm tableaux à deux dimension avec au minimum la taille du labyrinthe.
	 * @return Retourne le labyrinthe.
	 */
	public Maze setMazeForm(boolean[][] mazeForm) {
		if (editable) {
			for (boolean[] bs : mazeForm) {
				for (boolean b : bs) {
					if (b) {
						numberOfCellActivated++;
					}
				}
			}
			this.mazeForm = mazeForm;
		}
		return this;
	}
	
	/**
	 * Cet accesseur retourne un tableau à deux dimensions qui contient toutes les informations de chaques cellules. La première dimension du tableau correspond à l'axe X soit aux colonnes.
	 * La seconde dimension du tableau, quant à elle, correspond à l'axe Y soit aux lignes. Les informations de chaques cellules sont regroupé dans des objets de type <code>Cell</code>.
	 * @return tableau à deux dimensions contenant toutes les informations de chaques cellules.
	 */
	public MazeCell[][] getMazeCells() {
		return tab_mazeCells;
	}
	
	/**
	 * Cet accesseur permet d'avoir le délai de pause entre chaque étape de la génération du labyrinthe pour l'animation.
	 * @return Le délai de pause entre chaque étape de la génération pour l'animation.
	 */
	public int getDelayAnimation() {
		return delayAnimation;
	}

	/**
	 * Ce muttateur permet de définir le délai de pause entre chaque étape de la génération du labyrinthe pour l'animation. Par défaut, le délai est à 0.
	 * @param delayAnimation Le délai de pause entre chaque étape de la génération pour l'animation.
	 * @return Retourne le labyrinthe.
	 */
	public Maze setDelayAnimation(int delayAnimation) {
		this.delayAnimation = delayAnimation;
		return this;
	}

	/**
	 * Cet accesseur permet d'obtenir la taille du chemin le plus long du labyrinthe. Cette valeur permet de placer correctement l'arrivée par rapport au pion du joueur.
	 * @return La taille du chemin le plus long du labyrinthe.
	 */
	public int getSizeOfLongerPath() {
		return sizeOfLongerPath;
	}

	/**
	 * Cet accesseur permet d'obtenir le nombre de cellules active du labyrinthe.  Cette valeur permet à l'intelligence artificielle de savoir sur combiens de threads elle doit s'exécuter.
	 * @return Le nombre de cellules active du labyrinthe;
	 */
	public int getNumberOfCellActivated() {
		return numberOfCellActivated;
	}

	/**
	 * Cet accesseur permet d'obtenir le curseur de gnénération qui à été défini. Il est utilisé durant la génération du labyrinthe.
	 * @return the mazeGeneratorCursor Le curseur de génération.
	 */
	public MazeCursor getMazeGeneratorCursor() {
		return mazeGeneratorCursor;
	}

	/**
	 * Ce muttateur permet de définir le curseur de génération. Il est utilisé lors de la gnénération du labyrinthe.
	 * @param mazeGeneratorCursor Le curseur de génération.
	 */
	public void setMazeGeneratorCursor(MazeCursor mazeGeneratorCursor) {
		this.mazeGeneratorCursor = mazeGeneratorCursor;
	}

	/**
	 * Cet accesseur permet de dire si le labyrinthe est modifiable au niveau de sa forme.
	 * @return <code>true</code> si le labyrinthe est modifiable, <code>false</code> sinon.
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Cet accesseur permet de dire si le labyrinthe est initialisé, réinitialisé ou non.
	 * @return <code>true</code> si le labyrinthe est initialisé ou réinitialisé, <code>false</code> sinon.
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * Cet accesseur permet de dire si le labyrinthe est généré ou non.
	 * @return <code>true</code> si le labyrinthe est généré, <code>false</code> sinon.
	 */
	public boolean isGenerated() {
		return generated;
	}

	/**
	 * Cette méthode ajoute l'écouteur passé en paramètre. 
	 * Cet écouteur de type <code>MazeListener</code> notifie quand une étape de la génération s'est effectué, ce qui permet de réaliser une animation lorsque la génération est en cours.
	 * @param onGenerationMazeListener Ecouteur à ajouter.
	 * @return Retourne le labyrinthe.
	 */
	public Maze addOnGenerationMazeListener(MazeGenerationListener onGenerationMazeListener) {
		listOfOnGenerationMazeListeners.add(onGenerationMazeListener);
		return this;
	}
	
	/**
	 * Cette méthode retire l'écouteur passé en paramètre de la liste des écouteurs.
	 * @param onGenerationMazeListener écouteur à retirer.
	 * @return l'écouteur retiré.
	 */
	public MazeGenerationListener removeMazeListener(MazeGenerationListener onGenerationMazeListener) {
		return listOfOnGenerationMazeListeners.remove(listOfOnGenerationMazeListeners.indexOf(onGenerationMazeListener));
	}
	
	/**
	 * Cette méthode permet d'initialiser ou de réinitialiser le labyrinthe.
	 * @return Retourne le labyrinthe.
	 */
	public synchronized Maze initialize() {
		tab_mazeCells = new MazeCell[columns][rows];
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				tab_mazeCells[x][y] = new MazeCell(x, y);
			}
		}
		if (mazeForm != null) {
			for (int x = 0; x < mazeForm.length; x++) {
				for (int y = 0; y < mazeForm[x].length; y++) {
					tab_mazeCells[x][y].setEnabled(mazeForm[x][y]);
				}
			}
		}
		
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				
				if (x > 0 && tab_mazeCells[x - 1][y].isEnabled())
					tab_mazeCells[x][y].getDoorsRemainingToTryToOpen().add(MazeCellDoor.WEST);

				if (x < columns - 1 && tab_mazeCells[x + 1][y].isEnabled())
					tab_mazeCells[x][y].getDoorsRemainingToTryToOpen().add(MazeCellDoor.EAST);

				if (y > 0 && tab_mazeCells[x][y - 1].isEnabled())
					tab_mazeCells[x][y].getDoorsRemainingToTryToOpen().add(MazeCellDoor.NORTH);

				if (y < rows - 1 && tab_mazeCells[x][y + 1].isEnabled())
					tab_mazeCells[x][y].getDoorsRemainingToTryToOpen().add(MazeCellDoor.SOUTH);
			}
		}
		
		initialized = true;
		editable = true;
		generated = false;

		actualizeOnGenerationMazeListener(this, generated, mazeGeneratorCursor);
		
		return this;
	}

	/**
	 * Cette méthode génère le labyrinthe.
	 * @return Retourne le labyrinthe généré.
	 */
	public synchronized Maze generate() {
		if (!initialized) {
			initialize();
		}
		
		editable = false;
		initialized = false;
		
		int idExit = 0;
		int sizePath = 0;
		
		final int NORTH = -1, EAST = 1, SOUTH = 1, WEST = -1;
		
		boolean exitFind = false;
		
		ArrayList<MazeCell> cellArrayList = new ArrayList<MazeCell>();
		
		do {
			mazeGeneratorCursor.setMazeCursorPosX(new Random().nextInt(tab_mazeCells.length));
			mazeGeneratorCursor.setMazeCursorPosY(new Random().nextInt(tab_mazeCells[0].length));
		} while (!tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].isEnabled());
		
		cellArrayList.add(tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()]);
		
		
		do {
			actualizeOnGenerationMazeListener(this, generated, mazeGeneratorCursor);
			tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].setVisitedByMazeGenerator(true);
			
			try {
				Thread.sleep(delayAnimation);
			} catch (InterruptedException e) {
			}
			
			ArrayList<MazeCellDoor> listOfDoorsRemainingToTryToOpen = new ArrayList<MazeCellDoor>();
			while (!tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsRemainingToTryToOpen().isEmpty() && !exitFind) {

				if (tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsRemainingToTryToOpen().size() > 1) {
					idExit = new Random().nextInt(tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsRemainingToTryToOpen().size());
				} else {
					idExit = 0;
				}
				
				switch (tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsRemainingToTryToOpen().get(idExit)) {
				
				case NORTH:
					tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsRemainingToTryToOpen().remove(MazeCellDoor.NORTH);
					MazeCell mazeCellNorth = tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()+NORTH];
					
					if (mazeCellNorth.isEnabled() && !mazeCellNorth.isVisitedByMazeGenerator()) {
						exitFind = true;
						
						sizePath++;
						tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsOpened().add(MazeCellDoor.NORTH);
						
						mazeGeneratorCursor.setMazeCursorPosY(mazeGeneratorCursor.getMazeCursorPosY() + NORTH);
						
						cellArrayList.add(getMazeCells()[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()]);
						
						tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsRemainingToTryToOpen().remove(MazeCellDoor.SOUTH);
						tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsOpened().add(MazeCellDoor.SOUTH);
					}
					
					break;
				case EAST:
					tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsRemainingToTryToOpen().remove(MazeCellDoor.EAST);
					MazeCell mazeCellEast = tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()+EAST][mazeGeneratorCursor.getMazeCursorPosY()];
					
					if (mazeCellEast.isEnabled() && !mazeCellEast.isVisitedByMazeGenerator()) {
						exitFind = true;
						
						sizePath++;
						
						tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsOpened().add(MazeCellDoor.EAST);
						
						mazeGeneratorCursor.setMazeCursorPosX(mazeGeneratorCursor.getMazeCursorPosX() + EAST);
						
						cellArrayList.add(getMazeCells()[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()]);
						
						tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsRemainingToTryToOpen().remove(MazeCellDoor.WEST);
						tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsOpened().add(MazeCellDoor.WEST);
					}
					
					break;
				case SOUTH:
					tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsRemainingToTryToOpen().remove(MazeCellDoor.SOUTH);
					MazeCell mazeCellSouth = tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()+SOUTH];
					
					if (mazeCellSouth.isEnabled() && !mazeCellSouth.isVisitedByMazeGenerator()) {
						exitFind = true;
						
						sizePath++;
						
						tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsOpened().add(MazeCellDoor.SOUTH);
						
						mazeGeneratorCursor.setMazeCursorPosY(mazeGeneratorCursor.getMazeCursorPosY() + SOUTH);
						
						cellArrayList.add(getMazeCells()[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()]);
						
						tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsRemainingToTryToOpen().remove(MazeCellDoor.NORTH);
						tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsOpened().add(MazeCellDoor.NORTH);
					}
					
					break;
				case WEST:
					tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsRemainingToTryToOpen().remove(MazeCellDoor.WEST);
					MazeCell mazeCellWest = tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()+WEST][mazeGeneratorCursor.getMazeCursorPosY()];
					
					if (mazeCellWest.isEnabled() && !mazeCellWest.isVisitedByMazeGenerator()) {
						exitFind = true;
						
						sizePath++;
						
						tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsOpened().add(MazeCellDoor.WEST);
						
						mazeGeneratorCursor.setMazeCursorPosX(mazeGeneratorCursor.getMazeCursorPosX() + WEST);
						
						cellArrayList.add(getMazeCells()[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()]);
						
						tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsRemainingToTryToOpen().remove(MazeCellDoor.EAST);
						tab_mazeCells[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsOpened().add(MazeCellDoor.EAST);
					}
					
					break;
				}
			}
			
			if (getMazeCells()[mazeGeneratorCursor.getMazeCursorPosX()][mazeGeneratorCursor.getMazeCursorPosY()].getDoorsRemainingToTryToOpen().isEmpty() && !exitFind) {
				cellArrayList.remove(cellArrayList.size() -1);
				if (!cellArrayList.isEmpty()) {
					mazeGeneratorCursor.setMazeCursorPosX(cellArrayList.get(cellArrayList.size() -1).getColumnIndex());
					mazeGeneratorCursor.setMazeCursorPosY(cellArrayList.get(cellArrayList.size() -1).getRowIndex());
					
					if (sizePath > sizeOfLongerPath) {
						sizeOfLongerPath = sizePath;
					}
					
					sizePath--;
				}
				
			}
			
			exitFind = false;
		} while (!cellArrayList.isEmpty());
		generated = true;
		actualizeOnGenerationMazeListener(this, generated, mazeGeneratorCursor);
		return this;
	}

	/**
	 * Cette méthode permet d'actualiser les écouteurs lors de la génération du labyrinthe.
	 * @param maze Labyrinthe en cours de génération.
	 * @param generated <code>true</code> si le labyrinthe est généré, <code>false</code> sinon.
	 * @param mazeGeneratorCursor Curseur de génération.
	 */
	private void actualizeOnGenerationMazeListener(Maze maze, boolean generated, MazeCursor mazeGeneratorCursor) {
		for (MazeGenerationListener mazeListener : listOfOnGenerationMazeListeners) {
			mazeListener.onGeneration(new MazeGenerationEvent(this, generated, mazeGeneratorCursor));
		}
	}
	
	public Maze copy() {
		Maze maze = null;
		
		try {
			maze = (Maze) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return maze;
	}
}
