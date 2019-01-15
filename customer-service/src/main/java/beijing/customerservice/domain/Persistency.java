package beijing.customerservice.domain;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Set;

public class Persistency implements Serializable {
	private static final long serialVersionUID = 9217656447808128794L;

	public static <E> void save(String fileName, Set<E> informationSaved) {
		try {
			String path = System.getProperty("user.dir") + "/Persistency";
			File file = new File(path);
			file.mkdir();
			
			FileOutputStream fileOut = new FileOutputStream(path + "/" + fileName);
			ObjectOutputStream save = new ObjectOutputStream(fileOut);
			
			save.writeObject(informationSaved);
			save.flush();
			save.close();
			fileOut.close();
		} catch (IOException e) {
			e.getMessage();
		} 
	}


	@SuppressWarnings("unchecked")
	public static <E> Set<E> load(String fileName) {
		Set<E> storedInformation = null;
		try {
			String path = System.getProperty("user.dir") + "/Persistency/" + fileName;
			FileInputStream fileIn = new FileInputStream(path);
			
			ObjectInputStream load = new ObjectInputStream(fileIn);
			storedInformation = (Set<E>) load.readObject();
			
			load.close();
			fileIn.close();
		} catch (IOException e) {
			e.getMessage();
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}
		return storedInformation;
	}
	
	
}
