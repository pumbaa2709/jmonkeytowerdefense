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
import com.jme3.math.FastMath;
import java.io.IOException;
import java.util.List;

/**
 * A simple class to provide the basic AI behind a monster in tower defense
 *
 * @author Bhasker Hariharan <bhasker@bhasker.net>
 */
class MonsterAIControl extends AbstractControl implements Savable, Cloneable {

    MonsterAttributes attribs;
    List<Vector3f> currentPath;
    private final PathGenerator pathGen;
    Vector3f nextStop;
    Vector3f lastPos;

    public MonsterAIControl(MonsterAttributes attribs, PathGenerator pathGen) {
        super();
        this.attribs = attribs;
        this.pathGen = pathGen;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (spatial != null && attribs != null) {
            float movePerFrame = (tpf * attribs.getMoveSpeed());

            if (currentPath == null) {
                currentPath = pathGen.generatePath(spatial.getLocalTranslation(),
                                                   this.attribs.getTargetLoc());
                //no valid path then just return and don't try and move anywhere.
                if (currentPath == null) {
                    return;
                }
                lastPos = spatial.getLocalTranslation().clone();
                nextStop = currentPath.get(0);
                nextStop.y = spatial.getLocalTranslation().y;
                //System.out.println("Next Stop is:"+nextStop.toString());
                currentPath.remove(0);
            }
            else {
                if ((spatial.getLocalTranslation().equals(nextStop)
                     ||(spatial.getLocalTranslation().subtract(nextStop).length()<0.25))
                     && !currentPath.isEmpty()) {
                    lastPos = spatial.getLocalTranslation().clone();
                    nextStop = currentPath.get(0);
                    nextStop.y = spatial.getLocalTranslation().y;
                    currentPath.remove(0);
                }
                if (currentPath.isEmpty() &&
                        (spatial.getLocalTranslation().equals(nextStop) ||
                        (spatial.getLocalTranslation().subtract(nextStop).length()<0.01))) {
                    //we reached our destination stop.
                    //System.out.println("CurrentPath is empty");
                    return;
                }
            }
            Vector3f newLocation = lastPos.clone();
            newLocation.interpolate(nextStop, movePerFrame);
            spatial.move(newLocation.subtract(lastPos));
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //do anything special rendering here, should normally be just handled
        //by regular scene graph rendering.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        final MonsterAIControl control = new MonsterAIControl(attribs,this.pathGen);
        control.setSpatial(spatial);
        control.setEnabled(isEnabled());
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule ic = im.getCapsule(this);
        attribs = (MonsterAttributes) ic.readSavable("MonsterAttributes", null);
        spatial = (Spatial) ic.readSavable("spatial", null);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(attribs, "MonsterAttributes", null);
        oc.write(spatial, "spatial", null);
    }
}
