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

    private MonsterAttributes attribs;
    private List<Vector3f> currentPath;
    private final PathGenerator pathGen;
    private Vector3f nextStop;
    private Vector3f lastPos;
    private boolean atTarget;

    public MonsterAIControl(MonsterAttributes attribs, PathGenerator pathGen) {
        super();
        this.attribs = attribs;
        this.pathGen = pathGen;
        atTarget = false;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (spatial != null && getAttribs() != null) {
            float movePerFrame = (tpf * getAttribs().getMoveSpeed());

            if (getCurrentPath() == null) {
                currentPath = pathGen.generatePath(spatial.getLocalTranslation(),
                                                   this.getAttribs().getTargetLoc());
                //no valid path then just return and don't try and move anywhere.
                if (getCurrentPath() == null) {
                    return;
                }
                lastPos = spatial.getLocalTranslation().clone();
                nextStop = getCurrentPath().get(0);
                nextStop.y = spatial.getLocalTranslation().y;
                //System.out.println("Next Stop is:"+nextStop.toString());
                getCurrentPath().remove(0);
            }
            else {
                if ((spatial.getLocalTranslation().equals(getNextStop())
                     ||(spatial.getLocalTranslation().subtract(getNextStop()).length()<0.25))
                     && !currentPath.isEmpty()) {
                    lastPos = spatial.getLocalTranslation().clone();
                    nextStop = getCurrentPath().get(0);
                    nextStop.y = spatial.getLocalTranslation().y;
                    getCurrentPath().remove(0);
                }
                if (getCurrentPath().isEmpty() &&
                        (spatial.getLocalTranslation().equals(getNextStop()) ||
                        (spatial.getLocalTranslation().subtract(getNextStop()).length()<0.01))) {
                    //we reached our destination stop.
                    //System.out.println("CurrentPath is empty");
                    atTarget = true;
                    return;
                }
            }
            Vector3f newLocation = getLastPos().clone();
            newLocation.interpolate(getNextStop(), movePerFrame);
            spatial.move(newLocation.subtract(getLastPos()));
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //do anything special rendering here, should normally be just handled
        //by regular scene graph rendering.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        final MonsterAIControl control = new MonsterAIControl(getAttribs(),this.pathGen);
        control.setSpatial(spatial);
        control.setEnabled(isEnabled());
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule ic = im.getCapsule(this);
        setAttribs((MonsterAttributes) ic.readSavable("MonsterAttributes", null));
        spatial = (Spatial) ic.readSavable("spatial", null);
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
    public MonsterAttributes getAttribs() {
        return attribs;
    }

    /**
     * @param attribs the attribs to set
     */
    public void setAttribs(MonsterAttributes attribs) {
        this.attribs = attribs;
    }

    /**
     * @return the currentPath
     */
    public List<Vector3f> getCurrentPath() {
        return currentPath;
    }

    /**
     * @return the nextStop
     */
    public Vector3f getNextStop() {
        return nextStop;
    }

    /**
     * @return the lastPos
     */
    public Vector3f getLastPos() {
        return lastPos;
    }

    /**
     * @return the reachedTarget
     */
    public boolean isAtTarget() {
        return atTarget;
    }
}
