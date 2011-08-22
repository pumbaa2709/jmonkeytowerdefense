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
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 *
 * @author Bhasker Hariharan <bhasker@bhasker.net>
 */
public class GunTowerAIControl extends AbstractControl implements Savable, Cloneable {

    private TowerAttributes attribs;
    private Spatial target;
    
    public GunTowerAIControl(TowerAttributes attribs) {
        this.attribs = attribs;
    }
    
    @Override
    protected void controlUpdate(float tpf) {

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    public Control cloneForSpatial(Spatial spatial) {
        final GunTowerAIControl control = new GunTowerAIControl(getAttribs());
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule ic = im.getCapsule(this);
        setAttribs((TowerAttributes) ic.readSavable("GunTowerAttributes", null));
        spatial = (Spatial) ic.readSavable("spatial", null);
        target = (Spatial)ic.readSavable("target", null);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(getAttribs(), "MonsterAttributes", null);
        oc.write(spatial, "spatial", null);
    }

    /**
     * @return the attribs
     */
    public TowerAttributes getAttribs() {
        return attribs;
    }

    /**
     * @param attribs the attribs to set
     */
    public void setAttribs(TowerAttributes attribs) {
        this.attribs = attribs;
    }

    /**
     * @return the target
     */
    public Spatial getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(Spatial target) {
        this.target = target;
    }
    
}
