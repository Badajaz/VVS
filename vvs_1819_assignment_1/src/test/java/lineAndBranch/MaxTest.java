package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


import sut.ArrayNTree;

public class MaxTest {

	@Test
	public void testIfMax () {
		List<Integer> lista = Arrays.asList(1,2,3,4,5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		int result = tree.max();
		assertEquals(result,5,"maximo errado");

	}
	@Test
	public void testEmptyTree () {
		List<Integer> lista = Arrays.asList();
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		int result = tree.max();
		assertEquals(result,5,"maximo errado");

	}
	
	
	
}