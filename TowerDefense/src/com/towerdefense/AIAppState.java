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
import java.util.HashMap;
import java.util.Date;
/**
 *
 * @author bhasker
 */
public class AIAppState extends AbstractAppState {
 
    private int waveStrength = 10;
    private int waveNumber=0;
    private long lastSpawnTime = 0;
    private long waveSpawnInterval = 10000;
    private HashMap<String,Spatial> monsters;
    private TDApp app;
    
    @Override
    public void initialize(AppStateManager stateManager,Application theApp) {
        super.initialize(stateManager,theApp);
        app = (TDApp)theApp;
        setMonsters(new HashMap<String,Spatial>());
    }
    
    @Override
    public void update(float tpf) {
        //Check for attack from towers
        Date d = new Date();
        long curTime = d.getTime();
        //
        if ((curTime-getLastSpawnTime())>10000) {
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
        for (int i=0;i<getWaveStrength();i++) {
            Box box = new Box(Vector3f.ZERO,0.25f,0.25f,0.25f);
            String monsterId = "monster_"+getWaveNumber()+"_"+i;
            Spatial monster = new Geometry(monsterId,box);
            monster.updateModelBound();
            monster.addControl(new MonsterAIControl(new MonsterAttributes(100+5*getWaveNumber(),
                    100+5*getWaveNumber(),1.0+(float)getWaveNumber()/100
                    )));
            getMonsters().put(monsterId, monster);
            Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/SolidColor.j3md");
            mat.setColor("m_Color", ColorRGBA.Red);
            monster.setMaterial(mat);
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

    /**
     * @return the monsters
     */
    public HashMap<String,Spatial> getMonsters() {
        return monsters;
    }

    /**
     * @param monsters the monsters to set
     */
    public void setMonsters(HashMap<String,Spatial> monsters) {
        this.monsters = monsters;
    }
}