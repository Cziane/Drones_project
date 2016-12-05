package fr.utbm.info.vi51.motionbehavior;

import fr.utbm.drone.environment.DynamicType;
import fr.utbm.drone.environment.motions.BehaviourOutput;
import fr.utbm.drone.environment.motions.WanderBehaviour;
import fr.utbm.drone.maths.Orientation3f;
import fr.utbm.drone.maths.Point3f;
import fr.utbm.drone.maths.Vector3f;
import io.sarl.lang.annotation.SarlSpecification;
import java.util.Random;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SarlSpecification("0.4")
@SuppressWarnings("all")
public class KinematicWanderBehaviour implements WanderBehaviour {
  private final Random random = new Random();
  
  @Override
  public BehaviourOutput runWander(final Point3f position, final Vector3f orientation, final float linearSpeed, final float maxLinearSpeed, final float angularSpeed, final float maxAngularSpeed) {
    final Vector3f v = orientation.toColinearVector(maxLinearSpeed);
    double _nextGaussian = this.random.nextGaussian();
    final double rotationL = (_nextGaussian * maxAngularSpeed);
    double _nextGaussian_1 = this.random.nextGaussian();
    final double rotationE = (_nextGaussian_1 * maxAngularSpeed);
    BehaviourOutput _behaviourOutput = new BehaviourOutput(DynamicType.KINEMATIC);
    final Procedure1<BehaviourOutput> _function = (BehaviourOutput it) -> {
      it.setLinear(v);
      Orientation3f _orientation3f = new Orientation3f(((float) rotationL), ((float) rotationE));
      it.setAngular(_orientation3f);
    };
    return ObjectExtensions.<BehaviourOutput>operator_doubleArrow(_behaviourOutput, _function);
  }
}
