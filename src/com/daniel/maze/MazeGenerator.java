package com.daniel.maze;

import java.util.ArrayList;

import org.bukkit.Location;

public class MazeGenerator {
	public ArrayList<MazeRepresentation> mazeRepresentations = new ArrayList<MazeRepresentation>();
	public ArrayList<MazeBuilder> mazeBuilders = new ArrayList<MazeBuilder>();

	public void generate(Location l) {
		MazeRepresentation mazeRepresentation = new MazeRepresentation();
		mazeRepresentation.setLocation(l);
		mazeRepresentation.setSize(10);
		mazeRepresentation.setHeight(5);
		mazeRepresentation.setWallThickness(1);
		//Creates a Builder for the generation task
		createBuilder(mazeRepresentation);
		mazeRepresentations.add(mazeRepresentation);
	}
	
	public MazeBuilder createBuilder(MazeRepresentation mazeRepresentation) {
		MazeBuilder mazeBuilder = new MazeBuilder(mazeRepresentation);
		mazeBuilders.add(mazeBuilder);
		return mazeBuilder;
	}
}
