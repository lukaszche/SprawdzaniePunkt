package package1;

public class Point implements Comparable<Point>{
	private int id;
	private double x,y;
	public Point (int id, double x, double y){
		this.id=id;
		this.x=x;
		this.y=y;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int compareTo(Point p)
	{
	     return(id - p.id);
	}
}
