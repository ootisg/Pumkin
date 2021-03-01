package main;

import gameObjects.GlobalSave;
import gui.TitleScreen;
import music.MusicPlayer;

public class GameCode extends GameAPI {
	private GameWindow gameWindow;
	public void initialize () {
		//Set the save file path
		getSave ().setFile ("saves/save.txt");
		//Create the global save data
		new GlobalSave ().declare (0, 0);
		//Make the music player so music can be played
		new MusicPlayer ();
		MainLoop.getWindow ().setResolution (256, 144);
		MainLoop.getWindow ().setSize (1024, 576);
		
		//Start up the title screen
		new TitleScreen ().declare (0, 0);
		
	}
	public void gameLoop () {
		//Saveable.printSaves ();
		//Runs once per frame
	}
}