package com.daniel.maze;

import org.bukkit.Location;

public class MazeRepresentation {
	private Location location;
	private int size;
	private int height;
	private int material;
	private int wallThickness;
	private MazeBuilder builder;

	public int getWallThickness() { return wallThickness; }

	public void setWallThickness(int wallThickness) { this.wallThickness = wallThickness; }

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) { this.size = size; }

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public MazeBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(MazeBuilder builder) {
		this.builder = builder;
	}

	public int getMaterial() { return material; }

	public void setMaterial(int material) { this.material = material; }
}
