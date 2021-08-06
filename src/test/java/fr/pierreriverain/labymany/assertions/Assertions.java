package fr.pierreriverain.labymany.assertions;

import java.awt.image.BufferedImage;

import fr.pierreriverain.maze.Maze;
import fr.pierreriverain.maze.MazeCell;

public class Assertions {
	
	public static MazeAssert assertThat(Maze maze) {
		return new MazeAssert(maze);
	}
	
	public static BufferedImageAssert assertThat(BufferedImage bufferedImage) {
		return new BufferedImageAssert(bufferedImage);
	}
}
