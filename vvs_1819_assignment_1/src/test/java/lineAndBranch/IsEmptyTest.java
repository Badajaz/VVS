package lineAndBranch;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.ArrayNTree;



public class IsEmptyTest {

	@Test
	public void testEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(0);
		boolean result = tree.isEmpty();
		assertEquals(result,true,"Nao esta vazia");
	}
	
	
	
}
