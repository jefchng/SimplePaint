package paint;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

public class FilledRectanglePen extends Pen {
	Point p1, p2;
    FilledRectangle rct = null;
    List<Point> pts = new ArrayList<Point>();
    FilledPolygon clipped = null;
    List<FilledPolygon> fpolys = new ArrayList<FilledPolygon>();
    
    FilledRectanglePen(GL2 gl){
    	super(gl);
    }

    public void mouseDown(MouseEvent e){
		int xnow = e.getX();
		int ynow = e.getY();
		p1 = p2 = new Point(xnow,ynow);
		// vertices
	    pts.add(new Point(p1.x, p1.y));
	    pts.add(new Point(p1.x, p2.y));
	    pts.add(new Point(p2.x, p2.y));
	    pts.add(new Point(p2.x, p1.y));
	    //

	    rct = new FilledRectangle(p1,p2);
	    
	    
	    if (!clipRcts.isEmpty()) {
			for (ClipRectangle clipRct : clipRcts) {
				if (clipRct.sutherlandHodgman(pts) != null) {
					clipped = new FilledPolygon(clipRct.sutherlandHodgman(pts));
					fpolys.add(clipped);
					//gl.glColor3f(1-r,1-g,1-b);
					//clipped.draw(gl, GL2.GL_XOR);
				}
				else {
					fpolys.add(new FilledPolygon(pts));
				}
				for (FilledPolygon fpoly : fpolys) {
					gl.glColor3f(1-r,1-g,1-b);
					fpoly.draw(gl, GL2.GL_XOR);
				}
			}
		}
	    
	    else {
			gl.glColor3f(1-r,1-g,1-b);
			rct.draw(gl, GL2.GL_XOR);
		}
	    
    }
    
    public void mouseUp(MouseEvent e){
		// erase the last rectangle
    	if (!clipRcts.isEmpty() && !fpolys.isEmpty()) {
			//clipped.draw(gl, GL2.GL_XOR);
    		for (FilledPolygon fpoly : fpolys) {
				fpoly.draw(gl, GL2.GL_XOR);
			}
		}

    	else {
			rct.draw(gl, GL2.GL_XOR);
		}
    	fpolys.clear();
    	
		// get the new corner point
		int xnow = e.getX();
		int ynow = e.getY();
		p2 = new Point(xnow,ynow);
		
		pts.add(new Point(p1.x, p1.y));
	    pts.add(new Point(p1.x, p2.y));
	    pts.add(new Point(p2.x, p2.y));
	    pts.add(new Point(p2.x, p1.y));
		
	    rct = new FilledRectangle(p1,p2);
		
		if (!clipRcts.isEmpty()) {
			for (ClipRectangle clipRct : clipRcts) {
				if (clipRct.sutherlandHodgman(pts) != null) {
					clipped = new FilledPolygon(clipRct.sutherlandHodgman(pts));
					fpolys.add(clipped);
					//gl.glColor3f(r,g,b);
					//clipped.draw(gl, GL2.GL_COPY);
				}
				for (FilledPolygon fpoly : fpolys) {
					gl.glColor3f(r,g,b);
					fpoly.draw(gl, GL2.GL_COPY);
				}
			}
		}
		else {
			
			gl.glColor3f(r,g,b);
			// draw the new version permanently
			rct.draw(gl, GL2.GL_COPY);
	    }
		
		pts.clear();
		fpolys.clear();
	}
    
    public void mouseDragged(MouseEvent e){
    	// erase the last rectangle
    	if (!clipRcts.isEmpty() && !fpolys.isEmpty()) {
			//clipped.draw(gl, GL2.GL_XOR);
    		for (FilledPolygon fpoly : fpolys) {
				fpoly.draw(gl, GL2.GL_XOR);
			}
		}

    	//if (!clipRcts.isEmpty() && clipped != null) {
		//	clipped.draw(gl, GL2.GL_XOR);
		//}
    	
    	else {
			rct.draw(gl, GL2.GL_XOR);
		}
		pts.clear();
		fpolys.clear();
		
		// get the new corner point
		int xnow = e.getX();
		int ynow = e.getY();
		p2 = new Point(xnow,ynow);
		
		pts.add(new Point(p1.x, p1.y));
	    pts.add(new Point(p1.x, p2.y));
	    pts.add(new Point(p2.x, p2.y));
	    pts.add(new Point(p2.x, p1.y));
		
		if (!clipRcts.isEmpty()) {
			for (ClipRectangle clipRct : clipRcts) {
				if (clipRct.sutherlandHodgman(pts) != null) {
					clipped = new FilledPolygon(clipRct.sutherlandHodgman(pts));
					//clipped.draw(gl, GL2.GL_XOR);
					fpolys.add(clipped);
				}
			}
			for (FilledPolygon fpoly : fpolys) {
				fpoly.draw(gl, GL2.GL_XOR);
			}
		}
		
		else {
			rct = new FilledRectangle(p1,p2);
			// draw the new version
			rct.draw(gl, GL2.GL_XOR);
		}
		pts.clear();
    }
}
