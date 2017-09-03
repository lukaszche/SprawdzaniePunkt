package package1;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;

public class CPMain {
	
	//------------------funkcja main-------------------------
	
	public static void main(String[] Args) throws IOException{
		//long millisActualTime = System.currentTimeMillis(); 
		
		ArrayList<Point> points=new ArrayList<>(); //kontener przechowujacy wczytane punkty z pliku
		ArrayList<Poligon> polygons=new ArrayList<>();  //kontener przechowujacy wczytane poligony z pliku
		wczytaj("wsad.txt",points,polygons);  //wczytywanie  punktow i poligonow z pliku "wsad.txt"
		System.out.println("Punkty:"); 
		wypiszPunkty(points);		//wypisanie wczytanych punktow
		wypiszPoligony(polygons);		//wypisanie wczytanych poligonow
		System.out.println();
		System.out.println("Punkty wystepujace w wiecej niz 2 polygonach:"); 
		//for(int i=0;i<10000;i++){
		wypiszPunkty(sprawdzPunkty(points, polygons));   //wyswietlenie wyniku funkcji z zadania
		//}
		//wykonanie programu
		//long executionTime = System.currentTimeMillis() - millisActualTime; //czas wykonania programu
		//System.out.println(executionTime);
	}
	//--------------------------------------------------------
	
	public static void wypiszPunkty (ArrayList<Point> points){ //metoda wyswietlajaca id, posX,posY z obiektow Point z tablicy
		Collections.sort(points);  //Sortowanie dla lepszej przejrzystosci wyswietlanaych danych
		for(Point i:points){    //iterujemy punkty z podanej tablicy
			System.out.println(i.getId()+", "+i.getX()+", "+i.getY());
		}
	}
	public static void wypiszPoligony (ArrayList<Poligon> polygons){ //metoda wyswietlajaca liczbe punktow i id punktow skladajacych sie na poligony
		for(Poligon i:polygons){ 	//iterujemy poligony z podanej tablicy
			System.out.println("Liczba punktów: "+i.getLiczba_punktow());   //wyswietlamy liczbe punktow 
			System.out.println(i.getPunkt_id());   							//wyswietlamy id punktow
		}
	}
	
	//---------------------------------------------------------------
	
	public static void wczytaj (String path, ArrayList<Point> points, ArrayList<Poligon> polygons) throws IOException{   //metoda wczytujaca dane z pliku
		FileReader fReader = new FileReader(path);
		Scanner scaner = new Scanner(fReader);		//tworzymy scanner ktory ulatwi parsowanie danych z pliku do naszych struktur danych
		scaner.useLocale(Locale.US);				//jako ze dane podane sa z kropka jako separatorem dziesietnym  zmieniamy na US system
		int iter=0;									// iterator dla tablicy polygonow
		
		String textLine;							//tmp string dla pojedynczej linii
		String [] pointLine;						// tablica w ktorej zapisze 3 stringi reprezentujace id, posX, posY
		
		while(scaner.hasNext()) {   				//petla wczytujaca dane z pliku
			textLine = scaner.nextLine();			//bierzemy linie znakow z pliku
			if (textLine.contains("tablica")){		//sprawdzamy czy rozpoczynaja sie dane dla tablicy punktow
				while (scaner.hasNext() && !scaner.hasNext("}")){	//wczytujemy kolejne linie danych id, posX, posY dopoki nie natrafimy na "}" konczacy zbior danych
					textLine=scaner.nextLine();			//bierzemy linie danych
					pointLine=textLine.split(",");		//jako ze id,posX,posY sa oddzielone od siebie przecinkami oddzielamy je za pomoca metody split, gdyby dane byly oddzielone " " mozna by pominac ten krok i od razu wczytywac za pomoca scaner.nextInt/Double
					points.add(new Point(Integer.parseInt(pointLine[0]),Double.parseDouble(pointLine[1]),Double.parseDouble(pointLine[2])));	//dodajemy nowy obiekt Point do tablicy punktow za pomoca konstruktora przy okazji ustawiamy pola id,posX,posY
				}
			}
			
			if (textLine.contains("poligon")){		//sprawdzamy czy rozpoczynaja sie dane dla poligonu 
				textLine=textLine.substring(textLine.indexOf("{"));  //wyciagamy z linii znakow liczbe punktow w poligonie, ktora znajduje sie po znaku "{" np: "poligon p1 {39{"
				polygons.add(new Poligon(Integer.parseInt(textLine.replaceAll("[\\D]", "")))); //usuwamy kolejne zbedne znaki nie bedace cyframi np: po poprzedniej operacji zostalo "{39{", a po tej zostanie "39"
				while (scaner.hasNext() && !scaner.hasNext("}}")){			//wczytujemy kolejne linie danych dopoki nie natrafimy na "}}" konczacy zbior danych dla poligonu
					polygons.get(iter).addPunkt_id(scaner.nextInt());
				}
				iter++;								//zwiekszamy iterator dla tablicy poligonow
			}		  
		    
		}
		scaner.close();								//zamykamy skaner
	}
	
	public static ArrayList<Point> sprawdzPunkty(ArrayList<Point> points, ArrayList<Poligon> polygons){    //funkcja bedaca przedmiotem zadania z e-maila
		ArrayList<Point> result=new ArrayList<Point>();											//tworzymy tablice wynikowa dla punktow ktore wystepuja ponad 2 razy
		int count=0;			//zmienna dla liczby wystapien danego punktu
		
		ArrayList<HashSet<Integer>> tmp=new ArrayList<HashSet<Integer>>();  //HashSet pomocniczy zeby uzyskac szybsze przeszukiwanie danych w celu znalezienia wystapienia id punktu
		for(Poligon poly:polygons){	
			tmp.add(new HashSet<Integer>(poly.getPunkt_id()));			//przepisanie daych z poligonow do pomocniczych HashSetow
		}
		
		for(Point point:points){								//iterujemy po wszystkich punktach
			for(HashSet<Integer> poly:tmp){						//iterujemy po wszystkich polygonach (HashSetach utworzonych na potrzeby szybszego wyszukiwania)
				if(poly.contains(point.getId())){		//sprawdzamy czy HashSet zawiera id danego punktu, dzieki zastosowaniu HashSet(poniewaz nie jest wymagany dostep do id punktow w poligonie) zlozonosc obliczeniowa dla sprawdzania jest mozliwie najnizsza
					count++;				//jezeli sie zawiera zwiekszamy liczbe wystapien
				}
				if(count>2){				
					result.add(point);		//jezeli liczba wystapien jest wieksza niz 2 dodajemy punkt to tablicy wynikowej i przerywamy petle for poniewaz musimy wiedziec tylko czy punkt wystepuje wiecej niz 2 razy, a nie istotna jest dokladna liczba wystapien
					break;
				}
			}
			
			count=0;					//resteujemy liczbe wystapien na potrzeby kolejnego punktu
			
		}
		
		return result;				//zwracamy tablice z punktami spelniajacymi warunki zadania
	}
}
