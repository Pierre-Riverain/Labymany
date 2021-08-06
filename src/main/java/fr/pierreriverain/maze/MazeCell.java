package fr.pierreriverain.maze;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe contient toutes les informations concernant une cellule du labyrinthe.
 * @author Pierre Riverain
 *
 */
public class MazeCell implements Cloneable, Serializable {

	/**
	 * Cette liste privé contient toutes les portes non ouvertes. Elle permet à la génération de savoir combiens de portes restent non ouverte et lesquels lorsqu'une issue n'est pas encore trouvé.
	 */
	private List<MazeCellDoor> doorsRemainingToTryToOpen;
	
	/**
	 * Cette liste privé contient toutes les portes ouvertes. Elle permet à la génération de l'image de savoir quelles portes ont été ouvertes.
	 */
	private List<MazeCellDoor> doorsOpened;
	
	/**
	 * Ce booléen privé permet à la génération de savoir si la cellule est active ou non.
	 */
	private boolean enabled = true;
	
	/**
	 * Ce booléen permet à la génération de savoir si la cellule à été visitée ou non. 
	 */
	private boolean visitedByMazeGenerator = false;
	
	/**
	 * Cet entier permet de savoir dans quelle colonne se trouve la cellule.
	 */
	private int columnIndex = 0;
	
	/**
	 * Cet entier permet de savoir dans quelle ligne se trouve la cellule.
	 */
	private int rowIndex = 0;
	 
	/**
	 * Ce constructeur initialise <code>doorRemainingToTryToOpen</code> et défini l'emplacement de la cellule avec les paramètres <code>columnIndex</code> et <code>rowIndex</code>.
	 * @param columnIndex index de la colonne ou se trouve la cellule.
	 * @param rowIndex index de la ligne ou se trouve la cellule.
	 */
	public MazeCell(int columnIndex, int rowIndex) {
		super();
		doorsRemainingToTryToOpen = new ArrayList<MazeCellDoor>();
		doorsOpened = new ArrayList<MazeCellDoor>();
		this.columnIndex = columnIndex;
		this.rowIndex = rowIndex;
	}

	/**
	 * Cet accesseur retourne la liste des portes qui ne sont pas encore ouvertes pour cette cellule.
	 * @return La liste des portes qui ne sont pas encore ouvertes pour cette cellule.
	 */
	public List<MazeCellDoor> getDoorsRemainingToTryToOpen() {
		return doorsRemainingToTryToOpen;
	}
	
	/**
	 * Cet accesseur retourne la liste des portes ouvertes pour cette cellule.
	 * @return La liste des portes ouvertes pour cette cellule.
	 */
	public List<MazeCellDoor> getDoorsOpened() {
		return doorsOpened;
	}	

	/**
	 * Ce muttateur définit si la cellule est active et si elle est s'affichera. Ceci permet de définir la forme qu'aura le labyrinthe.
	 * @param enabled <code>true</code>si la cellule est active et si elle s'affichera, <code>false</code> sinon.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Cet accesseur retourne un booléen qui indique si la cellule est active. 
	 * @return <code>true</code> si la cellule est active <code>false</code> sinon.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Ce muttateur permet au générateur d'enregistrer son passage 
	 * @param visitedByMazeGenerator <code>true</code> si la cellule vient d'être visité, <code>false</code> sinon.
	 */
	public void setVisitedByMazeGenerator(boolean visitedByMazeGenerator) {
		this.visitedByMazeGenerator = visitedByMazeGenerator;
	}

	/**
	 * Cet accesseur permet au générateur de savoir s'il a déjà visité la cellule.
	 * @return <code>true</code> si le générateur à déjà visité cette cellule, <code>false</code> sinon.
	 */
	public boolean isVisitedByMazeGenerator() {
		return visitedByMazeGenerator;
	}

	/**
	 * Cet accesseur permet d'avoir l'index de la colonne. Cet index (avec l'index de la ligne) permet au générateur de savoir dans quelle cellule ou il est.
	 * @return L'index de la colonne ou se trouve la cellule.
	 */
	public int getColumnIndex() {
		return columnIndex;
	}
	
	/**
	 * Cet accesseur permet d'avoir l'index de la ligne. Cet index (avec l'index de la colonnes) permet au générateur de savoir dans quelle cellule ou il est.
	 * @return L'index de la ligne ou se trouve la cellule.
	 */
	public int getRowIndex() {
		return rowIndex;
	}
}
