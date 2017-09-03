package package1;
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
	public Poligon(int lp){
		liczba_punktow=lp;
	}
	public int getLiczba_punktow() {
		return liczba_punktow;
	}
	public void setLiczba_punktow(int liczba_punktow) {
		this.liczba_punktow = liczba_punktow;
	}
	public ArrayList<Integer> getPunkt_id() {
		return punkt_id;
	}
	public void setPunkt_id(ArrayList<Integer> punkt_id) {
		this.punkt_id = punkt_id;
	}
	public void addPunkt_id(int id) {
		this.punkt_id.add(id);
	}
	
	//public void sortuj(){
	//	punkt_id.sort(null);
	//}
	
}
