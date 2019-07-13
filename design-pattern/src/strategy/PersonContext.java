package strategy;

import strategy.abs.TravelStrategy;

/**
 * 环境类(Context)
 * 
 * @author think
 *
 */
public class PersonContext {

	// 拥有一个出行策略引用
	private TravelStrategy strategy;

	public PersonContext(TravelStrategy strategy) {
		this.strategy = strategy;
	}

	public void setStrategy(TravelStrategy strategy) {
		this.strategy = strategy;
	}

	public void travel() {
		// 根据具体策略类，执行对应的出行策略
		strategy.travelWay();
	}
}
