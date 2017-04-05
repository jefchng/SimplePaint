package paint;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

public abstract class Pen {
	protected static final int FIRST=1, CONTINUE=2;
    protected int state;
    protected float r,g,b;
    protected GL2 gl;
    protected static List<ClipRectangle> clipRcts = new ArrayList<ClipRectangle>();
    
    Pen(GL2 gl){
		this.gl = gl;
		state = FIRST;
    }
    
    public void mouseDown(MouseEvent e){}
    public void mouseUp(MouseEvent e){}
    public void mouseDragged(MouseEvent e){}
    public void setColor(float r, float g, float b){
    	this.r = r; this.g = g; this.b = b;
    }
    public void clearClipRcts(){
    	clipRcts.clear();
    }
	
}
