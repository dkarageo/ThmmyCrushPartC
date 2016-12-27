package gr.auth.ee.dsproject.crush.node;

import java.util.ArrayList;

import gr.auth.ee.dsproject.crush.board.Board;
import gr.auth.ee.dsproject.crush.player.move.PlayerMove;


/**
 * Node class provides a way to model the nodes
 * needed in an a tree representing the state of 
 * a ThmmyCrush Board.
 * 
 * 
 * Public constructors defined in Node:
 * -public Node()
 * -public Node(Node parent)
 * -public Node(Node parent, Board board)
 * 
 * Public methods defined in Node:
 * -public Node getParent()
 * -public ArrayList<Node> getChildren()
 * -public Board getNodeBoard()
 * -public int getNodeDepth()
 * -public PlayerMove getNodeMove()
 * -public double getNodeEvaluation()
 * -public void setParent(Node parent)
 * -public void setChildren(ArrayList<Node> children)
 * -public void setNodeBoard(Board nodeBoard)
 * -public void setNodeDepth(int nodeDepth)
 * -public void setNodeMove(PlayerMove nodeMove)
 * -public void setNodeEvaluation(double nodeEvaluation)
 * -public void addChild(Node child) throws NullNodeRuntimeException
 * 
 * Exceptions defined in Node:
 * -public static class NullNodeRuntimeException extends RuntimeException
 * 
 * @author Dimitrios Karageorgiou
 * @version 0.3
 */
public class Node {
	
//==== Private instance variables ====
	
	/**
	 * The parent node of this node.
	 */
	private Node parent;
	
	/**
	 * An ArrayList containing all the children nodes of this 
	 * node.
	 */
	private ArrayList<Node> children;
	
	/**
	 * The depth current node has in the Tree.
	 */
	private int nodeDepth;
	
	/**
	 * The PlayerMove object associated with this node.
	 */
	private PlayerMove nodeMove;
	
	/**
	 * The Board object associated with this node.
	 */
	private Board nodeBoard;
	
	/**
	 * The evaluation score this node's move has.
	 */
	private double nodeEvaluation;

	
//==== Public Constructors ====
	
	/**
	 * Create a Node object without any arguments.
	 */
	public Node() { }
	
	/**
	 * Create a Node object by providing the parent Node object.
	 * 
	 * @param parent The Node to be considered the parent one in
	 * 				 the tree.
	 */
	public Node(Node parent) {
		this.parent = parent;
	}
	
	/**
	 * Create a Node object by providing the parent Node object
	 * and the Board object associated with this node. 
	 * 
	 * @param parent The Node to be considered the parent one in
	 * 				 the tree.
	 * @param board The board associated with this node.
	 */
	public Node(Node parent, Board board) {
		this.parent = parent;
		this.nodeBoard = board;
	}
	
	
//==== Public Getters ====
	
	/**
	 * Get the parent node of this node.
	 * 
	 * @return The parent Node object associated with
	 * 		   this node.
	 */
	public Node getParent() { return this.parent; }
	
	/**
	 * Get the children of this node.
	 * 
	 * @return An ArrayList<Node> object containing all the
	 * 		   children nodes of this node.
	 */
	public ArrayList<Node> getChildren() { return this.children; }
	
	/**
	 * Get the board of this node.
	 * 
	 * @return The Board object associated with this node.
	 */
	public Board getNodeBoard() { return this.nodeBoard; }
	
	/**
	 * Get the depth of this node in the tree.
	 * 
	 * @return An int representing the depth of this node in
	 * 		   its tree.
	 */
	public int getNodeDepth() { return this.nodeDepth; }
	
	/**
	 * Get the move associated with this node.
	 * 
	 * @return The PlayerMove object associated with this node.
	 */
	public PlayerMove getNodeMove() { return this.nodeMove; }
	
	/**
	 * Get the evaluation of this node.
	 * 
	 * @return A double representing the evaluation of this node,
	 * 		   provided by user.
	 */
	public double getNodeEvaluation() { return this.nodeEvaluation; }
	
	
//==== Public Setters ====
	
	/**
	 * Set the parent node of this node.
	 * 
	 * @param parent A Node object that will be set as parent of
	 * 				 the current one.
	 */
	public void setParent(Node parent) { this.parent = parent; }
	
	/**
	 * Set the children nodes of this node.
	 * 
	 * @param children An ArrayList<Node> object containing all
	 * 				   children nodes of the current one.
	 */
	public void setChildren(ArrayList<Node> children) { this.children = children; }
	
	/**
	 * Sets given board as the board of this node.
	 * 
	 * @param nodeBoard The Board object to be set as the board of
	 * 					this node. 
	 */
	public void setNodeBoard(Board nodeBoard) { this.nodeBoard = nodeBoard; }
	
	/**
	 * Sets given depth as the depth of this node.
	 * 
	 * @param nodeDepth An int representing the depth of the node
	 * 					in the tree.
	 */
	public void setNodeDepth(int nodeDepth) { this.nodeDepth = nodeDepth; }
	
	/**
	 * Sets the move associated with this node.
	 * 
	 * @param nodeMove A PlayerMove object to be set as the node's move.
	 */
	public void setNodeMove(PlayerMove nodeMove) { this.nodeMove = nodeMove; }
	
	/**
	 * Sets the evaluation score of this node.
	 * 
	 * @param nodeEvaluation A double to be set as the evaluation score
	 * 						 of this node.
	 */
	public void setNodeEvaluation(double nodeEvaluation) { this.nodeEvaluation = nodeEvaluation; }
	

//==== Public Methods ====
	
	/**
	 * Add given node as a child node to the current one.
	 * 
	 * If given node is null, a NullNodeRuntimeException is thrown.
	 * 
	 * @param child A Node object to be added as child node of this
	 * 				node.
	 * @throws NullNodeRuntimeException
	 */
	public void addChild(Node child) throws NullNodeRuntimeException 
	{
		if(child == null) throw new NullNodeRuntimeException();
		
		if (children == null) children = new ArrayList<>();
		
		children.add(child);
	}
	

//==== Exceptions defined in Node ====
	
	/**
	 * An exception to be thrown when a Node reference is null where it
	 * shouldn't.
	 */
	public static class NullNodeRuntimeException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
}
