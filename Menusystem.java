package com.tubigames.galaxy.shooter.hd;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.util.Log;

public class Menusystem {

	float hd = OpenGLRenderer.hd;
	
	public class Mainmenu
	{
		final int bgimage_src = R.drawable.mainmenu;
		//final int bgimage_src_c = R.drawable.campaign_selector;
		My2DImage bgimage = new My2DImage((int)(hd*480),(int)(hd*800),false);
		My2DImage campaignselector = new My2DImage((int)(hd*480),(int)(hd*800),false);
		final int button_num = 10;

		final int[] buttonimages_src = {
				R.drawable.button_start,		// 0 = Start
				R.drawable.button_start,		// 1 = Survival
				R.drawable.button_options,		// 2 = Options
				R.drawable.button_profile,		// 3 = Profile
				R.drawable.button_unlock,		// 4 = Unlock
				R.drawable.facebook,			// 5 = Facebook
				R.drawable.twitter,				// 6 = Twitter
				R.drawable.youtube,				// 7 = Youtube
				R.drawable.google_play,			// 8 = Google Play, temp (Openfeint)
				R.drawable.credits,				// 9 = Credits
				R.drawable.google_play				// 10 = Amazon
		};
		My2DImage[] buttonimages = new My2DImage[10];
		
		final float[] btpresscolor_0_4 = { 1.0f, 1.0f, 1.0f, 1.0f };	// overwrite color if button 0 to 4 pressed 
		final float[] btpresscolor_5_9 = { 1.0f, 1.0f, 1.0f, 1.0f };

		final int[][] button_pos = {					// buttons' x, y coordinates, + width, height values
				{ (int)(hd*200), (int)(hd*315), (int)(hd*278), (int)(hd*66) }, 
				{ (int)(hd*1), (int)(hd*1), (int)(hd*1), (int)(hd*1) }, 
				{ (int)(hd*200), (int)(hd*387), (int)(hd*278), (int)(hd*66) },
				{ (int)(hd*200), (int)(hd*459), (int)(hd*278), (int)(hd*66) },
				{ (int)(hd*200), (int)(hd*531), (int)(hd*278), (int)(hd*66) },
				{ (int)(hd* 28), (int)(hd*626), (int)(hd*64), (int)(hd*64) },
				{ (int)(hd*118), (int)(hd*626), (int)(hd*64), (int)(hd*64) },
				{ (int)(hd*208), (int)(hd*626), (int)(hd*64), (int)(hd*64) },
				{ (int)(hd*298), (int)(hd*626), (int)(hd*64), (int)(hd*64) },
				{ (int)(hd*388), (int)(hd*626), (int)(hd*64), (int)(hd*64) },
			};
		boolean[] button_pressed = { false,false,false,false,false,false,false,false,false,false };
		
	}
	
	Mainmenu mainmenu = new Mainmenu();
	//My2DImage mainmenu_lock = new My2DImage((int)(hd*40),(int)(hd*46),true);
	
	// Galaxy Data
	class Galaxy
	{
		String name = "";
		int distance = 0; // million LY
		int status = 0; // 0 = unreachable, 1 = locked, 2 = unlocked
		int total_missions = 0;
		int[] mission_list; 
		int[] unlock_galaxy_list;
		int[] unlockedby_galaxy_list;
		int update = 0;
		boolean isboss = false;
		boolean isbonus = false;
		boolean fullgame = false;
		int[] bt_xy = { 0 , 0 };
		int[] label_xy = { 0 , 0 };
		float[] color = { 1, 1, 1 };
	}
	
	final int[] bgimage_src = {
			R.drawable.galaxyscreen_01,
			R.drawable.galaxyscreen_02,
			R.drawable.galaxyscreen_03,
			R.drawable.galaxyscreen_04,
			R.drawable.galaxyscreen_05,
			R.drawable.galaxyscreen_06,
			R.drawable.galaxyscreen_07,
			R.drawable.galaxyscreen_08,
			R.drawable.galaxyscreen_09,
			R.drawable.galaxyscreen_10,
			R.drawable.galaxyscreen_11,
			R.drawable.galaxyscreen_12,
			R.drawable.galaxyscreen_13,
			R.drawable.galaxyscreen_14,
			R.drawable.galaxyscreen_15,
			R.drawable.galaxyscreen_16,
	};
	
	My2DImage bgimage = new My2DImage((int)(hd*480),(int)(hd*800),false);
	final int[] upgrade_bg_src = {
			R.drawable.ship_upgrade_1,
			R.drawable.ship_upgrade_1,
			R.drawable.ship_upgrade_1,
			R.drawable.ship_upgrade_1,
			R.drawable.ship_upgrade_1,
			R.drawable.ship_upgrade_1,
	};

	final int total_galaxy = 16;
	public int selected_galaxy = -1;
	public int selected_mission = -1;
	final int start_galaxy = 0;
	//public int current_galaxy = 0;
	Galaxy[] galaxy = new Galaxy[total_galaxy];

	My2DImage[] shiningstar = new My2DImage[10];
	public float shiningstar_loop = 0;
	public long shiningstar_timer = 0;
	public int shiningstar_current = 0;
	public int[][] shiningstar_loc = {
			{ (int)(hd*84) , (int)(hd*10) } ,
			{ (int)(hd*62) , (int)(hd*662) } ,
			{ (int)(hd*396) , (int)(hd*564) } ,
			{ (int)(hd*128) , (int)(hd*414) } ,
			{ (int)(hd*322) , (int)(hd*254) } ,
	};
	public int shiningstar_current2 = 0;
	public int[][] shiningstar_loc2 = {
			{ (int)(hd*335) , (int)(hd*38) } ,
			{ (int)(hd*6) , (int)(hd*198) } ,
			{ (int)(hd*405) , (int)(hd*226) } ,
			{ (int)(hd*167) , (int)(hd*360) } ,
			{ (int)(hd*23) , (int)(hd*430) } ,
			{ (int)(hd*313) , (int)(hd*710) } ,
			{ (int)(hd*67) , (int)(hd*651) } ,
			{ (int)(hd*139) , (int)(hd*560) } ,
	};
	
	My2DImage[] starline = new My2DImage[8];
	My2DImage starline_base = new My2DImage((int)(hd*10),(int)(hd*200),true);
	My2DImage[] parrow = new My2DImage[6];
	public float parrow_loop = 0;
	public float parrow_total = 0;
	//public boolean parrow_enabled = true;
	public float starline_loop = 0;
	My2DImage galaxy_marker = new My2DImage((int)(hd*77),(int)(hd*84),true);
	My2DImage galaxy_enter = new My2DImage((int)(hd*166),(int)(hd*70),true);
	My2DImage galaxy_unlock = new My2DImage((int)(hd*166),(int)(hd*70),true);
	My2DImage galaxy_border = new My2DImage((int)(hd*394),(int)(hd*139),true);
	My2DImage black = new My2DImage(100,100,true);
	My2DImage white = new My2DImage(100,100,true);
	final String missionA = "Accomplished "; String missionB = " of "; String missionC = "Missions: ";
	final String scoreA = "Best: ";
	final String unlockA = "Cost: "; String LY = " LY";
	final int[] galaxy_bt = { (int)(hd*0), (int)(hd*0), (int)(hd*150), (int)(hd*80) };
	boolean galaxy_bt_pressed = false;

	My2DImage[] wormholeA = new My2DImage[16];
	float wormholeA_loop = 0;
	float wormholeA_alpha = 0.0f;
	long wormholeA_tick = 0;

	//My2DImage back = new My2DImage((int)(hd*50),(int)(hd*50),true);
	//final public int[] back_bt = { (int)(hd*0), (int)(hd*0), (int)(hd*60), (int)(hd*60) };
	//My2DImage back1 = new My2DImage(80,80,true);
	//public boolean back_pressed = false;
	
	// Mission Data
	public class Mission
	{
		String name = "";
		String description = "";
		int difficulty = 0; // 0 = easy, 1 = medium, 2 = hard, 3 = insane
		int status = 0; // 0 = unavailable, 1 = mission, 2 = space station 
		int mission_to_load = 0; // mission # to load from Missions.java
		int completed = 0;	// 0 not started, 1 unfinished, 2 completed (easy), 3 completed (normal), 4 completed (hard)
		int highscore = 0;
		int reward = 0;
		boolean fullgame = false;
		int[] bt_xy = { 0 , 0 };
		int[] label_xy = { 0 , 0 };
		//float[] color = { 1, 1, 1 };
		boolean textup = true;
		int music = 0;
		boolean mining = true;
		int npc_group = 0;
		int env_group = 0;
		int boss = -1;
		int background = 0;
		int power = 3000;
	 }

	final int total_mission = 115;
	Mission[] mission = new Mission[total_mission];
	My2DImage mission_marker_active = new My2DImage((int)(hd*110),(int)(hd*104),true);
	My2DImage mission_marker_finished = new My2DImage((int)(hd*110),(int)(hd*104),true);
	My2DImage mission_marker_station = new My2DImage((int)(hd*110),(int)(hd*104),true);
	My2DImage mission_enter = new My2DImage((int)(hd*171),(int)(hd*85),true);
	My2DImage mission_cancel = new My2DImage((int)(hd*172),(int)(hd*88),true);
	My2DImage mission_border = new My2DImage((int)(hd*384),(int)(hd*514),true);
	final int[] mission_bt_enter = { (int)(hd*90), (int)(hd*500), (int)(hd*150), (int)(hd*80) };
	final int[] mission_bt_cancel = { (int)(hd*235), (int)(hd*500), (int)(hd*150), (int)(hd*80) };
	int mission_marker_pressed = -1;
	boolean mission_enter_pressed = false;
	boolean mission_cancel_pressed = false;
	final String status = "Status: ";
	//final String difficulty = "Difficulty: ";
	final String reward = "Reward: ";
	final String reward_noreward = "Already collected";
	final String[] completed = { "Not Started", "Unfinished", "Accomplished", "Accomplished", "Accomplished" };
	//final String[] difficulty_level = { "Easy", "Medium", "Hard", "Insane" };
	final float[][] completed_color = {
			{ 0.7f, 0.7f, 0.7f }, 
			{ 0.7f, 0.7f, 0.7f }, 
			{ 0.25f, 1f, 0.25f }, 
			{ 0.25f, 1f, 0.25f }, 
			{ 0.25f, 1f, 0.25f }, 
	};
	My2DImage completed_star = new My2DImage((int)(hd*46),(int)(hd*46),true);
	My2DImage mission_bt_easy = new My2DImage((int)(hd*100),(int)(hd*55),true);
	My2DImage mission_bt_medium = new My2DImage((int)(hd*100),(int)(hd*55),true);
	My2DImage mission_bt_hard = new My2DImage((int)(hd*100),(int)(hd*55),true);
	int mission_bt_difficulty = 1;
	
	// Upgrade screen
	My2DImage upgrade_bg = new My2DImage((int)(hd*480),(int)(hd*800),false);
	My2DImage upgrade_uparrow = new My2DImage((int)(hd*90),(int)(hd*86),true);
	My2DImage upgrade_downarrow = new My2DImage((int)(hd*90),(int)(hd*86),true);
	//My2DImage upgrade_border = new My2DImage(480,800,true);
	//My2DImage upgrade_border2 = new My2DImage((int)(hd*462),(int)(hd*578),true);
	final int[] upgrade_bt_up = { (int)(hd*376), (int)(hd*68), (int)(hd*90), (int)(hd*86) };
	final int[] upgrade_bt_down = { (int)(hd*376), (int)(hd*660), (int)(hd*90), (int)(hd*86) };
	final int[] upgrade_bt_start = { (int)(hd*45), (int)(hd*138), (int)(hd*90) };

	final int[] upgrade_icon_ship_bt = { (int)(hd*65), (int)(hd*63) };
	final int[][] upgrade_icon_weapon_bt = {
			{ (int)(hd*156), (int)(hd*246) } , { (int)(hd*248), (int)(hd*185) } , { (int)(hd*356), (int)(hd*185) } , { (int)(hd*34), (int)(hd*185) } };
	final int[][] upgrade_icon_modifier_bt = {
			{ (int)(hd*247), (int)(hd*246) } , { (int)(hd*290), (int)(hd*300) } , { (int)(hd*194), (int)(hd*300) } };
	final int[][] upgrade_icon_special_bt = {
			{ (int)(hd*65), (int)(hd*370) } , { (int)(hd*156), (int)(hd*370) } , { (int)(hd*247), (int)(hd*370) } , { (int)(hd*339), (int)(hd*370) } };
	final int[][] upgrade_icon_upgrade_bt = {
			{ (int)(hd*65), (int)(hd*489) } , { (int)(hd*156), (int)(hd*489) } , { (int)(hd*247), (int)(hd*489) } , { (int)(hd*339), (int)(hd*489) } ,
			{ (int)(hd*65), (int)(hd*577) } , { (int)(hd*156), (int)(hd*577) } , { (int)(hd*247), (int)(hd*577) } , { (int)(hd*339), (int)(hd*577) } };
	final int[] upgrade_icon_purchasable_bt = { (int)(hd*156), (int)(hd*63) };
	
	final int upgrade_icon_size = (int)(hd*80);	
	boolean upgrade_icon_pressed = false;
	boolean upgrade_selected = false;
	int upgrade_slot_selected = -1;
	int upgrade_selected_weapon = -1;
	int upgrade_selected_modifier = -1;
	int upgrade_selected_special = -1;
	int upgrade_selected_upgrade = -1;
	int upgrade_selected_ship = -1;
	int upgrade_selected_purchasable = -1;
	int upgrade_page = 0;
	int upgrade_maxpage = 1;
	int upgrade_total = 0;
	int[] itemlist = { -2, -2, -2, -2, -2, -2 };
	
	// upgrade layers
	My2DImage[] layer = new My2DImage[5];
	int[] layer_src = {
		R.drawable.bronze_layer,
		R.drawable.silver_layer,
		R.drawable.gold_layer,
		R.drawable.extra_layer,
		R.drawable.active_layer,
	};
	
	// Dialogue
	My2DImage dialogue_bg = new My2DImage((int)(hd*480),(int)(hd*120),true);
	final int[][] dialogue_xy = { 
				{(int)(hd*56), (int)(hd*40)}, 	// one line
				{(int)(hd*56), (int)(hd*30)}, {(int)(hd*56), (int)(hd*53)},  	// two lines
				{(int)(hd*56), (int)(hd*25)}, {(int)(hd*56), (int)(hd*47)}, {(int)(hd*56), (int)(hd*69)} 	// three lines
			};
	boolean dialogue_galaxy = false;
	boolean dialogue_mission = false;
	boolean dialogue_upgrade = false;
	boolean dialogue_game = false;
	int dialogue_galaxy_num = -1;
	int dialogue_mission_num = -1;
	int dialogue_upgrade_num = -1;
	int dialogue_game_num = -1;
	final int dialogue_delay = 6000;
	boolean dialogue_galaxy_isrunning = false;
	boolean dialogue_mission_isrunning = false;
	boolean dialogue_upgrade_isrunning = false;
	boolean dialogue_game_isrunning = false;
	
	boolean quit = false;
	long pause_time = 0;
	My2DImage quit_yes = new My2DImage((int)(hd*172),(int)(hd*88),true);
	My2DImage quit_no = new My2DImage((int)(hd*172),(int)(hd*88),true);
	final int[] quit_bt_yes = { (int)(hd*80), (int)(hd*300), (int)(hd*172), (int)(hd*88) };
	final int[] quit_bt_no = { (int)(hd*240), (int)(hd*300), (int)(hd*172), (int)(hd*88) };
	boolean quit_yes_pressed = false;
	boolean quit_no_pressed = false;
	
	//My2DImage profile_select = new My2DImage(120,80,true);
	My2DImage profile_reset = new My2DImage((int)(hd*124),(int)(hd*56),true);
	My2DImage profile_reset2 = new My2DImage((int)(hd*172),(int)(hd*88),true);
	My2DImage profile_confirm = new My2DImage((int)(hd*120),(int)(hd*80),true);
	My2DImage profile_border = new My2DImage((int)(hd*466),(int)(hd*184),true);
	final int[] profile_select_bt = { (int)(hd*40), (int)(hd*40), (int)(hd*400), (int)(hd*160) };
	final int[] profile_reset_bt = { (int)(hd*310), (int)(hd*50), (int)(hd*130), (int)(hd*50) };
	boolean[] profile_select_pressed = { false, false, false };
	boolean[] profile_reset_pressed = { false, false, false };
	boolean reset_yes_pressed = false;
	boolean reset_no_pressed = false;

