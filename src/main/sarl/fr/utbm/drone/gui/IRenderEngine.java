package fr.utbm.drone.gui;

import java.util.List;

import fr.utbm.drone.gui.graph.items.GuiItem;

public interface IRenderEngine {
	
    void init(Window window) throws Exception;
    
    void input(Window window, MouseInput mouseInput);

    void update(float interval, MouseInput mouseInput);
    
    void render(Window window);
    
    void cleanup();
    
    void addItem(List<GuiItem> items);
}
