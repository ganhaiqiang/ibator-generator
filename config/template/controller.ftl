package ${package};

import demo.service.${tableClass.shortClassName}Service;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 通用 Controller 代码生成器
 *
 * @author mapper-generator
 */
@Controller
public class ${tableClass.shortClassName}${mapperSuffix} {

	@Autowired
	private ${tableClass.shortClassName}Service ${tableClass.variableName}Service;

	@RequestMapping("demo")
	@ResponseBody
	public Object demo() {
		
	}
}




