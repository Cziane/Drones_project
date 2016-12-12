package fr.utbm.drone.environment.object;

import java.io.Serializable;
import java.util.UUID;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import fr.utbm.drone.environment.AbstractStaticObject;
import fr.utbm.drone.environment.ObjectType;
import fr.utbm.drone.maths.Shape3f;
import fr.utbm.drone.physics.physicBuidling;

public class Target extends AbstractStaticObject {

	public Target(Point3f position, Shape3f<?> shape, UUID id) {
		super(position, shape, id);
		this.body= null;
	}
	@Override
	public Serializable getType() {
		// TODO Auto-generated method stub
		return  ObjectType.TARGET;
	}

}
