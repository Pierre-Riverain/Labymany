package fr.pierreriverain.maze;

/**
 * Cette classe "JavaBeans" contient toutes les informations quand la génération du labyrinthe lance les notifications.
 * @author Pierre Riverain
 *
 */
public class MazeGenerationEvent {
	
	/**
	 * Cet attribut représente le labyrinthe et contient toutes les informations le concernant.
	 */
	private Maze maze;
	
	/**
	 * Cet attribut permet de dire si le labyrinthe est généré (<code>true</code>) ou non (<code>false</code>).
	 */
	private boolean generated;
	
	/**
	 * Cet objet représente le cursuer de génération et contient toutes les informations le concernant.
	 */
	private MazeCursor mazeGenerationCursor;

	/**
	 * Constructeur par défaut.
	 */
	public MazeGenerationEvent() {
		super();
	}

	/**
	 * Ce constructeur permet de définir les différents paramètres dès la création de l'instance.
	 * @param maze Le labyrinthe en cours de génération ou généré.
	 * @param generated Ce booléen permet de dire si le labyrinthe est généré (<code>true</code>) ou non (<code>false</code>).
	 * @param mazeGenerationCursor Cette instance représente le curseur de génération du labyrinthe.
	 */
	public MazeGenerationEvent(Maze maze, boolean generated, MazeCursor mazeGenerationCursor) {
		super();
		this.maze = maze;
		this.generated = generated;
		this.mazeGenerationCursor = mazeGenerationCursor;
	}

	/**
	 * Cet accesseur permet d'obtenir le labyrinthe (en cours de génération ou généré).
	 * @return Le labyrinthe (en cours de génération ou généré).
	 */
	public Maze getMaze() {
		return maze;
	}

	/**
	 * e muttateur permet de définir le Labyrinthe (en cours de génération ou généré).
	 * @param maze Le labyrinthe (en cours de génération ou généré).
	 */
	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	/**
	 * Cet accesseur permet de savoir si le labyrinthe est généré (<code>true</code>) ou non (<code>false</code>).
	 * @return <code>true</code> si le labyrinthe est généré, <code>false</code> sinon.
	 */
	public boolean isGenerated() {
		return generated;
	}

	/**
	 * Ce muttateur permet de dire si le labyrinthe est généré (<code>true</code>) ou non (<code>false</code>).
	 * @param generated <code>true</code> si le labyrinthe est généré, <code>false</code> sinon.
	 */
	public void setGenerated(boolean generated) {
		this.generated = generated;
	}

	/**
	 * Cet accesseur permet d'obtenir l'objet représentant le curseur de génération ainsi que les informations le concernant.
	 * @return Le curseur de génération.
	 */
	public MazeCursor getMazeGenerationCursor() {
		return mazeGenerationCursor;
	}

	/**
	 * Ce muttateur permet de définir l'objet représentant le curseur de génération ainsi que les informations le concernant.
	 * @param mazeGenerationCursor Le curseur de génération.
	 */
	public void setMazeGenerationCursor(MazeCursor mazeGenerationCursor) {
		this.mazeGenerationCursor = mazeGenerationCursor;
	}
	
}
