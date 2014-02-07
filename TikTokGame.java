import java.util.*;

class TikTokGame{
	private int winConNum = 4;
	private boolean isMultiEvalFunction = true;

	// setting flag whether using mulit evaluation function
	public void whetherUseMulitEvalFunction(boolean flag){
		this.isMultiEvalFunction = flag;
	}

	// judge whether is cutoff 
	public CutoffState isCutoff(int depth, ChessPiece[][] state){
		// cutoff state
		// when depth equals to zero, cut off
		if (depth == 0){
			return CutoffState.CUTOFF;
		}

		// check whether state has vertical, horizontal, diagonal connected line (winCon)
		int noneCounter = 0;
		for (int i=0; i < state.length; i++){
			for (int j=0; j< state[0].length; j++){

				ChessPiece chessType = state[i][j];

				if (chessType == ChessPiece.NONE){
					noneCounter++;
					continue;
				}

				// horizontal	
				int horizontalCounter = 0;
				for (int k=j; k-j < this.winConNum; k++){
					try{
						if (state[i][k] != chessType){
							break;
						}else{
							horizontalCounter++;
						}
					}catch (ArrayIndexOutOfBoundsException e){
						break;
					}
				}
				if (horizontalCounter == this.winConNum){
					if (chessType == ChessPiece.PROGRAM){
						return CutoffState.PROGRAMWIN;
					}else{
						return CutoffState.HUMANWIN;
					}
					
				}

				// vertical
				int verticalCounter = 0;
				for (int k=i; k-i < this.winConNum; k++){
					try{
						if (state[k][j] != chessType){
							break;
						}else{
							verticalCounter++;
						}
					}catch (ArrayIndexOutOfBoundsException e){
						break;
					}
				}
				if (verticalCounter == this.winConNum){
					if (chessType == ChessPiece.PROGRAM){
						return CutoffState.PROGRAMWIN;
					}else{
						return CutoffState.HUMANWIN;
					}
				}

				
				//diagonal 135 degree
				int diagonalCounterPos = 0;
				int mPos = i;
				int nPos = j;
				for (; nPos-j < this.winConNum; nPos++,mPos++){
					try{
						if (state[mPos][nPos] != chessType){
							break;
						}else{
							diagonalCounterPos++;
						}
						

					}catch (ArrayIndexOutOfBoundsException e){
						break;
					}
				}
				if (diagonalCounterPos == this.winConNum){
					if (chessType == ChessPiece.PROGRAM){
						return CutoffState.PROGRAMWIN;
					}else{
						return CutoffState.HUMANWIN;
					}
				}
			
		
				// diagonal 45 degree
				int diagonalCounterNeg = 0;
				int mNeg = i;
				int nNeg = j;
				for (; j-nNeg < this.winConNum; nNeg--){
					try{
						if (state[mNeg][nNeg] != chessType){
							break;
						}else{
							diagonalCounterNeg++;
						}
						mNeg++;

					}catch (ArrayIndexOutOfBoundsException e){
						break;
					}
				}
				if (diagonalCounterNeg == this.winConNum){
					if (chessType == ChessPiece.PROGRAM){
						return CutoffState.PROGRAMWIN;
					}else{
						return CutoffState.HUMANWIN;
					}
				}

			}
		}

		// draw state
		if (noneCounter == 0){
			return CutoffState.DRAW;
		}

		

		return null;
	}

