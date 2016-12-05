package fr.utbm.drone.gui;

import fr.utbm.drone.environment.ObjectType;
import fr.utbm.drone.gui.graph.items.BridgeItem;
import fr.utbm.drone.gui.graph.items.BuildingItem;
import fr.utbm.drone.gui.graph.items.DroneItem;
import fr.utbm.drone.gui.graph.items.GuiItem;

public class RenderFactory {
	
	public static GuiItem createGuiItem(ObjectType type,float scale, float x, float y, float z) throws Exception{
		GuiItem obj=null;
		switch(type){
			case DRONE : 
				obj = new DroneItem(scale, x, y, z);
				break;
			case BUILDING :
				obj=new BuildingItem(scale, x, y, z);
				break;
			case BRIDGE :
				obj=new BridgeItem(scale, x, y, z);
				break;
			default :
				throw new Exception();
		}
		return obj;
	}
}
