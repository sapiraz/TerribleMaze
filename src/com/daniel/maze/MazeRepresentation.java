package com.daniel.maze;

import org.bukkit.Location;

public class MazeRepresentation {
	public Location location;
	public int size;
	public int height;
	public MazeBuilder builder;

	public int getWallThickness() {
		return wallThickness;
	}

	public void setWallThickness(int wallThickness) {
		this.wallThickness = wallThickness;
	}

	public int wallThickness;
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
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
}
