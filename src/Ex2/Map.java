package Ex2;
import java.io.Serializable;
import java.util.*;

/**
 * This class represents a 2D map (int[w][h]) as a "screen" or a raster matrix or maze over integers.
 * This is the main class needed to be implemented.
 *
 * @author boaz.benmoshe
 *
 */



public class Map implements Map2D, Serializable{


    private int [][] map;


    // edit this class below
	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {
        init(w, h, v);
    }
	/**
	 * Constructs a square map (size*size).
	 * @param size
	 */
	public Map(int size)
    {
        this(size,size, 0);
    }
	
	/**
	 * Constructs a map from a given 2D array.
	 * @param data
	 */
	public Map(int[][] data) {

        init(data);
	}



    @Override
	public void init(int w, int h, int v) {
        if (w != 0 && h != 0) {
           this.map = new int[w][h];
            for (int i =0;i< map.length;i++){
                for (int j =0;j<map[0].length;j++){
                    this.map[i][j] = v;


                }

            }
        }


	}
	@Override
	public void init(int[][] arr) {
        if (arr == null || arr.length<1 || arr[0].length< 1){
            throw new RuntimeException("Invalid array for init");
        }
        this.map = new int[arr.length][arr[0].length];
        for (int i =0;i<arr.length;i++){
            for (int j =0;j<arr[0].length;j++){
                this.map[i][j] = arr[i][j];
            }

        }

	}
	@Override
	public int[][] getMap() {
		int[][] ans = null;
        if (this.map == null)
            return ans;
        int w = this.getWidth();
        int h = this.getHeight();
        int [] [] copy = new int[w][h];
        for (int i =0; i<copy.length; i++){
            for (int j = 0;j<copy[0].length;j++){
                copy[i][j] = this.map[i][j];

            }
        }
		return copy;
	}
	@Override
	public int getWidth() {
        int ans = 0;
        if (this.map == null)
            return ans;


        return this.map.length;
    }
	@Override
	public int getHeight() {
        int ans = 0;
        if (this.map == null || this.map[0]==null)
            return ans;

        return this.map[0].length;
    }
	@Override
	public int getPixel(int x, int y) {
        int ans = -1;
        if (x<0 || y<0)
            return ans;

        return this.map[x][y];
    }
	@Override
	public int getPixel(Pixel2D p) {
        int ans = -1;
        if (p.getX()<0 || p.getY()<0)
            return ans;

        return getPixel(p.getX(),p.getY());
	}
	@Override
	public void setPixel(int x, int y, int v) {
        map[x][y] = v;

    }
	@Override
	public void setPixel(Pixel2D p, int v) {
        setPixel(p.getX(),p.getY(),v);

	}

    @Override
    public boolean isInside(Pixel2D p) {
        boolean ans = true;
        int x = p.getX();
        int y = p.getY();
        int h = getHeight();
        int w = getWidth();
        return ((x>=0 && x<w) && (y >= 0 && y<h));
    }

    @Override
    public boolean sameDimensions(Map2D p) {
        boolean ans = false;
        if (this.getWidth() == p.getWidth() && this.getHeight() == p.getHeight())
            ans = true;
        return ans;
    }

    @Override
    public void addMap2D(Map2D p) {
        if (this.sameDimensions(p) && p!= null){
            for (int i =0;i<p.getWidth();i++){
                for (int j =0;j<p.getHeight();j++){
                    int adding = p.getPixel(i,j);
                    this.map[i][j] += adding;

                }

            }

        }




    }

    @Override
    public void mul(double scalar) {
        for (int i =0;i<this.getWidth();i++){
            for (int j =0;j<this.getHeight();j++){
               double multy = this.map[i][j]* scalar;
               this.map[i][j] = (int) multy;
            }
        }


    }

