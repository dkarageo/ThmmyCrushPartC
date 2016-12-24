package gr.auth.ee.dsproject.crush.player;

import java.util.ArrayList;

import gr.auth.ee.dsproject.crush.defplayers.AbstractPlayer;
import gr.auth.ee.dsproject.crush.board.CrushUtilities;
import gr.auth.ee.dsproject.crush.board.Board;
import gr.auth.ee.dsproject.crush.heuristics.*;
import gr.auth.ee.dsproject.crush.player.move.PlayerMove;
import gr.auth.ee.dsproject.crush.board.Tile;


public class HeuristicPlayer implements AbstractPlayer
{
    // TODO Fill the class code.

    int score;
    int id;
    String name;

    public HeuristicPlayer (Integer pid)
    {
        id = pid;
        score = 0;
    }

    @Override
    public String getName ()
    {
        return "evaluation";
    }

    @Override
    public int getId ()
    {
        return id;
    }

    @Override
    public void setScore (int score)
    {
        this.score = score;
    }

    @Override
    public int getScore ()
    {
        return score;
    }

    @Override
    public void setId (int id)
    {
        this.id = id;
    }

    @Override
    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public int[] getNextMove(ArrayList<int[]> availableMoves, Board board)
    {
        int[] move = availableMoves.get(findBestMoveIndex(availableMoves, board));

        return calculateNextMove(move);
    }

    int[] calculateNextMove (int[] move)
    {
	    int[] returnedMove = new int[4];
	
	    if (move[2] == CrushUtilities.UP) {
	    // System.out.println("UP");
	        returnedMove[0] = move[0];
	        returnedMove[1] = move[1];
	        returnedMove[2] = move[0];
	        returnedMove[3] = move[1] + 1;
	    }
	    
	    if (move[2] == CrushUtilities.DOWN) {
	    // System.out.println("DOWN");
	        returnedMove[0] = move[0];
	        returnedMove[1] = move[1];
	        returnedMove[2] = move[0];
	        returnedMove[3] = move[1] - 1;
	    }
	    
	    if (move[2] == CrushUtilities.LEFT) {
	    // System.out.println("LEFT");
	        returnedMove[0] = move[0];
	        returnedMove[1] = move[1];
	        returnedMove[2] = move[0] - 1;
	        returnedMove[3] = move[1];
	    }
	    
	    if (move[2] == CrushUtilities.RIGHT) {
	    // System.out.println("RIGHT");
	       returnedMove[0] = move[0];
	       returnedMove[1] = move[1];
	       returnedMove[2] = move[0] + 1;
	       returnedMove[3] = move[1];
	    }
	
	    return returnedMove;
    }

    int findBestMoveIndex (ArrayList<int[]> availableMoves, Board board)
    {	
	    double max = 0.0;
	    int max_i = 0;
	  
	    for (int i = 0; i < availableMoves.size(); i++) {
		   	double moveScore = moveEvaluation(availableMoves.get(i), board);
		  	
		  	//System.out.println("Move score: " + moveScore);
	  	
		  	if (moveScore > max) {
		  		max = moveScore;
		  	    max_i = i;
		  	}
        }
  
        //System.out.println("Selected move score: " + max);
	  
	    return max_i;	  
    }
  
    /**
     * Evaluates the given move based on heuristics run on a HeuristicsEngine.
     * 
     * The math model in use is the SliderMathModel with a scale factor of 2.0
     * 
     * Heuristics used for evaluation:
     * -CandiesRemovedHeuristic on VERY_HIGH
     * -DistanceFromTopHeuristic on VERY_LOW
     *
     * @param move A move to be evaluted in the form of {x, y, direction}
     * 			 array.
     * @param board The board on which the move will be evaluated.
     * @return A value between 0.0 and less than 100.0 indicating how good a move is.
     * 		 Greater values mean better moves.	
     */
    double moveEvaluation (int[] move, Board board)
    {
	    HeuristicsEngine engine = new HeuristicsEngine(new SliderMathModel(2.0));
	  	  
	    int[] destTile = getMoveDestination(move);
	  
	    Tile tile1 = board.getTile(move[0], move[1]);
	    Tile tile2 = board.getTile(destTile[0], destTile[1]);
	    PlayerMove pMove = new PlayerMove(tile1, tile2);
	  
	    engine.add(new CandiesRemovedHeuristic(pMove, board), SliderMathModel.VERY_HIGH);
	    engine.add(new DistanceFromTopHeuristic(pMove, board), SliderMathModel.VERY_LOW);
	  
	    return engine.evaluate();
    }

  
//==== Private Static Methods ====
  
    /** 
     * getMoveDestination() calculates the cords of the block next to the
     * given one, on the given direction.
     * 
     * directedMove argument is in the form of { x, y, CrushUtilities.[DIRECTION] }
     * 
     * No checking is done for negative origin or destination cords.
     * 
     * @param directedMove A move in the form of {x, y, direction} int array.
     * @return The block's cord where move's direction points in the form of
     * 		{x, y} int array.
     */ 
    private static int[] getMoveDestination (int[] directedMove) 
    {
	    // Assume that destination cords is the same as origin cords.
        int[] dest = { directedMove[0], directedMove[1] };
  
        int direction = directedMove[2];
  
        // Correct dest cords according to the move direction. 
	    if (direction == CrushUtilities.LEFT) { 
		    dest[0] -= 1;
	    } 
	    else if (direction == CrushUtilities.RIGHT) {
		    dest[0] += 1;
	    } 
	    else if (direction == CrushUtilities.UP) {
		    dest[1] += 1;
	    } 
	    else if (direction == CrushUtilities.DOWN) {
		    dest[1] -= 1;
	    }
	  
	    return dest;	  
    }
}
