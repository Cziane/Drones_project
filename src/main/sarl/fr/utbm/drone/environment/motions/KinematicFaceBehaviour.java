package fr.utbm.drone.environment.motions;


import fr.utbm.drone.maths.Point3f;
import fr.utbm.drone.maths.Vector3f;

@SuppressWarnings("all")
public class KinematicFaceBehaviour implements FaceBehaviour {
  private final KinematicAlignBehaviour align;
  
  public KinematicFaceBehaviour(final KinematicAlignBehaviour align) {
    this.align = align;
  }
  
  @Override
  public BehaviourOutput runFace(final Point3f position, final Vector3f orientation, final float angularSpeed, final float maxAngularSpeed, final Point3f target) {
    Vector3f alignTarget = target.operator_minus(position);
    return this.align.runAlign(orientation, angularSpeed, maxAngularSpeed, alignTarget);
  }
}
