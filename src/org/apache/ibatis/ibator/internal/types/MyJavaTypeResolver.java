package org.apache.ibatis.ibator.internal.types;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.ibator.api.IntrospectedColumn;
import org.apache.ibatis.ibator.api.JavaTypeResolver;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.config.IbatorContext;
import org.apache.ibatis.ibator.config.PropertyRegistry;
import org.apache.ibatis.ibator.internal.util.StringUtility;

/**
 * 自定义的类型解析器
 * 
 * @author Administrator
 *
 */
public class MyJavaTypeResolver implements JavaTypeResolver {
	protected List<String> warnings;

	protected Properties properties;

	protected IbatorContext ibatorContext;

	protected boolean forceBigDecimals;

	protected Map<Integer, JdbcTypeInformation> typeMap;

	public MyJavaTypeResolver() {
		super();
		properties = new Properties();
		typeMap = new HashMap<Integer, JdbcTypeInformation>();

		typeMap.put(Types.ARRAY, new JdbcTypeInformation("ARRAY", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.BIGINT, new JdbcTypeInformation("BIGINT", new FullyQualifiedJavaType(Long.class.getName())));
		typeMap.put(Types.BINARY, new JdbcTypeInformation("BINARY", new FullyQualifiedJavaType("byte[]")));
		typeMap.put(Types.BIT, new JdbcTypeInformation("BIT", new FullyQualifiedJavaType(Boolean.class.getName())));
		typeMap.put(Types.BLOB, new JdbcTypeInformation("BLOB", new FullyQualifiedJavaType("byte[]")));
		typeMap.put(Types.BOOLEAN, new JdbcTypeInformation("BOOLEAN", new FullyQualifiedJavaType(Boolean.class.getName())));
		typeMap.put(Types.CHAR, new JdbcTypeInformation("CHAR", new FullyQualifiedJavaType(String.class.getName())));
		typeMap.put(Types.CLOB, new JdbcTypeInformation("CLOB", new FullyQualifiedJavaType(String.class.getName())));
		typeMap.put(Types.DATALINK, new JdbcTypeInformation("DATALINK", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.DATE, new JdbcTypeInformation("DATE", new FullyQualifiedJavaType(Date.class.getName())));
		typeMap.put(Types.DISTINCT, new JdbcTypeInformation("DISTINCT", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.DOUBLE, new JdbcTypeInformation("DOUBLE", new FullyQualifiedJavaType(Double.class.getName())));
		typeMap.put(Types.FLOAT, new JdbcTypeInformation("FLOAT", new FullyQualifiedJavaType(Double.class.getName())));
		typeMap.put(Types.INTEGER, new JdbcTypeInformation("INTEGER", new FullyQualifiedJavaType(Integer.class.getName())));
		typeMap.put(Types.JAVA_OBJECT, new JdbcTypeInformation("JAVA_OBJECT", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.LONGVARBINARY, new JdbcTypeInformation("LONGVARBINARY", new FullyQualifiedJavaType("byte[]")));
		typeMap.put(Types.LONGVARCHAR, new JdbcTypeInformation("LONGVARCHAR", new FullyQualifiedJavaType(String.class.getName())));
		typeMap.put(Types.NULL, new JdbcTypeInformation("NULL", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.OTHER, new JdbcTypeInformation("OTHER", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.REAL, new JdbcTypeInformation("REAL", new FullyQualifiedJavaType(Float.class.getName())));
		typeMap.put(Types.REF, new JdbcTypeInformation("REF", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.SMALLINT, new JdbcTypeInformation("SMALLINT", new FullyQualifiedJavaType(Integer.class.getName())));
		typeMap.put(Types.STRUCT, new JdbcTypeInformation("STRUCT", new FullyQualifiedJavaType(Object.class.getName())));
		typeMap.put(Types.TIME, new JdbcTypeInformation("TIME", new FullyQualifiedJavaType(Date.class.getName())));
		typeMap.put(Types.TIMESTAMP, new JdbcTypeInformation("TIMESTAMP", new FullyQualifiedJavaType(Date.class.getName())));
		typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Byte.class.getName())));
		typeMap.put(Types.VARBINARY, new JdbcTypeInformation("VARBINARY", new FullyQualifiedJavaType("byte[]")));
		typeMap.put(Types.VARCHAR, new JdbcTypeInformation("VARCHAR", new FullyQualifiedJavaType(String.class.getName())));
	}

	public void addConfigurationProperties(Properties properties) {
		this.properties.putAll(properties);
		forceBigDecimals = StringUtility.isTrue(properties.getProperty(PropertyRegistry.TYPE_RESOLVER_FORCE_BIG_DECIMALS));
	}

	public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
		FullyQualifiedJavaType answer;
		JdbcTypeInformation jdbcTypeInformation = typeMap.get(introspectedColumn.getJdbcType());

		if (jdbcTypeInformation == null) {
			switch (introspectedColumn.getJdbcType()) {
			case Types.DECIMAL:
			case Types.NUMERIC:
				if (introspectedColumn.getScale() == 0 && introspectedColumn.getLength() == 22) {
					answer = new FullyQualifiedJavaType(Integer.class.getName());
				} else if (introspectedColumn.getScale() > 0 || introspectedColumn.getLength() > 18 || forceBigDecimals) {
					answer = new FullyQualifiedJavaType(BigDecimal.class.getName());
				} else if (introspectedColumn.getLength() > 9) {
					answer = new FullyQualifiedJavaType(Long.class.getName());
				} else if (introspectedColumn.getLength() > 4) {
					answer = new FullyQualifiedJavaType(Integer.class.getName());
				} else {
					answer = new FullyQualifiedJavaType(Integer.class.getName());
				}

				break;

			default:
				answer = null;
				break;
			}
		} else {
			answer = jdbcTypeInformation.getFullyQualifiedJavaType();
		}

		return answer;
	}

	public String calculateJdbcTypeName(IntrospectedColumn introspectedColumn) {
		String answer;
		JdbcTypeInformation jdbcTypeInformation = typeMap.get(introspectedColumn.getJdbcType());

		if (jdbcTypeInformation == null) {
			switch (introspectedColumn.getJdbcType()) {
			case Types.DECIMAL:
				answer = "DECIMAL";
				break;
			case Types.NUMERIC:
				answer = "NUMERIC";
				break;
			default:
				answer = null;
				break;
			}
		} else {
			answer = jdbcTypeInformation.getJdbcTypeName();
		}

		return answer;
	}

	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	public void setIbatorContext(IbatorContext ibatorContext) {
		this.ibatorContext = ibatorContext;
	}

	private static class JdbcTypeInformation {
		private String jdbcTypeName;

		private FullyQualifiedJavaType fullyQualifiedJavaType;

		public JdbcTypeInformation(String jdbcTypeName, FullyQualifiedJavaType fullyQualifiedJavaType) {
			this.jdbcTypeName = jdbcTypeName;
			this.fullyQualifiedJavaType = fullyQualifiedJavaType;
		}

		public String getJdbcTypeName() {
			return jdbcTypeName;
		}

		public FullyQualifiedJavaType getFullyQualifiedJavaType() {
			return fullyQualifiedJavaType;
		}
	}
}
