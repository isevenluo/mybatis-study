package strategy;

import org.junit.Test;

import strategy.abs.TravelStrategy;
import strategy.concrete.AirPlanelStrategy;
import strategy.concrete.BicycleStrategy;
import strategy.concrete.TrainStrategy;

/**
 * 测试类
 * @author think
 *
 */
public class StrategyTest {

	@Test
	public void test() {
		//抽象策略类
		TravelStrategy strategy = null;
		PersonContext person = null;
		
		// 太远了，需要做飞机
		strategy = new AirPlanelStrategy() ;
		person = new PersonContext(strategy);
		person.travel();
		
		// 不太远，飞机太贵，选择火车
		strategy = new TrainStrategy();
		person.setStrategy(strategy);
		person.travel();
		
		// 很近，直接选择自行车
		strategy = new BicycleStrategy();
		person.setStrategy(strategy);
		person.travel();
	}
}
