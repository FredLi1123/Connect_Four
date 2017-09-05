package hw4;

import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.BasicArrowButton;

public class GUICF extends CFGame
{
	// create the frame for the game and the grid that represents the board
	// boolean variable turn represents whether the first AI plays the current move (useful for the second constructor)
	private final JFrame gameframe;
	private final JPanel[][] grids;
	private boolean turn = true;
	private static final Random rand = new Random(System.currentTimeMillis());
	
	{
		// initialize the grids on the board: set black borders and white background
		grids = new JPanel[6][7];
		for(int i=0;i<6;i++)
		{
			for(int j=0;j<7;j++)
			{
				JPanel grid = new JPanel();
				grid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				grid.setBackground(Color.WHITE);
				grids[i][j] = grid;
			}
		}
	}
	
	public GUICF(CFAI ai)
	{	
		// set up the control panel that consists of 7 buttons
		JPanel control = new JPanel();
		control.setLayout(new GridLayout(1,7));
		
		// set up 7 buttons one by one and add them to the control panel
		// add the ActionListeners designed for the columns in Human-AI play
		for(int i=0;i<7;i++)
		{
			JButton button = new BasicArrowButton(BasicArrowButton.SOUTH);
			button.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
			button.addActionListener(new Humanplay(i+1,ai));
			control.add(button);
		}
		
		// create a panel that contains the board
		JPanel board = createboard();
		
		// set up the game frame by adding the control panel at NORTH and board at CENTER
		// the frame will close after disposition
		// display the frame
		gameframe = new JFrame();
		gameframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameframe.add(control,BorderLayout.NORTH);
		gameframe.add(board,BorderLayout.CENTER);
		gameframe.setSize(500,400);
		gameframe.setVisible(true);
		
		// if the random boolean variable is true, ai starts first
		if(rand.nextBoolean())
		{
			// play the column and change the color of the corresponding position on the board to red
			int column = ai.nextMove(this);
			play(column);
			grids[5][column-1].setBackground(Color.RED);
		}
	}
	
	public GUICF(CFAI ai1, CFAI ai2)
	{
		// if the random boolean variable is true, ai2 starts first
		if(rand.nextBoolean()) 
			turn = false;
		
		// set up the "Play" button
		// add the ActionListener designed for AI-AI play
		JButton button = new JButton("Play");
		button.addActionListener(new AIplay(ai1,ai2));
		
		// create the control panel and the board panel
		JPanel control = new JPanel();
		control.add(button, BorderLayout.CENTER);
		
		JPanel board = createboard();
		
		// set up the game frame by adding the control panel at NORTH and board at CENTER
		// the frame will close after disposition
		// display the frame
		gameframe = new JFrame();
		gameframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameframe.add(control, BorderLayout.NORTH);
		gameframe.add(board, BorderLayout.CENTER);
		gameframe.setSize(500,400);
		gameframe.setVisible(true);
	}
		
	private class Humanplay implements ActionListener
	{
		private final int column;
		private final CFAI ai;
		
		public Humanplay(int column, CFAI ai)
		{
			this.column = column;
			this.ai = ai;
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// change the color on the board before we play the column
			for(int i=5;i>=0;i--)
			{
				// white background indicates availability
				// isRedTurn() determines whether the human player is the red player
				if(grids[i][column-1].getBackground() == Color.WHITE)
				{
					if(isRedTurn()) grids[i][column-1].setBackground(Color.RED);
					else grids[i][column-1].setBackground(Color.BLACK);
					break;
				}
			}
			play(column);
			
			// if the game is over after the move, pop up a window that tells the winner and dispose the frame
			// the event will end immediately
			if(isGameOver()) 
			{
				if(winner() == 0) 
				{
					JOptionPane.showMessageDialog(gameframe,"It's a draw!"); 
					gameframe.dispose();
					return;
				}
				else if(winner() == 1) 
				{
					JOptionPane.showMessageDialog(gameframe,"Red wins!"); 
					gameframe.dispose(); 
					return;
				}
				else 
				{
					JOptionPane.showMessageDialog(gameframe,"Black wins!"); 
					gameframe.dispose();
					return;
				}
			}
			else
			{
				// AI immediately follows the human player
				// GUICF.this refers to the current outer class
				int aicolumn = ai.nextMove(GUICF.this);
				for(int i=5;i>=0;i--)
				{
					// white background indicates availability
					// isRedTurn() determines whether the AI is the red player
					if(grids[i][aicolumn-1].getBackground() == Color.WHITE)
					{
						if(isRedTurn()) grids[i][aicolumn-1].setBackground(Color.RED);
						else grids[i][aicolumn-1].setBackground(Color.BLACK);
						break;
					}
				}
				play(aicolumn);
				// if the game is over after the move, pop up a window that tells the winner and dispose the frame
				if(isGameOver())
				{
					if(winner() == 0) 
					{
						JOptionPane.showMessageDialog(gameframe,"It's a draw!"); 
						gameframe.dispose();
						return;
					}
					else if(winner() == 1) 
					{
						JOptionPane.showMessageDialog(gameframe,"Red wins!"); 
						gameframe.dispose(); 
						return;
					}
					else 
					{
						JOptionPane.showMessageDialog(gameframe,"Black wins!"); 
						gameframe.dispose();
						return;
					}
				}
			}
		}
	}
	
	private class AIplay implements ActionListener
	{
		private final CFAI ai1;
		private final CFAI ai2;
		
		public AIplay(CFAI ai1, CFAI ai2)
		{
			this.ai1 = ai1;
			this.ai2 = ai2;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int aicolumn;
			// if turn is true, the first AI gets the column it will play
			if(turn) 
			{
				aicolumn = ai1.nextMove(GUICF.this);
				turn = false;
			}
			// otherwise, the second AI gets the column it will play
			else 
			{
				aicolumn = ai2.nextMove(GUICF.this);
				turn = true;
			}
			// change the color on the board before AI plays the column
			for(int i=5;i>=0;i--)
			{
				if(grids[i][aicolumn-1].getBackground() == Color.WHITE)
				{
					if(isRedTurn()) grids[i][aicolumn-1].setBackground(Color.RED);
					else grids[i][aicolumn-1].setBackground(Color.BLACK);
					break;
				}
			}
			play(aicolumn);
			// if the game is over after the move, pop up a window that tells the winner and dispose the frame 
			if(isGameOver())
			{
				if(winner() == 1) JOptionPane.showMessageDialog(gameframe,"Red wins!");
				else if(winner() == -1) JOptionPane.showMessageDialog(gameframe,"Black wins!");
				else JOptionPane.showMessageDialog(gameframe,"It's a draw!");
				gameframe.dispose();
			}
		}
	}
	
	private JPanel createboard()
	{
		// create a panel that represents the board, set the layout, and add the grids in order
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(6,7));
		for(int i=0;i<6;i++)
		{
			for(int j=0;j<7;j++)
				board.add(grids[i][j]);
		}
		return board;
	}

}
