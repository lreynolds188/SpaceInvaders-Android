package com.example.luke.spaceinvaders;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 *    author: Luke Aaron Reynolds
 *    email: lreynolds188@gmail.com
 *    website: http://lukereynolds.net/
 */

public class GameThread extends java.lang.Thread
{
    private int fps = 30;
    private double averageFps;
    private GamePanel gamePanel;
    private SurfaceHolder surfaceHolder;
    public boolean running;
    public float timePlayed = 0;
    public float difference = 1;
    public long waitTime;
    public static Canvas canvas;

    public GameThread(SurfaceHolder sHolder, GamePanel gPanel)
    {
        super();
        gamePanel = gPanel;
        surfaceHolder = sHolder;
    }

    @Override
    public void run()
    {
        long startTime;
        long timeMilli;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/fps;

        while (running)
        {
            startTime = System.nanoTime();

            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            }catch(Exception e){

            }
            finally{
                if(canvas!=null)
                {
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}

                }
            }

            timeMilli = (System.nanoTime()-startTime)/1000000;
            waitTime = targetTime - timeMilli;

            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()-startTime;

            frameCount++;

            timePlayed += difference / 3;

            if(frameCount == fps){
                averageFps = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }

    public void setRunning(boolean blnRunning){
        running = blnRunning;
    }
}
