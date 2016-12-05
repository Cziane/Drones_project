package fr.utbm.drone.events;

import fr.utbm.drone.environment.motions.BehaviourOutput;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.4")
@SuppressWarnings("all")
public class InfluenceInvent extends Event {
  public BehaviourOutput behaviour;
  
  public InfluenceInvent(final BehaviourOutput bo) {
    this.behaviour = bo;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    InfluenceInvent other = (InfluenceInvent) obj;
    if (this.behaviour == null) {
      if (other.behaviour != null)
        return false;
    } else if (!this.behaviour.equals(other.behaviour))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.behaviour== null) ? 0 : this.behaviour.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the InfluenceInvent event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("behaviour  = ").append(this.behaviour);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 3832118946L;
}
