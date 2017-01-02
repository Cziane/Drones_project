package fr.utbm.drone.environment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import fr.utbm.drone.maths.Vector3f;

public enum Direction {
	
	NORTH(1,0,0),
	SOUTH(0,0,0),
	WEST(0,0,0),
	EAST(0,0,1);
	private final Vector3f dir;
	private static final List<Direction> VALUES =
		    Collections.unmodifiableList(Arrays.asList(values()));
	 private static final int SIZE = VALUES.size();
	 private static final Random RANDOM = new Random();
	private Direction(int x, int y, int z){
		this.dir= new Vector3f(x,y,z);
	}
	
	public Vector3f getPos(Vector3f body, Vector3f limits){
		if(this == NORTH || this==SOUTH){
			Vector3f result=this.dir.clone();
			result.setX(result.getX()*limits.getX()-body.getX());
			result.setY(limits.getY()/2-body.getY());
			result.setZ(0);
			return result;
		}
		else{
			Vector3f result=this.dir.clone();
			result.setZ(result.getZ()*limits.getZ()-body.getZ());
			result.setX(0);
			result.setY(limits.getY()/2-body.getY());
			return result;
		}
	}
	
	public static Direction randomDirection(){
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
	
	public Direction inverse(){
		switch(this){
		case NORTH:
			return SOUTH;
		case SOUTH :
			return NORTH;
		case EAST :
			return WEST;
		case WEST :
			return EAST;
		default:
			return null;
		}
	}
}
