
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {
	public static void main(String[] args) {
		/*
		try {
			BufferedWriter file = new BufferedWriter(new FileWriter("output.txt")); 
			file.write("Hello, World!");
			file.close();
			} catch (IOException e){
				e.printStackTrace();
			}
			*/
		
		try {
			FileWriter file = new FileWriter("output.txt", true);
			file.write("Hello World!");
			file.close();
		} catch(IOException e) {
			
		}
		try {
			BufferedReader file = new BufferedReader(new FileReader("output.txt"));
			String line;
			while ((line = file.readLine()) != null) {
				System.out.println(line);
			}
			file.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}	