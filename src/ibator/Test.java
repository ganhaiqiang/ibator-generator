package ibator;

import java.util.Date;

import org.apache.ibatis.ibator.util.ClassNameUtils;

public class Test {

	public static void main(String[] args) {
		
		System.out.println(ClassNameUtils.captureName("Student"));
	}
}
