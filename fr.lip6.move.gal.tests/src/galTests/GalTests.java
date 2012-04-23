package galTests;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.eclipse.xtext.junit.AbstractXtextTests;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import fr.lip6.move.GalInjectorProvider;
import fr.lip6.move.gal.System;



@RunWith(XtextRunner.class)
@InjectWith(GalInjectorProvider.class)
public class GalTests extends AbstractXtextTests {
	
	@Inject
	ParseHelper<System> galParser;
	
	private static int index = -1;

	
	
	static {
		new ReadGalFile(new File("/home/steph/workspaceSVN/fr.lip6.move.gal.tests/src/galFiles"), ".*\\.gal");
	}
	
	File files[] = ReadGalFile.getFiles();
	
	
	private static String readFileAsString(File filePath) throws IOException{
	    byte[] buffer = new byte[(int) filePath.length()];
	    BufferedInputStream f = null;
	    try {
	        f = new BufferedInputStream(new FileInputStream(filePath));
	        f.read(buffer);
	    } finally {
	        if (f != null) 
	        	try { f.close(); } 
	        catch (IOException ignored) { }
	    }
	    return new String(buffer);
	}
	
	
	@Before
	public void incrIndex(){
		index++;
	}
	
	
	@Test
	public void bakeryGal() throws Exception{

		System bakery = galParser.parse(readFileAsString(files[index]));
		assertEquals(2, bakery.getArrays().size());
		assertEquals(9, bakery.getVariables().size());
		assertEquals(24, bakery.getTransitions().size());
		assertEquals(0, bakery.getLists().size());
		
		assertEquals("bakery.4.dve", bakery.getName());
		
		for(int i=0; i < 24; i++)
			assertEquals("t"+i, bakery.getTransitions().get(i).getName());
		
		assertTrue(3 == bakery.getTransitions().get(9).getActions().size());
	}
	
	@Test
	public void brpGal() throws Exception{
		System brp = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(0, brp.getArrays().size());
		assertEquals(18, brp.getVariables().size());
		assertEquals(38, brp.getTransitions().size());
		assertEquals(0, brp.getLists().size());
		
		assertEquals("brp.1.dve", brp.getName());
		
		for(int i=0; i < 38; i++)
			assertEquals("t"+i, brp.getTransitions().get(i).getName());
		
		assertTrue(2 == brp.getTransitions().get(19).getActions().size());
	}
	
	@Test
	public void exitGal() throws Exception{
		System exit = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(2, exit.getArrays().size());
		assertEquals(4, exit.getVariables().size());
		assertEquals(45, exit.getTransitions().size());
		assertEquals(0, exit.getLists().size());
		
		assertEquals("exit.1.dve", exit.getName());
		
		for(int i=0; i < 44; i++)
			assertEquals("t"+i, exit.getTransitions().get(i).getName());
		
		assertTrue(2 == exit.getTransitions().get(30).getActions().size());
	}
	
	@Test
	public void firewire_linkGal() throws Exception{
		System firewire = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(3, firewire.getArrays().size());
		java.lang.System.out.println(firewire.getVariables().size());
		assertEquals(29, firewire.getVariables().size());
		assertEquals(412, firewire.getTransitions().size());
		assertEquals(0, firewire.getLists().size());
		
		assertEquals("firewire_link.2.dve", firewire.getName());
		
		for(int i=0; i < 412; i++)
			assertEquals("t"+i, firewire.getTransitions().get(i).getName());
		
		assertTrue(4 == firewire.getTransitions().get(130).getActions().size());
	}
	
	@Test
	public void fischerGal() throws Exception{
		System fischer = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(1, fischer.getArrays().size());
		assertEquals(5, fischer.getVariables().size());
		assertEquals(25, fischer.getTransitions().size());
		assertEquals(0, fischer.getLists().size());
		
		assertEquals("fischer.1.dve", fischer.getName());
		
		for(int i=0; i < 25; i++)
			assertEquals("t"+i, fischer.getTransitions().get(i).getName());
		
		assertTrue(2 == fischer.getTransitions().get(12).getActions().size());
	}
	
	@Test
	public void frogsGal() throws Exception{
		System frogs = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(1, frogs.getArrays().size());
		assertEquals(3, frogs.getVariables().size());
		assertEquals(9, frogs.getTransitions().size());
		assertEquals(0, frogs.getLists().size());
		
		assertEquals("frogs.1.dve", frogs.getName());
		
		for(int i=0; i < 9; i++)
			assertEquals("t"+i, frogs.getTransitions().get(i).getName());
		
		assertTrue(3 == frogs.getTransitions().get(6).getActions().size());
	}
	
	@Test
	public void hanoiGal() throws Exception{
		System hanoi = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(1, hanoi.getArrays().size());
		assertEquals(0, hanoi.getVariables().size());
		assertEquals(48, hanoi.getTransitions().size());
		assertEquals(0, hanoi.getLists().size());
		
		assertEquals("hanoi.1.dve", hanoi.getName());
		
		for(int i=0; i < 48; i++)
			assertEquals("t"+i, hanoi.getTransitions().get(i).getName());
		
		assertTrue(1 == hanoi.getTransitions().get(47).getActions().size());
	}
	
