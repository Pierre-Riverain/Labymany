package fr.pierreriverain.maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;


/**
 * Cette classe permet de créer et de gérer les différents curseur qui seront affiché dans l'image du labyrinthe.
 * Ces curseurs peuvent afficher la position de la génération quand le labyrinthe est en cours de génération, la position du/des joueurs
 * et des ennemis par exemple.
 * @author Pierre Riverain
 *
 */
public class MazeCursor implements Serializable, Cloneable {

	/**
	 * Cet attribut contient l'image du curseur. Par défaut, c'est un rond plein noir.
	 */
	private transient BufferedImage mazeCursorPicture = null;
	
	/**
	 * Cet entier contient la position du curseur sur l'axe X. Par défaut, cette position est à 0.
	 */
	private int mazeCursorPosX = 0;
	
	/**
	 * Cet entier contient la position du curseur sur l'axe Y. Par défaut, cette position est à 0.
	 */
	private int mazeCursorPosY = 0;
	
	
	public MazeCursor() {
		super();
	}
	
	/**
	 * Ce constructeur permet de définir les positions du curseur.
	 * @param mazeCursorPosX La position sur l'axe X du curseur.
	 * @param mazeCursorPosY La position sur l'axe Y du curseur.
	 */
	public MazeCursor(int mazeCursorPosX, int mazeCursorPosY) {
		super();
		
		if (mazeCursorPosX >= 0) {
			this.mazeCursorPosX = mazeCursorPosX;
		}
		if (mazeCursorPosY >= 0) {
			this.mazeCursorPosY = mazeCursorPosY;
		}
	}

	/**
	 * Ce constructeur permet de définir l'image du curseur ainsi que ses positions.
	 * @param mazeCursorPicture L'image du curseur.
	 * @param mazeCursorPosX La position sur l'axe X du curseur.
	 * @param mazeCursorPosY La position sur l'axe Y du curseur.
	 */
	public MazeCursor(BufferedImage mazeCursorPicture, int mazeCursorPosX, int mazeCursorPosY) {
		super();
		
		this.mazeCursorPicture = mazeCursorPicture;
		
		if (mazeCursorPosX >= 0) {
			this.mazeCursorPosX = mazeCursorPosX;
		}
		if (mazeCursorPosY >= 0) {
			this.mazeCursorPosY = mazeCursorPosY;
		}
	}
	
	/**
	 * Ce constructeur permet de copier une instance de cette classe.
	 * @param mazeCursor Curseur à copier.
	 */
	public MazeCursor(MazeCursor mazeCursor) {
		try {
			MazeCursor mazeCursor2 = (MazeCursor) mazeCursor.clone();
			
			this.mazeCursorPicture = mazeCursor2.mazeCursorPicture;
			this.mazeCursorPosX = mazeCursor2.mazeCursorPosX;
			this.mazeCursorPosY = mazeCursor2.mazeCursorPosY;
			
		} catch (CloneNotSupportedException e) {
		}
	}

	/**
	 * Cet accesseur permet d'obtenir l'image de ce curseur.
	 * @return L'image du curseur.
	 */
	public BufferedImage getMazeCursorPicture() {
		if (mazeCursorPicture == null) {
			mazeCursorPicture = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
			Graphics g = mazeCursorPicture.getGraphics();
			g.setColor(Color.BLACK);
			g.fillOval(0, 0, mazeCursorPicture.getWidth(), mazeCursorPicture.getHeight());
		}
		return mazeCursorPicture;
	}

	/**
	 * Ce muttateur permet de définir l'image de ce curseur.
	 * @param mazeCursorPicture L'image de ce curseur.
	 */
	public void setMazeCursorPicture(BufferedImage mazeCursorPicture) {
		if(mazeCursorPicture != null) {
			this.mazeCursorPicture = mazeCursorPicture;
			
		}
	}

	/**
	 * Cet accesseur permet d'obtenir la position sur l'axe X de ce curseur.
	 * @return La position sur l'axe X de ce curseur.
	 */
	public int getMazeCursorPosX() {
		return mazeCursorPosX;
	}

	/**
	 * Ce muttateur permet de définir la position sur l'axe X de ce curseur.
	 * @param mazeCursorPosX La position sur l'axe X de ce curseur.
	 */
	public void setMazeCursorPosX(int mazeCursorPosX) {
		if (mazeCursorPosX >= 0) {
			this.mazeCursorPosX = mazeCursorPosX;
		}
	}

	/**
	 * Cet accesseur permet d'obtenir la position sur l'axe Y de ce curseur.
	 * @return La position sur l'axe Y de ce curseur.
	 */
	public int getMazeCursorPosY() {
		return mazeCursorPosY;
	}

	/**
	 * Ce muttateur permet de définir la position sur l'axe Y de ce curseur.
	 * @param mazeCursorPosY la position sur l'axe Y de ce curseur.
	 */
	public void setMazeCursorPosY(int mazeCursorPosY) {
		if (mazeCursorPosY >= 0) {
			this.mazeCursorPosY = mazeCursorPosY;
		}
	}
	
	/**
	 * Cette méthode permet de définir les mêmes coordonnées que le curseur passé en paramètres.
	 * @param mazeCursor Le curseur qui va copier sa position à ce curseur.
	 */
	public void setSameMazeCursorPosTo(MazeCursor mazeCursor) {
		this.mazeCursorPosX = mazeCursor.mazeCursorPosX;
		this.mazeCursorPosY = mazeCursor.mazeCursorPosY;
	}
	
	/**
	 * Cette méthode permet de savoir si le curseur est à la même position que le curseur passé en paramètre.
	 * @param mazeCursor Le curseur à tester.
	 * @return <code>true</code> si les coordonnées des curseurs sont les mêmes, <code>false</code> sinon.
	 */
	public boolean isSameCoordinates(MazeCursor mazeCursor) {
		return this.mazeCursorPosX == mazeCursor.mazeCursorPosX && this.mazeCursorPosY == mazeCursor.mazeCursorPosY;
	}
	
	/**
	 * Cette méthode permet de copier l'instance de ce curseur.
	 * @return la copie de l'instance de ce curseur.
	 */
	public MazeCursor copy() {
		MazeCursor mazeCursor = null;
		try {
			mazeCursor = (MazeCursor) this.clone();
		} catch (CloneNotSupportedException e) {
		}
		
		return mazeCursor;
	}
}
