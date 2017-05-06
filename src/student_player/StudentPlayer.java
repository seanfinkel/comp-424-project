package student_player;

import java.util.ArrayList; 


import bohnenspiel.BohnenspielBoardState;
import bohnenspiel.BohnenspielMove;
import bohnenspiel.BohnenspielPlayer;
import bohnenspiel.BohnenspielMove.MoveType;
import student_player.mytools.MyTools;

/** A Hus player submitted by a student. */
public class StudentPlayer extends BohnenspielPlayer {

    /** You must modify this constructor to return your student number.
     * This is important, because this is what the code that runs the
     * competition uses to associate you with your agent.
     * The constructor should do nothing else. */
    public StudentPlayer() { super("260429466"); }

    /** This is the primary method that you need to implement.
     * 	The ``board_state`` object contains the current state of the game,
     * 	which your agent can use to make decisions. See the class
	 *	bohnenspiel.RandomBohnenspielPlayer
     * 	for another example agent. */
    
    
    public BohnenspielMove chooseMove(BohnenspielBoardState pMyTurnBoardState)
    {    
   
    	//operator chooseMove()
    	//	for each legal operator o:
    	//		apply the operator o and obtain the new game states s.
    	//		Value[o] = MinimaxValue(s)
    	//	return the operator with the highest Value[o].
    	
    	int testMoveScore; // general value of a move
        int bestMoveScore = -1; // chosen move's score up to date
        
        ArrayList<BohnenspielMove> legalMoves = pMyTurnBoardState.getLegalMoves();// Get legal moves for current board state.
        BohnenspielMove bestMove = legalMoves.get(0); // set best move to the first move for now

        // Use code stored in ``mytools`` package.
        MyTools.getSomething();

        for (BohnenspielMove testMove : legalMoves){ // for all moves in legal moves
        	BohnenspielBoardState boardStateClone = (BohnenspielBoardState) pMyTurnBoardState.clone(); // clone board state
        	
            boardStateClone.move(testMove); // We can see the effects of a move on our clone
            testMoveScore = miniMaxValue(boardStateClone, 7); // score of move = value returned by minimax
            if (testMoveScore > bestMoveScore){ // if the returned value is the highest so far
            	bestMoveScore = testMoveScore; // set the new benchmark
            	bestMove = testMove; // set test move to new optimal move
            }
        }
        return bestMove;
    }
    
    public int miniMaxValue(BohnenspielBoardState pBoardState, int pLevel){
    	int stateValue;
    	if (pBoardState.gameOver()||pLevel==0) return evaluateState(pBoardState); // if game is over we at end of the line
    	ArrayList<BohnenspielBoardState> successorStates = getSuccessorStates(pBoardState);
    	int currentPlayer = pBoardState.getTurnPlayer();
    	if (currentPlayer == player_id){
    		int maxValueOfStates = Integer.MIN_VALUE;
        	for (BohnenspielBoardState testState : successorStates){
            	//System.out.println("got to test state in max player of minimax");
        		stateValue = miniMaxValue(testState, pLevel-1);// set value(s') = miniMaxValue(s')
        		if (stateValue > maxValueOfStates){maxValueOfStates = stateValue;}
        	}
        	return maxValueOfStates;
    	}
    	else {
        	int minValueOfStates = Integer.MAX_VALUE;
        	for (BohnenspielBoardState testState : successorStates){
        		//System.out.println("got to test state in min player of minimax");
        		stateValue = miniMaxValue(testState, pLevel-1);// set value(s') = miniMaxValue(s')
        		if (stateValue < minValueOfStates){minValueOfStates = stateValue;}
        	}
        	return minValueOfStates;
    	}
    }
    
    public ArrayList<BohnenspielBoardState> getSuccessorStates(BohnenspielBoardState pTestState){ //gets successor states
    	ArrayList<BohnenspielBoardState> successors = new ArrayList<>(); 
    	ArrayList<BohnenspielMove> legalMoves = pTestState.getLegalMoves();// gets legal moves on the state
    	for (BohnenspielMove currentMove : legalMoves) { // for each of these moves
    		//System.out.println("got to successor states");
    		BohnenspielBoardState clone = (BohnenspielBoardState) pTestState.clone();
        	clone.move(currentMove);
        	successors.add(clone);
    	}

    	return successors;
    }
    
    public int evaluateState(BohnenspielBoardState pBoardState){
    	
    	//System.out.println("got to evaluation");
    	
    	int utilityScore = 0;
    	int myHeuristic=0;
    	int oppHeuristic=0;
    	
        // Get the contents of the pits so we can use it to make decisions.
        int[][] pits = pBoardState.getPits();

        // Use ``player_id`` and ``opponent_id`` to get my pits and opponent pits.
        int[] myPits = pits[player_id];
        int[] oppPits = pits[opponent_id];
        
        for (int currentPit : myPits){
        	if (currentPit != 0){myHeuristic++;}
        }
                
        for (int currentPit : oppPits){
        	if (currentPit != 0){
        		oppHeuristic++;
        	}
        }
        
        utilityScore = 20*(myHeuristic-oppHeuristic) + 100*(pBoardState.getScore(player_id) - pBoardState.getScore(opponent_id));

    	return utilityScore;
    }
}