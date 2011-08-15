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
 * This is the main appstate class to control the AI of the game. 
 * 
 * Its responsible for spawning waves of monsters etc.
 * 
 * @author bhasker
 */
public class AIAppState extends AbstractAppState {
 
    private int waveStrength = 10;
    private int waveNumber=0;
    private long lastSpawnTime = 0;
    private long waveSpawnInterval = 10000;
    private TDApp app;
    private World world;

    public AIAppState(World world) {
        this.world = world;
    }
    
    @Override
    public void initialize(AppStateManager stateManager,Application theApp) {
        super.initialize(stateManager,theApp);
        app = (TDApp)theApp;
    }
    
    @Override
    public void update(float tpf) {
        //Check for attack from towers
        Date d = new Date();
        long curTime = d.getTime();
        //spawn every certain seconds.
        if ((curTime-getLastSpawnTime())>getWaveSpawnInterval()) {
            if (getWaveNumber()<100) {
                spawnWave();
                setLastSpawnTime(curTime);
                waveNumber++;
            }
        }
    }
    
    @Override
    public void render(RenderManager rm) {
        
    }
    
    private void spawnWave(){
        System.out.println("Spawning Wave:"+getWaveNumber());
        //spawn X new monsters with hitpoints scaled to wave number
        //pick a random spawn point
        Vector3f waveSpawnPoint = world.getSpawnPoints().get((int)(Math.random()*world.getSpawnPoints().size()));
        Vector3f targetLoc = world.getTargets().get((int)(Math.random()*world.getTargets().size()));
        for (int i=0;i<getWaveStrength();i++) {
            
            Box box = new Box(waveSpawnPoint,0.25f,0.25f,0.25f);
            String monsterId = "monster_"+getWaveNumber()+"_"+i;
            Spatial monster = new Geometry(monsterId,box);
            monster.updateModelBound();
            monster.addControl(new MonsterAIControl(
                    new MonsterAttributes(
                    100+5*getWaveNumber(),
                    100+5*getWaveNumber(),
                    1.0+(float)getWaveNumber()/100,
                    targetLoc
                    )
                    ));
            world.addMonster(monsterId, monster);
            Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/SolidColor.j3md");
            mat.setColor("m_Color", ColorRGBA.Red);
            monster.setMaterial(mat);
            monster.center().move(Vector3f.ZERO);
            app.getRootNode().attachChild(monster);
        }
    }

    /**
     * @return the waveStrength
     */
    public int getWaveStrength() {
        return waveStrength;
    }

    /**
     * @param waveStrength the waveStrength to set
     */
    public void setWaveStrength(int waveStrength) {
        this.waveStrength = waveStrength;
    }

    /**
     * @return the waveNumber
     */
    public int getWaveNumber() {
        return waveNumber;
    }

    /**
     * @return the lastSpawnTime
     */
    public long getLastSpawnTime() {
        return lastSpawnTime;
    }

    /**
     * @param lastSpawnTime the lastSpawnTime to set
     */
    public void setLastSpawnTime(long lastSpawnTime) {
        this.lastSpawnTime = lastSpawnTime;
    }

    /**
     * @return the waveSpawnInterval
     */
    public long getWaveSpawnInterval() {
        return waveSpawnInterval;
    }

    /**
     * @param waveSpawnInterval the waveSpawnInterval to set
     */
    public void setWaveSpawnInterval(long waveSpawnInterval) {
        this.waveSpawnInterval = waveSpawnInterval;
    }
    
}
