package com.daniel.maze;

import java.util.ArrayList;

import org.bukkit.Location;

public class MazeGenerator {
	public ArrayList<MazeRepresentation> mazeRepresentations = new ArrayList<MazeRepresentation>();
	public ArrayList<MazeBuilder> mazeBuilders = new ArrayList<MazeBuilder>();

	public void generate(Location l, int size, int height, int material) {
		MazeRepresentation mazeRepresentation = new MazeRepresentation();
		mazeRepresentation.setLocation(l);
		mazeRepresentation.setSize(size);
		mazeRepresentation.setHeight(height);
		mazeRepresentation.setWallThickness(1);
		mazeRepresentation.setMaterial(material);
		//Creates a Builder for the generation task
		createBuilder(mazeRepresentation);
		mazeRepresentations.add(mazeRepresentation);
	}
	
	public MazeBuilder createBuilder(MazeRepresentation mazeRepresentation) {
		MazeBuilder mazeBuilder = new MazeBuilder(mazeRepresentation);
		mazeBuilders.add(mazeBuilder);
		mazeBuilder.start();
		return mazeBuilder;
	}
}
