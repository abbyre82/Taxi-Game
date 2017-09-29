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
 * The Planet class creates and positions Planet objects ensuring that they 
 * don't overlap. It also sets the destination planet and checks to see if the
 * taxi has landed on it successfully or has crashed
 *
 * Bugs: IndexOutofBoundsException for planets array
 * 
 * @author Abby Rechkin and Holly Michalak
 */
import java.util.Random;
import java.util.ArrayList;

public class Planet {
	
	private Graphic graphic; //planet graphic
	private boolean isDestination; //whether or not the planet is the 
	//destination
	
	/**
	 * The Planet constructor creates a new graphic with the appearance 
	 * of "PLANET" and sets its position. This is used in the loadCustomLevel 
	 * method.
	 * @param x is the X position
	 * @param y is the Y position
	 */
	public Planet(float x, float y){
		graphic = new Graphic("PLANET"); //sets appearance of graphic to
		//"PLANET"
		graphic.setPosition( x,  y); //sets position
		graphic.draw(); //draws graphic onto the screen
	}
	
	/**
	 * The overloaded Planet constructor creates new graphics and randomly
	 * positions them on the screen without any graphics overlapping. This is 
	 * only used in the loadRandomLevel method.
	 * @param rng is a random number generator
	 * @param planets is the array that holds all of the planets
	 */
	public Planet(Random rng, ArrayList<Planet> planets){
		

		graphic = new Graphic("PLANET"); //new graphic with appearance of 
		//"PLANET"
		//randomly positions the graphic
		graphic.setPosition(rng.nextFloat()*GameEngine.getWidth(),
				rng.nextFloat()*GameEngine.getHeight());
		//checks to make sure it is not overlapping with any another 
		//planet in the array
		for(int j =0; j<planets.size(); j++){
			//while the graphic is overlapping with other planets,
			//the position is reset
			while(graphic.isCollidingWith(planets.get(j).graphic)){
				graphic.setPosition(rng.nextFloat()*GameEngine.getWidth(),
					rng.nextFloat()*GameEngine.getHeight());
				j=0; //resets the index in the loop
			}
		}
		graphic.draw();	
		
	}
	/**
	 * This method set the current planet to either be the current destination 
	 * or not, and updates the appearance of it's graphic accordingly
	 * @param is true when this planet is being marked as the current 
	 * destination, and false when it is being un-marked or returned to its 
	 * status as a normal planet
	 */
	public void setDestination(boolean isDestination){
		//if the planet is the destination, its appearance is changed to
		//that of destination planet
		if(isDestination == true){
			this.isDestination = true;
			graphic.setAppearance("DESTINATION");
		}
		//when it is no longer the destination, its appearance is set
		//back to that of a regular planet
		else{
			this.isDestination = false;
			graphic.setAppearance("PLANET");
		}		
	}
	/**
	 * This method detects and handles collisions between taxis and planets 
	 * that result in either crashing: when traveling at warp speed, or in 
	 * reaching a destination and progressing through the current level.
	 * @param taxi - the taxi that might be colliding with this planet
	 * @return
	 */
	public boolean handleLanding(Taxi taxi){
		//when the taxi collides with a planet while travelling at warp,
		//the taxi crash method is called
		if(taxi.checkCollision(graphic) && taxi.isTravellingAtWarp()){
			taxi.crash();
		}
		//when the taxi lands on the destination planet while not
		//travelling at warp, the method returns true
		if(taxi.checkCollision(graphic) && !(taxi.isTravellingAtWarp()) &&
				this.isDestination == true){
			return true;
		}
		//otherwise, it returns false
		else {
			return false;
		}
	}
	/**
	 * This method simply draws the current planet at it's current position.
	 * @param time - is the number of milliseconds that have elapsed
	 * since the last update
	 */
	public void update(int time){
		graphic.draw();
		
	}

}
