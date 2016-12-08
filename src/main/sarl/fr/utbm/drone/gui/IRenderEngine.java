package fr.utbm.drone.gui;

import java.util.List;

import org.joml.Vector3f;

import fr.utbm.drone.gui.graph.items.GuiItem;

public interface IRenderEngine {
	
    void init(Window window) throws Exception;
    
    void input(Window window, MouseInput mouseInput);

    void update(float interval, MouseInput mouseInput);
    
    void render(Window window);
    
    void setCamera(Vector3f pos);
    
    void setItemToFollow(GuiItem fo);
    
    void cleanup();
    
    void addItem(List<GuiItem> items);
}
