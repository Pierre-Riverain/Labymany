package fr.pierreriverain.labymany.assertions;

import org.assertj.core.api.AbstractAssert;

import fr.pierreriverain.maze.Maze;
import fr.pierreriverain.maze.MazeCell;

public class MazeAssert extends AbstractAssert<MazeAssert, Maze> {
	private Maze maze;

	public MazeAssert(Maze maze) {
		super(maze, MazeAssert.class);
		this.maze = maze;
	}

	/*
	 * Cette méthode permet de vérifier que la génération du labyrinthe s'est réalisée correctement. Pour cela, la méthode vérifie que les cellules actives ont
	 * bien été visité par le générateur et que les cellules qui ne sont pas actives ne sont pas visité par le générateur. Sinon, le test échoue.
	 */
	public MazeAssert isCorrectlyGenerated() {
		isNotNull();
		if (maze.getMazeCells() == null) {
			failWithMessage("Le labyrinthe n'est pas initialisé.");
		}
		for (MazeCell[] tab_cells : maze.getMazeCells()) {
			for (MazeCell cell : tab_cells) {
				if ((cell.isEnabled() && !cell.isVisitedByMazeGenerator())) {
					failWithMessage(
							"Le labyrinthe n'est pas correctement généré : la cellule de la colonne : " + cell.getColumnIndex()
									+ "et ligne : " + cell.getRowIndex() + " est active mais n'a pas été visitée par le générateur.");
				} else if ((!cell.isEnabled() && cell.isVisitedByMazeGenerator())) {
					failWithMessage(
							"Le labyrinthe n'est pas correctement généré : la cellule de la colonne : " + cell.getColumnIndex()
									+ "et ligne : " + cell.getRowIndex() + " n'est pas active mais elle à été visitée par le générateur.");
				}
			}
		}
		return this;
	}
}
