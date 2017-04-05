package paint;

import com.jogamp.opengl.GL2;

public class Line {
	// two points of a line
	Point p1;
	Point p2;
	
	Line(Point p1, Point p2){
		this.p1 = p1;
		this.p2 = p2;
	}
	
	// draw the line as line
	public void draw(GL2 gl, int how){
		gl.glLogicOp(how);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2f(p1.x,p1.y);
		gl.glVertex2f(p2.x,p2.y);
		gl.glEnd();
		gl.glFlush();
    }
}
