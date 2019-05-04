package quickCheck;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import sut.ArrayNTree;

public class TreeGenerator extends Generator <ArrayNTree<Integer>> {




	private static final int MAX_ELEM = 1000;
	private static final int MIN_ELEM = -MAX_ELEM;
	private static final int NUM = 50;
	private static final int CAPACITY = 5;

	public TreeGenerator(Class<ArrayNTree<Integer>> type)  {
		super(type);
	}


	@Override
	public ArrayNTree<Integer> generate(SourceOfRandomness random, GenerationStatus status) {

		int sizeTree = random.nextInt(NUM);

		ArrayNTree<Integer> tree = new ArrayNTree<>(CAPACITY);

		while(sizeTree > 0) {
			tree.insert(random.nextInt(MIN_ELEM,MAX_ELEM));
			sizeTree--;
		}


		return tree;
	}





}
