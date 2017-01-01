package gr.auth.ee.dsproject.crush.player;

import java.util.ArrayList;

import gr.auth.ee.dsproject.crush.board.Board;
import gr.auth.ee.dsproject.crush.board.CrushUtilities;
import gr.auth.ee.dsproject.crush.defplayers.AbstractPlayer;
import gr.auth.ee.dsproject.crush.heuristics.*;
import gr.auth.ee.dsproject.crush.node.Node;
import gr.auth.ee.dsproject.crush.player.move.PlayerMove;


public class MinMaxPlayer implements AbstractPlayer
{
	
//==== Instance Variables ====
	
    int score;
    int id;
    String name;

    
//==== Public Constructors ====
    
    public MinMaxPlayer (Integer pid)
    {
        name = "MinMax";
    	id = pid;
        score = 0;
    }

    
//==== Public Getters ====    
    
    public String getName ()
    {
        return name;
    }

    public int getId ()
    {
        return id;
    }

    public int getScore ()
    {
        return score;
    }
    

//==== Public Setters ====
    
    public void setScore (int score)
    {
        this.score = score;
    }
    
    public void setId (int id)
    {
        this.id = id;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    
//==== Public Methods ====
    
    public int[] getNextMove (ArrayList<int[]> availableMoves, Board board)
    {
    	// Create the root node representing current state of board.
    	// This is the initial state for minimax.
    	Node root = new Node(null, board, null);
    	
    	// Create the minimax tree to a depth of 2.
    	createMinimaxTree(root, 2, -Double.MAX_VALUE, Double.MAX_VALUE, true);
    	
    	// Return the highest scoring move out of minimax tree.
    	return findBestMove(root).toCordsArray();
    }   

   
//==== Private Methods ====
    
    /**
     * Creates all the children of the given node.
     * 
     * It creates all the children, i.e. the next states,
     * that are possible based on the board of the given node.
     * 
     * It finds out all possible moves on the board of given node
     * and for every single one of these moves creates a child node
     * with its move set to the move lead there, its board set to
     * the board this move caused to be created and parent set to
     * given node.
     * 
     * @param n The node whose children should be created.
     */
    private void createChildren(Node n) {

    	Board curBoard = n.getNodeBoard();
    	
    	for (int[] dirMove : CrushUtilities.getAvailableMoves(curBoard)) {
    		
    		// Convert old style move of [x, y, direction] to PlayerMove object.
    		int[] cordsMove = CrushUtilities.calculateNextMove(dirMove);
    		PlayerMove move = new PlayerMove(
    				curBoard.giveTileAt(cordsMove[0], cordsMove[1]),
    				curBoard.giveTileAt(cordsMove[2], cordsMove[3])
    		);
    		
    		Board afterMoveBoard = CrushUtilities.boardAfterFullMove(curBoard, dirMove);
    		
    		Node child = new Node(n, afterMoveBoard, move);
    		
    		n.addChild(child);
    	}
    }
    
    /**
     * Creates an A-B pruned minimax tree under the root node until
     * the given depth and returns the evaluation of the current state
     * for specified player, maximizing or minimizing.
     * 
     * Root node should contain at least the board of the initial state.
     * 
     * It should normally be called with min and max arguments of
     * -Double.MAX_VALUE and Double.MAX_VALUE accordingly. Though
     * they can be set to any best matching value and are the 
     * initial values used for A-B pruning.
     * 
     * Maximizing defines if the current state should be checked from
     * player's perspective, i.e. maximizing, or enemy's perspective,
     * i.e. minimizing. Maximizing player considers greater values better,
     * when minimizing player considers lower values better.
     * 
     * @param n The root node for the tree to be created.
     * @param depth The depth to which the tree will be created.
     * @param min The minimum evaluation value that is considered valid.
     * @param max The maximum evaluation value that is considered valid.
     * @param maximizing True for getting evaluation for player's perspective,
     * 					 false for getting evaluation for enemy's perspective.	
     * @return The evaluation of the current state for given player.
     */
    private double createMinimaxTree(Node n, int depth, double min, 
    								 double max, boolean maximizing) 
    {
    	// Find the evaluation of current state. Since this method is going
    	// to run one more time than depth, the evaluation here is the
    	// opposite than maximizing. This happens because the first call
    	// is the root node, and for every move it's effect is calculated
    	// on the next run. 
    	if (n.getParent() == null) {
    		// If parent is null, then no move lead to current state so
    		// evaluation of current state is 0.
    		n.setNodeEvaluation(0);
    	
    	} else if (maximizing) {
    		n.setNodeEvaluation(
    				-simpleHeuristicEvaluation(n.getParent().getNodeBoard(), n.getNodeMove())
    		);
    	
    	} else {
    		n.setNodeEvaluation(
    				simpleHeuristicEvaluation(n.getParent().getNodeBoard(), n.getNodeMove())
    		);
    	}
    	
    	// If current node is not a leaf, then evaluate its children,
    	// and form the overall evaluation by using the ones from deeper
    	// levels.
    	if (depth != 0) {
    		createChildren(n);
    		if (n.getChildren().size() == 0) return n.getNodeEvaluation();
    		
    		if (maximizing) {
    			double cMax = -Double.MAX_VALUE;
    			
    			for (Node child : n.getChildren()) {
    				double eval = createMinimaxTree(child, depth - 1, cMax, max, false);
    				
    				cMax = Math.max(cMax, eval);
    				
//    				if (n.getNodeEvaluation() + eval >= max) {
//    					cMax = max;
//    					break;
//    				}
        		}
    			
    			n.setNodeEvaluation(n.getNodeEvaluation() + cMax);
    			
    		} else {
    			double cMin = Double.MAX_VALUE;
    			
    			for (Node child : n.getChildren()) {
    				double eval = createMinimaxTree(child, depth - 1, cMin, max, true);
    				
    				cMin = Math.min(cMin, eval);
    				
//    				if (n.getNodeEvaluation() + eval <= min) {
//    					cMin = min;
//    					break;
//    				}	
        		}
    			
    			n.setNodeEvaluation(n.getNodeEvaluation() + cMin);    			
    		}
    	}
    	
    	return n.getNodeEvaluation();
    }
       
    /**
     * Find the move that lead to the highest evaluated branch
     * on a minimax tree.
     * 
     * @param root The root node of the minimax tree.
     * @return The move lead to highest scoring branch of
     * 		   the tree.
     */
    private PlayerMove findBestMove(Node root) {    	
    	PlayerMove bestMove = null;
    	double max = -Double.MAX_VALUE;
    	    	
    	for (Node child : root.getChildren()) {
    		double curEval = child.getNodeEvaluation();
    		
    		if (max < curEval) {
    			max = curEval;
    			bestMove = child.getNodeMove();
    		}
    	}
    	
    	return bestMove;
    }    
    
    /**
     * Do a simple heuristic evaluation of the given move, based on the
     * given board.
     * 
     * Evaluation is done using solely a CandiesRemovedHeuristic.
     * 
     * @param board A board on which the move will be evaluated.
     * @param move The move to be evaluated.
     * @return A double representing how good the move is.
     */
    private double simpleHeuristicEvaluation(Board board, PlayerMove move) {
    	HeuristicsEngine engine = new HeuristicsEngine(new SliderMathModel(1.0));
    	
    	engine.add(new CandiesRemovedHeuristic(move, board), SliderMathModel.VERY_HIGH);
    	
    	return engine.evaluate();
    }

// ==== Unused Code ====
//    
//    /**
//     * 
//     * @param board
//     * @param move
//     * @return
//     */
//    private double complexHeuristicEvaluation(Board board, PlayerMove move) {
//    	HeuristicsEngine engine = new HeuristicsEngine(new SliderMathModel(1.0));
//    	
//    	engine.add(new CandiesRemovedHeuristic(move, board), SliderMathModel.VERY_HIGH);
//    	//engine.add(new DistanceFromTopHeuristic(move, board), SliderMathModel.VERY_LOW);
//    	
//    	return engine.evaluate();
//    }
}