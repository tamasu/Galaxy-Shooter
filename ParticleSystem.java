package com.tubigames.galaxy.shooter.hd;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;

public class ParticleSystem {

	final float hd = OpenGLRenderer.hd;
	
    final int MAX_PARTICLES= 100;
    boolean	rainbow=true;				// Rainbow Mode?
    boolean	sp;							// Spacebar Pressed?
    boolean	rp;							// Enter Key Pressed?
    float	slowdown=2.0f;				// Slow Down Particles
    float	xspeed;						// Base X Speed (To Allow Keyboard Direction Of Tail)
    float	yspeed;						// Base Y Speed (To Allow Keyboard Direction Of Tail)
    float	zoom=0.0f;				// Used To Zoom Out
    int	loop;						// Misc Loop Variable
    int	col;						// Current Color Selection
    int	delay;						// Rainbow Effect Delay

    float prevX;
    float prevY;
    
    class particles
    {
       boolean	active;					// Active (Yes/No)
       float	life;					// Particle Life
       float	fade;					// Fade Speed
       float	r;						// Red Value
       float	g;						// Green Value
       float	b;						// Blue Value
       float	x;						// X Position
       float	y;						// Y Position
       float	z;						// Z Position
       float	xi;						// X Direction
       float	yi;						// Y Direction
       float	zi;						// Z Direction
       float	xg;						// X Gravity
       float	yg;						// Y Gravity
       float	zg;						// Z Gravity
    }

    particles[] particle=new particles[MAX_PARTICLES];
    
    public int setting = 0;
    
    float colors[][]=
    {
       {1.0f,0.5f,0.5f},{1.0f,0.75f,0.5f},{1.0f,1.0f,0.5f},{0.75f,1.0f,0.5f},
       {0.5f,1.0f,0.5f},{0.5f,1.0f,0.75f},{0.5f,1.0f,1.0f},{0.5f,0.75f,1.0f},
       {0.5f,0.5f,1.0f},{0.75f,0.5f,1.0f},{1.0f,0.5f,1.0f},{1.0f,0.5f,0.75f}
    };
    
	/** The buffer holding the vertices */
	private FloatBuffer vertexBuffer;
	/** The buffer holding the texture coordinates */
	private FloatBuffer textureBuffer;
	/** The buffer holding the indices */
	
	/** Our texture pointer */

	private int[] textures = new int[1];
	/** The initial vertex definition */
    	private float vertices[];
    	/** The initial texture coordinates (u, v) */	
    	private float texture[];

	float mHeight_aspect = 1;
    float mWidth_aspect = 1;

	public class Engine {
		float fadespeed = 0f;
		float xspeed = 0f;
		float yspeed = 0f;
		float xdir = 0f;
		float ydir = 0f;
		float xgrav = 0f;
		float ygrav = 0f;
	};

	Engine engine[] = new Engine[2];

