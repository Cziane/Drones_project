package fr.utbm.drone.environment;

import java.util.UUID;

import javax.vecmath.Point3f;

import fr.utbm.drone.maths.Shape3f;
/*
 * The abstract representation of a static object 
 */

public abstract class AbstractStaticObject extends AbstractEnvObject {

	/**
	 *   
	 */
	private static final long serialVersionUID = 8431489036628649781L;

	public AbstractStaticObject(Point3f position, Shape3f<?> shape, UUID id) {
		super(position, shape, id);
	}
	@Override
	public void applyTransform() {
		
	}

}
