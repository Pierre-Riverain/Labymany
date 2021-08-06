package fr.pierreriverain.labymany.assertions;

import java.awt.image.BufferedImage;

import org.assertj.core.api.AbstractAssert;

import fr.pierreriverain.maze.MazePictureGenerator;

public class BufferedImageAssert extends AbstractAssert<BufferedImageAssert, BufferedImage> {
	
	BufferedImage bufferedImage;
	
	public BufferedImageAssert(BufferedImage bufferedImage) {
		super(bufferedImage, BufferedImageAssert.class);
		this.bufferedImage = bufferedImage;
	}
	
	/*
	 * Cette méthode permet de vérifier que la taille de l'image correspond bien aux dimensions passée en paramètres. Si ce n'est pas le cas, le test échoue.
	 */
	public BufferedImageAssert checkIfSizeMatches(int width, int height) {
		if (bufferedImage.getWidth() > width && bufferedImage.getHeight() > height) {
			failWithMessage("La largeur et la hauteur de l'image sont supérieurs à ceux attendus.");
		} else if (bufferedImage.getWidth() > width && bufferedImage.getHeight() < height) {
			failWithMessage("La largeur de l'image est supérieur, et la hauteur de l'image est inférieur à ceux attendus.");
		} else if (bufferedImage.getWidth() < width && bufferedImage.getHeight() > height) {
			failWithMessage("La largeur de l'image est inférieur, et la hauteur de l'image est supérieur à ceux attendus.");
		} else if (bufferedImage.getWidth() < width && bufferedImage.getHeight() < height) {
			failWithMessage("La largeur et la hauteur de l'image sont inférieurs à ceux attendus.");
		} else if (bufferedImage.getWidth() > width) {
			failWithMessage("La largeur de l'image est supérieure à celui attendu.");
		} else if (bufferedImage.getWidth() < width) {
			failWithMessage("La largeur de l'image est inférieure à celui attendu.");
		} else if (bufferedImage.getHeight() > height) {
			failWithMessage("La hauteur de l'image est supérieure à celui attendu.");
		} else if (bufferedImage.getHeight() < height) {
			failWithMessage("La hauteur de l'image est inférieure à celui attendu.");
		}
		
		return this;
	}
}
