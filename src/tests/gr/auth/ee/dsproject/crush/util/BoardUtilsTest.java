package tests.gr.auth.ee.dsproject.crush.util;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

import gr.auth.ee.dsproject.crush.board.Board;
import gr.auth.ee.dsproject.crush.util.BoardUtils;
import gr.auth.ee.dsproject.crush.board.Tile;
import gr.auth.ee.dsproject.crush.player.move.*;
import gr.auth.ee.dsproject.crush.board.CrushUtilities;


public class BoardUtilsTest {
	BoardUtils utils;
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
		
		/*
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				System.out.print(board.giveTileAt(x, y).getColor() + " ");
			}
			System.out.print("\n");
		}
		*/
		
		return board;
	}
	
	@Before
	public void setUp() throws Exception {
		utils = new BoardUtils();
		
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

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBoardCopyException() {
		boolean ex = false;
		
		try {
			utils.boardCopy(null);
		} catch (BoardUtils.NullBoardRuntimeException e) {
			ex = true;
		}
		
		assertTrue(ex);
	}
	
	@Test
	public void testBoardCopyWithNoMoveBoard() {
		Board noMoveBoardCopy = utils.boardCopy(noMoveBoard);
		
		for(int y = 0; y < noMoveBoard.getCols(); y++){
			for(int x = 0; x < noMoveBoard.getWidth(); x++) {
				assertEquals(noMoveBoard.giveTileAt(x, y).getX(), noMoveBoardCopy.giveTileAt(x, y).getX());
				assertEquals(noMoveBoard.giveTileAt(x, y).getY(), noMoveBoardCopy.giveTileAt(x, y).getY());
				assertTrue(noMoveBoard.giveTileAt(x, y).equals(noMoveBoardCopy.giveTileAt(x, y)));
			}
		}
	}
	
	@Test
	public void testDoMoveNullMoveException() {
		boolean ex = false;
		
		try {
			utils.doMove(noMoveBoard, null);
		} catch (BoardUtils.NullMoveRuntimeException e) {
			ex = true;
		}
		
		assertTrue(ex);
	}
	
	@Test
	public void testDoMoveNullBoardException() {
		PlayerMove move = new PlayerMove(noMoveBoard.giveTileAt(0, 0), 
										 noMoveBoard.giveTileAt(1, 0));
		
		boolean ex = false;
		
		try {
			utils.doMove(null, move);
		} catch (BoardUtils.NullBoardRuntimeException e) {
			ex = true;
		}
		
		assertTrue(ex);
	}
	
	@Test
	public void testDoMoveVertically() {
		Board noMoveBoardCopy = utils.boardCopy(noMoveBoard);
		
		PlayerMove move = new PlayerMove(noMoveBoardCopy.giveTileAt(2, 2), 
				 						 noMoveBoardCopy.giveTileAt(2, 3));
		
		noMoveBoardCopy = utils.doMove(noMoveBoardCopy, move);
		
		assertEquals(2, noMoveBoardCopy.giveTileAt(2, 2).getX());
		assertEquals(2, noMoveBoardCopy.giveTileAt(2, 2).getY());
		
		assertEquals(2, noMoveBoardCopy.giveTileAt(2, 3).getX());
		assertEquals(3, noMoveBoardCopy.giveTileAt(2, 3).getY());
		
		assertEquals(noMoveBoard.giveTileAt(2, 2).getColor(), noMoveBoardCopy.giveTileAt(2, 3).getColor());
		assertEquals(noMoveBoard.giveTileAt(2, 3).getColor(), noMoveBoardCopy.giveTileAt(2, 2).getColor());
	}
	
	@Test
	public void testFindInDirectionSameColorTilesNullBoardException() {
		boolean ex = false;
		
		try {
			utils.findInDirectionSameColorTiles(null, noMoveBoard.giveTileAt(0, 0), CrushUtilities.UP, 3);
		} catch (BoardUtils.NullBoardRuntimeException e) {
			ex = true;
		}
		
		assertTrue(ex);
	}
	
	@Test
	public void testFindInDirectionSameColorTilesNullTileException() {
		boolean ex = false;
		
		try {
			utils.findInDirectionSameColorTiles(noMoveBoard, null, CrushUtilities.UP, 3);
		} catch (BoardUtils.NullTileRuntimeException e) {
			ex = true;
		}
		
		assertTrue(ex);
	}
	
	@Test
	public void testFindInDirectionSameColorTilesInvalidDirectionException() {
		boolean ex = false;
		
		try {
			utils.findInDirectionSameColorTiles(noMoveBoard, noMoveBoard.giveTileAt(0, 0), 1821, 3);
		} catch (BoardUtils.InvalidDirectionsRuntimeException e) {
			ex = true;
		}
		
		assertTrue(ex);
	}
	
	@Test
	public void testFindInDirectionSameColorTiles() {
		int[][] scheme = {
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 4, 6, 6, 0, 1, 2, 3 },
				{ 2, 3, 6, 6, 6, 6, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 6, 1, 2, 3, 4, 5 },
				{ 4, 5, 6, 0, 6, 2, 3, 4, 5, 6 },
				{ 5, 6, 0, 1, 2, 3, 4, 5, 6, 0 },
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 4, 5, 6, 0, 1, 2, 3 },
				{ 2, 3, 4, 5, 6, 0, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 0, 1, 2, 3, 4, 5 }, 
		};
		
		Board board = createBoard(scheme);
				
		Set<Tile> toLeft = utils.findInDirectionSameColorTiles(board, board.giveTileAt(4, 2), CrushUtilities.LEFT, 3);
		assertEquals(2, toLeft.size());
		assertTrue(toLeft.contains(board.giveTileAt(3, 2)));
		assertTrue(toLeft.contains(board.giveTileAt(2, 2)));
		
		Set<Tile> toRight = utils.findInDirectionSameColorTiles(board, board.giveTileAt(4, 2), CrushUtilities.RIGHT, 3);
		assertEquals(1, toRight.size());
		assertTrue(toRight.contains(board.giveTileAt(5, 2)));
		
		Set<Tile> toDown = utils.findInDirectionSameColorTiles(board, board.giveTileAt(4, 2), CrushUtilities.DOWN, 3);
		
		assertEquals(1, toDown.size());
		assertTrue(toDown.contains(board.giveTileAt(4, 1)));
		
		Set<Tile> toUp = utils.findInDirectionSameColorTiles(board, board.giveTileAt(4, 2), CrushUtilities.UP, 3);
		assertEquals(2, toUp.size());
		assertTrue(toUp.contains(board.giveTileAt(4, 3)));
		assertTrue(toUp.contains(board.giveTileAt(4, 4)));
	}
	
	@Test
	public void testFindAdjacentSameColorTilesNullBoardException() {
		boolean ex = false;
		
		try {
			utils.findAdjacentSameColorTiles(null, noMoveBoard.giveTileAt(0, 0), 10);
		} catch (BoardUtils.NullBoardRuntimeException e) {
			ex = true;
		}
		
		assertTrue(ex);
	}
	
	@Test
	public void testFindAdjacentSameColorTilesNullTileException() {
		boolean ex = false;
		
		try {
			utils.findAdjacentSameColorTiles(noMoveBoard, null, 10);
		} catch (BoardUtils.NullTileRuntimeException e) {
			ex = true;
		}
		
		assertTrue(ex);
	}
	
	@Test
	public void testFindAdjacentSameColorTiles() {
		int[][] scheme = {
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 4, 6, 6, 0, 1, 2, 3 },
				{ 2, 3, 6, 6, 6, 6, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 6, 1, 2, 3, 4, 5 },
				{ 4, 5, 6, 0, 1, 2, 3, 4, 5, 6 },
				{ 5, 6, 0, 1, 2, 3, 4, 5, 6, 0 },
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 4, 5, 6, 0, 1, 2, 3 },
				{ 2, 3, 4, 5, 6, 0, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 0, 1, 2, 3, 4, 5 }, 
		};
		
		Board board = createBoard(scheme);
		
		Set<Tile> tiles = utils.findAdjacentSameColorTiles(board, board.giveTileAt(4, 2), 5);
		
		assertEquals(5, tiles.size());
		assertTrue(tiles.contains(board.giveTileAt(2, 2)));
		assertTrue(tiles.contains(board.giveTileAt(3, 2)));
		assertTrue(tiles.contains(board.giveTileAt(5, 2)));
		assertTrue(tiles.contains(board.giveTileAt(4, 1)));
		assertTrue(tiles.contains(board.giveTileAt(4, 3)));
	}
	
	@Test
	public void testFindAdjacentSameColorTiles2() {
		int[][] scheme = {
				{ 0, 1, 2, 3, 6, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 4, 6, 6, 0, 1, 2, 3 },
				{ 2, 3, 3, 2, 6, 6, 6, 2, 3, 4 },
				{ 3, 4, 5, 6, 6, 1, 2, 3, 4, 5 },
				{ 4, 5, 6, 0, 6, 2, 3, 4, 5, 6 },
				{ 5, 6, 0, 1, 2, 3, 4, 5, 6, 0 },
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 4, 5, 6, 0, 1, 2, 3 },
				{ 2, 3, 4, 5, 6, 0, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 0, 1, 2, 3, 4, 5 }, 
		};
		
		Board board = createBoard(scheme);
		
		Set<Tile> tiles = utils.findAdjacentSameColorTiles(board, board.giveTileAt(4, 2), 5);
		
		assertEquals(6, tiles.size());
		assertTrue(tiles.contains(board.giveTileAt(4, 1)));
		assertTrue(tiles.contains(board.giveTileAt(4, 0)));
		assertTrue(tiles.contains(board.giveTileAt(4, 3)));
		assertTrue(tiles.contains(board.giveTileAt(4, 4)));
		assertTrue(tiles.contains(board.giveTileAt(5, 2)));
		assertTrue(tiles.contains(board.giveTileAt(6, 2)));
	}
	
	@Test
	public void testFindTilesThatCrush() {
		int[][] scheme = {
				{ 0, 1, 2, 3, 6, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 2, 6, 6, 0, 1, 2, 3 },
				{ 1, 2, 2, 2, 6, 6, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 6, 1, 2, 3, 4, 5 },
				{ 4, 5, 6, 0, 6, 2, 3, 4, 5, 6 },
				{ 5, 6, 0, 1, 2, 3, 4, 5, 6, 0 },
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 4, 5, 6, 0, 1, 2, 3 },
				{ 2, 3, 4, 5, 6, 0, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 0, 1, 2, 3, 4, 5 }, 
		};
		
		Board board = createBoard(scheme);
		
		Set<Tile> allAdjacent = new HashSet<>(); 
		
		allAdjacent.addAll(utils.findAdjacentSameColorTiles(board, board.giveTileAt(3, 2), 2));
		allAdjacent.addAll(utils.findAdjacentSameColorTiles(board, board.giveTileAt(4, 2), 2));
		allAdjacent.add(board.giveTileAt(3, 2));
		allAdjacent.add(board.giveTileAt(4, 2));
		
		// Assertions to test that the initial set is correct.
		assertEquals(10, allAdjacent.size());
		assertTrue(allAdjacent.contains(board.giveTileAt(4, 0)));
		assertTrue(allAdjacent.contains(board.giveTileAt(4, 1)));
		assertTrue(allAdjacent.contains(board.giveTileAt(4, 2)));
		assertTrue(allAdjacent.contains(board.giveTileAt(4, 3)));
		assertTrue(allAdjacent.contains(board.giveTileAt(4, 4)));
		assertTrue(allAdjacent.contains(board.giveTileAt(5, 2)));
		assertTrue(allAdjacent.contains(board.giveTileAt(3, 2)));
		assertTrue(allAdjacent.contains(board.giveTileAt(2, 2)));
		assertTrue(allAdjacent.contains(board.giveTileAt(1, 2)));
		assertTrue(allAdjacent.contains(board.giveTileAt(3, 1)));
		
		// Now test the findTIlesThatCrushMethod.
		Set<Tile> crushTiles = utils.findTilesThatCrush(allAdjacent);
		
		assertEquals(8, crushTiles.size());
		assertTrue(crushTiles.contains(board.giveTileAt(4, 0)));
		assertTrue(crushTiles.contains(board.giveTileAt(4, 1)));
		assertTrue(crushTiles.contains(board.giveTileAt(4, 2)));
		assertTrue(crushTiles.contains(board.giveTileAt(4, 3)));
		assertTrue(crushTiles.contains(board.giveTileAt(4, 4)));
		assertTrue(crushTiles.contains(board.giveTileAt(3, 2)));
		assertTrue(crushTiles.contains(board.giveTileAt(2, 2)));
		assertTrue(crushTiles.contains(board.giveTileAt(1, 2)));
	}
	
	@Test
	public void testFindTilesThatCrushWithNoTilesToCrush() {
		int[][] scheme = {
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 2, 5, 6, 0, 1, 2, 3 },
				{ 1, 1, 2, 2, 6, 6, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 6, 1, 2, 3, 4, 5 },
				{ 4, 5, 6, 0, 1, 2, 3, 4, 5, 6 },
				{ 5, 6, 0, 1, 2, 3, 4, 5, 6, 0 },
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 4, 5, 6, 0, 1, 2, 3 },
				{ 2, 3, 4, 5, 6, 0, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 0, 1, 2, 3, 4, 5 }, 
		};
		
		Board board = createBoard(scheme);
		
		Set<Tile> allAdjacent = new HashSet<>(); 
		
		allAdjacent.addAll(utils.findAdjacentSameColorTiles(board, board.giveTileAt(3, 2), 2));
		allAdjacent.addAll(utils.findAdjacentSameColorTiles(board, board.giveTileAt(4, 2), 2));
		allAdjacent.add(board.giveTileAt(3, 2));
		allAdjacent.add(board.giveTileAt(4, 2));
		
		// Assertions to test that the initial set is correct.
		assertEquals(6, allAdjacent.size());
		assertTrue(allAdjacent.contains(board.giveTileAt(4, 2)));
		assertTrue(allAdjacent.contains(board.giveTileAt(4, 3)));
		assertTrue(allAdjacent.contains(board.giveTileAt(5, 2)));
		assertTrue(allAdjacent.contains(board.giveTileAt(3, 2)));
		assertTrue(allAdjacent.contains(board.giveTileAt(2, 2)));
		assertTrue(allAdjacent.contains(board.giveTileAt(3, 1)));
		
		// Now test the findTIlesThatCrushMethod.
		Set<Tile> crushTiles = utils.findTilesThatCrush(allAdjacent);
		
		assertEquals(0, crushTiles.size());
	}
	
	@Test
	public void testIsValidCords() {
		for (int y = 0; y < CrushUtilities.NUMBER_OF_ROWS; y++) {
			for (int x = 0; x < CrushUtilities.NUMBER_OF_COLUMNS; x++) {
				assertTrue(BoardUtils.isValidCords(x, y));
			}
		}
		
		assertFalse(BoardUtils.isValidCords(-1, 0));
		assertFalse(BoardUtils.isValidCords(0, -1));
		assertFalse(BoardUtils.isValidCords(CrushUtilities.NUMBER_OF_COLUMNS, 0));
		assertFalse(BoardUtils.isValidCords(0, CrushUtilities.NUMBER_OF_ROWS));
	}
	
	@Test
	public void testGetBoardAfterMoveAndCrush() {
		int[][] boardScheme = {
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 6, 5, 6, 6, 1, 2, 3 },
				{ 2, 3, 4, 5, 6, 0, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 0, 1, 2, 3, 4, 5 },
				{ 4, 5, 6, 0, 1, 2, 3, 4, 5, 6 },
				{ 5, 6, 0, 1, 2, 3, 4, 5, 6, 0 },
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 4, 5, 6, 0, 1, 2, 3 },
				{ 2, 3, 4, 5, 6, 0, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 0, 1, 2, 3, 4, 5 }, 
		};
		
		Board board = createBoard(boardScheme);
		
		Board afterMoveBoard = utils.getBoardAfterMoveAndCrush(
				board, new PlayerMove(board.giveTileAt(3, 1), board.giveTileAt(4, 1)));
		
		/*
		for (int y = 0; y < afterMoveBoard.getRows(); y++) {
			for (int x = 0; x < afterMoveBoard.getCols(); x++) {
				System.out.print(afterMoveBoard.giveTileAt(x, y).getColor() + " ");
			}
			System.out.print("\n");
		}
		*/
		
		assertEquals(4, afterMoveBoard.giveTileAt(4, 1).getColor());
		assertEquals(5, afterMoveBoard.giveTileAt(5, 1).getColor());
		assertEquals(6, afterMoveBoard.giveTileAt(6, 1).getColor());
	}
	
	@Test
	public void testGetBoardAfterMoveAndCrush2() {
		int[][] boardScheme = {
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 6, 5, 6, 6, 1, 2, 3 },
				{ 2, 3, 4, 5, 6, 0, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 0, 1, 2, 3, 4, 5 },
				{ 4, 5, 6, 0, 1, 2, 3, 4, 5, 6 },
				{ 5, 6, 0, 1, 2, 3, 4, 5, 6, 0 },
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 4, 5, 6, 0, 1, 2, 3 },
				{ 2, 3, 4, 5, 6, 0, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 0, 1, 3, 3, 3, 5 }, 
		};
		
		Board board = createBoard(boardScheme);
		
		Board afterMoveBoard = utils.getBoardAfterMoveAndCrush(
				board, new PlayerMove(board.giveTileAt(3, 1), board.giveTileAt(4, 1)));
		
		/*
		for (int y = 0; y < afterMoveBoard.getRows(); y++) {
			for (int x = 0; x < afterMoveBoard.getCols(); x++) {
				System.out.print(afterMoveBoard.giveTileAt(x, y).getColor() + " ");
			}
			System.out.print("\n");
		}
		*/
				
		assertEquals(4, afterMoveBoard.giveTileAt(4, 1).getColor());
		assertEquals(5, afterMoveBoard.giveTileAt(5, 1).getColor());
		assertEquals(6, afterMoveBoard.giveTileAt(6, 2).getColor());
		assertEquals(6, afterMoveBoard.giveTileAt(6, 7).getColor());
		assertEquals(0, afterMoveBoard.giveTileAt(7, 7).getColor());
		assertEquals(1, afterMoveBoard.giveTileAt(8, 7).getColor());
		assertEquals(0, afterMoveBoard.giveTileAt(6, 8).getColor());
		assertEquals(1, afterMoveBoard.giveTileAt(7, 8).getColor());
		assertEquals(2, afterMoveBoard.giveTileAt(8, 8).getColor());
		assertEquals(1, afterMoveBoard.giveTileAt(6, 9).getColor());
		assertEquals(2, afterMoveBoard.giveTileAt(7, 9).getColor());
		assertEquals(3, afterMoveBoard.giveTileAt(8, 9).getColor());
	}
	
	@Test
	public void testGetBoardAfterMoveAndCrush3() {
		int[][] boardScheme = {
				{ 0, 1, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 2, 3, 6, 5, 6, 6, 1, 2, 3 },
				{ 2, 3, 4, 5, 6, 0, 1, 2, 3, 4 },
				{ 3, 4, 5, 6, 0, 1, 2, 3, 4, 5 },
				{ 4, 5, 6, 0, 1, 2, 3, 4, 5, 6 },
				{ 5, 6, 0, 1, 2, 3, 4, 5, 6, 0 },
				{ 0, 3, 2, 3, 4, 5, 6, 0, 1, 2 },
				{ 1, 3, 3, 4, 5, 6, 0, 1, 2, 3 },
				{ 2, 3, 4, 5, 6, 0, 1, 2, 3, 4 },
				{ 3, 3, 5, 6, 0, 1, 3, 3, 3, 5 }, 
		};
		
		Board board = createBoard(boardScheme);
		
		Board afterMoveBoard = utils.getBoardAfterMoveAndCrush(
				board, new PlayerMove(board.giveTileAt(3, 1), board.giveTileAt(4, 1)));
		
		/*
		for (int y = 0; y < afterMoveBoard.getRows(); y++) {
			for (int x = 0; x < afterMoveBoard.getCols(); x++) {
				System.out.print(afterMoveBoard.giveTileAt(x, y).getColor() + " ");
			}
			System.out.print("\n");
		}
		*/
				
		assertEquals(4, afterMoveBoard.giveTileAt(4, 1).getColor());
		assertEquals(5, afterMoveBoard.giveTileAt(5, 1).getColor());
		assertEquals(6, afterMoveBoard.giveTileAt(6, 2).getColor());
		assertEquals(6, afterMoveBoard.giveTileAt(6, 7).getColor());
		assertEquals(0, afterMoveBoard.giveTileAt(7, 7).getColor());
		assertEquals(1, afterMoveBoard.giveTileAt(8, 7).getColor());
		assertEquals(0, afterMoveBoard.giveTileAt(6, 8).getColor());
		assertEquals(1, afterMoveBoard.giveTileAt(7, 8).getColor());
		assertEquals(2, afterMoveBoard.giveTileAt(8, 8).getColor());
		assertEquals(1, afterMoveBoard.giveTileAt(6, 9).getColor());
		assertEquals(2, afterMoveBoard.giveTileAt(7, 9).getColor());
		assertEquals(3, afterMoveBoard.giveTileAt(8, 9).getColor());
		assertEquals(4, afterMoveBoard.giveTileAt(1, 7).getColor());
		assertEquals(5, afterMoveBoard.giveTileAt(1, 8).getColor());
		assertEquals(6, afterMoveBoard.giveTileAt(1, 9).getColor());		
	}
}
