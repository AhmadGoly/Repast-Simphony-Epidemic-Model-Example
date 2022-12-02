package coronaVirus;
/**
 * @author $AhmadGoly
 *
 */
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class DeadHuman {
	private ContinuousSpace < Object > space ;
	private Grid < Object > grid ;
	private int TimesGotInfected;
	private int age;
	
	public DeadHuman ( ContinuousSpace < Object > space , Grid < Object > grid , int TimesGotInfected , int age )
	{
	this . space = space ;
	this . grid = grid ;
	this . TimesGotInfected = TimesGotInfected;
	this . age = age;
	}
	public int getTimesGotInfected() {return TimesGotInfected;}
	public int getAge() {return age;}
}
