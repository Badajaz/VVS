package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class InfoTest {

	@Test
	public void TestInfo() {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		String result = tree.info();
		String try1 = ", size: " + tree.size() + ", height: " + tree.height() + ", nLeaves: " + tree.countLeaves();
		assertEquals(result.equals(try1),true,"não imprime correctamente a informaçao");
	}
	
	
}
