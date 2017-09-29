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
 * The WarpStar class creates and positions WarpStar objects and determines
 * if the user is clicking on a warp star in order to travel at warp speed
 * 
 * Bugs: N/A
 * 
 * @author Abby Rechkin and Holly Michalak
 */

public class WarpStar extends java.lang.Object{
private Graphic graphic;
/**
 * Initializes a new WarpStar object to show up at the specified position
 * @param x - is the horizontal position of the newly created warp star.
 * @param y - is the vertical position of the newly created warp star.
 */
public WarpStar(float x, float y){
	graphic = new Graphic("WARP_STAR"); //appearance is changed to "WARP_STAR"
	graphic.setPosition(x, y); //sets position
	graphic.draw();
}
/**
 * Draws this WarpStar object at its current position.
 */
public void update(){
	graphic.draw();
	
}
/**
 * This method detects whether both 1) the player's taxi has fuel, and 2) the 
 * player is clicking on this WarpStar object. When both are detected this 
 * method sets the taxi to travel at warp speed toward this WarpStar.
 * @param taxi - is the taxi that is both checked for fuel, and then set to 
 * travel at warp speed when it has fuel and the player clicks this object.
 */
public void handleNavigation(Taxi taxi){
	//if the taxi has fuel and the player is clicking on a warp star,
	//setWarp is called on the taxi to change the speed to warp speed
	//and the direction to that of the mouse's position
	if(taxi.getFuel()>0 && graphic.isCoveringPosition(GameEngine.getMouseX(), 
			GameEngine.getMouseY()) && GameEngine.isKeyHeld("MOUSE")){
		taxi.setWarp(GameEngine.getMouseX(),GameEngine.getMouseY());
	}
}
}
