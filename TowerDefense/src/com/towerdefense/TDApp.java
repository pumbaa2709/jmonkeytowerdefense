package com.towerdefense;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;


/**
 * TowerDefense
 * @author Bhasker Hariharan
 */
public class TDApp extends SimpleApplication implements ScreenController {

    
    private Nifty nifty;
    private Game  curGame=null;
    public static void main(String[] args) {
        TDApp app = new TDApp();
        app.start();
    }

    public void initNifty() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                inputManager,
                audioRenderer,
                guiViewPort
                );
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/MainMenu.xml","start",this);
        guiViewPort.addProcessor(niftyDisplay);
        flyCam.setDragToRotate(true);
    }
    
    
    @Override
    public void simpleInitApp() {
        initNifty();
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        if (curGame!=null) {
            curGame.update(tpf);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
        if (curGame!=null) {
            curGame.render(rm);
        }
    }

    public void bind(Nifty nifty, Screen screen) {
        
    }

    public void onStartScreen() {
        
    }

    public void onEndScreen() {
        
    }
    
    public void quit() {
        curGame.endGame();
        this.stop();
    }
    
    public void startGame() {
        System.out.println("startGame() called");
        nifty.gotoScreen("end");
        flyCam.setDragToRotate(false);
        curGame = new Game(this);
        curGame.startGame();
    }

    /**
     * @return the curGame
     */
    public Game getCurGame() {
        return curGame;
    }
}
