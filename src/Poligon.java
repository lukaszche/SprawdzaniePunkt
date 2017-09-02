import java.util.ArrayList;

public class Poligon {
	private int liczba_punktow; 
	private ArrayList<Integer> punkt_id=new ArrayList<Integer>();
	public Poligon(int lp, ArrayList<Integer> pid){
		liczba_punktow=lp;
		for(int i:pid){
			punkt_id.add(i);
		}
	}
}
