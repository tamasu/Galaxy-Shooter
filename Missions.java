package com.tubigames.galaxy.shooter.hd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import android.content.Context;
import android.util.Log;

public class Missions {

	final public int Total_Missions = 1;

	int min_waves = 8; // minimális hullámok száma pályánként
	int max_waves = 8; // maximális hullámok száma pályánként
	float waves_min_delay = 0.0f; // min késleltetés 2 hullám között másodpercben
	final float waves_max_delay = 0.5f; // max késleltetés 2 hullám között másodpercben
	final float npc_min_delay = 0.25f; // min késleltetés 2 creature között másodpercben
	float npc_max_delay = 1.25f; // max késleltetés 2 creature között másodpercben
	float npc_row_delay = 1.0f; // min késleltetés 2 creature között másodpercben
	final int min_npc_type = 5; // min creature type per level
	final int max_npc_type = 8; // max creature type per level
	final int strong_vs_many = 70;	// kevés erős lény (1) vagy sok gyenge lény (100) jöjjön inkább átlagos érték
	final int strong_vs_many_random = 20; // random kitérés a strong vs many értékhez +/- irányban
	final float waves_strength_avg_increase = 5; // hullámok átlagos erősödése %-ban megadva
	final int waves_strength_random = 50; // hullámok átlagos erősödése % értékének +/- random értéke %-ban

	public int nebula_type = 0;
	
