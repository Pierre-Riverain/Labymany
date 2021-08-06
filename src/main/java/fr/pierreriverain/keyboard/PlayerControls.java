package fr.pierreriverain.keyboard;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import fr.pierreriverain.labymany.Labymany;

/**
 * Cette classe permet d'informer quand l'une des touches pour déplacer le pion du joueur à été frappé.
 * @author Pierre Riverain
 *
 */
public abstract class PlayerControls implements NativeKeyListener {
	
	/**
	 * Cet objet représente l'instance de la classe de démarrage.
	 */
	protected Labymany start;
	
	public PlayerControls() {
		super();
	}
	/**
	 * Ce constructeur permet d'initialiser les attributs de cette classe. 
	 * @param start L'instance de la classe de démarrage.
	 */
	public PlayerControls(Labymany start) {
		super();
		this.start = start;
	}
	
	/**
	 * Cet accesseur permet d'obtenir l'instance de la classe de Démarrage.
	 * @return La classe de démarrage.
	 */
	public Labymany getStart() {
		return start;
	}
	
	/**
	 * Ce muttateur permet de définir la classe de démarrage. Ceci permet de changer de scène.
	 * @param start La classe de démarrage à définir.
	 */
	public void setStart(Labymany start) {
		this.start = start;
	}

	/**
	 * Cette méthode est appelé lorsqu'une touche du clavier à été frappé. Si c'est le cas, les notifications sont envoyés.
	 */
	@Override
	public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
		if (nativeKeyEvent.getKeyCode() == start.getSettings().getKeyPlayDown()) {
			onKeyPlayDownPressed();
		} else if (nativeKeyEvent.getKeyCode() == start.getSettings().getKeyPlayLeft()) {
			onKeyPlayLeftPressed();
		} else if (nativeKeyEvent.getKeyCode() == start.getSettings().getKeyPlayRight()) {
			onKeyPlayRightPressed();
		} else if (nativeKeyEvent.getKeyCode() == start.getSettings().getKeyPlayUp()) {
			onKeyPlayUpPressed();
		}
	}
	
	/**
	 * Cette méthode est appelée quand la touche pour déplacer le pion du joueur vers le haut, à été frappée.
	 */
	public abstract void onKeyPlayUpPressed();
	
	/**
	 * Cette méthode est appelée quand la touche pour déplacer le pion du joueur vers la droite, à été frappée.
	 */
	public abstract void onKeyPlayRightPressed();
	
	/**
	 * Cette méthode est appelée quand la touche pour déplacer le pion du joueur vers le bas, à été frappée.
	 */
	public abstract void onKeyPlayDownPressed();
	
	/**
	 * Cette méthode est appelée quand la touche pour déplacer le pion du joueur vers la gauche, à été frappée.
	 */
	public abstract void onKeyPlayLeftPressed();

	@Override
	public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {}

	@Override
	public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {}
}
