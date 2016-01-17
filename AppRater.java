package com.tubigames.galaxy.shooter.hd;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


import java.util.HashMap;
import java.util.List;

public class AppRater extends Main {
    private final static String APP_TITLE = "Galaxy Shooter";
    //TODO apk név megadása
    private final static String APP_PNAME = "com.tubigames.galaxy.shooter";
    
    private final static int DAYS_UNTIL_PROMPT = 0;
    private final static int LAUNCHES_UNTIL_PROMPT = 2;
    private static boolean launched_rate = false;
    private static boolean launched_promo = false;
    private static boolean launched_end = false;
    private static boolean launched_moregames = false;
    
    public static boolean launch_RateDialog(Context mContext, int num) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (launched_rate || prefs.getBoolean("dontshowagain_rate", false)) { return false; }
        SharedPreferences.Editor editor = prefs.edit();
        /*
        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }
        
        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch + 
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }
        */
        showRateDialog(mContext, editor, num);
        editor.commit();
        return true;
    }   

    public static boolean launch_PromoDialog(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (launched_promo || prefs.getBoolean("dontshowagain_promo", false)) { return false; }
        SharedPreferences.Editor editor = prefs.edit();
        
        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }
        
        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch + 
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showPromoDialog(mContext, editor);
            }
        }
        
        //showPromoDialog(mContext, editor);
        editor.commit();
        return true;
    }   

    public static boolean launch_EndDialog(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (launched_end || prefs.getBoolean("dontshowagain_end", false)) { Log.i("SC2", "Returning false"); return false; }
        SharedPreferences.Editor editor = prefs.edit();
        showEndDialog(mContext, editor);
        editor.commit();
        return true;
    }   
    
    public static boolean launch_MoreGamesDialog(Context mContext, boolean bt, int scrWidth, int scrHeight) {
        
    	SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
    	if (!bt) {
	        if (/*launched_moregames || */prefs.getBoolean("dontshowagain_moregames", false)) { return false; }
    	}
        SharedPreferences.Editor editor = prefs.edit();
        showMoreGamesDialog(mContext, editor, bt, scrWidth, scrHeight);
        editor.commit();
        return true;
    }   

    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor, int num) {
    	
    	launched_rate = true;
    	
    	try {
    	
    	final Dialog dialog = new Dialog(mContext);
        dialog.setTitle("Rate " + APP_TITLE);

        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.VERTICAL);
        
        TextView tv = new TextView(mContext);
        tv.setText("Outstanding performance! Your review would be a great help!");
        tv.setWidth(240);
        tv.setTextColor(0xFFF5DC49);
        tv.setPadding(4, 0, 4, 10);
        ll.addView(tv);
        
        Button b1 = new Button(mContext);
        b1.setText("Rate " + APP_TITLE);
        b1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	if (OpenGLRenderer.isGoogle) {
            		mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
            	} else {
            		mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=" + APP_PNAME)));
            	}
                if (editor != null) {
                    editor.putBoolean("dontshowagain_rate", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });        
        ll.addView(b1);

        Button b2 = new Button(mContext);
        b2.setText("Remind me later");
        b2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ll.addView(b2);

        if (num > 0) {
            Button b3 = new Button(mContext);
            b3.setText("No, thanks");
            b3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (editor != null) {
                        editor.putBoolean("dontshowagain_rate", true);
                        editor.commit();
                    }
                    dialog.dismiss();
                }
            });
            ll.addView(b3);
        }

        dialog.setContentView(ll);        
        dialog.show();
        
    	} 
    	catch (Exception e) 
	     {
	      e.printStackTrace();
	     }
    }
    
    
    public static void showPromoDialog(final Context mContext, final SharedPreferences.Editor editor) {
    	launched_promo = true;
    	
    	try {
    	
        final Dialog dialog = new Dialog(mContext);
        dialog.setTitle("Additional Features");

        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.VERTICAL);
        
        TextView tv = new TextView(mContext);
        tv.setText("Get free stuff from Facebook and unlock additional unique features!");
        tv.setWidth(240);
        tv.setTextColor(0xFFF5DC49);
        tv.setPadding(4, 0, 4, 10);
        ll.addView(tv);
        
        Button b1 = new Button(mContext);
        b1.setText("Get free bonus");
        b1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://on.fb.me/1oLaG0J")));
                if (editor != null) {
                    editor.putBoolean("dontshowagain_promo", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });        
        ll.addView(b1);

        Button b2 = new Button(mContext);
        b2.setText("Remind me later");
        b2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ll.addView(b2);

        Button b3 = new Button(mContext);
        b3.setText("No, thanks");
        b3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain_promo", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });
        ll.addView(b3);

        dialog.setContentView(ll);        
        dialog.show();   
        
    	} 
    	catch (Exception e) 
	     {
	      e.printStackTrace();
	     }
    }

    
    
    public static void showEndDialog(final Context mContext, final SharedPreferences.Editor editor) {
    	launched_end = true;
    	
    	try {
    		
        final Dialog dialog = new Dialog(mContext);
        dialog.setTitle("Congratulation!");

        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.VERTICAL);
        
        TextView tv = new TextView(mContext);
        tv.setText("You have reached Baby Boom Galaxy and discovered the source of the mysterious signal! In the following episodes, this Ultimate Weapon can be used against your enemies!" +
        		" Also if you have enjoyed playing " + APP_TITLE + ", share your experience with others!");
        tv.setWidth(240);
        tv.setTextColor(0xFFF5DC49);
        tv.setPadding(4, 0, 4, 10);
        ll.addView(tv);
        
        Button b1 = new Button(mContext);
        b1.setText("Comment " + APP_TITLE);
        b1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	if (OpenGLRenderer.isGoogle) {
            		mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
            	} else {
            		mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=" + APP_PNAME)));
            	}
                if (editor != null) {
                    editor.putBoolean("dontshowagain_end", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });        
        ll.addView(b1);

        Button b3 = new Button(mContext);
        b3.setText("No, thanks");
        b3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain_end", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });
        ll.addView(b3);

        dialog.setContentView(ll);        
        dialog.show();        
        
    	} 
    	catch (Exception e) 
	     {
	      e.printStackTrace();
	     }
    }

    
    public static void showMoreGamesDialog(final Context mContext, final SharedPreferences.Editor editor, boolean bt, int scrWidth, int scrHeight) {

	    	launched_moregames = true;
	    	
	    	try {
		
	        final Dialog dialog = new Dialog(mContext);
	        dialog.setTitle("Try our other games!");
	        //dialog.getWindow().setLayout(400, 600);
    
	        // linear layout
	        LinearLayout linear = new LinearLayout(mContext);
	        linear.setOrientation(LinearLayout.VERTICAL);
	        
	        // listview
	        ListView list = new ListView(mContext);
	        
	        int GAME_NUM = 15;
	        
	        String[] countries = new String[] {
	                "Cell Planet HD Tower Defender",
	                "Space Shooter: Star Invader", 
	                
	                "Find the Difference Italy Tour",
	                "Spot Differences in Paris, France",
	                "Find the Difference Thailand",
	                
	                "Horse Breeds & Pony Quiz",
	                "Dog Breeds Quiz",

	                "Tamago Egg vs RoboGo Fred",

	                "Bird Species Quiz HD",
	                "Desserts and Cakes Quiz HD",
	                "Flowers and Blossoms Quiz HD",
	                
	                "Kitty Cat Breeds Quiz",
	                "Aircrafts and Planes Quiz",
	                "Deep Sea & Ocean Fish Quiz",
	                "Most Beautiful Cities Quiz", 	            
	            };
	         
	            // Array of integers points to images stored in /res/drawable-ldpi/
	            int[] flags = new int[]{
		                R.drawable.feature_cellplanet,
		                R.drawable.feature_spaceshooter, 
		                
		                R.drawable.feature_italy,
		                R.drawable.feature_paris,
		                R.drawable.feature_thailand,

		                R.drawable.feature_horse,
		                R.drawable.feature_dog,

		                R.drawable.feature_tamago,

		                R.drawable.feature_bird,
		                R.drawable.feature_cake,
		                R.drawable.feature_flower,

		                R.drawable.feature_cat,
		                R.drawable.feature_aircraft,
		                R.drawable.feature_fish,
		                R.drawable.feature_city, 
	            };
	         
	            // Array of strings to store currencies
	            String[] currency = new String[]{
		                "In this Tower Defender game, you must defend the cell wall against waves of viruses and bacterias!",
		                "Space Shooter Star Invader is a top-down perspective space shooter game.", 
		                
		                "Find the differences between two pictures in time!",
		                "Observation skills are sharpened as you search for the differences.",
		                "3 game modes to choose from and 100 unique high quality (720p) pictures.",

		                "Get to know horse breed names as you progress to more levels playing an enjoyable quiz game.",
		                "Dog Breeds Quiz enables you to learn the name of the dog breeds in an effective way by the form of quiz.",

		                "Defeat Robo Go Fred and open the mysterious tamago egg and see what's inside!",

		                "Bird Species Quiz enables you to discover cute birds.",
		                "Learn the name of desserts and cakes with this quiz game.",
		                "Flowers and Blossoms Quiz enables you to learn the name of flowers in an effective way.",

		                "Get to know different cat breeds as you progress to more levels playing an enjoyable quiz game.",
		                "Aircrafts and Planes Quiz enables you to discover cool aircrafts.",
		                "Learn the name of Deep Sea creatures with this quiz game.",
		                "Most Beautiful Cities Quiz enables you to learn the name of the cities in an effective way.",
	            };
	        
	            List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
	            
	            for(int i=0;i<GAME_NUM;i++){
	                HashMap<String, String> hm = new HashMap<String,String>();
	                hm.put("txt", "" + countries[i]);
	                hm.put("cur","" + currency[i]);
	                hm.put("flag", Integer.toString(flags[i]) );
	                aList.add(hm);
	            }
	     
	            // Keys used in Hashmap
	            String[] from = { "flag","txt","cur" };
	     
	            // Ids of views in listview_layout
	            int[] to = { R.id.flag,R.id.txt,R.id.cur};

	            SimpleAdapter adapter;
	            if (scrWidth >= 720) adapter = new SimpleAdapter(mContext, aList, R.layout.listitemhd, from, to);
	            else if (scrWidth < 480) adapter = new SimpleAdapter(mContext, aList, R.layout.listitemlow, from, to);
	            else adapter = new SimpleAdapter(mContext, aList, R.layout.listitem, from, to);
	            
	            list.setAdapter(adapter);
	            
	            list.setPadding(2, 2, 2, 2);
	            list.setDividerHeight(1);
	            list.setCacheColorHint(Color.TRANSPARENT);
	            list.setBackgroundColor(Color.TRANSPARENT);
	            
	            list.setOnItemClickListener(new OnItemClickListener() {
	                @Override
	                public void onItemClick(AdapterView<?> arg0, View view, int arg2,long itemID) {
	                    View itemView = view;
	                    //int position = (int) arg0.getSelectedItemId();
	  					Intent intentM = new Intent(Intent.ACTION_VIEW);
	  					//if (isGoogle) intentM.setData(Uri.parse("market://details?id=com.tubigames.galaxy.shooter"));
	  					//TODO amazon store link
	  					//intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.galaxy.shooter"));
	                    switch (arg2) {
	                    case 0: 
	                    	if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.tubigames.cellplanet.hd"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=cellplanethd.apk")); 
	                    	break;
	                    case 1: 
	      					if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.tubigames.galaxy.shooter"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.galaxy.shooter")); 
	      					break;

	                    case 2: 
	                    	if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.mangata.find.difference.italy"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.mangata.find.difference.italy"));
	                    	break;
	                    case 3: 
	                    	if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.mangata.find.difference.paris"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.mangata.find.difference.paris"));
	                    	break;
	                    case 4: 
	                    	if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.mangata.find.difference.thailand"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.mangata.find.difference.thailand"));
	                    	break;

	                    case 5: 
	                    	if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.tubigames.horse.quiz"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.horse.quiz"));
	                    	break;
	                    case 6: 
	                    	if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.tubigames.dog.breeds.quiz"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.dog.breeds.quiz"));
	                    	break;

	                    case 7: 
	                    	if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.mangata.robot.tamago.hd"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.mangata.robot.tamago.hd"));
	                    	break;

	                    case 8: 
	                    	if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.mangata.birds.quiz"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.mangata.birds.quiz"));
	                    	break;
	                    case 9: 
	                    	if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.mangata.cakes.quiz"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.mangata.cakes.quiz"));
	                    	break;
	                    case 10: 
	                    	if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.mangata.flowers.quiz"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.mangata.flowers.quiz"));
	                    	break;

	                    	
	                    case 11: 
	                    	if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.tubigames.cat.breeds.quiz"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.cat.breeds.quiz"));
	                    	break;
	                    case 12: 
	                    	if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.tubigames.aircrafts.quiz"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.aircrafts.quiz"));
	                    	break;
	                    case 13: 
	                    	if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.tubigames.ocean.fish.quiz"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.ocean.fish.quiz"));
	                    	break;
	                    case 14: 
	                    	if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.tubigames.cities.quiz"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.cities.quiz"));
	                    	break;
	                    
	                    default:
	      					if (OpenGLRenderer.isGoogle) intentM.setData(Uri.parse("market://details?id=com.tubigames.galaxy.shooter"));
	                    	else intentM.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.tubigames.galaxy.shooter")); 
	      					break;
	                    };
	                    
	    				mContext.startActivity(intentM);
	
	                }
	            });
	
	            
	        float x = 1; float y = 1;
	        if (scrWidth >= 960) { x = 0.7f; y = 0.6f; }
	        else if (scrWidth >= 800) { x = 0.8f; y = 0.6f; }
	        else if (scrWidth >= 720) { x = 0.85f; y = 0.6f; }
	        else if (scrWidth >= 480) { x = 0.85f; y = 0.6f; }
	        else if (scrWidth >= 240) { x = 0.85f; y = 0.5f; }
	        else { x = 0.85f; y = 0.4f; }
	        if (scrWidth > scrHeight) { x *= 0.8f; }
	
	        int dgWidth = (int)(x * scrWidth);
	        int dgHeight = (int)(y * scrHeight);  
	        //if (dgHeight > scrHeight * 0.75f) dgHeight = scrHeight - 400;
	        Log.i("SC2", "scrh:" + Integer.toString(scrHeight));
	        Log.i("SC2", "dgh:" + Integer.toString(dgHeight));
	        linear.addView(list, dgWidth, dgHeight);
	        
	        if (!bt) {
		        CheckBox chk = new CheckBox(mContext);
		        chk.setText("Don't show this again");
		        chk.setTextColor(Color.WHITE);
		        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		        	   @Override
		        	   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		        		   if (isChecked) {
			   		        	if (editor != null) {
			   		        		Log.i("SC2", "Checkbox Checked");
					        		editor.putBoolean("dontshowagain_moregames", true);
					        		editor.commit();
					        	}
		        		   } else {
			   		        	if (editor != null) {
			   		        		Log.i("SC2", "Checkbox UnChecked");
					        		editor.putBoolean("dontshowagain_moregames", false);
					        		editor.commit();
					        	}
		        		   }
		        	   }
		        	});
		        
		        linear.addView(chk);

		        /*
		        // linear layout
		        LinearLayout blinear = new LinearLayout(mContext);
		        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		        	    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		        	params.weight = 1.0f;
		        blinear.setLayoutParams(params);
		        
		        Button b2 = new Button(mContext);
		        b2.setText("Quit");
		        b2.setOnClickListener(new OnClickListener() {
		            public void onClick(View v) {
		                //dialog.dismiss();
		                //((Activity)getContext()).finish();
		                //mContext.getc
		           	 	finish();
		           	 	//((Activity)getContext()).finish();
		            }
		        });
		        b2.setLayoutParams(params);
		        blinear.addView(b2);
	
		        Button b3 = new Button(mContext);
		        b3.setText("Cancel");
		        b3.setOnClickListener(new OnClickListener() {
		            public void onClick(View v) {
		                //dialog.dismiss();
		                dialog.dismiss();
		            }
		        });
		        b3.setLayoutParams(params);
		        blinear.addView(b3);
		        
		        linear.addView(blinear);
		        */
	        
	    	} //else {
		        Button b1 = new Button(mContext);
		        b1.setText("Close Window");
		        b1.setOnClickListener(new OnClickListener() {
		            public void onClick(View v) {
		                //dialog.dismiss();
		                dialog.dismiss();
		            }
		        });
		        linear.addView(b1);

	    	//}

	        dialog.setContentView(linear);        
	        dialog.show();        
	        
	    	} 
	    	catch (Exception e) 
		     {
		      e.printStackTrace();
		     }
    	}


    
}
