package com.harium.propan.octree;


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.harium.propan.core.model.Face;
import com.harium.propan.linear.BoundingBox3D;
import com.harium.propan.storage.octree.Octree;
import com.harium.propan.storage.octree.OctreeNode;
import com.harium.propan.storage.octree.VolumeOctree;
import com.harium.etyl.linear.Point3D;

public class VolumeOctreeTest {

	private VolumeOctree<Face> tree;
	
	@Before
	public void setUp() {
		
		Point3D minPoint = new Point3D(0, 0, 0);
		Point3D maxPoint = new Point3D(10, 10, 10);
		
		BoundingBox3D box = new BoundingBox3D(minPoint, maxPoint);
		
		tree = new VolumeOctree<Face>(box);
	}
	
	@Test
	public void testInit() {
		OctreeNode<Face> root = tree.getRoot();
		
		Assert.assertNotNull(root);
		//Hash size allocated is 8
		//Hash is empty
		Assert.assertEquals(0, root.getChildrenNodes().size());
	}
	
	@Test
	public void testAddPoint() {
		tree.add(new Point3D(1, 1), null);
		
		OctreeNode<Face> root = tree.getRoot();
		
		List<OctreeNode<Face>> children = new ArrayList<OctreeNode<Face>>(root.getChildrenNodes());
		
		OctreeNode<Face> leftLower = children.get(Octree.BELOW_LEFT_LOWER);
		List<OctreeNode<Face>> leftLowerChildren = new ArrayList<OctreeNode<Face>>(leftLower.getChildrenNodes());
		
		Assert.assertNotNull(leftLower);
				
		BoundingBox3D innerBox = leftLower.getBox();
		Assert.assertEquals(15.625, innerBox.getVolume(), 0.1);
		
		Assert.assertNotNull(leftLowerChildren.get(Octree.BELOW_LEFT_LOWER));
	}
	
}
