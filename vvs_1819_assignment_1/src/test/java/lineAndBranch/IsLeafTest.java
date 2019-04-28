package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class IsLeafTest {

	@Test
	public void testLeaf () {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(0);
		assertFalse(tree.isLeaf(),"Falhou como folha");
		
	}

 //colocar no relatório que é uma condição impossível.

}
