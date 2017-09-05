package hw4;

public class Test 
{
	public static void main(String[] args) 
	{
		CFAI ai1 = new NuoyuAI();
		CFAI ai2 = new RandomAI();
		int n = 100000;
		int winCount = 0;
		for(int i=0;i<n;i++) 
		{
			ConsoleCF game = new ConsoleCF(ai1,ai2);
			game.playOut();
			if (game.getWinner() == ai1.getName())
				winCount++;
		}
		
		System.out.println(ai1.getName()+" wins ");
		System.out.println(((double) winCount)/((double) n)*100+"%");
		System.out.println(" of the time ");
	}
}
