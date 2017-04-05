package paint;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

public class ClipRectangle {
	// two points at opposite corners of a rectangle
    Point p1;
    Point p2;
    
    int tmin = 0;
    int tmax = 1;
    int xright, xleft, ybottom, ytop;
    
    ClipRectangle(Point p1, Point p2){
    	int xleft, xright, ybottom, ytop;
    	
		if (p1.x < p2.x) {
			xleft = p1.x;
			xright = p2.x;
		}
		else {
			xleft = p2.x;
			xright = p1.x;
		}
		if (p1.y < p2.y) {
			ytop = p1.y;
			ybottom = p2.y;
		}
		else {
			ytop = p2.y;
			ybottom = p1.y;
		}
		
		this.p1 = p1;
		this.p2 = p2;
		this.xleft = xleft;
		this.xright = xright;
		this.ybottom = ybottom;
		this.ytop = ytop;
		
		

    }
    // Liang Barsky
    public Line liangbarsky(Line L){
    	float dx, dy, t;
    	float tmin = 0;
    	float tmax = 1;
    	if (L == null) {
    		return null;
    	}
    	
    	dx = L.p2.x - L.p1.x;
    	dy = L.p2.y - L.p1.y;
    	
    	// if L is entering at xleft
    	if (dx >= 0 && xleft >= L.p1.x) {
    		t = (xleft - L.p1.x) / dx;
    		tmin = Math.max(tmin, t);
    		}
    	// if L is entering at xright
    	if (dx <= 0 && xright <= L.p1.x) {
    		t = (xright - L.p1.x) / dx;
    		tmin = Math.max(tmin, t);
    		}
    	// if L is entering at ytop
    	if (dy >= 0 && ytop >= L.p1.y) {
    		t = (ytop - L.p1.y) / dy;
    		tmin = Math.max(tmin, t);
    		}
    	// if L is entering at ybottom
    	if (dy <= 0 && ybottom <= L.p1.y) {
    		t = (ybottom - L.p1.y) / dy;
    		tmin = Math.max(tmin, t);
    		}

    	// Exiting
    	// if L is exiting at xleft
    	if (dx <= 0 && xleft >= L.p2.x) {
    		t = (xleft - L.p1.x) / dx;
    		tmax = Math.min(tmax, t);
    		}
    	// if L is exiting at xright
    	if (dx >= 0 && xright <= L.p2.x) {
    		t = (xright - L.p1.x) / dx;
    		tmax = Math.min(tmax, t);
    		}
    	// if L is exiting at ytop
    	if (dy <= 0 && ytop >= L.p2.y) {
    		t = (ytop - L.p1.y) / dy;
    		tmax = Math.min(tmax, t);
    		}
    	// if L is exiting at ybottom
    	if (dy >= 0 && ybottom <= L.p2.y) {
    		t = (ybottom - L.p1.y) / dy;
    		tmax = Math.min(tmax, t);
    		}
    	
    	// Rejection
    	if (tmax < tmin) {
    		return null;
    	}
    	// Clip
    	else {
    		return new Line(new Point(Math.round(L.p1.x + tmin*dx), Math.round(L.p1.y + tmin*dy)), 
    				new Point(Math.round(L.p1.x + tmax*dx), Math.round(L.p1.y + tmax*dy)));
    	}
    }
    
    // Helper function for "insideness" of a point relative to clipping rectangle
    public int insideness(int i, Point p) {
    	if (i == 0) {
    		if (p.x >= xleft) {return 1;}
    		else {return 0;}
    	}
    	if (i == 1) {
    		if (p.x <= xright) {return 1;}
    		else {return 0;}
    	}
    	if (i == 2) {
    		if (p.y >= ytop) {return 1;}
    		else {return 0;}
    	}
    	if (i == 3) { 
    		if (p.y <= ybottom) {return 1;}	
    		else {return 0;}
    	}
    	return 0;
    }
    
    // Helper function to find the intersection of an edge and a line
    public Point intersection(int i, Line L) {
    	Point pt = null;
    	float dx, dy, t;
    	dx = L.p2.x - L.p1.x;
    	dy = L.p2.y - L.p1.y;
    	
    	if (i == 0) {
    		t = (xleft - L.p1.x) / dx; 
    		if (t <= 1 && t >=0) {
    			pt = new Point(xleft, Math.round(L.p1.y + t*dy));
    		}
    	}
    	if (i == 1) {
    		t = (xright - L.p1.x) / dx;
    		if (t <= 1 && t >=0) {
    			pt = new Point(xright, Math.round(L.p1.y + t*dy));
    		}
    	}
    	if (i == 2) {
    		t = (ytop - L.p1.y) / dy; 
    		if (t <= 1 && t >=0) {
    			pt = new Point(Math.round(L.p1.x + t*dx), ytop);
    		}		
    	}
    	if (i == 3) {
    		t = (ybottom - L.p1.y) / dy; 
    		if (t <= 1 && t >=0) {
    			pt = new Point(Math.round(L.p1.x + t*dx), ybottom);
    		}
    	}
    		
    	
    	return pt;
    }
    
    // Sutherland Hodgman
    public List<Point> sutherlandHodgman(List<Point> pts){
    	int currState, nextState;
    	Point prevPt, currPt, intersection;
    	Line currLine;
    	List<Point> unclippedPts = new ArrayList<Point>();
    	List<Point> clippedPts = new ArrayList<Point>();
    	unclippedPts.addAll(pts);
    	
    	
    	
    	for (int i = 0; i < 4; i++) {
    		if (unclippedPts.size() == 0) {
        		return null;
        	}
    		prevPt = unclippedPts.get(unclippedPts.size() - 1);
        	currState = insideness(i, prevPt);
    		clippedPts.clear();
		    for (int j = 0; j < unclippedPts.size(); j++) {
		    	currPt = unclippedPts.get(j); 
		    	nextState = insideness(i, unclippedPts.get(j));
		    	if (currState == 1 && nextState == 1) {
		    		clippedPts.add(currPt);
		    	}
		    	if (currState == 1 && nextState == 0) {
		    		currLine = new Line(prevPt, currPt);
		    		intersection = intersection(i, currLine);
		    		if (intersection != null) {
		    			clippedPts.add(intersection);
		    		}
		    	}
		    	if (currState == 0 && nextState == 1) {
		    		currLine = new Line(prevPt, currPt);
		    		intersection = intersection(i, currLine);
		    		if (intersection != null) {
		    			clippedPts.add(intersection);
		    		}
		    		clippedPts.add(currPt);
		    	}
		    	if (currState == 0 && nextState == 0) {
		    		// do nothing
		    	}
		    	currState = nextState;
		    	prevPt = new Point(currPt.x, currPt.y);
		    	
		    }
		    unclippedPts.clear();
	    	unclippedPts.addAll(clippedPts);
    	}
    	unclippedPts.clear();
    	if (clippedPts.size() == 0) {return null;}
    	return clippedPts;
    }
    
    // draw the rectangle as a line loop
    public void draw(GL2 gl, int how){
		gl.glLogicOp(how);
		gl.glBegin(GL2.GL_LINE_LOOP);
		gl.glVertex2f(p1.x,p1.y);
		gl.glVertex2f(p1.x,p2.y);
		gl.glVertex2f(p2.x,p2.y);
		gl.glVertex2f(p2.x,p1.y);
		gl.glEnd();
		gl.glFlush();
	}
}
