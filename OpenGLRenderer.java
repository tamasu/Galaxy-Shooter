package com.tubigames.galaxy.shooter.hd;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.FloatMath;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaPlayer;

// amazon stuff
import com.amazon.inapp.purchasing.PurchasingManager;
//tapjoy stuff
import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyNotifier;
import com.tapjoy.TapjoySpendPointsNotifier;
// facebook stuff
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener; 


import com.google.android.gms.games.Games;
import com.google.android.gms.common.api.*;
import com.google.example.games.basegameutils.GameHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import com.tubigames.galaxy.shooter.hd.util.IabHelper;
import com.tubigames.galaxy.shooter.hd.util.IabResult;
import com.tubigames.galaxy.shooter.hd.util.Inventory;
import com.tubigames.galaxy.shooter.hd.util.Purchase;
//GOOGLE IN-APP BILLING 3
//GOOGLE IN-APP BILLING 3
//GOOGLE IN-APP BILLING 3
//===================================================================================


public class OpenGLRenderer extends GLSurfaceView implements Renderer, TapjoyNotifier, TapjoySpendPointsNotifier, GameHelper.GameHelperListener {

	private final boolean cinemamode = false;	// false | true : DISABLE HUD, +INVULNERABILITY
	// TODO false
	private final boolean developer_mode = false;
	public final static boolean isGoogle = true;		// true = Google Play, false = Amazon
	public boolean tapjoy_actions[] = { true, true, true };
	
	static final float hd = 1.5f;
	
	private boolean fullgame = false;
	private boolean database = false;
	private boolean rate_firsttime = true;
	private float adtimer = 0;
	private boolean showad = false;
	private int adrandomtime = 0;
	private int difficulty = 1;
	float dif_easy = 0.6667f;
	float dif_hard = 3f;
	//private boolean firstrun = true;
	//private My2DImage loading_bg = new My2DImage(480,800,false);
	//private long survival_LY = 0;
	//private long profile_LY = 0;
	boolean dialogue = false;
	private Calendar today;
	private long lastrun = 0;
	boolean dialoghelper = false;
	int dailyreward = 0;
	boolean purchase_in_progress = false; 
	
	boolean media_update = true;
	//int media_num = 0;
	
	final private int TICKS_PER_SECOND = 30; // GAME LOGIC SPEED
	final private float tmod = 60 / TICKS_PER_SECOND; // GAME LOGIC SPEED MODIFIER

    // game logic run parameters
	final private int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	final private int MAX_FRAMESKIP = 5;
	private long next_game_tick = 0;
	private int loops = 0;
	final private float game_timer_fix = 1.0f; // timer correction for slow machines 

	boolean pause = false;
    boolean quit = false; // main menu quit comfirmation
    boolean buynow = false; // upgrade menu purchase comfirmation
    String buynow_item = "";
    
    // displaying game logic per second
    int gameloops = 0;
	long looptime = 0;
	long looptime_dif = 0;
	float time_dif = 0;
	
	boolean network = true;
    
	final private boolean vertical_move = true; // lehessen e fuggolegesen mozgatni az urhajot
	final private int vertical_move_max = 0; // felulrol a legkisebb ertek ahova meg mozgathato

	final float ly_szorzo = 0.75f;
	float drop_szorzo = 4.0f;
	
	// displaying drawing fps
	long time_current = 0;
	long time_current_real = 0; 
	long time_previous = 0;
	int fps = 0;
	int fps_sum = 0;
	int fps_framecount = 0;
	int v1 = 0; // fps text 1 ...
	int v2 = 0;
	int v3 = 0;
	
	// display text class
    TexFont galaxyfnt;
    TexFont menufnt;
    
	// game parameters
	private static int menu = 1;
	private static boolean sound = false; /* sound, music */
	private static boolean music = false; /* sound, music */
	//private static boolean animation = false; /* sound, music */
	private static boolean vibration = false; /* sound, music */
	private static boolean keepscreen = false; /* sound, music */
	//private static int res = 0; /* 0 = high, 1 = low */
	//private boolean res_changed = false;
	long logo_timer = 0;
	boolean logo_start = true;
	boolean logo_reset = false;
	boolean firstrun = true;

	// save game variable
	SharedPreferences[] pref = new SharedPreferences[4];
	int current_profile = 1;
	int[] profile_ship = { 0, 0, 0 };
	long[] profile_ly = { 10000, 10000, 10000 };
	int[] profile_galaxies = { 0, 0, 0 };
	int[] profile_missions = { 0, 0, 0 };
	long[] profile_highscore = { 0, 0, 0 };
	int profile_reset = 0;
	String promocode = "";
	String promostring = "";
	Vibrator vibrator;
	final int promonum = 3;	
	boolean[] profile_promos = { 
			false,	// 0 = Adds 20000 Extra Credit 
			false,	// 1 = Adds 1 Crystal of Destruction
			false,	// 2 = Adds 1 Crystal of Power
	};
	final String[] promocodes = { 
			"", // 0 
			"", // 1 
			"", // 2 
	};
	
	
	final private int[] vibration_pattern = { 
			 250,	// 0 
			 500, 	// 1
			 750, 	// 2
			1000, 	// 3
			1250, 	// 4
	};

	final private int dimensionX = (int)(hd*480); 
	final private int dimensionY = (int)(hd*800);
	private float scaleX;
	private float scaleY;
    float scrScaleX = 1.0f;
    float scrScaleY = 1.0f;
	private float bgloopY = 0;
	private float bgextraloopY = 0;
	private float cloudloopY = 0;
	boolean loading = false;
	int[] sharefeed = { 0, 0, 0, 0, 0 };
	boolean liked = false;
	int[] shield_size = { (int)(140*hd), (int)(140*hd) };
	
	int like_tap = 0;
	int like_pos[][] = {
			{ (int)(hd*100), (int)(hd*160), (int)(hd*260), (int)(hd*350) },	
			{ (int)(hd*0), (int)(hd*230), (int)(hd*130), (int)(hd*430) },	
			{ (int)(hd*0), (int)(hd*400), (int)(hd*200), (int)(hd*640) },	
	};
	
	/** My2DImage instance */
	//private My2DImage mainmenu_bg = new My2DImage(480,800,false);	
	//private My2DImage campaignselector_bg = new My2DImage(480,800,false);
	//private My2DImage galaxy01_bg = new My2DImage(480,800,false);
	private MyBGScroller background = new MyBGScroller((int)(hd*480),(int)(hd*800),false);	
	private My2DImage background_clouds = new My2DImage((int)(hd*480),(int)(hd*800),true);	
	private MyBGScroller background_extra = new MyBGScroller((int)(hd*480),(int)(hd*800),false);
	private boolean bg_extra = false;
	//private My2DImage background_lowres;
	float background_lowres_scale = 1.0f;
	boolean clouds = false;

	private My2DImage hud_bar;
	private My2DImage structure_bar;
	private My2DImage shield_bar;
	//private My2DImage skill_bar;
	private My2DImage skill_icon;	
	private My2DImage skill_buyicon;	

	private My2DImage npc_hpbar;
	private My2DImage hpbar_bg;
	//private My2DImage npc_shieldbar;
	private My2DImage npc_shadow;

	private ParticleSystem[] particle = new ParticleSystem[3];
	int[] particle_size = { (int)(hd*32), (int)(hd*32) }; 
	
	private My2DImage shield;

	private float endmission_timer = 0;
	private int endmission_state = 0;
	private boolean endmission_success = false;
	private My2DImage endmission_completed;
	private My2DImage endmission_failed;
	private My2DImage endmission_warning;
	private float warning_timer = 0;
	private boolean warning = false;
	
	//final private int[] bottommenu_button1 = { (int)(hd*0), (int)(hd*720), (int)(hd*80), (int)(hd*800) } ;
	//final private int[] bottommenu_button2 = { (int)(hd*80), (int)(hd*720), (int)(hd*160), (int)(hd*800) } ;
	final private int[] bottommenu_button3 = { (int)(hd*0), (int)(hd*720), (int)(hd*80), (int)(hd*800) } ;
	final private int[] bottommenu_button4 = { (int)(hd*80), (int)(hd*720), (int)(hd*160), (int)(hd*800) } ;
	final private int[] bottommenu_button5 = { (int)(hd*320), (int)(hd*720), (int)(hd*400), (int)(hd*800) } ;
	final private int[] bottommenu_button6 = { (int)(hd*400), (int)(hd*720), (int)(hd*480), (int)(hd*800) } ;
	
	private boolean[] bmenu_button_down = { false, false, false, false, false, false } ;
	private boolean bmenu_button_down_any = false ;
	
	Menusystem menusystem = new Menusystem();
	
	final private int[] media_list = { 
			R.raw.mainmenu,			// 0
			R.raw.battletheme1,		// 1
			R.raw.battletheme2,		// 2
			R.raw.battletheme3,		// 3
			R.raw.battletheme4,		// 4
	};
	MediaPlayer mediaplayer;
	float musicvolume = 0.6f;

	private float oldX;
	private float oldY;

	private class Bullet {
		public boolean active = false;
		public int type = 0; // bullet type
		public float rotation = 0.0f;
		public float x = 0;
		public float y = 0;
		public float xmove = 0;
		public float ymove = 0;
		public int damage = 0;
		public boolean unstoppable = false;
		public int damage_type = 0;
		public boolean isparticle = false;
		public ParticleSystem particle;
	}
	
	private class Pickup {
		public boolean active = false;
		public int type = -1; 
		public float x = 0;
		public float y = 0;
		public long timer = 0;
		public long floatingtext = -1;
		public int explosion_loop = 0;
		public int loop = 0;
	}
	
	ArrayList<Entity> npc = new ArrayList<Entity>();
	ArrayList<Bullet> spaceship_bullet = new ArrayList<Bullet>();
	ArrayList<Bullet> npc_bullet = new ArrayList<Bullet>();
	ArrayList<Pickup> pickup = new ArrayList<Pickup>();

	ArrayList<Integer> remove_list_npc = new ArrayList<Integer>();
	ArrayList<Integer> remove_list_spaceship_bullet = new ArrayList<Integer>();
	ArrayList<Integer> remove_list_npc_bullet = new ArrayList<Integer>();
	ArrayList<Integer> remove_list_pickup = new ArrayList<Integer>();

	private boolean spaceship_bullet_launch = false;
	private boolean spaceship_bullet_launcher = false;
	private long spaceship_bullet_timer = 0;
	//private boolean launch_delayer_active = true;
	//private boolean launch_delayer_isrunning = false;
	private int spaceship_damage_value = 0;
	private int spaceship_hulldmg_value = 0;
	private int spaceship_shielddmg_value = 0;

	private int spaceship_special_launch = -1;
	private int pickup_special_launch = -1;

	private class SpaceShip {

		public int type = 0;
		
		public int explosion_loop = 0;
		public boolean isalive = true;
		
		public int turret_num = 2;
		public int[] turret = { 0, -1, -1, -1 };
		public int turret_active = 0;
		
		public int modifier_num = 2;
		public int[] modifier = { 0, -1, -1 };
		public int modifier_active = 0;
		
		public int special_num = 3;
		public int[] special = { 7, 0, 4, -1 };
		public int[] special_order = { 0, 1, 2, 3 };
		public boolean[] special_cd = { false, false, false, false };
		public long[] special_cd_timer = { 0, 0, 0, 0 };
		
		public int upgrade_num = 5;
		public int[] upgrade = { -1, -1, -1, -1, -1, -1, -1, -1 };
		
		public float x = (int)(hd*200);
		public float y = (int)(hd*620); // touchmove-nal a vertikalis minimumkent is definialva van!!
		
		public int hp = 10;
		public int hp_max = 10;
		public int shield = 10;
		public int shield_max = 10;
		public float shield_recharge = 4;
		public long shield_recharge_timer = 0;
		public float repair = 0;
		public long repair_timer = 0;
		public float speed = 12;

		public float rofmod = 0.0f;
		public float damagemod = 0.0f;
		public float lymod = 1.0f;
		public float cdmod = 1.0f;
		public float collisionmod = 1.0f;
		public float bulletspeedmod = 0.0f;
		public float dropchancemod = 0.0f;
		public float lypickupmod = 1.0f;
		public int crit = 0;
		public boolean winged = false;
		
		public boolean freeze = false;
		public long freeze_timer = 0;
		public long hit_timer = 0;
	}
	
	SpaceShip spaceship = new SpaceShip();
	
	long LY = 0;
	int missionLY = 0;
	long vLY = 0;
	private int mission = 0;
	private int mission_script = 0;
	private boolean mission_script_wait = true;
	private boolean mission_script_delay = true;
	private long mission_script_timer = 0;
	private boolean mission_update = true;

	// Specials
	private boolean freeze = false;
	private long freeze_timer = 0;
	private float emp_loop = 0;
	private float ship_emp_loop = 0;
	private boolean invincible = false;
	private long invincible_timer = 0;
	private boolean autofire = false;
	private long autofire_timer = 0;
	private boolean homing = false;
	private Bullet homing_bullet = new Bullet();
	private boolean cone = false;
	private long cone_timer = 0;
	private int[] cone_xy = { 0, 0 };
	private boolean gravity = false;
	private long gravity_timer = 0;
	private float gravity_rotate = 0;
	private boolean booster = false;
	private long booster_timer = 0;
	private float booster_value = 1f;
	private boolean instantshield = false;
	private long instantshield_timer = 0;
	//private long instantshield_recharge_timer = 0;
	private boolean mining = false;
	private long mining_timer = 0;
	private boolean crazy = false;
	private Bullet crazy_bullet = new Bullet();
	private boolean crazy_update = false;
	private int crazy_update_num = 0;
	private int[][] crazy_pattern = {
		{ (int)(hd*30), (int)(hd*80), (int)(hd*150), (int)(hd*210), (int)(hd*300), (int)(hd*330), (int)(hd*390), (int)(hd*430) },
		{ (int)(hd*90), (int)(hd*170), (int)(hd*300), (int)(hd*370), (int)(hd*460), (int)(hd*550), (int)(hd*610), (int)(hd*690) },
	};
	private int crazy_x = 0;
	private int crazy_y = 0;
	private int nuke_timer = 0;
	private int hp_timer = 0;
	private boolean wings = false;
	private long wings_timer = 0;
	private long wings_starttime = 0;
	private boolean wing_bullet_launch = false;
	//private long wing_bullet_timer = 0;
	//private boolean wing_skipbullet = false;
	private int rocket = 0;
	private int rocket_type = 0;
	private long rocket_time = 0;

	private int extramodifier = -1;
	private int extraturret = -1;
	private int prevmodifier = -1;
	private int prevturret = -1;
	private long extramodifier_time = 0;
	private long extraturret_time = 0;
	//private boolean handofgod = false;

	private class Entity {
		public boolean isalive = false;
		public int type = 0;
		public int explosion_loop = 0;
		public int bg_loop = 0;
		public boolean drop = false;
		public float x = 0; // current xy coordinates
		public float y = 0;
		public float move_x = 0; // xy koordináta lépték - mennyit mozogjon az adott irányba egy tick alatt, átlós mozgatásnál
		public float move_y = 0;
		public boolean move_update = true; // move_xy csak akkor frissül, ha új script következik
		public int hp = 0;
		public int shield = 0;
		public long recharge_timer = 0;
		public int move_pattern = 0;
		public int current_script = 0;
		public long bullet_timer = 0;
		public boolean mining = false; // mining in progress
		public float rotate = 0.0f;
		public long hit_timer = 0;
		public long hit_timer_hp = 0;
		
		private boolean disruptor_enabled = false;
		private long disruptor_timer = 0;
		private int[] disruptor_xy = { 0, 0 };
		private float speedmod = 1.0f;
		
		//private int disort = 0;

	}
	
	Upgrades allUpgrades = new Upgrades();
	Patterns allPatterns = new Patterns();
	Entities allEntities = new Entities();
	Missions allMissions = new Missions();
	Explosions allExplosions = new Explosions();
	MenuAnimations menuAnimations = new MenuAnimations();
	Bullets allBullets = new Bullets();
	
	//final private float TOUCH_SCALE = 1.0f;
	private float touch_scroll_value = 0.0f;
	
	// DO texture update
	private boolean galaxybg_update = true;
	private boolean menusystem_update = false;
	private boolean entities_update = false;
	private boolean menuupgrade_update = true;
	
    float menufnt_scale = 1.0f;
    final private int menufnt_space = (int)(hd*23);
    float galaxyfnt_scale = 1.0f;
    float menufnt_alfa = 1.0f;
    float scrWidth; 
    float scrHeight;

    // Multitouch support
    private static final int INVALID_POINTER_ID = -1;
    // The ‘active pointer’ is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;
    
	// explosion animáció gyorsasága, FIGYELEM! tmod-al szorozva
    final private float explosion_loop_scale = 4.0f / tmod;

	String[] helper = { "","","","","","","","","","" };
	
	/* The Activity Context */
	public Context context;
	private Handler handler;

	// LEADBOLT
	boolean lb_adshown = true;
	int lb_ad = 0;

	// amazon stuff
    // Keys for our shared prefrences
    static final String FULL_VERSION = "amazonFullVersion";
    static final String EXCELSIOR = "amazonExcelsior";
    static final String BULLETS7 = "amazon7bullets";
    static final String WINGS = "amazonWings";
    static final String CRUISER = "amazonCruiser";
    static final String MODULATOR = "amazonModulator";
    static final String TIMEWARP = "amazonTimewarp";
    static final String WAVE = "amazonWave";
    static final String MINES = "amazonMines";
    static final String ALL = "amazonAll";
    static final String HAS_SUBSCRIPTION = "amazonSubscription";	// not used
    static final String NUM_LY = "amazonLY";
    static final String BULLETS9 = "amazon9bullets";
    static final String CHAOSGUN = "amazonChaosgun";
	
    final String fv = "Full_Version";
	final String ep = "Excelsior_Purchased";
	final String cp = "Cruiser_Purchased";
	final String b7 = "7bullets_Purchased";
	final String wp = "Wings_Purchased";
	final String mp = "Modulator_Purchased";
	final String tw = "Timewarp_Purchased";
	final String wv = "Wave_Purchased";
	final String ms = "Mines_Purchased";
	final String ap = "All_Purchased";
	final String b9 = "9bullets_Purchased";
	final String cg = "Chaosgun_Purchased";

	// GOOGLE IN-APP BILLING 3
	// GOOGLE IN-APP BILLING 3
	// GOOGLE IN-APP BILLING 3
	// ===================================================================================
    // The helper object
    IabHelper mHelper;
    // (arbitrary) request code for the purchase flow
    static final int RC_BILLING_REQUEST = 10001;
    static final String TAG_BILLING = "BILLING";
    
    static final String SKU_TEST = "android.test.purchased";
    static final String SKU_FULLVERSION = "sc2_fullversion";
	static final String SKU_LY5 = "sc2_ly5";
	static final String SKU_LY1K = "sc2_ly1k";
	static final String SKU_LY2K = "sc2_ly2k";
	static final String SKU_CRUISER = "sc2_cruiser";
	static final String SKU_EXCELSIOR = "sc2_excelsior";
	static final String SKU_7BULLETS = "sc2_7bullets";
	static final String SKU_WINGS = "sc2_wings";
	static final String SKU_MODULATOR = "sc2_modulator";
	static final String SKU_TIMEWARP = "sc2_timewarp";
	static final String SKU_WAVE = "sc2_wave";
    static final String SKU_MINES = "sc2_mines";
    static final String SKU_ALL = "sc2_all";
	static final String SKU_9BULLETS = "sc2_9bullets";
	static final String SKU_CHAOSGUN = "sc2_chaosgun";

    /*
      	Cruiser_Purchased
		Excelsior_Purchased
		7bullets_Purchased
		Wings_Purchased
		Modulator_Purchased
		Timewarp_Purchased
		Wave_Purchased
		Mines_Purchased
		Full_Version
     */
	// =================================================================================== 
	// GOOGLE GAME SERVICES
    public boolean ggs = true;
    public/*protected*/ GameHelper gameHelper;
    final static int RC_GAME_REQUEST = 9001;
    final static int RC_UNUSED = 9002;
    public static final int CLIENT_GAMES = GameHelper.CLIENT_GAMES;
    public static final int CLIENT_APPSTATE = GameHelper.CLIENT_APPSTATE;
    public static final int CLIENT_PLUS = GameHelper.CLIENT_PLUS;
    public static final int CLIENT_ALL = GameHelper.CLIENT_ALL;
    public boolean signedin = false;
    int login_process = 0;
    static final String ACHIEVEMENT_1MISSION = "CgkI3LHY2IkeEAIQEw";
    static final String ACHIEVEMENT_1GALAXY = "CgkI3LHY2IkeEAIQFA";
    static final String ACHIEVEMENT_100KILL = "CgkI3LHY2IkeEAIQFQ";
    static final String ACHIEVEMENT_10KKILL = "CgkI3LHY2IkeEAIQFg";
    static final String ACHIEVEMENT_100EXTRAKILL = "CgkI3LHY2IkeEAIQFw";
    static final String ACHIEVEMENT_100DEFEAT = "CgkI3LHY2IkeEAIQGA";
    static final String ACHIEVEMENT_1KPICKUP = "CgkI3LHY2IkeEAIQGQ";
    static final String ACHIEVEMENT_1MLY = "CgkI3LHY2IkeEAIQGg";
    static final String ACHIEVEMENT_10BOSS = "CgkI3LHY2IkeEAIQGw";
    static final String ACHIEVEMENT_ALLSPEC = "CgkI3LHY2IkeEAIQHA";
    static final String ACHIEVEMENT_5HARDMISSION = "CgkI3LHY2IkeEAIQHQ";
    static final String ACHIEVEMENT_75PGALAXY = "CgkI3LHY2IkeEAIQHg";
    static final String ACHIEVEMENT_500KSPENT = "CgkI3LHY2IkeEAIQHw";
    static final String ACHIEVEMENT_5LEVEL3 = "CgkI3LHY2IkeEAIQIA";
    static final String ACHIEVEMENT_NOESCAPE = "CgkI3LHY2IkeEAIQIQ";
    static final String ACHIEVEMENT_BABYBOOM = "CgkI3LHY2IkeEAIQIg";
    static final String LEADERBOARD_ID = "CgkI3LHY2IkeEAIQAg";
    
    float achievement_anim_timer = 0;
    boolean achievement_anim = false;
    boolean[] got_achievement = { false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false };
    static final String[] got_achievement_id = { "ACHIEVEMENT1","ACHIEVEMENT2","ACHIEVEMENT3","ACHIEVEMENT4","ACHIEVEMENT5","ACHIEVEMENT6","ACHIEVEMENT7","ACHIEVEMENT8",
    	"ACHIEVEMENT9","ACHIEVEMENT10","ACHIEVEMENT11","ACHIEVEMENT12","ACHIEVEMENT13","ACHIEVEMENT14","ACHIEVEMENT15","ACHIEVEMENT16"};
    
    // required variables:
    int total_kill = 0;
    int special_kill = 0;
    int total_defeat = 0;
    int total_pickup = 0;
    boolean[] spec_used = { false, false, false, false, false, false, false, false, false, false, false, false, false };
    String[] spec_used_id = { "SPEC_USED_1", "SPEC_USED_2", "SPEC_USED_3", "SPEC_USED_4", "SPEC_USED_5", "SPEC_USED_6", "SPEC_USED_7",
    		"SPEC_USED_8", "SPEC_USED_9", "SPEC_USED_10", "SPEC_USED_11", "SPEC_USED_12", "SPEC_USED_13" };
    int lyspent = 0;
    int npc_escaped = 0;
    
	
	// tapjoy
    int tap_points = 0;
    boolean tapjoy_resume = false;
    boolean tapjoy_earned = false;
	
    // currently logged in user
    private String currentUser;    
    // Mapping of our requestIds to unlockable content
    public Map<String, String> requestIds;
    public boolean amazon_initialized = false;

    @SuppressLint("NewApi") private static Point getDisplaySize(final Display display) {
        final Point point = new Point();
        try {
        	display.getSize(point);
			Log.i("Starship Commander", "display size method new");
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
			Log.i("Starship Commander", "display size method old");
        }
        return point;
    }
    
	/**
	 * Instance the objects and set the Activity Context handed over
	 */
	public OpenGLRenderer(Context context, Handler handler) {

	/** erintokepernyo hasznalatahoz az ablak fokuszba helyezese */
		super(context);

		//@SuppressWarnings("deprecation")
		//int pixelformat = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getPixelFormat();
		try {
			this.setEGLConfigChooser(8, 8, 8, 8, 0, 0);
			this.getHolder().setFormat(PixelFormat.RGBA_8888);
		} catch (Exception e) { 
			e.printStackTrace();
			Log.e("SC2", "OpenGL : Config chooser error");
		} 
		
		//Set this as Renderer
		this.setRenderer(this);
		//Request focus, otherwise buttons won't react
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		//
		this.context = context;
		this.handler = handler;
		//this.setRenderMode(RENDERMODE_WHEN_DIRTY);

		// display information - ellenorizni h jo e egyaltalan?
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        //int scrPixelFormat = display.getPixelFormat();
    
        Point dip = getDisplaySize(display);
        scrWidth = dip.x; 
        scrHeight = dip.y;
        //float scrRefreshRate = display.getRefreshRate();
        // ha rossz a keparany, csere
        if (scrHeight < scrWidth) {
        	float csere = scrWidth;
        	scrWidth = scrHeight;
        	scrHeight = csere;
        } 
        
        String Manufacturer = android.os.Build.MANUFACTURER;
        String Model = android.os.Build.MODEL;
        
        if (Manufacturer.equals("Amazon") || Model.equals("Kindle Fire")) {
        	if (scrHeight >= 1024) scrHeight -= 20;
        }
        
        scrScaleX = scrWidth / dimensionX;
        scrScaleY = scrHeight / dimensionY;

        network = isNetworkAvailable(context); 
        
		mediaplayer = MediaPlayer.create(context, R.raw.mainmenu);
		//mediaplayer.pause();
		mediaplayer.setLooping(true);
		mediaplayer.setVolume(musicvolume, musicvolume);
		mediaplayer.stop();
		
		vibrator = ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE));
		
		// pixel format 4444 -- save memory???
		//getHolder().setFormat(PixelFormat.RGBA_4444); 
		/*
		if (isGoogle) {
			BillingHelper.setCompletedHandler(mTransactionHandler);
			Log.i("BillingService", "completed handler set");
		}
		 */
		
		pref[0] = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
		activatePurchasables();
		database = pref[0].getBoolean("Database", false);
		rate_firsttime = pref[0].getBoolean("Rate_Firsttime", true);

		for (int i=0; i<16; i++) {
			got_achievement[i] = pref[0].getBoolean(got_achievement_id[i], false);
		} 
		total_defeat = pref[0].getInt("Total_Defeat", 0);
		total_kill = pref[0].getInt("Total_Kill", 0);
		special_kill = pref[0].getInt("Special_Kill", 0);
		total_pickup = pref[0].getInt("Total_Pickup", 0);
		//boolean[] spec_used = { false, false, false, false, false, false, false, false, false, false, false, false, false };
		lyspent = pref[0].getInt("LY_Spent", 0);;
		//int npc_escaped = 0;
		
		// LEADBOLT
		lb_ad = pref[0].getInt("LB_AD", 0);

		// get current date
		today = Calendar.getInstance();
		// get previous date
		Calendar prevday = Calendar.getInstance();
		int year; int month; int day; int hourOfDay; int minute;
		year = pref[0].getInt("Lastrun_Year", 2013);
		month = pref[0].getInt("Lastrun_Month", 1);
		day = pref[0].getInt("Lastrun_Day", 1);
		hourOfDay = pref[0].getInt("Lastrun_Hour", 0);
		minute = pref[0].getInt("Lastrun_Minute", 0);
		// save current day
		//pref[0].edit().putInt("Lastrun_Year", today.get(Calendar.YEAR)).commit();
		//pref[0].edit().putInt("Lastrun_Month", today.get(Calendar.MONTH)).commit();
		//pref[0].edit().putInt("Lastrun_Day", today.get(Calendar.DAY_OF_MONTH)).commit();
		//pref[0].edit().putInt("Lastrun_Hour", today.get(Calendar.HOUR_OF_DAY)).commit();
		//pref[0].edit().putInt("Lastrun_Minute", today.get(Calendar.MINUTE)).commit();
		// set prev date, calculate time between
		prevday.set(year, month, day, hourOfDay, minute);
		lastrun = daysBetween(prevday,today);
		
		current_profile = pref[0].getInt("Current_Profile", 1);
		sound = pref[0].getBoolean("Sound", true);
		music = pref[0].getBoolean("Music", true);
		musicvolume = pref[0].getFloat("MusicVol", 0.6f);
		//res = pref[0].getInt("Res", -1);
		//animation = pref[0].getBoolean("Animation", true);
		vibration = pref[0].getBoolean("Vibration", true);
		keepscreen = pref[0].getBoolean("Keepscreen", false);
		if (keepscreen) { handler.sendEmptyMessage(3); }
		//if (res == -1) {
		//	if (scrWidth >= 480) { res = 0; }
		//else { res = 1; }
		//}
		String promotemp = pref[0].getString("Promo", "00000000000"); 
		for (int i=0;i<promotemp.length();i++) {
			if ((Integer.valueOf(promotemp.substring(i,i+1)) == 0) && i<promonum) profile_promos[i] = false;
			else if ((Integer.valueOf(promotemp.substring(i,i+1)) == 1) && i<promonum) profile_promos[i] = true;
		}
		// unlock secret level
		//if (profile_promos[8] && menusystem.mission[9].status == 0) menusystem.mission[9].status = 1;
		
//		allUpgrades.load(gl, context, res);
//		allUpgrades.loadships(gl, context, res);
//		allUpgrades.reset();
//		allUpgrades.update(0,profile_promos);

		pref[1] = context.getSharedPreferences("Profile1", Context.MODE_PRIVATE);
		pref[2] = context.getSharedPreferences("Profile2", Context.MODE_PRIVATE);
		pref[3] = context.getSharedPreferences("Profile3", Context.MODE_PRIVATE);
		
		boolean version_update = false;
		
//		allExplosions.load(gl, context);
//		allBullets.load();
//		allEntities.load();

		
	    // =================================================================================== 
		
		// GOOGLE GAME SERVICES
		login_process = 0;
		
		// Getting status
	    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
	    // Showing status
	    if(status==ConnectionResult.SUCCESS) { Log.i("Google Play Services", "isGooglePlayServicesAvailable Success"); ggs = true; }
	    else { Log.w("Google Play Services", "isGooglePlayServicesAvailable Failed. Google Game Services are disabled"); ggs = false; }
	    
	    if (!isGoogle) ggs = false;
	    
	    if (ggs) {
			gameHelper = new GameHelper((Activity)context, GameHelper.CLIENT_ALL);
			Log.i("Google Play Services", "gameHelper created");
			gameHelper.enableDebugLog(developer_mode);
			gameHelper.setup(this);
			Log.i("Google Play Services", "gameHelper setup");
			signedin = gameHelper.getApiClient().isConnected();
	    }		

		
	}

    private void storeRequestId(String requestId, String key) {
        requestIds.put(requestId, key);
    }
    private void generateSubscribeDialog() {
        DialogCommandWrapper.createConfirmationDialog(this.context, "Subscribe to button clicker to press the button!",
            "Subscribe", "No Thanks", new Runnable() {
                @Override
                public void run() {
                    PurchasingManager.initiatePurchaseRequest("sc2_fullversion");
                }
            }).show();
    }
    private SharedPreferences getSharedPreferencesForCurrentUser() {
        final SharedPreferences settings = context.getSharedPreferences(currentUser, Context.MODE_PRIVATE);
        return settings;
    }
    private SharedPreferences.Editor getSharedPreferencesEditor(){
        return getSharedPreferencesForCurrentUser().edit();
    }
    String getCurrentUser(){
        return currentUser;
    }
    void setCurrentUser(final String currentUser){
        this.currentUser = currentUser;
    }
    private boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	} 
    public void Amazon_Update() {
   
            final SharedPreferences settings = getSharedPreferencesForCurrentUser();

            if (!fullgame && settings.getBoolean(FULL_VERSION, false)) {
                // set full game
				Log.i("Amazon-IAP", "Full Version purchased");
				fullgame = true;
				pref[0].edit().putBoolean("Full_Version", true).commit();
				// hide ad if shown
				if (showad) {
					showad = false;
					handler.sendEmptyMessage(0);
				}
            }
            else {
                // 
            }
            
            if (!allUpgrades.purchased_excelsior && settings.getBoolean(EXCELSIOR, false)) {
                // set full game
				Log.i("Amazon-IAP", "SC Excelsior purchased");
				if (!allUpgrades.purchased_excelsior) {
					allUpgrades.purchased_excelsior = true;
					pref[0].edit().putBoolean("Excelsior_Purchased", true).commit();
					allUpgrades.ship[5].bought = true;
					Save_Profile(0);
				}
            }
            else {
                // 
            }
            if (!allUpgrades.purchased_7bullets && settings.getBoolean(BULLETS7, false)) {
                // set full game
				Log.i("Amazon-IAP", "SC 7 Bullets purchased");
				if (!allUpgrades.purchased_7bullets) {
					allUpgrades.purchased_7bullets = true;
					pref[0].edit().putBoolean("7bullets_Purchased", true).commit();
					allUpgrades.modifier[4].bought = true;
					Save_Profile(0);
				}
            }
            else {
                // 
            }
            if (!allUpgrades.purchased_9bullets && settings.getBoolean(BULLETS9, false)) {
                // set full game
				Log.i("Amazon-IAP", "SC 9 Bullets purchased");
				if (!allUpgrades.purchased_9bullets) {
					allUpgrades.purchased_9bullets = true;
					pref[0].edit().putBoolean("9bullets_Purchased", true).commit();
					allUpgrades.modifier[5].bought = true;
					Save_Profile(0);
				}
            }
            else {
                // 
            }
            if (!allUpgrades.purchased_chaosgun && settings.getBoolean(CHAOSGUN, false)) {
                // set full game
				Log.i("Amazon-IAP", "SC Chaosgun purchased");
				if (!allUpgrades.purchased_chaosgun) {
					allUpgrades.purchased_chaosgun = true;
					pref[0].edit().putBoolean("Chaosgun_Purchased", true).commit();
					allUpgrades.turret[18].bought = true;
					Save_Profile(0);
				}
            }

            if (!allUpgrades.purchased_wings && settings.getBoolean(WINGS, false)) {
                // set full game
				Log.i("Amazon-IAP", "SC Wing Commander purchased");
				if (!allUpgrades.purchased_wings) {
					allUpgrades.purchased_wings = true;
					pref[0].edit().putBoolean("Wings_Purchased", true).commit();
					allUpgrades.upgrade[51].bought = true;
					Save_Profile(0);
				}
            }
            else {
                // 
            }
            if (!allUpgrades.purchased_cruiser && settings.getBoolean(CRUISER, false)) {
                // set full game
				Log.i("Amazon-IAP", "SC Cruiser purchased");
				if (!allUpgrades.purchased_cruiser) {
					allUpgrades.purchased_cruiser = true;
					pref[0].edit().putBoolean("Cruiser_Purchased", true).commit();
					allUpgrades.ship[4].bought = true;
					Save_Profile(0);
				}
            }
            else {
                // 
            }
            if (!allUpgrades.purchased_modulator && settings.getBoolean(MODULATOR, false)) {
                // set full game
				Log.i("Amazon-IAP", "SC Modulator purchased");
				if (!allUpgrades.purchased_modulator) {
					allUpgrades.purchased_modulator = true;
					pref[0].edit().putBoolean("Modulator_Purchased", true).commit();
					allUpgrades.turret[15].bought = true;
					Save_Profile(0);
				}
            }
            else {
                // 
            }

            if (!allUpgrades.purchased_timewarp && settings.getBoolean(TIMEWARP, false)) {
                // set full game
				Log.i("Amazon-IAP", "SC Time Warp purchased");
				if (!allUpgrades.purchased_timewarp) {
					allUpgrades.purchased_timewarp = true;
					pref[0].edit().putBoolean("Timewarp_Purchased", true).commit();
					allUpgrades.special[3].bought = true;
					Save_Profile(0);
				}
            }
            else {
                // 
            }
            if (!allUpgrades.purchased_wave && settings.getBoolean(WAVE, false)) {
                // set full game
				Log.i("Amazon-IAP", "SC Wave purchased");
				if (!allUpgrades.purchased_wave) {
					allUpgrades.purchased_wave = true;
					pref[0].edit().putBoolean("Rocket_Purchased", true).commit();
					allUpgrades.special[28].bought = true;
					Save_Profile(0);
				}
            }
            else {
                // 
            }
            if (!allUpgrades.purchased_mines && settings.getBoolean(MINES, false)) {
                // set full game
				Log.i("Amazon-IAP", "SC Mines purchased");
				if (!allUpgrades.purchased_mines) {
					allUpgrades.purchased_mines = true;
					pref[0].edit().putBoolean("Mines_Purchased", true).commit();
					allUpgrades.special[36].bought = true;
					Save_Profile(0);
				}
            }
            else {
                // 
            }
            if (settings.getBoolean(ALL, false)) {
                // set full game
				Log.i("Amazon-IAP", "SC All purchased");
				
				if (!allUpgrades.purchased_cruiser) {
					allUpgrades.purchased_cruiser = true;
					pref[0].edit().putBoolean("Cruiser_Purchased", true).commit();
					allUpgrades.ship[4].bought = true;
				}
				if (!allUpgrades.purchased_excelsior) {
					allUpgrades.purchased_excelsior = true;
					pref[0].edit().putBoolean("Excelsior_Purchased", true).commit();
					allUpgrades.ship[5].bought = true;
				}
				if (!allUpgrades.purchased_7bullets) {
					allUpgrades.purchased_7bullets = true;
					pref[0].edit().putBoolean("7bullets_Purchased", true).commit();
					allUpgrades.modifier[4].bought = true;
				}
				if (!allUpgrades.purchased_9bullets) {
					allUpgrades.purchased_9bullets = true;
					pref[0].edit().putBoolean("9bullets_Purchased", true).commit();
					allUpgrades.modifier[5].bought = true;
				}
				if (!allUpgrades.purchased_chaosgun) {
					allUpgrades.purchased_chaosgun = true;
					pref[0].edit().putBoolean("Chaosgun_Purchased", true).commit();
					allUpgrades.turret[18].bought = true;
				}

				if (!allUpgrades.purchased_wings) {
					allUpgrades.purchased_wings = true;
					pref[0].edit().putBoolean("Wings_Purchased", true).commit();
					allUpgrades.upgrade[51].bought = true;
				}
				if (!allUpgrades.purchased_modulator) {
					allUpgrades.purchased_modulator = true;
					pref[0].edit().putBoolean("Modulator_Purchased", true).commit();
					allUpgrades.turret[15].bought = true;
				}
				if (!allUpgrades.purchased_timewarp) {
					allUpgrades.purchased_timewarp = true;
					pref[0].edit().putBoolean("Timewarp_Purchased", true).commit();
					allUpgrades.special[3].bought = true;
				}
				if (!allUpgrades.purchased_wave) {
					allUpgrades.purchased_wave = true;
					pref[0].edit().putBoolean("Wave_Purchased", true).commit();
					allUpgrades.special[28].bought = true;
				}
				if (!allUpgrades.purchased_mines) {
					allUpgrades.purchased_mines = true;
					pref[0].edit().putBoolean("Mines_Purchased", true).commit();
					allUpgrades.special[36].bought = true;
				}
				Save_Profile(0);

            }
            else {
                // 
            }

            int numLY = settings.getInt(NUM_LY, 0);
            if (numLY > 0) {
            	// add LY 
            	Log.i("Amazon-IAP", "SC NUMBER LY purchased");
            	LY += numLY;
            	Save_Profile(4);
            	settings.edit().putInt(NUM_LY, 0).commit();
            }
    }

	/*
    // FIZETÉSHEZ
    public Handler mTransactionHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {

			if (!BillingHelper.isRestore) {
			
				Log.i("BillingService", "Transaction complete");
				Log.i("BillingService", "Transaction status: "+BillingHelper.latestPurchase.purchaseState);
				Log.i("BillingService", "Item purchased is: "+BillingHelper.latestPurchase.productId);
				
				
				if(BillingHelper.latestPurchase.isPurchased() && purchase_in_progress){
					purchase_in_progress = false;
					if (BillingHelper.latestPurchase.productId.toString().equals("sc2_fullversion")) {
						Log.i("BillingService", "SC Full Version purchased");
						if (!fullgame) {
							fullgame = true;
							pref[0].edit().putBoolean("Full_Version", true).commit();
							
							// hide ad if shown
							if (showad) {
								showad = false;
								handler.sendEmptyMessage(0);
							}
						}
					} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_ly5")) {
						Log.i("BillingService", "SC 500,000 LY purchased");
						LY += 500000;
						Save_Profile(4);
					} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_ly1k")) {
						Log.i("BillingService", "SC 1,000,000 LY purchased");
						LY += 1000000;
						Save_Profile(4);
					} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_ly2k")) {
						Log.i("BillingService", "SC 2,000,000 LY purchased");
						LY += 2000000;
						Save_Profile(4);
					} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_cruiser")) {
						Log.i("BillingService", "SC Ship 1 purchased");
						if (!allUpgrades.purchased_cruiser) {
							allUpgrades.purchased_cruiser = true;
							pref[0].edit().putBoolean("Cruiser_Purchased", true).commit();
							allUpgrades.ship[4].bought = true;
							Save_Profile(0);
						}
					} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_excelsior")) {
						Log.i("BillingService", "SC Ship 2 purchased");
						if (!allUpgrades.purchased_excelsior) {
							allUpgrades.purchased_excelsior = true;
							pref[0].edit().putBoolean("Excelsior_Purchased", true).commit();
							allUpgrades.ship[5].bought = true;
							Save_Profile(0);
						}
					} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_7bullets")) {
						Log.i("BillingService", "SC Penta Fire purchased");
						if (!allUpgrades.purchased_7bullets) {
							allUpgrades.purchased_7bullets = true;
							pref[0].edit().putBoolean("7bullets_Purchased", true).commit();
							allUpgrades.modifier[4].bought = true;
							Save_Profile(0);
						}
					} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_wings")) {
						Log.i("BillingService", "SC Wing Commander purchased");
						if (!allUpgrades.purchased_wings) {
							allUpgrades.purchased_wings = true;
							pref[0].edit().putBoolean("Wings_Purchased", true).commit();
							allUpgrades.upgrade[51].bought = true;
							Save_Profile(0);
						}
					} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_modulator")) {
						Log.i("BillingService", "SC Modulator purchased");
						if (!allUpgrades.purchased_modulator) {
							allUpgrades.purchased_modulator = true;
							pref[0].edit().putBoolean("Modulator_Purchased", true).commit();
							allUpgrades.turret[15].bought = true;
							Save_Profile(0);
						}
					} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_timewarp")) {
						Log.i("BillingService", "SC Timewarp purchased");
						if (!allUpgrades.purchased_timewarp) {
							allUpgrades.purchased_timewarp = true;
							pref[0].edit().putBoolean("Timewarp_Purchased", true).commit();
							allUpgrades.special[3].bought = true;
							Save_Profile(0);
						}
					} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_wave")) {
						Log.i("BillingService", "SC Wave purchased");
						if (!allUpgrades.purchased_wave) {
							allUpgrades.purchased_wave = true;
							pref[0].edit().putBoolean("Wave_Purchased", true).commit();
							allUpgrades.special[28].bought = true;
							Save_Profile(0);
						}
					} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_mines")) {
						Log.i("BillingService", "SC Mines purchased");
						if (!allUpgrades.purchased_mines) {
							allUpgrades.purchased_mines = true;
							pref[0].edit().putBoolean("Mines_Purchased", true).commit();
							allUpgrades.special[36].bought = true;
							Save_Profile(0);
						}
					} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_all") ||
							(BillingHelper.latestPurchase.productId.toString().equals("android.test.purchased") && developer_mode)) {
						
						if (developer_mode) {
							LY += 10000000;
							Save_Profile(4);
							if (!fullgame) {
								fullgame = true;
								pref[0].edit().putBoolean("Full_Version", true).commit();
								
								// hide ad if shown
								if (showad) {
									showad = false;
									handler.sendEmptyMessage(0);
								}
							}

						}
								
						Log.i("BillingService", "SC All purchased");
						
						if (!allUpgrades.purchased_cruiser) {
							allUpgrades.purchased_cruiser = true;
							pref[0].edit().putBoolean("Cruiser_Purchased", true).commit();
							allUpgrades.ship[4].bought = true;
						}
						if (!allUpgrades.purchased_excelsior) {
							allUpgrades.purchased_excelsior = true;
							pref[0].edit().putBoolean("Excelsior_Purchased", true).commit();
							allUpgrades.ship[5].bought = true;
						}
						if (!allUpgrades.purchased_7bullets) {
							allUpgrades.purchased_7bullets = true;
							pref[0].edit().putBoolean("7bullets_Purchased", true).commit();
							allUpgrades.modifier[4].bought = true;
						}
						if (!allUpgrades.purchased_wings) {
							allUpgrades.purchased_wings = true;
							pref[0].edit().putBoolean("Wings_Purchased", true).commit();
							allUpgrades.upgrade[51].bought = true;
						}
						if (!allUpgrades.purchased_modulator) {
							allUpgrades.purchased_modulator = true;
							pref[0].edit().putBoolean("Modulator_Purchased", true).commit();
							allUpgrades.turret[15].bought = true;
						}
						if (!allUpgrades.purchased_timewarp) {
							allUpgrades.purchased_timewarp = true;
							pref[0].edit().putBoolean("Timewarp_Purchased", true).commit();
							allUpgrades.special[3].bought = true;
						}
						if (!allUpgrades.purchased_wave) {
							allUpgrades.purchased_wave = true;
							pref[0].edit().putBoolean("Wave_Purchased", true).commit();
							allUpgrades.special[28].bought = true;
						}
						if (!allUpgrades.purchased_mines) {
							allUpgrades.purchased_mines = true;
							pref[0].edit().putBoolean("Mines_Purchased", true).commit();
							allUpgrades.special[36].bought = true;
						}
						Save_Profile(0);

					}
				}
			
			} else if (BillingHelper.ppurchases != null) {	// isRestore
				
				if (BillingHelper.ppurchases.get(msg.what).productId.toString().equals("sc2_fullversion")) {
					Log.i("BillingService", "SC Full Version purchased");
					if (!fullgame) {
						fullgame = true;
						pref[0].edit().putBoolean("Full_Version", true).commit();
						// hide ad if shown
						if (showad) {
							showad = false;
							handler.sendEmptyMessage(0);
						}
					}
				} else if (BillingHelper.ppurchases.get(msg.what).productId.toString().equals("sc2_excelsior")) {
					Log.i("BillingService", "SC Ship 2 purchased");
					if (!allUpgrades.purchased_excelsior) {
						allUpgrades.purchased_excelsior = true;
						pref[0].edit().putBoolean("Excelsior_Purchased", true).commit();
						allUpgrades.ship[5].bought = true;
						Save_Profile(0);
					}
				} else if (BillingHelper.ppurchases.get(msg.what).productId.toString().equals("sc2_7bullets")) {
					Log.i("BillingService", "SC Penta Fire purchased");
					if (!allUpgrades.purchased_7bullets) {
						allUpgrades.purchased_7bullets = true;
						pref[0].edit().putBoolean("7bullets_Purchased", true).commit();
						allUpgrades.modifier[4].bought = true;
						Save_Profile(0);
					}
				} else if (BillingHelper.ppurchases.get(msg.what).productId.toString().equals("sc2_cruiser")) {
					Log.i("BillingService", "SC Ship 1 purchased");
					if (!allUpgrades.purchased_cruiser) {
						allUpgrades.purchased_cruiser = true;
						pref[0].edit().putBoolean("Cruiser_Purchased", true).commit();
						allUpgrades.ship[4].bought = true;
						Save_Profile(0);
					}
				} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_wings")) {
					Log.i("BillingService", "SC Wing Commander purchased");
					if (!allUpgrades.purchased_wings) {
						allUpgrades.purchased_wings = true;
						pref[0].edit().putBoolean("Wings_Purchased", true).commit();
						allUpgrades.upgrade[51].bought = true;
						Save_Profile(0);
					}
				} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_modulator")) {
					Log.i("BillingService", "SC Modulator purchased");
					if (!allUpgrades.purchased_modulator) {
						allUpgrades.purchased_modulator = true;
						pref[0].edit().putBoolean("Modulator_Purchased", true).commit();
						allUpgrades.turret[15].bought = true;
						Save_Profile(0);
					}
				} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_timewarp")) {
					Log.i("BillingService", "SC Timewarp purchased");
					if (!allUpgrades.purchased_timewarp) {
						allUpgrades.purchased_timewarp = true;
						pref[0].edit().putBoolean("Timewarp_Purchased", true).commit();
						allUpgrades.special[3].bought = true;
						Save_Profile(0);
					}
				} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_wave")) {
					Log.i("BillingService", "SC Wave purchased");
					if (!allUpgrades.purchased_wave) {
						allUpgrades.purchased_wave = true;
						pref[0].edit().putBoolean("Wave_Purchased", true).commit();
						allUpgrades.special[28].bought = true;
						Save_Profile(0);
					}
				} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_mines")) {
					Log.i("BillingService", "SC Mines purchased");
					if (!allUpgrades.purchased_mines) {
						allUpgrades.purchased_mines = true;
						pref[0].edit().putBoolean("Mines_Purchased", true).commit();
						allUpgrades.special[36].bought = true;
						Save_Profile(0);
					}					
				} else if (BillingHelper.latestPurchase.productId.toString().equals("sc2_all")) {
					Log.i("BillingService", "SC All purchased");
					
					if (!allUpgrades.purchased_cruiser) {
						allUpgrades.purchased_cruiser = true;
						pref[0].edit().putBoolean("Cruiser_Purchased", true).commit();
						allUpgrades.ship[4].bought = true;
					}
					if (!allUpgrades.purchased_excelsior) {
						allUpgrades.purchased_excelsior = true;
						pref[0].edit().putBoolean("Excelsior_Purchased", true).commit();
						allUpgrades.ship[5].bought = true;
					}
					if (!allUpgrades.purchased_7bullets) {
						allUpgrades.purchased_7bullets = true;
						pref[0].edit().putBoolean("7bullets_Purchased", true).commit();
						allUpgrades.modifier[4].bought = true;
					}
					if (!allUpgrades.purchased_wings) {
						allUpgrades.purchased_wings = true;
						pref[0].edit().putBoolean("Wings_Purchased", true).commit();
						allUpgrades.upgrade[51].bought = true;
					}
					if (!allUpgrades.purchased_modulator) {
						allUpgrades.purchased_modulator = true;
						pref[0].edit().putBoolean("Modulator_Purchased", true).commit();
						allUpgrades.turret[15].bought = true;
					}
					if (!allUpgrades.purchased_timewarp) {
						allUpgrades.purchased_timewarp = true;
						pref[0].edit().putBoolean("Timewarp_Purchased", true).commit();
						allUpgrades.special[3].bought = true;
					}
					if (!allUpgrades.purchased_wave) {
						allUpgrades.purchased_wave = true;
						pref[0].edit().putBoolean("Wave_Purchased", true).commit();
						allUpgrades.special[28].bought = true;
					}
					if (!allUpgrades.purchased_mines) {
						allUpgrades.purchased_mines = true;
						pref[0].edit().putBoolean("Mines_Purchased", true).commit();
						allUpgrades.special[36].bought = true;
					}
					Save_Profile(0);				}				
				
			}
			
			
		};
	
    };
    */

	/**
	 * Itt jon letre az opengl rajzolo felulet
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {		

		//loading_bg.load(gl, this.context,R.drawable.loading);
		
		gl.glViewport(0, 0, dimensionX, dimensionY); 	//Reset The Current Viewport

		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping 
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 	//Black Background
		gl.glDisable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glEnable(GL10.GL_BLEND);			//Turn Blending On ( NEW )
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);  // alfa
		
		
		onSurfaceCreated_Init(gl);
		
		quit = false;
		firstrun = true;
		adtimer = 0;
		menu = 0;

	}

	private void activatePurchasables() {
		fullgame = pref[0].getBoolean(fv, false);
		allUpgrades.purchased_excelsior = pref[0].getBoolean(ep, false);
		allUpgrades.purchased_cruiser = pref[0].getBoolean(cp, false);
		allUpgrades.purchased_wings = pref[0].getBoolean(wp, false);
		allUpgrades.purchased_7bullets = pref[0].getBoolean(b7, false);
		allUpgrades.purchased_modulator = pref[0].getBoolean(mp, false);
		allUpgrades.purchased_timewarp = pref[0].getBoolean(tw, false);
		allUpgrades.purchased_wave = pref[0].getBoolean(wv, false);
		allUpgrades.purchased_mines = pref[0].getBoolean(ms, false);
		allUpgrades.purchased_9bullets = pref[0].getBoolean(b9, false);
		allUpgrades.purchased_chaosgun = pref[0].getBoolean(cg, false);

	}

	private void Load_Start_Images(GL10 gl) {

		allBullets.load();
		allEntities.load(this.context);

		particle[0].load(gl, this.context,R.drawable.particle);
		particle[1].load(gl, this.context,R.drawable.particle);
		particle[2].load(gl, this.context,R.drawable.particle);
		shield.load(gl, this.context,R.drawable.shield);

		//Load the texture for the object once during Surface creation
		hud_bar.load(gl, this.context,R.drawable.hudbar);
		structure_bar.load(gl, this.context,R.drawable.spaceship_hpbar);
		hpbar_bg.load(gl, this.context,R.drawable.hpbar_bg); 
		shield_bar.load(gl, this.context,R.drawable.spaceship_shieldbar);
		//skill_bar.load(gl, this.context,R.drawable.skillbar);
		skill_icon.load(gl, this.context,R.drawable.emptyskillbutton);
		skill_buyicon.load(gl, this.context,R.drawable.icon_purchase);

		//mainmenu_bg.load(gl, this.context,R.drawable.mainmenu);
//		menusystem.initialize(gl, context);
//		menusystem.loadimages(gl, context, isGoogle);
		//campaignselector_bg.load(gl, this.context,R.drawable.campaign_selector);
		//galaxy01_bg.load(gl, this.context,R.drawable.galaxyscreen_01);
		
		npc_hpbar.load(gl, this.context,R.drawable.structure_bar);
		//npc_shieldbar.load(gl, this.context,R.drawable.shield_bar);
		npc_shadow.load(gl, this.context,R.drawable.shadow);

		endmission_completed.load(gl, this.context,R.drawable.mission_completed);
		endmission_failed.load(gl, this.context,R.drawable.mission_failed);
		endmission_warning.load(gl, this.context,R.drawable.warning);
		// KIFIZETETT SZOLGALTATASOK ELLENORZESE
		//String[] plista = new String[1];
		//plista[0] = "android.test.purchased";
		//BillingHelper.getPurchaseInformation(plista);
		
		menusystem.initialize(gl, context);
		menusystem.loadimages(gl, context, isGoogle);
	
		allUpgrades.load(gl, context);
		allUpgrades.loadships(gl, context);
		allUpgrades.reset();
		allUpgrades.update(0,profile_promos);

		Load_Profile();
		
		// Set initial dialogue options depending on profile / game state
		menusystem.Update_Dialogues(-1, 0);
		menusystem.Update_Dialogues(-1, 1);
		menusystem.Update_Dialogues(-1, 2);
		
		// tapjoy
		// Retrieve the virtual currency amount from the server.
		if (Main.tapjoy) {
       	try { 
       		Log.i("Tapjoy", "Game Start");
       		tapjoy_earned = true;
       		TapjoyConnect.getTapjoyConnectInstance().getTapPoints(this);
       		/*
       		this.postDelayed(new Runnable() { 
       			public void run() {
       				TapjoyConnect.getTapjoyConnectInstance().spendTapPoints(tap_points, this);
       			}
       		}, 0);*/
       		//TapjoyConnect.getTapjoyConnectInstance().spendTapPoints(tap_points, this);
       	} catch (Exception e) { e.printStackTrace(); } 
		} 

		boolean version_update = false;
		// database frissites legfrissebb verziónál
		if (pref[0].getInt("Version", 100) < 103) { 
			pref[0].edit().putInt("Version", 103).commit();
			version_update = true; 
		}
		
		/*
		if (isGoogle) {
			BillingHelper.setCompletedHandler(mTransactionHandler);
			Log.i("BillingService", "completed handler set");
		
		}
		*/
		
		if (!database || version_update) {
			pref[0].edit().putBoolean("Database", true).commit();
			//if (isGoogle) BillingHelper.restoreTransactionInformation(BillingSecurity.generateNonce());
		}
		
		if (isGoogle) {
			// GOOGLE IN-APP BILLING 3
			// GOOGLE IN-APP BILLING 3
			// GOOGLE IN-APP BILLING 3
			// ===================================================================================
			String base64EncodedPublicKey;
	        if (hd>1) base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj1yg77Z8ssj7N+u9Jb78OZN0+fPuixqYRJoYaHH6fv1fCjRRL09AH8sCqfE4Nb/wS71pLcYbja4AxAag/08hMIm4Uufwm5NZ5gjbkwiScSgvZxgRsHUr6S3a6FvtqSoSdlX4RRrr4odd93IyHDMma+xMJ6epEmZpoz/1ZLxiQYTck/xdOc5fSaqedcyN3HgnsqnE0DWG/rmqxPWzJ7ROO64m74DWX8b70xf/RqoS8zo2cyAF0KihasIOG9U1P+q5tcQx7BB9a/Y2084gKU75EfWbrLAz+M5E1bAQQPKlfFhVItRCRfIB5Kj6/R/hX8qCMK597HLxL9xQJc4dHcdLDQIDAQAB";
	        else base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn5ZLZIU87+ku2lCqr4SZ1zzNKwWw8Nc2piqGsVNpivavnEke/sLZ7UyD6T3DrSMVCzmJqCa0SgXpeOB++zGBS00GJLgL9F45bGmw2of4qZvoo0fQ0BkvxTksStJ7JEs8XqlnmSY80o/kSszD/LOtpEwMlrGFsnoSBbSr4s1FoVLUI/WYmq8TfZbHzPQEIXFVpO9Zov0isboJg6cwXDQa4F9HGPY+GCtsZ+D5uC9TFvaTyqb7be7HbvqOstCnheSWSF+lpikICesP/RKxbzV0WAOfG7Bvr88zExvjIfTeqq9xCVgIp8Qw6y2cVJkNIWcXe0D4J2FOSKImUF1P/s5MxQIDAQAB";
	        // Create the helper, passing it our context and the public key to verify signatures with
	        Log.d(TAG_BILLING, "Creating IAB helper.");
	        mHelper = new IabHelper(context, base64EncodedPublicKey); 
	        // enable debug logging (for a production application, you should set this to false).
	        mHelper.enableDebugLogging(developer_mode);
	        // Start setup. This is asynchronous and the specified listener
	        // will be called once setup completes.
	        Log.d(TAG_BILLING, "Starting setup.");
	        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
	            public void onIabSetupFinished(IabResult result) {
	                Log.d(TAG_BILLING, "Setup finished.");
	
	                if (!result.isSuccess()) {
	                    // Oh noes, there was a problem.
	                    complain("Problem setting up in-app billing: " + result);
	                    return;
	                }
	
	                // Hooray, IAB is fully set up. Now, let's get an inventory of stuff we own.
	                Log.d(TAG_BILLING, "Setup successful. Querying inventory.");
	                mHelper.queryInventoryAsync(mGotInventoryListener);
	            }
	        });
	        
	        //isBillingSupported 
		}

		if (!database || version_update) {
			pref[0].edit().putBoolean("Database", true).commit();
			//if (isGoogle) BillingHelper.restoreTransactionInformation(BillingSecurity.generateNonce());
		} 
		
		
		Update_Ship_Properties();

		 //amazon stuff
		 if (!isGoogle) {
		    requestIds = new HashMap<String, String>();
			ButtonClickerObserver buttonClickerObserver = new ButtonClickerObserver(this); 
			PurchasingManager.registerObserver(buttonClickerObserver);
			amazon_initialized = true;
		 }

		allExplosions.load(gl, context);
		menuAnimations.initialize(hd);
		menuAnimations.loadimages(gl, context);

		menusystem_update = true;
		//menusystem_update_menu = 0;
		logo_start = true;
		logo_reset = false;
		menu = 711;
	}
	
	
	
	// GOOGLE IN-APP BILLING 3
	// GOOGLE IN-APP BILLING 3
	// GOOGLE IN-APP BILLING 3
	// ===================================================================================
    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG_BILLING, "Query inventory finished.");
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG_BILLING, "Query inventory was successful.");
            
            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */
            
            // Do we have the fullversion?
            Purchase purchase;
            boolean p;
            
            purchase = inventory.getPurchase(SKU_FULLVERSION);
            p = (purchase != null && verifyDeveloperPayload(purchase));
            Log.d(TAG_BILLING, "User is " + (p ? "FULLVERSION" : "NOT FULLVERSION"));

            if (p) {
				Log.i("BillingService", "SC Full Version purchased");
				if (!fullgame) {
					fullgame = true;
					pref[0].edit().putBoolean("Full_Version", true).commit();
					
					// hide ad if shown
					if (showad) {
						showad = false;
						handler.sendEmptyMessage(0);
					}
				}
            }

            purchase = inventory.getPurchase(SKU_CRUISER);
            p = (purchase != null && verifyDeveloperPayload(purchase));
            Log.d(TAG_BILLING, "User is " + (p ? "CRUISER" : "NOT CRUISER"));

            if (p) {
				Log.i("BillingService", "SC Ship 1 purchased");
				if (!allUpgrades.purchased_cruiser) {
					allUpgrades.purchased_cruiser = true;
					pref[0].edit().putBoolean("Cruiser_Purchased", true).commit();
					allUpgrades.ship[4].bought = true;
					Save_Profile(0);
				}
            }

            purchase = inventory.getPurchase(SKU_EXCELSIOR);
            p = (purchase != null && verifyDeveloperPayload(purchase));
            Log.d(TAG_BILLING, "User is " + (p ? "EXCELSIOR" : "NOT EXCELSIOR"));

            if (p) {
				Log.i("BillingService", "SC Ship 2 purchased");
				if (!allUpgrades.purchased_excelsior) {
					allUpgrades.purchased_excelsior = true;
					pref[0].edit().putBoolean("Excelsior_Purchased", true).commit();
					allUpgrades.ship[5].bought = true;
					Save_Profile(0);
				}
            }

            purchase = inventory.getPurchase(SKU_7BULLETS);
            p = (purchase != null && verifyDeveloperPayload(purchase));
            Log.d(TAG_BILLING, "User is " + (p ? "7BULLETS" : "NOT 7BULLETS"));

            if (p) {
				Log.i("BillingService", "SC Penta Fire purchased");
				if (!allUpgrades.purchased_7bullets) {
					allUpgrades.purchased_7bullets = true;
					pref[0].edit().putBoolean("7bullets_Purchased", true).commit();
					allUpgrades.modifier[4].bought = true;
					Save_Profile(0);
				}
            }

            purchase = inventory.getPurchase(SKU_9BULLETS);
            p = (purchase != null && verifyDeveloperPayload(purchase));
            Log.d(TAG_BILLING, "User is " + (p ? "9BULLETS" : "NOT 9BULLETS"));

            if (p) {
				Log.i("BillingService", "SC 9 Bullets purchased");
				if (!allUpgrades.purchased_9bullets) {
					allUpgrades.purchased_9bullets = true;
					pref[0].edit().putBoolean("9bullets_Purchased", true).commit();
					allUpgrades.modifier[5].bought = true;
					Save_Profile(0);
				}
            }

            purchase = inventory.getPurchase(SKU_CHAOSGUN);
            p = (purchase != null && verifyDeveloperPayload(purchase));
            Log.d(TAG_BILLING, "User is " + (p ? "CHAOSGUN" : "NOT CHAOSGUN"));

            if (p) {
				Log.i("BillingService", "SC Chaosgun purchased");
				if (!allUpgrades.purchased_chaosgun) {
					allUpgrades.purchased_chaosgun = true;
					pref[0].edit().putBoolean("Chaosgun_Purchased", true).commit();
					allUpgrades.turret[18].bought = true;
					Save_Profile(0);
				}
            }

            purchase = inventory.getPurchase(SKU_WINGS);
            p = (purchase != null && verifyDeveloperPayload(purchase));
            Log.d(TAG_BILLING, "User is " + (p ? "WINGS" : "NOT WINGS"));

            if (p) {
				Log.i("BillingService", "SC Wing Commander purchased");
				if (!allUpgrades.purchased_wings) {
					allUpgrades.purchased_wings = true;
					pref[0].edit().putBoolean("Wings_Purchased", true).commit();
					allUpgrades.upgrade[51].bought = true;
					Save_Profile(0);
				}
            }

            purchase = inventory.getPurchase(SKU_MODULATOR);
            p = (purchase != null && verifyDeveloperPayload(purchase));
            Log.d(TAG_BILLING, "User is " + (p ? "MODULATOR" : "NOT MODULATOR"));

            if (p) {
				Log.i("BillingService", "SC Modulator purchased");
				if (!allUpgrades.purchased_modulator) {
					allUpgrades.purchased_modulator = true;
					pref[0].edit().putBoolean("Modulator_Purchased", true).commit();
					allUpgrades.turret[15].bought = true;
					Save_Profile(0);
				}
            }

            purchase = inventory.getPurchase(SKU_TIMEWARP);
            p = (purchase != null && verifyDeveloperPayload(purchase));
            Log.d(TAG_BILLING, "User is " + (p ? "TIMEWARP" : "NOT TIMEWARP"));

            if (p) {
				Log.i("BillingService", "SC Timewarp purchased");
				if (!allUpgrades.purchased_timewarp) {
					allUpgrades.purchased_timewarp = true;
					pref[0].edit().putBoolean("Timewarp_Purchased", true).commit();
					allUpgrades.special[3].bought = true;
					Save_Profile(0);
				}
            }

            purchase = inventory.getPurchase(SKU_WAVE);
            p = (purchase != null && verifyDeveloperPayload(purchase));
            Log.d(TAG_BILLING, "User is " + (p ? "WAVE" : "NOT WAVE"));

            if (p) {
				Log.i("BillingService", "SC Wave purchased");
				if (!allUpgrades.purchased_wave) {
					allUpgrades.purchased_wave = true;
					pref[0].edit().putBoolean("Wave_Purchased", true).commit();
					allUpgrades.special[28].bought = true;
					Save_Profile(0);
				}
            }

            purchase = inventory.getPurchase(SKU_MINES);
            p = (purchase != null && verifyDeveloperPayload(purchase));
            Log.d(TAG_BILLING, "User is " + (p ? "MINES" : "NOT MINES"));

            if (p) {
				Log.i("BillingService", "SC Mines purchased");
				if (!allUpgrades.purchased_mines) {
					allUpgrades.purchased_mines = true;
					pref[0].edit().putBoolean("Mines_Purchased", true).commit();
					allUpgrades.special[36].bought = true;
					Save_Profile(0);
				}
            }


            purchase = inventory.getPurchase(SKU_ALL);
            p = (purchase != null && verifyDeveloperPayload(purchase));
            Log.d(TAG_BILLING, "User is " + (p ? "ALL" : "NOT ALL"));

            if (p) {
				Log.i("BillingService", "SC All purchased");
				
				if (!allUpgrades.purchased_cruiser) {
					allUpgrades.purchased_cruiser = true;
					pref[0].edit().putBoolean("Cruiser_Purchased", true).commit();
					allUpgrades.ship[4].bought = true;
				}
				if (!allUpgrades.purchased_excelsior) {
					allUpgrades.purchased_excelsior = true;
					pref[0].edit().putBoolean("Excelsior_Purchased", true).commit();
					allUpgrades.ship[5].bought = true;
				}
				if (!allUpgrades.purchased_7bullets) {
					allUpgrades.purchased_7bullets = true;
					pref[0].edit().putBoolean("7bullets_Purchased", true).commit();
					allUpgrades.modifier[4].bought = true;
				}
				if (!allUpgrades.purchased_9bullets) {
					allUpgrades.purchased_9bullets = true;
					pref[0].edit().putBoolean("9bullets_Purchased", true).commit();
					allUpgrades.modifier[5].bought = true;
				}
				if (!allUpgrades.purchased_chaosgun) {
					allUpgrades.purchased_chaosgun = true;
					pref[0].edit().putBoolean("Chaosgun_Purchased", true).commit();
					allUpgrades.turret[18].bought = true;
				}

				if (!allUpgrades.purchased_wings) {
					allUpgrades.purchased_wings = true;
					pref[0].edit().putBoolean("Wings_Purchased", true).commit();
					allUpgrades.upgrade[51].bought = true;
				}
				if (!allUpgrades.purchased_modulator) {
					allUpgrades.purchased_modulator = true;
					pref[0].edit().putBoolean("Modulator_Purchased", true).commit();
					allUpgrades.turret[15].bought = true;
				}
				if (!allUpgrades.purchased_timewarp) {
					allUpgrades.purchased_timewarp = true;
					pref[0].edit().putBoolean("Timewarp_Purchased", true).commit();
					allUpgrades.special[3].bought = true;
				}
				if (!allUpgrades.purchased_wave) {
					allUpgrades.purchased_wave = true;
					pref[0].edit().putBoolean("Wave_Purchased", true).commit();
					allUpgrades.special[28].bought = true;
				}
				if (!allUpgrades.purchased_mines) {
					allUpgrades.purchased_mines = true;
					pref[0].edit().putBoolean("Mines_Purchased", true).commit();
					allUpgrades.special[36].bought = true;
				}
				Save_Profile(0);
            }

            
			/*
            // Do we have the infinite gas plan?
            Purchase infiniteGasPurchase = inventory.getPurchase(SKU_INFINITE_GAS);
            mSubscribedToInfiniteGas = (infiniteGasPurchase != null && 
                    verifyDeveloperPayload(infiniteGasPurchase));
            Log.d(TAG_BILLING, "User " + (mSubscribedToInfiniteGas ? "HAS" : "DOES NOT HAVE") 
                        + " infinite gas subscription.");
            if (mSubscribedToInfiniteGas) mTank = TANK_MAX;
			*/
            
            // Check for consumable delivery -- if we own consumable, we should fill up
            
            purchase = inventory.getPurchase(SKU_LY5);
            if (purchase != null && verifyDeveloperPayload(purchase)) {
                Log.d(TAG_BILLING, "We have it. Consuming it.");
                mHelper.consumeAsync(inventory.getPurchase(SKU_LY5), mConsumeFinishedListener);
                return;
            }
            purchase = inventory.getPurchase(SKU_LY1K);
            if (purchase != null && verifyDeveloperPayload(purchase)) {
                Log.d(TAG_BILLING, "We have it. Consuming it.");
                mHelper.consumeAsync(inventory.getPurchase(SKU_LY1K), mConsumeFinishedListener);
                return;
            }
            purchase = inventory.getPurchase(SKU_LY2K);
            if (purchase != null && verifyDeveloperPayload(purchase)) {
                Log.d(TAG_BILLING, "We have it. Consuming it.");
                mHelper.consumeAsync(inventory.getPurchase(SKU_LY2K), mConsumeFinishedListener);
                return;
            }
            Purchase testPurchase = inventory.getPurchase(SKU_TEST);
            if (testPurchase != null && verifyDeveloperPayload(testPurchase)) {
                Log.d(TAG_BILLING, "We have it. Consuming it.");
                mHelper.consumeAsync(inventory.getPurchase(SKU_TEST), mConsumeFinishedListener);
                return;
            }

            //updateUi();
            //setWaitScreen(false);
            //Log.d(TAG_BILLING, "Initial inventory query finished; enabling main UI.");
        }
    }; 
    
     /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        
        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         * 
         * WARNING: Locally generating a random string when starting a purchase and 
         * verifying it here might seem like a good approach, but this will fail in the 
         * case where the user purchases an item on one device and then uses your app on 
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         * 
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         * 
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on 
         *    one device work on other devices owned by the user).
         * 
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */
        
        return true;
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG_BILLING, "Purchase finished: " + result + ", purchase: " + purchase);
            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                //setWaitScreen(false);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                //setWaitScreen(false);
                return;
            }

            Log.d(TAG_BILLING, "Purchase successful.");

            if (purchase.getSku().equals(SKU_LY5)) {
                // bought consumable. So consume it.
                Log.d(TAG_BILLING, "Purchase is 5k LY. Starting consumption.");
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
            else if (purchase.getSku().equals(SKU_LY1K)) {
                // bought consumable. So consume it.
                Log.d(TAG_BILLING, "Purchase is 1m LY. Starting consumption.");
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
            else if (purchase.getSku().equals(SKU_LY2K)) {
                // bought consumable. So consume it.
                Log.d(TAG_BILLING, "Purchase is 2m LY. Starting consumption.");
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
            else if (purchase.getSku().equals(SKU_TEST)) {
                // bought consumable. So consume it.
                Log.d(TAG_BILLING, "Purchase is test. Starting test consumption.");
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
            else if (purchase.getSku().equals(SKU_FULLVERSION)) {
                // bought the premium upgrade!
                Log.d(TAG_BILLING, "Purchase is fullversion. Congratulating user.");
                alert("Thank you for upgrading to full version!");
				Log.i("BillingService", "SC Full Version purchased");
				if (!fullgame) {
					fullgame = true;
					pref[0].edit().putBoolean("Full_Version", true).commit();
					
					// hide ad if shown
					if (showad) {
						showad = false;
						handler.sendEmptyMessage(0);
					}
				}
            }
            else if (purchase.getSku().equals(SKU_CRUISER)) {
                // bought the premium upgrade!
				Log.i("BillingService", "SC Ship 1 purchased");
				if (!allUpgrades.purchased_cruiser) {
					allUpgrades.purchased_cruiser = true;
					pref[0].edit().putBoolean("Cruiser_Purchased", true).commit();
					allUpgrades.ship[4].bought = true;
					Save_Profile(0);
				}
			}
            else if (purchase.getSku().equals(SKU_EXCELSIOR)) {
                // bought the premium upgrade!
				Log.i("BillingService", "SC Ship 2 purchased");
				if (!allUpgrades.purchased_excelsior) {
					allUpgrades.purchased_excelsior = true;
					pref[0].edit().putBoolean("Excelsior_Purchased", true).commit();
					allUpgrades.ship[5].bought = true;
					Save_Profile(0);
				}
            }
            else if (purchase.getSku().equals(SKU_7BULLETS)) {
                // bought the premium upgrade!
				Log.i("BillingService", "SC Penta Fire purchased");
				if (!allUpgrades.purchased_7bullets) {
					allUpgrades.purchased_7bullets = true;
					pref[0].edit().putBoolean("7bullets_Purchased", true).commit();
					allUpgrades.modifier[4].bought = true;
					Save_Profile(0);
				}
            }
            else if (purchase.getSku().equals(SKU_9BULLETS)) {
                // bought the premium upgrade!
				Log.i("BillingService", "SC 9 Bullets purchased");
				if (!allUpgrades.purchased_9bullets) {
					allUpgrades.purchased_9bullets = true;
					pref[0].edit().putBoolean("9bullets_Purchased", true).commit();
					allUpgrades.modifier[5].bought = true;
					Save_Profile(0);
				}
            }
            else if (purchase.getSku().equals(SKU_CHAOSGUN)) {
                // bought the premium upgrade!
				Log.i("BillingService", "SC Chaosgun purchased");
				if (!allUpgrades.purchased_chaosgun) {
					allUpgrades.purchased_chaosgun = true;
					pref[0].edit().putBoolean("Chaosgun_Purchased", true).commit();
					allUpgrades.turret[18].bought = true;
					Save_Profile(0);
				}
            }

            else if (purchase.getSku().equals(SKU_WINGS)) {
                // bought the premium upgrade!
				Log.i("BillingService", "SC Wing Commander purchased");
				if (!allUpgrades.purchased_wings) {
					allUpgrades.purchased_wings = true;
					pref[0].edit().putBoolean("Wings_Purchased", true).commit();
					allUpgrades.upgrade[51].bought = true;
					Save_Profile(0);
				}
            }
            else if (purchase.getSku().equals(SKU_MODULATOR)) {
                // bought the premium upgrade!
				Log.i("BillingService", "SC Modulator purchased");
				if (!allUpgrades.purchased_modulator) {
					allUpgrades.purchased_modulator = true;
					pref[0].edit().putBoolean("Modulator_Purchased", true).commit();
					allUpgrades.turret[15].bought = true;
					Save_Profile(0);
				}
            }
            else if (purchase.getSku().equals(SKU_TIMEWARP)) {
                // bought the premium upgrade!
				Log.i("BillingService", "SC Timewarp purchased");
				if (!allUpgrades.purchased_timewarp) {
					allUpgrades.purchased_timewarp = true;
					pref[0].edit().putBoolean("Timewarp_Purchased", true).commit();
					allUpgrades.special[3].bought = true;
					Save_Profile(0);
				}
            }
            else if (purchase.getSku().equals(SKU_WAVE)) {
                // bought the premium upgrade!
				Log.i("BillingService", "SC Wave purchased");
				if (!allUpgrades.purchased_wave) {
					allUpgrades.purchased_wave = true;
					pref[0].edit().putBoolean("Wave_Purchased", true).commit();
					allUpgrades.special[28].bought = true;
					Save_Profile(0);
				}
            }
            else if (purchase.getSku().equals(SKU_MINES)) {
                // bought the premium upgrade!
				Log.i("BillingService", "SC Mines purchased");
				if (!allUpgrades.purchased_mines) {
					allUpgrades.purchased_mines = true;
					pref[0].edit().putBoolean("Mines_Purchased", true).commit();
					allUpgrades.special[36].bought = true;
					Save_Profile(0);
				}
            }
            else if (purchase.getSku().equals(SKU_ALL)) {
                // bought the premium upgrade!
				Log.i("BillingService", "SC All purchased");
				
				if (!allUpgrades.purchased_cruiser) {
					allUpgrades.purchased_cruiser = true;
					pref[0].edit().putBoolean("Cruiser_Purchased", true).commit();
					allUpgrades.ship[4].bought = true;
				}
				if (!allUpgrades.purchased_excelsior) {
					allUpgrades.purchased_excelsior = true;
					pref[0].edit().putBoolean("Excelsior_Purchased", true).commit();
					allUpgrades.ship[5].bought = true;
				}
				if (!allUpgrades.purchased_7bullets) {
					allUpgrades.purchased_7bullets = true;
					pref[0].edit().putBoolean("7bullets_Purchased", true).commit();
					allUpgrades.modifier[4].bought = true;
				}
				if (!allUpgrades.purchased_9bullets) {
					allUpgrades.purchased_9bullets = true;
					pref[0].edit().putBoolean("9bullets_Purchased", true).commit();
					allUpgrades.modifier[5].bought = true;
				}
				if (!allUpgrades.purchased_chaosgun) {
					allUpgrades.purchased_chaosgun = true;
					pref[0].edit().putBoolean("Chaosgun_Purchased", true).commit();
					allUpgrades.turret[18].bought = true;
				}

				if (!allUpgrades.purchased_wings) {
					allUpgrades.purchased_wings = true;
					pref[0].edit().putBoolean("Wings_Purchased", true).commit();
					allUpgrades.upgrade[51].bought = true;
				}
				if (!allUpgrades.purchased_modulator) {
					allUpgrades.purchased_modulator = true;
					pref[0].edit().putBoolean("Modulator_Purchased", true).commit();
					allUpgrades.turret[15].bought = true;
				}
				if (!allUpgrades.purchased_timewarp) {
					allUpgrades.purchased_timewarp = true;
					pref[0].edit().putBoolean("Timewarp_Purchased", true).commit();
					allUpgrades.special[3].bought = true;
				}
				if (!allUpgrades.purchased_wave) {
					allUpgrades.purchased_wave = true;
					pref[0].edit().putBoolean("Wave_Purchased", true).commit();
					allUpgrades.special[28].bought = true;
				}
				if (!allUpgrades.purchased_mines) {
					allUpgrades.purchased_mines = true;
					pref[0].edit().putBoolean("Mines_Purchased", true).commit();
					allUpgrades.special[36].bought = true;
				}
				Save_Profile(0);            }
            
        }
    };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG_BILLING, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            if (result.isSuccess()) {
                if (purchase.getSku().equals(SKU_LY5)) {
                	Log.d(TAG_BILLING, "Consumption successful. Provisioning.");
					Log.i("BillingService", "SC 500,000 LY purchased");
					LY += 500000;
					Save_Profile(4);
					alert("You gained 500,000 Light Years.");
                }
                else if (purchase.getSku().equals(SKU_LY1K)) {
                	Log.d(TAG_BILLING, "Consumption successful. Provisioning.");
					Log.i("BillingService", "SC 1,000,000 LY purchased");
					LY += 1000000;
					Save_Profile(4);
					alert("You gained 1,000,000 Light Years.");
                }
                else if (purchase.getSku().equals(SKU_LY2K)) {
                	Log.d(TAG_BILLING, "Consumption successful. Provisioning.");
					Log.i("BillingService", "SC 2,000,000 LY purchased");
					LY += 2000000;
					Save_Profile(4);
					alert("You gained 2,000,000 Light Years.");
                }
                else if (purchase.getSku().equals(SKU_TEST)) {
                 	Log.d(TAG_BILLING, "Consumption successful. Provisioning.");
					LY += 1000000;
					Save_Profile(4);
                	alert("TEST PURCHASE");
                }
            }
            else {
                complain("Error while consuming: " + result);
            }
            //updateUi();
            //setWaitScreen(false);
            Log.d(TAG_BILLING, "End consumption flow.");
        }
    }; 
    
    void complain(String message) {
        Log.e(TAG_BILLING, "**** Error: " + message);
        if (developer_mode) alert(message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(context);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG_BILLING, "Showing alert dialog: " + message);
        bld.create().show();
    } 
	// =================================================================================== 
    
    
	
	private void onSurfaceCreated_Init(GL10 gl) {

        //
		particle[0] = new ParticleSystem(particle_size[0],particle_size[1],true,0);
		particle[1] = new ParticleSystem(particle_size[0],particle_size[1],true,0);
		particle[2] = new ParticleSystem(particle_size[0],particle_size[1],true,0);
		shield = new My2DImage(shield_size[0],shield_size[1],true);

		hud_bar = new My2DImage((int)(hd*16),(int)(hd*32),true);
		structure_bar = new My2DImage((int)(hd*16),(int)(hd*32),true);
		shield_bar = new My2DImage((int)(hd*16),(int)(hd*32),true);
		//skill_bar = new My2DImage((int)(hd*480),(int)(hd*80),true);
		skill_icon = new My2DImage((int)(hd*80),(int)(hd*80),true);
		skill_buyicon = new My2DImage((int)(hd*80),(int)(hd*80),true);

		npc_hpbar = new My2DImage((int)(hd*22),(int)(hd*4),true);
		//npc_shieldbar = new My2DImage((int)(hd*22),(int)(hd*4),true);
		npc_shadow = new My2DImage((int)(hd*128),(int)(hd*128),true);
		hpbar_bg = new My2DImage((int)(68*hd),(int)(8*hd),true);  
		
		endmission_completed = new My2DImage((int)(hd*348),(int)(hd*82),true);
		endmission_failed = new My2DImage((int)(hd*338),(int)(hd*43),true);
		endmission_warning = new My2DImage((int)(hd*288),(int)(hd*52),true);

		menusystem.bgimage.load(gl, this.context,R.drawable.loading);
		//helper[1] = Integer.toString(menusystem.galaxy[0].status);	

		menufnt = new TexFont(context,gl,scrScaleX,scrScaleY,dimensionY);
		galaxyfnt = new TexFont(context,gl,scrScaleX,scrScaleY,dimensionY);

    	Log.i("Font", "Loading fonts...");
	     // Load font file from Assets
	     try
	     {
	    	 // high 720x1200 or higher
	         if (scrWidth >= 720) {
		    	  galaxyfnt.LoadFont("OCRAStd22.bff",gl);
		    	  menufnt.LoadFont("Verdana34.bff",gl);
		    	  Log.i("Font", "...Success");
		    	  // Use scaling for higher resolutions
		    	  // if (scrWidth > 800) { menufnt_scale = scrScaleY; galaxyfnt_scale = scrScaleY; }
		    // native 480 x 800 support
	         } else if (scrWidth < 720 && scrWidth >= 480) {
		    	  galaxyfnt.LoadFont("OCRAStd15.bff",gl);
		    	  menufnt.LoadFont("Verdana23.bff",gl);
		    	  Log.i("Font", "...Success");
		    // 320 x 480 support
	         } else if (scrWidth < 480 && scrWidth >= 320) {
		    	  galaxyfnt.LoadFont("OCRAStd10.bff",gl);
		    	  menufnt.LoadFont("Verdana15.bff",gl);
		    	  Log.i("Font", "...Success");
		    // 240 x 400 support
	         } else if (scrWidth < 320 && scrHeight >= 400) {
		    	  galaxyfnt.LoadFont("OCRAStd8.bff",gl);
		    	  menufnt.LoadFont("Verdana12.bff",gl);
		    	  Log.i("Font", "...Success");
		    // 240 x 320 or lower resolution support
	         } else if (scrHeight <= 320) {
		    	  galaxyfnt.LoadFont("OCRAStd7.bff",gl);
		    	  menufnt.LoadFont("Verdana10.bff",gl);
		    	  Log.i("Font", "...Success");
		    // Unknown resolution, use native font with scaling
	         } else {
		    	  galaxyfnt.LoadFont("OCRAStd15.bff",gl);
		    	  menufnt.LoadFont("Verdana23.bff",gl);
		    	  menufnt_scale = scrScaleY; galaxyfnt_scale = scrScaleY;
		    	  Log.i("Font", "...Success");
	         }
	     }	      
	     catch (IOException e) 
	     {
	      e.printStackTrace();
	      Log.i("Font", "...Failed");
	     }

    	Log.i("Starship Commander", "screen size: " + Float.toString(scrWidth) + "x" + Float.toString(scrHeight));
    	Log.i("Starship Commander", "font scale: " + Float.toString(menufnt_scale));

  	  
	     //galaxyfnt.SetScale(scrScaleX*galaxyfnt_scale,scrScaleY*galaxyfnt_scale);
	     galaxyfnt.SetScale(galaxyfnt_scale,galaxyfnt_scale); 
	     galaxyfnt.SetPolyColor(0.5f, 0.5f, 0.5f, 0.5f);
	     menufnt.SetScale(menufnt_scale,menufnt_scale);


		//if (music) mediaplayer.start();

		//loading_bg.release(gl);
	}

	// load new audio file to media player
	private void media_load(int resid, boolean start) {

		//float mvolume = 1f;
		//if (volume > 0) mvolume = volume;
		//else mvolume = musicvolume;
		
		AssetFileDescriptor afd = context.getResources().openRawResourceFd(resid);
		try
	    {   
	    	mediaplayer.reset();
	    	mediaplayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
	    	mediaplayer.prepare();
	    	mediaplayer.setLooping(true);
			mediaplayer.setVolume(musicvolume, musicvolume);
	    	afd.close();
	    	Log.e("MediaPlayer", "media file loaded");

	    	if (start) mediaplayer.start(); //else mediaplayer.pause();
			Log.e("MediaPlayer", "media file started");
	    }
	    catch (Exception e)
	    {
	        //Log.e(TAG, "Unable to play audio queue do to exception: " + e.getMessage(), e);
	    	e.printStackTrace();
	    }
		
	}
	
	// get time function (in ms)
	public long GetTickCount() {
		
		return System.nanoTime() / 1000000;
		//return System.currentTimeMillis();
		//return SystemClock.uptimeMillis();
	}

	// convert LY integer to proper string (isK = /1000)
	public String toLY(long ly, boolean isK) {
		
		if (isK) ly = ly / 1000;
        String numString = Long.toString(ly);
        String result = "";

        while (numString.length() > 3)
        {
                String chunk = numString.substring(numString.length() - 3);
                numString = numString.substring(0, numString.length() - 3);
                result = "," + chunk + result;
        }

        if (numString.length() > 0)
        {
                result = numString + result;
        }

        if (isK) return (result+"K");
        else return result;
	}
	

	
	// Calculate days between two calendar data
	public static long daysBetween(Calendar startDate, Calendar endDate) {
		  Calendar date = (Calendar) startDate.clone();
		  long daysBetween = 0;
		  while (date.before(endDate)) {
		      date.add(Calendar.DAY_OF_MONTH, 1);
		      daysBetween++;
		  }
		  return daysBetween;
	}
	

public void Save_Profile(int part) { 
	// 0 = update ship properties
	// 1 = update galaxy status
	// 2 = update mission status, mission scores
	// 3 = update mission status only
	// 4 = update LY only
	// 5 = update promos
	// 6 = update survival
	if (current_profile < 1 ||  current_profile > 3) current_profile = 1;
	
	try {
		if (pref[current_profile] == null) {
			if (current_profile == 1) pref[1] = context.getSharedPreferences("Profile1", Context.MODE_PRIVATE);
			if (current_profile == 2) pref[2] = context.getSharedPreferences("Profile2", Context.MODE_PRIVATE);
			if (current_profile == 3) pref[3] = context.getSharedPreferences("Profile3", Context.MODE_PRIVATE);
		} 
	} catch(Exception e) { }
	
	if (extraturret >= 0) {
		extraturret = -1;
		spaceship.turret[spaceship.turret_active] = prevturret;
		prevturret = -1;
	}
	if (extramodifier >= 0) {
		extramodifier = -1;
		spaceship.modifier[spaceship.modifier_active] = prevmodifier;
		prevmodifier = -1;
	}
	
	if (part == 0) {
		// SpaceShip
		pref[current_profile].edit().putInt("SpaceShip_Type", spaceship.type).commit();
		pref[current_profile].edit().putInt("SpaceShip_TurretNum", spaceship.turret_num).commit();
		pref[current_profile].edit().putInt("SpaceShip_Turret1", spaceship.turret[0]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Turret2", spaceship.turret[1]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Turret3", spaceship.turret[2]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Turret4", spaceship.turret[3]).commit();
		pref[current_profile].edit().putInt("SpaceShip_ModifierNum", spaceship.modifier_num).commit();
		pref[current_profile].edit().putInt("SpaceShip_Modifier1", spaceship.modifier[0]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Modifier2", spaceship.modifier[1]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Modifier3", spaceship.modifier[2]).commit();
		pref[current_profile].edit().putInt("SpaceShip_SpecialNum", spaceship.special_num).commit();
		pref[current_profile].edit().putInt("SpaceShip_Special1", spaceship.special[0]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Special2", spaceship.special[1]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Special3", spaceship.special[2]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Special4", spaceship.special[3]).commit();
		pref[current_profile].edit().putInt("SpaceShip_UpgradeNum", spaceship.upgrade_num).commit();
		pref[current_profile].edit().putInt("SpaceShip_Upgrade1", spaceship.upgrade[0]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Upgrade2", spaceship.upgrade[1]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Upgrade3", spaceship.upgrade[2]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Upgrade4", spaceship.upgrade[3]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Upgrade5", spaceship.upgrade[4]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Upgrade6", spaceship.upgrade[5]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Upgrade7", spaceship.upgrade[6]).commit();
		pref[current_profile].edit().putInt("SpaceShip_Upgrade8", spaceship.upgrade[7]).commit();
		pref[current_profile].edit().putInt("SpaceShip_ActiveTurret", spaceship.turret_active).commit();
		pref[current_profile].edit().putInt("SpaceShip_ActiveModifier", spaceship.modifier_active).commit();
		// Shop
		String shop = "";
		for (int i=0;i<allUpgrades.maxturret;i++) {
			if (allUpgrades.turret[i].bought) { shop+= "1"; } else { shop+= "0"; }
		}
		pref[current_profile].edit().putString("Shop_Turrets", shop).commit();
		
		shop = "";
		for (int i=0;i<allUpgrades.maxmodifier;i++) {
			if (allUpgrades.modifier[i].bought) { shop+= "1"; } else { shop+= "0"; }
		}
		pref[current_profile].edit().putString("Shop_Modifiers", shop).commit();
		
		shop = "";
		for (int i=0;i<allUpgrades.maxspecial;i++) {
			if (allUpgrades.special[i].bought) { shop+= "1"; } else { shop+= "0"; }
		}
		pref[current_profile].edit().putString("Shop_Specials", shop).commit();
		
		shop = "";
		for (int i=0;i<allUpgrades.maxupgrade;i++) {
			if (allUpgrades.upgrade[i].bought) { shop+= "1"; } else { shop+= "0"; }
		}
		pref[current_profile].edit().putString("Shop_Upgrades", shop).commit();
		
		shop = "";
		for (int i=0;i<allUpgrades.maxship;i++) {
			if (allUpgrades.ship[i].bought) { shop+= "1"; } else { shop+= "0"; }
		}
		pref[current_profile].edit().putString("Shop_Ships", shop).commit();
		
		// Player
		pref[current_profile].edit().putLong("LY", LY).commit();
	} else if (part == 1) {	
		// Galaxy
		String galaxy = "";
		int k = 0;
		for (int i=0;i<menusystem.total_galaxy;i++) {
			if (menusystem.galaxy[i].status == 0) galaxy += "0";
			else if (menusystem.galaxy[i].status == 1) galaxy += "1";
			else if (menusystem.galaxy[i].status == 2) { galaxy += "2"; k++; }
		}
		pref[current_profile].edit().putString("Galaxy_Status", galaxy).commit();
		pref[current_profile].edit().putInt("Galaxy_Completed", k).commit();
		// Player
		pref[current_profile].edit().putLong("LY", LY).commit();

		// GOOGLE GAME SERVICES
		if (k >= 2) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_1GALAXY);
			}
			if (!got_achievement[2]) {
				got_achievement[2] = true; pref[0].edit().putBoolean(got_achievement_id[2], true);
				achievement_anim = true;
			}
		}
		if (k >= 8) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_1MLY);
			}
			if (!got_achievement[8]) {
				got_achievement[8] = true; pref[0].edit().putBoolean(got_achievement_id[8], true);
				achievement_anim = true;
			}
		}
		if (k >= 12) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_75PGALAXY);
			}
			if (!got_achievement[12]) {
				got_achievement[12] = true; pref[0].edit().putBoolean(got_achievement_id[12], true);
				achievement_anim = true;
			}
		}

	} else if (part == 2) { 
		// Mission Data
		String mission = "";
		int k = 0;
		for (int i=0;i<menusystem.total_mission;i++) {
			if (menusystem.mission[i].completed == 0) mission += "0";
			else if (menusystem.mission[i].completed == 1) mission += "1";
			else if (menusystem.mission[i].completed == 2) { mission += "2"; k++; }
			else if (menusystem.mission[i].completed == 3) { mission += "3"; k++; }
			else if (menusystem.mission[i].completed == 4) { mission += "4"; k++; }
		}
		pref[current_profile].edit().putString("Mission_Status", mission).commit();
		pref[current_profile].edit().putInt("Mission_Completed", k).commit();
		long l = 0;
		String highscore = "";
		for (int i=0;i<menusystem.total_mission;i++) {
			highscore += Integer.toString(menusystem.mission[i].highscore) + " ";
			l += menusystem.mission[i].highscore;
		}
		pref[current_profile].edit().putString("Mission_Highscore", highscore).commit();
		pref[current_profile].edit().putLong("Mission_Totalscore", l).commit();
		// Player
		pref[current_profile].edit().putLong("LY", LY).commit();
	
		// GOOGLE GAME SERVICES
		if (ggs && gameHelper.getApiClient().isConnected()) {
			Games.Leaderboards.submitScore(gameHelper.getApiClient(), LEADERBOARD_ID, l);
		}
	} else if (part == 3) { 
		// Mission Data
		String mission = "";
		int k = 0;
		for (int i=0;i<menusystem.total_mission;i++) {
			if (menusystem.mission[i].completed == 0) mission += "0";
			else if (menusystem.mission[i].completed == 1) mission += "1";
			else if (menusystem.mission[i].completed == 2) { mission += "2"; k++; }
			else if (menusystem.mission[i].completed == 3) { mission += "3"; k++; }
			else if (menusystem.mission[i].completed == 4) { mission += "4"; k++; }
			//else mission += "0";
		}
		pref[current_profile].edit().putString("Mission_Status", mission).commit();
		pref[current_profile].edit().putInt("Mission_Completed", k).commit();
	// LY
	} else if (part == 4) { 
		pref[current_profile].edit().putLong("LY", LY).commit();
	// Promos
	} else if (part == 5) {
		String promotemp = "";
		for (int m=0;m<promonum;m++) {
			if (profile_promos[m]) {
				promotemp+="1";
			} else {
				promotemp+="0";
			}
		}
		pref[0].edit().putString("Promo", promotemp).commit();
	}
}

public void Load_Profile() { 
		// SpaceShip
		spaceship.type = pref[current_profile].getInt("SpaceShip_Type", 0);
		spaceship.turret_num = pref[current_profile].getInt("SpaceShip_TurretNum", 1);
		spaceship.turret[0] = pref[current_profile].getInt("SpaceShip_Turret1", 0);
		spaceship.turret[1] = pref[current_profile].getInt("SpaceShip_Turret2", -1);
		spaceship.turret[2] = pref[current_profile].getInt("SpaceShip_Turret3", -1);
		spaceship.turret[3] = pref[current_profile].getInt("SpaceShip_Turret4", -1);
		spaceship.modifier_num = pref[current_profile].getInt("SpaceShip_ModifierNum", 1);
		spaceship.modifier[0] = pref[current_profile].getInt("SpaceShip_Modifier1", 0);
		spaceship.modifier[1] = pref[current_profile].getInt("SpaceShip_Modifier2", -1);
		spaceship.modifier[2] = pref[current_profile].getInt("SpaceShip_Modifier3", -1);
		spaceship.special_num = pref[current_profile].getInt("SpaceShip_SpecialNum", 2);
		spaceship.special[0] = pref[current_profile].getInt("SpaceShip_Special1", 0);
		spaceship.special[1] = pref[current_profile].getInt("SpaceShip_Special2", 4);
		spaceship.special[2] = pref[current_profile].getInt("SpaceShip_Special3", -1);
		spaceship.special[3] = pref[current_profile].getInt("SpaceShip_Special4", -1);
		spaceship.upgrade_num = pref[current_profile].getInt("SpaceShip_UpgradeNum", 6);
		spaceship.upgrade[0] = pref[current_profile].getInt("SpaceShip_Upgrade1", -1);
		spaceship.upgrade[1] = pref[current_profile].getInt("SpaceShip_Upgrade2", -1);
		spaceship.upgrade[2] = pref[current_profile].getInt("SpaceShip_Upgrade3", -1);
		spaceship.upgrade[3] = pref[current_profile].getInt("SpaceShip_Upgrade4", -1);
		spaceship.upgrade[4] = pref[current_profile].getInt("SpaceShip_Upgrade5", -1);
		spaceship.upgrade[5] = pref[current_profile].getInt("SpaceShip_Upgrade6", -1);
		spaceship.upgrade[6] = pref[current_profile].getInt("SpaceShip_Upgrade7", -1);
		spaceship.upgrade[7] = pref[current_profile].getInt("SpaceShip_Upgrade8", -1);
		spaceship.turret_active = pref[current_profile].getInt("SpaceShip_ActiveTurret", 0);
		spaceship.modifier_active = pref[current_profile].getInt("SpaceShip_ActiveModifier", 0);

		// Shop
		int num = 0;
		String shop = pref[current_profile].getString("Shop_Turrets", "");
		for (int i=0;i<allUpgrades.maxturret;i++) {
			if (shop.length() <= num) break;
			if (Integer.valueOf(shop.substring(num,num+1)) == 1) { allUpgrades.turret[i].bought = true; } else { allUpgrades.turret[i].bought = false; }
			num++;
		}

		num = 0;
		shop = pref[current_profile].getString("Shop_Modifiers", "");
		for (int i=0;i<allUpgrades.maxmodifier;i++) {
			if (shop.length() <= num) break;
			if (Integer.valueOf(shop.substring(num,num+1)) == 1) { allUpgrades.modifier[i].bought = true; } else { allUpgrades.modifier[i].bought = false; }
			num++;
		}

		num = 0;
		shop = pref[current_profile].getString("Shop_Specials", "");
		for (int i=0;i<allUpgrades.maxspecial;i++) {
			if (shop.length() <= num) break;
			if (Integer.valueOf(shop.substring(num,num+1)) == 1) { allUpgrades.special[i].bought = true; } else { allUpgrades.special[i].bought = false; }
			num++;
		}

		num = 0;
		shop = pref[current_profile].getString("Shop_Upgrades", "");
		for (int i=0;i<allUpgrades.maxupgrade;i++) {
			if (shop.length() <= num) break;
			if (Integer.valueOf(shop.substring(num,num+1)) == 1) { allUpgrades.upgrade[i].bought = true; } else { allUpgrades.upgrade[i].bought = false; }
			num++;
		}
		
		num = 0;
		shop = pref[current_profile].getString("Shop_Ships", "");
		for (int i=0;i<allUpgrades.maxship;i++) {
			if (shop.length() <= num) break;
			if (Integer.valueOf(shop.substring(num,num+1)) == 1) { allUpgrades.ship[i].bought = true; } else { allUpgrades.ship[i].bought = false; }
			num++;
			if (allUpgrades.ship[i].bought) allUpgrades.ship[i].show = true;
		}
		
		
		//if (allUpgrades.modifier[8].available || allUpgrades.special[28].available || allUpgrades.special[33].available 
		//		|| allUpgrades.modifier[9].available || allUpgrades.modifier[11].available) liked = true;

		for (int i=0;i<promonum; i++) {
			if (profile_promos[i]) liked = true;
		}
		
		//if (allUpgrades.purchased_alienship) allUpgrades.ship[3].bought = true;
		//if (allUpgrades.purchased_pentafire) allUpgrades.modifier[12].bought = true;
		//if (allUpgrades.purchased_wings) allUpgrades.upgrade[51].bought = true;
		//if (allUpgrades.purchased_hexafire) allUpgrades.modifier[13].bought = true;
		//if (allUpgrades.purchased_primeship) allUpgrades.ship[5].bought = true;
		//if (allUpgrades.purchased_rocket) allUpgrades.special[48].bought = true;
		
		// Player
		LY = pref[current_profile].getLong("LY", 10000);
		// Galaxy
		int update = 0;
		String galaxy = pref[current_profile].getString("Galaxy_Status", "");
		for (int i=0;i<galaxy.length();i++) {
			if ((Integer.valueOf(galaxy.substring(i,i+1)) == 0) && i<menusystem.total_galaxy) menusystem.galaxy[i].status = 0;
			else if ((Integer.valueOf(galaxy.substring(i,i+1)) == 1) && i<menusystem.total_galaxy) menusystem.galaxy[i].status = 1;
			else if ((Integer.valueOf(galaxy.substring(i,i+1)) == 2) && i<menusystem.total_galaxy) {
				menusystem.galaxy[i].status = 2;
				if (menusystem.galaxy[i].update > update) update = menusystem.galaxy[i].update; 
			}
		}
		allUpgrades.update(update,profile_promos);
		// Mission Data
		String mission = pref[current_profile].getString("Mission_Status", "");
		for (int i=0;i<mission.length();i++) {
			if ((Integer.valueOf(mission.substring(i,i+1)) == 0) && i<menusystem.total_mission) menusystem.mission[i].completed = 0;
			else if ((Integer.valueOf(mission.substring(i,i+1)) == 1) && i<menusystem.total_mission) menusystem.mission[i].completed = 1;
			else if ((Integer.valueOf(mission.substring(i,i+1)) == 2) && i<menusystem.total_mission) menusystem.mission[i].completed = 2;
			else if ((Integer.valueOf(mission.substring(i,i+1)) == 3) && i<menusystem.total_mission) menusystem.mission[i].completed = 3;
			else if ((Integer.valueOf(mission.substring(i,i+1)) == 4) && i<menusystem.total_mission) menusystem.mission[i].completed = 4;
		}
		String highscore = pref[current_profile].getString("Mission_Highscore", "");
		if (highscore != "" && highscore.length() > 0) {
			String[] columns = new String[menusystem.total_mission+1];
			columns = highscore.split("\\s+", menusystem.total_mission+1);
			for (int i=0;i<menusystem.total_mission;i++) {
				try {
					menusystem.mission[i].highscore = Integer.parseInt(columns[i]);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}

		// update dialogues accordingly
		menusystem.Update_Dialogues(-1, 0);
		menusystem.Update_Dialogues(-1, 1);
		menusystem.Update_Dialogues(-1, 2);

}

public void Update_Ship_Properties() {
	
		if (allUpgrades.ship[spaceship.type].turret_num < spaceship.turret_num) {
			for (int i=0;i<spaceship.turret_num;i++) spaceship.turret[i] = -2;
		}
		spaceship.turret_num = allUpgrades.ship[spaceship.type].turret_num;
		if (allUpgrades.ship[spaceship.type].modifier_num < spaceship.modifier_num) {
			for (int i=0;i<spaceship.modifier_num;i++) spaceship.modifier[i] = -2;
		}
		spaceship.modifier_num = allUpgrades.ship[spaceship.type].modifier_num;
		if (allUpgrades.ship[spaceship.type].special_num < spaceship.special_num) {
			for (int i=0;i<spaceship.special_num;i++) spaceship.special[i] = -2;
		}
		spaceship.special_num = allUpgrades.ship[spaceship.type].special_num;
		if (allUpgrades.ship[spaceship.type].upgrade_num < spaceship.upgrade_num) {
			for (int i=0;i<spaceship.upgrade_num;i++) spaceship.upgrade[i] = -2;
		}
		spaceship.upgrade_num = allUpgrades.ship[spaceship.type].upgrade_num;

		if (spaceship.turret[spaceship.turret_active] < 0) {
			for (int i=0;i<spaceship.turret_num;i++) {
				if (spaceship.turret[i] >= 0) {
					spaceship.turret_active = i;
					break;
				}
			}
		}
		if (spaceship.modifier[spaceship.modifier_active] < 0) {
			for (int i=0;i<spaceship.modifier_num;i++) {
				if (spaceship.modifier[i] >= 0) {
					spaceship.modifier_active = i;
					break;
				}
			}
		}
		
		spaceship.special_order[0] = 0; spaceship.special_order[1] = 1; spaceship.special_order[2] = 2; spaceship.special_order[3] = 3;  
		//else if (spaceship.type == 1) { spaceship.special_order[0] = 0; spaceship.special_order[1] = 0; spaceship.special_order[2] = 1; spaceship.special_order[3] = 3; }
		//else if (spaceship.type == 2) { spaceship.special_order[0] = 0; spaceship.special_order[1] = 0; spaceship.special_order[2] = 1; spaceship.special_order[3] = 3; }
		
		spaceship.hp_max = allUpgrades.ship[spaceship.type].hp_base;
		spaceship.shield_max = allUpgrades.ship[spaceship.type].shield_base;
		spaceship.speed = allUpgrades.ship[spaceship.type].speed_base;
		spaceship.damagemod = 0;
		spaceship.lymod = 1.0f;
		spaceship.rofmod = 0;
		spaceship.cdmod = 1.0f;
		spaceship.collisionmod = 1.0f;
		spaceship.bulletspeedmod = 0.0f;
		spaceship.dropchancemod = 0.0f;
		spaceship.lypickupmod = 1.0f;
		spaceship.crit = 0;
		spaceship.winged = false;
		spaceship.shield_recharge = allUpgrades.ship[spaceship.type].shield_recharge_base;
		spaceship.repair = 0.0f;
		for (int i=0;i<spaceship.upgrade_num;i++) {
			if (spaceship.upgrade[i] >= 0) {
				spaceship.hp_max += allUpgrades.upgrade[spaceship.upgrade[i]].hp;
				spaceship.shield_max += allUpgrades.upgrade[spaceship.upgrade[i]].shield;
				spaceship.speed += allUpgrades.upgrade[spaceship.upgrade[i]].speed;
				spaceship.damagemod += allUpgrades.upgrade[spaceship.upgrade[i]].damage;
				spaceship.lymod *= allUpgrades.upgrade[spaceship.upgrade[i]].lymod;
				spaceship.rofmod += allUpgrades.upgrade[spaceship.upgrade[i]].rof;
				spaceship.cdmod *= allUpgrades.upgrade[spaceship.upgrade[i]].cdmod;
				spaceship.dropchancemod += allUpgrades.upgrade[spaceship.upgrade[i]].dropchance;
				spaceship.bulletspeedmod += allUpgrades.upgrade[spaceship.upgrade[i]].bulletspeed;
				spaceship.collisionmod *= allUpgrades.upgrade[spaceship.upgrade[i]].collisionmod;
				spaceship.lypickupmod *= allUpgrades.upgrade[spaceship.upgrade[i]].lypickupmod;
				spaceship.crit += allUpgrades.upgrade[spaceship.upgrade[i]].crit;
				spaceship.shield_recharge += allUpgrades.upgrade[spaceship.upgrade[i]].recharge;
				spaceship.repair += allUpgrades.upgrade[spaceship.upgrade[i]].repair;
				if (spaceship.upgrade[i] == 51) spaceship.winged = true; 
			}
		}
		spaceship.hp = spaceship.hp_max;
		spaceship.shield = spaceship.shield_max;

		// GOOGLE GAME SERVICES
		pref[0].edit().putInt("LY_Spent", lyspent).commit();
		if (lyspent >= 500000) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_500KSPENT);
			}
			if (!got_achievement[13]) {
				got_achievement[13] = true; pref[0].edit().putBoolean(got_achievement_id[13], true);
				achievement_anim = true;
			}
		}
		int level3 = 0;
		for (int i=0; i<allUpgrades.maxturret; i++) if (allUpgrades.turret[i].bought && allUpgrades.turret[i].followup == -1) level3++;
		for (int i=0; i<allUpgrades.maxspecial; i++) if (allUpgrades.special[i].bought && allUpgrades.special[i].followup == -1) level3++;
		for (int i=0; i<allUpgrades.maxupgrade; i++) if (allUpgrades.upgrade[i].bought && allUpgrades.upgrade[i].followup == -1) level3++;
		if (level3 >= 5) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_5LEVEL3);
			}
			if (!got_achievement[14]) {
				got_achievement[14] = true; pref[0].edit().putBoolean(got_achievement_id[14], true);
				achievement_anim = true;
			}
		}
		
		Save_Profile(0);
	}

	//public void Spaceship_bullet_shoot(GL10 gl, boolean isturret) {
	//Spaceship_bullet_shoot(gl, isturret, 0, 0, 0, 0, -1, false);
	//}


	// Verifier class error fix - túl hosszú class részekre bontása???
	//public void Spaceship_bullet_shoot(GL10 gl, boolean isturret, int bullet_type, int shoot_type, int bullet_damage, float bullet_speed, int bullet_sound, boolean unstoppable) {
	public void Spaceship_bullet_shoot(boolean isturret) {
		
		int bullet_type = 0;
		int shoot_type = 0; 
		int bullet_damage = 0;
		float bullet_speed = 0;
		int bullet_sound = -1;
		boolean unstoppable = false;
		
		int random = (int)Math.ceil(Math.random()*100);
		Bullet bullet_;
		int dmod = 1;
		
    	if (isturret) {
    		if (spaceship.crit > 0 && spaceship.crit > random) dmod = 2;
    		bullet_damage = dmod* allUpgrades.turret[spaceship.turret[spaceship.turret_active]].damage + (int)spaceship.damagemod;
    		bullet_type = allUpgrades.turret[spaceship.turret[spaceship.turret_active]].bullet;
    		shoot_type = spaceship.modifier[spaceship.modifier_active];
    		bullet_speed = allUpgrades.turret[spaceship.turret[spaceship.turret_active]].bulletspeed + spaceship.bulletspeedmod;
    		if (allUpgrades.turret[spaceship.turret[spaceship.turret_active]].unstoppable) unstoppable = true;
    		if (allUpgrades.turret[spaceship.turret[spaceship.turret_active]].sound > 0) bullet_sound = allUpgrades.turret[spaceship.turret[spaceship.turret_active]].sound;
        	spaceship_bullet_launch = false;
    	}
		if (sound) {
			if (bullet_sound >= 0) {
				SoundManager.playSound(bullet_sound, 1);
			}
		} 

		switch(shoot_type) {
		
			// Single fire
			case -1: 
				bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = 0.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[0][0] - (int)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[0][1] - (int)allBullets.bullet[bullet_.type].height/2;
		    	bullet_.xmove = 0;
		    	bullet_.ymove = tmod * (bullet_speed);
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
				break;

			// Dual Fire
			case 0:
				// Bullet 1
				bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = 0.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (int)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (int)allBullets.bullet[bullet_.type].height/2;
		    	bullet_.xmove = 0;
		    	bullet_.ymove = tmod * (bullet_speed);
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
		    	// Bullet 2
		    	bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = 0.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (int)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (int)allBullets.bullet[bullet_.type].height/2;
		    	bullet_.xmove = 0;
		    	bullet_.ymove = tmod * (bullet_speed);
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
		    	break;
				
			// Triple W
			case 1: 
				// Bullet 1
				bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = 0.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[0][0] - (int)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[0][1] - (int)allBullets.bullet[bullet_.type].height/2;
		    	bullet_.xmove = 0;
		    	bullet_.ymove = tmod * (bullet_speed);
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
				// Bullet 2
				bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = -8.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[3][0] - (float)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[3][1] - (float)allBullets.bullet[bullet_.type].height/2;
		    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width + 0.25f*allBullets.bullet[bullet_.type].height);
		    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height - 0.25f*allBullets.bullet[bullet_.type].width);
		    	bullet_.xmove = - (0.1391f)* tmod * (bullet_speed);
		    	bullet_.ymove = (0.9993f)* tmod * (bullet_speed);//
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
		    	// Bullet 3
		    	bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = 8.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[4][0] - (float)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[4][1] - (float)allBullets.bullet[bullet_.type].height/2;
		    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width - 0.25f*allBullets.bullet[bullet_.type].height);
		    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height + 0.25f*allBullets.bullet[bullet_.type].width);
		    	bullet_.xmove = (0.1391f)* tmod * (bullet_speed);
		    	bullet_.ymove = (0.9993f)* tmod * (bullet_speed);
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
				break;					

			// Quad IVI
			case 2: 
				// Bullet 1
				bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = 0.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (int)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (int)allBullets.bullet[bullet_.type].height/2;
		    	bullet_.xmove = 0;
		    	bullet_.ymove = tmod * (bullet_speed);
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
		    	// Bullet 2
		    	bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = 0.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (int)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (int)allBullets.bullet[bullet_.type].height/2;
		    	bullet_.xmove = 0;
		    	bullet_.ymove = tmod * (bullet_speed);
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
				// Bullet 3
				bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = -15.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[3][0] - (float)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[3][1] - (float)allBullets.bullet[bullet_.type].height/2;
		    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width + 0.25f*allBullets.bullet[bullet_.type].height);
		    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height - 0.25f*allBullets.bullet[bullet_.type].width);
		    	bullet_.xmove = - (0.2588f)* tmod * (bullet_speed);
		    	bullet_.ymove = (0.9659f)* tmod * (bullet_speed);//
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
		    	// Bullet 4
		    	bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = 15.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[4][0] - (float)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[4][1] - (float)allBullets.bullet[bullet_.type].height/2;
		    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width - 0.25f*allBullets.bullet[bullet_.type].height);
		    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height + 0.25f*allBullets.bullet[bullet_.type].width);
		    	bullet_.xmove = (0.2588f)* tmod * (bullet_speed);
		    	bullet_.ymove = (0.9659f)* tmod * (bullet_speed);
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
				break;

			// Penta EV
			case 3: 
				// Bullet 1
				bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = 0.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[0][0] - (int)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[0][1] - (int)allBullets.bullet[bullet_.type].height/2;
		    	bullet_.xmove = 0;
		    	bullet_.ymove = tmod * (bullet_speed);
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
				// Bullet 2
				bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = 0.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[3][0] - (int)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[3][1] - (int)allBullets.bullet[bullet_.type].height/2;
		    	bullet_.xmove = 0;
		    	bullet_.ymove = tmod * (bullet_speed);
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
		    	// Bullet 3
		    	bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = 0.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[4][0] - (int)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[4][1] - (int)allBullets.bullet[bullet_.type].height/2;
		    	bullet_.xmove = 0;
		    	bullet_.ymove = tmod * (bullet_speed);
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
				// Bullet 4
				bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = -15.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[3][0] - (float)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[3][1] - (float)allBullets.bullet[bullet_.type].height/2;
		    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width + 0.25f*allBullets.bullet[bullet_.type].height);
		    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height - 0.25f*allBullets.bullet[bullet_.type].width);
		    	bullet_.xmove = - (0.2588f)* tmod * (bullet_speed);
		    	bullet_.ymove = (0.9659f)* tmod * (bullet_speed);//
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
		    	// Bullet 5
		    	bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = bullet_type;
		    	bullet_.damage = bullet_damage;
		    	bullet_.rotation = 15.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[4][0] - (float)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[4][1] - (float)allBullets.bullet[bullet_.type].height/2;
		    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width - 0.25f*allBullets.bullet[bullet_.type].height);
		    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height + 0.25f*allBullets.bullet[bullet_.type].width);
		    	bullet_.xmove = (0.2588f)* tmod * (bullet_speed);
		    	bullet_.ymove = (0.9659f)* tmod * (bullet_speed);
		    	bullet_.unstoppable = unstoppable;
		    	spaceship_bullet.add(bullet_);
				break;

				// 7 
				case 4: 
					// Bullet 1
					bullet_ = new Bullet();
					bullet_.active = true;
					bullet_.type = bullet_type;
			    	bullet_.damage = bullet_damage;
			    	bullet_.rotation = 0.0f;
			    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[0][0] - (int)allBullets.bullet[bullet_.type].width/2;
			    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[0][1] - (int)allBullets.bullet[bullet_.type].height/2;
			    	bullet_.xmove = 0;
			    	bullet_.ymove = tmod * (bullet_speed);
			    	bullet_.unstoppable = unstoppable;
			    	spaceship_bullet.add(bullet_);
					// Bullet 2
					bullet_ = new Bullet();
					bullet_.active = true;
					bullet_.type = bullet_type;
			    	bullet_.damage = bullet_damage;
			    	bullet_.rotation = 0.0f;
			    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[3][0] - (int)allBullets.bullet[bullet_.type].width/2;
			    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[3][1] - (int)allBullets.bullet[bullet_.type].height/2;
			    	bullet_.xmove = 0;
			    	bullet_.ymove = tmod * (bullet_speed);
			    	bullet_.unstoppable = unstoppable;
			    	spaceship_bullet.add(bullet_);
			    	// Bullet 3
			    	bullet_ = new Bullet();
					bullet_.active = true;
					bullet_.type = bullet_type;
			    	bullet_.damage = bullet_damage;
			    	bullet_.rotation = 0.0f;
			    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[4][0] - (int)allBullets.bullet[bullet_.type].width/2;
			    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[4][1] - (int)allBullets.bullet[bullet_.type].height/2;
			    	bullet_.xmove = 0;
			    	bullet_.ymove = tmod * (bullet_speed);
			    	bullet_.unstoppable = unstoppable;
			    	spaceship_bullet.add(bullet_);
					// Bullet 4
					bullet_ = new Bullet();
					bullet_.active = true;
					bullet_.type = bullet_type;
			    	bullet_.damage = bullet_damage;
			    	bullet_.rotation = -30.0f;
			    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (float)allBullets.bullet[bullet_.type].width/2;
			    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (float)allBullets.bullet[bullet_.type].height/2;
			    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width + 0.25f*allBullets.bullet[bullet_.type].height);
			    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height - 0.25f*allBullets.bullet[bullet_.type].width);
			    	bullet_.xmove = - (0.5f)* tmod * (bullet_speed);
			    	bullet_.ymove = (0.866f)* tmod * (bullet_speed);
			    	bullet_.unstoppable = unstoppable;
			    	spaceship_bullet.add(bullet_);
			    	// Bullet 5
			    	bullet_ = new Bullet();
					bullet_.active = true;
					bullet_.type = bullet_type;
			    	bullet_.damage = bullet_damage;
			    	bullet_.rotation = 30.0f;
			    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (float)allBullets.bullet[bullet_.type].width/2;
			    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (float)allBullets.bullet[bullet_.type].height/2;
			    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width - 0.25f*allBullets.bullet[bullet_.type].height);
			    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height + 0.25f*allBullets.bullet[bullet_.type].width);
			    	bullet_.xmove = (0.5f)* tmod * (bullet_speed);
			    	bullet_.ymove = (0.866f)* tmod * (bullet_speed);
			    	bullet_.unstoppable = unstoppable;
			    	spaceship_bullet.add(bullet_);
					// Bullet 6
					bullet_ = new Bullet();
					bullet_.active = true;
					bullet_.type = bullet_type;
			    	bullet_.damage = bullet_damage;
			    	bullet_.rotation = -15.0f;
			    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (float)allBullets.bullet[bullet_.type].width/2;
			    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (float)allBullets.bullet[bullet_.type].height/2;
			    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width + 0.25f*allBullets.bullet[bullet_.type].height);
			    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height - 0.25f*allBullets.bullet[bullet_.type].width);
			    	bullet_.xmove = - (0.2588f)* tmod * (bullet_speed);
			    	bullet_.ymove = (0.9659f)* tmod * (bullet_speed);
			    	bullet_.unstoppable = unstoppable;
			    	spaceship_bullet.add(bullet_);
			    	// Bullet 7
			    	bullet_ = new Bullet();
					bullet_.active = true;
					bullet_.type = bullet_type;
			    	bullet_.damage = bullet_damage;
			    	bullet_.rotation = 15.0f;
			    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (float)allBullets.bullet[bullet_.type].width/2;
			    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (float)allBullets.bullet[bullet_.type].height/2;
			    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width - 0.25f*allBullets.bullet[bullet_.type].height);
			    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height + 0.25f*allBullets.bullet[bullet_.type].width);
			    	bullet_.xmove = (0.2588f)* tmod * (bullet_speed);
			    	bullet_.ymove = (0.9659f)* tmod * (bullet_speed);
			    	bullet_.unstoppable = unstoppable;
			    	spaceship_bullet.add(bullet_);
					break;

					// 9 
					case 5: 
						// Bullet 1
						bullet_ = new Bullet();
						bullet_.active = true;
						bullet_.type = bullet_type;
				    	bullet_.damage = bullet_damage;
				    	bullet_.rotation = 0.0f;
				    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[0][0] - (int)allBullets.bullet[bullet_.type].width/2;
				    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[0][1] - (int)allBullets.bullet[bullet_.type].height/2;
				    	bullet_.xmove = 0;
				    	bullet_.ymove = tmod * (bullet_speed);
				    	bullet_.unstoppable = unstoppable;
//				    	/*NEW*/	    	bullet_.isparticle = allBullets.bullet[bullet_.type].isparticle;
//				    	/*NEW*/	    	if (particles && bullet_.isparticle) bullet_.particlesystem = new BulletParticle();
				    	spaceship_bullet.add(bullet_);
						// Bullet 2
						bullet_ = new Bullet();
						bullet_.active = true;
						bullet_.type = bullet_type;
				    	bullet_.damage = bullet_damage;
				    	bullet_.rotation = 0.0f;
				    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[3][0] - (int)allBullets.bullet[bullet_.type].width/2;
				    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[3][1] - (int)allBullets.bullet[bullet_.type].height/2;
				    	bullet_.xmove = 0;
				    	bullet_.ymove = tmod * (bullet_speed);
				    	bullet_.unstoppable = unstoppable;
//				    	/*NEW*/	    	bullet_.isparticle = allBullets.bullet[bullet_.type].isparticle;
//				    	/*NEW*/	    	if (particles && bullet_.isparticle) bullet_.particlesystem = new BulletParticle();
				    	spaceship_bullet.add(bullet_);
				    	// Bullet 3
				    	bullet_ = new Bullet();
						bullet_.active = true;
						bullet_.type = bullet_type;
				    	bullet_.damage = bullet_damage;
				    	bullet_.rotation = 0.0f;
				    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[4][0] - (int)allBullets.bullet[bullet_.type].width/2;
				    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[4][1] - (int)allBullets.bullet[bullet_.type].height/2;
				    	bullet_.xmove = 0;
				    	bullet_.ymove = tmod * (bullet_speed);
				    	bullet_.unstoppable = unstoppable;
//				    	/*NEW*/	    	bullet_.isparticle = allBullets.bullet[bullet_.type].isparticle;
//				    	/*NEW*/	    	if (particles && bullet_.isparticle) bullet_.particlesystem = new BulletParticle();
				    	spaceship_bullet.add(bullet_);
						// Bullet 4
						bullet_ = new Bullet();
						bullet_.active = true;
						bullet_.type = bullet_type;
				    	bullet_.damage = bullet_damage;
				    	bullet_.rotation = -30.0f;
				    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (float)allBullets.bullet[bullet_.type].width/2;
				    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (float)allBullets.bullet[bullet_.type].height/2;
				    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width + 0.25f*allBullets.bullet[bullet_.type].height);
				    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height - 0.25f*allBullets.bullet[bullet_.type].width);
				    	bullet_.xmove = - (0.5f)* tmod * (bullet_speed);
				    	bullet_.ymove = (0.866f)* tmod * (bullet_speed);
				    	bullet_.unstoppable = unstoppable;
				    	/*NEW*/	    	//bullet_.isparticle = allBullets.bullet[bullet_.type].isparticle;
				    	/*NEW*/	    	//if (particles && bullet_.isparticle) bullet_.particlesystem = new BulletParticle();
				    	spaceship_bullet.add(bullet_);
				    	// Bullet 5
				    	bullet_ = new Bullet();
						bullet_.active = true;
						bullet_.type = bullet_type;
				    	bullet_.damage = bullet_damage;
				    	bullet_.rotation = 30.0f;
				    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (float)allBullets.bullet[bullet_.type].width/2;
				    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (float)allBullets.bullet[bullet_.type].height/2;
				    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width - 0.25f*allBullets.bullet[bullet_.type].height);
				    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height + 0.25f*allBullets.bullet[bullet_.type].width);
				    	bullet_.xmove = (0.5f)* tmod * (bullet_speed);
				    	bullet_.ymove = (0.866f)* tmod * (bullet_speed);
				    	bullet_.unstoppable = unstoppable;
				    	/*NEW*/	    	//bullet_.isparticle = allBullets.bullet[bullet_.type].isparticle;
				    	/*NEW*/	    	//if (particles && bullet_.isparticle) bullet_.particlesystem = new BulletParticle();
				    	spaceship_bullet.add(bullet_);
						// Bullet 6
						bullet_ = new Bullet();
						bullet_.active = true;
						bullet_.type = bullet_type;
				    	bullet_.damage = bullet_damage;
				    	bullet_.rotation = -15.0f;
				    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (float)allBullets.bullet[bullet_.type].width/2;
				    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (float)allBullets.bullet[bullet_.type].height/2;
				    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width + 0.25f*allBullets.bullet[bullet_.type].height);
				    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height - 0.25f*allBullets.bullet[bullet_.type].width);
				    	bullet_.xmove = - (0.2588f)* tmod * (bullet_speed);
				    	bullet_.ymove = (0.9659f)* tmod * (bullet_speed);
				    	bullet_.unstoppable = unstoppable;
				    	/*NEW*/	    	//bullet_.isparticle = allBullets.bullet[bullet_.type].isparticle;
				    	/*NEW*/	    	//if (particles && bullet_.isparticle) bullet_.particlesystem = new BulletParticle();
				    	spaceship_bullet.add(bullet_);
				    	// Bullet 7
				    	bullet_ = new Bullet();
						bullet_.active = true;
						bullet_.type = bullet_type;
				    	bullet_.damage = bullet_damage;
				    	bullet_.rotation = 15.0f;
				    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (float)allBullets.bullet[bullet_.type].width/2;
				    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (float)allBullets.bullet[bullet_.type].height/2;
				    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width - 0.25f*allBullets.bullet[bullet_.type].height);
				    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height + 0.25f*allBullets.bullet[bullet_.type].width);
				    	bullet_.xmove = (0.2588f)* tmod * (bullet_speed);
				    	bullet_.ymove = (0.9659f)* tmod * (bullet_speed);
				    	bullet_.unstoppable = unstoppable;
				    	/*NEW*/	    	//bullet_.isparticle = allBullets.bullet[bullet_.type].isparticle;
				    	/*NEW*/	    	//if (particles && bullet_.isparticle) bullet_.particlesystem = new BulletParticle();
				    	spaceship_bullet.add(bullet_);
						// Bullet 8
						bullet_ = new Bullet();
						bullet_.active = true;
						bullet_.type = bullet_type;
				    	bullet_.damage = bullet_damage;
				    	bullet_.rotation = -60.0f;
				    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[3][0] - (float)allBullets.bullet[bullet_.type].width/2;
				    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[3][1] - (float)allBullets.bullet[bullet_.type].height/2;
				    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width + 0.25f*allBullets.bullet[bullet_.type].height);
				    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height - 0.25f*allBullets.bullet[bullet_.type].width);
				    	bullet_.xmove = - (0.866f)* tmod * (bullet_speed);
				    	bullet_.ymove = (0.5f)* tmod * (bullet_speed);//
				    	bullet_.unstoppable = unstoppable;
				    	/*NEW*/	    	//bullet_.isparticle = allBullets.bullet[bullet_.type].isparticle;
				    	/*NEW*/	    	//if (particles && bullet_.isparticle) bullet_.particlesystem = new BulletParticle();
				    	spaceship_bullet.add(bullet_);
				    	// Bullet 9
				    	bullet_ = new Bullet();
						bullet_.active = true;
						bullet_.type = bullet_type;
				    	bullet_.damage = bullet_damage;
				    	bullet_.rotation = 60.0f;
				    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[4][0] - (float)allBullets.bullet[bullet_.type].width/2;
				    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[4][1] - (float)allBullets.bullet[bullet_.type].height/2;
				    	//bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (int)(0.433f*allBullets.bullet[bullet_.type].width - 0.25f*allBullets.bullet[bullet_.type].height);
				    	//bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (int)(0.433f*allBullets.bullet[bullet_.type].height + 0.25f*allBullets.bullet[bullet_.type].width);
				    	bullet_.xmove = (0.866f)* tmod * (bullet_speed);
				    	bullet_.ymove = (0.5f)* tmod * (bullet_speed);
				    	bullet_.unstoppable = unstoppable;
				    	/*NEW*/	    	//bullet_.isparticle = allBullets.bullet[bullet_.type].isparticle;
				    	/*NEW*/	    	//if (particles && bullet_.isparticle) bullet_.particlesystem = new BulletParticle();
				    	spaceship_bullet.add(bullet_);
						break;					
 



				
			default: 

				break;	
		}
		
    	
	}

	// Verifier class error fix - túl hosszú class részekre bontása???
	public void Wing_bullet_shoot() {
		Bullet bullet_;
		
		int bbullet = allUpgrades.turret[spaceship.turret[spaceship.turret_active]].bullet;
		int ddamage = (allUpgrades.turret[spaceship.turret[spaceship.turret_active]].damage + (int)spaceship.damagemod)/2;
		float bspeed = tmod * (allUpgrades.turret[spaceship.turret[spaceship.turret_active]].bulletspeed + spaceship.bulletspeedmod);
		int ssound = allUpgrades.turret[spaceship.turret[spaceship.turret_active]].sound;
		
		if (spaceship.winged) {
			// Bullet 1
			bullet_ = new Bullet();
			bullet_.active = true;
			bullet_.type = bbullet;
	    	bullet_.damage = ddamage;
	    	bullet_.rotation = 0.0f;
	    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].wing_oo[0][0] + (int)(hd*30/2) - (int)allBullets.bullet[bullet_.type].width/2;
	    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].wing_oo[0][1] - (int)allBullets.bullet[bullet_.type].height/2;
	    	bullet_.xmove = 0;
	    	bullet_.ymove = bspeed;
	    	spaceship_bullet.add(bullet_);
	    	// Bullet 2
			bullet_ = new Bullet();
			bullet_.active = true;
			bullet_.type = bbullet;
	    	bullet_.damage = ddamage;
	    	bullet_.rotation = 0.0f;
	    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].wing_oo[1][0] + (int)(hd*30/2) - (int)allBullets.bullet[bullet_.type].width/2;
	    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].wing_oo[1][1] - (int)allBullets.bullet[bullet_.type].height/2;
	    	bullet_.xmove = 0;
	    	bullet_.ymove = bspeed;
	    	spaceship_bullet.add(bullet_);
		}
		if (wings) {
			int wingmod = 0;
			if (spaceship.winged) wingmod = (int)(hd*45); 
			// Bullet 1
			bullet_ = new Bullet();
			bullet_.active = true;
			bullet_.type = bbullet;
	    	bullet_.damage = ddamage;
	    	bullet_.rotation = 0.0f;
	    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].wing_oo[0][0] + (int)(hd*30/2) -wingmod - (int)allBullets.bullet[bullet_.type].width/2;
	    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].wing_oo[0][1] - (int)allBullets.bullet[bullet_.type].height/2;
	    	bullet_.xmove = 0;
	    	bullet_.ymove = bspeed;
	    	spaceship_bullet.add(bullet_);
	    	// Bullet 2
			bullet_ = new Bullet();
			bullet_.active = true;
			bullet_.type = bbullet;
	    	bullet_.damage = ddamage;
	    	bullet_.rotation = 0.0f;
	    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].wing_oo[1][0] + (int)(hd*30/2) +wingmod - (int)allBullets.bullet[bullet_.type].width/2;
	    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].wing_oo[1][1] - (int)allBullets.bullet[bullet_.type].height/2;
	    	bullet_.xmove = 0;
	    	bullet_.ymove = bspeed;
	    	spaceship_bullet.add(bullet_);
	}
		
		//if (sound) {
			// gatling sound
		//	if (ssound > 0) {
		//		SoundManager.playSound(ssound, 1);
		//	}
		//} 
    	wing_bullet_launch = false;

    	
	}

	
	// Verifier class error fix - túl hosszú class részekre bontása???
	public void Spaceship_special_shoot(GL10 gl) {
		boolean fired = false;
		
		boolean pickup_request = false;
		// ha pickupbol jott a request
		if (spaceship_special_launch < 0) { pickup_request = true; spaceship_special_launch = pickup_special_launch; }
		
		switch(spaceship_special_launch) {

			// Mining laser
			case 0 :
			case 1 :
			case 2 :
				if (!mining) {
					//boolean npc_found = false;
					for(Entity npc_: npc){
						if (npc_.y < (int)(hd*700) && allEntities.npc[npc_.type].mineable) {
							mining = true;
							npc_.mining = true;
							mining_timer = time_current + allUpgrades.special[spaceship_special_launch].duration*1000;
							//npc_found = true;
							break;
						}
					}
					fired = true;
					//if (!npc_found) {
					//	for (int i=0;i<spaceship.special_num;i++) {
					//		if (spaceship.special[i] == spaceship_special_launch) {
					//			spaceship.special_cd_timer[i] = (long)(time_current - spaceship.cdmod*allUpgrades.special[spaceship.special[i]].cooldown  * 1000 * game_timer_fix + 1000);
					//			break;
					//		}
					//	}
					//}
				} else {
					for (int i=0;i<spaceship.special_num;i++) {
						if (spaceship.special[i] == spaceship_special_launch) {
							spaceship.special_cd_timer[i] = (long)(time_current - spaceship.cdmod*allUpgrades.special[spaceship.special[i]].cooldown  * 1000 * game_timer_fix + 1000);
							break;
						}
					}
				}
				if (!pickup_request) { if (!spec_used[0]) { spec_used[0] = true; pref[0].edit().putBoolean(spec_used_id[0], true); } };
				break;
			// Speed booster
			case 3 :
				booster = true;
				booster_value = 0.25f;
				booster_timer = time_current + allUpgrades.special[spaceship_special_launch].duration*1000;
				fired = true;
				break;
			// Instant shield
			case 4 :
			case 5 :
			case 6 :
				if (!instantshield) {
					instantshield = true;
					spaceship.shield_max += 10;
					spaceship.shield += 10;
					instantshield_timer = time_current + allUpgrades.special[spaceship_special_launch].duration*1000;
					//instantshield_recharge_timer = time_current;
					fired = true;
				} else {
					// ha mar fent van, csak az idotartam no
					spaceship.shield += 10; if (spaceship.shield > spaceship.shield_max) spaceship.shield = spaceship.shield_max;
					instantshield_timer = time_current + allUpgrades.special[spaceship_special_launch].duration*1000;
					fired = true;
				}
				if (!pickup_request) { if (!spec_used[1]) { spec_used[1] = true; pref[0].edit().putBoolean(spec_used_id[1], true); } };

				
					//else {
					//for (int i=0;i<spaceship.special_num;i++) {
					//	if (spaceship.special[i] == spaceship_special_launch) {
					//		spaceship.special_cd_timer[i] = (long)(time_current - spaceship.cdmod*allUpgrades.special[spaceship.special[i]].cooldown  * 1000 * game_timer_fix + 1000);
					//		break;
					//	}
				//	}
				//}
				break;
			// Photon Torpedo
			case 7 :
			case 8 :
			case 9 :
				//Spaceship_bullet_shoot(gl, false, allUpgrades.special[spaceship_special_launch].bullet, 3, allUpgrades.special[spaceship_special_launch].damage + (int)spaceship.damagemod,
				//					allUpgrades.special[spaceship_special_launch].speed, -1, true);
				
				Bullet bullet_;
				
				bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = allUpgrades.special[spaceship_special_launch].bullet;
		    	bullet_.damage = allUpgrades.special[spaceship_special_launch].damage + (int)spaceship.damagemod;
		    	bullet_.rotation = 0.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[0][0] - (int)allBullets.bullet[bullet_.type].width/2;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[0][1] - (int)allBullets.bullet[bullet_.type].height/2;
		    	bullet_.xmove = 0;
		    	bullet_.ymove = tmod * allUpgrades.special[spaceship_special_launch].speed;
		    	spaceship_bullet.add(bullet_);

				bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = allUpgrades.special[spaceship_special_launch].bullet;
		    	bullet_.damage = allUpgrades.special[spaceship_special_launch].damage + (int)spaceship.damagemod;
		    	bullet_.rotation = 0.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[1][0] - (int)allBullets.bullet[bullet_.type].width/2 - allBullets.bullet[bullet_.type].width;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[1][1] - (int)allBullets.bullet[bullet_.type].height/2;
		    	bullet_.xmove = 0;
		    	bullet_.ymove = tmod * allUpgrades.special[spaceship_special_launch].speed;
		    	spaceship_bullet.add(bullet_);

				bullet_ = new Bullet();
				bullet_.active = true;
				bullet_.type = allUpgrades.special[spaceship_special_launch].bullet;
		    	bullet_.damage = allUpgrades.special[spaceship_special_launch].damage + (int)spaceship.damagemod;
		    	bullet_.rotation = 0.0f;
		    	bullet_.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[2][0] - (int)allBullets.bullet[bullet_.type].width/2 + allBullets.bullet[bullet_.type].width;
		    	bullet_.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[2][1] - (int)allBullets.bullet[bullet_.type].height/2;
		    	bullet_.xmove = 0;
		    	bullet_.ymove = tmod * allUpgrades.special[spaceship_special_launch].speed;
		    	spaceship_bullet.add(bullet_);

		    	fired = true;
				if (!pickup_request) { if (!spec_used[2]) { spec_used[2] = true; pref[0].edit().putBoolean(spec_used_id[2], true); } };

				break;
		    // Freeze enemies
			case 10 :
			case 11 :
			case 12 :
				freeze = true;
				freeze_timer = time_current + allUpgrades.special[spaceship_special_launch].duration;
				mission_script_timer += allUpgrades.special[spaceship_special_launch].duration;
				fired = true;
				for(Entity npc_: npc) {
					if (allEntities.npc[npc_.type].frozable) {
						npc_.bullet_timer += allUpgrades.special[spaceship_special_launch].duration;
						npc_.disruptor_timer += allUpgrades.special[spaceship_special_launch].duration;
					}
		  		}
				if (!pickup_request) { if (!spec_used[3]) { spec_used[3] = true; pref[0].edit().putBoolean(spec_used_id[3], true); } };

				break;
			// Homing missile	
			case 13 :
			case 14 :
			case 15 :
				if (!homing_bullet.active) {
					homing = true;
					homing_bullet.active = true;
					homing_bullet.damage = allUpgrades.special[spaceship_special_launch].damage;
					homing_bullet.rotation = 0.0f;
					homing_bullet.type = 9;
					homing_bullet.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[0][0] - (int)allBullets.bullet[homing_bullet.type].width/2;
					homing_bullet.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[0][1] - (int)allBullets.bullet[homing_bullet.type].height/2;
					homing_bullet.xmove = 0;
					homing_bullet.ymove = 0;
					fired = true;
				} else {
					for (int i=0;i<spaceship.special_num;i++) {
						if (spaceship.special[i] == spaceship_special_launch) {
							spaceship.special_cd_timer[i] = (long)(time_current - spaceship.cdmod*allUpgrades.special[spaceship.special[i]].cooldown  * 1000 * game_timer_fix + 1000);
							break;
						}
					}
				}
				if (!pickup_request) { if (!spec_used[4]) { spec_used[4] = true; pref[0].edit().putBoolean(spec_used_id[4], true); } };

				break;
			// Gravity
			case 16 :
			case 17 :
			case 18 :
				gravity = true;
				gravity_timer = time_current + allUpgrades.special[spaceship_special_launch].duration;
				freeze = true;
				fired = true;
				for(Entity npc_: npc) {
					if (allEntities.npc[npc_.type].gravitable) {
						npc_.bullet_timer += allUpgrades.special[spaceship_special_launch].duration;
						npc_.disruptor_timer += allUpgrades.special[spaceship_special_launch].duration;
					}
		  		}
				if (!pickup_request) { if (!spec_used[5]) { spec_used[5] = true; pref[0].edit().putBoolean(spec_used_id[5], true); } };

				break;	
			// Cone Damage
			case 19 :
			case 20 :
			case 21 :
				cone = true;
				cone_timer = time_current + allUpgrades.special[spaceship_special_launch].duration;
				
				cone_xy[0] = (int)(spaceship.x + (float)allUpgrades.ship[spaceship.type].width/2 - (float)allBullets.bullet[13].width/2);
				cone_xy[1] = (int)(spaceship.y + (float)allUpgrades.ship[spaceship.type].height/2 - (float)allBullets.bullet[13].height);
				
				fired = true;
				for(Entity npc_: npc){
					if (npc_.hp > 0) {
						if (npc_.x + allEntities.npc[npc_.type].width > spaceship.x + allUpgrades.ship[spaceship.type].width / 2 - allUpgrades.special[spaceship_special_launch].range / 2
							&& npc_.x < spaceship.x + allUpgrades.ship[spaceship.type].width / 2 + allUpgrades.special[spaceship_special_launch].range / 2
							&& npc_.y + allEntities.npc[npc_.type].height < spaceship.y) {
							npc_.hit_timer = 1;
							npc_.hit_timer_hp = 1;
							// ha van az npc-nek pajzsa is
							if (npc_.shield > 0) { 
								npc_.shield -= allUpgrades.special[spaceship_special_launch].damage;
								if (npc_.shield < 0) {
									npc_.hp += npc_.shield; npc_.shield = 0;
								}
							// ha nincs pajzs
							} else {
								npc_.hp -= allUpgrades.special[spaceship_special_launch].damage;
							}
							// ha elfogyott a hp-ja
							if (npc_.hp <= 0) { 
								npc_.hp = 0; 
								npc_.isalive = false;
								special_kill++;
								total_kill++;
								
								if (sound) {
									if (allEntities.npc[npc_.type].die_sound > 0) {
										SoundManager.playSound(allEntities.npc[npc_.type].die_sound, 1);
									}
								}
								if (vibration) {
									if (allEntities.npc[npc_.type].vibration >= 0) {
										vibrator.vibrate(vibration_pattern[allEntities.npc[npc_.type].vibration]);
									}
								}
								npc_.drop = true;
								float difmod = 1f; if (difficulty == 0) difmod = 0.5f; else if (difficulty == 2) difmod = 1.5f; 
								missionLY += allEntities.npc[npc_.type].lyvalue * ly_szorzo * spaceship.lymod * difmod;
								//missionLY += (allEntities.npc[npc_.type].shield_max+allEntities.npc[npc_.type].hp_max) * ly_szorzo * spaceship.lymod * difmod;
								
							}
						}
					}
				}
				if (!pickup_request) { if (!spec_used[6]) { spec_used[6] = true; pref[0].edit().putBoolean(spec_used_id[6], true); } };

				break;	
			// Auto-Fire
			case 22 :
			case 23 :
				autofire = true;
				autofire_timer = time_current + allUpgrades.special[spaceship_special_launch].duration;
				fired = true;
				if (!pickup_request) { if (!spec_used[7]) { spec_used[7] = true; pref[0].edit().putBoolean(spec_used_id[7], true); } };

				break;
			case 24 :
				autofire = !autofire;
				autofire_timer = 0;
				fired = true;
				if (!pickup_request) { if (!spec_used[7]) { spec_used[7] = true; pref[0].edit().putBoolean(spec_used_id[7], true); } };

				break;
			// Spaceship Invincible
			case 25 :
			case 26 :
			case 27 :
				invincible = true;
				invincible_timer = time_current + allUpgrades.special[spaceship_special_launch].duration;
				fired = true;
				if (!pickup_request) { if (!spec_used[8]) { spec_used[8] = true; pref[0].edit().putBoolean(spec_used_id[8], true); } };
				break;
			// Special Waves
			case 28 :
			case 29 :
			case 30 :
				Bullet b_;
				for (int i=0;i<8;i++) {
					b_ = new Bullet();
					b_.active = true;
					b_.type = allUpgrades.special[spaceship_special_launch].bullet;
			    	b_.damage = allUpgrades.special[spaceship_special_launch].damage;
			    	b_.rotation = 0.0f+i*45.0f;
			    	b_.x = spaceship.x + allUpgrades.ship[spaceship.type].width/2;
			    	b_.y = spaceship.y + allUpgrades.ship[spaceship.type].height/2;
			    	float bspeed = allUpgrades.turret[0].bulletspeed /2;
			    	switch (i) {
			    	case 0:
				    	b_.xmove = 0;
				    	b_.ymove = tmod * (bspeed);
				    	break;
			    	case 1:
				    	b_.xmove = (0.7071f)* tmod * (bspeed);
				    	b_.ymove = (0.7071f)* tmod * (bspeed);
				    	break;
			    	case 2:
				    	b_.xmove = tmod * (bspeed);
				    	b_.ymove = 0;
				    	break;
			    	case 3:
				    	b_.xmove = (0.7071f)* tmod * (bspeed);
				    	b_.ymove = - (0.7071f)* tmod * (bspeed);
				    	break;
			    	case 4:
				    	b_.xmove = 0;
				    	b_.ymove = - tmod * (bspeed);
				    	break;
			    	case 5:
				    	b_.xmove = - (0.7071f)* tmod * (bspeed);
				    	b_.ymove = - (0.7071f)* tmod * (bspeed);
				    	break;
			    	case 6:
				    	b_.xmove = - tmod * (bspeed);
				    	b_.ymove = 0;
				    	break;
			    	case 7:
				    	b_.xmove = - (0.7071f)* tmod * (bspeed);
				    	b_.ymove = (0.7071f)* tmod * (bspeed);
				    	break;
			    	}
			    	spaceship_bullet.add(b_);
				}			    	
				
				// Additional waves
		    	rocket = 1;
		    	rocket_type = spaceship_special_launch;
		    	rocket_time = time_current + 300;

				fired = true;
				break;
			// Crystal of Destruction
			case 31 :
				nuke_timer = 1;
				for(Entity npc_: npc){
					if (npc_.hp > 0) {
							npc_.hit_timer = 1;
							npc_.hit_timer_hp = 1;
							// ha van az npc-nek pajzsa is
							if (npc_.shield > 0) { 
								npc_.shield -= allUpgrades.special[spaceship_special_launch].damage;
								if (npc_.shield < 0) {
									npc_.hp += npc_.shield; npc_.shield = 0;
								}
							// ha nincs pajzs
							} else {
								npc_.hp -= allUpgrades.special[spaceship_special_launch].damage;
							}
							// ha elfogyott a hp-ja
							if (npc_.hp <= 0) { 
								npc_.hp = 0; 
								npc_.isalive = false;
								special_kill++;
								total_kill++;

								if (sound) {
									if (allEntities.npc[npc_.type].die_sound > 0) {
										SoundManager.playSound(allEntities.npc[npc_.type].die_sound, 1);
									}
								}
								if (vibration) {
									if (allEntities.npc[npc_.type].vibration >= 0) {
										vibrator.vibrate(vibration_pattern[allEntities.npc[npc_.type].vibration]);
									}
								}
								npc_.drop = true;
								float difmod = 1f; if (difficulty == 0) difmod = 0.5f; else if (difficulty == 2) difmod = 1.5f;
								missionLY += allEntities.npc[npc_.type].lyvalue * ly_szorzo * spaceship.lymod * difmod;
								//missionLY += (allEntities.npc[npc_.type].shield_max+allEntities.npc[npc_.type].hp_max) * ly_szorzo * spaceship.lymod * difmod;
								
							}
						}
				}
				if (pickup_special_launch != spaceship_special_launch) {
					allUpgrades.special[spaceship_special_launch].bought = false;
					for (int i=0;i<spaceship.special_num;i++) {
						if (spaceship.special[i] == spaceship_special_launch) spaceship.special[i] = -1;
					}
					Save_Profile(0);
				}
				fired = true;
				break;
			// Crystal of Power
			case 32 :
				spaceship.hp += allUpgrades.special[spaceship_special_launch].damage;
				if (spaceship.hp>spaceship.hp_max)spaceship.hp=spaceship.hp_max;
				spaceship.shield += allUpgrades.special[spaceship_special_launch].damage;
				if (spaceship.shield>spaceship.shield_max)spaceship.shield=spaceship.shield_max;

				if (pickup_special_launch != spaceship_special_launch) {
					allUpgrades.special[spaceship_special_launch].bought = false;
					for (int i=0;i<spaceship.special_num;i++) {
						if (spaceship.special[i] == spaceship_special_launch) spaceship.special[i] = -1;
					}
					Save_Profile(0);
				}
				fired = true;
				break;
			// Crazy Missile
			case 33 :
			case 34 :
			case 35 :
				if (!crazy_bullet.active && !crazy) {
					crazy_update = true;
					crazy = true;
					crazy_update_num = 0;
					crazy_bullet.active = true;
					crazy_bullet.damage = allUpgrades.special[spaceship_special_launch].damage;
					crazy_bullet.rotation = 0.0f;
					crazy_bullet.type = 9;
					crazy_bullet.x = spaceship.x + allUpgrades.ship[spaceship.type].turret_oo[0][0] - (int)allBullets.bullet[crazy_bullet.type].width/2;
					crazy_bullet.y = spaceship.y + allUpgrades.ship[spaceship.type].turret_oo[0][1] - (int)allBullets.bullet[crazy_bullet.type].height/2;
					crazy_bullet.xmove = 0;
					crazy_bullet.ymove = 0;
					fired = true;
				} else {
					for (int i=0;i<spaceship.special_num;i++) {
						if (spaceship.special[i] == spaceship_special_launch) {
							spaceship.special_cd_timer[i] = (long)(time_current - spaceship.cdmod*allUpgrades.special[spaceship.special[i]].cooldown  * 1000 * game_timer_fix + 1000);
							break;
						}
					}
				}
				if (!pickup_request) { if (!spec_used[9]) { spec_used[9] = true; pref[0].edit().putBoolean(spec_used_id[9], true); } };

				break;
			// Deploy Mine
			case 36 :
			case 37 :
			case 38 :
				Bullet m_;
				for (int i=-1; i<2; i++) {
					for (int j=-1; j<2; j++) {
						m_ = new Bullet();
						m_.active = true;
						m_.type = allUpgrades.special[spaceship_special_launch].bullet;
					    m_.damage = allUpgrades.special[spaceship_special_launch].damage;
					    m_.rotation = 0.0f;
					    m_.x = spaceship.x + allUpgrades.ship[spaceship.type].width/2 - (int)(hd*27) + i*(int)(hd*74) ;
					    m_.y = spaceship.y + allUpgrades.ship[spaceship.type].height/2 - (int)(hd*27) + j*(int)(hd*74) ;
				    	m_.xmove = 0;
				    	m_.ymove = -(tmod * (allUpgrades.special[spaceship_special_launch].speed));
				    	spaceship_bullet.add(m_);
					}
				}
				fired = true;
				break;
			// Cryogenic Modulator
			case 39 :
			case 40 :
			case 41 :
				for (int i=0; i<spaceship.special_num; i++){
					if (!spaceship.special_cd[i] && spaceship.special[i] >= 0 && spaceship.special_cd_timer[i] >= 0 && spaceship.special[i] != spaceship_special_launch) {
						spaceship.special_cd_timer[i] -= allUpgrades.special[spaceship_special_launch].damage*1000;
						if (spaceship.special_cd_timer[i] < 0) {
							spaceship.special_cd_timer[i] = 0;
							spaceship.special_cd[i] = true;
						}
					}
				}
				if (!pickup_request) { if (!spec_used[10]) { spec_used[10] = true; pref[0].edit().putBoolean(spec_used_id[10], true); } };

				fired = true;
				break;
			// Antirocket / guardian
			case 42 :
			case 43 :
			case 44 :
				for(Bullet bulletx_: npc_bullet){
					if (bulletx_.active && !bulletx_.unstoppable) {
						bulletx_.active = false;
					}
				}
				fired = true;
				if (!pickup_request) { if (!spec_used[11]) { spec_used[11] = true; pref[0].edit().putBoolean(spec_used_id[11], true); } };

				break;
			// Wing Modul
			case 45 :
			case 46 :
			case 47 :
				wings = true;
				wings_timer = time_current + allUpgrades.special[spaceship_special_launch].duration*1000;
				wings_starttime = time_current;
				fired = true;
				if (!pickup_request) { if (!spec_used[12]) { spec_used[12] = true; pref[0].edit().putBoolean(spec_used_id[12], true); } };

				break;
			// Turret Upgrade
			case 48 : 
				//rocket = 1;
				//rocket_time = time_current + 150;
				if (extraturret == -1 && prevturret == -1) prevturret = spaceship.turret[spaceship.turret_active];
				extraturret = (spaceship.turret[spaceship.turret_active]/3 +1 )*3;
				if (extraturret > allUpgrades.maxturret-1) extraturret = allUpgrades.maxturret-1;
				spaceship.turret[spaceship.turret_active] = extraturret; 
				extraturret_time = time_current + 10000;
				fired = true;
				break;
			// Modifier Upgrade
			case 49 : 
				if (extramodifier == -1 && prevmodifier == -1) prevmodifier = spaceship.modifier[spaceship.modifier_active];
				extramodifier = spaceship.modifier[spaceship.modifier_active]+1;
				if (extramodifier > allUpgrades.maxmodifier-1) extramodifier = allUpgrades.maxmodifier-1;
				spaceship.modifier[spaceship.modifier_active] = extramodifier;
				extramodifier_time = time_current + 10000;
				fired = true;
				break;
			// Upgrade TO MAX
			case 50 : 
				if (extraturret == -1 && prevturret == -1) prevturret = spaceship.turret[spaceship.turret_active];
				extraturret = allUpgrades.maxturret-1;
				spaceship.turret[spaceship.turret_active] = extraturret; 
				extraturret_time = time_current + 10000;
				if (extramodifier == -1 && prevmodifier == -1) prevmodifier = spaceship.modifier[spaceship.modifier_active];
				extramodifier = allUpgrades.maxmodifier-1;
				spaceship.modifier[spaceship.modifier_active] = extramodifier;
				extramodifier_time = time_current + 10000;
				
				fired = true;
				break;

			default : 
				break;
		}
		if (sound && fired && spaceship_special_launch < allUpgrades.maxspecial) {
			if (allUpgrades.special[spaceship_special_launch].sound > 0) {
				SoundManager.playSound(allUpgrades.special[spaceship_special_launch].sound, 1);
			}
		} 
		if (vibration && fired && spaceship_special_launch < allUpgrades.maxspecial) {
			if (allUpgrades.special[spaceship_special_launch].vibration >= 0) {
				vibrator.vibrate(vibration_pattern[allUpgrades.special[spaceship_special_launch].vibration]);
			}
		}
		if (pickup_special_launch == spaceship_special_launch) { pickup_special_launch = -1; }
		spaceship_special_launch = -1;
	}

	// Sound play thread - fix game loop lag issue ??? 
	public class MyRunnable implements Runnable {
		
		private int s_id; 
		
		public MyRunnable(int sound_id) {
			s_id = sound_id;
		}

		public void run() {
			SoundManager.playSound(s_id, 1);
		}
	}

	// ALL MISSION END SCRIPT
	// ALL MISSION END SCRIPT
	// ALL MISSION END SCRIPT
	// ALL MISSION END SCRIPT
	public void End_Mission(boolean b) {

		//Log.i("MediaPlayer", "media update set to true");
		//helper[0] = "kuldetes vege";

		if (b) {
			//LY += missionLY; 
			
			// end game dialog
			int mission_finished = menusystem.selected_mission;
			if (menusystem.mission[menusystem.selected_mission].completed > 1) mission_finished = -1;   

			//menusystem.selected_mission = -1;
			menusystem.dialogue_mission = true;
			menusystem.dialogue_galaxy = false;
			menusystem.dialogue_upgrade = false;
			menusystem.dialogue_game = false;
			menusystem.selected_mission = -1;
			galaxybg_update = true;
			menusystem_update = true;
			if (instantshield) { spaceship.shield_max -= 10; if (spaceship.shield > spaceship.shield_max) spaceship.shield = spaceship.shield_max; }
			
			/*
	        if (mission_finished == 102 && OpenGLRenderer.isGoogle && Main.tapjoy && tapjoy_actions[2]) {
	        	try { 
	        		TapjoyConnect.getTapjoyConnectInstance().actionComplete("a05698e3-26bc-4486-b7a6-1f9179b37a68");
	        	} catch (Exception e) { e.printStackTrace(); } 
        		tapjoy_actions[2] = false;
        		
	        } else if (OpenGLRenderer.isGoogle && Main.tapjoy && tapjoy_actions[1]) {
	        	try { 
	        		TapjoyConnect.getTapjoyConnectInstance().actionComplete("608e3938-528b-4624-9fd1-ff1721a0ce63");
	        	} catch (Exception e) { e.printStackTrace(); } 
        		tapjoy_actions[1] = false;
	        }
	        */
	        
			
			if (music) {
				if (mediaplayer.isPlaying()) {
					mediaplayer.stop();
        		}
				media_load(media_list[0],false);
				media_update = true;
				//mediaplayer.start();
			}

				adtimer = 0;
				menu = 8;
				// LEADBOLT
				//if (!fullgame && lb_adshown) { lb_adshown = false; if (lb_ad == 0) handler.sendEmptyMessage(5); lb_ad++; if (lb_ad > 0) lb_ad = 0; pref[0].edit().putInt("LB_AD", lb_ad).commit(); }
				if (lb_adshown && !fullgame) { if (lb_ad <= 4) handler.sendEmptyMessage(5); lb_ad++; if (lb_ad > 4) lb_adshown = false; }

				
					// Show dialogs on missioncomplete
					int completed = 0;
					for (int i=0; i<menusystem.total_mission;i++) {
						if (menusystem.mission[i].completed > 1) completed++;
						if (completed > 10) break;
					}
					dialoghelper = false;
					
					if (mission_finished == 102) { 
						((Activity) context).runOnUiThread(new Runnable() {
			                public void run()
			                { 
								AppRater.launch_EndDialog(context);
								//AppRater.showEndDialog(context, null);
			                }
			            });
						dialoghelper = true;
					}
					
					if (!dialoghelper) if (completed >= 6) {
						((Activity) context).runOnUiThread(new Runnable() {
			                public void run()
			                { 
								dialoghelper = AppRater.launch_PromoDialog(context);
								//AppRater.showPromoDialog(context, null);
			                }
			            });
					}
					
					int completenum = 2;
					//if (!isGoogle) completenum = 2;
					
					if (!dialoghelper) if (completed >= completenum && rate_firsttime) { 
						((Activity) context).runOnUiThread(new Runnable() {
			                public void run()
			                { 
								dialoghelper = AppRater.launch_RateDialog(context, 0);
								rate_firsttime = false;
								pref[0].edit().putBoolean("Rate_Firsttime", false).commit();
								//AppRater.showRateDialog(context, null);
			                }
			            });						
					} 
					
					else if (!dialoghelper) if (completed >= completenum+3 && !rate_firsttime) { 
						((Activity) context).runOnUiThread(new Runnable() {
			                public void run()
			                { 
								dialoghelper = AppRater.launch_RateDialog(context, 1); 
								//AppRater.showRateDialog(context, null);
			                }
			            });						
					} 

		} else {
			missionLY = 0;
			menusystem.dialogue_mission = true;
			menusystem.dialogue_galaxy = false;
			menusystem.dialogue_upgrade = false;
			menusystem.dialogue_game = false;
			menusystem.selected_mission = -1;
			galaxybg_update = true;
			menusystem_update = true;
			if (instantshield) spaceship.shield_max -= 10;
			
				if (music) {
					if (mediaplayer.isPlaying()) {
						mediaplayer.stop();
					}
					media_load(media_list[0],false);
					media_update = true;
				//	mediaplayer.start();
				}

				adtimer = 0;
				menu = 8;

		}
		
	}

	public void End_Mission_Save(boolean b) {

		//Log.i("MediaPlayer", "media update set to true");
		//helper[0] = "kuldetes vege";

		if (b) {
			vLY = LY;
			LY += missionLY; 
			
				if (menusystem.mission[menusystem.selected_mission].completed < 2) {
					LY += menusystem.mission[menusystem.selected_mission].reward;
				}
				if (difficulty == 0 && menusystem.mission[menusystem.selected_mission].completed < 2) {
					menusystem.mission[menusystem.selected_mission].completed = 2;
				} else if (difficulty == 1 && menusystem.mission[menusystem.selected_mission].completed < 3) {
					menusystem.mission[menusystem.selected_mission].completed = 3;
				} else if (difficulty == 2 && menusystem.mission[menusystem.selected_mission].completed < 4) {
					menusystem.mission[menusystem.selected_mission].completed = 4;
				}
				if (missionLY > menusystem.mission[menusystem.selected_mission].highscore) {
					menusystem.mission[menusystem.selected_mission].highscore = missionLY;
				}
			//missionLY = 0;
			Save_Profile(2);
			
		} else {
			total_defeat ++;
			pref[0].edit().putInt("Total_Defeat", total_defeat).commit();

		}
		
		pref[0].edit().putInt("Total_Kill", total_kill).commit();
		pref[0].edit().putInt("Special_Kill", special_kill).commit();
		pref[0].edit().putInt("Total_Pickup", total_pickup).commit();
	    //boolean[] spec_used = { false, false, false, false, false, false, false, false, false, false, false, false, false };
	    //int lyspent = 0;
	    //int npc_escaped = 0;

		
		// GOOGLE GAME SERVICES
		// mission end achievements
		if (b) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_1MISSION);
			}
			if (!got_achievement[0]) {
				got_achievement[0] = true; pref[0].edit().putBoolean(got_achievement_id[0], true);
				achievement_anim = true;
			}
		} 
		if (!b && total_defeat >= 100) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_100DEFEAT);
			}
			if (!got_achievement[6]) {
				got_achievement[6] = true; pref[0].edit().putBoolean(got_achievement_id[6], true);
				achievement_anim = true;
			}
		}
		if (total_kill >= 500) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_100KILL);
			}
			if (!got_achievement[3]) {
				got_achievement[3] = true; pref[0].edit().putBoolean(got_achievement_id[3], true);
				achievement_anim = true;
			}
		}
		if (total_kill >= 10000) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_10KKILL);
			}
			if (!got_achievement[4]) {
				got_achievement[4] = true; pref[0].edit().putBoolean(got_achievement_id[4], true);
				achievement_anim = true;
			}
		}
		if (special_kill >= 100) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_100EXTRAKILL);
			}
			if (!got_achievement[5]) {
				got_achievement[5] = true; pref[0].edit().putBoolean(got_achievement_id[5], true);
				achievement_anim = true;
			}
		}
		if (total_pickup >= 1000) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_1KPICKUP);
			}
			if (!got_achievement[7]) {
				got_achievement[7] = true; pref[0].edit().putBoolean(got_achievement_id[7], true);
				achievement_anim = true;
			}
		}
		int counter = 0; for (int i=0; i<13; i++) if (spec_used[i]) counter++;
		if (counter >= 13) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_ALLSPEC);
			}
			if (!got_achievement[10]) {
				got_achievement[10] = true; pref[0].edit().putBoolean(got_achievement_id[10], true);
				achievement_anim = true;
			}
		}
		if (b && npc_escaped == 0) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_NOESCAPE);
			}
			if (!got_achievement[15]) {
				got_achievement[15] = true; pref[0].edit().putBoolean(got_achievement_id[15], true);
				achievement_anim = true;
			}
		}
		int mission_completed = 0;
		int hardmission_completed = 0;
		for (int i=0; i<menusystem.total_mission; i++) {
			if (menusystem.mission[i].completed >= 2) mission_completed++;
			if (menusystem.mission[i].completed >= 4) hardmission_completed++;
		}
		// GOOGLE GAME SERVICES
		if (mission_completed >= 10) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_10BOSS);
			}
			if (!got_achievement[9]) {
				got_achievement[9] = true; pref[0].edit().putBoolean(got_achievement_id[9], true);
				achievement_anim = true;
			}
		}
		if (hardmission_completed >= 5) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_5HARDMISSION);
			}
			if (!got_achievement[11]) {
				got_achievement[11] = true; pref[0].edit().putBoolean(got_achievement_id[11], true);
				achievement_anim = true;
			}
		}
		if (menusystem.mission[111].completed >= 2) {
			if (ggs && gameHelper.getApiClient().isConnected()) {
				Games.Achievements.unlock(gameHelper.getApiClient(), ACHIEVEMENT_BABYBOOM);
			}
			if (!got_achievement[1]) {
				got_achievement[1] = true; pref[0].edit().putBoolean(got_achievement_id[1], true);
				achievement_anim = true;
			}
		}
		
		
	}

	// THIS IS THE GAME LOGIC
	// THIS IS THE GAME LOGIC
	// THIS IS THE GAME LOGIC
	// THIS IS THE GAME LOGIC
	// THIS IS THE GAME LOGIC
	// THIS IS THE GAME LOGIC
	// THIS IS THE GAME LOGIC
	// THIS IS THE GAME LOGIC
	public void GameUpdateLogic(GL10 gl) {
		
		int random = (int)Math.ceil(Math.random()*100);

		//if (animation) {
		if (bgloopY > (hd*800)) { bgloopY = 0; }
		bgloopY = bgloopY+ allMissions.missionControl.bgspeed * tmod * (booster_value);
		if (cloudloopY > (hd*800)) { cloudloopY = 0; }
		cloudloopY = cloudloopY+ allMissions.missionControl.bgspeed * 4f * tmod * (booster_value);
		if (bg_extra) {
			if (bgextraloopY > (hd*800)) { bgextraloopY = 0; }
			bgextraloopY = bgextraloopY+ allMissions.missionControl.bgspeed * tmod * (booster_value) / 2;
		}
		//}
		
		if (spaceship.freeze) {
			if (time_current > spaceship.freeze_timer + 1000) {
				spaceship.freeze = false;
			}
		}
		
		boolean environment_only = true;
		for(Entity npc_: npc){
			if (allEntities.npc[npc_.type].hp_max != 0) { environment_only = false; break; }
		}

		//if (!mission_script_wait && npc.isEmpty()) { mission_script_wait = true; }
		if (!mission_script_wait && environment_only) { mission_script_wait = true; }
		
		// incoming npc
		if (!freeze && mission_script_wait && mission_script_delay && allMissions.missionControl!=null
				&& mission_script < allMissions.missionControl.num && mission_script >= 0) {
			
			if (allMissions.missionControl.script[mission_script].type <= 1) {
				Entity npc_ = new Entity();
				npc_.isalive = true;
				npc_.type = allMissions.missionControl.script[mission_script].entity;
				npc_.explosion_loop = 0;
				npc_.hp =allEntities.npc[npc_.type].hp_max;
				npc_.shield = allEntities.npc[npc_.type].shield_max;
				if (npc_.hp > 0) {
					if (difficulty == 0) {
						npc_.hp = (int)((float)npc_.hp*0.6667f);
						if (npc_.hp < 1) npc_.hp = 1;
						npc_.shield = (int)((float)npc_.shield*0.6667f);
					}
					if (difficulty == 2) {
						if (npc_.hp < 4) npc_.hp++; else npc_.hp = (int)((float)npc_.hp*dif_hard);
						npc_.shield = (int)((float)npc_.shield*dif_hard);
					}
				}
				npc_.move_pattern = allMissions.missionControl.script[mission_script].pattern;
				npc_.current_script = 0;
				npc_.bullet_timer = time_current;
				// npc disruptor
				if (allEntities.npc[npc_.type].disruptor > -1) { 
					npc_.disruptor_timer = time_current + allEntities.npc_special[allEntities.npc[npc_.type].disruptor].cooldown * 1000;
				}
				npc.add(npc_);
			} else {
				if (allMissions.missionControl.script[mission_script].type == 2) {
					//bg speed
					allMissions.missionControl.bgspeed = (float)(allMissions.missionControl.script[mission_script].entity / 100.0f); 
				} else if (allMissions.missionControl.script[mission_script].type == 3) {
					//menusystem.dialogue_game_num = allMissions.missionControl.script[mission_script].entity;
					int num = menusystem.selected_mission + 7;
					if (num < allMissions.Dialogue_list.length && !allMissions.Dialogue_list[num][0].equals("")) {
						menusystem.dialogue_game_num = menusystem.selected_mission + 7;
						menusystem.dialogue_game = true;
						// hide ads and reset timer if dialogue is displayed 
						adtimer = 0;
						if (showad) {
							showad = false;
							handler.sendEmptyMessage(0);
						}
					}					
				} else if (allMissions.missionControl.script[mission_script].type == 4) {
					if(sound)SoundManager.playSound(allMissions.missionControl.script[mission_script].entity,1);
				} else if (allMissions.missionControl.script[mission_script].type == 5) {
					// do nothing
				} else if (allMissions.missionControl.script[mission_script].type == 6) {
					//warning_timer ++;
					warning = true;
				}
				
			}
			mission_script++;
			if (mission_script >= allMissions.missionControl.num) {
				// the end
				freeze = true;
				
				if (extraturret >= 0) {
					extraturret = -1;
					spaceship.turret[spaceship.turret_active] = prevturret;
					prevturret = -1;
				}
				if (extramodifier >= 0) {
					extramodifier = -1;
					spaceship.modifier[spaceship.modifier_active] = prevmodifier;
					prevmodifier = -1;
				}
				
				if (spaceship.isalive) endmission_success = true; else endmission_success = false; 
				if (music) { if (musicvolume > 0.2f) mediaplayer.setVolume(0.2f, 0.2f); }
				if(sound)SoundManager.playSound(36,1);
				
				sharefeed[0] = 1;
				sharefeed[1] = menusystem.selected_mission;
				sharefeed[2] = missionLY;
				sharefeed[3] = pref[current_profile].getInt("Galaxy_Completed", 0);
				sharefeed[4] = pref[current_profile].getInt("Mission_Completed", 0);

				End_Mission_Save(true);
				endmission_state = 1;
				
			} else {
				if (allMissions.missionControl.script[mission_script].wait) { mission_script_wait = false; } else { mission_script_wait = true; }
				mission_script_delay = false;
				
				// POST DELAY helyett GETTICK TIME-al is meg lehet oldani
				//this.postDelayed(new Runnable() { 
				//	public void run() {	mission_script_delay = true; }
				//}, (int)(allMissions.missionControl.script[mission_script].delay*1000));
				mission_script_timer = time_current + (int)(allMissions.missionControl.script[mission_script].delay*1000/(booster_value));
				if (allMissions.missionControl.script[mission_script].delay > 0) {
					if (difficulty == 0) {
						mission_script_timer += 250;
						if (allMissions.missionControl.script[mission_script].delay > 1) {
							mission_script_timer += 250;
						}
					}
					if (difficulty == 2) {
						mission_script_timer -= 100;
					}

				}
			}
		}
		
		if (!mission_script_delay) {
			if (time_current > mission_script_timer) {
				mission_script_delay = true;
			}
		}
		
		boolean elso = true;
		
		// npc iteráció
		remove_list_npc.clear();
		for(Entity npc_: npc){

			if (npc_.isalive) {
				// if script = restart
				if (allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].type == 2) {
					npc_.current_script = allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].x;
					npc_.move_update = true;
				}
				// if script = spawn
				if (allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].type == 0) {
					if (allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].x < 0) {
						npc_.x = -allEntities.npc[npc_.type].width;
					} else {
						npc_.x = allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].x;
					}
					if (allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].y < 0) {
						npc_.y = -allEntities.npc[npc_.type].height;
					} else {
						npc_.y = allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].y;
					}
					npc_.current_script++;
					npc_.move_update = true;
				}
				// if script = destroy
				if (allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].type == 3) {
					npc_.isalive = false;
					npc_escaped++;
					npc_.drop = false;
					if (mining) {
						if (npc_.mining) {
							mining = false;
							npc_.mining = false;
						}
					}
				}
				// if script = move
				if (allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].type == 1) {
					
					// ha változik az npc mozgása, új mérték kiszámítása = növekvő / csökkenő / változatlan
					if (npc_.move_update) {
						// uj x es y koordinatak
						float x = allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].x;
						float y = allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].y;
						// x,y valtozas merteke
						float a = Math.abs(npc_.x - x);
						float b = Math.abs(npc_.y - y);
						
						float xmod = 1; float ymod = 1;
						
						if (a == 0) { xmod = 0; }
						else if (b == 0) { ymod = 0; }
						// atlos mozgas
						else if (a > 0 && b > 0) {
						
							float c = FloatMath.sqrt( a*a + b*b ); 
						
							xmod = a*c / (a+b);
							ymod = b*c / (a+b);
						
							// gyök 2-vel szorzás az átlós mozgás gyorsítására
							xmod = 1.4142f * xmod * (FloatMath.sqrt(1 / (a*a+b*b)));
							ymod = 1.4142f * ymod * (FloatMath.sqrt(1 / (a*a+b*b)));
						}
						// mozgas iranya, elojel
						int elojelx = 1;
						int elojely = 1;
						if (npc_.x > x) elojelx = -1;
						if (npc_.y > y) elojely = -1;

						// x koordináta változás
						npc_.move_x = elojelx * xmod * allEntities.npc[npc_.type].speed / 10 * allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].speed;
						//npc_.disort = 0;
						//if (npc_.move_x > 0) npc_.disort = -5;
						//else if (npc_.move_x < 0) npc_.disort = 5;
						
						// y koordináta változás
						npc_.move_y = elojely * ymod * allEntities.npc[npc_.type].speed / 10 * allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].speed;
						
						if (allEntities.npc[npc_.type].earthling || allEntities.npc[npc_.type].mine) npc_.move_y = 1;

						npc_.move_update = false;
					}
					// koordináták frissítése a változással
					if ( ( (!freeze || npc_.hp == 0) ||  !allEntities.npc[npc_.type].frozable) && !allEntities.npc[npc_.type].earthling && !allEntities.npc[npc_.type].mine) {
						npc_.x = npc_.x + npc_.move_x * tmod * (booster_value/2) * npc_.speedmod;
						npc_.y = npc_.y + npc_.move_y * tmod * (booster_value) * npc_.speedmod;
						
						if (allEntities.npc[npc_.type].rotator > 0) {
							npc_.rotate += (float)allEntities.npc[npc_.type].rotator * tmod;
							//npc_.rotate += (float)5 * tmod;
							if (npc_.rotate >= 360) npc_.rotate = npc_.rotate - 360;
						
						// non-rotator npc, rotate towards player ship
						} else if (allEntities.npc[npc_.type].rotate_towards && spaceship.isalive) {
							float x = spaceship.x + allUpgrades.ship[spaceship.type].width / 2;
							float y = spaceship.y + allUpgrades.ship[spaceship.type].height / 2;
							// x,y valtozas merteke
							float a = Math.abs(npc_.x - x);
							float b = Math.abs(npc_.y - y);
							float c = 0;
							float xmod = 1; float ymod = 1;
							if (a == 0) { xmod = 0; }
							else if (b == 0) { ymod = 0; }
							// atlos mozgas
							else if (a > 0 && b > 0) {
								c = FloatMath.sqrt( a*a + b*b ); 
								xmod = a*c / (a+b);
								ymod = b*c / (a+b);
								// gyök 2-vel szorzás az átlós mozgás gyorsítására
								xmod = 1.4142f * xmod * (FloatMath.sqrt(1 / (a*a+b*b)));
								ymod = 1.4142f * ymod * (FloatMath.sqrt(1 / (a*a+b*b)));
							}
							// mozgas iranya, elojel
							int elojelx = -1;
							int elojely = -1;
							if (npc_.x > x) elojelx = 1;
							if (npc_.y > y) elojely = 1;
							// x koordináta változás
							float xmove = elojelx * xmod;// * allEntities.npc[npc_.type].speed;
							// y koordináta változás
							float ymove = elojely * ymod;// * allEntities.npc[npc_.type].speed;;
							if (a > 0 && b > 0 && c > 0) { 
								if (xmove > 0 && ymove < 0) { npc_.rotate = (float)(57.295779513*Math.asin((float)a/c)); }
								else if (xmove > 0 && ymove > 0) { npc_.rotate = 90+(float)(57.295779513*Math.asin((float)b/c)); }
								else if (xmove < 0 && ymove > 0) { npc_.rotate = 180+(float)(57.295779513*Math.asin((float)a/c)); }
								else if (xmove < 0 && ymove < 0) { npc_.rotate = 270+(float)(57.295779513*Math.asin((float)b/c)); }
							} else {
								if (a == 0 && ymove < 0) { npc_.rotate = 0; }
								else if (a == 0 && ymove > 0) { npc_.rotate = 180; }
								else if (b == 0 && xmove < 0) { npc_.rotate = 270; }
								else if (b == 0 && xmove > 0) { npc_.rotate = 90; }
							}
							//npc_.rotate = 180 - npc_.rotate; 
						}
					} else if (allEntities.npc[npc_.type].earthling || allEntities.npc[npc_.type].mine) {
						npc_.y = npc_.y + allMissions.missionControl.bgspeed * tmod * (booster_value);
					}
					// npc regeneration, shield recharge
					if (allEntities.npc[npc_.type].recharge > 0) {

						int hpmax_ = allEntities.npc[npc_.type].hp_max;
						int shieldmax_ = allEntities.npc[npc_.type].shield_max;
						
						if (difficulty == 0) {
							hpmax_ = (int)((float)hpmax_*0.6667f);
							if (hpmax_ < 1) hpmax_ = 1;
							shieldmax_ = (int)((float)shieldmax_*0.6667f);
						}
						if (difficulty == 2) {
							if (hpmax_ < 4) hpmax_++; else hpmax_ = (int)((float)hpmax_*dif_hard);
							shieldmax_ = (int)((float)shieldmax_*dif_hard);
						}
						
						// shield
						if (shieldmax_ > 0) {
							if (npc_.shield < shieldmax_) {
								if (time_current > npc_.recharge_timer + (10000 / allEntities.npc[npc_.type].recharge)) {
									npc_.shield++;
									if (npc_.shield > shieldmax_) npc_.shield = shieldmax_;
									npc_.recharge_timer = time_current;
								}
							}
						// hp
						} else {
							if (npc_.hp < hpmax_) {
								if (time_current > npc_.recharge_timer + (10000 / allEntities.npc[npc_.type].recharge)) {
									npc_.hp++;
									if (npc_.hp > hpmax_) npc_.hp = hpmax_;
									npc_.recharge_timer = time_current;
								}
							}
						}
					}

					// npc bgloop
					npc_.bg_loop++;
					if (npc_.bg_loop >= explosion_loop_scale * allExplosions.explosion[11].picture_num) {
						npc_.bg_loop = 0;
					}
						
					boolean shoot = true;
					if (freeze && allEntities.npc[npc_.type].frozable) shoot = false;
					if (gravity && allEntities.npc[npc_.type].gravitable) shoot = false;
					
					// npc disruptor
					if ( shoot && allEntities.npc[npc_.type].disruptor > -1) {
						if (time_current >= npc_.disruptor_timer
								// do not shoot if npc goes behind bottom menu - 200
								&& npc_.y < 520 - allEntities.npc[npc_.type].height) {
							
							npc_.speedmod = 0.1f;
							npc_.disruptor_enabled = true;
							npc_.disruptor_timer = time_current + allEntities.npc_special[allEntities.npc[npc_.type].disruptor].cooldown * 1000;
							
							npc_.disruptor_xy[0] = (int)(npc_.x + (float)allEntities.npc[npc_.type].width/2 - (float)allEntities.npc_special[allEntities.npc[npc_.type].disruptor].width/2);
							npc_.disruptor_xy[1] = (int)(npc_.y + (float)allEntities.npc[npc_.type].height);
							float dx = npc_.disruptor_xy[0] + (float)allEntities.npc_special[allEntities.npc[npc_.type].disruptor].width/2 - (float)allEntities.npc_special[allEntities.npc[npc_.type].disruptor].rangex/2;
							
							// npc-vel való ütközés
							if (spaceship.isalive && !invincible) {
								if (!( spaceship.y + allUpgrades.ship[spaceship.type].height-allUpgrades.ship[spaceship.type].size_mod[3] < npc_.disruptor_xy[1] || spaceship.y+allUpgrades.ship[spaceship.type].size_mod[2] > npc_.disruptor_xy[1] + allEntities.npc_special[allEntities.npc[npc_.type].disruptor].rangey
									|| spaceship.x + allUpgrades.ship[spaceship.type].width-allUpgrades.ship[spaceship.type].size_mod[1] < dx || spaceship.x+allUpgrades.ship[spaceship.type].size_mod[0] > dx + allEntities.npc_special[allEntities.npc[npc_.type].disruptor].rangex ))
								{
									//spaceship_damage_value = allEntities.npc_special[allEntities.npc[npc_.type].disruptor].damage;
									//use npc bullet damage * 2 as disruptor damage
									spaceship_damage_value = allEntities.npc[npc_.type].damage * 2;
								}
							}
							
							if (sound) {
								if (allEntities.npc_special[allEntities.npc[npc_.type].disruptor].sound > 0) {
									Runnable r = new MyRunnable(allEntities.npc_special[allEntities.npc[npc_.type].disruptor].sound);
									handler.postDelayed(r, 0);
									//new Thread(r).start();
									//SoundManager.playSound(allEntities.npc[npc_.type].weapon_sound, 1);
								}
							}
						}
					}
					
					
					/*
					
					// spaceship bullet kilovese
					if (bullet_launch) {
						Bullet bullet_;

						int btype = 0;
				    	//float brot = 0.0f;
				    	float brot = towerROT;
				    	float bx = allMissions.tower_pos[0] + allMissions.tower_pos[2]/2 - allBullets.bullet[btype].width/2;
				    	float by = allMissions.tower_pos[1] + allMissions.tower_pos[3]/2 - allBullets.bullet[btype].height/2;
				    	float bxmove = 0;
				    	float bymove = 0;
				    	
				    	// uj x es y koordinatak
						float x = shootXY[0];
						float y = shootXY[1];
						// x,y valtozas merteke
						float a = Math.abs(bx - x);
						float b = Math.abs(by - y);
						float c = 0;
						float xmod = 1; float ymod = 1;
						if (a == 0) { xmod = 0; }
						else if (b == 0) { ymod = 0; }
						// atlos mozgas
						else if (a > 0 && b > 0) {
							c = (float)Math.sqrt( a*a + b*b ); 
							xmod = a*c / (a+b);
							ymod = b*c / (a+b);
							// gyök 2-vel szorzás az átlós mozgás gyorsítására
							xmod = 1.4142f * xmod * ((float)Math.sqrt(1 / (a*a+b*b)));
							ymod = 1.4142f * ymod * ((float)Math.sqrt(1 / (a*a+b*b)));
						}
						// mozgas iranya, elojel
						int elojelx = 1;
						int elojely = 1;
						if (bx > x) elojelx = -1;
						if (by > y) elojely = -1;
						// x koordináta változás
						bxmove = elojelx * xmod * 10;
						// y koordináta változás
						bymove = elojely * ymod * 10;

						// calculate bullet center by tower rotation
						c = 90*hd;	// ???
						//c = 0*hd;
						float sinfv = (float)(Math.sin(-towerROT*0.0175f));
						float cosfv = (float)(Math.cos(-towerROT*0.0175f));
						a = cosfv*c;
						b = -sinfv*c;
						//helper[1] = "a: " + Float.toString(a) + "   b: " + Float.toString(b) + "   rot: " + Float.toString(-towerROT);
						
						//Log.i("Cell Planet", "Bullet added.");
						int bulletnum = (int) (allUpgrades.tower[3].amount[0]  + allUpgrades.tower[3].level * allUpgrades.tower[3].amount_increase[0]); if (bulletnum > 5) bulletnum = 5;
				    	//bulletnum = 5;
						//Log.i("Cell Planet", Integer.toString(bulletnum));

						boolean critical = false;
				    	if ( (int)Math.ceil(Math.random()*100) < allUpgrades.tower[0].amount[1] + allUpgrades.tower[0].level * allUpgrades.tower[0].amount_increase[1]) 
				    		critical = true;
						
						for (int i=1; i<=bulletnum; i++) {
							
				    		bullet_ = new Bullet();
							bullet_.active = true;
							bullet_.type = btype;
					    	bullet_.damage = allUpgrades.tower[0].amount[0] + allUpgrades.tower[0].level * allUpgrades.tower[0].amount_increase[0];

					    	if (critical) {
					    		bullet_.damage *= 2;
					    		bullet_.critical = true;
					    	}
					    	if ( (int)Math.ceil(Math.random()*100) < allUpgrades.tower[2].amount[0] + allUpgrades.tower[2].level * allUpgrades.tower[2].amount_increase[0]) {
					    		bullet_.slow = true;
					    	}
					    	// tobb lovedek eseten csokkentett sebzes lovedekenkent
					    	float dmgdiv = 1;
					    	if (bulletnum == 1) dmgdiv = 1.0f; 
					    	else if (bulletnum == 2) dmgdiv = 0.90f; 
					    	else if (bulletnum == 3) dmgdiv = 0.80f;
					    	else if (bulletnum == 4) dmgdiv = 0.70f;
					    	else if (bulletnum == 5) dmgdiv = 0.60f;
					    	bullet_.damage = bullet_.damage * dmgdiv; 
					    	
					    	bullet_.rotation = brot;
					    	bullet_.x = bx+a;
					    	bullet_.y = by+b;
					    	bullet_.xmove = bxmove;
					    	bullet_.ymove = bymove;
					    	
					    	float cmove = (float)Math.sqrt(bxmove*bxmove + bymove*bymove);
					    	float alfa = 0;

							switch (i) {
								case 1 :
									if (bulletnum == 2 || bulletnum == 4) {
										bullet_.x -= sinfv*(12*hd);
										bullet_.y -= cosfv*(12*hd);
										bullet_.rotation -= 2;
									}

							    	alfa = bullet_.rotation; //if (alfa <= 90) alfa = 90 - alfa; else alfa = alfa - 90; 
							    	bullet_.xmove = cmove * (float)Math.cos(alfa*0.0175f);
							    	bullet_.ymove = (float)Math.sqrt(cmove*cmove - bullet_.xmove*bullet_.xmove); if (bullet_.rotation < 0) bullet_.ymove *= -1;

									bullet.add(bullet_);
									break;
									
									
									
									*/
					
					
					// npc lövés
					if ( shoot && allEntities.npc[npc_.type].weapon > -1 && spaceship.isalive) {
						if (time_current >= npc_.bullet_timer + (allEntities.npc[npc_.type].rate_of_fire / booster_value)
								// do not shoot if npc goes behind bottom menu
								&& npc_.y < 720 - allEntities.npc[npc_.type].height) {

							for (int i=0; i<allEntities.npc[npc_.type].bullet_num; i++) {
								Bullet bullet_ = new Bullet();
								bullet_.active = true;
								bullet_.damage = allEntities.npc[npc_.type].damage;
								if (difficulty == 0) {
									bullet_.damage = (int)((float)bullet_.damage*0.6667f);
									if (bullet_.damage < 1) bullet_.damage = 1;
								}
								if (difficulty == 2) {
									bullet_.damage = (int)((float)bullet_.damage*	(1+	(dif_hard-1)*2/3	)	);
								}

								bullet_.type = allEntities.npc[npc_.type].weapon;
								bullet_.damage_type = allEntities.npc[npc_.type].damage_type;
								if (allEntities.npc[npc_.type].bullet_num <= 1) {
									bullet_.x = npc_.x + (float)allEntities.npc[npc_.type].width / 2 - allBullets.bullet[bullet_.type].width / 2;
									bullet_.y = npc_.y + allEntities.npc[npc_.type].height;
								} else {
									bullet_.x = npc_.x + allEntities.npc[npc_.type].weapon_pos[i][0] - allBullets.bullet[bullet_.type].width / 2;
									bullet_.y = npc_.y + allEntities.npc[npc_.type].weapon_pos[i][1];
								}
								
								if (allEntities.npc[npc_.type].aimed || allEntities.npc[npc_.type].rotate_towards) {
									
									bullet_.y = npc_.y + (float)allEntities.npc[npc_.type].height / 2 - allBullets.bullet[bullet_.type].height / 2;
									
									// uj x es y koordinatak
									float x = spaceship.x + allUpgrades.ship[spaceship.type].width / 2;
									float y = spaceship.y + allUpgrades.ship[spaceship.type].height / 2;
									// x,y valtozas merteke
									float a = Math.abs(bullet_.x - x);
									float b = Math.abs(bullet_.y - y);
									float c = 0;
									float xmod = 1; float ymod = 1;
									if (a == 0) { xmod = 0; }
									else if (b == 0) { ymod = 0; }
									// atlos mozgas
									else if (a > 0 && b > 0) {
										c = FloatMath.sqrt( a*a + b*b ); 
										xmod = a*c / (a+b);
										ymod = b*c / (a+b);
										// gyök 2-vel szorzás az átlós mozgás gyorsítására
										xmod = 1.4142f * xmod * (FloatMath.sqrt(1 / (a*a+b*b)));
										ymod = 1.4142f * ymod * (FloatMath.sqrt(1 / (a*a+b*b)));
									}
									// mozgas iranya, elojel
									int elojelx = 1;
									int elojely = 1;
									if (bullet_.x > x) elojelx = -1;
									if (bullet_.y > y) elojely = -1;
									// x koordináta változás
									bullet_.xmove = elojelx * xmod * allEntities.npc[npc_.type].bullet_speed;
									// y koordináta változás
									bullet_.ymove = elojely * ymod * allEntities.npc[npc_.type].bullet_speed;
									
									float sinfv = allEntities.npc[npc_.type].height/2 * (float)(Math.sin(npc_.rotate*0.0175f));
									float cosfv = allEntities.npc[npc_.type].height/2 * (float)(Math.cos(npc_.rotate*0.0175f));

									//x = cx + r * cos(a)
									//y = cy + r * sin(a)
									//bullet_.x = bullet_.x + allEntities.npc[npc_.type].height/2 * (float)(Math.cos(npc_.rotate*0.0175f));
									//bullet_.y = bullet_.y + allEntities.npc[npc_.type].height/2 * (float)(Math.sin(npc_.rotate*0.0175f));
									int negyed = -1;
									
									if (a > 0 && b > 0 && c > 0) { 
										if (bullet_.xmove > 0 && bullet_.ymove < 0) { bullet_.rotation = (float)(57.295779513*Math.asin((float)a/c)); /*bullet_.x -= cosfv; bullet_.y += sinfv; bullet_.y -= allEntities.npc[npc_.type].height/2;*/ negyed = 1; }
										else if (bullet_.xmove > 0 && bullet_.ymove > 0) { bullet_.rotation = 90+(float)(57.295779513*Math.asin((float)b/c)); /*bullet_.x += cosfv; bullet_.y += sinfv;*/ negyed = 2; }
										else if (bullet_.xmove < 0 && bullet_.ymove > 0) { bullet_.rotation = 180+(float)(57.295779513*Math.asin((float)a/c)); /*bullet_.x -= cosfv; bullet_.y -= sinfv;*/ negyed = 3;  }
										else if (bullet_.xmove < 0 && bullet_.ymove < 0) { bullet_.rotation = 270+(float)(57.295779513*Math.asin((float)b/c)); /*bullet_.x += cosfv; bullet_.y -= sinfv; bullet_.y -= allEntities.npc[npc_.type].height/2;*/ negyed = 4;  }
									} else {
										if (a == 0 && bullet_.ymove < 0) { bullet_.rotation = 0; }
										else if (a == 0 && bullet_.ymove > 0) { bullet_.rotation = 180; }
										else if (b == 0 && bullet_.xmove < 0) { bullet_.rotation = 270; }
										else if (b == 0 && bullet_.xmove > 0) { bullet_.rotation = 90; }
									}

									/*
									if (elso) {
										elso = false;
										helper[0] = "" + Float.toString(sinfv);
										helper[1] = "" + Float.toString(cosfv);
										helper[2] = "" + Float.toString(npc_.rotate);
										helper[3] = "" + Integer.toString(negyed);
									}
									*/
									
									/*
									float sinfv = (float)(Math.sin(npc_.rotate*0.0175f));
									float cosfv = (float)(Math.cos(npc_.rotate*0.0175f));
									bullet_.x -= sinfv*allEntities.npc[npc_.type].width;
									bullet_.y -= cosfv*allEntities.npc[npc_.type].height;
									*/
									
								} else {
									bullet_.ymove = allEntities.npc[npc_.type].bullet_speed;
									bullet_.rotation = 180;
								}
								npc_bullet.add(bullet_);
								npc_.bullet_timer = time_current;
							}
							
							if (sound) {
								if (allEntities.npc[npc_.type].weapon_sound > 0) {
									Runnable r = new MyRunnable(allEntities.npc[npc_.type].weapon_sound);
									handler.postDelayed(r, 0);
									//new Thread(r).start();
									//SoundManager.playSound(allEntities.npc[npc_.type].weapon_sound, 1);
								}
							}

						}
					}
					
					// ellenőrzés, hogy az npc elérte e az aktuális scriptben meghatározott koordinátákat
					if (npc_.move_x < 0 && allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].x > npc_.x) {
						npc_.x = allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].x; 
						npc_.current_script++;
						npc_.move_update = true;
					}					
					else if (npc_.move_x > 0 && allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].x < npc_.x) {
						npc_.x = allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].x; 
						npc_.current_script++;
						npc_.move_update = true;
					}					
					else if (npc_.move_y < 0 && allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].y > npc_.y) {
						npc_.y = allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].y; 
						npc_.current_script++;
						npc_.move_update = true;
					}					
					else if (npc_.move_y > 0 && allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].y < npc_.y) {
						npc_.y = allPatterns.movePattern[npc_.move_pattern].script[npc_.current_script].y; 
						npc_.current_script++;
						npc_.move_update = true;
					}					
				}

				// npc-vel való ütközés
				if (npc_.hp > 0 && spaceship.isalive && !allEntities.npc[npc_.type].earthling && !invincible) {
					if (!( spaceship.y + allUpgrades.ship[spaceship.type].height-allUpgrades.ship[spaceship.type].size_mod[3] < npc_.y || spaceship.y+allUpgrades.ship[spaceship.type].size_mod[2] > npc_.y + allEntities.npc[npc_.type].height
						|| spaceship.x + allUpgrades.ship[spaceship.type].width-allUpgrades.ship[spaceship.type].size_mod[1] < npc_.x || spaceship.x+allUpgrades.ship[spaceship.type].size_mod[0] > npc_.x + allEntities.npc[npc_.type].width ))
					{
						npc_.isalive = false;
						total_kill++;

						if (allEntities.npc[npc_.type].boss) spaceship.isalive = false;
						
						if (sound) {
							if (allEntities.npc[npc_.type].die_sound > 0) {
								SoundManager.playSound(allEntities.npc[npc_.type].die_sound, 1);
							}
						}
						if (vibration) {
							if (allEntities.npc[npc_.type].vibration >= 0) {
								vibrator.vibrate(vibration_pattern[allEntities.npc[npc_.type].vibration]);
							}
							// vibrate on collision
							vibrator.vibrate(vibration_pattern[0]);
						}
						float difficulty_mod = 1.0f;
						if (difficulty == 0) {
							difficulty_mod = 0.8f;
						} else if (difficulty == 2) {
							difficulty_mod = (1+	(dif_hard-1)/2	);
						}

						if (allEntities.npc[npc_.type].collision_damage_type == 0) {
							spaceship_damage_value = (int)(allEntities.npc[npc_.type].collision_damage * (1-(spaceship.collisionmod-1)) * difficulty_mod);
						} else 
						if (allEntities.npc[npc_.type].collision_damage_type == 1) {
							spaceship_shielddmg_value = (int)(allEntities.npc[npc_.type].collision_damage * (1-(spaceship.collisionmod-1)) * difficulty_mod);
						} else 
						if (allEntities.npc[npc_.type].collision_damage_type == 2) {
							spaceship_damage_value = (int)(allEntities.npc[npc_.type].collision_damage*0.6667f * (1-(spaceship.collisionmod-1)) * difficulty_mod);
							spaceship_hulldmg_value = (int)(allEntities.npc[npc_.type].collision_damage*0.3334f * (1-(spaceship.collisionmod-1)) * difficulty_mod);
						} else
						if (allEntities.npc[npc_.type].collision_damage_type == 3) {
							spaceship_damage_value = (int)(allEntities.npc[npc_.type].collision_damage * (1-(spaceship.collisionmod-1)) * difficulty_mod);
							
							// 50 % to freeze spaceship for 1 sec
							int percent = (int)Math.ceil(Math.random()*100);
							if (percent < 50) {
								bmenu_button_down_any = false;
								bmenu_button_down[0] = false; 
								bmenu_button_down[1] = false; 
								bmenu_button_down[2] = false; 
								bmenu_button_down[3] = false; 
								spaceship.freeze = true;
								spaceship.freeze_timer = time_current;
								spaceship_bullet_timer = 0;
								spaceship_bullet_launcher = false;
								spaceship_bullet_launch = false;
								wing_bullet_launch = false;
								//wing_bullet_timer = 0;
								autofire = false;
								booster = false;
								booster_value = 1f;
							}
						}  

						
					}
				}
					
					
			} // isalive
			else {
				boolean drop = false;
				if (allEntities.npc[npc_.type].die_effect == 0) {
					if (random < allEntities.npc[npc_.type].drop_chance * drop_szorzo +spaceship.dropchancemod && npc_.drop) {
						drop = true;
					}
					remove_list_npc.add(npc.indexOf(npc_)); 
				} else if (npc_.explosion_loop >= 0) {
					npc_.explosion_loop++;
					if (npc_.explosion_loop >= explosion_loop_scale * allExplosions.explosion[allEntities.npc[npc_.type].die_effect-1].picture_num) {
						npc_.explosion_loop = -1;
						remove_list_npc.add(npc.indexOf(npc_));
						if (random < allEntities.npc[npc_.type].drop_chance * drop_szorzo +spaceship.dropchancemod && npc_.drop) {
							drop = true;
						}
					}
				// already exploded, just remove it
				} else {
					remove_list_npc.add(npc.indexOf(npc_));
				}
				
				if (drop) {
					npc_.drop = false;
					Pickup pickup_ = new Pickup();
					pickup_.active = true;
					pickup_.timer = time_current + 10000;

					int percent1 = (int)Math.ceil(Math.random()*100);
					int dtype = 0; // 1-3: spec1-3, 	4-7: mix1-4, 	8-9: hp1-2, 	10-11: ly1-2
					// NOT VALID CASE
					if (allEntities.npc[npc_.type].drop == 0) {
						dtype = 10;
					} else
					// 1
					if (allEntities.npc[npc_.type].drop == 1) {
						// 1-3: spec1-3, 	4-7: mix1-4, 	8-9: hp1-2, 	10-11: ly1-2
						if (percent1 < 80) dtype = 10;
						else if (percent1 < 95) dtype = 1;
						else dtype = 8;
					} else
					// 2
					if (allEntities.npc[npc_.type].drop == 2) {
						// 1-3: spec1-3, 	4-7: mix1-4, 	8-9: hp1-2, 	10-11: ly1-2
						if (percent1 < 40) dtype = 5;
						else if (percent1 < 65) dtype = 4;
						else if (percent1 < 75) dtype = 6;
						else if (percent1 < 80) dtype = 7;
						else if (percent1 < 95) dtype = 2;
						else dtype = 1;
					} else
					// 3
					if (allEntities.npc[npc_.type].drop == 3) {
						// 1-3: spec1-3, 	4-7: mix1-4, 	8-9: hp1-2, 	10-11: ly1-2
						if (percent1 < 35) dtype = 1;
						else if (percent1 < 55) dtype = 2;
						else if (percent1 < 85) dtype = 6;
						else dtype = 5;
					} else
					// 4
					if (allEntities.npc[npc_.type].drop == 4) {
						// 1-3: spec1-3, 	4-7: mix1-4, 	8-9: hp1-2, 	10-11: ly1-2
						if (percent1 < 60) dtype = 11;
						else if (percent1 < 80) dtype = 1;
						else if (percent1 < 85) dtype = 2;
						else if (percent1 < 95) dtype = 8;
						else dtype = 9;
					} else
					// 5
					if (allEntities.npc[npc_.type].drop == 5) {
						// 1-3: spec1-3, 	4-7: mix1-4, 	8-9: hp1-2, 	10-11: ly1-2
						if (percent1 < 35) dtype = 3;
						else if (percent1 < 55) dtype = 2;
						else if (percent1 < 85) dtype = 6;
						else dtype = 7;
					} else
					// 6
					if (allEntities.npc[npc_.type].drop == 6) {
						if (percent1 < 80) dtype = 10;
						else dtype = 11;
					} else
					// 7
					if (allEntities.npc[npc_.type].drop == 7) {
						if (percent1 < 60) dtype = 11;
						else dtype = 10;
					} else
					// 8
					if (allEntities.npc[npc_.type].drop == 8) {
						dtype = 0;
					}
					
					//Random generator = new Random();
					
					int percent2 = (int)Math.ceil(Math.random()*100);
					// 1-3: spec1-3, 	4-7: mix1-4, 	8-9: hp1-2, 	10-11: ly1-2
					switch (dtype) {
						case 1:
							if (percent2 < 16) pickup_.type = 19;
							else if (percent2 < 26) pickup_.type = 25;
							else if (percent2 < 36) pickup_.type = 22;
							else if (percent2 < 42) pickup_.type = 31;
							else if (percent2 < 58) pickup_.type = 16;
							else if (percent2 < 68) pickup_.type = 28;
							else if (percent2 < 74) pickup_.type = 15;
							else if (percent2 < 85) pickup_.type = 12;
							else if (percent2 < 92) pickup_.type = 36; 
							else if (percent2 < 98) pickup_.type = 37; 
							else pickup_.type = 38;
							break;
						case 2:
							if (percent2 < 16) pickup_.type = 20;
							else if (percent2 < 26) pickup_.type = 26;
							else if (percent2 < 36) pickup_.type = 23;
							else if (percent2 < 42) pickup_.type = 32;
							else if (percent2 < 58) pickup_.type = 17;
							else if (percent2 < 68) pickup_.type = 29;
							else if (percent2 < 74) pickup_.type = 35;
							else if (percent2 < 85) pickup_.type = 13;
							else if (percent2 < 91) pickup_.type = 36; 
							else if (percent2 < 98) pickup_.type = 37; 
							else pickup_.type = 38;
							break;
						case 3:
							if (percent2 < 16) pickup_.type = 21;
							else if (percent2 < 26) pickup_.type = 27;
							else if (percent2 < 36) pickup_.type = 24;
							else if (percent2 < 42) pickup_.type = 33;
							else if (percent2 < 58) pickup_.type = 18;
							else if (percent2 < 68) pickup_.type = 30;
							else if (percent2 < 74) pickup_.type = 34;
							else if (percent2 < 85) pickup_.type = 14;
							else if (percent2 < 90) pickup_.type = 36; 
							else if (percent2 < 95) pickup_.type = 37; 
							else pickup_.type = 38;
							break;
						case 4:
							if (percent2 < 40) pickup_.type = 0;
							else if (percent2 < 73) pickup_.type = 4;
							else pickup_.type = 8;
							break;
						case 5:
							if (percent2 < 40) pickup_.type = 1;
							else if (percent2 < 73) pickup_.type = 5;
							else pickup_.type = 9;
							break;
						case 6:
							if (percent2 < 40) pickup_.type = 2;
							else if (percent2 < 73) pickup_.type = 6;
							else pickup_.type = 10;
							break;
						case 7:
							if (percent2 < 40) pickup_.type = 3;
							else if (percent2 < 73) pickup_.type = 7;
							else pickup_.type = 11;
							break;
						case 8:
							if (percent2 < 35) pickup_.type = 4;
							else if (percent2 < 50) pickup_.type = 5;
							else if (percent2 < 85) pickup_.type = 8;
							else pickup_.type = 9;
							break;
						case 9:
							if (percent2 < 35) pickup_.type = 6;
							else if (percent2 < 50) pickup_.type = 7;
							else if (percent2 < 85) pickup_.type = 10;
							else pickup_.type = 11;
							break;
						case 10:
							if (percent2 < 65) pickup_.type = 0;
							else pickup_.type = 1;
							break;
						case 11:
							if (percent2 < 65) pickup_.type = 2;
							else pickup_.type = 3;
							break;
						// NOT VALID CASE
						default:
							pickup_.type = 0;
							break;
					}
					
					//helper[2] = Integer.toString(percent1);
					//helper[3] = Integer.toString(percent2);
					
					pickup_.x = npc_.x + allEntities.npc[npc_.type].width / 2 - allEntities.pickup[pickup_.type].width / 2;
					pickup_.y = npc_.y + allEntities.npc[npc_.type].height / 2 - allEntities.pickup[pickup_.type].height / 2;
					if (pickup_.x < 0) pickup_.x = 0; 
					if (pickup_.x > (int)(hd*480) - allEntities.pickup[pickup_.type].width) pickup_.x = (int)(hd*480) - allEntities.pickup[pickup_.type].width;
					if (pickup_.y < vertical_move_max + allEntities.pickup[pickup_.type].height / 2) pickup_.y = vertical_move_max + allEntities.pickup[pickup_.type].height / 2;
					if (pickup_.y > (int)(hd*800) - allEntities.pickup[pickup_.type].height / 2) pickup_.y = (int)(hd*800) - allEntities.pickup[pickup_.type].height / 2;
					pickup.add(pickup_);
				}
			}
			
			
		}
		Collections.sort(remove_list_npc, Collections.reverseOrder());

		// remove all items on remove_list from npc
		for(int i: remove_list_npc){
			
			try {
				if (npc.contains(npc.get(i))) {
					npc.remove(i);
					//break;
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
				

		}
		remove_list_pickup.clear();
		for(Pickup pickup_: pickup){
			if (pickup_.active) {  
				if (spaceship.isalive && pickup_.floatingtext < 0) {
					if (!( spaceship.y + allUpgrades.ship[spaceship.type].height-allUpgrades.ship[spaceship.type].size_mod[3] < pickup_.y || spaceship.y+allUpgrades.ship[spaceship.type].size_mod[2] > pickup_.y + allEntities.pickup[pickup_.type].height
							|| spaceship.x + allUpgrades.ship[spaceship.type].width-allUpgrades.ship[spaceship.type].size_mod[1] < pickup_.x || spaceship.x+allUpgrades.ship[spaceship.type].size_mod[0] > pickup_.x + allEntities.pickup[pickup_.type].width ))
					{
						//pickup_.active = false;
						pickup_.floatingtext = time_current + 3000;
						if (allEntities.pickup[pickup_.type].hp > 0) { spaceship.hp += allEntities.pickup[pickup_.type].hp; if (spaceship.hp > spaceship.hp_max) spaceship.hp = spaceship.hp_max; } 
						if (allEntities.pickup[pickup_.type].shield > 0) { spaceship.shield += allEntities.pickup[pickup_.type].shield; if (spaceship.shield > spaceship.shield_max) spaceship.shield = spaceship.shield_max; } 
						float difmod = 1f; if (difficulty == 0) difmod = 0.5f; else if (difficulty == 2) difmod = 1.5f;
						if (allEntities.pickup[pickup_.type].ly > 0) { missionLY += allEntities.pickup[pickup_.type].ly * spaceship.lypickupmod * difmod; }
						if (allEntities.pickup[pickup_.type].damage > 0) { spaceship_damage_value += allEntities.pickup[pickup_.type].damage; }
						if (allEntities.pickup[pickup_.type].spec >= 0) { pickup_special_launch = allEntities.pickup[pickup_.type].spec; }
						
						total_pickup++;
						
						if (sound) {
							if (allEntities.pickup[pickup_.type].sound > 0) {
								SoundManager.playSound(allEntities.pickup[pickup_.type].sound, 1);
							}
						}
					}
				}
				
				// loop
				if (allEntities.pickup[pickup_.type].anim >= 0) {
					pickup_.loop++;
					if (pickup_.loop >= explosion_loop_scale * allExplosions.explosion[allEntities.pickup[pickup_.type].anim].picture_num) { 
						pickup_.loop = 0;
					}
				}
				
				//Log.i("Randomizer", Integer.toString((int)(pickup_.loop/explosion_loop_scale)));
				
				if (time_current > pickup_.timer && pickup_.floatingtext < 0) {
					pickup_.active = false;
				}
				if (time_current > pickup_.floatingtext && pickup_.floatingtext >= 0) {
					pickup_.active = false;
				}
				if (pickup_.floatingtext >= 0) {
					if (allEntities.pickup[pickup_.type].die_effect > 0 && pickup_.explosion_loop > -1) {
						pickup_.explosion_loop++;
						if (pickup_.explosion_loop >= explosion_loop_scale * allExplosions.explosion[allEntities.pickup[pickup_.type].die_effect-1].picture_num) {
							pickup_.explosion_loop = -1;
						}
					}
				}
			} else {
				remove_list_pickup.add(pickup.indexOf(pickup_));
			}
		}
		
		Collections.sort(remove_list_pickup, Collections.reverseOrder());

		for(int i: remove_list_pickup){
			
			try {
				if (pickup.contains(pickup.get(i))) {
					pickup.remove(i);
					//break;
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
				

		}		
		
		// spaceship special használat
		if ( (spaceship_special_launch >= 0 || pickup_special_launch >=0 )&& spaceship.isalive) {
			Spaceship_special_shoot(gl);
		}
		
		if (freeze && !gravity && endmission_state==0) {
			if (time_current > freeze_timer) {
				freeze = false;
			}
			
		}
		if (invincible) {
			if (time_current > invincible_timer) invincible = false;
		}	
		if (autofire) {
			if (autofire_timer > 0 && time_current > autofire_timer) autofire = false;
		}
		if (homing) {
			boolean npc_found = false;
			for(Entity npc_: npc){
				if (npc_.hp > 0) {
					npc_found = true;
					
					// uj x es y koordinatak
					float x = npc_.x + allEntities.npc[npc_.type].width / 2;
					float y = npc_.y + allEntities.npc[npc_.type].height / 2;
					// x,y valtozas merteke
					float a = Math.abs(homing_bullet.x - x);
					float b = Math.abs(homing_bullet.y - y);
					float c = 0;
					float xmod = 1; float ymod = 1;
					if (a == 0) { xmod = 0; }
					else if (b == 0) { ymod = 0; }
					// atlos mozgas
					else if (a > 0 && b > 0) {
						c = FloatMath.sqrt( a*a + b*b ); 
						xmod = a*c / (a+b);
						ymod = b*c / (a+b);
						// gyök 2-vel szorzás az átlós mozgás gyorsítására
						xmod = 1.4142f * xmod * (FloatMath.sqrt(1 / (a*a+b*b)));
						ymod = 1.4142f * ymod * (FloatMath.sqrt(1 / (a*a+b*b)));
					}
					// mozgas iranya, elojel
					int elojelx = 1;
					int elojely = 1;
					if (homing_bullet.x > x) elojelx = -1;
					if (homing_bullet.y > y) elojely = -1;
					// x koordináta változás
					homing_bullet.xmove = elojelx * xmod * 5;
					// y koordináta változás
					homing_bullet.ymove = elojely * ymod * 5;
					if (a > 0 && b > 0 && c > 0) { 
						if (homing_bullet.xmove > 0 && homing_bullet.ymove < 0) { homing_bullet.rotation = (float)(57.295779513*Math.asin((float)a/c)); }
						else if (homing_bullet.xmove > 0 && homing_bullet.ymove > 0) { homing_bullet.rotation = 90+(float)(57.295779513*Math.asin((float)b/c)); }
						else if (homing_bullet.xmove < 0 && homing_bullet.ymove > 0) { homing_bullet.rotation = 180+(float)(57.295779513*Math.asin((float)a/c)); }
						else if (homing_bullet.xmove < 0 && homing_bullet.ymove < 0) { homing_bullet.rotation = 270+(float)(57.295779513*Math.asin((float)b/c)); }
					} else {
						if (a == 0 && homing_bullet.ymove < 0) { homing_bullet.rotation = 0; }
						else if (a == 0 && homing_bullet.ymove > 0) { homing_bullet.rotation = 180; }
						else if (b == 0 && homing_bullet.xmove < 0) { homing_bullet.rotation = 270; }
						else if (b == 0 && homing_bullet.xmove > 0) { homing_bullet.rotation = 90; }
					}

					homing_bullet.x = homing_bullet.x + homing_bullet.xmove * tmod;
					homing_bullet.y = homing_bullet.y + homing_bullet.ymove * tmod;
					// bullet felso kozepso koordinatajanak ellenorzese, utkozik e npc-vel
					int bullet_center = (int)(allBullets.bullet[homing_bullet.type].width/2);
					if (homing_bullet.x+bullet_center > npc_.x && homing_bullet.x+bullet_center < npc_.x+allEntities.npc[npc_.type].width &&
						homing_bullet.y > npc_.y && homing_bullet.y < npc_.y+allEntities.npc[npc_.type].height)
					{
						homing_bullet.active = false;
						homing = false;
						npc_.hit_timer = 1;
						npc_.hit_timer_hp = 1;
						// ha van az npc-nek pajzsa is
						if (npc_.shield > 0) { 
							npc_.shield -= homing_bullet.damage;
							if (npc_.shield < 0) {
								npc_.hp += npc_.shield; npc_.shield = 0;
							}
						// ha nincs pajzs
						} else {
							npc_.hp -= homing_bullet.damage;
						}
						// ha elfogyott a hp-ja
						if (npc_.hp <= 0) { 
							npc_.hp = 0; 
							npc_.isalive = false;
							special_kill++;
							total_kill++;
							if (sound) {
								if (allEntities.npc[npc_.type].die_sound > 0) {
									SoundManager.playSound(allEntities.npc[npc_.type].die_sound, 1);
								}
							}
							if (vibration) {
								if (allEntities.npc[npc_.type].vibration >= 0) {
									vibrator.vibrate(vibration_pattern[allEntities.npc[npc_.type].vibration]);
								}
							}
							npc_.drop = true;
							float difmod = 1f; if (difficulty == 0) difmod = 0.5f; else if (difficulty == 2) difmod = 1.5f;
							missionLY += allEntities.npc[npc_.type].lyvalue * ly_szorzo * spaceship.lymod * difmod;
							//missionLY += (allEntities.npc[npc_.type].shield_max+allEntities.npc[npc_.type].hp_max) * ly_szorzo * spaceship.lymod * difmod;
						}
						
					}
					break;
				}
			}
			if (!npc_found) {
				homing_bullet.xmove = 0;
				homing_bullet.ymove = - 1 * 5;
				homing_bullet.rotation = 0.0f;
				homing_bullet.x = homing_bullet.x + homing_bullet.xmove * tmod;
				homing_bullet.y = homing_bullet.y + homing_bullet.ymove * tmod;
				if (homing_bullet.y < -allBullets.bullet[homing_bullet.type].height || homing_bullet.x < -allBullets.bullet[homing_bullet.type].width || homing_bullet.x > dimensionX) { 
					homing_bullet.active = false;
					homing = false;
				}
			}
		}
		if (cone) {
			if (time_current > cone_timer) cone = false;
		}
		if (gravity) {
			
			gravity_rotate += (float)2 * tmod;
			if (gravity_rotate >= 360) gravity_rotate = gravity_rotate - 360;
			
			if (time_current > gravity_timer) { 
				gravity = false; 
				if (!freeze) freeze = false;
				for(Entity npc_: npc){
					if (npc_.hp > 0 && !allEntities.npc[npc_.type].earthling) {
						npc_.move_update = true;
					}
				}
			} else {
				for(Entity npc_: npc){
					if (npc_.hp > 0 && !allEntities.npc[npc_.type].earthling && allEntities.npc[npc_.type].gravitable) {
						// uj x es y koordinatak
						float x = 240 - allEntities.npc[npc_.type].width / 2;
						float y = 360 - allEntities.npc[npc_.type].height / 2;
						// x,y valtozas merteke
						float a = Math.abs(npc_.x - x);
						float b = Math.abs(npc_.y - y);
						if (a > 5 && b > 5) {
							float c = 0;
							float xmod = 1; float ymod = 1;
							if (a == 0) { xmod = 0; }
							else if (b == 0) { ymod = 0; }
							// atlos mozgas
							else if (a > 0 && b > 0) {
								c = FloatMath.sqrt( a*a + b*b ); 
								xmod = a*c / (a+b);
								ymod = b*c / (a+b);
								// gyök 2-vel szorzás az átlós mozgás gyorsítására
								xmod = 1.4142f * xmod * (FloatMath.sqrt(1 / (a*a+b*b)));
								ymod = 1.4142f * ymod * (FloatMath.sqrt(1 / (a*a+b*b)));
							}
							// mozgas iranya, elojel
							int elojelx = 1;
							int elojely = 1;
							if (npc_.x > x) elojelx = -1;
							if (npc_.y > y) elojely = -1;
							// x koordináta változás
							float move_x = elojelx * xmod * 1.0f;
							// y koordináta változás
							float move_y = elojely * ymod * 1.0f;
							npc_.x = npc_.x + move_x * tmod;
							npc_.y = npc_.y + move_y * tmod;
						}
					}
				}
			}
		}
		if (spaceship_bullet_launcher || autofire) {
			if (time_current >= spaceship_bullet_timer) {
				spaceship_bullet_launch = true;
				
				// launch wing bullets 
				//if (!wing_skipbullet) {
					wing_bullet_launch = true;
				//}
				//wing_skipbullet = !wing_skipbullet;
				spaceship_bullet_timer = time_current + (int)(1 / (allUpgrades.turret[spaceship.turret[spaceship.turret_active]].rateoffire + spaceship.rofmod) * 1000);
			}
			//if (time_current >= wing_bullet_timer) {
			//	wing_bullet_launch = true;
				//wing_bullet_timer = time_current + (int)(1f / 2f * 1000);
			//}
		}
		
		if (booster) {
			if (time_current > booster_timer) {
				booster = false;
				booster_value = 1f;
			}
		}

		if (wings) {
			if (time_current > wings_timer) {
				wings = false;
			}
		}
		
		if (extraturret >= 0) {
			if (time_current > extraturret_time) {
				extraturret = -1;
				spaceship.turret[spaceship.turret_active] = prevturret;
				prevturret = -1;
			}
		}

		if (extramodifier >= 0) {
			if (time_current > extramodifier_time) {
				extramodifier = -1;
				spaceship.modifier[spaceship.modifier_active] = prevmodifier;
				prevmodifier = -1;
			}
		}

		if (instantshield) {
			if (time_current > instantshield_timer) { 
				spaceship.shield_max -= 10;
				if (spaceship.shield > spaceship.shield_max) spaceship.shield = spaceship.shield_max;
				instantshield = false;
			} //else {
			//	if (spaceship.shield < spaceship.shield_max && spaceship.shield_recharge == 0) {
			//		if (time_current > instantshield_recharge_timer + (20000 / 3)) {
			////			spaceship.shield++;
			//			if (spaceship.shield > spaceship.shield_max) spaceship.shield = spaceship.shield_max;
			//			instantshield_recharge_timer = time_current;
			//		}
			//	}
			//}
		}
		
		if (mining) {
			if (time_current > mining_timer) {
				mining = false;
				for(Entity npc_: npc){
					if (npc_.mining) {
						npc_.mining = false;
						npc_.isalive = false;
						npc_.drop = true;
						float difmod = 1f; if (difficulty == 0) difmod = 0.5f; else if (difficulty == 2) difmod = 1.5f;
						missionLY += allEntities.npc[npc_.type].lyvalue * ly_szorzo * spaceship.lymod * difmod;
						//missionLY += (allEntities.npc[npc_.type].shield_max+allEntities.npc[npc_.type].hp_max) * ly_szorzo * spaceship.lymod * difmod;
						
						break;
					}
				}
			}
		}
		
		if (rocket > 0) {
			if (time_current > rocket_time) {
				
				Bullet b_;
				for (int i=0;i<8;i++) {
					b_ = new Bullet();
					b_.active = true;
					b_.type = allUpgrades.special[rocket_type].bullet;
			    	b_.damage = allUpgrades.special[rocket_type].damage;
			    	b_.rotation = 0.0f+i*45.0f;
			    	b_.x = spaceship.x + allUpgrades.ship[spaceship.type].width/2;
			    	b_.y = spaceship.y + allUpgrades.ship[spaceship.type].height/2;
			    	float bspeed = allUpgrades.turret[0].bulletspeed /2;
			    	switch (i) {
			    	case 0:
				    	b_.xmove = 0;
				    	b_.ymove = tmod * (bspeed);
				    	break;
			    	case 1:
				    	b_.xmove = (0.7071f)* tmod * (bspeed);
				    	b_.ymove = (0.7071f)* tmod * (bspeed);
				    	break;
			    	case 2:
				    	b_.xmove = tmod * (bspeed);
				    	b_.ymove = 0;
				    	break;
			    	case 3:
				    	b_.xmove = (0.7071f)* tmod * (bspeed);
				    	b_.ymove = - (0.7071f)* tmod * (bspeed);
				    	break;
			    	case 4:
				    	b_.xmove = 0;
				    	b_.ymove = - tmod * (bspeed);
				    	break;
			    	case 5:
				    	b_.xmove = - (0.7071f)* tmod * (bspeed);
				    	b_.ymove = - (0.7071f)* tmod * (bspeed);
				    	break;
			    	case 6:
				    	b_.xmove = - tmod * (bspeed);
				    	b_.ymove = 0;
				    	break;
			    	case 7:
				    	b_.xmove = - (0.7071f)* tmod * (bspeed);
				    	b_.ymove = (0.7071f)* tmod * (bspeed);
				    	break;
			    	}
			    	spaceship_bullet.add(b_);
				}	
				rocket += 1;
				
				if (rocket == 2) {
					rocket_time+=300;
				} else {
					rocket = 0;
				}
				
				
				/*
				rocket_time+=150;
				Bullet o_;
				o_ = new Bullet();
				o_.active = true;
				o_.type = allUpgrades.special[rocket_type].bullet;
		    	o_.damage = allUpgrades.special[rocket_type].damage;
		    	o_.rotation = -90.0f+(rocket-1)*45.0f;
		    	o_.x = spaceship.x + allUpgrades.ship[spaceship.type].width/2;
		    	o_.y = spaceship.y + allUpgrades.ship[spaceship.type].height/2;
		    	float bspeed = allUpgrades.turret[0].bulletspeed /3;
		    	switch (rocket) {
		    	case 1:
			    	o_.xmove = - tmod * (bspeed);
			    	o_.ymove = 0;
			    	break;
		    	case 2:
			    	o_.xmove = - (0.7071f)* tmod * (bspeed);
			    	o_.ymove = (0.7071f)* tmod * (bspeed);
			    	break;
		    	case 3:
			    	o_.xmove = 0;
			    	o_.ymove = tmod * (bspeed);
			    	break;
		    	case 4:
			    	o_.xmove = (0.7071f)* tmod * (bspeed);
			    	o_.ymove = (0.7071f)* tmod * (bspeed);
			    	break;
		    	case 5:
			    	o_.xmove = tmod * (bspeed);
			    	o_.ymove = 0;
			    	break;
		    	case 6:
			    	o_.xmove = (0.7071f)* tmod * (bspeed);
			    	o_.ymove = - (0.7071f)* tmod * (bspeed);
			    	break;
		    	case 7:
			    	o_.xmove = 0;
			    	o_.ymove = - tmod * (bspeed);
			    	break;
		    	case 8:
			    	o_.xmove = - (0.7071f)* tmod * (bspeed);
			    	o_.ymove = - (0.7071f)* tmod * (bspeed);
			    	break;
		    	}
		    	spaceship_bullet.add(o_);
		    	rocket++;
		    	*/
				//if (sound) {
				//	if (allUpgrades.special[48].sound > 0) {
				//		SoundManager.playSound(allUpgrades.special[48].sound, 1);
				//	}
				//} 

			}


			
		}
		
		if (crazy) {
			// uj x es y koordinatak
			float x = 0; float y = 0;
			if (crazy_update) {
				if (crazy_update_num < 9) {
					x = crazy_pattern[0][(int)((int)Math.ceil(Math.random()*100)/14.5f)]; // 8 pattern, 0..7
					y = crazy_pattern[1][(int)((int)Math.ceil(Math.random()*100)/14.5f)];
				} else {
					x = 240;
					y = -100;
				}
				crazy_update = false;
				crazy_update_num++;
				crazy_x = (int)x;
				crazy_y = (int)y;

				// x,y valtozas merteke
				float a = Math.abs(crazy_bullet.x - x);
				float b = Math.abs(crazy_bullet.y - y);
				float c = 0;
				float xmod = 1; float ymod = 1;
				if (a == 0) { xmod = 0; }
				else if (b == 0) { ymod = 0; }
				// atlos mozgas
				else if (a > 0 && b > 0) {
					c = FloatMath.sqrt( a*a + b*b ); 
					xmod = a*c / (a+b);
					ymod = b*c / (a+b);
					// gyök 2-vel szorzás az átlós mozgás gyorsítására
					xmod = 1.4142f * xmod * (FloatMath.sqrt(1 / (a*a+b*b)));
					ymod = 1.4142f * ymod * (FloatMath.sqrt(1 / (a*a+b*b)));
				}
				// mozgas iranya, elojel
				int elojelx = 1;
				int elojely = 1;
				if (crazy_bullet.x > x) elojelx = -1;
				if (crazy_bullet.y > y) elojely = -1;
				// x koordináta változás
				crazy_bullet.xmove = elojelx * xmod * 3;
				// y koordináta változás
				crazy_bullet.ymove = elojely * ymod * 3;
				if (a > 0 && b > 0 && c > 0) { 
					if (crazy_bullet.xmove > 0 && crazy_bullet.ymove < 0) { crazy_bullet.rotation = (float)(57.295779513*Math.asin((float)a/c)); }
					else if (crazy_bullet.xmove > 0 && crazy_bullet.ymove > 0) { crazy_bullet.rotation = 90+(float)(57.295779513*Math.asin((float)b/c)); }
					else if (crazy_bullet.xmove < 0 && crazy_bullet.ymove > 0) { crazy_bullet.rotation = 180+(float)(57.295779513*Math.asin((float)a/c)); }
					else if (crazy_bullet.xmove < 0 && crazy_bullet.ymove < 0) { crazy_bullet.rotation = 270+(float)(57.295779513*Math.asin((float)b/c)); }
				} else {
					if (a == 0 && crazy_bullet.ymove < 0) { crazy_bullet.rotation = 0; }
					else if (a == 0 && crazy_bullet.ymove > 0) { crazy_bullet.rotation = 180; }
					else if (b == 0 && crazy_bullet.xmove < 0) { crazy_bullet.rotation = 270; }
					else if (b == 0 && crazy_bullet.xmove > 0) { crazy_bullet.rotation = 90; }
				}
			}
				
			crazy_bullet.x = crazy_bullet.x + crazy_bullet.xmove * tmod;
			crazy_bullet.y = crazy_bullet.y + crazy_bullet.ymove * tmod;
			
			if (crazy_update_num < 9) {
				if (crazy_bullet.xmove < 0 && crazy_x > crazy_bullet.x) {
					crazy_update = true;
				}					
				else if (crazy_bullet.xmove > 0 && crazy_x < crazy_bullet.x) {
					crazy_update = true;
				}					
				else if (crazy_bullet.ymove < 0 && crazy_y > crazy_bullet.y) {
					crazy_update = true;
				}					
				else if (crazy_bullet.ymove > 0 && crazy_y < crazy_bullet.y) {
					crazy_update = true;
				}
			} else 			
			if (crazy_bullet.y < -allBullets.bullet[crazy_bullet.type].height || crazy_bullet.x < -allBullets.bullet[crazy_bullet.type].width || crazy_bullet.x > dimensionX) { 
				crazy_bullet.active = false;
				crazy = false;
			}
			
			// bullet felso kozepso koordinatajanak ellenorzese, utkozik e npc-vel
			for(Entity npc_: npc){
				if (npc_.hp > 0) {
							
					int bullet_center = (int)(allBullets.bullet[crazy_bullet.type].width/2);
					if (crazy_bullet.x+bullet_center > npc_.x && crazy_bullet.x+bullet_center < npc_.x+allEntities.npc[npc_.type].width &&
						crazy_bullet.y > npc_.y && crazy_bullet.y < npc_.y+allEntities.npc[npc_.type].height)
					{
						crazy_bullet.active = false;
						crazy = false;
						npc_.hit_timer = 1;
						npc_.hit_timer_hp = 1;
						// ha van az npc-nek pajzsa is
						if (npc_.shield > 0) { 
							npc_.shield -= crazy_bullet.damage;
							if (npc_.shield < 0) {
								npc_.hp += npc_.shield; npc_.shield = 0;
							}
						// ha nincs pajzs
						} else {
							npc_.hp -= crazy_bullet.damage;
						}
						// ha elfogyott a hp-ja
						if (npc_.hp <= 0) { 
							npc_.hp = 0; 
							npc_.isalive = false;
							special_kill++;
							total_kill++;

							if (sound) {
								if (allEntities.npc[npc_.type].die_sound > 0) {
									SoundManager.playSound(allEntities.npc[npc_.type].die_sound, 1);
								}
							}
							if (vibration) {
								if (allEntities.npc[npc_.type].vibration >= 0) {
									vibrator.vibrate(vibration_pattern[allEntities.npc[npc_.type].vibration]);
								}
							}
							npc_.drop = true;
							float difmod = 1f; if (difficulty == 0) difmod = 0.5f; else if (difficulty == 2) difmod = 1.5f;
							
							missionLY += allEntities.npc[npc_.type].lyvalue * ly_szorzo * spaceship.lymod * difmod;
							//missionLY += (allEntities.npc[npc_.type].shield_max+allEntities.npc[npc_.type].hp_max) * ly_szorzo * spaceship.lymod * difmod;
							
						}
						
					}
					break;
				}
			}
		}

		// spaceship bullet kilovese
		if (spaceship_bullet_launch && spaceship.turret[spaceship.turret_active]>=0 && spaceship.isalive) {
			Spaceship_bullet_shoot(true);
		}
		if (wing_bullet_launch && spaceship.isalive && (wings || spaceship.winged)) {
			Wing_bullet_shoot();
		}

		// spaceship_bullet iteration
		remove_list_spaceship_bullet.clear();
		for(Bullet bullet_: spaceship_bullet){
			if (bullet_.active) {
				
				// bullet mozgás
				bullet_.x += bullet_.xmove;
				bullet_.y -= bullet_.ymove;
				
				if (bullet_.y > dimensionY || bullet_.y < -allBullets.bullet[bullet_.type].height || bullet_.x < -allBullets.bullet[bullet_.type].width || bullet_.x > dimensionX) { 
					bullet_.active = false;
				} else {
					// bullet ütközés figyelés
					for(Entity npc_: npc){
						if (npc_.isalive && npc_.hp > 0) {
							
							// spaceship bullet felso kozepso koordinatajanak ellenorzese, utkozik e npc-vel
							//int bullet_center = (int)(allBullets.bullet[bullet_.type].width/2);
							//if (bullet_.x+bullet_center > npc_.x && bullet_.x+bullet_center < npc_.x+allEntities.npc[npc_.type].width &&
							//	bullet_.y > npc_.y && bullet_.y < npc_.y+allEntities.npc[npc_.type].height)
							//{
							
							// VAGY !!! bullet teljes méretére vonatkozó ütközésfigyelés
							if (!( npc_.y + allEntities.npc[npc_.type].height < bullet_.y || npc_.y > bullet_.y + allBullets.bullet[bullet_.type].height
								|| npc_.x + allEntities.npc[npc_.type].width < bullet_.x || npc_.x > bullet_.x + allBullets.bullet[bullet_.type].width ))
							{	
								if (!bullet_.unstoppable) {
									bullet_.active = false;
								}
								npc_.hit_timer = 1;
								npc_.hit_timer_hp = 1;
								// ha van az npc-nek pajzsa is
								if (npc_.shield > 0) { 
									npc_.shield -= bullet_.damage;
									if (npc_.shield < 0) {
										npc_.hp += npc_.shield; npc_.shield = 0;
									}
								// ha nincs pajzs
								} else {
									npc_.hp -= bullet_.damage;
								}
								// ha elfogyott a hp-ja
								if (npc_.hp <= 0) { 
									npc_.hp = 0; 
									npc_.isalive = false;
									total_kill++;

									if (sound) {
										if (allEntities.npc[npc_.type].die_sound > 0) {
											SoundManager.playSound(allEntities.npc[npc_.type].die_sound, 1);
										}
									}
									if (vibration) {
										if (allEntities.npc[npc_.type].vibration >= 0) {
											vibrator.vibrate(vibration_pattern[allEntities.npc[npc_.type].vibration]);
										}
									}
									npc_.drop = true;
									float difmod = 1f; if (difficulty == 0) difmod = 0.5f; else if (difficulty == 2) difmod = 1.5f;
									missionLY += allEntities.npc[npc_.type].lyvalue * ly_szorzo * spaceship.lymod * difmod;
									//missionLY += (allEntities.npc[npc_.type].shield_max+allEntities.npc[npc_.type].hp_max) * ly_szorzo * spaceship.lymod * difmod;
									
								} 
								
								break;
							}
						}
					}
				}
			} else {
				remove_list_spaceship_bullet.add(spaceship_bullet.indexOf(bullet_));

			}
		}
		
		//helper[3] = "Bullet number:" + Integer.toString(spaceship_bullet.size());
		
		Collections.sort(remove_list_spaceship_bullet, Collections.reverseOrder());
		
		// remove all items on remove_list from spaceship_bullet
		for(int i: remove_list_spaceship_bullet){
			try {
				if (spaceship_bullet.contains(spaceship_bullet.get(i))) {
					spaceship_bullet.remove(i);
					// remove only 1 element at once
					// ismeretlen bug miatt ???
					// break;
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		
		
		// npc_bullet iteration
		remove_list_npc_bullet.clear();
		for(Bullet bullet_: npc_bullet){
			if (bullet_.active) {
				
				// bullet mozgás
				bullet_.x = bullet_.x + tmod * bullet_.xmove * (booster_value/2);
				bullet_.y = bullet_.y + tmod * bullet_.ymove * (booster_value);
				if (bullet_.y >= dimensionY + allBullets.bullet[bullet_.type].height) { 
					bullet_.active = false;
				}
				
				if (spaceship.isalive && !invincible) {
					// bullet ütközés figyelés
					// npc bullet also kozepso koordinatajanak ellenorzese, utkozik e spaceshippel
					//int[] bullet_center = { 
						// bullet elforgatasa miatt mashol van a bal felso koordinata !!!
					//	(int)(bullet_.x - allBullets.bullet[bullet_.type].width/2), 
					//	(int)(bullet_.y) 
					//};
					//if (bullet_center[0] > (int)(spaceship.x+allUpgrades.ship[spaceship.type].size_mod[0]) && bullet_center[0] < (int)(spaceship.x+allUpgrades.ship[spaceship.type].width-allUpgrades.ship[spaceship.type].size_mod[1]) &&
					//	bullet_center[1] > (int)(spaceship.y+allUpgrades.ship[spaceship.type].size_mod[2]) && bullet_center[1] < (int)(spaceship.y+allUpgrades.ship[spaceship.type].height-allUpgrades.ship[spaceship.type].size_mod[3]))
					//{
					
					// VAGY !!! bullet teljes méretére vonatkozó ütközésfigyelés
					if (!( spaceship.y + allUpgrades.ship[spaceship.type].height-allUpgrades.ship[spaceship.type].size_mod[3] < bullet_.y || spaceship.y+allUpgrades.ship[spaceship.type].size_mod[2] > bullet_.y + allBullets.bullet[bullet_.type].height
						|| spaceship.x + allUpgrades.ship[spaceship.type].width-allUpgrades.ship[spaceship.type].size_mod[1] < bullet_.x || spaceship.x+allUpgrades.ship[spaceship.type].size_mod[0] > bullet_.x + allBullets.bullet[bullet_.type].width ))
					{
						bullet_.active = false;
						
						if (bullet_.damage_type == 0) {
							spaceship_damage_value = bullet_.damage;
						} else 
						if (bullet_.damage_type == 1) {
							spaceship_shielddmg_value = bullet_.damage;
						} else 
						if (bullet_.damage_type == 2) {
							spaceship_damage_value = (int)(bullet_.damage*0.6667f);
							spaceship_hulldmg_value = (int)(bullet_.damage*0.3334f);
						} else
						if (bullet_.damage_type == 3) {
							spaceship_damage_value = bullet_.damage;
							bmenu_button_down_any = false;
							bmenu_button_down[0] = false; 
							bmenu_button_down[1] = false; 
							bmenu_button_down[2] = false; 
							bmenu_button_down[3] = false; 
							spaceship.freeze = true;
							spaceship.freeze_timer = time_current;
							spaceship_bullet_timer = 0;
							spaceship_bullet_launcher = false;
							spaceship_bullet_launch = false;
							wing_bullet_launch = false;
							//wing_bullet_timer = 0;
							autofire = false;
							booster = false;
							booster_value = 1f;
						}
						
					}
				}
			} else {
				remove_list_npc_bullet.add(npc_bullet.indexOf(bullet_));

			}
		}
		
		Collections.sort(remove_list_npc_bullet, Collections.reverseOrder());
		
		// remove all items on remove_list from npc_bullet
		for(int i: remove_list_npc_bullet){
			
			try {
				if (npc_bullet.contains(npc_bullet.get(i))) {
					npc_bullet.remove(i);
					//break;
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			
		}
		
		// space ship regeneration, shield recharge
		if (spaceship.shield_recharge > 0 && spaceship.isalive) {
			if (spaceship.shield < spaceship.shield_max) {
				if (time_current > spaceship.shield_recharge_timer + (20000 / spaceship.shield_recharge)) {
					spaceship.shield++;
					if (spaceship.shield > spaceship.shield_max) spaceship.shield = spaceship.shield_max;
					spaceship.shield_recharge_timer = time_current;
				}
			}
		}
		if (spaceship.repair > 0 && spaceship.isalive) {
			if (spaceship.hp < spaceship.hp_max) {
				if (time_current > spaceship.repair_timer + (20000 / spaceship.repair)) {
					spaceship.hp++;
					if (spaceship.hp > spaceship.hp_max) spaceship.hp = spaceship.hp_max;
					spaceship.repair_timer = time_current;
				}
			}
		}
		
		// ha a spaceship sebzodott...
		if ((spaceship_shielddmg_value > 0 || spaceship_hulldmg_value > 0 || spaceship_damage_value > 0) 
			&& spaceship.isalive && !invincible && endmission_state==0 && !cinemamode) 
		{
			// damage shield only
			if (spaceship_shielddmg_value > 0) {
				if (spaceship.shield > 0) {
					spaceship.hit_timer = 1; 
					spaceship.shield -= spaceship_damage_value;
					if (spaceship.shield < 0) {
						spaceship.shield = 0;
					}
				}
			}
			// damage hull only
			if (spaceship_hulldmg_value > 0) {
				spaceship.hp -= spaceship_hulldmg_value;
			}
			// normal damage first shield, then hull
			if (spaceship_damage_value > 0) {
				// ha van pajzs
				if (spaceship.shield > 0) { 
					spaceship.hit_timer = 1; 
					spaceship.shield -= spaceship_damage_value;
					if (spaceship.shield < 0) {
						spaceship.hp += spaceship.shield; spaceship.shield = 0;
					}
				// ha nincs pajzs
				} else {
					spaceship.hp -= spaceship_damage_value;
				}
			}
			
			// HP FOGYAS PIROSITAS
			if (spaceship.hp+spaceship.shield < 10 && (float)(spaceship.hp+spaceship.shield)/spaceship.hp_max<0.5f) hp_timer = 1;
			
			// ha elfogyott a hp-ja
			if (spaceship.hp <= 0) { 
				spaceship.hp = 0; 
				// explosion
				if (sound) {
					if (allUpgrades.ship[spaceship.type].die_sound > 0) {
						SoundManager.playSound(allUpgrades.ship[spaceship.type].die_sound, 1);
					}
				}
				if (vibration) {
					if (allUpgrades.ship[spaceship.type].vibration >= 0) {
						vibrator.vibrate(vibration_pattern[allUpgrades.ship[spaceship.type].vibration]);
					}
				}
				spaceship.isalive = false;
				//nuke_timer = 1;
				
			}
			//spaceship_damage_value = 0;
		}
		spaceship_damage_value = 0;
		spaceship_shielddmg_value = 0;
		spaceship_hulldmg_value = 0;
		
		if (!spaceship.isalive && endmission_state==0) {
			spaceship.explosion_loop++;
			if (spaceship.explosion_loop >= explosion_loop_scale * allExplosions.explosion[allUpgrades.ship[spaceship.type].die_effect].picture_num + tmod*30) {
				freeze = true;
				endmission_success = false;
				
				if (extraturret >= 0) {
					extraturret = -1;
					spaceship.turret[spaceship.turret_active] = prevturret;
					prevturret = -1;
				}
				if (extramodifier >= 0) {
					extramodifier = -1;
					spaceship.modifier[spaceship.modifier_active] = prevmodifier;
					prevmodifier = -1;
				}
				
				if (music) { if (musicvolume > 0.2f) mediaplayer.setVolume(0.2f, 0.2f); }
				if(sound)SoundManager.playSound(37,1);
				
				sharefeed[0] = 0;
				sharefeed[1] = menusystem.selected_mission;
				sharefeed[2] = 0;
				sharefeed[3] = pref[current_profile].getInt("Galaxy_Completed", 0);
				sharefeed[4] = pref[current_profile].getInt("Mission_Completed", 0);

				End_Mission_Save(false);
				endmission_state = 1;
			}
		}
		
		// Calculating UPS - game logic update per second
        gameloops++;
        looptime_dif += GetTickCount() - looptime;
        looptime = GetTickCount();

	}
	
	// THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  
	// THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  
	// THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  
	// THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  
	// THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  
	// THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  
	// THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  THIS IS DRAWING  
	public void onDrawFrame(GL10 gl) {
	try {
		
		// Calculating FPS
		time_current_real = GetTickCount();
		time_dif = (float)(time_current_real - time_previous); if (time_dif == 0 || time_previous == 0) time_dif = 1;
		fps = (int)(1000 / time_dif);
		time_previous = time_current_real;

		if (!pause) { time_current = time_current_real; }
		
		//Clear Screen And Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);	
	
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();					//Reset The Current Modelview Matrix
		gl.glOrthof(0f, dimensionX, dimensionY, 0f, -1f, 1f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();					//Reset The Current Modelview Matrix 

		//if (firstrun) {
		//	firstrun = false;
		//	onSurfaceCreated_Init(gl);
		//}

		//loading_bg.draw(gl, 0, 0);

		// Google Ads Code
		if (!fullgame && menu!=0 && menu!=711) {
			adtimer+=time_dif;
			if (menu == 10 || menu == 108 || menu == 102) {
				
				if (menu == 108 && !showad) {
					showad = true;
					handler.sendEmptyMessage(2);
				} 
				else if ((menu == 10 || menu == 102) && endmission_state>0 && !showad) {
					showad = true;
					handler.sendEmptyMessage(2);
				}
				else if (menu == 10 && endmission_state<=0 && showad) {
					showad = false;
					handler.sendEmptyMessage(0);
				}
				
			} else {
				if (adtimer > 10000+adrandomtime && !showad) {
					showad = true;
					Log.w("Ads", "Msg 1 sent.");
					handler.sendEmptyMessage(1);
				} else if (adtimer > 45000+adrandomtime && showad) {
					showad = false;
					Log.w("Ads", "Msg 0 case 1 sent.");
					handler.sendEmptyMessage(0);
					adtimer = 0;
					adrandomtime = (int)Math.ceil(Math.random()*10000);
				} else if (adtimer <= 10000+adrandomtime && showad) {
					showad = false;
					Log.w("Ads", "Msg 0 case 2 sent.");
					handler.sendEmptyMessage(0);
					adrandomtime = (int)Math.ceil(Math.random()*10000);
				}
			}
		}

		
		switch (menu) {

//TBD		
			case 0 :
				//mainmenu_bg.draw(gl, 0, 0);
				menusystem.bgimage.draw(gl, 0, 0);
				if (!firstrun) Load_Start_Images(gl);
				firstrun = false;
				next_game_tick = GetTickCount();
			
				break;

				// LOGO
				case 711 :
					if (menusystem_update) {
						menusystem_update = false;
						//menusystem_update_menu = 7;
						//Log.i("Cell Planet", "Menu update called.");
						// release game entities
						menusystem.bgimage.release(gl);
						menusystem.bgimage.load(gl, context, R.drawable.logo);
						
						if (sound && music) {
							SoundManager.playSound(44, 1);
						}

					}

					//Log.i("Cell Planet", Long.toString(time_current - logo_timer));
					
					if (logo_reset) { logo_timer = time_current; logo_reset = false; }
					if (logo_start) { 
						logo_start = false;
						logo_reset = true;
					} else {
						menusystem.bgimage.draw(gl, 0, 0, 1.0f, 1.0f, 1.0f, 1.0f);

						float passed = 0;
						if (time_current-logo_timer <= 700) {
							passed = 1 - ((float)(time_current-logo_timer) / 700);
							if (passed < 0.0f) passed = 0.0f;
							menusystem.white.draw(gl, 0, 0, passed, passed, passed, passed, hd*4.8f, hd*8.0f);
						} else if (time_current-logo_timer > 2700) {
							passed = (float)(time_current-logo_timer-2700) / 300;
							if (passed > 1.0f) passed = 1.0f;
							menusystem.black.draw(gl, 0, 0, passed, passed, passed, passed, hd*4.8f, hd*8.0f);
						}
						//Log.i("Cell Planet", Float.toString(passed));

						if (time_current-logo_timer > 3000) {
							menusystem_update = true;
							//next_game_tick = GetTickCount();
							adtimer = 0;
							//media_num = 0;
							media_update = true;
							
							dailyreward = pref[0].getInt("DailyProgress", 0);
							if (dailyreward >= 4) if (menusystem.mission[1].status == 0) menusystem.mission[1].status = 1;
							if (dailyreward >= 5) {
								allUpgrades.ship[1].bought = true;
								allUpgrades.ship[1].available = true;
								allUpgrades.ship[1].show = true;
							}
							if (lastrun > 1 && dailyreward<5) {
								dailyreward ++;
								pref[0].edit().putInt("DailyProgress", dailyreward).commit();
								// save current day
								pref[0].edit().putInt("Lastrun_Year", today.get(Calendar.YEAR)).commit();
								pref[0].edit().putInt("Lastrun_Month", today.get(Calendar.MONTH)).commit();
								pref[0].edit().putInt("Lastrun_Day", today.get(Calendar.DAY_OF_MONTH)).commit();
								pref[0].edit().putInt("Lastrun_Hour", today.get(Calendar.HOUR_OF_DAY)).commit();
								pref[0].edit().putInt("Lastrun_Minute", today.get(Calendar.MINUTE)).commit();

								switch (dailyreward) {
									case 1 :
										LY += 10000;
										Save_Profile(4);
										break;
									case 2 :
										allUpgrades.upgrade[0].bought = true;
										allUpgrades.upgrade[1].bought = true;
										Save_Profile(0);
										break;
									case 3 :
										LY += 20000;
										Save_Profile(4);
										break;
									case 4 :
										if (menusystem.mission[1].status == 0) menusystem.mission[1].status = 1;
										break;
									case 5 :
										allUpgrades.ship[1].bought = true;
										allUpgrades.ship[1].available = true;
										allUpgrades.ship[1].show = true;
										Save_Profile(0);
										break;
									default :
										break;
								}
								menu = 105;
							} else { 
								menu = 1;
								like_tap = 0;
							}
						}
					}				
					break;

				
//MAIN MENU
			case 101 :
			case 105 :
			case 1 :

				if (media_update && music) {
					media_update = false;
					//if (!mediaplayer.isPlaying()) {
					//	mediaplayer.start();
					//}
					if (!mediaplayer.isPlaying()) {
						media_load(media_list[0],true);
					}
				}
				
				menusystem.mainmenu.bgimage.draw(gl, 0, 0);
				for (int i=0; i<menusystem.mainmenu.button_num; i++) {
					if (i!=1){
						if (!menusystem.mainmenu.button_pressed[i]) {
							menusystem.mainmenu.buttonimages[i].draw(gl, menusystem.mainmenu.button_pos[i][0], menusystem.mainmenu.button_pos[i][1]);
						} else {
							menusystem.mainmenu.buttonimages[i].draw(gl, menusystem.mainmenu.button_pos[i][0], menusystem.mainmenu.button_pos[i][1], 0.6f, 1.0f, 0.7f, 1.0f);
						}
					}
				}
				//if (!fullgame) {
					//menusystem.mainmenu_lock.draw(gl, menusystem.mainmenu.button_pos[1][0]-5, menusystem.mainmenu.button_pos[1][1]);
				//}
				
				if (!quit) {
					if (menusystem.shiningstar_timer < time_current) {
						menusystem.shiningstar[(int)(menusystem.shiningstar_loop / 10)].draw(gl, menusystem.shiningstar_loc[menusystem.shiningstar_current][0], menusystem.shiningstar_loc[menusystem.shiningstar_current][1]);
						menusystem.shiningstar_loop+= time_dif / 6;
						if (menusystem.shiningstar_loop >= 100.0f) {
							menusystem.shiningstar_loop = 0.0f;
							int r = (int)Math.ceil(Math.random()*100) / 20;
							if (r >= 5) r = 4;
							if (r != menusystem.shiningstar_current) {
								menusystem.shiningstar_current = r;
							} else {
								r++; if (r > 4) r = 0;
								menusystem.shiningstar_current = r;
							}
							menusystem.shiningstar_timer = time_current + 1000;
						}
			  		}
				}
				
				if (quit) {
					//menusystem.black.draw(gl, 40, 200, 0.7f, 0.7f, 0.7f, 0.7f, hd*4.0f, hd*2.0f);
					menusystem.verify_border.draw(gl, (int)(hd*36), (int)(hd*184));
					//menufnt.PrintAt(gl, "Are you sure you want to quit?", (int)(hd*60), (int)(hd*220));
					menusystem.quit_text.draw(gl, (int)(hd*60), (int)(hd*220));
					//menufnt.PrintAt(gl, "Are you sure you want to quit?", 60, 250);
					if (!menusystem.quit_yes_pressed) {
						menusystem.quit_yes.draw(gl, menusystem.quit_bt_yes[0], menusystem.quit_bt_yes[1]);
					} else {
						menusystem.quit_yes.draw(gl, menusystem.quit_bt_yes[0], menusystem.quit_bt_yes[1], 0.6f, 1.0f, 0.7f, 1.0f);
					}
					if (!menusystem.quit_no_pressed) {
						menusystem.mission_cancel.draw(gl, menusystem.quit_bt_no[0], menusystem.quit_bt_no[1]);
					} else {
						menusystem.mission_cancel.draw(gl, menusystem.quit_bt_no[0], menusystem.quit_bt_no[1], 0.6f, 1.0f, 0.7f, 1.0f);
					}
				}
				
				if (menu == 105) {
					menusystem.daily_border.draw(gl, (int)(hd*10), (int)(hd*75));
					for (int i=0; i< dailyreward; i++) menusystem.dailyok.draw(gl, (int)(hd*10)+menusystem.dailyok_pos[i][0], (int)(hd*75)+menusystem.dailyok_pos[i][1]);
					if (!menusystem.getmore_pressed) {
						menusystem.getmore.draw(gl, menusystem.getmore_pos[0], menusystem.getmore_pos[1]);
					} else {
						menusystem.getmore.draw(gl, menusystem.getmore_pos[0], menusystem.getmore_pos[1], 0.6f, 1.0f, 0.7f, 1.0f);
					}
				}
				
				break;

//CAMPAIGN MENU		
			case 2 :
				menusystem.mainmenu.campaignselector.draw(gl, 0, 0);

					if (menusystem.shiningstar_timer < time_current) {
						menusystem.shiningstar[(int)(menusystem.shiningstar_loop / 10)].draw(gl, menusystem.shiningstar_loc2[menusystem.shiningstar_current2][0], menusystem.shiningstar_loc2[menusystem.shiningstar_current2][1]);
						menusystem.shiningstar_loop+= time_dif / 8;
						if (menusystem.shiningstar_loop >= 100.0f) {
							menusystem.shiningstar_loop = 0.0f;
							int r = (int)(Math.ceil(Math.random()*100) / 12.5f);
							if (r >= 7) r = 7;
							if (r != menusystem.shiningstar_current2) {
								menusystem.shiningstar_current2 = r;
							} else {
								r++; if (r > 7) r = 0;
								menusystem.shiningstar_current2 = r;
							}
							menusystem.shiningstar_timer = time_current + 3000;
						}
			  		}

				for (int i=0; i<menusystem.total_galaxy; i++) {
					// galaxis nevek
					if (menusystem.galaxy[i].status > 0 || i == 15) {
						if (menusystem.galaxy[i].status < 2) { galaxyfnt.SetPolyColor(0.5f, 0.5f, 0.5f, 0.5f); }
						galaxyfnt.PrintAt(gl, menusystem.galaxy[i].name, menusystem.galaxy[i].label_xy[0], menusystem.galaxy[i].label_xy[1]);
						galaxyfnt.SetPolyColor(1.0f, 1.0f, 1.0f, 1.0f);
					}
					// vonalak
					for (int j=0; j<menusystem.galaxy[i].unlock_galaxy_list.length; j++) {
						if (menusystem.galaxy[i].status == 2 && menusystem.galaxy[i].unlock_galaxy_list[j] >= 0) {
							float a = Math.abs(menusystem.galaxy[i].bt_xy[0] - menusystem.galaxy[menusystem.galaxy[i].unlock_galaxy_list[j]].bt_xy[0]);
							float b = Math.abs(menusystem.galaxy[i].bt_xy[1] - menusystem.galaxy[menusystem.galaxy[i].unlock_galaxy_list[j]].bt_xy[1]);
							double c = Math.sqrt(a*a+b*b);
							float alfa = (float)(57.295779513*Math.asin((float)a/c));
							int cx; int cy; float cs;
							cx = menusystem.galaxy[menusystem.galaxy[i].unlock_galaxy_list[j]].bt_xy[0]+(int)(hd*38);
							cy = menusystem.galaxy[menusystem.galaxy[i].unlock_galaxy_list[j]].bt_xy[1]+(int)(hd*42);
							
							if (menusystem.galaxy[i].bt_xy[0] < menusystem.galaxy[menusystem.galaxy[i].unlock_galaxy_list[j]].bt_xy[0]
								&& menusystem.galaxy[i].bt_xy[1] < menusystem.galaxy[menusystem.galaxy[i].unlock_galaxy_list[j]].bt_xy[1]) {
									//alfa+=90;
									alfa=180-alfa;
							} else if (menusystem.galaxy[i].bt_xy[0] > menusystem.galaxy[menusystem.galaxy[i].unlock_galaxy_list[j]].bt_xy[0]
									&& menusystem.galaxy[i].bt_xy[1] < menusystem.galaxy[menusystem.galaxy[i].unlock_galaxy_list[j]].bt_xy[1]) {
									alfa+=180;
							} else if (menusystem.galaxy[i].bt_xy[0] > menusystem.galaxy[menusystem.galaxy[i].unlock_galaxy_list[j]].bt_xy[0]
									&& menusystem.galaxy[i].bt_xy[1] > menusystem.galaxy[menusystem.galaxy[i].unlock_galaxy_list[j]].bt_xy[1]) {
									alfa=-alfa;
							}
							cs = (float)c/(hd*200);
							if (menusystem.galaxy[menusystem.galaxy[i].unlock_galaxy_list[j]].status == 1) {
								menusystem.starline[(int)(menusystem.starline_loop / 10)].drawROT(gl, cx, cy, 1, 1, 1, 0.5f, cs, cs, alfa, 0, 0);
							} else {
								menusystem.starline_base.drawROT(gl, cx, cy, 1, 1, 1, 0.5f, cs, cs, alfa, 0, 0);
							}

						}
					}
				}
				

				//helper[0] = Float.toString(menusystem.parrow_total);
				
				if (menusystem.galaxy[1].status < 2 && menusystem.galaxy[2].status < 2 && menusystem.parrow_total < 240) 
						//&& menusystem.dialogue_galaxy && menusystem.dialogue_galaxy_num >= 0) 
				{
					//Log.i("Starship Commander", "Pointing Arrow Drawn");
					if (hd > 1) menusystem.parrow[(int)(menusystem.parrow_loop / 10)].drawROT(gl, (int)(hd*240)-(90f*hd-90), (int)(hd*600)-(42f*hd-42), 1,1,1,1,1,1, (int)(hd*45), (int)(hd*21), 20);
					else menusystem.parrow[(int)(menusystem.parrow_loop / 10)].drawROT(gl, (int)(hd*183), (int)(hd*600)-(42f*hd-42), 1,1,1,1,1,1, (int)(hd*45), (int)(hd*21), 20);
					menusystem.parrow_loop+= time_dif / 15;
					menusystem.parrow_total+= time_dif / 15;
			  		if (menusystem.parrow_loop >= 60.0f) {
			  			menusystem.parrow_loop = 0.0f; 
			  		}
				}
					
		  		menusystem.starline_loop+= time_dif / 9;
		  		if (menusystem.starline_loop >= 80.0f) {
		  			menusystem.starline_loop = 0.0f; 
		  		}

		  		
				
				if (menusystem.selected_galaxy >= 0) {
					int i = menusystem.selected_galaxy;
					menusystem.galaxy_marker.draw(gl, menusystem.galaxy[i].bt_xy[0], menusystem.galaxy[i].bt_xy[1]);
					
					int layerX = menusystem.galaxy[i].bt_xy[0] + (int)(hd*80); 
					int layerY = menusystem.galaxy[i].bt_xy[1] - (int)(hd*70);
					if (layerX > (int)(hd*120)) layerX = (int)(hd*120);
					if (layerY < (int)(hd*80)) layerY = menusystem.galaxy[i].bt_xy[1] + (int)(hd*90);

					// ezek az uj valtozok torolhetoek
					int btX = menusystem.galaxy_bt[0]; //menusystem.galaxy[i].bt_xy[0]+80;
					int btY = menusystem.galaxy_bt[1]; //menusystem.galaxy[i].bt_xy[1]-4;
					//if (btX > 380) btX = menusystem.galaxy[i].bt_xy[0] - 80;
					
					//menusystem.black.draw(gl, layerX - 5, layerY - 5, 1, 1, 1, 0.6f, 4.0f, 0.8f);
					menusystem.galaxy_border.draw(gl, layerX - (int)(hd*27), layerY - (int)(hd*35));
					
				     //menufnt.SetScale(scrScaleX*menufnt_scale,scrScaleY*menufnt_scale);
				     menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
					
					if (menusystem.galaxy[i].status == 1) {
						if (menusystem.galaxy[i].isbonus && !menusystem.galaxy[i].isboss) {
							menufnt.PrintAt(gl, menusystem.galaxy[i].name + " (Bonus)", layerX, layerY);
						} else if (menusystem.galaxy[i].isboss && !menusystem.galaxy[i].isbonus) {
							menufnt.PrintAt(gl, menusystem.galaxy[i].name + " (Boss)", layerX, layerY);
						} else if (menusystem.galaxy[i].isboss && menusystem.galaxy[i].isbonus) {
							menufnt.PrintAt(gl, menusystem.galaxy[i].name + " (Bonus, Boss)", layerX, layerY);
						} else {
							menufnt.PrintAt(gl, menusystem.galaxy[i].name, layerX, layerY);
						}
						menufnt.PrintAt(gl, menusystem.missionC + Integer.toString(menusystem.galaxy[i].total_missions-1), layerX, layerY + menufnt_space);
						// if insufficient funds, set it red
						if (menusystem.galaxy[i].fullgame && !fullgame) {
							menufnt.SetPolyColor(1.0f, 0.6f, 0.0f, menufnt_alfa);
							menufnt.PrintAt(gl, "Requires Full Game", layerX, layerY + menufnt_space*2);
							menufnt.SetPolyColor(1,1,1,menufnt_alfa);
						}
						else if (LY < menusystem.galaxy[i].distance) { 
							menufnt.SetPolyColor(1.0f, 0.3f, 0.3f, menufnt_alfa);
							//menufnt.PrintAt(gl, menusystem.unlockA + toLY(menusystem.galaxy[i].distance, false) + menusystem.LY + " (Insufficient Funds)", layerX, layerY + menufnt_space*2);
							menufnt.PrintAt(gl, menusystem.unlockA + toLY(menusystem.galaxy[i].distance, false) + menusystem.LY, layerX, layerY + menufnt_space*2);
						} else {
							menufnt.PrintAt(gl, menusystem.unlockA + toLY(menusystem.galaxy[i].distance, false) + menusystem.LY, layerX, layerY + menufnt_space*2);
						}
						// set color back to white
						menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
						if (!menusystem.galaxy_bt_pressed) {
							menusystem.galaxy_unlock.draw(gl, layerX+(int)(hd*196), layerY+(int)(hd*0));
						} else {
							// button pressed color
							menusystem.galaxy_unlock.draw(gl, layerX+(int)(hd*196), layerY+(int)(hd*0), 0.6f, 1.0f, 0.7f, 1.0f);
						}
					}
					else if (menusystem.galaxy[i].status == 2) {
						menufnt.PrintAt(gl, menusystem.galaxy[i].name, layerX, layerY);
						int missionscompleted = 0;
						long highscore = 0;
						for (int j=0;j<menusystem.galaxy[i].total_missions;j++) {
							if (menusystem.mission[menusystem.galaxy[i].mission_list[j]].completed >= 2) {
								missionscompleted++;
								highscore+=menusystem.mission[menusystem.galaxy[i].mission_list[j]].highscore;
							}
						}
						if (missionscompleted == menusystem.galaxy[i].total_missions-1) {
							menufnt.SetPolyColor(0.3f, 1.0f, 0.3f, menufnt_alfa);
						}
						// secret level manual code
						int tmission = menusystem.galaxy[i].total_missions-1; if (i==0 && menusystem.mission[9].status < 1) tmission--;
						menufnt.PrintAt(gl, menusystem.missionA + Integer.toString(missionscompleted) + menusystem.missionB + Integer.toString(tmission), layerX, layerY + menufnt_space);
						menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
						menufnt.PrintAt(gl, menusystem.scoreA + toLY(highscore, false) + menusystem.LY, layerX, layerY + menufnt_space*2);
						if (!menusystem.galaxy_bt_pressed) {
							menusystem.galaxy_enter.draw(gl, layerX+(int)(hd*196), layerY+(int)(hd*0));
						} else {
							menusystem.galaxy_enter.draw(gl, layerX+(int)(hd*196), layerY+(int)(hd*0), 0.6f, 1.0f, 0.7f, 1.0f);
						}
					}
				}
				/*
				if (!menusystem.back_pressed) {
					menusystem.back.draw(gl, menusystem.back_bt[0], menusystem.back_bt[1]);
				} else {
					menusystem.back.draw(gl, menusystem.back_bt[0], menusystem.back_bt[1], 0.6f, 1.0f, 0.7f, 1.0f);
				}
				*/
				
				//menufnt.SetScale(scrScaleX*menufnt_scale,scrScaleY*menufnt_scale);
				menufnt.PrintAt(gl, "LY: " + toLY(LY, false), (int)(hd*5), (int)(hd*3));
				allUpgrades.ship[spaceship.type].image[0].draw(gl, (int)(hd*445), (int)(hd*3), 1, 1, 1, 1, 32.0f/allUpgrades.ship[spaceship.type].image_size[0], 32.0f/allUpgrades.ship[spaceship.type].image_size[1]);
				
				if (ggs) {
					if (!signedin) {
						if (menusystem.btsignin_pressed) menusystem.btsignin.draw(gl, menusystem.btsignin_pos[0], menusystem.btsignin_pos[1], 0.6f, 0.6f, 0.6f, 0.6f);
						else menusystem.btsignin.draw(gl, menusystem.btsignin_pos[0], menusystem.btsignin_pos[1]);
					}
					if (menusystem.btleaderboard_pressed) menusystem.btleaderboard.draw(gl, menusystem.btleaderboard_pos[0], menusystem.btleaderboard_pos[1], 0.6f, 1.0f, 0.6f, 1.0f);
					else menusystem.btleaderboard.draw(gl, menusystem.btleaderboard_pos[0], menusystem.btleaderboard_pos[1]);
					if (menusystem.btachievements_pressed) menusystem.btachievements.draw(gl, menusystem.btachievements_pos[0], menusystem.btachievements_pos[1], 0.6f, 1.0f, 0.6f, 1.0f);
					else menusystem.btachievements.draw(gl, menusystem.btachievements_pos[0], menusystem.btachievements_pos[1]);
				
					if (achievement_anim) {
						int animnum = 0;
						achievement_anim_timer += time_dif / 25;
						if (achievement_anim_timer >= explosion_loop_scale * menuAnimations.animation[animnum].picture_num) {
							achievement_anim_timer = 0;
						}
						menuAnimations.animation[animnum].image[(int)(achievement_anim_timer/explosion_loop_scale)]
								.draw(gl, menusystem.btachievements_pos[0]-(int)(hd*12), menusystem.btachievements_pos[1]-(int)(hd*0), 1, 1, 1, 1, hd/1.5f, hd/1.5f);
					}
				} 
				
				//helper[0] = "andromeda status : " + Integer.toString(menusystem.dialogue_galaxy_num);
				
				if (menusystem.dialogue_galaxy && menusystem.dialogue_galaxy_num >= 0
					&& menusystem.dialogue_galaxy_num < allMissions.Dialogue_list.length) {
					try {
						int dg = menusystem.dialogue_galaxy_num;

						menusystem.dialogue_bg.draw(gl, 0, 0);
						if (allMissions.Dialogue_list[dg].length == 3) {
							menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][0], menusystem.dialogue_xy[3][0], menusystem.dialogue_xy[3][1]);
							menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][1], menusystem.dialogue_xy[4][0], menusystem.dialogue_xy[4][1]);
							menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][2], menusystem.dialogue_xy[5][0], menusystem.dialogue_xy[5][1]);
						} else if (allMissions.Dialogue_list[dg].length == 2) {
							menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][0], menusystem.dialogue_xy[1][0], menusystem.dialogue_xy[1][1]);
							menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][1], menusystem.dialogue_xy[2][0], menusystem.dialogue_xy[2][1]);
						} else if (allMissions.Dialogue_list[dg].length == 1) {
							menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][0], menusystem.dialogue_xy[0][0], menusystem.dialogue_xy[0][1]);
						}
						if (!menusystem.dialogue_galaxy_isrunning) { 
							menusystem.dialogue_galaxy_isrunning = true;
							this.postDelayed(new Runnable() { public void run() { 
								menusystem.dialogue_galaxy = false; menusystem.dialogue_galaxy_isrunning = false;
								menusystem.Update_Dialogues(menusystem.dialogue_galaxy_num, 0); 
								}
							}, menusystem.dialogue_delay);
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				} else { menusystem.dialogue_galaxy = false; }
				
				break;
				
			// animation between menu 2 and 8
		  	case 28:
		  		
		  		if (galaxybg_update) {
					menusystem.bgimage.release(gl);
					menusystem.bgimage = new My2DImage((int)(hd*480),(int)(hd*800),false);
					menusystem.bgimage.load(gl, context, menusystem.bgimage_src[menusystem.selected_galaxy]);
					galaxybg_update = false;
				}
		  		
		  		if (menusystem.wormholeA_loop < 80) {
		  			menusystem.mainmenu.campaignselector.draw(gl, 0, 0);
		  			menusystem.wormholeA_alpha = menusystem.wormholeA_loop / 40; if (menusystem.wormholeA_alpha > 1.0f) menusystem.wormholeA_alpha = 1.0f;
		  		}
		  		else {
		  			menusystem.bgimage.draw(gl, 0, 0);
		  			if (menusystem.wormholeA_loop >= 120) {
		  				menusystem.wormholeA_alpha = (40 - (menusystem.wormholeA_loop - 119)) /40; if (menusystem.wormholeA_alpha < 0.0f) menusystem.wormholeA_alpha = 0.0f;
		  			}
		  		}
		  		
		  		menusystem.wormholeA[(int)(menusystem.wormholeA_loop / 10)].draw(gl, 0, 0, 
		  				menusystem.galaxy[menusystem.selected_galaxy].color[0], 
		  				menusystem.galaxy[menusystem.selected_galaxy].color[1], 
		  				menusystem.galaxy[menusystem.selected_galaxy].color[2], 
		  				menusystem.wormholeA_alpha, (float)dimensionX/120, (float)dimensionY/200);
		  		
		  		menusystem.wormholeA_loop+= time_dif / 6;
		  		if (menusystem.wormholeA_loop >= 160.0f) {
		  			menusystem.wormholeA_loop = 0.0f; 
		  			menusystem.wormholeA_alpha = 0.0f;
		  			menusystem.dialogue_mission = true;
					menusystem.dialogue_galaxy = false;
					menusystem.dialogue_upgrade = false;
					menusystem.dialogue_game = false;
					
					menusystem_update = false;
		  			menu = 8;
		  		}
		  		
		  		break;
		  		
		  		
//SURVIVAL MENU		
			case 3 :
				//mainmenu_bg.draw(gl, 0, 0);
				
				break;
				
//OPTIONS MENU		
			case 4 :
				menusystem.mainmenu.bgimage.draw(gl, 0, 0);
				//menusystem.black.draw(gl, (int)(hd*40), (int)(hd*40), 0.7f, 0.7f, 0.7f, 0.7f, hd*4.0f, hd*6.2f);
				menusystem.options_border.draw(gl, (int)(hd*10), (int)(hd*20));
				menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);

				if (sound) {
					menusystem.options_soundon.draw(gl, menusystem.options_sound_bt[0], menusystem.options_sound_bt[1]);
					menufnt.PrintAt(gl, "Sound is ON.", (int)(hd*150), (int)(hd*80));
				} else {
					menusystem.options_soundoff.draw(gl, menusystem.options_sound_bt[0], menusystem.options_sound_bt[1]);
					menufnt.PrintAt(gl, "Sound is OFF.", (int)(hd*150), (int)(hd*80));
				}
				if (menusystem.options_sound_pressed) menusystem.white.draw(gl, menusystem.options_sound_bt[0], menusystem.options_sound_bt[1], 0.1f, 0.3f, 0.2f, 0.01f, hd*3.8f, hd*0.8f);
				
				// music bar
				menusystem.white.draw(gl, (int)(hd*150), (int)(hd*180), 0.3f,0.3f,0.3f,1, hd*2.7f, hd*0.25f);
				if (musicvolume > 0) menusystem.white.draw(gl, (int)(hd*150), (int)(hd*180), 0, 0.9f, 0.4f, 0.5f, hd*2.7f * musicvolume, hd*0.25f);
				if (music) {
					menusystem.options_musicon.draw(gl, menusystem.options_music_bt[0], menusystem.options_music_bt[1]);
					menufnt.PrintAt(gl, "Music volume is " + Integer.toString((int)(musicvolume*10)) + ".", (int)(hd*150), (int)(hd*150));
				} else {
					menusystem.options_musicoff.draw(gl, menusystem.options_music_bt[0], menusystem.options_music_bt[1]);
					menufnt.PrintAt(gl, "Music is OFF.", (int)(hd*150), (int)(hd*150));
				}
				if (menusystem.options_music_pressed) menusystem.white.draw(gl, menusystem.options_music_bt[0], menusystem.options_music_bt[1], 0.1f, 0.3f, 0.2f, 0.01f, hd*3.8f, hd*0.8f);
				if (!sound)	menusystem.black.draw(gl, menusystem.options_music_bt[0], menusystem.options_music_bt[1], 0.3f, 0.3f, 0.3f, 0.3f, hd*3.8f, hd*0.8f);
				
				if (vibration) {
					menusystem.options_vibrationon.draw(gl, menusystem.options_vibration_bt[0], menusystem.options_vibration_bt[1]);
					menufnt.PrintAt(gl, "Vibration is ON.", (int)(hd*150), (int)(hd*260));
				} else {
					menusystem.options_vibrationoff.draw(gl, menusystem.options_vibration_bt[0], menusystem.options_vibration_bt[1]);
					menufnt.PrintAt(gl, "Vibration is OFF.", (int)(hd*150), (int)(hd*260));
				}
				if (menusystem.options_vibration_pressed) menusystem.white.draw(gl, menusystem.options_vibration_bt[0], menusystem.options_vibration_bt[1], 0.1f, 0.3f, 0.2f, 0.01f, hd*3.8f, hd*0.8f);
				if (keepscreen) {
					menusystem.options_keepscreenon.draw(gl, menusystem.options_keepscreen_bt[0], menusystem.options_keepscreen_bt[1]);
					menufnt.PrintAt(gl, "Keep screen mode is ON.", (int)(hd*150), (int)(hd*335));
				} else {
					menusystem.options_keepscreenoff.draw(gl, menusystem.options_keepscreen_bt[0], menusystem.options_keepscreen_bt[1]);
					menufnt.PrintAt(gl, "Keep screen mode is OFF.", (int)(hd*150), (int)(hd*335));
				}
				if (menusystem.options_keepscreen_pressed) menusystem.white.draw(gl, menusystem.options_keepscreen_bt[0], menusystem.options_keepscreen_bt[1], 0.1f, 0.3f, 0.2f, 0.01f, hd*3.8f, hd*0.8f);

				/*
				if (!menusystem.back_pressed) {
					menusystem.back.draw(gl, menusystem.back1_bt[0], menusystem.back1_bt[1]);
				} else {
					menusystem.back.draw(gl, menusystem.back1_bt[0], menusystem.back1_bt[1], 0.6f, 1.0f, 0.7f, 1.0f);
				}
				*/				

				if (loading) {
					menusystem.black.draw(gl, 0, 0, 0.7f, 0.7f, 0.7f, 0.7f, hd*4.8f, hd*8.0f);
					menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Please wait while loading...", 120, 340);
				}

				break;

//PROFILE MENU	
			case 502 : 
			case 501 : 
			case 5 :
				menusystem.mainmenu.bgimage.draw(gl, 0, 0);
				
				for (int i=0;i<3;i++) {
					//menusystem.black.draw(gl, (int)(hd*40), (int)(hd*40)+i*(int)(hd*200), 0.7f, 0.7f, 0.7f, 0.7f, hd*4.0f, hd*1.6f);
					if (menusystem.profile_select_pressed[i]) menusystem.white.draw(gl, (int)(hd*40), (int)(hd*40)+i*(int)(hd*200), 0.1f, 0.3f, 0.2f, 0.01f, hd*4.0f, hd*1.6f);
					if (current_profile == i+1) { 
						menusystem.profile_border.draw(gl, 0, (int)(hd*28)+i*(int)(hd*200), 0.3f, 1.0f, 0.6f, 1.0f);
					}
					else { menusystem.profile_border.draw(gl, 0, (int)(hd*28)+i*(int)(hd*200)); }
					allUpgrades.ship[profile_ship[i]].image[0].draw(gl, (int)(hd*50), (int)(hd*50)+i*(int)(hd*200), 0.5f, 0.5f, 0.5f, 0.5f, (float)100/allUpgrades.ship[profile_ship[i]].image_size[0], (float)100/allUpgrades.ship[profile_ship[i]].image_size[1]);
					//if (current_profile == i+1) menusystem.white.draw(gl, 40, 40+i*200, 0.0f, 0.05f, 0.1f, 0.005f, 4.0f, 1.6f);
					menufnt.SetPolyColor(0.0f, 0.7f, 1.0f, menufnt_alfa);
					String current = ""; if (current_profile == i+1) current = " SELECTED";
					menufnt.PrintAt(gl, "PROFILE " + Integer.toString(i+1) + current, (int)(hd*45), (int)(hd*45)+i*(int)(hd*200));
					menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "LY: " + toLY(profile_ly[i], false), (int)(hd*45), (int)(hd*68)+i*(int)(hd*200));
					menufnt.SetPolyColor(0.0f, 0.7f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Progression", (int)(hd*45), (int)(hd*101)+i*(int)(hd*200));
					menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Galaxies: " + Integer.toString(profile_galaxies[i]) + " of " + Integer.toString(menusystem.total_galaxy), (int)(hd*45), (int)(hd*124)+i*(int)(hd*200));
					// secret level manual code
					int tmission = menusystem.total_mission - menusystem.total_galaxy; if (menusystem.mission[9].status < 1) tmission--;
					menufnt.PrintAt(gl, "Missions: " + Integer.toString(profile_missions[i]) + " of " + Integer.toString(tmission), (int)(hd*45), (int)(hd*147)+i*(int)(hd*200));
					menufnt.PrintAt(gl, "Score: " + toLY(profile_highscore[i], false), (int)(hd*45), (int)(hd*170)+i*(int)(hd*200));
					if (!menusystem.profile_reset_pressed[i]) menusystem.profile_reset.draw(gl, menusystem.profile_reset_bt[0], menusystem.profile_reset_bt[1]+i*(int)(hd*200));
					else menusystem.profile_reset.draw(gl, menusystem.profile_reset_bt[0], menusystem.profile_reset_bt[1]+i*(int)(hd*200), 0.6f, 1.0f, 0.7f, 1.0f);

				}
				
				if (profile_reset > 0) {
					menusystem.black.draw(gl, (int)(hd*0), (int)(hd*0), 0.6f, 0.6f, 0.6f, 0.6f, hd*4.8f, hd*8.0f);
					//menusystem.black.draw(gl, (int)(hd*40), (int)(hd*200), 0.6f, 0.6f, 0.6f, 0.6f, hd*4.0f, hd*2.0f);
					menusystem.verify_border.draw(gl, (int)(hd*36), (int)(hd*184));
					//menufnt.PrintAt(gl, "All progress will be lost.", (int)(hd*60), (int)(hd*220));
					//menufnt.PrintAt(gl, "Are you sure you want to reset?", (int)(hd*60), (int)(hd*250));
					menusystem.reset_text.draw(gl, (int)(hd*55), (int)(hd*220));

					if (!menusystem.reset_yes_pressed) {
						menusystem.profile_reset2.draw(gl, menusystem.quit_bt_yes[0], menusystem.quit_bt_yes[1]);
					} else {
						menusystem.profile_reset2.draw(gl, menusystem.quit_bt_yes[0], menusystem.quit_bt_yes[1], 0.6f, 1.0f, 0.7f, 1.0f);
					}
					if (!menusystem.reset_no_pressed) {
						menusystem.mission_cancel.draw(gl, menusystem.quit_bt_no[0], menusystem.quit_bt_no[1]);
					} else {
						menusystem.mission_cancel.draw(gl, menusystem.quit_bt_no[0], menusystem.quit_bt_no[1], 0.6f, 1.0f, 0.7f, 1.0f);
					}
				}

				/*
				if (!menusystem.back_pressed) {
					menusystem.back.draw(gl, menusystem.back1_bt[0], menusystem.back1_bt[1]);
				} else {
					menusystem.back.draw(gl, menusystem.back1_bt[0], menusystem.back1_bt[1], 0.6f, 1.0f, 0.7f, 1.0f);
				}
				*/				

				if (loading) {
					menusystem.black.draw(gl, 0, 0, 0.7f, 0.7f, 0.7f, 0.7f, hd*4.8f, hd*8.0f);
					menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Please wait while loading...", (int)(hd*120), (int)(hd*340));
				}
				
				break;

//CREDITS		
			case 6 :
					menusystem.mainmenu.bgimage.draw(gl, 0, 0);
					//menusystem.black.draw(gl, (int)(hd*40), (int)(hd*40), 0.7f, 0.7f, 0.7f, 0.7f, hd*4.0f, hd*6.2f);
					menusystem.options_border.draw(gl, (int)(hd*10), (int)(hd*20));
					menufnt.SetPolyColor(0.0f, 0.7f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Produced by", (int)(hd*50), (int)(hd*75));
					menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Mangata Media", (int)(hd*50), (int)(hd*100));

					menufnt.SetPolyColor(0.0f, 0.7f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Code / Design", (int)(hd*50), (int)(hd*150));
					menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Tamas Ujszaszi", (int)(hd*50), (int)(hd*175));

					menufnt.SetPolyColor(0.0f, 0.7f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Art / Design", (int)(hd*50), (int)(hd*225));
					menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Istvan Bodai", (int)(hd*50), (int)(hd*250));

					menufnt.SetPolyColor(0.0f, 0.7f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Music / Sound", (int)(hd*50), (int)(hd*300));
					menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Kevin MacLeod", (int)(hd*50), (int)(hd*325));
					menufnt.PrintAt(gl, "Mike Koenig", (int)(hd*50), (int)(hd*350));

					menufnt.SetPolyColor(0.0f, 0.7f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Contact information", (int)(hd*50), (int)(hd*400));
					menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "mangatamedia@gmail.com", (int)(hd*50), (int)(hd*425));

					menufnt.SetPolyColor(0.0f, 0.7f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Follow game updates on", (int)(hd*50), (int)(hd*475));
					menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "www.facebook.com/mangatamedia", (int)(hd*50), (int)(hd*500));
					//menufnt.PrintAt(gl, "www.twitter.com/TubiGames", 50, 575);

					/*
					if (!menusystem.back_pressed) {
						menusystem.back.draw(gl, menusystem.back1_bt[0], menusystem.back1_bt[1]);
					} else {
						menusystem.back.draw(gl, menusystem.back1_bt[0], menusystem.back1_bt[1], 0.6f, 1.0f, 0.7f, 1.0f);
					}
					*/				

				break;

//UNLOCK SCREEN
			// Enter code
			case 701 : 
				/*
				menusystem.mainmenu.bgimage.draw(gl, 0, 0);
				menusystem.black.draw(gl, (int)(hd*40), (int)(hd*40), 0.7f, 0.7f, 0.7f, 0.7f, hd*4.0f, hd*6.2f);
				menusystem.options_border.draw(gl, 0, 0);
				
				menusystem.black.draw(gl, (int)(hd*80), (int)(hd*80), 1.0f, 1.0f, 1.0f, 1.0f, hd*3.2f, hd*0.6f);
				menufnt.SetScale(2.0f, 2.0f);
				menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
				menufnt.PrintAt(gl, promocode, (int)(hd*90), (int)(hd*85));
				menufnt.SetScale(1,1);

				if (promocode.length() == 10) menufnt.PrintAt(gl, promostring, (int)(hd*90), (int)(hd*142));
				
				for (int i=0;i<12;i++) {
					if (!menusystem.unlock_keys_pressed[i]) {
						menusystem.unlock_keys[i].draw(gl, menusystem.unlock_keys_bt[i][0], menusystem.unlock_keys_bt[i][1]);
					} else {
						menusystem.unlock_keys[i].draw(gl, menusystem.unlock_keys_bt[i][0], menusystem.unlock_keys_bt[i][1], 0.6f, 1.0f, 0.7f, 1.0f);
					}
				}				
				
				
				if (!menusystem.back_pressed) {
					menusystem.back.draw(gl, menusystem.back1_bt[0], menusystem.back1_bt[1]);
				} else {
					menusystem.back.draw(gl, menusystem.back1_bt[0], menusystem.back1_bt[1], 0.6f, 1.0f, 0.7f, 1.0f);
				}
				*/
				break;
				
			case 7 :
				menusystem.mainmenu.bgimage.draw(gl, 0, 0);
				//menusystem.black.draw(gl, (int)(hd*40), (int)(hd*40), 0.7f, 0.7f, 0.7f, 0.7f, hd*4.0f, hd*6.2f);
				menusystem.options_border.draw(gl, (int)(hd*10), (int)(hd*20));
				
				menufnt.SetPolyColor(0.0f, 0.7f, 1.0f, menufnt_alfa);
				menufnt.PrintAt(gl, "Free upgrades", (int)(hd*50), (int)(hd*75));
				menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
				menufnt.PrintAt(gl, "Unlock additional unique features!", (int)(hd*50), (int)(hd*100));
				//menufnt.PrintAt(gl, "features!", (int)(hd*50), (int)(hd*100));
				menufnt.PrintAt(gl, "Instructions can be found on our", (int)(hd*50), (int)(hd*125));
				menufnt.PrintAt(gl, "Facebook page.", (int)(hd*50), (int)(hd*150));

				if (!fullgame) {
					menufnt.SetPolyColor(0.0f, 0.7f, 1.0f, menufnt_alfa);
					if (isGoogle) menufnt.PrintAt(gl, "Full game - USD 1.99", (int)(hd*50), (int)(hd*315));
					else menufnt.PrintAt(gl, "Full game", (int)(hd*50), (int)(hd*315));
					menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
					menufnt.PrintAt(gl, "Unlock all galaxies and missions", (int)(hd*50), (int)(hd*340));
					menufnt.PrintAt(gl, "Access full game upgrades", (int)(hd*50), (int)(hd*365));
					menufnt.PrintAt(gl, "Remove all ads", (int)(hd*50), (int)(hd*390));
				} 

				if (!menusystem.unlock_facebook_pressed) {
					menusystem.mainmenu.buttonimages[5].draw(gl, menusystem.unlock_facebook_bt[0], menusystem.unlock_facebook_bt[1]);
				} else {
					menusystem.mainmenu.buttonimages[5].draw(gl, menusystem.unlock_facebook_bt[0], menusystem.unlock_facebook_bt[1], 0.6f, 1.0f, 0.7f, 1.0f);
				}

				if (!fullgame) {
					if (!menusystem.unlock_fullgame_pressed) {
						menusystem.unlock_fullgame.draw(gl, menusystem.unlock_fullgame_bt[0], menusystem.unlock_fullgame_bt[1]);
					} else {
						menusystem.unlock_fullgame.draw(gl, menusystem.unlock_fullgame_bt[0], menusystem.unlock_fullgame_bt[1], 0.6f, 1.0f, 0.7f, 1.0f);
					}
				}
				
				/*
				if (!menusystem.back_pressed) {
					menusystem.back.draw(gl, menusystem.back1_bt[0], menusystem.back1_bt[1]);
				} else {
					menusystem.back.draw(gl, menusystem.back1_bt[0], menusystem.back1_bt[1], 0.6f, 1.0f, 0.7f, 1.0f);
				}
				*/				
				break;

//MISSION SCREEN		
			case 8 :
				if (menusystem_update) {
					menusystem_update = false;
					Log.w("Space Shooter", "Menu update called.");
					// release game entities
					allEntities.release(gl);
					allBullets.release(gl);
					//menusystem.releaseimages(gl);
					menusystem.loadimages(gl, context, isGoogle);
					menusystem.load_upgradeimages(gl, context, spaceship.type);
				}
				
				if (media_update && music) {
					media_update = false;
					if (!mediaplayer.isPlaying()) {
						mediaplayer.start();
					}
				}
				
				if (menusystem.selected_galaxy >= 0) {
					int g = menusystem.selected_galaxy; if (g<0) g=0;

					if (galaxybg_update) {
						menusystem.bgimage.release(gl);
						menusystem.bgimage = new My2DImage((int)(hd*480),(int)(hd*800),false);
						menusystem.bgimage.load(gl, context, menusystem.bgimage_src[menusystem.selected_galaxy]);
						galaxybg_update = false;
					}
					
					menusystem.bgimage.draw(gl, 0, 0);
					
					//menusystem.mission_marker_finished.draw(gl, menusystem.mission[0].bt_xy[0], menusystem.mission[0].bt_xy[1]);				
					for (int i=0; i<menusystem.galaxy[g].total_missions; i++) {
						
						int mission_num = menusystem.galaxy[g].mission_list[i];

						if (menusystem.mission[mission_num].status == 2) {
							if (menusystem.mission_marker_pressed != mission_num) {
								menusystem.mission_marker_station.draw(gl, menusystem.mission[mission_num].bt_xy[0], menusystem.mission[mission_num].bt_xy[1]);
							} else {
								menusystem.mission_marker_station.draw(gl, menusystem.mission[mission_num].bt_xy[0], menusystem.mission[mission_num].bt_xy[1], 0.6f, 1.0f, 0.7f, 0.5f);
							}
							//menufnt.SetScale(scrScaleX*menufnt_scale,scrScaleY*menufnt_scale); //0.66+24
						    menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
						    if (menusystem.mission[mission_num].textup) {
						    	menufnt.PrintAt(gl, " Space", menusystem.mission[mission_num].bt_xy[0]+(int)(hd*20), menusystem.mission[mission_num].bt_xy[1]-menufnt_space*2-(int)(hd*0));
						    	menufnt.PrintAt(gl, "Station", menusystem.mission[mission_num].bt_xy[0]+(int)(hd*20), menusystem.mission[mission_num].bt_xy[1]-menufnt_space-(int)(hd*0));
						    } else {
						    	menufnt.PrintAt(gl, " Space", menusystem.mission[mission_num].bt_xy[0]+(int)(hd*20), menusystem.mission[mission_num].bt_xy[1]+(int)(hd*100));
						    	menufnt.PrintAt(gl, "Station", menusystem.mission[mission_num].bt_xy[0]+(int)(hd*20), menusystem.mission[mission_num].bt_xy[1]+(int)(hd*100)+menufnt_space);
						    }
						} else if (menusystem.mission[mission_num].status == 1) {
							if (menusystem.mission[mission_num].completed >= 2) {
								if (menusystem.mission_marker_pressed != mission_num) {
									menusystem.mission_marker_finished.draw(gl, menusystem.mission[mission_num].bt_xy[0], menusystem.mission[mission_num].bt_xy[1], 0.75f,0.75f,0.75f,0.75f);
								} else {
									menusystem.mission_marker_finished.draw(gl, menusystem.mission[mission_num].bt_xy[0], menusystem.mission[mission_num].bt_xy[1], 0.6f, 1.0f, 0.7f, 0.5f);
								}
							} else {
								if (menusystem.mission_marker_pressed != mission_num) {
									menusystem.mission_marker_active.draw(gl, menusystem.mission[mission_num].bt_xy[0], menusystem.mission[mission_num].bt_xy[1]);
								} else {
									menusystem.mission_marker_active.draw(gl, menusystem.mission[mission_num].bt_xy[0], menusystem.mission[mission_num].bt_xy[1], 0.6f, 1.0f, 0.7f, 0.5f);
								}
							}
						}
					}
					
					allUpgrades.ship[spaceship.type].image[0].draw(gl, (int)(hd*445), (int)(hd*3), 1, 1, 1, 1, 32.0f/allUpgrades.ship[spaceship.type].image_size[0], 32.0f/allUpgrades.ship[spaceship.type].image_size[1]);
					if (ggs) {
						if (!signedin) {
							if (menusystem.btsignin_pressed) menusystem.btsignin.draw(gl, menusystem.btsignin_pos[0], menusystem.btsignin_pos[1], 0.6f, 0.6f, 0.6f, 0.6f);
							else menusystem.btsignin.draw(gl, menusystem.btsignin_pos[0], menusystem.btsignin_pos[1]);
						}
						if (menusystem.btleaderboard_pressed) menusystem.btleaderboard.draw(gl, menusystem.btleaderboard_pos[0], menusystem.btleaderboard_pos[1], 0.6f, 1.0f, 0.6f, 1.0f);
						else menusystem.btleaderboard.draw(gl, menusystem.btleaderboard_pos[0], menusystem.btleaderboard_pos[1]);
						if (menusystem.btachievements_pressed) menusystem.btachievements.draw(gl, menusystem.btachievements_pos[0], menusystem.btachievements_pos[1], 0.6f, 1.0f, 0.6f, 1.0f);
						else menusystem.btachievements.draw(gl, menusystem.btachievements_pos[0], menusystem.btachievements_pos[1]);
					
						if (achievement_anim) {
							int animnum = 0;
							achievement_anim_timer += time_dif / 25;
							if (achievement_anim_timer >= explosion_loop_scale * menuAnimations.animation[animnum].picture_num) {
								achievement_anim_timer = 0;
							}
							menuAnimations.animation[animnum].image[(int)(achievement_anim_timer/explosion_loop_scale)]
									.draw(gl, menusystem.btachievements_pos[0]-(int)(hd*12), menusystem.btachievements_pos[1]-(int)(hd*0), 1, 1, 1, 1, hd/1.5f, hd/1.5f);
						}
					} 

					
					if (menusystem.selected_mission >= 0 && menusystem.mission[menusystem.selected_mission].status == 1) {
						int mi = menusystem.selected_mission;
						
						//menusystem.black.draw(gl, (int)(hd*80), (int)(hd*120), 1, 1, 1, 0.7f, hd*3.2f, hd*4.8f);
						menusystem.mission_border.draw(gl, (int)(hd*48), (int)(hd*108));
						
						int starty = (int)(hd*140);
						
						//menufnt.SetScale(scrScaleX*menufnt_scale,scrScaleY*menufnt_scale); //0.66+24
					    menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
						
						menufnt.PrintAt(gl, menusystem.mission[mi].name, (int)(hd*90), starty);
						//menufnt.PrintAt(gl, menusystem.mission[mi].description, 90, 210);
						
						menufnt.PrintAt(gl, menusystem.status, (int)(hd*90), starty+(int)(hd*40));
						if (menusystem.mission[mi].fullgame && !fullgame) {
							menufnt.SetPolyColor(1.0f, 0.6f, 0.0f, menufnt_alfa);
							menufnt.PrintAt(gl, "Requires Full Game", (int)(hd*90), starty+(int)(hd*40)+menufnt_space);
							menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
						} else {
							menufnt.SetPolyColor(
									menusystem.completed_color[menusystem.mission[mi].completed][0], 
									menusystem.completed_color[menusystem.mission[mi].completed][1], 
									menusystem.completed_color[menusystem.mission[mi].completed][2], 
									menufnt_alfa);
							menufnt.PrintAt(gl, menusystem.completed[menusystem.mission[mi].completed], (int)(hd*90), starty+(int)(hd*40)+menufnt_space);
							menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
							
							float color[] = { 0.25f, 0.25f, 0.25f };
							if (menusystem.mission[mi].completed > 1) color[0] = 1f;
							if (menusystem.mission[mi].completed > 2) color[1] = 1f;
							if (menusystem.mission[mi].completed > 3) color[2] = 1f;
							
							menusystem.completed_star.draw(gl, (int)(hd*244), starty+(int)(hd*48), color[0],color[0],color[0], 1f);
							menusystem.completed_star.draw(gl, (int)(hd*293), starty+(int)(hd*48), color[1],color[1],color[1], 1f);
							menusystem.completed_star.draw(gl, (int)(hd*340), starty+(int)(hd*48), color[2],color[2],color[2], 1f);
						}
						
						menufnt.PrintAt(gl, menusystem.reward, (int)(hd*90), starty+(int)(hd*100));
						if (menusystem.mission[mi].completed >= 2) menufnt.PrintAt(gl, menusystem.reward_noreward, (int)(hd*90), starty+(int)(hd*100)+menufnt_space);
						else menufnt.PrintAt(gl, toLY(menusystem.mission[mi].reward, false)+" LY", (int)(hd*90), starty+(int)(hd*100)+menufnt_space);
						
						
						menufnt.PrintAt(gl, "Select difficulty:", (int)(hd*90), starty+(int)(hd*160));
						
						if (menusystem.mission_bt_difficulty == 0) {
							menusystem.mission_bt_easy.draw(gl, (int)(hd*90), starty+(int)(hd*165)+menufnt_space, 1.0f, 1.0f, 1.0f, 1.0f);
							menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
							menufnt.PrintAt(gl, "Weaker enemies but less LY", (int)(hd*90), starty+(int)(hd*245));
						} else {
							menusystem.mission_bt_easy.draw(gl, (int)(hd*90), starty+(int)(hd*165)+menufnt_space, 0.5f, 0.4f, 0.5f, 0.6f);
						}
						if (menusystem.mission_bt_difficulty == 1) {
							menusystem.mission_bt_medium.draw(gl, (int)(hd*192), starty+(int)(hd*165)+menufnt_space, 1.0f, 1.0f, 1.0f, 1.0f);
							menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
							menufnt.PrintAt(gl, "Default difficulty level", (int)(hd*90), starty+(int)(hd*245));
						} else {
							menusystem.mission_bt_medium.draw(gl, (int)(hd*192), starty+(int)(hd*165)+menufnt_space, 0.5f, 0.5f, 0.4f, 0.6f);
						}
						if (menusystem.mission_bt_difficulty == 2) {
							menusystem.mission_bt_hard.draw(gl, (int)(hd*296), starty+(int)(hd*165)+menufnt_space, 1.0f, 1.0f, 1.0f, 1.0f);
							if (menusystem.mission[mi].completed < 3) {
								menufnt.SetPolyColor(1.0f, 0.0f, 0.0f, menufnt_alfa);
								menufnt.PrintAt(gl, "Finish on Medium to unlock", (int)(hd*90), starty+(int)(hd*245));
							} else {
								menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
								menufnt.PrintAt(gl, "More LY but stronger enemies", (int)(hd*90), starty+(int)(hd*245));
							}
						} else {
							menusystem.mission_bt_hard.draw(gl, (int)(hd*296), starty+(int)(hd*165)+menufnt_space, 0.4f, 0.5f, 0.5f, 0.6f);
						}

						//menufnt.PrintAt(gl, menusystem.difficulty, 90, starty+160);
					    //menufnt.SetPolyColor(menusystem.difficulty_color[menusystem.mission[mi].difficulty][0], menusystem.difficulty_color[menusystem.mission[mi].difficulty][1], menusystem.difficulty_color[menusystem.mission[mi].difficulty][2], menufnt_alfa);
						//menufnt.PrintAt(gl, menusystem.difficulty_level[menusystem.mission[mi].difficulty], 90, starty+160+menufnt_space);
					    
						menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
						menufnt.PrintAt(gl, menusystem.scoreA, (int)(hd*90), starty+(int)(hd*285));
						menufnt.PrintAt(gl, toLY(menusystem.mission[mi].highscore, false) + " LY", (int)(hd*90), starty+(int)(hd*285)+menufnt_space);

						if (!menusystem.mission_enter_pressed) {
							if (menusystem.mission[mi].fullgame && !fullgame) {
								menusystem.unlock_fullgame.draw(gl, menusystem.mission_bt_enter[0], menusystem.mission_bt_enter[1]);
							} else {
								if (menusystem.mission_bt_difficulty == 2 && menusystem.mission[mi].completed < 3) {
									menusystem.mission_enter.draw(gl, menusystem.mission_bt_enter[0], menusystem.mission_bt_enter[1], 0.5f, 0.5f, 0.5f, 0.6f);
								} else {
									menusystem.mission_enter.draw(gl, menusystem.mission_bt_enter[0], menusystem.mission_bt_enter[1]);
								}
							}
						} else {
							if (menusystem.mission[mi].fullgame && !fullgame) {
								menusystem.unlock_fullgame.draw(gl, menusystem.mission_bt_enter[0], menusystem.mission_bt_enter[1], 0.6f, 1.0f, 0.7f, 1.0f);
							} else {
								menusystem.mission_enter.draw(gl, menusystem.mission_bt_enter[0], menusystem.mission_bt_enter[1], 0.6f, 1.0f, 0.7f, 1.0f);
							}
						}
						if (!menusystem.mission_cancel_pressed) {
							menusystem.mission_cancel.draw(gl, menusystem.mission_bt_cancel[0], menusystem.mission_bt_cancel[1]);
						} else {
							menusystem.mission_cancel.draw(gl, menusystem.mission_bt_cancel[0], menusystem.mission_bt_cancel[1], 0.6f, 1.0f, 0.7f, 1.0f);
						}
					}

					/*
					if (!menusystem.back_pressed) {
						menusystem.back.draw(gl, menusystem.back_bt[0], menusystem.back_bt[1]);
					} else {
						menusystem.back.draw(gl, menusystem.back_bt[0], menusystem.back_bt[1], 0.6f, 1.0f, 0.7f, 1.0f);
					}
					*/

					//menufnt.SetScale(scrScaleX*menufnt_scale,scrScaleY*menufnt_scale);
					menufnt.PrintAt(gl, "LY: " + toLY(LY, false), (int)(hd*5), (int)(hd*3));
		
					if (menusystem.dialogue_mission && menusystem.dialogue_mission_num >= 0 &&
						menusystem.dialogue_mission_num < allMissions.Dialogue_list.length) {
						try {
							int dg = menusystem.dialogue_mission_num;
							menusystem.dialogue_bg.draw(gl, 0, 0);
							if (allMissions.Dialogue_list[dg].length == 3) {
								menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][0], menusystem.dialogue_xy[3][0], menusystem.dialogue_xy[3][1]);
								menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][1], menusystem.dialogue_xy[4][0], menusystem.dialogue_xy[4][1]);
								menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][2], menusystem.dialogue_xy[5][0], menusystem.dialogue_xy[5][1]);
							} else if (allMissions.Dialogue_list[dg].length == 2) {
								menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][0], menusystem.dialogue_xy[1][0], menusystem.dialogue_xy[1][1]);
								menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][1], menusystem.dialogue_xy[2][0], menusystem.dialogue_xy[2][1]);
							} else if (allMissions.Dialogue_list[dg].length == 1) {
								menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][0], menusystem.dialogue_xy[0][0], menusystem.dialogue_xy[0][1]);
							}
							if (!menusystem.dialogue_mission_isrunning) { 
								menusystem.dialogue_mission_isrunning = true;
								this.postDelayed(new Runnable() { public void run() { 
									menusystem.dialogue_mission = false; menusystem.dialogue_mission_isrunning = false; 
									menusystem.Update_Dialogues(menusystem.dialogue_mission_num, 1); 
									}
								}, menusystem.dialogue_delay);
							}
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					} else { menusystem.dialogue_mission = false; }
				}
				
				break;
				
//UPGRADE SCREEN
			case 91 :
			case 9 :
				/*
				if (menusystem_update) {
					menusystem_update = false;
					Log.w("Space Shooter", "Menu update called.");
					// release game entities
					allEntities.release(gl);
					allBullets.release(gl);
					//menusystem.releaseimages(gl);
					menusystem.loadimages(gl, context, isGoogle);
					menusystem.load_upgradeimages(gl, context, spaceship.type);
				}
				*/

				if (menuupgrade_update) {
					menuupgrade_update = false;
					menusystem.release_upgradeimages(gl);
					menusystem.load_upgradeimages(gl, context, spaceship.type);
				}
				
				if (media_update && music) {
					media_update = false;
					if (!mediaplayer.isPlaying()) {
						mediaplayer.start();
					}
				}

				Ondrawframe_case9(gl);

				/*
				if (!menusystem.back_pressed) {
					menusystem.back.draw(gl, menusystem.back_bt[0], menusystem.back_bt[1]);
				} else {
					menusystem.back.draw(gl, menusystem.back_bt[0], menusystem.back_bt[1], 0.6f, 1.0f, 0.7f, 1.0f);
				}
				*/
				//menufnt.PrintAt(gl, "LY: " + toLY(LY, false), (int)(hd*65), (int)(hd*29));

				/*
				if (buynow) {
					menusystem.black.draw(gl, 0, 0, 0.6f, 0.6f, 0.6f, 0.6f, hd*4.8f, hd*8.0f);
					//menusystem.black.draw(gl, (int)(hd*40), (int)(hd*200), 0.6f, 0.6f, 0.6f, 0.6f, hd*4.0f, hd*2.0f);
					menusystem.verify_border.draw(gl, (int)(hd*36), (int)(hd*184));
					menufnt.PrintAt(gl, "This item can be purchased on", (int)(hd*60), (int)(hd*220));
					if (isGoogle) menufnt.PrintAt(gl, "Google Play. Tap to continue.", (int)(hd*60), (int)(hd*245));
					else menufnt.PrintAt(gl, "Amazon Store. Tap to continue.", (int)(hd*60), (int)(hd*245));
					//menufnt.PrintAt(gl, "Are you sure you want to quit?", 60, 250);
					if (!menusystem.quit_yes_pressed) {
						menusystem.unlock_buynow.draw(gl, menusystem.quit_bt_yes[0], menusystem.quit_bt_yes[1]);
					} else {
						menusystem.unlock_buynow.draw(gl, menusystem.quit_bt_yes[0], menusystem.quit_bt_yes[1], 0.6f, 1.0f, 0.7f, 1.0f);
					}
					if (!menusystem.quit_no_pressed) {
						menusystem.mission_cancel.draw(gl, menusystem.quit_bt_no[0], menusystem.quit_bt_no[1]);
					} else {
						menusystem.mission_cancel.draw(gl, menusystem.quit_bt_no[0], menusystem.quit_bt_no[1], 0.6f, 1.0f, 0.7f, 1.0f);
					}
				}
				*/
				break;
			
			case 108 : 
				// continue case 10
			case 102 : 
				// continue case 10

//START GAME		
			case 10 : 
				
				if (entities_update) {
					entities_update = false;
					Log.w("Space Shooter", "Entities update called.");
					menusystem.releaseimages(gl);
					// load game entities
					allEntities.loadimages(gl, context);
					allBullets.loadimages(gl, context);
				}
				
				if (!pause) {
					// Start the game logic
					loops = 0;
					while( GetTickCount() > next_game_tick && loops < MAX_FRAMESKIP) {
						GameUpdateLogic(gl);
						if (next_game_tick == 0) { next_game_tick = GetTickCount(); freeze = false; } 
						next_game_tick += SKIP_TICKS;
						loops++;
					}
				}

				// load new missionbackground
		        if (allMissions.missionControl != null) {
		        	if (mission_update) {
		        		clouds = false;
		        		background.release(gl);
		        		background_clouds.release(gl);
		        		background_extra.release(gl);
		        		
		        			background = new MyBGScroller((int)(hd*480),(int)(hd*800),false);
		        			background.load(gl, context, allMissions.missionControl.background);
        					
		        			background_extra = new MyBGScroller((int)(hd*480),(int)(hd*800),false);
		        			
		        			clouds = true; 
        					background_clouds = new My2DImage((int)(hd*480),(int)(hd*800),true);
        					
        					boolean extranebu = false;
        					int extranebula[] = { 4, 18, 24, 55, 10, 69, 92, 97 };
        					for (int i=0; i<extranebula.length; i++) {
        						if (menusystem.selected_mission == extranebula[i]) {
        							extranebu = true;
        							break;
        						}
        					}
        					background_clouds.load(gl, context, allMissions.background_clouds[allMissions.nebula_type]);
        					if (extranebu) {
        						bg_extra = true;
        						background_extra.load(gl, context, allMissions.background_clouds[3]);
        						allMissions.missionControl.shadow = true;
        					} else {
        						bg_extra = false;
        					}
		        			
                			/*
                			// 50% to load clouds
		        			if (allMissions.missionControl.clouds) {
		        				//int random = (int)Math.ceil(Math.random()*100);
		        				//if (random < 50) {
		        					clouds = true; 
		        					background_clouds = new My2DImage((int)(hd*480),(int)(hd*800),true);
		                			background_clouds.load(gl, context, allMissions.background_clouds[0]);
		        				//}
		        			} else {
		        					clouds = true; 
		        					background_clouds = new My2DImage((int)(hd*480),(int)(hd*800),true);
		                			background_clouds.load(gl, context, allMissions.background_clouds[1]);
		        			}
		        			*/

		        		mission_update = false;
		        	}
	        		background.draw(gl, 0, bgloopY);
		        }
		        
		        
		        //if (!cinemamode) {
		        //skill_bar.draw(gl, (int)(hd*0), (int)(hd*720),1,1,1,0.6f);
		        //}
				
				Ondrawframe_case10(gl);
				
				if (media_update && music) {
					media_update = false;
					if (!mediaplayer.isPlaying()) {
						mediaplayer.start();
					}
				}

			if (!cinemamode) {
				/*
				if (!bmenu_button_down[0]) {
					if (spaceship.turret[spaceship.turret_active] >= 0) {
						allUpgrades.turret[spaceship.turret[spaceship.turret_active]].icon.draw(gl, (int)(hd*0), (int)(hd*720), 1.0f, 1.0f, 1.0f, 0.5f);
					} else {
						skill_icon.draw(gl, (int)(hd*0), (int)(hd*720), 1.0f, 1.0f, 1.0f, 0.5f);
					}
				} else {
					if (spaceship.turret[spaceship.turret_active] >= 0) {
						allUpgrades.turret[spaceship.turret[spaceship.turret_active]].icon.draw(gl, (int)(hd*0), (int)(hd*720), 0.5f, 1.0f, 0.8f, 0.5f);
					} else {
						skill_icon.draw(gl, (int)(hd*0), (int)(hd*720), 1.0f, 1.0f, 1.0f, 0.5f);
					}
				}
				
				if (!bmenu_button_down[1]) {
					if (spaceship.modifier[spaceship.modifier_active] >= 0) {
						allUpgrades.modifier[spaceship.modifier[spaceship.modifier_active]].icon.draw(gl, (int)(hd*80), (int)(hd*720), 1.0f, 1.0f, 1.0f, 0.5f);
					} else {
						skill_icon.draw(gl, (int)(hd*80), (int)(hd*720), 1.0f, 1.0f, 1.0f, 0.5f);
					}
				} else {
					if (spaceship.modifier[spaceship.modifier_active] >= 0) {
						allUpgrades.modifier[spaceship.modifier[spaceship.modifier_active]].icon.draw(gl, (int)(hd*80), (int)(hd*720), 0.5f, 1.0f, 0.8f, 0.5f);
					} else {
						skill_icon.draw(gl, (int)(hd*80), (int)(hd*720), 1.0f, 1.0f, 1.0f, 0.5f);
					}
				}
				*/
				
				for (int i=0;i<4;i++) {
					int j = 0;
					for (j=0;j<4;j++) { if (spaceship.special_order[j] == i) { break; } }
					if (!bmenu_button_down[j+2]) {
						if (i < spaceship.special_num) {
							if (spaceship.special[i] >= 0) {
								allUpgrades.special[spaceship.special[i]].icon.draw(gl, menusystem.skillpos_left[j][0], menusystem.skillpos_left[j][1], 1.0f, 1.0f, 1.0f, 0.5f);
								menusystem.layer[4].draw(gl, menusystem.skillpos_left[j][0], menusystem.skillpos_left[j][1], 1.0f, 1.0f, 1.0f, 0.5f);
								
								// special cooldown timer beszürkítése
								if (!spaceship.special_cd[i]) {
									float time = (spaceship.cdmod*allUpgrades.special[spaceship.special[i]].cooldown*1.10f) - ((time_current - spaceship.special_cd_timer[i]) / 1000 / game_timer_fix);
									float ysc = time / (spaceship.cdmod*allUpgrades.special[spaceship.special[i]].cooldown*1.10f);
									if (ysc <= 0.10f) { 
										ysc = 0;
										spaceship.special_cd[i] = true;
									} else {
										if (time > spaceship.cdmod*allUpgrades.special[spaceship.special[i]].cooldown/10 + 2) {
											
											int kocka = 12-(int)((ysc-0.1f)*100/7.692f);
											if (kocka<0) kocka = 0; else if (kocka>12) kocka = 12;
											menusystem.cd[0].draw(gl, menusystem.skillpos_left[j][0], menusystem.skillpos_left[j][1], 0.25f, 0.25f, 0.25f, 0.25f);
											menusystem.cd[kocka].draw(gl, menusystem.skillpos_left[j][0], menusystem.skillpos_left[j][1], 0.50f, 0.50f, 0.50f, 0.50f); 
											
											//menusystem.black.draw(gl, (int)(hd*160)+j*(int)(hd*80), (int)(hd*720)+(1-ysc)*(int)(hd*80), 1.0f, 1.0f, 1.0f, 0.5f, hd*0.8f, hd*0.8f*ysc);
										} else {
											
											menusystem.cd[13].draw(gl, menusystem.skillpos_left[j][0], menusystem.skillpos_left[j][1], 0.5f, 0.5f, 0.5f, 0.5f);
											
											//menusystem.white.draw(gl, (int)(hd*164)+j*(int)(hd*80), (int)(hd*724), 0.5f, 0.5f, 0.5f, 0.5f, hd*0.72f, hd*0.72f);
										}
									}
								}
							} else {
								//skill_icon.draw(gl, (int)(hd*160)+j*(int)(hd*80), (int)(hd*720), 1.0f, 1.0f, 1.0f, 0.5f);
							}
						} else {
							//skill_icon.draw(gl, (int)(hd*160)+j*(int)(hd*80), (int)(hd*720), 1.0f, 1.0f, 1.0f, 0.5f);
						}
					} else {
						if (i < spaceship.special_num) {
							if (spaceship.special[i] >= 0) {
								allUpgrades.special[spaceship.special[i]].icon.draw(gl, menusystem.skillpos_left[j][0], menusystem.skillpos_left[j][1], 1.0f, 1.0f, 1.0f, 0.5f);
								menusystem.layer[4].draw(gl, menusystem.skillpos_left[j][0], menusystem.skillpos_left[j][1], 1.0f, 1.0f, 1.0f, 0.5f);
							} else {
								//skill_icon.draw(gl, (int)(hd*160)+j*(int)(hd*80), (int)(hd*720), 1.0f, 1.0f, 1.0f, 0.5f);
							}
						} else {
							//skill_icon.draw(gl, (int)(hd*160)+j*(int)(hd*80), (int)(hd*720), 1.0f, 1.0f, 1.0f, 0.5f);
						}
					}
				}
			
				if (spaceship.shield > 0) shield_bar.draw(gl, (int)(hd*466), (int)(hd*700) - (float)spaceship.shield/5*hd*32, 1.0f, 0.7f, 0.7f, 0.7f, 0.7f, (float)spaceship.shield/5);
				if (spaceship.hp > 0) structure_bar.draw(gl, (int)(hd*2), (int)(hd*700) - (float)spaceship.hp/5*hd*32, 1.0f, 0.7f, 0.7f, 0.7f, 0.7f, (float)spaceship.hp/5);
			
				int stbarnum = (int)spaceship.hp_max / 5;
				for (int i=0; i<stbarnum; i++) {
					hud_bar.draw(gl, (int)(hd*0), (int)(hd*668)-i*(int)(hd*32), 1.0f, 1.0f, 1.0f, 1.0f);	
				}

				int shbarnum = (int)spaceship.shield_max / 5;
				for (int i=0; i<shbarnum; i++) {
					hud_bar.draw(gl, (int)(hd*464), (int)(hd*668)-i*(int)(hd*32), 1.0f, 1.0f, 1.0f, 1.0f);	
				}
				
				//menufnt.SetScale(scrScaleX*menufnt_scale,scrScaleY*menufnt_scale);
				if (endmission_state < 2) {
					menufnt.PrintAt(gl, "LY: " + toLY(missionLY, false), (int)(hd*5), (int)(hd*3));
				}
				
			}
				
				if (menusystem.dialogue_game && menusystem.dialogue_game_num >= 0) {
					int num = menusystem.dialogue_game_num;
				
					try {	
						menusystem.dialogue_bg.draw(gl, 0, 0);
						if (allMissions.Dialogue_list[num].length == 3) {
							menufnt.PrintAt(gl, allMissions.Dialogue_list[num][0], menusystem.dialogue_xy[3][0], menusystem.dialogue_xy[3][1]);
							menufnt.PrintAt(gl, allMissions.Dialogue_list[num][1], menusystem.dialogue_xy[4][0], menusystem.dialogue_xy[4][1]);
							menufnt.PrintAt(gl, allMissions.Dialogue_list[num][2], menusystem.dialogue_xy[5][0], menusystem.dialogue_xy[5][1]);
						} else if (allMissions.Dialogue_list[num].length == 2) {
							menufnt.PrintAt(gl, allMissions.Dialogue_list[num][0], menusystem.dialogue_xy[1][0], menusystem.dialogue_xy[1][1]);
							menufnt.PrintAt(gl, allMissions.Dialogue_list[num][1], menusystem.dialogue_xy[2][0], menusystem.dialogue_xy[2][1]);
						} else if (allMissions.Dialogue_list[num].length == 1) {
							menufnt.PrintAt(gl, allMissions.Dialogue_list[num][0], menusystem.dialogue_xy[0][0], menusystem.dialogue_xy[0][1]);
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					if (!menusystem.dialogue_game_isrunning) { 
						menusystem.dialogue_game_isrunning = true;
						this.postDelayed(new Runnable() { public void run() { 
							menusystem.dialogue_game = false; 
							menusystem.dialogue_game_num = -1; 
							menusystem.dialogue_game_isrunning = false; 
							} 
						}, menusystem.dialogue_delay);
					}
				} else { menusystem.dialogue_game = false; }

				if (pause) {
					//menusystem.black.draw(gl, (int)(hd*40), (int)(hd*200), 0.6f, 0.6f, 0.6f, 0.6f, hd*4.0f, hd*2.0f);
					menusystem.verify_border.draw(gl, (int)(hd*36), (int)(hd*184));
					//menufnt.PrintAt(gl, "Level progress will be lost.", (int)(hd*60), (int)(hd*220));
					//menufnt.PrintAt(gl, "Are you sure you want to quit?", (int)(hd*60), (int)(hd*250));
					menusystem.quitmission_text.draw(gl, (int)(hd*50), (int)(hd*220));

					if (!menusystem.quit_yes_pressed) {
						menusystem.quit_yes.draw(gl, menusystem.quit_bt_yes[0], menusystem.quit_bt_yes[1]);
					} else {
						menusystem.quit_yes.draw(gl, menusystem.quit_bt_yes[0], menusystem.quit_bt_yes[1], 0.6f, 1.0f, 0.7f, 1.0f);
					}
					if (!menusystem.quit_no_pressed) {
						menusystem.quit_no.draw(gl, menusystem.quit_bt_no[0], menusystem.quit_bt_no[1]);
					} else {
						menusystem.quit_no.draw(gl, menusystem.quit_bt_no[0], menusystem.quit_bt_no[1], 0.6f, 1.0f, 0.7f, 1.0f);
					}
				}

				
				break;
				
			default : 
				
				break;
		}
//SWITCH-CASE
				
		fps_framecount+=1; 
		fps_sum+= fps;
		//fnt.SetCursor(50, 250);
		menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
		//fnt.SetScale(2);
		//menufnt.SetScale(scrScaleX*menufnt_scale,scrScaleY*menufnt_scale);

		if (fps_framecount == 100) {
			v1 = fps;
			v2 = (int)fps_sum/100;
			fps_sum = 0;
			fps_framecount = 0;
		}
		if (looptime_dif >= 5000) {
			v3 = (int) (gameloops / 5); gameloops = 0; looptime_dif = 0;
		}
		
		for(int i=0;i<10;i++)if(helper[i]!="")menufnt.PrintAt(gl, helper[i], 15, 100+i*menufnt_space);


		//menufnt.PrintAt(gl, Integer.toString((int)v3), 15, 120);
		//menufnt.PrintAt(gl, Integer.toString((int)(algrades.turret[spaceship.turret_active].rateoffire + spaceship.rofmod)), 15, 150);
		//if (spaceship.turret_active >= 0)
		//	if (spaceship.turret[spaceship.turret_active] >= 0) 
		//		menufnt.PrintAt(gl, Integer.toString((int) (1 / (allUpgrades.turret[spaceship.turret[spaceship.turret_active]].rateoffire + spaceship.rofmod) * 1000)), 15, 180);
		
		//menufnt.PrintAt(gl, "FPS AVG: "+Integer.toString(v2), 15, 150);
		//menufnt.PrintAt(gl, "LOOPS: "+Integer.toString(v3), 15, 200);
		
		//menusystem.white.draw(gl, 100, 100);

		//fnt.PrintAt(gl, Integer.toString(spaceship.hp)+"/"+Integer.toString(spaceship.hp_max), 20, 670);
		//fnt.PrintAt(gl, Integer.toString(spaceship.shield)+"/"+Integer.toString(spaceship.shield_max), 380, 670);

		//fnt.PrintAt(gl, ""+Integer.toString(npc.size())+", "+Integer.toString(spaceship_bullet.size()), 15, 250);

//		fnt.SetPolyColor(1.0f,1.0f,1.0f,0.5f); if (v1 > 0) { 
	
	// catch error in drawing frame and print to stacktrace
	} catch (Exception e) { e.printStackTrace(); } 
	}
	
	public void Ondrawframe_case9(GL10 gl) {
		
		//menusystem.upgrade_bg[spaceship.type].draw(gl, 0, 0);
		menusystem.upgrade_bg.draw(gl, 0, 0);
		
		if (!menusystem.upgrade_selected) {
			
			//menusystem.upgrade_border.draw(gl, 0, 0);
			
			skill_icon.draw(gl, menusystem.upgrade_icon_ship_bt[0], menusystem.upgrade_icon_ship_bt[1]);
			allUpgrades.ship[spaceship.type].icon.draw(gl, menusystem.upgrade_icon_ship_bt[0], menusystem.upgrade_icon_ship_bt[1]);
			menusystem.layer[allUpgrades.ship[spaceship.type].layer].draw(gl, menusystem.upgrade_icon_ship_bt[0], menusystem.upgrade_icon_ship_bt[1]);

			skill_buyicon.draw(gl, menusystem.upgrade_icon_purchasable_bt[0], menusystem.upgrade_icon_purchasable_bt[1]);

			//menusystem.white.draw(gl, 20, 160, 0.2f, 0.0f, 0.0f, 0.2f, 4.3f, 1.08f);
			for (int i=0;i<allUpgrades.ship[spaceship.type].turret_num;i++) {
				skill_icon.draw(gl, menusystem.upgrade_icon_weapon_bt[i][0], menusystem.upgrade_icon_weapon_bt[i][1]);
				if (spaceship.turret[i] >= 0) {
					allUpgrades.turret[spaceship.turret[i]].icon.draw(gl, menusystem.upgrade_icon_weapon_bt[i][0], menusystem.upgrade_icon_weapon_bt[i][1]);
					menusystem.layer[allUpgrades.turret[spaceship.turret[i]].layer].draw(gl, menusystem.upgrade_icon_weapon_bt[i][0], menusystem.upgrade_icon_weapon_bt[i][1]);
				}
			}
			//menufnt.PrintAt(gl, "Turrets", 34, 161);
			
			//menusystem.white.draw(gl, 20, 275, 0.0f, 0.0f, 0.2f, 0.2f, 4.3f, 1.08f);
			for (int i=0;i<allUpgrades.ship[spaceship.type].modifier_num;i++) {
				skill_icon.draw(gl, menusystem.upgrade_icon_modifier_bt[i][0], menusystem.upgrade_icon_modifier_bt[i][1]);
				if (spaceship.modifier[i] >= 0) {
					allUpgrades.modifier[spaceship.modifier[i]].icon.draw(gl, menusystem.upgrade_icon_modifier_bt[i][0], menusystem.upgrade_icon_modifier_bt[i][1]);
					menusystem.layer[allUpgrades.modifier[spaceship.modifier[i]].layer].draw(gl, menusystem.upgrade_icon_modifier_bt[i][0], menusystem.upgrade_icon_modifier_bt[i][1]);
				}
			}
			//menufnt.PrintAt(gl, "Turret Modifiers", 34, 276);
			
			//menusystem.white.draw(gl, 20, 390, 0.2f, 0.2f, 0, 0.2f, 4.3f, 1.08f);
			for (int i=0;i<allUpgrades.ship[spaceship.type].special_num;i++) {
				skill_icon.draw(gl, menusystem.upgrade_icon_special_bt[i][0], menusystem.upgrade_icon_special_bt[i][1]);
				if (spaceship.special[i] >= 0) {
					allUpgrades.special[spaceship.special[i]].icon.draw(gl, menusystem.upgrade_icon_special_bt[i][0], menusystem.upgrade_icon_special_bt[i][1]);
					menusystem.layer[allUpgrades.special[spaceship.special[i]].layer].draw(gl, menusystem.upgrade_icon_special_bt[i][0], menusystem.upgrade_icon_special_bt[i][1]);
				}
			}
			//menufnt.PrintAt(gl, "Special Modules", 34, 391);
			
			//menusystem.white.draw(gl, 20, 505, 0.0f, 0.2f, 0, 0.2f, 4.3f, 1.98f);
			for (int i=0;i<allUpgrades.ship[spaceship.type].upgrade_num;i++) {
				skill_icon.draw(gl, menusystem.upgrade_icon_upgrade_bt[i][0], menusystem.upgrade_icon_upgrade_bt[i][1]);
				if (spaceship.upgrade[i] >= 0) {
					allUpgrades.upgrade[spaceship.upgrade[i]].icon.draw(gl, menusystem.upgrade_icon_upgrade_bt[i][0], menusystem.upgrade_icon_upgrade_bt[i][1]);
					menusystem.layer[allUpgrades.upgrade[spaceship.upgrade[i]].layer].draw(gl, menusystem.upgrade_icon_upgrade_bt[i][0], menusystem.upgrade_icon_upgrade_bt[i][1]);
				}
			}
			//menufnt.PrintAt(gl, "Ship Upgrades", 34, 506);
			
			int[] panelXY = { (int)(hd*248), (int)(hd*29) }; 
			//menusystem.black.draw(gl, panelXY[0]-5, panelXY[1]-5, 1, 1, 1, 0.6f, 2.2f, 1.4f);
			//menufnt.SetScale(scrScaleX*menufnt_scale,scrScaleY*menufnt_scale);
			menufnt.PrintAt(gl, "Type: " + allUpgrades.ship[spaceship.type].name, panelXY[0], panelXY[1]);
			menufnt.PrintAt(gl, "Hull: " + Integer.toString(spaceship.hp_max), panelXY[0], panelXY[1]+menufnt_space);
			menufnt.PrintAt(gl, "Shield: " + Integer.toString(spaceship.shield_max), panelXY[0], panelXY[1]+menufnt_space*2);
			menufnt.PrintAt(gl, "Damage: +" + Float.toString(spaceship.damagemod), panelXY[0], panelXY[1]+menufnt_space*3);
			menufnt.PrintAt(gl, "Fire Rate: +" + Float.toString(spaceship.rofmod), panelXY[0], panelXY[1]+menufnt_space*4);
			menufnt.PrintAt(gl, "Speed: " + Float.toString(spaceship.speed), panelXY[0], panelXY[1]+menufnt_space*5);
		
		} else {
			menusystem.black.draw(gl, 0, 0, 0.6f, 0.6f, 0.6f, 0.6f, hd*4.8f, hd*8.0f);
			//menusystem.black.draw(gl, (int)(hd*40), (int)(hd*120), 1, 1, 1, 0.6f, hd*4.0f, hd*5.5f);
			menusystem.options_border.draw(gl, (int)(hd*9), (int)(hd*106));
			
			int posXY[] = { 0, 0, 0 };
			posXY[0] = menusystem.upgrade_bt_start[0];
			posXY[1] = menusystem.upgrade_bt_start[1];
			posXY[2] = menusystem.upgrade_bt_start[2];
			
			
			// temp page data (page refresh purposes)
			int[] itemlist = { -2, -2, -2, -2, -2, -2 };
			int pp = menusystem.upgrade_page;
			int mp = menusystem.upgrade_maxpage;
			for (int i=0;i<6;i++) itemlist[i] = menusystem.itemlist[i];
			int selected[] = { 	menusystem.upgrade_selected_weapon,
								menusystem.upgrade_selected_modifier,
								menusystem.upgrade_selected_special,
								menusystem.upgrade_selected_upgrade,
								menusystem.upgrade_selected_ship,
								menusystem.upgrade_selected_purchasable,
			};

			//menufnt.SetScale(scrScaleX*menufnt_scale,scrScaleY*menufnt_scale);

			// Up and Down arrows
			if (mp > 1) {
				if (pp > 0) {
					menusystem.upgrade_uparrow.draw(gl, menusystem.upgrade_bt_up[0], menusystem.upgrade_bt_up[1]);
				}
				if (pp < mp-1) {
					menusystem.upgrade_downarrow.draw(gl, menusystem.upgrade_bt_down[0], menusystem.upgrade_bt_down[1]);
				}
				menufnt.PrintAt(gl, "Page " + Integer.toString(pp+1) + " of " + Integer.toString(mp), (int)(hd*270), (int)(hd*672));
			}
			// TURRET SELECTED
			if (selected[0] >= 0) {
				for (int j=0;j<6;j++) {
					if (itemlist[j] == -1) {
						skill_icon.draw(gl, posXY[0], posXY[1]);
						menufnt.PrintAt(gl, "Empty this slot", posXY[0]+(int)(hd*85), posXY[1]);
					}
					if (itemlist[j] >= 0 && itemlist[j] < allUpgrades.maxturret) {
						allUpgrades.turret[itemlist[j]].icon.draw(gl, posXY[0], posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menusystem.layer[allUpgrades.turret[itemlist[j]].layer].draw(gl, posXY[0], posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.turret[itemlist[j]].name, posXY[0]+(int)(hd*85), posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.turret[itemlist[j]].description, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*19) + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.turret[itemlist[j]].description2, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*38) + (j-1)*posXY[2]);
						if (j == 1 && itemlist[j] == spaceship.turret[selected[0]]) {
							menufnt.SetPolyColor(0.4f, 0.4f, 1.0f, menufnt_alfa);
							menufnt.PrintAt(gl, "Currently Equipped", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
							menufnt.SetPolyColor(1,1,1,menufnt_alfa);
						} else {
							if (allUpgrades.turret[itemlist[j]].bought) {
								menufnt.SetPolyColor(0.4f, 1.0f, 0.4f, menufnt_alfa);
								menufnt.PrintAt(gl, "In Stock", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							} else {
								if (!allUpgrades.turret[itemlist[j]].buycode.toString().equals("") && !allUpgrades.turret[itemlist[j]].bought) {
									menufnt.SetPolyColor(1.0f, 0.0f, 1.0f, menufnt_alfa);
									menufnt.PrintAt(gl, "Purchasable", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
									menufnt.SetPolyColor(1,1,1,menufnt_alfa);
								} else 
								if (allUpgrades.turret[itemlist[j]].fullgame && !fullgame) {
									menufnt.SetPolyColor(1.0f, 0.6f, 0.0f, menufnt_alfa);
									menufnt.PrintAt(gl, "Requires Full Game", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
									menufnt.SetPolyColor(1,1,1,menufnt_alfa);
								} else 
								if (allUpgrades.turret[itemlist[j]].require >= 0) {
									if (!allUpgrades.turret[allUpgrades.turret[itemlist[j]].require].bought) { 
										menufnt.SetPolyColor(1.0f, 0.2f, 0.2f, menufnt_alfa);
										menufnt.PrintAt(gl, "Requires " + allUpgrades.turret[allUpgrades.turret[itemlist[j]].require].name, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
									} else {
										if (allUpgrades.turret[itemlist[j]].cost > LY) { 
											menufnt.SetPolyColor(1.0f, 0.2f, 0.2f, menufnt_alfa);
										}
										menufnt.PrintAt(gl, "Cost: " + toLY(allUpgrades.turret[itemlist[j]].cost, false) + " LY", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
									}
									menufnt.SetPolyColor(1,1,1,menufnt_alfa);
								}
								else {
									if (allUpgrades.turret[itemlist[j]].cost > LY) { 
										menufnt.SetPolyColor(1.0f, 0.2f, 0.2f, menufnt_alfa);
									}
									menufnt.PrintAt(gl, "Cost: " + toLY(allUpgrades.turret[itemlist[j]].cost, false) + " LY", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								}
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							}
						}
					}
				}						
			}
			// MODIFIER SELECTED
			else if (selected[1] >= 0) {
				for (int j=0;j<6;j++) {
					if (itemlist[j] == -1) {
						skill_icon.draw(gl, posXY[0], posXY[1]);
						menufnt.PrintAt(gl, "Empty this slot", posXY[0]+(int)(hd*85), posXY[1]);
					}
					if (itemlist[j] >= 0 && itemlist[j] < allUpgrades.maxmodifier) {
						allUpgrades.modifier[itemlist[j]].icon.draw(gl, posXY[0], posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menusystem.layer[allUpgrades.modifier[itemlist[j]].layer].draw(gl, posXY[0], posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.modifier[itemlist[j]].name, posXY[0]+(int)(hd*85), posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.modifier[itemlist[j]].description, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*19) + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.modifier[itemlist[j]].description2, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*38) + (j-1)*posXY[2]);
						if (j == 1 && itemlist[j] == spaceship.modifier[selected[1]]) {
							menufnt.SetPolyColor(0.4f, 0.4f, 1.0f, menufnt_alfa);
							menufnt.PrintAt(gl, "Currently Equipped", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
							menufnt.SetPolyColor(1,1,1,menufnt_alfa);
						} else {
							if (!allUpgrades.modifier[itemlist[j]].buycode.toString().equals("") && !allUpgrades.modifier[itemlist[j]].bought) {
								menufnt.SetPolyColor(1.0f, 0.0f, 1.0f, menufnt_alfa);
								menufnt.PrintAt(gl, "Purchasable", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							} else 
							if (allUpgrades.modifier[itemlist[j]].fullgame && !fullgame) {
								menufnt.SetPolyColor(1.0f, 0.6f, 0.0f, menufnt_alfa);
								menufnt.PrintAt(gl, "Requires Full Game", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							} else
							if (allUpgrades.modifier[itemlist[j]].bought) {
								menufnt.SetPolyColor(0.4f, 1.0f, 0.4f, menufnt_alfa);
								menufnt.PrintAt(gl, "In Stock", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							} else {
								if (allUpgrades.modifier[itemlist[j]].require >= 0) {
									if (!allUpgrades.modifier[allUpgrades.modifier[itemlist[j]].require].bought) { 
										menufnt.SetPolyColor(1.0f, 0.2f, 0.2f, menufnt_alfa);
										menufnt.PrintAt(gl, "Requires " + allUpgrades.modifier[allUpgrades.modifier[itemlist[j]].require].name, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
									} else {
										if (allUpgrades.modifier[itemlist[j]].cost > LY) { 
											menufnt.SetPolyColor(1.0f, 0.2f, 0.2f, menufnt_alfa);
										}
										menufnt.PrintAt(gl, "Cost: " + toLY(allUpgrades.modifier[itemlist[j]].cost, false) + " LY", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
									}
								}
								else {
									if (allUpgrades.modifier[itemlist[j]].cost > LY) { 
										menufnt.SetPolyColor(1.0f, 0.2f, 0.2f, menufnt_alfa);
									}
									menufnt.PrintAt(gl, "Cost: " + toLY(allUpgrades.modifier[itemlist[j]].cost, false) + " LY", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								}
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							}
						}
					}
				}
				
			}
			// SPECIAL SELECTED
			else if (selected[2] >= 0) {
				for (int j=0;j<6;j++) {
					if (itemlist[j] == -1) {
						skill_icon.draw(gl, posXY[0], posXY[1]);
						menufnt.PrintAt(gl, "Empty this slot", posXY[0]+(int)(hd*85), posXY[1]);
					}
					if (itemlist[j] >= 0 && itemlist[j] < allUpgrades.maxspecial) {
						allUpgrades.special[itemlist[j]].icon.draw(gl, posXY[0], posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menusystem.layer[allUpgrades.special[itemlist[j]].layer].draw(gl, posXY[0], posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.special[itemlist[j]].name, posXY[0]+(int)(hd*85), posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.special[itemlist[j]].description, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*19) + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.special[itemlist[j]].description2, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*38) + (j-1)*posXY[2]);
						if (j == 1 && itemlist[j] == spaceship.special[selected[2]]) {
							menufnt.SetPolyColor(0.4f, 0.4f, 1.0f, menufnt_alfa);
							menufnt.PrintAt(gl, "Currently Equipped", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
							menufnt.SetPolyColor(1,1,1,menufnt_alfa);
						} else {
							if (!allUpgrades.special[itemlist[j]].buycode.toString().equals("") && !allUpgrades.special[itemlist[j]].bought) {
								menufnt.SetPolyColor(1.0f, 0.0f, 1.0f, menufnt_alfa);
								menufnt.PrintAt(gl, "Purchasable", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							} else 
							if (allUpgrades.special[itemlist[j]].fullgame && !fullgame) {
								menufnt.SetPolyColor(1.0f, 0.6f, 0.0f, menufnt_alfa);
								menufnt.PrintAt(gl, "Requires Full Game", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							} else
							if (allUpgrades.special[itemlist[j]].bought) {
								menufnt.SetPolyColor(0.4f, 1.0f, 0.4f, menufnt_alfa);
								menufnt.PrintAt(gl, "In Stock", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							} else {
								if (allUpgrades.special[itemlist[j]].require >= 0) {
									if (!allUpgrades.special[allUpgrades.special[itemlist[j]].require].bought) { 
										menufnt.SetPolyColor(1.0f, 0.2f, 0.2f, menufnt_alfa);
										menufnt.PrintAt(gl, "Requires " + allUpgrades.special[allUpgrades.special[itemlist[j]].require].name, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
									} else {
										if (allUpgrades.special[itemlist[j]].cost > LY) { 
											menufnt.SetPolyColor(1.0f, 0.2f, 0.2f, menufnt_alfa);
										}
										menufnt.PrintAt(gl, "Cost: " + toLY(allUpgrades.special[itemlist[j]].cost, false) + " LY", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
									}
								}
								else {
									if (allUpgrades.special[itemlist[j]].cost > LY) { 
										menufnt.SetPolyColor(1.0f, 0.2f, 0.2f, menufnt_alfa);
									}
									menufnt.PrintAt(gl, "Cost: " + toLY(allUpgrades.special[itemlist[j]].cost, false) + " LY", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								}
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							}
						}
					}
				}
				
			}
			// UPGRADE SELECTED
			else if (selected[3] >= 0) {
				for (int j=0;j<6;j++) {
					if (itemlist[j] == -1) {
						skill_icon.draw(gl, posXY[0], posXY[1]);
						menufnt.PrintAt(gl, "Empty this slot", posXY[0]+(int)(hd*85), posXY[1]);
					}
					if (itemlist[j] >= 0 && itemlist[j] < allUpgrades.maxupgrade) {
						allUpgrades.upgrade[itemlist[j]].icon.draw(gl, posXY[0], posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menusystem.layer[allUpgrades.upgrade[itemlist[j]].layer].draw(gl, posXY[0], posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.upgrade[itemlist[j]].name, posXY[0]+(int)(hd*85), posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.upgrade[itemlist[j]].description, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*19) + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.upgrade[itemlist[j]].description2, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*38) + (j-1)*posXY[2]);
						if (j == 1 && itemlist[j] == spaceship.upgrade[selected[3]]) {
							menufnt.SetPolyColor(0.4f, 0.4f, 1.0f, menufnt_alfa);
							menufnt.PrintAt(gl, "Currently Equipped", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
							menufnt.SetPolyColor(1,1,1,menufnt_alfa);
						} else {
							if (!allUpgrades.upgrade[itemlist[j]].buycode.toString().equals("") && !allUpgrades.upgrade[itemlist[j]].bought) {
								menufnt.SetPolyColor(1.0f, 0.0f, 1.0f, menufnt_alfa);
								menufnt.PrintAt(gl, "Purchasable", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							} else 
							if (allUpgrades.upgrade[itemlist[j]].fullgame && !fullgame) {
								menufnt.SetPolyColor(1.0f, 0.6f, 0.0f, menufnt_alfa);
								menufnt.PrintAt(gl, "Requires Full Game", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							} else
							if (allUpgrades.upgrade[itemlist[j]].bought) {
								menufnt.SetPolyColor(0.4f, 1.0f, 0.4f, menufnt_alfa);
								menufnt.PrintAt(gl, "In Stock", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							} else {
								if (allUpgrades.upgrade[itemlist[j]].require >= 0) {
									if (!allUpgrades.upgrade[allUpgrades.upgrade[itemlist[j]].require].bought) { 
										menufnt.SetPolyColor(1.0f, 0.2f, 0.2f, menufnt_alfa);
										menufnt.PrintAt(gl, "Requires " + allUpgrades.upgrade[allUpgrades.upgrade[itemlist[j]].require].name, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
									} else {
										if (allUpgrades.upgrade[itemlist[j]].cost > LY) { 
											menufnt.SetPolyColor(1.0f, 0.2f, 0.2f, menufnt_alfa);
										}
										menufnt.PrintAt(gl, "Cost: " + toLY(allUpgrades.upgrade[itemlist[j]].cost, false) + " LY", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
									}
								}
								else {
									if (allUpgrades.upgrade[itemlist[j]].cost > LY) { 
										menufnt.SetPolyColor(1.0f, 0.2f, 0.2f, menufnt_alfa);
									}
									menufnt.PrintAt(gl, "Cost: " + toLY(allUpgrades.upgrade[itemlist[j]].cost, false) + " LY", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								}
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							}
						}
					}
				}
				
			}
			// SHIP SELECTED
			if (selected[4] >= 0) {
				for (int j=0;j<6;j++) {
					if (itemlist[j] >= 0 && itemlist[j] < allUpgrades.maxship) { // total ship's number
						//allUpgrades.ship[itemlist[j]].image[0].draw(gl, posXY[0], posXY[1]+posXY[2] + (j-1)*posXY[2],1,1,1,1,hd*76.0f/allUpgrades.ship[itemlist[j]].image_size[0],hd*76.0f/allUpgrades.ship[itemlist[j]].image_size[1]);
						allUpgrades.ship[itemlist[j]].icon.draw(gl, posXY[0], posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menusystem.layer[allUpgrades.ship[itemlist[j]].layer].draw(gl, posXY[0], posXY[1]+posXY[2] + (j-1)*posXY[2]);

						menufnt.PrintAt(gl, allUpgrades.ship[itemlist[j]].name, posXY[0]+(int)(hd*85), posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.ship[itemlist[j]].description, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*19) + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.ship[itemlist[j]].description2, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*38) + (j-1)*posXY[2]);
						if (j == 0) {
							menufnt.SetPolyColor(0.4f, 0.4f, 1.0f, menufnt_alfa);
							menufnt.PrintAt(gl, "Current Ship", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
							menufnt.SetPolyColor(1,1,1,menufnt_alfa);
						} else {
							if (!allUpgrades.ship[itemlist[j]].buycode.toString().equals("") && !allUpgrades.ship[itemlist[j]].bought) {
								menufnt.SetPolyColor(1.0f, 0.0f, 1.0f, menufnt_alfa);
								menufnt.PrintAt(gl, "Purchasable", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							} else 
							if (allUpgrades.ship[itemlist[j]].fullgame && !fullgame) {
								menufnt.SetPolyColor(1.0f, 0.6f, 0.0f, menufnt_alfa);
								menufnt.PrintAt(gl, "Requires Full Game", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							} else
							if (allUpgrades.ship[itemlist[j]].bought) {
								menufnt.SetPolyColor(0.4f, 1.0f, 0.4f, menufnt_alfa);
								menufnt.PrintAt(gl, "In Stock", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							} else
							if (!allUpgrades.ship[itemlist[j]].available && allUpgrades.ship[itemlist[j]].show) {
								menufnt.SetPolyColor(1.0f, 1.0f, 0.2f, menufnt_alfa);
								menufnt.PrintAt(gl, "Not available yet", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							} else {
								if (allUpgrades.ship[itemlist[j]].cost > LY) { menufnt.SetPolyColor(1.0f, 0.2f, 0.2f, 1.0f); }
								menufnt.PrintAt(gl, "Cost: " + toLY(allUpgrades.ship[itemlist[j]].cost, false) + " LY", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
								menufnt.SetPolyColor(1,1,1,menufnt_alfa);
							}
						}
					}
				}						
			}

			// PURCHASABLE SELECTED
			else if (selected[5] >= 0) {
				for (int j=0;j<6;j++) {
					if (itemlist[j] == -1) {
						skill_icon.draw(gl, posXY[0], posXY[1]);
						menufnt.PrintAt(gl, "Return to ship upgrades", posXY[0]+(int)(hd*85), posXY[1]);
					}
					if (itemlist[j] >= 0 && itemlist[j] < allUpgrades.maxpurchasable) {
						allUpgrades.purchasable[itemlist[j]].icon.draw(gl, posXY[0], posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menusystem.layer[allUpgrades.purchasable[itemlist[j]].layer].draw(gl, posXY[0], posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.purchasable[itemlist[j]].name, posXY[0]+(int)(hd*85), posXY[1]+posXY[2] + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.purchasable[itemlist[j]].description, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*19) + (j-1)*posXY[2]);
						menufnt.PrintAt(gl, allUpgrades.purchasable[itemlist[j]].description2, posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*38) + (j-1)*posXY[2]);

						if (!allUpgrades.purchasable[itemlist[j]].buycode.toString().equals("") && !allUpgrades.purchasable[itemlist[j]].bought) {
							if (allUpgrades.purchasable[itemlist[j]].buycode.toString().equals("tapjoy")) {
								menufnt.SetPolyColor(0.0f, 1.0f, 0.0f, menufnt_alfa);
								menufnt.PrintAt(gl, "Free", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
							} else {
								menufnt.SetPolyColor(1.0f, 0.0f, 1.0f, menufnt_alfa);
								menufnt.PrintAt(gl, "Purchasable", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
							}
							menufnt.SetPolyColor(1,1,1,menufnt_alfa);
						} else {
							menufnt.SetPolyColor(1.0f, 0.6f, 0.0f, menufnt_alfa);
							menufnt.PrintAt(gl, "Currently unavailable", posXY[0]+(int)(hd*85), posXY[1]+posXY[2]+(int)(hd*57) + (j-1)*posXY[2]);
							menufnt.SetPolyColor(1,1,1,menufnt_alfa);
						}
					}
				}
				
			}

			if (menusystem.upgrade_slot_selected >= 0) {
				menusystem.white.draw(gl, posXY[0], posXY[1]+posXY[2] + (menusystem.upgrade_slot_selected-1)*posXY[2], 0.1f, 0.3f, 0.2f, 0.01f, hd*3.9f, hd*0.8f);
			}

		}
		
		menufnt.PrintAt(gl, "LY: " + toLY(LY, false), (int)(hd*65), (int)(hd*29));
		
		if (menusystem.dialogue_upgrade && menusystem.dialogue_upgrade_num >= 0 &&
			menusystem.dialogue_upgrade_num < allMissions.Dialogue_list.length) {
			menusystem.dialogue_bg.draw(gl, 0, 0);
			menusystem.dialogue_bg.draw(gl, 0, 0);
			try {
				int dg = menusystem.dialogue_upgrade_num;
				if (allMissions.Dialogue_list[dg].length == 3) {
					menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][0], menusystem.dialogue_xy[3][0], menusystem.dialogue_xy[3][1]);
					menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][1], menusystem.dialogue_xy[4][0], menusystem.dialogue_xy[4][1]);
					menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][2], menusystem.dialogue_xy[5][0], menusystem.dialogue_xy[5][1]);
				} else if (allMissions.Dialogue_list[dg].length == 2) {
					menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][0], menusystem.dialogue_xy[1][0], menusystem.dialogue_xy[1][1]);
					menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][1], menusystem.dialogue_xy[2][0], menusystem.dialogue_xy[2][1]);
				} else if (allMissions.Dialogue_list[dg].length == 1) {
					menufnt.PrintAt(gl, allMissions.Dialogue_list[dg][0], menusystem.dialogue_xy[0][0], menusystem.dialogue_xy[0][1]);
				}
				if (!menusystem.dialogue_upgrade_isrunning) { 
					menusystem.dialogue_upgrade_isrunning = true;
					this.postDelayed(new Runnable() { public void run() { 
						menusystem.dialogue_upgrade = false; menusystem.dialogue_upgrade_isrunning = false; 
						menusystem.Update_Dialogues(menusystem.dialogue_upgrade_num, 2); 
						} 
					}, menusystem.dialogue_delay);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else { menusystem.dialogue_upgrade = false; }
		
		if (loading) {
			menusystem.black.draw(gl, 0, 0, 0.7f, 0.7f, 0.7f, 0.7f, hd*4.8f, hd*8.0f);
			menufnt.SetPolyColor(1.0f, 1.0f, 1.0f, menufnt_alfa);
			menufnt.PrintAt(gl, "Please wait while loading...", (int)(hd*120), (int)(hd*340));
		}

		
	}
	
	
	public void Ondrawframe_case10(GL10 gl) {
	
        // draw environmental entities first - hpmax = 0
		for(Entity npc_: npc){
			if (npc_.isalive && allEntities.npc[npc_.type].hp_max == 0 && !allEntities.npc[npc_.type].mineable) {
				// draw npc
				// Rotated
				if (npc_.rotate > 0) {
					allEntities.gfx[allEntities.npc[npc_.type].gfx].image[0].drawROT(gl, npc_.x, npc_.y, allEntities.npc[npc_.type].colorRGBA[0], allEntities.npc[npc_.type].colorRGBA[1], allEntities.npc[npc_.type].colorRGBA[2], allEntities.npc[npc_.type].colorRGBA[3], (float)allEntities.npc[npc_.type].width/allEntities.gfx[allEntities.npc[npc_.type].gfx].width, (float)allEntities.npc[npc_.type].height/allEntities.gfx[allEntities.npc[npc_.type].gfx].height, (float)npc_.rotate, (float)allEntities.npc[npc_.type].width/2, (float)allEntities.npc[npc_.type].height/2);
				// Non-rotated entity - save some transformation
				} else {
					allEntities.gfx[allEntities.npc[npc_.type].gfx].image[0].draw(gl, npc_.x, npc_.y, allEntities.npc[npc_.type].colorRGBA[0], allEntities.npc[npc_.type].colorRGBA[1], allEntities.npc[npc_.type].colorRGBA[2], allEntities.npc[npc_.type].colorRGBA[3], (float)allEntities.npc[npc_.type].width/allEntities.gfx[allEntities.npc[npc_.type].gfx].width, (float)allEntities.npc[npc_.type].height/allEntities.gfx[allEntities.npc[npc_.type].gfx].height);
				}
			}
		}
		
		if (bg_extra) background_extra.draw(gl, 0, bgextraloopY);
		
		if (gravity) {

			float nullparam = 400*hd;
			allExplosions.explosion[10].image[0]
					.drawROT(gl, (int)(hd*240)-nullparam/2, (int)(hd*400)-nullparam/2, 1,1,1,1,//0.6f,0.6f,0.6f,0.6f,
						// explosion effekt scale-ezése
						nullparam / allExplosions.explosion[10].width,
						nullparam / allExplosions.explosion[10].height,
						(float)gravity_rotate,		// ROTATION
						(int)(nullparam/2),(int)(nullparam/2)
			);
		}

        // draw mineables
		for(Entity npc_: npc){
			if (npc_.isalive && allEntities.npc[npc_.type].mineable && !npc_.mining) {
				// draw npc
				// Rotated
				if (npc_.rotate > 0) {
					allEntities.gfx[allEntities.npc[npc_.type].gfx].image[0].drawROT(gl, npc_.x, npc_.y, allEntities.npc[npc_.type].colorRGBA[0], allEntities.npc[npc_.type].colorRGBA[1], allEntities.npc[npc_.type].colorRGBA[2], allEntities.npc[npc_.type].colorRGBA[3], (float)allEntities.npc[npc_.type].width/allEntities.gfx[allEntities.npc[npc_.type].gfx].width, (float)allEntities.npc[npc_.type].height/allEntities.gfx[allEntities.npc[npc_.type].gfx].height, (float)npc_.rotate, (float)allEntities.npc[npc_.type].width/2, (float)allEntities.npc[npc_.type].height/2);
				// Non-rotated entity - save some transformation
				} else {
					allEntities.gfx[allEntities.npc[npc_.type].gfx].image[0].draw(gl, npc_.x, npc_.y, allEntities.npc[npc_.type].colorRGBA[0], allEntities.npc[npc_.type].colorRGBA[1], allEntities.npc[npc_.type].colorRGBA[2], allEntities.npc[npc_.type].colorRGBA[3], (float)allEntities.npc[npc_.type].width/allEntities.gfx[allEntities.npc[npc_.type].gfx].width, (float)allEntities.npc[npc_.type].height/allEntities.gfx[allEntities.npc[npc_.type].gfx].height);
				}
			}
		}
		
        // draw mining laser
        if (mining) {
        	float npcx = 0; float npcy = 0; boolean miner_found = false;
        	for(Entity npc_: npc){
				if (npc_.mining) {
					npcx = npc_.x + allEntities.npc[npc_.type].width/2;
					npcy = npc_.y + allEntities.npc[npc_.type].height/2;
					miner_found = true;
					// draw npc
					// Rotated
					if (npc_.rotate > 0) {
						allEntities.gfx[allEntities.npc[npc_.type].gfx].image[0].drawROT(gl, npc_.x, npc_.y, allEntities.npc[npc_.type].colorRGBA[0], allEntities.npc[npc_.type].colorRGBA[1], allEntities.npc[npc_.type].colorRGBA[2], allEntities.npc[npc_.type].colorRGBA[3], (float)allEntities.npc[npc_.type].width/allEntities.gfx[allEntities.npc[npc_.type].gfx].width, (float)allEntities.npc[npc_.type].height/allEntities.gfx[allEntities.npc[npc_.type].gfx].height, (float)npc_.rotate, (float)allEntities.npc[npc_.type].width/2, (float)allEntities.npc[npc_.type].height/2);
					// Non-rotated entity - save some transformation
					} else {
						allEntities.gfx[allEntities.npc[npc_.type].gfx].image[0].draw(gl, npc_.x, npc_.y, allEntities.npc[npc_.type].colorRGBA[0], allEntities.npc[npc_.type].colorRGBA[1], allEntities.npc[npc_.type].colorRGBA[2], allEntities.npc[npc_.type].colorRGBA[3], (float)allEntities.npc[npc_.type].width/allEntities.gfx[allEntities.npc[npc_.type].gfx].width, (float)allEntities.npc[npc_.type].height/allEntities.gfx[allEntities.npc[npc_.type].gfx].height);
					}
					break;
				}
        	}
        	if (miner_found) {
        		float shipx = spaceship.x+allUpgrades.ship[spaceship.type].width/2;
        		float shipy = spaceship.y+allUpgrades.ship[spaceship.type].height/2;
        		
        		// TODO check if ok - 1 nel kisebb akkor 1 - ez  még mindig hibás
        		float a = Math.abs(npcx - shipx); if (a < 1) a = 1;
				float b = Math.abs(npcy - shipy); if (b < 1) b = 1;
				double c = Math.sqrt(a*a+b*b);
				float alfa = (float)(57.295779513*Math.asin((float)a/c));
				//float cs;
				float cx = shipx - (float)allBullets.bullet[11].width / 2;
				float cy = shipy;
				if (npcx < shipx
					&& npcy < shipy) {
						//alfa+=90;
						alfa=180-alfa;
				} else if (npcx > shipx
						&& npcy < shipy) {
						alfa+=180;
				} else if (npcx > shipx
						&& npcy > shipy) {
						alfa=-alfa;
				} else {
				}
				//cs = (float)c/128;
				allBullets.bullet[11].image.drawROT(gl, cx, cy, 1, 1, 1, 0.5f, 1, (float)c/allBullets.bullet[11].height, alfa, (float)allBullets.bullet[11].width / 2, 0);
				//menusystem.white.drawROT(gl, cx, cy, 1, 1, 1, 0.5f, 1, (float)c/100, alfa, 50, 0);
				// draw mining effect
				allBullets.bullet[12].image.drawROT(gl, npcx-(float)allBullets.bullet[12].width/2, npcy-(float)allBullets.bullet[12].height/2, 1, 1, 1, 0.75f, 1, 1, alfa, (float)allBullets.bullet[12].width/2, (float)allBullets.bullet[12].height/2);
				
        	}
        }


		// draw pickups
		for(Pickup pickup_: pickup){
			if (pickup_.active && pickup_.floatingtext < 0) {

				// draw pickup shadow
				if (allMissions.missionControl.shadow) {
					float shadow_x = pickup_.x + pickup_.x/(int)(hd*400)*(int)(hd*8)+(int)(hd*16);
					float shadow_y = pickup_.y + pickup_.y/(int)(hd*720)*(int)(hd*20)+(int)(hd*20);
					float shadow_sizex = (float)allEntities.pickup[pickup_.type].width/(int)(hd*128);
					float shadow_sizey = (float)allEntities.pickup[pickup_.type].height/(int)(hd*128);
					npc_shadow.draw(gl, shadow_x, shadow_y, 1,1,1,0.3f, shadow_sizex, shadow_sizey);
				}
				
				if (allEntities.pickup[pickup_.type].anim >= 0) {
					allExplosions.explosion[allEntities.pickup[pickup_.type].anim].image[(int)(pickup_.loop/explosion_loop_scale)]
							.draw(gl, pickup_.x, pickup_.y, 
							allExplosions.explosion[allEntities.pickup[pickup_.type].anim].colorR, allExplosions.explosion[allEntities.pickup[pickup_.type].anim].colorG, allExplosions.explosion[allEntities.pickup[pickup_.type].anim].colorB, allExplosions.explosion[allEntities.pickup[pickup_.type].anim].colorA
							);

				} else {
				
					float scalex = (float)allEntities.pickup[pickup_.type].width/allEntities.gfx[allEntities.pickup[pickup_.type].gfx].width;
					float scaley = (float)allEntities.pickup[pickup_.type].height/allEntities.gfx[allEntities.pickup[pickup_.type].gfx].height;
					float centerx = pickup_.x;
					float centery = pickup_.y;
					
					// TODO kell ez???
					// pickup pulse size
						float a = (float)(pickup_.timer - time_current) /1000; // 10..07
						int b = (int)a;
						a = a - (int)a; // 0.99..0.00 x 10
						if (b % 2 == 0) a = 1-a;
						a *= 0.2f; // 0.2..0.0
						
						scalex = scalex * 0.9f + a; 
						scaley = scaley * 0.9f + a; 
						centerx = centerx + (float)allEntities.pickup[pickup_.type].width * (1-scalex) * 0.5f;
						centery = centery + (float)allEntities.pickup[pickup_.type].height * (1-scaley) * 0.5f;
						
					// draw pickup
					allEntities.gfx[allEntities.pickup[pickup_.type].gfx].image[0].draw(gl, centerx, centery, allEntities.pickup[pickup_.type].colorRGBA[0], allEntities.pickup[pickup_.type].colorRGBA[1], allEntities.pickup[pickup_.type].colorRGBA[2], allEntities.pickup[pickup_.type].colorRGBA[3], scalex, scaley);
				}
			} else {
				if (pickup_.active && pickup_.floatingtext >= 0) {
					
					// ha ly-t ad, akkor nem a descriptiont irjuk ki hanem a szamot (difficultzvel modositva)
					if (allEntities.pickup[pickup_.type].ly > 0) {
						int ly_ = allEntities.pickup[pickup_.type].ly;
						if (difficulty == 0) ly_ = (int)(ly_ * 0.5f); if (difficulty == 2) ly_ = (int)(ly_ * 1.5f);
						menufnt.PrintAt(gl, "+"+toLY(ly_,false)+" LY", (int)pickup_.x, (int)(pickup_.y - menufnt_space));
					} else {
						menufnt.PrintAt(gl, allEntities.pickup[pickup_.type].description, (int)pickup_.x, (int)(pickup_.y - menufnt_space));
					}
				}
				if (pickup_.explosion_loop > 0) {
					// or draw explosion
					int expnum = allEntities.pickup[pickup_.type].die_effect-1;
					// Rotated explosion
					if (allExplosions.explosion[expnum].rotation > 0) {
						allExplosions.explosion[expnum].image[(int)(pickup_.explosion_loop/explosion_loop_scale)]
							.drawROT(gl, pickup_.x, pickup_.y, allExplosions.explosion[expnum].colorR, allExplosions.explosion[expnum].colorG, allExplosions.explosion[expnum].colorB, allExplosions.explosion[expnum].colorA,
							// explosion effekt scale-ezése az pickup méretére
							(float)allEntities.pickup[pickup_.type].width/allExplosions.explosion[expnum].width,
							(float)allEntities.pickup[pickup_.type].height/allExplosions.explosion[expnum].height,
							allExplosions.explosion[expnum].rotation, (float)allEntities.pickup[pickup_.type].width/2, (float)allEntities.pickup[pickup_.type].height/2
							);
					// Non-rotated explosion - save some transformation
					} else {
						allExplosions.explosion[expnum].image[(int)(pickup_.explosion_loop/explosion_loop_scale)]
								.draw(gl, pickup_.x, pickup_.y, allExplosions.explosion[expnum].colorR, allExplosions.explosion[expnum].colorG, allExplosions.explosion[expnum].colorB, allExplosions.explosion[expnum].colorA,
								// explosion effekt scale-ezése az pickup méretére
								(float)allEntities.pickup[pickup_.type].width/allExplosions.explosion[expnum].width,
								(float)allEntities.pickup[pickup_.type].height/allExplosions.explosion[expnum].height
								);
					}
				}
			}
		}

		// draw other entities - hpmax > 0
		for(Entity npc_: npc){
			if (npc_.isalive && allEntities.npc[npc_.type].hp_max > 0 && !allEntities.npc[npc_.type].earthling) {

				// draw npc shadow
				if (allMissions.missionControl.shadow) {
					float shadow_x = npc_.x + npc_.x/(int)(hd*400)*(int)(hd*8)+(int)(hd*16);
					float shadow_y = npc_.y + npc_.y/(int)(hd*720)*(int)(hd*20)+(int)(hd*20);
					float shadow_sizex = (float)allEntities.npc[npc_.type].width/(int)(hd*128);
					float shadow_sizey = (float)allEntities.npc[npc_.type].height/(int)(hd*128);
					npc_shadow.draw(gl, shadow_x, shadow_y, 1,1,1,0.3f, shadow_sizex, shadow_sizey);
				}
				
				if (npc_.disruptor_enabled) {
					float alfaval = (float)(npc_.disruptor_timer - time_current
							- allEntities.npc_special[allEntities.npc[npc_.type].disruptor].cooldown*1000
							+ allEntities.npc_special[allEntities.npc[npc_.type].disruptor].duration)
							/ allEntities.npc_special[allEntities.npc[npc_.type].disruptor].duration;
					
					if (allEntities.npc_special[allEntities.npc[npc_.type].disruptor].type == 1) { if (alfaval < 0.4f) alfaval += 0.3f; }
					if (alfaval > 0) {

						// fix size for low detail
						float scaleX = (float)allEntities.npc_special[allEntities.npc[npc_.type].disruptor].width/
						allEntities.gfx[allEntities.npc_special[allEntities.npc[npc_.type].disruptor].image_id].width;
						float scaleY = (float)allEntities.npc_special[allEntities.npc[npc_.type].disruptor].height/
						allEntities.gfx[allEntities.npc_special[allEntities.npc[npc_.type].disruptor].image_id].height;
						float minus = allEntities.npc_special[allEntities.npc[npc_.type].disruptor].minus;
						
						// elhalvanyulos
						if (allEntities.npc_special[allEntities.npc[npc_.type].disruptor].type < 2) {

							
							//helper[0] = "scale x . " +Float.toString(scaleX);
							//helper[1] = "scale y . " +Float.toString(scaleY);
							//helper[2] = "scale x alfa . " +Float.toString(scaleX*alfaval);
							
							allEntities.gfx[allEntities.npc_special[allEntities.npc[npc_.type].disruptor].image_id].image[0].draw(gl, npc_.disruptor_xy[0], npc_.disruptor_xy[1]-minus, alfaval, alfaval, alfaval, alfaval, scaleX, scaleY);
						} else if (allEntities.npc_special[allEntities.npc[npc_.type].disruptor].type == 2) {
							float dx = npc_.disruptor_xy[0] + (float)allEntities.npc_special[allEntities.npc[npc_.type].disruptor].width/2*(1-alfaval);
							allEntities.gfx[allEntities.npc_special[allEntities.npc[npc_.type].disruptor].image_id].image[0].draw(gl, dx, npc_.disruptor_xy[1]-minus, 1, 1, 1, 0.7f, alfaval*scaleX, scaleY);
						}
					} else {
						npc_.disruptor_enabled = false;
						npc_.speedmod = 1.0f;
					}
				}
				
				// draw npc background
				int animnum = 11;
				float w = (float)allEntities.npc[npc_.type].width*1.5f;
				float h = (float)allEntities.npc[npc_.type].height*1.5f;
				
				allExplosions.explosion[animnum].image[(int)(npc_.bg_loop/explosion_loop_scale)]
						.drawROT(gl, npc_.x-(float)allEntities.npc[npc_.type].width*0.25f, npc_.y-(float)allEntities.npc[npc_.type].height*0.25f, allExplosions.explosion[animnum].colorR, allExplosions.explosion[animnum].colorG, allExplosions.explosion[animnum].colorB, allExplosions.explosion[animnum].colorA,
						// explosion effekt scale-ezése az pickup méretére
						w/allExplosions.explosion[animnum].width, h/allExplosions.explosion[animnum].height, 
						(float)npc_.rotate, (float)allEntities.npc[npc_.type].width/2*1.5f, (float)allEntities.npc[npc_.type].height/2*1.5f
						);

				
				// draw npc
				// Rotated
				if (npc_.rotate > 0) {
					allEntities.gfx[allEntities.npc[npc_.type].gfx].image[0].drawROT(gl, npc_.x, npc_.y, allEntities.npc[npc_.type].colorRGBA[0], allEntities.npc[npc_.type].colorRGBA[1], allEntities.npc[npc_.type].colorRGBA[2], allEntities.npc[npc_.type].colorRGBA[3], (float)allEntities.npc[npc_.type].width/allEntities.gfx[allEntities.npc[npc_.type].gfx].width, (float)allEntities.npc[npc_.type].height/allEntities.gfx[allEntities.npc[npc_.type].gfx].height, (float)npc_.rotate, (float)allEntities.npc[npc_.type].width/2, (float)allEntities.npc[npc_.type].height/2);

					// draw blended entity over original one when hit
					if (npc_.hit_timer > 0) {
						float alfa = 1-(float)(npc_.hit_timer - 100) / 150; if (alfa > 1) alfa = 1;
						gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
						allEntities.gfx[allEntities.npc[npc_.type].gfx].image[0].drawROT(gl, npc_.x, npc_.y, 1,1,1,alfa, (float)allEntities.npc[npc_.type].width/allEntities.gfx[allEntities.npc[npc_.type].gfx].width, (float)allEntities.npc[npc_.type].height/allEntities.gfx[allEntities.npc[npc_.type].gfx].height, (float)npc_.rotate, (float)allEntities.npc[npc_.type].width/2, (float)allEntities.npc[npc_.type].height/2);
						gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
						npc_.hit_timer += time_dif;
						if (npc_.hit_timer >= 250) npc_.hit_timer = 0;
					}

					
				// Non-rotated entity - save some transformation
				} else {
					allEntities.gfx[allEntities.npc[npc_.type].gfx].image[0].draw(gl, npc_.x, npc_.y, allEntities.npc[npc_.type].colorRGBA[0], allEntities.npc[npc_.type].colorRGBA[1], allEntities.npc[npc_.type].colorRGBA[2], allEntities.npc[npc_.type].colorRGBA[3], (float)allEntities.npc[npc_.type].width/allEntities.gfx[allEntities.npc[npc_.type].gfx].width, (float)allEntities.npc[npc_.type].height/allEntities.gfx[allEntities.npc[npc_.type].gfx].height/*, npc_.disort*/);
					
					// draw blended entity over original one when hit
					if (npc_.hit_timer > 0) {
						float alfa = 1-(float)(npc_.hit_timer - 100) / 150; if (alfa > 1) alfa = 1;
						gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
						allEntities.gfx[allEntities.npc[npc_.type].gfx].image[0].draw(gl, npc_.x, npc_.y, 1, 1, 1, alfa, (float)allEntities.npc[npc_.type].width/allEntities.gfx[allEntities.npc[npc_.type].gfx].width, (float)allEntities.npc[npc_.type].height/allEntities.gfx[allEntities.npc[npc_.type].gfx].height);
						gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
						npc_.hit_timer += time_dif;
						if (npc_.hit_timer >= 250) npc_.hit_timer = 0;
					}
				}

				if (allEntities.npc[npc_.type].hp_max > 0 && npc_.hit_timer_hp > 0) {
					float hpbarwidth;
					float hpbarbgwidth;
					float hpmax_ = allEntities.npc[npc_.type].hp_max;
					float shieldmax_ = allEntities.npc[npc_.type].shield_max;

					if (difficulty == 0) {
						hpmax_ = (int)((float)hpmax_*0.6667f);
						if (hpmax_ < 1) hpmax_ = 1;
						shieldmax_ = (int)((float)shieldmax_*0.6667f);
					}
					if (difficulty == 2) {
						if (hpmax_ < 4) hpmax_++; else hpmax_ = (int)((float)hpmax_*dif_hard);
						shieldmax_ = (int)((float)shieldmax_*dif_hard);
					}
					
					if (npc_.hp > hpmax_) hpmax_ = npc_.hp; 
					hpbarbgwidth = (1.0f / (int)(68*hd)) * allEntities.npc[npc_.type].width;
					hpbarwidth = (1.0f / (int)(22*hd)) * (((float)npc_.hp / hpmax_ * ((float)allEntities.npc[npc_.type].width - (int)(hd*6))));
					if (hpbarwidth > 0) {
						npc_hpbar.draw(gl, npc_.x + (int)(3*hd), npc_.y - (int)(7*hd), 1.0f, 1.0f, 1.0f, 1.0f, hpbarwidth, 1.0f);
						hpbar_bg.draw(gl, npc_.x, npc_.y - (int)(8*hd), 1.0f, 1.0f, 1.0f, 1.0f, hpbarbgwidth, 1.0f);
					}
					
					float shield_alfa = (1 - (float)npc_.hit_timer_hp / 1000) * ((float)npc_.shield / allEntities.npc[npc_.type].shield_max);
					float shield_width = (float)allEntities.npc[npc_.type].width*1.5f;
					float shield_height = (float)allEntities.npc[npc_.type].height*1.5f;
					if (shield_alfa > 0) shield.drawROT(gl, npc_.x + allEntities.npc[npc_.type].width/2 - shield_width/2, npc_.y + allEntities.npc[npc_.type].height/2 - shield_height/2, shield_alfa, shield_alfa, shield_alfa, shield_alfa, shield_width/shield_size[0], shield_height/shield_size[1],(float)npc_.rotate, shield_width/2, shield_height/2);

					npc_.hit_timer_hp += time_dif;
					if (npc_.hit_timer_hp >= 1000) npc_.hit_timer_hp = 0;
				} 

				//	if (!allEntities.npc[npc_.type].shieldonly) {
				//		if (hpbarwidth > 0) npc_hpbar.draw(gl, npc_.x, npc_.y - (int)(hd*8), 1.0f, 1.0f, 1.0f, allEntities.npc[npc_.type].colorRGBA[3], hpbarwidth, 1.0f);
				//	} else {
				//		if (hpbarwidth > 0) npc_shieldbar.draw(gl, npc_.x, npc_.y - (int)(hd*8), 1.0f, 1.0f, 1.0f, allEntities.npc[npc_.type].colorRGBA[3], hpbarwidth, 1.0f);
				//	}
				//	
				//	if (allEntities.npc[npc_.type].shield_max > 0) {
				//		float shbarwidth;
				//		shbarwidth = (1.0f / hd*32) * (((float)npc_.shield / shieldmax_ * allEntities.npc[npc_.type].width));
				//		if (shbarwidth > 0)	npc_shieldbar.draw(gl, npc_.x, npc_.y - (int)(hd*12), 1.0f, 1.0f, 1.0f, allEntities.npc[npc_.type].colorRGBA[3], shbarwidth, 1.0f);
				//	}
				
				if (freeze && !gravity && endmission_state==0) {
					
					if (allEntities.npc[npc_.type].frozable && !allEntities.npc[npc_.type].mine) {
					
						allExplosions.explosion[9].image[(int)(emp_loop / 10)]
							.draw(gl, npc_.x, npc_.y, allExplosions.explosion[9].colorR, allExplosions.explosion[9].colorG, allExplosions.explosion[9].colorB, allExplosions.explosion[9].colorA,
								// explosion effekt scale-ezése az npc méretére
								(float)allEntities.npc[npc_.type].width/allExplosions.explosion[9].width,
								(float)allEntities.npc[npc_.type].height/allExplosions.explosion[9].height
						);
					}
					
					emp_loop+= time_dif / 60;
					if (emp_loop >= 40.0f) {
						emp_loop = 0.0f;
					}
				}
					
			}
			else if (npc_.explosion_loop > 0) {
				// or draw explosion
				int expnum = allEntities.npc[npc_.type].die_effect-1;
				// Rotated explosion
				if (allExplosions.explosion[expnum].rotation > 0) {
					allExplosions.explosion[expnum].image[(int)(npc_.explosion_loop/explosion_loop_scale)]
						.drawROT(gl, npc_.x, npc_.y, allExplosions.explosion[expnum].colorR, allExplosions.explosion[expnum].colorG, allExplosions.explosion[expnum].colorB, allExplosions.explosion[expnum].colorA,
						// explosion effekt scale-ezése az npc méretére
						(float)allEntities.npc[npc_.type].width/allExplosions.explosion[expnum].width,
						(float)allEntities.npc[npc_.type].height/allExplosions.explosion[expnum].height,
						allExplosions.explosion[expnum].rotation, (float)allEntities.npc[npc_.type].width/2, (float)allEntities.npc[npc_.type].height/2
						);
				// Non-rotated explosion - save some transformation
				} else {
					allExplosions.explosion[expnum].image[(int)(npc_.explosion_loop/explosion_loop_scale)]
							.draw(gl, npc_.x, npc_.y, allExplosions.explosion[expnum].colorR, allExplosions.explosion[expnum].colorG, allExplosions.explosion[expnum].colorB, allExplosions.explosion[expnum].colorA,
							// explosion effekt scale-ezése az npc méretére
							(float)allEntities.npc[npc_.type].width/allExplosions.explosion[expnum].width,
							(float)allEntities.npc[npc_.type].height/allExplosions.explosion[expnum].height
							);
				}
			}
		}

		// draw player's ship
		//if (spaceship.image_update == 0) {
		float spaceship_alfa = 1f;
		if (invincible) spaceship_alfa = 0.5f;
		
		if (spaceship.isalive) {

			// draw spaceship shadow
			if (allMissions.missionControl.shadow) {
				float shadow_x = spaceship.x + spaceship.x/(int)(hd*400)*(int)(hd*8)+(int)(hd*16);
				float shadow_y = spaceship.y + spaceship.y/(int)(hd*720)*(int)(hd*20)+(int)(hd*20);
				float shadow_sizex = (float)allUpgrades.ship[spaceship.type].width/(int)(hd*128);
				float shadow_sizey = (float)allUpgrades.ship[spaceship.type].height/(int)(hd*128);
				npc_shadow.draw(gl, shadow_x, shadow_y, 1,1,1,0.3f, shadow_sizex, shadow_sizey);
			}
			
			if (spaceship.winged) {
				allEntities.gfx[172].image[0].draw(gl, spaceship.x+allUpgrades.ship[spaceship.type].wing_oo[0][0], spaceship.y+allUpgrades.ship[spaceship.type].wing_oo[0][1], 0.8f, 0.8f, 0.8f, 0.8f, 1, 1);
				allEntities.gfx[172].image[0].draw(gl, spaceship.x+allUpgrades.ship[spaceship.type].wing_oo[1][0], spaceship.y+allUpgrades.ship[spaceship.type].wing_oo[1][1], 0.8f, 0.8f, 0.8f, 0.8f, 1, 1);
			}
			
			if (wings) {
				int wingmod = 0; if (spaceship.winged) wingmod = (int)(hd*45);
				float alfa = 0f;
				if (time_current - wings_starttime <= 500) { 
					alfa = (float)(time_current - wings_starttime) / 500; if (alfa > 1f) alfa = 1f;
				} else if (wings_timer - time_current <= 750) {
					alfa = (float)(wings_timer - time_current) / 750; if (alfa < 0f) alfa = 0f;
				} else alfa = 1f;
				
				allEntities.gfx[171].image[0].draw(gl, spaceship.x+allUpgrades.ship[spaceship.type].wing_oo[0][0]-wingmod, spaceship.y+allUpgrades.ship[spaceship.type].wing_oo[0][1], alfa, alfa, alfa, alfa, 1, 1);
				allEntities.gfx[171].image[0].draw(gl, spaceship.x+allUpgrades.ship[spaceship.type].wing_oo[1][0]+wingmod, spaceship.y+allUpgrades.ship[spaceship.type].wing_oo[1][1], alfa, alfa, alfa, alfa, 1, 1);
			}

			for (int i=0; i<allUpgrades.ship[spaceship.type].particle_num; i++) {
				particle[i].draw(gl, spaceship.x+allUpgrades.ship[spaceship.type].particle_pos[i][0] - (float)particle_size[0]/2, spaceship.y+allUpgrades.ship[spaceship.type].particle_pos[i][1] - (float)particle_size[1]/2);
			}
			allUpgrades.ship[spaceship.type].image[0].draw(gl, spaceship.x, spaceship.y,spaceship_alfa,spaceship_alfa,spaceship_alfa,spaceship_alfa,(float)allUpgrades.ship[spaceship.type].width / allUpgrades.ship[spaceship.type].image_size[0], (float)allUpgrades.ship[spaceship.type].height / allUpgrades.ship[spaceship.type].image_size[1]);

			
			if (spaceship.shield > 0 && spaceship.hit_timer > 0) {
				//float alfaval = (1 - (float)spaceship.hit_timer / 1000) * ((float)spaceship.shield / spaceship.shield_max);
				//float shield_width = (float)allUpgrades.ship[spaceship.type].width*1.5f;
				//float shield_height = (float)allUpgrades.ship[spaceship.type].height*1.5f;
				//if (shield_alfa > 0) shield.drawROT(gl, npc_.x + allEntities.npc[npc_.type].width/2 - shield_width/2, npc_.y + allEntities.npc[npc_.type].height/2 - shield_height/2, shield_alfa, shield_alfa, shield_alfa, shield_alfa, shield_width/140, shield_height/140,(float)npc_.rotate, shield_width/2, shield_height/2);
				//if (alfaval > 1.0f) alfaval = 1.0f;
				//if (alfaval < 0.3f) alfaval = 0.3f;
				//if (invincible) alfaval *= spaceship_alfa;
				//allUpgrades.ship[spaceship.type].image[1].draw(gl, spaceship.x, spaceship.y, alfaval,alfaval,alfaval,alfaval,(float)allUpgrades.ship[spaceship.type].width / allUpgrades.ship[spaceship.type].image_size[0], (float)allUpgrades.ship[spaceship.type].height / allUpgrades.ship[spaceship.type].image_size[1]);
				//TODO xxx draw shield xxx
				float shield_alfa = (1 - (float)spaceship.hit_timer / 1000) * ((float)spaceship.shield / spaceship.shield_max);
				float shield_width = (float)allUpgrades.ship[spaceship.type].width*1.5f;
				float shield_height = (float)allUpgrades.ship[spaceship.type].height*1.5f;
				if (shield_alfa > 0) shield.drawROT(gl, spaceship.x + allUpgrades.ship[spaceship.type].width/2 - shield_width/2, spaceship.y + allUpgrades.ship[spaceship.type].height/2 - shield_height/2, shield_alfa, shield_alfa, shield_alfa, shield_alfa, shield_width/shield_size[0], shield_height/shield_size[1], 180, shield_width/2, shield_height/2);

				spaceship.hit_timer += time_dif;
				if (spaceship.hit_timer >= 1000) spaceship.hit_timer = 0;
			} 

			/*
			if (spaceship.shield > 0 && spaceship.shield_max > 0) {
				float alfaval = (float)spaceship.shield*1.2f / spaceship.shield_max;
				if (alfaval > 1.0f) alfaval = 1.0f;
				if (alfaval < 0.3f) alfaval = 0.3f;
				if (invincible) alfaval *= spaceship_alfa;
				allUpgrades.ship[spaceship.type].image[1].draw(gl, spaceship.x, spaceship.y, alfaval,alfaval,alfaval,alfaval,(float)allUpgrades.ship[spaceship.type].width / allUpgrades.ship[spaceship.type].image_size[0], (float)allUpgrades.ship[spaceship.type].height / allUpgrades.ship[spaceship.type].image_size[1]);
			}
			*/
			
			if (spaceship.freeze) {
				allExplosions.explosion[9].image[(int)(ship_emp_loop / 10)]
					.draw(gl, spaceship.x, spaceship.y, allExplosions.explosion[9].colorR, allExplosions.explosion[9].colorG, allExplosions.explosion[9].colorB, allExplosions.explosion[9].colorA,
						// explosion effekt scale-ezése az npc méretére
						(float)allUpgrades.ship[spaceship.type].width/allExplosions.explosion[9].width,
						(float)allUpgrades.ship[spaceship.type].height/allExplosions.explosion[9].height
				);
			}
			ship_emp_loop+= time_dif / 60;
			if (ship_emp_loop >= 40.0f) {
				ship_emp_loop = 0.0f;
			}
			
		} else {
			if (spaceship.explosion_loop > 0) {
				// or draw explosion
				if (spaceship.explosion_loop/explosion_loop_scale < allExplosions.explosion[allUpgrades.ship[spaceship.type].die_effect].picture_num) {
					allExplosions.explosion[allUpgrades.ship[spaceship.type].die_effect].image[(int)(spaceship.explosion_loop/explosion_loop_scale)]
						.draw(gl, spaceship.x, spaceship.y, 1.0f, 1.0f, 1.0f, 1.0f,
						// explosion effekt scale-ezése az npc méretére
						(float)allUpgrades.ship[spaceship.type].width/allExplosions.explosion[allUpgrades.ship[spaceship.type].die_effect].width,
						(float)allUpgrades.ship[spaceship.type].height/allExplosions.explosion[allUpgrades.ship[spaceship.type].die_effect].height
						);
				}
			}
		}
		//
		
		for(Bullet bullet_: npc_bullet){
			if (bullet_.active) {
				// draw bullets
				allBullets.bullet[bullet_.type].image.drawROT(gl, bullet_.x, bullet_.y, 1, 1, 1, 1, 1, 1, bullet_.rotation, (float)allBullets.bullet[bullet_.type].width/2 ,(float)allBullets.bullet[bullet_.type].height/2);
			}
		}

		for(Bullet bullet_: spaceship_bullet){
			if (bullet_.active) {
				// draw bullets
				if (bullet_.isparticle) {
					bullet_.particle.draw(gl, bullet_.x+(float)allBullets.bullet[bullet_.type].width/2-(float)allBullets.bullet[bullet_.type].particle_width/2, bullet_.y+(float)allBullets.bullet[bullet_.type].height-(float)allBullets.bullet[bullet_.type].particle_height/2);
				}
				allBullets.bullet[bullet_.type].image.drawROT(gl, bullet_.x, bullet_.y, 1, 1, 1, 1, 1, 1, bullet_.rotation, (float)allBullets.bullet[bullet_.type].width/2 ,(float)allBullets.bullet[bullet_.type].height/2);
			}
		}
		if (homing_bullet.active) {
			allBullets.bullet[homing_bullet.type].image.drawROT(gl, homing_bullet.x, homing_bullet.y, 1, 1, 1, 1, 1, 1, homing_bullet.rotation, (float)allBullets.bullet[homing_bullet.type].width/2 ,(float)allBullets.bullet[homing_bullet.type].height/2);
		}
		if (crazy_bullet.active) {
			allBullets.bullet[crazy_bullet.type].image.drawROT(gl, crazy_bullet.x, crazy_bullet.y, 1, 1, 1, 1, 1, 1, crazy_bullet.rotation, (float)allBullets.bullet[crazy_bullet.type].width/2 ,(float)allBullets.bullet[crazy_bullet.type].height/2);
		}
		if (cone) {
			float alfaval = (float)(cone_timer - time_current)/1500;
			//menusystem.white.draw(gl, spaceship.x + allUpgrades.ship[spaceship.type].width / 2 - 100, 0, alfaval, alfaval, alfaval, alfaval, 2.0f, 7.2f);
			if (alfaval > 0) {
				float cone_x = cone_xy[0] + (float)allBullets.bullet[13].width/2*(1-alfaval); 
				allBullets.bullet[13].image.draw(gl, cone_x, cone_xy[1], 1f, 1f, 1f, 0.7f, alfaval, 1f);
			}
		}
		if (nuke_timer > 0) {
			float alfaval = (float)(1500-nuke_timer)/1500;
			menusystem.white.draw(gl, 0, 0, alfaval, alfaval, alfaval, alfaval, hd*4.8f, hd*8.0f);
			nuke_timer+=time_dif;
			if (nuke_timer > 1500) nuke_timer = 0;
		}
		if (hp_timer > 0) {
			int max = 9; if (spaceship.hp_max/2-1<9) max = spaceship.hp_max/2-1; 
			float start = (1-(float)(spaceship.hp+spaceship.shield) / max) * 600;
			float alfaval = (float)(900+start-hp_timer)/3000;
			if (alfaval > 0) {
				menusystem.white.draw(gl, 0, 0, alfaval, 0, 0, alfaval, hd*4.8f, hd*8.0f);
			}
			hp_timer+=time_dif;
			if (hp_timer > 900+start) hp_timer = 0;
		}
		
		//topmenu.draw(gl, 0, 0, 1.0f, 1.0f, 1.0f, 0.3f);

		// draw clouds
		if (clouds) {
        		background_clouds.draw(gl, 0, cloudloopY,0.334f,0.334f,0.334f,0.334f); 
        		background_clouds.draw(gl, 0, cloudloopY-(int)(hd*800),0.334f,0.334f,0.334f,0.334f);
		}
		
		if (warning) {
			warning_timer += time_dif;
			float alfa = 1.00f;
			if (warning_timer <= 1000) alfa = warning_timer / 1000;
			else if (warning_timer <= 2000) alfa = (1000-(warning_timer-1000)) / 1000;
			else if (warning_timer <= 3000) alfa = 0;
			if (alfa < 0f) alfa = 0.00f; else if (alfa > 1f) alfa = 1.00f;
			endmission_warning.draw(gl, (int)(hd*96), (int)(hd*265), alfa, alfa, alfa, alfa);
			if (warning_timer >= 2000) {
				warning = false;
				warning_timer = 0;
			}
		}
		
		int ly_x = (int)(hd*125);
		int ly_y1 = (int)(hd*324);
		int ly_y2 = (int)(hd*347);
		
		if (endmission_state > 0) {
			
			if (!pause) endmission_timer += time_dif; 
			
			if (endmission_success) {
				if (endmission_state == 1) {
					float alfa = endmission_timer / 1500; if (alfa > 1) alfa = 1;
					endmission_completed.draw(gl, (int)(hd*66), (int)(hd*235), alfa, alfa, alfa, alfa);
					if (endmission_timer > 1500) {
						endmission_timer = 0;
						endmission_state = 2;
					}
				} else if (endmission_state == 2) {
					if (endmission_timer > 900) {
						menusystem.popupbg.draw(gl, menusystem.popupbg_pos[0], menusystem.popupbg_pos[1]);
						//menusystem.black.draw(gl, 0, 240, 0.3f, 0.3f, 0.3f, 0.3f, 4.8f, 2.2f);
					}
					endmission_completed.draw(gl, (int)(hd*66), (int)(hd*235));
					int textx = (int)(hd*5 + (ly_x-hd*5)*endmission_timer/1000); if (textx > ly_x) textx = ly_x; 
					int texty = (int)(hd*3 + (ly_y1-hd*3)*endmission_timer/1000); if (texty > ly_y1) texty = ly_y1;
					menufnt.PrintAt(gl, "LY: " + toLY(missionLY, false), textx, texty);
					if (endmission_timer > 1000) {
						menufnt.PrintAt(gl, "Total LY: " + toLY(vLY, false), ly_x, ly_y2);
					}
					if (endmission_timer > 2500) {
						endmission_timer = 0;
						if(sound)SoundManager.playSound(38,1);
						endmission_state = 3;
						menu = 102;
					}
				} else if (endmission_state == 3) {
					menusystem.popupbg.draw(gl, menusystem.popupbg_pos[0], menusystem.popupbg_pos[1]);
					//menusystem.black.draw(gl, 0, 240, 0.3f, 0.3f, 0.3f, 0.3f, 4.8f, 2.2f);
					endmission_completed.draw(gl, (int)(hd*66), (int)(hd*235));
					long mLY = (long)(missionLY - (float)missionLY*endmission_timer/2500); if (mLY < 0) mLY = 0;
					long tLY = (long)(vLY + (float)missionLY*endmission_timer/2500); if (tLY > vLY + missionLY) tLY = vLY + missionLY;
					menufnt.PrintAt(gl, "LY: " + toLY(mLY, false), ly_x, ly_y1);
					menufnt.PrintAt(gl, "Total LY: " + toLY(tLY, false), ly_x, ly_y2);
					if (endmission_timer > 5000) {
						endmission_timer = 0;
						endmission_state = 4;
					}
				} else if (endmission_state == 4) {
					menusystem.popupbg.draw(gl, menusystem.popupbg_pos[0], menusystem.popupbg_pos[1]);
					//menusystem.black.draw(gl, 0, 240, 0.3f, 0.3f, 0.3f, 0.3f, 4.8f, 2.2f);
					endmission_completed.draw(gl, (int)(hd*66), (int)(hd*235));
					menufnt.PrintAt(gl, "LY: " + toLY(0, false), ly_x, ly_y1);
					menufnt.PrintAt(gl, "Total LY: " + toLY(vLY+missionLY, false), ly_x, ly_y2);
					
					//End_Mission(true);
				}
			} else {
				if (endmission_state == 1) {
					float alfa = endmission_timer / 1500; if (alfa > 1) alfa = 1;
					endmission_failed.draw(gl, (int)(hd*71), (int)(hd*255), alfa, alfa, alfa, alfa);
					if (endmission_timer > 1500) {
						endmission_timer = 0;
						endmission_state = 2;
					}
				} else if (endmission_state == 2) {
					if (endmission_timer > 900) {
						menusystem.popupbg.draw(gl, menusystem.popupbg_pos[0], menusystem.popupbg_pos[1]);
						//menusystem.black.draw(gl, 0, 240, 0.3f, 0.3f, 0.3f, 0.3f, 4.8f, 2.2f);
					}
					endmission_failed.draw(gl, (int)(hd*71), (int)(hd*255));
					int textx = (int)(hd*5 + (ly_x-hd*5)*endmission_timer/1000); if (textx > ly_x) textx = ly_x; 
					int texty = (int)(hd*3 + (ly_y1-hd*3)*endmission_timer/1000); if (texty > ly_y1) texty = ly_y1;
					menufnt.PrintAt(gl, "LY: " + toLY(missionLY, false), textx, texty);
					if (endmission_timer > 1000) {
						menufnt.PrintAt(gl, "Total LY: " + toLY(vLY, false), ly_x, ly_y2);
					}
					if (endmission_timer > 2500) {
						endmission_timer = 0;
						if(sound)SoundManager.playSound(38,1);
						endmission_state = 3;
						menu = 102;
					}
				} else if (endmission_state == 3) {
					menusystem.popupbg.draw(gl, menusystem.popupbg_pos[0], menusystem.popupbg_pos[1]);
					//menusystem.black.draw(gl, 0, 240, 0.3f, 0.3f, 0.3f, 0.3f, 4.8f, 2.2f);
					endmission_failed.draw(gl, (int)(hd*71), (int)(hd*255));
					long mLY = (long)(missionLY - (float)missionLY*endmission_timer/2500); if (mLY < 0) mLY = 0;
					menufnt.SetPolyColor(1,0,0,1);
					menufnt.PrintAt(gl, "LY: " + toLY(mLY, false), ly_x, ly_y1);
					menufnt.SetPolyColor(1,1,1,1);
					menufnt.PrintAt(gl, "Total LY: " + toLY(vLY, false), ly_x, ly_y2);
					if (endmission_timer > 5000) {
						endmission_timer = 0;
						endmission_state = 4;
					}
				} else if (endmission_state == 4) {
					menusystem.popupbg.draw(gl, menusystem.popupbg_pos[0], menusystem.popupbg_pos[1]);
					//menusystem.black.draw(gl, 0, 71, 0.3f, 0.3f, 0.3f, 0.3f, 4.8f, 2.2f);
					endmission_failed.draw(gl, (int)(hd*71), (int)(hd*255));
					menufnt.SetPolyColor(1,0,0,1);
					menufnt.PrintAt(gl, "LY: " + toLY(0, false), ly_x, ly_y1);
					menufnt.SetPolyColor(1,1,1,1);
					menufnt.PrintAt(gl, "Total LY: " + toLY(vLY, false), ly_x, ly_y2);
					//End_Mission(false);

				}
			}
			
			if (endmission_state >= 3) {
				//menusystem.black.draw(gl, 0, 460, 0.3f, 0.3f, 0.3f, 0.3f, 4.8f, 2.0f);
				if (!liked) {
					menufnt.PrintAt(gl, "Like to receive free LY and more!", (int)(hd*100), (int)(hd*480));
					allUpgrades.purchasable[0].icon.draw(gl, (int)(hd*135), (int)(hd*515), 1, 1, 1, 1, hd*0.67f/hd, hd*0.67f/hd);
					menusystem.layer[3].draw(gl, (int)(hd*135), (int)(hd*515), 1, 1, 1, 1, hd*0.67f/hd, hd*0.67f/hd);
					allUpgrades.special[31].icon.draw(gl, (int)(hd*205), (int)(hd*515), 1, 1, 1, 1, hd*0.67f/hd, hd*0.67f/hd);
					menusystem.layer[3].draw(gl, (int)(hd*205), (int)(hd*515), 1, 1, 1, 1, hd*0.67f/hd, hd*0.67f/hd);
					allUpgrades.special[32].icon.draw(gl, (int)(hd*275), (int)(hd*515), 1, 1, 1, 1, hd*0.67f/hd, hd*0.67f/hd);
					menusystem.layer[3].draw(gl, (int)(hd*275), (int)(hd*515), 1, 1, 1, 1, hd*0.67f/hd, hd*0.67f/hd);
				}
				menusystem.btcontinue.draw(gl, menusystem.btcontinue_pos[0], menusystem.btcontinue_pos[1]);
				if (!menusystem.fbshare_pressed) menusystem.fbshare.draw(gl, menusystem.fbshare_pos[0], menusystem.fbshare_pos[1]);
				else menusystem.fbshare.draw(gl, menusystem.fbshare_pos[0], menusystem.fbshare_pos[1], 0f, 1f, 0.5f, 1f);
				if (!liked) {
					if (!menusystem.fblike_pressed) menusystem.fblike.draw(gl, menusystem.fblike_pos[0], menusystem.fblike_pos[1]);
					else menusystem.fblike.draw(gl, menusystem.fblike_pos[0], menusystem.fblike_pos[1], 0f, 1f, 0.5f, 1f);
				} else {
					if (!menusystem.fblike_pressed) menusystem.btrate.draw(gl, menusystem.fblike_pos[0], menusystem.fblike_pos[1]);
					else menusystem.btrate.draw(gl, menusystem.fblike_pos[0], menusystem.fblike_pos[1], 0f, 1f, 0.5f, 1f);
				}
			}
		}

	}	
	
	
	
	
	
	

	/**
	 * If the surface changes, reset the view
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {

		//Request focus, otherwise buttons won't react
		//this.requestFocus();
		//this.setFocusableInTouchMode(true);
		
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}

		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();					//Reset The Current Modelview Matrix
		gl.glOrthof(0f, dimensionX, dimensionY, 0f, -1f, 1f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();					//Reset The Current Modelview Matrix 
  
	}

	// spaceship folyamatos, aktív lövése action down esetén
	/*
	public void launch_delayer() {

		spaceship_bullet_launch = true;
		this.postDelayed(new Runnable() { 
			public void run() {
				if (launch_delayer_isrunning) { 
					launch_delayer();
				} else launch_delayer_active = true;
			}
		}, (int) (game_timer_fix * (1 / (allUpgrades.turret[spaceship.turret[spaceship.turret_active]].rateoffire + spaceship.rofmod) * 1000))); // auto firing rate
		  
	}
*/

	
/* ***** Erintokepernyo figyelese ***** */	
	/**
	 * Override the touch screen listener.
	 * 
	 * React to moves and presses on the touchscreen.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	try {
		
		final int action = event.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
	    
		// touch event action down
		case MotionEvent.ACTION_DOWN: {
	    	final float x = event.getX();
	    	final float y = event.getY();
	    	scaleX = x * dimensionX / this.getWidth();
	    	scaleY = y * dimensionY / this.getHeight();
	  
	    	// Save the ID of this pointer
	        mActivePointerId = event.getPointerId(0);
	        
	    	if(!loading) {

    	  switch (menu) {
          	
    	  	case 0: 
    	  		
    	  		break;
    	  	
    	  	case 91:
    	  	case 101:
    	  	case 108:
    	  		if(scaleX > menusystem.quit_bt_yes[0] && scaleY > menusystem.quit_bt_yes[1] && scaleX < menusystem.quit_bt_yes[0]+menusystem.quit_bt_yes[2] && scaleY < menusystem.quit_bt_yes[1]+menusystem.quit_bt_yes[3]) {
    	  			menusystem.quit_yes_pressed = true;
    	  			if(sound)SoundManager.playSound(1,1);
    	  		} else if(scaleX > menusystem.quit_bt_no[0] && scaleY > menusystem.quit_bt_no[1] && scaleX < menusystem.quit_bt_no[0]+menusystem.quit_bt_no[2] && scaleY < menusystem.quit_bt_no[1]+menusystem.quit_bt_no[3]) {
    	  			menusystem.quit_no_pressed = true;
    	  			if(sound)SoundManager.playSound(1,1);
    	  		}

    	  		break;

    	  	case 105 :
    	  		if(scaleX > menusystem.getmore_pos[0] && scaleY > menusystem.getmore_pos[1] && scaleX < menusystem.getmore_pos[0]+menusystem.getmore_pos[2] && scaleY < menusystem.getmore_pos[1]+menusystem.getmore_pos[3]) {
    	  			menusystem.getmore_pressed = true;
    	  			if(sound)SoundManager.playSound(1,1);
    	  		} else {
    	  			menusystem.daily_touched = true;
    	  			if(sound)SoundManager.playSound(1,1);
    	  		}
    	  		break;
    	  		
    	  	case 1: 
    	  		
    	  		for (int i=0; i<menusystem.mainmenu.button_num; i++) {
    	  			if(scaleX > menusystem.mainmenu.button_pos[i][0] && scaleY > menusystem.mainmenu.button_pos[i][1] && scaleX < menusystem.mainmenu.button_pos[i][0]+menusystem.mainmenu.button_pos[i][2] && scaleY < menusystem.mainmenu.button_pos[i][1]+menusystem.mainmenu.button_pos[i][3]) {
    	  				menusystem.mainmenu.button_pressed[i] = true;
    	  				if(sound)try{SoundManager.playSound(1,1);}catch(Exception e){e.printStackTrace();}
    	  				break;
    	  			}
    	  		}
    	  		break;
    	  	
    	  	case 2: 

    	  		if (menusystem.selected_galaxy >= 0) {
    	  			if (scaleX > menusystem.galaxy_bt[0] && scaleX < menusystem.galaxy_bt[0]+menusystem.galaxy_bt[2] && 
        	  				scaleY > menusystem.galaxy_bt[1] && scaleY < menusystem.galaxy_bt[1]+menusystem.galaxy_bt[3])
        	  		{
        	  			menusystem.galaxy_bt_pressed = true;
        	  			if(sound)try{SoundManager.playSound(1,1);}catch(Exception e){e.printStackTrace();}
        	  			break;
        	  		}
    	  		}

    	  		// ellenorzes, hogy egy galaxis ikonjara kattintottunk e (80px)
    	  		for (int i=0; i<menusystem.total_galaxy; i++) {
    	  			if(menusystem.galaxy[i].status > 0) {
    	  				if(scaleX > menusystem.galaxy[i].bt_xy[0] && scaleY > menusystem.galaxy[i].bt_xy[1] && scaleX < menusystem.galaxy[i].bt_xy[0]+(int)(hd*80) && scaleY < menusystem.galaxy[i].bt_xy[1]+(int)(hd*80)) {
    	  					if(sound)try{SoundManager.playSound(1,1);}catch(Exception e){e.printStackTrace();}
    	  					menusystem.selected_galaxy = i;
    	  					
    	  					int layerX = menusystem.galaxy[i].bt_xy[0] + (int)(hd*80); 
    						int layerY = menusystem.galaxy[i].bt_xy[1] - (int)(hd*70);
    						if (layerX > (int)(hd*120)) layerX = (int)(hd*120);
    						if (layerY < (int)(hd*80)) layerY = menusystem.galaxy[i].bt_xy[1] + (int)(hd*90);
    	  					
    						menusystem.galaxy_bt[0] = layerX + (int)(hd*196);
    						menusystem.galaxy_bt[1] = layerY;
    						//if (menusystem.galaxy_bt[0] > (int)(hd*380)) menusystem.galaxy_bt[0] = menusystem.galaxy[menusystem.selected_galaxy].bt_xy[0] - (int)(hd*166);
    	  					menusystem.parrow_total = 1000;

    	  					break;
    	  				}
    	  			}
    	  			// ha egyik galaxisra sem kattintottunk
    	  			if(i==menusystem.total_galaxy-1) {
    	  				if (menusystem.selected_galaxy >= 0) {
    	  					if(sound)SoundManager.playSound(22,1);
    	  					menusystem.selected_galaxy = -1;
    	  				}
    	  				break;
    	  			}
    	  		}
    	  		
    	  		if(ggs && !signedin && scaleX > menusystem.btsignin_pos[0] && scaleY > menusystem.btsignin_pos[1] && scaleX < menusystem.btsignin_pos[0]+menusystem.btsignin_pos[2] && scaleY < menusystem.btsignin_pos[1]+menusystem.btsignin_pos[3]) {
    	  			menusystem.btsignin_pressed = true;
    	  			if(sound)try{SoundManager.playSound(1,1);}catch(Exception e){e.printStackTrace();}
    	  		}     
    	  		if(ggs && scaleX > menusystem.btachievements_pos[0] && scaleY > menusystem.btachievements_pos[1] && scaleX < menusystem.btachievements_pos[0]+menusystem.btachievements_pos[2] && scaleY < menusystem.btachievements_pos[1]+menusystem.btachievements_pos[3]) {
    	  			menusystem.btachievements_pressed = true;
    	  			if(sound)try{SoundManager.playSound(1,1);}catch(Exception e){e.printStackTrace();}
    	  		}     
    	  		if(ggs && scaleX > menusystem.btleaderboard_pos[0] && scaleY > menusystem.btleaderboard_pos[1] && scaleX < menusystem.btleaderboard_pos[0]+menusystem.btleaderboard_pos[2] && scaleY < menusystem.btleaderboard_pos[1]+menusystem.btleaderboard_pos[3]) {
    	  			menusystem.btleaderboard_pressed = true;
    	  			if(sound)try{SoundManager.playSound(1,1);}catch(Exception e){e.printStackTrace();}
    	  		}     
    	  		
    	  		break;
    	  	
    	  	case 3: 
    	  		
    	  		break;
    	  	
    	  	case 4: {
    	  		if (scaleX > menusystem.options_sound_bt[0] && scaleX < menusystem.options_sound_bt[0]+menusystem.options_sound_bt[2] && 
    	  				scaleY > menusystem.options_sound_bt[1] && scaleY < menusystem.options_sound_bt[1]+menusystem.options_sound_bt[3])
    	  		{
    	  			menusystem.options_sound_pressed = true;
    	  			if(sound)try{SoundManager.playSound(1,1);}catch(Exception e){e.printStackTrace();}
    	  			break;
    	  		} else 
    	  		if (sound && scaleX > menusystem.options_music_bt[0] && scaleX < menusystem.options_music_bt[0]+menusystem.options_music_bt[2] && 
    	  				scaleY > menusystem.options_music_bt[1] && scaleY < menusystem.options_music_bt[1]+menusystem.options_music_bt[3])
    	  		{
    	  			for (int i=0; i<11; i++) {
    	  				if (scaleX > menusystem.options_music_vol[i][0] && scaleX < menusystem.options_music_vol[i][1] && 
    	    	  			scaleY > menusystem.options_music_bt[1] && scaleY < menusystem.options_music_bt[1]+menusystem.options_music_bt[3])
    	  				{
    	  					musicvolume = (float)i / 10;
    	  					break;
    	  				}
    	  			}
    	  			menusystem.options_music_pressed = true;
    	  			if(sound)try{SoundManager.playSound(1,1);}catch(Exception e){e.printStackTrace();}
    	  			break;
    	  		} else 
    	  		if (scaleX > menusystem.options_vibration_bt[0] && scaleX < menusystem.options_vibration_bt[0]+menusystem.options_vibration_bt[2] && 
    	  				scaleY > menusystem.options_vibration_bt[1] && scaleY < menusystem.options_vibration_bt[1]+menusystem.options_vibration_bt[3])
    	  		{
    	  			menusystem.options_vibration_pressed = true;
    	  			if(sound)try{SoundManager.playSound(1,1);}catch(Exception e){e.printStackTrace();}
    	  			break;
    	  		} else 
    	  		if (scaleX > menusystem.options_keepscreen_bt[0] && scaleX < menusystem.options_keepscreen_bt[0]+menusystem.options_keepscreen_bt[2] && 
    	  				scaleY > menusystem.options_keepscreen_bt[1] && scaleY < menusystem.options_keepscreen_bt[1]+menusystem.options_keepscreen_bt[3])
    	  		{
    	  			menusystem.options_keepscreen_pressed = true;
    	  			if(sound)try{SoundManager.playSound(1,1);}catch(Exception e){e.printStackTrace();}
    	  			break;
    	  		}
    	  		break;
    	  	}

    	  	case 502:
    	  	case 501:
    	  		
    	  		if(scaleX > menusystem.quit_bt_yes[0] && scaleY > menusystem.quit_bt_yes[1] && scaleX < menusystem.quit_bt_yes[0]+menusystem.quit_bt_yes[2] && scaleY < menusystem.quit_bt_yes[1]+menusystem.quit_bt_yes[3]) {
    	  			if(sound)SoundManager.playSound(1,1);
    	  			menusystem.reset_yes_pressed = true;
    	  		} else if(scaleX > menusystem.quit_bt_no[0] && scaleY > menusystem.quit_bt_no[1] && scaleX < menusystem.quit_bt_no[0]+menusystem.quit_bt_no[2] && scaleY < menusystem.quit_bt_no[1]+menusystem.quit_bt_no[3]) {
    	  			if(sound)SoundManager.playSound(1,1);
    	  			menusystem.reset_no_pressed = true;
    	  		}
    	  		break;
    	  	
    	  	case 5:
    	  		for (int i=0;i<3;i++) {
       	  			if(scaleX > menusystem.profile_reset_bt[0] && scaleY > menusystem.profile_reset_bt[1]+i*hd*200 && scaleX < menusystem.profile_reset_bt[0]+menusystem.profile_reset_bt[2] && scaleY < menusystem.profile_reset_bt[1]+menusystem.profile_reset_bt[3]+i*hd*200) { 
       	  				if(sound)SoundManager.playSound(1,1);
       	  				menusystem.profile_reset_pressed[i]=true;
       	  			} else 
    	  			if(scaleX > menusystem.profile_select_bt[0] && scaleY > menusystem.profile_select_bt[1]+i*hd*200 && scaleX < menusystem.profile_select_bt[0]+menusystem.profile_select_bt[2] && scaleY < menusystem.profile_select_bt[1]+menusystem.profile_select_bt[3]+i*hd*200) { 
    	  				if(sound)SoundManager.playSound(1,1);
    	  				menusystem.profile_select_pressed[i]=true;
       	  			} 
    	    	}
    	  		break;
    	  	
    	  	case 6: 
    	  		
    	  		break;

    	  	case 701:
    	  		/*
    	  		for (int i=0;i<12;i++) {
    	  			if(scaleX > menusystem.unlock_keys_bt[i][0] && scaleY > menusystem.unlock_keys_bt[i][1] && scaleX < menusystem.unlock_keys_bt[i][0]+menusystem.unlock_keys_bt[i][2] && scaleY < menusystem.unlock_keys_bt[i][1]+menusystem.unlock_keys_bt[i][3]) { 
    	  				if(sound)SoundManager.playSound(1,1);
    	  				menusystem.unlock_keys_pressed[i]=true;
       	  				break;
       	  			}
				}
				*/
    	  		break;
    	  		
    	  	case 7: 
	  			if(!fullgame && scaleX > menusystem.unlock_fullgame_bt[0] && scaleY > menusystem.unlock_fullgame_bt[1] && scaleX < menusystem.unlock_fullgame_bt[0]+menusystem.unlock_fullgame_bt[2] && scaleY < menusystem.unlock_fullgame_bt[1]+menusystem.unlock_fullgame_bt[3]) { 
	  				if(sound)SoundManager.playSound(1,1);
	  				menusystem.unlock_fullgame_pressed=true;
   	  			} else 
   	  			if(scaleX > menusystem.unlock_facebook_bt[0] && scaleY > menusystem.unlock_facebook_bt[1] && scaleX < menusystem.unlock_facebook_bt[0]+menusystem.unlock_facebook_bt[2] && scaleY < menusystem.unlock_facebook_bt[1]+menusystem.unlock_facebook_bt[3]) { 
   	  				if(sound)SoundManager.playSound(1,1);
   	  				menusystem.unlock_facebook_pressed=true;
        			Log.i("SC","Facebook bt action down");
   	  			}  
    	  		break;
    	  	
    	  	case 8: 
    	  		// ACTION DOWN - gomb lenyomódás figyelése
    	  		menusystem.mission_marker_pressed = -1;
    	  		menusystem.mission_enter_pressed = false;
    	  		menusystem.mission_cancel_pressed = false;
    	  		// no mission selected
    	  		if (menusystem.selected_mission < 0) {
    	  			for (int i=0;i<menusystem.galaxy[menusystem.selected_galaxy].total_missions;i++) {
    	  				int mi = menusystem.galaxy[menusystem.selected_galaxy].mission_list[i];
    	  				if(menusystem.mission[mi].status > 0 && scaleX > menusystem.mission[mi].bt_xy[0] && scaleY > menusystem.mission[mi].bt_xy[1] && scaleX < menusystem.mission[mi].bt_xy[0]+(int)(hd*90) && scaleY < menusystem.mission[mi].bt_xy[1]+(int)(hd*90)) {
    	  					if(sound)SoundManager.playSound(1,1);
    	  					menusystem.mission_marker_pressed = mi;
    	  					break;
    	  				}
    	  			}
    	  			
    	  		// mission already selected	
    	  		} else {
    	  			if(scaleX > (int)(hd*85) && scaleY > (int)(hd*325) && scaleX < (int)(hd*190) && scaleY < (int)(hd*380)) {
    	  				if(sound)SoundManager.playSound(1,1);
	  					menusystem.mission_bt_difficulty = 0;
	  					difficulty = 0;
    	  			} 
    	  			if(scaleX >= (int)(hd*190) && scaleY > (int)(hd*325) && scaleX < (int)(hd*296) && scaleY < (int)(hd*380)) {
    	  				if(sound)SoundManager.playSound(1,1);
	  					menusystem.mission_bt_difficulty = 1;
	  					difficulty = 1;
    	  			} 
    	  			if(scaleX >= (int)(hd*296) && scaleY > (int)(hd*325) && scaleX < (int)(hd*395) && scaleY < (int)(hd*380)) {
    	  				if(sound)SoundManager.playSound(1,1);
	  					menusystem.mission_bt_difficulty = 2;
	  					difficulty = 2;
    	  			} 
    	  			if(scaleX > menusystem.mission_bt_enter[0] && scaleY > menusystem.mission_bt_enter[1] && scaleX < menusystem.mission_bt_enter[0]+menusystem.mission_bt_enter[2] && scaleY < menusystem.mission_bt_enter[1]+menusystem.mission_bt_enter[3]) {
    	  				if (menusystem.mission_bt_difficulty == 2 && menusystem.mission[menusystem.selected_mission].completed < 3) {
    	  					// do nothing
    	  					break;
    	  				} else {
    	  					if(sound)SoundManager.playSound(1,1);
    	  					menusystem.mission_enter_pressed = true;
    	  					break;
    	  				}
    	  			} else 
    	  			if(scaleX > menusystem.mission_bt_cancel[0] && scaleY > menusystem.mission_bt_cancel[1] && scaleX < menusystem.mission_bt_cancel[0]+menusystem.mission_bt_enter[2] && scaleY < menusystem.mission_bt_enter[1]+menusystem.mission_bt_cancel[3]) {
    	  				if(sound)SoundManager.playSound(1,1);	
	  					menusystem.mission_cancel_pressed = true;
    	  				break;
	  				}
    	  		}
    	  		//if(scaleX > menusystem.back_bt[0] && scaleY > menusystem.back_bt[1] && scaleX < menusystem.back_bt[0]+menusystem.back_bt[2] && scaleY < menusystem.back_bt[1]+menusystem.back_bt[3]) {
    	  		//	menusystem.back_pressed = true;
    	  		//}
    	  		
    	  		if(ggs && !signedin && scaleX > menusystem.btsignin_pos[0] && scaleY > menusystem.btsignin_pos[1] && scaleX < menusystem.btsignin_pos[0]+menusystem.btsignin_pos[2] && scaleY < menusystem.btsignin_pos[1]+menusystem.btsignin_pos[3]) {
    	  			menusystem.btsignin_pressed = true;
    	  			if(sound)try{SoundManager.playSound(1,1);}catch(Exception e){e.printStackTrace();}
    	  		}     
    	  		if(ggs && scaleX > menusystem.btachievements_pos[0] && scaleY > menusystem.btachievements_pos[1] && scaleX < menusystem.btachievements_pos[0]+menusystem.btachievements_pos[2] && scaleY < menusystem.btachievements_pos[1]+menusystem.btachievements_pos[3]) {
    	  			menusystem.btachievements_pressed = true;
    	  			if(sound)try{SoundManager.playSound(1,1);}catch(Exception e){e.printStackTrace();}
    	  		}     
    	  		if(ggs && scaleX > menusystem.btleaderboard_pos[0] && scaleY > menusystem.btleaderboard_pos[1] && scaleX < menusystem.btleaderboard_pos[0]+menusystem.btleaderboard_pos[2] && scaleY < menusystem.btleaderboard_pos[1]+menusystem.btleaderboard_pos[3]) {
    	  			menusystem.btleaderboard_pressed = true;
    	  			if(sound)try{SoundManager.playSound(1,1);}catch(Exception e){e.printStackTrace();}
    	  		}     

    	  		
    	  		break;
    	  	
    	  	case 9:
    	  		
    	  		ontouch_down_case_9();
    	  		break;

    	  	case 102:
    	  		
				if(scaleX > menusystem.fbshare_pos[0] && scaleY > menusystem.fbshare_pos[1] && scaleX < menusystem.fbshare_pos[0] + menusystem.fbshare_pos[2] && scaleY < menusystem.fbshare_pos[1] + menusystem.fbshare_pos[3]) { 
					menusystem.fbshare_pressed = true;
				}
				else if(scaleX > menusystem.fblike_pos[0] && scaleY > menusystem.fblike_pos[1] && scaleX < menusystem.fblike_pos[0] + menusystem.fblike_pos[2] && scaleY < menusystem.fblike_pos[1] + menusystem.fblike_pos[3]) { 
					menusystem.fblike_pressed = true;
				}
				else {
					menusystem.continue_pressed = true;
				}
				break;
				
    	  	case 10: 
    	  		
    	  		if (!spaceship.freeze && endmission_state==0) {
    	  			
	    	  		case10_down(scaleX, scaleY);

    	  		}
    	  		    	      	
    	  		break;
    	  		
    	  	//case 108 : 
    	  	//	if(scaleX > menusystem.quit_bt_yes[0] && scaleY > menusystem.quit_bt_yes[1] && scaleX < menusystem.quit_bt_yes[0]+menusystem.quit_bt_yes[2] && scaleY < menusystem.quit_bt_yes[1]+menusystem.quit_bt_yes[3]) {
    	  	//		if(sound)SoundManager.playSound(1,1);
    	  	//		menusystem.quit_yes_pressed = true;
    	  	//	} else if(scaleX > menusystem.quit_bt_no[0] && scaleY > menusystem.quit_bt_no[1] && scaleX < menusystem.quit_bt_no[0]+menusystem.quit_bt_no[2] && scaleY < menusystem.quit_bt_no[1]+menusystem.quit_bt_no[3]) {
    	  	//		if(sound)SoundManager.playSound(1,1);
    	  	//		menusystem.quit_no_pressed = true;
    	  	//	}
    	  	//
    	  	//	break;
    	  	
    	  	default :
    	  		
    	  		break;
    	  		
    	  } // switch menu
    	  

    	  
    	  
      } // if ! loading
	    
			//Remember the values
	        oldX = x;
	        oldY = y;

	    	break;
		} // action down case
		
		// touch event action move
		case MotionEvent.ACTION_MOVE: {
			// Find the index of the active pointer and fetch its position
	        final int pointerIndex = event.findPointerIndex(mActivePointerId);
	        
	    	final float x = event.getX(pointerIndex);
	    	final float y = event.getY(pointerIndex);
	    	scaleX = x * dimensionX / this.getWidth();
	    	scaleY = y * dimensionY / this.getHeight();

	    	if(!loading) {
        	//Calculate the change
        	float dx = (x - oldX) * dimensionX / this.getWidth();;
	        float dy = (y - oldY) * dimensionY / this.getHeight();;

	    	  switch (menu) {
	          	
	    	  	case 0: 
	    	  		
	    	  		break;

	    	  	case 91:
	    	  	case 101:
	    	  	case 108:
	    	  		if(menusystem.quit_yes_pressed) {
	    	  			if(scaleX < menusystem.quit_bt_yes[0] || scaleY < menusystem.quit_bt_yes[1] || scaleX > menusystem.quit_bt_yes[0]+menusystem.quit_bt_yes[2] || scaleY > menusystem.quit_bt_yes[1]+menusystem.quit_bt_yes[3]) {
	    	  				menusystem.quit_yes_pressed = false;
	    	  			}
	    	  		}
	    	  		if(menusystem.quit_no_pressed) {
	    	  			if(scaleX < menusystem.quit_bt_no[0] || scaleY < menusystem.quit_bt_no[1] || scaleX > menusystem.quit_bt_no[0]+menusystem.quit_bt_no[2] || scaleY > menusystem.quit_bt_no[1]+menusystem.quit_bt_no[3]) {
	    	  				menusystem.quit_no_pressed = false;
	    	  			}
	    	  		}
	    	  		break;
	    	  		
	    	  	case 1: 
	    	  		
	    	  		for (int i=0; i<menusystem.mainmenu.button_num; i++) {
	    	  			if(menusystem.mainmenu.button_pressed[i]) {
	    	  				if(scaleX <= menusystem.mainmenu.button_pos[i][0] || scaleY <= menusystem.mainmenu.button_pos[i][1] || scaleX >= menusystem.mainmenu.button_pos[i][0]+menusystem.mainmenu.button_pos[i][2] || scaleY >= menusystem.mainmenu.button_pos[i][1]+menusystem.mainmenu.button_pos[i][3]) {
	    	  					menusystem.mainmenu.button_pressed[i] = false;
	    	  				}
	    	  			}
	    	  		}
	    	  		break;
	    	  		
	    	  	case 105 :
	    	  		if(scaleX < menusystem.getmore_pos[0] || scaleY < menusystem.getmore_pos[1] || scaleX > menusystem.getmore_pos[0]+menusystem.getmore_pos[2] || scaleY > menusystem.getmore_pos[1]+menusystem.getmore_pos[3]) {
	    	  			menusystem.getmore_pressed = false;
	    	  		} 
	    	  		if(scaleX > menusystem.getmore_pos[0] && scaleY > menusystem.getmore_pos[1] && scaleX < menusystem.getmore_pos[0]+menusystem.getmore_pos[2] && scaleY < menusystem.getmore_pos[1]+menusystem.getmore_pos[3]) {
	    	  			menusystem.daily_touched = false;
	    	  		}	    	  		
	    	  		break;
	    	  	
	    	  	case 2: 
	    	  		if (scaleX <= menusystem.galaxy_bt[0] || scaleX >= menusystem.galaxy_bt[0]+menusystem.galaxy_bt[2] || 
	    	  			scaleY <= menusystem.galaxy_bt[1] || scaleY >= menusystem.galaxy_bt[1]+menusystem.galaxy_bt[3])
	    	  		{
	    	  			menusystem.galaxy_bt_pressed = false;
	    	  		}
	    	  		
	    	  		if(menusystem.btsignin_pressed) {
		    	  		if(scaleX < menusystem.btsignin_pos[0] || scaleY < menusystem.btsignin_pos[1] || scaleX > menusystem.btsignin_pos[0]+menusystem.btsignin_pos[2] || scaleY > menusystem.btsignin_pos[1]+menusystem.btsignin_pos[3]) {
		    	  			menusystem.btsignin_pressed = false;
		    	  		}
	    	  		} 
	    	  		if(menusystem.btachievements_pressed) {
		    	  		if(scaleX < menusystem.btachievements_pos[0] || scaleY < menusystem.btachievements_pos[1] || scaleX > menusystem.btachievements_pos[0]+menusystem.btachievements_pos[2] || scaleY > menusystem.btachievements_pos[1]+menusystem.btachievements_pos[3]) {
		    	  			menusystem.btachievements_pressed = false;
		    	  		}
	    	  		} 
	    	  		if(menusystem.btleaderboard_pressed) {
		    	  		if(scaleX < menusystem.btleaderboard_pos[0] || scaleY < menusystem.btleaderboard_pos[1] || scaleX > menusystem.btleaderboard_pos[0]+menusystem.btleaderboard_pos[2] || scaleY > menusystem.btleaderboard_pos[1]+menusystem.btleaderboard_pos[3]) {
		    	  			menusystem.btleaderboard_pressed = false;
		    	  		}
	    	  		} 

	    	  		break;
	    	  	
	    	  	case 3: 
	    	  		
	    	  		break;
	    	  	
	    	  	case 4: 
	    	  		if(menusystem.options_sound_pressed) {
	    	  			if (scaleX < menusystem.options_sound_bt[0] || scaleX > menusystem.options_sound_bt[0]+menusystem.options_sound_bt[2] || 
	    	  				scaleY < menusystem.options_sound_bt[1] || scaleY > menusystem.options_sound_bt[1]+menusystem.options_sound_bt[3])
	    	  			{
	    	  				menusystem.options_sound_pressed = false;
	    	  			}
	    	  		}
	    	  		if(menusystem.options_music_pressed) {
	    	  			if (scaleX < menusystem.options_music_bt[0] || scaleX > menusystem.options_music_bt[0]+menusystem.options_music_bt[2] || 
	    	  				scaleY < menusystem.options_music_bt[1] || scaleY > menusystem.options_music_bt[1]+menusystem.options_music_bt[3])
	    	  			{
	    	  				menusystem.options_music_pressed = false;
	    	  				if (musicvolume < 0.1f) {
	    	  					music = false;
	    	  					if (mediaplayer.isPlaying()) { mediaplayer.stop(); }
	    	  				} else {
	    	  					music = true;
	    	  					if (!mediaplayer.isPlaying()) {
	    	  						media_load(media_list[0],true);
	    	  						//mediaplayer.start();
	    	  					} else {
	    	  						mediaplayer.setVolume(musicvolume, musicvolume);
	    	  					}
	    	  				}
	    	  				pref[0].edit().putBoolean("Music", music).commit();
	    	  				pref[0].edit().putFloat("MusicVol", musicvolume).commit();

	    	  			} else {
		        	  		if (scaleX > menusystem.options_music_bt[0] && scaleX < menusystem.options_music_bt[0]+menusystem.options_music_bt[2] && 
		        	  				scaleY > menusystem.options_music_bt[1] && scaleY < menusystem.options_music_bt[1]+menusystem.options_music_bt[3])
		        	  		{
		        	  			for (int i=0; i<11; i++) {
		        	  				if (scaleX > menusystem.options_music_vol[i][0] && scaleX < menusystem.options_music_vol[i][1] && 
		        	    	  			scaleY > menusystem.options_music_bt[1] && scaleY < menusystem.options_music_bt[1]+menusystem.options_music_bt[3])
		        	  				{
		        	  					musicvolume = (float)i / 10;
		        	  					break;
		        	  				}
		        	  			}
		        	  		}
    	  				}
	    	  		}
	    	  		if(menusystem.options_animation_pressed) {
	    	  			if (scaleX < menusystem.options_animation_bt[0] || scaleX > menusystem.options_animation_bt[0]+menusystem.options_animation_bt[2] || 
	    	  				scaleY < menusystem.options_animation_bt[1] || scaleY > menusystem.options_animation_bt[1]+menusystem.options_animation_bt[3])
	    	  			{
	    	  				menusystem.options_animation_pressed = false;
	    	  			}
	    	  		}
	    	  		if(menusystem.options_vibration_pressed) {
	    	  			if (scaleX < menusystem.options_vibration_bt[0] || scaleX > menusystem.options_vibration_bt[0]+menusystem.options_vibration_bt[2] || 
	    	  				scaleY < menusystem.options_vibration_bt[1] || scaleY > menusystem.options_vibration_bt[1]+menusystem.options_vibration_bt[3])
	    	  			{
	    	  				menusystem.options_vibration_pressed = false;
	    	  			}
	    	  		}
	    	  		if(menusystem.options_gfx_pressed) {
	    	  			if (scaleX < menusystem.options_gfx_bt[0] || scaleX > menusystem.options_gfx_bt[0]+menusystem.options_gfx_bt[2] || 
	    	  				scaleY < menusystem.options_gfx_bt[1] || scaleY > menusystem.options_gfx_bt[1]+menusystem.options_gfx_bt[3])
	    	  			{
	    	  				menusystem.options_gfx_pressed = false;
	    	  			}
	    	  		}
	    	  		if(menusystem.options_keepscreen_pressed) {
	    	  			if (scaleX < menusystem.options_keepscreen_bt[0] || scaleX > menusystem.options_keepscreen_bt[0]+menusystem.options_keepscreen_bt[2] || 
	    	  				scaleY < menusystem.options_keepscreen_bt[1] || scaleY > menusystem.options_keepscreen_bt[1]+menusystem.options_keepscreen_bt[3])
	    	  			{
	    	  				menusystem.options_keepscreen_pressed = false;
	    	  			}
	    	  		}
	    	  		break;

	    	  	case 502 :
	    	  	case 501 :
	    	  		
	    	  		if(menusystem.reset_yes_pressed) {
	    	  			if(scaleX < menusystem.quit_bt_yes[0] || scaleY < menusystem.quit_bt_yes[1] || scaleX > menusystem.quit_bt_yes[0]+menusystem.quit_bt_yes[2] || scaleY > menusystem.quit_bt_yes[1]+menusystem.quit_bt_yes[3]) {
	    	  				menusystem.reset_yes_pressed = false;
	    	  			}
	    	  		} 
	    	  		if(menusystem.reset_no_pressed) {
	    	  			if(scaleX < menusystem.quit_bt_no[0] || scaleY < menusystem.quit_bt_no[1] || scaleX > menusystem.quit_bt_no[0]+menusystem.quit_bt_no[2] || scaleY > menusystem.quit_bt_no[1]+menusystem.quit_bt_no[3]) {
	    	  				menusystem.reset_no_pressed = false;
	    	  			}
	    	  		}
	    	  		break;
	    	  		
	    	  	case 5: 
	    	  		for (int i=0;i<3;i++) {
	    	  			if (menusystem.profile_reset_pressed[i]) {
	    	  				if(scaleX < menusystem.profile_reset_bt[0] || scaleY < menusystem.profile_reset_bt[1]+i*hd*200 || scaleX > menusystem.profile_reset_bt[0]+menusystem.profile_reset_bt[2] || scaleY > menusystem.profile_reset_bt[1]+menusystem.profile_reset_bt[3]+i*(int)(hd*200)) { 
	    	  					menusystem.profile_reset_pressed[i]=false;
	    	  				}
	    	  			}
	    	  			if (menusystem.profile_select_pressed[i]) {
	    	  				if(scaleX < menusystem.profile_select_bt[0] || scaleY < menusystem.profile_select_bt[1]+i*hd*200 || scaleX > menusystem.profile_select_bt[0]+menusystem.profile_select_bt[2] || scaleY > menusystem.profile_select_bt[1]+menusystem.profile_select_bt[3]+i*(int)(hd*200)) { 
	    	  					menusystem.profile_select_pressed[i]=false;
	    	  				}
	    	  			}
	    	    	}
	    	  		break;
	    	  	
	    	  	case 6: 
	    	  		
	    	  		break;
	    	  	
	    	  	case 701: 
	    	  		/*
	    	  		for (int i=0;i<12;i++) {
	    	  			if(scaleX < menusystem.unlock_keys_bt[i][0] || scaleY < menusystem.unlock_keys_bt[i][1] || scaleX > menusystem.unlock_keys_bt[i][0]+menusystem.unlock_keys_bt[i][2] || scaleY > menusystem.unlock_keys_bt[i][1]+menusystem.unlock_keys_bt[i][3]) { 
	       	  				menusystem.unlock_keys_pressed[i]=false;
	       	  			}
					}
					*/
	    	  		break;

	    	  	
	    	  	case 7: 
	    	  		if (menusystem.unlock_fullgame_pressed) {
	    	  			if(scaleX < menusystem.unlock_fullgame_bt[0] || scaleY < menusystem.unlock_fullgame_bt[1] || scaleX > menusystem.unlock_fullgame_bt[0]+menusystem.unlock_fullgame_bt[2] || scaleY > menusystem.unlock_fullgame_bt[1]+menusystem.unlock_fullgame_bt[3]) { 
	    	  				menusystem.unlock_fullgame_pressed=false;
	    	  			}
	   	  			}  
	    	  		if (menusystem.unlock_facebook_pressed) {
	    	  			if(scaleX < menusystem.unlock_facebook_bt[0] || scaleY < menusystem.unlock_facebook_bt[1] || scaleX > menusystem.unlock_facebook_bt[0]+menusystem.unlock_facebook_bt[2] || scaleY > menusystem.unlock_facebook_bt[1]+menusystem.unlock_facebook_bt[3]) { 
	    	  				menusystem.unlock_facebook_pressed=false;
   	 	        			Log.i("SC","Facebook bt action move");
	    	  			}
	   	  			}  
	    	  		
	    	  		break;
	    	  	
	    	  	case 8: 
	    	  		// no mission selected
	    	  		if (menusystem.selected_mission < 0) {
	    	  			for (int i=0;i<menusystem.galaxy[menusystem.selected_galaxy].total_missions;i++) {
	    	  				int mi = menusystem.galaxy[menusystem.selected_galaxy].mission_list[i];
	    	  				if(scaleX <= menusystem.mission[mi].bt_xy[0] || scaleY <= menusystem.mission[mi].bt_xy[1] || scaleX >= menusystem.mission[mi].bt_xy[0]+(int)(hd*90) || scaleY >= menusystem.mission[mi].bt_xy[1]+(int)(hd*90)) {
	    	  					if (menusystem.mission_marker_pressed == mi) {
	    	  						menusystem.mission_marker_pressed = -1;
	    	  						break;
	    	  					}
	    	  				}
	    	  			}
	    	  		// mission already selected	
	    	  		} else {
	    	  			if(scaleX <= menusystem.mission_bt_enter[0] || scaleY <= menusystem.mission_bt_enter[1] || scaleX >= menusystem.mission_bt_enter[0]+menusystem.mission_bt_enter[2] || scaleY >= menusystem.mission_bt_enter[1]+menusystem.mission_bt_enter[3]) 
		  					menusystem.mission_enter_pressed = false;
		    	  		if(scaleX <= menusystem.mission_bt_cancel[0] || scaleY <= menusystem.mission_bt_cancel[1] || scaleX >= menusystem.mission_bt_cancel[0]+menusystem.mission_bt_cancel[2] || scaleY >= menusystem.mission_bt_cancel[1]+menusystem.mission_bt_cancel[3]) 	    	  					
		  					menusystem.mission_cancel_pressed = false;
	    	  		}
	    	  		//if(menusystem.back_pressed) {
	    	  		//	if(scaleX < menusystem.back_bt[0] || scaleY < menusystem.back_bt[1] || scaleX > menusystem.back_bt[0]+menusystem.back_bt[2] || scaleY > menusystem.back_bt[1]+menusystem.back_bt[3]) {
	    	  		//	}
	    	  		//	menusystem.back_pressed = false;
	    	  		//}
	    	  		
	    	  		if(menusystem.btsignin_pressed) {
		    	  		if(scaleX < menusystem.btsignin_pos[0] || scaleY < menusystem.btsignin_pos[1] || scaleX > menusystem.btsignin_pos[0]+menusystem.btsignin_pos[2] || scaleY > menusystem.btsignin_pos[1]+menusystem.btsignin_pos[3]) {
		    	  			menusystem.btsignin_pressed = false;
		    	  		}
	    	  		} 
	    	  		if(menusystem.btachievements_pressed) {
		    	  		if(scaleX < menusystem.btachievements_pos[0] || scaleY < menusystem.btachievements_pos[1] || scaleX > menusystem.btachievements_pos[0]+menusystem.btachievements_pos[2] || scaleY > menusystem.btachievements_pos[1]+menusystem.btachievements_pos[3]) {
		    	  			menusystem.btachievements_pressed = false;
		    	  		}
	    	  		} 
	    	  		if(menusystem.btleaderboard_pressed) {
		    	  		if(scaleX < menusystem.btleaderboard_pos[0] || scaleY < menusystem.btleaderboard_pos[1] || scaleX > menusystem.btleaderboard_pos[0]+menusystem.btleaderboard_pos[2] || scaleY > menusystem.btleaderboard_pos[1]+menusystem.btleaderboard_pos[3]) {
		    	  			menusystem.btleaderboard_pressed = false;
		    	  		}
	    	  		} 

	    	  		break;
	    	  		
	    	  	case 9:
	    	  		
	    	  		ontouch_move_case_9(dy);
	    	  		break;

	    	  	case 102:

					if(scaleX > menusystem.fbshare_pos[0] && scaleY > menusystem.fbshare_pos[1] && scaleX < menusystem.fbshare_pos[0] + menusystem.fbshare_pos[2] && scaleY < menusystem.fbshare_pos[1] + menusystem.fbshare_pos[3]) { 
						menusystem.continue_pressed = false;
					}
					else if(scaleX > menusystem.fblike_pos[0] && scaleY > menusystem.fblike_pos[1] && scaleX < menusystem.fblike_pos[0] + menusystem.fblike_pos[2] && scaleY < menusystem.fblike_pos[1] + menusystem.fblike_pos[3]) { 
						menusystem.continue_pressed = false;
					}

	    	  		if(menusystem.fbshare_pressed) {
						if(scaleX < menusystem.fbshare_pos[0] || scaleY < menusystem.fbshare_pos[1] || scaleX > menusystem.fbshare_pos[0] + menusystem.fbshare_pos[2] || scaleY > menusystem.fbshare_pos[1] + menusystem.fbshare_pos[3]) { 
							menusystem.fbshare_pressed = false;
						}
	    	  		}
	    	  		if(menusystem.fblike_pressed) {
						if(scaleX < menusystem.fblike_pos[0] || scaleY < menusystem.fblike_pos[1] || scaleX > menusystem.fblike_pos[0] + menusystem.fblike_pos[2] || scaleY > menusystem.fblike_pos[1] + menusystem.fblike_pos[3]) { 
							menusystem.fblike_pressed = false;
						}
	    	  		}
					break;
					
	    	  	case 10:
	    	  		// spaceship jobbra - balra kep
	    	  		//if (dx < -TOUCH_SCALE) spaceship.image_update = 1;
	    	  		//else if (dx > TOUCH_SCALE) spaceship.image_update = 2;
	    	  		//else spaceship.image_update = 0;
	    	  		if (spaceship.isalive && !spaceship.freeze) {
	    	  			spaceship.x += dx * spaceship.speed / 10; // TOUCH_SCALE
	    	  			if (spaceship.x < 0) spaceship.x = 0;
	    	  			if (spaceship.x > dimensionX - allUpgrades.ship[spaceship.type].width) spaceship.x = dimensionX - allUpgrades.ship[spaceship.type].width;

	    	  			if (vertical_move) {
	    	  				spaceship.y += dy * spaceship.speed / 10; // TOUCH_SCALE
	    	  				if (spaceship.y < vertical_move_max) spaceship.y = vertical_move_max;
	    	  				// spaceship default y koordinataja
	    	  				if (spaceship.y > (int)(hd*800) - allUpgrades.ship[spaceship.type].height) spaceship.y = (int)(hd*800) - allUpgrades.ship[spaceship.type].height;
	    	  			}
	    	  		}
	    	        
	            	if ( bmenu_button_down_any) {
	            		/*if (bmenu_button_down[0]) {
	            			if(scaleX <= bottommenu_button1[0] || scaleY <= bottommenu_button1[1] || scaleX >= bottommenu_button1[2] || scaleY >= bottommenu_button1[3]) { 
	            				bmenu_button_down[0] = false; bmenu_button_down_any = false;
	            			}
	            		} else if (bmenu_button_down[1]) {
	            	    	if(scaleX <= bottommenu_button2[0] || scaleY <= bottommenu_button2[1] || scaleX >= bottommenu_button2[2] || scaleY >= bottommenu_button2[3]) { 
	            	    		bmenu_button_down[1] = false; bmenu_button_down_any = false;
	            	    	}
	            		} else */if (bmenu_button_down[2]) {
	            	    	if(scaleX <= bottommenu_button3[0] || scaleY <= bottommenu_button3[1] || scaleX >= bottommenu_button3[2] || scaleY >= bottommenu_button3[3]) { 
	            	    		bmenu_button_down[2] = false; bmenu_button_down_any = false;
	            	    	}
	            		} else if (bmenu_button_down[3]) {
	            	    	if(scaleX <= bottommenu_button4[0] || scaleY <= bottommenu_button4[1] || scaleX >= bottommenu_button4[2] || scaleY >= bottommenu_button4[3]) { 
	            	    		bmenu_button_down[3] = false; bmenu_button_down_any = false;
	            	    	}
	            		} else if (bmenu_button_down[4]) {
	            	    	if(scaleX <= bottommenu_button5[0] || scaleY <= bottommenu_button5[1] || scaleX >= bottommenu_button5[2] || scaleY >= bottommenu_button5[3]) { 
	            	    		bmenu_button_down[4] = false; bmenu_button_down_any = false;
	            	    	}
	            		} else if (bmenu_button_down[5]) {
	            	    	if(scaleX <= bottommenu_button6[0] || scaleY <= bottommenu_button6[1] || scaleX >= bottommenu_button6[2] || scaleY >= bottommenu_button6[3]) { 
	            	    		bmenu_button_down[5] = false; bmenu_button_down_any = false;
	            	    	}
	            		}
	            		else {
	            			for (int i=0; i<6; i++) { bmenu_button_down[i] = false; }
	            			bmenu_button_down_any = false;
	            		}
	  	    	  	}	        

	    	  		break;

	    	  	//case 108 : 
	    	  	//	if(menusystem.quit_yes_pressed) {
	    	  	//		if(scaleX < menusystem.quit_bt_yes[0] || scaleY < menusystem.quit_bt_yes[1] || scaleX > menusystem.quit_bt_yes[0]+menusystem.quit_bt_yes[2] || scaleY > menusystem.quit_bt_yes[1]+menusystem.quit_bt_yes[3]) {
	    	  	//			menusystem.quit_yes_pressed = false;
	    	  	//		}
	    	  	//	} else if(menusystem.quit_no_pressed) {
	    	  	//		if(scaleX < menusystem.quit_bt_no[0] || scaleY < menusystem.quit_bt_no[1] || scaleX > menusystem.quit_bt_no[0]+menusystem.quit_bt_no[2] || scaleY > menusystem.quit_bt_no[1]+menusystem.quit_bt_no[3]) {
	    	  	//			menusystem.quit_no_pressed = false;
	    	  	//		}
	    	  	//	}
	    	  	//
	    	  	//	break;
	    	  		
	    	  	default :
	    	  		
	    	  		break;
	    	  		
	        
	        
	    	  } // switch menu
        		
        	} // if ! loading

			//Remember the values
	        oldX = x;
	        oldY = y;

	    	break;

		} // action move case

		case MotionEvent.ACTION_UP: {

			// Find the index of the active pointer and fetch its position
	        final int pointerIndex = event.findPointerIndex(mActivePointerId);
	        final float x = event.getX(pointerIndex);
	    	final float y = event.getY(pointerIndex);
	    	scaleX = x * dimensionX / this.getWidth();
	    	scaleY = y * dimensionY / this.getHeight();
	
	    	mActivePointerId = INVALID_POINTER_ID;
	    	
			if(!loading) {

			try {
	    	  switch (menu) {
	          	
	    	  	case 0: 
	    	  		
	    	  		break;

	    	  	case 101:
	    	  		if(menusystem.quit_yes_pressed) {
	    	  			if(scaleX > menusystem.quit_bt_yes[0] && scaleY > menusystem.quit_bt_yes[1] && scaleX < menusystem.quit_bt_yes[0]+menusystem.quit_bt_yes[2] && scaleY < menusystem.quit_bt_yes[1]+menusystem.quit_bt_yes[3]) {
	    	  				quit = false;
	    	  				menu = 1;
	    	  				like_tap = 0;
	    	  				((Activity)getContext()).finish();
	    	  			}
	    	  			menusystem.quit_yes_pressed = false;
	    	  		} else if(menusystem.quit_no_pressed) {
	    	  			if(scaleX > menusystem.quit_bt_no[0] && scaleY > menusystem.quit_bt_no[1] && scaleX < menusystem.quit_bt_no[0]+menusystem.quit_bt_no[2] && scaleY < menusystem.quit_bt_no[1]+menusystem.quit_bt_no[3]) {
	    	  				if(sound)SoundManager.playSound(22,1);
	    	  				quit = false;
	    	  				menu = 1;
	    	  				like_tap = 0;
	    	  			}
	    	  			menusystem.quit_no_pressed = false;
	    	  		}
	    	  		
	    	  		break;
	    	  		
	    	  	case 1:
	    	  		if(!liked && like_tap < 3) {
	       	  			if(scaleX > like_pos[like_tap][0] && scaleY > like_pos[like_tap][1] && scaleX < like_pos[like_tap][2] && scaleY < like_pos[like_tap][3]) {
	        	  				like_tap++;
		    	  		}
	    	  		}
	      
	    	  		for (int i=0; i<menusystem.mainmenu.button_num; i++) {
	    	  			if(menusystem.mainmenu.button_pressed[i]) {
	        	  			if(scaleX > menusystem.mainmenu.button_pos[i][0] && scaleY > menusystem.mainmenu.button_pos[i][1] && scaleX < menusystem.mainmenu.button_pos[i][0]+menusystem.mainmenu.button_pos[i][2] && scaleY < menusystem.mainmenu.button_pos[i][1]+menusystem.mainmenu.button_pos[i][3]) {
	        	  				switch (i) {
	        	  				case 0: 
	        	  					menusystem.dialogue_galaxy = true;
	        	  					menusystem.dialogue_mission = false;
	        	  					menusystem.dialogue_upgrade = false;
	        	  					menusystem.dialogue_game = false;
	        	  					menusystem.parrow_total = 0;
	        	  					
	        	  					if (!liked && like_tap == 3) {
	        	  						profile_promos[0] = true;
	  									Save_Profile(5);
	  									LY += 20000;
	  									Save_Profile(4); 
	  									if (!allUpgrades.special[31].bought) {
	  										allUpgrades.special[31].bought = true;
	  										Save_Profile(0);
	  										profile_promos[1] = true;
	  										Save_Profile(5);
	  									} 
	  									if (!allUpgrades.special[32].bought) {
	  										allUpgrades.special[32].bought = true;
	  										Save_Profile(0);
	  										profile_promos[2] = true;
	  										Save_Profile(5);
	  									}
	  									liked = true;
	        	  					}
	        	  					
        	  						menu = 2;
	        	  					break;
	        	  				case 1:
	        	  					break;
	        	  				case 2: 
	        	  					if(sound)SoundManager.playSound(21,1);
	        	  					menu = 4;
	        	  					break;
	        	  				case 3: 
	        	  					if(sound)SoundManager.playSound(21,1);
	        	  			  		loading = false;
	        	  			  		
	        	  			  		for (int k=0;k<3;k++) {
	        	  			  			profile_ship[k] = pref[k+1].getInt("SpaceShip_Type", 0);
	        	  			  			profile_ly[k] = pref[k+1].getLong("LY", 10000);
	        	  			  			profile_galaxies[k] = pref[k+1].getInt("Galaxy_Completed", 0);
	        	  			  			profile_missions[k] = pref[k+1].getInt("Mission_Completed", 0);
	        	  			  			profile_highscore[k] = pref[k+1].getLong("Mission_Totalscore", 0);
	        	  			  		}
	        	  			  		
	        	  					menu = 5;
	        	  					break;
	        	  				case 4: 
	        	  					if(sound)SoundManager.playSound(21,1);
	        	  					menusystem.unlock_prev = 1;
	        	  					menu = 7;
	        	  					break;
	        	  				case 5: 
	        	  					Intent intent5 = new Intent(Intent.ACTION_VIEW); 
	                				intent5.setData(Uri.parse("http://www.facebook.com/mangatamedia"));
	                				context.startActivity(intent5); 
	        	  					break;
	        	  				case 6: 
	        	  					//AppRater.launch_MoreGamesDialog(context, true, (int)scrWidth, (int)scrHeight );
	        	  					//AppRater.launch_MoreGamesDialog(context, true, (int)scrWidth, (int)scrHeight );
	        	  					Intent intent6 = new Intent(Intent.ACTION_VIEW); 
	                				intent6.setData(Uri.parse("http://www.twitter.com/mangatamedia"));
	                				context.startActivity(intent6);
	        	  					break;
	        	  				case 7: 
	        	  					AppRater.launch_MoreGamesDialog(context, true, (int)scrWidth, (int)scrHeight );
	        	  					/*
	        	  					Intent intent7 = new Intent(Intent.ACTION_VIEW);
	        	  					intent7.setData(Uri.parse("http://www.youtube.com/watch?v=7jJyUfPErl0"));
	                				context.startActivity(intent7);
	                				*/
	        	  					break;
	        	  				case 8: 
	        	  					Intent intent8 = new Intent(Intent.ACTION_VIEW);
	        	  					if (hd > 1) {
		        	  					if (isGoogle) intent8.setData(Uri.parse("market://details?id=com.tubigames.galaxy.shooter.hd"));
		        	  					else intent8.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.galaxy.shooter.hd"));
	        	  					} else {
		        	  					if (isGoogle) intent8.setData(Uri.parse("market://details?id=com.tubigames.galaxy.shooter"));
		        	  					else intent8.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.galaxy.shooter"));
	        	  					}
	                				context.startActivity(intent8);
	        	  					break;
	        	  				case 9: 
	        	  					if(sound)SoundManager.playSound(21,1);
	        	  					menu = 6;
	        	  					break;
	        	  				default: 
	        	  					break;
	        	  				}
	        	  				break;
	    	  				}
	    	  			}
	    	  		}
	    	  		for (int i=0; i<menusystem.mainmenu.button_num; i++) {
	    	  			menusystem.mainmenu.button_pressed[i] = false;
	    	  		}
	    	  		break;

	    	  	case 105 :
	    	  		if (menusystem.getmore_pressed && scaleX > menusystem.getmore_pos[0] && scaleY > menusystem.getmore_pos[1] && scaleX < menusystem.getmore_pos[0]+menusystem.getmore_pos[2] && scaleY < menusystem.getmore_pos[1]+menusystem.getmore_pos[3]) {
	    	  			if(sound)SoundManager.playSound(21,1);
	    	  			menusystem.prev_menu = 105;
	    	  			
	    	  			menusystem.upgrade_selected = true;
	    	  			menusystem.upgrade_selected_purchasable = 0;
	    	  			
	    	  					//loading = true;
	    	  					menusystem.upgrade_selected = true; 
	    	  					menusystem.upgrade_selected_weapon = -1;
	    	  					menusystem.upgrade_selected_modifier = -1;
	    	  					menusystem.upgrade_selected_special = -1;
	    	  					menusystem.upgrade_selected_upgrade = -1;
	    	  					menusystem.upgrade_selected_purchasable = 0;
	    	  					menusystem.upgrade_selected_ship = -1;
	    						menusystem.upgrade_page = 0;
	    						if(sound)SoundManager.playSound(21,1);
	    						
	    						// reset upgrade list
	    						for (int o=0; o<6; o++) menusystem.itemlist[o] = -2;
	    						int num = 0; int current = 0;
	    						// UPGRADE SELECTED
	    						if (menusystem.upgrade_selected_purchasable >= 0) {
	    							for (int n=0; n<allUpgrades.maxpurchasable; n++) {
	    								if (allUpgrades.purchasable[n].available) {
	    									num++;
	    									if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
	    										menusystem.itemlist[current] = n;
	    										current++;
	    									}
	    								}
	    							}
	    							menusystem.upgrade_maxpage = (num-1) / 6 + 1;
	    							menusystem.upgrade_slot_selected = -1;
	    						}
	    	  					//break;
	    	  			
	    	  			menu = 9;
	    	  			//menusystem.prev_menu = 1;
	    	  		} else if (menusystem.daily_touched) {
	    	  			if(sound)SoundManager.playSound(21,1);
	    	  			menu = 1;
	    	  			like_tap = 0;
	    	  		}
	    	  		menusystem.getmore_pressed = false;
	    	  		menusystem.daily_touched = false;
	    	  		break;
	    	  		
	    	  	case 2: 
	    	  		if (menusystem.galaxy_bt_pressed && menusystem.selected_galaxy >=0) {
	    	  			if (scaleX > menusystem.galaxy_bt[0] && scaleX < menusystem.galaxy_bt[0]+menusystem.galaxy_bt[2] && 
	    	  				scaleY > menusystem.galaxy_bt[1] && scaleY < menusystem.galaxy_bt[1]+menusystem.galaxy_bt[3])
	    	  			{
	    	  				if (menusystem.galaxy[menusystem.selected_galaxy].fullgame && !fullgame && menusystem.galaxy[menusystem.selected_galaxy].status != 2) {
	    	  					if(sound)SoundManager.playSound(21,1);
	    	  					menusystem.unlock_prev = 2;
	    	  					menu = 7;
	    	  				} else 
	    	  				if (menusystem.galaxy[menusystem.selected_galaxy].status == 1){
	    	  					if (LY >= menusystem.galaxy[menusystem.selected_galaxy].distance){
	    	  						if(sound)SoundManager.playSound(2,1);
	    	  						LY -= menusystem.galaxy[menusystem.selected_galaxy].distance;
	    	  						menusystem.galaxy[menusystem.selected_galaxy].status = 2;
	    	  						allUpgrades.update(menusystem.galaxy[menusystem.selected_galaxy].update,profile_promos);
	    	  						for(int i=0;i<menusystem.galaxy[menusystem.selected_galaxy].unlock_galaxy_list.length;i++){
	    	  							if(menusystem.galaxy[menusystem.selected_galaxy].unlock_galaxy_list[i]>=0) {
	    	  								if(menusystem.galaxy[menusystem.galaxy[menusystem.selected_galaxy].unlock_galaxy_list[i]].status < 1)
	    	  									menusystem.galaxy[menusystem.galaxy[menusystem.selected_galaxy].unlock_galaxy_list[i]].status = 1;
	    	  							}
	    	  						}
	    	  						Save_Profile(1);
	    	  						menusystem.selected_galaxy = -1;
	    	  					}
	    	  				}
	    	  				else if (menusystem.galaxy[menusystem.selected_galaxy].status == 2){
	    	  					menusystem.selected_mission = -1;
	    	  					if(sound)SoundManager.playSound(24,1);
	    	  					menu = 28;
	    	  					galaxybg_update = true;
	    	  				}
	    	  			}
	    	  		}
	  				menusystem.galaxy_bt_pressed = false;

	    	  		//if(menusystem.back_pressed) {
	    	  		/*
	  				if(scaleX > menusystem.back_bt[0] && scaleY > menusystem.back_bt[1] && scaleX < menusystem.back_bt[0]+menusystem.back_bt[2] && scaleY < menusystem.back_bt[1]+menusystem.back_bt[3]) {
	    	  			menusystem.back_pressed = false;
	    		  		if (menusystem.selected_galaxy >= 0) {
	    		  			menusystem.selected_galaxy = -1;
	    		  			if(sound)SoundManager.playSound(21,1);
	    		  		} else {
	    		  			if(sound)SoundManager.playSound(25,1);
	    		  			menu = 1;
	    		  		}
	    	  		}
	    	  		*/
	    	  		//}
	  				
	    	  		if(menusystem.btsignin_pressed) {
		    	  		if(!signedin && scaleX > menusystem.btsignin_pos[0] && scaleY > menusystem.btsignin_pos[1] && scaleX < menusystem.btsignin_pos[0]+menusystem.btsignin_pos[2] && scaleY < menusystem.btsignin_pos[1]+menusystem.btsignin_pos[3]) {
		    	  			if(sound)SoundManager.playSound(21,1);
		    	            if (!gameHelper.getApiClient().isConnected()) {
		    	            	try {
		    	            		login_process = 0;
		    	            		gameHelper.beginUserInitiatedSignIn(); 
		    	            	} catch (Exception e) { e.printStackTrace(); }
		    	            }	        	  			
		    	  		}
	    	  		} 
	    	  		if(menusystem.btleaderboard_pressed) {
		    	  		if(scaleX > menusystem.btleaderboard_pos[0] && scaleY > menusystem.btleaderboard_pos[1] && scaleX < menusystem.btleaderboard_pos[0]+menusystem.btleaderboard_pos[2] && scaleY < menusystem.btleaderboard_pos[1]+menusystem.btleaderboard_pos[3]) {
		    	  			if(sound)SoundManager.playSound(21,1);
		    	            if (gameHelper.getApiClient().isConnected()) {
		    	            	Log.i("Google Play Services", "gameHelper is Connected.");
		    	                // unlock the "Trivial Victory" achievement.
		    	            	try {
		    	            		((Activity) context).startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), LEADERBOARD_ID), RC_GAME_REQUEST);
		    	            	} catch (Exception e) { e.printStackTrace(); }
		    	            }  else {
		    	            	Log.i("Google Play Services", "gameHelper is NOT Connected. Login required.");
		    	            	try {
		    	            		login_process = 2;
		    	            		gameHelper.beginUserInitiatedSignIn(); 
		    	            	} catch (Exception e) { e.printStackTrace(); }
		    	            }
		    	  		}
	    	  		}
	    	  		if(menusystem.btachievements_pressed) {
		    	  		if(scaleX > menusystem.btachievements_pos[0] && scaleY > menusystem.btachievements_pos[1] && scaleX < menusystem.btachievements_pos[0]+menusystem.btachievements_pos[2] && scaleY < menusystem.btachievements_pos[1]+menusystem.btachievements_pos[3]) {
		    	  			if(sound)SoundManager.playSound(21,1);
	    	  				//baseGameActivity.showAlert("Victory");
		    	            if (gameHelper.getApiClient().isConnected()) {
		    	            	Log.i("Google Play Services", "gameHelper is Connected.");
		    	                // unlock the "Trivial Victory" achievement.
		    	            	try {
		    	            		((Activity) context).startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), RC_GAME_REQUEST);
		    	            	} catch (Exception e) { e.printStackTrace(); }
		    	            }  else {
		    	            	Log.i("Google Play Services", "gameHelper is NOT Connected. Login required.");
		    	            	try {
		    	            		login_process = 1;
		    	            		gameHelper.beginUserInitiatedSignIn(); 
		    	            	} catch (Exception e) { e.printStackTrace(); }
		    	            }
		    	            achievement_anim = false;
		    	  		}
	    	  		}
	    	  		menusystem.btsignin_pressed = false;
	    	  		menusystem.btleaderboard_pressed = false;
	    	  		menusystem.btachievements_pressed = false;

	    	  		break;
	    	  		
	    	  	case 3: 
	    	  		
	    	  		break;
	    	  	
	    	  	case 4:
	    	  		/*
	    	  		if(scaleX > menusystem.back1_bt[0] && scaleY > menusystem.back1_bt[1] && scaleX < menusystem.back1_bt[0]+menusystem.back1_bt[2] && scaleY < menusystem.back1_bt[1]+menusystem.back1_bt[3]) {
	    	  			menusystem.back_pressed = false;
	    	  			if(sound)SoundManager.playSound(22,1);
	    		  		menu = 1;
	    	  		} else */if (menusystem.options_sound_pressed) {
	    	  			if (scaleX > menusystem.options_sound_bt[0] && scaleX < menusystem.options_sound_bt[0]+menusystem.options_sound_bt[2] && 
	    	  				scaleY > menusystem.options_sound_bt[1] && scaleY < menusystem.options_sound_bt[1]+menusystem.options_sound_bt[3])
	    	  			{
	    	  				sound = !sound;
	    	  				
	    	  				if (sound && musicvolume > 0) {
	    	  					music = true;
	    	  					if (!mediaplayer.isPlaying()) {
	    	  						media_load(media_list[0],true);
	    	  						//mediaplayer.start();
	    	  					} else {
	    	  						mediaplayer.setVolume(musicvolume, musicvolume);
	    	  					}
	    	  				}
	    	  				else {
	    	  					music = false;
	    	  					if (mediaplayer.isPlaying()) { mediaplayer.stop(); }
	    	  				}
	    	  				
	    	  				pref[0].edit().putBoolean("Sound", sound).commit();
	    	  				pref[0].edit().putBoolean("Music", music).commit();
	    	  			}
	    	  		} else if (menusystem.options_music_pressed) {
	    	  			if (scaleX > menusystem.options_music_bt[0] && scaleX < menusystem.options_music_bt[0]+menusystem.options_music_bt[2] && 
	    	  				scaleY > menusystem.options_music_bt[1] && scaleY < menusystem.options_music_bt[1]+menusystem.options_music_bt[3])
	    	  			{
	    	  				if (musicvolume < 0.1f) {
	    	  					music = false;
	    	  					if (mediaplayer.isPlaying()) { mediaplayer.stop(); }
	    	  				} else {
	    	  					music = true;
	    	  					if (!mediaplayer.isPlaying()) {
	    	  						media_load(media_list[0],true);
	    	  						//mediaplayer.start();
	    	  					} else {
	    	  						mediaplayer.setVolume(musicvolume, musicvolume);
	    	  					}
	    	  				}
	    	  				pref[0].edit().putBoolean("Music", music).commit();
	    	  				pref[0].edit().putFloat("MusicVol", musicvolume).commit();
	    	  			}
	    	  		} else if (menusystem.options_vibration_pressed) {
	    	  			if (scaleX > menusystem.options_vibration_bt[0] && scaleX < menusystem.options_vibration_bt[0]+menusystem.options_vibration_bt[2] && 
	    	  				scaleY > menusystem.options_vibration_bt[1] && scaleY < menusystem.options_vibration_bt[1]+menusystem.options_vibration_bt[3])
	    	  			{
	    	  				
	    	  				
	    	  				vibration = !vibration;
	    	  				if (vibration) {
	    	  				vibrator.vibrate(vibration_pattern[1]);
	    	  				}
	    	  				
	    	  				pref[0].edit().putBoolean("Vibration", vibration).commit();
	    	  			}
	    	  		} else if (menusystem.options_keepscreen_pressed) {
	    	  			if (scaleX > menusystem.options_keepscreen_bt[0] && scaleX < menusystem.options_keepscreen_bt[0]+menusystem.options_keepscreen_bt[2] && 
	    	  				scaleY > menusystem.options_keepscreen_bt[1] && scaleY < menusystem.options_keepscreen_bt[1]+menusystem.options_keepscreen_bt[3])
	    	  			{
	    	  				keepscreen = !keepscreen;
	    	  				if (keepscreen) handler.sendEmptyMessage(3);
	    	  				else handler.sendEmptyMessage(4);
	    	  				pref[0].edit().putBoolean("Keepscreen", keepscreen).commit();
	    	  			}
	    	  		}
	    	  		menusystem.options_music_pressed = false;
    	  			menusystem.options_sound_pressed = false;
    	  			menusystem.options_vibration_pressed = false;
    	  			menusystem.options_keepscreen_pressed = false;
	    	  		break;

	    	  	case 502 : 

	    	  		break;
	    	  		
	    	  	case 501 : 
	    	  		if(menusystem.reset_yes_pressed) {
	    	  			if(scaleX > menusystem.quit_bt_yes[0] && scaleY > menusystem.quit_bt_yes[1] && scaleX < menusystem.quit_bt_yes[0]+menusystem.quit_bt_yes[2] && scaleY < menusystem.quit_bt_yes[1]+menusystem.quit_bt_yes[3]) {
	    	  				loading = true;
	    	  				pref[profile_reset].edit().clear().commit();
	    	  				if (current_profile == profile_reset) {
	    	  					allUpgrades.reset();
	    	  					menusystem.reset();
	    	  					Load_Profile();
	    	  					Update_Ship_Properties();
	    	  				}
	    	  				for (int k=0;k<3;k++) {
    	  			  			profile_ship[k] = pref[k+1].getInt("SpaceShip_Type", 0);
    	  			  			profile_ly[k] = pref[k+1].getLong("LY", 10000);
    	  			  			profile_galaxies[k] = pref[k+1].getInt("Galaxy_Completed", 0);
    	  			  			profile_missions[k] = pref[k+1].getInt("Mission_Completed", 0);
    	  			  			profile_highscore[k] = pref[k+1].getLong("Mission_Totalscore", 0);
    	  			  		}
	    	  				profile_reset = 0;
	    	  				menu = 5;
	    	  		  		loading = false;
	    	  			}
	    	  			menusystem.reset_yes_pressed = false;
	    	  		} else if(menusystem.reset_no_pressed) {
	    	  			if(scaleX > menusystem.quit_bt_no[0] && scaleY > menusystem.quit_bt_no[1] && scaleX < menusystem.quit_bt_no[0]+menusystem.quit_bt_no[2] && scaleY < menusystem.quit_bt_no[1]+menusystem.quit_bt_no[3]) {
	    	  				if(sound)SoundManager.playSound(22,1);
	    	  				profile_reset = 0;
	    	  				menu = 5;
	    	  		  		loading = false;
	    	  			}
	    	  			menusystem.reset_no_pressed = false;
	    	  		}
	    	  		
	    	  		break;
	    	  		
	    	  	case 5: 

	    	  			for (int i=0;i<3;i++) {
	    	  				if(scaleX > menusystem.profile_reset_bt[0] && scaleY > menusystem.profile_reset_bt[1]+i*hd*200 && scaleX < menusystem.profile_reset_bt[0]+menusystem.profile_reset_bt[2] && scaleY < menusystem.profile_reset_bt[1]+menusystem.profile_reset_bt[3]+i*hd*200) { 
		       	  				if (menusystem.profile_reset_pressed[i]) {
		       	  					profile_reset = i+1;
		       	  					if(sound)SoundManager.playSound(21,1);
		       	  					menu = 501;
		       	  					break;
		       	  				}
		       	  			} else 
	    	  				if(scaleX > menusystem.profile_select_bt[0] && scaleY > menusystem.profile_select_bt[1]+i*hd*200 && scaleX < menusystem.profile_select_bt[0]+menusystem.profile_select_bt[2] && scaleY < menusystem.profile_select_bt[1]+menusystem.profile_select_bt[3]+i*hd*200) { 
	    	  					if (menusystem.profile_select_pressed[i]) {
	    	  						if (current_profile != i+1) {
	    	  							loading = true;
	    	  							current_profile = i+1;
	    	  							allUpgrades.reset();
	    	  							menusystem.reset();
	    	  							Load_Profile();
	    								// try to restore transactions on profileselect
	    								//if (isGoogle) BillingHelper.restoreTransactionInformation(BillingSecurity.generateNonce()); 
	    	  							Update_Ship_Properties();
	    	  							pref[0].edit().putInt("Current_Profile", current_profile).commit();
	    	  						} 
	    	  						//menu = 1;
	    	  						loading = false;
	    	  					}
	    	  				}
	    	  			}  
	       	  			 
	    	  		//}
	    	  		menusystem.profile_select_pressed[0] = false;
	    	  		menusystem.profile_select_pressed[1] = false;
	    	  		menusystem.profile_select_pressed[2] = false;
	    	  		menusystem.profile_reset_pressed[0] = false;
	    	  		menusystem.profile_reset_pressed[1] = false;
	    	  		menusystem.profile_reset_pressed[2] = false;
	    	  		break;
	    	  	
	    	  	case 6: 

	    	  		break;
	    	  	
	    	  	case 701: 
	    	  		
	    	  		break;
	    	  	
	    	  	case 7:

	   	  			if(!fullgame && menusystem.unlock_fullgame_pressed) {
	   	  				if(scaleX > menusystem.unlock_fullgame_bt[0] && scaleY > menusystem.unlock_fullgame_bt[1] && scaleX < menusystem.unlock_fullgame_bt[0]+menusystem.unlock_fullgame_bt[2] && scaleY < menusystem.unlock_fullgame_bt[1]+menusystem.unlock_fullgame_bt[3]) { 
	   	  					menusystem.unlock_fullgame_pressed=false;
	   	  					if(isGoogle) {
		    	  				// GOOGLE IN-APP BILLING 3
		    	  				// ===================================================================================
		    	  		        Log.d(TAG_BILLING, "Launching purchase flow for purchasable.");
		    	  		        String payload = "";
		    	  		        mHelper.launchPurchaseFlow((Activity)context, SKU_FULLVERSION, RC_BILLING_REQUEST, mPurchaseFinishedListener, payload);
		    	  		        // =================================================================================== 
	   	  					}
							// amazon
   	  						else buy_amazon("sc2_fullversion");
	   	  					
	   	  				}
	   	  			} else 
	   	  			if(menusystem.unlock_facebook_pressed) {
	   	  				if(scaleX > menusystem.unlock_facebook_bt[0] && scaleY > menusystem.unlock_facebook_bt[1] && scaleX < menusystem.unlock_facebook_bt[0]+menusystem.unlock_facebook_bt[2] && scaleY < menusystem.unlock_facebook_bt[1]+menusystem.unlock_facebook_bt[3]) { 
	   	  					menusystem.unlock_facebook_pressed=false;
	   	  					Intent intentF = new Intent(Intent.ACTION_VIEW);
            				intentF.setData(Uri.parse("http://on.fb.me/1oLaG0J"));
            				context.startActivity(intentF); 
   	 	        			Log.i("SC","Facebook bt action up");
	   	  				}
	   	  			} 

	    	  		break;
	    	  	
	    	  	case 8: 

	    	  		// no mission selected
	    	  		if (menusystem.selected_mission < 0 && menusystem.mission_marker_pressed >=0) {
	    	  			for (int i=0;i<menusystem.galaxy[menusystem.selected_galaxy].total_missions;i++) {
	    	  				int mi = menusystem.galaxy[menusystem.selected_galaxy].mission_list[i];
	    	  				if(scaleX > menusystem.mission[mi].bt_xy[0] && scaleY > menusystem.mission[mi].bt_xy[1] && scaleX < menusystem.mission[mi].bt_xy[0]+(int)(hd*90) && scaleY < menusystem.mission[mi].bt_xy[1]+(int)(hd*90)) {
	    	  					if (menusystem.mission[mi].completed > 2) {
	    	  						menusystem.mission_bt_difficulty = 2; difficulty = 2;
	    	  					} else {
	    	  						menusystem.mission_bt_difficulty = 1; difficulty = 1;
	    	  					}
	    	  					menusystem.selected_mission = mi;
	    	  					
		    	  				if (menusystem.mission[menusystem.selected_mission].status == 2) {
		    	  					menusystem.dialogue_upgrade = true;
		    	  					menusystem.dialogue_mission = false;
		    	  					menusystem.dialogue_galaxy = false;
		    	  					menusystem.dialogue_game = false;
		    	  					//if(sound)SoundManager.playSound(25,1);
		    	    	  			menusystem.prev_menu = 8;
		    	  					menu = 9;
		    	  					break;
		    	  				} else {
		    	  					if(sound)SoundManager.playSound(21,1);
		    	  				}
		    	  				
		    	  				menusystem.mission_marker_pressed = -1;
		    	  				break;
	    	  				}
	    	  			}
	    	  			
	    	  		// mission already selected	
	    	  		} else {
	    	  			if(menusystem.mission_enter_pressed && scaleX > menusystem.mission_bt_enter[0] && scaleY > menusystem.mission_bt_enter[1] && scaleX < menusystem.mission_bt_enter[0]+menusystem.mission_bt_enter[2] && scaleY < menusystem.mission_bt_enter[1]+menusystem.mission_bt_enter[3]) {
	    	  				
	    	  				if (menusystem.mission[menusystem.selected_mission].fullgame && !fullgame) {
	    	  					menusystem.unlock_prev = 8;
	    	  					if(sound)SoundManager.playSound(21,1);
	    	  					menu = 7;
	    	  					menusystem.mission_enter_pressed = false;
	    	  				} else 
	    	  				if (menusystem.mission[menusystem.selected_mission].status == 1) {

	    	  					Start_Mission();
	    	  					menusystem.mission_enter_pressed = false;
	    	  					//SoundManager.playMutedSound();
	    	  				} 
	    	    	  		break;
	    	    	  		
	    	  			} else 
	    	  			if(menusystem.mission_cancel_pressed && scaleX > menusystem.mission_bt_cancel[0] && scaleY > menusystem.mission_bt_cancel[1] && scaleX < menusystem.mission_bt_cancel[0]+menusystem.mission_bt_enter[2] && scaleY < menusystem.mission_bt_enter[1]+menusystem.mission_bt_cancel[3]) {
	    	  					
	    	  				menusystem.selected_mission = -1;
	    	  				if(sound)SoundManager.playSound(22,1);
	    	  				menusystem.mission_cancel_pressed = false;
	    	  				break;
    	  				}
	    	  		}
	    	  		
	    	  		if(menusystem.btsignin_pressed) {
		    	  		if(!signedin && scaleX > menusystem.btsignin_pos[0] && scaleY > menusystem.btsignin_pos[1] && scaleX < menusystem.btsignin_pos[0]+menusystem.btsignin_pos[2] && scaleY < menusystem.btsignin_pos[1]+menusystem.btsignin_pos[3]) {
		    	  			if(sound)SoundManager.playSound(21,1);
		    	            if (!gameHelper.getApiClient().isConnected()) {
		    	            	try {
		    	            		login_process = 0;
		    	            		gameHelper.beginUserInitiatedSignIn(); 
		    	            	} catch (Exception e) { e.printStackTrace(); }
		    	            }	        	  			
		    	  		}
	    	  		} 
	    	  		if(menusystem.btleaderboard_pressed) {
		    	  		if(scaleX > menusystem.btleaderboard_pos[0] && scaleY > menusystem.btleaderboard_pos[1] && scaleX < menusystem.btleaderboard_pos[0]+menusystem.btleaderboard_pos[2] && scaleY < menusystem.btleaderboard_pos[1]+menusystem.btleaderboard_pos[3]) {
		    	  			if(sound)SoundManager.playSound(21,1);
		    	            if (gameHelper.getApiClient().isConnected()) {
		    	            	Log.i("Google Play Services", "gameHelper is Connected.");
		    	                // unlock the "Trivial Victory" achievement.
		    	            	try {
		    	            		((Activity) context).startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), LEADERBOARD_ID), RC_GAME_REQUEST);
		    	            	} catch (Exception e) { e.printStackTrace(); }
		    	            }  else {
		    	            	Log.i("Google Play Services", "gameHelper is NOT Connected. Login required.");
		    	            	try {
		    	            		login_process = 2;
		    	            		gameHelper.beginUserInitiatedSignIn(); 
		    	            	} catch (Exception e) { e.printStackTrace(); }
		    	            }
		    	  		}
	    	  		}
	    	  		if(menusystem.btachievements_pressed) {
		    	  		if(scaleX > menusystem.btachievements_pos[0] && scaleY > menusystem.btachievements_pos[1] && scaleX < menusystem.btachievements_pos[0]+menusystem.btachievements_pos[2] && scaleY < menusystem.btachievements_pos[1]+menusystem.btachievements_pos[3]) {
		    	  			if(sound)SoundManager.playSound(21,1);
	    	  				//baseGameActivity.showAlert("Victory");
		    	            if (gameHelper.getApiClient().isConnected()) {
		    	            	Log.i("Google Play Services", "gameHelper is Connected.");
		    	                // unlock the "Trivial Victory" achievement.
		    	            	try {
		    	            		((Activity) context).startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), RC_GAME_REQUEST);
		    	            	} catch (Exception e) { e.printStackTrace(); }
		    	            }  else {
		    	            	Log.i("Google Play Services", "gameHelper is NOT Connected. Login required.");
		    	            	try {
		    	            		login_process = 1;
		    	            		gameHelper.beginUserInitiatedSignIn(); 
		    	            	} catch (Exception e) { e.printStackTrace(); }
		    	            }
		    	            achievement_anim = false;
		    	  		}
	    	  		}
	    	  		menusystem.btsignin_pressed = false;
	    	  		menusystem.btleaderboard_pressed = false;
	    	  		menusystem.btachievements_pressed = false;
	    	  		break;

	    	  	case 91:
	    	  		if(menusystem.quit_yes_pressed) {
	    	  			if(scaleX > menusystem.quit_bt_yes[0] && scaleY > menusystem.quit_bt_yes[1] && scaleX < menusystem.quit_bt_yes[0]+menusystem.quit_bt_yes[2] && scaleY < menusystem.quit_bt_yes[1]+menusystem.quit_bt_yes[3]) {
	   	  					if(isGoogle) {
		    	  				// GOOGLE IN-APP BILLING 3
		    	  				// ===================================================================================
		    	  		        Log.d(TAG_BILLING, "Launching purchase flow for purchasable.");
		    	  		        String payload = "";
		    	  		        mHelper.launchPurchaseFlow((Activity)context, buynow_item, RC_BILLING_REQUEST, mPurchaseFinishedListener, payload);
		    	  		        // =================================================================================== 
	   	  					}
	    	  				buynow = false;
	    	  				menu = 9;
	    	  			}
	    	  			menusystem.quit_yes_pressed = false;
	    	  		} else if(menusystem.quit_no_pressed) {
	    	  			if(scaleX > menusystem.quit_bt_no[0] && scaleY > menusystem.quit_bt_no[1] && scaleX < menusystem.quit_bt_no[0]+menusystem.quit_bt_no[2] && scaleY < menusystem.quit_bt_no[1]+menusystem.quit_bt_no[3]) {
	    	  				if(sound)SoundManager.playSound(22,1);
	    	  				buynow = false;
	    	  				menu = 9;
	    	  			}
	    	  			menusystem.quit_no_pressed = false;
	    	  		}
	    	  		
	    	  		break;

	    	  	case 9:
	    	  		
	    	  		ontouch_up_case_9();
	    	  		break;

	    	  	case 102:
	    	  		
	    	  		if(menusystem.fbshare_pressed) {
						if(scaleX > menusystem.fbshare_pos[0] && scaleY > menusystem.fbshare_pos[1] && scaleX < menusystem.fbshare_pos[0] + menusystem.fbshare_pos[2] && scaleY < menusystem.fbshare_pos[1] + menusystem.fbshare_pos[3]) { 
	        	  			loginFacebook(); 
						}
	    	  		}
	    	  		else if(menusystem.fblike_pressed) {
						if(scaleX > menusystem.fblike_pos[0] && scaleY > menusystem.fblike_pos[1] && scaleX < menusystem.fblike_pos[0] + menusystem.fblike_pos[2] && scaleY < menusystem.fblike_pos[1] + menusystem.fblike_pos[3]) {
							if (!liked) {
		   	  					Intent intentFB = new Intent(Intent.ACTION_VIEW);
	            				intentFB.setData(Uri.parse("http://on.fb.me/1oLaG0J"));
	            				context.startActivity(intentFB);
							} else {
        	  					Intent intent8 = new Intent(Intent.ACTION_VIEW);
        	  					if (hd > 1) {
	        	  					if (isGoogle) intent8.setData(Uri.parse("market://details?id=com.tubigames.galaxy.shooter.hd"));
	        	  					else intent8.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.galaxy.shooter.hd"));
        	  					} else {
	        	  					if (isGoogle) intent8.setData(Uri.parse("market://details?id=com.tubigames.galaxy.shooter"));
	        	  					else intent8.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.galaxy.shooter"));
        	  					}
                				context.startActivity(intent8);
							}
						}
	    	  		}
					else if(menusystem.continue_pressed) {
						End_Mission(endmission_success);
					}
					
					menusystem.fbshare_pressed = false;
					menusystem.fblike_pressed = false;
					menusystem.continue_pressed = false;
					break;
	    	  		
	    	  	case 10: 
	    	  		
	            	spaceship_bullet_launcher = false;

	    	  		case10_up(scaleX, scaleY);
	            	
	    	  		break;
	    	  		
	    	  	case 108 : 
	    	  		if(menusystem.quit_yes_pressed) {
	    	  			if(scaleX > menusystem.quit_bt_yes[0] && scaleY > menusystem.quit_bt_yes[1] && scaleX < menusystem.quit_bt_yes[0]+menusystem.quit_bt_yes[2] && scaleY < menusystem.quit_bt_yes[1]+menusystem.quit_bt_yes[3]) {
	    	  				menusystem.selected_mission = -1;
	    	  				menusystem.dialogue_mission = true;
	    	  				menusystem.dialogue_galaxy = false;
	    	  				menusystem.dialogue_upgrade = false;
	    	  				menusystem.dialogue_game = false;
	    	  				
	    	  				if (music) {
	    	  					if (mediaplayer.isPlaying()) {
	    	  						mediaplayer.stop();
    		            		}
	    	  					media_load(media_list[0],false);
	    	  					media_update = true;
	    	  				//	mediaplayer.start();
	    	  				}

    		            	if (instantshield) spaceship.shield_max -= 10; if (spaceship.shield > spaceship.shield_max) spaceship.shield = spaceship.shield_max;
	    	  				menusystem_update = true;
	    	  				adtimer = 0;
	    	  				
	    	  				if (extraturret >= 0) {
	    	  					extraturret = -1;
	    	  					spaceship.turret[spaceship.turret_active] = prevturret;
	    	  					prevturret = -1;
	    	  				}
	    	  				if (extramodifier >= 0) {
	    	  					extramodifier = -1;
	    	  					spaceship.modifier[spaceship.modifier_active] = prevmodifier;
	    	  					prevmodifier = -1;
	    	  				}
	    	  				
    	  					menu = 8;
    	  					
    	  					// LEADBOLT
    	  					//if (!fullgame && lb_adshown) { lb_adshown = false; if (lb_ad == 0) handler.sendEmptyMessage(5); lb_ad++; if (lb_ad > 0) lb_ad = 0; pref[0].edit().putInt("LB_AD", lb_ad).commit(); }
    	  					if (lb_adshown && !fullgame) { if (lb_ad <= 4) handler.sendEmptyMessage(5); lb_ad++; if (lb_ad > 4) lb_adshown = false; }

	    	  			}
	    	  			menusystem.quit_yes_pressed = false;
	    	  		} else if(menusystem.quit_no_pressed) {
	    	  			if(scaleX > menusystem.quit_bt_no[0] && scaleY > menusystem.quit_bt_no[1] && scaleX < menusystem.quit_bt_no[0]+menusystem.quit_bt_no[2] && scaleY < menusystem.quit_bt_no[1]+menusystem.quit_bt_no[3]) {
	    	  				pause = false;
	    	  				PauseTimer_Update();
	    	  				if(sound)SoundManager.playSound(22,1);
	    	  				menu = 10;
	    	  			}
	    	  			menusystem.quit_no_pressed = false;
	    	  		}
	    	  		
	    	  		break;
	    	  	
	    	  	default :
	    	  		
	    	  		break;
	    	  		
	    	  }	            	
        	
	    	
        	}	      
        	catch (Exception e) 
        	{
        		e.printStackTrace();
        	}
  
        }    	// if ! loading

			//Remember the values
	        oldX = x;
	        oldY = y;

			break;
		} // case action up

		case MotionEvent.ACTION_POINTER_DOWN: {
	        // Extract the index of the pointer that left the touch sensor
	        final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) 
	                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
			// Find the index of the active pointer and fetch its position
	        final int pointerId = event.getPointerId(pointerIndex);

	        final float bx = event.getX(pointerId);
	    	final float by = event.getY(pointerId);
	    	float bscaleX = bx * dimensionX / this.getWidth();
	    	float bscaleY = by * dimensionY / this.getHeight();
	  		case10_down(bscaleX, bscaleY);
	  		case10_up(bscaleX, bscaleY);
		}
		case MotionEvent.ACTION_POINTER_UP: {
	        // Extract the index of the pointer that left the touch sensor
	        final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) 
	                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
	        final int pointerId = event.getPointerId(pointerIndex);
	        if (pointerId == mActivePointerId) {
	            // This was our active pointer going up. Choose a new
	            // active pointer and adjust accordingly.
	            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
	            oldX = event.getX(newPointerIndex);
	            oldY = event.getY(newPointerIndex);
	            mActivePointerId = event.getPointerId(newPointerIndex);
	        }
	        
	        final float bx = event.getX(pointerId);
	    	final float by = event.getY(pointerId);
	    	float bscaleX = bx * dimensionX / this.getWidth();
	    	float bscaleY = by * dimensionY / this.getHeight();
	    	//case10_up(bscaleX, bscaleY);

	        break;
	    }
		
		} // switch action
		
	
	// try to catch motionevent error
	} catch (Exception e) { e.printStackTrace(); }
		
		//We handled the event
		return true;

	}
	
	public void case10_down(float scaleX, float scaleY) {
			/*if(scaleX > bottommenu_button1[0] && scaleY > bottommenu_button1[1] && scaleX < bottommenu_button1[2] && scaleY < bottommenu_button1[3]) {
	  			if(sound)SoundManager.playSound(1,1);
	    		bmenu_button_down[0] = true; bmenu_button_down_any = true;
	    	}
	    	else if(scaleX > bottommenu_button2[0] && scaleY > bottommenu_button2[1] && scaleX < bottommenu_button2[2] && scaleY < bottommenu_button2[3]) {
	    		if(sound)SoundManager.playSound(1,1);
	    		bmenu_button_down[1] = true; bmenu_button_down_any = true;
	    	}
	    	else */if(scaleX > bottommenu_button3[0] && scaleY > bottommenu_button3[1] && scaleX < bottommenu_button3[2] && scaleY < bottommenu_button3[3]) { 
	    		//tempheck_1 = true;
	    		if (spaceship.special_cd[spaceship.special_order[0]]) {
	    			bmenu_button_down[2] = true; bmenu_button_down_any = true;
	    		}
	    	}
	    	else if(scaleX > bottommenu_button4[0] && scaleY > bottommenu_button4[1] && scaleX < bottommenu_button4[2] && scaleY < bottommenu_button4[3]) { 
	    		//tempheck_2 = true;
	    		if (spaceship.special_cd[spaceship.special_order[1]]) {
	    			bmenu_button_down[3] = true; bmenu_button_down_any = true;
	    		}
	    	}
	    	else if(scaleX > bottommenu_button5[0] && scaleY > bottommenu_button5[1] && scaleX < bottommenu_button5[2] && scaleY < bottommenu_button5[3]) { 
	    		//tempheck_3 = true;
	    		if (spaceship.special_cd[spaceship.special_order[2]]) {
	    			bmenu_button_down[4] = true; bmenu_button_down_any = true;
	    		}
	    	}
	    	else if(scaleX > bottommenu_button6[0] && scaleY > bottommenu_button6[1] && scaleX < bottommenu_button6[2] && scaleY < bottommenu_button6[3]) { 
	    		//tempheck_4 = true;
	    		if (spaceship.special_cd[spaceship.special_order[3]]) {
	    			bmenu_button_down[5] = true; bmenu_button_down_any = true;
	    		}
	    	} else {
	    		spaceship_bullet_launcher = true;
	    	}
	}
	
	public void case10_up(float scaleX, float scaleY) {
    	if ( bmenu_button_down_any) {
    		/*if (bmenu_button_down[0]) {
    			if(scaleX > bottommenu_button1[0] && scaleY > bottommenu_button1[1] && scaleX < bottommenu_button1[2] && scaleY < bottommenu_button1[3]) { 
    				bmenu_button_down[0] = false;
    				bmenu_button_down_any = false;
    				
    				for (int i=0;i<spaceship.turret_num;i++) {
    					spaceship.turret_active++;
    					if (spaceship.turret_active >= spaceship.turret_num) { spaceship.turret_active = 0; }
    					if (spaceship.turret[spaceship.turret_active] >= 0) { break; }
    				}
    			}
    		}
    		else if (bmenu_button_down[1]) {
       			if(scaleX > bottommenu_button2[0] && scaleY > bottommenu_button2[1] && scaleX < bottommenu_button2[2] && scaleY < bottommenu_button2[3]) { 
    				bmenu_button_down[1] = false;
    				bmenu_button_down_any = false;
    				
    				for (int i=0;i<spaceship.modifier_num;i++) {
    					spaceship.modifier_active++;
    					if (spaceship.modifier_active >= spaceship.modifier_num) { spaceship.modifier_active = 0; }
    					if (spaceship.modifier[spaceship.modifier_active] >= 0) { break; }
    				}
    			}
    		}
    		else */if (bmenu_button_down[2]) {
            	if(scaleX > bottommenu_button3[0] && scaleY > bottommenu_button3[1] && scaleX < bottommenu_button3[2] && scaleY < bottommenu_button3[3]) { 
    				bmenu_button_down[2] = false;
    				bmenu_button_down_any = false;
    				if (spaceship.special[spaceship.special_order[0]] >=0 && spaceship.special_cd[spaceship.special_order[0]] && spaceship_special_launch <0 ) {
    					spaceship_special_launch = spaceship.special[spaceship.special_order[0]];
    					spaceship.special_cd_timer[spaceship.special_order[0]] = time_current;
    					spaceship.special_cd[spaceship.special_order[0]] = false;
    				}
    			}
    		}
    		else if (bmenu_button_down[3]) {
            	if(scaleX > bottommenu_button4[0] && scaleY > bottommenu_button4[1] && scaleX < bottommenu_button4[2] && scaleY < bottommenu_button4[3]) { 
    				bmenu_button_down[3] = false;
    				bmenu_button_down_any = false;
    				if (spaceship.special[spaceship.special_order[1]] >=0 && spaceship.special_cd[spaceship.special_order[1]] && spaceship_special_launch <0 ) {
    					spaceship_special_launch = spaceship.special[spaceship.special_order[1]];
    					spaceship.special_cd_timer[spaceship.special_order[1]] = time_current;
    					spaceship.special_cd[spaceship.special_order[1]] = false;
    				}
    			}
    		}
    		else if (bmenu_button_down[4]) {
            	if(scaleX > bottommenu_button5[0] && scaleY > bottommenu_button5[1] && scaleX < bottommenu_button5[2] && scaleY < bottommenu_button5[3]) { 
    				bmenu_button_down[4] = false;
    				bmenu_button_down_any = false;
    				if (spaceship.special[spaceship.special_order[2]] >=0 && spaceship.special_cd[spaceship.special_order[2]] && spaceship_special_launch <0 ) {
    					spaceship_special_launch = spaceship.special[spaceship.special_order[2]];
    					spaceship.special_cd_timer[spaceship.special_order[2]] = time_current;
    					spaceship.special_cd[spaceship.special_order[2]] = false;
    				}
    			}
    		}
    		else if (bmenu_button_down[5]) {
            	if(scaleX > bottommenu_button6[0] && scaleY > bottommenu_button6[1] && scaleX < bottommenu_button6[2] && scaleY < bottommenu_button6[3]) { 
    				bmenu_button_down[5] = false;
    				bmenu_button_down_any = false;
    				if (spaceship.special[spaceship.special_order[3]] >=0 && spaceship.special_cd[spaceship.special_order[3]] && spaceship_special_launch <0 ) {
    					spaceship_special_launch = spaceship.special[spaceship.special_order[3]];
    					spaceship.special_cd_timer[spaceship.special_order[3]] = time_current;
    					spaceship.special_cd[spaceship.special_order[3]] = false;
    				}
    			}
    		}
    		else {
    			for (int i=0; i<6; i++) { bmenu_button_down[i] = false; }
    			bmenu_button_down_any = false;
    		}
    	}

	}

	// Mission Start script
	// Mission Start script
	// Mission Start script
	// Mission Start script
	// Mission Start script
	// Mission Start script
	public void Start_Mission() {
		
			freeze = true;
			pause = false;

			npc.clear();
			spaceship_bullet.clear();
			npc_bullet.clear();
			pickup.clear();
			remove_list_npc.clear();
			remove_list_spaceship_bullet.clear();
			remove_list_npc_bullet.clear();
			remove_list_pickup.clear();
			spaceship.isalive = true;
			spaceship.explosion_loop = 0;
			spaceship.x = (int)(hd*240) - allUpgrades.ship[spaceship.type].width / 2;
			spaceship.y = (int)(hd*700) - allUpgrades.ship[spaceship.type].height;
			spaceship_bullet_launch = false;
			wing_bullet_launch = false;
			
			spaceship.hp = spaceship.hp_max;
			spaceship.shield = spaceship.shield_max;

			mission = menusystem.mission[menusystem.selected_mission].mission_to_load;
			mission_script = 0;
			mission_script_wait = true;
			mission_script_delay = true;
			mission_update = true;
			
			// increase mission power by x% after every completed mission in the current galaxy
			int power = menusystem.mission[menusystem.selected_mission].power;
			float szorzo = 1.0f;
			for (int i=0; i<menusystem.galaxy[menusystem.selected_galaxy].total_missions; i++) {
				if (menusystem.mission[menusystem.galaxy[menusystem.selected_galaxy].mission_list[i]].completed >= 2) {
					szorzo += 0.05f;
				}
			}
			power *= szorzo;
			
			allMissions.load(context, 
					menusystem.mission[menusystem.selected_mission].npc_group, 
					menusystem.mission[menusystem.selected_mission].env_group,
					menusystem.mission[menusystem.selected_mission].background,
					power,
					menusystem.mission[menusystem.selected_mission].boss,
					allEntities);
			if (menusystem.mission[menusystem.selected_mission].completed == 0) {
				menusystem.mission[menusystem.selected_mission].completed = 1;
				Save_Profile(3);
			}
			
			// add laser 1 and mod I if nothing else equipped
			boolean noturret = true;
			for (int i=0;i<spaceship.turret_num;i++) {
				if (spaceship.turret[i] >= 0) noturret = false;
			}
			if (noturret) {
				spaceship.turret[0] = 0;
				spaceship.turret_active = 0;
			}
			
			boolean nomodifier = true;
			for (int i=0;i<spaceship.modifier_num;i++) {
				if (spaceship.modifier[i] >= 0) nomodifier = false;
			}
			if (nomodifier) { 
				spaceship.modifier[0] = 0;
				spaceship.modifier_active = 0;
			}
			
			for (int i=0;i<spaceship.special_num;i++) {
				if (spaceship.special[i] >= 0) spaceship.special_cd[i] = true;
				else spaceship.special_cd[i] = false;
			}
			
			missionLY = 0;
			
			spaceship_bullet_timer = 0;
			spaceship_bullet_launcher = false;
			spaceship_bullet_launch = false;
			wing_bullet_launch = false;
			//wing_bullet_timer = 0;
			//freeze = false;
			invincible = false;
			autofire = false;
			gravity = false;
			homing = false;
			cone = false;
			homing_bullet.active = false;
			booster = false;
			booster_value = 1f;
			instantshield = false;
			mining = false;
			crazy = false;
			crazy_bullet.active = false;
			spaceship.freeze = false;
			
			npc_escaped = 0;
			
			//media_num = menusystem.mission[menusystem.selected_mission].music;
			//media_update = true;
			//Log.i("MediaPlayer", "media update set to true");
			
			/*
	        if (OpenGLRenderer.isGoogle && Main.tapjoy && tapjoy_actions[0]) {
	        	try { 
	        		TapjoyConnect.getTapjoyConnectInstance().actionComplete("4a7cb329-23b4-4cb2-a16f-b662a260c24d");
	        	} catch (Exception e) { e.printStackTrace(); } 
        		tapjoy_actions[0] = false;
	        }
	        */

			
			if (music) {
				
				if (mediaplayer.isPlaying()) {
					mediaplayer.stop();
				}
				
				if (menusystem.mission[menusystem.selected_mission].music > 0) {
					media_load(media_list[menusystem.mission[menusystem.selected_mission].music],false);
					media_update = true;
					// 	delay music start by 3 sec
					//this.postDelayed(new Runnable() { 
					//	public void run() {	mediaplayer.start(); }
					//}, 500);
					//	mediaplayer.start();
				}
			}
			
			
			entities_update = true; 
			if(sound)SoundManager.playSound(25,1);
			looptime = GetTickCount(); // for game loop per sec text

			endmission_state = 0;
			endmission_timer = 0;
			
			next_game_tick = 0; // game loop parameter
			adtimer = 0;

			menu = 10;

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	//Handle the back button
	if (keyCode == KeyEvent.KEYCODE_BACK) {
		
		Log.i("Starship Commander", "KEYCODE BACK, MENU: " + Integer.toString(menu));

  	  switch (menu) {
    	
	  	case 0:
	  		return true;

	  	case 711:
	  		return true;
	  	
	  	case 101:
	  		if(sound)SoundManager.playSound(22,1);
	  		quit = false;
	  		menu = 1;
	  		like_tap = 0;
	  		return true;

	  	case 105:
	  		if(sound)SoundManager.playSound(22,1);
	  		menu = 1;
	  		like_tap = 0;
	  		return true;

	  	case 1: 
	  		if(sound)SoundManager.playSound(21,1);
	  		
	  		SharedPreferences prefs_apprater = context.getSharedPreferences("apprater", 0);
	        if (!prefs_apprater.getBoolean("dontshowagain_moregames", false)) {
	        	Log.i("SC2", "Show more games dialog");
	        	AppRater.launch_MoreGamesDialog(context, false, (int)scrWidth, (int)scrHeight );
	        	//AppRater.launch_MoreGamesDialog(context, false, (int)scrWidth, (int)scrHeight );
	        	quit = true;
		  		menu = 101;
	        } else {
	        	Log.i("SC2", "Openglrenderer returning false");
		  		quit = true;
		  		menu = 101;
	        }
	  		return true;
	  	
	  	case 2: 
	  		if (menusystem.selected_galaxy >= 0) {
	  			menusystem.selected_galaxy = -1;
	  			if(sound)SoundManager.playSound(22,1);
	  		} else {
	  			if(sound)SoundManager.playSound(25,1);
	  			menu = 1;
	  			like_tap = 0;
	  		}
	  		return true;
	  	
	  	case 28: 
	  		
	  		return true;

	  	case 3: 
	  		
	  		return true;
	  	
	  	case 4: 
	  		if(sound)SoundManager.playSound(22,1);
	  		menu = 1;
	  		like_tap = 0;
	  		return true;
	  	
	  	case 502: 
	  		//if(sound)SoundManager.playSound(22,1);
	  		//loading = false;
	  		//menu = 5;
	  		return true;

	  	case 501: 
	  		if(sound)SoundManager.playSound(22,1);
	  		profile_reset = 0;
	  		loading = false;
	  		menu = 5;
	  		return true;
	  		
	  	case 5: 
	  		if(sound)SoundManager.playSound(22,1);
	  		menu = 1;
	  		like_tap = 0;
	  		return true;
	  	
	  	case 6: 
	  		if(sound)SoundManager.playSound(22,1);
	  		menu = 1;
	  		like_tap = 0;
	  		return true;
	  	
	  	case 701: 
	  		if(sound)SoundManager.playSound(22,1);
	  		menu = 7;
	  		return true;

	  	case 7: 
	  		if(sound)SoundManager.playSound(22,1);
	  		if (menusystem.unlock_prev >= 0) {
	  			menu = menusystem.unlock_prev;
	  		} else { menu = 1; like_tap = 0; }
	  		return true;
	  	
	  	case 8: 
	  		if (menusystem.selected_mission >= 0) {
	  			menusystem.selected_mission = -1;
	  			if(sound)SoundManager.playSound(22,1);
	  		} else {
	  			menusystem.dialogue_galaxy = true;
	  			menusystem.dialogue_mission = false;
				menusystem.dialogue_upgrade = false;
				menusystem.dialogue_game = false;
				if(sound)SoundManager.playSound(25,1);
				menusystem.parrow_total = 1000;
	  			menu = 2;
		  		menusystem.selected_galaxy = -1;
	  		}
	  		return true;
	  	
	  	case 91: 
	  		if(sound)SoundManager.playSound(22,1);
	  		buynow = false;
	  		menu = 9;
	  		return true;

	  	case 9: 
	  		if (menusystem.upgrade_selected) {
	  			for (int o=0; o<6; o++) menusystem.itemlist[o] = -2;
	  			if(sound)SoundManager.playSound(22,1);
	  			menusystem.upgrade_selected = false; 
	  			menusystem.upgrade_selected_weapon = -1;
	  			menusystem.upgrade_selected_modifier = -1;
	  			menusystem.upgrade_selected_special = -1;
	  			menusystem.upgrade_selected_upgrade = -1;
	  			menusystem.upgrade_selected_ship = -1;
	  			menusystem.upgrade_selected_purchasable = -1;
	  			menusystem.upgrade_page = 0;
	  			if (menusystem.prev_menu == 105) {
	  				menu = 105;
	  				menusystem.prev_menu = 8;
	  			}
	  		} else {
	  			menusystem.selected_mission = -1;
	  			menusystem.dialogue_mission = true;
				menusystem.dialogue_galaxy = false;
				menusystem.dialogue_upgrade = false;
				menusystem.dialogue_game = false;
				galaxybg_update = true;
				if(sound)SoundManager.playSound(25,1);
				menusystem_update = false;
				menu = 8;
				//AppRater.showEndDialog(context, null);
	  		}
	  		return true;
	  	
	  	case 10: 
	  		if(sound)SoundManager.playSound(21,1);
	  		pause = true;
	  		menusystem.pause_time = GetTickCount();
	  		menu = 108;
	  		return true;

	  	case 102:
	  		if (endmission_state >= 3) {
				End_Mission(endmission_success);
				menusystem.fbshare_pressed = false;
				menusystem.fblike_pressed = false;
				menusystem.continue_pressed = false;
	  		}
	  		return true;

	  	case 108 : 
	  		if(sound)SoundManager.playSound(22,1);
	  		pause = false;
	  		PauseTimer_Update();
	  		menu = 10;
	  		return true;
	  		
	  	default :
	  		
	  		return true;
	  		
	  }			

	}
	
	if (keyCode == KeyEvent.KEYCODE_HOME) {
		

		return true;
	}
	 
	return super.onKeyDown(keyCode, event);
	}

	public void PauseTimer_Update() {
		
		spaceship_bullet_launcher = false;
		
  		spaceship.shield_recharge_timer += GetTickCount() - menusystem.pause_time;
  		spaceship.repair_timer += GetTickCount() - menusystem.pause_time;
  		for (int i=0; i<spaceship.special_num;i++) {
  			if (!spaceship.special_cd[i] && spaceship.special[i] >= 0 && spaceship.special_cd_timer[i] >= 0) {
  				spaceship.special_cd_timer[i] += GetTickCount() - menusystem.pause_time;
  			}
  		}
  		for(Entity npc_: npc) {
  			npc_.bullet_timer += GetTickCount() - menusystem.pause_time;
  			npc_.recharge_timer += GetTickCount() - menusystem.pause_time;
  			npc_.disruptor_timer += GetTickCount() - menusystem.pause_time;
  		}
  		for(Pickup pickup_: pickup) {
  			pickup_.timer += GetTickCount() - menusystem.pause_time;
  			if (pickup_.floatingtext >=0) pickup_.floatingtext += GetTickCount() - menusystem.pause_time;
  		}
  		mission_script_timer += GetTickCount() - menusystem.pause_time;
  		
  		freeze_timer += GetTickCount() - menusystem.pause_time;
  		if (autofire_timer > 0) autofire_timer += GetTickCount() - menusystem.pause_time;
  		invincible_timer += GetTickCount() - menusystem.pause_time;
  		cone_timer += GetTickCount() - menusystem.pause_time;
  		gravity_timer += GetTickCount() - menusystem.pause_time;
  		spaceship_bullet_timer += GetTickCount() - menusystem.pause_time;
  		//wing_bullet_timer += GetTickCount() - menusystem.pause_time;
  		instantshield_timer += GetTickCount() - menusystem.pause_time;
  		//instantshield_recharge_timer += GetTickCount() - menusystem.pause_time;
  		booster_timer += GetTickCount() - menusystem.pause_time;
  		wings_timer += GetTickCount() - menusystem.pause_time;
  		mining_timer += GetTickCount() - menusystem.pause_time;
  		spaceship.freeze_timer += GetTickCount() - menusystem.pause_time;
  		
  		next_game_tick = GetTickCount();
	}
	
	@Override
	public void onPause() {

		//GLSurfaceView.
		
  	  switch (menu) {
    	
	  	case 0: 
	  	case 101:
	  	case 105: 
	  	case 1: 
	  	case 2: 
	  	case 28:
	  	case 3: 
	  	case 4: 
	  	case 501: 
	  	case 502: 
	  	case 5: 
	  	case 6: 
	  	case 701: 
	  	case 7: 
	  	case 711: 
	  	case 8: 
	  	case 91: 
	  	case 9: 
	  		if (music) {
	  			if (mediaplayer.isPlaying()) {
	  			mediaplayer.pause();
	  			}
	  		}
	  		break;
	  		
	  	case 108: 
	  	case 102: 
	  		if (music) {
	  			if (mediaplayer.isPlaying()) {
	  			mediaplayer.pause();
	  			}
	  		}
	  		break;

	  	case 10: 
	  		pause = true;
	  		menusystem.pause_time = GetTickCount();
	  		if (music) {
	  			if (mediaplayer.isPlaying()) {
	  			mediaplayer.pause();
	  			}
	  		}
	  		break;
	  	
	  	default :
	  		
	  		break;
	  		
	  }			
		System.gc();
	}

	@Override
	public void onResume() {

		//Request focus, otherwise buttons won't react
		this.requestFocus();
		this.setFocusableInTouchMode(true);

		network = isNetworkAvailable(context);
		
		// tapjoy
				// Retrieve the virtual currency amount from the server.
				if (Main.tapjoy && tapjoy_resume) {
		        	try { 
		        		Log.i("Tapjoy", "Resume Game");
		        		tapjoy_resume = false;
		        		tapjoy_earned = true;
		        		TapjoyConnect.getTapjoyConnectInstance().getTapPoints(this);
		        		//TapjoyConnect.getTapjoyConnectInstance().spendTapPoints(tap_points, this);
		        	} catch (Exception e) { e.printStackTrace(); } 
				}
		
		// amazon stuff
		if (!isGoogle && amazon_initialized) {
			try {
				PurchasingManager.initiateGetUserIdRequest();
			}	      
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
  	  switch (menu) {
    	
	  	case 0: 
	  	case 101:
	  	case 105: 
	  	case 1: 
	  	case 2: 
	  	case 28:
	  	case 3: 
	  	case 4: 
	  	case 501: 
	  	case 502: 
	  	case 5: 
	  	case 6: 
	  	case 701: 
	  	case 7: 
	  	case 711: 
	  	case 8: 
	  	case 91: 
	  	case 9: 
	  		if (music) {
	  			if (!mediaplayer.isPlaying()) {
	  				mediaplayer.start();
	  			}
	  		}
	  		break;
	  		
	  	case 108: 
	  	case 102: 
	  		if (music) {
	  			if (mediaplayer.isPlaying()) {
	  			mediaplayer.pause();
	  			}
	  		}
	  		next_game_tick = GetTickCount();
	  		break;

	  	case 10: 
	  		pause = false;
	  		PauseTimer_Update();
	  		if (music) {
	  			if (!mediaplayer.isPlaying()) {
	  				mediaplayer.start();
	  			}
	  		}
	  		break;
	  	
	  	default :
	  		
	  		break;
	  		
	  }	
  	  
	}
		
	public void onDestroy() {
		
		// GOOGLE IN-APP BILLING 3
		// GOOGLE IN-APP BILLING 3
		// GOOGLE IN-APP BILLING 3
		// ===================================================================================
        // very important:
        Log.d(TAG_BILLING, "Destroying helper.");
        if (mHelper != null) mHelper.dispose();
        mHelper = null; 
    	// =================================================================================== 
        
		mediaplayer.release();
		System.gc();
	}


	
	
	
	void ontouch_down_case_9() {
  		
  		if (menusystem.upgrade_selected) {
  			for (int i=0;i<6;i++) {
  				if(scaleX > menusystem.upgrade_bt_start[0] && scaleY > menusystem.upgrade_bt_start[1]+i*menusystem.upgrade_bt_start[2] && scaleX < menusystem.upgrade_bt_start[0]+(int)(hd*400) && scaleY < menusystem.upgrade_bt_start[1]+(i+1)*menusystem.upgrade_bt_start[2]) {
  					if (menusystem.itemlist[i] > -2) {
  					
  						if (menusystem.upgrade_selected_weapon >= 0) {	
  							if (menusystem.itemlist[i] == -1) {
  								if(sound)SoundManager.playSound(1,1);
  								menusystem.upgrade_slot_selected = i;
  							}
  							else if (!allUpgrades.turret[menusystem.itemlist[i]].bought && !allUpgrades.turret[menusystem.itemlist[i]].buycode.toString().equals("")) {
								if(sound)SoundManager.playSound(1,1);
								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.turret[menusystem.itemlist[i]].fullgame && !fullgame) {
								if(sound)SoundManager.playSound(1,1);
								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.turret[menusystem.itemlist[i]].bought) {
								if(sound)SoundManager.playSound(1,1);
								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.turret[menusystem.itemlist[i]].require<0) {
  								if (allUpgrades.turret[menusystem.itemlist[i]].cost <= LY) {
  									if(sound)SoundManager.playSound(1,1);
  									menusystem.upgrade_slot_selected = i;
  								}
  							}
  							// if prerequirements fail or not enough money
  							else if (allUpgrades.turret[menusystem.itemlist[i]].require>=0) {
  								if (allUpgrades.turret[menusystem.itemlist[i]].cost <= LY && allUpgrades.turret[allUpgrades.turret[menusystem.itemlist[i]].require].bought) {
  									if(sound)SoundManager.playSound(1,1);
  									menusystem.upgrade_slot_selected = i;
  								}
  							}
  						}
  						if (menusystem.upgrade_selected_modifier >= 0) {	
  							if (menusystem.itemlist[i] == -1) {
  								if(sound)SoundManager.playSound(1,1);
  								menusystem.upgrade_slot_selected = i;
  							}
  							else if (!allUpgrades.modifier[menusystem.itemlist[i]].bought && !allUpgrades.modifier[menusystem.itemlist[i]].buycode.toString().equals("")) {
								if(sound)SoundManager.playSound(1,1);
								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.modifier[menusystem.itemlist[i]].fullgame && !fullgame) {
								if(sound)SoundManager.playSound(1,1);
								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.modifier[menusystem.itemlist[i]].bought) {
								if(sound)SoundManager.playSound(1,1);
								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.modifier[menusystem.itemlist[i]].require<0) {
  								if (allUpgrades.modifier[menusystem.itemlist[i]].cost <= LY) {
  									if(sound)SoundManager.playSound(1,1);
  									menusystem.upgrade_slot_selected = i;
  								}
  							}
  							// if prerequirements fail or not enough money
  							else if (allUpgrades.modifier[menusystem.itemlist[i]].require>=0) {
  								if (allUpgrades.modifier[menusystem.itemlist[i]].cost <= LY && allUpgrades.modifier[allUpgrades.modifier[menusystem.itemlist[i]].require].bought) {
  									if(sound)SoundManager.playSound(1,1);
  									menusystem.upgrade_slot_selected = i;
  								}
  							}
  						}
  						if (menusystem.upgrade_selected_special >= 0) {	
  							if (menusystem.itemlist[i] == -1) {
  								if(sound)SoundManager.playSound(1,1);
  								menusystem.upgrade_slot_selected = i;
  							}
  							else if (!allUpgrades.special[menusystem.itemlist[i]].bought && !allUpgrades.special[menusystem.itemlist[i]].buycode.toString().equals("")) {
								if(sound)SoundManager.playSound(1,1);
								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.special[menusystem.itemlist[i]].fullgame && !fullgame) {
								if(sound)SoundManager.playSound(1,1);
								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.special[menusystem.itemlist[i]].bought) {
								if(sound)SoundManager.playSound(1,1);
								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.special[menusystem.itemlist[i]].require<0) {
  								if (allUpgrades.special[menusystem.itemlist[i]].cost <= LY) {
  									if(sound)SoundManager.playSound(1,1);
  									menusystem.upgrade_slot_selected = i;
  								}
  							}
  							// if prerequirements fail or not enough money
  							else if (allUpgrades.special[menusystem.itemlist[i]].require>=0) {
  								if (allUpgrades.special[menusystem.itemlist[i]].cost <= LY && allUpgrades.special[allUpgrades.special[menusystem.itemlist[i]].require].bought) {
  									if(sound)SoundManager.playSound(1,1);
  									menusystem.upgrade_slot_selected = i;
  								}
  							}
  						}
  						if (menusystem.upgrade_selected_upgrade >= 0) {	
  							if (menusystem.itemlist[i] == -1) {
  								if(sound)SoundManager.playSound(1,1);
  								menusystem.upgrade_slot_selected = i;
  							}
  							else if (!allUpgrades.upgrade[menusystem.itemlist[i]].bought && !allUpgrades.upgrade[menusystem.itemlist[i]].buycode.toString().equals("")) {
								if(sound)SoundManager.playSound(1,1);
								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.upgrade[menusystem.itemlist[i]].fullgame && !fullgame) {
								if(sound)SoundManager.playSound(1,1);
								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.upgrade[menusystem.itemlist[i]].bought) {
								if(sound)SoundManager.playSound(1,1);
								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.upgrade[menusystem.itemlist[i]].require<0) {
  								if (allUpgrades.upgrade[menusystem.itemlist[i]].cost <= LY) {
  									if(sound)SoundManager.playSound(1,1);
  									menusystem.upgrade_slot_selected = i;
  								}
  							}
  							// if prerequirements fail or not enough money
  							else if (allUpgrades.upgrade[menusystem.itemlist[i]].require>=0) {
  								if (allUpgrades.upgrade[menusystem.itemlist[i]].cost <= LY && allUpgrades.upgrade[allUpgrades.upgrade[menusystem.itemlist[i]].require].bought) {
  									if(sound)SoundManager.playSound(1,1);
  									menusystem.upgrade_slot_selected = i;
  								}
  							}
  						}
  						if (menusystem.upgrade_selected_ship >= 0) {	
  							if (menusystem.itemlist[i] == -1) {
  								if(sound)SoundManager.playSound(1,1);
  								menusystem.upgrade_slot_selected = i;
  							}
  							else if (!allUpgrades.ship[menusystem.itemlist[i]].bought && !allUpgrades.ship[menusystem.itemlist[i]].buycode.toString().equals("")) {
  								if(sound)SoundManager.playSound(1,1);
  								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.ship[menusystem.itemlist[i]].fullgame && !fullgame) {
  								if(sound)SoundManager.playSound(1,1);
  								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.ship[menusystem.itemlist[i]].bought) {
								if(sound)SoundManager.playSound(1,1);
								menusystem.upgrade_slot_selected = i;
  							}
  							else if (allUpgrades.ship[menusystem.itemlist[i]].require<0) {
  								if (allUpgrades.ship[menusystem.itemlist[i]].cost <= LY) {
  									if(sound)SoundManager.playSound(1,1);
  									menusystem.upgrade_slot_selected = i;
  								}
  							}
  							// if prerequirements fail or not enough money
  							else if (allUpgrades.ship[menusystem.itemlist[i]].require>=0) {
  								if (allUpgrades.ship[menusystem.itemlist[i]].cost <= LY && allUpgrades.ship[allUpgrades.ship[menusystem.itemlist[i]].require].bought) {
  									if(sound)SoundManager.playSound(1,1);
  									menusystem.upgrade_slot_selected = i;
  								}
  							}
  						}
  						if (menusystem.upgrade_selected_purchasable >= 0) {	
							if(sound)SoundManager.playSound(1,1);
							menusystem.upgrade_slot_selected = i;
  						}
  					}
  					break;
  				}
  			}
  		} else {
  	  		if (!menusystem.upgrade_icon_pressed) {
  	  			for (int i=0;i<allUpgrades.ship[spaceship.type].turret_num;i++) {
  	  				if(scaleX > menusystem.upgrade_icon_weapon_bt[i][0] && scaleY > menusystem.upgrade_icon_weapon_bt[i][1] && scaleX < menusystem.upgrade_icon_weapon_bt[i][0]+menusystem.upgrade_icon_size && scaleY < menusystem.upgrade_icon_weapon_bt[i][1]+menusystem.upgrade_icon_size) { 
  	  					if(sound)SoundManager.playSound(1,1);
  	  					menusystem.upgrade_icon_pressed = true; 
  	  					break;
  	  				}
  	  			}
  	  		} 
  	  		if (!menusystem.upgrade_icon_pressed) {
  	  			for (int i=0;i<allUpgrades.ship[spaceship.type].modifier_num;i++) {
  	  				if(scaleX > menusystem.upgrade_icon_modifier_bt[i][0] && scaleY > menusystem.upgrade_icon_modifier_bt[i][1] && scaleX < menusystem.upgrade_icon_modifier_bt[i][0]+menusystem.upgrade_icon_size && scaleY < menusystem.upgrade_icon_modifier_bt[i][1]+menusystem.upgrade_icon_size) { 
  	  					if(sound)SoundManager.playSound(1,1);
  	  					menusystem.upgrade_icon_pressed = true; 
  	  					break;
  	  				}
  	  			}
  	  		}
  	  		if (!menusystem.upgrade_icon_pressed) {
  	  			for (int i=0;i<allUpgrades.ship[spaceship.type].special_num;i++) {
  	  				if(scaleX > menusystem.upgrade_icon_special_bt[i][0] && scaleY > menusystem.upgrade_icon_special_bt[i][1] && scaleX < menusystem.upgrade_icon_special_bt[i][0]+menusystem.upgrade_icon_size && scaleY < menusystem.upgrade_icon_special_bt[i][1]+menusystem.upgrade_icon_size) { 
  	  					if(sound)SoundManager.playSound(1,1);
  	  					menusystem.upgrade_icon_pressed = true; 
  	  					break;
  	  				}
  	  			}
  	  		}
  	  		if (!menusystem.upgrade_icon_pressed) {
  	  			for (int i=0;i<allUpgrades.ship[spaceship.type].upgrade_num;i++) {
  	  				if(scaleX > menusystem.upgrade_icon_upgrade_bt[i][0] && scaleY > menusystem.upgrade_icon_upgrade_bt[i][1] && scaleX < menusystem.upgrade_icon_upgrade_bt[i][0]+menusystem.upgrade_icon_size && scaleY < menusystem.upgrade_icon_upgrade_bt[i][1]+menusystem.upgrade_icon_size) { 
  	  					if(sound)SoundManager.playSound(1,1);
  	  					menusystem.upgrade_icon_pressed = true;
  	  					break;
  	  				}
  		    	}
  			}
  	  		if (!menusystem.upgrade_icon_pressed) {
  	 				if(scaleX > menusystem.upgrade_icon_ship_bt[0] && scaleY > menusystem.upgrade_icon_ship_bt[1] && scaleX < menusystem.upgrade_icon_ship_bt[0]+menusystem.upgrade_icon_size && scaleY < menusystem.upgrade_icon_ship_bt[1]+menusystem.upgrade_icon_size) { 
  	 					if(sound)SoundManager.playSound(1,1);
  	 					menusystem.upgrade_icon_pressed = true;
  		    	}
  			}
  	  		if (!menusystem.upgrade_icon_pressed) {
	 				if(scaleX > menusystem.upgrade_icon_purchasable_bt[0] && scaleY > menusystem.upgrade_icon_purchasable_bt[1] && scaleX < menusystem.upgrade_icon_purchasable_bt[0]+menusystem.upgrade_icon_size && scaleY < menusystem.upgrade_icon_purchasable_bt[1]+menusystem.upgrade_icon_size) { 
	 					if(sound)SoundManager.playSound(1,1);
	 					menusystem.upgrade_icon_pressed = true;
		    	}
			}

  		}
  		
	}
	
	
	
	void ontouch_move_case_9(float dy) {
  		if (menusystem.upgrade_selected) {
  			
  			if (menusystem.upgrade_slot_selected >= 0) {
  				if(scaleX < menusystem.upgrade_bt_start[0] || scaleY < menusystem.upgrade_bt_start[1]+menusystem.upgrade_slot_selected*menusystem.upgrade_bt_start[2] || scaleX > menusystem.upgrade_bt_start[0]+(int)(hd*400) || scaleY > menusystem.upgrade_bt_start[1]+(menusystem.upgrade_slot_selected+1)*menusystem.upgrade_bt_start[2]) {
  					menusystem.upgrade_slot_selected = -1;
  				}
  			}
			
  			touch_scroll_value += dy;
  		// Up and Down arrows
  			
			if (menusystem.upgrade_maxpage > 1) {
				boolean pagechange = false;
				if (menusystem.upgrade_page > 0) {
					if(touch_scroll_value > 170) {
						menusystem.upgrade_page--;
						touch_scroll_value = 0.0f;
						pagechange = true;
						
						if (menusystem.upgrade_page < 0) {
							menusystem.upgrade_page = 0;
						} else { if(sound)SoundManager.playSound(23,1); }
    	  			}
				}
				if (menusystem.upgrade_page < menusystem.upgrade_maxpage-1) {
					if(touch_scroll_value < - 170) {
						menusystem.upgrade_page++;
						touch_scroll_value = 0.0f;
						pagechange = true;
						if (menusystem.upgrade_page >= menusystem.upgrade_maxpage) {
							menusystem.upgrade_page = menusystem.upgrade_maxpage - 1;
						} else { if(sound)SoundManager.playSound(23,1); }
    	  			}
				}
				if (pagechange) {
					// reset upgrade list
					for (int o=0; o<6; o++) menusystem.itemlist[o] = -2;
					int num = 0; int current = 0;
					// UPGRADE SELECTED
					if (menusystem.upgrade_selected_weapon >= 0) {
						num++;
						if (menusystem.upgrade_page == 0) {
							menusystem.itemlist[current] = -1;
							current++;
						}
						if (spaceship.turret[menusystem.upgrade_selected_weapon] >= 0) { 
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = spaceship.turret[menusystem.upgrade_selected_weapon];
								current++;
							}
							
						}
						for (int n=0; n<allUpgrades.maxturret; n++) {
							if (allUpgrades.turret[n].available) {
								// Check if the upgrade is already equipped
								boolean b = true;
								for (int m=0; m<spaceship.turret_num;m++) {
									if (spaceship.turret[m] == n) {
										b = false;
										break;
									}
								}
								if (allUpgrades.turret[n].followup >=0) {
									if (allUpgrades.turret[allUpgrades.turret[n].followup].bought) {
								
										b = false;
									}
								}
								// Add the rest of the upgrades
								if(b) {
									num++;
									if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
										menusystem.itemlist[current] = n;
										current++;
									}
								}
							}
						}
						
					}
					else if (menusystem.upgrade_selected_modifier >= 0) {
						num++;
						if (menusystem.upgrade_page == 0) {
							menusystem.itemlist[current] = -1;
							current++;
						}
						if (spaceship.modifier[menusystem.upgrade_selected_modifier] >= 0) {
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = spaceship.modifier[menusystem.upgrade_selected_modifier];
								current++;
							}
						}
						for (int n=0; n<allUpgrades.maxmodifier; n++) {
							if (allUpgrades.modifier[n].available) {
								// Check if the upgrade is already equipped
								boolean b = true;
								for (int m=0; m<spaceship.modifier_num;m++) {
									if (spaceship.modifier[m] == n) {
										b = false;
										break;
									}
								}
								if (allUpgrades.modifier[n].followup >=0) {
									if (allUpgrades.modifier[allUpgrades.modifier[n].followup].bought) {
								
										b = false;
									}
								}
								// Add the rest of the upgrades
								if(b) {
									num++;
									if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
										menusystem.itemlist[current] = n;
										current++;
									}
								}
							}
						}
					}
					else if (menusystem.upgrade_selected_special >= 0) {
						num++;
						if (menusystem.upgrade_page == 0) {
							menusystem.itemlist[current] = -1;
							current++;
						}
						if (spaceship.special[menusystem.upgrade_selected_special] >= 0) {
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = spaceship.special[menusystem.upgrade_selected_special];
								current++;
							}
						}
						for (int n=0; n<allUpgrades.maxspecial; n++) {
							if (allUpgrades.special[n].available) {
								// Check if the upgrade is already equipped
								boolean b = true;
								for (int m=0; m<spaceship.special_num;m++) {
									if (spaceship.special[m] == n) {
										b = false;
										break;
									}
								}
								if (allUpgrades.special[n].followup >=0) {
									if (allUpgrades.special[allUpgrades.special[n].followup].bought) {
								
										b = false;
									}
								}
								// Add the rest of the upgrades
								if(b) {
									num++;
									if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
										menusystem.itemlist[current] = n;
										current++;
									}
								}
							}
						}
					}
					else if (menusystem.upgrade_selected_upgrade >= 0) {
						num++;
						if (menusystem.upgrade_page == 0) {
							menusystem.itemlist[current] = -1;
							current++;
						}
						if (spaceship.upgrade[menusystem.upgrade_selected_upgrade] >= 0) {
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = spaceship.upgrade[menusystem.upgrade_selected_upgrade];
								current++;
							}
						}
						for (int n=0; n<allUpgrades.maxupgrade; n++) {
							if (allUpgrades.upgrade[n].available) {
								// Check if the upgrade is already equipped
								boolean b = true;
								for (int m=0; m<spaceship.upgrade_num;m++) {
									if (spaceship.upgrade[m] == n) {
										b = false;
										break;
									}
								}
								if (allUpgrades.upgrade[n].followup >=0) {
									if (allUpgrades.upgrade[allUpgrades.upgrade[n].followup].bought) {
								
										b = false;
									}
								}
								// Add the rest of the upgrades
								if(b) {
									num++;
									if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
										menusystem.itemlist[current] = n;
										current++;
									}
								}
							}
						}
					}
					else if (menusystem.upgrade_selected_ship >= 0) {
						//if (spaceship.upgrade[menusystem.upgrade_selected_ship] >= 0) {
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = spaceship.type;
								current++;
							}
						//}
						for (int n=0; n<allUpgrades.maxship; n++) {
							if (n!=spaceship.type && (allUpgrades.ship[n].available || allUpgrades.ship[n].show)) {
								num++;
								if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
									menusystem.itemlist[current] = n;
									current++;
								}
							}
						}
					}
					else if (menusystem.upgrade_selected_purchasable >= 0) {
						for (int n=0; n<allUpgrades.maxpurchasable; n++) {
							if (allUpgrades.purchasable[n].available) {
								num++;
								if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
									menusystem.itemlist[current] = n;
									current++;
								}
							}
						}
					}
					
					menusystem.upgrade_slot_selected = -1;
					menusystem.upgrade_maxpage = (num-1) / 6 + 1;
				}
			}
  		}

	}

	void ontouch_up_case_9() {
		
  		if (!menusystem.upgrade_selected) {
  			if (menusystem.upgrade_icon_pressed) {
  				
	  			for (int i=0;i<allUpgrades.ship[spaceship.type].turret_num;i++) {
	  				if(scaleX > menusystem.upgrade_icon_weapon_bt[i][0] && scaleY > menusystem.upgrade_icon_weapon_bt[i][1] && scaleX < menusystem.upgrade_icon_weapon_bt[i][0]+menusystem.upgrade_icon_size && scaleY < menusystem.upgrade_icon_weapon_bt[i][1]+menusystem.upgrade_icon_size) {
	  					loading = true;
	  					menusystem.upgrade_selected = true; 
	  					menusystem.upgrade_selected_weapon = i;
	  					menusystem.upgrade_selected_modifier = -1;
	  					menusystem.upgrade_selected_special = -1;
	  					menusystem.upgrade_selected_upgrade = -1;
	  					menusystem.upgrade_selected_ship = -1;
	  					menusystem.upgrade_selected_purchasable = -1;
	  					menusystem.upgrade_page = 0;
	  					if(sound)SoundManager.playSound(21,1);
	  					
						// reset upgrade list
						for (int o=0; o<6; o++) menusystem.itemlist[o] = -2;
						int num = 0; int current = 0;
						// UPGRADE SELECTED
						if (menusystem.upgrade_selected_weapon >= 0) {
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = -1;
								current++;
							}
							if (spaceship.turret[menusystem.upgrade_selected_weapon] >= 0) { 
								num++;
								if (menusystem.upgrade_page == 0) {
									menusystem.itemlist[current] = spaceship.turret[menusystem.upgrade_selected_weapon];
									current++;
								}
								
							}
							for (int n=0; n<allUpgrades.maxturret; n++) {
								if (allUpgrades.turret[n].available) {
									// Check if the upgrade is already equipped
									boolean b = true;
									for (int m=0; m<spaceship.turret_num;m++) {
										if (spaceship.turret[m] == n) {
											b = false;
											break;
										}
									}
									if (allUpgrades.turret[n].followup >=0) {
										if (allUpgrades.turret[allUpgrades.turret[n].followup].bought) {
									
											b = false;
										}
									}
									// Add the rest of the upgrades
									if(b) {
										num++;
										if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
											menusystem.itemlist[current] = n;
											current++;
										}
									}
								}
							}
							menusystem.upgrade_maxpage = (num-1) / 6 + 1;
							menusystem.upgrade_slot_selected = -1;
						}
	  					break;
	  				}
	  			}
	  			for (int i=0;i<allUpgrades.ship[spaceship.type].modifier_num;i++) {
	  				if(scaleX > menusystem.upgrade_icon_modifier_bt[i][0] && scaleY > menusystem.upgrade_icon_modifier_bt[i][1] && scaleX < menusystem.upgrade_icon_modifier_bt[i][0]+menusystem.upgrade_icon_size && scaleY < menusystem.upgrade_icon_modifier_bt[i][1]+menusystem.upgrade_icon_size) { 
	  					loading = true;
	  					menusystem.upgrade_selected = true; 
	  					menusystem.upgrade_selected_weapon = -1;
	  					menusystem.upgrade_selected_modifier = i;
	  					menusystem.upgrade_selected_special = -1;
	  					menusystem.upgrade_selected_upgrade = -1;
	  					menusystem.upgrade_selected_ship = -1;
	  					menusystem.upgrade_selected_purchasable = -1;
	  					menusystem.upgrade_page = 0;
	  					if(sound)SoundManager.playSound(21,1);
	  					
						// reset upgrade list
						for (int o=0; o<6; o++) menusystem.itemlist[o] = -2;
						int num = 0; int current = 0;
						// UPGRADE SELECTED
						if (menusystem.upgrade_selected_modifier >= 0) {
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = -1;
								current++;
							}
							if (spaceship.modifier[menusystem.upgrade_selected_modifier] >= 0) {
								num++;
								if (menusystem.upgrade_page == 0) {
									menusystem.itemlist[current] = spaceship.modifier[menusystem.upgrade_selected_modifier];
									current++;
								}
							}
							for (int n=0; n<allUpgrades.maxmodifier; n++) {
								if (allUpgrades.modifier[n].available) {
									// Check if the upgrade is already equipped
									boolean b = true;
									for (int m=0; m<spaceship.modifier_num;m++) {
										if (spaceship.modifier[m] == n) {
											b = false;
											break;
										}
									}
									if (allUpgrades.modifier[n].followup >=0) {
										if (allUpgrades.modifier[allUpgrades.modifier[n].followup].bought) {
									
											b = false;
										}
									}
									// Add the rest of the upgrades
									if(b) {
										num++;
										if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
											menusystem.itemlist[current] = n;
											current++;
										}
									}
								}
							}
							menusystem.upgrade_maxpage = (num-1) / 6 + 1;
							menusystem.upgrade_slot_selected = -1;
						}
	  					break;
	  				}
	  			}
	  			for (int i=0;i<allUpgrades.ship[spaceship.type].special_num;i++) {
	  				if(scaleX > menusystem.upgrade_icon_special_bt[i][0] && scaleY > menusystem.upgrade_icon_special_bt[i][1] && scaleX < menusystem.upgrade_icon_special_bt[i][0]+menusystem.upgrade_icon_size && scaleY < menusystem.upgrade_icon_special_bt[i][1]+menusystem.upgrade_icon_size) { 
	  					loading = true;
	  					menusystem.upgrade_selected = true; 
	  					menusystem.upgrade_selected_weapon = -1;
	  					menusystem.upgrade_selected_modifier = -1;
	  					menusystem.upgrade_selected_special = i;
	  					menusystem.upgrade_selected_upgrade = -1;
	  					menusystem.upgrade_selected_ship = -1;
	  					menusystem.upgrade_selected_purchasable = -1;
	  					menusystem.upgrade_page = 0;
	  					if(sound)SoundManager.playSound(21,1);
	  					
						// reset upgrade list
						for (int o=0; o<6; o++) menusystem.itemlist[o] = -2;
						int num = 0; int current = 0;
						// UPGRADE SELECTED
						if (menusystem.upgrade_selected_special >= 0) {
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = -1;
								current++;
							}
							if (spaceship.special[menusystem.upgrade_selected_special] >= 0) {
								num++;
								if (menusystem.upgrade_page == 0) {
									menusystem.itemlist[current] = spaceship.special[menusystem.upgrade_selected_special];
									current++;
								}
							}
							for (int n=0; n<allUpgrades.maxspecial; n++) {
								if (allUpgrades.special[n].available) {
									// Check if the upgrade is already equipped
									boolean b = true;
									for (int m=0; m<spaceship.special_num;m++) {
										if (spaceship.special[m] == n) {
											b = false;
											break;
										}
									}
									if (allUpgrades.special[n].followup >=0) {
										if (allUpgrades.special[allUpgrades.special[n].followup].bought) {
									
											b = false;
										}
									}
									// Add the rest of the upgrades
									if(b) {
										num++;
										if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
											menusystem.itemlist[current] = n;
											current++;
										}
									}
								}
							}
							menusystem.upgrade_maxpage = (num-1) / 6 + 1;
							menusystem.upgrade_slot_selected = -1;
						}
	  					break;
	  				}
	  			}
	  			for (int i=0;i<allUpgrades.ship[spaceship.type].upgrade_num;i++) {
	  				if(scaleX > menusystem.upgrade_icon_upgrade_bt[i][0] && scaleY > menusystem.upgrade_icon_upgrade_bt[i][1] && scaleX < menusystem.upgrade_icon_upgrade_bt[i][0]+menusystem.upgrade_icon_size && scaleY < menusystem.upgrade_icon_upgrade_bt[i][1]+menusystem.upgrade_icon_size) { 
	  					loading = true;
	  					menusystem.upgrade_selected = true; 
	  					menusystem.upgrade_selected_weapon = -1;
	  					menusystem.upgrade_selected_modifier = -1;
	  					menusystem.upgrade_selected_special = -1;
	  					menusystem.upgrade_selected_upgrade = i;
	  					menusystem.upgrade_selected_ship = -1;
	  					menusystem.upgrade_selected_purchasable = -1;
						menusystem.upgrade_page = 0;
						if(sound)SoundManager.playSound(21,1);
						
						// reset upgrade list
						for (int o=0; o<6; o++) menusystem.itemlist[o] = -2;
						int num = 0; int current = 0;
						// UPGRADE SELECTED
						if (menusystem.upgrade_selected_upgrade >= 0) {
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = -1;
								current++;
							}
							if (spaceship.upgrade[menusystem.upgrade_selected_upgrade] >= 0) {
								num++;
								if (menusystem.upgrade_page == 0) {
									menusystem.itemlist[current] = spaceship.upgrade[menusystem.upgrade_selected_upgrade];
									current++;
								}
							}
							for (int n=0; n<allUpgrades.maxupgrade; n++) {
								if (allUpgrades.upgrade[n].available) {
									// Check if the upgrade is already equipped
									boolean b = true;
									for (int m=0; m<spaceship.upgrade_num;m++) {
										if (spaceship.upgrade[m] == n) {
											b = false;
											break;
										}
									}
									if (allUpgrades.upgrade[n].followup >=0) {
										if (allUpgrades.upgrade[allUpgrades.upgrade[n].followup].bought) {
									
											b = false;
										}
									}
									// Add the rest of the upgrades
									if(b) {
										num++;
										if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
											menusystem.itemlist[current] = n;
											current++;
										}
									}
								}
							}
							menusystem.upgrade_maxpage = (num-1) / 6 + 1;
							menusystem.upgrade_slot_selected = -1;
						}
	  					break;
	  				}
    	    	}
	  			
	  			// SHIPS
	  			for (int i=0;i<allUpgrades.maxship;i++) {
	  				if(scaleX > menusystem.upgrade_icon_ship_bt[0] && scaleY > menusystem.upgrade_icon_ship_bt[1] && scaleX < menusystem.upgrade_icon_ship_bt[0]+menusystem.upgrade_icon_size && scaleY < menusystem.upgrade_icon_ship_bt[1]+menusystem.upgrade_icon_size) { 
	  					loading = true;
	  					menusystem.upgrade_selected = true; 
	  					menusystem.upgrade_selected_weapon = -1;
	  					menusystem.upgrade_selected_modifier = -1;
	  					menusystem.upgrade_selected_special = -1;
	  					menusystem.upgrade_selected_upgrade = -1;
	  					menusystem.upgrade_selected_purchasable = -1;
	  					menusystem.upgrade_selected_ship = i;
						menusystem.upgrade_page = 0;
						if(sound)SoundManager.playSound(21,1);
						
						// reset upgrade list
						for (int o=0; o<6; o++) menusystem.itemlist[o] = -2;
						int num = 0; int current = 0;
						// UPGRADE SELECTED
						if (menusystem.upgrade_selected_ship >= 0) {
							if (spaceship.type >= 0) {
								num++;
								if (menusystem.upgrade_page == 0) {
									menusystem.itemlist[current] = spaceship.type;
									current++;
								}
							}
							for (int n=0; n<allUpgrades.maxship; n++) {
								if (n!=spaceship.type && (allUpgrades.ship[n].available || allUpgrades.ship[n].show)) {
									num++;
									if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6 && n != spaceship.type) {
										menusystem.itemlist[current] = n;
										current++;
									}
								}
							}
							menusystem.upgrade_maxpage = (num-1) / 6 + 1;
							menusystem.upgrade_slot_selected = -1;
						}
	  					break;
	  				}
    	    	}
	  			
	  			// PURCHASABLES
	  			for (int i=0;i<allUpgrades.maxpurchasable;i++) {
	  				if(scaleX > menusystem.upgrade_icon_purchasable_bt[0] && scaleY > menusystem.upgrade_icon_purchasable_bt[1] && scaleX < menusystem.upgrade_icon_purchasable_bt[0]+menusystem.upgrade_icon_size && scaleY < menusystem.upgrade_icon_purchasable_bt[1]+menusystem.upgrade_icon_size) { 
	  					loading = true;
	  					menusystem.upgrade_selected = true; 
	  					menusystem.upgrade_selected_weapon = -1;
	  					menusystem.upgrade_selected_modifier = -1;
	  					menusystem.upgrade_selected_special = -1;
	  					menusystem.upgrade_selected_upgrade = -1;
	  					menusystem.upgrade_selected_purchasable = i;
	  					menusystem.upgrade_selected_ship = -1;
						menusystem.upgrade_page = 0;
						if(sound)SoundManager.playSound(21,1);
						
						// reset upgrade list
						for (int o=0; o<6; o++) menusystem.itemlist[o] = -2;
						int num = 0; int current = 0;
						// UPGRADE SELECTED
						if (menusystem.upgrade_selected_purchasable >= 0) {
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = -1;
								current++;
							}
							for (int n=0; n<allUpgrades.maxpurchasable; n++) {
								if (allUpgrades.purchasable[n].available) {
									num++;
									if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
										menusystem.itemlist[current] = n;
										current++;
									}
								}
							}
							menusystem.upgrade_maxpage = (num-1) / 6 + 1;
							menusystem.upgrade_slot_selected = -1;
						}
	  					break;
	  				}
    	    	}
	  			
			}
  		}
  		else {
  			touch_scroll_value = 0.0f;
  		// Up and Down arrows
			if (menusystem.upgrade_maxpage > 1) {
				
				boolean pagechange = false;
				if (menusystem.upgrade_page > 0) {
					if(scaleX > menusystem.upgrade_bt_up[0] && scaleY > menusystem.upgrade_bt_up[1] && scaleX < menusystem.upgrade_bt_up[0]+menusystem.upgrade_bt_up[2] && scaleY < menusystem.upgrade_bt_up[1]+menusystem.upgrade_bt_up[3]) {
						menusystem.upgrade_page--;
						pagechange = true;
						if (menusystem.upgrade_page < 0) {
							menusystem.upgrade_page = 0;
						} else {
							if(sound)SoundManager.playSound(23,1);
						}
    	  			}
				}
				
				if (menusystem.upgrade_page < menusystem.upgrade_maxpage-1) {

					if(scaleX > menusystem.upgrade_bt_down[0] && scaleY > menusystem.upgrade_bt_down[1] && scaleX < menusystem.upgrade_bt_down[0]+menusystem.upgrade_bt_down[2] && scaleY < menusystem.upgrade_bt_down[1]+menusystem.upgrade_bt_down[3]) {
						menusystem.upgrade_page++;
						pagechange = true;
						if (menusystem.upgrade_page >= menusystem.upgrade_maxpage) {
							menusystem.upgrade_page = menusystem.upgrade_maxpage - 1;
						} else {
							if(sound)SoundManager.playSound(23,1);
						}
    	  			}
 
				}
				
				if (pagechange) {
					// reset upgrade list
					for (int o=0; o<6; o++) menusystem.itemlist[o] = -2;
					int num = 0; int current = 0;
					// UPGRADE SELECTED

					if (menusystem.upgrade_selected_weapon >= 0) {

						num++;
						if (menusystem.upgrade_page == 0) {
							menusystem.itemlist[current] = -1;
							current++;
						}
						if (spaceship.turret[menusystem.upgrade_selected_weapon] >= 0) { 
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = spaceship.turret[menusystem.upgrade_selected_weapon];
								current++;
							}
							
						}
						for (int n=0; n<allUpgrades.maxturret; n++) {
							if (allUpgrades.turret[n].available) {
								// Check if the upgrade is already equipped
								boolean b = true;
								for (int m=0; m<spaceship.turret_num;m++) {
									if (spaceship.turret[m] == n) {
										b = false;
										break;
									}
								}
								if (allUpgrades.turret[n].followup >=0) {
									if (allUpgrades.turret[allUpgrades.turret[n].followup].bought) {
								
										b = false;
									}
								}
								// Add the rest of the upgrades
								if(b) {
									num++;
									if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
										menusystem.itemlist[current] = n;
										current++;
									}
								}
							}
						}

					}
					else if (menusystem.upgrade_selected_modifier >= 0) {
						num++;
						if (menusystem.upgrade_page == 0) {
							menusystem.itemlist[current] = -1;
							current++;
						}
						if (spaceship.modifier[menusystem.upgrade_selected_modifier] >= 0) {
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = spaceship.modifier[menusystem.upgrade_selected_modifier];
								current++;
							}
						}
						for (int n=0; n<allUpgrades.maxmodifier; n++) {
							if (allUpgrades.modifier[n].available) {
								// Check if the upgrade is already equipped
								boolean b = true;
								for (int m=0; m<spaceship.modifier_num;m++) {
									if (spaceship.modifier[m] == n) {
										b = false;
										break;
									}
								}
								if (allUpgrades.modifier[n].followup >=0) {
									if (allUpgrades.modifier[allUpgrades.modifier[n].followup].bought) {
								
										b = false;
									}
								}
								// Add the rest of the upgrades
								if(b) {
									num++;
									if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
										menusystem.itemlist[current] = n;
										current++;
									}
								}
							}
						}
					}
					else if (menusystem.upgrade_selected_special >= 0) {
						num++;
						if (menusystem.upgrade_page == 0) {
							menusystem.itemlist[current] = -1;
							current++;
						}
						if (spaceship.special[menusystem.upgrade_selected_special] >= 0) {
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = spaceship.special[menusystem.upgrade_selected_special];
								current++;
							}
						}
						for (int n=0; n<allUpgrades.maxspecial; n++) {
							if (allUpgrades.special[n].available) {
								// Check if the upgrade is already equipped
								boolean b = true;
								for (int m=0; m<spaceship.special_num;m++) {
									if (spaceship.special[m] == n) {
										b = false;
										break;
									}
								}
								if (allUpgrades.special[n].followup >=0) {
									if (allUpgrades.special[allUpgrades.special[n].followup].bought) {
								
										b = false;
									}
								}
								// Add the rest of the upgrades
								if(b) {
									num++;
									if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
										menusystem.itemlist[current] = n;
										current++;
									}
								}
							}
						}
					}
					else if (menusystem.upgrade_selected_upgrade >= 0) {
						num++;
						if (menusystem.upgrade_page == 0) {
							menusystem.itemlist[current] = -1;
							current++;
						}
						if (spaceship.upgrade[menusystem.upgrade_selected_upgrade] >= 0) {
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = spaceship.upgrade[menusystem.upgrade_selected_upgrade];
								current++;
							}
						}
						for (int n=0; n<allUpgrades.maxupgrade; n++) {
							if (allUpgrades.upgrade[n].available) {
								// Check if the upgrade is already equipped
								boolean b = true;
								for (int m=0; m<spaceship.upgrade_num;m++) {
									if (spaceship.upgrade[m] == n) {
										b = false;
										break;
									}
								}
								if (allUpgrades.upgrade[n].followup >=0) {
									if (allUpgrades.upgrade[allUpgrades.upgrade[n].followup].bought) {
								
										b = false;
									}
								}
								// Add the rest of the upgrades
								if(b) {
									num++;
									if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
										menusystem.itemlist[current] = n;
										current++;
									}
								}
							}
						}
					}
					else if (menusystem.upgrade_selected_ship >= 0) {
						//if (spaceship.upgrade[menusystem.upgrade_selected_ship] >= 0) {
							num++;
							if (menusystem.upgrade_page == 0) {
								menusystem.itemlist[current] = spaceship.type;
								current++;
							}
						//}
						for (int n=0; n<allUpgrades.maxship; n++) {
							if (n!=spaceship.type && (allUpgrades.ship[n].available || allUpgrades.ship[n].show)) {
								num++;
								if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
									menusystem.itemlist[current] = n;
									current++;
								}
							}
						}
					}
					else if (menusystem.upgrade_selected_purchasable >= 0) {
						for (int n=0; n<allUpgrades.maxpurchasable; n++) {
							if (allUpgrades.purchasable[n].available) {
								num++;
								if (num > menusystem.upgrade_page * 6 && num <= menusystem.upgrade_page * 6 + 6) {
									menusystem.itemlist[current] = n;
									current++;
								}
							}
						}
					}
					menusystem.upgrade_slot_selected = -1;
					menusystem.upgrade_maxpage = (num-1) / 6 + 1;
				}
				
				
			}
			

			if (menusystem.upgrade_slot_selected >= 0) {

				for (int i=0;i<6;i++) {
					if(scaleX > menusystem.upgrade_bt_start[0] && scaleY > menusystem.upgrade_bt_start[1]+i*menusystem.upgrade_bt_start[2] && scaleX < menusystem.upgrade_bt_start[0]+(int)(hd*400) && scaleY < menusystem.upgrade_bt_start[1]+(i+1)*menusystem.upgrade_bt_start[2]) {
						if (menusystem.upgrade_slot_selected == i) {
							if (menusystem.itemlist[i] > -2) {
								if (menusystem.upgrade_selected_weapon >= 0) {
		  							if (menusystem.itemlist[i] >= 0 && !allUpgrades.turret[menusystem.itemlist[i]].bought && !allUpgrades.turret[menusystem.itemlist[i]].buycode.toString().equals("")) {
		  								if(sound)SoundManager.playSound(21,1);
	  									buynow_item = allUpgrades.turret[menusystem.itemlist[i]].buycode.toString();

	  									if (isGoogle) {
	  				    	  				// GOOGLE IN-APP BILLING 3
	  				    	  				// ===================================================================================
	  				    	  		        Log.d(TAG_BILLING, "Launching purchase flow for purchasable.");
	  				    	  		        String payload = "";
	  				    	  		        mHelper.launchPurchaseFlow((Activity)context, buynow_item, RC_BILLING_REQUEST, mPurchaseFinishedListener, payload);
	  				    	  		        // =================================================================================== 
		  								} 
		  		   	  					// amazon
		  		   	  					else buy_amazon(buynow_item);

		  							} else if (menusystem.itemlist[i] >= 0 && allUpgrades.turret[menusystem.itemlist[i]].fullgame && !fullgame) {
	  	  								if(sound)SoundManager.playSound(21,1);
	  	  								menusystem.unlock_prev = 9;
	  	        	  					menu = 7;
		  							} else {
		  								// ha a prerequirementje épp a hajón van, akkor azt leszedni
		  								for (int k=0;k<spaceship.turret_num;k++) {
		  									if (spaceship.turret[k] >= 0 && menusystem.itemlist[i] >=0 ) {
		  										if (spaceship.turret[k] == allUpgrades.turret[menusystem.itemlist[i]].require) {
		  											spaceship.turret[k] = -1;
		  										}
		  									}
		  								}
		  								// 	felrakni az újat
		  								spaceship.turret[menusystem.upgrade_selected_weapon] = menusystem.itemlist[i];
		  								// 	ha nincs raktáron, levonni a pénzt
		  								if (menusystem.itemlist[i]>=0) {
		  									if (!allUpgrades.turret[menusystem.itemlist[i]].bought) {
		  										if(sound)SoundManager.playSound(2,1);
		  										allUpgrades.turret[menusystem.itemlist[i]].bought = true;
		  										LY -= allUpgrades.turret[menusystem.itemlist[i]].cost;
		  										lyspent += allUpgrades.turret[menusystem.itemlist[i]].cost;
		  									} else { if(sound)SoundManager.playSound(18,1); }
		  								}
		  								// 	selection-t törölni
		  								menusystem.upgrade_selected_weapon = -1;
		  								menusystem.upgrade_selected = false;
		  								menusystem.upgrade_slot_selected = -1;
		  								// 	hajó adatokat frissíteni
		  								Update_Ship_Properties();
		  							}
									break;
			
								}
								else if (menusystem.upgrade_selected_modifier >= 0) {
		  							if (menusystem.itemlist[i] >= 0 && !allUpgrades.modifier[menusystem.itemlist[i]].bought && !allUpgrades.modifier[menusystem.itemlist[i]].buycode.toString().equals("")) {
		  								if(sound)SoundManager.playSound(21,1);
		  								buynow_item = allUpgrades.modifier[menusystem.itemlist[i]].buycode.toString();
		  								if (isGoogle) {
		  			    	  				// GOOGLE IN-APP BILLING 3
		  			    	  				// ===================================================================================
		  			    	  		        Log.d(TAG_BILLING, "Launching purchase flow for purchasable.");
		  			    	  		        String payload = "";
		  			    	  		        mHelper.launchPurchaseFlow((Activity)context, buynow_item, RC_BILLING_REQUEST, mPurchaseFinishedListener, payload);
		  			    	  		        // =================================================================================== 
		  								} 
		  								// amazon
	  		   	  						else buy_amazon(buynow_item);

		  							} else if (menusystem.itemlist[i] >= 0 && allUpgrades.modifier[menusystem.itemlist[i]].fullgame && !fullgame) {
	  	  								if(sound)SoundManager.playSound(21,1);
	  	  								menusystem.unlock_prev = 9;
	  	        	  					menu = 7;
		  							} else {
		  								for (int k=0;k<spaceship.modifier_num;k++) {
		  									if (spaceship.modifier[k] >= 0 && menusystem.itemlist[i] >=0 ) {
		  										if (spaceship.modifier[k] == allUpgrades.modifier[menusystem.itemlist[i]].require) {
		  											spaceship.modifier[k] = -1;
		  										}
		  									}
		  								}
		  								spaceship.modifier[menusystem.upgrade_selected_modifier] = menusystem.itemlist[i];
		  								if (menusystem.itemlist[i]>=0) {
		  									if (!allUpgrades.modifier[menusystem.itemlist[i]].bought) {
		  										if(sound)SoundManager.playSound(2,1);
		  										allUpgrades.modifier[menusystem.itemlist[i]].bought = true;
		  										LY -= allUpgrades.modifier[menusystem.itemlist[i]].cost;
		  										lyspent += allUpgrades.modifier[menusystem.itemlist[i]].cost;
		  									} else { if(sound)SoundManager.playSound(18,1); }
		  								}
		  								menusystem.upgrade_selected_modifier = -1;
		  								menusystem.upgrade_selected = false;
		  								menusystem.upgrade_slot_selected = -1;
		  								Update_Ship_Properties();
		  							}
									break;
								}
								else if (menusystem.upgrade_selected_special >= 0) {
		  							if (menusystem.itemlist[i] >= 0 && !allUpgrades.special[menusystem.itemlist[i]].bought && !allUpgrades.special[menusystem.itemlist[i]].buycode.toString().equals("")) {
		  								if(sound)SoundManager.playSound(21,1);
		  								buynow_item = allUpgrades.special[menusystem.itemlist[i]].buycode.toString();
		  								if (isGoogle) {
		  			    	  				// GOOGLE IN-APP BILLING 3
		  			    	  				// ===================================================================================
		  			    	  		        Log.d(TAG_BILLING, "Launching purchase flow for purchasable.");
		  			    	  		        String payload = "";
		  			    	  		        mHelper.launchPurchaseFlow((Activity)context, buynow_item, RC_BILLING_REQUEST, mPurchaseFinishedListener, payload);
		  			    	  		        // =================================================================================== 
		  								}
		  								// amazon
	  		   	  						else buy_amazon(buynow_item);

		  							} else if (menusystem.itemlist[i] >= 0 && allUpgrades.special[menusystem.itemlist[i]].fullgame && !fullgame) {
	  	  								if(sound)SoundManager.playSound(21,1);
	  	  								menusystem.unlock_prev = 9;
	  	        	  					menu = 7;
		  							} else {
		  								for (int k=0;k<spaceship.special_num;k++) {
		  									if (spaceship.special[k] >= 0 && menusystem.itemlist[i] >=0 ) {
		  										if (spaceship.special[k] == allUpgrades.special[menusystem.itemlist[i]].require) {
		  											spaceship.special[k] = -1;
		  										}
		  									}
		  								}
		  								spaceship.special[menusystem.upgrade_selected_special] = menusystem.itemlist[i];
		  								if (menusystem.itemlist[i]>=0) {
		  									if (!allUpgrades.special[menusystem.itemlist[i]].bought) {
		  										if(sound)SoundManager.playSound(2,1);
		  										allUpgrades.special[menusystem.itemlist[i]].bought = true;
		  										LY -= allUpgrades.special[menusystem.itemlist[i]].cost;
		  										lyspent += allUpgrades.special[menusystem.itemlist[i]].cost;
		  									} else { if(sound)SoundManager.playSound(18,1); }
		  								}											
		  								menusystem.upgrade_selected_special = -1;
		  								menusystem.upgrade_selected = false;
		  								menusystem.upgrade_slot_selected = -1;
		  								Update_Ship_Properties();
		  							}
									break;
								}
								else if (menusystem.upgrade_selected_upgrade >= 0) {
		  							if (menusystem.itemlist[i] >= 0 && !allUpgrades.upgrade[menusystem.itemlist[i]].bought && !allUpgrades.upgrade[menusystem.itemlist[i]].buycode.toString().equals("")) {
		  								if(sound)SoundManager.playSound(21,1);
		  								buynow_item = allUpgrades.upgrade[menusystem.itemlist[i]].buycode.toString();
		  								if (isGoogle) {
		  			    	  				// GOOGLE IN-APP BILLING 3
		  			    	  				// ===================================================================================
		  			    	  		        Log.d(TAG_BILLING, "Launching purchase flow for purchasable.");
		  			    	  		        String payload = "";
		  			    	  		        mHelper.launchPurchaseFlow((Activity)context, buynow_item, RC_BILLING_REQUEST, mPurchaseFinishedListener, payload);
		  			    	  		        // =================================================================================== 
		  								}
		  								// amazon
	  		   	  						else buy_amazon(buynow_item);

		  							} else if (menusystem.itemlist[i] >= 0 && allUpgrades.upgrade[menusystem.itemlist[i]].fullgame && !fullgame) {
	  	  								if(sound)SoundManager.playSound(21,1);
	  	  								menusystem.unlock_prev = 9;
	  	        	  					menu = 7;
		  							} else {
		  								for (int k=0;k<spaceship.upgrade_num;k++) {
		  									if (spaceship.upgrade[k] >= 0 && menusystem.itemlist[i] >=0 ) {
		  										if (spaceship.upgrade[k] == allUpgrades.upgrade[menusystem.itemlist[i]].require) {
		  											spaceship.upgrade[k] = -1;
		  										}
		  									}
		  								}
		  								spaceship.upgrade[menusystem.upgrade_selected_upgrade] = menusystem.itemlist[i];
		  								if (menusystem.itemlist[i]>=0) {
		  									if (!allUpgrades.upgrade[menusystem.itemlist[i]].bought) {
		  										if(sound)SoundManager.playSound(2,1);
		  										allUpgrades.upgrade[menusystem.itemlist[i]].bought = true;
		  										LY -= allUpgrades.upgrade[menusystem.itemlist[i]].cost;
		  										lyspent += allUpgrades.upgrade[menusystem.itemlist[i]].cost;
		  									} else { if(sound)SoundManager.playSound(18,1); }
		  								}											
		  								menusystem.upgrade_selected_upgrade = -1;
		  								menusystem.upgrade_selected = false;
		  								menusystem.upgrade_slot_selected = -1;
		  								Update_Ship_Properties();
		  							}
									break;
								}
								else if (menusystem.upgrade_selected_ship >= 0) {
		  							if (menusystem.itemlist[i] >= 0 && !allUpgrades.ship[menusystem.itemlist[i]].bought && !allUpgrades.ship[menusystem.itemlist[i]].buycode.toString().equals("")) {
		  								if(sound)SoundManager.playSound(21,1);
		  								buynow_item = allUpgrades.ship[menusystem.itemlist[i]].buycode.toString();
		  								if (isGoogle) {
		  			    	  				// GOOGLE IN-APP BILLING 3
		  			    	  				// ===================================================================================
		  			    	  		        Log.d(TAG_BILLING, "Launching purchase flow for purchasable.");
		  			    	  		        String payload = "";
		  			    	  		        mHelper.launchPurchaseFlow((Activity)context, buynow_item, RC_BILLING_REQUEST, mPurchaseFinishedListener, payload);
		  			    	  		        // =================================================================================== 
		  								}
		  								// amazon
	  		   	  						else buy_amazon(buynow_item);

		  							} else if (menusystem.itemlist[i] >= 0 && allUpgrades.ship[menusystem.itemlist[i]].fullgame && !fullgame) {
	  	  								if(sound)SoundManager.playSound(21,1);
	  	  								menusystem.unlock_prev = 9;
	  	        	  					menu = 7;
		  							} else if (menusystem.itemlist[i] >= 0 && !allUpgrades.ship[menusystem.itemlist[i]].available && allUpgrades.ship[menusystem.itemlist[i]].show) {

		  							} else {
		  								spaceship.type = menusystem.itemlist[i];
		  								// update upgrade screen background
		  								menuupgrade_update = true;
		  								if (menusystem.itemlist[i]>=0) {
		  									if (!allUpgrades.ship[menusystem.itemlist[i]].bought) {
		  										if(sound)SoundManager.playSound(2,1);
		  										allUpgrades.ship[menusystem.itemlist[i]].bought = true;
		  										LY -= allUpgrades.ship[menusystem.itemlist[i]].cost;
		  									} else { if(sound)SoundManager.playSound(18,1); }
		  								}
		  								menusystem.upgrade_selected_upgrade = -1;
		  								menusystem.upgrade_selected = false;
		  								menusystem.upgrade_slot_selected = -1;
		  								Update_Ship_Properties();
		  							}
		  							break;
								}
								else if (menusystem.upgrade_selected_purchasable >= 0) {
		  							if (menusystem.itemlist[i] >= 0 && !allUpgrades.purchasable[menusystem.itemlist[i]].bought && !allUpgrades.purchasable[menusystem.itemlist[i]].buycode.toString().equals("")) {
		  								if(sound)SoundManager.playSound(21,1);
		  								buynow_item = allUpgrades.purchasable[menusystem.itemlist[i]].buycode.toString();
		  								if (buynow_item.equals("tapjoy")) {
		  									if (Main.tapjoy) {
		  				        	        	try { 
		  				        	        		TapjoyConnect.getTapjoyConnectInstance().showOffers();
		  				        	        		tapjoy_resume = true;
		  				        	        	} catch (Exception e) { e.printStackTrace(); } 
		  			        	  			}
		  								}
		  								else if (isGoogle) {
		  			    	  				// GOOGLE IN-APP BILLING 3
		  			    	  				// ===================================================================================
		  			    	  		        Log.d(TAG_BILLING, "Launching purchase flow for purchasable.");
		  			    	  		        String payload = "";
		  			    	  		        mHelper.launchPurchaseFlow((Activity)context, buynow_item, RC_BILLING_REQUEST, mPurchaseFinishedListener, payload);
		  			    	  		        // =================================================================================== 
		  								}
		  								// amazon
	  		   	  						else buy_amazon(buynow_item);

		  							} else {
		  								menusystem.upgrade_selected_upgrade = -1;
		  								menusystem.upgrade_selected = false;
		  								menusystem.upgrade_slot_selected = -1;
		  							}
		  							break;
								}
								
							}
						}
					}
  				}
				menusystem.upgrade_slot_selected = -1;
				
			
  			}

			
  		}
  		menusystem.upgrade_icon_pressed = false;
  		loading = false;
  		/*
  		if(scaleX > menusystem.back_bt[0] && scaleY > menusystem.back_bt[1] && scaleX < menusystem.back_bt[0]+menusystem.back_bt[2] && scaleY < menusystem.back_bt[1]+menusystem.back_bt[3]) {

  			menusystem.back_pressed = false;
	  		if (menusystem.upgrade_selected) {
	  			for (int o=0; o<6; o++) menusystem.itemlist[o] = -2;
	  			menusystem.upgrade_selected = false; 
	  			menusystem.upgrade_selected_weapon = -1;
	  			menusystem.upgrade_selected_modifier = -1;
	  			menusystem.upgrade_selected_special = -1;
	  			menusystem.upgrade_selected_upgrade = -1;
	  			menusystem.upgrade_selected_ship = -1;
	  			menusystem.upgrade_selected_purchasable = -1;
	  			menusystem.upgrade_page = 0;
	  			if(sound)SoundManager.playSound(22,1);
	  		} else {
	  			menusystem.selected_mission = -1;
	  			menusystem.dialogue_mission = true;
				menusystem.dialogue_galaxy = false;
				menusystem.dialogue_upgrade = false;
				menusystem.dialogue_game = false;
				galaxybg_update = true;
				if(sound)SoundManager.playSound(25,1);
				menu = 8;
	  		}
  		}*/
	}

	// amazon
	public void buy_amazon(String buynow_item) {
		
           final SharedPreferences settings = getSharedPreferencesForCurrentUser();
           boolean entitled = false;
           
           String amazon_item_id;
           if (hd > 1) amazon_item_id = "gshd_";
           else amazon_item_id = "gs_";
           
	           if (buynow_item.equals("sc2_modulator")) {entitled = settings.getBoolean(MODULATOR, false); amazon_item_id += "modulator"; }
	           else if (buynow_item.equals("sc2_7bullets")) { entitled = settings.getBoolean(BULLETS7, false); amazon_item_id += "7bullets"; }
	           else if (buynow_item.equals("sc2_9bullets")) { entitled = settings.getBoolean(BULLETS9, false); amazon_item_id += "9bullets"; }
	           else if (buynow_item.equals("sc2_chaosgun")) { entitled = settings.getBoolean(CHAOSGUN, false); amazon_item_id += "chaosgun"; }
	           else if (buynow_item.equals("sc2_wings")) { entitled = settings.getBoolean(WINGS, false); amazon_item_id += "wings"; }
	           else if (buynow_item.equals("sc2_fullversion")) { entitled = settings.getBoolean(FULL_VERSION, false); amazon_item_id += "fullversion"; }
	           else if (buynow_item.equals("sc2_timewarp")) { entitled = settings.getBoolean(TIMEWARP, false); amazon_item_id += "timewarp"; }
	           else if (buynow_item.equals("sc2_wave")) { entitled = settings.getBoolean(WAVE, false); amazon_item_id += "wave"; }
	           else if (buynow_item.equals("sc2_mines")) { entitled = settings.getBoolean(MINES, false); amazon_item_id += "mines"; }
	           else if (buynow_item.equals("sc2_cruiser")) { entitled = settings.getBoolean(CRUISER, false); amazon_item_id += "cruiser"; }
	           else if (buynow_item.equals("sc2_excelsior")) { entitled = settings.getBoolean(EXCELSIOR, false); amazon_item_id += "excelsior"; }
	           else if (buynow_item.equals("sc2_all")) { entitled = settings.getBoolean(ALL, false); amazon_item_id += "all"; }
	           else if (buynow_item.equals("sc2_ly1k")) { amazon_item_id += "ly1k"; }
	           else if (buynow_item.equals("sc2_ly2k")) { amazon_item_id += "ly2k"; }
	           else if (buynow_item.equals("sc2_ly5")) { amazon_item_id += "ly5"; }

	           if (!entitled) {
	               	//String requestId = PurchasingManager.initiatePurchaseRequest(buynow_item);
	        	   	String requestId = PurchasingManager.initiatePurchaseRequest(amazon_item_id);
		            if (buynow_item.equals("sc2_modulator")) storeRequestId(requestId, MODULATOR);
		            else if (buynow_item.equals("sc2_7bullets")) storeRequestId(requestId, BULLETS7);
		            else if (buynow_item.equals("sc2_9bullets")) storeRequestId(requestId, BULLETS9);
		            else if (buynow_item.equals("sc2_chaosgun")) storeRequestId(requestId, CHAOSGUN);
		            else if (buynow_item.equals("sc2_wings")) storeRequestId(requestId, WINGS);
		            else if (buynow_item.equals("sc2_fullversion")) storeRequestId(requestId, FULL_VERSION);
		            else if (buynow_item.equals("sc2_timewarp")) storeRequestId(requestId, TIMEWARP);
		            else if (buynow_item.equals("sc2_wave")) storeRequestId(requestId, WAVE);
		            else if (buynow_item.equals("sc2_mines")) storeRequestId(requestId, MINES);
		            else if (buynow_item.equals("sc2_cruiser")) storeRequestId(requestId, CRUISER);
		            else if (buynow_item.equals("sc2_excelsior")) storeRequestId(requestId, EXCELSIOR);
		            else if (buynow_item.equals("sc2_all")) storeRequestId(requestId, ALL);
		            else storeRequestId(requestId, NUM_LY);
		            Log.v("Amazon-IAP",
		            //String.format("Sending Request for Sku: %s Request ID: %s" + requestId, buynow_item, requestId));
		            String.format("Sending Request for Sku: %s Request ID: %s" + requestId, amazon_item_id, requestId));
	           }
	}

	
	// Tapjoy
	// Tapjoy
	// Tapjoy
	// Tapjoy
	// Tapjoy
	
	//================================================================================
	// CALLBACK Methods
	//================================================================================

	
	// This method must be implemented if using the TapjoyConnect.getTapPoints() method.
	// It is the callback method which contains the currency and points data.
	public void getUpdatePoints(String currencyName, int pointTotal)
	{
		Log.i("Tapjoy", "getUpdatePoints");
		Log.i("Tapjoy", "currencyName: " + currencyName);
		Log.i("Tapjoy", "pointTotal: " + pointTotal);
		
		//currency_name = currencyName;
		if (tapjoy_earned) {
			tapjoy_earned = false;
			tap_points = pointTotal;
			Log.i("Tapjoy", "----- points passed: " + tap_points);
			if (tap_points > 0) TapjoyConnect.getTapjoyConnectInstance().spendTapPoints(tap_points, this);
		}
		
		/*
		update_text = true;
		
		if (earnedPoints)
		{
			displayText = displayText + "\n" + currencyName + ": " + pointTotal;
			earnedPoints = false;
		}
		else
		{
			displayText = currencyName + ": " + pointTotal;
		}
		*/
		// We must use a handler since we cannot update UI elements from a different thread.
		//handler.post(mUpdateResults);
	}
	
	
	// This method must be implemented if using the TapjoyConnect.getTapPoints() method.
	// It is the callback method which contains the currency and points data.
	public void getUpdatePointsFailed(String error)
	{
		Log.i("Tapjoy", "getUpdatePointsFailed");
		Log.i("Tapjoy", "getTapPoints error: " + error);
		
		tap_points = 0;
		/*
		update_text = true;
		displayText = "Unable to retrieve tap points from server.";
		
		// We must use a handler since we cannot update UI elements from a different thread.
		handler.post(mUpdateResults);
		*/
	}

	
	// Notifier for when spending virtual currency succeeds.
	public void getSpendPointsResponse(String currencyName, int pointTotal)
	{
		Log.i("Tapjoy", "getSpendPointsResponse");
		Log.i("Tapjoy", "currencyName: " + currencyName);
		Log.i("Tapjoy", "pointTotal: " + pointTotal);

		/*
		int duration = Toast.LENGTH_SHORT;
		CharSequence text = "You have earned "+Integer.toString(pointTotal) + " Vitamins!";
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		*/
		
		LY += tap_points/* pointTotal*/;
		Save_Profile(4);
		tap_points = 0;
		/*
		update_text = true;
		displayText = currencyName + ": " + pointTotal;
		
		// We must use a handler since we cannot update UI elements from a different thread.
		handler.post(mUpdateResults);
		*/
	}

	
	// Notifier for when spending virtual currency fails.
	public void getSpendPointsResponseFailed(String error)
	{
		Log.e("Tapjoy", "spendTapPoints error: " + error);
		
		tap_points = 0;
		/*
		update_text = true;
		displayText = "Spend Tap Points: " + error;
		
		// We must use a handler since we cannot update UI elements from a different thread.
		handler.post(mUpdateResults);
		*/
	} 


    private void loginFacebook() {

    	try {
	    	// start Facebook Login
	        Session.openActiveSession((Activity)context, true, new Session.StatusCallback() {
	
	          // callback when session changes state
	          @Override
	          public void call(Session session, SessionState state, Exception exception) {
	            if (session.isOpened()) {
	
	              // make request to the /me API
	              Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
	
	                // callback after Graph API response with user object
	                @Override
	                public void onCompleted(GraphUser user, Response response) {
	                  if (user != null) {
	                    //TextView welcome = (TextView) findViewById(R.id.welcome);
	                	  Log.i("Facebook", "UserName: "+user.getName());
	                    //welcome.setText("Hello " + user.getName() + "!");
	                	  publishFeedDialog();
	                  }
	                }
	              });
	            }
	          }
	        });
	    } catch (Exception e) { 
	    	
	    }
    }
        
    /*
	 @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	  }
	 */

	 private void publishFeedDialog() {
		 
		 	String name = "";
		 	String caption = "";
		 	String description = "";
		 	
		 	if (sharefeed[0] == 1) {
		 		name = "Mission "+menusystem.mission[sharefeed[1]].name+" completed.";
		 		caption = "Reached "+Integer.toString(sharefeed[2])+" Light Years (LY) during the mission.";
		 		description = "Visited "+Integer.toString(sharefeed[3])+" Galaxies and completed "+Integer.toString(sharefeed[4])+" missions. Can you beat this?";
		 	} else {
		 		name = "Mission "+menusystem.mission[sharefeed[1]].name+" in progress.";
		 		caption = "Reached "+Integer.toString(sharefeed[2])+" Light Years (LY) during the mission.";
		 		description = "Visited "+Integer.toString(sharefeed[3])+" Galaxies and completed "+Integer.toString(sharefeed[4])+" missions. Can you beat this?";
		 	}

		    Bundle params = new Bundle();
		    params.putString("name", name);
		    params.putString("caption", caption);
		    params.putString("description", description);
		    if (hd > 1) {
			    if (isGoogle) {
			    	params.putString("link", "https://play.google.com/store/apps/details?id=com.tubigames.galaxy.shooter.hd");
			    } else {
			    	params.putString("link", "http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.galaxy.shooter.hd");
			    }
		    } else {
			    if (isGoogle) {
			    	params.putString("link", "https://play.google.com/store/apps/details?id=com.tubigames.galaxy.shooter");
			    } else {
			    	params.putString("link", "http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.galaxy.shooter");
			    }
		    }
		    params.putString("picture", "http://img824.imageshack.us/img824/6751/frdw.png");

		    Log.i("Facebook", "----- Opening Web Dialog...");
		    WebDialog feedDialog = (
		        new WebDialog.FeedDialogBuilder((Activity)context,Session.getActiveSession(),
		            params)).setOnCompleteListener(new OnCompleteListener() {

	            	//@Override
		            public void onComplete(Bundle values, FacebookException error) {
		                if (error == null) {
		                	
		                	/*
		                    // When the story is posted, echo the success
		                    // and the post Id.
		                    final String postId = values.getString("post_id");
		                    if (postId != null) {
		                        Toast.makeText(Main.this.getApplicationContext(),
		                            "Posted story, id: "+postId,
		                            Toast.LENGTH_SHORT).show();
		                    } else {
		                        // User clicked the Cancel button
		                        Toast.makeText(Main.this.getApplicationContext(), 
		                            "Publish cancelled", 
		                            Toast.LENGTH_SHORT).show();
		                    }
		                    */
		                	Log.i("Facebook", "Feed posted on wall successfully");

		                	
		                } else if (error instanceof FacebookOperationCanceledException) {
		                    
		                	/*
		                	// User clicked the "x" button
		                    Toast.makeText(Main.this.getApplicationContext(), 
		                        "Publish cancelled", 
		                        Toast.LENGTH_SHORT).show();
		                     */   
		                	Log.i("Facebook", "Cancel pressed");
		                        
		                } else {
		                	
		                	/*
		                    // Generic, ex: network error
		                    Toast.makeText(Main.this.getApplicationContext(), 
		                        "Error posting story", 
		                        Toast.LENGTH_SHORT).show();
		                    */
		                	Log.e("Facebook", "Feed not posted, error: "+error);
		                }
		                
		                
		            }

		        })
		        .build();
		    feedDialog.show();
		}

	 
		public void onSignInSucceeded() {
	        Log.i("Google Play Services", "Sign in Succeeded.");
	        signedin = true;
	        if (login_process == 1) {
            	try {
            		((Activity) context).startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), RC_GAME_REQUEST);
            	} catch (Exception e) { e.printStackTrace(); }
	        }
	        if (login_process == 2) {
            	try {
            		((Activity) context).startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), LEADERBOARD_ID), RC_GAME_REQUEST);
            	} catch (Exception e) { e.printStackTrace(); }
	        }
	        login_process = 0;
		    // (your code here: update UI, enable functionality that depends on sign in, etc)
	    }

		public void onSignInFailed() {
	        Log.i("Google Play Services", "Sign in Failed.");
	        signedin = false;
	        login_process = 0;
		} 
 

} 