    @Override
    public void rescale(double sx, double sy) {
        if (sx<= 0.0 || sy<=0.0 ){
            throw new RuntimeException("Invalid array for init");
        }
        int w = (int) (this.getWidth() * sx);
        int h = (int) (this.getHeight() * sy);
        int newW = Math.min(w,this.getWidth());
        int newH = Math.min(h,this.getHeight());
        int[][] copy = this.getMap();

        setMap(w,h);

        for (int i =0;i<newW;i++){
            for (int j=0;j<newH;j++){
                this.map[i][j] = copy[i][j];


            }
        }




    }
    public void setMap(int w,int h){

        this.map = new int[w][h];

    }

    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {
        for (int i =0; i< map.length;i++){
            for (int j =0;j<map[0].length;j++) {
                Pixel2D current = new Index2D(i,j);
                if (current.distance2D(center) <= rad)
                    setPixel(i,j,color);
            }
        }

    }

    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {
        if (!(this.isInside(p1)) || !(this.isInside(p2)))
            throw new RuntimeException("Invalid array for drawLine");
        if (p2 == p1)
            setPixel(p1,color);
        int dx =Math.abs( p1.getX() - p2.getX());
        int dy = Math.abs(p1.getY() - p2.getY());
        double m = 0.0;
        if (dx >= dy ){
            if (p1.getX()>p2.getX())
                 drawLine(p2,p1,color);
            else {

                if (dx != 0)
                    m = (double) dy / dx;
                for (int x = p1.getX(); x <= p2.getX(); x++) {
                    double y = (int) p1.getY() + m * (x - p1.getX());
                    setPixel(x,(int)Math.round(y),color );
                }
            }

        }
        else {
            if (p1.getY()>p2.getY())
                drawLine(p2,p1,color);
            else {
                if (dy != 0)
                    m = (double) dx/dy;
                for (int y=p1.getY();y <=p2.getY();y++){
                    double x = p1.getX() +m*(y-p1.getY());
                    setPixel((int) Math.round(x),y,color);
                }
            }
        }
    }

    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {
        int x1 = p1.getX();int x2 = p2.getX();
        int y1 = p1.getY(); int y2 = p2.getY();
        if (p1 == null || p2 == null)
            throw new RuntimeException("Invalid array for init");
        if (p1 == p2)
            setPixel(p1,color);

        else {
            int minX = Math.min(x1,x2 );
            int maxX = Math.max(x1,x2);
            int minY = Math.min(y1,y2);
            int maxY = Math.max(y1,y2);
            for (int i =minX;i<=maxX;i++){
                for (int j=minY;j<=maxY;j++){
                    setPixel(i,j,color);
                }

            }


        }


    }

    @Override
    public boolean equals(Object ob) {
        boolean ans = false;
        if (this == ob) {
            ans = true;
            return ans;
        }
        if(!(ob instanceof Map2D))
            return ans;
        Map2D n = (Map2D) ob;
        if (!this.sameDimensions(n))
            return ans;
            for(int i =0;i<this.getWidth();i++){
                for (int j =0;j<this.getHeight();j++){
                    if (this.getPixel(i,j) != n.getPixel(i,j))
                        return ans;
                }
            }
            ans = true;
        return ans;
    }
    public Queue<Pixel2D> getNeighbers(Pixel2D p, boolean cyclic){
        Queue<Pixel2D> Neighbers = new LinkedList<>();
        int x = p.getX();
        int y = p.getY();
        int w = this.getWidth();
        int h = this.getHeight();
        int [] dx = {1,-1,0,0};
        int [] dy = {0,0,1,-1};
        for (int i =0;i<4;i++){
            int nextX = x + dx[i];
            int nextY = y + dy[i];
            if (cyclic){
                nextX = (nextX+w)%w;
                nextY = (nextY+h)%h;
                Neighbers.add(new Index2D(nextX,nextY));
            }
            else {
                if ((nextX >= 0) && (nextX < w) && (nextY >= 0) && nextY<h){
                    Neighbers.add(new Index2D(nextX,nextY));
                }
            }
        }
        return Neighbers;
    }
	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
		int Pcolor = getPixel(xy);
        if (Pcolor == new_v)
            return 0;
        Queue<Pixel2D> list = new LinkedList<>();
        list.add(xy);
        setPixel(xy,new_v);
        int count = 1;
        while (!list.isEmpty()){
            Pixel2D currunt = list.poll();
            for (Pixel2D neighber:getNeighbers(currunt,cyclic)){
                if (getPixel(neighber) == Pcolor){
                    setPixel(neighber,new_v);
                    list.add(neighber);
                    count++;
                }

            }

        }
		return count;
	}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
		Pixel2D[] ans = null;  // the result.
        Map2D copy = allDistance(p1,obsColor,cyclic);
        if (copy.getPixel(p2)<0 || !(isInside(p2)) || !(isInside(p1)))
            return ans;
        int anslen = copy.getPixel(p2) +1;
        ans =new Pixel2D[anslen];
        Pixel2D cur = p2;

        for (int i = anslen-1;i>=0;i--){
            ans[i] = cur;
            if (i ==0)
                break;
            for (Pixel2D neighber:getNeighbers(cur,cyclic)) {
                if (i-1 == copy.getPixel(neighber)) {
                    cur = neighber;
                    break;


                }
            }
        }

		return ans;
	}

    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        Map2D ans = null;// the result.
        if (this.getPixel(start) == obsColor || !(this.isInside(start)) )
            return ans;

        ans = new Map(this.getWidth(),this.getHeight(),-1);
        ans.setPixel(start,0);
        Queue<Pixel2D> List = new LinkedList<>();
        List.add(start);
        while (!List.isEmpty()) {
            Pixel2D cur = List.poll();
            int distocur = ans.getPixel(cur);
            for (Pixel2D neighber : getNeighbers(cur, cyclic)) {
                if (this.getPixel(neighber) == obsColor)
                    continue;
                if (ans.getPixel(neighber) == -1) {
                    ans.setPixel(neighber, distocur + 1);
                    List.add(neighber);
                }
            }
        }

        return ans;
    }
	////////////////////// Private Methods ///////////////////////

}
