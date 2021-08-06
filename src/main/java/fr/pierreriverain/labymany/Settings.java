package fr.pierreriverain.labymany;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import org.jnativehook.keyboard.NativeKeyEvent;

import fr.pierreriverain.maze.MazeCursor;

/**
 * Cette classe Java beans permet d'enregistrer les différents paramètres de jeu.
 * @author Pierre Riverain
 */
public class Settings implements Serializable, Cloneable {

	/**
	 * Ce long contient le délai de l'animation de la génération du labyrinthe.
	 */
	private int mazeGenerationDelayAnimation = 10;
	
	/**
	 * Cet entier contient l'identifiant de la touche permettant de faire déplacer le pion du joueur d'une case vers la gauche.
	 */
	private int keyPlayLeft = NativeKeyEvent.VC_LEFT;
	
	/**
	 * Cet entier contient l'identifiant de la touche permettant de faire déplacer le pion du joueur d'une case vers la droite.
	 */
	private int keyPlayRight = NativeKeyEvent.VC_RIGHT;
	
	/**
	 * Cet entier contient l'identifiant de la touche permettant de faire déplacer le pion du joueur d'une case vers le haut.
	 */
	private int keyPlayUp = NativeKeyEvent.VC_UP;
	
	/**
	 * Cet entier contient l'identifiant de la touche permettant de faire déplacer le pion du joueur d'une case vers le bas.
	 */
	private int keyPlayDown = NativeKeyEvent.VC_DOWN;
	
	/**
	 * Cet objet contient toutes les informations du dossier où se trouvent une parties des images du jeu.
	 */
	private File pictureFolder = new File("pictures");
	
	/**
	 * Cet objet contient toutes les informations de l'emplacement de l'image du curseur du générateur.
	 */
	private File generatorCursorImageFile = new File("pictures", "generatorCursor.png");
	
	/**
	 * Cet objet contient toutes les informations de l'emplacement de l'image du pion du joueur.
	 */
	private File playerCursorImageFile = new File("pictures", "playerCursor.png");
	
	/**
	 * Cet objet contient toutes les informations de l'emplacement de l'image de l'arrivée.
	 */
	private File playerWinCursorImageFile = new File("pictures", "playerWinCursor.png");
	
	/**
	 * Cet objet représente le curseur de génération du labyrinthe. Il est utilisé lors de la création des images du labyrinthe durant la génération.
	 * Par défaut, l'image du curseur est un cercle orange.
	 */
	private volatile MazeCursor generatorCursor;
	
	/**
	 * Cet objet représente le curseur du joueur. Il est utilisé lors de la création des images du labyrinthe lorsque l'application est en mode de jeu afin de représenter la position du joueur
	 * dans le labyrinthe.
	 * Par défaut, l'image du curseur est un cercle bleue.
	 */
	private volatile MazeCursor playerCursor;
	
	/**
	 * Cet objet représente le curseur de l'arrivé à atteindre par le joueur. Il est utilisé lors de la création des images du labyrinthe lorsque l'application est en mode de jeu afin de 
	 * représenter l'arrivé dans le labyrinthe.
	 * Par défaut, l'image du curseur est un cercle vert. 
	 */
	private volatile MazeCursor playerWinCursor;
	
	public Settings() {
		super();
	}

	/**
	 * Cet accesseur permet d'obtenir le délai entre chaque étape de la génération du labyrinthe en millisecondes. 
	 * Par défaut, cette valeur est à 0.
	 * @return Le délai entre chaque étape de la génération.
	 */
	public int getMazeGenerationDelayAnimation() {
		return mazeGenerationDelayAnimation;
	}

	/**
	 * Ce muttateur permet de définir le délai entre chaque étape de la génération du labyrinthe en millisecondes. Si ce délai est supérieur à 0, il est possible de générer une animation de génération.
	 * Si la valeur défini est inférieur à 0 (négative), 0 est défini.
	 * @param mazeGenerationDelayAnimation Le délai entre chaque étape de la génération.
	 */
	public void setMazeGenerationDelayAnimation(int mazeGenerationDelayAnimation) {
		if (mazeGenerationDelayAnimation < 0) {
			this.mazeGenerationDelayAnimation = 0;
		}
		this.mazeGenerationDelayAnimation = mazeGenerationDelayAnimation;
	}

	/**
	 * Cet accesseur permet d'obtenir la valeur de la touche défini pour faire déplacer le pion du joueur d'une case vers la gauche.
	 * @return La valeur de la touche défini pour faire déplacer le pion du joueur d'une case vers la gauche.
	 */
	public int getKeyPlayLeft() {
		return keyPlayLeft;
	}

