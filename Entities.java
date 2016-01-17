package com.tubigames.galaxy.shooter.hd;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context; 

public class Entities {
	
	float hd = OpenGLRenderer.hd;
	
	int npc_num = 165;
	
	public class Npc_Image {
		public int image_num = 1;
		public My2DImage[] image; // az entitás képfájljának id-je a res/drawables/ könyvtárból
		public int width = 0; // a kép szélessége
		public int height = 0; // a kép magassága		
	}
	
	// ellenséges hajók és lények tulajdonságainak listája
	public class Npc {
		
		public String name = ""; // entitás neve 
		public int gfx; // az entitás kép sorszáma az NPC_IMAGE classból
		public float[] colorRGBA = { 1, 1, 1, 1 };
		/* robbanás típus sorszáma
		 * 0 = no effect
		 * 1 = basic explosion
		 * 2 = extended explosion
		 * 3 = monster die effect
		 */
		public int die_effect = 0; 
		public int die_sound = 7; 
		public int vibration = -1;
		public int power = 1;
		
		public int width = 0; // a kép szélessége
		public int height = 0; // a kép magassága
		
		public int hp_max = 0; // maximális életerő / structure, 0 = indestructable / enviromental
		public int shield_max = 0; // maximális pajzs
		public float recharge = 0; // pajzs visszatöltődés 10 másodpercenként
		public float speed = 0; // sebesség
	
		/* Weapon:
		 * -1 = no weapon
		 */
		public int weapon = -1;
		public int weapon_sound = 19;
		public int damage = 0; // sebzés, ha nem a fegyvertípusból számolódik
		public int damage_type = 0; // 0 = normal, 1 = shield only, 2 = 50% penetrate to hull, 3 = emp for 5 sec 
		
		public float rate_of_fire = 2000.0f; // tüzelési sebesség, ennyi millisecundumonként lő
		public float bullet_speed = 6.0f; // lövedék sebessége
		
		public int disruptor = -1;
	
		public int bullet_num = 1; // max 4
		public int[][] weapon_pos = { // if bulletnum > 1, use these weapon coordinates to launch multiple bullets
				{ 0, 0 },
				{ 0, 0 },
				{ 0, 0 },
				{ 0, 0 },
		};
		
		public int collision_damage = 0; // ütközési sebzés
		public int collision_damage_type = 0; // 0 = normal, 1 = shield only, 2 = 50% penetrate to hull, 3 = emp for 5 sec
		public boolean penetrate = false; // penetrate collision damage through invulnerability
		public boolean boss = false;
		public boolean mineable = false;
		public float rotator = 0; // 5 = normal
		public boolean rotate_towards = false;
		public boolean aimed = false;
		public boolean earthling = false;
		public boolean mine = false;
		public boolean frozable = true;
		public boolean gravitable = true;
		public boolean shieldonly = false;	// colors the hp bar as the shield bar
		public int drop = -1;
		public int drop_chance = 0; // százalékos esély, hogy a fenti objektumot dobja halálakor 0-100
		public int lyvalue = 0;
		
		public int max_in_row = 0;
		public int row_chance = 0;
		public int pattern_group = 0;

	}
	
	public class Pickups
	{
		public String name = "";
		public String description = "";

		// permanent bonus
		public int hp = 0;
		public int shield = 0;
		public int ly = 0;
		// special module to use
		public int spec = -1;
		// trap damage
		public int damage = 0;
		// quest item
		public int questitem = 0;
		// properties
		public int gfx; // az entitás kép sorszáma az NPC_IMAGE classból
		public int anim = -1;
		public float[] colorRGBA = { 1, 1, 1, 1 };
		public int die_effect = 0; 
		public int width = 0; // a kép szélessége
		public int height = 0; // a kép magassága
		public int sound = -1;
	}
	
	public class Npc_Special
	{
		public int image_id;
		public int sound = 0;
		public int vibration = -1;
		public int cooldown = 30;
		public int width = 0;
		public int height = 0;
		public int minus = 0;
		public int rangex = 0;
		public int rangey = 0;
		public int damage = 0;
		public int duration = 0;
		public int type = 0;
	}

