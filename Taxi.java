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
 * The Taxi class creates and sets the position of a Taxi object. It allows the
 * taxi to move, determines its speed, and whether it has crashed. 
 *
 * Bugs: Setting direction of the taxi correctly
 * 
 * @author Abby Rechkin and Holly Michalak
 */

public class Taxi extends java.lang.Object{
private Graphic graphic;
private float thrusterSpeed;
private float fuel;
private float warpSpeed; 
private boolean isTravellingAtWarp; 
private boolean hasCrashed;


/**
 * Initialized all fields of the new taxi object so that it will appear 
 * at the specified position.
 * @param x - specifies the initial horizontal position for this new object
 * @param y - specifies the initial vertical position for this new object
 */
public Taxi(float x, float y){
	this.fuel = 30.0f; //amount of fuel 
	this.thrusterSpeed = 0.01f; //thruster speed of taxi
	this.warpSpeed = 0.2f; //speed of taxi when player clicks on warp star
	this.isTravellingAtWarp = false; //initially not travelling at warp speed
	this.hasCrashed = false; //has not crashed initially
	graphic = new Graphic("TAXI"); //appearance of taxi graphic
	graphic.setPosition(x,y); //sets position on screen of graphic
	graphic.draw();
}
/**
 * This method is primarily responsible for updating the taxi's position 
 * so that the move appropriately: using thrusters or warp star travel. 
 * When a taxi moves off an edge of the screen, they should simultaneously 
 * enter from the opposite screen edge. The a taxi is either out of fuel or 
 * has crashed into a planet, they should not move at all.
 * @param time - in milliseconds is used to move taxi at the correct speed
 * @return
 */
public boolean update(int time){
	graphic.draw();
	//taxi can only move when it has fuel and has not crashed
	if(this.fuel>0 && this.hasCrashed == false){
		//taxi turns and moves to the right when the "D" key or the right arrow
		//key are held
		if(GameEngine.isKeyHeld("D")||GameEngine.isKeyHeld("RIGHT")){
			graphic.setX(graphic.getX() + (thrusterSpeed * time));
			graphic.setDirection(0);
			this.fuel = this.fuel - thrusterSpeed * time; //loses fuel
			isTravellingAtWarp = false; //is travelling at thruster speed
		}
	//taxi turns and moves to the left when the "A" key or the left arrow
	//key are held
		if(GameEngine.isKeyHeld("A")||GameEngine.isKeyHeld("LEFT")){
			graphic.setX(graphic.getX() - (thrusterSpeed * time));
			graphic.setDirection((float)Math.PI); //setDirection takes float
			this.fuel = this.fuel - thrusterSpeed * time;
			isTravellingAtWarp = false;
		}
	//taxi turns and moves upwards when the "W" or the up arrow keys
	//are held
		if(GameEngine.isKeyHeld("W")||GameEngine.isKeyHeld("UP")){
			graphic.setY(graphic.getY() - (thrusterSpeed * time));
			graphic.setDirection((float)(Math.PI/2));
			this.fuel = this.fuel - thrusterSpeed * time;
			isTravellingAtWarp = false;
		}
	//taxi turns and moves downwards when the "S" or the down arrow keys
	//are held
		if(GameEngine.isKeyHeld("S")||GameEngine.isKeyHeld("DOWN")){
			graphic.setY(graphic.getY() + (thrusterSpeed * time));
			graphic.setDirection((float)(3*Math.PI/2));
			this.fuel = this.fuel - thrusterSpeed * time;
			isTravellingAtWarp = false;
		}
	//if isTravellingAtWarp() returns true, taxi moves at warp speed
		if(isTravellingAtWarp()){
			graphic.setX(graphic.getX() + 
					(warpSpeed * graphic.getDirectionX() * time));
			graphic.setY(graphic.getY() + 
					(warpSpeed * graphic.getDirectionY() * time));
		
		}
	//if the taxi moves off the screen, it is repositioned on the opposite 
	//side of the screen 
		if(graphic.getX() > GameEngine.getWidth()){
			graphic.setPosition(0, (GameEngine.getHeight() - graphic.getY()));
		}
		if(graphic.getY() > GameEngine.getHeight()){
			graphic.setPosition((GameEngine.getWidth() - graphic.getX()), 0);
		}
		if(graphic.getY() < 0){
			graphic.setPosition(GameEngine.getWidth() - graphic.getX(),
				GameEngine.getHeight());
		}
		if(graphic.getX() < 0){
			graphic.setPosition(GameEngine.getWidth(), 
				GameEngine.getHeight() - graphic.getY());
		}
	}
	//if the taxi has crashed or run out of fuel, once the SPACE key is pressed,
	//the update method returns true and the game ends
	else{
		if(GameEngine.isKeyPressed("SPACE")){
		return true;
		}
	}
	//the game continues
	return false;
	
}
/**
 * Determines whether this taxi object's graphic is overlapping with the 
 * graphic of another object in the game.
 * @param other - is the graphic to check for a collision against
 * @return
 */
public boolean checkCollision(Graphic other){
	//if the graphic is colliding with another graphic in the game,
	//the method returns true
	if(graphic.isCollidingWith(other)){
		return true;
	}
	else{
	return false; 
	}
}
/**
 * This method increments a taxi's fuel level by the specified amount
 * @param fuel - is the amount that should be added to this taxi's fuel
 */
public void addFuel(float fuel){
	//adds fuel to by a specific amount
	this.fuel = this.fuel + fuel;
}
/**
 * This accessor method retrieves a taxi object's fuel level
 * @return
 */
public float getFuel(){
	return this.fuel;
}
/**
 * Mutates the state of this taxi to begin traveling at warp speed
 * in the direction of the specified position.
 * @param x - is the horizontal coordinate to move at warp speed toward
 * @param y - is the vertical coordinate to move at warp speed toward
 */
public void setWarp(float x, float y) {
	this.isTravellingAtWarp = true; //allows the taxi to begin to travel at
	//warp speed
	graphic.setDirection(x,y);//the direction of the taxi is changed to that
	//of the warp star the player is clicking on 
	}
/**
 * This accessor retrieves whether this taxi is travelling at warp speed.
 * @return
 */
public boolean isTravellingAtWarp() { 
	return this.isTravellingAtWarp;
	}
/**
 * This method changes the appearance of this taxi's graphic to EXPLOSION
 * and also changes this object's state to be crashed which effects the ship's 
 * movement among other things.
 * @return
 */
public boolean hasCrashed(){
	return this.hasCrashed;
}
/**
 * This accessor retrieves whether this taxi has crashed into a planet.
 * @return
 */
public boolean crash(){
	//when the taxi has crashed, its appearance is set to "EXPLOSION"
	graphic.setAppearance("EXPLOSION");
	this.hasCrashed = true; 
	return this.hasCrashed;
	
}
}