	// maximum 3 lines
	final public String[][] Dialogue_list = {

			// BASIC TUTORIAL
			// 0
			{ 	"Welcome back on board, Commander!",
				"Select a galaxy you want to travel to!" },
			// 1
			{ 	"Travelling between galaxies costs",
				"Light Years (LY). If you have gathered",
				"enough, you can unlock a new galaxy." },
			// 2
			{ 	"Finish a mission to gather more LY,",
				"or enter the Space Station ",
				"to upgrade your ship!" },
			// 3
			{ 	"Buy better weapons and mods to ",
				"make your ship more effective." },
			// 4
			{ 	"Equipped Special modules can be",
				"used any time during a mission.",
				"Each have it's unique power." },
			// 5
			{ 	"Travel to new Galaxies to unlock",
				"advanced weapons and upgrades." },
			// 6
			{ 	"If you find a mission too hard,", 
				"finish another mission first,",
				"or switch difficulty level!" },
				
			// STORY START
			// 7 
			{ 	"The moon was created when a rock the",
				"size of Mars slammed into Earth." },
			// 8 
			{ 	"The surface of Venus is hot enough",
				"to melt lead due to its runaway",
				"greenhouse atmosphere." },
			// 9 
			{ 	"Although Mercury has no atmosphere at",
				"all, there is a faint magnetic field",
				"around the planet." },
			// 10
			{ 	"Commander! You must reach Baby Boom",
				"Galaxy to investigate the source of",
				"the mysterious signals." },
			// 11
			{ 	"Baby Boom Galaxy is in the undiscovered",
				"region of the Universe. Noone knows",
				"what we find out there." },
			// 12
			{ 	"Collect more Light Years, Commander!",
				"To gain access to more upgrades,",
				"you must unlock new galaxies." },
			// 13
			{ 	"Unlike the Earth, the Sun is completely",
				"gaseous, there is no solid surface",
				"on the Sun." },
			// 14 na
			{ 	"",
				"",
				"" },
			// 15 na
			{ 	"",
				"",
				"" },
			// 16 na
			{ 	"",
				"",
				"" },
			// 17 na
			{ 	"",
				"",
				"" },
			// 18 na
			{ 	"",
				"",
				"" },
			// 19 na
			{ 	"",
				"",
				"" },
			// 20 na
			{ 	"",
				"",
				"" },
			// 21 na
			{ 	"",
				"",
				"" },
			// 22
			{ 	"Shielded enemy ships are more dangerous",
				"in general, because they can regenerate",
				"back their power in a short time." },
			// 23
			{ 	"Signal is getting stronger and stronger.",
				"Keep going towards Baby Boom Galaxy!" },
			// 24
			{ 	"Some aliens can penetrate through",
				"your ship's shield, and damage",
				"the hull directly." },
			// 25
			{ 	"Commander! The strange signal from",
				"Baby Boom Galaxy confuses the hostile",
				"alien ships! We must find the source!" },
			// 26 na
			{ 	"",
				"",
				"" },
			// 27 na
			{ 	"",
				"",
				"" },
			// 28
			{ 	"A meteoroid is a chunk of space rock.",
				"If a meteoroid is more than 10 metres in ",
				"diameter,it is classified as an asteroid" },
			// 29 na
			{ 	"",
				"",
				"" },
			// 30
			{ 	"There is a strange energy alien life",
				"form that can deactivate your ship",
				"for a short time, like EMP modules." },
			// 31
			{ 	"To take down larger and stronger",
				"hostile ships, use torpedos or other",
				"special modules!" },
			// 32 na
			{ 	"",
				"",
				"" },
			// 33
			{ 	"Mining laser can be used on some",
				"asteroids to harvest materials. From",
				"those, you will gain extra Light Years." },
			// 34
			{ 	"Collision damage can quickly destroy",
				"your ship. Try to avoid direct contact",
				"with hostile ships at all cost!" },
			// 35 na 
			{ 	"",
				"",
				"" },
			// 36 na
			{ 	"",
				"",
				"" },
			// 37
			{ 	"When stars become more luminous,",
				"they expand outward becoming red",
				"giants." },
			// 38 
			{ 	"Binary stars are two stars orbit a common",
				"center of gravity. There are other",
				"systems with 3, 4 and even more stars." },
			// 39 
			{ 	"Space can become increasingly distorted",
				"as an object's mass gets larger." },
			// 40 na
			{ 	"",
				"",
				"" },
			// 41 na
			{ 	"",
				"",
				"" },
			// 42 na
			{ 	"",
				"",
				"" },
			// 43 na
			{ 	"",
				"",
				"" },
			// 44 
			{ 	"Black Holes are noisy. When stuff",
				"falls through the center of the event",
				"horizon there is a gurgling sound." },
			// 45 na
			{ 	"",
				"",
				"" },
			// 46 na
			{ 	"",
				"",
				"" },
			// 47 na
			{ 	"",
				"",
				"" },
			// 48
			{ 	"Our enemies are located the signal's",
				"source. From now on, they will try",
				"even harder stop us reaching it." },
			// 49 na
			{ 	"",
				"",
				"" },
			// 50 
			{ 	"Commander! I hope you didn't drink", 
				"much from that gaseous methyl alcohol",
				"we have seen few galaxies before." },
			// 51 na
			{ 	"",
				"",
				"" },
				
			// END OF FREE CONTENT
				
			// 52
			{ 	"",
				"",
				"" },
			// 53
			{ 	"",
				"",
				"" },
			// 54 RED GIANT  
			{ 	"All stars begin from clouds",
				"of cold molecular hydrogen",
				"that gravitationally collapse." },
			// 55
			{ 	"",
				"",
				"" },
			// 56 na
			{ 	"",
				"",
				"" },
			// 57
			{ 	"",
				"",
				"" },
			// 58
			{ 	"",
				"",
				"" },
			// 59
			{ 	"",
				"",
				"" },
			// 60
			{ 	"",
				"",
				"" },			
			// 61 na
			{ 	"",
				"",
				"" },
			// 62
			{ 	"",
				"",
				"" },
			// 63
			{ 	"",
				"",
				"" },
			// 64
			{ 	"",
				"",
				"" },
			// 65
			{ 	"",
				"",
				"" },
			// 66
			{ 	"",
				"",
				"" },
			// 67 BIG BLUE 
			{ 	"Class B stars are very luminous and",
				"blue. Their spectra have neutral",
				"helium and moderate hydrogen lines." },
			// 68
			{ 	"",
				"",
				"" },
			// 69
			{ 	"",
				"",
				"" },
			// 70 na
			{ 	"",
				"",
				"" },
			// 71
			{ 	"",
				"",
				"" },
			// 72
			{ 	"",
				"",
				"" },
			// 73
			{ 	"",
				"",
				"" },
			// 74
			{ 	"",
				"",
				"" },
			// 75
			{ 	"",
				"",
				"" },
			// 76
			{ 	"",
				"",
				"" },
			// 77 na
			{ 	"",
				"",
				"" },
			// 78
			{ 	"",
				"",
				"" },
			// 79
			{ 	"",
				"",
				"" },
			// 80
			{ 	"",
				"",
				"" },			
			// 81
			{ 	"",
				"",
				"" },
			// 82
			{ 	"",
				"",
				"" },
			// 83 na
			{ 	"",
				"",
				"" },
			// 84
			{ 	"",
				"",
				"" },
			// 85
			{ 	"",
				"",
				"" },
			// 86
			{ 	"",
				"",
				"" },
			// 87
			{ 	"",
				"",
				"" },
			// 88
			{ 	"",
				"",
				"" },
			// 89
			{ 	"",
				"",
				"" },
			// 90 TAURUS 
			{ 	"The landscape and climate of Taurus A",
				"seems very much like that of",
				"harsher climates on Earth." },
			// 91
			{ 	"",
				"",
				"" },
			// 92 na
			{ 	"",
				"",
				"" },
			// 93 DIOGENITE VERTEX 
			{ 	"A black hole is a region of spacetime",
				"from which gravity prevents anything,",
				"including light, from escaping." },
			// 94
			{ 	"",
				"",
				"" },
			// 95
			{ 	"",
				"",
				"" },
			// 96
			{ 	"",
				"",
				"" },
			// 97
			{ 	"",
				"",
				"" },
			// 98
			{ 	"",
				"",
				"" },
			// 99
			{ 	"",
				"",
				"" },
			// 100 na
			{ 	"",
				"",
				"" },
			// 101
			{ 	"",
				"",
				"" },
			// 102
			{ 	"",
				"",
				"" },
			// 103 PLANET CLOSE TO END GAME
			{ 	"Well done, Commander. We are closing",
				"to Baby Boom Galaxy. Keep gathering",
				"Light Years to venture forth." },
			// 104
			{ 	"",
				"",
				"" },
			// 105
			{ 	"",
				"",
				"" },
			// 106
			{ 	"",
				"",
				"" },
			// 107 na
			{ 	"",
				"",
				"" },
			// 108
			{ 	"We are getting close to Baby Boom.",
				"No enemy ship can block our path!" },
			// 109
			{ 	"",
				"",
				"" },
			// 110
			{ 	"",
				"",
				"" },
			// 111
			{ 	"",
				"",
				"" },
			// 112 na
			{ 	"",
				"",
				"" },
			// 113
			{ 	"",
				"",
				"" },
			// 114
			{ 	"",
				"",
				"" },
			// 115
			{ 	"",
				"",
				"" },
			// 116
			{ 	"",
				"",
				"" },
			// 117
			{ 	"",
				"",
				"" },
			// 118 BABY BOOM
			{ 	"Commander! The signals... are...",
				"coming from the Sun! This must be",
				"artifical in some way!" },
			// 119 APOLLO PLANET CLOSE TO BABY BOOM
			{ 	"The signals are very strong from",
				"here. Baby Boom is near. We must",
				"discover the source!" },
			// 120
			{ 	"",
				"",
				"" },
			// 121 na
			{ 	"",
				"",
				"" },
				
	};
	
