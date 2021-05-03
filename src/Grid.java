import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/* 
    The idea of this program is to visualize a players
    "render distance" on an interactive board.

    Eventually, I want the user to be able to change:
    - the user's render distance
    - the size of the squares on the grid

    As of now, I am having an issue with 2 JLabels being
    displayed, when only 1 should be displayed at the
    bottom of the window. The second one appears at the
    top of the window for some reason.

    This could be an issue with the layout, but I have not 
    had any success in trying to implement an alternative
    layout.
*/

public class Grid extends JPanel implements KeyListener{

    public static int xPos;
    public static int yPos;
    public static JLabel data;
    /* xPos and yPos hold the x and y coords of the square
       on the board in which the player is located.

       The bounds are
            30 <= x <= 600
            30 <= y <= 600
        
        xPos and yPos must ALWAYS be multiples of 30 since 
        each square on the grid is 30px x 30px.
    */
      

    public void paintComponent(Graphics g) {

        //draw grid lines
        for(int x = 30; x <= 630; x+= 30) {
            g.drawLine(x,30,x,630);
        }
        for(int y = 30; y <= 630; y += 30) {
            g.drawLine(30,y,630,y);
        }

        // draw rectangles
        for(int x = 30; x <= 600; x += 30) {
            for(int y = 30; y <= 600; y += 30) {
                if(shaded(x, y)){
                    g.setColor(Color.RED);
                } 
                else g.setColor(Color.WHITE);
                g.fillRect(x+1,y+1,29,29);

                if(x == xPos && y == yPos){
                    g.setColor(Color.black);
                    g.fillOval(x+10, y+3, 10, 10);
                    g.fillRect(x+7, y+12, 17, 15); 
                }
                
            }
        }
    }

    public boolean shaded(int x, int y) {
        // this method determines wether a box needs to be shaded or not
        int xDist = Math.abs(xPos - x);
        int yDist = Math.abs(yPos - y);
        if(xDist <= 60 && yDist <= 60) return true;
        else if(xDist <= 90 && yDist <=30) return true;
        else if(xDist <= 30 && yDist <= 90) return true;
        else return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(xPos < 600)
                xPos += 30;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if(xPos > 30)
                xPos -= 30;
        }else if (e.getKeyCode() == KeyEvent.VK_UP){
            if(yPos > 30)
                yPos -= 30;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            if(yPos < 600)
                yPos += 30;
        }

        data.setText("xPos: " + xPos + "   yPos: " + yPos);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }


    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Pretty Picture");
        BorderLayout layout = new BorderLayout();
        frame.setLayout(layout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set initial player position
        xPos = 150; yPos = 150;

        Grid grid = new Grid();
        data = new JLabel();
        
        data.setText("xPos: " + xPos + "   yPos: " + yPos);


        frame.setSize(700, 700);
        frame.add(grid, BorderLayout.CENTER);
        frame.add(data, BorderLayout.SOUTH);
        frame.addKeyListener(grid);
        frame.setVisible(true);

    }

    
    
}