	/**
	 * The My2DImage constructor.
	 * 
	 * Initiate the buffers.
	 */
	public ParticleSystem(int mWidth, int mHeight, boolean ispowof2, int param) {

		ispowof2 = true;
		setting = param;

		//base
		//engine[0].fadespeed = 0.05f;
		//engine[0].xspeed = 60f;
		//engine[0].yspeed = 60f;
		//engine[0].xdir = -1f;
		//engine[0].ydir = 1f;
		//engine[0].xgrav = 0f;
		//engine[0].ygrav = 500f;
		
		engine[0] = new Engine();
		engine[0].fadespeed = 0.05f;
		engine[0].xspeed = 60f*hd;
		engine[0].yspeed = 60f*hd;
		engine[0].xdir = 1f*hd;
		engine[0].ydir = 1f*hd;
		engine[0].xgrav = 0f*hd;
		engine[0].ygrav = 500f*hd;

		engine[1] = new Engine();
		engine[1].fadespeed = 0.05f;
		engine[1].xspeed = 0f*hd;
		engine[1].yspeed = 1f*hd;
		engine[1].xdir = 1f*hd;
		engine[1].ydir = 1f*hd;
		engine[1].xgrav = 0f*hd;
		engine[1].ygrav = -1500f*hd;

		
	    float Myvertices[] = {
	        0f, mHeight,     
	        mWidth, mHeight,     
	        mWidth, 0f, 
	        0f, 0f,  
	    				 };
	
	    				 
	    vertices = Myvertices;

    	if (ispowof2) {
	    	// textura koordinatainak kiszamitasa
	    	//feltetelezve h a png fajl merete az objektumhoz legkozelebbi 2 hatvanya
	    	int mHeight_powof2 = 1;	while (mHeight_powof2 < mHeight) { mHeight_powof2 = mHeight_powof2 * 2; }
	    	int mWidth_powof2 = 1;	while (mWidth_powof2 < mWidth) { mWidth_powof2 = mWidth_powof2 * 2; }

	    	mHeight_aspect = (float)mHeight / (float)mHeight_powof2;
	    	mWidth_aspect = (float)mWidth / (float)mWidth_powof2;
    	}

		float Mytexture[] = {         
	            0.0f, mHeight_aspect,
	            mWidth_aspect, mHeight_aspect,
	            mWidth_aspect, 0.0f,
	            0.0f, 0.0f,
		     				 };

		
		texture = Mytexture;

		//
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		//
		byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuf.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
	
		byteBuf = null;
		
        for (loop=0;loop<MAX_PARTICLES;loop++)				// Initials All The Textures
        {
           particle[loop]=new particles();
           particle[loop].active=true;								// Make All The Particles Active
           particle[loop].life=1.0f;								// Give All The Particles Full Life
           particle[loop].fade=(float)(100 * Math.random())/1000.0f+engine[setting].fadespeed;	// Random Fade Speed
           //particle[loop].fade=engine[setting].fadespeed;	// Random Fade Speed
           //particle[loop].fade=(float)(100 * Math.random())/1000.0f+0.003f;	// Random Fade Speed
           particle[loop].r=colors[loop*(12/MAX_PARTICLES)][0];	// Select Red Rainbow Color
           particle[loop].g=colors[loop*(12/MAX_PARTICLES)][1];	// Select Red Rainbow Color
           particle[loop].b=colors[loop*(12/MAX_PARTICLES)][2];	// Select Red Rainbow Color
           particle[loop].xi=(float)((engine[setting].xspeed * Math.random())+engine[setting].xdir*engine[setting].xspeed/2)*10.0f;		// Random Speed On X Axis
           particle[loop].yi=(float)((engine[setting].yspeed * Math.random())+engine[setting].ydir*engine[setting].yspeed/2)*10.0f;		// Random Speed On Y Axis
           //particle[loop].xi=(float)((50 * Math.random())-26.0f)*10.0f;		// Random Speed On X Axis
           //particle[loop].yi=(float)((50 * Math.random())-25.0f)*10.0f;		// Random Speed On Y Axis
           //particle[loop].zi=(float)((50 * Math.random())-25.0f)*10.0f;		// Random Speed On Z Axis
           particle[loop].xg=engine[setting].xgrav;									// Set Horizontal Pull To Zero
           particle[loop].yg=engine[setting].ygrav;								// Set Vertical Pull Downward
           //particle[loop].zg=0.0f;									// Set Pull On Z Axis To Zero
        }

	}


	/**
	 * The object own drawing function.
	 * Called from the renderer to redraw this instance
	 * with possible changes in values.
	 * 
	 */

