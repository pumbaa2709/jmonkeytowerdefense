/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.towerdefense;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.towerdefense.World.Square;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.lang.Math;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * PathGenerator takes in a grid of squares and provides routing information
 * using A*
 * 
 * @author Bhasker Hariharan <bhasker@bhasker.net>
 */
public class PathGenerator {
   
    /**
     * 
     * 
     * 
     */
    private Square[][] grid;


    public PathGenerator(Square[][] grid) {
        this.grid = grid;        
    }
    
    public float distance(Vector3f start, Vector3f end)  {
        return (float)Math.sqrt(
                (end.x-start.x)*(end.x-start.x)+
                (end.y-start.y)*(end.y-start.y)+
                (end.z-start.z)*(end.z-start.z)
                );
    }
    
    public List<Vector3f> getNeighbours(Vector3f loc) {
        List<Vector3f> neighbours = new ArrayList<Vector3f>(4);
        int directions[][] = { {-1,0},{0,-1},{1,0},{0,1} };
        for(int[] d:directions) {
            Vector3f v = new Vector3f(loc.x+d[0],loc.y,loc.z+d[1]);
            if (v.x<0 || v.z<0 || v.x>=grid[0].length ||
                    v.z>=grid.length)
                continue;
            neighbours.add(v);
        }
        return neighbours;
    }
    
    /**
     * Do an A* search from start->end and return a valid path or an empty list otherwise.
     * 
     * @param start starting point on the grid, the floored x/z values of the Vector represent grid location
     * @param end ending point on the grid, the floored x/z values of the vector3f represent grid location
     * @return 
     */
    List<Vector3f> generatePath(Vector3f start,Vector3f end) {
        Queue<Vector3f> unvisited=new LinkedList<Vector3f>();
        
        Set<Vector3f> closedSet= new HashSet<Vector3f>();
        unvisited.add(start);
        HashMap<Vector3f,Integer> visited = new HashMap<Vector3f,Integer>();
        visited.put(start,Integer.valueOf(0));
        boolean found=false;
        while (!unvisited.isEmpty()) {
            Vector3f curLoc = unvisited.poll();
            if (curLoc==null) {
                //no valid path from start->end
                break;
            }
            if (curLoc.equals(end)){
                found=true;
                break;
            }
            
            List<Vector3f> neighbours = getNeighbours(curLoc);
            closedSet.add(curLoc);

            for(Vector3f neighbour:neighbours) {
                if (
                   grid[(int)neighbour.z][(int)neighbour.x].isOccupied) {
                    //can't route through blocked squares
                    continue;
                }
                if (closedSet.contains(neighbour)) {
                    continue;
                }
                else {
                    if (!grid[(int)neighbour.z][(int)neighbour.x].isOccupied &&
                            !visited.containsKey(neighbour)) {
                        unvisited.add(neighbour);
                        visited.put(neighbour, new Integer(visited.get(curLoc)+1));
                    }
                }
            }
        }
        if (found) {
            return extractPath(start,end,visited);
        }
        return null;
    }

    /**
     * Walks visited back from end to start and reconstructs the path.
     * @param start starting Location
     * @param end  ending Location
     * @param visited list of visited nodes
     * @return 
     */
    private List<Vector3f> extractPath(Vector3f start, 
                                       Vector3f end,
                                       HashMap<Vector3f, Integer> visited) {
        List<Vector3f> path = new LinkedList<Vector3f>();
        Vector3f curLoc = end;
        while (visited.get(curLoc)!=0) {
            List<Vector3f> neighbours = getNeighbours(curLoc);
            int min_value = visited.get(curLoc);
            Vector3f tmpLoc = null;
            for(Vector3f neighbour:neighbours) {
                if ((visited.get(neighbour)!=null) &&
                        (visited.get(neighbour)<min_value)) {
                    tmpLoc = neighbour;
                    min_value = visited.get(neighbour);
                }
            }
            //prepend to list the currentLocation;
            path.add(0, curLoc);
            curLoc = tmpLoc;
        }
        return path;
    }
    
    /**
     * checks if any of the locations in the path is blocked if
     * so returns false.
     * 
     * @param a list of locations that make up the path
     */
    public boolean validPath(List<Vector3f> path) {
        for (Vector3f v:path) {
            if (grid[(int)v.z][(int)v.x].isOccupied) {
                System.out.println("PathGenerator::validPath:false");
                printGrid();
                return false;
            }
        }
        return true;
    }
    
    /** 
     * print the grid to System.out
     */
    public void printGrid() {
        for (Square[] row:grid) {
            for (Square sq:row){ 
                System.out.print(sq);
            }
            System.out.print("\n");
        }
    }
}
