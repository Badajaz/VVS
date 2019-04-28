package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class InfoTest {

	@Test
	public void TestInfo() {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		String result = tree.info();
		String try1 = tree.toString()+", size: " + tree.size() + ", height: " + tree.height() + ", nLeaves: " + tree.countLeaves();
		assertTrue(result.equals(try1),"não imprime correctamente a informaçao");
	}
	
	
}
