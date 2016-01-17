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
import android.opengl.GLUtils;
import android.util.Log;

public class MyBGScroller {

	final float hd = OpenGLRenderer.hd;
	
	/** The buffer holding the vertices */
	private FloatBuffer vertexBuffer;
	/** The buffer holding the texture coordinates */
	private FloatBuffer textureBuffer;
	/** The buffer holding the indices */
//	private ByteBuffer indexBuffer;
	
	/** Our texture pointer */

	private int[] textures = new int[1];
	/** The initial vertex definition */
    	private float vertices[];
    	/** The initial texture coordinates (u, v) */	
    	private float texture[];

    	/** The initial indices definition */	
//    	private byte indices[] = {
		//Faces definition
//    		0,1,3, 0,3,2, 			//Face front
//	};

    
	/**
	 * The My2DImage constructor.
	 * 
	 * Initiate the buffers.
	 */
	public MyBGScroller(int mWidth, int mHeight, boolean ispowof2) {

		ispowof2 = true;
		
	    float Myvertices[] = {
	        0f, mHeight,     
	        mWidth, mHeight,     
	        mWidth, 0f, 
	        0f, 0f,  
	    				 };
	
	    				 
	    vertices = Myvertices;

    	float mHeight_aspect = 1;
    	float mWidth_aspect = 1;

    	if (ispowof2) {
	    	// textura koordinatainak kiszamitasa
	    	//feltetelezve h a png fajl merete az objektumhoz legkozelebbi 2 hatvanya
	    	int mHeight_powof2 = 1;	while (mHeight_powof2 < mHeight) { mHeight_powof2 = mHeight_powof2 * 2; }
	    	int mWidth_powof2 = 1;	while (mWidth_powof2 < mWidth) { mWidth_powof2 = mWidth_powof2 * 2; }

	    	mHeight_aspect = (float)mHeight / (float)mHeight_powof2;
	    	mWidth_aspect = (float)mWidth / (float)mWidth_powof2;
    	}
//		mHeight_aspect = 1;
//		mWidth_aspect = 1;

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

		//
		//indexBuffer = ByteBuffer.allocateDirect(indices.length);
		//indexBuffer.put(indices);
		//indexBuffer.position(0);
	}


	/**
	 * The object own drawing function.
	 * Called from the renderer to redraw this instance
	 * with possible changes in values.
	 * 
	 */

	public void draw(GL10 gl, float x, float y, float scaleX, float scaleY) {

		//gl.glTranslatef(x, y, 0.0f);
		gl.glScalef(scaleX, scaleY, 1.0f);
		
		gl.glColor4f(1f,1f,1f,1f); //color

			//Bind our only previously generated texture in this case
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		
		//Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		//Set the face rotation
		gl.glFrontFace(GL10.GL_CCW);
		
		//
		float offset = 1-y/(800*hd);
		texture[1] = 1+offset; 
		texture[3] = 1+offset;
		texture[5] = offset; 
		texture[7] = offset;
		
		textureBuffer.clear();
		textureBuffer.put(texture);
		textureBuffer.position(0); 
		
		//Enable the vertex and texture state
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		
		//Draw the vertices as triangles, based on the Index Buffer information
//		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
		
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		if (scaleX == 0) scaleX = 1; if (scaleY == 0) scaleY = 1;
		gl.glScalef(1/scaleX, 1/scaleY, 1.0f);
		//gl.glTranslatef(-x, -y, 0.0f);
	}

	public void draw(GL10 gl, float x, float y) {
		draw(gl, x, y, 1.0f, 1.0f);
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

		//gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE);

		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
		
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