	My2DImage options_soundon = new My2DImage((int)(hd*82),(int)(hd*84),true);
	My2DImage options_soundoff = new My2DImage((int)(hd*82),(int)(hd*84),true);
	My2DImage options_musicon = new My2DImage((int)(hd*88),(int)(hd*84),true);
	My2DImage options_musicoff = new My2DImage((int)(hd*88),(int)(hd*84),true);
	My2DImage options_vibrationon = new My2DImage((int)(hd*80),(int)(hd*80),true);
	My2DImage options_vibrationoff = new My2DImage((int)(hd*80),(int)(hd*80),true);
	My2DImage options_keepscreenon = new My2DImage((int)(hd*84),(int)(hd*90),true);
	My2DImage options_keepscreenoff = new My2DImage((int)(hd*84),(int)(hd*90),true);
	My2DImage options_border = new My2DImage((int)(hd*460),(int)(hd*602),true);
	My2DImage verify_border = new My2DImage((int)(hd*408),(int)(hd*216),true);
	final int[] options_sound_bt = { (int)(hd*50), (int)(hd*50), (int)(hd*380), (int)(hd*80) };
	final int[] options_music_bt = { (int)(hd*50), (int)(hd*140), (int)(hd*380), (int)(hd*80) };
	final int[][] options_music_vol = { 
			{ (int)(hd*50), (int)(hd*170) },	//0 
			{ (int)(hd*170), (int)(hd*195) },	//1
			{ (int)(hd*195), (int)(hd*220) },	//2
			{ (int)(hd*220), (int)(hd*245) },	//3
			{ (int)(hd*245), (int)(hd*270) },	//4
			{ (int)(hd*270), (int)(hd*295) },	//5
			{ (int)(hd*295), (int)(hd*320) },	//6
			{ (int)(hd*320), (int)(hd*345) },	//7
			{ (int)(hd*345), (int)(hd*370) },	//8
			{ (int)(hd*370), (int)(hd*395) },	//9
			{ (int)(hd*395), (int)(hd*430) },	//10
	};
	
	final int[] options_vibration_bt = { (int)(hd*50), (int)(hd*230), (int)(hd*380), (int)(hd*80) };
	final int[] options_gfx_bt = { (int)(hd*50), (int)(hd*320), (int)(hd*380), (int)(hd*80) };
	final int[] options_animation_bt = { (int)(hd*50), (int)(hd*410), (int)(hd*380), (int)(hd*80) };
	final int[] options_keepscreen_bt = { (int)(hd*50), (int)(hd*320), (int)(hd*380), (int)(hd*80) };
	boolean options_sound_pressed = false;
	boolean options_music_pressed = false;
	boolean options_gfx_pressed = false;
	boolean options_vibration_pressed = false;
	boolean options_animation_pressed = false;
	boolean options_keepscreen_pressed = false;

	//My2DImage unlock_entercode = new My2DImage((int)(hd*120),(int)(hd*80),true);
	My2DImage unlock_fullgame = new My2DImage((int)(hd*150),(int)(hd*78),true);
	//My2DImage unlock_buynow = new My2DImage((int)(hd*120),(int)(hd*80),true);
	//My2DImage unlock_market = new My2DImage((int)(hd*72),(int)(hd*72),true);
	//final int[] unlock_entercode_bt = { (int)(hd*140), (int)(hd*195), (int)(hd*120), (int)(hd*80) };
	final int[] unlock_fullgame_bt = { (int)(hd*164), (int)(hd*435), (int)(hd*174), (int)(hd*80) };
	//boolean unlock_entercode_pressed = false;
	boolean unlock_fullgame_pressed = false;
	final int[] unlock_facebook_bt = { (int)(hd*200), (int)(hd*202), (int)(hd*70), (int)(hd*70) };
	//final int[] unlock_market_bt = { (int)(hd*280), (int)(hd*540), (int)(hd*70), (int)(hd*70) };
	boolean unlock_facebook_pressed = false;
	//boolean unlock_market_pressed = false;
	int unlock_prev = -1;
	
	final int[] cd_src = { 
			R.drawable.icon_black,
			R.drawable.icon_cd01,	//1
			R.drawable.icon_cd02,
			R.drawable.icon_cd03,
			R.drawable.icon_cd04,
			R.drawable.icon_cd05,
			R.drawable.icon_cd06,
			R.drawable.icon_cd07,	//7
			R.drawable.icon_cd08,
			R.drawable.icon_cd09,
			R.drawable.icon_cd10,
			R.drawable.icon_cd11,
			R.drawable.icon_cd12,	//12
			R.drawable.icon_white,	//13
	};
	final int[] cd_size = { (int)(hd*80), (int)(hd*80) };
	My2DImage[] cd = new My2DImage[14];

	/*
	//My2DImage[] unlock_keys = new My2DImage[12];
	//boolean[] unlock_keys_pressed = { false, false, false, false, false, false, 
	//		false, false, false, false, false, false };
	final int[][] unlock_keys_bt = {
			{ (int)(hd*90), (int)(hd*180), (int)(hd*80), (int)(hd*80) },	// 1
			{ (int)(hd*200), (int)(hd*180), (int)(hd*80), (int)(hd*80) },	// 2
			{ (int)(hd*310), (int)(hd*180), (int)(hd*80), (int)(hd*80) },	// 3
			{ (int)(hd*90), (int)(hd*290), (int)(hd*80), (int)(hd*80) },	// 4
			{ (int)(hd*200), (int)(hd*290), (int)(hd*80), (int)(hd*80) },	// 5
			{ (int)(hd*310), (int)(hd*290), (int)(hd*80), (int)(hd*80) },	// 6
			{ (int)(hd*90), (int)(hd*400), (int)(hd*80), (int)(hd*80) },	// 7
			{ (int)(hd*200), (int)(hd*400), (int)(hd*80), (int)(hd*80) },	// 8
			{ (int)(hd*310), (int)(hd*400), (int)(hd*80), (int)(hd*80) },	// 9
			{ (int)(hd*200), (int)(hd*510), (int)(hd*80), (int)(hd*80) },	// 0
			{ (int)(hd*90), (int)(hd*510), (int)(hd*80), (int)(hd*80) },	// CLEAR
			{ (int)(hd*310), (int)(hd*510), (int)(hd*80), (int)(hd*80) },	// BACKSPACE
	};
	final public int[] back1_bt = { (int)(hd*392), (int)(hd*612), (int)(hd*60), (int)(hd*60) };
	*/
	
	My2DImage fblike = new My2DImage((int)(hd*169),(int)(hd*58),true);
	final int[] fblike_pos = { (int)(hd*245), (int)(hd*378), (int)(hd*172), (int)(hd*66) };
	boolean fblike_pressed = false;
	My2DImage fbshare = new My2DImage((int)(hd*169),(int)(hd*57),true);
	final int[] fbshare_pos = { (int)(hd*70), (int)(hd*378), (int)(hd*172), (int)(hd*66) };
	boolean fbshare_pressed = false;
	boolean continue_pressed = false;
	My2DImage btrate = new My2DImage((int)(hd*169),(int)(hd*63),true);
	My2DImage popupbg = new My2DImage((int)(hd*410),(int)(hd*286),true);
	final int[] popupbg_pos = { (int)(hd*42), (int)(hd*204) };
	My2DImage btcontinue = new My2DImage((int)(hd*274),(int)(hd*26),true);
	final int[] btcontinue_pos = { (int)(hd*108), (int)(hd*436) };

	My2DImage btachievements = new My2DImage((int)(hd*67),(int)(hd*67),true);
	final int[] btachievements_pos = { (int)(hd*346), (int)(hd*0), (int)(hd*67), (int)(hd*67) };
	My2DImage btleaderboard = new My2DImage((int)(hd*67),(int)(hd*67),true);
	final int[] btleaderboard_pos = { (int)(hd*413), (int)(hd*0), (int)(hd*67), (int)(hd*67) };
	My2DImage btsignin = new My2DImage((int)(hd*67),(int)(hd*67),true);
	final int[] btsignin_pos = { (int)(hd*279), (int)(hd*0), (int)(hd*67), (int)(hd*67) };
	boolean btachievements_pressed = false;
	boolean btleaderboard_pressed = false;
	boolean btsignin_pressed = false;

	final int[][] skillpos_left = {
			{ (int)(hd*0), (int)(hd*720) },
			{ (int)(hd*80), (int)(hd*720) },
			{ (int)(hd*320), (int)(hd*720) },
			{ (int)(hd*400), (int)(hd*720) },
	};
	
	My2DImage daily_border = new My2DImage((int)(hd*464),(int)(hd*608),true);
	My2DImage getmore = new My2DImage((int)(hd*210),(int)(hd*63),true);
	final int[] getmore_pos = { (int)(hd*133), (int)(hd*506), (int)(hd*210), (int)(hd*64) };
	boolean getmore_pressed = false;
	boolean daily_touched = false;
	int prev_menu = 0;
	
	My2DImage dailyok = new My2DImage((int)(hd*113),(int)(hd*122),true);
	My2DImage quit_text = new My2DImage((int)(hd*347),(int)(hd*61),true);
	My2DImage quitmission_text = new My2DImage((int)(hd*378),(int)(hd*60),true);
	My2DImage reset_text = new My2DImage((int)(hd*364),(int)(hd*60),true);
	int[][] dailyok_pos = {
			{ (int)(hd*39), (int)(hd*150) },	
			{ (int)(hd*177), (int)(hd*150) },	
			{ (int)(hd*315), (int)(hd*150) },	
			{ (int)(hd*104), (int)(hd*288) },	
			{ (int)(hd*253), (int)(hd*288) },	
	};

	public Menusystem() {
		//menufnt_space = param1;
	}

	public void Update_Dialogues(int prevD, int dType) {
		// prevD = previous dialogue #
		// dType = 0: galaxy, 1: mission screen, 2: upgrade screen
		if (dType == 0) {
			dialogue_galaxy_num = -1;
			
			if (galaxy[1].status == 1 && galaxy[2].status == 1) {
				dialogue_galaxy_num = 0;
				if (prevD == 0) dialogue_galaxy_num = 1;
				if (prevD == 1) dialogue_galaxy_num = -1;
			}
		} else if (dType == 1) {
			dialogue_mission_num = -1;
			if (galaxy[1].status == 1 && galaxy[2].status == 1) {
				boolean mission_started = false;
				boolean mission_unfinished = false;
				for (int i=0;i<galaxy[0].total_missions;i++) {
					if (mission[galaxy[0].mission_list[i]].completed > 0) mission_started = true;
					if (mission[galaxy[0].mission_list[i]].completed == 1) mission_unfinished = true;
				}
				if (!mission_started) { 
					dialogue_mission_num = 2;
					if (prevD == 2) dialogue_mission_num = -1;
				} else if (mission_unfinished) {
					dialogue_mission_num = 6;
					if (prevD == 6) dialogue_mission_num = -1;
				}
			}
				
		} else if (dType == 2) {
			dialogue_upgrade_num = -1;

			if (galaxy[1].status == 1 && galaxy[2].status == 1) {
				int mission_finished = 0;
				for (int i=0;i<galaxy[0].total_missions;i++) {
					if (mission[galaxy[0].mission_list[i]].completed == 2) mission_finished++;
				}
				if (mission_finished < 3) {
					dialogue_upgrade_num = 3;
					if (prevD == 3) dialogue_upgrade_num = 4;
					if (prevD == 4) dialogue_upgrade_num = 5;
					if (prevD == 5) dialogue_upgrade_num = -1;
				}
			}
		}
		
		
	}
	
