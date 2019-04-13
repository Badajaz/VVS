package lineAndBranch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class CloneTest {
	
	@Test
	public void testClone () {
		List<Integer> lista = new ArrayList <> ();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(5);
		ArrayNTree<Integer> tree = new ArrayNTree<Integer>(lista,5);
		
		ArrayNTree<Integer> result = tree.clone();
		List<Integer> list1 = tree.toList();
		boolean check = true;
			for(int i=1; i<lista.size(); i++){
				if (lista.get(i) != list1.get(i)) {
					check = false;
				}
			}
		assertEquals((list1.size() != lista.size()),false, "tamanhos diferentes");
		assertEquals(check,true, "os elementos sao iguais");
		assertEquals(result.equals(tree),true,"as árvores sao iguais");
	}	
}
