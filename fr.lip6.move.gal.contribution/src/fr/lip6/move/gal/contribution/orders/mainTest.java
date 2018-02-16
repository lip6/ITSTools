
public class mainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			myCode.script();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(String s : myCode.order())
			System.out.println(s);
		
	}

}
