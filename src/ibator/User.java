package ibator;

import java.util.Date;

/**
 * 
 * 
 * @author QQ:34847009
 * @date 2010-11-4 下午03:01:10
 */
public class User extends BaseBean {

	private String username;
	private String password;
	private Long age;
	private Double pre;
	private Integer card;
	private int money;
	private long id;
	private Man man;
	private Date brithday;

	public Date getBrithday() {
		return brithday;
	}

	public void setBrithday(Date brithday) {
		this.brithday = brithday;
	}

	public Man getMan() {
		return man;
	}

	public void setMan(Man man) {
		this.man = man;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public Double getPre() {
		return pre;
	}

	public void setPre(Double pre) {
		this.pre = pre;
	}

	public Integer getCard() {
		return card;
	}

	public void setCard(Integer card) {
		this.card = card;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
