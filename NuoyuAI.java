package hw4;

public class NuoyuAI implements CFAI{
	private int[][] prev_state; // record the state of the game when the AI made the last move
	
	public NuoyuAI()
	{
		prev_state = new int[6][7];
		for(int i=0;i<6;i++)
			for(int j=0;j<7;j++)
		        prev_state[i][j] = 0;
	}
	
	@Override
	public int nextMove(CFGame g)
	{
		int[][] state = g.getState();
		int move = -1;
		int x = 0;
		int y = 0;
		boolean blank = true;
		
		// if the AI is the red player, use 1 to represent the move; otherwise, use -1
		if(g.isRedTurn()) move = 1;
		
		// check whether the board is blank by checking if all elements the last row are 0
		for(int j=0;j<7;j++)
		{
			if(state[5][j] != 0)
			{
				blank = false;
				break;
			}
		}
		
		// if the board is blank, play the 4th column
		if(blank)
		{
			prev_state[5][3] = move; 
			return 4;
		}
		
		// otherwise, compare the current state with the previous state to detect last move
		for(int i=0;i<6;i++)
		{
			for(int j=0;j<7;j++)
			{
				if(state[i][j] != prev_state[i][j])
				{
					x = i;
					y = j;
					break;
				}
			}
		}
		prev_state[x][y] = state[x][y];
		
		// if 3 consecutive 1s or -1s appear in an available column, play the column and record the move in prev_state
		// and return the column
		if(x > 0 && x < 4 && state[x][y] == state[x+1][y] && state[x+1][y] == state[x+2][y]) 
		{
			prev_state[x-1][y] = move; 
			return y+1;
		}
		// else if the 4th column is available, play it and return 4
		else if(state[0][3] == 0)
		{
			// determine the firstly available position and record the move in prev_state
			for(int j=5;j>=0;j--)
			{
				if(state[j][3] == 0) 
				{
					prev_state[j][3] = move;
					return 4;
				}
			}
		}
		else
		{
			// else if the column the opponent plays is still available, play it
			if(state[0][y] == 0)
			{
				prev_state[x-1][y] = move;
				return y+1;
			}
			// else choose a column as close as possible to the 4th column
			else
			{
				// d is the distance from a column to the 4th column
				for(int d=1;d<4;d++)
				{
					// try (4-d)th column
					if(state[0][3-d] == 0)
					{
						// determine the firstly available position and record the move in prev_state
						// and return the column
						for(int j=5;j>=0;j--)
						{
							if(state[j][3-d] == 0) 
							{
								prev_state[j][3-d] = move;
								return 4-d;
							}
						}
					}
					// try (4+d)th column
					if(state[0][3+d] == 0)
					{
						for(int j=5;j>=0;j--)
						{
							if(state[j][3+d] == 0) 
							{
								prev_state[j][3+d] = move;
								return 4+d;
							}
						}
					}
				}
			}
		}
		return 4;
	}
	
	@Override
	public String getName()
	{ 
		return "Nuoyu's AI";
	}
}
