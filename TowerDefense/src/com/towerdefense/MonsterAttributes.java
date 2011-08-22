/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.towerdefense;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import java.io.IOException;

/**
 * A class to encapsulate all data about a given monster.
 * 
 * This class will also include any active effects on each monster. Like if
 * a slowing tower hits a monster an active slow effect will be added to the monster
 * in the effects field. 
 * 
 * @author Bhasker Hariharan <bhasker@bhasker.net>
 */
class MonsterAttributes implements Savable {
    private int maxHitPoints;
    private int health;
    private float moveSpeed; //speed of movement in m/s
    private Vector3f targetLoc;
    private int lifeCost;
    
    public MonsterAttributes(int maxHitPoints,
                             int health,
                             float moveSpeed,
                             Vector3f targetLoc) {
        this.maxHitPoints = maxHitPoints;
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.targetLoc = targetLoc;
        this.lifeCost = 1;//by default all monsters cost one life to player.
    }

    /**
     * @return the maxHitPoints
     */
    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    /**
     * @param maxHitPoints the maxHitPoints to set
     */
    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }

    /**
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return the moveSpeed
     */
    public float getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * @param moveSpeed the moveSpeed to set
     */
    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule oc= ex.getCapsule(this);
        oc.write(maxHitPoints,"maxHitPoints",100);
        oc.write(health,"health",100);
        oc.write(moveSpeed,"moveSpeed",1.0f);
        oc.write(targetLoc,"targetLoc",Vector3f.ZERO);
        oc.write(lifeCost,"lifeCost",1);
    }

    public void read(JmeImporter im) throws IOException {
        InputCapsule ic = im.getCapsule(this);
        setMaxHitPoints(ic.readInt("maxHitPoints", 100));
        setHealth(ic.readInt("health",100));
        setMoveSpeed(ic.readFloat("moveSpeed", 1.0f));
        setTargetLoc((Vector3f)ic.readSavable("targetLoc",Vector3f.ZERO));
        setLifeCost(ic.readInt("lifeCost",1));
    }

    /**
     * @return the targetLoc
     */
    public Vector3f getTargetLoc() {
        return targetLoc;
    }

    /**
     * @param targetLoc the targetLoc to set
     */
    public void setTargetLoc(Vector3f targetLoc) {
        this.targetLoc = targetLoc;
    }

    /**
     * @return the lifeCost
     */
    public int getLifeCost() {
        return lifeCost;
    }

    /**
     * @param lifeCost the lifeCost to set
     */
    public void setLifeCost(int lifeCost) {
        this.lifeCost = lifeCost;
    }
}
