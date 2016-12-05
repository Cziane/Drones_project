package fr.utbm.drone.environment;

import java.io.Serializable;
import java.util.UUID;

import javax.vecmath.Point3f;

import fr.utbm.drone.maths.Shape3f;

public class Target extends AbstractStaticObject {

	public Target(Point3f position, Shape3f<?> shape, UUID id) {
		super(position, shape, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Serializable getType() {
		// TODO Auto-generated method stub
		return ObjectType.TARGET;
	}

}
