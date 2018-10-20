package org.a07planning.androidgame2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Pathfinder {
    public Pathfinder() {

    }



    List<Coordinate> calculateShortestGrid(Grid grid, Coordinate start, Coordinate finish) {
        Set<Coordinate> visited = new HashSet<>();
        Set<Coordinate> unvisited = new HashSet<>();
        Coordinate coords[][] = new Coordinate[grid.gridWidth][grid.gridHeight];
        Map<Coordinate,Coordinate> shortestGrid = new HashMap<Coordinate, Coordinate>();
        Map<Coordinate, Integer> distance = new HashMap<Coordinate, Integer>();
        for(int i = 0; i < grid.gridWidth; i++) {
            for(int j = 0; j < grid.gridHeight; j++) {
                coords[i][j] = new Coordinate(i,j);
                distance.put(coords[i][j], 10000000 );
            }
        }
        Coordinate newStart = coords[start.x][start.y];
        Coordinate newFinish = coords[finish.x][finish.y];
        distance.put(newStart, 0);
        unvisited.add(newStart);
        while(unvisited.size() != 0) {
            Coordinate next = findShortestCoordinate(unvisited, distance);
            unvisited.remove(next);
            List<Coordinate> adjacent = new ArrayList<>();
            if((next.x+1) < grid.gridWidth &&
                    grid.road(next.x+1,next.y,1,1)) { //right
                adjacent.add(coords[next.x+1][next.y]);
            }
            if((next.y+1) < grid.gridHeight &&
                    grid.road(next.x,next.y+1,1,1)) { //bottom
                adjacent.add(coords[next.x][next.y+1]);
            }
            if(next.x > 0 &&
                    grid.road(next.x-1,next.y,1,1)) { //left
                adjacent.add(coords[next.x-1][next.y]);
            }
            if(next.y > 0 &&
                    grid.road(next.x,next.y-1,1,1)) { //top
                adjacent.add(coords[next.x][next.y-1]);
            }
            for(int i = 0; i < adjacent.size(); i++) {
                if(!visited.contains(adjacent.get(i))) {
                    unvisited.add(adjacent.get(i));
                    distance.put(adjacent.get(i),distance.get(next)+1);
                    shortestGrid.put(adjacent.get(i), next);
                }
            }
            visited.add(next);
        }
        List<Coordinate> path = new ArrayList<>();
        Coordinate current = newFinish;
        while(current != null) {
            path.add(0,current);
            current = shortestGrid.get(current);
        }
        return path;
    }

    Coordinate findShortestCoordinate(Set<Coordinate> unvisited, Map<Coordinate, Integer> distance) {

        Integer shortestDistance = 10000000;
        Coordinate shortestCoordinate = null;
        Iterator it = unvisited.iterator();
        while (it.hasNext()) {
            Coordinate next = (Coordinate)it.next();
            if(distance.get(next) < shortestDistance) {
                shortestDistance = distance.get(next);
                shortestCoordinate = next;
            }
        }
        return shortestCoordinate;
    }

    List<Coordinate> findPath(Coordinate start, Coordinate finish, Grid grid) {
        return calculateShortestGrid(grid, start, finish);
    }
}
