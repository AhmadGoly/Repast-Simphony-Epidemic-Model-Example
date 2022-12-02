package coronaVirus;
/**
 * @author $AhmadGoly
 *
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;

public class InfectedHuman {

	private ContinuousSpace < Object > space ;
	private Grid < Object > grid ;
	private int neighborDistanceToBeInfected;
	private int TimesGotInfected;
	private int tick = 0;
	private int age;
	public InfectedHuman ( ContinuousSpace < Object > space , Grid < Object > grid , int neighborDistanceToBeInfected , int TimesGotInfected , int age )
	{
	this . space = space ;
	this . grid = grid ;
	this . neighborDistanceToBeInfected = neighborDistanceToBeInfected;
	this . TimesGotInfected = TimesGotInfected+1;
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
		makePeopleSick(pt);
		tick++;
		if (tick > 40 )makeMeAlerted();
	}
	
	public void makeMeAlerted() {
		Context < Object > context = ContextUtils . getContext ( this );
		NdPoint spacePt = space . getLocation ( this );
		GridPoint pt = grid . getLocation ( this );
		InfectedHumanAlerted x = new InfectedHumanAlerted ( space , grid , TimesGotInfected , age);
		context . add ( x );
		space . moveTo ( x , spacePt . getX () , spacePt . getY ());
		grid . moveTo ( x , pt . getX () , pt . getY ());
		context . remove ( this );
	}
	
	public void moveTowards ( GridPoint pt ) {
		// only move if we are not already in this grid location
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
	
	public void makePeopleSick(GridPoint pt) {
		GridCellNgh < Human > nghCreator = new GridCellNgh < Human >( grid , pt ,
				Human . class , neighborDistanceToBeInfected , neighborDistanceToBeInfected);
		List < GridCell < Human > > gridCells = nghCreator . getNeighborhood ( true );
		
		List < Object > humans = new ArrayList < Object >();
		
		for ( GridCell < Human > cell : gridCells ) {
			for ( Object obj : grid.getObjectsAt ( cell.getPoint().getX() , cell.getPoint().getY())) {
				if ( obj instanceof Human || obj instanceof CuredHuman ) {
					humans . add ( obj );
					}
			}
		}
			for(int i = 0 ; i< humans . size () ; i++ )
			{
				int timeGotInfected;
				int age;
				Object obj = humans . get ( i );
				if ( obj instanceof Human ) {timeGotInfected = ((Human) obj).getTimesGotInfected(); age=((Human) obj).getAge();}
				else {timeGotInfected = (((CuredHuman) obj).getTimesGotInfected());age=((CuredHuman) obj).getAge();}
			
			if ( new Random().nextInt(100)/ (timeGotInfected+1) > 20 ) {
				
				NdPoint spacePt = space . getLocation ( obj );
				Context < Object > context = ContextUtils . getContext ( obj );
				context . remove ( obj );
				InfectedHuman x = new InfectedHuman ( space , grid , neighborDistanceToBeInfected , timeGotInfected ,age );
				context . add ( x );
				space . moveTo ( x , spacePt . getX () , spacePt . getY ());
				grid . moveTo ( x , pt . getX () , pt . getY ());
			}
			}
		}
		
	}

