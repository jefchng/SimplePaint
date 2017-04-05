package paint;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

public class RectanglePen extends Pen {
	Point p1,p2;
    Rectangle rct;
    List<Point> pts = new ArrayList<Point>();
    List<Line> clippedLines = new ArrayList<Line>();

    RectanglePen(GL2 gl){
	super(gl);
    }
    
    public void mouseDown(MouseEvent e){
    	int xnow = e.getX();
    	int ynow = e.getY();
    	p1 = p2 = new Point(xnow,ynow);
    	rct = new Rectangle(p1,p2);
    	gl.glColor3f(1-r,1-g,1-b);
    	
    	if (clipRcts.isEmpty()) {
    		rct.draw(gl, GL2.GL_XOR);
    	}
    	
    	else {
    		for (ClipRectangle clipRct : clipRcts) {
    			for (int i = 0; i < 4; i++) {
    				if (clipRct.liangbarsky(rct.lines.get(i)) != null) {
    					clippedLines.add(clipRct.liangbarsky(rct.lines.get(i)));
    				}
    				
    			}
    		}
    		for (Line clipped : clippedLines) {
    			clipped.draw(gl, GL2.GL_COPY);
    		}
    	}
    	
    }
        
    public void mouseUp(MouseEvent e){
    	// erase the last rectangle
    	if (clippedLines.isEmpty() && clipRcts.isEmpty()){
	    	rct.draw(gl, GL2.GL_XOR);
    	}
    	else {
    		for (Line clipped : clippedLines) {
    			clipped.draw(gl, GL2.GL_XOR);			
    		}
    	}
    	
    	// get the new corner point
    	int xnow = e.getX();
    	int ynow = e.getY();
    	p2 = new Point(xnow,ynow);
    	rct = new Rectangle(p1,p2);
    	gl.glColor3f(r,g,b);
    	
    	
    	// draw the new version
    	if (clipRcts.isEmpty()) {
	    	rct.draw(gl, GL2.GL_COPY);
    	}
    	
    	else {
    		for (ClipRectangle clipRct : clipRcts) {
    			for (int i = 0; i < 4; i++) {
    				if (clipRct.liangbarsky(rct.lines.get(i)) != null) {
    					clippedLines.add(clipRct.liangbarsky(rct.lines.get(i)));
    				}
    			}
    		}
			for (Line clipped : clippedLines) {
    			clipped.draw(gl, GL2.GL_COPY);
    		}
    	}
    	clippedLines.clear();
    }
        
    public void mouseDragged(MouseEvent e){
    	// erase the last rectangle
    	if (clippedLines.isEmpty() && clipRcts.isEmpty()){
	    	rct.draw(gl, GL2.GL_XOR);
    	}
    	else {
    		for (Line clipped : clippedLines) {
    			clipped.draw(gl, GL2.GL_XOR);			
    		}
    	}
    	
    	clippedLines.clear();
    	
    	// get the new corner point
    	int xnow = e.getX();
    	int ynow = e.getY();
    	p2 = new Point(xnow,ynow);
    	rct = new Rectangle(p1,p2);
    	
    	// draw the new version
    	if (clipRcts.isEmpty()) {
	    	rct.draw(gl, GL2.GL_XOR);
    	}
    	
    	else {
    		for (ClipRectangle clipRct : clipRcts) {
    			for (int i = 0; i < 4; i++) {
    				if (clipRct.liangbarsky(rct.lines.get(i)) != null) {
    					clippedLines.add(clipRct.liangbarsky(rct.lines.get(i)));
    				}
    			}
    		}
    		
			for (Line clipped : clippedLines) {
    			clipped.draw(gl, GL2.GL_XOR);
    		}
    	}
    }
}
