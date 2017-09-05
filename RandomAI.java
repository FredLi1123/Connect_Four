package hw4;

import java.util.Random;
import java.util.Vector;

public class RandomAI implements CFAI
{
	// create a vector of available columns to improve the efficiency of randomization
	// use the same random generator for each instance (or all RandomAIs will behave similarly)
	private Vector<Integer> available = new Vector<Integer>();
	private static final Random rand = new Random(System.currentTimeMillis());
	
	public RandomAI()
	{
		// add all column indices to the vector at start
		for(int i=1;i<=7;i++)
		available.addElement(i);
	}
	
	@Override
	public int nextMove(CFGame g)
	{
		int[][] state = g.getState();
		// randomly choose a vector index and get the column from it
		int index = rand.nextInt(available.size());
		int column = available.get(index);
		
		// if the column is no longer legal, remove it from the vector and choose again until we get a legal column
		while(state[0][column-1] != 0) 
		{
			available.removeElementAt(index);
			index = rand.nextInt(available.size());
			column = available.get(index);
		}
		return column;
	}
	
	@Override
	public String getName()
	{
		return "Random Player";
	}
}
