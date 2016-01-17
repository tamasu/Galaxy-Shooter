package com.tubigames.galaxy.shooter.hd;

import javax.microedition.khronos.opengles.GL10;

import com.tubigames.galaxy.shooter.hd.R;
import com.tubigames.galaxy.shooter.hd.Bullets.Bullet;

import android.content.Context;

public class Bullets {
	
	float hd = OpenGLRenderer.hd;
	
	// explosion típusok
	public class Bullet {
		
		public int picture_id; // az entitás képfájljának id-je a res/drawables/ könyvtárból
		public My2DImage image; // az entitás képfájljának id-je a res/drawables/ könyvtárból
		public int width = 0; // a kép szélessége
		public int height = 0; // a kép magassága
		
		public int particle_id = R.drawable.particle;
		public int particle_width = (int)(hd*32); 
		public int particle_height = (int)(hd*32); 
		public boolean isparticle = false;
		//public ParticleSystem particle;
	}
	
	public Bullet[] bullet; 
	public int bullet_num = 44;
	
	public Bullets() {
		
	}
	
	public void loadimages(GL10 gl, Context context) {
		
		for (int i=0;i<bullet_num;i++) {
			bullet[i].image = null;
			bullet[i].image = new My2DImage(bullet[i].width, bullet[i].height,true);
			bullet[i].image.load(gl, context, bullet[i].picture_id);
			
			//bullet[i].particle = null;
			//bullet[i].particle = new ParticleSystem(bullet[i].particle_width, bullet[i].particle_height,true,1);
			//bullet[i].particle.load(gl, context, bullet[i].particle_id);
		}
		
	}
	
