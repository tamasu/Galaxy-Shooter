package com.tubigames.galaxy.shooter.hd;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager; 
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tapjoy.TapjoyConnect;
/*
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.util.Base64;
*/
import com.facebook.*;
//LEADBOLT
import com.sycdwxfdkpmwxl.AdController;
import com.appfireworks.android.track.AppTracker; 


/**
 * The initial Android Activity, setting and initiating
 */
public class Main extends Activity {

	/** Our own OpenGL View overridden */
	private OpenGLRenderer openGLRenderer;
	
	//private final boolean isGoogle = false;
	
	public AdView adView;
	private InterstitialAd interstitial;

	private RelativeLayout rl;
	private RelativeLayout.LayoutParams relativeParamsTOP;
	private RelativeLayout.LayoutParams relativeParamsBOTTOM; 

	private Context mContext;
	private boolean fullgame = false;
	private boolean adshown = false;
	public static boolean tapjoy = false;
	
	// LEADBOLT
	//private AdController lb_popupad;
	//private AdController lb_alertad;
	private AdController lb_reengagement;


	public Handler handler = new Handler() {
        public void handleMessage(Message msg) {

        	Log.i("Handler", "Handler called with code: " + msg);
        	
        	AlphaAnimation animation = new AlphaAnimation( 0.0f, 1.0f );
            animation.setDuration( 400 );
            animation.setFillAfter( true );
            animation.setInterpolator( new AccelerateInterpolator() );
        	AlphaAnimation animation2 = new AlphaAnimation( 1.0f, 0.0f );
            animation2.setDuration( 400 );
            animation2.setFillAfter( true );
            animation2.setInterpolator( new AccelerateInterpolator() );

        	switch(msg.what)
        	{
        		case 0:
    				try {  
    					adView.startAnimation( animation2 );
    					adView.setVisibility(View.GONE);
    					this.postDelayed(new Runnable() { public void run() { 
    						try {
    							adshown = false;
    							rl.removeView(adView); 
    						} catch (Exception e) { e.printStackTrace(); }
    					} }, 400);
    				} catch (Exception e) { e.printStackTrace(); }
        			break;
        		case 1:
        			if (!fullgame && !adshown) {
        				try {  
        					adshown = true;
        					rl.addView(adView, relativeParamsBOTTOM);
        					adView.startAnimation( animation );
        					adView.setVisibility(View.VISIBLE);
        				} catch (Exception e) { e.printStackTrace(); }
        			}
        			break;           
        		case 2:
        			if (!fullgame && !adshown) {
        				try {  
        					adshown = true;
        					rl.addView(adView, relativeParamsTOP);
        			    	adView.startAnimation( animation );
        					adView.setVisibility(View.VISIBLE);
        				} catch (Exception e) { e.printStackTrace(); }
        			}
        			break;
        		case 3: 
        			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        			break;
        		case 4: 
        			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        			break;

        		case 5: 
  			        // try to play video first
  			        /*try {
  			        	
  			        	Intent videoPlaybackActivity = new Intent(getBaseContext(), VideoPlayer.class);
  			        	startActivity(videoPlaybackActivity);
  			        } catch (Exception e) 
  			        {
  			        	e.printStackTrace();
  			        }*/
        			// LEADBOLT POPUP
        			//try { if (!fullgame) lb_popupad.loadAd(); } catch (Exception e) { e.printStackTrace(); }

        			try { 
        				
        				if (interstitial.isLoaded()) { 
        					interstitial.show(); 
        					
        				} else {
        					AdRequest adRequest = new AdRequest.Builder().build();
        		            interstitial.loadAd(adRequest);
        				}
        			
        			
        			} catch (Exception e) { e.printStackTrace(); }

  			        break;

        		case 6: 
        		       // LEADBOLT EXIT / ALERT
        		      // try { if (!fullgame) lb_alertad.loadAd(); } catch (Exception e) { e.printStackTrace(); }
  			        break;

        		default:
        			break;
        	  } 
        }
    };

    // FIZETÉSHEZ
    //public Handler mTransactionHandler = new Handler(){
	//	public void handleMessage(android.os.Message msg) {
	//		Log.i("BillingService", "Transaction complete");
	//		Log.i("BillingService", "Transaction status: "+BillingHelper.latestPurchase.purchaseState);
	//		Log.i("BillingService", "Item purchased is: "+BillingHelper.latestPurchase.productId);
	//		
	//		if(BillingHelper.latestPurchase.isPurchased()){
	//			//showItem();
	//		}
	//	};
	//
    //}; 

	/**
	 * Initiate the OpenGL View and set our own
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mContext = this;

        /* CATCH UNHANDLED EXCEPTIONS
    	Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, final Throwable ex) {

                Intent crashedIntent = new Intent(Main.this, SplashActivity.class);
                crashedIntent.putExtra(EXTRA_CRASHED_FLAG,  "Unexpected Error occurred.");
                crashedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                crashedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(crashedIntent);

                System.exit(0);
                // If you don't kill the VM here the app goes into limbo
            }
    	});
    	*/
        