	//evaluation function same as text book
	public double evalFunction2(ChessPiece[][] state){
		
		int x3=0,x2=0,x1=0;
		int o3=0,o2=0,o1=0;

		// check each row 
		for (int i=0; i< state.length; i++){
			ChessPiece rowType = null;
			int rowCounter = 0;
			for (int j=0; j < state[0].length; j++){
				if (state[i][j] == ChessPiece.NONE){
					continue;
				}else{
					if (rowType == null){
						rowType = state[i][j];
						rowCounter++;
					}else{
						if (rowType != state[i][j]){
							rowCounter = 0;
							break;
						}else{
							rowCounter++;
						}
					}
					
				}
			}

			// summary in each row
			if (rowType == ChessPiece.PROGRAM){
				if(rowCounter == 3){
					x3++;
				}else if (rowCounter == 2){
					x2++;
				}else if (rowCounter == 1){
					x1++;
				}

			}else if (rowType == ChessPiece.HUMAN){
			    if(rowCounter == 3){
					o3++;
				}else if (rowCounter == 2){
					o2++;
				}else if (rowCounter == 1){
					o1++;
				}
			}

		}

		// check each column
		for (int j=0; j< state[0].length; j++){
			ChessPiece colType = null;
			int colCounter = 0;
			for (int i=0; i < state.length; i++){
				if (state[i][j] == ChessPiece.NONE){
					continue;
				}else{
					if (colType == null){
						colType = state[i][j];
						colCounter++;
					}else{
						if (colType != state[i][j]){
							colCounter = 0;
							break;
						}else{
							colCounter++;
						}
					}
				}
				
			}

			// summary in each col
			if (colType == ChessPiece.PROGRAM){
				if(colCounter == 3){
					x3++;
				}else if (colCounter == 2){
					x2++;
				}else if (colCounter == 1){
					x1++;
				}

			}else if (colType == ChessPiece.HUMAN){
			    if(colCounter == 3){
					o3++;
				}else if (colCounter == 2){
					o2++;
				}else if (colCounter == 1){
					o1++;
				}
			}
			
			
		}

		// check each diagonal (only four line for this problem)
		// 135 degree
		for (int k=0; k< 2; k++){
			ChessPiece diagType = null;
			int diagCounter = 0;
			for (int i=0,j=k; i< state.length; i++,j++){
				if (state[i][j] == ChessPiece.NONE){
					continue;
				}else{
					if (diagType == null){
						diagType = state[i][j];
						diagCounter++;
					}else{
						if (diagType != state[i][j]){
							diagCounter = 0;
							break;
						}else{
							diagCounter++;
						}
					}
					
				}
			}
			// summary in each diag 135
			if (diagType == ChessPiece.PROGRAM){
				if(diagCounter == 3){
					x3++;
				}else if (diagCounter == 2){
					x2++;
				}else if (diagCounter == 1){
					x1++;
				}

			}else if (diagType == ChessPiece.HUMAN){
			    if(diagCounter == 3){
					o3++;
				}else if (diagCounter == 2){
					o2++;
				}else if (diagCounter == 1){
					o1++;
				}
			}	
		}

		// 45 degree
		for (int k = 3; k< 5; k++){
			ChessPiece diagType = null;
			int diagCounter = 0;
			for (int i=0,j=k; i< state.length; i++,j--){
				if (state[i][j] == ChessPiece.NONE){
					continue;
				}else{
					if (diagType == null){
						diagType = state[i][j];
						diagCounter++;
					}else{
						if (diagType != state[i][j]){
							diagCounter = 0;
							break;
						}else{
							diagCounter++;
						}
					}
					
				}
			}

			// summary in each diag 45
			if (diagType == ChessPiece.PROGRAM){
				if(diagCounter == 3){
					x3++;
				}else if (diagCounter == 2){
					x2++;
				}else if (diagCounter == 1){
					x1++;
				}

			}else if (diagType == ChessPiece.HUMAN){
			    if(diagCounter == 3){
					o3++;
				}else if (diagCounter == 2){
					o2++;
				}else if (diagCounter == 1){
					o1++;
				}
			}	
		}
		
		return 3*x3+2*x2+x1-(3*o3+2*o2+o1);
			
	}

