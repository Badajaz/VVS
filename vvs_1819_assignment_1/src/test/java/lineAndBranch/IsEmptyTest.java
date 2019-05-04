package lineAndBranch;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;


import sut.ArrayNTree;



public class IsEmptyTest {

	@Test
	public void testEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(0);
		assertTrue(tree.isEmpty(),"Nao esta vazia");
	}
	
	
	
}
