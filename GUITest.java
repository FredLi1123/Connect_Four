package hw4;

public class GUITest 
{
	public static void main(String[] args)
	{
		CFAI ai1 = new NuoyuAI();
		CFAI ai2 = new RandomAI();
		
		GUICF gui = new GUICF(ai1,ai2);
	}
}
