package com.daniel.maze;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class MazeBuilder extends Thread {
	private MazeRepresentation mazeRepresentation;
	
	public MazeBuilder(MazeRepresentation mazeRepresentation) {
		this.mazeRepresentation = mazeRepresentation;
	}

	public void run() {
		this.fillSpace();
		this.carve();
	}

	public int getGlobalX(int localX) {
		return localX + (int) Math.floor(mazeRepresentation.getLocation().getX());
	}

	public int getGlobalZ(int localZ) {
		return localZ + (int) Math.floor(mazeRepresentation.getLocation().getZ());
	}

	private void fillSpace() {
		Material material = Material.getMaterial(mazeRepresentation.getMaterial());
		Location mazeLocation = mazeRepresentation.getLocation();
		World world = mazeLocation.getWorld();
		for(int x = 0; x < mazeRepresentation.getSize(); x++) {
			for(int z = 0; z < mazeRepresentation.getSize(); z++) {
				for(int y = 0; y < mazeRepresentation.getHeight(); y++) {
					Location blockLocation = mazeLocation.clone();
					blockLocation.add(x, y, z);
					world.getBlockAt(blockLocation).setType(material);
				}
			}	
		}
	}
	
	private void carve() {
		List<Direction> possibleDirections = Arrays.asList(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
		boolean hasUnvisited = true;
		boolean[][] visitedAreas = new boolean[mazeRepresentation.getSize()][mazeRepresentation.getSize()];
		int currentX = 0;
		int currentZ = 0;
		visitedAreas[currentX][currentZ] = true;
		ArrayList<Point2D> history = new ArrayList(){{
			add(new Point2D.Double(0, 0));
		}};
		while(hasUnvisited) {
			int lastX = 0;
			int lastZ = 0;
			int nextX = currentX;
			int nextZ = currentZ;
			boolean foundGoodLocation = false;
			Collections.shuffle(possibleDirections);
			// Try finding a direction to go to
			for(Direction d : possibleDirections) {
				switch(d) {
					case SOUTH:
						nextX = currentX;
						nextZ = currentZ + 2;
					break;
					case WEST:
						nextX = currentX - 2;
						nextZ = currentZ;
					break;
					case NORTH:
						nextX = currentX;
						nextZ = currentZ - 2;
					break;
					case EAST:
						nextX = currentX + 2;
						nextZ = currentZ;
					break;
				}
				// Check if the current direction was visited before,
				// if not - stop checking other directions and go there
				if (nextX >= 0 && getGlobalX(nextX) < mazeRepresentation.getLocation().getX() + mazeRepresentation.getSize() &&
					nextZ >= 0 && getGlobalZ(nextZ) < mazeRepresentation.getLocation().getZ() + mazeRepresentation.getSize() &&
					!visitedAreas[nextX][nextZ]){
					// Good location found, choose it and end the loop
					foundGoodLocation = true;
					lastX = currentX;
					lastZ = currentZ;
					currentX = nextX;
					currentZ = nextZ;
					break;
				}
			}
			// Check if we have a chosen location, if so - step to it
			if(foundGoodLocation) {
				visitedAreas[currentX][currentZ] = true;
				history.add(new Point2D.Double(currentX, currentZ));
				breakRange(lastX, lastZ, currentX, currentZ);
				if (Main.LOGGING_ENABLED) {
					Bukkit.getLogger().info("Stepped within the maze");
				}
			}
			// If no place was found and there's still a block we can go back to, do so
			else if (history.size() > 0) {
				Point2D lastPosition = history.get(history.size() - 1);
				currentX = (int) Math.floor(lastPosition.getX());
				currentZ = (int) Math.floor(lastPosition.getY());
				history.remove(history.size() - 1);
			}
			// If no direction was found at all, end the generation
			else {
				hasUnvisited = false;
				if (Main.LOGGING_ENABLED) {
					Bukkit.getLogger().info("Done generating maze");
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void breakRange(int fromX, int fromZ, int toX, int toZ) {
		for (int x = Math.min(fromX, toX); x <= Math.max(fromX, toX); x++) {
			for (int z = Math.min(fromZ, toZ); z <= Math.max(fromZ, toZ); z++) {
				Location l = mazeRepresentation.getLocation().clone();
				l.setX(getGlobalX(x));
				l.setZ(getGlobalZ(z));
				for (int y = 0; y <= mazeRepresentation.getHeight(); y++){
					l.setY(Math.floor(mazeRepresentation.getLocation().getY() + y));
					l.getWorld().getBlockAt(l).setType(Material.AIR);
				}
			}
		}
	}
}