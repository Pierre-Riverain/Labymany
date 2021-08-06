package fr.pierreriverain.maze;

/**
 * Cette interface permet d'être notifié lorsque le générateur à réalisé une étape. Elle permet de créer une animation de génération.
 * @author Pierre Riverain
 *
 */
public interface MazeGenerationListener {
	
	/**
	 * Cette méthode est appelé par le générateur lorqu'il à terminé une étape de son travail.
	 * @param mazeGenerationEvent Cet objet contient toutes les informations concernant la génération.
	 */
	public void onGeneration(MazeGenerationEvent mazeGenerationEvent);
}
