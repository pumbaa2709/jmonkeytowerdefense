/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.towerdefense;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import java.io.IOException;

/**
 *
 * @author Bhasker Hariharan <bhasker@bhasker.net>
 */
class MonsterAttributes implements Savable {
    private int maxHitPoints;
    private int health;
    private double moveSpeed; //speed of movement in m/s
    private Vector3f targetLoc;
    
    public MonsterAttributes() {

    }
    
    public MonsterAttributes(int maxHitPoints,int health,double moveSpeed,Vector3f targetLoc) {
        this.maxHitPoints = maxHitPoints;
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.targetLoc = targetLoc;
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
    public double getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * @param moveSpeed the moveSpeed to set
     */
    public void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    private static final String CONTROL_DIR_NAME = "controlDir";
    
    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
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
}
