package fr.lip6.move.gal.contribution.orders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class myCode {
	//	/home/joe/Documents/LTSmin/greatSPN/SmallOperatingSystem-PT-MT0016DC0008
	
	
	
//	final String samplesDirName = "/home/joe/Documents/LTSmin/greatSPN/SmallOperatingSystem-PT-MT0016DC0008"; 
//	List<File> listeFichiers;
//

//		File samplesDir = new File(samplesDirName);
//		for (File f : samplesDir.listFiles()) 
//			files.add(f);

//			BufferedReader reader = new BufferedReader(new FileReader(f));
//			String ligne;
//			while((ligne = reader.readLine()) != null)
//				ls.add(ligne);


	public static String[] order(){
		
		String [] res = null;;
		File f = new File("order.txt");
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(f));
			String ligne;
			ligne = reader.readLine();
			res = ligne.split(" ");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public static void script() {
		String cmd = "";
		cmd += "/home/joe/Documents/LTSmin/greatSPN/usr/local/GreatSPN/bin/RGMEDD2 /home/joe/Documents/LTSmin/greatSPN/SmallOperatingSystem-PT-MT0016DC0008/model -FR -varord | sed -n /\"VARORDER\"/p | sed -E s/^\"VARORDER:  \"// > order.txt";
		String[] args = { "/bin/sh", "-c", cmd};

		try {
			Runtime.getRuntime().exec(args);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	
	}
	
	

//		File f;
//		FileReader fd = null;
//		String buffer ="";
//		int i;
//		ArrayList<String> res = new ArrayList<String>();
//
//		try{
//			if( !pathFic.endsWith(".txt") )
//				throw new BadArgumentException("Tools.fic_to_listWord: Le fichier n'est pas au bon format"); 
//
//			f = new File(pathFic);
//			if(!f.exists()){
//				throw new BadArgumentException("Tools.fic_to_listWord: Le fichier n'existe pas");
//			}
//
//			fd = new FileReader(f);
//			FileInputStream f;
//			BufferedInputStream b;
//			b.
//			fd.
//			/* remplissage de la liste */
//			while((i = fd.read()) != -1){
//				if(i == (int) ' ' || i == (int) '\n' 
//						|| i == (int) '\t' || i == (int) '\f' || i == (int) '\r'){
//					if(buffer.compareTo("") != 0){
//						res.add(buffer);
//						buffer = "";
//					}
//				}else buffer += (char) i; 
//			}
//		}catch(BadArgumentException e){
//			e.getMessage();
//			return null;
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally {
//			try {
//				if(fd != null) fd.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return res;
//	}
}