	/**
	 * Ce muttateur permet de définir la valeur de la touche pour faire déplacer le pion du joueur d'une case vers la gauche.
	 * @param keyPlayLeft La valeur de la touche défini pour faire déplacer le pion du joueur d'une case vers la gauche.
	 */
	public void setKeyPlayLeft(int keyPlayLeft) {
		this.keyPlayLeft = keyPlayLeft;
	}

	/**
	 * Cet accesseur permet d'obtenir la valeur de la touche défini pour faire déplacer le pion du joueur d'une case vers la droite.
	 * @return La valeur de la touche défini pour faire déplacer le pion du joueur d'une case vers la droite.
	 */
	public int getKeyPlayRight() {
		return keyPlayRight;
	}

	/**
	 * Ce muttateur permet de définir la valeur de la touche pour faire déplacer le pion du joueur d'une case vers la droite.
	 * @param keyPlayRight La valeur de la touche défini pour faire déplacer le pion du joueur d'une case vers la droite.
	 */
	public void setKeyPlayRight(int keyPlayRight) {
		this.keyPlayRight = keyPlayRight;
	}

	/**
	 * Cet accesseur permet d'obtenir la valeur de la touche défini pour faire déplacer le pion du joueur d'une case vers le haut.
	 * @return La valeur de la touche défini pour faire déplacer le pion du joueur d'une case vers le haut.
	 */
	public int getKeyPlayUp() {
		return keyPlayUp;
	}

	/**
	 * Ce muttateur permet de définir la valeur de la touche pour faire déplacer le pion du joueur d'une case vers le haut.
	 * @param keyPlayUp La valeur de la touche défini pour faire déplacer le pion du joueur d'une case vers le haut.
	 */
	public void setKeyPlayUp(int keyPlayUp) {
		this.keyPlayUp = keyPlayUp;
	}

	/**
	 * Cet accesseur permet d'obtenir la valeur de la touche défini pour faire déplacer le pion du joueur d'une case vers le bas.
	 * @return La valeur de la touche défini pour faire déplacer le pion du joueur d'une case vers le bas.
	 */
	public int getKeyPlayDown() {
		return keyPlayDown;
	}

	/**
	 * Ce muttateur permet de définir la valeur de la touche pour faire déplacer le pion du joueur d'une case vers le bas.
	 * @param keyPlayDown La valeur de la touche défini pour faire déplacer le pion du joueur d'une case vers le bas.
	 */
	public void setKeyPlayDown(int keyPlayDown) {
		this.keyPlayDown = keyPlayDown;
	}

	/**
	 * Cet accesseur permet d'obtenir les informations sur le dossier qui contient une partie des images.
	 * @return Les informations du dossier qui contient une partie des images.
	 */
	public File getPictureFolder() {
		return pictureFolder;
	}

	/**
	 * Cet accesseur permet d'obtenir les informations sur l'emplacement de l'image du curseur de génération.
	 * @return Les informations sur l'emplacement de l'image du curseur de génération.
	 */
	public File getGeneratorCursorImageFile() {
		return generatorCursorImageFile;
	}

	/**
	 * Cet accesseur permet d'obtenir les informations sur l'emplacement de l'image du pion du joeur.
	 * @return Les informations sur l'emplacement de l'image du pion du joueur.
	 */
	public File getPlayerCursorImageFile() {
		return playerCursorImageFile;
	}

	/**
	 * Cet accesseur permet d'obtenir les informations sur l'emplacement de l'image de l'arrivée.
	 * @return Les informations sur l'emplacement de l'image de l'arrivée.
	 */
	public File getPlayerWinCursorImageFile() {
		return playerWinCursorImageFile;
	}

	/**
	 * Cet accesseur permet d'obtenir l'instance du curseur de génération. Ce curseur est utilisé pour la création des images pendant la génération du labyrinthe.
	 * @return L'instance de <code>generatorCursor</code>.
	 */
	public MazeCursor getGeneratorCursor() {
		return generatorCursor;
	}

	/**
	 * Ce muttateur permet de définir une nouvelle instance du curseur de génération. Ce curseur sera utilisé pour la création des images pendant la génération du labyrinthe.
	 * @param generatorCursor La nouvelle instance de <code>generatorCursor</code>.
	 */
	public void setGeneratorCursor(MazeCursor generatorCursor) {
		this.generatorCursor = generatorCursor;
	}

	/**
	 * Cet accesseur permet d'obtenir l'instance du curseur du joueur. Ce curseur est utilisé lorsque l'application est en mode de jeu afin de représenter la position du joueur dans le labyrinthe.
	 * @return L'instance de <code>playerCursor</code>.
	 */
	public MazeCursor getPlayerCursor() {
		return playerCursor;
	}

	/**
	 * Ce muttateur permet de définir une nouvelle instance du curseur du joueur. Ce curseur est utilisé lorsque l'application est en mode de jeu afin de représenter ma position du joueur dans le
	 * labyrinthe.
	 * @param playerCursor L'instance de <code>playerCursor</code>.
	 */
	public void setPlayerCursor(MazeCursor playerCursor) {
		this.playerCursor = playerCursor;
	}

