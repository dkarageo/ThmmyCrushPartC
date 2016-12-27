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
    	// Create the root node that only has children.
    	Node root = new Node(null, null);
    	root.setNodeMove(null);
    	createChildren(root, board, availableMoves, 0);
    	
    	// Create the minimax tree to a depth of 4.
    	evaluateMinimax(root, 2, -Double.MAX_VALUE, Double.MAX_VALUE, true);
    	
    	// Return the highest scoring move out of minimax tree.
    	return findBestMove(root).toCordsArray();
    }   

   
//==== Private Methods ====
    
    /**
     * 
     * @param root
     * @return
     */
    private PlayerMove findBestMove (Node root) {        
    	System.out.print("Finfind best move");
    	
    	PlayerMove bestMove = null;
    	double max = -Double.MAX_VALUE;
    	    	
    	for (Node child : root.getChildren()) {
    		double curEval = child.getNodeEvaluation();
    		
    		if (max < curEval) {
    			max = curEval;
    			bestMove = child.getNodeMove();
    		}
    	}
    	
    	System.out.println("x1: " + bestMove.getX1() + " y1: " + bestMove.getY1() + " x2: " + bestMove.getX2() + " y2: " + bestMove.getY2());
    	
    	return bestMove;
    }
    
    private double evaluateMinimax ( 
		  	  Node curNode, int depth, double min, double max, 
		  	  boolean maximizing)
    {    	
    	// On leaves use all available heuristics in order to get the best
		// possible evaluation, since no more future check is done by minimax.
    	if (depth == 0 && maximizing) {    		
    		double eval = complexHeuristicEvaluation(
    				curNode.getNodeBoard(), curNode.getNodeMove()
    		);
    		curNode.setNodeEvaluation(curNode.getNodeEvaluation() + eval);
    		
    		return curNode.getNodeEvaluation();
    		
	    } else if (depth == 0 && !maximizing) {
	    	double eval = complexHeuristicEvaluation(
    				curNode.getNodeBoard(), curNode.getNodeMove()
    		);
    		curNode.setNodeEvaluation(curNode.getNodeEvaluation() - eval);
    		
    		return curNode.getNodeEvaluation();
	    }
    	
	    if (maximizing) {	    	
	    	// If the node contains a move, i.e. it's not the root one,
	    	// evaluate this partial score of its move and set it as
	    	// node's evaluation.
	    	if (curNode.getNodeMove() != null) {
		    	curNode.setNodeEvaluation(
		    			curNode.getNodeEvaluation() +
		    			simpleHeuristicEvaluation(
		    					curNode.getNodeBoard(),	curNode.getNodeMove()
		    	));
	    	}
	    	
	    	double cMax = -Double.MAX_VALUE;
	    		    	
	    	for (Node child : curNode.getChildren()) {  			    		
	    		createChildren(
	    				child, 
	    				CrushUtilities.boardAfterFullMove(
	    						child.getNodeBoard(), child.getNodeMove().toDirArray()),
	    				CrushUtilities.getAvailableMoves(child.getNodeBoard()),
	    				child.getNodeEvaluation()
	    		);
	    		
	    		double eval = evaluateMinimax(child, depth - 1, cMax, max, false);
	    		
	    		cMax = Math.max(cMax, eval);
	    		if (cMax > max) {
	    			cMax = max;
	    			break;
	    		}
	    	}
	    	
	    	curNode.setNodeEvaluation(cMax + curNode.getNodeEvaluation());
	    	
	    	return curNode.getNodeEvaluation();
	    } else {
	    	// If the node contains a move, i.e. it's not the root one,
	    	// evaluate this partial score of its move and set it as
	    	// node's evaluation.
	    	if (curNode.getNodeMove() != null) {
		    	curNode.setNodeEvaluation(
		    			curNode.getNodeEvaluation() -
		    			simpleHeuristicEvaluation(
		    					curNode.getNodeBoard(),	curNode.getNodeMove()
		    	));
	    	}
	    	
	    	double cMin = Double.MAX_VALUE;
	    		    	
	    	for (Node child : curNode.getChildren()) {  			    		
	    		createChildren(
	    				child, 
	    				CrushUtilities.boardAfterFullMove(
	    						child.getNodeBoard(), child.getNodeMove().toDirArray()),
	    				CrushUtilities.getAvailableMoves(child.getNodeBoard()),
	    				child.getNodeEvaluation()
	    		);
	    		
	    		double eval = evaluateMinimax(child, depth - 1, min, cMin, true);
	    		
	    		cMin = Math.min(cMin, eval);
	    		if (cMin < min) {
	    			cMin = min;
	    			break;
	    		}
	    	}
	    	
	    	curNode.setNodeEvaluation(cMin + curNode.getNodeEvaluation());
	    	
	    	return curNode.getNodeEvaluation();
	    }
    }
    
    private void createChildren(
    		Node n, Board curBoard, ArrayList<int[]> availableMoves, 
    		double previous) 
    {      	
    	for (int[] move : availableMoves) {
    		int[] cordMove = CrushUtilities.calculateNextMove(move);
    		PlayerMove pMove = new PlayerMove(curBoard.getTileAt(cordMove[0], cordMove[1]),
    										  curBoard.getTileAt(cordMove[2], cordMove[3]));
    		
    		Node child = new Node(n, curBoard);
    		child.setNodeMove(pMove);
    		child.setNodeEvaluation(previous);
    		
    		n.addChild(child);
    	}
    }
    
    private double simpleHeuristicEvaluation(Board board, PlayerMove move) {
    	HeuristicsEngine engine = new HeuristicsEngine(new SliderMathModel(1.0));
    	
    	engine.add(new CandiesRemovedHeuristic(move, board), SliderMathModel.VERY_HIGH);
    	
    	return engine.evaluate();
    }
    
    private double complexHeuristicEvaluation(Board board, PlayerMove move) {
    	HeuristicsEngine engine = new HeuristicsEngine(new SliderMathModel(1.5));
    	
    	engine.add(new CandiesRemovedHeuristic(move, board), SliderMathModel.VERY_HIGH);
    	engine.add(new DistanceFromTopHeuristic(move, board), SliderMathModel.VERY_LOW);
    	
    	return engine.evaluate();
    }
}