		// teljes képernyő használata
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  

        
        try {
	        final SharedPreferences settings =
	                getSharedPreferences("localPreferences", MODE_PRIVATE);
	            if (settings.getBoolean("isFirstRun", true)) {
	            	final SpannableString s = new SpannableString("We may use cookies or device identifiers to personalise content and ads, to provide social media features and to analyse our traffic. We might also share such identifiers and other information from your device with our social media, advertising and analytics partners. See details: www.mangata-media.com/policy");
	            	Linkify.addLinks(s, Linkify.WEB_URLS);
	            	final AlertDialog d = new AlertDialog.Builder(this)
	                .setTitle("Privacy Policy & Cookies")
	                .setMessage(s)
	                .setIcon(RESULT_OK)
	                .setNegativeButton("Exit", new OnClickListener() {
	                  @Override
	                  public void onClick(DialogInterface dialog, int which) {
	                	  //getActivity().finish();
	                	  System.exit(0);
	                  }
	                })
	                .setPositiveButton("Accept", new OnClickListener() {
	                  @Override
	                  public void onClick(DialogInterface dialog, int which) {
	                   settings.edit().putBoolean("isFirstRun", false).commit();
	                  }
	                })
	                .show();
	              ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
	            }
        } catch (Exception e) { e.printStackTrace(); }

        
        // try to play video first
        //try {
        //	Intent videoPlaybackActivity = new Intent(getBaseContext(), VideoPlayer.class);
        //	startActivity(videoPlaybackActivity);
        //} catch (Exception e) 
        //{
        //	e.printStackTrace();
        //}
        
