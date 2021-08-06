package fr.pierreriverain.maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Cette classe permet de convertir un labyrinthe en image.
 * @author Pierre Riverain
 *
 */
public class MazePictureGenerator implements Serializable, Cloneable {

	/**
	 * Cet objet représente le labyrinthe qui doit être converti en image.
	 */
	private Maze maze = null;
	
	/**
	 * Cet entier indique la taille des cellules sur l'axe X.
	 */
	private int cellSizeX = 8;
	
	/**
	 * Cet entier indique la taille des cellules sur l'axe Y.
	 */
	private int cellSizeY = 8;
	
	/**
	 * Cet entier indique l'épaisseur des murs sur l'axe X.
	 */
	private int wallSizeX = 2;
	
	/**
	 * Cet entier indique l'épaisseur des murs sur l'axe Y.
	 */
	private int wallSizeY = 2;
	
	/**
	 * Cet objet représente la couleur de l'arrière plan du labyrinthe.
	 */
	private Color backgroundColor = null;
	
	/**
	 * Cet objet représente la couleur des mur du labyrinthe.
	 */
	private Color wallsColor = null;
	
	/**
	 * Cette collection contient tous les curseurs qui doivent être dessiné.
	 */
	private ArrayList<MazeCursor> listOfMazeCursors = null;
	
	/**
	 * Ce constructeur initialise <code>listOfMazeCursors</code>.
	 */
	public MazePictureGenerator() {
		super();
		
		listOfMazeCursors = new ArrayList<MazeCursor>();
	}

	/**
	 * Cet accesseur permet d'obtenir le labyrinthe à convertir en image.
	 * @return Le labyrinthe à convertir en image.
	 */
	public synchronized Maze getMaze() {
		return maze;
	}

	/**
	 * Ce muttateur permet de définir le labyrinthe à convertir en image.
	 * @param maze Le labyrinthe à convertir en image.
	 */
	public synchronized void setMaze(Maze maze) {
		this.maze = maze;
	}

	/**
	 * Cet accesseur permet d'obtenir la taille des cellules du labyrinthe sur l'axe X.
	 * @return La taille des cellules du labyrinthe sur l'axe X.
	 */
	public synchronized int getCellSizeX() {
		return cellSizeX;
	}

	/**
	 * Ce muttateur permet de définir la taille des cellules du labyrinthe sur l'axe X.
	 * @param cellSizeX La taille des cellules du labyrinthe sur l'axe X.
	 */
	public synchronized void setCellSizeX(int cellSizeX) {
		this.cellSizeX = cellSizeX;
	}

	/**
	 * Cet accesseur permet de définir la taille des cellules du labyrinthe sur l'axe Y.
	 * @return La taille des cellules du labyrinthe sur l'axe Y.
	 */
	public synchronized int getCellSizeY() {
		return cellSizeY;
	}

	/**
	 * Ce muttateur permet de définir la taille des cellules du labyrinthe sur l'axe Y.
	 * @param cellSizeY La taille des cellules du labyrinthe sur l'axe Y.
	 */
	public synchronized void setCellSizeY(int cellSizeY) {
		this.cellSizeY = cellSizeY;
	}

	/**
	 * Cet accesseur permet d'obtenir l'épaisseur des murs du labyrinthe sur l'axe X.
	 * @return the wallSize L'épaisseur des murs du labyrinthe sur l'axe X.
	 */
	public synchronized int getWallSizeX() {
		return wallSizeX;
	}

	/**
	 * Ce muttateur permet de définir l'épaisseur des murs du labyrinthe sur l'axe X.
	 * @param wallSizeX L'épaisseur des murs du labyrinthe sur l'axe X.
	 */
	public synchronized void setWallSizeX(int wallSizeX) {
		this.wallSizeX = wallSizeX;
	}
	
	/**
	 * Cet accesseur permet d'obtenir l'épaisseur des murs du labyrinthe sur l'axe Y.
	 * @return the wallSize L'épaisseur des murs du labyrinthe sur l'axe Y.
	 */
	public synchronized int getWallSizeY() {
		return wallSizeY;
	}

	/**
	 * Ce muttateur permet de définir l'épaisseur des murs du labyrinthe sur l'axe Y.
	 * @param wallSizeY L'épaisseur des murs du labyrinthe sur l'axe Y.
	 */
	public synchronized void setWallSizeY(int wallSizeY) {
		this.wallSizeY = wallSizeY;
	}

	/**
	 * Cet accesseur permet d'obtenir la couleur de l'arrière plan du labyrinthe.
	 * @return La couleur de l'arrière plan du labyrinthe.
	 */
	public synchronized Color getBackgroudColor() {
		return backgroundColor;
	}

