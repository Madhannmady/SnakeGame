import javax.swing.*;
import java.awt.event.*;
import java.security.Key;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
	static final int SCREEN_WIDTH=1300;
	static final int SCREEN_HEIGHT=750;
	static final int UNIT_SIZE=50;
	static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY=100;
	final int x[]=new int[UNIT_SIZE];
	final int y[]=new int[UNIT_SIZE];
	int bodyParts=6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction='R';
	boolean running=false;
	Timer timer;
	Random random;
	
	
	GamePanel(){
		random=new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
		
	}
	public void startGame() {
		newApple();
		running=true;
		timer=new Timer(DELAY,this);
		timer.start();
		
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	public void draw(Graphics g) {
		
		if(running) {
		/*for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
		    g.drawLine(0, i*UNIT_SIZE, SCREEN_HEIGHT, i*UNIT_SIZE);
		
		}*/
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
	    //creating the body of the snake
		for(int i=0;i<bodyParts;i++) {
		//if: for if it is the head of the snake
			if(i==0) {
				      g.setColor(Color.green);
				      g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				      }
		//else: for if it is the body part of the snake
			else {
				//Creating  a random RGB color type
				g.setColor(new Color(45,180,0));
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			    }
		 }
		   g.setColor(Color.red);
    	   g.setFont(new Font("Bold",Font.BOLD,40));
    	   FontMetrics metrics=g.getFontMetrics(g.getFont());
    	   g.drawString("SCORE:"+applesEaten, (SCREEN_WIDTH- metrics.stringWidth("SCORE:"+applesEaten))/2,g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
	}
	public void newApple(){
		appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
		
		}
	public void move() {
		for(int i=bodyParts;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
			}
		switch(direction){
		case 'U':
			    y[0]=y[0]-UNIT_SIZE;
			    break;
		case 'D':
			     y[0]=y[0]+UNIT_SIZE;
			     break;
		case 'L':
			     x[0]=x[0]-UNIT_SIZE;
			       break;
		case 'R':
			     x[0]=x[0]+UNIT_SIZE;
			      break;
		 }
	}
	public void checkApple() {
		if(x[0]==appleX&&y[0]==appleY) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
    public void checkCollisions() {
    	for(int i=bodyParts;i>0;i--) {
    		//if:snake's head and body collides
    		if(x[0]==x[i]&&y[0]==y[i]) {
    			running=false;
    		}
    	}
    	//checks if head touches the left  border
    	if(x[0]<0) {
    		running=false;
    	}
    	//checks if head touches the right border
    	if(x[0]>SCREEN_WIDTH){
    		running=false;
    	}
    	//checks if head touches the top  border
    	if(y[0]<0) {
    		running=false;
    	}
    	//checks if head touches the bottom border
    	if(y[0]>SCREEN_HEIGHT){
    		running=false;
    	}
    	if(!running) {
    		timer.stop();
    	}
    }
    public void gameOver(Graphics g) {
    	//Score Display
    	g.setColor(Color.white);
 	    g.setFont(new Font("Bold",Font.BOLD,40));
 	    FontMetrics metrics1=g.getFontMetrics(g.getFont());
 	    g.drawString("SCORE:"+applesEaten, (SCREEN_WIDTH- metrics1.stringWidth("SCORE:"+applesEaten))/2,g.getFont().getSize());
    	//for Game over indication
    	g.setColor(Color.red);
    	g.setFont(new Font("Ink Free",Font.BOLD,75));
    	FontMetrics metrics2=g.getFontMetrics(g.getFont());
    	g.drawString("Game Over", (SCREEN_WIDTH- metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    	
    	g.setColor(Color.WHITE);
    	g.setFont(new Font("Italic",Font.BOLD,50));
    	FontMetrics metrics3=g.getFontMetrics(g.getFont());
    	g.drawString("Presented by: 2021PITCS208", (SCREEN_WIDTH- metrics3.stringWidth("Presented by: 2021PITCS208"))/2,500);
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
	  if(running){
		  move();
		  checkApple();
		  checkCollisions();
		 }
	  repaint();
	   
	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction!='R') {
					direction='L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction!='L') {
					direction='R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction!='D') {
					direction='U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction!='U') {
					direction='D';
				}
				break;
				
			}
			
		}
	}

}