	// eavluation function 
	public double evalFunction (int depth,CutoffState cutoff, ChessPiece[][] state){
		// win state
		if (cutoff == CutoffState.PROGRAMWIN){
			return depth*100000.0;

		// lose state
		}else if (cutoff == CutoffState.HUMANWIN){
			return -100000.0;

		// draw state
		}else if (cutoff == CutoffState.DRAW){
			return 0.0;

		}else {

			int progThreePointTwoEnd = 0;
			int progThreePointOneEnd = 0;
			int progTwoPointTwoEnd = 0;
			int progTwoPointOneEnd = 0;

			int humThreePointTwoEnd = 0;
			int humThreePointOneEnd = 0;
			int humTwoPointTwoEnd = 0;
			int humTwoPointOneEnd = 0;
			

			// check each row
			for (int i=0; i< state.length; i++){
				int noneCounter = 0;
				int proCounter = 0;
				int humCounter = 0;

				for (int j=0; j<state[0].length; j++){
					if (state[i][j] == ChessPiece.PROGRAM){
						proCounter++;
					}else if (state[i][j] == ChessPiece.HUMAN){
						humCounter++;
					}else{
						noneCounter++;
					}
				}

				if (humCounter == 3 || proCounter == 3){
					// three points two open ends
					if (state[i][0] == ChessPiece.NONE && state[i][4] == ChessPiece.NONE){
						if (state[i][1] == ChessPiece.PROGRAM){
							progThreePointTwoEnd++;
						}else{
							humThreePointTwoEnd++;
						}
					// three points one open end
					}else if ((state[i][0] == state[i][1] && state[i][1] == state[i][2] && state[i][3] == ChessPiece.NONE) ||
							  (state[i][2] == state[i][3] && state[i][3] == state[i][4] && state[i][1] == ChessPiece.NONE) ||
						      ((state[i][1] == state[i][2] && state[i][2] == state[i][3]) && (state[i][0] == ChessPiece.NONE || state[i][4] == ChessPiece.NONE))
						      ){

						if (state[i][2] == ChessPiece.PROGRAM){
							progThreePointOneEnd++;
						}else{
							humThreePointOneEnd++;
						}
					}
				}

				if (humCounter >= 2 || proCounter >= 2){
					// two points two open ends
					if ((state[i][1] == state[i][2] && (state[i][0] == state[i][3] && state[i][0] == ChessPiece.NONE)) ||
					    (state[i][2] == state[i][3] && (state[i][1] == state[i][4] && state[i][1] == ChessPiece.NONE))
					    ){

						if (state[i][2] == ChessPiece.PROGRAM){
							progTwoPointTwoEnd++;
						}else{
							humTwoPointTwoEnd++;
						}
					// two points one open end
					}else if ((state[i][0] == state[i][1] && state[i][2] == ChessPiece.NONE && (state[i][3] == ChessPiece.NONE || state[i][3] == state[i][0])) ||
						      (state[i][3] == state[i][4] && state[i][2] == ChessPiece.NONE && (state[i][1] == ChessPiece.NONE || state[i][1] == state[i][3]))
						     ){

						if (state[i][1] == ChessPiece.PROGRAM || state[i][3] == ChessPiece.PROGRAM){
							progTwoPointOneEnd++;
						}else{
							humTwoPointOneEnd++;
						}

					}
				}


			}

			// check each colum
			for (int j=0; j< state[0].length; j++){
				int noneCounter = 0;
				int proCounter = 0;
				int humCounter = 0;

				for (int i=0; i< state.length; i++){
					if (state[i][j] == ChessPiece.PROGRAM){
						proCounter++;
					}else if (state[i][j] == ChessPiece.HUMAN){
						humCounter++;
					}else{
						noneCounter++;
					}
				}

				if (humCounter == 3 || proCounter == 3){
					// three points one open end
					if (state[0][j] == ChessPiece.NONE || state[3][j] == ChessPiece.NONE){
						if (state[1][j] == ChessPiece.PROGRAM){
							progThreePointOneEnd++;
						}else{
							humThreePointOneEnd++;
						}
					}
				}

				if (humCounter >= 2 || proCounter >= 2) {
					// two points two open ends
					if (state[0][j] == state[3][j] && state[0][j] == ChessPiece.NONE){
						if (state[1][j] == ChessPiece.PROGRAM){
							progTwoPointTwoEnd++;
						}else{
							humTwoPointTwoEnd++;
						}
					// two points one open end
					}else if (state[1][j] == ChessPiece.NONE){
						if (state[2][j] == state[3][j] && (state[0][j] == ChessPiece.NONE || state[0][j] == state[2][j])){
							if (state[2][j] == ChessPiece.PROGRAM){
								progTwoPointOneEnd++;
							}else{
								humTwoPointOneEnd++;
							}
						}
					// two points one open end
					}else if (state[2][j] == ChessPiece.NONE){
						if (state[0][j] == state[1][j] && (state[3][j] == ChessPiece.NONE || state[3][j] == state[1][j])){
							if (state[1][j] == ChessPiece.PROGRAM){
								progTwoPointOneEnd++;
							}else{
								humTwoPointOneEnd++;
							}
						}
					}

				}
			}

			// check each diagonal
			// 135 degree
			for (int k=0; k < 2; k++){
				int noneCounter = 0;
				int proCounter = 0;
				int humCounter = 0;

				for (int i=0, j=k; i< state.length; i++,j++){
					if (state[i][j] == ChessPiece.PROGRAM){
						proCounter++;
					}else if (state[i][j] == ChessPiece.HUMAN){
						humCounter++;
					}else{
						noneCounter++;
					}
				}

				if (humCounter == 3 || proCounter == 3){
					// three points one open end
					if (state[0][k] == ChessPiece.NONE || state[3][k+3] == ChessPiece.NONE){
						if (state[1][k+1] == ChessPiece.PROGRAM){
							progThreePointOneEnd++;
						}else{
							humThreePointOneEnd++;
						}
					}
				}

				if (humCounter >= 2 || proCounter >=2 ){
					// two points two open ends
					if (state[0][k] == ChessPiece.NONE && state[3][k+3] == ChessPiece.NONE){
						if (state[1][k+1] == ChessPiece.PROGRAM){
							progTwoPointTwoEnd++;
						}else{
							humTwoPointTwoEnd++;
						}
					}else if (state[1][k+1] == ChessPiece.NONE){
						if (state[2][k+2] == state[3][k+3] && (state[0][k] == ChessPiece.NONE || state[0][k] == state[2][k+2])){
							if (state[2][k+2] == ChessPiece.PROGRAM){
								progTwoPointOneEnd++;
							}else{
								humTwoPointOneEnd++;
							}
						}
					// two points one open end
					}else if (state[2][k+2] == ChessPiece.NONE){
						if (state[0][k] == state[1][k+1] && (state[3][k+3] == ChessPiece.NONE || state[3][k+3] == state[1][k+1])){
							if (state[1][k+1] == ChessPiece.PROGRAM){
								progTwoPointOneEnd++;
							}else{
								humTwoPointOneEnd++;
							}
						}
					}

				}

			}

			// 45 degree
			for (int k=3; k < 5; k++){
				int noneCounter = 0;
				int proCounter = 0;
				int humCounter = 0;

				for (int i=0, j=k; i< state.length; i++,j--){
					if (state[i][j] == ChessPiece.PROGRAM){
						proCounter++;
					}else if (state[i][j] == ChessPiece.HUMAN){
						humCounter++;
					}else{
						noneCounter++;
					}
				}

				if (humCounter == 3 || proCounter == 3){
					// three points one open end
					if (state[0][k] == ChessPiece.NONE || state[3][k-3] == ChessPiece.NONE){
						if (state[1][k-1] == ChessPiece.PROGRAM){
							progThreePointOneEnd++;
						}else{
							humThreePointOneEnd++;
						}
					}
				}

				if (humCounter >= 2 || proCounter >=2 ){
					// two points two open ends
					if (state[0][k] == ChessPiece.NONE && state[3][k-3] == ChessPiece.NONE){
						if (state[1][k-1] == ChessPiece.PROGRAM){
							progTwoPointTwoEnd++;
						}else{
							humTwoPointTwoEnd++;
						}
					}else if (state[1][k-1] == ChessPiece.NONE){
						if (state[2][k-2] == state[3][k-3] && (state[0][k] == ChessPiece.NONE || state[0][k] == state[2][k-2])){
							if (state[2][k-2] == ChessPiece.PROGRAM){
								progTwoPointOneEnd++;
							}else{
								humTwoPointOneEnd++;
							}
						}
					// two points one open end
					}else if (state[2][k-2] == ChessPiece.NONE){
						if (state[0][k] == state[1][k-1] && (state[3][k-3] == ChessPiece.NONE || state[3][k-3] == state[1][k-1])){
							if (state[1][k-1] == ChessPiece.PROGRAM){
								progTwoPointOneEnd++;
							}else{
								humTwoPointOneEnd++;
							}
						}
					}

				}
			}

			double evalValue = 100*progThreePointTwoEnd+30*progThreePointOneEnd+10*progTwoPointTwoEnd+5*progTwoPointOneEnd
							   - (100*humThreePointTwoEnd+30*humThreePointOneEnd+10*humTwoPointTwoEnd+5*humTwoPointOneEnd);

			if (isMultiEvalFunction)
				evalValue += evalFunction2(state);
			
			return evalValue;
		}	

	}

