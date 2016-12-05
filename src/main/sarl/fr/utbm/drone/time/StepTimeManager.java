/* 
 * $Id$
 * 
 * Copyright (c) 2011-15 Stephane GALLAND <stephane.galland@utbm.fr>.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.utbm.drone.time;


/** Step-based Time manager.
 *
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class StepTimeManager extends AbstractTimeManager {

	private float delay = 10;
	private float t;
	private final float step;
	
	/**
	 * @param stepDuration the duration of one simulation step (in ms)
	 */
	public StepTimeManager(float stepDuration) {
		this.step = stepDuration / 1000f;
	}

	@Override
	public synchronized float getCurrentTime() {
		return this.t;
	}

	@Override
	public synchronized float getLastStepDuration() {
		return this.step;
	}

	@Override
	public synchronized void increment() {
		this.t += this.step;
	}

	@Override
	public synchronized float getSimulationDelay() {
		return this.delay;
	}

	@Override
	public synchronized void setSimulationDelay(float delay) {
		this.delay = Math.max(0f, delay);
	}
	
	@Override
	public String toString() {
		return "t=" + this.t + "; step=" + this.step + "; delay = " + this.delay;
	}

}