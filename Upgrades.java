package com.tubigames.galaxy.shooter.hd;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class Upgrades {

	float hd = OpenGLRenderer.hd;
	
	public class Turret
	{
		public String name = "";
		public String description = "";
		public String description2 = "";
		public int cost = 0;
		public boolean available = false;
		public boolean bought = false;
		public boolean fullgame = false;
		public String buycode = ""; 
		public int promo = -1;
		public My2DImage icon = new My2DImage((int)(hd*80), (int)(hd*80), true);
		public int bullet = 0;
		public int sound = 0;
		public boolean unstoppable = false;
		public float bulletspeed = 5.0f;
		public int damage = 0;
		public float rateoffire = 1;
		public int require = -1;
		public int followup = -1;
		public int layer = 0;
	}
	public class Modifier
	{
		public String name = "";
		public String description = "";
		public String description2 = "";
		public int cost = 0;
		public boolean available = false;
		public boolean bought = false;
		public String buycode = ""; 
		boolean fullgame = false;
		public int promo = -1;
		public My2DImage icon = new My2DImage((int)(hd*80), (int)(hd*80), true);
		public int require = -1;
		public int followup = -1;
		public int layer = 0;
	}
	public class Special
	{
		public String name = "";
		public String description = "";
		public String description2 = "";
		public int cost = 0;
		public boolean available = false;
		public boolean bought = false;
		public String buycode = ""; 
		boolean fullgame = false;
		public int promo = -1;
		public My2DImage icon = new My2DImage((int)(hd*80), (int)(hd*80), true);
		//public int image_num = 1;
		//public int[] image_id;
		//public int[] image_size = { 8, 8 };
		//public My2DImage[] image;
		public int sound = 0;
		public int vibration = -1;
		public int cooldown = 30;
		public int range = 0;
		public int damage = 0;
		public int duration = 0;
		public float speed = 0;
		public int bullet = 0;
		public int require = -1;
		public int followup = -1;
		public int layer = 0;
	}
	public class Upgrade
	{
		public String name = "";
		public String description = "";
		public String description2 = "";
		public int cost = 0;
		public boolean available = false;
		public boolean bought = false;
		public String buycode = ""; 
		boolean fullgame = false;
		public int promo = -1;
		public My2DImage icon = new My2DImage((int)(hd*80), (int)(hd*80), true);
		public int damage = 0;
		public int hp = 0;
		public int shield = 0;
		public float recharge = 0.0f;
		public float repair = 0.0f;
		public float speed = 0.0f;
		public float rof = 0.0f;
		public float lymod = 1.0f;
		public float lypickupmod = 1.0f;
		public float cdmod = 1.0f;
		public float collisionmod = 1.0f;
		public float bulletspeed = 0.0f;
		public float dropchance = 0.0f;
		public int crit = 0;
		public int require = -1;
		public int followup = -1;
		public int layer = 0;
	}
	
	public class Ship
	{
		public String name = "";
		public String description = "";
		public String description2 = "";
		public int cost = 0;
		public boolean available = false;
		public boolean show = true;
		public boolean bought = false;
		boolean fullgame = false;
		public String buycode = ""; 
		public int promo = -1;
		public int[] image_id = { 0, 0 };
		public int[] image_size = { (int)(hd*64), (int)(hd*64) };
		public My2DImage[] image = new My2DImage[2];
		public My2DImage icon = new My2DImage( (int)(hd*80), (int)(hd*80), true);
		public int turret_num = 4;
		//public int[] turret = { -1, -1, -1, -1 };
		public int[][] turret_oo = {
				{ 0, 0 }, // center
				{ 0, 0 }, // left forward
				{ 0, 0 }, // right forward
				{ 0, 0 }, // left
				{ 0, 0 }, // right
		};
		public int[][] wing_oo = {
				{ 0, 0 }, // center
				{ 0, 0 }, // left forward
		};
		public int modifier_num = 3;
		//public int[] modifier = { -1, -1, -1 };
		public int special_num = 4;
		//public int[] special = { -1, -1, -1, -1 };
		public int upgrade_num = 8;
		//public int[] upgrade = { -1, -1, -1, -1, -1, -1, -1, -1 };
		
		public int die_effect = 0;
		public int die_sound = 9;
		public int vibration = 1;
		public int width = (int)(hd*64);
		public int height = (int)(hd*64);
		public int[] size_mod = { 7, 7, 7, 7 };
		public int hp_base = 45;
		public int shield_base = 35;
		public float speed_base = 10.0f;
		public float shield_recharge_base = 4f;
		
		public int require = -1;
		public int followup = -1;
		public int layer = 0;
		
		public int particle_num = 1;
		public int[][] particle_pos = {
				{ 0, 0 },
				{ 0, 0 },
				{ 0, 0 },
		};
	};

	public class Purchasable
	{
		public String name = "";
		public String description = "";
		public String description2 = "";
		public int cost = 0;
		public My2DImage icon = new My2DImage((int)(hd*80), (int)(hd*80), true);
		public boolean available = false;
		public boolean bought = false;
		boolean fullgame = false;
		public String buycode = ""; 
		public int promo = -1;
		public int require = -1;
		public int followup = -1;
		public int layer = 0;
	};

	final int maxturret = 21;
	final int maxmodifier = 6;
	final int maxspecial = 48;
	final int maxupgrade = 52;
	final int maxship = 6;
	final int maxpurchasable = 5;
	
	Turret[] turret = new Turret[maxturret];
	Modifier[] modifier = new Modifier[maxmodifier];
	Special[] special = new Special[maxspecial];
	Upgrade[] upgrade = new Upgrade[maxupgrade];
	
	Ship ship[] = new Ship[maxship];
	Purchasable purchasable[] = new Purchasable[maxpurchasable];

	// fizetős cuccok
	boolean purchased_cruiser = false;
	boolean purchased_excelsior = false;
	boolean purchased_wings = false;
	boolean purchased_modulator = false;
	boolean purchased_7bullets = false;
	boolean purchased_timewarp = false;
	boolean purchased_wave = false;
	boolean purchased_mines = false;
	boolean purchased_9bullets = false;
	boolean purchased_chaosgun = false;
	
	public Upgrades() {

	}

	public void reset() {
		for (int i=0;i<maxturret;i++) {
			turret[i].bought = false;
			turret[i].available = true;
		}
		turret[0].bought = true;
		
		for (int i=0;i<maxmodifier;i++) {
			modifier[i].bought = false;
			modifier[i].available = true;
		}
		modifier[0].bought = true;
		
		//if (purchased_pentafire) modifier[12].bought = true;
		//if (purchased_wings) upgrade[51].bought = true;
		//if (purchased_hexafire) modifier[13].bought = true;
		//if (purchased_primeship) ship[5].bought = true;
		
		for (int i=0;i<maxspecial;i++) {
			special[i].bought = false;
			special[i].available = true;
		}
		special[0].bought = true;
		//special[3].bought = true;
		special[4].bought = true;
		special[7].bought = true;
		
		for (int i=0;i<maxupgrade;i++) {
			upgrade[i].bought = false;
			upgrade[i].available = true;
		}
		//upgrade[15].bought = true;
		
		for (int i=0;i<maxship;i++) {
			ship[i].bought = false;
			ship[i].available = true;
		}
		ship[0].bought = true;
		ship[1].available = false;
		
		//if (purchased_alienship) ship[3].bought = true;
		
		if (purchased_excelsior) ship[5].bought = true;
		if (purchased_cruiser) ship[4].bought = true;
		if (purchased_wings) upgrade[51].bought = true;
		if (purchased_7bullets) modifier[4].bought = true;
		if (purchased_modulator) turret[15].bought = true;
		if (purchased_timewarp) special[3].bought = true;
		if (purchased_wave) special[28].bought = true;
		if (purchased_mines) special[36].bought = true;
		if (purchased_9bullets) modifier[5].bought = true;
		if (purchased_chaosgun) turret[18].bought = true;

	}
	
	public void update(int unlock_state, boolean[] promo) {
		
		/*
		if (unlock_state >= 0) {
			turret[0].available = true; //laser1
			turret[1].available = true; //laser2
			turret[3].available = true; //plasma1
			modifier[0].available = true; //V
			modifier[1].available = true; //I
			//modifier[2].available = true; //II
			//modifier[3].available = true; //II
			//modifier[12].available = true; //pp
			special[7].available = true; //photon1
			special[8].available = true; //photon2
			special[10].available = true; //emp1
			special[22].available = true; //auto1
			upgrade[0].available = true; //titanium1
			upgrade[15].available = true; //shield1
			upgrade[18].available = true; //recharge1
			upgrade[21].available = true; //store1
			upgrade[9].available = true; //rof1 //elorehozva a 2-esbol
			ship[0].available = true; //explorer
			//modifier[12].available = true; //I
			ship[3].available = true; //
			special[45].available = true; // wing1
			
			// PURCHASABLE ITEMS
			turret[15].available = true; //plasma1
			modifier[4].available = true; //pp
			special[3].available = true; //booster
			special[28].available = true; //mine1
			special[36].available = true; //mine1
			ship[4].available = true; //explorer
			ship[5].available = true; //explorer
			upgrade[51].available = true; // wings

			
			//if (promo[modifier[8].promo]) modifier[8].available = true; //V extra
			//if (promo[special[28].promo]) special[28].available = true; //hedgehog1
			//if (promo[special[33].promo]) special[33].available = true; //crazy1

			special[33].available = true;
			
			upgrade[30].available = true; //crit1
			upgrade[36].available = true; //junk1
			upgrade[39].available = true; //gamma1

			special[0].available = true; //mining1
			special[4].available = true; //shieldb1

			special[39].available = true; //cryomod1
			special[42].available = true; //antirocket1
			
			//modifier[13].available = true; //explorer
			//special[48].available = true; //explorer
			special[31].available = true; //mining1
			special[32].available = true; //mining1


		}
		if (unlock_state >= 2) {
			turret[4].available = true; //plasma2
			turret[6].available = true; //phaser1
			turret[9].available = true; //gatling1
			//modifier[1].available = true; //II
			//modifier[5].available = true; //T
			special[13].available = true; //homing1
			special[23].available = true; //auto2
			upgrade[3].available = true; //speed1
			//upgrade[9].available = true; //rof1
			upgrade[24].available = true; //ly1
			upgrade[33].available = true; //evasion1
			
			special[34].available = true; //crazy2
			upgrade[42].available = true; //pilot1

			special[1].available = true; //mining2
		}
		
		if (unlock_state >= 3) {
			turret[7].available = true; //phaser2
			//turret[2].available = true; //laser3
			//turret[9].available = true; //gatling1
			modifier[2].available = true; //T
			//modifier[5].available = true; //W
			special[11].available = true; //emp2
			special[25].available = true; //cloak1
			upgrade[6].available = true; //dam1
			upgrade[16].available = true; //shield2
			upgrade[25].available = true; //ly2
			upgrade[27].available = true; //cd1
			
			special[29].available = true; //hedgehog2
			upgrade[45].available = true; //engineer1
			
			special[43].available = true; //antirocket2
			special[5].available = true; //shieldb2
		}
		
		if (unlock_state >= 4) {
			turret[2].available = true; //laser3
			//turret[12].available = true; //ion1
			//modifier[6].available = true; //K
			//modifier[7].available = true; //K
			//if (promo[modifier[9].promo]) modifier[9].available = true; //W extra
			special[14].available = true; //homing2
			special[19].available = true; //disrupt1
			upgrade[4].available = true; //speed2
			upgrade[12].available = true; //repair1
			upgrade[19].available = true; //recharge2
			upgrade[22].available = true; //store2
			upgrade[1].available = true; //titanium2
			
			//special[31].available = true; //crystal1
			special[35].available = true; //crazy3
			upgrade[37].available = true; //junk2
			upgrade[48].available = true; //commander1

			special[44].available = true; //antirocket3
			special[2].available = true; //mining3
			
			ship[2].available = true; //bounty

		}
		if (unlock_state >= 5) {
			turret[12].available = true; //ion1
			//modifier[4].available = true; //W
			turret[10].available = true; //gatling2
			//modifier[7].available = true; //E
			special[9].available = true; //photon3
			special[20].available = true; //disrupt2
			upgrade[10].available = true; //rof2
			upgrade[34].available = true; //evasion2
			
			//if (promo[modifier[9].promo]) modifier[9].available = true; //W extra
			upgrade[43].available = true; //pilot2
			special[37].available = true; //mine2
			upgrade[38].available = true; //junk3

			special[6].available = true; //shieldb3

		}
		if (unlock_state >= 6) {
			turret[5].available = true; //plasma3
			//turret[10].available = true; //gatling2
			modifier[3].available = true; //K
			//modifier[8].available = true; //LL
			special[16].available = true; //gravity1
			special[24].available = true; //auto3
			upgrade[5].available = true; //speed3
			upgrade[26].available = true; //ly3
			upgrade[28].available = true; //cd2
			upgrade[31].available = true; //crit2
			upgrade[17].available = true; //shield3
			special[46].available = true; // wing2
			special[30].available = true; //hedgehog3
			upgrade[46].available = true; //engineer2
		}
		
		if (unlock_state >= 7) {
			turret[8].available = true; //phaser3
			turret[13].available = true; //ion2
			//modifier[3].available = true; //E
			//if (promo[modifier[11].promo]) modifier[11].available = true; //VV
			//modifier[9].available = true; //L
			//modifier[10].available = true; //L
			special[12].available = true; //emp3
			special[26].available = true; //cloak2
			upgrade[7].available = true; //dam2
			upgrade[13].available = true; //repair2
			upgrade[23].available = true; //store3
			
			special[38].available = true; //mine3
			turret[15].available = true; //pulsar1
			//modifier[10].available = true; //LL
			//special[32].available = true; //crystal2
			upgrade[40].available = true; //gamma2
			upgrade[49].available = true; //commander2
		}
		if (unlock_state >= 8) {
			turret[11].available = true; //gatling3
			//modifier[11].available = true; //L
			special[15].available = true; //homing3
			special[17].available = true; //gravity2
			upgrade[2].available = true; //titanium3
			upgrade[11].available = true; //rof3
			upgrade[35].available = true; //evasion3
			ship[3].available = true; //dread
			
			turret[16].available = true; //pulsar2
			//if (promo[modifier[11].promo]) modifier[11].available = true; //VV
			upgrade[41].available = true; //gamma3
			upgrade[44].available = true; //pilot3
		}
		if (unlock_state >= 9) {
			turret[14].available = true; //ion3
			special[21].available = true; //disrupt3
			special[27].available = true; //cloak3
			upgrade[8].available = true; //dam3
			upgrade[20].available = true; //recharge3
			upgrade[29].available = true; //cd3
			special[47].available = true; // wing3
			
			special[40].available = true; //cryomod2
			upgrade[47].available = true; //engineer3
		}
		if (unlock_state >= 10) {
			special[18].available = true; //gravity3
			upgrade[14].available = true; //repair3
			upgrade[32].available = true; //crit3
			
			turret[17].available = true; //pulsar3
			special[41].available = true; //cryomod3
			upgrade[50].available = true; //commander3

			
		}
		*/
	}

	public void load_images(GL10 gl, Context context) {

	}
	
	public void load(GL10 gl, Context context) {
		
		// Laser Cannon
		turret[0] = new Turret();
		turret[0].name = "Heavy Laser Turret";
		turret[0].description = "Damage: 2, Fire Rate: 5";
		turret[0].description2 = "Bullet Speed: Normal";
		turret[0].bulletspeed = 12.0f;
		turret[0].rateoffire = 5f;
		turret[0].cost = 0;
		turret[0].icon.load(gl, context, R.drawable.icon_lasermk1);
		turret[0].bullet = 0;
		turret[0].sound = 14;
		turret[0].damage = 2;
		turret[0].require = -1;
		turret[0].followup = 1;

		// Laser Cannon II
		turret[1] = new Turret();
		turret[1].name = "Heavy Laser Turret II";
		turret[1].description = "Damage: 2, Fire Rate: 6";
		turret[1].description2 = "Bullet Speed: Normal";
		turret[1].bulletspeed = 12.0f;
		turret[1].rateoffire = 6.00f;
		turret[1].cost = 12000;
		turret[1].icon.load(gl, context, R.drawable.icon_lasermk1);
		turret[1].bullet = 0;
		turret[1].sound = 14;
		turret[1].damage = 2;
		turret[1].require = 0;
		turret[1].followup = 2;
		turret[1].layer = 1;
				
		// Laser Cannon III
		turret[2] = new Turret();
		turret[2].name = "Heavy Laser Turret III";
		turret[2].description = "Damage: 6, Fire Rate: 6";
		turret[2].description2 = "Bullet Speed: Normal";
		turret[2].bulletspeed = 12.0f;
		turret[2].rateoffire = 6.00f;
		turret[2].cost = 28000;
		turret[2].icon.load(gl, context, R.drawable.icon_lasermk1);
		turret[2].bullet = 0;
		turret[2].sound = 14;
		turret[2].damage = 6;
		turret[2].require = 1;
		turret[2].followup = -1;
		turret[2].layer = 2;
		
		// Plasma Gun
		turret[3] = new Turret();
		turret[3].name = "Rail Gun";
		turret[3].description = "Damage: 5, Fire Rate: 2,25";
		turret[3].description2 = "Bullet Speed: Slow";
		turret[3].bulletspeed = 10.0f;
		turret[3].rateoffire = 2.25f;
		turret[3].cost = 15000;
		turret[3].icon.load(gl, context, R.drawable.icon_plasma1);
		turret[3].bullet = 2;
		turret[3].sound = 17;
		turret[3].damage = 5;
		turret[3].require = -1;
		turret[3].followup = 4;

		// Plasma Gun II
		turret[4] = new Turret();
		turret[4].name = "Rail Gun II";
		turret[4].description = "Damage: 10, Fire Rate: 2,25";
		turret[4].description2 = "Bullet Speed: Slow";
		turret[4].bulletspeed = 10.0f;
		turret[4].rateoffire = 2.25f;
		turret[4].cost = 25000;
		turret[4].icon.load(gl, context, R.drawable.icon_plasma1);
		turret[4].bullet = 2;
		turret[4].sound = 17;
		turret[4].damage = 10;
		turret[4].require = 3;
		turret[4].followup = 5;
		turret[4].layer = 1;
		
		// Plasma Gun III
		turret[5] = new Turret();
		turret[5].name = "Rail Gun III";
		turret[5].description = "Damage: 12, Fire Rate: 3,30";
		turret[5].description2 = "Bullet Speed: Slow";
		turret[5].bulletspeed = 10.0f;
		turret[5].rateoffire = 3.3f;
		turret[5].cost = 60000;
		turret[5].icon.load(gl, context, R.drawable.icon_plasma1);
		turret[5].bullet = 2;
		turret[5].sound = 17;
		turret[5].damage = 12;
		turret[5].require = 4;
		turret[5].followup = -1;
		turret[5].layer = 2;

		// Phaser
		turret[6] = new Turret();
		turret[6].name = "Plasma Beam Cannon";
		turret[6].description = "Damage: 5, Fire Rate: 3,75";
		turret[6].description2 = "Bullet Speed: Normal";
		turret[6].bulletspeed = 12.0f;
		turret[6].rateoffire = 3.75f;
		turret[6].cost = 25000;
		turret[6].icon.load(gl, context, R.drawable.icon_phaser1);
		turret[6].bullet = 3;
		turret[6].sound = 15;
		turret[6].damage = 5;
		turret[6].require = -1;
		turret[6].followup = 7;

		// Phaser II
		turret[7] = new Turret();
		turret[7].name = "Plasma Beam Cannon II";
		turret[7].description = "Damage: 8, Fire Rate: 3,75";
		turret[7].description2 = "Bullet Speed: Normal";
		turret[7].bulletspeed = 12.0f;
		turret[7].rateoffire = 3.75f;
		turret[7].cost = 35000;
		turret[7].icon.load(gl, context, R.drawable.icon_phaser1);
		turret[7].bullet = 3;
		turret[7].sound = 15;
		turret[7].damage = 8;
		turret[7].require = 6;
		turret[7].followup = 8;
		turret[7].layer = 1;

		// Phaser III
		turret[8] = new Turret();
		turret[8].name = "Plasma Beam Cannon III";
		turret[8].description = "Damage: 10, Fire Rate: 4,50";
		turret[8].description2 = "Bullet Speed: Normal";
		turret[8].bulletspeed = 12.0f;
		turret[8].rateoffire = 4.5f;
		turret[8].cost = 100000;
		turret[8].icon.load(gl, context, R.drawable.icon_phaser1);
		turret[8].bullet = 3;
		turret[8].sound = 15;
		turret[8].damage = 10;
		turret[8].require = 7;
		turret[8].followup = -1;
		turret[8].layer = 2;

		// Gatling Laser
		turret[9] = new Turret();
		turret[9].name = "Fusion Blaster";
		turret[9].description = "Damage: 3, Fire Rate: 6";
		turret[9].description2 = "Bullet Speed: Fast";
		turret[9].bulletspeed = 12.00f;
		turret[9].rateoffire = 6.0f;
		turret[9].cost = 21000;
		turret[9].icon.load(gl, context, R.drawable.icon_gatlinglaser1);
		turret[9].bullet = 42;
		turret[9].sound = 10;
		turret[9].damage = 3;
		turret[9].require = -1;
		turret[9].followup = 10;
		turret[9].fullgame = true;
		
		// Gatling Laser II
		turret[10] = new Turret();
		turret[10].name = "Fusion Blaster II";
		turret[10].description = "Damage: 5, Fire Rate: 8";
		turret[10].description2 = "Bullet Speed: Fast";
		turret[10].bulletspeed = 12f;
		turret[10].rateoffire = 8f;
		turret[10].cost = 83000;
		turret[10].icon.load(gl, context, R.drawable.icon_gatlinglaser1);
		turret[10].bullet = 42;
		turret[10].sound = 10;
		turret[10].damage = 5;
		turret[10].require = 9;
		turret[10].followup = 11;
		turret[10].layer = 1;

		// Gatling Laser III
		turret[11] = new Turret();
		turret[11].name = "Fusion Blaster III";
		turret[11].description = "Damage: 10, Fire Rate: 8";
		turret[11].description2 = "Bullet Speed: Fast";
		turret[11].bulletspeed = 12f;
		turret[11].rateoffire = 8f;
		turret[11].cost = 105000;
		turret[11].icon.load(gl, context, R.drawable.icon_gatlinglaser1);
		turret[11].bullet = 42;
		turret[11].sound = 10;
		turret[11].damage = 10;
		turret[11].require = 10;
		turret[11].followup = -1;
		turret[11].fullgame = true;
		turret[11].layer = 2;

		// Ion Cannon
		turret[12] = new Turret();
		turret[12].name = "Particle Cannon";
		turret[12].description = "Damage: 18, Fire Rate: 2";
		turret[12].description2 = "Bullet Speed: Normal";
		turret[12].bulletspeed = 12.0f;
		turret[12].rateoffire = 2f;
		turret[12].cost = 90000;
		turret[12].icon.load(gl, context, R.drawable.icon_ion_cannon1);
		turret[12].bullet = 4;
		turret[12].sound = 13;
		turret[12].damage = 18;
		turret[12].require = -1;
		turret[12].followup = 13;
		turret[12].fullgame = true;

		// Ion Cannon II
		turret[13] = new Turret();
		turret[13].name = "Particle Cannon II";
		turret[13].description = "Damage: 24, Fire Rate: 2,5";
		turret[13].description2 = "Bullet Speed: Normal";
		turret[13].bulletspeed = 12.0f;
		turret[13].rateoffire = 2.5f;
		turret[13].cost = 115000;
		turret[13].icon.load(gl, context, R.drawable.icon_ion_cannon1);
		turret[13].bullet = 4;
		turret[13].sound = 13;
		turret[13].damage = 24;
		turret[13].require = 12;
		turret[13].followup = 14;
		turret[13].layer = 1;

		// Ion Cannon III
		turret[14] = new Turret();
		turret[14].name = "Particle Cannon III";
		turret[14].description = "Damage: 25, Fire Rate: 3";
		turret[14].description2 = "Bullet Speed: Normal";
		turret[14].bulletspeed = 12.0f;
		turret[14].rateoffire = 3f;
		turret[14].cost = 195000;
		turret[14].icon.load(gl, context, R.drawable.icon_ion_cannon1);
		turret[14].bullet = 4;
		turret[14].sound = 13;
		turret[14].damage = 25;
		turret[14].require = 13;
		turret[14].followup = -1;
		turret[14].fullgame = true;
		turret[14].layer = 2;

		// Pulsar
		turret[15] = new Turret();
		turret[15].name = "Illudium PU-72 Modulator";
		turret[15].description = "Damage: 14, Fire Rate: 6";
		turret[15].description2 = "Bullet Speed: Very Fast";
		turret[15].bulletspeed = 12.0f;
		turret[15].rateoffire = 6f;
		turret[15].cost = 100000;
		turret[15].icon.load(gl, context, R.drawable.icon_pulsar1);
		turret[15].bullet = 10;
		turret[15].sound = 34;
		//turret[15].unstoppable = true;
		turret[15].damage = 14;
		turret[15].require = -1;
		turret[15].followup = 16;
		turret[15].buycode = "sc2_modulator";
		turret[15].layer = 3;	

		// Pulsar II
		turret[16] = new Turret();
		turret[16].name = "Illudium PU-72 Modulator II";
		turret[16].description = "Damage: 18, Fire Rate: 6";
		turret[16].description2 = "Bullet Speed: Very Fast";
		turret[16].bulletspeed = 12.0f;
		turret[16].rateoffire = 6f;
		turret[16].cost = 120000;
		turret[16].icon.load(gl, context, R.drawable.icon_pulsar1);
		turret[16].bullet = 10;
		turret[16].sound = 34;
		//turret[16].unstoppable = true;
		turret[16].damage = 18;
		turret[16].require = 15;
		turret[16].followup = 17;
		turret[16].layer = 1;

		// Pulsar III
		turret[17] = new Turret();
		turret[17].name = "Illudium PU-72 Modulator III";
		turret[17].description = "Damage: 20, Fire Rate: 6";
		turret[17].description2 = "Bullet Speed: Very Fast";
		turret[17].bulletspeed = 12.0f;
		turret[17].rateoffire = 6f;
		turret[17].cost = 215000;
		turret[17].icon.load(gl, context, R.drawable.icon_pulsar1);
		turret[17].bullet = 10;
		turret[17].sound = 34;
		//turret[17].unstoppable = true;
		turret[17].damage = 20;
		turret[17].require = 16;
		turret[17].followup = -1;
		turret[17].layer = 2;

		// Chaos Gun
		turret[18] = new Turret();
		turret[18].name = "Chaos Gun 'Devastator'";
		turret[18].description = "Damage: 20, Fire Rate: 6";
		turret[18].description2 = "Bullet Speed: Ultimate";
		turret[18].bulletspeed = 12.0f;
		turret[18].rateoffire = 6f;
		turret[18].cost = 1000000;
		turret[18].icon.load(gl, context, R.drawable.icon_chaosgun);
		turret[18].bullet = 43;
		turret[18].sound = 34;
		//turret[18].unstoppable = true;
		turret[18].damage = 20;
		turret[18].require = -1;
		turret[18].followup = 19;
		turret[18].buycode = "sc2_chaosgun";
		turret[18].layer = 3;	 
		
		// Chaos Gun
		turret[19] = new Turret();
		turret[19].name = "Chaos Gun 'Devastator' II";
		turret[19].description = "Damage: 22, Fire Rate: 6";
		turret[19].description2 = "Bullet Speed: Ultimate";
		turret[19].bulletspeed = 12.0f;
		turret[19].rateoffire = 6f;
		turret[19].cost = 1500000;
		turret[19].icon.load(gl, context, R.drawable.icon_chaosgun);
		turret[19].bullet = 43;
		turret[19].sound = 34;
		//turret[18].unstoppable = true;
		turret[19].damage = 22;
		turret[19].require = 18;
		turret[19].followup = 20;
		//turret[19].buycode = "shooter2_weapon1";
		turret[19].layer = 1;	 
		
		// Chaos Gun
		turret[20] = new Turret();
		turret[20].name = "Chaos Gun 'Devastator' III";
		turret[20].description = "Damage: 22, Fire Rate: 7";
		turret[20].description2 = "Bullet Speed: Ultimate";
		turret[20].bulletspeed = 12.0f;
		turret[20].rateoffire = 7f;
		turret[20].cost = 2000000;
		turret[20].icon.load(gl, context, R.drawable.icon_chaosgun);
		turret[20].bullet = 43;
		turret[20].sound = 34;
		//turret[20].unstoppable = true;
		turret[20].damage = 22;
		turret[20].require = 19;
		turret[20].followup = -1;
		//turret[20].buycode = "shooter2_weapon1";
		turret[20].layer = 2;	

		// II-Fire
		modifier[0] = new Modifier();
		modifier[0].name = "Double Dynamite";
		modifier[0].description = "Dual Firing Mode";
		modifier[0].cost = 0;
		modifier[0].icon.load(gl, context, R.drawable.icon_twinmod);

		// W-Fire
		modifier[1] = new Modifier();
		modifier[1].name = "Going Wild";
		modifier[1].description = "Triple Firing Mode";
		modifier[1].cost = 35000;
		modifier[1].icon.load(gl, context, R.drawable.icon_wfiremod);

		// IVI-Fire
		modifier[2] = new Modifier();
		modifier[2].name = "Fire In The Sky";
		modifier[2].description = "Quad Firing Mode";
		modifier[2].cost = 180000;
		modifier[2].icon.load(gl, context, R.drawable.icon_lfire);

		// EW-Fire
		modifier[3] = new Modifier();
		modifier[3].name = "Hell Breaks Loose";
		//else modifier[11].name = "Hell Breaks Loose";
		modifier[3].description = "Pentuple Firing Mode";
		modifier[3].cost = 300000;
		modifier[3].icon.load(gl, context, R.drawable.icon_evfire);
		
		//modifier[11].buycode = "sc2_6bullets";

		// WIW-Fire
		modifier[4] = new Modifier();
		modifier[4].name = "Death From Above";
		modifier[4].description = "7 Bullets. Thats it.";
		modifier[4].cost = 500000;
		modifier[4].icon.load(gl, context, R.drawable.icon_7shoot);
		modifier[4].buycode = "sc2_7bullets";
		modifier[4].layer = 3;

		modifier[5] = new Modifier();
		modifier[5].name = "Angel Fire";
		modifier[5].description = "9 Bullets. All dead.";
		modifier[5].cost = 1000000;
		modifier[5].icon.load(gl, context, R.drawable.icon_angelfire);
		modifier[5].buycode = "sc2_9bullets";
		modifier[5].layer = 3; 

		// Mining Laser
		special[0] = new Special();
		special[0].name = "Mining Beam";
		special[0].description = "Mine Asteroid to collect LY.";
		special[0].description2 = "Mining time: 8 sec.";
		special[0].cost = 5000;
		special[0].icon.load(gl, context, R.drawable.icon_mininglaser1);
		special[0].sound = 31;
		special[0].duration = 8;
		special[0].cooldown = 7;
		special[0].require = -1;
		special[0].followup = 1;

		// Mining Laser II
		special[1] = new Special();
		special[1].name = "Mining Beam II";
		special[1].description = "Mine Asteroid to collect LY.";
		special[1].description2 = "Mining time: 6 sec.";
		special[1].cost = 20000;
		special[1].icon.load(gl, context, R.drawable.icon_mininglaser1);
		special[1].sound = 31;
		special[1].duration = 6;
		special[1].cooldown = 7;
		special[1].require = 0;
		special[1].followup = 2;
		special[1].layer = 1;

		// Mining Laser III
		special[2] = new Special();
		special[2].name = "Mining Beam III";
		special[2].description = "Mine Asteroid to collect LY.";
		special[2].description2 = "Mining time: 4 sec.";
		special[2].cost = 30000;
		special[2].icon.load(gl, context, R.drawable.icon_mininglaser1);
		special[2].sound = 31;
		special[2].duration = 4;
		special[2].cooldown = 7;
		special[2].require = 1;
		special[2].followup = -1;
		special[2].layer = 2;

		// Speed Booster
		special[3] = new Special();
		special[3].name = "Time Warp";
		special[3].description = "Slow down everything.";
		special[3].description2 = "Duration: 10s, Cooldown: 30s.";
		special[3].cost = 0;
		special[3].icon.load(gl, context, R.drawable.icon_timewarp);
		special[3].sound = 24;
		special[3].duration = 10;
		special[3].cooldown = 30;
		special[3].require = -1;
		special[3].followup = -1;
		special[3].layer = 3;
		special[3].buycode = "sc2_timewarp";
		special[3].layer = 3;

		// Instant Shield
		special[4] = new Special();
		special[4].name = "Instant Shield";
		special[4].description = "Add 10 Max Shield for 30 sec.";
		special[4].description2 = "Recharge 10 Shield. Cd:35 sec.";
		special[4].cost = 5000;
		special[4].icon.load(gl, context, R.drawable.icon_instantshield1);
		special[4].sound = 30;
		special[4].duration = 25;
		special[4].cooldown = 45;
		special[4].require = -1;
		special[4].followup = 5;
		
		// Instant Shield II
		special[5] = new Special();
		special[5].name = "Instant Shield II";
		special[5].description = "Add 10 Max Shield for 30 sec.";
		special[5].description2 = "Recharge 10 Shield. Cd:30 sec.";
		special[5].cost = 25000;
		special[5].icon.load(gl, context, R.drawable.icon_instantshield1);
		special[5].sound = 30;
		special[5].duration = 30;
		special[5].cooldown = 30;
		special[5].require = 4;
		special[5].followup = 6;
		special[5].layer = 1;

		// Instant Shield III
		special[6] = new Special();
		special[6].name = "Instant Shield III";
		special[6].description = "Add 10 Max Shield for 30 sec.";
		special[6].description2 = "Recharge 10 Shield. Cd:25 sec.";
		special[6].cost = 55000;
		special[6].icon.load(gl, context, R.drawable.icon_instantshield1);
		special[6].sound = 30;
		special[6].duration = 30;
		special[6].cooldown = 20;
		special[6].require = 5;
		special[6].followup = -1;
		special[6].layer = 2;

		// Photon Torpedo
		special[7] = new Special();
		special[7].name = "Photon Torpedos";
		special[7].description = "Damage:3x25, Cooldown:18 sec";
		special[7].cost = 6000;
		special[7].icon.load(gl, context, R.drawable.icon_photontorpedo1);
		special[7].damage = 25;
		//special[7].bullet = 6;
		special[7].bullet = 6;
		special[7].sound = 16;
		special[7].speed = 6;
		special[7].cooldown = 18;
		special[7].require = -1;
		special[7].followup = 8;

		// Photon Torpedo II
		special[8] = new Special();
		special[8].name = "Photon Torpedos II";
		special[8].description = "Damage:3x50, Cooldown:14 sec";
		special[8].cost = 18000;
		special[8].icon.load(gl, context, R.drawable.icon_photontorpedo1);
		special[8].damage = 50;
		special[8].bullet = 6;
		special[8].sound = 16;
		special[8].speed = 6;
		special[8].cooldown = 14;
		special[8].require = 7;
		special[8].followup = 9;
		special[8].layer = 1;

		// Photon Torpedo III
		special[9] = new Special();
		special[9].name = "Photon Torpedos III";
		special[9].description = "Damage:3x100, Cooldown:10 sec";
		special[9].cost = 60000;
		special[9].icon.load(gl, context, R.drawable.icon_photontorpedo1);
		special[9].damage = 100;
		special[9].bullet = 6;
		special[9].sound = 16;
		special[9].speed = 6;
		special[9].cooldown = 10;
		special[9].require = 8;
		special[9].followup = -1;
		special[9].layer = 2;

		// EMP
		special[10] = new Special();
		special[10].name = "EMP";
		special[10].description = "Stop enemy ships for 3 sec";
		special[10].description2 = "Cooldown: 35 sec";
		special[10].cost = 22000;
		special[10].icon.load(gl, context, R.drawable.icon_emp1);
		special[10].damage = 0;
		special[10].sound = 5;
		special[10].duration = 3000;
		special[10].cooldown = 35;
		special[10].require = -1;
		special[10].followup = 11;
		
		// EMP II
		special[11] = new Special();
		special[11].name = "EMP II";
		special[11].description = "Stop enemy ships for 5 sec";
		special[11].description2 = "Cooldown: 30 sec";
		special[11].cost = 65000;
		special[11].icon.load(gl, context, R.drawable.icon_emp1);
		special[11].damage = 0;
		special[11].sound = 5;
		special[11].duration = 5000;
		special[11].cooldown = 30;
		special[11].require = 10;
		special[11].followup = 12;
		special[11].layer = 1;

		// EMP III
		special[12] = new Special();
		special[12].name = "EMP III";
		special[12].description = "Stop enemy ships for 6 sec";
		special[12].description2 = "Cooldown: 25 sec";
		special[12].cost = 105000;
		special[12].icon.load(gl, context, R.drawable.icon_emp1);
		special[12].damage = 0;
		special[12].sound = 5;
		special[12].duration = 6000;
		special[12].cooldown = 25;
		special[12].require = 11;
		special[12].followup = -1;
		special[12].layer = 2;

		// Homing Missile
		special[13] = new Special();
		special[13].name = "Homing Missile";
		special[13].description = "Damage: 25, always hit";
		special[13].description2 = "Cooldown: 20 sec";
		special[13].cost = 22000;
		special[13].icon.load(gl, context, R.drawable.icon_homingmissile1);
		special[13].damage = 25;
		special[13].cooldown = 20;
		special[13].speed = 5;
		special[13].bullet = 0;
		special[13].sound = 12;
		special[13].require = -1;
		special[13].followup = 14;
		
		// Homing Missile II
		special[14] = new Special();
		special[14].name = "Homing Missile II";
		special[14].description = "Damage: 50, always hit";
		special[14].description2 = "Cooldown: 16 sec";
		special[14].cost = 32000;
		special[14].icon.load(gl, context, R.drawable.icon_homingmissile1);
		special[14].damage = 50;
		special[14].cooldown = 16;
		special[14].speed = 5;
		special[14].bullet = 0;
		special[14].sound = 12;
		special[14].require = 13;
		special[14].followup = 15;
		special[14].layer = 1;

		// Homing Missile III
		special[15] = new Special();
		special[15].name = "Homing Missile III";
		special[15].description = "Damage: 100, always hit";
		special[15].description2 = "Cooldown: 12 sec";
		special[15].cost = 105000;
		special[15].icon.load(gl, context, R.drawable.icon_homingmissile1);
		special[15].damage = 100;
		special[15].cooldown = 12;
		special[15].speed = 5;
		special[15].bullet = 0;
		special[15].sound = 12;
		special[15].require = 14;
		special[15].followup = -1;
		special[15].fullgame = true;
		special[15].layer = 2;

		// Gravity Gun
		special[16] = new Special();
		special[16].name = "Gravity Gun";
		special[16].description = "Pull enemies to center";
		special[16].description2 = "Duration: 3 sec, Cd: 40 sec";
		special[16].cost = 70000;
		special[16].icon.load(gl, context, R.drawable.icon_gravitygun1);
		special[16].damage = 0;
		special[16].sound = 11;
		special[16].duration = 3000;
		special[16].cooldown = 40;
		special[16].require = -1;
		special[16].followup = 17;
		
		// Gravity Gun II
		special[17] = new Special();
		special[17].name = "Gravity Gun II";
		special[17].description = "Pull enemies to center";
		special[17].description2 = "Duration: 5 sec, Cd: 35 sec";
		special[17].cost = 100000;
		special[17].icon.load(gl, context, R.drawable.icon_gravitygun1);
		special[17].damage = 0;
		special[17].sound = 11;
		special[17].duration = 5000;
		special[17].cooldown = 35;
		special[17].require = 16;
		special[17].followup = 18;
		special[17].layer = 1;

		// Gravity Gun III
		special[18] = new Special();
		special[18].name = "Gravity Gun III";
		special[18].description = "Pull enemies to center";
		special[18].description2 = "Duration: 6 sec, Cd: 30 sec";
		special[18].cost = 170000;
		special[18].icon.load(gl, context, R.drawable.icon_gravitygun1);
		special[18].damage = 0;
		special[18].sound = 11;
		special[18].duration = 6000;
		special[18].cooldown = 30;
		special[18].require = 17;
		special[18].followup = -1;
		special[18].layer = 2;

		// Disruptor
		special[19] = new Special();
		special[19].name = "Disruptor";
		special[19].description = "Cone Damage: 30, Cd: 22 sec";
		special[19].cost = 40000;
		special[19].icon.load(gl, context, R.drawable.icon_disruptor1);
		special[19].damage = 30;
		special[19].sound = 4;
		special[19].range = 120;
		special[19].duration = 1500;
		special[19].cooldown = 22;
		special[19].require = -1;
		special[19].followup = 20;
	
		// Disruptor II
		special[20] = new Special();
		special[20].name = "Disruptor II";
		special[20].description = "Cone Damage: 50, Cd: 18 sec";
		special[20].cost = 55000;
		special[20].icon.load(gl, context, R.drawable.icon_disruptor1);
		special[20].damage = 50;
		special[20].sound = 4;
		special[20].range = 120;
		special[20].duration = 1500;
		special[20].cooldown = 18;
		special[20].require = 19;
		special[20].followup = 21;
		special[20].layer = 1;

		// Disruptor III
		special[21] = new Special();
		special[21].name = "Disruptor III";
		special[21].description = "Cone Damage: 80, Cd: 15 sec";
		special[21].cost = 170000;
		special[21].icon.load(gl, context, R.drawable.icon_disruptor1);
		special[21].damage = 80;
		special[21].sound = 4;
		special[21].range = 120;
		special[21].duration = 1500;
		special[21].cooldown = 15;
		special[21].require = 20;
		special[21].followup = -1;
		special[21].layer = 2;

		// Auto Turret
		special[22] = new Special();
		special[22].name = "Auto Turret";
		special[22].description = "Enable Auto-Fire for 15 sec";
		special[22].description2 = "Cooldown: 25 sec";
		special[22].cost = 9000;
		special[22].icon.load(gl, context, R.drawable.icon_autoturret1);
		special[22].damage = 0;
		special[22].sound = 1;
		special[22].duration = 15000;
		special[22].cooldown = 25;
		special[22].require = -1;
		special[22].followup = 23;

		// Auto Turret II
		special[23] = new Special();
		special[23].name = "Auto Turret II";
		special[23].description = "Enable Auto-Fire for 30 sec";
		special[23].description2 = "Cooldown: 40 sec";
		special[23].cost = 15000;
		special[23].icon.load(gl, context, R.drawable.icon_autoturret1);
		special[23].damage = 0;
		special[23].sound = 1;
		special[23].duration = 30000;
		special[23].cooldown = 40;
		special[23].require = 22;
		special[23].followup = 24;
		special[23].layer = 1;

		// Auto Turret III
		special[24] = new Special();
		special[24].name = "Auto Turret III";
		special[24].description = "Enable or Disable Auto-Fire";
		special[24].description2 = "Cooldown: 0 sec";
		special[24].cost = 45000;
		special[24].icon.load(gl, context, R.drawable.icon_autoturret1);
		special[24].damage = 0;
		special[24].sound = 1;
		special[24].cooldown = 0;
		special[24].require = 23;
		special[24].followup = -1;
		special[24].layer = 2;

		// Cloaking Device
		special[25] = new Special();
		special[25].name = "Cloaking Device";
		special[25].description = "Damage Immunity for 5 sec";
		special[25].description2 = "Cooldown: 70 sec";
		special[25].cost = 30000;
		special[25].icon.load(gl, context, R.drawable.icon_cloakingdevice1);
		special[25].damage = 0;
		special[25].sound = 3;
		special[25].duration = 5000;
		special[25].cooldown = 70;
		special[25].require = -1;
		special[25].followup = 26;
		//special[25].layer = 1;

		// Cloaking Device II
		special[26] = new Special();
		special[26].name = "Cloaking Device II";
		special[26].description = "Damage Immunity for 7 sec";
		special[26].description2 = "Cooldown: 65 sec";
		special[26].cost = 85000;
		special[26].icon.load(gl, context, R.drawable.icon_cloakingdevice1);
		special[26].damage = 0;
		special[26].sound = 3;
		special[26].duration = 7000;
		special[26].cooldown = 65;
		special[26].require = 25;
		special[26].followup = 27;
		special[26].layer = 1;

		// Cloaking Device III
		special[27] = new Special();
		special[27].name = "Cloaking Device III";
		special[27].description = "Damage Immunity for 8 sec";
		special[27].description2 = "Cooldown: 60 sec";
		special[27].cost = 185000;
		special[27].icon.load(gl, context, R.drawable.icon_cloakingdevice1);
		special[27].damage = 0;
		special[27].sound = 3;
		special[27].duration = 8000;
		special[27].cooldown = 60;
		special[27].require = 26;
		special[27].followup = -1;
		special[27].layer = 2;

		// P Wave
		special[28] = new Special();
		special[28].name = "Particle Wave";
		special[28].description = "Shoot waves to all directions";
		special[28].description2 = "Cooldown: 18 sec";
		special[28].cost = 15000;
		special[28].icon.load(gl, context, R.drawable.icon_particlewave);
		special[28].damage = 25;
		special[28].bullet = 39;
		special[28].sound = 14;
		special[28].cooldown = 18;
		special[28].require = -1;
		special[28].followup = 29;
		special[28].buycode = "sc2_wave";
		special[28].layer = 3;

		// P Wave II
		special[29] = new Special();
		special[29].name = "Particle Wave II";
		special[29].description = "Shoot waves to all directions";
		special[29].description2 = "Cooldown: 15 sec";
		special[29].cost = 45000;
		special[29].icon.load(gl, context, R.drawable.icon_particlewave);
		special[29].damage = 40;
		special[29].bullet = 39;
		special[29].sound = 15;
		special[29].cooldown = 15;
		special[29].require = 28;
		special[29].followup = 30;
		special[29].layer = 1;

		// P Wave III
		special[30] = new Special();
		special[30].name = "Particle Wave III";
		special[30].description = "Shoot waves to all directions";
		special[30].description2 = "Cooldown: 12 sec";
		special[30].cost = 90000;
		special[30].icon.load(gl, context, R.drawable.icon_particlewave);
		special[30].damage = 60;
		special[30].bullet = 39;
		special[30].sound = 13;
		special[30].cooldown = 12;
		special[30].require = 29;
		special[30].followup = -1;
		special[30].layer = 2;

		// Crystal of Destruction
		special[31] = new Special();
		special[31].name = "Crystal of Destruction";
		special[31].description = "Damage 200 to all enemies";
		special[31].description2 = "Consumed when used";
		special[31].cost = 10000;
		special[31].icon.load(gl, context, R.drawable.icon_crystalofdestruction);
		special[31].damage = 200;
		special[31].sound = 35;
		special[31].require = -1;
		special[31].followup = -1;

		// Crystal of Power
		special[32] = new Special();
		special[32].name = "Crystal of Power";
		special[32].description = "Recharge 30 hull and 30 shield";
		special[32].description2 = "Consumed when used";
		special[32].cost = 10000;
		special[32].icon.load(gl, context, R.drawable.icon_crystalofpower);
		special[32].damage = 30;
		special[32].sound = 33;
		special[32].require = -1;
		special[32].followup = -1;

		// Crazy Missile
		special[33] = new Special();
		special[33].name = "Crazy Missile";
		special[33].description = "Missile to random directions";
		special[33].description2 = "Damage: 40, Cooldown: 24 sec";
		special[33].cost = 9000;
		special[33].icon.load(gl, context, R.drawable.icon_crazymissile1);
		special[33].damage = 40;
		special[33].sound = 27;
		special[33].cooldown = 24;
		special[33].require = -1;
		special[33].followup = 34;
		//special[33].layer = 1;

		// Crazy Missile II
		special[34] = new Special();
		special[34].name = "Crazy Missile II";
		special[34].description = "Missile to random directions";
		special[34].description2 = "Damage: 70, Cooldown: 18 sec";
		special[34].cost = 46000;
		special[34].icon.load(gl, context, R.drawable.icon_crazymissile1);
		special[34].damage = 70;
		special[34].sound = 27;
		special[34].cooldown = 18;
		special[34].require = 33;
		special[34].followup = 35;
		special[34].layer = 1;

		// Crazy Missile III
		special[35] = new Special();
		special[35].name = "Crazy Missile III";
		special[35].description = "Missile to random directions";
		special[35].description2 = "Damage: 120, Cooldown: 12 sec";
		special[35].cost = 55000;
		special[35].icon.load(gl, context, R.drawable.icon_crazymissile1);
		special[35].damage = 120;
		special[35].sound = 27;
		special[35].cooldown = 12;
		special[35].require = 34;
		special[35].followup = -1;
		special[35].layer = 2;

		// Deploy Mine
		special[36] = new Special();
		special[36].name = "Deploy Mines";
		special[36].description = "Deploy pack of laser mines";
		special[36].description2 = "Speed: medium, Cd: 20 sec";
		special[36].cost = 19000;
		special[36].icon.load(gl, context, R.drawable.icon_deploymine1);
		special[36].damage = 10;
		special[36].bullet = 35;
		special[36].sound = 29;
		special[36].speed = 0.5f;
		special[36].cooldown = 20;
		special[36].require = -1;
		special[36].followup = 37;
		special[36].buycode = "sc2_mines";
		special[36].layer = 3;

		// Deploy Mine II
		special[37] = new Special();
		special[37].name = "Deploy Mines II";
		special[37].description = "Deploy pack of laser mines";
		special[37].description2 = "Speed: slow, Cd: 15 sec";
		special[37].cost = 40000;
		special[37].icon.load(gl, context, R.drawable.icon_deploymine1);
		special[37].damage = 12;
		special[37].bullet = 35;
		special[37].sound = 29;
		special[37].speed = 0.35f;
		special[37].cooldown = 15;
		special[37].require = 36;
		special[37].followup = 38;
		special[37].layer = 1;

		// Deploy Mine III
		special[38] = new Special();
		special[38].name = "Deploy Mines III";
		special[38].description = "Deploy pack of laser mines";
		special[38].description2 = "Speed:very slow, Cd:10 sec";
		special[38].cost = 85000;
		special[38].icon.load(gl, context, R.drawable.icon_deploymine1);
		special[38].damage = 14;
		special[38].bullet = 35;
		special[38].sound = 29;
		special[38].speed = 0.2f;
		special[38].cooldown = 10;
		special[38].require = 37;
		special[38].followup = -1;
		special[38].layer = 2;

		// Cryogenic Modulator
		special[39] = new Special();
		special[39].name = "Cryogenic Modulator";
		special[39].description = "Reduces all cooldown time";
		special[39].description2 = "by 10 sec, Cooldown: 70 sec";
		special[39].cost = 35000;
		special[39].icon.load(gl, context, R.drawable.icon_cryogenic_modulator1);
		special[39].sound = 28;
		special[39].damage = 10;
		special[39].cooldown = 70;
		special[39].require = -1;
		special[39].followup = 40;
		special[39].fullgame = true;

		// Cryogenic Modulator II
		special[40] = new Special();
		special[40].name = "Cryogenic Mod. II";
		special[40].description = "Reduces all cooldown time";
		special[40].description2 = "by 20 sec, Cooldown: 65 sec";
		special[40].cost = 175000;
		special[40].icon.load(gl, context, R.drawable.icon_cryogenic_modulator1);
		special[40].sound = 28;
		special[40].damage = 20;
		special[40].cooldown = 65;
		special[40].require = 39;
		special[40].followup = 41;
		special[40].fullgame = true;
		special[40].layer = 1;

		// Cryogenic Modulator III
		special[41] = new Special();
		special[41].name = "Cryogenic Mod. III";
		special[41].description = "Reduces all cooldown time";
		special[41].description2 = "by 30 sec, Cooldown: 60 sec";
		special[41].cost = 195000;
		special[41].icon.load(gl, context, R.drawable.icon_cryogenic_modulator1);
		special[41].sound = 28;
		special[41].damage = 30;
		special[41].cooldown = 60;
		special[41].require = 40;
		special[41].followup = -1;
		special[41].fullgame = true;
		special[41].layer = 2;

		// Guardian
		special[42] = new Special();
		special[42].name = "Guardian";
		special[42].description = "Destroy incoming bullets";
		special[42].description2 = "Cooldown: 25 sec";
		special[42].cost = 8000;
		special[42].icon.load(gl, context, R.drawable.icon_antirocket1);
		special[42].sound = 26;
		special[42].damage = 0;
		special[42].cooldown = 25;
		special[42].require = -1;
		special[42].followup = 43;
		special[42].fullgame = true;

		// Guardian II
		special[43] = new Special();
		special[43].name = "Guardian II";
		special[43].description = "Destroy incoming bullets";
		special[43].description2 = "Cooldown: 18 sec";
		special[43].cost = 16000;
		special[43].icon.load(gl, context, R.drawable.icon_antirocket1);
		special[43].sound = 26;
		special[43].damage = 0;
		special[43].cooldown = 18;
		special[43].require = 42;
		special[43].followup = 44;
		special[43].fullgame = true;
		special[43].layer = 1;

		// Guardian III
		special[44] = new Special();
		special[44].name = "Guardian III";
		special[44].description = "Destroy incoming bullets";
		special[44].description2 = "Cooldown: 12 sec";
		special[44].cost = 28000;
		special[44].icon.load(gl, context, R.drawable.icon_antirocket1);
		special[44].sound = 26;
		special[44].damage = 0;
		special[44].cooldown = 12;
		special[44].require = 43;
		special[44].followup = -1;
		special[44].fullgame = true;
		special[44].layer = 2;

		// Wing Modul I
		special[45] = new Special();
		special[45].name = "Wing Modul";
		special[45].description = "Add 2 side turrets for 8 sec";
		special[45].description2 = "Cooldown: 35 sec";
		special[45].cost = 25000;
		special[45].icon.load(gl, context, R.drawable.icon_wingmodul1);
		special[45].sound = 3;
		special[45].bullet = 1;
		special[45].damage = 2;
		special[45].cooldown = 35;
		special[45].duration = 8;
		special[45].require = -1;
		special[45].followup = 46;
		//special[45].fullgame = true;

		// Wing Modul II
		special[46] = new Special();
		special[46].name = "Wing Modul II";
		special[46].description = "Add 2 side turrets for 10 sec";
		special[46].description2 = "Cooldown: 30 sec";
		special[46].cost = 95000;
		special[46].icon.load(gl, context, R.drawable.icon_wingmodul1);
		special[46].sound = 3;
		special[46].bullet = 1;
		special[46].damage = 4;
		special[46].cooldown = 30;
		special[46].duration = 10;
		special[46].require = 45;
		special[46].followup = 47;
		special[46].layer = 1;

		// Wing Modul III
		special[47] = new Special();
		special[47].name = "Wing Modul III";
		special[47].description = "Add 2 side turrets for 12 sec";
		special[47].description2 = "Cooldown: 25 sec";
		special[47].cost = 195000;
		special[47].icon.load(gl, context, R.drawable.icon_wingmodul1);
		special[47].sound = 3;
		special[47].bullet = 1;
		special[47].damage = 6;
		special[47].cooldown = 25;
		special[47].duration = 12;
		special[47].require = 46;
		special[47].followup = -1;
		special[47].layer = 2;
		
		// Titanium Armor
		upgrade[0] = new Upgrade();
		upgrade[0].name = "Titanium Armor";
		upgrade[0].description = "+5 Hull";
		upgrade[0].cost = 18000;
		upgrade[0].icon.load(gl, context, R.drawable.icon_titanium_armor1);
		upgrade[0].hp = 5;
		upgrade[0].require = -1;
		upgrade[0].followup = 1;

		// Titanium Armor II
		upgrade[1] = new Upgrade();
		upgrade[1].name = "Titanium Armor II";
		upgrade[1].description = "+10 Hull";
		upgrade[1].cost = 50000;
		upgrade[1].icon.load(gl, context, R.drawable.icon_titanium_armor1);
		upgrade[1].hp = 10;
		upgrade[1].require = 0;
		upgrade[1].followup = 2;
		upgrade[1].layer = 1;

		// Titanium Armor III
		upgrade[2] = new Upgrade();
		upgrade[2].name = "Titanium Armor III";
		upgrade[2].description = "+15 Hull";
		upgrade[2].cost = 130000;
		upgrade[2].icon.load(gl, context, R.drawable.icon_titanium_armor1);
		upgrade[2].hp = 15;
		upgrade[2].require = 1;
		upgrade[2].followup = -1;
		upgrade[2].fullgame = true;
		upgrade[2].layer = 2;

		// Ion Engine
		upgrade[3] = new Upgrade();
		upgrade[3].name = "Ion Engine";
		upgrade[3].description = "+1 Speed";
		upgrade[3].cost = 18000;
		upgrade[3].icon.load(gl, context, R.drawable.icon_ionengine1);
		upgrade[3].speed = 1.0f;
		upgrade[3].require = -1;
		upgrade[3].followup = 4;
		//upgrade[3].layer = 1;

		// Ion Engine II
		upgrade[4] = new Upgrade();
		upgrade[4].name = "Ion Engine II";
		upgrade[4].description = "+3 Speed";
		upgrade[4].cost = 22000;
		upgrade[4].icon.load(gl, context, R.drawable.icon_ionengine1);
		upgrade[4].speed = 3.0f;
		upgrade[4].require = 3;
		upgrade[4].followup = 5;
		upgrade[4].layer = 1;

		// Ion Engine III
		upgrade[5] = new Upgrade();
		upgrade[5].name = "Ion Engine III";
		upgrade[5].description = "+5 Speed";
		upgrade[5].cost = 40000;
		upgrade[5].icon.load(gl, context, R.drawable.icon_ionengine1);
		upgrade[5].speed = 5.0f;
		upgrade[5].require = 4;
		upgrade[5].followup = -1;
		upgrade[5].layer = 2;

		// Energy Optimizer
		upgrade[6] = new Upgrade();
		upgrade[6].name = "Energy Optimizer";
		upgrade[6].description = "+1 Damage";
		upgrade[6].cost = 25000;
		upgrade[6].icon.load(gl, context, R.drawable.icon_energyoptimizer1);
		upgrade[6].damage = 1;
		upgrade[6].require = -1;
		upgrade[6].followup = 7;

		// Energy Optimizer II
		upgrade[7] = new Upgrade();
		upgrade[7].name = "Energy Optimizer II";
		upgrade[7].description = "+2 Damage";
		upgrade[7].cost = 80000;
		upgrade[7].icon.load(gl, context, R.drawable.icon_energyoptimizer1);
		upgrade[7].damage = 2;
		upgrade[7].require = 6;
		upgrade[7].followup = 8;
		upgrade[7].layer = 1;

		// Energy Optimizer III
		upgrade[8] = new Upgrade();
		upgrade[8].name = "Energy Optimizer III";
		upgrade[8].description = "+3 Damage";
		upgrade[8].cost = 190000;
		upgrade[8].icon.load(gl, context, R.drawable.icon_energyoptimizer1);
		upgrade[8].damage = 3;
		upgrade[8].require = 7;
		upgrade[8].followup = -1;
		upgrade[8].layer = 2;

		// Rapid Reloader
		upgrade[9] = new Upgrade();
		upgrade[9].name = "Enhanced Reloader";
		upgrade[9].description = "+0,25 Rate of Fire";
		upgrade[9].cost = 25000;
		upgrade[9].icon.load(gl, context, R.drawable.icon_rapid_reloader1);
		upgrade[9].rof = 0.25f;
		upgrade[9].require = -1;
		upgrade[9].followup = 10;

		// Rapid Reloader II
		upgrade[10] = new Upgrade();
		upgrade[10].name = "Enhanced Reloader II";
		upgrade[10].description = "+0,5 Rate of Fire";
		upgrade[10].cost = 45000;
		upgrade[10].icon.load(gl, context, R.drawable.icon_rapid_reloader1);
		upgrade[10].rof = 0.5f;
		upgrade[10].require = 9;
		upgrade[10].followup = 11;
		upgrade[10].layer = 1;

		// Rapid Reloader III
		upgrade[11] = new Upgrade();
		upgrade[11].name = "Enhanced Reloader III";
		upgrade[11].description = "+0,75 Rate of Fire";
		upgrade[11].cost = 110000;
		upgrade[11].icon.load(gl, context, R.drawable.icon_rapid_reloader1);
		upgrade[11].rof = 0.75f;
		upgrade[11].require = 10;
		upgrade[11].followup = -1;
		upgrade[11].layer = 2;

		// Repair Drone
		upgrade[12] = new Upgrade();
		upgrade[12].name = "Repair Drone";
		upgrade[12].description = "Auto Repair Hull";
		upgrade[12].description2 = "+1 Hull every 10 sec";
		upgrade[12].cost = 35000;
		upgrade[12].icon.load(gl, context, R.drawable.icon_repairdrone1);
		upgrade[12].repair = 2.0f;
		upgrade[12].require = -1;
		upgrade[12].followup = 13;
		upgrade[12].fullgame = true;

		// Repair Drone II
		upgrade[13] = new Upgrade();
		upgrade[13].name = "Repair Drone II";
		upgrade[13].description = "Auto Repair Hull";
		upgrade[13].description2 = "+1 Hull every 7 sec";
		upgrade[13].cost = 80000;
		upgrade[13].icon.load(gl, context, R.drawable.icon_repairdrone1);
		upgrade[13].repair = 3.0f;
		upgrade[13].require = 12;
		upgrade[13].followup = 14;
		upgrade[13].fullgame = true;
		upgrade[13].layer = 1;

		// Repair Drone III
		upgrade[14] = new Upgrade();
		upgrade[14].name = "Repair Drone III";
		upgrade[14].description = "Auto Repair Hull";
		upgrade[14].description2 = "+1 Hull every 5 sec";
		upgrade[14].cost = 195000;
		upgrade[14].icon.load(gl, context, R.drawable.icon_repairdrone1);
		upgrade[14].repair = 4.0f;
		upgrade[14].require = 13;
		upgrade[14].followup = -1;
		upgrade[14].fullgame = true;
		upgrade[14].layer = 2;

		// Plasma Shield
		upgrade[15] = new Upgrade();
		upgrade[15].name = "Plasma Shield";
		upgrade[15].description = "+10 Shield";
		//upgrade[15].description2 = "+4 Shield Recharge";
		upgrade[15].cost = 25000;
		upgrade[15].icon.load(gl, context, R.drawable.icon_plasma_shell1);
		upgrade[15].shield = 10;
		//upgrade[15].recharge = 4.0f;
		upgrade[15].require = -1;
		upgrade[15].followup = 16;

		// Plasma Shield II
		upgrade[16] = new Upgrade();
		upgrade[16].name = "Plasma Shield II";
		upgrade[16].description = "+15 Shield";
		//upgrade[16].description2 = "+4 Shield Recharge";
		upgrade[16].cost = 35000;
		upgrade[16].icon.load(gl, context, R.drawable.icon_plasma_shell1);
		upgrade[16].shield = 15;
		//upgrade[16].recharge = 4.0f;
		upgrade[16].require = 15;
		upgrade[16].followup = 17;
		upgrade[16].layer = 1;

		// Plasma Shield III
		upgrade[17] = new Upgrade();
		upgrade[17].name = "Plasma Shield III";
		upgrade[17].description = "+20 Shield";
		//upgrade[17].description2 = "+4 Shield Recharge";
		upgrade[17].cost = 100000;
		upgrade[17].icon.load(gl, context, R.drawable.icon_plasma_shell1);
		upgrade[17].shield = 20;
		//upgrade[17].recharge = 4.0f;
		upgrade[17].require = 16;
		upgrade[17].followup = -1;
		upgrade[17].layer = 2;

		// Shield Recharger
		upgrade[18] = new Upgrade();
		upgrade[18].name = "Shield Recharger";
		upgrade[18].description = "+2 Shield Recharge";
		upgrade[18].cost = 25000;
		upgrade[18].icon.load(gl, context, R.drawable.icon_shield_recharger1);
		upgrade[18].recharge = 2.0f;
		upgrade[18].require = -1;
		upgrade[18].followup = 19;

		// Shield Recharger II
		upgrade[19] = new Upgrade();
		upgrade[19].name = "Shield Recharger II";
		upgrade[19].description = "+4 Shield Recharge";
		upgrade[19].cost = 30000;
		upgrade[19].icon.load(gl, context, R.drawable.icon_shield_recharger1);
		upgrade[19].recharge = 4.0f;
		upgrade[19].require = 18;
		upgrade[19].followup = 20;
		upgrade[19].layer = 1;

		// Shield Recharger III
		upgrade[20] = new Upgrade();
		upgrade[20].name = "Shield Recharger III";
		upgrade[20].description = "+6 Shield Recharge";
		upgrade[20].cost = 180000;
		upgrade[20].icon.load(gl, context, R.drawable.icon_shield_recharger1);
		upgrade[20].recharge = 6.0f;
		upgrade[20].require = 19;
		upgrade[20].followup = -1;
		upgrade[20].layer = 2;

		// Storage Bay
		upgrade[21] = new Upgrade();
		upgrade[21].name = "Storage Bay";
		upgrade[21].description = "+25 percent LY from pickups";
		upgrade[21].cost = 26000;
		upgrade[21].icon.load(gl, context, R.drawable.icon_storage_bay1);
		upgrade[21].lypickupmod = 1.25f;
		upgrade[21].require = -1;
		upgrade[21].followup = 22;
		
		// Storage Bay II
		upgrade[22] = new Upgrade();
		upgrade[22].name = "Storage Bay II";
		upgrade[22].description = "+37 percent LY from pickups";
		upgrade[22].cost = 42000;
		upgrade[22].icon.load(gl, context, R.drawable.icon_storage_bay1);
		upgrade[22].lypickupmod = 1.37f;
		upgrade[22].require = 21;
		upgrade[22].followup = 23;
		upgrade[22].layer = 1;
		
		// Storage Bay III
		upgrade[23] = new Upgrade();
		upgrade[23].name = "Storage Bay III";
		upgrade[23].description = "+50 percent LY from pickups";
		upgrade[23].cost = 75000;
		upgrade[23].icon.load(gl, context, R.drawable.icon_storage_bay1);
		upgrade[23].lypickupmod = 1.5f;
		upgrade[23].require = 22;
		upgrade[23].followup = -1;
		upgrade[23].layer = 2;

		// Anti-Matter Scanner
		upgrade[24] = new Upgrade();
		upgrade[24].name = "Anti-Matter Scanner";
		upgrade[24].description = "+10 percent LY after each kill";
		upgrade[24].cost = 25000;
		upgrade[24].icon.load(gl, context, R.drawable.icon_antimatter_scanner1);
		upgrade[24].lymod = 1.1f;
		upgrade[24].require = -1;
		upgrade[24].followup = 25;
	
		// Anti-Matter Scanner II
		upgrade[25] = new Upgrade();
		upgrade[25].name = "Anti-Matter Scanner II";
		upgrade[25].description = "+17 percent LY after each kill";
		upgrade[25].cost = 35000;
		upgrade[25].icon.load(gl, context, R.drawable.icon_antimatter_scanner1);
		upgrade[25].lymod = 1.17f;
		upgrade[25].require = 24;
		upgrade[25].followup = 26;
		upgrade[25].layer = 1;

		// Anti-Matter Scanner III
		upgrade[26] = new Upgrade();
		upgrade[26].name = "Anti-Matter Scanner III";
		upgrade[26].description = "+25 percent LY after each kill";
		upgrade[26].cost = 70000;
		upgrade[26].icon.load(gl, context, R.drawable.icon_antimatter_scanner1);
		upgrade[26].lymod = 1.25f;
		upgrade[26].require = 25;
		upgrade[26].followup = -1;
		upgrade[26].layer = 2;

		// Cryogenic Pumps 
		upgrade[27] = new Upgrade();
		upgrade[27].name = "Cryogenic Pumps";
		upgrade[27].description = "-10 percent cooldown time";
		upgrade[27].cost = 42000;
		upgrade[27].icon.load(gl, context, R.drawable.icon_cryogenic_pumps1);
		upgrade[27].cdmod = 1.1f;
		upgrade[27].require = -1;
		upgrade[27].followup = 28;

		// Cryogenic Pumps II
		upgrade[28] = new Upgrade();
		upgrade[28].name = "Cryogenic Pumps II";
		upgrade[28].description = "-15 percent cooldown time";
		upgrade[28].cost = 65000;
		upgrade[28].icon.load(gl, context, R.drawable.icon_cryogenic_pumps1);
		upgrade[28].cdmod = 1.15f;
		upgrade[28].require = 27;
		upgrade[28].followup = 29;
		upgrade[28].layer = 1;

		// Cryogenic Pumps III
		upgrade[29] = new Upgrade();
		upgrade[29].name = "Cryogenic Pumps III";
		upgrade[29].description = "-20 percent cooldown time";
		upgrade[29].cost = 160000;
		upgrade[29].icon.load(gl, context, R.drawable.icon_cryogenic_pumps1);
		upgrade[29].cdmod = 1.2f;
		upgrade[29].require = 28;
		upgrade[29].followup = -1;
		upgrade[29].layer = 2;

		// Weak Spot Locator
		upgrade[30] = new Upgrade();
		upgrade[30].name = "Weak Spot Locator";
		upgrade[30].description = "10 percent critical chance";
		upgrade[30].cost = 22000;
		upgrade[30].icon.load(gl, context, R.drawable.icon_weakspot_locator1);
		upgrade[30].crit = 10;
		upgrade[30].require = -1;
		upgrade[30].followup = 31;
		upgrade[30].fullgame = true;

		// Weak Spot Locator II
		upgrade[31] = new Upgrade();
		upgrade[31].name = "Weak Spot Locator II";
		upgrade[31].description = "15 percent critical chance";
		upgrade[31].cost = 65000;
		upgrade[31].icon.load(gl, context, R.drawable.icon_weakspot_locator1);
		upgrade[31].crit = 15;
		upgrade[31].require = 30;
		upgrade[31].followup = 32;
		upgrade[31].fullgame = true;
		upgrade[31].layer = 1;

		// Weak Spot Locator III
		upgrade[32] = new Upgrade();
		upgrade[32].name = "Weak Spot Locator III";
		upgrade[32].description = "20 percent critical chance";
		upgrade[32].cost = 150000;
		upgrade[32].icon.load(gl, context, R.drawable.icon_weakspot_locator1);
		upgrade[32].crit = 20;
		upgrade[32].require = 31;
		upgrade[32].followup = -1;
		upgrade[32].fullgame = true;
		upgrade[32].layer = 2;

		// Auto-Evasion System
		upgrade[33] = new Upgrade();
		upgrade[33].name = "Auto-Evasion System";
		upgrade[33].description = "-15 percent collision damage";
		upgrade[33].cost = 35000;
		upgrade[33].icon.load(gl, context, R.drawable.icon_autoevasion_system1);
		upgrade[33].collisionmod = 1.15f;
		upgrade[33].require = -1;
		upgrade[33].followup = 34;

		// Auto-Evasion System II
		upgrade[34] = new Upgrade();
		upgrade[34].name = "Auto-Ev. System II";
		upgrade[34].description = "-22 percent collision damage";
		upgrade[34].cost = 50000;
		upgrade[34].icon.load(gl, context, R.drawable.icon_autoevasion_system1);
		upgrade[34].collisionmod = 1.22f;
		upgrade[34].require = 33;
		upgrade[34].followup = 35;
		upgrade[34].layer = 1;

		// Auto-Evasion System III
		upgrade[35] = new Upgrade();
		upgrade[35].name = "Auto-Ev. System III";
		upgrade[35].description = "-30 percent collision damage";
		upgrade[35].cost = 100000;
		upgrade[35].icon.load(gl, context, R.drawable.icon_autoevasion_system1);
		upgrade[35].collisionmod = 1.30f;
		upgrade[35].require = 34;
		upgrade[35].followup = -1;
		upgrade[35].layer = 2;

		// Junk Collector
		upgrade[36] = new Upgrade();
		upgrade[36].name = "Junk Collector";
		upgrade[36].description = "+3 percent item drop chance";
		upgrade[36].cost = 20000;
		upgrade[36].icon.load(gl, context, R.drawable.icon_junkcollector1);
		upgrade[36].dropchance = 3.0f;
		upgrade[36].require = -1;
		upgrade[36].followup = 37;
		upgrade[36].fullgame = true;
		
		// Junk Collector II
		upgrade[37] = new Upgrade();
		upgrade[37].name = "Junk Collector II";
		upgrade[37].description = "+4 percent item drop chance";
		upgrade[37].cost = 30000;
		upgrade[37].icon.load(gl, context, R.drawable.icon_junkcollector1);
		upgrade[37].dropchance = 4.0f;
		upgrade[37].require = 36;
		upgrade[37].followup = 38;
		upgrade[37].fullgame = true;
		upgrade[37].layer = 1;

		// Junk Collector III
		upgrade[38] = new Upgrade();
		upgrade[38].name = "Junk Collector III";
		upgrade[38].description = "+5 percent item drop chance";
		upgrade[38].cost = 60000;
		upgrade[38].icon.load(gl, context, R.drawable.icon_junkcollector1);
		upgrade[38].dropchance = 5.0f;
		upgrade[38].require = 37;
		upgrade[38].followup = -1;
		upgrade[38].fullgame = true;
		upgrade[38].layer = 2;

		// Gamma Booster
		upgrade[39] = new Upgrade();
		upgrade[39].name = "Gamma Booster";
		upgrade[39].description = "+1 bullet speed";
		upgrade[39].cost = 25000;
		upgrade[39].icon.load(gl, context, R.drawable.icon_gammabooster1);
		upgrade[39].bulletspeed = 1.0f;
		upgrade[39].require = -1;
		upgrade[39].followup = 40;
		upgrade[39].fullgame = true;
		
		// Gamma Booster II
		upgrade[40] = new Upgrade();
		upgrade[40].name = "Gamma Booster II";
		upgrade[40].description = "+2 bullet speed";
		upgrade[40].cost = 90000;
		upgrade[40].icon.load(gl, context, R.drawable.icon_gammabooster1);
		upgrade[40].bulletspeed = 2.0f;
		upgrade[40].require = 39;
		upgrade[40].followup = 41;
		upgrade[40].fullgame = true;
		upgrade[40].layer = 1;

		// Gamma Booster III
		upgrade[41] = new Upgrade();
		upgrade[41].name = "Gamma Booster III";
		upgrade[41].description = "+3 bullet speed";
		upgrade[41].cost = 125000;
		upgrade[41].icon.load(gl, context, R.drawable.icon_gammabooster1);
		upgrade[41].bulletspeed = 3.0f;
		upgrade[41].require = 40;
		upgrade[41].followup = -1;
		upgrade[41].fullgame = true;
		upgrade[41].layer = 2;

		// Pilot
		upgrade[42] = new Upgrade();
		upgrade[42].name = "Elite Pilot";
		upgrade[42].description = "+1 speed, -10p collision dmg";
		upgrade[42].description2 = "+4 percent LY after each kill";
		upgrade[42].cost = 35000;
		upgrade[42].icon.load(gl, context, R.drawable.icon_pilot1);
		upgrade[42].speed = 1.0f;
		upgrade[42].collisionmod = 1.1f;
		upgrade[42].lymod = 1.04f;
		upgrade[42].require = -1;
		upgrade[42].followup = 43;
		upgrade[42].fullgame = true;
		
		// Pilot II
		upgrade[43] = new Upgrade();
		upgrade[43].name = "Superior Pilot";
		upgrade[43].description = "+2 speed, -15p collision dmg";
		upgrade[43].description2 = "+7 percent LY after each kill";
		upgrade[43].cost = 50000;
		upgrade[43].icon.load(gl, context, R.drawable.icon_pilot1);
		upgrade[43].speed = 2.0f;
		upgrade[43].collisionmod = 1.15f;
		upgrade[43].lymod = 1.07f;
		upgrade[43].require = 42;
		upgrade[43].followup = 44;
		upgrade[43].fullgame = true;
		upgrade[43].layer = 1;

		// Pilot III
		upgrade[44] = new Upgrade();
		upgrade[44].name = "Nano-implanted Pilot";
		upgrade[44].description = "+2 speed, -20p collision dmg";
		upgrade[44].description2 = "+10 percent LY after each kill";
		upgrade[44].cost = 95000;
		upgrade[44].icon.load(gl, context, R.drawable.icon_pilot1);
		upgrade[44].speed = 2.0f;
		upgrade[44].collisionmod = 1.20f;
		upgrade[44].lymod = 1.1f;
		upgrade[44].require = 43;
		upgrade[44].followup = -1;
		upgrade[44].fullgame = true;
		upgrade[44].layer = 2;

		// Engineer
		upgrade[45] = new Upgrade();
		upgrade[45].name = "Elite Engineer";
		upgrade[45].description = "-10 percent cooldown time";
		upgrade[45].description2 = "+0,5 shield recharge";
		upgrade[45].cost = 35000;
		upgrade[45].icon.load(gl, context, R.drawable.icon_engineer1);
		upgrade[45].recharge = 0.5f;
		upgrade[45].cdmod = 1.1f;
		upgrade[45].require = -1;
		upgrade[45].followup = 46;
		upgrade[45].fullgame = true;

		// Engineer II
		upgrade[46] = new Upgrade();
		upgrade[46].name = "Superior Engineer";
		upgrade[46].description = "-15 percent cooldown time";
		upgrade[46].description2 = "+1,0 shield recharge";
		upgrade[46].cost = 75000;
		upgrade[46].icon.load(gl, context, R.drawable.icon_engineer1);
		upgrade[46].recharge = 1.0f;
		upgrade[46].cdmod = 1.15f;
		upgrade[46].require = 45;
		upgrade[46].followup = 47;
		upgrade[46].fullgame = true;
		upgrade[46].layer = 1;

		// Engineer III
		upgrade[47] = new Upgrade();
		upgrade[47].name = "Nano-implanted Engineer";
		upgrade[47].description = "-15 percent cooldown time";
		upgrade[47].description2 = "+5 shield, +2 shield recharge";
		upgrade[47].cost = 210000;
		upgrade[47].icon.load(gl, context, R.drawable.icon_engineer1);
		upgrade[47].shield = 5;
		upgrade[47].recharge = 2.0f;
		upgrade[47].cdmod = 1.15f;
		upgrade[47].require = 46;
		upgrade[47].followup = -1;
		upgrade[47].fullgame = true;
		upgrade[47].layer = 2;

		// Commander
		upgrade[48] = new Upgrade();
		upgrade[48].name = "Elite Commander";
		upgrade[48].description = "+0,1 Fire Rate";
		upgrade[48].cost = 45000;
		upgrade[48].icon.load(gl, context, R.drawable.icon_commander1);
		upgrade[48].rof = 0.1f;
		upgrade[48].require = -1;
		upgrade[48].followup = 49;
		upgrade[48].fullgame = true;

		// Commander II
		upgrade[49] = new Upgrade();
		upgrade[49].name = "Superior Commander";
		upgrade[49].description = "+0,2 Fire Rate";
		upgrade[49].cost = 90000;
		upgrade[49].icon.load(gl, context, R.drawable.icon_commander1);
		upgrade[49].rof = 0.2f;
		upgrade[49].require = 48;
		upgrade[49].followup = 50;
		upgrade[49].fullgame = true;
		upgrade[49].layer = 1;

		// Commander III
		upgrade[50] = new Upgrade();
		upgrade[50].name = "Nano-implanted Commander";
		upgrade[50].description = "+0,25 Fire Rate, +1 Damage";
		upgrade[50].cost = 225000;
		upgrade[50].icon.load(gl, context, R.drawable.icon_commander1);
		upgrade[50].rof = 0.25f;
		upgrade[50].damage = 1;
		upgrade[50].require = 49;
		upgrade[50].followup = -1;
		upgrade[50].fullgame = true;
		upgrade[50].layer = 2;

		// Wing Commander
		upgrade[51] = new Upgrade();
		upgrade[51].name = "Angel Wings";
		upgrade[51].description = "Add 2 side turrets permanently";
		upgrade[51].description2 = "";
		upgrade[51].cost = 500000;
		upgrade[51].icon.load(gl, context, R.drawable.icon_wingmodul1);
		upgrade[51].require = -1;
		upgrade[51].followup = -1;
		upgrade[51].buycode = "sc2_wings";
		upgrade[51].layer = 3;

		
		purchasable[0] = new Purchasable();
		purchasable[0].available = true;
		purchasable[0].name = "500,000 LY";
		purchasable[0].icon.load(gl, context, R.drawable.icon_buy1);
		purchasable[0].layer = 0;
		purchasable[0].description = "Purchase 500,000 LY";
		purchasable[0].description2 = "for current profile.";
		purchasable[0].buycode = "sc2_ly5";
		
		purchasable[1] = new Purchasable();
		purchasable[1].available = true;
		purchasable[1].name = "1,000,000 LY";
		purchasable[1].icon.load(gl, context, R.drawable.icon_buy1);
		purchasable[1].layer = 1;
		purchasable[1].description = "Purchase 1,000,000 LY";
		purchasable[1].description2 = "for current profile.";
		//purchasable[1].buycode = "android.test.purchased";
		purchasable[1].buycode = "sc2_ly1k";

		purchasable[2] = new Purchasable();
		purchasable[2].available = true;
		purchasable[2].name = "2,000,000 LY";
		purchasable[2].icon.load(gl, context, R.drawable.icon_buy1);
		purchasable[2].layer = 2;
		purchasable[2].description = "Purchase 2,000,000 LY";
		purchasable[2].description2 = "for current profile.";
		purchasable[2].buycode = "sc2_ly2k";
		
		purchasable[3] = new Purchasable();
		purchasable[3].available = true;
		purchasable[3].name = "Free LY via Tapjoy";
		purchasable[3].icon.load(gl, context, R.drawable.icon_tapjoy);
		purchasable[3].layer = 1;
		purchasable[3].description = "Get free LY by completing";
		purchasable[3].description2 = "Tapjoy offers.";
		purchasable[3].buycode = "tapjoy";

		purchasable[4] = new Purchasable();
		purchasable[4].available = true;
		purchasable[4].name = "I Want Them All";
		purchasable[4].icon.load(gl, context, R.drawable.icon_buyall);
		purchasable[4].layer = 3;
		purchasable[4].description = "All ships, turrets, mods, specs,";
		purchasable[4].description2 = "and upgrades in 1 package!";
		purchasable[4].buycode = "sc2_all";

		
		if (purchased_wings) upgrade[51].bought = true;
		if (purchased_7bullets) modifier[4].bought = true;
		if (purchased_modulator) turret[15].bought = true;
		if (purchased_timewarp) special[3].bought = true;
		if (purchased_wave) special[28].bought = true;
		if (purchased_mines) special[36].bought = true;
		if (purchased_9bullets) modifier[5].bought = true;
		if (purchased_chaosgun) turret[18].bought = true;

		
	}
	public void loadships(GL10 gl, Context context) {

		// Alienship
		ship[0] = new Ship();
		ship[0].name = "Alienship";
		ship[0].description = "Hull 15, Shield 15";
		ship[0].description2 = "Speed 12, Slots 2/6";
		ship[0].cost = 0;
		ship[0].fullgame = false;
		ship[0].available = true;
		ship[0].image_id[0] = R.drawable.playership_sillafree;
		//ship[0].image_id[1] = R.drawable.alienship_shield;
		ship[0].image_size[0] = (int)(hd*72);
		ship[0].image_size[1] = (int)(hd*75);
		ship[0].image[0] = new My2DImage(ship[0].image_size[0],ship[0].image_size[1],true);
		//ship[0].image[1] = new My2DImage(ship[0].image_size[0],ship[0].image_size[1],true);
		ship[0].image[0].load(gl, context, ship[0].image_id[0]);
		ship[0].icon.load(gl, context, R.drawable.icon_silla);
		ship[0].layer = 0;
		//ship[0].image[1].load(gl, context, ship[0].image_id[1]);
		ship[0].turret_num = 1;
		ship[0].turret_oo[0][0] = (int)(hd*36); ship[0].turret_oo[0][1] = (int)(hd*3);
		ship[0].turret_oo[1][0] = (int)(hd*21); ship[0].turret_oo[1][1] = (int)(hd*32);
		ship[0].turret_oo[2][0] = (int)(hd*50); ship[0].turret_oo[2][1] = (int)(hd*32);
		ship[0].turret_oo[3][0] = (int)(hd*12); ship[0].turret_oo[3][1] = (int)(hd*40);
		ship[0].turret_oo[4][0] = (int)(hd*60); ship[0].turret_oo[4][1] = (int)(hd*40);
		ship[0].wing_oo[0][0] = -(int)(hd*40); ship[0].wing_oo[0][1] = (int)(hd*32);
		ship[0].wing_oo[1][0] = (int)(hd*82); ship[0].wing_oo[1][1] = (int)(hd*32);
		ship[0].modifier_num = 1;
		ship[0].special_num = 2;
		ship[0].upgrade_num = 6;
		ship[0].die_effect = 0;
		ship[0].width = (int)(hd*72);
		ship[0].height = (int)(hd*75);
		ship[0].size_mod[0] = (int)(hd*10);
		ship[0].size_mod[1] = (int)(hd*10);
		ship[0].size_mod[2] = (int)(hd*3);
		ship[0].size_mod[3] = (int)(hd*2);
		ship[0].hp_base = 15;
		ship[0].shield_base = 15;
		ship[0].shield_recharge_base = 5f;
		ship[0].speed_base = 12.0f;
		ship[0].particle_pos[0][0] = (int)(hd*35); ship[0].particle_pos[0][1] = (int)(hd*67);

		// Alienship Red
		ship[1] = new Ship();
		ship[1].name = "Customized Alienship";
		ship[1].description = "Hull 15, Shield 20";
		ship[1].description2 = "Speed 12, Slots 3/6";
		ship[1].cost = 0;
		ship[1].fullgame = false;
		ship[1].available = false;
		ship[1].show = false;
		ship[1].image_id[0] = R.drawable.playership_silla_reward;
		//ship[1].image_id[1] = R.drawable.alienship_shield;
		ship[1].image_size[0] = (int)(hd*72);
		ship[1].image_size[1] = (int)(hd*75);
		ship[1].image[0] = new My2DImage(ship[1].image_size[0],ship[1].image_size[1],true);
		//ship[1].image[1] = new My2DImage(ship[1].image_size[0],ship[1].image_size[1],true);
		ship[1].image[0].load(gl, context, ship[1].image_id[0]);
		ship[1].icon.load(gl, context, R.drawable.icon_silla);
		ship[1].layer = 3;
		//ship[1].image[1].load(gl, context, ship[1].image_id[1]);
		ship[1].turret_num = 1;
		ship[1].turret_oo[0][0] = (int)(hd*36); ship[1].turret_oo[0][1] = (int)(hd*3);
		ship[1].turret_oo[1][0] = (int)(hd*21); ship[1].turret_oo[1][1] = (int)(hd*32);
		ship[1].turret_oo[2][0] = (int)(hd*50); ship[1].turret_oo[2][1] = (int)(hd*32);
		ship[1].turret_oo[3][0] = (int)(hd*12); ship[1].turret_oo[3][1] = (int)(hd*40);
		ship[1].turret_oo[4][0] = (int)(hd*60); ship[1].turret_oo[4][1] = (int)(hd*40);
		ship[1].wing_oo[0][0] = -(int)(hd*40); ship[1].wing_oo[0][1] = (int)(hd*32);
		ship[1].wing_oo[1][0] = (int)(hd*82); ship[1].wing_oo[1][1] = (int)(hd*32);
		ship[1].modifier_num = 1;
		ship[1].special_num = 3;
		ship[1].upgrade_num = 6;
		ship[1].die_effect = 0;
		ship[1].width = (int)(hd*72);
		ship[1].height = (int)(hd*75);
		ship[1].size_mod[0] = (int)(hd*10);
		ship[1].size_mod[1] = (int)(hd*10);
		ship[1].size_mod[2] = (int)(hd*3);
		ship[1].size_mod[3] = (int)(hd*2);
		ship[1].hp_base = 15;
		ship[1].shield_base = 20;
		ship[1].shield_recharge_base = 5f;
		ship[1].speed_base = 12.0f;
		ship[1].particle_pos[0][0] = (int)(hd*35); ship[1].particle_pos[0][1] = (int)(hd*67);

		// Maximus Prime
		ship[2] = new Ship();
		ship[2].name = "Maximus Prime";
		ship[2].description = "Hull 20, Shield 20";
		ship[2].description2 = "Speed 12, Slots 3/7";
		ship[2].cost = 250000;
		ship[2].fullgame = false;
		ship[2].available = true;
		ship[2].image_id[0] = R.drawable.playership_maximus;
		//ship[2].image_id[1] = R.drawable.alienship_shield;
		ship[2].image_size[0] = (int)(hd*60);
		ship[2].image_size[1] = (int)(hd*84);
		ship[2].image[0] = new My2DImage(ship[2].image_size[0],ship[2].image_size[1],true);
		//ship[2].image[1] = new My2DImage(ship[2].image_size[0],ship[2].image_size[1],true);
		ship[2].image[0].load(gl, context, ship[2].image_id[0]);
		ship[2].icon.load(gl, context, R.drawable.icon_maximus);
		ship[2].layer = 1;
		//ship[2].image[1].load(gl, context, ship[2].image_id[1]);
		ship[2].turret_num = 1;
		ship[2].turret_oo[0][0] = (int)(hd*29); ship[2].turret_oo[0][1] = (int)(hd*3);
		ship[2].turret_oo[1][0] = (int)(hd*17); ship[2].turret_oo[1][1] = (int)(hd*11);
		ship[2].turret_oo[2][0] = (int)(hd*43); ship[2].turret_oo[2][1] = (int)(hd*11);
		ship[2].turret_oo[3][0] = (int)(hd*11); ship[2].turret_oo[3][1] = (int)(hd*32);
		ship[2].turret_oo[4][0] = (int)(hd*49); ship[2].turret_oo[4][1] = (int)(hd*32);
		ship[2].wing_oo[0][0] = -(int)(hd*40); ship[2].wing_oo[0][1] = (int)(hd*58);
		ship[2].wing_oo[1][0] = (int)(hd*70); ship[2].wing_oo[1][1] = (int)(hd*58);
		ship[2].modifier_num = 1;
		ship[2].special_num = 3;
		ship[2].upgrade_num = 7;
		ship[2].die_effect = 0;
		ship[2].width = (int)(hd*60);
		ship[2].height = (int)(hd*84);
		ship[2].size_mod[0] = (int)(hd*3);
		ship[2].size_mod[1] = (int)(hd*3);
		ship[2].size_mod[2] = (int)(hd*3);
		ship[2].size_mod[3] = (int)(hd*2);
		ship[2].hp_base = 20;
		ship[2].shield_base = 20;
		ship[2].shield_recharge_base = 5f;
		ship[2].speed_base = 12.0f;
		ship[2].particle_pos[0][0] = (int)(hd*30); ship[2].particle_pos[0][1] = (int)(hd*73);

		// Drone Ship
		ship[3] = new Ship();
		ship[3].name = "Drone Ship";
		ship[3].description = "Hull 20, Shield 25";
		ship[3].description2 = "Speed 12, Slots 4/7";
		ship[3].cost = 500000;
		ship[3].fullgame = true;
		ship[3].available = true;
		ship[3].image_id[0] = R.drawable.playership_drone;
		//ship[3].image_id[1] = R.drawable.alienship_shield;
		ship[3].image_size[0] = (int)(hd*63);
		ship[3].image_size[1] = (int)(hd*84);
		ship[3].image[0] = new My2DImage(ship[3].image_size[0],ship[3].image_size[1],true);
		//ship[3].image[1] = new My2DImage(ship[3].image_size[0],ship[3].image_size[1],true);
		ship[3].image[0].load(gl, context, ship[3].image_id[0]);
		ship[3].icon.load(gl, context, R.drawable.icon_wingmodul1);
		ship[3].layer = 2;
		//ship[3].image[1].load(gl, context, ship[3].image_id[1]);
		ship[3].turret_num = 1;
		ship[3].turret_oo[0][0] = (int)(hd*31); ship[3].turret_oo[0][1] = (int)(hd*10);
		ship[3].turret_oo[1][0] = (int)(hd*28); ship[3].turret_oo[1][1] = (int)(hd*8);
		ship[3].turret_oo[2][0] = (int)(hd*35); ship[3].turret_oo[2][1] = (int)(hd*8);
		ship[3].turret_oo[3][0] = (int)(hd*16); ship[3].turret_oo[3][1] = (int)(hd*7);
		ship[3].turret_oo[4][0] = (int)(hd*46); ship[3].turret_oo[4][1] = (int)(hd*7);
		ship[3].wing_oo[0][0] = -(int)(hd*40); ship[3].wing_oo[0][1] = (int)(hd*44);
		ship[3].wing_oo[1][0] = (int)(hd*73); ship[3].wing_oo[1][1] = (int)(hd*44);
		ship[3].modifier_num = 1;
		ship[3].special_num = 4;
		ship[3].upgrade_num = 8;
		ship[3].die_effect = 0;
		ship[3].width = (int)(hd*63);
		ship[3].height = (int)(hd*84);
		ship[3].size_mod[0] = (int)(hd*2);
		ship[3].size_mod[1] = (int)(hd*2);
		ship[3].size_mod[2] = (int)(hd*10);
		ship[3].size_mod[3] = (int)(hd*10);
		ship[3].hp_base = 20;
		ship[3].shield_base = 25;
		ship[3].shield_recharge_base = 5f;
		ship[3].speed_base = 12.0f;
		ship[3].particle_pos[0][0] = (int)(hd*30); ship[3].particle_pos[0][1] = (int)(hd*75);

		// Battle Cruiser
		ship[4] = new Ship();
		ship[4].name = "Battle Cruiser";
		ship[4].description = "Hull 25, Shield 30";
		ship[4].description2 = "Speed 12, Slots 4/8";
		ship[4].cost = 0;
		ship[4].fullgame = false;
		ship[4].available = true;
		ship[4].buycode = "sc2_cruiser";
		ship[4].image_id[0] = R.drawable.playership_battlecruiser;
		//ship[4].image_id[1] = R.drawable.alienship_shield;
		ship[4].image_size[0] = (int)(hd*79);
		ship[4].image_size[1] = (int)(hd*94);
		ship[4].image[0] = new My2DImage(ship[4].image_size[0],ship[4].image_size[1],true);
		//ship[4].image[1] = new My2DImage(ship[4].image_size[0],ship[4].image_size[1],true);
		ship[4].image[0].load(gl, context, ship[4].image_id[0]);
		ship[4].icon.load(gl, context, R.drawable.icon_battlecruiser);
		ship[4].layer = 3;
		//ship[4].image[1].load(gl, context, ship[4].image_id[1]);
		ship[4].turret_num = 1;
		ship[4].turret_oo[0][0] = (int)(hd*39); ship[4].turret_oo[0][1] = (int)(hd*9);
		ship[4].turret_oo[1][0] = (int)(hd*22); ship[4].turret_oo[1][1] = (int)(hd*40);
		ship[4].turret_oo[2][0] = (int)(hd*57); ship[4].turret_oo[2][1] = (int)(hd*40);
		ship[4].turret_oo[3][0] = (int)(hd*6); ship[4].turret_oo[3][1] = (int)(hd*43);
		ship[4].turret_oo[4][0] = (int)(hd*70); ship[4].turret_oo[4][1] = (int)(hd*43);
		ship[4].wing_oo[0][0] = -(int)(hd*40); ship[4].wing_oo[0][1] = (int)(hd*53);
		ship[4].wing_oo[1][0] = (int)(hd*89); ship[4].wing_oo[1][1] = (int)(hd*53);
		ship[4].modifier_num = 1;
		ship[4].special_num = 4;
		ship[4].upgrade_num = 8;
		ship[4].die_effect = 0;
		ship[4].width = (int)(hd*79);
		ship[4].height = (int)(hd*94);
		ship[4].size_mod[0] = (int)(hd*2);
		ship[4].size_mod[1] = (int)(hd*2);
		ship[4].size_mod[2] = (int)(hd*6);
		ship[4].size_mod[3] = (int)(hd*3);
		ship[4].hp_base = 25;
		ship[4].shield_base = 30;
		ship[4].shield_recharge_base = 5f;
		ship[4].speed_base = 12.0f;
		ship[4].particle_num = 2;
		//ship[4].particle_pos[0][0] = (int)(hd*39); ship[4].particle_pos[0][1] = (int)(hd*82);
		ship[4].particle_pos[0][0] = (int)(hd*20); ship[4].particle_pos[0][1] = (int)(hd*80);
		ship[4].particle_pos[1][0] = (int)(hd*59); ship[4].particle_pos[1][1] = (int)(hd*80);

		// Excelsior
		ship[5] = new Ship();
		ship[5].name = "Excelsior";
		ship[5].description = "Hull 30, Shield 35";
		ship[5].description2 = "Speed 12, Slots 4/8";
		ship[5].cost = 0;
		ship[5].fullgame = false;
		ship[5].available = true;
		ship[5].buycode = "sc2_excelsior";
		ship[5].image_id[0] = R.drawable.playership_excelsior;
		//ship[5].image_id[1] = R.drawable.alienship_shield;
		ship[5].image_size[0] = (int)(hd*84);
		ship[5].image_size[1] = (int)(hd*132);
		ship[5].image[0] = new My2DImage(ship[5].image_size[0],ship[5].image_size[1],true);
		//ship[5].image[1] = new My2DImage(ship[5].image_size[0],ship[5].image_size[1],true);
		ship[5].image[0].load(gl, context, ship[5].image_id[0]);
		ship[5].icon.load(gl, context, R.drawable.icon_excelsior);
		ship[5].layer = 3;
		//ship[5].image[1].load(gl, context, ship[5].image_id[1]);
		ship[5].turret_num = 1;
		ship[5].turret_oo[0][0] = (int)(hd*42); ship[5].turret_oo[0][1] = (int)(hd*3);
		ship[5].turret_oo[1][0] = (int)(hd*26); ship[5].turret_oo[1][1] = (int)(hd*51);
		ship[5].turret_oo[2][0] = (int)(hd*59); ship[5].turret_oo[2][1] = (int)(hd*51);
		ship[5].turret_oo[3][0] = (int)(hd*8); ship[5].turret_oo[3][1] = (int)(hd*55);
		ship[5].turret_oo[4][0] = (int)(hd*75); ship[5].turret_oo[4][1] = (int)(hd*55);
		ship[5].wing_oo[0][0] = -(int)(hd*40); ship[5].wing_oo[0][1] = (int)(hd*68);
		ship[5].wing_oo[1][0] = (int)(hd*94); ship[5].wing_oo[1][1] = (int)(hd*68);
		ship[5].modifier_num = 1;
		ship[5].special_num = 4;
		ship[5].upgrade_num = 8;
		ship[5].die_effect = 0;
		ship[5].width = (int)(hd*84);
		ship[5].height = (int)(hd*132);
		ship[5].size_mod[0] = (int)(hd*6);
		ship[5].size_mod[1] = (int)(hd*6);
		ship[5].size_mod[2] = (int)(hd*4);
		ship[5].size_mod[3] = (int)(hd*10);
		ship[5].hp_base = 30;
		ship[5].shield_base = 35;
		ship[5].shield_recharge_base = 5f;
		ship[5].speed_base = 12.0f;
		ship[5].particle_num = 3;
		ship[5].particle_pos[0][0] = (int)(hd*42); ship[5].particle_pos[0][1] = (int)(hd*103);
		ship[5].particle_pos[1][0] = (int)(hd*8); ship[5].particle_pos[1][1] = (int)(hd*80);
		ship[5].particle_pos[2][0] = (int)(hd*75); ship[5].particle_pos[2][1] = (int)(hd*80);

		if (purchased_excelsior) ship[5].bought = true;
		if (purchased_cruiser) ship[4].bought = true;

	}

	public void releaseships(GL10 gl) {
		
		ship[0].image[0].release(gl);
		ship[0].image[1].release(gl);
		ship[1].image[0].release(gl);
		ship[1].image[1].release(gl);
		ship[2].image[0].release(gl);
		ship[2].image[1].release(gl);
		ship[3].image[0].release(gl);
		ship[3].image[1].release(gl);
		ship[4].image[0].release(gl);
		ship[4].image[1].release(gl);
		ship[5].image[0].release(gl);
		ship[5].image[1].release(gl);
		
	}
} 
