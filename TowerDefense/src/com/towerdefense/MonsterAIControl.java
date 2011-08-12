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
import com.jme3.math.Vector3f;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;
import java.lang.Math;

/**
 *
 * @author Bhasker Hariharan <bhasker@bhasker.net>
 */
class MonsterAIControl extends AbstractControl implements Savable,Cloneable {
    MonsterAttributes attribs ;
    
    public MonsterAIControl() {
        
    }
    
    public MonsterAIControl(MonsterAttributes attribs) {
        super();
        this.attribs = attribs;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (spatial!=null && attribs!=null) {
            spatial.move(new Vector3f((float)Math.random()*tpf,(float)0.0, (float)Math.random()*tpf));
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //do anything special rendering here, should normally be just handled
        //by regular scene graph rendering.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        final MonsterAIControl control = new MonsterAIControl(attribs);
        control.setSpatial(spatial);
        control.setEnabled(isEnabled());
        return control;
    }
    
    
    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule ic = im.getCapsule(this);
        attribs = (MonsterAttributes)ic.readSavable("MonsterAttributes",null);
        spatial = (Spatial)ic.readSavable("spatial",null);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(attribs,"MonsterAttributes",null);
        oc.write(spatial,"spatial",null);
    }   
}