	public void draw(GL10 gl, float posX, float posY) {

		//Bind our only previously generated texture in this case
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		
		//Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		//Set the face rotation
		gl.glFrontFace(GL10.GL_CCW);

		//Enable the vertex and texture state
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

		float difX = posX - prevX;
		float difY = posY - prevY;
		prevX = posX;
		prevY = posY;
		
        for (loop=0;loop<MAX_PARTICLES;loop++)					// Loop Through All The Particles
        {
           if (particle[loop].active)							// If The Particle Is Active
           {
              float x=particle[loop].x + difX;						// Grab Our Particle X Position
              float y=particle[loop].y + difY;						// Grab Our Particle Y Position
              //float z=particle[loop].z+zoom;					// Particle Z Pos + Zoom
           
              // Draw The Particle Using Our RGB Values, Fade The Particle Based On It's Life
              gl.glColor4f(particle[loop].r,particle[loop].g,particle[loop].b,particle[loop].life);

              gl.glTranslatef(x, y, 0.0f);
              //gl.glScalef(1.0f, 1.0f, 1.0f);
    		
              //Draw the vertices as triangles, based on the Index Buffer information
              gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);

              //if (scaleX == 0) scaleX = 1; if (scaleY == 0) scaleY = 1;
              //gl.glScalef(1/scaleX, 1/scaleY, 1.0f);
              gl.glTranslatef(-x, -y, 0.0f);

              /*
              gl.glTexCoord2d(1,1); gl.glVertex3f(x+0.5f,y+0.5f,z); // Top Right
              gl.glTexCoord2d(0,1); gl.glVertex3f(x-0.5f,y+0.5f,z); // Top Left
              gl.glTexCoord2d(1,0); gl.glVertex3f(x+0.5f,y-0.5f,z); // Bottom Right
              gl.glTexCoord2d(0,0); gl.glVertex3f(x-0.5f,y-0.5f,z); // Bottom Left
              */
           
              particle[loop].x+=particle[loop].xi/(slowdown*1000);// Move On The X Axis By X Speed
              particle[loop].y+=particle[loop].yi/(slowdown*1000);// Move On The Y Axis By Y Speed
              //particle[loop].z+=particle[loop].zi/(slowdown*1000);// Move On The Z Axis By Z Speed
           
              particle[loop].xi+=particle[loop].xg;			// Take Pull On X Axis Into Account
              particle[loop].yi+=particle[loop].yg;			// Take Pull On Y Axis Into Account
              //particle[loop].zi+=particle[loop].zg;			// Take Pull On Z Axis Into Account
              particle[loop].life-=particle[loop].fade;		// Reduce Particles Life By 'Fade'
           
              if (particle[loop].life<0.0f)					// If Particle Is Burned Out
              {
                 particle[loop].life=1.0f;					// Give It New Life
                 particle[loop].fade=(float)(100 * Math.random())/1000.0f+engine[setting].fadespeed;	// Random Fade Value
                 //particle[loop].fade=engine[setting].fadespeed;	// Random Fade Value
                 //particle[loop].fade=(float)(100 * Math.random())/1000.0f+0.003f;	// Random Fade Value
                 particle[loop].x=posX;						// Center On X Axis
                 particle[loop].y=posY;						// Center On Y Axis
                 particle[loop].z=0.0f;						// Center On Z Axis
                 particle[loop].xi=xspeed+(float)((engine[setting].xspeed * Math.random())+engine[setting].xdir*engine[setting].xspeed/2);	// X Axis Speed And Direction
                 particle[loop].yi=yspeed+(float)((engine[setting].yspeed * Math.random())+engine[setting].ydir*engine[setting].yspeed/2);	// Y Axis Speed And Direction
                 //particle[loop].xi=xspeed+(float)((60 * Math.random())-32.0f);	// X Axis Speed And Direction
                 //particle[loop].yi=yspeed+(float)((60 * Math.random())-30.0f);	// Y Axis Speed And Direction
                 //particle[loop].zi=(float)((60 * Math.random())-30.0f);	// Z Axis Speed And Direction
                 particle[loop].r=colors[col][0];			// Select Red From Color Table
                 particle[loop].g=colors[col][1];			// Select Green From Color Table
                 particle[loop].b=colors[col][2];			// Select Blue From Color Table
              }
           }
           
        }

		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

	}

	
	/**
	 * Load the textures
	 * 
	 */
	public void load(GL10 gl, Context context, int id) {
		
		//Get the texture from the Android resource directory
		InputStream is = context.getResources().openRawResource(id);
		Bitmap bitmap = null;
		try {
			//BitmapFactory is an Android graphics utility for images
			bitmap = BitmapFactory.decodeStream(is);
			//Log.w("Space Shooter", "bitmap strean decoded");

		} finally {
			//Always clear and close
			try {
				is.close();
				is = null;
				//Log.w("Space Shooter", "Is closed");
			} catch (IOException e) {
			}
		}

		//Generate one texture pointer...
		//gl.glDeleteTextures(1, textures, 0);
		gl.glGenTextures(1, textures, 0);
		//...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		Log.w("Space Shooter", "texture loaded. Num: " + Integer.toString(textures[0]) + " id: "+Integer.toString(id));
		
		//Create Nearest Filtered Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		//Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

		//Delete texture ???
		//gl.glDeleteTextures(1, textures, 0);
		
		//Clean up
		bitmap.recycle();
	}
	
	public void release(GL10 gl) {
		gl.glDeleteTextures(1, textures, 0);
		Log.w("Space Shooter", "texture freed: " + Integer.toString(textures[0]));
	}

} 
