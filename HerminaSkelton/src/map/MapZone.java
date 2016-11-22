package map;

public class MapZone {
	
	private MapNode[][] nodes;
	
	public MapZone(String[][] blueprint, int mapWidth, int mapHeight) {
		nodes = new MapNode[10][10];
		for(int i = 0; i < 10; i++) { //rows
			for(int j = 0; j < 10; j++) { //cols
				if(blueprint[i][j].equals(MapConstants.G)) { // grass
					nodes[j][i] = new GrassNode(j, i, mapWidth/10, mapHeight/10);
				} else if(blueprint[i][j].equals(MapConstants.P)) { // path
					nodes[j][i] = new PathNode(j, i, mapWidth/10, mapHeight/10);
				} else if(blueprint[i][j].equals(MapConstants.W)) { // path
					nodes[j][i] = new WallNode(j, i, mapWidth/10, mapHeight/10);
				} else if(blueprint[i][j].equals(MapConstants.CP)) { // path
					nodes[j][i] = new CPCenter(j, i, mapWidth/10, mapHeight/10);
				}
			}
		}
	}
	
	public MapNode[][] getNodes() {
		return nodes;
	}
}
