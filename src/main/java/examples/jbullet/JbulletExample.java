package examples.jbullet;

import com.harium.propan.Propan;
import com.harium.propan.core.context.ApplicationGL;
import examples.jbullet.vehicle.VehicleDemo;

public class JbulletExample extends Propan {

	public JbulletExample() {
		super(800,600);
	}

	// Main program
	public static void main(String[] args) {
		JbulletExample demo = new JbulletExample();
		demo.init();
	}
	
	@Override
	public ApplicationGL startApplication() {
		initialSetup("../../../");
		
		//return new BasicDemo(w, h);
		//return new BspDemo(w, h);
		//return new CharacterDemo(w, h);
		//return new ConcaveDemo(w, h);
		//return new DynamicControlDemo(w, h);
		//return new ForkLiftDemo(w, h);
		//return new GenericJointDemo(w, h);
		//return new MovingConcaveDemo(w, h);
		return new VehicleDemo(w, h);
	}
}
