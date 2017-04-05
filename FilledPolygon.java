package paint;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

public class FilledPolygon {
	Point p1;
	Point p2;
	Line l;
	int size;
	List<Point> pts = new ArrayList<Point>(); 
	
	FilledPolygon(List<Point> pts) {
		this.size = pts.size();
		this.pts = pts;
	}
	
	public void draw(GL2 gl, int how){
		gl.glLogicOp(how);
		gl.glBegin(GL2.GL_POLYGON);
		
		for (int i = 0; i<this.size; i++){
			float x, y;
			x = this.pts.get(i).x;
			y = this.pts.get(i).y;
			gl.glVertex2f(x,y);
		} 
		gl.glEnd();
		gl.glFlush();	
    }
}
