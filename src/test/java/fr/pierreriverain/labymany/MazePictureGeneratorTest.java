package fr.pierreriverain.labymany;

import static fr.pierreriverain.labymany.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.pierreriverain.maze.Maze;
import fr.pierreriverain.maze.MazeCell;
import fr.pierreriverain.maze.MazeCellDoor;
import fr.pierreriverain.maze.MazeCursor;
import fr.pierreriverain.maze.MazePictureGenerator;

@ExtendWith(MockitoExtension.class)
class MazePictureGeneratorTest {

	@Mock
	Maze maze;
	
	MazeCell[][] tab_mazeCells;
	
	public MazePictureGeneratorTest() {
		super();
	}
	
	@BeforeEach
	public void initialize() {
		tab_mazeCells = new MazeCell[4][4];
		
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				
				tab_mazeCells[x][y] = new MazeCell(x, y);
				
				switch(y) {
				case 0:
					switch(x) {
					case 0:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.EAST));
						break;
					case 1:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.SOUTH, MazeCellDoor.WEST));
						break;
					case 2:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.SOUTH));
						break;
					case 3:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.SOUTH));
						break;
					}
					break;
				case 1:
					switch(x) {
					case 0:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.EAST, MazeCellDoor.SOUTH));
						break;
					case 1:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.NORTH, MazeCellDoor.WEST));
						break;
					case 2:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.NORTH, MazeCellDoor.SOUTH));
						break;
					case 3:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.NORTH, MazeCellDoor.SOUTH));
						break;
					}
					break;
				case 2:
					switch(x) {
					case 0:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.NORTH, MazeCellDoor.SOUTH));
						break;
					case 1:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.EAST, MazeCellDoor.SOUTH));
						break;
					case 2:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.NORTH, MazeCellDoor.EAST, MazeCellDoor.SOUTH, MazeCellDoor.WEST));
						break;
					case 3:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.NORTH, MazeCellDoor.WEST));
						break;
					}
					break;
				case 3:
					switch(x) {
					case 0:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.NORTH, MazeCellDoor.EAST));
						break;
					case 1:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.NORTH, MazeCellDoor.WEST));
						break;
					case 2:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.NORTH, MazeCellDoor.EAST));
						break;
					case 3:
						tab_mazeCells[x][y].getDoorsOpened().addAll(Arrays.asList(MazeCellDoor.WEST));
						break;
					}
					break;
				}
				
				tab_mazeCells[x][y].setEnabled(true);
			}
		}
	}
	
	@Test
	public void should_generate_maze_picture_with_correct_size_and_cursor() {
		when(maze.getMazeCells()).thenReturn(tab_mazeCells);
		MazePictureGenerator mazePictureGenerator = new MazePictureGenerator();
		mazePictureGenerator.setBackgroudColor(Color.WHITE);
		mazePictureGenerator.setWallsColor(Color.BLACK);
		mazePictureGenerator.setCellSizeX(16);
		mazePictureGenerator.setCellSizeY(16);
		mazePictureGenerator.setWallSizeX(2);
		mazePictureGenerator.setWallSizeY(2);
		mazePictureGenerator.setMaze(maze);
		mazePictureGenerator.getListOfMazeCursors().add(new MazeCursor(0, 0));
		
		BufferedImage bufferedImage = mazePictureGenerator.generateMazePicture();
		
		try {
			ImageIO.write(bufferedImage, "png", new File("mazeTest.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertThat(bufferedImage).checkIfSizeMatches(64, 64);
	}

}
