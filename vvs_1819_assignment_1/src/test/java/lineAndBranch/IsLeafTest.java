package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class IsLeafTest {

	@Test
	public void testLeaf () {
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(0);
		boolean result = tree.isLeaf();
		assertEquals(result,false,"Falhou como folha");
		
	}

 //colocar no relatório que é uma condição impossível.

}
