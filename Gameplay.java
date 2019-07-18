package brickBracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	
	private boolean play = false;
	
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer timer;
	
	private int delay = 8;
	
	private int playerX = 310;
	
	private int ballPosX = 120;
	private int ballPosY = 350;
	private int ballDirX = -1;
	private int ballDirY = -2;
	
	private mapGenerator map;
	
	public Gameplay() {
		
		map = new mapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer (delay, this);
		timer.start();
		
	}
	
	public void paint(Graphics g) {
		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		// drawing map
		map.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
		
		// the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//the ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		if(totalBricks <= 0) {
			play = false;
			ballDirX = 0;
			ballDirY = 0;
			
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won", 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 15));
			g.drawString("Press Enter to Restart", 250, 300);
		}
		
		if(ballPosY > 570) {
			play = false;
			ballDirX = 0;
			ballDirY = 0;
			
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game  Over, Scores:"+ score, 190, 300);
			
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 250, 380);
		}
		
		g.dispose();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		timer.start();
		
		if(play) {
			
			if(new Rectangle(ballPosX, ballPosY, 20,20).intersects(new Rectangle(playerX, 550, 100,8))) {
				
				ballDirY = -ballDirY;
				
			}
			
			A : for(int i=0; i<map.map.length; i++) {
				for(int j=0; j<map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j*map.brickWidth + 80;
						int brickY = i*map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
								ballDirX = -ballDirX;
							}
							else {
								ballDirY = -ballDirY;
							}
							
							break A;
						}
					}
				}
			}
			
			ballPosX += ballDirX;
			ballPosY += ballDirY;
			
			if(ballPosX < 0) {
				ballDirX = -ballDirX;
			}
			
			if(ballPosY < 0) {
				ballDirY = -ballDirY;
			}
			
			if(ballPosX > 670) {
				ballDirX = -ballDirX;
			}
		}
		
		repaint();
		
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			
			if(playerX >= 600) {
				playerX = 600;
			}
			else 
			{
				moveRight();
			}
			
		}
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
        	
        	if(playerX < 10) {
				playerX = 10;
			}
			else 
			{
				moveLeft();
			}
        	
		}
		
        if(e.getKeyCode()== KeyEvent.VK_ENTER) {
        	if(!play) {
        		play = true;
        		ballPosX = 120;
        		ballPosY = 350;
        		ballDirX = -1;
        		ballDirY = -2;
        		playerX = 310;
        		score = 0;
        		totalBricks = 21;
        		map = new mapGenerator(3,7);
        		
        		repaint();
        	}
        }
        
	}
	
	public void moveRight() {
		
		play = true;
		playerX+=20;
		
	}
	
	public void moveLeft() {
		
		play = true;
		playerX-=20;
		
	}

	

	

}
