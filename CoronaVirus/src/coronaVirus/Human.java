package coronaVirus;
/**
 * @author $AhmadGoly
 *
 */
import java.util.List;
import java.util.Random;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.SimUtilities;

public class Human {

	private ContinuousSpace < Object > space ;
	private Grid < Object > grid ;
	private int TimesGotInfected = 0;
	private int age;
	public Human ( ContinuousSpace < Object > space , Grid < Object > grid , int age)
	{
	this . space = space ;
	this . grid = grid ;
	this . age = age;
	}
	
	@ScheduledMethod ( start = 1 , interval = 1)
	public void step () {
		GridPoint pt = grid . getLocation ( this );
		GridCellNgh < Human > nghCreator = new GridCellNgh < Human >( grid , pt ,
				Human . class , 1 , 1);
		List < GridCell < Human > > gridCells = nghCreator . getNeighborhood ( true );
		SimUtilities . shuffle ( gridCells , RandomHelper . getUniform ());
		GridPoint RandomWalk = null ;
		RandomWalk=gridCells.get( new Random().nextInt(9) ).getPoint();
		moveTowards ( RandomWalk );
	}
	
	public void moveTowards ( GridPoint pt ) {
		if (! pt . equals ( grid . getLocation ( this ))) {
			NdPoint myPoint = space . getLocation ( this );
			NdPoint otherPoint = new NdPoint ( pt . getX () , pt . getY ());
			double angle = SpatialMath . calcAngleFor2DMovement ( space ,
					myPoint , otherPoint );
			space . moveByVector ( this , 1 , angle , 0);
			myPoint = space . getLocation ( this );
			grid . moveTo ( this , ( int ) myPoint . getX () , ( int ) myPoint . getY ());
		}
	}

	public int getTimesGotInfected() {return TimesGotInfected;}
	public int getAge() {return age;}
}
