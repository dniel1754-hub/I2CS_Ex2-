package Ex2;

public class Index2D implements Pixel2D {
    private int x;
    private int y;
    public Index2D(int x, int y) {
        this.x = x;
        this.y = y;

    }
    public Index2D(Pixel2D other) {
        if (other != null)
        {
            this.x = other.getX();
            this.y = other.getY();
        }



    }
    @Override
    public int getX() {

        return this.x;
    }

    @Override
    public int getY() {

        return this.y;
    }

    @Override
    public double distance2D(Pixel2D p2) {
        if (p2 == null) return Double.MAX_VALUE;
        double dx = this.x - p2.getX();
        double dy = this.y - p2.getY();

        return Math.sqrt(dx*dx + dy*dy);
    }

    @Override
    public String toString() {

        return this.x + "," + this.y;
    }

    @Override
    public boolean equals(Object p) {
        if (this == p)
            return true;
        if (p == null || !(p instanceof Pixel2D))
        return false;
        Pixel2D other = (Pixel2D) p;
        boolean ans = this.x == other.getX() && this.y == other.getY();

        return ans;
    }
}