	@Test
	public void hanoiOriGal() throws Exception{
		System hanoi = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(3, hanoi.getArrays().size());
		assertEquals(3, hanoi.getVariables().size());
		assertEquals(6, hanoi.getTransitions().size());
		assertEquals(0, hanoi.getLists().size());
		
		assertEquals("hanoi.ori.1.dve", hanoi.getName());
		
		for(int i=0; i < 6; i++)
			assertEquals("t"+i, hanoi.getTransitions().get(i).getName());
		
		assertTrue(4 == hanoi.getTransitions().get(1).getActions().size());
	}
	
	@Test
	public void lamportGal() throws Exception{
		System lamport = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(1, lamport.getArrays().size());
		assertEquals(8, lamport.getVariables().size());
		assertEquals(42, lamport.getTransitions().size());
		assertEquals(0, lamport.getLists().size());
		
		assertEquals("lamport.2.dve", lamport.getName());
		
		for(int i=0; i < 42; i++)
			assertEquals("t"+i, lamport.getTransitions().get(i).getName());
		
		assertTrue(1 == lamport.getTransitions().get(25).getActions().size());
	}
	
	@Test
	public void loydGal() throws Exception{
		System loyd = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(1, loyd.getArrays().size());
		assertEquals(3, loyd.getVariables().size());
		assertEquals(5, loyd.getTransitions().size());
		assertEquals(0, loyd.getLists().size());
		
		assertEquals("loyd.1.dve", loyd.getName());
		
		for(int i=0; i < 5; i++)
			assertEquals("t"+i, loyd.getTransitions().get(i).getName());
		
		assertTrue(3 == loyd.getTransitions().get(3).getActions().size());
	}
	
	@Test
	public void lupGal() throws Exception{
		System lup = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(0, lup.getArrays().size());
		assertEquals(6, lup.getVariables().size());
		assertEquals(48, lup.getTransitions().size());
		assertEquals(0, lup.getLists().size());
		
		assertEquals("lup.1.dve", lup.getName());
		
		for(int i=0; i < 48; i++)
			assertEquals("t"+i, lup.getTransitions().get(i).getName());
		
		assertTrue(2 == lup.getTransitions().get(23).getActions().size());	
	}
	
	@Test
	public void petersonGal() throws Exception{
		System peterson = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(2, peterson.getArrays().size());
		assertEquals(12, peterson.getVariables().size());
		assertEquals(28, peterson.getTransitions().size());
		assertEquals(0, peterson.getLists().size());
		
		assertEquals("peterson.4.dve", peterson.getName());
		
		for(int i=0; i < 28; i++)
			assertEquals("t"+i, peterson.getTransitions().get(i).getName());
		
		assertTrue(2 == peterson.getTransitions().get(0).getActions().size());	
	}
	
	@Test
	public void philsGal() throws Exception{
		System phils = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(1, phils.getArrays().size());
		assertEquals(10, phils.getVariables().size());
		assertEquals(54, phils.getTransitions().size());
		assertEquals(0, phils.getLists().size());
		
		assertEquals("phils.4.dve", phils.getName());
		
		for(int i=0; i < 54; i++)
			assertEquals("t"+i, phils.getTransitions().get(i).getName());
		
		assertTrue(2 == phils.getTransitions().get(39).getActions().size());
	}
	
	@Test
	public void reader_writerGal() throws Exception{
		System reader_writer = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(0, reader_writer.getArrays().size());
		assertEquals(30, reader_writer.getVariables().size());
		assertEquals(112, reader_writer.getTransitions().size());
		assertEquals(0, reader_writer.getLists().size());
		
		assertEquals("reader_writer.3.dve", reader_writer.getName());
		
		for(int i=0; i < 112; i++)
			assertEquals("t"+i, reader_writer.getTransitions().get(i).getName());
		
		assertTrue(2 == reader_writer.getTransitions().get(98).getActions().size());
	}
	
	@Test
	public void szymanskiGal() throws Exception{
		System szymanski = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(3, szymanski.getArrays().size());
		assertEquals(6, szymanski.getVariables().size());
		assertEquals(72, szymanski.getTransitions().size());
		assertEquals(0, szymanski.getLists().size());
		
		assertEquals("szymanski.2.dve", szymanski.getName());
		
		for(int i=0; i < 72; i++)
			assertEquals("t"+i, szymanski.getTransitions().get(i).getName());
		
		assertTrue(1 == szymanski.getTransitions().get(56).getActions().size());
	}
	
	@Test
	public void trainGateGal() throws Exception{
		System trainGate = galParser.parse(readFileAsString(files[index]));
		
		assertEquals(0, trainGate.getArrays().size());
		assertEquals(18, trainGate.getVariables().size());
		assertEquals(51, trainGate.getTransitions().size());
		assertEquals(1, trainGate.getLists().size());
		
		assertEquals("train_gate.5.dve", trainGate.getName());
		
		for(int i=0; i < 51; i++)
			assertEquals("t"+i, trainGate.getTransitions().get(i).getName());
		
		assertTrue(5 == trainGate.getTransitions().get(5).getActions().size());
	}
	
}
