package fr.lip6.move.runtime.environment;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import fr.lip6.move.runtime.interfaces.IGAL;
import fr.lip6.move.runtime.interfaces.IState;
import fr.lip6.move.runtime.interfaces.ITransition;


/**
 * Class to manage strategies, 
 * by offering methods for each strategy, 
 * according to the way we launch Gal programs.
 *
 */
public class GALStrategy {

	
	/**
	 * This method try to cross transitions that comes from a trace.
	 */
	public static void proceedTraceStrategy(IGAL system, String storeFileName)
	{
		Util mode = Util.getInstance() ; 
		ArrayList<Integer> reached = storeFileName == null ? null : new ArrayList<Integer>() ;
		try 
		{
			IState current = system.getInitState();
			while (true) 
			{
				int index = mode.random(0, system.getTransitions().size() - 1,
						"Trying to cross transition number : ");
			
				ITransition trans = system.getTransitions().get(index);

				if (!trans.guard(current) || system.getTransient(current)) 
				{
					System.err.println("### "+ trans.getName() + " IS NOT REACHABLE !\n");
				}
				else 
				{
					System.out.println(trans.getName() + " reached. ");
					System.out.flush();
					if (storeFileName != null) 
					{
						reached.add(system.getTransitions().indexOf(trans));
						saveTrace(storeFileName, reached);
					}
					trans.successor(current);
				}
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			if (storeFileName != null) {
				saveTrace(storeFileName, reached);
			}
		}
	}
	
	/**
	 * Proceed the default launch strategy.
	 * Here, this method calls the strategy of keyboard.
	 */
	public static void proceedDefaultStrategy(IGAL system, String storeFileName)
	{
		Util.setStrategy(Util.Strategy.KEYBOARD);
		proceedKeyboardStrategy(system, storeFileName);
	}
	
	
	
	/**
	 * Interact with the user, asking him to choose transitions
	 * between those which are reachable
	 */
	public static void proceedKeyboardStrategy(IGAL system, String storeFileName)
	{
		Util mode = Util.getInstance() ; 
		ArrayList<Integer> reached = storeFileName == null ? null : new ArrayList<Integer>() ;
		boolean mustStop = false ; 
		try 
		{
			IState current = system.getInitState() ; 
			while (!mustStop) 
			{
				// list of all reachable transition at the current state
				ArrayList<ITransition> reachTrans = new ArrayList<ITransition>();
				String why = "(" ; 
				
				for (ITransition t : system.getTransitions()) 
				{
					if (t.guard(current) && !system.getTransient(current)) {
						why += system.getTransitions().indexOf(t) + ":" + t.getName() + ", " ;
						reachTrans.add(t);
					}
				}
				why += ")" ;
				why = why.replace(", )", ")");
				
				
				if (reachTrans.size() == 0) {
					// no more reachable transitions
					System.out.println("end");
					mustStop = true;
				} 
				else 
				{
					int choosen = mode.random(0, system.getTransitions().size()-1, why);
					if(!reachTrans.contains(system.getTransitions().get(choosen)))
					{
						System.err.println("Not reachable transition! ") ;
						continue ; 
					}
					
					ITransition trans = system.getTransitions().get(choosen);
					
					
					if(storeFileName != null)
					{
						reached.add(system.getTransitions().indexOf(trans)) ;
						saveTrace(storeFileName, reached) ;
					}
					trans.successor(current) ; 
				}
		
			}
		}
		catch(Exception e)
		{
			System.err.println("Error: " + e.getMessage()) ;
			e.printStackTrace();
			if(storeFileName != null)
			{
				saveTrace(storeFileName, reached) ;
			}	
		}
	}
	
	
	/**
	 * Choose a random transition to cross, between all reachables in the system.
	 */
	public static void proceedRandomStrategy(IGAL system, String storeFileName)
	{
		Util mode = Util.getInstance() ; 
		ArrayList<Integer> reached = storeFileName == null ? null : new ArrayList<Integer>() ;
		boolean mustStop = false ; 
		try 
		{
			IState current = system.getInitState() ; 
			while (!mustStop) 
			{
				// list of all reachable transition at the current state
				ArrayList<ITransition> reachTrans = new ArrayList<ITransition>();
				for (ITransition t : system.getTransitions()) {
					if (t.guard(current) && !system.getTransient(current)) {
						reachTrans.add(t);
					}
				}
				
				if (reachTrans.size() == 0) {
					// no more reachable transitions
					System.out.println("end");
					mustStop = true;
				} 
				else 
				{
					int choosen = mode.random(0,reachTrans.size()-1, "Randomly choosen transition ");
					
					
					ITransition trans = reachTrans.get(choosen);
					
					if(storeFileName != null)
					{
						reached.add(system.getTransitions().indexOf(trans)) ;
						saveTrace(storeFileName, reached) ;
					}
					trans.successor(current) ; 
				}
		
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage()) ;
			if(storeFileName != null)
			{
				saveTrace(storeFileName, reached) ;
			}	
		}
	}
	
	
	private static void saveTrace(String storeFileName,
			Iterable<Integer> reached) {
		try 
		{
			FileWriter fw = new FileWriter(new File(storeFileName)) ;
			for(Integer transIndex : reached)
			{
				fw.write(transIndex + "\n") ; 
			}
			fw.close() ; 
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		

	}
}
