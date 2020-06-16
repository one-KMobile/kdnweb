import org.junit.internal.matchers.SubstringMatcher;


public class Test {
	public static void main(String args[]) {
		String str = "1234a" ;
		str.length();
		System.out.println(str.subSequence(str.length()-1, str.length()));
		
	}
}