	// ellenséges hajók és lények mozgás mintázatai
	public class MissionScript {
		int type = 0; // 0 = npc, 1 = environment, 2 = bgspeed, 3 = dialogue, etc., 6 = end
		int entity = 0; // entity to spawn from Entities.java
		int pattern = 0; // pattern for the spawned entity from Patterns.java
		boolean wait = false; // spawn only if all previous entities die
		float delay = 1.0f; // delay BEFORE spawning the entity
	}
	
	public class MissionControl
	{
		int num = 0; // number of missionscript
		int background = 0;
		boolean clouds = false;
		boolean shadow = false;
		float power = 0;
		float bgspeed = 1.0f;
		My2DImage bgimage;
		MissionScript[] script; // array of missionscript 
	}
	
	private class All_npcs {
		public int type; 
		public float strength;
	};
	
	final int[] backgrounds = {
			R.drawable.asteroid,		//  0
			R.drawable.asteroid_belt,	//  1
			R.drawable.blue_nebula,		//  2
			R.drawable.green_nebula,	//  3
			R.drawable.icy_planet,		//  4
			R.drawable.red_nebula,		//  5
			R.drawable.rocky_planet,	//  6
			R.drawable.sandy_planet,	//  7
			R.drawable.spacewstars,		//  8
			R.drawable.sun_surface,		//  9
	};

	final int[] background_clouds = {
			R.drawable.planet_clouds,				//  high
			R.drawable.nebula_for_blue_green,		//  high
			R.drawable.nebula_for_red,				//  high
			R.drawable.ship_surface,
	};

	final boolean[] backgrounds_shadow = {
			false,		// 0
			false,		// 1
			false,		// 2
			false,		// 3
			true,		// 4
			false,		// 5
			true,		// 6
			true,		// 7
			false,		// 8
			true,		// 9
	};

