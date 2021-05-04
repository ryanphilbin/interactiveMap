import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/* 
    The idea of this program is to visualize a players
    "render distance" on an interactive board.

    Eventually, I want the user to be able to change:
    - the user's render distance
    - the size of the squares on the grid

    CURRENT ISSUES:

    Some boxSize's work better than others. Since it will
    be dependent on user choice, only offer boxSize options 
    in the toolbox that work well with program.

    Next time, build start screen that gives allows user
    to manipulate boxSize, renderDist, and gridSize. Then, 
    on button click, paint the grid and allow user
    interaction.
*/

public class Grid extends JPanel implements KeyListener{

    // user coordinates
    public static int xPos;
    public static int yPos;

    // grid info
    public static int boxSize = 30; // creates boxes w/ size (30px x 30px)
    public static int renderDist = 4; // creates radius of (renderDist) squares
    public static int gridSize = 14; // creates grid with (gridSize x gridSize) squares
    public static final int bound = boxSize * gridSize; // max x or y value for squares to be drawn

    // uhh
    public static JLabel coords;
    public static JPanel panel;

    /* xPos and yPos hold the x and y coords of the square
       on the board in which the player is located.

       The bounds are
            boxSize <= x <= bound
            boxSize <= y <= bound
        
        xPos and yPos must ALWAYS be multiples of (boxSize) since 
        each square on the grid is (boxSize)px x (boxSize)px.
    */
      

    public void paintComponent(Graphics g) {

        //draw grid lines
        for(int x = boxSize; x <= (bound + boxSize); x+= boxSize) {
            g.drawLine(x, boxSize, x, (bound + boxSize));
        }
        for(int y = boxSize; y <= (bound + boxSize); y += boxSize) {
            g.drawLine(boxSize, y, (bound + boxSize), y);
        }

        // draw rectangles
        for(int x = boxSize; x <= bound; x += boxSize) {
            for(int y = boxSize; y <= bound; y += boxSize) {

                if(shaded(x, y)){
                    g.setColor(Color.RED);
                } 
                else g.setColor(Color.WHITE);
                    
                g.fillRect(x+1, y+1, boxSize-2, boxSize-2);

                //if(x == xPos && y == yPos){
                    // draw player marker on top of red square
                    g.setColor(Color.BLACK);
                    //g.fillOval(x+(boxSize/3), y+3, (boxSize/3), (boxSize/3));
                    //g.fillRect(x+(boxSize/4), y+12, (boxSize/2)+1, (boxSize/2)); 
                    g.fillOval(xPos+(boxSize/4), yPos+(boxSize/4), boxSize/2, boxSize/2);
                //}
                
            }
        }
    }

    public boolean shaded(int x, int y) {
        // this method determines wether a box needs to be shaded or not
        int xDist = Math.abs(xPos - x);
        int yDist = Math.abs(yPos - y);

        // going to use pythagorean to determine whether shading is needed
        xDist = xDist / boxSize; // conv xDist from 'px' to 'squares'
        xDist = xDist * xDist;   // square
        yDist = yDist / boxSize;
        yDist = yDist * yDist;
        double total = (double)xDist + (double)yDist; // calculate a^2 + b^2
        total = Math.sqrt(total); // square root

        if(total <= renderDist) return true;
        else return false;

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(xPos < bound)
                xPos += boxSize;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if(xPos > boxSize)
                xPos -= boxSize;
        }else if (e.getKeyCode() == KeyEvent.VK_UP){
            if(yPos > boxSize)
                yPos -= boxSize;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            if(yPos < bound)
                yPos += boxSize;
        }

        coords.setText("xPos: " + xPos + "   yPos: " + yPos);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    public static void createWindow(JFrame frame) {

        // create panel to hold everything
        JPanel all = new JPanel();
        GridLayout layout = new GridLayout(1, 1, 15, 20);
        all.setLayout(layout);
        
        /*

        // create top panel to hold tools
        JPanel toolbox = new JPanel();
        toolbox.setLayout(new FlowLayout());

        JLabel rDistLabel = new JLabel("Render Distance");
        toolbox.add(rDistLabel);
        JSlider distSlider = new JSlider(JSlider.HORIZONTAL, 1, 8, 3);
        distSlider.setMajorTickSpacing(1);
        distSlider.setPaintTicks(true);
        distSlider.setPaintLabels(true);
        toolbox.add(distSlider);
        
        JLabel boxSizeLabel = new JLabel("Box Size (px)");
        toolbox.add(boxSizeLabel);
        JSlider boxSlider = new JSlider(JSlider.HORIZONTAL, 10, 60, 30);
        boxSlider.setMajorTickSpacing(10);
        boxSlider.setMinorTickSpacing(5);
        boxSlider.setPaintTicks(true);
        boxSlider.setPaintLabels(true);
        toolbox.add(boxSlider);

        */

        // create grid for bottom panel 
        Grid grid = new Grid();
        coords = new JLabel();
        coords.setText("xPos: " + xPos + "   yPos: " + yPos);
        coords.setPreferredSize(new Dimension(20, 50));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        coords.setBorder(border);

        //all.add(coords);
        all.add(grid);

        frame.setBackground(Color.darkGray);
        int limit = bound + (boxSize*2);
        frame.setSize(limit, limit+boxSize);
        frame.addKeyListener(grid);
        frame.setContentPane(all);
        frame.setVisible(true);
    }


    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Map Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        // set initial player position
        xPos = 150; yPos = 150;
        
        createWindow(frame);
        


        

    }

    
    
}
