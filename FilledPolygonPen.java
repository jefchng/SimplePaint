package paint;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

public class FilledPolygonPen extends Pen {
	Point p, p1,p2;
    Line l;
    List<Point> pts = new ArrayList<Point>();
    List<Line> lines = new ArrayList<Line>();
    FilledPolygon fpoly;
    FilledPolygon clippedPoly;
	
    FilledPolygonPen(GL2 gl){
	super(gl);
    }
    public void mouseUp(MouseEvent e){
    	int xnow = e.getX();
    	int ynow = e.getY();
    	if (pts.size() == 0) {
    		p = p1 = p2 = new Point(xnow,ynow);
    	}
    	else {
    		int index = pts.size() - 1;
    		p1 = pts.get(index);
        	p2 = new Point(xnow,ynow);
    	}

    	if (pts.size() != 0 && p1.x == p2.x && p1.y == p2.y) {
    		fpoly = new FilledPolygon(pts);
    		
    		if (!clipRcts.isEmpty()) {
    			for (ClipRectangle clipRct : clipRcts) {
    				if (clipRct.sutherlandHodgman(fpoly.pts) != null) {
	    				gl.glColor3f(r,g,b);
	    				clippedPoly = new FilledPolygon(clipRct.sutherlandHodgman(fpoly.pts));
	    				clippedPoly.draw(gl, GL2.GL_COPY);
    				}
    			}
    		}
    		else {
    			gl.glColor3f(r,g,b);
    			fpoly.draw(gl, GL2.GL_COPY);
    		}
    		
    		l = new Line(p2,p);
    		lines.add(l);
    		gl.glColor3f(1-r,1-g,1-b);
    		l.draw(gl, GL2.GL_XOR);
    		
    		
    		for (Line line : lines) {
    			line.draw(gl, GL2.GL_XOR);
    		}
    		
    		pts.clear();
    		lines.clear();
    	}
    	
    	else{
    		pts.add(p2);
	    	l = new Line(p1,p2);
	    	lines.add(l);
	    	gl.glColor3f(1-r,1-g,1-b);
	    	l.draw(gl, GL2.GL_XOR);
	    }	
    }
}
