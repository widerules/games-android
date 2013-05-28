package com.games.android.example;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;

public class TriangleRenderer1 implements Renderer {

	GLSurfaceView curView;
	int iProgId;
	int iPosition;
	//float[] fVertices = {0, 0.5f, 0,  0, 0,
		//	-0.5f,-0.5f,0, 1,0,
			//0.5f,-0.5f,0, 1,1,
			//};
	
	float[] fVertices = {		
			-0.5f,0.5f,0,
			-0.5f, -0.5f, 0,
			0.5f,-0.5f,0,
			
			0.5f,-0.5f,0,
			0.5f,0.5f,0,
			-0.5f,0.5f,0,
			};
	
	int iTexCoords;
	 int texCoords[] = {
	    0,0,
	    0,1,
	    1,1,
	    1,1,
	    0,1,
	    0,0
	};
	int iTexLoc;
	int iTexId;
	FloatBuffer vertexBuffer;
	IntBuffer textCooorBuff;

	public TriangleRenderer1(GLSurfaceView view) {
		curView = view;
		
		vertexBuffer = ByteBuffer.allocateDirect(fVertices.length *4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		textCooorBuff = ByteBuffer.allocateDirect(texCoords.length*4).order(ByteOrder.nativeOrder()).asIntBuffer();
		vertexBuffer.put(fVertices).position(0);
		textCooorBuff.put(texCoords).position(0);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		GLES20.glUseProgram(iProgId);
		
		vertexBuffer.position(0);
		GLES20.glVertexAttribPointer(iPosition, 3, GLES20.GL_FLOAT, false, 5 * 4, vertexBuffer);
		GLES20.glEnableVertexAttribArray(iPosition);
		
		//vertexBuffer.position(3);
		GLES20.glVertexAttribPointer(iPosition, 2, GLES20.GL_INT, false, 5* 4, textCooorBuff);
		GLES20.glEnableVertexAttribArray(iPosition);
		
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
	GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, iTexId);
	GLES20.glUniform1i(iTexLoc, 0);
		
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(0, 0, 0, 1);
		
		String strVShader = 
			    "attribute vec4 a_position;" +
			    "attribute vec2 a_texCoords;" +
			    "varying vec2 v_texCoords;" +
				"void main()" +
				"{" +
				    "gl_Position = a_position;" +
				    "v_texCoords = a_texCoords;" +
				"}";
		String strFShader = 
				"precision mediump float;" +
				"varying vec2 v_texCoords;" +
				"uniform sampler2D u_texId;" +
				"void main()" +
				"{" +
					"gl_FragColor = texture2D(u_texId, v_texCoords);" +
				"}";

		iProgId = Utils.LoadProgram(strVShader, strFShader);
		iPosition = GLES20.glGetAttribLocation(iProgId, "a_position");
		iTexCoords = GLES20.glGetAttribLocation(iProgId, "a_texCoords");
		iTexLoc = GLES20.glGetUniformLocation(iProgId, "u_texId");
		
		iTexId = Utils.LoadTexture(curView, R.drawable.texture);
	}
}

