package fr.utbm.drone.environment.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OcTreeIterator implements Iterator<OcNode> {
	private OcTree tree;
	private List<OcNode> stack = new ArrayList<>();	

	public OcTreeIterator(OcTree tree) {
		super();
		this.tree = tree;
		this.stack.add(tree.getRoot());
	}

	@Override
	public boolean hasNext() {
		return !this.stack.isEmpty();
	}

	@Override
	public OcNode next() {
		if(!this.hasNext()) return null;
		OcNode cur = stack.remove(0);
		for(OcNode n : cur.getChildren()){
			stack.add(n);
		}
		//if(cur.hasExtraNode()) stack.add(cur.getExtraNode());
		return cur;
	}
}
