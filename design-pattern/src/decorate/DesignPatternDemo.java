package decorate;

public class DesignPatternDemo {

	public static void main(String[] args) {
		PhoneInterface phone = new IPhone();
		phone.call();
		
		System.out.println("===========");
		
		//Phone phone2 = new PhoneDecorate(new IPhone());
		PhoneInterface phone2 = new IPhoneDecorate(phone);
		phone2.call();
		
	}

}
