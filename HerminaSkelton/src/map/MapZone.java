package map;

import java.awt.Dimension;

public class MapZone {
	
	private MapNode[][] nodes;
	
	public MapZone(String[][] blueprint, int mapWidth, int mapHeight) {
		nodes = new MapNode[10][10];
		for(int i = 0; i < 10; i++) { //rows
			for(int j = 0; j < 10; j++) { //cols
				if(blueprint[i][j].equals(MapConstants.G)) { // grass
					nodes[i][j] = new GrassNode(i, j, mapWidth/10, mapHeight/10);
				} else if(blueprint[i][j].equals(MapConstants.P)) { // path
					nodes[i][j] = new PathNode(i, j, mapWidth/10, mapHeight/10);
				} else if(blueprint[i][j].equals(MapConstants.W)) { // path
					nodes[i][j] = new WallNode(i, j, mapWidth/10, mapHeight/10);
				}
			}
		}
	}
	
	public MapNode[][] getNodes() {
		return nodes;
	}
}
