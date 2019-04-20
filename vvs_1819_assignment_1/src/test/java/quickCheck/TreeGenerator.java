package quickCheck;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import sut.ArrayNTree;

public class TreeGenerator extends Generator <ArrayNTree<Integer>> {
	
	
	private int min;
	private int max;
	
	
	private static final int MAX_ELEM = 1000;
	private static final int MIN_ELEM = -MAX_ELEM;
	
	private static final int CAPACITY = 5;
	
	public TreeGenerator(Class<ArrayNTree<Integer>> type)  {
		super(type);
	}
	
	
	public void configure(InRange range) {
		min = range.minInt();
		max = range.maxInt();
	}

	@Override
	public ArrayNTree<Integer> generate(SourceOfRandomness random, GenerationStatus status) {
		
		int sizeTree = random.nextInt(min,max);
		
		ArrayNTree<Integer> tree = new ArrayNTree<>(CAPACITY);
		
		while(sizeTree-- > 0) {
			tree.insert(random.nextInt(MIN_ELEM,MAX_ELEM));
		}
		
		
		return tree;
	}
	
	
	
	
	
}
