package coronaVirus;
/**
 * @author $AhmadGoly
 *
 */
import java.util.Random;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;

public class InfectedHumanAlerted {
	private ContinuousSpace < Object > space ;
	private Grid < Object > grid ;
	private int TimesGotInfected;
	private int tick = 0;
	private int age;
	private double luck;
	
	public InfectedHumanAlerted ( ContinuousSpace < Object > space , Grid < Object > grid , int TimesGotInfected , int age )
	{
	this . space = space ;
	this . grid = grid ;
	this . TimesGotInfected = TimesGotInfected;
	this . age = age;
	}
	@ScheduledMethod ( start = 1 , interval = 1)
	public void step () {
		tick++;
		
		if (tick > 30 ) {
			luck = new Random().nextDouble()*100;
		{
			if(age<17) {if(luck>0.2) makeMeCured(); else makeMeDead();}
			else if(age<29) {if(luck>2) makeMeCured(); else makeMeDead();}
			else if(age<39) {if(luck>7) makeMeCured(); else makeMeDead();}
			else if(age<49) {if(luck>12) makeMeCured(); else makeMeDead();}
			else {if(luck>19) makeMeCured(); else makeMeDead();}
			
		}}
	}
	
	public void makeMeCured() {
		
		Context < Object > context = ContextUtils . getContext ( this );
		NdPoint spacePt = space . getLocation ( this );
		GridPoint pt = grid . getLocation ( this );
		CuredHuman x = new CuredHuman ( space , grid , TimesGotInfected, age);
		context . add ( x );
		space . moveTo ( x , spacePt . getX () , spacePt . getY ());
		grid . moveTo ( x , pt . getX () , pt . getY ());
		context . remove ( this );
	}
	public void makeMeDead() {
		
		Context < Object > context = ContextUtils . getContext ( this );
		NdPoint spacePt = space . getLocation ( this );
		GridPoint pt = grid . getLocation ( this );
		DeadHuman x = new DeadHuman ( space , grid , TimesGotInfected, age);
		context . add ( x );
		space . moveTo ( x , spacePt . getX () , spacePt . getY ());
		grid . moveTo ( x , pt . getX () , pt . getY ());
		context . remove ( this );
	}
}
