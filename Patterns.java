package com.tubigames.galaxy.shooter.hd;

public class Patterns {
	
	int Total_Patterns = 69;
	final float hd = OpenGLRenderer.hd; 
	
	public class ScriptCommand
	{
		int type = 1; // 0 = spawntoxy; 1 = movetoxy; 2 = restart pattern from command # x; 3 = destroy
		int x = 0; // 
		int y = 0; // 
		float speed = 1.0f; // move speed, 1.0 = default
	}

	public class MovePattern
	{
		int num = 0; // number of scriptcommand
		ScriptCommand[] script; // array of scriptcommand 
	}
	
	MovePattern[] movePattern;

	
	public Patterns() {
		
		// összes pattern létrehozása
		movePattern = new MovePattern[Total_Patterns];
		for (int i=0; i<Total_Patterns; i++) { movePattern[i] = new MovePattern(); }
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[0].num = 3; 
		movePattern[0].script = new ScriptCommand[movePattern[0].num];
		for (int i=0; i<movePattern[0].num; i++) { movePattern[0].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[0].script[0].type = 0; 
		movePattern[0].script[0].x= (int)(hd*0); movePattern[0].script[0].y= -(int)(hd*80);
		// move
		movePattern[0].script[1].type = 1; 
		movePattern[0].script[1].x= (int)(hd*0); movePattern[0].script[1].y= (int)(hd*800); 
		// destroy
		movePattern[0].script[2].type = 3; 

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[1].num = 3; 
		movePattern[1].script = new ScriptCommand[movePattern[1].num];
		for (int i=0; i<movePattern[1].num; i++) { movePattern[1].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[1].script[0].type = 0; 
		movePattern[1].script[0].x= (int)(hd*50); movePattern[1].script[0].y= -(int)(hd*80);
		// move
		movePattern[1].script[1].type = 1; 
		movePattern[1].script[1].x= (int)(hd*50); movePattern[1].script[1].y= (int)(hd*800); 
		// destroy
		movePattern[1].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[2].num = 3; 
		movePattern[2].script = new ScriptCommand[movePattern[2].num];
		for (int i=0; i<movePattern[2].num; i++) { movePattern[2].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[2].script[0].type = 0; 
		movePattern[2].script[0].x= (int)(hd*80); movePattern[2].script[0].y= -(int)(hd*80);
		// move
		movePattern[2].script[1].type = 1; 
		movePattern[2].script[1].x= (int)(hd*80); movePattern[2].script[1].y= (int)(hd*800); 
		// destroy                                                                           
		movePattern[2].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[3].num = 3; 
		movePattern[3].script = new ScriptCommand[movePattern[3].num];
		for (int i=0; i<movePattern[3].num; i++) { movePattern[3].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[3].script[0].type = 0; 
		movePattern[3].script[0].x= (int)(hd*100); movePattern[3].script[0].y= -(int)(hd*80);
		// move
		movePattern[3].script[1].type = 1; 
		movePattern[3].script[1].x= (int)(hd*100); movePattern[3].script[1].y= (int)(hd*800); 
		// destroy
		movePattern[3].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[4].num = 3; 
		movePattern[4].script = new ScriptCommand[movePattern[4].num];
		for (int i=0; i<movePattern[4].num; i++) { movePattern[4].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[4].script[0].type = 0; 
		movePattern[4].script[0].x= (int)(hd*150); movePattern[4].script[0].y= -(int)(hd*80);
		// move
		movePattern[4].script[1].type = 1; 
		movePattern[4].script[1].x= (int)(hd*150); movePattern[4].script[1].y= (int)(hd*800); 
		// destroy
		movePattern[4].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[5].num = 3; 
		movePattern[5].script = new ScriptCommand[movePattern[5].num];
		for (int i=0; i<movePattern[5].num; i++) { movePattern[5].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[5].script[0].type = 0; 
		movePattern[5].script[0].x= (int)(hd*200); movePattern[5].script[0].y= -(int)(hd*80);
		// move
		movePattern[5].script[1].type = 1; 
		movePattern[5].script[1].x= (int)(hd*200); movePattern[5].script[1].y= (int)(hd*800); 
		// destroy
		movePattern[5].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[6].num = 3; 
		movePattern[6].script = new ScriptCommand[movePattern[6].num];
		for (int i=0; i<movePattern[6].num; i++) { movePattern[6].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[6].script[0].type = 0; 
		movePattern[6].script[0].x= (int)(hd*240); movePattern[6].script[0].y= -(int)(hd*80);
		// move
		movePattern[6].script[1].type = 1; 
		movePattern[6].script[1].x= (int)(hd*240); movePattern[6].script[1].y= (int)(hd*800); 
		// destroy
		movePattern[6].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[7].num = 3; 
		movePattern[7].script = new ScriptCommand[movePattern[7].num];
		for (int i=0; i<movePattern[7].num; i++) { movePattern[7].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[7].script[0].type = 0; 
		movePattern[7].script[0].x= (int)(hd*280); movePattern[7].script[0].y= -(int)(hd*80);
		// move
		movePattern[7].script[1].type = 1; 
		movePattern[7].script[1].x= (int)(hd*280); movePattern[7].script[1].y= (int)(hd*800); 
		// destroy
		movePattern[7].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[8].num = 3; 
		movePattern[8].script = new ScriptCommand[movePattern[8].num];
		for (int i=0; i<movePattern[8].num; i++) { movePattern[8].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[8].script[0].type = 0; 
		movePattern[8].script[0].x= (int)(hd*300); movePattern[8].script[0].y= -(int)(hd*80);
		// move
		movePattern[8].script[1].type = 1; 
		movePattern[8].script[1].x= (int)(hd*300); movePattern[8].script[1].y= (int)(hd*800); 
		// destroy
		movePattern[8].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[9].num = 3; 
		movePattern[9].script = new ScriptCommand[movePattern[9].num];
		for (int i=0; i<movePattern[9].num; i++) { movePattern[9].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[9].script[0].type = 0; 
		movePattern[9].script[0].x= (int)(hd*360); movePattern[9].script[0].y= -(int)(hd*80);
		// move
		movePattern[9].script[1].type = 1; 
		movePattern[9].script[1].x= (int)(hd*360); movePattern[9].script[1].y= (int)(hd*800); 
		// destroy
		movePattern[9].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[10].num = 3; 
		movePattern[10].script = new ScriptCommand[movePattern[10].num];
		for (int i=0; i<movePattern[10].num; i++) { movePattern[10].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[10].script[0].type = 0; 
		movePattern[10].script[0].x= (int)(hd*400); movePattern[10].script[0].y= -(int)(hd*80);
		// move
		movePattern[10].script[1].type = 1; 
		movePattern[10].script[1].x= (int)(hd*400); movePattern[10].script[1].y= (int)(hd*800); 
		// destroy
		movePattern[10].script[2].type = 3; 
	
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[11].num = 3; 
		movePattern[11].script = new ScriptCommand[movePattern[11].num];
		for (int i=0; i<movePattern[11].num; i++) { movePattern[11].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[11].script[0].type = 0; 
		movePattern[11].script[0].x= -(int)(hd*80); movePattern[11].script[0].y= (int)(hd*100);
		// move
		movePattern[11].script[1].type = 1; 
		movePattern[11].script[1].x= (int)(hd*480); movePattern[11].script[1].y= (int)(hd*400); 
		// destroy
		movePattern[11].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[12].num = 3; 
		movePattern[12].script = new ScriptCommand[movePattern[12].num];
		for (int i=0; i<movePattern[12].num; i++) { movePattern[12].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[12].script[0].type = 0; 
		movePattern[12].script[0].x= -(int)(hd*80); movePattern[12].script[0].y= (int)(hd*100);
		// move
		movePattern[12].script[1].type = 1; 
		movePattern[12].script[1].x= (int)(hd*480); movePattern[12].script[1].y= (int)(hd*640); 
		// destroy
		movePattern[12].script[2].type = 3; 
	
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[13].num = 3; 
		movePattern[13].script = new ScriptCommand[movePattern[13].num];
		for (int i=0; i<movePattern[13].num; i++) { movePattern[13].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[13].script[0].type = 0; 
		movePattern[13].script[0].x= -(int)(hd*80); movePattern[13].script[0].y= (int)(hd*100);
		// move
		movePattern[13].script[1].type = 1; 
		movePattern[13].script[1].x= (int)(hd*400); movePattern[13].script[1].y= (int)(hd*800); 
		// destroy
		movePattern[13].script[2].type = 3; 
	
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[14].num = 3; 
		movePattern[14].script = new ScriptCommand[movePattern[14].num];
		for (int i=0; i<movePattern[14].num; i++) { movePattern[14].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[14].script[0].type = 0; 
		movePattern[14].script[0].x= (int)(hd*480); movePattern[14].script[0].y= (int)(hd*100);
		// move
		movePattern[14].script[1].type = 1; 
		movePattern[14].script[1].x= -(int)(hd*100); movePattern[14].script[1].y= (int)(hd*400); 
		// destroy
		movePattern[14].script[2].type = 3; 
	
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[15].num = 3; 
		movePattern[15].script = new ScriptCommand[movePattern[15].num];
		for (int i=0; i<movePattern[15].num; i++) { movePattern[15].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[15].script[0].type = 0; 
		movePattern[15].script[0].x= (int)(hd*480); movePattern[15].script[0].y= (int)(hd*100);
		// move
		movePattern[15].script[1].type = 1; 
		movePattern[15].script[1].x= -(int)(hd*100); movePattern[15].script[1].y= (int)(hd*640); 
		// destroy
		movePattern[15].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[16].num = 3; 
		movePattern[16].script = new ScriptCommand[movePattern[16].num];
		for (int i=0; i<movePattern[16].num; i++) { movePattern[16].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[16].script[0].type = 0; 
		movePattern[16].script[0].x= (int)(hd*480); movePattern[16].script[0].y= (int)(hd*100);
		// move
		movePattern[16].script[1].type = 1; 
		movePattern[16].script[1].x= (int)(hd*0); movePattern[16].script[1].y= (int)(hd*800); 
		// destroy
		movePattern[16].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[17].num = 5; 
		movePattern[17].script = new ScriptCommand[movePattern[17].num];
		for (int i=0; i<movePattern[17].num; i++) { movePattern[17].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[17].script[0].type = 0; 
		movePattern[17].script[0].x= (int)(hd*200); movePattern[17].script[0].y= -(int)(hd*80);
		// move
		movePattern[17].script[1].type = 1; 
		movePattern[17].script[1].x= (int)(hd*200); movePattern[17].script[1].y= (int)(hd*300);
		// move
		movePattern[17].script[2].type = 1; 
		movePattern[17].script[2].x= (int)(hd*0); movePattern[17].script[2].y= (int)(hd*480);
		// move
		movePattern[17].script[3].type = 1; 
		movePattern[17].script[3].x= (int)(hd*0); movePattern[17].script[3].y= (int)(hd*800); movePattern[17].script[3].speed = 2.0f;
		// destroy
		movePattern[17].script[4].type = 3; 	
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[18].num = 5; 
		movePattern[18].script = new ScriptCommand[movePattern[18].num];
		for (int i=0; i<movePattern[18].num; i++) { movePattern[18].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[18].script[0].type = 0; 
		movePattern[18].script[0].x= (int)(hd*200); movePattern[18].script[0].y= -(int)(hd*80);
		// move
		movePattern[18].script[1].type = 1; 
		movePattern[18].script[1].x= (int)(hd*200); movePattern[18].script[1].y= (int)(hd*300);
		// move
		movePattern[18].script[2].type = 1; 
		movePattern[18].script[2].x= (int)(hd*400); movePattern[18].script[2].y= (int)(hd*480);
		// move
		movePattern[18].script[3].type = 1; 
		movePattern[18].script[3].x= (int)(hd*400); movePattern[18].script[3].y= (int)(hd*800); movePattern[18].script[3].speed = 2.0f;
		// destroy
		movePattern[18].script[4].type = 3; 	
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[19].num = 9; 
		movePattern[19].script = new ScriptCommand[movePattern[19].num];
		for (int i=0; i<movePattern[19].num; i++) { movePattern[19].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[19].script[0].type = 0; 
		movePattern[19].script[0].x= -(int)(hd*80); movePattern[19].script[0].y= (int)(hd*560);
		// fast
		movePattern[19].script[1].type = 1; 
		movePattern[19].script[1].x= (int)(hd*200); movePattern[19].script[1].y= (int)(hd*320); 
		// move 
		movePattern[19].script[2].type = 1; 
		movePattern[19].script[2].x= (int)(hd*200); movePattern[19].script[2].y= (int)(hd*180);
		// move 
		movePattern[19].script[3].type = 1; 
		movePattern[19].script[3].x= (int)(hd*400); movePattern[19].script[3].y= (int)(hd*180); 
		// move
		movePattern[19].script[4].type = 1; 
		movePattern[19].script[4].x= (int)(hd*0); movePattern[19].script[4].y= (int)(hd*180); 
		// move
		movePattern[19].script[5].type = 1; 
		movePattern[19].script[5].x= (int)(hd*0); movePattern[19].script[5].y= (int)(hd*320); 
		// move
		movePattern[19].script[6].type = 1; 
		movePattern[19].script[6].x= (int)(hd*400); movePattern[19].script[6].y= (int)(hd*360); 
		// move
		movePattern[19].script[7].type = 1; 
		movePattern[19].script[7].x= (int)(hd*400); movePattern[19].script[7].y= (int)(hd*800); movePattern[19].script[7].speed = 2.0f;
		// destroy
		movePattern[19].script[8].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[20].num = 9; 
		movePattern[20].script = new ScriptCommand[movePattern[20].num];
		for (int i=0; i<movePattern[20].num; i++) { movePattern[20].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[20].script[0].type = 0; 
		movePattern[20].script[0].x= (int)(hd*480); movePattern[20].script[0].y= (int)(hd*560);
		// fast
		movePattern[20].script[1].type = 1; 
		movePattern[20].script[1].x= (int)(hd*200); movePattern[20].script[1].y= (int)(hd*320); 
		// move 
		movePattern[20].script[2].type = 1; 
		movePattern[20].script[2].x= (int)(hd*200); movePattern[20].script[2].y= (int)(hd*180);
		// move 
		movePattern[20].script[3].type = 1; 
		movePattern[20].script[3].x= (int)(hd*0); movePattern[20].script[3].y= (int)(hd*180); 
		// move
		movePattern[20].script[4].type = 1; 
		movePattern[20].script[4].x= (int)(hd*400); movePattern[20].script[4].y= (int)(hd*180); 
		// move
		movePattern[20].script[5].type = 1; 
		movePattern[20].script[5].x= (int)(hd*400); movePattern[20].script[5].y= (int)(hd*320); 
		// move
		movePattern[20].script[6].type = 1; 
		movePattern[20].script[6].x= (int)(hd*0); movePattern[20].script[6].y= (int)(hd*360); 
		// move
		movePattern[20].script[7].type = 1; 
		movePattern[20].script[7].x= (int)(hd*0); movePattern[20].script[7].y= (int)(hd*800); movePattern[20].script[7].speed = 2.0f;
		// destroy
		movePattern[20].script[8].type = 3; 		
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[21].num = 7; 
		movePattern[21].script = new ScriptCommand[movePattern[21].num];
		for (int i=0; i<movePattern[21].num; i++) { movePattern[21].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[21].script[0].type = 0; 
		movePattern[21].script[0].x= (int)(hd*40); movePattern[21].script[0].y= -(int)(hd*80);
		//  move
		movePattern[21].script[1].type = 1; 
		movePattern[21].script[1].x= (int)(hd*40); movePattern[21].script[1].y= (int)(hd*300); 
		// move 
		movePattern[21].script[2].type = 1; 
		movePattern[21].script[2].x= (int)(hd*100); movePattern[21].script[2].y= (int)(hd*400);
		// move 
		movePattern[21].script[3].type = 1; 
		movePattern[21].script[3].x= (int)(hd*280); movePattern[21].script[3].y= (int)(hd*500); 
		// move 
		movePattern[21].script[4].type = 1; 
		movePattern[21].script[4].x= (int)(hd*340); movePattern[21].script[4].y= (int)(hd*600);
		// move
		movePattern[21].script[5].type = 1; 
		movePattern[21].script[5].x= (int)(hd*480); movePattern[21].script[5].y= (int)(hd*600); 
		// destroy
		movePattern[21].script[6].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[22].num = 7; 
		movePattern[22].script = new ScriptCommand[movePattern[22].num];
		for (int i=0; i<movePattern[22].num; i++) { movePattern[22].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[22].script[0].type = 0; 
		movePattern[22].script[0].x= (int)(hd*360); movePattern[22].script[0].y= -(int)(hd*80);
		//  move
		movePattern[22].script[1].type = 1; 
		movePattern[22].script[1].x= (int)(hd*360); movePattern[22].script[1].y= (int)(hd*300); 
		// move 
		movePattern[22].script[2].type = 1; 
		movePattern[22].script[2].x= (int)(hd*300); movePattern[22].script[2].y= (int)(hd*400);
		// move 
		movePattern[22].script[3].type = 1; 
		movePattern[22].script[3].x= (int)(hd*120); movePattern[22].script[3].y= (int)(hd*500); 
		// move 
		movePattern[22].script[4].type = 1; 
		movePattern[22].script[4].x= (int)(hd*60); movePattern[22].script[4].y= (int)(hd*600);
		// move
		movePattern[22].script[5].type = 1; 
		movePattern[22].script[5].x= -(int)(hd*100); movePattern[22].script[5].y= (int)(hd*600); 
		// destroy
		movePattern[22].script[6].type = 3; 		
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[23].num = 7; 
		movePattern[23].script = new ScriptCommand[movePattern[23].num];
		for (int i=0; i<movePattern[23].num; i++) { movePattern[23].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[23].script[0].type = 0; 
		movePattern[23].script[0].x= -(int)(hd*80); movePattern[23].script[0].y= (int)(hd*150);
		//  move
		movePattern[23].script[1].type = 1; 
		movePattern[23].script[1].x= (int)(hd*500); movePattern[23].script[1].y= (int)(hd*150); 
		// move 
		movePattern[23].script[2].type = 1; 
		movePattern[23].script[2].x= (int)(hd*500); movePattern[23].script[2].y= (int)(hd*330);
		// move 
		movePattern[23].script[3].type = 1; 
		movePattern[23].script[3].x= -(int)(hd*100); movePattern[23].script[3].y= (int)(hd*330); 
		// move 
		movePattern[23].script[4].type = 1; 
		movePattern[23].script[4].x= -(int)(hd*100); movePattern[23].script[4].y= (int)(hd*510);
		// move
		movePattern[23].script[5].type = 1; 
		movePattern[23].script[5].x= (int)(hd*480); movePattern[23].script[5].y= (int)(hd*510); 
		// destroy
		movePattern[23].script[6].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[24].num = 7; 
		movePattern[24].script = new ScriptCommand[movePattern[24].num];
		for (int i=0; i<movePattern[24].num; i++) { movePattern[24].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[24].script[0].type = 0; 
		movePattern[24].script[0].x= (int)(hd*480); movePattern[24].script[0].y= (int)(hd*150);
		//  move
		movePattern[24].script[1].type = 1; 
		movePattern[24].script[1].x= -(int)(hd*100); movePattern[24].script[1].y= (int)(hd*150); 
		// move 
		movePattern[24].script[2].type = 1; 
		movePattern[24].script[2].x= -(int)(hd*100); movePattern[24].script[2].y= (int)(hd*330);
		// move 
		movePattern[24].script[3].type = 1; 
		movePattern[24].script[3].x= (int)(hd*500); movePattern[24].script[3].y= (int)(hd*330); 
		// move 
		movePattern[24].script[4].type = 1; 
		movePattern[24].script[4].x= (int)(hd*500); movePattern[24].script[4].y= (int)(hd*510);
		// move
		movePattern[24].script[5].type = 1; 
		movePattern[24].script[5].x= -(int)(hd*100); movePattern[24].script[5].y= (int)(hd*510); 
		// destroy
		movePattern[24].script[6].type = 3; 		
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[25].num = 5; 
		movePattern[25].script = new ScriptCommand[movePattern[25].num];
		for (int i=0; i<movePattern[25].num; i++) { movePattern[25].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[25].script[0].type = 0; 
		movePattern[25].script[0].x= (int)(hd*360); movePattern[25].script[0].y= -(int)(hd*80);
		//  move
		movePattern[25].script[1].type = 1; 
		movePattern[25].script[1].x= (int)(hd*360); movePattern[25].script[1].y= (int)(hd*120); 
		// move 
		movePattern[25].script[2].type = 1; 
		movePattern[25].script[2].x= (int)(hd*50); movePattern[25].script[2].y= (int)(hd*400);
		// move 
		movePattern[25].script[3].type = 1; 
		movePattern[25].script[3].x= (int)(hd*50); movePattern[25].script[3].y= (int)(hd*800); 
		// destroy
		movePattern[25].script[4].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[26].num = 5; 
		movePattern[26].script = new ScriptCommand[movePattern[26].num];
		for (int i=0; i<movePattern[26].num; i++) { movePattern[26].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[26].script[0].type = 0; 
		movePattern[26].script[0].x= (int)(hd*40); movePattern[26].script[0].y= -(int)(hd*80);
		//  move
		movePattern[26].script[1].type = 1; 
		movePattern[26].script[1].x= (int)(hd*40); movePattern[26].script[1].y= (int)(hd*120); 
		// move 
		movePattern[26].script[2].type = 1; 
		movePattern[26].script[2].x= (int)(hd*350); movePattern[26].script[2].y= (int)(hd*400);
		// move 
		movePattern[26].script[3].type = 1; 
		movePattern[26].script[3].x= (int)(hd*350); movePattern[26].script[3].y= (int)(hd*800); 
		// destroy
		movePattern[26].script[4].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[27].num = 9; 
		movePattern[27].script = new ScriptCommand[movePattern[27].num];
		for (int i=0; i<movePattern[27].num; i++) { movePattern[27].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[27].script[0].type = 0; 
		movePattern[27].script[0].x= -(int)(hd*80); movePattern[27].script[0].y= (int)(hd*500);
		//  move
		movePattern[27].script[1].type = 1; 
		movePattern[27].script[1].x= (int)(hd*320); movePattern[27].script[1].y= (int)(hd*300); 
		// move 
		movePattern[27].script[2].type = 1; 
		movePattern[27].script[2].x= (int)(hd*320); movePattern[27].script[2].y= (int)(hd*100);
		// move 
		movePattern[27].script[3].type = 1; 
		movePattern[27].script[3].x= (int)(hd*80); movePattern[27].script[3].y= (int)(hd*100); 
		// move 
		movePattern[27].script[4].type = 1; 
		movePattern[27].script[4].x= (int)(hd*80); movePattern[27].script[4].y= (int)(hd*200);
		// move
		movePattern[27].script[5].type = 1; 
		movePattern[27].script[5].x= (int)(hd*320); movePattern[27].script[5].y= (int)(hd*200); 
		// move
		movePattern[27].script[6].type = 1; 
		movePattern[27].script[6].x= (int)(hd*320); movePattern[27].script[6].y= (int)(hd*500); 
		// move
		movePattern[27].script[7].type = 1; 
		movePattern[27].script[7].x= -(int)(hd*100); movePattern[27].script[7].y= (int)(hd*500); 
		// destroy
		movePattern[27].script[8].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[28].num = 9; 
		movePattern[28].script = new ScriptCommand[movePattern[28].num];
		for (int i=0; i<movePattern[28].num; i++) { movePattern[28].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[28].script[0].type = 0; 
		movePattern[28].script[0].x= (int)(hd*480); movePattern[28].script[0].y= (int)(hd*500);
		//  move
		movePattern[28].script[1].type = 1; 
		movePattern[28].script[1].x= (int)(hd*80); movePattern[28].script[1].y= (int)(hd*300); 
		// move 
		movePattern[28].script[2].type = 1; 
		movePattern[28].script[2].x= (int)(hd*80); movePattern[28].script[2].y= (int)(hd*100);
		// move 
		movePattern[28].script[3].type = 1; 
		movePattern[28].script[3].x= (int)(hd*320); movePattern[28].script[3].y= (int)(hd*100); 
		// move 
		movePattern[28].script[4].type = 1; 
		movePattern[28].script[4].x= (int)(hd*320); movePattern[28].script[4].y= (int)(hd*200);
		// move
		movePattern[28].script[5].type = 1; 
		movePattern[28].script[5].x= (int)(hd*80); movePattern[28].script[5].y= (int)(hd*200); 
		// move
		movePattern[28].script[6].type = 1; 
		movePattern[28].script[6].x= (int)(hd*80); movePattern[28].script[6].y= (int)(hd*500); 
		// move
		movePattern[28].script[7].type = 1; 
		movePattern[28].script[7].x= (int)(hd*480); movePattern[28].script[7].y= (int)(hd*500); 
		// destroy
		movePattern[28].script[8].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[29].num = 3; 
		movePattern[29].script = new ScriptCommand[movePattern[29].num];
		for (int i=0; i<movePattern[29].num; i++) { movePattern[29].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[29].script[0].type = 0; 
		movePattern[29].script[0].x= -(int)(hd*80); movePattern[29].script[0].y= (int)(hd*200);
		//  move
		movePattern[29].script[1].type = 1; 
		movePattern[29].script[1].x= (int)(hd*480); movePattern[29].script[1].y= (int)(hd*200); 
		// destroy
		movePattern[29].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[30].num = 3; 
		movePattern[30].script = new ScriptCommand[movePattern[30].num];
		for (int i=0; i<movePattern[30].num; i++) { movePattern[30].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[30].script[0].type = 0; 
		movePattern[30].script[0].x= (int)(hd*480); movePattern[30].script[0].y= (int)(hd*200);
		//  move
		movePattern[30].script[1].type = 1; 
		movePattern[30].script[1].x= -(int)(hd*100); movePattern[30].script[1].y= (int)(hd*200); 
		// destroy
		movePattern[30].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[31].num = 3; 
		movePattern[31].script = new ScriptCommand[movePattern[31].num];
		for (int i=0; i<movePattern[31].num; i++) { movePattern[31].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[31].script[0].type = 0; 
		movePattern[31].script[0].x= -(int)(hd*80); movePattern[31].script[0].y= (int)(hd*320);
		//  move
		movePattern[31].script[1].type = 1; 
		movePattern[31].script[1].x= (int)(hd*480); movePattern[31].script[1].y= (int)(hd*320); 
		// destroy
		movePattern[31].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[32].num = 3; 
		movePattern[32].script = new ScriptCommand[movePattern[32].num];
		for (int i=0; i<movePattern[32].num; i++) { movePattern[32].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[32].script[0].type = 0; 
		movePattern[32].script[0].x= (int)(hd*480); movePattern[32].script[0].y= (int)(hd*320);
		//  move
		movePattern[32].script[1].type = 1; 
		movePattern[32].script[1].x= -(int)(hd*100); movePattern[32].script[1].y= (int)(hd*320); 
		// destroy
		movePattern[32].script[2].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[33].num = 5; 
		movePattern[33].script = new ScriptCommand[movePattern[33].num];
		for (int i=0; i<movePattern[33].num; i++) { movePattern[33].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[33].script[0].type = 0; 
		movePattern[33].script[0].x= (int)(hd*160); movePattern[33].script[0].y= -(int)(hd*80);
		//  move
		movePattern[33].script[1].type = 1; 
		movePattern[33].script[1].x= (int)(hd*160); movePattern[33].script[1].y= (int)(hd*100); 
		// move 
		movePattern[33].script[2].type = 1; 
		movePattern[33].script[2].x= (int)(hd*400); movePattern[33].script[2].y= (int)(hd*100);
		// move 
		movePattern[33].script[3].type = 1; 
		movePattern[33].script[3].x= (int)(hd*0); movePattern[33].script[3].y= (int)(hd*100); 
		// repeat 
		movePattern[33].script[4].type = 2; 
		movePattern[33].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[34].num = 5; 
		movePattern[34].script = new ScriptCommand[movePattern[34].num];
		for (int i=0; i<movePattern[34].num; i++) { movePattern[34].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[34].script[0].type = 0; 
		movePattern[34].script[0].x= (int)(hd*240); movePattern[34].script[0].y= -(int)(hd*80);
		//  move
		movePattern[34].script[1].type = 1; 
		movePattern[34].script[1].x= (int)(hd*240); movePattern[34].script[1].y= (int)(hd*220); 
		// move 
		movePattern[34].script[2].type = 1; 
		movePattern[34].script[2].x= (int)(hd*0); movePattern[34].script[2].y= (int)(hd*220);
		// move 
		movePattern[34].script[3].type = 1; 
		movePattern[34].script[3].x= (int)(hd*400); movePattern[34].script[3].y= (int)(hd*220); 
		// repeat 
		movePattern[34].script[4].type = 2; 
		movePattern[34].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[35].num = 4; 
		movePattern[35].script = new ScriptCommand[movePattern[35].num];
		for (int i=0; i<movePattern[35].num; i++) { movePattern[35].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[35].script[0].type = 0; 
		movePattern[35].script[0].x= -(int)(hd*80); movePattern[35].script[0].y= (int)(hd*40);
		//  move
		movePattern[35].script[1].type = 1; 
		movePattern[35].script[1].x= (int)(hd*200); movePattern[35].script[1].y= (int)(hd*240); 
		// move 
		movePattern[35].script[2].type = 1; 
		movePattern[35].script[2].x= (int)(hd*200); movePattern[35].script[2].y= (int)(hd*800);
		// destroy
		movePattern[35].script[3].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[36].num = 4; 
		movePattern[36].script = new ScriptCommand[movePattern[36].num];
		for (int i=0; i<movePattern[36].num; i++) { movePattern[36].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[36].script[0].type = 0; 
		movePattern[36].script[0].x= -(int)(hd*80); movePattern[36].script[0].y= (int)(hd*40);
		//  move
		movePattern[36].script[1].type = 1; 
		movePattern[36].script[1].x= (int)(hd*200); movePattern[36].script[1].y= (int)(hd*400); 
		// move 
		movePattern[36].script[2].type = 1; 
		movePattern[36].script[2].x= (int)(hd*200); movePattern[36].script[2].y= (int)(hd*800);
		// destroy
		movePattern[36].script[3].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[37].num = 4; 
		movePattern[37].script = new ScriptCommand[movePattern[37].num];
		for (int i=0; i<movePattern[37].num; i++) { movePattern[37].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[37].script[0].type = 0; 
		movePattern[37].script[0].x= (int)(hd*480); movePattern[37].script[0].y= (int)(hd*40);
		//  move
		movePattern[37].script[1].type = 1; 
		movePattern[37].script[1].x= (int)(hd*200); movePattern[37].script[1].y= (int)(hd*240); 
		// move 
		movePattern[37].script[2].type = 1; 
		movePattern[37].script[2].x= (int)(hd*200); movePattern[37].script[2].y= (int)(hd*800);
		// destroy
		movePattern[37].script[3].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[38].num = 4; 
		movePattern[38].script = new ScriptCommand[movePattern[38].num];
		for (int i=0; i<movePattern[38].num; i++) { movePattern[38].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[38].script[0].type = 0; 
		movePattern[38].script[0].x= (int)(hd*480); movePattern[38].script[0].y= (int)(hd*40);
		//  move
		movePattern[38].script[1].type = 1; 
		movePattern[38].script[1].x= (int)(hd*200); movePattern[38].script[1].y= (int)(hd*400); 
		// move 
		movePattern[38].script[2].type = 1; 
		movePattern[38].script[2].x= (int)(hd*200); movePattern[38].script[2].y= (int)(hd*800);
		// destroy
		movePattern[38].script[3].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[39].num = 13; 
		movePattern[39].script = new ScriptCommand[movePattern[39].num];
		for (int i=0; i<movePattern[39].num; i++) { movePattern[39].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[39].script[0].type = 0; 
		movePattern[39].script[0].x= -(int)(hd*80); movePattern[39].script[0].y= (int)(hd*250);
		//  move
		movePattern[39].script[1].type = 1; 
		movePattern[39].script[1].x= (int)(hd*140); movePattern[39].script[1].y= (int)(hd*100); 
		// move 
		movePattern[39].script[2].type = 1; 
		movePattern[39].script[2].x= (int)(hd*260); movePattern[39].script[2].y= (int)(hd*100);
		// move 
		movePattern[39].script[3].type = 1; 
		movePattern[39].script[3].x= (int)(hd*360); movePattern[39].script[3].y= (int)(hd*250); 
		// move 
		movePattern[39].script[4].type = 1; 
		movePattern[39].script[4].x= (int)(hd*360); movePattern[39].script[4].y= (int)(hd*350);
		// move
		movePattern[39].script[5].type = 1; 
		movePattern[39].script[5].x= (int)(hd*260); movePattern[39].script[5].y= (int)(hd*500); 
		// move
		movePattern[39].script[6].type = 1; 
		movePattern[39].script[6].x= (int)(hd*140); movePattern[39].script[6].y= (int)(hd*500); 
		// move
		movePattern[39].script[7].type = 1; 
		movePattern[39].script[7].x= (int)(hd*40); movePattern[39].script[7].y= (int)(hd*350); 
		// move
		movePattern[39].script[8].type = 1; 
		movePattern[39].script[8].x= (int)(hd*40); movePattern[39].script[8].y= (int)(hd*250); 		
		// move
		movePattern[39].script[9].type = 1; 
		movePattern[39].script[9].x= (int)(hd*140); movePattern[39].script[9].y= (int)(hd*100); 		
		// move
		movePattern[39].script[10].type = 1; 
		movePattern[39].script[10].x= (int)(hd*260); movePattern[39].script[10].y= (int)(hd*100); 
		// move
		movePattern[39].script[11].type = 1; 
		movePattern[39].script[11].x= (int)(hd*480); movePattern[39].script[11].y= (int)(hd*250); 
		// destroy
		movePattern[39].script[12].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[40].num = 13; 
		movePattern[40].script = new ScriptCommand[movePattern[40].num];
		for (int i=0; i<movePattern[40].num; i++) { movePattern[40].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[40].script[0].type = 0; 
		movePattern[40].script[0].x= (int)(hd*480); movePattern[40].script[0].y= (int)(hd*250);
		//  move
		movePattern[40].script[1].type = 1; 
		movePattern[40].script[1].x= (int)(hd*260); movePattern[40].script[1].y= (int)(hd*100); 
		// move 
		movePattern[40].script[2].type = 1; 
		movePattern[40].script[2].x= (int)(hd*140); movePattern[40].script[2].y= (int)(hd*100);
		// move 
		movePattern[40].script[3].type = 1; 
		movePattern[40].script[3].x= (int)(hd*40); movePattern[40].script[3].y= (int)(hd*250); 
		// move 
		movePattern[40].script[4].type = 1; 
		movePattern[40].script[4].x= (int)(hd*40); movePattern[40].script[4].y= (int)(hd*350);
		// move
		movePattern[40].script[5].type = 1; 
		movePattern[40].script[5].x= (int)(hd*140); movePattern[40].script[5].y= (int)(hd*500); 
		// move
		movePattern[40].script[6].type = 1; 
		movePattern[40].script[6].x= (int)(hd*260); movePattern[40].script[6].y= (int)(hd*500); 
		// move
		movePattern[40].script[7].type = 1; 
		movePattern[40].script[7].x= (int)(hd*360); movePattern[40].script[7].y= (int)(hd*350); 
		// move
		movePattern[40].script[8].type = 1; 
		movePattern[40].script[8].x= (int)(hd*360); movePattern[40].script[8].y= (int)(hd*250); 		
		// move
		movePattern[40].script[9].type = 1; 
		movePattern[40].script[9].x= (int)(hd*260); movePattern[40].script[9].y= (int)(hd*100); 		
		// move
		movePattern[40].script[10].type = 1; 
		movePattern[40].script[10].x= (int)(hd*140); movePattern[40].script[10].y= (int)(hd*100); 
		// move
		movePattern[40].script[11].type = 1; 
		movePattern[40].script[11].x= -(int)(hd*100); movePattern[40].script[11].y= (int)(hd*250); 
		// destroy
		movePattern[40].script[12].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[41].num = 7; 
		movePattern[41].script = new ScriptCommand[movePattern[41].num];
		for (int i=0; i<movePattern[41].num; i++) { movePattern[41].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[41].script[0].type = 0; 
		movePattern[41].script[0].x= (int)(hd*60); movePattern[41].script[0].y= -(int)(hd*80); 
		//  move
		movePattern[41].script[1].type = 1; 
		movePattern[41].script[1].x= (int)(hd*60); movePattern[41].script[1].y= (int)(hd*110); movePattern[41].script[5].speed = 4.0f;
		// move 
		movePattern[41].script[2].type = 1; 
		movePattern[41].script[2].x= (int)(hd*340); movePattern[41].script[2].y= (int)(hd*110);
		// move 
		movePattern[41].script[3].type = 1; 
		movePattern[41].script[3].x= (int)(hd*80); movePattern[41].script[3].y= (int)(hd*330); 
		// move 
		movePattern[41].script[4].type = 1; 
		movePattern[41].script[4].x= (int)(hd*340); movePattern[41].script[4].y= (int)(hd*560);
		// move
		movePattern[41].script[5].type = 1; 
		movePattern[41].script[5].x= (int)(hd*340); movePattern[41].script[5].y= (int)(hd*800); movePattern[41].script[5].speed = 2.0f;
		// destroy
		movePattern[41].script[6].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[42].num = 7; 
		movePattern[42].script = new ScriptCommand[movePattern[42].num];
		for (int i=0; i<movePattern[42].num; i++) { movePattern[42].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[42].script[0].type = 0; 
		movePattern[42].script[0].x= (int)(hd*340); movePattern[42].script[0].y= -(int)(hd*80); 
		//  move
		movePattern[42].script[1].type = 1; 
		movePattern[42].script[1].x= (int)(hd*340); movePattern[42].script[1].y= (int)(hd*110); movePattern[42].script[5].speed = 4.0f;
		// move 
		movePattern[42].script[2].type = 1; 
		movePattern[42].script[2].x= (int)(hd*60); movePattern[42].script[2].y= (int)(hd*110);
		// move 
		movePattern[42].script[3].type = 1; 
		movePattern[42].script[3].x= (int)(hd*320); movePattern[42].script[3].y= (int)(hd*330); 
		// move 
		movePattern[42].script[4].type = 1; 
		movePattern[42].script[4].x= (int)(hd*60); movePattern[42].script[4].y= (int)(hd*560);
		// move
		movePattern[42].script[5].type = 1; 
		movePattern[42].script[5].x= (int)(hd*60); movePattern[42].script[5].y= (int)(hd*800); movePattern[42].script[5].speed = 2.0f;
		// destroy
		movePattern[42].script[6].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[43].num = 5; 
		movePattern[43].script = new ScriptCommand[movePattern[43].num];
		for (int i=0; i<movePattern[43].num; i++) { movePattern[43].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[43].script[0].type = 0; 
		movePattern[43].script[0].x= (int)(hd*0); movePattern[43].script[0].y= -(int)(hd*80);
		//  move
		movePattern[43].script[1].type = 1; 
		movePattern[43].script[1].x= (int)(hd*0); movePattern[43].script[1].y= (int)(hd*560); 
		// move 
		movePattern[43].script[2].type = 1; 
		movePattern[43].script[2].x= (int)(hd*400); movePattern[43].script[2].y= (int)(hd*40);
		// move 
		movePattern[43].script[3].type = 1; 
		movePattern[43].script[3].x= (int)(hd*400); movePattern[43].script[3].y= (int)(hd*800); 
		// destroy
		movePattern[43].script[4].type = 3; 
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[44].num = 5; 
		movePattern[44].script = new ScriptCommand[movePattern[44].num];
		for (int i=0; i<movePattern[44].num; i++) { movePattern[44].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[44].script[0].type = 0; 
		movePattern[44].script[0].x= (int)(hd*400); movePattern[44].script[0].y= -(int)(hd*80);
		//  move
		movePattern[44].script[1].type = 1; 
		movePattern[44].script[1].x= (int)(hd*400); movePattern[44].script[1].y= (int)(hd*560); 
		// move 
		movePattern[44].script[2].type = 1; 
		movePattern[44].script[2].x= (int)(hd*0); movePattern[44].script[2].y= (int)(hd*40);
		// move 
		movePattern[44].script[3].type = 1; 
		movePattern[44].script[3].x= (int)(hd*0); movePattern[44].script[3].y= (int)(hd*800); 
		// destroy
		movePattern[44].script[4].type = 3; 

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[45].num = 5; 
		movePattern[45].script = new ScriptCommand[movePattern[45].num];
		for (int i=0; i<movePattern[45].num; i++) { movePattern[45].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[45].script[0].type = 0; 
		movePattern[45].script[0].x= (int)(hd*100); movePattern[45].script[0].y= -(int)(hd*80);
		//  move
		movePattern[45].script[1].type = 1; 
		movePattern[45].script[1].x= (int)(hd*100); movePattern[45].script[1].y= (int)(hd*120); 
		// move 
		movePattern[45].script[2].type = 1; 
		movePattern[45].script[2].x= (int)(hd*20); movePattern[45].script[2].y= (int)(hd*120); movePattern[45].script[2].speed = 0.25f;
		// move 
		movePattern[45].script[3].type = 1; 
		movePattern[45].script[3].x= (int)(hd*180); movePattern[45].script[3].y= (int)(hd*120); movePattern[45].script[3].speed = 0.25f;
		// repeat 
		movePattern[45].script[4].type = 2; 
		movePattern[45].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[46].num = 5; 
		movePattern[46].script = new ScriptCommand[movePattern[46].num];
		for (int i=0; i<movePattern[46].num; i++) { movePattern[46].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[46].script[0].type = 0; 
		movePattern[46].script[0].x= (int)(hd*167); movePattern[46].script[0].y= -(int)(hd*80);
		//  move
		movePattern[46].script[1].type = 1; 
		movePattern[46].script[1].x= (int)(hd*167); movePattern[46].script[1].y= (int)(hd*120); 
		// move 
		movePattern[46].script[2].type = 1; 
		movePattern[46].script[2].x= (int)(hd*87); movePattern[46].script[2].y= (int)(hd*120); movePattern[46].script[2].speed = 0.25f;
		// move 
		movePattern[46].script[3].type = 1; 
		movePattern[46].script[3].x= (int)(hd*247); movePattern[46].script[3].y= (int)(hd*120); movePattern[46].script[3].speed = 0.25f;
		// repeat 
		movePattern[46].script[4].type = 2; 
		movePattern[46].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[47].num = 5; 
		movePattern[47].script = new ScriptCommand[movePattern[47].num];
		for (int i=0; i<movePattern[47].num; i++) { movePattern[47].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[47].script[0].type = 0; 
		movePattern[47].script[0].x= (int)(hd*234); movePattern[47].script[0].y= -(int)(hd*80);
		//  move
		movePattern[47].script[1].type = 1; 
		movePattern[47].script[1].x= (int)(hd*234); movePattern[47].script[1].y= (int)(hd*120); 
		// move 
		movePattern[47].script[2].type = 1; 
		movePattern[47].script[2].x= (int)(hd*154); movePattern[47].script[2].y= (int)(hd*120); movePattern[47].script[2].speed = 0.25f;
		// move 
		movePattern[47].script[3].type = 1; 
		movePattern[47].script[3].x= (int)(hd*314); movePattern[47].script[3].y= (int)(hd*120); movePattern[47].script[3].speed = 0.25f;
		// repeat 
		movePattern[47].script[4].type = 2; 
		movePattern[47].script[4].x= 2;
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[48].num = 5; 
		movePattern[48].script = new ScriptCommand[movePattern[48].num];
		for (int i=0; i<movePattern[48].num; i++) { movePattern[48].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[48].script[0].type = 0; 
		movePattern[48].script[0].x= (int)(hd*300); movePattern[48].script[0].y= -(int)(hd*80);
		//  move
		movePattern[48].script[1].type = 1; 
		movePattern[48].script[1].x= (int)(hd*300); movePattern[48].script[1].y= (int)(hd*120); 
		// move 
		movePattern[48].script[2].type = 1; 
		movePattern[48].script[2].x= (int)(hd*220); movePattern[48].script[2].y= (int)(hd*120); movePattern[48].script[2].speed = 0.25f;
		// move 
		movePattern[48].script[3].type = 1; 
		movePattern[48].script[3].x= (int)(hd*380); movePattern[48].script[3].y= (int)(hd*120); movePattern[48].script[3].speed = 0.25f;
		// repeat 
		movePattern[48].script[4].type = 2; 
		movePattern[48].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[49].num = 5; 
		movePattern[49].script = new ScriptCommand[movePattern[49].num];
		for (int i=0; i<movePattern[49].num; i++) { movePattern[49].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[49].script[0].type = 0; 
		movePattern[49].script[0].x= (int)(hd*100); movePattern[49].script[0].y= -(int)(hd*80);
		//  move
		movePattern[49].script[1].type = 1; 
		movePattern[49].script[1].x= (int)(hd*100); movePattern[49].script[1].y= (int)(hd*240); 
		// move 
		movePattern[49].script[2].type = 1; 
		movePattern[49].script[2].x= (int)(hd*20); movePattern[49].script[2].y= (int)(hd*240); movePattern[49].script[2].speed = 0.25f;
		// move 
		movePattern[49].script[3].type = 1; 
		movePattern[49].script[3].x= (int)(hd*180); movePattern[49].script[3].y= (int)(hd*240); movePattern[49].script[3].speed = 0.25f;
		// repeat 
		movePattern[49].script[4].type = 2; 
		movePattern[49].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[50].num = 5; 
		movePattern[50].script = new ScriptCommand[movePattern[50].num];
		for (int i=0; i<movePattern[50].num; i++) { movePattern[50].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[50].script[0].type = 0; 
		movePattern[50].script[0].x= (int)(hd*167); movePattern[50].script[0].y= -(int)(hd*80);
		//  move
		movePattern[50].script[1].type = 1; 
		movePattern[50].script[1].x= (int)(hd*167); movePattern[50].script[1].y= (int)(hd*240); 
		// move 
		movePattern[50].script[2].type = 1; 
		movePattern[50].script[2].x= (int)(hd*87); movePattern[50].script[2].y= (int)(hd*240); movePattern[50].script[2].speed = 0.25f;
		// move 
		movePattern[50].script[3].type = 1; 
		movePattern[50].script[3].x= (int)(hd*247); movePattern[50].script[3].y= (int)(hd*240); movePattern[50].script[3].speed = 0.25f;
		// repeat 
		movePattern[50].script[4].type = 2; 
		movePattern[50].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[51].num = 5; 
		movePattern[51].script = new ScriptCommand[movePattern[51].num];
		for (int i=0; i<movePattern[51].num; i++) { movePattern[51].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[51].script[0].type = 0; 
		movePattern[51].script[0].x= (int)(hd*234); movePattern[51].script[0].y= -(int)(hd*80);
		//  move
		movePattern[51].script[1].type = 1; 
		movePattern[51].script[1].x= (int)(hd*234); movePattern[51].script[1].y= (int)(hd*240); 
		// move 
		movePattern[51].script[2].type = 1; 
		movePattern[51].script[2].x= (int)(hd*154); movePattern[51].script[2].y= (int)(hd*240); movePattern[51].script[2].speed = 0.25f;
		// move 
		movePattern[51].script[3].type = 1; 
		movePattern[51].script[3].x= (int)(hd*314); movePattern[51].script[3].y= (int)(hd*240); movePattern[51].script[3].speed = 0.25f;
		// repeat 
		movePattern[51].script[4].type = 2; 
		movePattern[51].script[4].x= 2;
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[52].num = 5; 
		movePattern[52].script = new ScriptCommand[movePattern[52].num];
		for (int i=0; i<movePattern[52].num; i++) { movePattern[52].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[52].script[0].type = 0; 
		movePattern[52].script[0].x= (int)(hd*300); movePattern[52].script[0].y= -(int)(hd*80);
		//  move
		movePattern[52].script[1].type = 1; 
		movePattern[52].script[1].x= (int)(hd*300); movePattern[52].script[1].y= (int)(hd*240); 
		// move 
		movePattern[52].script[2].type = 1; 
		movePattern[52].script[2].x= (int)(hd*220); movePattern[52].script[2].y= (int)(hd*240); movePattern[52].script[2].speed = 0.25f;
		// move 
		movePattern[52].script[3].type = 1; 
		movePattern[52].script[3].x= (int)(hd*380); movePattern[52].script[3].y= (int)(hd*240); movePattern[52].script[3].speed = 0.25f;
		// repeat 
		movePattern[52].script[4].type = 2; 
		movePattern[52].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[53].num = 5; 
		movePattern[53].script = new ScriptCommand[movePattern[53].num];
		for (int i=0; i<movePattern[53].num; i++) { movePattern[53].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[53].script[0].type = 0; 
		movePattern[53].script[0].x= (int)(hd*100); movePattern[53].script[0].y= -(int)(hd*80);
		//  move
		movePattern[53].script[1].type = 1; 
		movePattern[53].script[1].x= (int)(hd*100); movePattern[53].script[1].y= (int)(hd*360); 
		// move 
		movePattern[53].script[2].type = 1; 
		movePattern[53].script[2].x= (int)(hd*20); movePattern[53].script[2].y= (int)(hd*360); movePattern[53].script[2].speed = 0.25f;
		// move 
		movePattern[53].script[3].type = 1; 
		movePattern[53].script[3].x= (int)(hd*180); movePattern[53].script[3].y= (int)(hd*360); movePattern[53].script[3].speed = 0.25f;
		// repeat 
		movePattern[53].script[4].type = 2; 
		movePattern[53].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[54].num = 5; 
		movePattern[54].script = new ScriptCommand[movePattern[54].num];
		for (int i=0; i<movePattern[54].num; i++) { movePattern[54].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[54].script[0].type = 0; 
		movePattern[54].script[0].x= (int)(hd*167); movePattern[54].script[0].y= -(int)(hd*80);
		//  move
		movePattern[54].script[1].type = 1; 
		movePattern[54].script[1].x= (int)(hd*167); movePattern[54].script[1].y= (int)(hd*360); 
		// move 
		movePattern[54].script[2].type = 1; 
		movePattern[54].script[2].x= (int)(hd*87); movePattern[54].script[2].y= (int)(hd*360); movePattern[54].script[2].speed = 0.25f;
		// move 
		movePattern[54].script[3].type = 1; 
		movePattern[54].script[3].x= (int)(hd*247); movePattern[54].script[3].y= (int)(hd*360); movePattern[54].script[3].speed = 0.25f;
		// repeat 
		movePattern[54].script[4].type = 2; 
		movePattern[54].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[55].num = 5; 
		movePattern[55].script = new ScriptCommand[movePattern[55].num];
		for (int i=0; i<movePattern[55].num; i++) { movePattern[55].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[55].script[0].type = 0; 
		movePattern[55].script[0].x= (int)(hd*234); movePattern[55].script[0].y= -(int)(hd*80);
		//  move
		movePattern[55].script[1].type = 1; 
		movePattern[55].script[1].x= (int)(hd*234); movePattern[55].script[1].y= (int)(hd*360); 
		// move 
		movePattern[55].script[2].type = 1; 
		movePattern[55].script[2].x= (int)(hd*154); movePattern[55].script[2].y= (int)(hd*360); movePattern[55].script[2].speed = 0.25f;
		// move 
		movePattern[55].script[3].type = 1; 
		movePattern[55].script[3].x= (int)(hd*314); movePattern[55].script[3].y= (int)(hd*360); movePattern[55].script[3].speed = 0.25f;
		// repeat 
		movePattern[55].script[4].type = 2; 
		movePattern[55].script[4].x= 2;
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[56].num = 5; 
		movePattern[56].script = new ScriptCommand[movePattern[56].num];
		for (int i=0; i<movePattern[56].num; i++) { movePattern[56].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[56].script[0].type = 0; 
		movePattern[56].script[0].x= (int)(hd*300); movePattern[56].script[0].y= -(int)(hd*80);
		//  move
		movePattern[56].script[1].type = 1; 
		movePattern[56].script[1].x= (int)(hd*300); movePattern[56].script[1].y= (int)(hd*360); 
		// move 
		movePattern[56].script[2].type = 1; 
		movePattern[56].script[2].x= (int)(hd*220); movePattern[56].script[2].y= (int)(hd*360); movePattern[56].script[2].speed = 0.25f;
		// move 
		movePattern[56].script[3].type = 1; 
		movePattern[56].script[3].x= (int)(hd*380); movePattern[56].script[3].y= (int)(hd*360); movePattern[56].script[3].speed = 0.25f;
		// repeat 
		movePattern[56].script[4].type = 2; 
		movePattern[56].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[57].num = 15; 
		movePattern[57].script = new ScriptCommand[movePattern[57].num];
		for (int i=0; i<movePattern[57].num; i++) { movePattern[57].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[57].script[0].type = 0; 
		movePattern[57].script[0].x= (int)(hd*100); movePattern[57].script[0].y= -(int)(hd*80);
		//  move
		movePattern[57].script[1].type = 1; 
		movePattern[57].script[1].x= (int)(hd*100); movePattern[57].script[1].y= (int)(hd*280); 
		// move 
		movePattern[57].script[2].type = 1; 
		movePattern[57].script[2].x= (int)(hd*100); movePattern[57].script[2].y= (int)(hd*280);
		// move 
		movePattern[57].script[3].type = 1; 
		movePattern[57].script[3].x= (int)(hd*180); movePattern[57].script[3].y= (int)(hd*280); movePattern[57].script[3].speed = 0.5f;
		// move 
		movePattern[57].script[4].type = 1; 
		movePattern[57].script[4].x= (int)(hd*180); movePattern[57].script[4].y= (int)(hd*200); movePattern[57].script[4].speed = 0.5f;
		// move 
		movePattern[57].script[5].type = 1; 
		movePattern[57].script[5].x= (int)(hd*100); movePattern[57].script[5].y= (int)(hd*200); movePattern[57].script[5].speed = 0.5f;
		// move 
		movePattern[57].script[6].type = 1; 
		movePattern[57].script[6].x= (int)(hd*100); movePattern[57].script[6].y= (int)(hd*280); movePattern[57].script[6].speed = 0.5f;
		// move 
		movePattern[57].script[7].type = 1; 
		movePattern[57].script[7].x= (int)(hd*300); movePattern[57].script[7].y= (int)(hd*280);
		// move 
		movePattern[57].script[8].type = 1; 
		movePattern[57].script[8].x= (int)(hd*300); movePattern[57].script[8].y= (int)(hd*200);
		// move 
		movePattern[57].script[9].type = 1; 
		movePattern[57].script[9].x= (int)(hd*220); movePattern[57].script[9].y= (int)(hd*200); movePattern[57].script[9].speed = 0.5f;
		// move 
		movePattern[57].script[10].type = 1; 
		movePattern[57].script[10].x= (int)(hd*220); movePattern[57].script[10].y= (int)(hd*280); movePattern[57].script[10].speed = 0.5f;
		// move 
		movePattern[57].script[11].type = 1; 
		movePattern[57].script[11].x= (int)(hd*300); movePattern[57].script[11].y= (int)(hd*280); movePattern[57].script[11].speed = 0.5f;
		// move 
		movePattern[57].script[12].type = 1; 
		movePattern[57].script[12].x= (int)(hd*300); movePattern[57].script[12].y= (int)(hd*200); movePattern[57].script[12].speed = 0.5f;
		// move 
		movePattern[57].script[13].type = 1; 
		movePattern[57].script[13].x= (int)(hd*100); movePattern[57].script[13].y= (int)(hd*200);
		// repeat 
		movePattern[57].script[14].type = 2; 
		movePattern[57].script[14].x= 2;
		
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[58].num = 7; 
		movePattern[58].script = new ScriptCommand[movePattern[58].num];
		for (int i=0; i<movePattern[58].num; i++) { movePattern[58].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[58].script[0].type = 0; 
		movePattern[58].script[0].x= (int)(hd*80); movePattern[58].script[0].y= -(int)(hd*80);
		//  move
		movePattern[58].script[1].type = 1; 
		movePattern[58].script[1].x= (int)(hd*80); movePattern[58].script[1].y= (int)(hd*160); 
		// move 
		movePattern[58].script[2].type = 1; 
		movePattern[58].script[2].x= (int)(hd*320); movePattern[58].script[2].y= (int)(hd*160); movePattern[58].script[2].speed = 0.5f;
		// move 
		movePattern[58].script[3].type = 1; 
		movePattern[58].script[3].x= (int)(hd*80); movePattern[58].script[3].y= (int)(hd*320); movePattern[58].script[3].speed = 0.5f;
		// move 
		movePattern[58].script[4].type = 1; 
		movePattern[58].script[4].x= (int)(hd*320); movePattern[58].script[4].y= (int)(hd*320); movePattern[58].script[4].speed = 0.5f;
		// move
		movePattern[58].script[5].type = 1; 
		movePattern[58].script[5].x= (int)(hd*80); movePattern[58].script[5].y= (int)(hd*160); movePattern[58].script[5].speed = 0.5f;
		// repeat
		movePattern[58].script[6].type = 2; 
		movePattern[58].script[6].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[59].num = 7; 
		movePattern[59].script = new ScriptCommand[movePattern[59].num];
		for (int i=0; i<movePattern[59].num; i++) { movePattern[59].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[59].script[0].type = 0; 
		movePattern[59].script[0].x= (int)(hd*320); movePattern[59].script[0].y= -(int)(hd*80);
		//  move
		movePattern[59].script[1].type = 1; 
		movePattern[59].script[1].x= (int)(hd*320); movePattern[59].script[1].y= (int)(hd*160); 
		// move 
		movePattern[59].script[2].type = 1; 
		movePattern[59].script[2].x= (int)(hd*80); movePattern[59].script[2].y= (int)(hd*160); movePattern[59].script[2].speed = 0.5f;
		// move 
		movePattern[59].script[3].type = 1; 
		movePattern[59].script[3].x= (int)(hd*320); movePattern[59].script[3].y= (int)(hd*320); movePattern[59].script[3].speed = 0.5f;
		// move 
		movePattern[59].script[4].type = 1; 
		movePattern[59].script[4].x= (int)(hd*80); movePattern[59].script[4].y= (int)(hd*320); movePattern[59].script[4].speed = 0.5f;
		// move
		movePattern[59].script[5].type = 1; 
		movePattern[59].script[5].x= (int)(hd*320); movePattern[59].script[5].y= (int)(hd*160); movePattern[59].script[5].speed = 0.5f;
		// repeat
		movePattern[59].script[6].type = 2; 
		movePattern[59].script[6].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[60].num = 5; 
		movePattern[60].script = new ScriptCommand[movePattern[60].num];
		for (int i=0; i<movePattern[60].num; i++) { movePattern[60].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[60].script[0].type = 0; 
		movePattern[60].script[0].x= -(int)(hd*80); movePattern[60].script[0].y= (int)(hd*160);
		//  move
		movePattern[60].script[1].type = 1; 
		movePattern[60].script[1].x= (int)(hd*80); movePattern[60].script[1].y= (int)(hd*160); 
		// move 
		movePattern[60].script[2].type = 1; 
		movePattern[60].script[2].x= (int)(hd*320); movePattern[60].script[2].y= (int)(hd*160);  movePattern[60].script[2].speed = 0.5f; 
		// move 
		movePattern[60].script[3].type = 1; 
		movePattern[60].script[3].x= (int)(hd*80); movePattern[60].script[3].y= (int)(hd*160);  movePattern[60].script[3].speed = 0.5f;
		// repeat
		movePattern[60].script[4].type = 2; 
		movePattern[60].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[61].num = 5; 
		movePattern[61].script = new ScriptCommand[movePattern[61].num];
		for (int i=0; i<movePattern[61].num; i++) { movePattern[61].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[61].script[0].type = 0; 
		movePattern[61].script[0].x= (int)(hd*480); movePattern[61].script[0].y= (int)(hd*260);
		//  move
		movePattern[61].script[1].type = 1; 
		movePattern[61].script[1].x= (int)(hd*320); movePattern[61].script[1].y= (int)(hd*260); 
		// move 
		movePattern[61].script[2].type = 1; 
		movePattern[61].script[2].x= (int)(hd*80); movePattern[61].script[2].y= (int)(hd*260);  movePattern[61].script[2].speed = 0.5f; 
		// move 
		movePattern[61].script[3].type = 1; 
		movePattern[61].script[3].x= (int)(hd*320); movePattern[61].script[3].y= (int)(hd*260);  movePattern[61].script[3].speed = 0.5f;
		// repeat
		movePattern[61].script[4].type = 2; 
		movePattern[61].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[62].num = 5; 
		movePattern[62].script = new ScriptCommand[movePattern[62].num];
		for (int i=0; i<movePattern[62].num; i++) { movePattern[62].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[62].script[0].type = 0; 
		movePattern[62].script[0].x= -(int)(hd*80); movePattern[62].script[0].y= (int)(hd*360);
		//  move
		movePattern[62].script[1].type = 1; 
		movePattern[62].script[1].x= (int)(hd*80); movePattern[62].script[1].y= (int)(hd*360); 
		// move 
		movePattern[62].script[2].type = 1; 
		movePattern[62].script[2].x= (int)(hd*320); movePattern[62].script[2].y= (int)(hd*360);  movePattern[62].script[2].speed = 0.5f; 
		// move 
		movePattern[62].script[3].type = 1; 
		movePattern[62].script[3].x= (int)(hd*80); movePattern[62].script[3].y= (int)(hd*360);  movePattern[62].script[3].speed = 0.5f;
		// repeat
		movePattern[62].script[4].type = 2; 
		movePattern[62].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[63].num = 5; 
		movePattern[63].script = new ScriptCommand[movePattern[63].num];
		for (int i=0; i<movePattern[63].num; i++) { movePattern[63].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[63].script[0].type = 0; 
		movePattern[63].script[0].x= (int)(hd*480); movePattern[63].script[0].y= (int)(hd*460);
		//  move
		movePattern[63].script[1].type = 1; 
		movePattern[63].script[1].x= (int)(hd*320); movePattern[63].script[1].y= (int)(hd*460); 
		// move 
		movePattern[63].script[2].type = 1; 
		movePattern[63].script[2].x= (int)(hd*80); movePattern[63].script[2].y= (int)(hd*460);  movePattern[63].script[2].speed = 0.5f; 
		// move 
		movePattern[63].script[3].type = 1; 
		movePattern[63].script[3].x= (int)(hd*320); movePattern[63].script[3].y= (int)(hd*460);  movePattern[63].script[3].speed = 0.5f;
		// repeat
		movePattern[63].script[4].type = 2; 
		movePattern[63].script[4].x= 2;

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[64].num = 9; 
		movePattern[64].script = new ScriptCommand[movePattern[64].num];
		for (int i=0; i<movePattern[64].num; i++) { movePattern[64].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[64].script[0].type = 0; 
		movePattern[64].script[0].x= (int)(hd*40); movePattern[64].script[0].y= -(int)(hd*80);
		//  move
		movePattern[64].script[1].type = 1; 
		movePattern[64].script[1].x= (int)(hd*40); movePattern[64].script[1].y= (int)(hd*0); movePattern[64].script[1].speed = 5.0f;
		// move 
		movePattern[64].script[2].type = 1; 
		movePattern[64].script[2].x= (int)(hd*80); movePattern[64].script[2].y= (int)(hd*200);
		// move 
		movePattern[64].script[3].type = 1; 
		movePattern[64].script[3].x= (int)(hd*40); movePattern[64].script[3].y= (int)(hd*50); 
		// move 
		movePattern[64].script[4].type = 1; 
		movePattern[64].script[4].x= (int)(hd*40); movePattern[64].script[4].y= (int)(hd*250); movePattern[64].script[4].speed = 2.0f;
		// move
		movePattern[64].script[5].type = 1; 
		movePattern[64].script[5].x= (int)(hd*40); movePattern[64].script[5].y= (int)(hd*200);
		// move
		movePattern[64].script[6].type = 1; 
		movePattern[64].script[6].x= -(int)(hd*100); movePattern[64].script[6].y= (int)(hd*200);
		// move
		movePattern[64].script[7].type = 1; 
		movePattern[64].script[7].x= (int)(hd*180); movePattern[64].script[7].y= (int)(hd*200);
		// repeat
		movePattern[64].script[8].type = 2; movePattern[64].script[8].x= 2; 

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[65].num = 3; 
		movePattern[65].script = new ScriptCommand[movePattern[65].num];
		for (int i=0; i<movePattern[65].num; i++) { movePattern[65].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[65].script[0].type = 0; 
		movePattern[65].script[0].x= -(int)(hd*80); movePattern[65].script[0].y= (int)(hd*0);
		//  move
		movePattern[65].script[1].type = 1; 
		movePattern[65].script[1].x= (int)(hd*480); movePattern[65].script[1].y= (int)(hd*560); 
		// destroy
		movePattern[65].script[2].type = 3; 

		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[66].num = 3; 
		movePattern[66].script = new ScriptCommand[movePattern[66].num];
		for (int i=0; i<movePattern[66].num; i++) { movePattern[66].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[66].script[0].type = 0; 
		movePattern[66].script[0].x= (int)(hd*480); movePattern[66].script[0].y= (int)(hd*80);
		//  move
		movePattern[66].script[1].type = 1; 
		movePattern[66].script[1].x= -(int)(hd*100); movePattern[66].script[1].y= (int)(hd*640); 
		// destroy
		movePattern[66].script[2].type = 3; 

		// Draconis Boss
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[67].num = 10; 
		movePattern[67].script = new ScriptCommand[movePattern[67].num];
		for (int i=0; i<movePattern[67].num; i++) { movePattern[67].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[67].script[0].type = 0; 
		movePattern[67].script[0].x= (int)(hd*224); movePattern[67].script[0].y= -(int)(hd*256);
		//  move
		movePattern[67].script[1].type = 1; 
		movePattern[67].script[1].x= (int)(hd*224); movePattern[67].script[1].y= -(int)(hd*80); movePattern[67].script[1].speed = 6.0f;
		// move 
		movePattern[67].script[2].type = 1; 
		movePattern[67].script[2].x= (int)(hd*224); movePattern[67].script[2].y= (int)(hd*80); movePattern[67].script[2].speed = 3.0f;
		// move 
		movePattern[67].script[3].type = 1; 
		movePattern[67].script[3].x= -(int)(hd*60); movePattern[67].script[3].y= (int)(hd*200); 
		// move 
		movePattern[67].script[4].type = 1; 
		movePattern[67].script[4].x= (int)(hd*284); movePattern[67].script[4].y= (int)(hd*200); 
		// move
		movePattern[67].script[5].type = 1; 
		movePattern[67].script[5].x= (int)(hd*112); movePattern[67].script[5].y= (int)(hd*200);
		// move
		movePattern[67].script[6].type = 1; 
		movePattern[67].script[6].x= (int)(hd*112); movePattern[67].script[6].y= -(int)(hd*40); movePattern[67].script[6].speed = 0.5f;
		// move
		movePattern[67].script[7].type = 1; 
		movePattern[67].script[7].x= (int)(hd*200); movePattern[67].script[7].y= (int)(hd*220); movePattern[67].script[7].speed = 2.5f;
		// move
		movePattern[67].script[8].type = 1; 
		movePattern[67].script[8].x= (int)(hd*100); movePattern[67].script[8].y= (int)(hd*80); 
		// repeat
		movePattern[67].script[9].type = 2; movePattern[67].script[9].x= 3; 

		// Zent Boss
		// aktuális patternben lévő scriptek száma és létrehozásuk
		movePattern[68].num = 14; 
		movePattern[68].script = new ScriptCommand[movePattern[68].num];
		for (int i=0; i<movePattern[68].num; i++) { movePattern[68].script[i] = new ScriptCommand(); }
		// spawn
		movePattern[68].script[0].type = 0; 
		movePattern[68].script[0].x= (int)(hd*80); movePattern[68].script[0].y= -(int)(hd*354);
		//  move
		movePattern[68].script[1].type = 1; 
		movePattern[68].script[1].x= (int)(hd*80); movePattern[68].script[1].y= -(int)(hd*50); movePattern[68].script[1].speed = 3.0f;
		// move 
		movePattern[68].script[2].type = 1; 
		movePattern[68].script[2].x= -(int)(hd*50); movePattern[68].script[2].y= (int)(hd*100);
		// move 
		movePattern[68].script[3].type = 1; 
		movePattern[68].script[3].x= (int)(hd*210); movePattern[68].script[3].y= (int)(hd*100); 
		// move 
		movePattern[68].script[4].type = 1; 
		movePattern[68].script[4].x= (int)(hd*480); movePattern[68].script[4].y= (int)(hd*100); movePattern[68].script[4].speed = 10.0f;
		// move
		movePattern[68].script[5].type = 1; 
		movePattern[68].script[5].x= (int)(hd*480); movePattern[68].script[5].y= -(int)(hd*354); movePattern[68].script[5].speed = 50.0f;
		// move
		movePattern[68].script[6].type = 1; 
		movePattern[68].script[6].x= (int)(hd*80); movePattern[68].script[6].y= -(int)(hd*354); movePattern[68].script[6].speed = 50.0f;
		//  move
		movePattern[68].script[7].type = 1; 
		movePattern[68].script[7].x= (int)(hd*80); movePattern[68].script[7].y= -(int)(hd*50); movePattern[68].script[7].speed = 3.0f;
		// move 
		movePattern[68].script[8].type = 1; 
		movePattern[68].script[8].x= (int)(hd*210); movePattern[68].script[8].y= (int)(hd*100);
		// move 
		movePattern[68].script[9].type = 1; 
		movePattern[68].script[9].x= -(int)(hd*50); movePattern[68].script[9].y= (int)(hd*100); 
		// move 
		movePattern[68].script[10].type = 1; 
		movePattern[68].script[10].x= -(int)(hd*320); movePattern[68].script[10].y= (int)(hd*100); movePattern[68].script[10].speed = 10.0f;
		// move
		movePattern[68].script[11].type = 1; 
		movePattern[68].script[11].x= -(int)(hd*320); movePattern[68].script[11].y= -(int)(hd*354); movePattern[68].script[11].speed = 50.0f;
		// move
		movePattern[68].script[12].type = 1; 
		movePattern[68].script[12].x= (int)(hd*80); movePattern[68].script[12].y= -(int)(hd*354); movePattern[68].script[12].speed = 50.0f;
		// repeat
		movePattern[68].script[13].type = 2; movePattern[68].script[13].x= 1; 


	
	}
	
} 
