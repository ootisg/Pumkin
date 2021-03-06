package main;

import java.io.FileNotFoundException;

import gameObjects.GlobalSave;
import gui.MapScreen;
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
		
		//Make the gui components
		getGui ().addComponent (new TitleScreen ());
		
		//Load the room
		try {
			getRoom ().loadRMF ("resources/maps/map_1.rmf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void gameLoop () {
		getRoom ().frameEvent ();
		//Saveable.printSaves ();
		//Runs once per frame
	}
}