	/**
	 * Cet accesseur permet d'obtenir l'instance du curseur de l'arrivé à atteindre par le joueur. Il est utilisé lors de la création des images du labyrinthe lorsque l'application est 
	 * en mode de jeu afin de représenter l'arrivé dans le labyrinthe.
	 * @return the playerWinCursor L'instance de <code>playerWinCursor</code>.
	 */
	public MazeCursor getPlayerWinCursor() {
		return playerWinCursor;
	}

	/**
	 * Ce muttateur permet de définir une nouvelle instance du curseur de l'arrivé à atteindre par le joueur. Il est utilisé lors de la création des images du labyrinthe lorsque l'application est 
	 * en mode de jeu afin de représenter l'arrivé dans le labyrinthe.
	 * @param playerWinCursor L'instance de <code>playerWinCursor</code>.
	 */
	public void setPlayerWinCursor(MazeCursor playerWinCursor) {
		this.playerWinCursor = playerWinCursor;
	}
	
	/**
	 * Cette méthode permet de charger les images du jeu.
	 */
	public void loadPictures() {

		generatorCursor = new MazeCursor();
		playerCursor = new MazeCursor();
		playerWinCursor = new MazeCursor();
		
		//Ce booléen indique si le chargement de l'image s'est bien effectué. S'il ne l'est pas, l'image par défaut est chargée.
				boolean cursorGeneratorImageLoaded = false;
				BufferedImage cursorGeneratorImage = null;
				try {
					if (generatorCursorImageFile.exists()) {
						cursorGeneratorImage = ImageIO.read(generatorCursorImageFile);
						cursorGeneratorImageLoaded = true;
					}
				} catch (IOException e) {
					Labymany.showError(e, "L'erreur suivante s'est produite pendant le chargement de l'image du curseur de génération, l'image par défaut est chargé. Désolé pour le désagrément encouru : ");
				}
				if (!cursorGeneratorImageLoaded) {
					cursorGeneratorImage = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
					Graphics cursorGeneratorImageGraphics = cursorGeneratorImage.getGraphics();
					cursorGeneratorImageGraphics.setColor(Color.ORANGE);
					cursorGeneratorImageGraphics.fillOval(0, 0, cursorGeneratorImage.getWidth(), cursorGeneratorImage.getHeight());
				}
				
				generatorCursor.setMazeCursorPicture(cursorGeneratorImage);
				
				//Ce booléen indique si le chargement de l'image s'est bien effectué. S'il ne l'est pas, l'image par défaut est chargée.
				boolean cursorPlayerImageLoaded = false;
				BufferedImage cursorPlayerImage = null;
				try {
					if (playerCursorImageFile.exists()) {
						cursorPlayerImage = ImageIO.read(playerCursorImageFile);
						cursorPlayerImageLoaded = true;
					}
				} catch (IOException e) {
					Labymany.showError(e, "L'erreur suivante s'est produite pendant le chargement de l'image du pion du joueur, l'image par défaut est chargé. Désolé pour le désagrément encouru : ");
				}
				if (!cursorPlayerImageLoaded) {
					cursorPlayerImage = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
					Graphics cursorPlayerImageGraphics = cursorPlayerImage.getGraphics();
					cursorPlayerImageGraphics.setColor(Color.BLUE);
					cursorPlayerImageGraphics.fillOval(0, 0, cursorPlayerImage.getWidth(), cursorPlayerImage.getHeight());
				}
				playerCursor.setMazeCursorPicture(cursorPlayerImage);
				
				//Ce booléen indique si le chargement de l'image s'est bien effectué. S'il ne l'est pas, l'image par défaut est chargée.
				boolean cursorPlayerWinImageLoaded = false;
				BufferedImage cursorPlayerWinImage = null;
				try {
					if (playerWinCursorImageFile.exists()) {
						cursorPlayerWinImage = ImageIO.read(playerWinCursorImageFile);
						cursorPlayerWinImageLoaded = true;
					}
				} catch (IOException e) {
					Labymany.showError(e, "L'erreur suivante s'est produite pendant le chargement de l'image de l'arrivée, l'image par défaut. Désolé pour le désagrément encouru : ");
				}
				if (!cursorPlayerWinImageLoaded) {
					cursorPlayerWinImage = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
					Graphics cursorPlayerWinImageGraphics = cursorPlayerWinImage.getGraphics();
					cursorPlayerWinImageGraphics.setColor(Color.GREEN);
					cursorPlayerWinImageGraphics.fillOval(0, 0, cursorPlayerWinImage.getWidth(), cursorPlayerWinImage.getHeight());
				}
				playerWinCursor.setMazeCursorPicture(cursorPlayerWinImage);
	}
}
