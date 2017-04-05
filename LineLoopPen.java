package paint;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

public class LineLoopPen extends Pen {
	Point p, p1,p2;
    Line l;
    List<Point> pts = new ArrayList<Point>();
    List<Line> lines = new ArrayList<Line>();
    
    LineLoopPen(GL2 gl){
	super(gl);
    }
        
    public void mouseUp(MouseEvent e){
    	int xnow = e.getX();
    	int ynow = e.getY();
    	gl.glColor3f(r,g,b);
    	
    	if (pts.size() == 0) {
    		p = p1 = p2 = new Point(xnow,ynow);
    	}
    	
    	else {
    		int index = pts.size() - 1;
    		p1 = pts.get(index);
        	p2 = new Point(xnow,ynow);
    	}

    	if (pts.size() != 0 && p1.x == p2.x && p1.y == p2.y) {
    		l = new Line(p2,p);
    		
    		if (!clipRcts.isEmpty()) {
    			for (ClipRectangle clipRct : clipRcts) {
    				if (clipRct.liangbarsky(l) != null) {
    					lines.add(clipRct.liangbarsky(l));
    				}
    			}
    			
    			for (Line line : lines) {
            		line.draw(gl, GL2.GL_COPY);
    			}
    			
    		}
    		else {
        		l.draw(gl, GL2.GL_COPY);
    		}

    		pts.clear();
    	}
    	
    	else{
    		pts.add(p2);
	    	l = new Line(p1,p2);
	    	gl.glColor3f(r,g,b);
	    	
	    	if (!clipRcts.isEmpty()) {
    			for (ClipRectangle clipRct : clipRcts) {
    				if (clipRct.liangbarsky(l) != null) {
    					lines.add(clipRct.liangbarsky(l));
    				}
    			}
    			for (Line line : lines) {
            		line.draw(gl, GL2.GL_COPY);
    			}
    		}    	
	    	else {	
        		l.draw(gl, GL2.GL_COPY);
    		}
	    }	 	
    }
}
