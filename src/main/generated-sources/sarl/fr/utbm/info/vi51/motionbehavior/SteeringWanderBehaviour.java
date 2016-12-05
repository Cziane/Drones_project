package fr.utbm.info.vi51.motionbehavior;

import fr.utbm.drone.environment.DynamicType;
import fr.utbm.drone.environment.motions.BehaviourOutput;
import fr.utbm.drone.environment.motions.WanderBehaviour;
import fr.utbm.drone.maths.Orientation3f;
import fr.utbm.drone.maths.Point3f;
import fr.utbm.drone.maths.Vector3f;
import fr.utbm.info.vi51.motionbehavior.SteeringFaceBehaviour;
import fr.utbm.info.vi51.motionbehavior.SteeringSeekBehaviour;
import io.sarl.lang.annotation.SarlSpecification;
import java.util.Random;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SarlSpecification("0.4")
@SuppressWarnings("all")
public class SteeringWanderBehaviour implements WanderBehaviour {
  private final float circleDistance;
  
  private final float circleRadius;
  
  private final float maxRotation;
  
  private final SteeringFaceBehaviour faceBehaviour;
  
  private final SteeringSeekBehaviour seekBehaviour;
  
  private final Random random = new Random();
  
  private float rotation = 0f;
  
  public SteeringWanderBehaviour(final float circleDistance, final float circleRadius, final float maxRotation, final SteeringFaceBehaviour faceBehaviour, final SteeringSeekBehaviour seekBehaviour) {
    this.circleDistance = circleDistance;
    this.circleRadius = circleRadius;
    this.maxRotation = maxRotation;
    this.faceBehaviour = faceBehaviour;
    this.seekBehaviour = seekBehaviour;
  }
  
  @Override
  public BehaviourOutput runWander(final Point3f position, final Vector3f orientation, final float linearSpeed, final float maxLinearAcceleration, final float angularSpeed, final float maxAngularAcceleration) {
    Vector3f circleCenter = orientation.toColinearVector(this.circleDistance);
    Vector3f displacement = circleCenter.toColinearVector(this.circleRadius);
    displacement.turn(this.rotation);
    float _rotation = this.rotation;
    float _nextFloat = this.random.nextFloat();
    float _multiply = (_nextFloat * 2f);
    float _minus = (_multiply - 1f);
    float _multiply_1 = (_minus * this.maxRotation);
    this.rotation = (_rotation + _multiply_1);
    Point3f circleCenterPosition = position.operator_plus(circleCenter);
    Point3f faceTarget = circleCenterPosition.operator_plus(displacement);
    final BehaviourOutput faceOutput = this.faceBehaviour.runFace(position, orientation, angularSpeed, maxAngularAcceleration, faceTarget);
    final BehaviourOutput seekOutput = this.seekBehaviour.runSeek(position, linearSpeed, maxLinearAcceleration, circleCenterPosition);
    BehaviourOutput _behaviourOutput = new BehaviourOutput(DynamicType.KINEMATIC);
    final Procedure1<BehaviourOutput> _function = (BehaviourOutput it) -> {
      Vector3f _linear = seekOutput.getLinear();
      it.setLinear(_linear);
      Orientation3f _angular = faceOutput.getAngular();
      it.setAngular(_angular);
    };
    return ObjectExtensions.<BehaviourOutput>operator_doubleArrow(_behaviourOutput, _function);
  }
}
