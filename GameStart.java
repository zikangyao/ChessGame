import java.util.*;
import java.awt.EventQueue;
public class GameStart{
	public static void main(String args[]){
		// the entry of the whole game
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// create a Game frame
				new ChessFrame();
			}
		});
		
	}
}
