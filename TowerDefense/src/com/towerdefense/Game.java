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

/**
 *
 * @author bhasker
 */
public class Game {
    
    private TDApp app;
    private AIAppState aiState;
    private Geometry levelFloor;
    private Geometry levelGrid;
    private int rows;
    private int cols;
    public Game(TDApp app) {
        this.app = app;        
        rows = 10;
        cols = 20;
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
        //create a green floor for the level.
        levelGrid = new Geometry("floorGrid",new Grid(rows+1,cols+1,1.0f));
       
        Material gridMat = new Material(app.getAssetManager(),"Common/MatDefs/Misc/SolidColor.j3md");
        gridMat.getAdditionalRenderState().setWireframe(true);
        gridMat.setColor("m_Color",new ColorRGBA(0.0f,0.0f,0.0f,1.0f));
        levelGrid.setMaterial(gridMat);
        levelGrid.center().move(new Vector3f(cols/2,0.1f,rows/2));
       
        levelFloor = new Geometry("floor",new Box(Vector3f.ZERO,cols/2,0.0f,rows/2f));     
        Material floorMat = new Material(app.getAssetManager(),"Common/MatDefs/Misc/SolidColor.j3md");
        floorMat.setColor("m_Color",new ColorRGBA(0.2f,0.6f,0.2f,1.0f));
        levelFloor.setMaterial(floorMat);
        levelFloor.center().move(new Vector3f(cols/2,0.0f,rows/2));
        
        app.getRootNode().attachChild(levelFloor);
        app.getRootNode().attachChild(levelGrid);
        app.getCamera().setLocation(new Vector3f(cols/2.0f,20.0f,(rows/2.0f)+0.001f));
        app.getCamera().lookAt(new Vector3f(cols/2,0.0f,rows/2),Vector3f.UNIT_Y);
        app.getFlyByCamera().setEnabled(false);
        return true;
    }
    
    private void initAIAppState() {
        System.out.println("Game::initAIAppState()");
        aiState = new AIAppState(); 
        aiState.addSpawnPoint(Vector3f.ZERO);
        aiState.addTarget(new Vector3f(cols,0,rows));
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
