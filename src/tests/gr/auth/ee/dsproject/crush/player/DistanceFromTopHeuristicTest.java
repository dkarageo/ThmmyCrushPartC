package tests.gr.auth.ee.dsproject.crush.player;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gr.auth.ee.dsproject.crush.player.DistanceFromTopHeuristic;
import gr.auth.ee.dsproject.crush.board.*;
import gr.auth.ee.dsproject.crush.player.move.*;

public class DistanceFromTopHeuristicTest {
	Board noMoveBoard;
	
	private Board createBoard(int[][] boardScheme) {
		int rows = boardScheme.length;
		int cols = boardScheme[0].length;
		
		Board board = new Board(cols, rows, cols, rows);
		
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				board.setTile(y * 10 + x, x, y, boardScheme[y][x], false);
			}
		}
		
		return board;
	}
	
	@Before
	public void setUp() throws Exception {
		// Create a new 10 x 10 no move board.
		int[][] noMoveBoardScheme = {
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 4, 5, 6, 0, 1, 2, 3 },
				{ 2, 3, 4, 5, 6, 0, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 0, 1, 2, 3, 4, 5 },
				{ 4, 5, 6, 0, 1, 2, 3, 4, 5, 6 },
				{ 5, 6, 0, 1, 2, 3, 4, 5, 6, 0 },
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 4, 5, 6, 0, 1, 2, 3 },
				{ 2, 3, 4, 5, 6, 0, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 0, 1, 2, 3, 4, 5 }, 
		};
		
		noMoveBoard = createBoard(noMoveBoardScheme);
	}

	@Test
	public void testMoveAndBoardArgumentsConstructorWithValidArgs() {
		PlayerMove move = new PlayerMove(noMoveBoard.giveTileAt(3, 3),
									     noMoveBoard.giveTileAt(3, 4));
		
		DistanceFromTopHeuristic heur = new DistanceFromTopHeuristic(move, noMoveBoard);
		
		assertEquals(move, heur.getMove());
		assertEquals(noMoveBoard, heur.getBoard());
	}
	
	@Test
	public void testEvaluateWithHorizontalMove1() {
		PlayerMove move = new PlayerMove(noMoveBoard.giveTileAt(3, 0),
			     						 noMoveBoard.giveTileAt(4, 0));

		DistanceFromTopHeuristic heur = new DistanceFromTopHeuristic(move, noMoveBoard);
		
		assertEquals(0.0, heur.evaluate(), 0.1);
	}
	
	@Test
	public void testEvaluateWithHorizontalMove2() {
		PlayerMove move = new PlayerMove(noMoveBoard.giveTileAt(3, 3),
			     						 noMoveBoard.giveTileAt(4, 3));

		DistanceFromTopHeuristic heur = new DistanceFromTopHeuristic(move, noMoveBoard);
		
		assertEquals(30.0, heur.evaluate(), 0.1);
	}
	
	@Test
	public void testEvaluateWithHorizontalMove3() {
		PlayerMove move = new PlayerMove(noMoveBoard.giveTileAt(3, 9),
			     						 noMoveBoard.giveTileAt(4, 9));

		DistanceFromTopHeuristic heur = new DistanceFromTopHeuristic(move, noMoveBoard);
		
		assertEquals(90.0, heur.evaluate(), 0.1);
	}
	
	@Test
	public void testEvaluateWithVerticalMove1() {
		PlayerMove move = new PlayerMove(noMoveBoard.giveTileAt(1, 0),
			     						 noMoveBoard.giveTileAt(1, 1));

		DistanceFromTopHeuristic heur = new DistanceFromTopHeuristic(move, noMoveBoard);
		
		assertEquals(0.0, heur.evaluate(), 0.1);
	}
	
	@Test
	public void testEvaluateWithVerticalMove2() {
		PlayerMove move = new PlayerMove(noMoveBoard.giveTileAt(3, 3),
			     						 noMoveBoard.giveTileAt(3, 4));

		DistanceFromTopHeuristic heur = new DistanceFromTopHeuristic(move, noMoveBoard);
		
		assertEquals(30.0, heur.evaluate(), 0.1);
	}
	
	@Test
	public void testEvaluateWithVerticalMove3() {
		PlayerMove move = new PlayerMove(noMoveBoard.giveTileAt(3, 8),
			     						 noMoveBoard.giveTileAt(3, 9));

		DistanceFromTopHeuristic heur = new DistanceFromTopHeuristic(move, noMoveBoard);
		
		assertEquals(80.0, heur.evaluate(), 0.1);
	}
}