	// check whether the state is terminal 
	public ChessPiece isTerminal(ChessPiece[][] state){
		int noneCounter = 0;
		for (int i=0; i < state.length; i++){
			for (int j=0; j< state[0].length; j++){

				ChessPiece chessType = state[i][j];

				if (chessType == ChessPiece.NONE){
					noneCounter++;
					continue;
				}

				// check whether state has vertical, horizontal, diagonal connected line (winCon)
				// horizontal	
				int horizontalCounter = 0;
				for (int k=j; k-j < this.winConNum; k++){
					try{
						if (state[i][k] != chessType){
							break;
						}else{
							horizontalCounter++;
						}
					}catch (ArrayIndexOutOfBoundsException e){
						break;
					}
				}
				if (horizontalCounter == this.winConNum){
					return chessType;
				}

				// vertical
				int verticalCounter = 0;
				for (int k=i; k-i < this.winConNum; k++){
					try{
						if (state[k][j] != chessType){
							break;
						}else{
							verticalCounter++;
						}
					}catch (ArrayIndexOutOfBoundsException e){
						break;
					}
				}
				if (verticalCounter == this.winConNum){
					return chessType;
				}

				
				//diagonal 45 degree
				int diagonalCounterPos = 0;
				int mPos = i;
				int nPos = j;
				for (; nPos-j < this.winConNum; nPos++,mPos++){
					try{
						if (state[mPos][nPos] != chessType){
							break;
						}else{
							diagonalCounterPos++;
						}
						

					}catch (ArrayIndexOutOfBoundsException e){
						break;
					}
				}
				if (diagonalCounterPos == this.winConNum){
					return chessType;
				}
			
		
				// diagonal 135 degree
				int diagonalCounterNeg = 0;
				int mNeg = i;
				int nNeg = j;
				for (; j-nNeg < this.winConNum; nNeg--){
					try{
						if (state[mNeg][nNeg] != chessType){
							break;
						}else{
							diagonalCounterNeg++;
						}
						mNeg++;

					}catch (ArrayIndexOutOfBoundsException e){
						break;
					}
				}
				if (diagonalCounterNeg == this.winConNum){
					return chessType;
				}

			}
		}

		if (noneCounter == 0){
			return ChessPiece.NONE;
		}

		return null;
	
	}

