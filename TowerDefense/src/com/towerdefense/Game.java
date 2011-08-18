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
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        //Walk List of monsters and remove any that have reached the last square.
        HashMap<String,Spatial> monsters = world.getMonsters();

        Iterator it = monsters.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String,Spatial> monsterEntry = (Map.Entry<String,Spatial>)it.next();
            MonsterAIControl ctrl = monsterEntry.getValue().getControl(MonsterAIControl.class);
            if (ctrl.isAtTarget()) {
                app.getRootNode().detachChild(monsterEntry.getValue());
                it.remove();//remove this from the monsters list.
            }
        }
    }
        
    public void render(RenderManager rm){
        
    }
}
