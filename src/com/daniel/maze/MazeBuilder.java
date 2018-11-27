package com.daniel.maze;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class MazeBuilder {
	private MazeRepresentation mazeRepresentation;
	
	public MazeBuilder(MazeRepresentation mazeRepresentation) {
		this.mazeRepresentation = mazeRepresentation;
		this.fillSpace();
		this.carve();
		
	}
	private int getWorldLocationX(int localX) {
		return (localX + (mazeRepresentation.getWallThickness() * localX)) + (int) Math.floor(mazeRepresentation.getLocation().getX());
	}

	private int getWorldLocationY(int localY) {
		return localY + (int) Math.floor(mazeRepresentation.getLocation().getY());
	}

	private int getWorldLocationZ(int localZ) {
		return (localZ + (mazeRepresentation.wallThickness * localZ)) + (int) Math.floor(mazeRepresentation.getLocation().getZ());
	}

	private void fillSpace() {
		Location location = mazeRepresentation.getLocation();
		World world = location.getWorld();
		//Set blocks.
		for(int x = 0; x < mazeRepresentation.getSize(); x++) {
			for(int z = 0; z < mazeRepresentation.getSize(); z++) {
				for(int y = 0; y < mazeRepresentation.getHeight(); y++) {
					Location blockLocation = new Location(world, location.getX() + x, location.getY() + y, location.getZ() + z);
					world.getBlockAt(blockLocation).setType(Material.COBBLESTONE);
				}
			}	
		}
	}
	
	private void carve() {
		Location Mazelocation = mazeRepresentation.getLocation();
		World world = Mazelocation.getWorld();
		List<Direction> possibleDirections = Arrays.asList(Direction.FORWARD, Direction.RIGHT, Direction.BACKWARD, Direction.LEFT);
		int currentX = 0;
		int currentZ = 0;
//		Location location = new Location(world, currentX, currentY, currentZ);
		boolean hasUnvisited = true;
		boolean[][] visitedAreas = new boolean[mazeRepresentation.getSize()][mazeRepresentation.getSize()];
//		for (int i = 0; i < mazeRepresentation.getHeight(); i++){
//			Location verticalLocation = location.clone();
//			verticalLocation.setY(verticalLocation.getY() + i);
//			world.getBlockAt(verticalLocation).setType(Material.AIR);
//		}
		visitedAreas[currentX][currentZ] = true;
		int counter = 0;
		while(hasUnvisited) {
//			Bukkit.broadcastMessage("I love you a lot");
			int x = currentX;
			int z = currentZ;
			int lastX = 0;
			int lastZ = 0;
//			Collections.shuffle(possibleDirections);
			boolean foundGoodLocation = false;
			// Try finding a direction to go to
			for(Direction d : possibleDirections) {
				switch(d) {
					case FORWARD:
						x = currentX;
						z = currentZ + 1;
					break;
					case RIGHT:
						x = currentX + 1;
						z = currentZ;
					break;
					case BACKWARD:
						x = currentX;
						z = currentZ - 1;
					break;
					case LEFT:
						x = currentX - 1;
						z = currentZ;
					break;
				}
//				 Check if the current direction was visited before,
//				 if not - stop checking other directions and go there
				if (x >= 0 && getWorldLocationX(x) < mazeRepresentation.getLocation().getX() + mazeRepresentation.getSize() &&
					z >= 0 && getWorldLocationZ(z) < mazeRepresentation.getLocation().getZ() + mazeRepresentation.getSize() &&
					!visitedAreas[x][z]){
					foundGoodLocation = true;
					//good location, set to it.
//					chosenLocation = new Location(world, x, currentY, z);
					lastX = currentX;
					lastZ = currentZ;
					currentX = x;
					currentZ = z;
//					Bukkit.broadcastMessage("Making a step towards: " + d);
					break;
				}
			}
			// Check if we have a chosen location, if so - step to it
			if(foundGoodLocation && counter < 15) {
				counter++;
				// world.getBlockAt(chosenLocation).setType(Material.AIR);
				visitedAreas[currentX][currentZ] = true;
				breakRange(lastX, lastZ, currentX, currentZ);
				Bukkit.broadcastMessage("ok");
				//TODO: remove next line
//				hasUnvisited = false;
			}
			else {
				hasUnvisited = false;
				Bukkit.broadcastMessage("done");
			}
			// Check for unvisited area.
			//TODO: randomize step location and check if it's unvisited.
			// If no unvisited areas left, end loop.
			// If unvisited: carve to block, set current location and mark visited.

		}
		
	}
	private void breakRange(int fromX, int fromZ, int toX, int toZ){
//		World world = mazeRepresentation.getLocation().getWorld();
//		Location l = mazeRepresentation.getLocation().clone();
//		if (fromX != toX) {
//			Bukkit.broadcastMessage("digging X");
//			for (int x = 0; x < toX; x++){
//				l.setX(getWorldLocationX(x));
//			world.getBlockAt(l).setType(Material.AIR);
//				for (int y = 0; y <= mazeRepresentation.getHeight(); y++){
//					Location yLocation = l.clone();
//					yLocation.setY(Math.floor(yLocation.getY() + y));
//					yLocation.getWorld().getBlockAt(yLocation).setType(Material.AIR);
//				}
//			}
//		}
//		else {
//			Bukkit.broadcastMessage("digging Z");
//			for (int z = 0; z < toX; z++){
//				l.setZ(getWorldLocationX(z));
//				world.getBlockAt(l).setType(Material.AIR);
//				for (int y = 0; y <= mazeRepresentation.getHeight(); y++){
//					Location yLocation = l.clone();
//					yLocation.setY(Math.floor(yLocation.getY() + y));
//					yLocation.getWorld().getBlockAt(yLocation).setType(Material.AIR);
//				}
//			}
//		}

		if (fromX == toX) {
			int zDistance = toZ - fromZ;
			Bukkit.broadcastMessage("D: " + zDistance);
			int realWorldFromZ = getWorldLocationZ(fromZ);
			int wallThickness = mazeRepresentation.getWallThickness();
			int realWorldToZ = realWorldFromZ + zDistance + (zDistance * wallThickness);
			for (int z = realWorldFromZ; z <= realWorldToZ; z++) {
				Location l = mazeRepresentation.getLocation().clone();
				l.setX(getWorldLocationX(fromX));
				l.setZ(z);
				for (int y = 0; y <= mazeRepresentation.getHeight(); y++){
					Location yLocation = l.clone();
					yLocation.setY(Math.floor(yLocation.getY() + y));
//					Bukkit.broadcastMessage("1. Breaking a block on: " + (yLocation.toString()));
					yLocation.getWorld().getBlockAt(yLocation).setType(Material.AIR);
				}
			}
		}
		else {
			int xDistance = toX - fromX;
			int realWorldFromX = getWorldLocationX(fromX);
			int wallThickness = mazeRepresentation.getWallThickness();
			int realWorldToX = realWorldFromX + xDistance + (xDistance * wallThickness);
			for (int x = realWorldFromX; x <= realWorldToX; x++) {
				Location l = mazeRepresentation.getLocation().clone();
				l.setZ(getWorldLocationZ(fromZ));
				l.setX(x);
				for (int y = 0; y <= mazeRepresentation.getHeight(); y++){
					Location yLocation = l.clone();
					yLocation.setY(Math.floor(yLocation.getY() + y));
//					Bukkit.broadcastMessage("2. Breaking a block on: " + (yLocation.toString()));
					yLocation.getWorld().getBlockAt(yLocation).setType(Material.AIR);
				}
			}
		}
	}
}