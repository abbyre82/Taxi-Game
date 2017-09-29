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
 * The GasCloud class creates, determines the position, and rotates
 * the graphics of GasCloud objects.
 * It checks whether the taxi collides with the gas clouds and handles fueling 
 * accordingly. 
 *
 * Bugs: Removing and rotating gas cloud graphics 
 * 
 * @author Abby Rechkin and Holly Michalak
 */
public class GasCloud {
	
private Graphic graphic; //gas cloud graphic
private float rotationSpeed; //speed at gas cloud rotates
private boolean shouldRemove; //whether the gas cloud should be removed from
//the array

/**
 * This constructor initializes a new graphic and sets the appearance to "GAS".
 * It also sets the position of the cloud on the screen and direction the 
 * gas cloud is facing.
 * @param x is the X position
 * @param y is the Y position
 * @param direction is the angular direction the graphic is turned to
 */
public GasCloud(float x, float y, float direction){
	this.shouldRemove = false; //initially the gas cloud is not removed
	graphic = new Graphic("GAS"); //new graphic with appearance "GAS"
	graphic.setPosition(x, y); //sets position of gas cloud
	graphic.setDirection(direction); //sets direction of gas cloud
	graphic.draw(); //draws gas cloud on the screen
}
/**
 * The update method is continually drawing the graphic as its direction is
 * updated. 
 * @param time
 */
public void update(int time){
	graphic.draw();
	graphic.setDirection(graphic.getDirection() + (0.001f * time));	//gas cloud 
	//turns counterclockwise 
	
}
/**
 * The shouldRemove method returns the instance field shouldRemove
 * @return
 */
public boolean shouldRemove(){
	return this.shouldRemove;
}
/**
 * The handleFueling method checks to see if the Taxi object is colliding with 
 * the gas cloud and updates the fuel and shouldRemove instance fields 
 * accordingly.
 * @param taxi
 */
public void handleFueling(Taxi taxi){
	//if the taxi is colliding with the gas cloud, fuel is added and the
	//gas cloud is removed
	if(taxi.checkCollision(graphic)){
		taxi.addFuel(20);
		this.shouldRemove = true;
	}
}
}
