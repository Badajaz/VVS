package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class MinTest {

	@Test
	public void testIfMin () {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		int result = tree.min();
		assertEquals(result,1,"minimo errado");

	}
}
