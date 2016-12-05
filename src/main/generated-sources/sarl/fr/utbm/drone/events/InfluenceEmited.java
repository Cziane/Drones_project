package fr.utbm.drone.events;

import fr.utbm.drone.environment.motions.BehaviourOutput;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.4")
@SuppressWarnings("all")
public class InfluenceEmited extends Event {
  public BehaviourOutput output;
  
  public UUID emiter;
  
  public String msg;
  
  public InfluenceEmited(final BehaviourOutput b) {
    this.output = b;
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
    InfluenceEmited other = (InfluenceEmited) obj;
    if (this.output == null) {
      if (other.output != null)
        return false;
    } else if (!this.output.equals(other.output))
      return false;
    if (this.emiter == null) {
      if (other.emiter != null)
        return false;
    } else if (!this.emiter.equals(other.emiter))
      return false;
    if (this.msg == null) {
      if (other.msg != null)
        return false;
    } else if (!this.msg.equals(other.msg))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.output== null) ? 0 : this.output.hashCode());
    result = prime * result + ((this.emiter== null) ? 0 : this.emiter.hashCode());
    result = prime * result + ((this.msg== null) ? 0 : this.msg.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the InfluenceEmited event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("output  = ").append(this.output);
    result.append("emiter  = ").append(this.emiter);
    result.append("msg  = ").append(this.msg);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 3194876391L;
}
