package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class toStringTest {

	@Test
	public void TestEmptyString() {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(0);
		String result = tree.toString();
		assertEquals(result,"[]","não imprime correctamente");
	}
	
	@Test 
	public void TestLeafTreePrint() {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		String result = tree.toString();
		assertEquals(result.equals("[1]"),true,"não imprime correctamente");
		
	}
}
