package com.turpgames.physics.box2d.builders;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.World;

@SuppressWarnings("unchecked")
public abstract class JointBuilder<TBuilder extends JointBuilder<TBuilder, TDef, TJoint>, TDef extends JointDef, TJoint extends Joint> {
	protected final TDef jointDef;

	protected JointBuilder(TDef jointDef) {
		this.jointDef = jointDef;
	}
	
	protected void reset() {
		jointDef.bodyA = null;
		jointDef.bodyB = null;
		jointDef.collideConnected = false;
	}
	
	public TBuilder setBodyA(Body bodyA) {
		jointDef.bodyA = bodyA;
		return (TBuilder)this;
	}

	public TBuilder setBodyB(Body bodyB) {
		jointDef.bodyB = bodyB;
		return (TBuilder)this;
	}

	public TBuilder setCollideConnected(boolean collideConnected) {
		jointDef.collideConnected = collideConnected;
		return (TBuilder)this;
	}
	
	public TJoint build(World world) {
		return (TJoint)world.createJoint(jointDef);
	}
}