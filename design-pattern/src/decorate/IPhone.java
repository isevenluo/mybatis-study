package decorate;
/**
 * 目标类
 * @author think
 *
 */
public class IPhone implements PhoneInterface {
	@Override
	public void call() {
		System.out.println("使用苹果手机打电话");
	}

}
