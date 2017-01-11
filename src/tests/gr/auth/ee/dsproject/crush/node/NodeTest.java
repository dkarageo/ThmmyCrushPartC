package tests.gr.auth.ee.dsproject.crush.node;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import gr.auth.ee.dsproject.crush.board.*;
import gr.auth.ee.dsproject.crush.node.*;
import gr.auth.ee.dsproject.crush.player.move.*;


public class NodeTest {

	@Test
	public void testNoArgumentsConstructor() {
		Node84208535 n = new Node84208535();
		
		assertEquals(null, n.getParent());
		assertNotEquals(null, n.getChildren());
		assertEquals(0, n.getChildren().size());
		assertEquals(0, n.getNodeDepth());
		assertEquals(null, n.getNodeMove());
		assertEquals(0.0, n.getNodeEvaluation(), 0.001);
		assertEquals(null, n.getNodeBoard());
	}
	
	
	@Test
	public void testParentArgumentConstructor() {
		Node84208535 parent = new Node84208535();
		
		Node84208535 n = new Node84208535(parent);
		
		assertNotEquals(null, n.getChildren());
		assertEquals(0, n.getChildren().size());
		assertEquals(0, n.getNodeDepth());
		assertEquals(null, n.getNodeMove());
		assertEquals(0.0, n.getNodeEvaluation(), 0.001);
		assertEquals(null, n.getNodeBoard());

		assertEquals(parent, n.getParent());
	}
	
	@Test
	public void testParentAndBoardArgumentsConstructor() {
		Node84208535 parent = new Node84208535();
		Board board = new Board(10);
		
		Node84208535 n = new Node84208535(parent, board);
		
		assertNotEquals(null, n.getChildren());
		assertEquals(0, n.getChildren().size());
		assertEquals(0, n.getNodeDepth());
		assertEquals(null, n.getNodeMove());
		assertEquals(0.0, n.getNodeEvaluation(), 0.001);
		
		assertEquals(parent, n.getParent());
		assertEquals(board, n.getNodeBoard());
	}
	
	@Test
	public void testParentBoardAndMoveArgumentsConstructor() {
		Node84208535 parent = new Node84208535();
		Board board = new Board(10);
		PlayerMove move = new PlayerMove();
		
		Node84208535 n = new Node84208535(parent, board, move);
		
		assertNotEquals(null, n.getChildren());
		assertEquals(0, n.getChildren().size());
		assertEquals(0, n.getNodeDepth());
		assertEquals(0.0, n.getNodeEvaluation(), 0.001);
		
		assertEquals(parent, n.getParent());
		assertEquals(board, n.getNodeBoard());
		assertEquals(move, n.getNodeMove());
	}
	
	@Test
	public void testSetParent() {
		Node84208535 parent = new Node84208535();
		Node84208535 n = new Node84208535();
		n.setParent(parent);
		
		assertEquals(parent, n.getParent());
	}
	
	@Test
	public void testSetNodeBoard() {
		Board board = new Board(10);
		
		Node84208535 n = new Node84208535();
		n.setNodeBoard(board);
		
		assertEquals(board, n.getNodeBoard());
	}
	
	@Test
	public void testSetChildren() {
		ArrayList<Node84208535> children = new ArrayList<>();
		Node84208535 n = new Node84208535();
		n.setChildren(children);
		
		assertEquals(children, n.getChildren());
	}
	
	@Test
	public void testSetNodeDepth() {
		Node84208535 n = new Node84208535();
		n.setNodeDepth(2);
		
		assertEquals(2, n.getNodeDepth());
	}
	
	@Test
	public void testSetNodeMove() {
		PlayerMove move = new PlayerMove(new Tile(), new Tile());
		
		Node84208535 n = new Node84208535();
		n.setNodeMove(move);
		
		assertEquals(move, n.getNodeMove());
	}
	
	@Test
	public void testSetNodeEvaluation() {
		Node84208535 n = new Node84208535();
		n.setNodeEvaluation(38.265);
		
		assertEquals(38.265, n.getNodeEvaluation(), 0.001);
	}
	
	@Test
	public void testAddChildException() {
		Node84208535 n = new Node84208535();
		boolean ex = false;
		
		try {
			n.addChild(null);
		} catch (Node84208535.NullNodeRuntimeException e) {
			ex = true;
		}
		
		assertTrue(ex);
	}
	
	@Test
	public void testAddChildOnPreviouslyEmptyChildren() {
		Node84208535 n = new Node84208535();
		
		Node84208535 child = new Node84208535();
		n.addChild(child);
		
		assertNotEquals(null, n.getChildren());
		
		assertEquals(1, n.getChildren().size());
		assertTrue(n.getChildren().contains(child));
	}
	
	@Test
	public void testAddChildOnPreviouslyOneChild() {
		Node84208535 n = new Node84208535();
		n.addChild(new Node84208535());
		
		Node84208535 child = new Node84208535();
		n.addChild(child);
		
		assertNotEquals(null, n.getChildren());
		
		assertEquals(2, n.getChildren().size());
		assertTrue(n.getChildren().contains(child));
	}
}