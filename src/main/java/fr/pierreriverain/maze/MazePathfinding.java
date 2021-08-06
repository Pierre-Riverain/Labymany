package fr.pierreriverain.maze;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe contient toutes les informations concernant le chemin entre les deux curseurs.
 * @author Pierre Riverain
 */
public class MazePathfinding {
	
	/**
	 * Cet objet représente le labyrinthe ou se trouve les deux curseurs.
	 */
	private Maze maze;
	
	/**
	 * Cet objet représente le curseur de départ.
	 */
	private MazeCursor startMazeCursor;
	
	/**
	 * Cet objet représente le curseur d'arrivée.
	 */
	private MazeCursor endMazeCursor;
	
	/**
	 * Ce booléen indique si l'intelligence artificielle est arrêté (<code>true</code>) ou non (<code>false</code>).
	 */
	private boolean stopped = true;
	
	/**
	 * Ce booléen indique si le chemin entre le curseur de départ et d'arrivée est généré (<code>true</code>), ou non (<code>false</code>).
	 */
	private boolean mazePathGenerated = false;
	
	/**
	 * Cette liste contient tous les curseurs qui permettent d'indiquer le chemin entre le curseur de départ et d'arrivée.
	 */
	private List<MazeCursor> mazePath = new ArrayList<MazeCursor>();
	
	/**
	 * Ce constructeur permet de définir les différents objets nécessaire pour le calcul du chemin.
	 * @param maze le labyrinthe où se trouve les curseurs et opu le chemin doit être calculé.
	 * @param startMazeCursor Le curseur de départ.
	 * @param endMazeCursor Le curseur d'arrivée.
	 */
	public MazePathfinding(Maze maze, MazeCursor startMazeCursor, MazeCursor endMazeCursor) throws NullPointerException {
		super();
		
		if (maze == null) {
			throw new NullPointerException("Le paramètre maze ne doit pas être nulle.");
		}
		
		if (startMazeCursor == null) {
			throw new NullPointerException("Le paramètre startMazeCursor ne doit pas être nulle.");
		}
		
		if (endMazeCursor == null) {
			throw new NullPointerException("Le paramètre endMazeCursor ne doit pas être nulle.");
		}
		
		this.maze = maze;
		this.startMazeCursor = startMazeCursor;
		this.endMazeCursor = endMazeCursor;
	}
	
	/**
	 * Cet accesseur permet d'obtenir le labyrinthe où le chemin doit être calculé.
	 * @return Le labyrinthe où doit être calculé le chemin.
	 */
	public Maze getMaze() {
		return maze;
	}

	/**
	 * Ce muttateur permet de définir le labyrinthe où le chemin doit être calculé.
	 * @param maze the maze Le labyrinthe où doit être calculé le chemin.
	 */
	public void setMaze(Maze maze) {
		if(stopped) {
			if (maze == null) {
				throw new NullPointerException("Le paramètre maze ne doit pas être nulle.");
			}
			this.maze = maze;
		}
	}

	/**
	 * Cet accesseur permet d'obtenir le curseur de départ (à partir duquel le calcul du chemin démarrera).
	 * @return Le curseur de départ.
	 */
	public MazeCursor getStartMazeCursor() {
		return startMazeCursor;
	}

	/**
	 * Ce muttateur permet de définir le curseur de départ (à partir duquel le calcul du chemin démarrera).
	 * @param startMazeCursor Le curseur de départ.
	 */
	public void setStartMazeCursor(MazeCursor startMazeCursor) throws NullPointerException {
		if(stopped) {
			if (startMazeCursor == null) {
				throw new NullPointerException("Le paramètre startMazeCursor ne doit pas être nulle.");
			}
			this.startMazeCursor = startMazeCursor;
		}
	}

	/**
	 * Cet accesseur permet d'obtenir le curseur de fin (la où s'arrête le calcul du chemin).
	 * @return Le curseur de fin.
	 */
	public MazeCursor getEndMazeCursor() {
		return endMazeCursor;
	}