	public void load() {

		bullet = new Bullet[bullet_num]; // összes explosion típus mennyisége

		// 
		bullet[0] = new Bullet();
		bullet[0].width = (int)(hd*16);
		bullet[0].height = (int)(hd*35);
		bullet[0].picture_id = R.drawable.bullet_laser;
		//bullet[0].image = new My2DImage(bullet[0].width, bullet[0].height,true);
		//bullet[0].image.load(gl, context, bullet[0].picture_id);
		// 
		bullet[1] = new Bullet();
		bullet[1].width = (int)(hd*8);
		bullet[1].height = (int)(hd*18);
		bullet[1].picture_id = R.drawable.bullet_smalllaser;
		//bullet[1].image = new My2DImage(bullet[1].width, bullet[1].height,true);
		//bullet[1].image.load(gl, context, bullet[1].picture_id);
		// 
		bullet[2] = new Bullet();
		bullet[2].width = (int)(hd*33);
		bullet[2].height = (int)(hd*33);
		bullet[2].picture_id = R.drawable.bullet_plasmaball;
		//bullet[2].image = new My2DImage(bullet[2].width, bullet[2].height,true);
		//bullet[2].image.load(gl, context, bullet[2].picture_id);
		// 
		bullet[3] = new Bullet();
		bullet[3].width = (int)(hd*36);
		bullet[3].height = (int)(hd*24);
		bullet[3].picture_id = R.drawable.bullet_phaser;
		//bullet[3].image = new My2DImage(bullet[3].width, bullet[3].height,true);
		//bullet[3].image.load(gl, context, bullet[3].picture_id);
		// 
		bullet[4] = new Bullet();
		bullet[4].width = (int)(hd*20);
		bullet[4].height = (int)(hd*20);
		bullet[4].picture_id = R.drawable.bullet_blueenergy;
		//bullet[4].image = new My2DImage(bullet[4].width, bullet[4].height,true);
		//bullet[4].image.load(gl, context, bullet[4].picture_id);
		// 
		bullet[5] = new Bullet();
		bullet[5].width = (int)(hd*20);
		bullet[5].height = (int)(hd*37);
		bullet[5].picture_id = R.drawable.bullet_redlaser;
		//bullet[5].image = new My2DImage(bullet[5].width, bullet[5].height,true);
		//bullet[5].image.load(gl, context, bullet[5].picture_id);
		// 
		bullet[6] = new Bullet();
		bullet[6].width = (int)(hd*38);
		bullet[6].height = (int)(hd*40);
		bullet[6].picture_id = R.drawable.bullet_photontorpedo;
		//bullet[6].image = new My2DImage(bullet[6].width, bullet[6].height,true);
		//bullet[6].image.load(gl, context, bullet[6].picture_id);
		// 
		bullet[7] = new Bullet();
		bullet[7].width = (int)(hd*8);
		bullet[7].height = (int)(hd*18);
		bullet[7].picture_id = R.drawable.bullet_greenlaser;
		//bullet[7].image = new My2DImage(bullet[7].width, bullet[7].height,true);
		//bullet[7].image.load(gl, context, bullet[7].picture_id);
		// 
		bullet[8] = new Bullet();
		bullet[8].width = (int)(hd*25);
		bullet[8].height = (int)(hd*44);
		bullet[8].picture_id = R.drawable.bullet_fireball;
		//bullet[8].image = new My2DImage(bullet[8].width, bullet[8].height,true);
		//bullet[8].image.load(gl, context, bullet[8].picture_id);
		// 
		bullet[9] = new Bullet();
		bullet[9].width = (int)(hd*16);
		bullet[9].height = (int)(hd*40);
		bullet[9].picture_id = R.drawable.bullet_homingrocket;
		//bullet[9].image = new My2DImage(bullet[9].width, bullet[9].height,true);
		//bullet[9].image.load(gl, context, bullet[9].picture_id);
		// 
		bullet[10] = new Bullet();
		bullet[10].width = (int)(hd*23);
		bullet[10].height = (int)(hd*86);
		bullet[10].picture_id = R.drawable.bullet_pulsar;
		//bullet[10].image = new My2DImage(bullet[10].width, bullet[10].height,true);
		//bullet[10].image.load(gl, context, bullet[10].picture_id);
		// 
		bullet[11] = new Bullet();
		bullet[11].width = (int)(hd*25);
		bullet[11].height = (int)(hd*184);
		bullet[11].picture_id = R.drawable.bullet_mininglaser;
		//bullet[11].image = new My2DImage(bullet[11].width, bullet[11].height,true);
		//bullet[11].image.load(gl, context, bullet[11].picture_id);
		// 
		bullet[12] = new Bullet();
		bullet[12].width = (int)(hd*91);
		bullet[12].height = (int)(hd*158); // +60 mining effect poziciora
		bullet[12].picture_id = R.drawable.bullet_miningeffect;
		//bullet[12].image = new My2DImage(bullet[12].width, bullet[12].height,true);
		//bullet[12].image.load(gl, context, bullet[12].picture_id);
		// 
		bullet[13] = new Bullet();
		bullet[13].width = (int)(hd*145);
		bullet[13].height = (int)(hd*823);
		bullet[13].picture_id = R.drawable.bullet_disruptor;
		//bullet[13].image = new My2DImage(bullet[13].width, bullet[13].height,true);
		//bullet[13].image.load(gl, context, bullet[13].picture_id);

		bullet[14] = new Bullet();
		bullet[14].width = (int)(hd*13);
		bullet[14].height = (int)(hd*26);
		bullet[14].picture_id = R.drawable.bullet_missile;
		bullet[14].isparticle = true;

		bullet[15] = new Bullet();
		bullet[15].width = (int)(hd*21);
		bullet[15].height = (int)(hd*21);
		bullet[15].picture_id = R.drawable.bullet_greenplasma;

		bullet[16] = new Bullet();
		bullet[16].width = (int)(hd*16);
		bullet[16].height = (int)(hd*35);
		bullet[16].picture_id = R.drawable.bullet_purpleplasma;
		
		bullet[17] = new Bullet();
		bullet[17].width = (int)(hd*24);
		bullet[17].height = (int)(hd*50);
		bullet[17].picture_id = R.drawable.bullet_fire1;
	
		bullet[18] = new Bullet();
		bullet[18].width = (int)(hd*23);
		bullet[18].height = (int)(hd*86);
		bullet[18].picture_id = R.drawable.bullet_vulcan;

		bullet[19] = new Bullet();
		bullet[19].width = (int)(hd*32);
		bullet[19].height = (int)(hd*18);
		bullet[19].picture_id = R.drawable.bullet_roundfire;

		// anagan
		bullet[20] = new Bullet();
		bullet[20].width = (int)(hd*42);
		bullet[20].height = (int)(hd*42);
		bullet[20].picture_id = R.drawable.bullet_redfog;

		bullet[21] = new Bullet();
		bullet[21].width = (int)(hd*32);
		bullet[21].height = (int)(hd*18);
		bullet[21].picture_id = R.drawable.bullet_roundfire_blue;

		bullet[22] = new Bullet();
		bullet[22].width = (int)(hd*37);
		bullet[22].height = (int)(hd*37);
		bullet[22].picture_id = R.drawable.bullet_emp;		

		bullet[23] = new Bullet();
		bullet[23].width = (int)(hd*20);
		bullet[23].height = (int)(hd*20);
		bullet[23].picture_id = R.drawable.bullet_greenenergy;

		bullet[24] = new Bullet();
		bullet[24].width = (int)(hd*33);
		bullet[24].height = (int)(hd*33);
		bullet[24].picture_id = R.drawable.bullet_enemyplasmaball;

		bullet[25] = new Bullet();
		bullet[25].width = (int)(hd*16);
		bullet[25].height = (int)(hd*36);
		bullet[25].picture_id = R.drawable.bullet_yellowlaser;

		bullet[26] = new Bullet();
		bullet[26].width = (int)(hd*10);
		bullet[26].height = (int)(hd*30);
		bullet[26].picture_id = R.drawable.bullet_greenlaser2;

		bullet[27] = new Bullet();
		bullet[27].width = (int)(hd*10);
		bullet[27].height = (int)(hd*30);
		bullet[27].picture_id = R.drawable.bullet_purplelaser;

		bullet[28] = new Bullet();
		bullet[28].width = (int)(hd*10);
		bullet[28].height = (int)(hd*30);
		bullet[28].picture_id = R.drawable.bullet_lightbluelaser;

		bullet[29] = new Bullet();
		bullet[29].width = (int)(hd*10);
		bullet[29].height = (int)(hd*30);
		bullet[29].picture_id = R.drawable.bullet_darkbluelaser;

		bullet[30] = new Bullet();
		bullet[30].width = (int)(hd*16);
		bullet[30].height = (int)(hd*26);
		bullet[30].picture_id = R.drawable.bullet_blueshining;

		bullet[31] = new Bullet();
		bullet[31].width = (int)(hd*24);
		bullet[31].height = (int)(hd*26);
		bullet[31].picture_id = R.drawable.bullet_greenshining;

		bullet[32] = new Bullet();
		bullet[32].width = (int)(hd*24);
		bullet[32].height = (int)(hd*20);
		bullet[32].picture_id = R.drawable.bullet_purpleshining;

		bullet[33] = new Bullet();
		bullet[33].width = (int)(hd*8);
		bullet[33].height = (int)(hd*18);
		bullet[33].picture_id = R.drawable.bullet_smallpurplelaser;

		bullet[34] = new Bullet();
		bullet[34].width = (int)(hd*8);
		bullet[34].height = (int)(hd*18);
		bullet[34].picture_id = R.drawable.bullet_smallyellowlaser;
	
		bullet[35] = new Bullet();
		bullet[35].width = (int)(hd*58);
		bullet[35].height = (int)(hd*52);
		bullet[35].picture_id = R.drawable.player_mine;

		bullet[36] = new Bullet();
		bullet[36].width = (int)(hd*35);
		bullet[36].height = (int)(hd*20);
		bullet[36].picture_id = R.drawable.bullet_phaserorange;

		bullet[37] = new Bullet();
		bullet[37].width = (int)(hd*20);
		bullet[37].height = (int)(hd*20);
		bullet[37].picture_id = R.drawable.bullet_blueenergy_enemy;

		bullet[38] = new Bullet();
		bullet[38].width = (int)(hd*38);
		bullet[38].height = (int)(hd*40);
		bullet[38].picture_id = R.drawable.bullet_photontorpedo_enemy;

		bullet[39] = new Bullet();
		bullet[39].width = (int)(hd*38);
		bullet[39].height = (int)(hd*40);
		bullet[39].picture_id = R.drawable.bullet_photontorpedo_orange;

		bullet[40] = new Bullet();
		bullet[40].width = (int)(hd*24);
		bullet[40].height = (int)(hd*26);
		bullet[40].picture_id = R.drawable.bullet_redshining;

		bullet[41] = new Bullet();
		bullet[41].width = (int)(hd*14);
		bullet[41].height = (int)(hd*46);
		bullet[41].picture_id = R.drawable.bullet_smalldisruptor;

		bullet[42] = new Bullet();
		bullet[42].width = (int)(hd*13);
		bullet[42].height = (int)(hd*29);
		bullet[42].picture_id = R.drawable.bullet_smallparticlebeam;

		bullet[43] = new Bullet();
		bullet[43].width = (int)(hd*12);
		bullet[43].height = (int)(hd*43);
		bullet[43].picture_id = R.drawable.bullet_chaosgun;
		//bullet[10].image = new My2DImage(bullet[10].width, bullet[10].height,true);
		//bullet[10].image.load(gl, context, bullet[10].picture_id);


	}
	
	public void release(GL10 gl) {
		for (int i=0;i<bullet_num;i++){
			bullet[i].image.release(gl);
		}
	}
} 