	/**
	 * Ce muttateur permet de définir la couleur de l'arière plan du labyrinthe.
	 * @param backgroudColor La couleur de l'arrière plan du labyrinthe.
	 */
	public synchronized void setBackgroudColor(Color backgroudColor) {
		this.backgroundColor = backgroudColor;
	}

	/**
	 * Cet accesseur permet d'obtenir la couleur des murs du labyrinthe.
	 * @return La couleur des murs du labyrinthe.
	 */
	public synchronized Color getWallsColor() {
		return wallsColor;
	}

	/**
	 * Ce muttateur permet de définir la couleur des murs du labyrinthe.
	 * @param wallsColor La couleur des murs du labyrinthe.
	 */
	public synchronized void setWallsColor(Color wallsColor) {
		this.wallsColor = wallsColor;
	}

	/**
	 * Cet accesseur permet d'obtenir la liste des curseurs à dessiner sur l'image.
	 * @return La liste des curseurs à dessiner sur l'image.
	 */
	public synchronized ArrayList<MazeCursor> getListOfMazeCursors() {
		return listOfMazeCursors;
	}

	/**
	 * Ce muttateur permet de définir une nouvelle liste des curseur à dessiner sur l'image.
	 * @param listOfMazeCursors the listOfMazeCursors to set
	 */
	public synchronized void setListOfMazeCursors(ArrayList<MazeCursor> listOfMazeCursors) {
		this.listOfMazeCursors = listOfMazeCursors;
	}

	/**
	 * Cette méthode permet de générer l'image du labyrinthe.
	 * @return L'image du labyrinthe générée.
	 */
	public synchronized BufferedImage generateMazePicture() {
		
		BufferedImage mazeImage = new BufferedImage(cellSizeX * maze.getMazeCells().length, cellSizeY * maze.getMazeCells()[0].length, BufferedImage.TYPE_INT_ARGB);
		Graphics mazeImageGraphics = mazeImage.getGraphics();
		
		mazeImageGraphics.setColor(backgroundColor);
		mazeImageGraphics.fillRect(0, 0, mazeImage.getWidth(), mazeImage.getHeight());
		
		mazeImageGraphics.setColor(wallsColor);
		
		int x;
		int y;
		int x2;
		int y2;
		
		for (MazeCell[] mazeRowCells : maze.getMazeCells()) {
			for (MazeCell cell : mazeRowCells) {
				if (cell.isEnabled()) {
					
					x = (cell.getColumnIndex() * cellSizeX) - (wallSizeX / 2);
					y = (cell.getRowIndex() * cellSizeY) - (wallSizeY / 2);
					x2 = (cell.getColumnIndex() * cellSizeX + cellSizeX) - (wallSizeX / 2);
					y2 = (cell.getRowIndex() * cellSizeY + cellSizeY) - (wallSizeY / 2);
					
					mazeImageGraphics.fillRect(x, y, wallSizeX, wallSizeY);
					mazeImageGraphics.fillRect(x2, y, wallSizeX, wallSizeY);
					mazeImageGraphics.fillRect(x, y2, wallSizeX, wallSizeY);
					mazeImageGraphics.fillRect(x2, y2, wallSizeX, wallSizeY);
					
					boolean mazeCellDoorNorthClosed = !cell.getDoorsOpened().contains(MazeCellDoor.NORTH);
					boolean mazeCellDoorEastClosed = !cell.getDoorsOpened().contains(MazeCellDoor.EAST);
					boolean mazeCellDoorSouthClosed = !cell.getDoorsOpened().contains(MazeCellDoor.SOUTH);
					boolean mazeCellDoorWestClosed = !cell.getDoorsOpened().contains(MazeCellDoor.WEST);
					
					if (mazeCellDoorWestClosed) {
						mazeImageGraphics.fillRect(x, y, wallSizeX, cellSizeY);
					}
					if (mazeCellDoorNorthClosed) {
						mazeImageGraphics.fillRect(x, y, cellSizeX, wallSizeY);
					}
					if (mazeCellDoorEastClosed) {
						mazeImageGraphics.fillRect(x2, y, wallSizeX, cellSizeY);
					}
					if (mazeCellDoorSouthClosed) {
						mazeImageGraphics.fillRect(x, y2, cellSizeX, wallSizeY);
					}
				}
			}
		}
		for (MazeCursor mazeCursor : listOfMazeCursors) {
			mazeImageGraphics.drawImage(mazeCursor.getMazeCursorPicture(), (mazeCursor.getMazeCursorPosX() * cellSizeX) + (wallSizeX / 2), (mazeCursor.getMazeCursorPosY() * cellSizeY) + (wallSizeY / 2), cellSizeX - wallSizeX, cellSizeY - wallSizeY, null);
		}
		
		return mazeImage;
	}
}
