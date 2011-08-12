/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.towerdefense;

import com.jme3.app.Application;
import com.jme3.renderer.RenderManager;

/**
 *
 * @author bhasker
 */
public class Game {
    
    private Application app;
    private AIAppState aiState;
    public Game(Application app) {
        this.app = app;        
    }
    
    public boolean startGame() {
        loadLevel();
        initAIAppState();
        initHud();
        //Now that initialization of UI is complete we can trigger the ai
        aiState.setActive(true);
        return true;
    }
    
    public boolean loadLevel() {
        return true;
    }
    
    private void initAIAppState() {
        aiState = new AIAppState();
        //set inactive so that ai doesn't start till initialization is complete
        aiState.setActive(false);
        app.getStateManager().attach(aiState);
    }
    
    private boolean initHud() {
        return true;
    }
    
    public void pauseGame() {
        aiState.setActive(false);
    }
    
    public boolean endGame() {
        //detach the AI
        app.getStateManager().detach(aiState);
        return true;
    }
    
    public void update(float tpf) {
        
    }
        
    public void render(RenderManager rm){
        
    }
}
