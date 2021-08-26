package ibator;

import java.util.Date;

/**
 *
 *
 * @author QQ:34847009
 * @date 2010-11-4 下午03:02:26
 */
public class Test {

	public static void main(String[] args) {
		Man man = new Man();
		man.setHeight(112.33);
		man.setSex("0");
		User user = new User();
		user.setMan(man);
		user.setAge(null);
		user.setCard(123232);
		user.setId(9l);
		user.setBrithday(new Date());
		user.setUsername("=");
		user.setPre(23.4);
		user.setMoney(11111);
		user.setPassword("****");
		System.out.println(user);
		System.out.println(user.toString());
	}
}
