/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.towerdefense;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.Date;

/**
 * PlayerAppState is responsbile for maintaining player state and
 * player specific behaviours
 * 
 * 
 * @author Bhasker Hariharan <bhasker@bhasker.net>
 */
public class PlayerAppState extends AbstractAppState {
   private boolean alive = true;
   private long money = 0;
   private int livesLeft = 0;
   private TDApp app;
   private long lastTowerSpawnTime =0;
   private long towerSpawnTime=5000;
   private long towerNumber;
   private World world;

   public PlayerAppState(World world) {
       this.world = world;
   }
   
   @Override
   public void initialize(AppStateManager stateManager,Application theApp) {
       super.initialize(stateManager,theApp);
       app = (TDApp)theApp;
    }
   
   @Override
   public void update(float tpf) {
       Date d = new Date();
       long curTime = d.getTime();
       if ((curTime-getLastTowerSpawnTime())>getTowerSpawnTime()) {
           spawnTower();
           spawnTower();
           spawnTower();
           spawnTower();
           setLastTowerSpawnTime(curTime);
       }
   }
   
   @Override
   public void render(RenderManager rm) {
       
   }
   
   /** Simulates a player playing the game by spawning towers
    *  at random locations.
    * 
    * 
    */
   public void spawnTower() {
       Box box = new Box(Vector3f.ZERO,0.1f,0.5f,0.1f);
       String towerId = "tower_"+getTowerNumber();
       setTowerNumber(getTowerNumber()+1);
       Spatial tower = new Geometry(towerId,box);
       tower.updateModelBound();
       tower.addControl(new GunTowerAIControl(new
               TowerAttributes(
               45.0f,//dmg per shot
               5.0f,//radar range
               3.0f,//attack range
               0.2f,//recharge time in seconds
               true// if the tower is initially charged or not.
               )));
       Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/SolidColor.j3md");
       mat.setColor("m_Color", ColorRGBA.Red);
       tower.setMaterial(mat);
       tower.move(getTowerLoc());
       world.addTower(towerId, tower);
       app.getRootNode().attachChild(tower);      
   }
   
   /** 
    * returns a random map location for a tower
    * 
    * @return the vector position for the tower.
    */
   public Vector3f getTowerLoc() {
       float col = (float)Math.random()*world.getCols();
       float row = (float)Math.random()*world.getRows();
       col = (int)(col);
       row = (int)(row);
       return new Vector3f(col,
               0.0f,
               row);
   }
   
   /**
    * @return the alive
    */
   public boolean isAlive() {
       return alive;
   }

   /**
    * @return the money
    */
   public long getMoney() {
       return money;
   }
   
   /**
    * @return the livesLeft
    */
   public int getLivesLeft() {
       return livesLeft;
   }
   
   /**
    * @param livesLeft the livesLeft to set
    */
   public void setLivesLeft(int livesLeft) {
       this.livesLeft = livesLeft;
   }
   
   /** 
    * @param lives the lives to add
    */
   public void addLives(int lives) {
       this.livesLeft+=lives;
   }
   
   /**
    * @param lives the lives to remove, if livesLeft goes to zero player alive is set to false
    */
   public void subtractLives(int lives) {
       this.livesLeft-=lives;
       if (this.livesLeft<=0) {
           alive=false;
           this.livesLeft = 0;
       }
   }
   
   /**
    * @param money the money to set
    */
   public void setMoney(long money) {
       this.money = money;
   }
   
   /**
    * @param money to add to this player
    */
   public void addMoney(long money) {
       this.money+=money;
   }
   
   /**
    * @param money to remove from this player
    */
   public void subtractMoney(long money) {
       this.money-=money;
   }

    /**
     * @return the lastTowerSpawnTime
     */
    public long getLastTowerSpawnTime() {
        return lastTowerSpawnTime;
    }

    /**
     * @param lastTowerSpawnTime the lastTowerSpawnTime to set
     */
    public void setLastTowerSpawnTime(long lastTowerSpawnTime) {
        this.lastTowerSpawnTime = lastTowerSpawnTime;
    }

    /**
     * @return the towerNumber
     */
    public long getTowerNumber() {
        return towerNumber;
    }

    /**
     * @param towerNumber the towerNumber to set
     */
    public void setTowerNumber(long towerNumber) {
        this.towerNumber = towerNumber;
    }

    /**
     * @return the towerSpawnTime
     */
    public long getTowerSpawnTime() {
        return towerSpawnTime;
    }

    /**
     * @param towerSpawnTime the towerSpawnTime to set
     */
    public void setTowerSpawnTime(long towerSpawnTime) {
        this.towerSpawnTime = towerSpawnTime;
    }
}
