package Images;

import java.util.Comparator;

public class Point {
  private   int x;
  private   int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

   public static Comparator<Point> byX = new Comparator<Point>() {
       @Override
       public int compare(Point o1, Point o2) {
           return o1.x-o2.x;
   }};

    public static Comparator<Point> byY = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            return o1.y-o2.y;
        }};
}