	public int[][] gfx_list = {
			//0 - 9
			{ R.drawable.activemissions, 2, 2 },
			//1 - first
			{ R.drawable.npc_pirate_fighter, 70, 92 },
			{ R.drawable.npc_pirate_elite, 70, 88 },
			{ R.drawable.npc_pirate_captain, 72, 92 },
			{ R.drawable.npc_bomber, 90, 80 },
			{ R.drawable.npc_hunter, 84, 96 },
			{ R.drawable.npc_hunter_head, 114, 130 },
			{ R.drawable.npc_predator, 82, 96 },
			{ R.drawable.npc_bomber_xl, 170, 170 },
			{ R.drawable.npc_gorgo_fighter, 80, 110 },
			//10 - 19
			{ R.drawable.npc_gorgo_elite, 86, 116 },
			{ R.drawable.npc_gorgo_punisher, 86, 116 },
			{ R.drawable.npc_gorgo_punisher_head, 116, 160 },
			{ R.drawable.npc_gorgo_mothership, 180, 200 },
			{ R.drawable.npc_thargoid_fighter, 80, 70 },
			{ R.drawable.npc_thargoid_warship, 100, 90 },
			{ R.drawable.npc_thargoid_xtreme, 100, 88 },
			{ R.drawable.npc_taurian_carrier_pod, 90, 90 },
			{ R.drawable.npc_taurian_carrier_dock, 120, 214 },
			{ R.drawable.npc_taurian_carrier, 140, 298 },
			//20 - 29
			{ R.drawable.npc_taurian_carrier_xl, 200, 428 },
			{ R.drawable.npc_taurian_warship_pod, 100, 100 },
			{ R.drawable.npc_taurian_warship_dock, 64, 150 },
			{ R.drawable.npc_taurian_warship, 140, 302 },
			{ R.drawable.npc_taurian_antimatter_pod, 110, 110 },
			{ R.drawable.npc_taurian_antimatter_dock, 120, 182 },
			{ R.drawable.npc_taurian_antimatter, 150, 314 },
			{ R.drawable.npc_taurian_commandpod, 94, 94 },
			{ R.drawable.npc_xenio_fighter, 70, 104 },
			{ R.drawable.npc_xenio_elite, 70, 104 },
			//30 - 39
			{ R.drawable.npc_xenio_infected, 74, 108 },
			{ R.drawable.npc_klaxorn_fighter, 86, 122 },
			{ R.drawable.npc_klaxorn_elite, 90, 142 },
			{ R.drawable.npc_klaxorn_mothership, 146, 208 },
			{ R.drawable.npc_klaxorn_mothership_xl, 210, 232 },
			{ R.drawable.npc_qvantor_elite_cruiser, 108, 104 },
			{ R.drawable.npc_qvantor_rocket_cruiser, 90, 88 },
			{ R.drawable.npc_qvantor_rocket_center, 136, 132 },
			{ R.drawable.npc_qvantor_commander, 110, 104 },
			{ R.drawable.npc_qvantor_extreme_xl, 180, 172 },
			//40 - 49
			{ R.drawable.npc_explorer, 74, 98 },
			{ R.drawable.npc_bounty_hunter, 90, 120 },
			{ R.drawable.npc_dreadnought, 110, 124 },
			{ R.drawable.npc_mineasteroid_empty, 300, 300 },
			{ R.drawable.npc_huge_asteroid, 494, 486 },
			{ R.drawable.npc_blue_sun, 816, 816 },
			{ R.drawable.npc_purpleplanet2, 348, 346 },
			{ R.drawable.npc_shiningstar2, 116, 98 },
			{ R.drawable.npc_laser_mine, 86, 78 },
			{ R.drawable.npc_asteroidfield_small, 288, 326 },
			//50 - 59
			{ R.drawable.npc_shiningstar3, 264, 188 },
			{ R.drawable.npc_emp_mine, 72, 64 },
			{ R.drawable.npc_executer, 90, 150 },
			{ R.drawable.npc_supporter, 68, 144 },
			{ R.drawable.npc_hive_commander, 90, 186 },
			{ R.drawable.npc_hive_commander_lead, 120, 250 },
			{ R.drawable.npc_shiningstar4, 272, 254 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.npc_pirate_commander, 120, 160 },
			//60 - 69
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.npc_predator_boss, 130, 134 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.npc_taurian_warship_boss, 170, 272 },
			{ R.drawable.npc_qvantor_commander_boss, 150, 144 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			//70 - 79
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			//80 - 89
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.npc_silla_boss, 200, 214 },
			{ R.drawable.npc_spacejunk2, 218, 238 },	
			//90 - 99
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.npc_shiningsun, 224, 188 },	
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.npc_fluxor_protector_child, 70, 64 },
			//100 - 109
			{ R.drawable.npc_fluxor_protector_adult, 86, 78 },
			{ R.drawable.npc_fluxor_protector_elder, 120, 110 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.npc_zent_fighter, 100, 96 },
			{ R.drawable.npc_zent_elite, 80, 78 },
			{ R.drawable.npc_zent_stalker, 110, 106 },
			{ R.drawable.npc_zent_xtreme_xl, 170, 170 },
			{ R.drawable.npc_cobra_fighter, 60, 90 },
			{ R.drawable.npc_cobra_elite, 70, 104 },
			{ R.drawable.npc_cobra_destroyer, 80, 122 },
			//110 - 119
			{ R.drawable.npc_cobra_destroyer_captain, 112, 170 },
			{ R.drawable.npc_cobra_superior, 170, 250 },
			{ R.drawable.npc_silla_hunter, 112, 100 },
			{ R.drawable.npc_silla_destroyer, 110, 118 },
			{ R.drawable.npc_silla_commandship, 124, 124 },
			{ R.drawable.npc_ufo, 100, 100 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			//120 - 129
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.pickup_ly, 84, 98 },
			{ R.drawable.npc_mineasteroid_medium, 132, 118 },
			{ R.drawable.npc_mineasteroid_large, 150, 132 },
			{ R.drawable.npc_mineasteroid_xl, 192, 168 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.npc_meteorlarge, 110, 112 },
			//130-139
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.npc_anomalialyuk, 170, 172 },
			{ R.drawable.npc_bluemeteor, 90, 146 },
			{ R.drawable.npc_redmeteor, 110, 178 },
			{ R.drawable.npc_anomalia, 268, 290 },
			{ R.drawable.npc_anomalia2, 192, 162 },
			{ R.drawable.npc_spacejunk, 264, 228 },
			{ R.drawable.npc_asteroid2, 116, 194 },
			{ R.drawable.npc_asteroidfield, 512, 864 },
			{ R.drawable.npc_bigbrownplanet, 500, 500 },
			//140-149
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.npc_bigsaturnplanet, 512, 216 },
			{ R.drawable.npc_bigyellowplanet, 306, 306 },
			{ R.drawable.npc_blackhole200px, 200, 200 },
			{ R.drawable.npc_blueplanet, 400, 400 },
			{ R.drawable.npc_blueplanet2, 310, 316 },
			{ R.drawable.npc_brightstars, 160, 138 },
			{ R.drawable.npc_darkmoon, 200, 200 },
			{ R.drawable.activemissions, 2, 2 },
			{ R.drawable.npc_blackhole2, 204, 198 },
			//150-159
			{ R.drawable.npc_lavaplanet, 362, 362 },
			{ R.drawable.npc_lightmoon, 350, 316 },
			{ R.drawable.npc_mediumblueplanet, 250, 250 },
			{ R.drawable.npc_mediumvenusplanet, 728, 728 },
			{ R.drawable.npc_bluegalaxy, 158, 162 },			//154
			{ R.drawable.npc_bigredgalaxy, 158, 162 },			//155
			{ R.drawable.npc_purpleplanet, 256, 256 },
			{ R.drawable.npc_redplanet, 240, 240 },
			{ R.drawable.npc_smallmarsplanet, 192, 192 },
			{ R.drawable.activemissions, 2, 2 },
			//160-169
			{ R.drawable.npc_sun, 192, 200 },
			{ R.drawable.npc_sun2, 472, 482 },
			{ R.drawable.npc_sun3, 734, 730 },
			{ R.drawable.npc_yellowplanet, 350, 350 },		//163
			{ R.drawable.pickup_ly2, 84, 98 },
			{ R.drawable.bullet_aliendisruptor, 202, 1114 },	//165
			{ R.drawable.bullet_bigbeamblue, 62, 1175 },				//166
			{ R.drawable.bullet_bigbeampurple, 62, 1175 },			//167
			{ R.drawable.npc_spacewreck, 236, 174 },			//168
			{ R.drawable.npc_meteorsmall, 76, 68 },		//169
			//170-179
			{ R.drawable.bullet_bigbeamyellow, 62, 1175 },			//170
			{ R.drawable.drone_small, 46, 52 },			//171
			{ R.drawable.drone_extreme_small, 46, 52 },			//172
							//173
	}; 

	public Npc[] npc; 
	public Npc_Image[] gfx; 
	public Pickups[] pickup;
	public Npc_Special[] npc_special;
	
	public int[][] npc_group = {
			/*group  0*/ { 0, 4, 10, 43, 56, 57, 104, 69, 70, 49 }, // beginner 
			/*group  1*/ { 1, 5, 10, 43, 57, 104, 69, 70, 105, 49 }, // beginner 
			/*group  2*/ { 1, 2, 5, 7, 11, 15, 18, 19, 30, 43, 44, 57, 105, 69, 70, 71 }, // easy 
			/*group  3*/ { 2, 7, 11, 16, 18, 20, 30, 31, 43, 44, 57, 105, 9, 69, 71, 46, 50, 52 }, // easy  
			/*group  4*/ { 2, 7, 12, 16, 17, 22, 23, 50, 24, 29, 31, 32, 33, 37, 38, 43, 44, 46, 47, 50, 53, 57, 62, 105, 106, 9, 14, 69, 71 }, // medium  
			/*group  5*/ { 2, 7, 12, 13, 16, 17, 22, 23, 24, 50, 26, 29, 31, 32, 33, 34, 38, 40, 43, 44, 46, 47, 49, 50, 52, 53, 56, 57, 62, 105, 106, 9, 14, 21, 69, 71, 72 }, // medium  
			/*group  6*/ { 3, 6, 8, 12, 13, 14, 17, 26, 27, 28, 29, 31, 32, 34, 35, 36, 38, 39, 40, 41, 43, 44, 47, 48, 50, 51, 53, 54, 58, 59, 62, 63, 105, 106, 14, 21, 25, 71, 72 }, // hard  

			/*group  7*/ { 3, 6, 8, 12, 13, 14, 17, 26, 27, 28, 29, 32, 33, 35, 36, 39, 40, 41, 43, 44, 47, 48, 49, 51, 53, 54, 58, 59, 62, 63, 105, 106, 21, 25, 36, 69, 70, 72 }, // hard  
			/*group  8*/ { 3, 6, 8, 14, 17, 26, 27, 28, 29, 32, 35, 40, 41, 44, 48, 51, 53, 54, 55, 58, 59, 60, 62, 63, 106, 36, 42, 72 }, // ultimate  
			/*group  9*/ { 5, 6, 8, 14, 17, 26, 27, 28, 29, 32, 39, 40, 41, 44, 48, 51, 54, 55, 58, 59, 60, 62, 63, 106, 36, 42, 71, 72 }, // ultimate  
	};
	public int[][] boss_group = {
			/*group  0*/ { 9, 14 }, //  
			/*group  1*/ { 9, 14, 21 }, //  
			/*group  2*/ { 14, 25 }, // 
			/*group  3*/ { 25, 36, 42 }, //   
			/*group  4*/ { 25, 36, 42 }, // medium  
			/*group  5*/ { 36, 42, 55 }, //   
			/*group  6*/ { 36, 42, 55 }, //   

			/*group  7*/ { 42, 55, 60 }, // hard  
			/*group  8*/ { 60, 64 }, // ultimate  
			/*group  9*/ { 60, 64 }, // ultimate  
	};
	public int[][] pattern_group = {
			/*group  0*/ { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, // sík egyenes vonal, meteorok, stb.
			/*group  1*/ { 2, 3, 4, 5, 6, 7, 8, 9 }, // sík egynes vonal, de szélek kizárva!
			/*group  2*/ { 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 21, 22, 25, 26, 29, 30, 31, 32, 35, 36, 37, 38, 65, 66 }, // áthaladók, egyszerűsített útvonal
			/*group  3*/ { 19, 20, 23, 24, 27, 28, 39, 40, 41, 42, 43, 44 }, // áthaladók, bonyolított útvonal
			/*group  4*/ { 33, 34, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63 }, // pályán maradók, összes  
			/*group  5*/ { 2,3,4,5,6,7,8,9,11,12,14,15,17,18,19,20,23,24,25,26,29,30,33,34,35,36,37,38,39,40,41,42,45,46,47,48,49,50,51,52,60,61,62,65,66 }, // vegyes útvonalak, biztonsági részen  
			/*group  6*/ { 3,4,5,6,7,8,13,16,19,20,23,24,27,28,31,32,39,40,43,44,53,54,55,56,57,58,59,62,63,65,66 }, // vegyes útvonalak, támadó jellegűek
			/*group  7*/ { 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,65,66 }, // vegyes, összes pattern
			/*group  8*/ { 33, 34, 45, 46, 47, 48, 49, 50, 51, 52, 57, 58, 59, 60, 61, 62 }, // mini boss-ok
			/*group  9*/ { 64, 67, 68 }, // óriás boss-ok  
	};
	public int[][] env_group = {
			/*group  0*/ { 73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,107,108,109,110,111,112,113,114 }, // összes díszítő elem
			/*group  1*/ { 66,67,68 }, // bányász aszteroidák
	};

	public Entities() {
		
	}
	
	public void loadimages(GL10 gl, Context context) {

		Log.w("SC2", "Entity LOAD called.");
		gfx = null;
		int gfx_max = 173;
		gfx = new Npc_Image[gfx_max]; // összes ellenség KÉP típus mennyisége
		
		for (int i=0; i<gfx_max; i++) {
			gfx[i] = new Npc_Image();
			gfx[i].image_num = 1;
			gfx[i].width = (int)((float)gfx_list[i][1]/Math.abs(hd-2.5f));		//(int)(hd*gfx_list[i][1]);
			gfx[i].height = (int)((float)gfx_list[i][2]/Math.abs(hd-2.5f));	//(int)(hd*gfx_list[i][2]);
			gfx[i].image = new My2DImage[gfx[i].image_num];
			gfx[i].image[0] = new My2DImage(gfx[i].width, gfx[i].height,true);
			gfx[i].image[0].load(gl, context, gfx_list[i][0]);
		}
		
	}
	
	public void load(Context context) {
		
		try {
	        InputStream is = context.getResources().openRawResource(R.raw.entities);
            InputStreamReader inputreader = new InputStreamReader(is);
            BufferedReader buffreader = new BufferedReader(inputreader);

    		Log.i("SC2", "File opened");
            String strLine = null;

		    int j = 0; int lineNum = 1;
		    while ((strLine = buffreader.readLine()) != null && j <= lineNum) {
		        
		    	if (j==0) { 
		    		String[] columns = {"0", ""};
		    		columns = strLine.split("\\s+", 2);
		    		lineNum = Integer.parseInt(columns[0]); 
		    		npc_num = lineNum;
		    		Log.i("SC2", "Total lines: " + Integer.toString(npc_num));
		    		npc = new Npc[npc_num];

		    	} else {
		    		Log.i("SC2", "Line loaded");

		    		String[] columns = {"x","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45" };
		    		columns = strLine.split("\\s+", 47);
		    		
		    		//for (int i=0; i<44; i++)
		    		//Log.i("SC2", "Teszt " + Integer.toString(i) + ": " + columns[i]);
		    		
		    		Log.i("SC2", "Line loaded");
		    		
		    		npc[j-1] = new Npc();
		    		npc[j-1].name = columns[0];
		    		npc[j-1].gfx = Integer.parseInt(columns[2]); 
		    		npc[j-1].die_effect = Integer.parseInt(columns[3]); 
		    		npc[j-1].die_sound = Integer.parseInt(columns[4]); 
		    		npc[j-1].vibration = Integer.parseInt(columns[5]); 
		    		npc[j-1].power = Integer.parseInt(columns[6]); 
		    		npc[j-1].width =  (int) (Float.parseFloat(columns[7]) / Math.abs(hd-2.5f)); 
		    		npc[j-1].height = (int) (Float.parseFloat(columns[8]) / Math.abs(hd-2.5f)); 
		    		npc[j-1].hp_max = Integer.parseInt(columns[9]); 
		    		npc[j-1].shield_max = Integer.parseInt(columns[10]); 
		    		npc[j-1].recharge = Float.parseFloat(columns[11]); 
		    		npc[j-1].speed = Float.parseFloat(columns[12]); 
		    		npc[j-1].weapon = Integer.parseInt(columns[13]); 
		    		npc[j-1].weapon_sound = Integer.parseInt(columns[14]); 
		    		npc[j-1].damage = Integer.parseInt(columns[15]); 
		    		npc[j-1].damage_type = Integer.parseInt(columns[16]); 
		    		npc[j-1].rate_of_fire = Float.parseFloat(columns[17]); 
		    		npc[j-1].bullet_speed = Float.parseFloat(columns[18]); 
		    		npc[j-1].disruptor = Integer.parseInt(columns[19]); 
		    		npc[j-1].bullet_num = Integer.parseInt(columns[20]); 
		    		npc[j-1].weapon_pos[0][0] = (int)(hd*Integer.parseInt(columns[21])); 
		    		npc[j-1].weapon_pos[0][1] = (int)(hd*Integer.parseInt(columns[22])); 
		    		npc[j-1].weapon_pos[1][0] = (int)(hd*Integer.parseInt(columns[23])); 
		    		npc[j-1].weapon_pos[1][1] = (int)(hd*Integer.parseInt(columns[24])); 
		    		npc[j-1].weapon_pos[2][0] = (int)(hd*Integer.parseInt(columns[25])); 
		    		npc[j-1].weapon_pos[2][1] = (int)(hd*Integer.parseInt(columns[26])); 
		    		npc[j-1].weapon_pos[3][0] = (int)(hd*Integer.parseInt(columns[27])); 
		    		npc[j-1].weapon_pos[3][1] = (int)(hd*Integer.parseInt(columns[28])); 
		    		npc[j-1].collision_damage = Integer.parseInt(columns[29]); 
		    		npc[j-1].collision_damage_type = Integer.parseInt(columns[30]); 
		    		npc[j-1].rotator = Float.parseFloat(columns[31]);
		    		npc[j-1].penetrate = Boolean.parseBoolean(columns[32]); 
		    		npc[j-1].boss = Boolean.parseBoolean(columns[33]); 
		    		npc[j-1].mineable = Boolean.parseBoolean(columns[34]); 
		    		npc[j-1].rotate_towards = Boolean.parseBoolean(columns[35]); 
		    		npc[j-1].aimed = Boolean.parseBoolean(columns[36]); 
		    		npc[j-1].mine = Boolean.parseBoolean(columns[37]); 
		    		npc[j-1].frozable = Boolean.parseBoolean(columns[38]); 
		    		npc[j-1].gravitable = Boolean.parseBoolean(columns[39]); 
		    		npc[j-1].shieldonly = Boolean.parseBoolean(columns[40]); 
		    		npc[j-1].drop = Integer.parseInt(columns[41]); 
		    		npc[j-1].drop_chance = Integer.parseInt(columns[42]); 
		    		npc[j-1].lyvalue = Integer.parseInt(columns[43]); 
		    		npc[j-1].max_in_row = Integer.parseInt(columns[44]); 
		    		npc[j-1].row_chance = Integer.parseInt(columns[45]); 
		    		npc[j-1].pattern_group = Integer.parseInt(columns[46]); 

		    		Log.i("SC2", "J: " + Integer.toString(j));
		    	}
		    	j++;
		    }

		    buffreader.close();
		    is.close();
		}
		catch  (Exception e) {  
		}
		
		// PICK UPS
		
		pickup = new Pickups[39];

		pickup[0] = new Pickups();
		pickup[0].name = "LY 300"; 
		pickup[0].description = "+300 LY";
		pickup[0].gfx = 123;
		pickup[0].die_effect = 0; 
		pickup[0].width = (int)(hd*56);
		pickup[0].height = (int)(hd*64);
		pickup[0].ly = 300;
		pickup[0].sound = 32;

		pickup[1] = new Pickups();
		pickup[1].name = "LY 500"; 
		pickup[1].description = "+500 LY";
		pickup[1].gfx = 123;
		pickup[1].die_effect = 0; 
		pickup[1].width = (int)(hd*56);
		pickup[1].height = (int)(hd*64);
		pickup[1].ly = 500;
		pickup[1].sound = 32;

		pickup[2] = new Pickups();
		pickup[2].name = "LY 1000"; 
		pickup[2].description = "+1000 LY";
		pickup[2].gfx = 164;
		pickup[2].die_effect = 0; 
		pickup[2].width = (int)(hd*56);
		pickup[2].height = (int)(hd*64);
		pickup[2].ly = 1000;
		pickup[2].sound = 32;

		pickup[3] = new Pickups();
		pickup[3].name = "LY 1500"; 
		pickup[3].description = "+1500 LY";
		pickup[3].gfx = 164;
		pickup[3].die_effect = 0; 
		pickup[3].width = (int)(hd*56);
		pickup[3].height = (int)(hd*64);
		pickup[3].ly = 1500;
		pickup[3].sound = 32;

		pickup[4] = new Pickups();
		pickup[4].name = "Shield 10"; 
		pickup[4].description = "+10 Shield";
		pickup[4].anim = 13;
		pickup[4].die_effect = 0; 
		pickup[4].width = (int)(hd*76);
		pickup[4].height = (int)(hd*46);
		pickup[4].shield = 10;
		pickup[4].sound = 32;

		pickup[5] = new Pickups();
		pickup[5].name = "Shield 20"; 
		pickup[5].description = "+20 Shield";
		pickup[5].anim = 13;
		pickup[5].die_effect = 0; 
		pickup[5].width = (int)(hd*76);
		pickup[5].height = (int)(hd*46);
		pickup[5].shield = 20;
		pickup[5].sound = 32;

		pickup[6] = new Pickups();
		pickup[6].name = "Shield 30"; 
		pickup[6].description = "+30 Shield";
		pickup[6].anim = 13;
		pickup[6].die_effect = 0; 
		pickup[6].width = (int)(hd*76);
		pickup[6].height = (int)(hd*46);
		pickup[6].shield = 30;
		pickup[6].sound = 32;

		pickup[7] = new Pickups();
		pickup[7].name = "Shield 40"; 
		pickup[7].description = "+40 Shield";
		pickup[7].anim = 13;
		pickup[7].die_effect = 0; 
		pickup[7].width = (int)(hd*76);
		pickup[7].height = (int)(hd*46);
		pickup[7].shield = 40;
		pickup[7].sound = 32;

		pickup[8] = new Pickups();
		pickup[8].name = "Hull 5"; 
		pickup[8].description = "+5 Hull";
		pickup[8].anim = 12;
		pickup[8].die_effect = 0; 
		pickup[8].width = (int)(hd*76);
		pickup[8].height = (int)(hd*46);
		pickup[8].hp = 5;
		pickup[8].sound = 32;

		pickup[9] = new Pickups();
		pickup[9].name = "Hull 10"; 
		pickup[9].description = "+10 Hull";
		pickup[9].anim = 12;
		pickup[9].die_effect = 0; 
		pickup[9].width = (int)(hd*76);
		pickup[9].height = (int)(hd*46);
		pickup[9].hp = 10;
		pickup[9].sound = 32;

		pickup[10] = new Pickups();
		pickup[10].name = "Hull 20"; 
		pickup[10].description = "+20 Hull";
		pickup[10].anim = 12;
		pickup[10].die_effect = 0; 
		pickup[10].width = (int)(hd*76);
		pickup[10].height = (int)(hd*46);
		pickup[10].hp = 20;
		pickup[10].sound = 32;

		pickup[11] = new Pickups();
		pickup[11].name = "Hull 30"; 
		pickup[11].description = "+30 Hull";
		pickup[11].anim = 12;
		pickup[11].die_effect = 0; 
		pickup[11].width = (int)(hd*76);
		pickup[11].height = (int)(hd*46);
		pickup[11].hp = 30;
		pickup[11].sound = 32;

		pickup[12] = new Pickups();
		pickup[12].name = "Instant Shield 1"; 
		pickup[12].description = "Instant Shield";
		pickup[12].anim = 14;
		pickup[12].die_effect = 0; 
		pickup[12].width = (int)(hd*76);
		pickup[12].height = (int)(hd*46);
		pickup[12].spec = 4;

		pickup[13] = new Pickups();
		pickup[13].name = "Instant Shield 2"; 
		pickup[13].description = "Instant Shield II";
		pickup[13].anim = 14;
		pickup[13].die_effect = 0; 
		pickup[13].width = (int)(hd*76);
		pickup[13].height = (int)(hd*46);
		pickup[13].spec = 5;

		pickup[14] = new Pickups();
		pickup[14].name = "Instant Shield 3"; 
		pickup[14].description = "Instant Shield III";
		pickup[14].anim = 14;
		pickup[14].die_effect = 0; 
		pickup[14].width = (int)(hd*76);
		pickup[14].height = (int)(hd*46);
		pickup[14].spec = 6;
		
		pickup[15] = new Pickups();
		pickup[15].name = "Time Warp"; 
		pickup[15].description = "Slow Down";
		pickup[15].anim = 14;
		pickup[15].die_effect = 0; 
		pickup[15].width = (int)(hd*76);
		pickup[15].height = (int)(hd*46);
		pickup[15].spec = 3;

		pickup[16] = new Pickups();
		pickup[16].name = "Plasma Waves 1"; 
		pickup[16].description = "Plasma Waves";
		pickup[16].anim = 14;
		pickup[16].die_effect = 0; 
		pickup[16].width = (int)(hd*76);
		pickup[16].height = (int)(hd*46);
		pickup[16].spec = 28;

		pickup[17] = new Pickups();
		pickup[17].name = "Plasma Waves 2"; 
		pickup[17].description = "Plasma Waves II";
		pickup[17].anim = 14;
		pickup[17].die_effect = 0; 
		pickup[17].width = (int)(hd*76);
		pickup[17].height = (int)(hd*46);
		pickup[17].spec = 29;
		
		pickup[18] = new Pickups();
		pickup[18].name = "Plasma Waves 3"; 
		pickup[18].description = "Plasma Waves III";
		pickup[18].anim = 14;
		pickup[18].die_effect = 0; 
		pickup[18].width = (int)(hd*76);
		pickup[18].height = (int)(hd*46);
		pickup[18].spec = 30;
		
		pickup[19] = new Pickups();
		pickup[19].name = "EMP 1"; 
		pickup[19].description = "EMP";
		pickup[19].anim = 14;
		pickup[19].die_effect = 0; 
		pickup[19].width = (int)(hd*76);
		pickup[19].height = (int)(hd*46);
		pickup[19].spec = 10;

		pickup[20] = new Pickups();
		pickup[20].name = "EMP 2"; 
		pickup[20].description = "EMP II";
		pickup[20].anim = 14;
		pickup[20].die_effect = 0; 
		pickup[20].width = (int)(hd*76);
		pickup[20].height = (int)(hd*46);
		pickup[20].spec = 11;

		pickup[21] = new Pickups();
		pickup[21].name = "EMP 3"; 
		pickup[21].description = "EMP III";
		pickup[21].anim = 14;
		pickup[21].die_effect = 0; 
		pickup[21].width = (int)(hd*76);
		pickup[21].height = (int)(hd*46);
		pickup[21].spec = 12;

		pickup[22] = new Pickups();
		pickup[22].name = "Disruptor 1"; 
		pickup[22].description = "Disruptor";
		pickup[22].anim = 14;
		pickup[22].die_effect = 0; 
		pickup[22].width = (int)(hd*76);
		pickup[22].height = (int)(hd*46);
		pickup[22].spec = 19;
		
		pickup[23] = new Pickups();
		pickup[23].name = "Disruptor 2"; 
		pickup[23].description = "Disruptor II";
		pickup[23].anim = 14;
		pickup[23].die_effect = 0; 
		pickup[23].width = (int)(hd*76);
		pickup[23].height = (int)(hd*46);
		pickup[23].spec = 20;
		
		pickup[24] = new Pickups();
		pickup[24].name = "Disruptor 3"; 
		pickup[24].description = "Disruptor III";
		pickup[24].anim = 14;
		pickup[24].die_effect = 0; 
		pickup[24].width = (int)(hd*76);
		pickup[24].height = (int)(hd*46);
		pickup[24].spec = 21;

		pickup[25] = new Pickups();
		pickup[25].name = "Gravity 1"; 
		pickup[25].description = "Gravity";
		pickup[25].anim = 14;
		pickup[25].die_effect = 0; 
		pickup[25].width = (int)(hd*76);
		pickup[25].height = (int)(hd*46);
		pickup[25].spec = 16;

		pickup[26] = new Pickups();
		pickup[26].name = "Gravity 2"; 
		pickup[26].description = "Gravity II";
		pickup[26].anim = 14;
		pickup[26].die_effect = 0; 
		pickup[26].width = (int)(hd*76);
		pickup[26].height = (int)(hd*46);
		pickup[26].spec = 17;

		pickup[27] = new Pickups();
		pickup[27].name = "Gravity 3"; 
		pickup[27].description = "Gravity III";
		pickup[27].anim = 14;
		pickup[27].die_effect = 0; 
		pickup[27].width = (int)(hd*76);
		pickup[27].height = (int)(hd*46);
		pickup[27].spec = 18;

		pickup[28] = new Pickups();
		pickup[28].name = "Cryogenic 1"; 
		pickup[28].description = "Cooldown";
		pickup[28].anim = 14;
		pickup[28].die_effect = 0; 
		pickup[28].width = (int)(hd*76);
		pickup[28].height = (int)(hd*46);
		pickup[28].spec = 39;

		pickup[29] = new Pickups();
		pickup[29].name = "Cryogenic 2"; 
		pickup[29].description = "Cooldown II";
		pickup[29].anim = 14;
		pickup[29].die_effect = 0; 
		pickup[29].width = (int)(hd*76);
		pickup[29].height = (int)(hd*46);
		pickup[29].spec = 40;

		pickup[30] = new Pickups();
		pickup[30].name = "Cryogenic 3"; 
		pickup[30].description = "Cooldown III";
		pickup[30].anim = 14;
		pickup[30].die_effect = 0; 
		pickup[30].width = (int)(hd*76);
		pickup[30].height = (int)(hd*46);
		pickup[30].spec = 41;

		pickup[31] = new Pickups();
		pickup[31].name = "Cloaking 1"; 
		pickup[31].description = "Invulnerability";
		pickup[31].anim = 14;
		pickup[31].die_effect = 0; 
		pickup[31].width = (int)(hd*76);
		pickup[31].height = (int)(hd*46);
		pickup[31].spec = 25;

		pickup[32] = new Pickups();
		pickup[32].name = "Cloaking 2"; 
		pickup[32].description = "Invulnerability II";
		pickup[32].anim = 14;
		pickup[32].die_effect = 0; 
		pickup[32].width = (int)(hd*76);
		pickup[32].height = (int)(hd*46);
		pickup[32].spec = 26;

		pickup[33] = new Pickups();
		pickup[33].name = "Cloaking 3"; 
		pickup[33].description = "Invulnerability III";
		pickup[33].anim = 14;
		pickup[33].die_effect = 0; 
		pickup[33].width = (int)(hd*76);
		pickup[33].height = (int)(hd*46);
		pickup[33].spec = 27;

		pickup[34] = new Pickups();
		pickup[34].name = "Crystal of Power"; 
		pickup[34].description = "Power Up";
		pickup[34].anim = 14;
		pickup[34].die_effect = 0; 
		pickup[34].width = (int)(hd*76);
		pickup[34].height = (int)(hd*46);
		pickup[34].spec = 32;

		pickup[35] = new Pickups();
		pickup[35].name = "Crystal of Destruction"; 
		pickup[35].description = "Nuke";
		pickup[35].anim = 14;
		pickup[35].die_effect = 0; 
		pickup[35].width = (int)(hd*76);
		pickup[35].height = (int)(hd*46);
		pickup[35].spec = 31;

		// trap nem kell
		pickup[36] = new Pickups();
		pickup[36].name = "Turret Upgrade"; 
		pickup[36].description = "Extra Turret";
		pickup[36].anim = 14;
		pickup[36].die_effect = 0; 
		pickup[36].width = (int)(hd*76);
		pickup[36].height = (int)(hd*46);
		pickup[36].spec = 48;

		pickup[37] = new Pickups();
		pickup[37].name = "Modifier Upgrade"; 
		pickup[37].description = "Extra Modifier";
		pickup[37].anim = 14;
		pickup[37].die_effect = 0; 
		pickup[37].width = (int)(hd*76);
		pickup[37].height = (int)(hd*46);
		pickup[37].spec = 49;

		pickup[38] = new Pickups();
		pickup[38].name = "No Limits"; 
		pickup[38].description = "HAND OF GOD";
		pickup[38].anim = 14;
		pickup[38].die_effect = 0; 
		pickup[38].width = (int)(hd*76);
		pickup[38].height = (int)(hd*46);
		pickup[38].spec = 50;
		
		npc_special = new Npc_Special[5];
		
		npc_special[0] = new Npc_Special(); 
		npc_special[0].image_id = 165;
		npc_special[0].width = (int)(hd*133);
		npc_special[0].height = (int)(hd*735);
		npc_special[0].sound = 39;
		npc_special[0].vibration = -1;
		npc_special[0].cooldown = 3;
		//npc_special[0].cooldown = 8;
		npc_special[0].rangex = (int)(hd*90);
		npc_special[0].rangey = (int)(hd*720);
		npc_special[0].type = 2;	// 0 = elhalvanyul, 1 = megvillan, 2 = osszemegy
		npc_special[0].damage = 20;
		npc_special[0].duration = 1000;

		npc_special[1] = new Npc_Special(); 
		npc_special[1].image_id = 166;
		npc_special[1].width = (int)(hd*41);
		npc_special[1].height = (int)(hd*776);
		npc_special[1].minus = 30;
		npc_special[1].sound = 40;
		npc_special[1].vibration = -1;
		npc_special[1].cooldown = 3;
		npc_special[1].rangex = (int)(hd*40);
		npc_special[1].rangey = (int)(hd*800);
		npc_special[1].type = 1;	// 0 = elhalvanyul, 1 = megvillan, 2 = osszemegy
		npc_special[1].damage = 12;
		npc_special[1].duration = 1000;

		npc_special[2] = new Npc_Special(); 
		npc_special[2].image_id = 167;
		npc_special[2].width = (int)(hd*41);
		npc_special[2].height = (int)(hd*776);
		npc_special[2].minus = 30;
		npc_special[2].sound = 40;
		npc_special[2].vibration = -1;
		npc_special[2].cooldown = 4;
		npc_special[2].rangex = (int)(hd*40);
		npc_special[2].rangey = (int)(hd*800);
		npc_special[2].type = 1;	// 0 = elhalvanyul, 1 = megvillan, 2 = osszemegy
		npc_special[2].damage = 15;
		npc_special[2].duration = 1000;

		npc_special[3] = new Npc_Special(); 
		npc_special[3].image_id = 165;
		npc_special[3].width = (int)(hd*133);
		npc_special[3].height = (int)(hd*735);
		npc_special[3].sound = 39;
		npc_special[3].vibration = -1;
		npc_special[3].cooldown = 4;
		//npc_special[3].cooldown = 8;
		npc_special[3].rangex = (int)(hd*90);
		npc_special[3].rangey = (int)(hd*800);
		npc_special[3].type = 2;	// 0 = elhalvanyul, 1 = megvillan, 2 = osszemegy
		npc_special[3].damage = 12;
		npc_special[3].duration = 1000;

		npc_special[4] = new Npc_Special(); 
		npc_special[4].image_id = 170;
		npc_special[4].width = (int)(hd*41);
		npc_special[4].height = (int)(hd*776);
		npc_special[4].minus = 80;
		npc_special[4].sound = 40;
		npc_special[4].vibration = -1;
		npc_special[4].cooldown = 4;
		//npc_special[4].cooldown = 5;
		npc_special[4].rangex = (int)(hd*40);
		npc_special[4].rangey = (int)(hd*800);
		npc_special[4].type = 1;	// 0 = elhalvanyul, 1 = megvillan, 2 = osszemegy
		npc_special[4].damage = 15;
		npc_special[4].duration = 1000;

	}
	
	public void release(GL10 gl) {
		for (int i=0;i<gfx.length;i++) {
			for (int j=0;j<gfx[i].image_num;j++) {
				gfx[i].image[j].release(gl);
				Log.w("Space Shooter", "Entity release called: " + gfx[i].image[j].toString());
			}
		}
	}
} 
