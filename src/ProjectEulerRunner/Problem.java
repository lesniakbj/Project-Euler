package ProjectEulerRunner;

public interface Problem 
{
	public String run();
	
	public String getProblemDescription();
	public boolean isCorrect();
	public int getID();
}
