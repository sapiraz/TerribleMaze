package com.daniel.maze;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class Main extends JavaPlugin {
	public MazeGenerator mazeGenerator;
	public Listener breakListener;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		mazeGenerator = new MazeGenerator();
		new BreakListener(this, mazeGenerator);
		Bukkit.broadcastMessage("HE BROKE< CALL THE POLICE!!!");
	}

	public static void main(String[] args) {

	}

}
