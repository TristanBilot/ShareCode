package services;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import users.UsersDatabase;
public class CheckJsonFileService implements IService {
	private String jsonFile;
	
	public CheckJsonFileService(String file) {
		this.jsonFile = file;
	}
	
	public String getResultOfService() throws FileNotFoundException {
		Scanner scanner;

			scanner = new Scanner(new File(UsersDatabase.DEFAULT_FTP_URL + "analyse/" + jsonFile + ".json") );
			String jsonText = scanner.useDelimiter("\\A").next();
			scanner.close();

			JsonParser parser = new JsonParser();
			try {
				parser.parse(jsonText); // throws JsonSyntaxException
				return "[+] This file seams to be a Json file !";
			} catch (JsonSyntaxException e) {
				return "[-] This file not seams to be a Json file..";
			}					
	}

	@Override
	public void run() {
		try {
			getResultOfService();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
}
