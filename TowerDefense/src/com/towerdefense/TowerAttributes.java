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
import java.io.IOException;

/**
 * This class encapsulates common tower attributes like radar range/attack range
 * dmg by this tower per hit.
 * 
 * Later we will enhance this to support different kinds of attacks like
 * aoe attacks vs single shot
 * 
 * 
 * @author Bhasker Hariharan <bhasker@bhasker.net>
 */
public class TowerAttributes implements Savable {
   
    private float towerDmg;
    private float radarRadius;
    private float attackRadius;
    private float rechargeTime;
    private boolean charged;
    private float dmgModifier;
    
    /**
     * 
     * @param towerDmg dmg caused by this tower per hit
     * @param radarRadius detection range for incoming enemies for this tower
     * @param attackRadius range at which the tower can hit enemies
     * @param rechargeTime the charge time per shot
     * @param charged whether this tower is ready to fire
     */
    public TowerAttributes(float towerDmg,
                           float radarRadius,
                           float attackRadius,
                           float rechargeTime,
                           boolean charged) {
        this.towerDmg = towerDmg;
        this.radarRadius = radarRadius;
        this.attackRadius = attackRadius;
        this.rechargeTime = rechargeTime;
        this.charged = charged;
        this.dmgModifier = 1.0f;
    }
    
    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(this.towerDmg,"towerDmg",0.0f);
        oc.write(this.radarRadius,"radarRadius",0.0f);
        oc.write(this.attackRadius,"attackRadius",0.0f);
        oc.write(this.rechargeTime,"rechargeTime",0.0f);
        oc.write(this.charged, "charged", false);
        oc.write(this.dmgModifier,"dmgModifier",1.0f);
    }

    public void read(JmeImporter im) throws IOException {
        InputCapsule ic = im.getCapsule(this);
        setTowerDmg(ic.readFloat("towerDmg", 0.0f));
        setRadarRadius(ic.readFloat("radarRadius",0.0f));
        setAttackRadius(ic.readFloat("attackRadius",0.0f));
        setRechargeTime(ic.readFloat("rechargeTime",0.0f));
        setCharged(ic.readBoolean("charged", false));
        setDmgModifier(ic.readFloat("dmgModifier", 1.0f));
    }

    /**
     * @return the towerDmg
     */
    public float getTowerDmg() {
        return towerDmg;
    }

    /**
     * @param towerDmg the towerDmg to set
     */
    public void setTowerDmg(float towerDmg) {
        this.towerDmg = towerDmg;
    }


    /**
     * @return the radarRadius
     */
    public float getRadarRadius() {
        return radarRadius;
    }

    /**
     * @param radarRadius the radarRadius to set
     */
    public void setRadarRadius(float radarRadius) {
        this.radarRadius = radarRadius;
    }

    /**
     * @return the attackRadius
     */
    public float getAttackRadius() {
        return attackRadius;
    }

    /**
     * @param attackRadius the attackRadius to set
     */
    public void setAttackRadius(float attackRadius) {
        this.attackRadius = attackRadius;
    }

    /**
     * @return the rechargeTime
     */
    public float getRechargeTime() {
        return rechargeTime;
    }

    /**
     * @param rechargeTime the rechargeTime to set
     */
    public void setRechargeTime(float rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    /**
     * @return the charged
     */
    public boolean isCharged() {
        return charged;
    }

    /**
     * @param charged the charged to set
     */
    public void setCharged(boolean charged) {
        this.charged = charged;
    }

    /**
     * @return the dmgModifier
     */
    public float getDmgModifier() {
        return dmgModifier;
    }

    /**
     * @param dmgModifier the dmgModifier to set
     */
    public void setDmgModifier(float dmgModifier) {
        this.dmgModifier = dmgModifier;
    }
    
}
