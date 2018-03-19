import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


class MyCanvas extends Canvas {  
  
    private String solution=null;  

    public void setSolution(String solution) {  
        this.solution = solution;  
    }  
  
    /** 
     * This method is called by the graphics thread. Depending on the board values it 
     * calls the drawQueen method. 
     */  
    public void paint(Graphics g) {  
        super.paint(g);  
        drawGraph(g);  
        try{
        for (int i = 0; i < 8; i++) {  
              drawQueen(solution.charAt(i)-'1',i);  
        } 
        }catch(Exception e){
           System.out.println("Processing");
        }
    }  
  
    /** 
     * This method draws the cells on the screen. 
     */  
    public void drawGraph(Graphics g) {  
        g.setColor(Color.WHITE);  
        int height = getHeight();  
        int width = getWidth();  
        int side = height / 8;  
        int spacing = 0;  
        for (int i = 0; i < 7; i++) {  
            spacing += side;  
            g.drawLine(0, spacing, width, spacing);  
        }  
        side = width / 8;  
        spacing = 0;  
        for (int i = 0; i < 7; i++) {  
            spacing += side;  
            g.drawLine(spacing, 0, spacing, height);  
        }  
        colorWhite(g);  
    }  
  
    /** 
     * Color alternate cells with white color so as to give the appearance of a chess 
     * board. Coloring black is not required as the background itself is black. 
     * 
     * @param g 
     */  
    private void colorWhite(Graphics g) {  
        g.setColor(Color.WHITE);  
        int side = getWidth() / 8;  
        int spacing;  
        int alternate = 1;  
        for (int i = 0; i < 8; i++) {  
            spacing = 0;  
            for (int j = 0; j < 4; j++) {  
                if (alternate == 1) {  
                    g.fillRect(spacing, i * getHeight() / 8, side, getHeight() / 8);  
                }  
                if (alternate == 0) {  
                    g.fillRect(spacing + side, i * getHeight() / 8, side, getHeight() / 8);  
                }  
                spacing = spacing + 2 * side;  
            }  
            alternate = (++alternate) % 2;  
        }  
    }  
  
    /** 
     * The parameters denotes the cell in which the queen will be placed. Subsequently 
     * the queen is drawn in the cell. The queen is represented as a circle. 
     * 
     * @param row 
     * @param column 
     */  
    public void drawQueen(int row, int column) {  
        int side = (int)(getHeight() / 8 + getWidth() / 8) / 2;  
        int center_x = (int)column * getWidth() / 8 + side / 4;  
        int center_y = (int)row * getHeight() / 8 + side / 4;  
        Graphics g = getGraphics();  
        g.setColor(Color.RED);  
        g.fillOval(center_x, center_y, side / 2, side / 2);  
    }  
}  