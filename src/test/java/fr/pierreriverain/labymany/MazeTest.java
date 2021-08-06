package fr.pierreriverain.labymany;

import static fr.pierreriverain.labymany.assertions.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pierreriverain.maze.Maze;
import fr.pierreriverain.maze.MazeCursor;

public class MazeTest {
	
	Maze maze;
	
	@Test
	public void should_generate_maze_correctly() {
		maze = new Maze(8, 8);
		
		maze.initialize();
		maze.generate();
		
		assertThat(maze).isCorrectlyGenerated();
	}
	
	@Test
	public void should_generate_maze_only_cell_enabled() {
		//Ce tableau de booléen permet de définir la forme du labyrinthe en cercle.
		boolean[][] mazeCircularForm = {
				{false, false, true, true, true, true, false, false},
				{false, true, true, true, true, true, true, false},
				{true, true, true, true, true, true, true, true},
				{true, true, true, true, true, true, true, true},
				{true, true, true, true, true, true, true, true},
				{true, true, true, true, true, true, true, true},
				{false, true, true, true, true, true, true, false},
				{false, false, true, true, true, true, false, false},
		};
		
		maze = new Maze(8, 8, mazeCircularForm);
		
		maze.initialize();
		maze.generate();
		
		assertThat(maze).isCorrectlyGenerated();
	}
	
	@Test
	public void should_generate_maze_with_a_custom_maze_generator_cursor() {
		maze = new Maze(8, 8, new MazeCursor());
		
		maze.initialize();
		maze.generate();
		
		assertThat(maze).isCorrectlyGenerated();
	}
	
	@BeforeEach
	public void tearDownPerTest() {
		maze = null;
	}
}