	/**
	 * Ce muttateur permet de définir le curseur de fin (la où s'arrête le calcul du chemin).
	 * @param endMazeCursor Le curseur de fin.
	 */
	public void setEndMazeCursor(MazeCursor endMazeCursor) throws NullPointerException {
		if(stopped) {
			if (endMazeCursor == null) {
				throw new NullPointerException("Le paramètre endMazeCursor ne doit pas être nulle.");
			}
			this.endMazeCursor = endMazeCursor;
		}
	}

	/**
	 * Cet accesseur permet de savoir si l'intelligence artificelle est arrêté ou non.
	 * @return <code>true</code> si l'intelligence est arrêté, <code>false</code> sinon.
	 */
	public boolean isStopped() {
		return stopped;
	}

	/**
	 * Cet accesseur permet de savoir si l'intelligence artificielle à généré le chemin entre le curseur de départ et d'arrivée.
	 * @return <code>true</code> si l'intelligence à généré le chemin entre le curseur de départ et d'arrivée, <code>false</code> sinon.
	 */
	public boolean isMazePathGenerated() {
		return mazePathGenerated;
	}

	/**
	 * Cet accesseur permet d'obtenir la liste qui contient tous les curseurs qui permettent d'indiquer le chemin entre le curseur de départ et d'arrivée.
	 * @return La liste qui contient tous les curseurs qui peremttent d'indiquer le chemin entre le curseur de départ et d'arrivée.
	 */
	public List<MazeCursor> getMazePath() {
		return mazePath;
	}
	
	/**
	 * Cette méthode lance la génération du chemin.
	 */
	public MazePathfinding generateMazePath() throws Exception {
		
		if (!maze.isGenerated()) {
			throw new Exception("Le labyrinthe n'est pas généré, l'intelligence artificielle ne peut pas générer de chemin quand le labyrinthe n'est pas généré.");
		}
		
		MazeCursor currentMazeCursor = startMazeCursor.copy();
		mazePathGenerated = false;
		if (!startMazeCursor.isSameCoordinates(endMazeCursor)) {
			for (MazeCellDoor mazeCellDoor : maze.getMazeCells()[currentMazeCursor.getMazeCursorPosX()][currentMazeCursor.getMazeCursorPosY()].getDoorsOpened()) {
				MazeCursor newMazeCursor = currentMazeCursor.copy();
				List<MazeCursor> tempMazePath = new ArrayList<MazeCursor>();
				switch(mazeCellDoor) {
				case NORTH:
					newMazeCursor.setMazeCursorPosY(newMazeCursor.getMazeCursorPosY()-1);
					tempMazePath = analyzeMazeCell(newMazeCursor, MazeCellDoor.SOUTH);
					if (!tempMazePath.isEmpty()) {
						mazePath.add(currentMazeCursor);
						mazePath.addAll(tempMazePath);
					}
					break;
				case EAST:
					newMazeCursor.setMazeCursorPosX(newMazeCursor.getMazeCursorPosX()+1);
					tempMazePath = analyzeMazeCell(newMazeCursor, MazeCellDoor.WEST);
					if (!tempMazePath.isEmpty()) {
						mazePath.add(currentMazeCursor);
						mazePath.addAll(tempMazePath);
					}
					break;
				case SOUTH:
					newMazeCursor.setMazeCursorPosY(newMazeCursor.getMazeCursorPosY()+1);
					tempMazePath = analyzeMazeCell(newMazeCursor, MazeCellDoor.NORTH);
					if (!tempMazePath.isEmpty()) {
						mazePath.add(currentMazeCursor);
						mazePath.addAll(tempMazePath);
					}
					break;
				case WEST:
					newMazeCursor.setMazeCursorPosX(newMazeCursor.getMazeCursorPosX()-1);
					tempMazePath = analyzeMazeCell(newMazeCursor, MazeCellDoor.EAST);
					if (!tempMazePath.isEmpty()) {
						mazePath.add(currentMazeCursor);
						mazePath.addAll(tempMazePath);
					}
					break;
				}
			}
		} else {
			mazePath.add(currentMazeCursor);
		}
		
		mazePathGenerated = !mazePath.isEmpty();
		return this;
	}
	
