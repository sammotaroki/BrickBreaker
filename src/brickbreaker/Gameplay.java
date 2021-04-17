/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author xamcr
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    
    public int totalBricks =81;
    
    private Timer timer;
    private int delay = 1;
    
    private int playerX= 310 ;
    
    private int ballPosX = 110;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    
    
       
    private MapGenerator map;
    
    public Gameplay(){
        map = new MapGenerator(9,9);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }    
    
    public void paint(Graphics g){
        //background
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(1, 1, 692, 592);
        
        //drawing map
        map.draw((Graphics2D)g);
        
        //borders
        g.setColor(Color.black);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        
        //scores
        g.setColor(Color.black);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score, 590, 30);
        
        // the paddle
        g.setColor(Color.DARK_GRAY);
        g.fillRect(playerX, 550, 100, 8);
        
        //the ball
        g.setColor(Color.blue);
        g.fillOval(ballPosX, ballPosY, 20, 20); 
        
       
        
        if(totalBricks <= 0){
            
                play = false;
                ballPosX = 0;
                ballPosY = 0;
                g.setColor(Color.BLACK);
                g.setFont(new Font("sans-serif", Font.BOLD, 25));
                g.drawString("You won : "+score, 260, 300);
                
                g.setFont(new Font("sans-serif", Font.BOLD, 20));
                g.drawString("Press ENTER to restart " , 230, 350);
        
        }
        
        if(ballPosY >570){
        play= false;
        ballXdir = 0;
        ballYdir = 0;
        g.setColor(Color.BLACK);
        g.setFont(new Font("sans-serif", Font.BOLD, 30));
        g.drawString("Game Over" , 240, 300);
        
        g.setFont(new Font("sans-serif", Font.BOLD, 35));
        g.drawString("Score "+score , 255, 330);
        
        g.setFont(new Font("sans-serif", Font.BOLD, 20));
        g.drawString("Press ENTER to restart " , 220, 360);
        
        }
        
        
        g.dispose();
    
    
    }
    

    @Override
    public void keyTyped(KeyEvent ke) {}
    @Override
    public void keyReleased(KeyEvent ke) {}
    @Override
    public void actionPerformed(ActionEvent ae) {
        timer.start();
        if(play){
            if(new Rectangle(ballPosX,ballPosY,20 ,20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir = -ballYdir;
            }
            A: for(int i = 0; i <map.map.length; i++){
                for (int j= 0; j<map.map[0].length; j++){
                    if(map.map[i][j] > 0){
                        int brickX = j*map.brickWidth +80;
                        int brickY = i*map.brickHeight +50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        
                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalBricks --;
                            score += 2;
                        
                        if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width){
                            ballXdir =- ballXdir;
                        }else{
                            ballYdir =- ballYdir;
                        }
                        
                        break A;
                        
                        }
                    }
                }
                
            }
            
            ballPosX += ballXdir;
            ballPosY += ballYdir;
            if(ballPosX < 0) {
               ballXdir =- ballXdir;
            }
            if(ballPosY < 0) {
               ballYdir =- ballYdir;
            }
            if(ballPosX > 670) {
               ballXdir =- ballXdir;
            }
        }
                
        repaint();
        
    }
    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX = 600;
            }else{
                moveRight();
            }
        }
        if(ke.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 10;
            }else{
                moveLeft();
            } 
        }
        if(ke.getKeyCode()== KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score =0;
                totalBricks= 81;
                map = new MapGenerator (9,9);
                
                repaint();
            }
        }
    }
    
    public void moveRight(){
    play = true;
    playerX+=20;
    }
    public void moveLeft(){
    play = true;
    playerX-=20;
    }
}
