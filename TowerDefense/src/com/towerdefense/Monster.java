/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.towerdefense;

import com.jme3.scene.Spatial;

/**
 *
 * @author Bhasker Hariharan <bhasker@bhasker.net>
 */
class Monster {
    
    private Spatial spatial;
    private MonsterAttributes attribs;
    public Monster(Spatial spatial,MonsterAttributes attribs) {
        this.spatial = spatial;
        this.attribs = attribs;
        this.spatial.addControl(new MonsterAIControl(attribs));
    }

    /**
     * @return the spatial
     */
    public Spatial getSpatial() {
        return spatial;
    }

    /**
     * @param spatial the spatial to set
     */
    public void setSpatial(Spatial spatial) {
        this.spatial = spatial;
    }

    /**
     * @return the attribs
     */
    public MonsterAttributes getAttribs() {
        return attribs;
    }

    /**
     * @param attribs the attribs to set
     */
    public void setAttribs(MonsterAttributes attribs) {
        this.attribs = attribs;
    }
    
    public void update(float tpf) {
        //update the location of the monster
    }
    
}
