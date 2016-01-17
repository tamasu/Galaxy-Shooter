package com.tubigames.galaxy.shooter.hd;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class Explosions {
	
	float hd = OpenGLRenderer.hd;
	
	// explosion típusok
	public class Explosion {
		
		public int[] picture_id; // az entitás képfájljának id-je a res/drawables/ könyvtárból
		public My2DImage[] image; // az entitás képfájljának id-je a res/drawables/ könyvtárból
		public int picture_num = 0;
		public int width = 0; // a kép szélessége
		public int height = 0; // a kép magassága
		public float rotation = 0.0f;
		public float colorR = 1.0f;
		public float colorG = 1.0f;
		public float colorB = 1.0f;
		public float colorA = 1.0f;
	}
	
	public Explosion[] explosion; 

	
	public Explosions() {
		
	}
	
	public void load(GL10 gl, Context context) {

		explosion = new Explosion[15]; // összes explosion típus mennyisége

		// BASIC EXPLOSION
		explosion[0] = new Explosion();
		explosion[0].picture_num = 20;
		explosion[0].width = 192;
		explosion[0].height = 192;
		explosion[0].picture_id = new int[explosion[0].picture_num];
		explosion[0].picture_id[0] = R.drawable.fire_01;
		explosion[0].picture_id[1] = R.drawable.fire_02;
		explosion[0].picture_id[2] = R.drawable.fire_03;
		explosion[0].picture_id[3] = R.drawable.fire_04;
		explosion[0].picture_id[4] = R.drawable.fire_05;
		explosion[0].picture_id[5] = R.drawable.fire_06;
		explosion[0].picture_id[6] = R.drawable.fire_07;
		explosion[0].picture_id[7] = R.drawable.fire_08;
		explosion[0].picture_id[8] = R.drawable.fire_09;
		explosion[0].picture_id[9] = R.drawable.fire_10;
		explosion[0].picture_id[10] = R.drawable.fire_11;
		explosion[0].picture_id[11] = R.drawable.fire_12;
		explosion[0].picture_id[12] = R.drawable.fire_13;
		explosion[0].picture_id[13] = R.drawable.fire_14;
		explosion[0].picture_id[14] = R.drawable.fire_15;
		explosion[0].picture_id[15] = R.drawable.fire_16;
		explosion[0].picture_id[16] = R.drawable.fire_17;
		explosion[0].picture_id[17] = R.drawable.fire_18;
		explosion[0].picture_id[18] = R.drawable.fire_19;
		explosion[0].picture_id[19] = R.drawable.fire_20;
		explosion[0].image = new My2DImage[explosion[0].picture_num];
		for (int i=0; i<explosion[0].picture_num; i++) {
			explosion[0].image[i] = new My2DImage(explosion[0].width,explosion[0].height,true);
			explosion[0].image[i].load(gl, context, explosion[0].picture_id[i]);
		}

		// SINGULARITY
		explosion[1] = new Explosion();
		explosion[1].picture_num = 16;
		explosion[1].width = 80;
		explosion[1].height = 80;
		explosion[1].picture_id = new int[explosion[1].picture_num];
		explosion[1].picture_id[0] = R.drawable.explosion_b_01;
		explosion[1].picture_id[1] = R.drawable.explosion_b_02;
		explosion[1].picture_id[2] = R.drawable.explosion_b_03;
		explosion[1].picture_id[3] = R.drawable.explosion_b_04;
		explosion[1].picture_id[4] = R.drawable.explosion_b_05;
		explosion[1].picture_id[5] = R.drawable.explosion_b_06;
		explosion[1].picture_id[6] = R.drawable.explosion_b_07;
		explosion[1].picture_id[7] = R.drawable.explosion_b_08;
		explosion[1].picture_id[8] = R.drawable.explosion_b_09;
		explosion[1].picture_id[9] = R.drawable.explosion_b_10;
		explosion[1].picture_id[10] = R.drawable.explosion_b_11;
		explosion[1].picture_id[11] = R.drawable.explosion_b_12;
		explosion[1].picture_id[12] = R.drawable.explosion_b_13;
		explosion[1].picture_id[13] = R.drawable.explosion_b_14;
		explosion[1].picture_id[14] = R.drawable.explosion_b_15;
		explosion[1].picture_id[15] = R.drawable.explosion_b_16;
		explosion[1].image = new My2DImage[explosion[1].picture_num];
		for (int i=0; i<explosion[1].picture_num; i++) {
			explosion[1].image[i] = new My2DImage(explosion[1].width,explosion[1].height,true);
			explosion[1].image[i].load(gl, context, explosion[1].picture_id[i]);
		}

		// EXTENDED EXPLOSION
		explosion[2] = new Explosion();
		explosion[2].picture_num = 1;
		explosion[2].width = 2;
		explosion[2].height = 2;
		explosion[2].picture_id = new int[explosion[2].picture_num];
		explosion[2].picture_id[0] = R.drawable.fire_01;
		explosion[2].image = new My2DImage[explosion[2].picture_num];
		for (int i=0; i<explosion[2].picture_num; i++) {
			explosion[2].image[i] = new My2DImage(explosion[2].width,explosion[2].height,true);
			explosion[2].image[i].load(gl, context, explosion[2].picture_id[i]);
		}

		// CRASH ICE
		explosion[3] = new Explosion();
		explosion[3].picture_num = 16;
		explosion[3].width = 128;
		explosion[3].height = 128;
		explosion[3].picture_id = new int[explosion[3].picture_num];
		explosion[3].picture_id[0] = R.drawable.ice_001;
		explosion[3].picture_id[1] = R.drawable.ice_002;
		explosion[3].picture_id[2] = R.drawable.ice_003;
		explosion[3].picture_id[3] = R.drawable.ice_004;
		explosion[3].picture_id[4] = R.drawable.ice_005;
		explosion[3].picture_id[5] = R.drawable.ice_006;
		explosion[3].picture_id[6] = R.drawable.ice_007;
		explosion[3].picture_id[7] = R.drawable.ice_008;
		explosion[3].picture_id[8] = R.drawable.ice_009;
		explosion[3].picture_id[9] = R.drawable.ice_010;
		explosion[3].picture_id[10] = R.drawable.ice_011;
		explosion[3].picture_id[11] = R.drawable.ice_012;
		explosion[3].picture_id[12] = R.drawable.ice_013;
		explosion[3].picture_id[13] = R.drawable.ice_014;
		explosion[3].picture_id[14] = R.drawable.ice_015;
		explosion[3].picture_id[15] = R.drawable.ice_016;
		explosion[3].image = new My2DImage[explosion[3].picture_num];
		for (int i=0; i<explosion[3].picture_num; i++) {
			explosion[3].image[i] = new My2DImage(explosion[3].width,explosion[3].height,true);
			explosion[3].image[i].load(gl, context, explosion[3].picture_id[i]);
		}

		
		// EXPLOSION 4 - 8 REMOVED
		// EXPLOSION 4 - 8 REMOVED
		// EXPLOSION 4 - 8 REMOVED
		// EXPLOSION 4 - 8 REMOVED
		// EXPLOSION 4 - 8 REMOVED
		
		// BASIC YELLOW
		explosion[4] = new Explosion();
		explosion[4].picture_num = 1;
		explosion[4].width = 2;
		explosion[4].height = 2;
		explosion[4].image = new My2DImage[explosion[4].picture_num];
		for (int i=0; i<explosion[4].picture_num; i++) {
			explosion[4].image[i] = new My2DImage(explosion[4].width,explosion[4].height,true);
			explosion[4].image[i].load(gl, context, explosion[0].picture_id[i]);
		}
		//explosion[4].rotation = 90.0f;
		explosion[4].colorR = 1.0f;
		explosion[4].colorG = 1.0f;
		explosion[4].colorB = 0.0f;

		// BASIC RED
		explosion[5] = new Explosion();
		explosion[5].picture_num = 1;
		explosion[5].width = 2;
		explosion[5].height = 2;
		explosion[5].image = new My2DImage[explosion[5].picture_num];
		for (int i=0; i<explosion[5].picture_num; i++) {
			explosion[5].image[i] = new My2DImage(explosion[5].width,explosion[5].height,true);
			explosion[5].image[i].load(gl, context, explosion[0].picture_id[i]);
		}
		//explosion[5].rotation = 90.0f;
		explosion[5].colorR = 1.0f;
		explosion[5].colorG = 0.0f;
		explosion[5].colorB = 0.0f;

		// BASIC BLUE
		explosion[6] = new Explosion();
		explosion[6].picture_num = 1;
		explosion[6].width = 2;
		explosion[6].height = 2;
		explosion[6].image = new My2DImage[explosion[6].picture_num];
		for (int i=0; i<explosion[6].picture_num; i++) {
			explosion[6].image[i] = new My2DImage(explosion[6].width,explosion[6].height,true);
			explosion[6].image[i].load(gl, context, explosion[0].picture_id[i]);
		}
		//explosion[6].rotation = 90.0f;
		explosion[6].colorR = 0.0f;
		explosion[6].colorG = 0.0f;
		explosion[6].colorB = 1.0f;

		// BASIC GREEN
		explosion[7] = new Explosion();
		explosion[7].picture_num = 1;
		explosion[7].width = 2;
		explosion[7].height = 2;
		explosion[7].image = new My2DImage[explosion[7].picture_num];
		for (int i=0; i<explosion[7].picture_num; i++) {
			explosion[7].image[i] = new My2DImage(explosion[7].width,explosion[7].height,true);
			explosion[7].image[i].load(gl, context, explosion[0].picture_id[i]);
		}
		//explosion[7].rotation = 90.0f;
		explosion[7].colorR = 0.0f;
		explosion[7].colorG = 1.0f;
		explosion[7].colorB = 0.0f;

		// BASIC PURPLE
		explosion[8] = new Explosion();
		explosion[8].picture_num = 1;
		explosion[8].width = 2;
		explosion[8].height = 2;
		explosion[8].image = new My2DImage[explosion[8].picture_num];
		for (int i=0; i<explosion[8].picture_num; i++) {
			explosion[8].image[i] = new My2DImage(explosion[8].width,explosion[8].height,true);
			explosion[8].image[i].load(gl, context, explosion[0].picture_id[i]);
		}
		//explosion[8].rotation = 90.0f;
		explosion[8].colorR = 1.0f;
		explosion[8].colorG = 0.0f;
		explosion[8].colorB = 1.0f;


		
		
		// EMP
		explosion[9] = new Explosion();
		explosion[9].picture_num = 4;
		explosion[9].width = 145;
		explosion[9].height = 189;
		explosion[9].picture_id = new int[explosion[9].picture_num];
		explosion[9].picture_id[0] = R.drawable.emp_01;
		explosion[9].picture_id[1] = R.drawable.emp_02;
		explosion[9].picture_id[2] = R.drawable.emp_03;
		explosion[9].picture_id[3] = R.drawable.emp_04;
		explosion[9].image = new My2DImage[explosion[9].picture_num];
		for (int i=0; i<explosion[9].picture_num; i++) {
			explosion[9].image[i] = new My2DImage(explosion[9].width,explosion[9].height,true);
			explosion[9].image[i].load(gl, context, explosion[9].picture_id[i]);
		}

		// GRAVITY
		explosion[10] = new Explosion();
		explosion[10].picture_num = 1;
		explosion[10].width = 303;
		explosion[10].height = 297;
		explosion[10].picture_id = new int[explosion[10].picture_num];
		explosion[10].picture_id[0] = R.drawable.gravity_gun;
		explosion[10].image = new My2DImage[explosion[10].picture_num];
		for (int i=0; i<explosion[10].picture_num; i++) {
			explosion[10].image[i] = new My2DImage(explosion[10].width,explosion[10].height,true);
			explosion[10].image[i].load(gl, context, explosion[10].picture_id[i]);
		}
		
		explosion[11] = new Explosion();
		explosion[11].picture_num = 7;
		explosion[11].width = 128;
		explosion[11].height = 128;
		explosion[11].picture_id = new int[explosion[11].picture_num];
		explosion[11].picture_id[0] = R.drawable.flyfx0000;
		explosion[11].picture_id[1] = R.drawable.flyfx0001;
		explosion[11].picture_id[2] = R.drawable.flyfx0002;
		explosion[11].picture_id[3] = R.drawable.flyfx0003;
		explosion[11].picture_id[4] = R.drawable.flyfx0004;
		explosion[11].picture_id[5] = R.drawable.flyfx0005;
		explosion[11].picture_id[6] = R.drawable.flyfx0006;
		explosion[11].image = new My2DImage[explosion[11].picture_num];
		for (int i=0; i<explosion[11].picture_num; i++) {
			explosion[11].image[i] = new My2DImage(explosion[11].width,explosion[11].height,true);
			explosion[11].image[i].load(gl, context, explosion[11].picture_id[i]);
		}
		explosion[11].colorR = 0.8f;
		explosion[11].colorG = 0.8f;
		explosion[11].colorB = 0.8f;
		explosion[11].colorA = 0.5f;
		
		explosion[12] = new Explosion();
		explosion[12].picture_num = 20;
		explosion[12].width = (int)(hd*66);
		explosion[12].height = (int)(hd*66);
		explosion[12].picture_id = new int[explosion[12].picture_num];
		explosion[12].picture_id[0] = R.drawable.hp_red_01;
		explosion[12].picture_id[1] = R.drawable.hp_red_02;
		explosion[12].picture_id[2] = R.drawable.hp_red_03;
		explosion[12].picture_id[3] = R.drawable.hp_red_04;
		explosion[12].picture_id[4] = R.drawable.hp_red_05;
		explosion[12].picture_id[5] = R.drawable.hp_red_06;
		explosion[12].picture_id[6] = R.drawable.hp_red_07;
		explosion[12].picture_id[7] = R.drawable.hp_red_08;
		explosion[12].picture_id[8] = R.drawable.hp_red_09;
		explosion[12].picture_id[9] = R.drawable.hp_red_010;
		explosion[12].picture_id[10] = R.drawable.hp_red_011;
		explosion[12].picture_id[11] = R.drawable.hp_red_012;
		explosion[12].picture_id[12] = R.drawable.hp_red_013;
		explosion[12].picture_id[13] = R.drawable.hp_red_014;
		explosion[12].picture_id[14] = R.drawable.hp_red_015;
		explosion[12].picture_id[15] = R.drawable.hp_red_016;
		explosion[12].picture_id[16] = R.drawable.hp_red_017;
		explosion[12].picture_id[17] = R.drawable.hp_red_018;
		explosion[12].picture_id[18] = R.drawable.hp_red_019;
		explosion[12].picture_id[19] = R.drawable.hp_red_020;
		explosion[12].image = new My2DImage[explosion[12].picture_num];
		for (int i=0; i<explosion[12].picture_num; i++) {
			explosion[12].image[i] = new My2DImage(explosion[12].width,explosion[12].height,true);
			explosion[12].image[i].load(gl, context, explosion[12].picture_id[i]);
		}
		
		explosion[13] = new Explosion();
		explosion[13].picture_num = 20;
		explosion[13].width = (int)(hd*66);
		explosion[13].height = (int)(hd*66);
		explosion[13].picture_id = new int[explosion[13].picture_num];
		explosion[13].picture_id[0] = R.drawable.shield_blue_01;
		explosion[13].picture_id[1] = R.drawable.shield_blue_02;
		explosion[13].picture_id[2] = R.drawable.shield_blue_03;
		explosion[13].picture_id[3] = R.drawable.shield_blue_04;
		explosion[13].picture_id[4] = R.drawable.shield_blue_05;
		explosion[13].picture_id[5] = R.drawable.shield_blue_06;
		explosion[13].picture_id[6] = R.drawable.shield_blue_07;
		explosion[13].picture_id[7] = R.drawable.shield_blue_08;
		explosion[13].picture_id[8] = R.drawable.shield_blue_09;
		explosion[13].picture_id[9] = R.drawable.shield_blue_010;
		explosion[13].picture_id[10] = R.drawable.shield_blue_011;
		explosion[13].picture_id[11] = R.drawable.shield_blue_012;
		explosion[13].picture_id[12] = R.drawable.shield_blue_013;
		explosion[13].picture_id[13] = R.drawable.shield_blue_014;
		explosion[13].picture_id[14] = R.drawable.shield_blue_015;
		explosion[13].picture_id[15] = R.drawable.shield_blue_016;
		explosion[13].image = new My2DImage[explosion[13].picture_num];
		explosion[13].picture_id[16] = R.drawable.shield_blue_017;
		explosion[13].picture_id[17] = R.drawable.shield_blue_018;
		explosion[13].picture_id[18] = R.drawable.shield_blue_019;
		explosion[13].picture_id[19] = R.drawable.shield_blue_020;		
		for (int i=0; i<explosion[13].picture_num; i++) {
			explosion[13].image[i] = new My2DImage(explosion[13].width,explosion[13].height,true);
			explosion[13].image[i].load(gl, context, explosion[13].picture_id[i]);
		}
		
		explosion[14] = new Explosion();
		explosion[14].picture_num = 20;
		explosion[14].width = (int)(hd*66);
		explosion[14].height = (int)(hd*66);
		explosion[14].picture_id = new int[explosion[14].picture_num];
		explosion[14].picture_id[0] = R.drawable.special_green_01;
		explosion[14].picture_id[1] = R.drawable.special_green_02;
		explosion[14].picture_id[2] = R.drawable.special_green_03;
		explosion[14].picture_id[3] = R.drawable.special_green_04;
		explosion[14].picture_id[4] = R.drawable.special_green_05;
		explosion[14].picture_id[5] = R.drawable.special_green_06;
		explosion[14].picture_id[6] = R.drawable.special_green_07;
		explosion[14].picture_id[7] = R.drawable.special_green_08;
		explosion[14].picture_id[8] = R.drawable.special_green_09;
		explosion[14].picture_id[9] = R.drawable.special_green_010;
		explosion[14].picture_id[10] = R.drawable.special_green_011;
		explosion[14].picture_id[11] = R.drawable.special_green_012;
		explosion[14].picture_id[12] = R.drawable.special_green_013;
		explosion[14].picture_id[13] = R.drawable.special_green_014;
		explosion[14].picture_id[14] = R.drawable.special_green_015;
		explosion[14].picture_id[15] = R.drawable.special_green_016;
		explosion[14].picture_id[16] = R.drawable.special_green_017;
		explosion[14].picture_id[17] = R.drawable.special_green_018;
		explosion[14].picture_id[18] = R.drawable.special_green_019;
		explosion[14].picture_id[19] = R.drawable.special_green_020;
		explosion[14].image = new My2DImage[explosion[14].picture_num];
		for (int i=0; i<explosion[14].picture_num; i++) {
			explosion[14].image[i] = new My2DImage(explosion[14].width,explosion[14].height,true);
			explosion[14].image[i].load(gl, context, explosion[14].picture_id[i]);
		}
		
		// TODO
		// METEOR EXPLOSION // resource used twice
		/*
		explosion[11] = new Explosion();
		explosion[11].picture_num = 10;
		explosion[11].width = 125;
		explosion[11].height = 125;
		explosion[11].picture_id = new int[explosion[11].picture_num];
		explosion[11].picture_id[0] = R.drawable.explosion_e_01;
		explosion[11].picture_id[1] = R.drawable.explosion_e_01;
		explosion[11].picture_id[2] = R.drawable.explosion_e_02;
		explosion[11].picture_id[3] = R.drawable.explosion_e_02;
		explosion[11].picture_id[4] = R.drawable.explosion_e_03;
		explosion[11].picture_id[5] = R.drawable.explosion_e_03;
		explosion[11].picture_id[6] = R.drawable.explosion_e_04;
		explosion[11].picture_id[7] = R.drawable.explosion_e_04;
		explosion[11].picture_id[8] = R.drawable.explosion_e_05;
		explosion[11].picture_id[9] = R.drawable.explosion_e_05;
		explosion[11].image = new My2DImage[explosion[11].picture_num];
		for (int i=0; i<explosion[11].picture_num; i++) {
			explosion[11].image[i] = new My2DImage(explosion[11].width,explosion[11].height,true);
			explosion[11].image[i].load(gl, context, explosion[11].picture_id[i]);
		}
		*/

	}
} 