	// get possible actions of speicfic state
	public ArrayList<Action> getActions(ChessPiece[][] state){
		ArrayList<Action> actions = new ArrayList<Action>();
		for (int i=0; i < state.length; i++){
			for (int j=0; j< state[0].length; j++){
				if (state[i][j] == ChessPiece.NONE){
					actions.add(new Action(i,j));
				}
			}
		}

		return actions;	

	}

	// get next state based on current state and action
	public ChessPiece[][] getResult(ChessPiece[][] state, Action action, boolean isComputer){
		int x = action.getX();
		int y = action.getY();

		ChessPiece[][] copy = new ChessPiece[state.length][];
		for (int i=0; i < state.length; i++){
			copy[i] = Arrays.copyOf(state[i], state[i].length);
		}

		// judge whether this step is for computer or not
		if (isComputer){
			copy[x][y] = ChessPiece.PROGRAM;
		}else{
			copy[x][y] = ChessPiece.HUMAN;
		}

		return copy;

	}

}

// class of action
class Action {

	private int x;
	private int y;

	public Action(int x,int y){
		this.x = x;
		this.y = y;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}
}

// four cut off state
enum CutoffState{
	HUMANWIN,PROGRAMWIN,DRAW,CUTOFF
}

// two chess piece and none state
enum ChessPiece{
	HUMAN,PROGRAM,NONE
}

