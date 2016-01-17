//   Android BFF Loader/Renderer v1.07
//   Copyright 2011 Karl Walsh
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package com.tubigames.galaxy.shooter.hd;

//import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.content.res.AssetManager;
import android.content.Context;
import android.util.Log;
 
public class TexFont 
{
	Context mContext;
	float xScale, yScale;
	int fntTexWidth, fntTexHeight;
	int fntCellWidth, fntCellHeight;
	int BPP,firstCharOffset,colCount;
	int[] charWidth;
	int texID;
	float redVal, greenVal, blueVal, alphaVal;
	int curX, curY;
	int UVarray[];
	int dimensionY;

	public float scrscaleX = 1;
	public float scrscaleY = 1;
	
	public TexFont(Context context, GL10 gl, float scx, float scy, int dY)
	{
		// Get handle on assets
		mContext = context;
		
		// new code = screenwidth / height scale for opengl surface
		scrscaleX = scx;
		scrscaleY = scy;
		dimensionY = dY;
		
		// Initialise parameters
		redVal = greenVal = blueVal = alphaVal = 1.0f;
		curX = curY = 0;
		
		// Set scale to neutral
		xScale = yScale = 1.0f;
		
		// Array to hold character width data
		charWidth = new int[256];
		
		// UV Array for crop rectangle
		UVarray = new int[4];

		// Generate GL texture ID
		int temp[] = new int[1];
		gl.glGenTextures(1, temp, 0);
		texID = temp[0];
		
	    gl.glBindTexture(GL10.GL_TEXTURE_2D, texID);
	    
	    // Set texture parameters
	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
		
		Log.i("Font", "Initialized");
		Log.i("Font", "ScaleX: " + Float.toString(scx) + " ScaleY: " + Float.toString(scy) + " ScrHeight: " + Float.toString(dY));
		Log.i("Font", "texID: " + Integer.toString(texID));

	}
	
	// BFF files should be placed in the assets folder to be picked up by this function
	public Boolean LoadFont(String fontName, GL10 gl) throws IOException
	{
		int bytesRead;
		
		// Array for file header
		byte[] head = new byte[20];
		
		try
		{
			AssetManager am = mContext.getAssets();
			InputStream uStream = am.open(fontName, AssetManager.ACCESS_BUFFER);
		 
			// Read header
			bytesRead = uStream.read(head, 0, 20);
		 
			// Check header size is correct
			if(bytesRead < 20)
			{
				throw new IOException("Header read failed");
			}
		 
			ByteBuffer headBuf = ByteBuffer.wrap(head);

			// Check header
			int h0 = getUnsignedByteVal(headBuf.get());
			int h1 = getUnsignedByteVal(headBuf.get());
		 
			// Check header signature
			if(h0 != 0xBF || h1 != 0xF2)
			{
				uStream.close();
				throw new IOException("Bad header signature");
			}
		 
			// Get image width and height
			fntTexWidth  = flipEndian(headBuf.getInt());
			fntTexHeight = flipEndian(headBuf.getInt());	
			
			// Get cell dimensions
			fntCellWidth  = flipEndian(headBuf.getInt());
			fntCellHeight = flipEndian(headBuf.getInt());

			// Sanity check (prevent divide by zero)
			if(fntCellWidth <= 0 || fntCellHeight <= 0)
			{
				throw new IOException("Invalid header content");
			}
			
			// Pre-calculate column count
			colCount = fntTexWidth / fntCellWidth;
			
			// Get colour depth
			BPP = getUnsignedByteVal(headBuf.get());
					
			// Get base offset
			firstCharOffset = getUnsignedByteVal(headBuf.get());
			
			// Read width information
			for(int wLoop = 0; wLoop < 256; ++wLoop)
			{
				charWidth[wLoop] = uStream.read(); // read() returns an unsigned byte, unlike the overloaded versions.  :s
			}			

			// Get bitmap
			int bitLen = (fntTexHeight * fntTexWidth) * (BPP / 8);
			byte bits[] = new byte[bitLen];
		
			uStream.read(bits, 0, bitLen);
				
			uStream.close();	
			
			// Flip image scanlines and wrap in Bytebuffer for glTexImage2D
			//ByteBuffer pix = ByteBuffer.allocate(bits.length);
			byte[] data = new byte[bits.length];
			
			int lineLen = fntTexWidth * (BPP / 8);
			
			int num = 0;
			for(int lines = fntTexHeight-1; lines>0 ; --lines)
			{
				for (int i=lineLen; i>0; --i) {
					data[num] = bits[lines * lineLen - i];
					num++;
				}
				//pix.put(bits, lines * lineLen, lineLen);
			}

			ByteBuffer pix = ByteBuffer.wrap( data );

			Log.i("Font", "texwidth: " + Integer.toString(fntTexWidth));
			Log.i("Font", "texheight: " + Integer.toString(fntTexHeight));
			Log.i("Font", "cellwidth: " + Integer.toString(fntCellWidth));
			Log.i("Font", "cellheight: " + Integer.toString(fntCellHeight));
			Log.i("Font", "column: " + Integer.toString(colCount));
			Log.i("Font", "bpp: " + Integer.toString(BPP));

			// Place bitmap in texture
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texID);

			Log.i("Font", "texture bind id: " + Integer.toString(texID));

		    pix.position(0);
		    Log.i("Font", pix.toString());
		    
			switch(BPP)
			{
			case 8: // Alpha channel info only
				gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_ALPHA, fntTexWidth, fntTexHeight, 0, GL10.GL_ALPHA, GL10.GL_UNSIGNED_BYTE, pix);
				break;
				
			case 24: // RGB Texture
				gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGB, fntTexWidth, fntTexHeight, 0, GL10.GL_RGB, GL10.GL_UNSIGNED_BYTE, pix);
				break;
				
