import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/* 
    The idea of this program is to visualize a players
    "render distance" on an interactive board.

    I accomplished my original goal, building an 
    interactive grid that displays render distance.

    All aspects of the grid are configurable and scaled
    properly.

    Would love to add more features in the future, making
    the grid more interesting.

    CURRENT ISSUES
    - .json is needed to run inside VS Code on my machine. 
      I would like to make it so the program can be run on any machine.
*/

public class Grid extends JPanel implements KeyListener{

    // user coordinates
    public static int xPos;
    public static int yPos;

    // grid info
    public static int boxSize = 30; // creates boxes w/ size (30px x 30px)
    public static int renderDist = 4; // creates radius of (4) squares
    public static int gridSize = 14; // creates grid with (14 x 14) squares
    public static int bound; // max x or y value for squares to be drawn

    // uhh
    public static JLabel coords; 
    public static JButton button; // Build Grid button
    public static JSlider distSlider;
    public static JSlider boxSlider;
    public static JSlider gridSlider;

    /* xPos and yPos hold the x and y coords of the square
       on the board in which the player is located.

       The bounds are
            boxSize <= x <= bound
            boxSize <= y <= bound
        
        xPos and yPos must ALWAYS be multiples of (boxSize) since 
        each square on the grid is (boxSize)px x (boxSize)px.
    */
      

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        
        //draw grid lines
        for(int x = 0; x <= bound; x+= boxSize) {
            g.drawLine(x+5, 0+5, x+5, bound+5);
        }
        for(int y = 0; y <= bound; y += boxSize) {
            g.drawLine(0+5, y+5, bound+5, y+5);
        }

