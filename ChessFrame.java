import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class ChessFrame extends JFrame{
	private final int DEFAULT_WIDTH = 460;
	private final int DEFAULT_HEIGHT = 405;
	private ChessPanel chessPanel;
	private JRadioButton humRadio;
	private JRadioButton compRadio;
	private JLabel difficulity;
	private TikTokGame game;
	private AlphaBetaSearch search;

	public ChessFrame(){
		// initiate search tool
		this.game = new TikTokGame();
		this.search = AlphaBetaSearch.getSearchToolKit(game);

		// frame setting
		this.setTitle("Tik Tok Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setResizable(false);
		this.setVisible(true);

		//menue bar
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Start");
		JMenu newGame = new JMenu("New Game");
		JMenuItem exitGame = new JMenuItem("Exit");
		JMenuItem easy = new JMenuItem("Easy");
		JMenuItem medium = new JMenuItem("Medium");
		JMenuItem hard = new JMenuItem("Hard");

		// set action listener
		exitGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				System.exit(0);
			}
		});

		// set action listener for easy
		easy.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				search.setSearchDepth(1);
				game.whetherUseMulitEvalFunction(false);
				difficulity.setText("Difficulity: Easy");
				ChessPiece[][] currentState = chessPanel.clearChessPanel();
				if (compRadio.isSelected()){
					Action action = search.makeDecision(currentState);
					chessPanel.computerMove(action,currentState);
				}
			}
		});

		// set action listener for medium
		medium.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				search.setSearchDepth(3);
				game.whetherUseMulitEvalFunction(true);
				difficulity.setText("Difficulity: Medium");
				ChessPiece[][] currentState = chessPanel.clearChessPanel();
				if (compRadio.isSelected()){
					Action action = search.makeDecision(currentState);
					chessPanel.computerMove(action,currentState);
					
				}
			}
		});

		// set action listener for hard
		hard.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				search.setSearchDepth(6);
				game.whetherUseMulitEvalFunction(true);
				difficulity.setText("Difficulity: Hard");
				ChessPiece[][] currentState = chessPanel.clearChessPanel();
				if (compRadio.isSelected()){
					Action action = search.makeDecision(currentState);
					chessPanel.computerMove(action,currentState);
				}
			}
		});

		// add menu component
		newGame.add(easy);
		newGame.add(medium);
		newGame.add(hard);
		gameMenu.add(newGame);
		gameMenu.add(exitGame);
		menuBar.add(gameMenu);
		
		

		// setting control panel
		this.compRadio = new JRadioButton("Computer first",false);
		this.humRadio = new JRadioButton("You first",true);
		// set action listener
		this.compRadio.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
			
				ChessPiece[][] currentState = chessPanel.clearChessPanel();
				Action action = search.makeDecision(currentState);
				chessPanel.computerMove(action,currentState);
				
			}
		});

		this.humRadio.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){			
				chessPanel.clearChessPanel();
			}
			
		});

		JPanel controlPanel = new JPanel();
		ButtonGroup group = new ButtonGroup();
		group.add(humRadio);
		group.add(compRadio);
		controlPanel.add(humRadio);
		controlPanel.add(compRadio);
	
		// chess panel 
		this.chessPanel = new ChessPanel();

		// settting south panel
		JPanel southPanel = new JPanel();
		this.difficulity = new JLabel("Difficulity: Medium");
		southPanel.add(this.difficulity);

		// adding components to frame
		this.setJMenuBar(menuBar);
		this.add(chessPanel,BorderLayout.CENTER);
		this.add(controlPanel,BorderLayout.NORTH);
		this.add(southPanel,BorderLayout.SOUTH);
		this.add(new JPanel(),BorderLayout.WEST);
		this.add(new JPanel(),BorderLayout.EAST);

	}


	// class of chess panel
	private class ChessPanel extends JPanel{

		private final int ROW = 4;
		private final int COLUMN = 5;
		private boolean isGameStop = false;
		private CellPanel[][] cellPanels;

		private class CellPanel extends JPanel{
			private final Color BORDER_COLOR = Color.BLACK;
			private ChessPiece chessPiece;
			private boolean isClicked;
			
			public CellPanel(){
				this.setBorder(BorderFactory.createRaisedBevelBorder());
				this.setSelectedType(ChessPiece.NONE);
				this.isClicked = false;
			}

			public void setSelectedType(ChessPiece chessPiece){
				this.chessPiece = chessPiece;

				if (chessPiece == ChessPiece.HUMAN){
					this.setBackground(Color.BLUE);
				}else if(chessPiece == ChessPiece.PROGRAM){
					this.setBackground(Color.RED);
				}else{
					this.setBackground(Color.LIGHT_GRAY);
				}
			}

			public void setClicked(boolean flag){
				this.isClicked = flag;
			}

			public boolean isClicked(){
				return this.isClicked;
			}

			public ChessPiece getSelectedType(){
				return this.chessPiece;
			}
		
		}

		public ChessPanel(){

			this.cellPanels = new CellPanel[ROW][COLUMN];
			this.setLayout(new GridLayout(ROW,COLUMN));

			for (int i=0; i< ROW; i++){
				for(int j=0; j< COLUMN; j++){
					cellPanels[i][j] = new CellPanel();
					final CellPanel cell = cellPanels[i][j];
					cell.addMouseListener(new MouseListener(){
						@Override
						public void mouseClicked(MouseEvent e) {
							if (!cell.isClicked()){
								// human move
								cell.setSelectedType(ChessPiece.HUMAN);
								cell.setClicked(true);
								ChessPiece[][] currentState = new ChessPiece[ROW][COLUMN];
								for (int i=0; i< cellPanels.length; i++){
									for (int j=0; j<cellPanels[0].length; j++){
										currentState[i][j] = cellPanels[i][j].getSelectedType();
									}
								}

								// whether is terminal
								ChessPiece checkType1 = game.isTerminal(currentState);
								terminalPopOut(checkType1);
								
								if (!isGameStop){

									// computer move
									Action action = search.makeDecision(currentState);
									ChessPiece[][] nextState = computerMove(action,currentState);

									// whether is terminal
									ChessPiece checkType2 = game.isTerminal(nextState);
									terminalPopOut(checkType2);
								}
								isGameStop = false;

							}

						}

						@Override
						public void mouseEntered(MouseEvent e) {
							cell.setBorder(BorderFactory.createLoweredBevelBorder());
						}

						@Override
						public void mouseExited(MouseEvent e) {
							cell.setBorder(BorderFactory.createRaisedBevelBorder());
						}

						@Override
						public void mousePressed(MouseEvent e) {

						}

						@Override
						public void mouseReleased(MouseEvent e) {

						}

					});
					this.add(cell);
				}
			}

		}
		// show the pop out window when it is terminal
		public void terminalPopOut(ChessPiece checkType){
			if (checkType != null){
				this.isGameStop = true; // set stop flag
				int nextAction;
				if (checkType == ChessPiece.PROGRAM){
					nextAction = JOptionPane.showConfirmDialog(ChessPanel.this,"Oops! Play again?","Tik Tok Game", JOptionPane.YES_NO_OPTION);
				}else if(checkType == ChessPiece.HUMAN){
					nextAction = JOptionPane.showConfirmDialog(ChessPanel.this,"You Won! Play again?","Tik Tok Game",JOptionPane.YES_NO_OPTION);
				}else{
					nextAction = JOptionPane.showConfirmDialog(ChessPanel.this,"Game Draw! Play again?","Tik Tok Game",JOptionPane.YES_NO_OPTION);
				}

				if (nextAction == 0){
					ChessPiece[][] currentState = this.clearChessPanel();
					
					if (compRadio.isSelected()){
						Action action = search.makeDecision(currentState);
						chessPanel.computerMove(action,currentState);
					}
				}else{
					System.exit(0);
				}

				
				
			}
		}

		// clear the chess board
		public ChessPiece[][] clearChessPanel(){
			ChessPiece[][] clearState = new ChessPiece[ROW][COLUMN];
			for (int i=0; i< clearState.length; i++){
				for (int j=0; j<clearState[0].length; j++){
					clearState[i][j] = ChessPiece.NONE;
					this.cellPanels[i][j].setSelectedType(ChessPiece.NONE);
					this.cellPanels[i][j].setClicked(false);
				}
			}

			return clearState;
		}

		// move one step for computer
		public ChessPiece[][] computerMove(Action action, ChessPiece[][] currentState){
			this.cellPanels[action.getX()][action.getY()].setClicked(true);
			ChessPiece[][] nextState = game.getResult(currentState,action,true);

			for (int i=0; i< cellPanels.length; i++){
				for (int j=0; j<cellPanels[0].length; j++){
					cellPanels[i][j].setSelectedType(nextState[i][j]);
				}
			}

			return nextState;
		}


	}
}



