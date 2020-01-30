import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel
{
    private final static int N_BLOCKS = 15;
    private final static int BLOCK_SIZE = 24;
    private final static int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final static int BORDER_SIZE = 25;
    
    private final static int PACMAN_SPRITE_DELAY = 2;
    private final static int PACMAN_SPRITE_NUM_POS = 4;
    
    protected final static int MAX_LEVEL = 6;
    private final static int MAX_GHOSTS = 12;
    private final static int PACMAN_SPEED = 6;

    private int spriteDelayCount = PACMAN_SPRITE_DELAY;
    private int spriteIncr = 1;
    private int spriteImgIdx = 0;

    private boolean inGame = false;
    private boolean dying = false;

    private int currLevel = 1;
    private int numGhosts = 5;
    private int pacsLeft, score;
    
    private ArrayList<MoveableShape> ghost;

    private Image pacman1;
    private Image pacman2up, pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;
    private Image pacman4up, pacman4down, pacman4left, pacman4right;

    private int pacman_x, pacman_y, pacman_dx, pacman_dy;
    private int req_dx, req_dy, view_dx, view_dy;

    private Timer timer;

    private short[][] screenData;
    
    private final short levelData[][] =
	    	{
	    		{ 19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22 },
	    		{ 21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
	    		{ 21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
	    		{ 21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20 },
	    		{ 17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20 },
	    		{ 17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20 },
	    		{ 25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21 },
	    		{ 1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21 },
	    		{ 1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21 },
	    		{ 1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21 },
	    		{ 1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21 },
	    		{ 1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21 },
	    		{ 1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21 },
	    		{ 1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20 },
	    		{ 9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28 }
	    	};

    public Board()
    {
        setMinimumSize (new Dimension (SCREEN_SIZE + BORDER_SIZE, SCREEN_SIZE + BORDER_SIZE));
        setPreferredSize (new Dimension (SCREEN_SIZE + BORDER_SIZE, SCREEN_SIZE + BORDER_SIZE));

        loadImages();
        initVariables();
        initBoard();
        initGame();
    }
    
    private void initBoard()
    {
        addKeyListener (new PacKeyAdapter());
        setFocusable (true);
        setBackground (Color.black);
        setDoubleBuffered (true);        
    }

    private void initVariables()
    {
        screenData = new short[N_BLOCKS][N_BLOCKS];
        
        timer = new Timer (40, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();				
			}
        });
        timer.start();
    }

    private void initGame()
    {
        pacsLeft = 3;
        score = 0;
        numGhosts = 5;
        currLevel = 1;
        
        initLevel();
    }

    private void initLevel()
    {
        for (int r = 0; r < N_BLOCKS; r++)
            for (int c = 0; c < N_BLOCKS; c++)
            	screenData[r][c] = levelData[r][c];

        continueLevel();
    }

    private void continueLevel()
    {
    	ghost = new ArrayList<MoveableShape>();
    	
    	for (int i = 0; i < numGhosts; i++)
    		ghost.add (new GhostShape (screenData, currLevel, 4, 4, BLOCK_SIZE));

        dying = false;
        pacman_x = 7 * BLOCK_SIZE;
        pacman_y = 11 * BLOCK_SIZE;
        pacman_dx = 0;
        pacman_dy = 0;
        req_dx = 0;
        req_dy = 0;
        view_dx = -1;
        view_dy = 0;
    }

    private void playGame (Graphics2D g2d)
    {
        if (dying)
            death();
        else
        {
            movePacman();
            drawPacman (g2d);
            moveGhosts (g2d);
            checkMaze ();
        }
    }
    
    private void showIntroScreen (Graphics2D g2d)
    {
        g2d.setColor (new Color (0, 32, 48));
        g2d.fillRect (50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        g2d.setColor (Color.white);
        g2d.drawRect (50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);

        String s = "Press s to start.";
        Font small = new Font ("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics (small);

        g2d.setColor (Color.white);
        g2d.setFont (small);
        g2d.drawString (s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
    }

    private void drawScore (Graphics2D g)
    {
        String s = "Score: " + score;
        Font smallFont = new Font ("Helvetica", Font.BOLD, 14);
        g.setFont (smallFont);
        g.setColor (new Color (96, 128, 255));
        g.drawString (s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        for (int i = 0; i < pacsLeft; i++)
            g.drawImage (pacman3left, i * 28 + 8, SCREEN_SIZE + 1, this);
    }

    private void checkMaze()
    {
        boolean finished = true;

        for (int r = 0; r < N_BLOCKS; r++)
        	for (int c = 0; c < N_BLOCKS; c++)
        	{
                if ((screenData[r][c] & 48) != 0)
                    finished = false;
            }
        		
        if (finished)
        {
            score += 50;

            if (numGhosts < MAX_GHOSTS)
                numGhosts++;

            if (currLevel < MAX_LEVEL)
                currLevel++;

            initLevel();
        }
    }

    private void death()
    {
        pacsLeft--;

        if (pacsLeft == 0)
            inGame = false;

        continueLevel();
    }

    private void moveGhosts (Graphics2D g2d)
    {
        for (int i = 0; i < numGhosts; i++)
        {
        	ghost.get(i).move();

            ghost.get(i).draw (g2d);
            
            if (ghost.get(i).contains (pacman_x, pacman_y))
                dying = true;
        }
    }

    private void movePacman()
    {
    	int px, py;
        short ch;

        if (req_dx == -pacman_dx && req_dy == -pacman_dy)
        {
            pacman_dx = req_dx;
            pacman_dy = req_dy;
            view_dx = pacman_dx;
            view_dy = pacman_dy;
        }

        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0)
        {
            px = pacman_x / BLOCK_SIZE;
            py = pacman_y / BLOCK_SIZE;
            ch = screenData[py][px];

            if ((ch & 16) != 0)
            {
            	screenData[py][px] = (short) (ch & 15);
                score++;
            }

            if (req_dx != 0 || req_dy != 0)
            {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0)))
                {
                    pacman_dx = req_dx;
                    pacman_dy = req_dy;
                    view_dx = pacman_dx;
                    view_dy = pacman_dy;
                }
            }

            // Check for standstill
            if ((pacman_dx == -1 && pacman_dy == 0 && (ch & 1) != 0)
                    || (pacman_dx == 1 && pacman_dy == 0 && (ch & 4) != 0)
                    || (pacman_dx == 0 && pacman_dy == -1 && (ch & 2) != 0)
                    || (pacman_dx == 0 && pacman_dy == 1 && (ch & 8) != 0))
            {
                pacman_dx = 0;
                pacman_dy = 0;
            }
        }
        pacman_x = pacman_x + PACMAN_SPEED * pacman_dx;
        pacman_y = pacman_y + PACMAN_SPEED * pacman_dy;
    }

    private void drawPacman (Graphics2D g2d)
    {
        if (view_dx == -1)
            drawPacmanLeft (g2d);
        else if (view_dx == 1)
            drawPacmanRight (g2d);
        else if (view_dy == -1)
            drawPacmanUp (g2d);
        else
            drawPacmanDown (g2d);
    }

    private void drawPacmanUp (Graphics2D g2d)
    {
        switch (spriteImgIdx) {
            case 1:
                g2d.drawImage (pacman2up, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage (pacman3up, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage (pacman4up, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage (pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanDown (Graphics2D g2d)
    {
        switch (spriteImgIdx) {
            case 1:
                g2d.drawImage (pacman2down, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage (pacman3down, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage (pacman4down, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage (pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanLeft (Graphics2D g2d)
    {
        switch (spriteImgIdx) {
            case 1:
                g2d.drawImage (pacman2left, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage (pacman3left, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage (pacman4left, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage (pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanRight (Graphics2D g2d)
    {
        switch (spriteImgIdx) {
            case 1:
                g2d.drawImage (pacman2right, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage (pacman3right, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage (pacman4right, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage (pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawMaze (Graphics2D g2d)
    {
        int r, c;
        Color dotColor = new Color (192, 192, 0);
        Color mazeColor = new Color (5, 100, 5);

        for (r = 0; r < SCREEN_SIZE; r += BLOCK_SIZE)
        {
            for (c = 0; c < SCREEN_SIZE; c += BLOCK_SIZE)
            {
            	int gr = r / BLOCK_SIZE;
            	int gc = c / BLOCK_SIZE;

                g2d.setColor (mazeColor);
                g2d.setStroke (new BasicStroke(2));

                if ((screenData[gr][gc] & 1) != 0)
                    g2d.drawLine (c, r, c, r + BLOCK_SIZE - 1);

                if ((screenData[gr][gc] & 2) != 0)
                    g2d.drawLine (c, r, c + BLOCK_SIZE - 1, r);

                if ((screenData[gr][gc] & 4) != 0)
                    g2d.drawLine (c + BLOCK_SIZE - 1, r, c + BLOCK_SIZE - 1,
                            r + BLOCK_SIZE - 1);

                if ((screenData[gr][gc] & 8) != 0)
                    g2d.drawLine (c, r + BLOCK_SIZE - 1, c + BLOCK_SIZE - 1,
                            r + BLOCK_SIZE - 1);

                if ((screenData[gr][gc] & 16) != 0)
                { 

                    g2d.setColor (dotColor);
                    g2d.fillRect (c + 11, r + 11, 2, 2);
                }
            }
        }
    }

    private void loadImages()
    {
        pacman1 = new ImageIcon("pacpix/PacMan1.gif").getImage();
        pacman2up = new ImageIcon("pacpix/PacMan2up.gif").getImage();
        pacman3up = new ImageIcon("pacpix/PacMan3up.gif").getImage();
        pacman4up = new ImageIcon("pacpix/PacMan4up.gif").getImage();
        pacman2down = new ImageIcon("pacpix/PacMan2down.gif").getImage();
        pacman3down = new ImageIcon("pacpix/PacMan3down.gif").getImage();
        pacman4down = new ImageIcon("pacpix/PacMan4down.gif").getImage();
        pacman2left = new ImageIcon("pacpix/PacMan2left.gif").getImage();
        pacman3left = new ImageIcon("pacpix/PacMan3left.gif").getImage();
        pacman4left = new ImageIcon("pacpix/PacMan4left.gif").getImage();
        pacman2right = new ImageIcon("pacpix/PacMan2right.gif").getImage();
        pacman3right = new ImageIcon("pacpix/PacMan3right.gif").getImage();
        pacman4right = new ImageIcon("pacpix/PacMan4right.gif").getImage();
    }
 
    private void doAnim()
    {
        spriteDelayCount--;

        if (spriteDelayCount <= 0) {
            spriteDelayCount = PACMAN_SPRITE_DELAY;
            spriteImgIdx = spriteImgIdx + spriteIncr;

            if (spriteImgIdx == (PACMAN_SPRITE_NUM_POS - 1) || spriteImgIdx == 0) {
                spriteIncr = -spriteIncr;
            }
        }
    }

    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor (Color.black);
        g2d.fillRect (0, 0, getWidth(), getHeight());

        drawMaze (g2d);
        drawScore (g2d);
        doAnim();

        if (inGame)
            playGame (g2d);
        else
            showIntroScreen (g2d);
    }

    protected class PacKeyAdapter extends KeyAdapter
    {
        public void keyPressed (KeyEvent e)
        {
            int key = e.getKeyCode();

            if (inGame)
            {
                if (key == KeyEvent.VK_LEFT)
                {
                    req_dx = -1;
                    req_dy = 0;
                }
                else if (key == KeyEvent.VK_RIGHT)
                {
                    req_dx = 1;
                    req_dy = 0;
                }
                else if (key == KeyEvent.VK_UP)
                {
                    req_dx = 0;
                    req_dy = -1;
                }
                else if (key == KeyEvent.VK_DOWN)
                {
                    req_dx = 0;
                    req_dy = 1;
                }
                else if (key == KeyEvent.VK_ESCAPE && timer.isRunning())
                {
                    inGame = false;
                }
                else if (key == KeyEvent.VK_PAUSE)
                {
                    if (timer.isRunning())
                        timer.stop();
                    else
                        timer.start();
                }
            }
            else if (key == 's' || key == 'S')
            {
            	inGame = true;
            	initGame();
            }
        }

        public void keyReleased (KeyEvent e)
        {
            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN)
            {
                req_dx = 0;
                req_dy = 0;
            }
        }
    }
}