	/**
	 * Cette méthode permet d'analyser la cellule (qui se situe aux coordonnées indiqué par le paramètre currentMazeCursor) afin de trouver le curseur d'arrivée. Si l'arrivée se trouve à cet emplacement ou si la cellule se trouve dans le chemin
	 * menant vers l'arrivée, cette méthode retourne une liste de curseurs qui compose le chemin, sinon, elle retourne une liste vide.
	 * @param currentMazeCursor Le curseur définissant les coordonnées de la cellule à analyser.
	 * @param mazeCellDoorDoNotAnalyze Ce paramètre permet de dire de ne pas analyser la porte indiqué car l'intelligence artificielle à déjà analysée la cellule 
	 * @return Une liste de curseurs quand l'arrivée se trouve aux emplacements de la cellule ou quand cette cellule mêne vers l'arrivée, sinon une liste vide.
	 */
	private List<MazeCursor> analyzeMazeCell(MazeCursor currentMazeCursor, MazeCellDoor mazeCellDoorDoNotAnalyze) {
		List<MazeCursor> tempMazePath = new ArrayList<MazeCursor>();
		if (currentMazeCursor.isSameCoordinates(endMazeCursor)) {
			tempMazePath.add(endMazeCursor);
		} else {
			for (MazeCellDoor mazeCellDoor : maze.getMazeCells()[currentMazeCursor.getMazeCursorPosX()][currentMazeCursor.getMazeCursorPosY()].getDoorsOpened()) {
				if (!mazeCellDoor.equals(mazeCellDoorDoNotAnalyze)) {
					MazeCursor newMazeCursor = currentMazeCursor.copy();
					List<MazeCursor> tempMazePath2 = null;
					switch(mazeCellDoor) {
					case NORTH:
						newMazeCursor.setMazeCursorPosY(newMazeCursor.getMazeCursorPosY()-1);
						tempMazePath2 = analyzeMazeCell(newMazeCursor, MazeCellDoor.SOUTH);
						if (!tempMazePath2.isEmpty()) {
							tempMazePath.add(currentMazeCursor);
							tempMazePath.addAll(tempMazePath2);
						}
						break;
					case EAST:
						newMazeCursor.setMazeCursorPosX(newMazeCursor.getMazeCursorPosX()+1);
						tempMazePath2 = analyzeMazeCell(newMazeCursor, MazeCellDoor.WEST);
						if (!tempMazePath2.isEmpty()) {
							tempMazePath.add(currentMazeCursor);
							tempMazePath.addAll(tempMazePath2);
						}
						break;
					case SOUTH:
						newMazeCursor.setMazeCursorPosY(newMazeCursor.getMazeCursorPosY()+1);
						tempMazePath2 = analyzeMazeCell(newMazeCursor, MazeCellDoor.NORTH);
						if (!tempMazePath2.isEmpty()) {
							tempMazePath.add(currentMazeCursor);
							tempMazePath.addAll(tempMazePath2);
						}
						break;
					case WEST:
						newMazeCursor.setMazeCursorPosX(newMazeCursor.getMazeCursorPosX()-1);
						tempMazePath2 = analyzeMazeCell(newMazeCursor, MazeCellDoor.EAST);
						if (!tempMazePath2.isEmpty()) {
							tempMazePath.add(currentMazeCursor);
							tempMazePath.addAll(tempMazePath2);
						}
						break;
					}
				}
			}
		}
		
		return tempMazePath;
	}
}
