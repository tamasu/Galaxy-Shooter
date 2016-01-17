package com.tubigames.galaxy.shooter.hd;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;


public class SoundManager {
	
	static private SoundManager _instance;
	private static SoundPool mSoundPool; 
	private static HashMap<Integer, Integer> mSoundPoolMap; 
	private static AudioManager  mAudioManager;
	private static Context mContext;
	
	private SoundManager()
	{   
	}
	
	/**
	 * Requests the instance of the Sound Manager and creates it
	 * if it does not exist.
	 * 
	 * @return Returns the single instance of the SoundManager
	 */
	static synchronized public SoundManager getInstance() 
	{
	    if (_instance == null) 
	      _instance = new SoundManager();
	    return _instance;
	 }
	
	/**
	 * Initialises the storage for the sounds
	 * 
	 * @param theContext The Application context
	 */
	public static  void initSounds(Context theContext) 
	{ 
		 mContext = theContext;
	     mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
	     mSoundPoolMap = new HashMap<Integer, Integer>(); 
	     mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE); 	    
	} 
	
	/**
	 * Add a new Sound to the SoundPool
	 * 
	 * @param Index - The Sound Index for Retrieval
	 * @param SoundID - The Android ID for the Sound asset.
	 */
	public static void addSound(int Index,int SoundID)
	{
		mSoundPoolMap.put(Index, mSoundPool.load(mContext, SoundID, 1));
	}
	
	/**
	 * Loads the various sound assets
	 * Currently hardcoded but could easily be changed to be flexible.
	 */
	public static void loadSounds()
	{
		
		mSoundPoolMap.put(1, mSoundPool.load(mContext, R.raw.button_click_01, 1));
		mSoundPoolMap.put(2, mSoundPool.load(mContext, R.raw.buy, 1));		
		mSoundPoolMap.put(3, mSoundPool.load(mContext, R.raw.cloaking_device, 1));		
		mSoundPoolMap.put(4, mSoundPool.load(mContext, R.raw.disruptor, 1));		
		mSoundPoolMap.put(5, mSoundPool.load(mContext, R.raw.emp, 1));		
		mSoundPoolMap.put(6, mSoundPool.load(mContext, R.raw.explosion_04, 1));
		mSoundPoolMap.put(7, mSoundPool.load(mContext, R.raw.explosion_02, 1));		
		mSoundPoolMap.put(8, mSoundPool.load(mContext, R.raw.explosion_01, 1));		
		mSoundPoolMap.put(9, mSoundPool.load(mContext, R.raw.explosion_03, 1));		
		mSoundPoolMap.put(10, mSoundPool.load(mContext, R.raw.gatling_laser, 1));		
		mSoundPoolMap.put(11, mSoundPool.load(mContext, R.raw.gravity_gun, 1));
		mSoundPoolMap.put(12, mSoundPool.load(mContext, R.raw.homing_missile, 1));		
		mSoundPoolMap.put(13, mSoundPool.load(mContext, R.raw.ion_bullet, 1));		
		mSoundPoolMap.put(14, mSoundPool.load(mContext, R.raw.laser_bullet, 1));		
		mSoundPoolMap.put(15, mSoundPool.load(mContext, R.raw.phaser_bullet, 1));		
		mSoundPoolMap.put(16, mSoundPool.load(mContext, R.raw.photon_torpedo, 1));
		mSoundPoolMap.put(17, mSoundPool.load(mContext, R.raw.plasma_bullet, 1));		
		mSoundPoolMap.put(18, mSoundPool.load(mContext, R.raw.slot, 1));		
		mSoundPoolMap.put(19, mSoundPool.load(mContext, R.raw.alien_bullet_01, 1));		
		mSoundPoolMap.put(20, mSoundPool.load(mContext, R.raw.alien_bullet_02, 1));		
		mSoundPoolMap.put(21, mSoundPool.load(mContext, R.raw.pageopen, 1));	
		mSoundPoolMap.put(22, mSoundPool.load(mContext, R.raw.pageclose, 1));	
		mSoundPoolMap.put(23, mSoundPool.load(mContext, R.raw.pageturn, 1));	
		mSoundPoolMap.put(24, mSoundPool.load(mContext, R.raw.whole, 1));	
		mSoundPoolMap.put(25, mSoundPool.load(mContext, R.raw.pagerest, 1));	
		mSoundPoolMap.put(26, mSoundPool.load(mContext, R.raw.antirocket, 1));	
		mSoundPoolMap.put(27, mSoundPool.load(mContext, R.raw.crazy, 1));	
		mSoundPoolMap.put(28, mSoundPool.load(mContext, R.raw.cryogenic, 1));	
		mSoundPoolMap.put(29, mSoundPool.load(mContext, R.raw.deploymine, 1));	
		mSoundPoolMap.put(30, mSoundPool.load(mContext, R.raw.instantshield, 1));	
		mSoundPoolMap.put(31, mSoundPool.load(mContext, R.raw.mininglaser, 1));	
		mSoundPoolMap.put(32, mSoundPool.load(mContext, R.raw.pickup, 1));	
		mSoundPoolMap.put(33, mSoundPool.load(mContext, R.raw.powerup, 1));	
		mSoundPoolMap.put(34, mSoundPool.load(mContext, R.raw.pulsar, 1));	
		mSoundPoolMap.put(35, mSoundPool.load(mContext, R.raw.nuke, 1));	
		mSoundPoolMap.put(36, mSoundPool.load(mContext, R.raw.victory, 1));	
		mSoundPoolMap.put(37, mSoundPool.load(mContext, R.raw.defeat, 1));	
		mSoundPoolMap.put(38, mSoundPool.load(mContext, R.raw.counter, 1));	
		mSoundPoolMap.put(39, mSoundPool.load(mContext, R.raw.fireball, 1));	
		mSoundPoolMap.put(40, mSoundPool.load(mContext, R.raw.lightning, 1));	
		mSoundPoolMap.put(41, mSoundPool.load(mContext, R.raw.alien_bullet_03, 1));		
		mSoundPoolMap.put(42, mSoundPool.load(mContext, R.raw.alien_bullet_04, 1));		
		mSoundPoolMap.put(43, mSoundPool.load(mContext, R.raw.alien_bullet_05, 1));		
		mSoundPoolMap.put(44, mSoundPool.load(mContext, R.raw.logo, 1));		
			
	}
	
	/**
	 * Plays a Sound
	 * 
	 * @param index - The Index of the Sound to be played
	 * @param speed - The Speed to play not, not currently used but included for compatibility
	 */
	public static void playSound(int index,float speed) 
	{ 		
		     float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); 
		     streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		     mSoundPool.play(mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 0, speed); 
	}

	public static void playMutedSound()
	{
		mSoundPool.play(mSoundPoolMap.get(0), 0, 0, 1, -1, 1f);
	}
	
	/**
	 * Stop a Sound
	 * @param index - index of the sound to be stopped
	 */
	public static void stopSound(int index)
	{
		mSoundPool.stop(mSoundPoolMap.get(index));
	}
	
	public static void cleanup()
	{
		mSoundPool.release();
		mSoundPool = null;
	    mSoundPoolMap.clear();
	    mAudioManager.unloadSoundEffects();
	    _instance = null;
	    
	}

	
}