	public void initialize(GL10 gl, Context context) {
		
		for (int i=0; i<mainmenu.button_num; i++) {
			mainmenu.buttonimages[i] = new My2DImage(mainmenu.button_pos[i][2], mainmenu.button_pos[i][3], true);
		}
		
		for (int i=0; i<total_galaxy; i++) {
			galaxy[i] = new Galaxy();
		}
		
		galaxy[0].name = "Milky Way";
		galaxy[0].distance = 0;
		galaxy[0].status = 2;
		galaxy[0].total_missions = 8;
		galaxy[0].unlock_galaxy_list = new int[2];
		galaxy[0].unlock_galaxy_list[0] = 1;
		galaxy[0].unlock_galaxy_list[1] = 2;
		galaxy[0].unlockedby_galaxy_list = new int[1];
		galaxy[0].unlockedby_galaxy_list[0] = -1;
		galaxy[0].update = 1;
		galaxy[0].mission_list = new int[ // folyt.
		galaxy[0].total_missions];
		galaxy[0].mission_list[0] = 0;
		galaxy[0].mission_list[1] = 1;
		galaxy[0].mission_list[2] = 2;
		galaxy[0].mission_list[3] = 3;
		galaxy[0].mission_list[4] = 4;
		galaxy[0].mission_list[5] = 5;
		galaxy[0].mission_list[6] = 6;
		galaxy[0].mission_list[7] = 7;
		galaxy[0].bt_xy[0] = (int)(hd*216);
		galaxy[0].bt_xy[1] = (int)(hd*633);
		galaxy[0].label_xy[0] = (int)(hd*211);
		galaxy[0].label_xy[1] = (int)(hd*633);
		galaxy[0].color[0] = 0.6f; // Red
		galaxy[0].color[1] = 0.7f; // Green
		galaxy[0].color[2] = 1.0f; // Blue
		
		galaxy[1].name = "Omega Centauri";
		galaxy[1].distance = 70000;
		galaxy[1].status = 1;
		galaxy[1].total_missions = 7;
		galaxy[1].unlock_galaxy_list = new int[1];
		galaxy[1].unlock_galaxy_list[0] = 3;
		galaxy[1].unlockedby_galaxy_list = new int[1];
		galaxy[1].unlockedby_galaxy_list[0] = 0;
		galaxy[1].update = 2;
		galaxy[1].mission_list = new int[ // folyt.
		galaxy[1].total_missions];
		galaxy[1].mission_list[0] = 8;
		galaxy[1].mission_list[1] = 9;
		galaxy[1].mission_list[2] = 10;
		galaxy[1].mission_list[3] = 11;
		galaxy[1].mission_list[4] = 12;
		galaxy[1].mission_list[5] = 13;
		galaxy[1].mission_list[6] = 14;
		galaxy[1].bt_xy[0] = (int)(hd*98);
		galaxy[1].bt_xy[1] = (int)(hd*592);
		galaxy[1].label_xy[0] = (int)(hd*80);
		galaxy[1].label_xy[1] = (int)(hd*592);
		galaxy[1].color[0] = 0.6f; // Red
		galaxy[1].color[1] = 0.7f; // Green
		galaxy[1].color[2] = 1.0f; // Blue
		galaxy[1].fullgame = true;

		galaxy[2].name = "Canis Major Dwarf";
		galaxy[2].distance = 75000;
		galaxy[2].status = 1;
		galaxy[2].total_missions = 8;
		galaxy[2].unlock_galaxy_list = new int[1];
		galaxy[2].unlock_galaxy_list[0] = 3;
		galaxy[2].unlockedby_galaxy_list = new int[1];
		galaxy[2].unlockedby_galaxy_list[0] = 0;
		galaxy[2].update = 2;
		galaxy[2].mission_list = new int[ // folyt.
		galaxy[2].total_missions];
		galaxy[2].mission_list[0] = 15;
		galaxy[2].mission_list[1] = 16;
		galaxy[2].mission_list[2] = 17;
		galaxy[2].mission_list[3] = 18;
		galaxy[2].mission_list[4] = 19;
		galaxy[2].mission_list[5] = 20;
		galaxy[2].mission_list[6] = 21;
		galaxy[2].mission_list[7] = 22;
		galaxy[2].bt_xy[0] = (int)(hd*326);
		galaxy[2].bt_xy[1] = (int)(hd*559);
		galaxy[2].label_xy[0] = (int)(hd*300);
		galaxy[2].label_xy[1] = (int)(hd*560);
		galaxy[2].color[0] = 0.6f; // Red
		galaxy[2].color[1] = 0.7f; // Green
		galaxy[2].color[2] = 1.0f; // Blue

		galaxy[3].name = "Bode Galaxy";
		galaxy[3].distance = 150000;
		galaxy[3].status = 0;
		galaxy[3].total_missions = 7;
		galaxy[3].unlock_galaxy_list = new int[1];
		galaxy[3].unlock_galaxy_list[0] = 4;
		galaxy[3].unlockedby_galaxy_list = new int[2];
		galaxy[3].unlockedby_galaxy_list[0] = 1;
		galaxy[3].unlockedby_galaxy_list[1] = 2;
		galaxy[3].update = 3;
		galaxy[3].mission_list = new int[ // folyt.
		galaxy[3].total_missions];
		galaxy[3].mission_list[0] = 23;
		galaxy[3].mission_list[1] = 24;
		galaxy[3].mission_list[2] = 25;
		galaxy[3].mission_list[3] = 26;
		galaxy[3].mission_list[4] = 27;
		galaxy[3].mission_list[5] = 28;
		galaxy[3].mission_list[6] = 29;
		galaxy[3].bt_xy[0] = (int)(hd*239);
		galaxy[3].bt_xy[1] = (int)(hd*503);
		galaxy[3].label_xy[0] = (int)(hd*227);
		galaxy[3].label_xy[1] = (int)(hd*503);
		galaxy[3].color[0] = 0.6f; // Red
		galaxy[3].color[1] = 0.7f; // Green
		galaxy[3].color[2] = 1.0f; // Blue

		galaxy[4].name = "Triangulum";
		galaxy[4].distance = 150000;
		galaxy[4].status = 0;
		galaxy[4].total_missions = 6;
		galaxy[4].unlock_galaxy_list = new int[1];
		galaxy[4].unlock_galaxy_list[0] = 5;
		galaxy[4].unlockedby_galaxy_list = new int[1];
		galaxy[4].unlockedby_galaxy_list[0] = 3;
		galaxy[4].update = 4;
		galaxy[4].mission_list = new int[ // folyt.
		galaxy[4].total_missions];
		galaxy[4].mission_list[0] = 30;
		galaxy[4].mission_list[1] = 31;
		galaxy[4].mission_list[2] = 32;
		galaxy[4].mission_list[3] = 33;
		galaxy[4].mission_list[4] = 34;
		galaxy[4].mission_list[5] = 35;
		galaxy[4].bt_xy[0] = (int)(hd*372);
		galaxy[4].bt_xy[1] = (int)(hd*440);
		galaxy[4].label_xy[0] = (int)(hd*367);
		galaxy[4].label_xy[1] = (int)(hd*440);
		galaxy[4].color[0] = 0.6f; // Red
		galaxy[4].color[1] = 0.7f; // Green
		galaxy[4].color[2] = 1.0f; // Blue

		galaxy[5].name = "Magellanic Cloud";
		galaxy[5].distance = 250000;
		galaxy[5].status = 0;
		galaxy[5].total_missions = 9;
		galaxy[5].unlock_galaxy_list = new int[1];
		galaxy[5].unlock_galaxy_list[0] = 6;
		galaxy[5].unlockedby_galaxy_list = new int[1];
		galaxy[5].unlockedby_galaxy_list[0] = 4;
		galaxy[5].update = 5;
		galaxy[5].mission_list = new int[ // folyt.
		galaxy[5].total_missions];
		galaxy[5].mission_list[0] = 36;
		galaxy[5].mission_list[1] = 37;
		galaxy[5].mission_list[2] = 38;
		galaxy[5].mission_list[3] = 39;
		galaxy[5].mission_list[4] = 40;
		galaxy[5].mission_list[5] = 41;
		galaxy[5].mission_list[6] = 42;
		galaxy[5].mission_list[7] = 43;
		galaxy[5].mission_list[8] = 44;
		galaxy[5].bt_xy[0] = (int)(hd*186);
		galaxy[5].bt_xy[1] = (int)(hd*409);
		galaxy[5].label_xy[0] = (int)(hd*160);
		galaxy[5].label_xy[1] = (int)(hd*409);
		galaxy[5].color[0] = 0.6f; // Red
		galaxy[5].color[1] = 1.0f; // Green
		galaxy[5].color[2] = 0.7f; // Blue

		galaxy[6].name = "Cygnus A";
		galaxy[6].distance = 250000;
		galaxy[6].status = 0;
		galaxy[6].total_missions = 6;
		galaxy[6].unlock_galaxy_list = new int[2];
		galaxy[6].unlock_galaxy_list[0] = 7;
		galaxy[6].unlock_galaxy_list[1] = 8;
		galaxy[6].unlockedby_galaxy_list = new int[1];
		galaxy[6].unlockedby_galaxy_list[0] = 5;
		galaxy[6].update = 6;
		galaxy[6].mission_list = new int[ // folyt.
		galaxy[6].total_missions];
		galaxy[6].mission_list[0] = 45;
		galaxy[6].mission_list[1] = 46;
		galaxy[6].mission_list[2] = 47;
		galaxy[6].mission_list[3] = 48;
		galaxy[6].mission_list[4] = 49;
		galaxy[6].mission_list[5] = 49;
		galaxy[6].bt_xy[0] = (int)(hd*56);
		galaxy[6].bt_xy[1] = (int)(hd*392);
		galaxy[6].label_xy[0] = (int)(hd*56);
		galaxy[6].label_xy[1] = (int)(hd*392);
		galaxy[6].color[0] = 0.6f; // Red
		galaxy[6].color[1] = 1.0f; // Green
		galaxy[6].color[2] = 0.7f; // Blue
		//galaxy[6].isbonus = true;
		galaxy[6].isboss = true;
		galaxy[6].fullgame = true;
		
		galaxy[7].name = "NGC 1068";
		galaxy[7].distance = 200000;
		galaxy[7].status = 0;
		galaxy[7].total_missions = 5;
		galaxy[7].unlock_galaxy_list = new int[1];
		galaxy[7].unlock_galaxy_list[0] = -1;
		galaxy[7].unlockedby_galaxy_list = new int[1];
		galaxy[7].unlockedby_galaxy_list[0] = 6;
		galaxy[7].update = 0;
		galaxy[7].mission_list = new int[ // folyt.
		galaxy[7].total_missions];
		galaxy[7].mission_list[0] = 50;
		galaxy[7].mission_list[1] = 51;
		galaxy[7].mission_list[2] = 52;
		galaxy[7].mission_list[3] = 53;
		galaxy[7].mission_list[4] = 54;
		galaxy[7].bt_xy[0] = (int)(hd*10);
		galaxy[7].bt_xy[1] = (int)(hd*493);
		galaxy[7].label_xy[0] = (int)(hd*10);
		galaxy[7].label_xy[1] = (int)(hd*493);
		galaxy[7].color[0] = 0.6f; // Red
		galaxy[7].color[1] = 1.0f; // Green
		galaxy[7].color[2] = 0.7f; // Blue
		//galaxy[7].isboss = true;
		galaxy[7].isbonus = true;
		//galaxy[7].fullgame = true;
		
		galaxy[8].name = "III Zw 2";
		galaxy[8].distance = 300000;
		galaxy[8].status = 0;
		galaxy[8].total_missions = 9;
		galaxy[8].unlock_galaxy_list = new int[2];
		galaxy[8].unlock_galaxy_list[0] = 9;
		galaxy[8].unlock_galaxy_list[1] = 10;
		galaxy[8].unlockedby_galaxy_list = new int[1];
		galaxy[8].unlockedby_galaxy_list[0] = 6;
		galaxy[8].update = 7;
		galaxy[8].mission_list = new int[ // folyt.
		galaxy[8].total_missions];
		galaxy[8].mission_list[0] = 55;
		galaxy[8].mission_list[1] = 56;
		galaxy[8].mission_list[2] = 57;
		galaxy[8].mission_list[3] = 58;
		galaxy[8].mission_list[4] = 59;
		galaxy[8].mission_list[5] = 60;
		galaxy[8].mission_list[6] = 61;
		galaxy[8].mission_list[7] = 62;
		galaxy[8].mission_list[8] = 63;
		galaxy[8].bt_xy[0] = (int)(hd*10);
		galaxy[8].bt_xy[1] = (int)(hd*262);
		galaxy[8].label_xy[0] = (int)(hd*8);
		galaxy[8].label_xy[1] = (int)(hd*262);
		galaxy[8].color[0] = 0.6f; // Red
		galaxy[8].color[1] = 1.0f; // Green
		galaxy[8].color[2] = 0.7f; // Blue
		//galaxy[8].fullgame = true;
	
		galaxy[9].name = "Malin 1";
		galaxy[9].distance = 400000;
		galaxy[9].status = 0;
		galaxy[9].total_missions = 7;
		galaxy[9].unlock_galaxy_list = new int[1];
		galaxy[9].unlock_galaxy_list[0] = 11;
		galaxy[9].unlockedby_galaxy_list = new int[1];
		galaxy[9].unlockedby_galaxy_list[0] = 8;
		galaxy[9].update = 8;
		galaxy[9].mission_list = new int[ // folyt.
		galaxy[9].total_missions];
		galaxy[9].mission_list[0] = 64;
		galaxy[9].mission_list[1] = 65;
		galaxy[9].mission_list[2] = 66;
		galaxy[9].mission_list[3] = 67;
		galaxy[9].mission_list[4] = 68;
		galaxy[9].mission_list[5] = 69;
		galaxy[9].mission_list[6] = 70;
		galaxy[9].bt_xy[0] = (int)(hd*106);
		galaxy[9].bt_xy[1] = (int)(hd*282);
		galaxy[9].label_xy[0] = (int)(hd*108);
		galaxy[9].label_xy[1] = (int)(hd*282);
		galaxy[9].color[0] = 0.6f; // Red
		galaxy[9].color[1] = 1.0f; // Green
		galaxy[9].color[2] = 0.7f; // Blue
		//galaxy[9].fullgame = true;

		galaxy[10].name = "BL Lacertae";
		galaxy[10].distance = 350000;
		galaxy[10].status = 0;
		galaxy[10].total_missions = 6;
		galaxy[10].unlock_galaxy_list = new int[1];
		galaxy[10].unlock_galaxy_list[0] = 11;
		galaxy[10].unlockedby_galaxy_list = new int[1];
		galaxy[10].unlockedby_galaxy_list[0] = 8;
		galaxy[10].update = 8;
		galaxy[10].mission_list = new int[ // folyt.
		galaxy[10].total_missions];
		galaxy[10].mission_list[0] = 71;
		galaxy[10].mission_list[1] = 72;
		galaxy[10].mission_list[2] = 73;
		galaxy[10].mission_list[3] = 74;
		galaxy[10].mission_list[4] = 75;
		galaxy[10].mission_list[5] = 76;
		galaxy[10].bt_xy[0] = (int)(hd*52);
		galaxy[10].bt_xy[1] = (int)(hd*127);
		galaxy[10].label_xy[0] = (int)(hd*48);
		galaxy[10].label_xy[1] = (int)(hd*127);
		galaxy[10].color[0] = 1.0f; // Red
		galaxy[10].color[1] = 0.7f; // Green
		galaxy[10].color[2] = 0.6f; // Blue
		//galaxy[10].fullgame = true;

		galaxy[11].name = "TN J0924-2201";
		galaxy[11].distance = 450000;
		galaxy[11].status = 0;
		galaxy[11].total_missions = 9;
		galaxy[11].unlock_galaxy_list = new int[1];
		galaxy[11].unlock_galaxy_list[0] = 12;
		galaxy[11].unlockedby_galaxy_list = new int[2];
		galaxy[11].unlockedby_galaxy_list[0] = 9;
		galaxy[11].unlockedby_galaxy_list[1] = 10;
		galaxy[11].update = 9;
		galaxy[11].mission_list = new int[ // folyt.
		galaxy[11].total_missions];
		galaxy[11].mission_list[0] = 77;
		galaxy[11].mission_list[1] = 78;
		galaxy[11].mission_list[2] = 79;
		galaxy[11].mission_list[3] = 80;
		galaxy[11].mission_list[4] = 81;
		galaxy[11].mission_list[5] = 82;
		galaxy[11].mission_list[6] = 83;
		galaxy[11].mission_list[7] = 84;
		galaxy[11].mission_list[8] = 85;
		galaxy[11].bt_xy[0] = (int)(hd*133);
		galaxy[11].bt_xy[1] = (int)(hd*187);
		galaxy[11].label_xy[0] = (int)(hd*133);
		galaxy[11].label_xy[1] = (int)(hd*187);
		galaxy[11].color[0] = 1.0f; // Red
		galaxy[11].color[1] = 0.7f; // Green
		galaxy[11].color[2] = 0.6f; // Blue
		//galaxy[11].fullgame = true;

		galaxy[12].name = "Markarian 421";
		galaxy[12].distance = 600000;
		galaxy[12].status = 0;
		galaxy[12].total_missions = 8;
		galaxy[12].unlock_galaxy_list = new int[1];
		galaxy[12].unlock_galaxy_list[0] = 13;
		galaxy[12].unlockedby_galaxy_list = new int[1];
		galaxy[12].unlockedby_galaxy_list[0] = 11;
		galaxy[12].update = 10;
		galaxy[12].mission_list = new int[ // folyt.
		galaxy[12].total_missions];
		galaxy[12].mission_list[0] = 86;
		galaxy[12].mission_list[1] = 87;
		galaxy[12].mission_list[2] = 88;
		galaxy[12].mission_list[3] = 89;
		galaxy[12].mission_list[4] = 90;
		galaxy[12].mission_list[5] = 91;
		galaxy[12].mission_list[6] = 92;
		galaxy[12].mission_list[7] = 93;
		galaxy[12].bt_xy[0] = (int)(hd*256);
		galaxy[12].bt_xy[1] = (int)(hd*262);
		galaxy[12].label_xy[0] = (int)(hd*246);
		galaxy[12].label_xy[1] = (int)(hd*262);
		galaxy[12].color[0] = 1.0f; // Red
		galaxy[12].color[1] = 0.9f; // Green
		galaxy[12].color[2] = 0.9f; // Blue
		galaxy[12].fullgame = true;

		galaxy[13].name = "Sagittarius Dwarf";
		galaxy[13].distance = 700000;
		galaxy[13].status = 0;
		galaxy[13].total_missions = 7;
		galaxy[13].unlock_galaxy_list = new int[2];
		galaxy[13].unlock_galaxy_list[0] = 14;
		galaxy[13].unlock_galaxy_list[1] = 15;
		galaxy[13].unlockedby_galaxy_list = new int[1];
		galaxy[13].unlockedby_galaxy_list[0] = 12;
		galaxy[13].update = 11;
		galaxy[13].mission_list = new int[ // folyt.
		galaxy[13].total_missions];
		galaxy[13].mission_list[0] = 94;
		galaxy[13].mission_list[1] = 95;
		galaxy[13].mission_list[2] = 96;
		galaxy[13].mission_list[3] = 97;
		galaxy[13].mission_list[4] = 98;
		galaxy[13].mission_list[5] = 99;
		galaxy[13].mission_list[6] = 100;
		galaxy[13].bt_xy[0] = (int)(hd*323);
		galaxy[13].bt_xy[1] = (int)(hd*131);
		galaxy[13].label_xy[0] = (int)(hd*313);
		galaxy[13].label_xy[1] = (int)(hd*131);
		galaxy[13].color[0] = 1.0f; // Red
		galaxy[13].color[1] = 0.7f; // Green
		galaxy[13].color[2] = 0.6f; // Blue
		galaxy[13].fullgame = true;

		galaxy[14].name = "3C279";
		galaxy[14].distance = 700000;
		galaxy[14].status = 0;
		galaxy[14].total_missions = 5;
		galaxy[14].unlock_galaxy_list = new int[1];
		galaxy[14].unlock_galaxy_list[0] = -1;
		galaxy[14].unlockedby_galaxy_list = new int[1];
		galaxy[14].unlockedby_galaxy_list[0] = 13;
		galaxy[14].update = 11;
		galaxy[14].mission_list = new int[ // folyt.
		galaxy[14].total_missions];
		galaxy[14].mission_list[0] = 101;
		galaxy[14].mission_list[1] = 102;
		galaxy[14].mission_list[2] = 103;
		galaxy[14].mission_list[3] = 104;
		galaxy[14].mission_list[4] = 105;
		galaxy[14].bt_xy[0] = (int)(hd*373);
		galaxy[14].bt_xy[1] = (int)(hd*253);
		galaxy[14].label_xy[0] = (int)(hd*383);
		galaxy[14].label_xy[1] = (int)(hd*253);
		galaxy[14].color[0] = 1.0f; // Red
		galaxy[14].color[1] = 0.7f; // Green
		galaxy[14].color[2] = 0.6f; // Blue
		galaxy[14].isbonus = true;
		//galaxy[14].isboss = true;
		galaxy[14].fullgame = true;
		
		galaxy[15].name = "Baby Boom Galaxy";
		galaxy[15].distance = 1000000;
		galaxy[15].status = 0;
		galaxy[15].total_missions = 9;
		galaxy[15].unlock_galaxy_list = new int[1];
		galaxy[15].unlock_galaxy_list[0] = -1;
		galaxy[15].unlockedby_galaxy_list = new int[1];
		galaxy[15].unlockedby_galaxy_list[0] = 14;
		galaxy[15].update = 12;
		galaxy[15].mission_list = new int[ // folyt.
		galaxy[15].total_missions];
		galaxy[15].mission_list[0] = 106;
		galaxy[15].mission_list[1] = 107;
		galaxy[15].mission_list[2] = 108;
		galaxy[15].mission_list[3] = 109;
		galaxy[15].mission_list[4] = 110;
		galaxy[15].mission_list[5] = 111;
		galaxy[15].mission_list[6] = 112;
		galaxy[15].mission_list[7] = 113;
		galaxy[15].mission_list[8] = 114;
		galaxy[15].bt_xy[0] = (int)(hd*210);
		galaxy[15].bt_xy[1] = (int)(hd*78);
		galaxy[15].label_xy[0] = (int)(hd*195);
		galaxy[15].label_xy[1] = (int)(hd*78);
		galaxy[15].color[0] = 1.0f; // Red
		galaxy[15].color[1] = 0.7f; // Green
		galaxy[15].color[2] = 0.6f; // Blue
		//galaxy[15].isboss = true;
		galaxy[15].fullgame = true;
		
		for (int i=0; i<16; i++) {
			wormholeA[i] = new My2DImage(120,200,true);
		}
		for (int i=0; i<10; i++) {
			shiningstar[i] = new My2DImage(64,64,true);
		}
		
		for (int i=0; i<8; i++) {
			starline[i] = new My2DImage((int)(hd*10),(int)(hd*200),true);
		}
		for (int i=0; i<6; i++) {
			parrow[i] = new My2DImage((int)(hd*90),(int)(hd*42),true);
		}
		
		for (int i=0; i<total_mission; i++) {
			mission[i] = new Mission();
		}
	
		// Milky Way 0
		mission[0].name = "Moon Mission";
		mission[0].description = "Description";
		mission[0].difficulty = 0;
		mission[0].reward = 18000;
		mission[0].mission_to_load = 0;
		mission[0].status = 1;
		mission[0].bt_xy[0] = -(int)(hd*10);
		mission[0].bt_xy[1] = (int)(hd*397);
		mission[0].label_xy[0] = (int)(hd*0);
		mission[0].label_xy[1] = (int)(hd*0);
		mission[0].music = 1;
		mission[0].background = 1;
		mission[0].power = 1800;
		mission[0].npc_group = 0;
		
		mission[1].name = "Mission Venus";
		mission[1].description = "Description";
		mission[1].difficulty = 1;
		mission[1].reward = 15000;
		mission[1].mission_to_load = 1;
		mission[1].status = 1;
		mission[1].bt_xy[0] = (int)(hd*50);
		mission[1].bt_xy[1] = (int)(hd*295);
		mission[1].label_xy[0] = (int)(hd*0);
		mission[1].label_xy[1] = (int)(hd*0);
		mission[1].music = 2;
		mission[1].background = 7;
		mission[1].status = 0;
		mission[1].power = 1800;
		mission[1].npc_group = 0;

		mission[2].name = "Mission Mercury";
		mission[2].description = "Description";
		mission[2].difficulty = 2;
		mission[2].reward = 20000;
		mission[2].mission_to_load = 2;
		mission[2].status = 1;
		mission[2].bt_xy[0] = (int)(hd*179);
		mission[2].bt_xy[1] = (int)(hd*219);
		mission[2].label_xy[0] = (int)(hd*0);
		mission[2].label_xy[1] = (int)(hd*0);
		mission[2].music = 3;
		mission[2].background = 6;
		mission[2].fullgame = true;
		mission[2].power = 1800;
		mission[2].npc_group = 0;

		mission[3].name = "Halleys Comet";
		mission[3].description = "Description";
		mission[3].difficulty = 0;
		mission[3].reward = 15000;
		mission[3].mission_to_load = 3;
		mission[3].status = 1;
		mission[3].bt_xy[0] = (int)(hd*384);
		mission[3].bt_xy[1] = (int)(hd*279);
		mission[3].label_xy[0] = (int)(hd*0);
		mission[3].label_xy[1] = (int)(hd*0);
		mission[3].music = 4;
		mission[3].background = 0;
		mission[3].power = 1800;
		mission[3].npc_group = 0;

		mission[4].name = "Draconids";
		mission[4].description = "Description";
		mission[4].difficulty = 1;
		mission[4].reward = 20000;
		mission[4].mission_to_load = 4;
		mission[4].status = 1;
		mission[4].bt_xy[0] = (int)(hd*131);
		mission[4].bt_xy[1] = (int)(hd*99);
		mission[4].label_xy[0] = (int)(hd*0);
		mission[4].label_xy[1] = (int)(hd*0);
		mission[4].music = 1;
		mission[4].background = 2;
		mission[4].power = 1800;
		mission[4].npc_group = 0;

		mission[5].name = "Proxima Centauri";
		mission[5].description = "Description";
		mission[5].difficulty = 1;
		mission[5].reward = 22000;
		mission[5].mission_to_load = 5;
		mission[5].status = 1;
		mission[5].bt_xy[0] = (int)(hd*361);
		mission[5].bt_xy[1] = (int)(hd*405);
		mission[5].label_xy[0] = (int)(hd*0);
		mission[5].label_xy[1] = (int)(hd*0);
		mission[5].music = 2;
		mission[5].mining = true;
		mission[5].background = 5;
		mission[5].power = 1800;
		mission[5].npc_group = 0;

		mission[6].name = "Sun Surface";
		mission[6].description = "Description";
		mission[6].difficulty = 2;
		mission[6].reward = 25000;
		mission[6].mission_to_load = 6;
		mission[6].status = 1;
		mission[6].bt_xy[0] = (int)(hd*341);
		mission[6].bt_xy[1] = (int)(hd*105);
		mission[6].label_xy[0] = (int)(hd*0);
		mission[6].label_xy[1] = (int)(hd*0);
		mission[6].music = 3;
		mission[6].background = 9;
		mission[6].power = 1800;
		mission[6].npc_group = 0;

		mission[7].name = "Space Station";
		mission[7].description = "Description";
		mission[7].status = 2;
		mission[7].bt_xy[0] = (int)(hd*204);
		mission[7].bt_xy[1] = (int)(hd*426);
		mission[7].label_xy[0] = (int)(hd*0);
		mission[7].label_xy[1] = (int)(hd*0);
		mission[7].textup = true;

		// Omega Centauri = galaxy 1
		mission[8].name = "Troad Group";
		mission[8].description = "Description";
		mission[8].difficulty = 2;
		mission[8].reward = 30000;
		mission[8].mission_to_load = 7;
		mission[8].status = 1;
		mission[8].bt_xy[0] = (int)(hd*4);
		mission[8].bt_xy[1] = (int)(hd*81);
		mission[8].label_xy[0] = (int)(hd*0);
		mission[8].label_xy[1] = (int)(hd*0);
		mission[8].music = 1;
		mission[8].fullgame = true;
		mission[8].background = 3;
		mission[8].power = 2500;
		mission[8].npc_group = 1;

		mission[9].name = "Cyrus  Cloud";
		mission[9].description = "Description";
		mission[9].difficulty = 1;
		mission[9].reward = 10000;
		mission[9].mission_to_load = 8;
		mission[9].status = 1;
		mission[9].bt_xy[0] = (int)(hd*167);
		mission[9].bt_xy[1] = (int)(hd*41);
		mission[9].label_xy[0] = (int)(hd*0);
		mission[9].label_xy[1] = (int)(hd*0);
		mission[9].music = 2;
		mission[9].background = 8;
		mission[9].power = 2500;
		mission[9].npc_group = 1;

		mission[10].name = "Omega Star";
		mission[10].description = "Description";
		mission[10].difficulty = 1;
		mission[10].reward = 30000;
		mission[10].mission_to_load = 9;
		mission[10].status = 1;
		mission[10].bt_xy[0] = (int)(hd*348);
		mission[10].bt_xy[1] = (int)(hd*127);
		mission[10].label_xy[0] = (int)(hd*0);
		mission[10].label_xy[1] = (int)(hd*0);
		mission[10].music = 3;
		mission[10].background = 9;
		mission[10].power = 2500;
		mission[10].npc_group = 1;

		mission[11].name = "Apex Zones";
		mission[11].description = "Description";
		mission[11].difficulty = 0;
		mission[11].reward = 25000;
		mission[11].mission_to_load = 10;
		mission[11].status = 1;
		mission[11].bt_xy[0] = (int)(hd*143);
		mission[11].bt_xy[1] = (int)(hd*331);
		mission[11].label_xy[0] = (int)(hd*0);
		mission[11].label_xy[1] = (int)(hd*0);
		mission[11].music = 4;
		mission[11].background = 0;
		mission[11].power = 2500;
		mission[11].npc_group = 1;

		mission[12].name = "Jarzer Meteorits";
		mission[12].description = "Description";
		mission[12].difficulty = 1;
		mission[12].reward = 35000;
		mission[12].mission_to_load = 11;
		mission[12].status = 1;
		mission[12].bt_xy[0] = (int)(hd*318);
		mission[12].bt_xy[1] = (int)(hd*394);
		mission[12].label_xy[0] = (int)(hd*0);
		mission[12].label_xy[1] = (int)(hd*0);
		mission[12].music = 1;
		mission[12].background = 1;
		mission[12].power = 2500;
		mission[12].npc_group = 1;

		mission[13].name = "Red Virgin Planet";
		mission[13].description = "Description";
		mission[13].difficulty = 2;
		mission[13].reward = 25000;
		mission[13].mission_to_load = 12;
		mission[13].status = 1;
		mission[13].bt_xy[0] = (int)(hd*0);
		mission[13].bt_xy[1] = (int)(hd*600);
		mission[13].label_xy[0] = (int)(hd*0);
		mission[13].label_xy[1] = (int)(hd*0);
		mission[13].music = 2;
		mission[13].background = 7;
		mission[13].power = 2500;
		mission[13].npc_group = 1;

		mission[14].name = "Space Station";
		mission[14].description = "Description";
		mission[14].status = 2;
		mission[14].bt_xy[0] = (int)(hd*280);
		mission[14].bt_xy[1] = (int)(hd*503);
		mission[14].label_xy[0] = (int)(hd*0);
		mission[14].label_xy[1] = (int)(hd*0);
		mission[14].textup = false;

		// 2
		mission[15].name = "Hablo Nebula";
		mission[15].description = "Description";
		mission[15].difficulty = 0;
		mission[15].reward = 25000;
		mission[15].mission_to_load = 14;
		mission[15].status = 1;
		mission[15].bt_xy[0] = (int)(hd*49);
		mission[15].bt_xy[1] = (int)(hd*47);
		mission[15].label_xy[0] = (int)(hd*0);
		mission[15].label_xy[1] = (int)(hd*0);
		mission[15].music = 4;
		mission[15].mining = true;
		mission[15].background = 3;
		mission[15].power = 2500;
		mission[15].npc_group = 1;

		mission[16].name = "Hydra A";
		mission[16].description = "Description";
		mission[16].difficulty = 0;
		mission[16].reward = 25000;
		mission[16].mission_to_load = 14;
		mission[16].status = 1;
		mission[16].bt_xy[0] = (int)(hd*120);
		mission[16].bt_xy[1] = (int)(hd*135);
		mission[16].label_xy[0] = (int)(hd*0);
		mission[16].label_xy[1] = (int)(hd*0);
		mission[16].music = 4;
		mission[16].mining = true;
		mission[16].background = 8;
		mission[16].power = 2500;
		mission[16].npc_group = 1;

		mission[17].name = "Lindsay-Shapley Ring";
		mission[17].description = "Description";
		mission[17].difficulty = 0;
		mission[17].reward = 30000;
		mission[17].mission_to_load = 15;
		mission[17].status = 1;
		mission[17].bt_xy[0] = (int)(hd*316);
		mission[17].bt_xy[1] = (int)(hd*56);
		mission[17].label_xy[0] = (int)(hd*0);
		mission[17].label_xy[1] = (int)(hd*0);
		mission[17].music = 4;
		mission[17].background = 1;
		mission[17].power = 2500;
		mission[17].npc_group = 1;

		mission[18].name = "Gamma Leonis Group";
		mission[18].description = "Description";
		mission[18].difficulty = 1;
		mission[18].reward = 35000;
		mission[18].mission_to_load = 16;
		mission[18].status = 1;
		mission[18].bt_xy[0] = (int)(hd*8);
		mission[18].bt_xy[1] = (int)(hd*323);
		mission[18].label_xy[0] = (int)(hd*0);
		mission[18].label_xy[1] = (int)(hd*0);
		mission[18].music = 1;
		mission[18].background = 2;
		mission[18].power = 2500;
		mission[18].npc_group = 1;

		mission[19].name = "Keenan Planet";
		mission[19].description = "Description";
		mission[19].difficulty = 0;
		mission[19].reward = 30000;
		mission[19].mission_to_load = 17;
		mission[19].status = 1;
		mission[19].bt_xy[0] = (int)(hd*125);
		mission[19].bt_xy[1] = (int)(hd*464);
		mission[19].label_xy[0] = (int)(hd*0);
		mission[19].label_xy[1] = (int)(hd*0);
		mission[19].music = 2;
		mission[19].fullgame = true;
		mission[19].background = 4;
		mission[19].power = 2500;
		mission[19].npc_group = 1;

		mission[20].name = "Mayalls Meteors";
		mission[20].description = "Description";
		mission[20].difficulty = 2;
		mission[20].reward = 35000;
		mission[20].mission_to_load = 18;
		mission[20].status = 1;
		mission[20].bt_xy[0] = (int)(hd*341);
		mission[20].bt_xy[1] = (int)(hd*374);
		mission[20].label_xy[0] = (int)(hd*0);
		mission[20].label_xy[1] = (int)(hd*0);
		mission[20].music = 4;
		mission[20].fullgame = true;
		mission[20].background = 0;
		mission[20].power = 2500;
		mission[20].npc_group = 1;

		mission[21].name = "New Mars";
		mission[21].description = "Description";
		mission[21].difficulty = 1;
		mission[21].reward = 40000;
		mission[21].mission_to_load = 19;
		mission[21].status = 1;
		mission[21].bt_xy[0] = (int)(hd*222);
		mission[21].bt_xy[1] = (int)(hd*536);
		mission[21].label_xy[0] = (int)(hd*0);
		mission[21].label_xy[1] = (int)(hd*0);
		mission[21].music = 3;
		mission[21].mining = true;
		mission[21].background = 7;
		mission[21].power = 2500;
		mission[21].npc_group = 1;

		mission[22].name = "Space Station";
		mission[22].description = "Description";
		mission[22].status = 2;
		mission[22].bt_xy[0] = (int)(hd*356);
		mission[22].bt_xy[1] = (int)(hd*540);
		mission[22].label_xy[0] = (int)(hd*0);
		mission[22].label_xy[1] = (int)(hd*0);
		mission[22].textup = false;
		
		// bode 3
		mission[23].name = "Antoss Planet";
		mission[23].description = "Description";
		mission[23].difficulty = 0;
		mission[23].reward = 30000;
		mission[23].mission_to_load = 21;
		mission[23].status = 1;
		mission[23].bt_xy[0] = (int)(hd*313);
		mission[23].bt_xy[1] = (int)(hd*48);
		mission[23].label_xy[0] = (int)(hd*0);
		mission[23].label_xy[1] = (int)(hd*0);
		mission[23].music = 1;
		mission[23].background = 6;
		mission[23].power = 4500;
		mission[23].npc_group = 2;

		mission[24].name = "Pictor A";
		mission[24].description = "Description";
		mission[24].difficulty = 0;
		mission[24].reward = 30000;
		mission[24].mission_to_load = 21;
		mission[24].status = 1;
		mission[24].bt_xy[0] = (int)(hd*9);
		mission[24].bt_xy[1] = (int)(hd*156);
		mission[24].label_xy[0] = (int)(hd*0);
		mission[24].label_xy[1] = (int)(hd*0);
		mission[24].music = 1;
		mission[24].background = 8;
		mission[24].power = 4500;
		mission[24].npc_group = 2;

		mission[25].name = "Winnecke 4";
		mission[25].description = "Description";
		mission[25].difficulty = 2;
		mission[25].reward = 45000;
		mission[25].mission_to_load = 22;
		mission[25].status = 1;
		mission[25].bt_xy[0] = (int)(hd*320);
		mission[25].bt_xy[1] = (int)(hd*166);
		mission[25].label_xy[0] = (int)(hd*0);
		mission[25].label_xy[1] = (int)(hd*0);
		mission[25].music = 2;
		mission[25].background = 3;
		mission[25].fullgame = true;
		mission[25].power = 4500;
		mission[25].npc_group = 2;

		mission[26].name = "Palomar Moon";
		mission[26].description = "Description";
		mission[26].difficulty = 0;
		mission[26].reward = 30000;
		mission[26].mission_to_load = 23;
		mission[26].status = 1;
		mission[26].bt_xy[0] = (int)(hd*42);
		mission[26].bt_xy[1] = (int)(hd*317);
		mission[26].label_xy[0] = (int)(hd*0);
		mission[26].label_xy[1] = (int)(hd*0);
		mission[26].music = 4;
		mission[26].background = 1;
		mission[26].power = 4500;
		mission[26].npc_group = 2;

		mission[27].name = "Trapezium Cluster";
		mission[27].description = "Description";
		mission[27].difficulty = 2;
		mission[27].reward = 40000;
		mission[27].mission_to_load = 24;
		mission[27].status = 1;
		mission[27].bt_xy[0] = (int)(hd*167);
		mission[27].bt_xy[1] = (int)(hd*401);
		mission[27].label_xy[0] = (int)(hd*0);
		mission[27].label_xy[1] = (int)(hd*0);
		mission[27].music = 3;
		mission[27].background = 2;
		mission[27].power = 4500;
		mission[27].npc_group = 2;

		mission[28].name = "Theta Carinae";
		mission[28].description = "Description";
		mission[28].difficulty = 1;
		mission[28].reward = 45000;
		mission[28].mission_to_load = 91;
		mission[28].status = 1;
		mission[28].bt_xy[0] = (int)(hd*78);
		mission[28].bt_xy[1] = (int)(hd*604);
		mission[28].label_xy[0] = (int)(hd*0);
		mission[28].label_xy[1] = (int)(hd*0);
		mission[28].music = 1;
		mission[28].background = 0;
		mission[28].fullgame = true;
		mission[28].power = 4500;
		mission[28].npc_group = 2;

		mission[29].name = "Space Station";
		mission[29].description = "Description";
		mission[29].status = 2;
		mission[29].bt_xy[0] = (int)(hd*344);
		mission[29].bt_xy[1] = (int)(hd*610);
		mission[29].label_xy[0] = (int)(hd*0);
		mission[29].label_xy[1] = (int)(hd*0);
		mission[29].textup = true;

		// triangulum
		mission[30].name = "Trifid Belt";
		mission[30].description = "Description";
		mission[30].difficulty = 0;
		mission[30].reward = 50000;
		mission[30].mission_to_load = 26;
		mission[30].status = 1;
		mission[30].bt_xy[0] = (int)(hd*89);
		mission[30].bt_xy[1] = (int)(hd*220);
		mission[30].label_xy[0] = (int)(hd*0);
		mission[30].label_xy[1] = (int)(hd*0);
		mission[30].music = 2;
		mission[30].background = 1;
		mission[30].power = 7000;
		mission[30].npc_group = 3;

		mission[31].name = "Twinjet Asteroids";
		mission[31].description = "Description";
		mission[31].difficulty = 1;
		mission[31].reward = 45000;
		mission[31].mission_to_load = 27;
		mission[31].status = 1;
		mission[31].bt_xy[0] = (int)(hd*31);
		mission[31].bt_xy[1] = (int)(hd*338);
		mission[31].label_xy[0] = (int)(hd*0);
		mission[31].label_xy[1] = (int)(hd*0);
		mission[31].music = 3;
		mission[31].background = 0;
		mission[31].power = 7000;
		mission[31].npc_group = 3;

		mission[32].name = "Colony Ship Wreck";
		mission[32].description = "Description";
		mission[32].difficulty = 2;
		mission[32].reward = 55000;
		mission[32].mission_to_load = 28;
		mission[32].status = 1;
		mission[32].bt_xy[0] = (int)(hd*159);
		mission[32].bt_xy[1] = (int)(hd*372);
		mission[32].label_xy[0] = (int)(hd*0);
		mission[32].label_xy[1] = (int)(hd*0);
		mission[32].music = 1;
		mission[32].background = 8;
		mission[32].power = 7000;
		mission[32].npc_group = 3;

		mission[33].name = "Toadstool Group";
		mission[33].description = "Description";
		mission[33].difficulty = 2;
		mission[33].reward = 50000;
		mission[33].mission_to_load = 29;
		mission[33].status = 1;
		mission[33].bt_xy[0] = (int)(hd*67);
		mission[33].bt_xy[1] = (int)(hd*537);
		mission[33].label_xy[0] = (int)(hd*0);
		mission[33].label_xy[1] = (int)(hd*0);
		mission[33].music = 4;
		mission[33].fullgame = true;
		mission[33].background = 5;
		mission[33].power = 7000;
		mission[33].npc_group = 3;

		mission[34].name = "Simeis 147";
		mission[34].description = "Description";
		mission[34].difficulty = 0;
		mission[34].reward = 55000;
		mission[34].mission_to_load = 30;
		mission[34].status = 1;
		mission[34].bt_xy[0] = (int)(hd*321);
		mission[34].bt_xy[1] = (int)(hd*517);
		mission[34].label_xy[0] = (int)(hd*0);
		mission[34].label_xy[1] = (int)(hd*0);
		mission[34].music = 2;
		mission[34].fullgame = true;
		mission[34].background = 2;
		mission[34].power = 7000;
		mission[34].npc_group = 3;

		mission[35].name = "Space Station";
		mission[35].description = "Description";
		mission[35].status = 2;
		mission[35].bt_xy[0] = (int)(hd*291);
		mission[35].bt_xy[1] = (int)(hd*56);
		mission[35].label_xy[0] = (int)(hd*0);
		mission[35].label_xy[1] = (int)(hd*0);
		mission[35].textup = false;

		//
		mission[36].name = "Nerandra Exoplanet";
		mission[36].description = "Description";
		mission[36].difficulty = 0;
		mission[36].reward = 65000;
		mission[36].mission_to_load = 31;
		mission[36].status = 1;
		mission[36].bt_xy[0] = (int)(hd*67);
		mission[36].bt_xy[1] = (int)(hd*87);
		mission[36].label_xy[0] = (int)(hd*0);
		mission[36].label_xy[1] = (int)(hd*0);
		mission[36].music = 1;
		mission[36].background = 4;
		mission[36].fullgame = true;
		mission[36].power = 10000;
		mission[36].npc_group = 4;

		mission[37].name = "Tau CMa cluster";
		mission[37].description = "Description";
		mission[37].difficulty = 1;
		mission[37].reward = 60000;
		mission[37].mission_to_load = 32;
		mission[37].status = 1;
		mission[37].bt_xy[0] = (int)(hd*74);
		mission[37].bt_xy[1] = (int)(hd*221);
		mission[37].label_xy[0] = (int)(hd*0);
		mission[37].label_xy[1] = (int)(hd*0);
		mission[37].music = 2;
		mission[37].background = 2;
		mission[37].power = 10000;
		mission[37].npc_group = 4;

		mission[38].name = "White Eyed Pea";
		mission[38].description = "Description";
		mission[38].difficulty = 2;
		mission[38].reward = 60000;
		mission[38].mission_to_load = 33;
		mission[38].status = 1;
		mission[38].bt_xy[0] = (int)(hd*369);
		mission[38].bt_xy[1] = (int)(hd*130);
		mission[38].label_xy[0] = (int)(hd*0);
		mission[38].label_xy[1] = (int)(hd*0);
		mission[38].music = 4;
		mission[38].background = 5;
		mission[38].fullgame = true;
		mission[38].power = 10000;
		mission[38].npc_group = 4;

		mission[39].name = "Ceres Giant Planet";
		mission[39].description = "Description";
		mission[39].difficulty = 1;
		mission[39].reward = 70000;
		mission[39].mission_to_load = 34;
		mission[39].status = 1;
		mission[39].bt_xy[0] = (int)(hd*182);
		mission[39].bt_xy[1] = (int)(hd*340);
		mission[39].label_xy[0] = (int)(hd*0);
		mission[39].label_xy[1] = (int)(hd*0);
		mission[39].music = 3;
		mission[39].fullgame = true;
		mission[39].background = 7;
		mission[39].power = 10000;
		mission[39].npc_group = 4;

		mission[40].name = "Hungaria Comet";
		mission[40].description = "Description";
		mission[40].difficulty = 1;
		mission[40].reward = 70000;
		mission[40].mission_to_load = 35;
		mission[40].status = 1;
		mission[40].bt_xy[0] = (int)(hd*369);
		mission[40].bt_xy[1] = (int)(hd*352);
		mission[40].label_xy[0] = (int)(hd*0);
		mission[40].label_xy[1] = (int)(hd*0);
		mission[40].music = 2;
		mission[40].background = 1;
		mission[40].fullgame = true;
		mission[40].power = 10000;
		mission[40].npc_group = 4;

		mission[41].name = "Rho Ophiuchi Complex";
		mission[41].description = "Description";
		mission[41].difficulty = 0;
		mission[41].reward = 65000;
		mission[41].mission_to_load = 36;
		mission[41].status = 1;
		mission[41].bt_xy[0] = (int)(hd*164);
		mission[41].bt_xy[1] = (int)(hd*454);
		mission[41].label_xy[0] = (int)(hd*0);
		mission[41].label_xy[1] = (int)(hd*0);
		mission[41].music = 2;
		mission[41].background = 5;
		mission[41].power = 10000;
		mission[41].npc_group = 4;

		mission[42].name = "Dyson Asteroid Belt";
		mission[42].description = "Description";
		mission[42].difficulty = 0;
		mission[42].reward = 65000;
		mission[42].mission_to_load = 36;
		mission[42].status = 1;
		mission[42].bt_xy[0] = (int)(hd*81);
		mission[42].bt_xy[1] = (int)(hd*565);
		mission[42].label_xy[0] = (int)(hd*0);
		mission[42].label_xy[1] = (int)(hd*0);
		mission[42].music = 2;
		mission[42].background = 0;
		mission[42].fullgame = true;
		mission[42].power = 10000;
		mission[42].npc_group = 4;

		mission[43].name = "Cleopatra's Eye";
		mission[43].description = "Description";
		mission[43].difficulty = 3;
		mission[43].reward = 75000;
		mission[43].mission_to_load = 37;
		mission[43].status = 1;
		mission[43].bt_xy[0] = (int)(hd*278);
		mission[43].bt_xy[1] = (int)(hd*532);
		mission[43].label_xy[0] = (int)(hd*0);
		mission[43].label_xy[1] = (int)(hd*0);
		mission[43].music = 1;
		mission[43].background = 9;
		mission[43].power = 10000;
		mission[43].npc_group = 4;

		mission[44].name = "Space Station";
		mission[44].description = "Description";
		mission[44].status = 2;
		mission[44].bt_xy[0] = (int)(hd*254);
		mission[44].bt_xy[1] = (int)(hd*189);
		mission[44].label_xy[0] = (int)(hd*0);
		mission[44].label_xy[1] = (int)(hd*0);
		mission[44].textup = true;

		
		// End of Free Game // Unlocked Content Below //
		
		
		mission[45].name = "Gamma Leonis Group";
		mission[45].description = "Description";
		mission[45].difficulty = 1;
		mission[45].reward = 65000;
		mission[45].mission_to_load = 39;
		mission[45].status = 1;
		mission[45].bt_xy[0] = (int)(hd*61);
		mission[45].bt_xy[1] = (int)(hd*148);
		mission[45].label_xy[0] = (int)(hd*0);
		mission[45].label_xy[1] = (int)(hd*0);
		mission[45].music = 1;
		mission[45].background = 1;
		mission[45].power = 10000;
		mission[45].npc_group = 5;

		mission[46].name = "Grus Quartet";
		mission[46].description = "Description";
		mission[46].difficulty = 2;
		mission[46].reward = 80000;
		mission[46].mission_to_load = 42;
		mission[46].status = 1;
		mission[46].bt_xy[0] = (int)(hd*20);
		mission[46].bt_xy[1] = (int)(hd*576);
		mission[46].label_xy[0] = (int)(hd*0);
		mission[46].label_xy[1] = (int)(hd*0);
		mission[46].music = 3;
		mission[46].background = 3;
		mission[46].power = 10000;
		mission[46].npc_group = 5;

		mission[47].name = "Red Giant";
		mission[47].description = "Description";
		mission[47].difficulty = 1;
		mission[47].reward = 75000;
		mission[47].mission_to_load = 40;
		mission[47].status = 1;
		mission[47].bt_xy[0] = (int)(hd*254);
		mission[47].bt_xy[1] = (int)(hd*204);
		mission[47].label_xy[0] = (int)(hd*0);
		mission[47].label_xy[1] = (int)(hd*0);
		mission[47].music = 2;
		mission[47].background = 9;
		mission[47].power = 10000;
		mission[47].npc_group = 5;

		mission[48].name = "Ghost Head Nebula";
		mission[48].description = "Description";
		mission[48].difficulty = 2;
		mission[48].reward = 75000;
		mission[48].mission_to_load = 41;
		mission[48].status = 1;
		mission[48].bt_xy[0] = (int)(hd*41);
		mission[48].bt_xy[1] = (int)(hd*432);
		mission[48].label_xy[0] = (int)(hd*0);
		mission[48].label_xy[1] = (int)(hd*0);
		mission[48].music = 4;
		mission[48].background = 3;
		mission[48].power = 10000;
		mission[48].npc_group = 5;

		mission[49].name = "Space Station";
		mission[49].description = "Description";
		mission[49].status = 2;
		mission[49].bt_xy[0] = (int)(hd*161);
		mission[49].bt_xy[1] = (int)(hd*330);
		mission[49].label_xy[0] = (int)(hd*0);
		mission[49].label_xy[1] = (int)(hd*0);
		mission[49].textup = false;

		// 
		mission[50].name = "Doradus";
		mission[50].description = "Description";
		mission[50].difficulty = 1;
		mission[50].reward = 75000;
		mission[50].mission_to_load = 43;
		mission[50].status = 1;
		mission[50].bt_xy[0] = (int)(hd*0);
		mission[50].bt_xy[1] = (int)(hd*244);
		mission[50].label_xy[0] = (int)(hd*0);
		mission[50].label_xy[1] = (int)(hd*0);
		mission[50].music = 2;
		mission[50].background = 5;
		mission[50].power = 10000;
		mission[50].npc_group = 6;

		mission[51].name = "Burbidge Chain";
		mission[51].description = "Description";
		mission[51].difficulty = 1;
		mission[51].reward = 65000;
		mission[51].mission_to_load = 44;
		mission[51].status = 1;
		mission[51].bt_xy[0] = (int)(hd*300);
		mission[51].bt_xy[1] = (int)(hd*161);
		mission[51].label_xy[0] = (int)(hd*0);
		mission[51].label_xy[1] = (int)(hd*0);
		mission[51].music = 1;
		mission[51].background = 1;
		mission[51].power = 10000;
		mission[51].npc_group = 6;

		mission[52].name = "Butterfly Cluster";
		mission[52].description = "Description";
		mission[52].difficulty = 0;
		mission[52].reward = 60000;
		mission[52].mission_to_load = 45;
		mission[52].status = 1;
		mission[52].bt_xy[0] = (int)(hd*380);
		mission[52].bt_xy[1] = (int)(hd*310);
		mission[52].label_xy[0] = (int)(hd*0);
		mission[52].label_xy[1] = (int)(hd*0);
		mission[52].music = 2;
		mission[52].background = 8;
		mission[52].power = 10000;
		mission[52].npc_group = 6;
		
		mission[53].name = "California Comet";
		mission[53].description = "Description";
		mission[53].difficulty = 0;
		mission[53].reward = 55000;
		mission[53].mission_to_load = 46;
		mission[53].status = 1;
		mission[53].bt_xy[0] = (int)(hd*281);
		mission[53].bt_xy[1] = (int)(hd*599);
		mission[53].label_xy[0] = (int)(hd*0);
		mission[53].label_xy[1] = (int)(hd*0);
		mission[53].music = 3;
		mission[53].background = 0;
		mission[53].power = 10000;
		mission[53].npc_group = 6;

		mission[54].name = "Space Station";
		mission[54].description = "Description";
		mission[54].status = 2;
		mission[54].bt_xy[0] = (int)(hd*122);
		mission[54].bt_xy[1] = (int)(hd*485);
		mission[54].label_xy[0] = (int)(hd*0);
		mission[54].label_xy[1] = (int)(hd*0);
		mission[54].textup = true;

		// 
		mission[55].name = "Boomerang Nebula";
		mission[55].description = "Description";
		mission[55].difficulty = 2;
		mission[55].reward = 70000;
		mission[55].mission_to_load = 47;
		mission[55].status = 1;
		mission[55].bt_xy[0] = (int)(hd*55);
		mission[55].bt_xy[1] = (int)(hd*53);
		mission[55].label_xy[0] = (int)(hd*0);
		mission[55].label_xy[1] = (int)(hd*0);
		mission[55].music = 4;
		mission[55].background = 2;
		mission[55].power = 13000;
		mission[55].npc_group = 6;

		mission[56].name = "Dragonfly Cluster";
		mission[56].description = "Description";
		mission[56].difficulty = 0;
		mission[56].reward = 80000;
		mission[56].mission_to_load = 48;
		mission[56].status = 1;
		mission[56].bt_xy[0] = (int)(hd*181);
		mission[56].bt_xy[1] = (int)(hd*58);
		mission[56].label_xy[0] = (int)(hd*0);
		mission[56].label_xy[1] = (int)(hd*0);
		mission[56].music = 1;
		mission[56].background = 8;
		mission[56].power = 13000;
		mission[56].npc_group = 6;

		mission[57].name = "Proxima K";
		mission[57].description = "Description";
		mission[57].difficulty = 1;
		mission[57].reward = 90000;
		mission[57].mission_to_load = 49;
		mission[57].status = 1;
		mission[57].bt_xy[0] = (int)(hd*332);
		mission[57].bt_xy[1] = (int)(hd*104);
		mission[57].label_xy[0] = (int)(hd*0);
		mission[57].label_xy[1] = (int)(hd*0);
		mission[57].music = 2;
		mission[57].background = 3;
		mission[57].power = 13000;
		mission[57].npc_group = 6;

		mission[58].name = "Delta Chronus Comet";
		mission[58].description = "Description";
		mission[58].difficulty = 2;
		mission[58].reward = 75000;
		mission[58].mission_to_load = 50;
		mission[58].status = 1;
		mission[58].bt_xy[0] = (int)(hd*195);
		mission[58].bt_xy[1] = (int)(hd*212);
		mission[58].label_xy[0] = (int)(hd*0);
		mission[58].label_xy[1] = (int)(hd*0);
		mission[58].music = 3;
		mission[58].background = 0;
		mission[58].power = 13000;
		mission[58].npc_group = 6;

		mission[59].name = "Magellanic Belt";
		mission[59].description = "Description";
		mission[59].difficulty = 2;
		mission[59].reward = 125000;
		mission[59].mission_to_load = 54;
		mission[59].status = 1;
		mission[59].bt_xy[0] = (int)(hd*278);
		mission[59].bt_xy[1] = (int)(hd*314);
		mission[59].label_xy[0] = (int)(hd*0);
		mission[59].label_xy[1] = (int)(hd*0);
		mission[59].music = 3;
		mission[59].background = 1;
		mission[59].power = 13000;
		mission[59].npc_group = 6;

		mission[60].name = "Big Blue";
		mission[60].description = "Description";
		mission[60].difficulty = 1;
		mission[60].reward = 80000;
		mission[60].mission_to_load = 51;
		mission[60].status = 1;
		mission[60].bt_xy[0] = (int)(hd*65);
		mission[60].bt_xy[1] = (int)(hd*365);
		mission[60].label_xy[0] = (int)(hd*0);
		mission[60].label_xy[1] = (int)(hd*0);
		mission[60].music = 4;
		mission[60].background = 2;
		mission[60].power = 13000;
		mission[60].npc_group = 6;

		mission[61].name = "Maffei Moon";
		mission[61].description = "Description";
		mission[61].difficulty = 1;
		mission[61].reward = 100000;
		mission[61].mission_to_load = 52;
		mission[61].status = 1;
		mission[61].bt_xy[0] = (int)(hd*173);
		mission[61].bt_xy[1] = (int)(hd*554);
		mission[61].label_xy[0] = (int)(hd*0);
		mission[61].label_xy[1] = (int)(hd*0);
		mission[61].music = 4;
		mission[61].background = 8;
		mission[61].power = 13000;
		mission[61].npc_group = 6;

		mission[62].name = "Mini Cassiopeia";
		mission[62].description = "Description";
		mission[62].difficulty = 2;
		mission[62].reward = 130000;
		mission[62].mission_to_load = 53;
		mission[62].status = 1;
		mission[62].bt_xy[0] = (int)(hd*377);
		mission[62].bt_xy[1] = (int)(hd*437);
		mission[62].label_xy[0] = (int)(hd*0);
		mission[62].label_xy[1] = (int)(hd*0);
		mission[62].music = 3;
		mission[62].background = 5;
		mission[62].power = 13000;
		mission[62].npc_group = 6;

		mission[63].name = "Space Station";
		mission[63].description = "Description";
		mission[63].status = 2;
		mission[63].bt_xy[0] = (int)(hd*303);
		mission[63].bt_xy[1] = (int)(hd*520);
		mission[63].label_xy[0] = (int)(hd*0);
		mission[63].label_xy[1] = (int)(hd*0);
		mission[63].textup = false;

		// 
		mission[64].name = "Vulpeculae Cluster";
		mission[64].description = "Description";
		mission[64].difficulty = 0;
		mission[64].reward = 90000;
		mission[64].mission_to_load = 55;
		mission[64].status = 1;
		mission[64].bt_xy[0] = (int)(hd*337);
		mission[64].bt_xy[1] = (int)(hd*194);
		mission[64].label_xy[0] = (int)(hd*0);
		mission[64].label_xy[1] = (int)(hd*0);
		mission[64].music = 2;
		mission[64].background = 3;
		mission[64].power = 16000;
		mission[64].npc_group = 7;

		mission[65].name = "Anon Platais";
		mission[65].description = "Description";
		mission[65].difficulty = 0;
		mission[65].reward = 85000;
		mission[65].mission_to_load = 56;
		mission[65].status = 1;
		mission[65].bt_xy[0] = (int)(hd*158);
		mission[65].bt_xy[1] = (int)(hd*152);
		mission[65].label_xy[0] = (int)(hd*0);
		mission[65].label_xy[1] = (int)(hd*0);
		mission[65].music = 1;
		mission[65].background = 6;
		mission[65].power = 16000;
		mission[65].npc_group = 7;

		mission[66].name = "Antares Planet";
		mission[66].description = "Description";
		mission[66].difficulty = 2;
		mission[66].reward = 175000;
		mission[66].mission_to_load = 60;
		mission[66].status = 1;
		mission[66].bt_xy[0] = (int)(hd*7);
		mission[66].bt_xy[1] = (int)(hd*286);
		mission[66].label_xy[0] = (int)(hd*0);
		mission[66].label_xy[1] = (int)(hd*0);
		mission[66].music = 2;
		mission[66].background = 4;
		mission[66].power = 16000;
		mission[66].npc_group = 7;

		mission[67].name = "Cirrus Nebula";
		mission[67].description = "Description";
		mission[67].difficulty = 2;
		mission[67].reward = 170000;
		mission[67].mission_to_load = 57;
		mission[67].status = 1;
		mission[67].bt_xy[0] = (int)(hd*332);
		mission[67].bt_xy[1] = (int)(hd*327);
		mission[67].label_xy[0] = (int)(hd*0);
		mission[67].label_xy[1] = (int)(hd*0);
		mission[67].music = 4;
		mission[67].background = 2;
		mission[67].power = 16000;
		mission[67].npc_group = 7;

		mission[68].name = "Fath 70";
		mission[68].description = "Description";
		mission[68].difficulty = 0;
		mission[68].reward = 120000;
		mission[68].mission_to_load = 58;
		mission[68].status = 1;
		mission[68].bt_xy[0] = (int)(hd*236);
		mission[68].bt_xy[1] = (int)(hd*464);
		mission[68].label_xy[0] = (int)(hd*0);
		mission[68].label_xy[1] = (int)(hd*0);
		mission[68].music = 1;
		mission[68].background = 1;
		mission[68].power = 16000;
		mission[68].npc_group = 7;

		mission[69].name = "Holmberg Cluster";
		mission[69].description = "Description";
		mission[69].difficulty = 1;
		mission[69].reward = 150000;
		mission[69].mission_to_load = 59;
		mission[69].status = 1;
		mission[69].bt_xy[0] = (int)(hd*149);
		mission[69].bt_xy[1] = (int)(hd*610);
		mission[69].label_xy[0] = (int)(hd*0);
		mission[69].label_xy[1] = (int)(hd*0);
		mission[69].music = 4;
		mission[68].background = 0;
		mission[69].power = 16000;
		mission[69].npc_group = 7;

		mission[70].name = "Space Station";
		mission[70].description = "Description";
		mission[70].status = 2;
		mission[70].bt_xy[0] = (int)(hd*332);
		mission[70].bt_xy[1] = (int)(hd*519);
		mission[70].label_xy[0] = (int)(hd*0);
		mission[70].label_xy[1] = (int)(hd*0);
		mission[70].textup = true;

		//
		mission[71].name = "Regent Cloud";
		mission[71].description = "Description";
		mission[71].difficulty = 1;
		mission[71].reward = 140000;
		mission[71].mission_to_load = 61;
		mission[71].status = 1;
		mission[71].bt_xy[0] = (int)(hd*78);
		mission[71].bt_xy[1] = (int)(hd*80);
		mission[71].label_xy[0] = (int)(hd*0);
		mission[71].label_xy[1] = (int)(hd*0);
		mission[71].music = 3;
		mission[71].background = 3;
		mission[71].power = 16000;
		mission[71].npc_group = 7;

		mission[72].name = "Little Dumbbell Nebula";
		mission[72].description = "Description";
		mission[72].difficulty = 2;
		mission[72].reward = 140000;
		mission[72].mission_to_load = 65;
		mission[72].status = 1;
		mission[72].bt_xy[0] = (int)(hd*240);
		mission[72].bt_xy[1] = (int)(hd*247);
		mission[72].label_xy[0] = (int)(hd*0);
		mission[72].label_xy[1] = (int)(hd*0);
		mission[72].music = 1;
		mission[72].background = 2;
		mission[72].power = 16000;
		mission[72].npc_group = 7;

		mission[73].name = "Nubecula Dwarf";
		mission[73].description = "Description";
		mission[73].difficulty = 2;
		mission[73].reward = 110000;
		mission[73].mission_to_load = 62;
		mission[73].status = 1;
		mission[73].bt_xy[0] = (int)(hd*141);
		mission[73].bt_xy[1] = (int)(hd*349);
		mission[73].label_xy[0] = (int)(hd*0);
		mission[73].label_xy[1] = (int)(hd*0);
		mission[73].music = 2;
		mission[73].background = 9;
		mission[73].power = 16000;
		mission[73].npc_group = 7;

		mission[74].name = "Palomar 7";
		mission[74].description = "Description";
		mission[74].difficulty = 1;
		mission[74].reward = 150000;
		mission[74].mission_to_load = 63;
		mission[74].status = 1;
		mission[74].bt_xy[0] = (int)(hd*29);
		mission[74].bt_xy[1] = (int)(hd*410);
		mission[74].label_xy[0] = (int)(hd*0);
		mission[74].label_xy[1] = (int)(hd*0);
		mission[74].music = 3;
		mission[74].background = 8;
		mission[74].power = 16000;
		mission[74].npc_group = 7;

		mission[75].name = "Eagle Asteroid Belt";
		mission[75].description = "Description";
		mission[75].difficulty = 1;
		mission[75].reward = 160000;
		mission[75].mission_to_load = 64;
		mission[75].status = 1;
		mission[75].bt_xy[0] = (int)(hd*37);
		mission[75].bt_xy[1] = (int)(hd*588);
		mission[75].label_xy[0] = (int)(hd*0);
		mission[75].label_xy[1] = (int)(hd*0);
		mission[75].music = 4;
		mission[75].background = 1;
		mission[75].power = 16000;
		mission[75].npc_group = 7;

		mission[76].name = "Space Station";
		mission[76].description = "Description";
		mission[76].status = 2;
		mission[76].bt_xy[0] = (int)(hd*219);
		mission[76].bt_xy[1] = (int)(hd*457);
		mission[76].label_xy[0] = (int)(hd*0);
		mission[76].label_xy[1] = (int)(hd*0);
		mission[76].textup = false;

		//
		mission[77].name = "Sefeida Star";
		mission[77].description = "Description";
		mission[77].difficulty = 1;
		mission[77].reward = 150000;
		mission[77].mission_to_load = 66;
		mission[77].status = 1;
		mission[77].bt_xy[0] = (int)(hd*138);
		mission[77].bt_xy[1] = (int)(hd*65);
		mission[77].label_xy[0] = (int)(hd*0);
		mission[77].label_xy[1] = (int)(hd*0);
		mission[77].music = 4;
		mission[77].background = 9;
		mission[77].power = 16000;
		mission[77].npc_group = 8;

		mission[78].name = "Merope Nebula Part";
		mission[78].description = "Description";
		mission[78].difficulty = 2;
		mission[78].reward = 110000;
		mission[78].mission_to_load = 67;
		mission[78].status = 1;
		mission[78].bt_xy[0] = (int)(hd*367);
		mission[78].bt_xy[1] = (int)(hd*70);
		mission[78].label_xy[0] = (int)(hd*0);
		mission[78].label_xy[1] = (int)(hd*0);
		mission[78].music = 1;
		mission[78].background = 5;
		mission[78].power = 16000;
		mission[78].npc_group = 8;

		mission[79].name = "Perseus Double Cluster";
		mission[79].description = "Description";
		mission[79].difficulty = 0;
		mission[79].reward = 120000;
		mission[79].mission_to_load = 68;
		mission[79].status = 1;
		mission[79].bt_xy[0] = (int)(hd*219);
		mission[79].bt_xy[1] = (int)(hd*162);
		mission[79].label_xy[0] = (int)(hd*0);
		mission[79].label_xy[1] = (int)(hd*0);
		mission[79].music = 3;
		mission[79].background = 0;
		mission[79].power = 16000;
		mission[79].npc_group = 8;

		mission[80].name = "Ring Tail Galaxies";
		mission[80].description = "Description";
		mission[80].difficulty = 1;
		mission[80].reward = 90000;
		mission[80].mission_to_load = 69;
		mission[80].status = 1;
		mission[80].bt_xy[0] = (int)(hd*27);
		mission[80].bt_xy[1] = (int)(hd*246);
		mission[80].label_xy[0] = (int)(hd*0);
		mission[80].label_xy[1] = (int)(hd*0);
		mission[80].music = 2;
		mission[80].background = 3;
		mission[80].power = 16000;
		mission[80].npc_group = 8;

		mission[81].name = "Spirograph Nebula";
		mission[81].description = "Description";
		mission[81].difficulty = 1;
		mission[81].reward = 130000;
		mission[81].mission_to_load = 73;
		mission[81].status = 1;
		mission[81].bt_xy[0] = (int)(hd*305);
		mission[81].bt_xy[1] = (int)(hd*297);
		mission[81].label_xy[0] = (int)(hd*0);
		mission[81].label_xy[1] = (int)(hd*0);
		mission[81].music = 4;
		mission[81].background = 5;
		mission[81].power = 16000;
		mission[81].npc_group = 8;

		mission[82].name = "Draconis Hole";
		mission[82].description = "Description";
		mission[82].difficulty = 3;
		mission[82].reward = 150000;
		mission[82].mission_to_load = 70;
		mission[82].status = 1;
		mission[82].bt_xy[0] = (int)(hd*40);
		mission[82].bt_xy[1] = (int)(hd*524);
		mission[82].label_xy[0] = (int)(hd*0);
		mission[82].label_xy[1] = (int)(hd*0);
		mission[82].music = 1;
		mission[82].background = 9;
		mission[82].power = 16000;
		mission[82].npc_group = 8;

		mission[83].name = "Taurus A";
		mission[83].description = "Description";
		mission[83].difficulty = 2;
		mission[83].reward = 100000;
		mission[83].mission_to_load = 71;
		mission[83].status = 1;
		mission[83].bt_xy[0] = (int)(hd*160);
		mission[83].bt_xy[1] = (int)(hd*469);
		mission[83].label_xy[0] = (int)(hd*0);
		mission[83].label_xy[1] = (int)(hd*0);
		mission[83].music = 2;
		mission[83].background = 1;
		mission[83].power = 16000;
		mission[83].npc_group = 8;

		mission[84].name = "Theta Vortex";
		mission[84].description = "Description";
		mission[84].difficulty = 1;
		mission[84].reward = 140000;
		mission[84].mission_to_load = 72;
		mission[84].status = 1;
		mission[84].bt_xy[0] = (int)(hd*192);
		mission[84].bt_xy[1] = (int)(hd*575);
		mission[84].label_xy[0] = (int)(hd*0);
		mission[84].label_xy[1] = (int)(hd*0);
		mission[84].music = 3;
		mission[84].background = 2;
		mission[84].power = 16000;
		mission[84].npc_group = 8;

		mission[85].name = "Space Station";
		mission[85].description = "Description";
		mission[85].status = 2;
		mission[85].bt_xy[0] = (int)(hd*367);
		mission[85].bt_xy[1] = (int)(hd*483);
		mission[85].label_xy[0] = (int)(hd*0);
		mission[85].label_xy[1] = (int)(hd*0);
		mission[85].textup = true;

		//
		mission[86].name = "Diogenite Vortex";
		mission[86].description = "Description";
		mission[86].difficulty = 1;
		mission[86].reward = 140000;
		mission[86].mission_to_load = 74;
		mission[86].status = 1;
		mission[86].bt_xy[0] = (int)(hd*29);
		mission[86].bt_xy[1] = (int)(hd*50);
		mission[86].label_xy[0] = (int)(hd*0);
		mission[86].label_xy[1] = (int)(hd*0);
		mission[86].music = 2;
		mission[86].background = 2;
		mission[86].power = 19000;
		mission[86].npc_group = 8;

		mission[87].name = "Thackeray Meteorites";
		mission[87].description = "Description";
		mission[87].difficulty = 1;
		mission[87].reward = 140000;
		mission[87].mission_to_load = 74;
		mission[87].status = 1;
		mission[87].bt_xy[0] = (int)(hd*354);
		mission[87].bt_xy[1] = (int)(hd*410);
		mission[87].label_xy[0] = (int)(hd*0);
		mission[87].label_xy[1] = (int)(hd*0);
		mission[87].music = 2;
		mission[87].background = 0;
		mission[87].power = 19000;
		mission[87].npc_group = 8;

		mission[88].name = "Thor's Planet";
		mission[88].description = "Description";
		mission[88].difficulty = 2;
		mission[88].reward = 130000;
		mission[88].mission_to_load = 75;
		mission[88].status = 1;
		mission[88].bt_xy[0] = (int)(hd*174);
		mission[88].bt_xy[1] = (int)(hd*76);
		mission[88].label_xy[0] = (int)(hd*0);
		mission[88].label_xy[1] = (int)(hd*0);
		mission[88].music = 1;
		mission[88].background = 4;
		mission[88].power = 19000;
		mission[88].npc_group = 8;

		mission[89].name = "Twinjet Nebula";
		mission[89].description = "Description";
		mission[89].difficulty = 1;
		mission[89].reward = 160000;
		mission[89].mission_to_load = 76;
		mission[89].status = 1;
		mission[89].bt_xy[0] = (int)(hd*375);
		mission[89].bt_xy[1] = (int)(hd*50);
		mission[89].label_xy[0] = (int)(hd*0);
		mission[89].label_xy[1] = (int)(hd*0);
		mission[89].music = 2;
		mission[89].background = 3;
		mission[89].power = 19000;
		mission[89].npc_group = 8;

		mission[90].name = "Planet Flamarro";
		mission[90].description = "Description";
		mission[90].difficulty = 0;
		mission[90].reward = 140000;
		mission[90].mission_to_load = 77;
		mission[90].status = 1;
		mission[90].bt_xy[0] = (int)(hd*267);
		mission[90].bt_xy[1] = (int)(hd*183);
		mission[90].label_xy[0] = (int)(hd*0);
		mission[90].label_xy[1] = (int)(hd*0);
		mission[90].music = 4;
		mission[90].background = 7;
		mission[90].power = 19000;
		mission[90].npc_group = 8;

		mission[91].name = "Zeta Sculptoris Group";
		mission[91].description = "Description";
		mission[91].difficulty = 1;
		mission[91].reward = 170000;
		mission[91].mission_to_load = 78;
		mission[91].status = 1;
		mission[91].bt_xy[0] = (int)(hd*174);
		mission[91].bt_xy[1] = (int)(hd*343);
		mission[91].label_xy[0] = (int)(hd*0);
		mission[91].label_xy[1] = (int)(hd*0);
		mission[91].music = 4;
		mission[91].background = 3;
		mission[91].power = 19000;
		mission[91].npc_group = 8;

		mission[92].name = "X-mas Tree Cluster";
		mission[92].description = "Description";
		mission[92].difficulty = 1;
		mission[92].reward = 150000;
		mission[92].mission_to_load = 79;
		mission[92].status = 1;
		mission[92].bt_xy[0] = (int)(hd*14);
		mission[92].bt_xy[1] = (int)(hd*423);
		mission[92].label_xy[0] = (int)(hd*0);
		mission[92].label_xy[1] = (int)(hd*0);
		mission[92].music = 1;
		mission[92].background = 8;
		mission[92].power = 19000;
		mission[92].npc_group = 8;

		mission[93].name = "Space Station";
		mission[93].description = "Description";
		mission[93].status = 2;
		mission[93].bt_xy[0] = (int)(hd*207);
		mission[93].bt_xy[1] = (int)(hd*543);
		mission[93].label_xy[0] = (int)(hd*0);
		mission[93].label_xy[1] = (int)(hd*0);
		mission[93].textup = false;

		// 
		mission[94].name = "True Lovers Flame";
		mission[94].description = "Description";
		mission[94].difficulty = 2;
		mission[94].reward = 120000;
		mission[94].mission_to_load = 80;
		mission[94].status = 1;
		mission[94].bt_xy[0] = (int)(hd*42);
		mission[94].bt_xy[1] = (int)(hd*96);
		mission[94].label_xy[0] = (int)(hd*0);
		mission[94].label_xy[1] = (int)(hd*0);
		mission[94].music = 1;
		mission[94].background = 9;
		mission[94].power = 20000;
		mission[94].npc_group = 9;

		mission[95].name = "Silver Sliver Galaxy";
		mission[95].description = "Description";
		mission[95].difficulty = 1;
		mission[95].reward = 160000;
		mission[95].mission_to_load = 81;
		mission[95].status = 1;
		mission[95].bt_xy[0] = (int)(hd*69);
		mission[95].bt_xy[1] = (int)(hd*277);
		mission[95].label_xy[0] = (int)(hd*0);
		mission[95].label_xy[1] = (int)(hd*0);
		mission[95].music = 3;
		mission[95].background = 8;
		mission[95].power = 20000;
		mission[95].npc_group = 9;

		mission[96].name = "Ceres Planet";
		mission[96].description = "Description";
		mission[96].difficulty = 0;
		mission[96].reward = 160000;
		mission[96].mission_to_load = 82;
		mission[96].status = 1;
		mission[96].bt_xy[0] = (int)(hd*258);
		mission[96].bt_xy[1] = (int)(hd*311);
		mission[96].label_xy[0] = (int)(hd*0);
		mission[96].label_xy[1] = (int)(hd*0);
		mission[96].music = 2;
		mission[96].background = 4;
		mission[96].power = 20000;
		mission[96].npc_group = 9;

		mission[97].name = "Tarantula Cloud";
		mission[97].description = "Description";
		mission[97].difficulty = 1;
		mission[97].reward = 150000;
		mission[97].mission_to_load = 83;
		mission[97].status = 1;
		mission[97].bt_xy[0] = (int)(hd*4);
		mission[97].bt_xy[1] = (int)(hd*450);
		mission[97].label_xy[0] = (int)(hd*0);
		mission[97].label_xy[1] = (int)(hd*0);
		mission[97].music = 4;
		mission[97].background = 2;
		mission[97].power = 20000;
		mission[97].npc_group = 9;

		mission[98].name = "Sextans Dwarf";
		mission[98].description = "Description";
		mission[98].difficulty = 2;
		mission[98].reward = 170000;
		mission[98].mission_to_load = 84;
		mission[98].status = 1;
		mission[98].bt_xy[0] = (int)(hd*123);
		mission[98].bt_xy[1] = (int)(hd*475);
		mission[98].label_xy[0] = (int)(hd*0);
		mission[98].label_xy[1] = (int)(hd*0);
		mission[98].music = 1;
		mission[98].background = 5;
		mission[98].power = 20000;
		mission[98].npc_group = 9;

		mission[99].name = "Zentors Belt";
		mission[99].description = "Description";
		mission[99].difficulty = 1;
		mission[99].reward = 130000;
		mission[99].mission_to_load = 85;
		mission[99].status = 1;
		mission[99].bt_xy[0] = (int)(hd*346);
		mission[99].bt_xy[1] = (int)(hd*445);
		mission[99].label_xy[0] = (int)(hd*0);
		mission[99].label_xy[1] = (int)(hd*0);
		mission[99].music = 3;
		mission[99].background = 1;
		mission[99].power = 20000;
		mission[99].npc_group = 9;

		mission[100].name = "Space Station";
		mission[100].description = "Description";
		mission[100].status = 2;
		mission[100].bt_xy[0] = (int)(hd*331);
		mission[100].bt_xy[1] = (int)(hd*209);
		mission[100].label_xy[0] = (int)(hd*0);
		mission[100].label_xy[1] = (int)(hd*0);
		mission[100].textup = true;
		
		//
		mission[101].name = "Kuiper Meteorite";
		mission[101].description = "Description";
		mission[101].difficulty = 0;
		mission[101].reward = 150000;
		mission[101].mission_to_load = 86;
		mission[101].status = 1;
		mission[101].bt_xy[0] = (int)(hd*71);
		mission[101].bt_xy[1] = (int)(hd*116);
		mission[101].label_xy[0] = (int)(hd*0);
		mission[101].label_xy[1] = (int)(hd*0);
		mission[101].music = 1;
		mission[101].background = 0;
		mission[101].power = 22000;
		mission[101].npc_group = 9;

		mission[102].name = "Siamese Twins";
		mission[102].description = "Description";
		mission[102].difficulty = 3;
		mission[102].reward = 240000;
		mission[102].mission_to_load = 87;
		mission[102].status = 1;
		mission[102].bt_xy[0] = (int)(hd*90);
		mission[102].bt_xy[1] = (int)(hd*279);
		mission[102].label_xy[0] = (int)(hd*0);
		mission[102].label_xy[1] = (int)(hd*0);
		mission[102].music = 4;
		mission[102].background = 3;
		mission[102].power = 22000;
		mission[102].npc_group = 9;

		mission[103].name = "Monster Planet";
		mission[103].description = "Description";
		mission[103].difficulty = 2;
		mission[103].reward = 190000;
		mission[103].mission_to_load = 88;
		mission[103].status = 1;
		mission[103].bt_xy[0] = (int)(hd*296);
		mission[103].bt_xy[1] = (int)(hd*235);
		mission[103].label_xy[0] = (int)(hd*0);
		mission[103].label_xy[1] = (int)(hd*0);
		mission[103].music = 1;
		mission[104].background = 6;
		mission[104].power = 22000;
		mission[104].npc_group = 9;

		mission[104].name = "Black Eye Vortex";
		mission[104].description = "Description";
		mission[104].difficulty = 2;
		mission[104].reward = 150000;
		mission[104].mission_to_load = 89;
		mission[104].status = 1;
		mission[104].bt_xy[0] = (int)(hd*13);
		mission[104].bt_xy[1] = (int)(hd*443);
		mission[104].label_xy[0] = (int)(hd*0);
		mission[104].label_xy[1] = (int)(hd*0);
		mission[104].music = 3;
		mission[104].background = 5;
		mission[104].power = 22000;
		mission[104].npc_group = 9;

		mission[105].name = "Space Station";
		mission[105].description = "Description";
		mission[105].status = 2;
		mission[105].bt_xy[0] = (int)(hd*169);
		mission[105].bt_xy[1] = (int)(hd*447);
		mission[105].label_xy[0] = (int)(hd*0);
		mission[105].label_xy[1] = (int)(hd*0);
		mission[105].textup = false;

		//
		mission[106].name = "Anonymous Comet";
		mission[106].description = "Description";
		mission[106].difficulty = 1;
		mission[106].reward = 140000;
		mission[106].mission_to_load = 90;
		mission[106].status = 1;
		mission[106].bt_xy[0] = (int)(hd*71);
		mission[106].bt_xy[1] = (int)(hd*42);
		mission[106].label_xy[0] = (int)(hd*0);
		mission[106].label_xy[1] = (int)(hd*0);
		mission[106].music = 2;
		mission[106].background = 0;
		mission[106].power = 22000;
		mission[106].npc_group = 9;

		mission[107].name = "Ant Galaxy";
		mission[107].description = "Description";
		mission[107].difficulty = 1;
		mission[107].reward = 140000;
		mission[107].mission_to_load = 90;
		mission[107].status = 1;
		mission[107].bt_xy[0] = (int)(hd*267);
		mission[107].bt_xy[1] = (int)(hd*24);
		mission[107].label_xy[0] = (int)(hd*0);
		mission[107].label_xy[1] = (int)(hd*0);
		mission[107].music = 3;
		mission[107].background = 2;
		mission[107].power = 22000;
		mission[107].npc_group = 9;

		mission[108].name = "Beehive Cluster";
		mission[108].description = "Description";
		mission[108].difficulty = 1;
		mission[108].reward = 140000;
		mission[108].mission_to_load = 90;
		mission[108].status = 1;
		mission[108].bt_xy[0] = (int)(hd*29);
		mission[108].bt_xy[1] = (int)(hd*185);
		mission[108].label_xy[0] = (int)(hd*0);
		mission[108].label_xy[1] = (int)(hd*0);
		mission[108].music = 1;
		mission[108].background = 1;
		mission[108].power = 22000;
		mission[108].npc_group = 9;

		mission[109].name = "Blinking Planet";
		mission[109].description = "Description";
		mission[109].difficulty = 1;
		mission[109].reward = 140000;
		mission[109].mission_to_load = 90;
		mission[109].status = 1;
		mission[109].bt_xy[0] = (int)(hd*234);
		mission[109].bt_xy[1] = (int)(hd*151);
		mission[109].label_xy[0] = (int)(hd*0);
		mission[109].label_xy[1] = (int)(hd*0);
		mission[109].music = 3;
		mission[109].background = 7;
		mission[109].power = 22000;
		mission[109].npc_group = 9;

		mission[110].name = "Vessen IX";
		mission[110].description = "Description";
		mission[110].difficulty = 1;
		mission[110].reward = 140000;
		mission[110].mission_to_load = 90;
		mission[110].status = 1;
		mission[110].bt_xy[0] = (int)(hd*57);
		mission[110].bt_xy[1] = (int)(hd*342);
		mission[110].label_xy[0] = (int)(hd*0);
		mission[110].label_xy[1] = (int)(hd*0);
		mission[110].music = 1;
		mission[110].background = 2;
		mission[110].power = 22000;
		mission[110].npc_group = 9;

		mission[111].name = "BABY BOOM";
		mission[111].description = "Description";
		mission[111].difficulty = 1;
		mission[111].reward = 140000;
		mission[111].mission_to_load = 90;
		mission[111].status = 1;
		mission[111].bt_xy[0] = (int)(hd*194);
		mission[111].bt_xy[1] = (int)(hd*489);
		mission[111].label_xy[0] = (int)(hd*0);
		mission[111].label_xy[1] = (int)(hd*0);
		mission[111].music = 2;
		mission[111].background = 9;
		mission[111].power = 30000;
		mission[111].npc_group = 9;

		mission[112].name = "Apollo Planet";
		mission[112].description = "Description";
		mission[112].difficulty = 1;
		mission[112].reward = 140000;
		mission[112].mission_to_load = 90;
		mission[112].status = 1;
		mission[112].bt_xy[0] = (int)(hd*216);
		mission[112].bt_xy[1] = (int)(hd*331);
		mission[112].label_xy[0] = (int)(hd*0);
		mission[112].label_xy[1] = (int)(hd*0);
		mission[112].music = 2;
		mission[112].background = 4;
		mission[112].power = 22000;
		mission[112].npc_group = 9;

		mission[113].name = "Alpha Persei Cluster";
		mission[113].description = "Description";
		mission[113].difficulty = 1;
		mission[113].reward = 140000;
		mission[113].mission_to_load = 90;
		mission[113].status = 1;
		mission[113].bt_xy[0] = (int)(hd*375);
		mission[113].bt_xy[1] = (int)(hd*408);
		mission[113].label_xy[0] = (int)(hd*0);
		mission[113].label_xy[1] = (int)(hd*0);
		mission[113].music = 1;
		mission[113].background = 3;
		mission[113].power = 22000;
		mission[113].npc_group = 9;

		mission[114].name = "Space Station";
		mission[114].description = "Description";
		mission[114].status = 2;
		mission[114].bt_xy[0] = (int)(hd*306);
		mission[114].bt_xy[1] = (int)(hd*283);
		mission[114].label_xy[0] = (int)(hd*0);
		mission[114].label_xy[1] = (int)(hd*0);
		mission[114].textup = true;
		
		black.load(gl, context, R.drawable.black128);
		white.load(gl, context, R.drawable.white128);
		dialogue_bg.load(gl, context, R.drawable.missiontextbox);
		verify_border.load(gl, context, R.drawable.menu_border_400x160);
		quit_yes.load(gl, context, R.drawable.button_quit);
		quit_no.load(gl, context, R.drawable.button_resume);

		fblike.load(gl, context, R.drawable.fblike);
		fbshare.load(gl, context, R.drawable.fbshare);
		btrate.load(gl, context, R.drawable.btrate);
		btcontinue.load(gl, context, R.drawable.btcontinue);
		popupbg.load(gl, context, R.drawable.popupbg);
		
		quitmission_text.load(gl, context, R.drawable.missionquit_text);

		
		for (int i=0; i<5; i++) {
			layer[i] = new My2DImage((int)(hd*80), (int)(hd*80), true);
			layer[i].load(gl, context, layer_src[i]);
		}
		
		for (int i=0; i<14; i++) {
			cd[i] = new My2DImage(cd_size[0], cd_size[1], true);
			cd[i].load(gl, context, cd_src[i]);
		}
		
	}
	
