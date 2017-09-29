//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:            Program3
// Files:            Taxi.java, Level.java, Planet.java, WarpStar.java,
//					GasCloud.java
// Semester:         Spring 2017
//
// Author:           Abby Rechkin
// Email:            rechkin@wisc.edu
// CS Login:         rechkin
// Lecturer's Name:  Gary Dahl
// Lab Section:      332
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:     Holly Michalak
// Partner Email:    hmichalak@wisc.edu
// Partner CS Login: michalak
// Lecturer's Name:  Gary Dahl
// Lab Section:      332
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//    _X__ Write-up states that Pair Programming is allowed for this assignment.
//    _X__ We have both read the CS302 Pair Programming policy.
//    _X__ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.
//
// Persons:          Chrissy R, peer in our lab
// Online Sources:   N/A
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
/**
 * This Level class is responsible for managing all of the objects in your game
 * The GameEngine creates a new Level object for each level, and then calls 
 * that Level object's update() method repeatedly until it returns either 
 * "ADVANCE" (to proceed to the next level) or "QUIT" (to end the entire game).
 * <br/><br/>
 * This class should contain and make use of the following private fields:
 * <tt><ul>
 * <li>private Random rng</li>
 * <li>private Taxi taxi</li>
 * <li>private ArrayList<WarpStar> warpStars</li>
 * <li>private ArrayList<GasCloud> gasClouds</li>
 * <li>private ArrayList<Planet> planets</li>
 * <li>private int destinationPlanetIndex</li>
 * </ul></tt>
 * 
 * Bugs: Reading the custom level files
 * 
 * @author Abby Rechkin and Holly Michalak
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;


public class Level
{	
	
	 
	private ArrayList<WarpStar> warpStars; //warpStars array
	private Taxi taxi; //Taxi object reference
	private ArrayList<GasCloud> gasClouds; //gasClouds array
	private ArrayList<Planet> planets; //planets array
	private int destinationPlanetIndex; //marks the destination planet
	private Random rng; //random number generator
	/**
	 * This constructor initializes a new level object, so that the GameEngine
	 * can begin calling its update() method to advance the game's play.  In
	 * the process of this initialization, all of the objects in the current
	 * level should be instantiated and initialized to their starting states.
	 * @param rng is the ONLY Random number generator that should be used by 
	 * throughout this level and by any of the objects within it.
	 * @param levelFilename is either null (when a random level should be 
	 * loaded) or a reference to the custom level file that should be loaded.
	 * @throws FileNotFoundException 
	 */
	public Level(Random rng, String levelFilename)
	{ 
		this.rng = rng; //rng field is set equal to rng parameter
		//calls on loadCustomLevel if levelFilename is not null
		if(levelFilename != null){
			//calls on loadRandomLevel if loadCustomLevel returns false
			if(!(loadCustomLevel(levelFilename))){
				loadRandomLevel();
			}
		}
		//if the levelFilename is null, then it calls on loadRandomLevel
		else{
			loadRandomLevel();
		}	
	}

	/**
	 * The GameEngine calls this method repeatedly to update all of the objects
	 * within your game, and to enforce all of your game's rules.
	 * @param time is the time in milliseconds that have elapsed since the last
	 * time this method was called (or your constructor was called). This can 
	 * be used to help control the speed of moving objects within your game.
	 * @return "CONTINUE", "ADVANCE", or "QUIT".  When this method returns
	 * "CONTINUE" the GameEngine will continue to play your game by repeatedly
	 * calling it's update() method.  Returning "ADVANCE" instructs the 
	 * GameEngine to end the current level, create a new level, and to start
	 * updating that new level object instead of the current one. Returning 
	 * "QUIT" instructs the GameEngine to end the entire game.  In the case of
	 * either "QUIT" or "ADVANCE" being returned, the GameEngine presents a
	 * short pause and transition message to help the player notice the change.
	 */
	public String update(int time) 
	{
		//when SPACE key is pressed, the game ends
		if(taxi.update(time)){
			return "QUIT";
		}
		//updates all warp stars in array and checks to see whether the player
		//is clicking on a warp star
		for(int i =0; i<warpStars.size(); i++){
			warpStars.get(i).update();
			warpStars.get(i).handleNavigation(taxi);
		}
		//updates all gas clouds in array and checks to see if the taxi has run
		//into them
		for(int i =0; i<gasClouds.size(); i++){
			gasClouds.get(i).update(time);
			gasClouds.get(i).handleFueling(taxi);
		}
		//if the taxi runs into a gas cloud, it is removed	
			for(int j=gasClouds.size() - 1;j>= 0;j--){
			if(gasClouds.get(j).shouldRemove() == true){
				gasClouds.remove(j);
			}
		}
		//loops through and updates all planets in array 
		for(int k=0; k < planets.size(); k++){
			planets.get(k).update(time);
			//checks to see if the current planet is the destination planet
			if(k == this.destinationPlanetIndex){
				planets.get(k).setDestination(true);
			}
			//if the taxi lands on the planet successfully, the destination
			//planet is changed to the next planet in the array
			if(planets.get(k).handleLanding(taxi) && !(taxi.hasCrashed())){
				this.destinationPlanetIndex++;
				planets.get(k).setDestination(false);
			}		
		}
		//if the taxi successfully lands on all of the destination planets 
		//in order, the game advances to the next level
		if(this.destinationPlanetIndex == planets.size()){
			return "ADVANCE";
		}
		//game continues to update
		return "CONTINUE";
	}	

	/**
	 * This method returns a string of text that will be displayed in the upper
	 * left hand corner of the game window.  Ultimately this text should convey
	 * the taxi's fuel level, and their progress through the destinations.
	 * However, this may also be useful for temporarily displaying messages
	 * that help you to debug your game.
	 * @return a string of text to be displayed in the upper-left hand corner
	 * of the screen by the GameEngine.
	 */
	public String getHUDMessage() 
	{
		//message displayed when taxi runs out of fuel
		if(taxi.getFuel()<=0){
			return "You've run out of fuel!\nPress the SPACEBAR to end this "
					+ "game.";
		}
		//message displayed when taxi crashes into a planet
		if(taxi.hasCrashed()){
			return "You've crashed into a planet!\nPress the SPACEBAR to end "
					+ "this game.";
		}
		//fuel and fares are always displayed
		return "Fuel: " + String.format("%.1f",taxi.getFuel()) +
			 "\nFares: " + (destinationPlanetIndex) + "/" + planets.size();
	}

	/**
	 * This method initializes the current level to contain a single taxi in 
	 * the center of the screen, along with 6 randomly positioned objects of 
	 * each of the following types: warp stars, gasClouds, and planets.
	 */
	private void loadRandomLevel() 
	{ 
		//destinationPlanetIndex begins at 0
		this.destinationPlanetIndex = 0;
		//taxi in the center of the screen
		taxi = new Taxi(400.0f, 300.0f);
		//creates and randomly positions 6 warp stars onto the screen
		warpStars = new ArrayList<WarpStar>(6);
		for(int i = 0; i<6;i++){
			warpStars.add(new WarpStar(rng.nextFloat() * GameEngine.getWidth(), 
					rng.nextFloat() * GameEngine.getHeight()));	
		}
		//creates and randomly positions 6 gas clouds onto the screen
		gasClouds = new ArrayList<GasCloud>(6);
		for(int i=0; i<6; i++){
			gasClouds.add(new GasCloud(rng.nextFloat() * GameEngine.getWidth(), 
					rng.nextFloat() * GameEngine.getHeight(),0));
		}
		//creates 6 planets and positions them so they do not overlap
		planets = new ArrayList<Planet>(6);
		for(int i=0; i<6; i++){
			planets.add(new Planet(rng, planets));
		}
		//sets the first planet in the array to the destination planet
		planets.get(0).setDestination(true);
	}

	/**
	 * This method initializes the current level to contain each of the objects
	 * described in the lines of text from the specified file.  Each line in
	 * this file contains the type of an object followed by the position that
	 * it should be initialized to start the level.
	 * @param levelFilename is the name of the file (relative to the current
	 * working directory) that these object types and positions are loaded from
	 * @return true after the specified file's contents are successfully loaded
	 * and false whenever any problems are encountered related to this loading
	 * @throws FileNotFoundException 
	 */
	private boolean loadCustomLevel(String levelFilename)
	{ 
		//destinationPlanetIndex begins at 0
		this.destinationPlanetIndex = 0;
		warpStars = new ArrayList<WarpStar>(6);
		gasClouds = new ArrayList<GasCloud>(6); 
		planets = new ArrayList<Planet>(6);
		//a new file object is created
		File inFile = new File(levelFilename);
		//new scanner to read file
		Scanner input = null;
		//reads through the file, assigns positions to all objects in game, 
		//and catches any exceptions
		try{
			input = new Scanner(inFile);	
			while(input.hasNextLine()){ //continues to read until there isn't
				//any content
			switch( input.next()){ //reads the first string in the file and the
			//corresponding case creates and assigns the position of the 
			//appropriate object
			case "GAS": 
				input.next(); //skips over "@"
				String gasX= input.next(); //the X position
				gasX = gasX.substring(0,gasX.length() -1).trim(); //removes the
				//"," and any spaces in the X position string
				float gasWidth= Float.parseFloat(gasX); //converts the X 
				//position into a float
				String gasY = input.next(); //the Y position
				float gasHeight = Float.parseFloat(gasY); //converts the Y 
				//position string into a float
				gasClouds.add(new GasCloud(gasWidth,gasHeight,0)); //adds a new
				//GasCloud object with the above position coordinates
				break;
			case "PLANET":
				input.next();
				String planetX = input.next();
				planetX = planetX.substring(0,planetX.length() -1).trim();
				float planetWidth = Float.parseFloat(planetX);
				String planetY = input.next();
				float planetHeight = Float.parseFloat(planetY);
				planets.add(new Planet(planetWidth,planetHeight));
				break;
			case "WARP_STAR":
				input.next();
				String warpStarX = input.next();
				warpStarX = warpStarX.substring(0,warpStarX.length()-1).trim();
				float warpStarWidth = Float.parseFloat(warpStarX);
				String warpStarY = input.next();
				float warpStarHeight = Float.parseFloat(warpStarY);
				warpStars.add(new WarpStar(warpStarWidth,warpStarHeight));
				break;
			case "TAXI":
				input.next();
				String taxiX = input.next();
				taxiX = taxiX.substring(0,taxiX.length()-1).trim();
				float taxiWidth = Float.parseFloat(taxiX);
				String taxiY = input.next();
				float taxiHeight = Float.parseFloat(taxiY);
				taxi = new Taxi(taxiWidth,taxiHeight); //creates a new Taxi 
				//object and assigns the above coordinates
				break;
			}
			}
			planets.get(0).setDestination(true); //set the first planet in
			//the array as the destination planet
			input.close(); //close the scanner
		} 
		//method returns false if an exception is thrown except
		//if it is a NoSuchElementException
		catch(FileNotFoundException e){
			return false;
		} catch(IOException e){
			return false;
		} catch(InputMismatchException e){
			return false;
		} catch(NullPointerException e){
			return false;
		} catch(NoSuchElementException e){
			return true;
		} 
		return true;
		
	}

	/**
	 * This method creates and runs a new GameEngine with its first Level. Any
	 * command line arguments passed into this program are treated as a list of
	 * custom level filenames that should be played in order by the player. 
	 * @param args is the sequence of custom level filenames to play through
	 */
	public static void main(String[] args)
	{
		
		GameEngine.start(null,args); //runs the game
		
		 
	} 
}
