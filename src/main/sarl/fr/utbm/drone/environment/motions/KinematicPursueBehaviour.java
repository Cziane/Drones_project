package fr.utbm.drone.environment.motions;

import fr.utbm.drone.maths.Point3f;
import fr.utbm.drone.maths.Vector3f;

@SuppressWarnings("all")
public class KinematicPursueBehaviour implements PursueBehaviour {
  private final KinematicSeekBehaviour seek;
  
  private final float predictionDuration;
  
  public KinematicPursueBehaviour(final KinematicSeekBehaviour seek, final float predictionDuration) {
    this.seek = seek;
    this.predictionDuration = predictionDuration;
  }
  
  @Override
  public BehaviourOutput runPursue(final Point3f position, final float linearSpeed, final float maxLinearSpeed, final Point3f targetPosition, final Vector3f targetLinearMotion) {
    Vector3f _multiply = targetLinearMotion.operator_multiply(this.predictionDuration);
    Point3f seekTarget = targetPosition.operator_plus(_multiply);
    return this.seek.runSeek(position, linearSpeed, maxLinearSpeed, seekTarget);
  }
}