	public void reset() {
		for (int i=0;i<total_galaxy;i++) {
			galaxy[i].status = 0;
		}
		galaxy[0].status = 2;
		galaxy[1].status = 1;
		galaxy[2].status = 1;
		
		for (int i=0;i<total_mission;i++) {
			mission[i].completed = 0;
			mission[i].highscore = 0;
		}
		Update_Dialogues(-1,0);
		Update_Dialogues(-1,1);
		Update_Dialogues(-1,2);
	}
	
	public void load_upgradeimages(GL10 gl, Context context, int shipnum) {
		upgrade_bg.load(gl, context, upgrade_bg_src[shipnum]);
	}

	public void release_upgradeimages(GL10 gl) {
		upgrade_bg.release(gl);
	}
	public void loadimages(GL10 gl, Context context, boolean isGoogle) {

		Log.w("Space Shooter", "Menusystem LOAD called.");

		for (int i=0; i<mainmenu.button_num; i++) {
			if (i == 8 && !isGoogle) mainmenu.buttonimages[i].load(gl, context, mainmenu.buttonimages_src[10]);
			else mainmenu.buttonimages[i].load(gl, context, mainmenu.buttonimages_src[i]);
		}
		mainmenu.bgimage.load(gl, context, mainmenu.bgimage_src);
		mainmenu.campaignselector.load(gl, context, R.drawable.campaign_selector);
		//mainmenu_lock.load(gl, context, R.drawable.lock);
		
		galaxy_marker.load(gl, context, R.drawable.galaxy_marker_80px);
		galaxy_enter.load(gl, context, R.drawable.button_entergalaxy);
		galaxy_unlock.load(gl, context, R.drawable.button_unlockgalaxy);
		//white.load(gl, context, R.drawable.white128);
		
		mission_marker_active.load(gl, context, R.drawable.activemissions);
		mission_marker_finished.load(gl, context, R.drawable.finishedmission);
		mission_marker_station.load(gl, context, R.drawable.activespacestation);
		mission_enter.load(gl, context, R.drawable.button_launch);
		mission_cancel.load(gl, context, R.drawable.button_cancel);
		
		mission_bt_easy.load(gl, context, R.drawable.button_easy);
		mission_bt_medium.load(gl, context, R.drawable.button_medium);
		mission_bt_hard.load(gl, context, R.drawable.button_hard);

//		upgrade_bg.load(gl, context, R.drawable.ship_upgrade_1);
		//upgrade_icon_ship.load(gl, context, R.drawable.upgrade_bt_ship);
		//upgrade_icon_weapon.load(gl, context, R.drawable.upgrade_bt_weapon);
		//upgrade_icon_modifier.load(gl, context, R.drawable.upgrade_bt_modifier);
		//upgrade_icon_special.load(gl, context, R.drawable.upgrade_bt_special);
		//upgrade_icon_upgrade.load(gl, context, R.drawable.upgrade_bt_upgrade);
		upgrade_downarrow.load(gl, context, R.drawable.upgrade_downarrow);
		upgrade_uparrow.load(gl, context, R.drawable.upgrade_uparrow);

		wormholeA[0].load(gl, context, R.drawable.wormhole_a_01);
		wormholeA[1].load(gl, context, R.drawable.wormhole_a_02);
		wormholeA[2].load(gl, context, R.drawable.wormhole_a_03);
		wormholeA[3].load(gl, context, R.drawable.wormhole_a_04);
		wormholeA[4].load(gl, context, R.drawable.wormhole_a_05);
		wormholeA[5].load(gl, context, R.drawable.wormhole_a_06);
		wormholeA[6].load(gl, context, R.drawable.wormhole_a_07);
		wormholeA[7].load(gl, context, R.drawable.wormhole_a_08);
		wormholeA[8].load(gl, context, R.drawable.wormhole_a_09);
		wormholeA[9].load(gl, context, R.drawable.wormhole_a_10);
		wormholeA[10].load(gl, context, R.drawable.wormhole_a_11);
		wormholeA[11].load(gl, context, R.drawable.wormhole_a_12);
		wormholeA[12].load(gl, context, R.drawable.wormhole_a_13);
		wormholeA[13].load(gl, context, R.drawable.wormhole_a_14);
		wormholeA[14].load(gl, context, R.drawable.wormhole_a_15);
		wormholeA[15].load(gl, context, R.drawable.wormhole_a_16);

		shiningstar[0].load(gl, context, R.drawable.shining_star_01);
		shiningstar[1].load(gl, context, R.drawable.shining_star_02);
		shiningstar[2].load(gl, context, R.drawable.shining_star_03);
		shiningstar[3].load(gl, context, R.drawable.shining_star_04);
		shiningstar[4].load(gl, context, R.drawable.shining_star_05);
		shiningstar[5].load(gl, context, R.drawable.shining_star_06);
		shiningstar[6].load(gl, context, R.drawable.shining_star_07);
		shiningstar[7].load(gl, context, R.drawable.shining_star_08);
		shiningstar[8].load(gl, context, R.drawable.shining_star_09);
		shiningstar[9].load(gl, context, R.drawable.shining_star_10);
		
		starline[0].load(gl, context, R.drawable.starline_1);
		starline[1].load(gl, context, R.drawable.starline_2);
		starline[2].load(gl, context, R.drawable.starline_3);
		starline[3].load(gl, context, R.drawable.starline_4);
		starline[4].load(gl, context, R.drawable.starline_5);
		starline[5].load(gl, context, R.drawable.starline_6);
		starline[6].load(gl, context, R.drawable.starline_7);
		starline[7].load(gl, context, R.drawable.starline_8);
		
		parrow[0].load(gl, context, R.drawable.pointing_arrow_01);
		parrow[1].load(gl, context, R.drawable.pointing_arrow_02);
		parrow[2].load(gl, context, R.drawable.pointing_arrow_03);
		parrow[3].load(gl, context, R.drawable.pointing_arrow_04);
		parrow[4].load(gl, context, R.drawable.pointing_arrow_05);
		parrow[5].load(gl, context, R.drawable.pointing_arrow_06);
		
		starline_base.load(gl, context, R.drawable.starline_0);
		
		//back.load(gl, context, R.drawable.button_back_50x);
		//back1.load(gl, context, R.drawable.button_back_80x);
		
		//profile_select.load(gl, context, R.drawable.button_selectprofile);
		profile_reset.load(gl, context, R.drawable.button_resetprofile2);
		profile_reset2.load(gl, context, R.drawable.button_resetprofile);
		//profile_confirm.load(gl, context, R.drawable.button_confirm);

		options_soundon.load(gl, context, R.drawable.button_soundon);
		options_soundoff.load(gl, context, R.drawable.button_soundoff);
		options_musicon.load(gl, context, R.drawable.button_musicon);
		options_musicoff.load(gl, context, R.drawable.button_musicoff);
		options_vibrationon.load(gl, context, R.drawable.button_vibrationon);
		options_vibrationoff.load(gl, context, R.drawable.button_vibrationoff);
		options_keepscreenon.load(gl, context, R.drawable.button_keepscreenon);
		options_keepscreenoff.load(gl, context, R.drawable.button_keepscreenoff);
		//unlock_entercode.load(gl, context, R.drawable.button_entercode);
		unlock_fullgame.load(gl, context, R.drawable.button_fullversion);
		//unlock_buynow.load(gl, context, R.drawable.button_buynow);
		//unlock_market.load(gl, context, R.drawable.button_market);
		/*
		for (int i=0;i<12;i++) {
			unlock_keys[i] = new My2DImage(80,80,true);
		}
		unlock_keys[0].load(gl, context, R.drawable.button_1);
		unlock_keys[1].load(gl, context, R.drawable.button_2);
		unlock_keys[2].load(gl, context, R.drawable.button_3);
		unlock_keys[3].load(gl, context, R.drawable.button_4);
		unlock_keys[4].load(gl, context, R.drawable.button_5);
		unlock_keys[5].load(gl, context, R.drawable.button_6);
		unlock_keys[6].load(gl, context, R.drawable.button_7);
		unlock_keys[7].load(gl, context, R.drawable.button_8);
		unlock_keys[8].load(gl, context, R.drawable.button_9);
		unlock_keys[9].load(gl, context, R.drawable.button_0);
		unlock_keys[10].load(gl, context, R.drawable.button_clr);
		unlock_keys[11].load(gl, context, R.drawable.button_bk);
		 */
		
		galaxy_border.load(gl, context, R.drawable.menu_border_320x80);
		mission_border.load(gl, context, R.drawable.menu_border_320x480);
		//upgrade_border.load(gl, context, R.drawable.menu_border_upgrade);
		//upgrade_border2.load(gl, context, R.drawable.menu_border_upgrade2);
		options_border.load(gl, context, R.drawable.menu_border_400x620);
		profile_border.load(gl, context, R.drawable.menu_border_400x200);
		
		completed_star.load(gl, context, R.drawable.star45);
		daily_border.load(gl, context, R.drawable.daily_frame);
		getmore.load(gl, context, R.drawable.button_getmore);
		
		quit_text.load(gl, context, R.drawable.quit_text);
		reset_text.load(gl, context, R.drawable.reset_text);
		dailyok.load(gl, context, R.drawable.dailyok);
		
		btachievements.load(gl, context, R.drawable.rewards);
		btleaderboard.load(gl, context, R.drawable.ladder);
		btsignin.load(gl, context, R.drawable.googleplus);

	}
	
	
	public void releaseimages(GL10 gl) {
		
		Log.w("Space Shooter", "Menusystem release called.");

		for (int i=0; i<mainmenu.button_num; i++) {
			mainmenu.buttonimages[i].release(gl);
		}
		for (int i=0; i<10; i++) {
			shiningstar[i].release(gl);
		}
		for (int i=0; i<8; i++) {
			starline[i].release(gl);
		}
		starline_base.release(gl);
		for (int i=0; i<6; i++) {
			parrow[i].release(gl);
		}
		
		mainmenu.bgimage.release(gl);
		mainmenu.campaignselector.release(gl);
		galaxy_marker.release(gl);
		galaxy_enter.release(gl);
		galaxy_unlock.release(gl);
		//black.release(gl);
		//white.release(gl);
		
		mission_enter.release(gl);
		mission_cancel.release(gl);
		mission_marker_active.release(gl);
		mission_marker_finished.release(gl);
		mission_marker_station.release(gl);

		mission_bt_easy.release(gl);
		mission_bt_medium.release(gl);
		mission_bt_hard.release(gl);

		upgrade_bg.release(gl);
		//upgrade_bg[1].release(gl);
		//upgrade_bg[2].release(gl);
		//upgrade_icon_ship.release(gl);
		//upgrade_icon_weapon.release(gl);
		//upgrade_icon_modifier.release(gl);
		//upgrade_icon_special.release(gl);
		//upgrade_icon_upgrade.release(gl);
		upgrade_uparrow.release(gl);
		upgrade_downarrow.release(gl);

		for (int i=0; i<16; i++) {
			wormholeA[i].release(gl);
		}
		
		//back.release(gl);
		//back1.release(gl);
		profile_reset.release(gl);
		profile_reset2.release(gl);
		//profile_select.release(gl);
		//dialogue_bg.release(gl);
		profile_confirm.release(gl);
		
		options_soundon.release(gl);
		options_soundoff.release(gl);
		options_musicon.release(gl);
		options_musicoff.release(gl);
		options_vibrationon.release(gl);
		options_vibrationoff.release(gl);
		options_keepscreenon.release(gl);
		options_keepscreenoff.release(gl);
		//unlock_entercode.release(gl);
		unlock_fullgame.release(gl);
		//unlock_buynow.release(gl);
		//unlock_market.release(gl);
		//for (int i=0;i<12;i++) {
		//	unlock_keys[i].release(gl);
		//}

		galaxy_border.release(gl);
		mission_border.release(gl);
		//upgrade_border.release(gl);
		//upgrade_border2.release(gl);
		options_border.release(gl);
		profile_border.release(gl);
		//verify_border.release(gl);
		//mainmenu_lock.release(gl);
		
		completed_star.release(gl);
		daily_border.release(gl);
		getmore.release(gl);
		
		quit_text.release(gl);
		reset_text.release(gl);
		dailyok.release(gl);
		
		btachievements.release(gl);
		btleaderboard.release(gl);
		btsignin.release(gl);


	}
} 
