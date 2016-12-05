package fr.utbm.drone.environment.motions;

import fr.utbm.drone.maths.Point3f;
import fr.utbm.drone.maths.Vector3f;

@SuppressWarnings("all")
public class SteeringFaceBehaviour implements FaceBehaviour {
  private final SteeringAlignBehaviour align;
  
  public SteeringFaceBehaviour(final SteeringAlignBehaviour align) {
    this.align = align;
  }
  
  @Override
  public BehaviourOutput runFace(final Point3f position, final Vector3f orientation, final float angularSpeed, final float maxAngularAcceleration, final Point3f target) {
    Vector3f alignTarget = target.operator_minus(position);
    return this.align.runAlign(orientation, angularSpeed, maxAngularAcceleration, alignTarget);
  }
}
