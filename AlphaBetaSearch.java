import java.util.*;

class AlphaBetaSearch {

	private TikTokGame game;
	private int maxDepth;

	public static AlphaBetaSearch getSearchToolKit(TikTokGame game){
		return new AlphaBetaSearch(game);
	}

	private AlphaBetaSearch(TikTokGame game){
		this.game = game;
		this.maxDepth = 3; // default max depth
	}

	public void setSearchDepth(int depth){
		this.maxDepth = depth;
	}

	public Action makeDecision(ChessPiece[][] state){

		Action resultAction = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		double alpha = Double.NEGATIVE_INFINITY;
		double beta =  Double.POSITIVE_INFINITY;
		ArrayList<Action> actions = this.game.getActions(state);
		// the first move is maxValue for computer
		for (int i=0; i < actions.size(); i++){
			Action action = actions.get(i);
			double value = minValue(this.maxDepth, game.getResult(state, action,true), alpha, beta);
			if (value >= beta) { return action; }
			alpha = Math.max(alpha, value);
			System.out.print("<"+value+">");
			if (value > resultValue){
				resultAction = action;
				resultValue = value;
			}

		}
		System.out.println("\nvalue: "+resultValue);
		return resultAction;

	}

	// maxValue function
	public double maxValue(int depth, ChessPiece[][] state, double alpha, double beta){
		// terminal test
		CutoffState cutoff = this.game.isCutoff(depth,state);
		if (cutoff != null){
			// is terminal
			return this.game.evalFunction(depth,cutoff,state);
		}
		
		double value = Double.NEGATIVE_INFINITY;

		//get each action
		ArrayList<Action> actions = this.game.getActions(state);
		for (int i=0; i < actions.size(); i++){
			Action action = actions.get(i);
			//System.out.println("alpha: "+alpha+" Beta: "+beta);
			value = Math.max(value, this.minValue(depth-1, this.game.getResult(state,action,true), alpha, beta));
			
			if (value >= beta) { return value;}
			alpha = Math.max(alpha, value);

		}

		return value;
	}

	// minValue function
	public double minValue(int depth, ChessPiece[][] state, double alpha, double beta){
		// terminal test
		CutoffState cutoff = this.game.isCutoff(depth,state);
		if (cutoff != null){
			// is terminal
			return this.game.evalFunction(depth,cutoff,state);
		}
		
		
		double value = Double.POSITIVE_INFINITY;

		//get each action
		ArrayList<Action> actions = this.game.getActions(state);
		for (int i=0; i < actions.size(); i++){
			Action action = actions.get(i);
			//System.out.println("alpha: "+alpha+" Beta: "+beta);
			value = Math.min(value, this.maxValue(depth-1, this.game.getResult(state,action,false), alpha, beta));

			if (value <= alpha) { return value;  }
			beta = Math.min(beta,value);
		}

		return value;

	}
}