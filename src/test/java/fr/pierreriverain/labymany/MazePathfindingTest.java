package fr.pierreriverain.labymany;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.pierreriverain.maze.Maze;
import fr.pierreriverain.maze.MazeCell;
import fr.pierreriverain.maze.MazeCellDoor;
import fr.pierreriverain.maze.MazeCursor;
import fr.pierreriverain.maze.MazePathfinding;

@ExtendWith(MockitoExtension.class)
public class MazePathfindingTest {
	
	@Mock
	Maze maze;

	MazePathfinding mazePathfinding;
	
	MazeCell[][] tab_mazeCells;
	
	public MazePathfindingTest() {
		super();
	}

	/**
	 * Cette méthode permet d'initialiser le tableau de cellule que le labyrinthe mocké est sensé retourner. Ce labyrinthe fictif doit ressembler à :
	 * 
	 *   XXXXXXXXXXXXX
	 *   X     X  X  X
	 *   XXXX  X  X  X
	 *   X     X  X  X
	 *   X  XXXX  X  X
	 *   X  X        X
	 *   X  X  X  XXXX
	 *   X     X     X
	 *   XXXXXXXXXXXXX
	 *   
	 */
	@BeforeEach
	public void initialize() {
		
		tab_mazeCells = new MazeCell[4][4];
		
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				
				tab_mazeCells[x][y] = new MazeCell(x, y);
				
				switch(y) {
				case 0://
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
	public void should_generate_maze_path_size() throws Exception {
		when(maze.getMazeCells()).thenReturn(tab_mazeCells);
		when(maze.isGenerated()).thenReturn(true);
		mazePathfinding = new MazePathfinding(maze, new MazeCursor(0, 0), new MazeCursor(3, 0));
		
		mazePathfinding.generateMazePath();
		
		assertThat(mazePathfinding.getMazePath().size()).isEqualTo(12);
	}
}