        // draw rectangles
        for(int x = 0; x <= (bound - boxSize); x += boxSize) {
            for(int y = 0; y <= (bound - boxSize); y += boxSize) {

                if(shaded(x, y)){
                    g.setColor(Color.RED);
                } 
                else g.setColor(Color.WHITE);
                    
                g.fillRect(x+1+5, y+1+5, boxSize-2, boxSize-2);

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
        int xDist = Math.abs( (xPos-5) - x);
        int yDist = Math.abs( (yPos-5) - y);

        // going to use pythagorean to determine whether shading is needed
        xDist = xDist / boxSize; // conv xDist from 'px' to 'squares'
        xDist = xDist * xDist;   // square
        yDist = yDist / boxSize;
        yDist = yDist * yDist;
        double total = (double)xDist + (double)yDist; // calculate a^2 + b^2
        total = Math.sqrt(total); // square root

        if(total <= (double)renderDist) return true;
        else return false;

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(xPos < (bound-boxSize))
                xPos += boxSize;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if(xPos > boxSize)
                xPos -= boxSize;
        }else if (e.getKeyCode() == KeyEvent.VK_UP){
            if(yPos > boxSize)
                yPos -= boxSize;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            if(yPos < (bound-boxSize))
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

    public static void displayToolbox(JFrame frame) {

        // create top panel to hold tools
        JPanel toolbox = new JPanel();
        BoxLayout griddy = new BoxLayout(toolbox, BoxLayout.Y_AXIS);
        toolbox.setLayout(griddy);
        toolbox.setBackground(Color.LIGHT_GRAY);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        
        JTextArea area = new JTextArea();
        area.setLineWrap(true);
        String intro = "\t     Hello!\n" 
        + "     Customize the settings below and "
        + "click\n \"Create Grid\" to build your "
        + "interactive graph";
        area.setText(intro);
        area.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        area.setEditable(false);
        area.setBackground(Color.LIGHT_GRAY);
        area.setBorder(border);
        toolbox.add(area);
        
        
        // render distance slider and label
        JLabel rDistLabel = new JLabel("User Render Distance in squares");
        rDistLabel.setHorizontalAlignment(SwingConstants.CENTER);
        toolbox.add(rDistLabel);
        distSlider = new JSlider(JSlider.HORIZONTAL, 1, 8, 3);
        distSlider.setMajorTickSpacing(1);
        distSlider.setPaintTicks(true);
        distSlider.setPaintLabels(true);
        JPanel slider1 = new JPanel();
        slider1.setLayout(new FlowLayout());
        slider1.add(distSlider);
        toolbox.add(slider1);
        
        // box size slider and label
        JLabel boxSizeLabel = new JLabel("Edge Length of 1 Grid Square(px)");
        boxSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        toolbox.add(boxSizeLabel);
        boxSlider = new JSlider(JSlider.HORIZONTAL, 10, 40, 30);
        boxSlider.setMajorTickSpacing(10);
        boxSlider.setMinorTickSpacing(5);
        boxSlider.setSnapToTicks(true);
        boxSlider.setPaintTicks(true);
        boxSlider.setPaintLabels(true);
        JPanel slider2 = new JPanel();
        slider2.setLayout(new FlowLayout());
        slider2.add(boxSlider);
        toolbox.add(slider2);

        // gridSize slider and label
        JLabel gridSizeLabel = new JLabel("Grid Size (n x n) squares");
        gridSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        toolbox.add(gridSizeLabel);
        gridSlider = new JSlider(JSlider.HORIZONTAL, 5, 20, 14);
        gridSlider.setMajorTickSpacing(5);
        gridSlider.setMinorTickSpacing(1);
        gridSlider.setSnapToTicks(true);
        gridSlider.setPaintTicks(true);
        gridSlider.setPaintLabels(true);
        JPanel slider3 = new JPanel();
        slider3.setLayout(new FlowLayout());
        slider3.add(gridSlider);
        toolbox.add(slider3);

        // Build Grid Button
        button = new JButton();
        button.setText("Create Grid");
        button.setSize(75, 50);
        JPanel button1 = new JPanel();
        button1.setLayout(new FlowLayout());
        button1.add(button);
        toolbox.add(button1);

        // set frame for display
        frame.setSize( 300 , 350 );
        frame.setResizable(false);
        frame.setTitle("Build a Grid");
        frame.setContentPane(toolbox);
        frame.setVisible(true);

    }

    public static void createWindow(JFrame frame) {

        // set initial player position to a random spot on the board
        Random rand = new Random();
        xPos = rand.nextInt(gridSize); // 0 to gridSize-1
        xPos = (xPos * boxSize) + 5; // convert grid coord to px
        yPos = rand.nextInt(gridSize);
        yPos = (yPos * boxSize) + 5;
        
        // create panel to hold everything
        JPanel all = new JPanel();
        GridLayout layout = new GridLayout(1, 2, 15, 20);
        all.setLayout(layout);
        all.setBackground(Color.DARK_GRAY);

        // mark pixel coordinates 
        coords = new JLabel();
        coords.setText("xPos: " + xPos + "   yPos: " + yPos);
        coords.setPreferredSize(new Dimension(20, 50));

        // create grid for bottom panel 
        Grid grid = new Grid();
        int limit = boxSize * gridSize;
        limit += (boxSize+30);
        grid.setMinimumSize(new Dimension(limit, limit));
        grid.setBackground(Color.DARK_GRAY);

        // add panels to the parent panel
        all.add(grid);
        all.setSize(grid.getMinimumSize());

        // add parent panel to frame and display
        frame.setResizable(true);
        frame.setSize(all.getSize());
        frame.addKeyListener(grid); // create key listener for grid
        frame.requestFocus();
        frame.setTitle("Grid Display");
        frame.setContentPane(all);
        frame.setVisible(true);
    }


    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        frame.setBackground(Color.darkGray);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        

        // display toolbox panel, on 'Run' button click
        displayToolbox(frame);
        button.addActionListener(e -> {
            boxSize = boxSlider.getValue();
            renderDist = distSlider.getValue();
            gridSize = gridSlider.getValue(); // for when I build this
            bound = boxSize * gridSize;
            createWindow(frame);
        });//end lambda function

        // I may have it so button on 1st screen runs this
        // function, which changes setContentPane()
        // createWindow(frame);
        
        
    }

    
    
}
