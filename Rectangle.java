package paint;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

public class Rectangle {
	// two points at opposite corners of a rectangle
    Point p1;
    Point p2;
    List<Point> pts = new ArrayList<Point>();
    List<Line> lines = new ArrayList<Line>();
    
    Rectangle(Point p1, Point p2){
		this.p1 = p1;
		this.p2 = p2;
		pts.add(new Point(p1.x, p1.y));
		pts.add(new Point(p1.x, p2.y));
		pts.add(new Point(p2.x, p2.y));
		pts.add(new Point(p2.x, p1.y));
		lines.add(new Line(pts.get(0), pts.get(1)));
		lines.add(new Line(pts.get(1), pts.get(2)));
		lines.add(new Line(pts.get(2), pts.get(3)));
		lines.add(new Line(pts.get(3), pts.get(0)));
    }
    
    Rectangle(List<Line> lines) {
		this.lines = lines;
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