	final boolean[] environment_objects = {
			false,		// 0
			false,		// 1
			true,		// 2
			true,		// 3
			false,		// 4
			true,		// 5
			false,		// 6
			false,		// 7
			true,		// 8
			false,		// 9
	};
	
	final boolean[] nebula = {
			true,		// 0
			true,		// 1
			true,		// 2
			true,		// 3
			false,		// 4
			true,		// 5
			false,		// 6
			false,		// 7
			true,		// 8
			false,		// 9
	};

	MissionControl missionControl;

	public Missions() {

	}

	public void load(Context context, int npc_group, int env_group, int bg, int mission_power, int boss_type, Entities allEntities) {
		
		missionControl = null;
		missionControl = new MissionControl();

		float target_strength = mission_power;
		missionControl.power = target_strength;
		Log.i("Randomizer", "Target Strength: " + Float.toString(target_strength));
		
		Random generator = new Random();

		ArrayList<All_npcs> allnpcs = new ArrayList<All_npcs>();
		ArrayList<All_npcs> missionnpcs = new ArrayList<All_npcs>();
		ArrayList<All_npcs> missiondetails = new ArrayList<All_npcs>();
		
		// set available creature types
		for (int i=0;i<allEntities.npc_num;i++) {
			if (allEntities.npc[i].hp_max >0 && !allEntities.npc[i].boss) {
				for (int j=0; j<allEntities.npc_group[npc_group].length; j++) {
					if (i == allEntities.npc_group[npc_group][j]) {
						All_npcs c = new All_npcs();
						c.type = i;
						c.strength = allEntities.npc[i].power;
						Log.i("Randomizer", Integer.toString(c.type));
						Log.i("Randomizer", Float.toString(c.strength));
						allnpcs.add(c);
						break;
					}
				}
			}
			// Add environment entities
			else if (allEntities.npc[i].hp_max == 0) {
				for (int j=0; j<allEntities.env_group[env_group].length; j++) {
					if (i == allEntities.env_group[env_group][j]) {
						All_npcs c = new All_npcs();
						c.type = i;
						c.strength = 0;
						Log.i("Randomizer", Integer.toString(c.type));
						missiondetails.add(c);
						break;
					}
				}
			}

		}
		
		// sort available npc types
		Collections.sort(allnpcs, new Comparator<All_npcs>(){
		       public int compare( All_npcs a, All_npcs b ){
		           return (int)(a.strength - b.strength);
		       }
		});
		
		int npc_type = min_npc_type + generator.nextInt(1 + max_npc_type - min_npc_type);
		int many = npc_type * (strong_vs_many + strong_vs_many_random - generator.nextInt(strong_vs_many_random * 2 + 1)) / 100;
		int strong = npc_type - many;
		Log.i("Randomizer", "Strong: "+Integer.toString(strong));
		Log.i("Randomizer", "Many: "+Integer.toString(many));
		
		// select stage npc types from available - based on strong vs many
		for (int i=0;i<many;i++) {
			if (allnpcs.size() > 0) {
				int halfsize = allnpcs.size() / 2;
				int index = 0;
				if (halfsize > 0) index = generator.nextInt(halfsize);
				missionnpcs.add(allnpcs.get(index));
				allnpcs.remove(index);
			} else break;
		}
		for (int i=0;i<strong;i++) {
			if (allnpcs.size() > 0) {
				int halfsize = allnpcs.size() / 2;
				int index = 0;
				if (halfsize > 0) index = halfsize + generator.nextInt(allnpcs.size() - halfsize);
				missionnpcs.add(allnpcs.get(index));
				allnpcs.remove(index);
			} else break;
		}
		
		allnpcs.clear();

		// sort stage npc types
		 Collections.sort(missionnpcs, new Comparator<All_npcs>(){
		        public int compare( All_npcs a, All_npcs b ){
		            return (int)(a.strength - b.strength);
		        }
		});
		 
			// calculate total wave number
			int waves = min_waves + generator.nextInt(1 + max_waves - min_waves);
			// modify target strength based on wave number
			target_strength *= (float)waves / ((float)(min_waves + max_waves) / 2);

			Log.i("Randomizer", "Waves: " + Integer.toString(waves));

			float[] waves_power = new float[waves];
			for (int i=0;i<waves;i++) {
				 waves_power[i] = target_strength / waves;

				 Log.i("Randomizer", "Waves Power " + Integer.toString(i) + " : " + Float.toString(waves_power[i]));
			}
			
			// ???
			int halfwaves = waves / 2;
			for (int i = halfwaves; i >= 1; i--) {
				float mod = waves_strength_avg_increase - (waves_strength_avg_increase * (float)generator.nextInt(2 * waves_strength_random + 1) / 100); 
				Log.i("Randomizer", "Mod " + Integer.toString(i) + " : " + Float.toString(mod));
				waves_power[i-1] = waves_power[i] - waves_power[i]*(waves_strength_avg_increase + mod)/100; 
			}
			for (int i = halfwaves; i < waves - 1; i++) {
				float mod = waves_strength_avg_increase - (waves_strength_avg_increase * (float)generator.nextInt(2 * waves_strength_random + 1) / 100); 
				Log.i("Randomizer", "Mod " + Integer.toString(i) + " : " + Float.toString(mod));
				waves_power[i+1] = waves_power[i] + waves_power[i]*(waves_strength_avg_increase + mod)/100; 
			}

			for (int i=0;i<waves;i++) {
				 Log.i("Randomizer", "Waves Power after sort " + Integer.toString(i) + " : " + Float.toString(waves_power[i]));
			}

			ArrayList<All_npcs> wavenpcs = new ArrayList<All_npcs>();
			float totalpower = 0;
			int beacon = 0;
			int prev_npc = -1; int prev_num = 0;
			for (int i=0; i<waves; i++) {
				float power = 0;
				do {
					int index;
					if (prev_npc >=0 && generator.nextInt(100) <= allEntities.npc[prev_npc].row_chance && prev_num <= allEntities.npc[prev_npc].max_in_row) index = prev_npc;
					else index = generator.nextInt(missionnpcs.size());
					// ha túl erős lesz az adott npc-rel a wave, akkor keressünk gyengébb npc-t
					while (power + missionnpcs.get(index).strength > waves_power[i] * (1+(waves_strength_avg_increase/100)) ) {
						if (index > 0) index--;
						if (index == 0) break; 
					}
					// TODO 
					// final int waves_max_npc_type = 5; // max npc type per wave
					if (prev_npc != index) { prev_npc = index; prev_num = 0; }
					else prev_num++;
					
					wavenpcs.add(missionnpcs.get(index));
					
				 Log.i("Randomizer", "Waves npc " + Integer.toString(i) + " Index: " + Integer.toString(index));
				 Log.i("Randomizer", "Waves npc Power " + Float.toString(waves_power[i]));

					power += missionnpcs.get(index).strength;
				} while (power < waves_power[i]);
				
				totalpower += power;
				if (i < waves-1) {
					All_npcs c = new All_npcs();
					c.type = -1;
					c.strength = -1;
					wavenpcs.add(c);
					beacon ++;
					Log.i("Randomizer", "Beacon increased");
				}
				
				if (totalpower > target_strength) {
					break;
				}
			}
			missionnpcs.clear();

			ArrayList<MissionScript> mlist = new ArrayList<MissionScript>();
			
			int num = 0;
			boolean wave_break = false;
			
			float timepast = 0;
			float timenext = 8;
			float miningpast = 0;
			float miningnext = 20;
			
			prev_npc = -1;
			int prev_pattern = -1;
			int pattern_increase = 0;
			
			// Add dialogue before mission
			MissionScript dg = new MissionScript();
			dg.type = 3;
			dg.delay = 0;
			dg.entity = 0;
			mlist.add(dg);
			
			for(All_npcs npc: wavenpcs){
				//if (num >= missionControl.num) break;
				if (npc.type >= 0) {
					// add npc
					MissionScript m = new MissionScript();
					m.entity = npc.type;
					m.type = 0;	// TODO not used yet, normal npc
					if (prev_npc == m.entity && prev_pattern >= 0 && pattern_increase < allEntities.npc[prev_npc].max_in_row) {
						m.pattern = prev_pattern;
						pattern_increase++;
					}
					// check pattern
					else m.pattern = allEntities.pattern_group[allEntities.npc[m.entity].pattern_group][generator.nextInt(allEntities.pattern_group[allEntities.npc[m.entity].pattern_group].length)]; 
		    		
		    		if (prev_npc != m.entity) { 
		    			prev_npc = m.entity;
		    			prev_pattern = m.pattern;
		    			pattern_increase = 0;
		    		}

					Log.i("Randomizer", "Waves npc " + Integer.toString(num) + " : " + Integer.toString(npc.type));
					
					if (num == 0) {
						m.delay = 3;
						
					} else if (!wave_break) {
						if (prev_npc == m.entity && prev_pattern >= 0 && pattern_increase < allEntities.npc[prev_npc].max_in_row) {
							npc_row_delay = 25f / allEntities.npc[prev_npc].speed;
							m.delay = npc_min_delay + npc_row_delay*2/3 + (float)(generator.nextInt(100)/100 * (npc_row_delay*1/3));
						}
						else m.delay = npc_min_delay + (float)(generator.nextInt(100)/100 * (npc_max_delay - npc_min_delay));
						Log.i("Randomizer", "Waves Delay " + Integer.toString(num) + " : " + Float.toString(m.delay));
						
					} else {
						m.delay = 0; //waves_min_delay + (float)(generator.nextInt(100)/100 * (waves_max_delay - waves_min_delay));
						wave_break = false;
						m.wait = true;
						Log.i("Randomizer", "Waves Delay " + Integer.toString(num) + " : " + Float.toString(m.delay));
						Log.i("Randomizer", "Waves Break");						
					}
					mlist.add(m);
					
					timepast += m.delay;
					miningpast += m.delay;
					// Add an environment entity
					if (environment_objects[bg] && timepast > timenext) {
						timepast = 0;
						timenext = 5+4*(float)generator.nextInt(100)/100;
						num++;
						MissionScript mdetail = new MissionScript();
						int detailindex = generator.nextInt(missiondetails.size());
						mdetail.entity = missiondetails.get(detailindex).type;
						mdetail.type = 1;
						mdetail.pattern =allEntities.pattern_group[allEntities.npc[mdetail.entity].pattern_group][generator.nextInt(allEntities.pattern_group[allEntities.npc[mdetail.entity].pattern_group].length)];
						Log.i("Randomizer", "Environment object " + Integer.toString(num) + " : " + Integer.toString(npc.type));
						mdetail.delay = 0;
						mlist.add(mdetail);
					}
					else if (miningpast > miningnext) {
						miningpast = 0;
						miningnext = 25+10*(float)generator.nextInt(100)/100;
						num++;
						MissionScript mdetail = new MissionScript();
						int detailindex = generator.nextInt(2);
						mdetail.entity = 66+detailindex; // mining asteroids
						mdetail.type = 0;
						mdetail.pattern = allEntities.pattern_group[allEntities.npc[mdetail.entity].pattern_group][generator.nextInt(allEntities.pattern_group[allEntities.npc[mdetail.entity].pattern_group].length)];
						Log.i("Randomizer", "Mining asteroid " + Integer.toString(num) + " : " + Integer.toString(npc.type));
						mdetail.delay = 0;
						mlist.add(mdetail);
					}
					
					num++;
				} else {
					// add wave break
					wave_break = true;
				}
			}
			wavenpcs.clear();

			// END GAME
			// WARNING SIGN
			MissionScript bsign = new MissionScript();
			bsign.type = 6;	
			bsign.delay = 2;
			bsign.wait = true;
			mlist.add(bsign);
			// BOSS
			MissionScript b = new MissionScript();
			b.entity = allEntities.boss_group[npc_group][generator.nextInt(allEntities.boss_group[npc_group].length)];
			b.type = 0;	
			b.delay = 2;
			b.pattern = allEntities.pattern_group[allEntities.npc[b.entity].pattern_group][generator.nextInt(allEntities.pattern_group[allEntities.npc[b.entity].pattern_group].length)];
			mlist.add(b);
			// END GAME
			MissionScript endgame = new MissionScript();
			endgame.type = 5;
			endgame.delay = 2;
			endgame.wait = true;
			mlist.add(endgame);
			
			missionControl.num = mlist.size();
			missionControl.script = new MissionScript[missionControl.num];
			for (int i=0; i<missionControl.num; i++) { 
				missionControl.script[i] = new MissionScript();
			}
			
			int index = 0;
			for(MissionScript m: mlist){
				if (index >= missionControl.num) break;
				missionControl.script[index].entity = m.entity;
				missionControl.script[index].type = m.type;
				missionControl.script[index].pattern = m.pattern;
				missionControl.script[index].delay = m.delay;
				missionControl.script[index].wait = m.wait;
				index++;
			}
			mlist.clear();
		 
			missionControl.background = backgrounds[bg];
    		missionControl.shadow = backgrounds_shadow[bg];
    		missionControl.clouds = !nebula[bg];
    		missionControl.bgspeed = 1f;
    		
    		if (missionControl.clouds) nebula_type = 0;
    		else nebula_type = 1+generator.nextInt(2);

	}
} 
