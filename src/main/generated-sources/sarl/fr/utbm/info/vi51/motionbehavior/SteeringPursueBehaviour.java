package fr.utbm.info.vi51.motionbehavior;

import fr.utbm.drone.environment.motions.BehaviourOutput;
import fr.utbm.drone.environment.motions.PursueBehaviour;
import fr.utbm.drone.maths.Point3f;
import fr.utbm.drone.maths.Vector3f;
import fr.utbm.info.vi51.motionbehavior.SteeringSeekBehaviour;
import io.sarl.lang.annotation.SarlSpecification;

@SarlSpecification("0.4")
@SuppressWarnings("all")
public class SteeringPursueBehaviour implements PursueBehaviour {
  private final SteeringSeekBehaviour seek;
  
  private final float predictionDuration;
  
  public SteeringPursueBehaviour(final SteeringSeekBehaviour seek, final float predictionDuration) {
    this.seek = seek;
    this.predictionDuration = predictionDuration;
  }
  
  @Override
  public BehaviourOutput runPursue(final Point3f position, final float linearSpeed, final float maxLinearAcceleration, final Point3f targetPosition, final Vector3f targetLinearMotion) {
    Vector3f _multiply = targetLinearMotion.operator_multiply(this.predictionDuration);
    Point3f seekTarget = targetPosition.operator_plus(_multiply);
    return this.seek.runSeek(position, linearSpeed, maxLinearAcceleration, seekTarget);
  }
}
