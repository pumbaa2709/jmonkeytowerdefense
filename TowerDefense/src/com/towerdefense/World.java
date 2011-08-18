/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.towerdefense;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Array;


/**
 * World is an encapsulation of the current state of a map. 
 * 
 * It will provide pathfinding functions for monsters as well as query 
 * functions to determine state of the world
 * 
 * @author Bhasker Hariharan <bhasker@bhasker.net>
 * 
 */
public class World {
    
    private Geometry levelFloor;
    private Geometry levelGrid;
    private int rows;
    private int cols;
    private float square_sz;
    private TDApp app;
    private List<Vector3f> targets;
    private List<Vector3f> spawnPoints;
    private HashMap<String,Spatial> monsters;
    private PathGenerator pathGen;

    /**
     * @return the pathGen
     */
    public PathGenerator getPathGen() {
        return pathGen;
    }
    
    //Represents one square on the map grid.
    public class Square {
        //isOccupied is true if the map square has a tower on it
        //and can't be navigated through.
        public boolean isOccupied;
        
        public Square() {
            isOccupied=false;
        }
    }
    
    private Square[][] grid;

    

    /**
     * 
     * @param rows number of rows in the map
     * @param cols number of columns in the map.
     * @param square_sz size of each square in the map in meters
     */
    public World(TDApp app,int rows,int cols,float square_sz) {
        this.rows = rows;
        this.cols = cols;
        this.square_sz = square_sz;
        this.app = app;
        this.targets = new ArrayList<Vector3f>();
        this.spawnPoints = new ArrayList<Vector3f>();        
        this.monsters = new HashMap<String,Spatial>();
        initNavGrid(rows,cols);
    }
    
    public void initNavGrid(int rows,int cols) {
        this.grid = (Square[][])Array.newInstance(Square.class,rows,cols);
        for(int i=0;i<rows;i++) 
            for (int j=0;j<cols;j++) {
                grid[i][j] = new Square();
            }
    }
    
    public boolean loadLevel(String name) {
        //create a green floor for the level.
        levelGrid = new Geometry("floorGrid",new Grid(rows+1,cols+1,1.0f));
       
        Material gridMat = new Material(app.getAssetManager(),"Common/MatDefs/Misc/SolidColor.j3md");
        gridMat.getAdditionalRenderState().setWireframe(true);
        gridMat.setColor("m_Color",new ColorRGBA(0.0f,0.0f,0.0f,1.0f));
        levelGrid.setMaterial(gridMat);
        levelGrid.center().move(new Vector3f(cols/2-0.5f,0.1f,rows/2-0.5f));
       
        levelFloor = new Geometry("floor",new Box(Vector3f.ZERO,cols/2,0.0f,rows/2f));     
        Material floorMat = new Material(app.getAssetManager(),"Common/MatDefs/Misc/SolidColor.j3md");
        floorMat.setColor("m_Color",new ColorRGBA(0.2f,0.6f,0.2f,1.0f));
        levelFloor.setMaterial(floorMat);
        levelFloor.center().move(new Vector3f(cols/2-0.5f,0.0f,rows/2-0.5f));
        
        app.getRootNode().attachChild(levelFloor);
        app.getRootNode().attachChild(levelGrid);
        app.getCamera().setLocation(new Vector3f(cols/2.0f,20.0f,(rows/2.0f)+0.001f));
        app.getCamera().lookAt(new Vector3f(cols/2,0.0f,rows/2),Vector3f.UNIT_Y);
        app.getFlyByCamera().setEnabled(false);

        spawnPoints.add(Vector3f.ZERO);
        targets.add(new Vector3f(cols-1,0,rows-1));
        
        //create the pathGenerator object and pass in the navigation grid 
        this.pathGen = new PathGenerator(this.grid);
        
        return true;
    }

    List<Vector3f> getTargets() {
        return targets;
    }

    List<Vector3f> getSpawnPoints() {
        return spawnPoints;
    }

    /**
     * @return the monsters
     */
    public HashMap<String,Spatial> getMonsters() {
        return monsters;
    }
    
    public void addMonster(String name,Spatial spatial) {
        monsters.put(name,spatial);
    }
    
    public void removeMonster(String name) {
        monsters.remove(name);
    }
    
}
