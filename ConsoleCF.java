package hw4;

import java.util.Random;
import java.util.Scanner;

public class ConsoleCF extends CFGame
{
	// set up the game by creating two private players
	// boolean variable first represents whether player 1 starts first
	// use the same random generator for each instance (or all Consoles will behave similarly)
	private final CFAI player_1; 
	private final CFAI player_2;
	private final boolean first; 
	private final static Random rand = new Random(System.currentTimeMillis());
	
	public ConsoleCF(CFAI ai)
	{		
		player_1 = new HumanPlayer();
		player_2 = ai;
		first = rand.nextBoolean();
	}
	
	public ConsoleCF(CFAI ai1, CFAI ai2)
	{		
		player_1 = ai1;
		player_2 = ai2;
		first = rand.nextBoolean();
	}
	
	public void playOut()
	{		
		if(first)
		{
			// check whether the game is over after each move
			while(!isGameOver())
			{
				// combine function nextMove and play to complete a move
				play(player_1.nextMove(this));
				if(!isGameOver()) play(player_2.nextMove(this));
				else break;
			}
		}
		else
		{
			while(!isGameOver())
			{
				play(player_2.nextMove(this));
				if(!isGameOver()) play(player_1.nextMove(this));
				else break;
			}
		}
	}
	
	public String getWinner()
	{
		if(winner() == 0) return "draw";
		// player 1 can win the game as the red player or the black player
		else if((winner() == 1 && first) || (winner() == -1 && !first)) return player_1.getName();
		else return player_2.getName();
	}
	
	private class HumanPlayer implements CFAI
	{
		public int nextMove(CFGame g)
		{
			Scanner s = new Scanner(System.in);
			int[][] state = g.getState();
			int column;
			
			// print the current board
			for(int i=0;i<6;i++)
			{
				for(int j=0;j<7;j++)
					System.out.print(state[i][j]+" ");
				System.out.println("");
			}
			System.out.println("");
			System.out.print("Enter the next move: ");
			
			// read the next move from input 
			column = s.nextInt();
			if(isGameOver()) s.close();
			return column;
		}
		
		public String getName()
		{
			return "Human Player";
		}
	}
}
