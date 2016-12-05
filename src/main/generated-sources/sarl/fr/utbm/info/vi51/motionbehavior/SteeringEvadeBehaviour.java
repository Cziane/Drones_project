package fr.utbm.info.vi51.motionbehavior;

import fr.utbm.drone.environment.motions.BehaviourOutput;
import fr.utbm.drone.environment.motions.EvadeBehaviour;
import fr.utbm.drone.maths.Point3f;
import fr.utbm.drone.maths.Vector3f;
import fr.utbm.info.vi51.motionbehavior.SteeringFleeBehaviour;
import io.sarl.lang.annotation.SarlSpecification;

@SarlSpecification("0.4")
@SuppressWarnings("all")
public class SteeringEvadeBehaviour implements EvadeBehaviour {
  private final SteeringFleeBehaviour flee;
  
  private final float predictionDuration;
  
  public SteeringEvadeBehaviour(final SteeringFleeBehaviour flee, final float predictionDuration) {
    this.flee = flee;
    this.predictionDuration = predictionDuration;
  }
  
  @Override
  public BehaviourOutput runEvade(final Point3f position, final float linearSpeed, final float maxLinearAcceleration, final Point3f targetPosition, final Vector3f targetLinearMotion) {
    Vector3f _multiply = targetLinearMotion.operator_multiply(this.predictionDuration);
    Point3f fleeTarget = targetPosition.operator_plus(_multiply);
    return this.flee.runFlee(position, linearSpeed, maxLinearAcceleration, fleeTarget);
  }
}
