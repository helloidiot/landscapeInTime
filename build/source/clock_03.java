import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class clock_03 extends PApplet {

//============================================//

//a representation of time as a landscape
//
//hours determine the sky colour
//minutes determine the shape of the landscape
//seconds determine the colour of the landscape
//which is progressively drawn every second

//============================================//


//constant variables
//setup a float with the current hour
float h = hour();



public void setup(){
  

  //create a colour variable based on the current hour for the sky
  float hour = map(h, 0, 11, 40, 100);
  
  //set background to be representative of the current hour
  background(hour);

  //draw the skylines and random stars
  drawSkylines();
  drawStars();
}

public void draw(){
  //draw the landscape based on current time
  timeLandscape();

  //draw the circle mask
  drawCircleMask();
}


public void drawCircleMask(){
  PGraphics circleMask = createGraphics(width, height, P2D);

  // begin the shape
  circleMask.beginDraw();

  //black background
  circleMask.background(0);

  // white fill so I can multiply
  circleMask.fill(255);

  //draw the ellipse for the mask
  circleMask.ellipse(width/2, height/2, width/1.3f, height/1.3f);

  //end the shape
  circleMask.endDraw();

  //blend using multiply blend mode
  blend(circleMask,0,0,width,height,0,0,width,height,MULTIPLY);
}

public void timeLandscape(){
  //set inital minute / second variables
  float s = second();
  float m = minute();

  //map noise scale to minutes
  float mins = map(m, 0, 59, 0.01f, 0.02f);

  //map seconds to greyscale colour
  float grey = map(s, 0, 59, 0, 200);

  //translate in order to position the landscape in the centre
  pushMatrix();
  translate(0, height/2.5f);
  stroke(s);

  //draw a series of noise lines based on time
  for (int i = 0; i < width; i++)
  {
      //create a perlin noise value from current minute
      float noiseVal = noise((mins + i) * mins, s * mins);

      //and map the seconds to a wider range to draw further
      float secondDist = map(s, 0, 59, 0, 100);

      //set the stroke to mapped seconds value with 50% alpha
      stroke(grey, 50);

      //draw a new line every second
      line(i, secondDist + noiseVal * 100, i + secondDist, height);
  }

  //end translation
  popMatrix();
}

public void drawSkylines(){

  //series of horizontal lines across the canvas
  for (int i = 0; i < height; i = i + 5){
    line(0, i, width, i);
  }
}

public void drawStars(){

  strokeWeight(3);

  //stars
  stroke(255);
  for (float i = 0; i < width; i = i + random(0, 100)){
    for (float j = 0; j < height; j = j + random(0, 100)){
      point(i, j);
    }
  }

  //dots to break up the lines for texture
  strokeWeight(1);
  for (float i = 0; i < width; i = i + 10){
    for (float j = 0; j < height; j = j + 10){
      //stroke(random(0, 60));
      point(i, j);
    }
  }
}
  public void settings() {  size(800, 800, P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "clock_03" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
