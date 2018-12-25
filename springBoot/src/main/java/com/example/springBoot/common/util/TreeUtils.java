package com.example.springBoot.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.springBoot.common.entity.Tree;


public class TreeUtils {

	protected TreeUtils(){

	}
	
	public static <T> Tree<T> build(List<Tree<T>> nodes) {
		if (nodes == null) {
			return null;
		}
		List<Tree<T>> topNodes = new ArrayList<>();
		for(Tree<T> children : nodes){
			String pid = children.getParentId();
			if (pid == null || "0".equals(pid)) {
				topNodes.add(children);
				continue;
			}
			for (Tree<T> parent : nodes) {
				String id = parent.getId();
				if (id != null && id.equals(pid)) {
					parent.getChildren().add(children);
					children.setHasParent(true);
					parent.setChildren(true);
					continue;
				}
			}
		}

		Tree<T> root = new Tree<>();
		root.setId("0");
		root.setParentId("");
		root.setHasParent(false);
		root.setChildren(true);
		root.setChecked(true);
		root.setChildren(topNodes);
		root.setText("根节点");
		Map<String, Object> state = new HashMap<>(16);
		state.put("opened", true);
		root.setState(state);
		return root;
	}
}