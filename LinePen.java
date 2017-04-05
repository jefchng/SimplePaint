package paint;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

public class LinePen extends Pen {
	Point p1,p2;
    Line l;
    List<Line> lines = new ArrayList<Line>();
    List<Line> clippedLines = new ArrayList<Line>();
    LinePen(GL2 gl){
	super(gl);
    }
    
	
    
    public void mouseDown(MouseEvent e){
    	int xnow = e.getX();
    	int ynow = e.getY();
    	p1 = p2 = new Point(xnow,ynow);
    	l = new Line(p1,p2);
    	lines.add(l);
    	gl.glColor3f(1-r,1-g,1-b);
    	l.draw(gl, GL2.GL_XOR);
    }
        
    public void mouseUp(MouseEvent e){
    	if (clipRcts.isEmpty()) {
	    	// erase the last line
	    	l.draw(gl, GL2.GL_XOR);
	    }
    	
		for (Line clipped : clippedLines) {
			clipped.draw(gl, GL2.GL_XOR);
		}
    	
    	// get the new corner point
    	int xnow = e.getX();
    	int ynow = e.getY();
    	p2 = new Point(xnow,ynow);
    	l = new Line(p1,p2);
    	gl.glColor3f(r,g,b);
    	
    	// Liang Barsky
    	
    	if (!clipRcts.isEmpty()){
    		for (ClipRectangle clipRct : clipRcts) {	
    			if (clipRct.liangbarsky(l) != null) {
    				clippedLines.add(clipRct.liangbarsky(l));
    			}
    		}
    		for (Line clipped : clippedLines) {
    			clipped.draw(gl, GL2.GL_COPY);
    		}
    	}
    	else {
    		// draw the new version(s) permanently
        	
        	l.draw(gl, GL2.GL_COPY);
    	}
    	
    	clippedLines.clear();
    }
        
    public void mouseDragged(MouseEvent e){
    	if (clipRcts.isEmpty()) {
	    	// erase the last line
	    	l.draw(gl, GL2.GL_XOR);
	    }
    	
		for (Line clipped : clippedLines) {
			clipped.draw(gl, GL2.GL_XOR);
		}
		
    	clippedLines.clear();
    	
    	// get the new corner point
    	int xnow = e.getX();
    	int ynow = e.getY();
    	p2 = new Point(xnow,ynow);
    	l = new Line(p1,p2);
    	
    	
    	for (ClipRectangle clipRct : clipRcts) {
			if (clipRct.liangbarsky(l) != null) {
				clippedLines.add(clipRct.liangbarsky(l));
			}
		}
    	for (Line clipped : clippedLines) {
			clipped.draw(gl, GL2.GL_XOR);
		}
    	
    	if (clipRcts.isEmpty()) {
	    	// draw the new version
	    	l.draw(gl, GL2.GL_XOR);
    	}
    }

}
