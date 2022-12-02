package coronaVirus;
/**
 * @author $AhmadGoly
 *
 */
import java.util.Random;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;

public class Builder implements ContextBuilder<Object> {
	public Context build(Context<Object> context) {
		context . setId ("CoronaVirus");
		
		
		//Params:
		Parameters params = RunEnvironment . getInstance (). getParameters ();
		int neighborDistanceToBeInfected = params.getInteger("neighbor_distance");
		int HumanCount = params.getInteger("human_count");
		int InfectedHumanCount = params.getInteger("infected_human_count");
		int spaceSizeX = params.getInteger("space_x");
		int spaceSizeY = params.getInteger("space_y");
		
		
		
		ContinuousSpaceFactory spaceFactory =
		ContinuousSpaceFactoryFinder . createContinuousSpaceFactory ( null );
		ContinuousSpace < Object > space =
		spaceFactory . createContinuousSpace ("space", context ,
		new RandomCartesianAdder < Object >() ,
		new repast.simphony.space.continuous.WrapAroundBorders () ,
		spaceSizeX,spaceSizeY);
				
		GridFactory gridFactory = GridFactoryFinder . createGridFactory ( null );
		Grid < Object > grid = gridFactory.createGrid ("grid", context ,
		new GridBuilderParameters < Object >( new WrapAroundBorders () ,
		new SimpleGridAdder < Object >() ,
		true,spaceSizeX,spaceSizeY));
		
		for ( int i = 0; i < HumanCount ; i ++) {
			context . add ( new Human ( space , grid , new Random().nextInt(50)+10 )); //Age is randomly generated. 10<Age<60
			}
		
		for ( int i = 0; i < InfectedHumanCount ; i ++) {
			context . add ( new InfectedHuman ( space , grid , neighborDistanceToBeInfected , 1 , new Random().nextInt(50)+10));
			}
		
		for ( Object obj : context ) {
			NdPoint pt = space . getLocation ( obj );
			grid . moveTo ( obj , ( int ) pt . getX () , ( int ) pt . getY ());
			}
		
	return context;
	}
}
