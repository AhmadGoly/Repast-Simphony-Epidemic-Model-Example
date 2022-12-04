package coronaVirus;

import java.util.Random;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

public class EnvController {
	private int periodTick;
	private int tickCounter = 0;
	private ContinuousSpace < Object > space ;
	private Grid < Object > grid ;
	private int neighborDistanceToBeInfected;
	private int totalTicks = 0;
	public EnvController ( int periodTick, ContinuousSpace < Object > space , Grid < Object > grid , int neighborDistanceToBeInfected)
	{
		this.periodTick=periodTick;
		this.grid=grid;
		this.space=space;
		this.neighborDistanceToBeInfected=neighborDistanceToBeInfected;
	}
	
	@ScheduledMethod ( start = 1 , interval = 1)
	public void step () {
		tickCounter++;
		if(tickCounter>=periodTick)
		{
			totalTicks +=tickCounter; 
			tickCounter=0;
			Context < Object > context = ContextUtils . getContext ( this );
			InfectedHuman x = new InfectedHuman ( space , grid , neighborDistanceToBeInfected , 1 , new Random().nextInt(50)+10);
			context . add ( x);
			NdPoint pt = space . getLocation ( x );
			grid . moveTo ( x , ( int ) pt . getX () , ( int ) pt . getY ());
			System.out.println("Added an infected Human on tick "+totalTicks);
		}
		
		
	}
}
