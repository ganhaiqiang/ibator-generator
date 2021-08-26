package ibator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.ibator.api.Ibator;
import org.apache.ibatis.ibator.config.IbatorConfiguration;
import org.apache.ibatis.ibator.config.xml.IbatorConfigurationParser;
import org.apache.ibatis.ibator.internal.DefaultShellCallback;

public class IbatorRunTest {

	public static void main(String... strings) {
		try {
			List<String> warnings = new ArrayList<String>();
			boolean overwrite = true;
			File configFile = new File(ClassLoader.getSystemResource("ConfigIbatisExample.xml").getFile());
			IbatorConfigurationParser cp = new IbatorConfigurationParser(warnings);
			IbatorConfiguration config = cp.parseIbatorConfiguration(new File("D:/Program Files/eclipse/eclipse-workspace/ibator_cx/config/ConfigIbatisExample.xml"));
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			Ibator ibator = new Ibator(config, callback, warnings);
			ibator.generate(null);
			for (String warning : warnings) {
				System.out.println("warning:" + warning);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
