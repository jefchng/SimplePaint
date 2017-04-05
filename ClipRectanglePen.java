package paint;

import java.awt.event.MouseEvent;

import com.jogamp.opengl.GL2;

public class ClipRectanglePen extends Pen{
	Point p1,p2;
	float xleft, xright, yleft, yright;
    ClipRectangle clipRct;
	
    ClipRectanglePen(GL2 gl){
	super(gl);
    }
    
    public void mouseDown(MouseEvent e){
    	int xnow = e.getX();
    	int ynow = e.getY();
    	p1 = p2 = new Point(xnow,ynow);
    	clipRct = new ClipRectangle(p1,p2);
    	gl.glColor3f(1,1,1);
    	clipRct.draw(gl, GL2.GL_XOR);
    }
        
    public void mouseUp(MouseEvent e){
    	// erase the last rectangle
    	clipRct.draw(gl, GL2.GL_XOR);
    	// get the new corner point
    	int xnow = e.getX();
    	int ynow = e.getY();
    	p2 = new Point(xnow,ynow);
    	clipRct = new ClipRectangle(p1,p2);
    	gl.glColor3f(0,0,0);
    	// draw the new version permanently
    	clipRct.draw(gl, GL2.GL_COPY);
    	clipRcts.add(clipRct);
    }
        
    public void mouseDragged(MouseEvent e){
    	// erase the last rectangle
    	clipRct.draw(gl, GL2.GL_XOR);
    	// get the new corner point
    	int xnow = e.getX();
    	int ynow = e.getY();
    	p2 = new Point(xnow,ynow);
    	clipRct = new ClipRectangle(p1,p2);
    	// draw the new version
    	clipRct.draw(gl, GL2.GL_XOR);
    }
}
