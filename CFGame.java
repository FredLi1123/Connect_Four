package hw4;

public class CFGame 
{
  //state[i][j]= 0 means the i,j slot is empty
  //state[i][j]= 1 means the i,j slot has red
  //state[i][j]=-1 means the i,j slot has black
  private final int[][] state;
  private boolean isRedTurn;
  private boolean isDraw; // whether the game is draw
  
  {
    state = new int[6][7];
    for (int i=0;i<6;i++)
      for (int j=0;j<7;j++)
        state[i][j] = 0;
    isRedTurn = true; //red goes first
    isDraw = false;
  }
    
  public int[][] getState() {
    int[][] ret_arr = new int[6][7];
    for (int i=0; i<6; i++)
      for (int j=0; j<7; j++)
        ret_arr[i][j] = state[i][j];
    return ret_arr;
  }
  
  public boolean isRedTurn() {
    return isRedTurn;
  }
  
  public boolean play(int column) {
	if(1 <= column && column <= 7) // valid argument
	{
		for(int i=5;i>=0;i--) 
		{
			if(state[i][column-1] == 0)
			{
				if(isRedTurn) 
				{
					state[i][column-1] = 1; 
					isRedTurn = false;  // next move is black
				}
				else 
				{
					state[i][column-1] = -1;
					isRedTurn = true; // next move is red
				}
				return true;
			}
		}
	}
	return false;
  }
  
  public boolean isGameOver() 
  {
	for(int i=0;i<6;i++)
	{
		for(int j=0;j<7;j++)
		{
			if(state[i][j] != 0)
			{
				// check "up" direction for vertical 4-connect
				if(i >= 3)  
					if(state[i][j] == state[i-1][j] && 
						state[i][j] == state[i-2][j] && 
						state[i][j] == state[i-3][j]) return true;
				if(j >= 3)
				{
					// check "left" direction for horizontal 4-connect
					if(state[i][j] == state[i][j-1] && 
						state[i][j] == state[i][j-2] && 
						state[i][j] == state[i][j-3]) return true;
					// check "down" direction for 4-connect on minor diagonal
					if(i <= 2)
						if(state[i][j] == state[i+1][j-1] &&
							state[i][j] == state[i+2][j-2] &&
							state[i][j] == state[i+3][j-3]) return true;
					// check "up" direction for 4-connect on major diagonal
					if(i >= 3)
						if(state[i][j] == state[i-1][j-1] &&
						    state[i][j] == state[i-2][j-2] &&
					    state[i][j] == state[i-3][j-3]) return true;
				}
			}
		}
	}
	// check if the first row has empty space; 
	// if it does not, it is a draw because the function has not returned yet
	for(int j=0;j<7;j++)
		if(state[0][j] == 0) return false;
	
	isDraw = true;
    return true;
  }
  
  public int winner() {
	if(isDraw) return 0;
	// if the game is not a draw, then the one who moves next loses
	else if(isRedTurn) return -1; 
	else return 1;
  }
}
