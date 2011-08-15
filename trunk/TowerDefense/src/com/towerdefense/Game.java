/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.towerdefense;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import java.util.List;

/**
 * Main abstraction of an active game
 * @author bhasker
 */
public class Game {
    
    private TDApp app;
    private AIAppState aiState;
    private World world;
    public Game(TDApp app) {
        this.app = app;        
    }
    
    public boolean startGame() {
        System.out.println("Game::startGame()");
        loadLevel();
        initHud();
        initAIAppState();
        app.getStateManager().attach(aiState);
        return true;
    }
    
    public boolean loadLevel() {
        System.out.println("Game::loadLevel");
        world = new World(app,10,20,1.0f);
        return world.loadLevel("");
    }
    
    private void initAIAppState() {
        System.out.println("Game::initAIAppState()");
        aiState = new AIAppState(world);         
    }
    
    private boolean initHud() {
        System.out.println("Game::initHud()");
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
