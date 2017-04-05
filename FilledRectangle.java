package paint;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

public class FilledRectangle {
	// two points at opposite corners of a rectangle
    Point p1;
    Point p2;
    List<Point> pts = new ArrayList<Point>();
    
    FilledRectangle(Point p1, Point p2){
		this.p1 = p1;
		this.p2 = p2;
		pts.add(new Point(p1.x, p1.y));
		pts.add(new Point(p1.x, p2.y));
		pts.add(new Point(p2.x, p2.y));
		pts.add(new Point(p2.x, p1.y));
    }
    
    FilledRectangle(List<Point> pts) {
		this.pts = pts;
    }
    
    // draw the rectangle as a polygon
    public void draw(GL2 gl, int how){
		gl.glLogicOp(how);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2f(p1.x,p1.y);
		gl.glVertex2f(p1.x,p2.y);
		gl.glVertex2f(p2.x,p2.y);
		gl.glVertex2f(p2.x,p1.y);
		gl.glEnd();
		gl.glFlush();
    }
}