        rl = new RelativeLayout(this);
        relativeParamsTOP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeParamsBOTTOM = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        
        relativeParamsTOP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        relativeParamsBOTTOM.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC); 
        SoundManager.getInstance();
        SoundManager.initSounds(this);
        SoundManager.loadSounds();

    	SharedPreferences pref = mContext.getSharedPreferences("Settings", Context.MODE_PRIVATE);
    	fullgame = pref.getBoolean("Full_Version", false);
        // Create the adView
    	//if (openGLRenderer.hd > 1) adView = new AdView(this, AdSize.SMART_BANNER, "a151dc15d347e7b");
    	//else adView = new AdView(this, AdSize.SMART_BANNER, "a151dc159680856");
    	adView = new AdView(this);
    	adView.setAdUnitId("ca-app-pub-7677805621929936/2232952003");
        adView.setAdSize(AdSize.SMART_BANNER);  
        
        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-7677805621929936/3709685205");

        
        try {
        	openGLRenderer = new OpenGLRenderer(this, handler);
        } catch (Exception e) 
        {
        	e.printStackTrace();
        }
        
        if (OpenGLRenderer.isGoogle) {
        	try {
        		if (openGLRenderer.hd > 1) TapjoyConnect.requestTapjoyConnect(this, "9218f152-2aa9-4e71-9b8b-1677bbafff25", "gEqV7g8euvzR4rnGPQ9K");
        		else TapjoyConnect.requestTapjoyConnect(this, "1280d792-66d4-453c-92bb-9f9ceb7446a5", "hlq9pBuUmHyd7CWEBh1a");
        		tapjoy = true;
        	} catch (Exception e) 
        	{
        		e.printStackTrace();
        	}
        }
        
        // LEADBOLT
        lb_reengagement = new AdController(this, "295268531");
        lb_reengagement.loadReEngagement();
        if (!fullgame) {
	        //lb_popupad = new AdController(this, "937807723");
	        //lb_popupad.loadAdToCache();
	        /*lb_alertad = new AdController(this, "788232776", new com.sycdwxfdkpmwxl.AdListener() { 
	        	public void onAdLoaded() { Log.i("Leadbolt", "loaded"); } 
	        	public void onAdClicked() { Log.i("Leadbolt", "clicked"); } 
	        	public void onAdClosed() { Log.i("Leadbolt", "closed"); Main.this.finish(); } 
	        	public void onAdCompleted() { Log.i("Leadbolt", "completed"); Main.this.finish(); } 
	        	public void onAdFailed() { Log.i("Leadbolt", "failed"); } 
	        	public void onAdProgress() { Log.i("Leadbolt", "progress"); } 
	        	public void onAdAlreadyCompleted() { Log.i("Leadbolt", "already completed"); } 
	        	public void onAdPaused() { Log.i("Leadbolt", "paused"); } 
	        	public void onAdResumed() { Log.i("Leadbolt", "resumed"); } 
	        	public void onAdCached() { Log.i("Leadbolt", "cached"); }
	        }); */
	        //lb_alertad.loadAdToCache();
	        AppTracker.startSession(this, "NbOwbdTEvRRb4QvtEReCgmwxh9Rv1H5I");
        }        



       	// Add the adView to it
        rl.addView(openGLRenderer);
        
        if (!fullgame) {
        	try {
        	//rl.addView(adView, relativeParamsBOTTOM);
            adView.setVisibility(View.GONE);
        	// Initiate a generic request to load it with an ad
        	//AdRequest adRequest = new AdRequest();
        	//adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
        	// TODO teszt device ??? nem tudom számít e
        	//adRequest.addTestDevice("388C5FDD16130FCDE02AFB2DA54F4906");
        	//adView.loadAd(adRequest);
            
        	adView.loadAd(new AdRequest.Builder().build()); 
        	// Create ad request.
            AdRequest adRequest = new AdRequest.Builder().build();
            // Begin loading your interstitial.
            interstitial.loadAd(adRequest);

            
        	} 
        	catch (Exception e) 
    	     {
    	      e.printStackTrace();
    	     }
        }
        
        setContentView(rl);

        // FIZETÉSHEZ
        //if (OpenGLRenderer.isGoogle) {
        //	Log.i("BillingService", "Starting");
        //	startService(new Intent(mContext, BillingService.class));
        //}
        //BillingHelper.setCompletedHandler(mTransactionHandler); 
        
        //AppRater teszt
        //AppRater.showPromoDialog(this, null);
        //AppRater éles
        //AppRater.app_launched(this);
       
       // Print Application Hash Key to Log
       /*
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.tubigames.galaxy.shooter",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("TEMPTAGHASH KEY:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        */
        
	}
	
	// Facebook Stuff
	// Facebook Stuff
	// Facebook Stuff
	// Facebook Stuff

	 @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {

	   try {
		 if (requestCode == 9001 || requestCode == 9002) {
			 Log.e("Google Play Services", "onActivityResult pass to gameHelper");
			 if(openGLRenderer.ggs && openGLRenderer.gameHelper != null) openGLRenderer.gameHelper.onActivityResult(requestCode, resultCode, data);
			 return;
		 }
		 
		// GOOGLE IN-APP BILLING 3
		// GOOGLE IN-APP BILLING 3
		// GOOGLE IN-APP BILLING 3
		// ===================================================================================
		// Pass on the activity result to the helper for handling
		 else if ( (!OpenGLRenderer.isGoogle) || (openGLRenderer.mHelper != null && !openGLRenderer.mHelper.handleActivityResult(requestCode, resultCode, data)) ) {
			           // not handled, so handle it ourselves (here's where you'd
			           // perform any handling of activity results not related to in-app
			           // billing...
			    	super.onActivityResult(requestCode, resultCode, data);
			    	// Facebook
			    	Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
			    	// Billing
			    	//openGLRenderer.mHelper.handleActivityResult(requestCode, resultCode, data);
			    	// Game Service
			    	//openGLRenderer.gameHelper.onActivityResult(requestCode, resultCode, data);
	    }
		// ===================================================================================
		 
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	 } 
	 
	/**
	 * Remember to resume the glSurface
	 */
	@Override
	protected void onResume() {
		super.onResume();
		openGLRenderer.onResume();
		// LEADBOLT
		AppTracker.resume(getApplicationContext()); 
		Log.i("OpenGL", "onResume called");
		try { adView.resume();  } catch (Exception e) { e.printStackTrace(); }

	}

	/**
	 * Also pause the glSurface
	 */
	
	@Override
	protected void onStart() {
		super.onStart();
		//try { if(openGLRenderer.ggs) openGLRenderer.gameHelper.onStart(this); } catch (Exception e) { e.printStackTrace(); }
	} 
	
	@Override
	protected void onPause() {
		// LEADBOLT
		try { adView.pause();  } catch (Exception e) { e.printStackTrace(); }

		if(!isFinishing()) { AppTracker.pause(getApplicationContext()); }
		openGLRenderer.onPause();
		Log.i("OpenGL", "onPause called");
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		//try { if(openGLRenderer.ggs) openGLRenderer.gameHelper.onStop(); } catch (Exception e) { e.printStackTrace(); }

	} 
	
	@Override
	protected void onDestroy() {
		try { adView.destroy(); } catch (Exception e) { e.printStackTrace(); }
		//if (OpenGLRenderer.isGoogle) try { BillingHelper.stopService(); } catch (Exception e) { e.printStackTrace(); } 
		try { openGLRenderer.onDestroy(); } catch (Exception e) { e.printStackTrace(); }
		try { SoundManager.cleanup(); } catch (Exception e) { e.printStackTrace(); }
		// LEADBOLT
		//if(lb_popupad != null) { lb_popupad.destroyAd(); }
		//if(lb_alertad != null) { lb_alertad.destroyAd(); }
		if(lb_reengagement != null) { lb_reengagement.destroyAd(); }
		AppTracker.closeSession(getApplicationContext(),true);

		super.onDestroy();
	}
	
	// LEADBOLT
	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event)
	{
		if (openGLRenderer!=null) openGLRenderer.onKeyDown(keyCode, event);
		return super.onKeyDown(keyCode, event);
	}

	public final void QuitGame() {
		Main.this.finish();
			//((Activity)getContext()).finish();
	}

}