			case 32: // RGBA Texture
				Log.i("Font", "TexImage2D");
				gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGBA, fntTexWidth, fntTexHeight, 0, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, pix);
				break;
			}
			
			//pix = null;

		}
		catch (java.io.EOFException e)
		{
			throw e;
		}
		catch (java.io.IOException e)
		{
			throw e;			
		}
		Log.i("Font", "Returning...");
		
		return true;
	}
	
	
	// Flip endian-ness of a 32 bit integer value. (Stupid Java, no cake for you)
	int flipEndian(int val)
	{
		return  (val >>> 24) | (val << 24) | ((val << 8) & 0x00FF0000) | ((val >> 8) & 0x0000FF00);
	}
	
	// Bodge to get unsigned byte values
	int getUnsignedByteVal(byte val)
	{
		if(val < 0)
			return 256 + val;
		else
			return val;		
	}
	
	// Set a general scale for text
	public void SetScale(float scaleVal)
	{
		xScale = yScale = scaleVal;
	}
	
	// Set text width and height scale independently
	public void SetScale(float xScaleVal, float yScaleVal)
	{
		xScale = xScaleVal;
		yScale = yScaleVal;	
	}
	
	// Set colour for text quads.
	public void SetPolyColor(float red, float green, float blue, float alpha)
	{
		redVal = red;
		greenVal = green;
		blueVal = blue;
		alphaVal = alpha;
	}
	
	// Set cursor position
	public void SetCursor(int x, int y)
	{
		curX = x;
		curY = y;		
	}
	
	// Print a line of text to screen at specified co-ords
	public void PrintAt(GL10 gl, String text, int x, int y)
	{
		int glyph, col, row;
		
		// Calculate quad size from scaling factors
		float cellWidth = fntCellWidth * xScale;
		float cellHeight = fntCellHeight * yScale;
		
		// for fix 480 scr height, reverse coords
		y = (int) (scrscaleY * (dimensionY - y) - cellHeight);
		//y = (int) (scrscaleY * y);
		x = (int) (scrscaleX * x);
		//y = (int) (0.478 * y);
		//x = (int) (scrscaleX * x);

		float xPos = x;
		
		// The height and width values in the crop rect never change
		UVarray[2]=fntCellWidth;
		UVarray[3]=fntCellHeight;
		
		// Set up GL for rendering the text
		gl.glColor4f(redVal, greenVal, blueVal, alphaVal);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texID);
		
		// Loop through each character of the string
		for(int index = 0 ; index != text.length(); ++index )
		{
			// Calculate glyph position within texture
			glyph = (int)text.charAt(index) - firstCharOffset;
			col = glyph % colCount;
			row = (glyph / colCount) + 1;
			
			// Update the crop rect
			UVarray[0] = col*fntCellWidth;
			UVarray[1] = fntTexHeight - (row*fntCellHeight);
			
			// Set crop area
			((GL11) gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES,UVarray,0);
			// Draw texture
			((GL11Ext) gl).glDrawTexfOES(xPos,(float)y,0.0f,cellWidth,cellHeight);
			
			// Add character width to offset for next glyph
			xPos += (xScale * charWidth[glyph + firstCharOffset]);
		}
		
		// Update cursor position
		curX = (int)xPos;
	}
	
	// Return the length (pixels) of a string
	public int GetTextLength(String text)
	{
		float len = 0.0f;
		
		for(int index = 0 ; index != text.length(); ++index )
		{
			len += (1/scrscaleX * xScale * charWidth[(int)text.charAt(index)]);
		}
		
		return (int)len;
	}
	
	// Return the height of text
	public int GetTextHeight()
	{
		return (int)(fntCellHeight * yScale * 1/scrscaleY);
	}
	
	public void release(GL10 gl) {
		
		//gl.glDeleteTextures(1, textures, 0);
	}

} 
