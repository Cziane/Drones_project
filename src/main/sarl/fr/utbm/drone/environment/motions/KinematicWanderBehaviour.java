package fr.utbm.drone.environment.motions;

import java.util.Random;

import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import fr.utbm.drone.environment.DynamicType;
import fr.utbm.drone.maths.Orientation3f;
import fr.utbm.drone.maths.Point3f;
import fr.utbm.drone.maths.Vector3f;

@SuppressWarnings("all")
public class KinematicWanderBehaviour implements WanderBehaviour {
  private final Random random = new Random();
  
  @Override
  public BehaviourOutput runWander(final Point3f position, final Vector3f orientation, final float linearSpeed, final float maxLinearSpeed, final float angularSpeed, final float maxAngularSpeed) {
    final Vector3f v = orientation.toColinearVector(maxLinearSpeed);
    double _nextGaussian = this.random.nextGaussian();
    final double rotation = (_nextGaussian * maxAngularSpeed);
    BehaviourOutput _behaviourOutput = new BehaviourOutput(DynamicType.KINEMATIC);
    final Procedure1<BehaviourOutput> _function = (BehaviourOutput it) -> {
      it.setLinear(v);
      it.setAngular(new Orientation3f((float) rotation, (float) rotation));
    };
    return ObjectExtensions.<BehaviourOutput>operator_doubleArrow(_behaviourOutput, _function);
  }
}
