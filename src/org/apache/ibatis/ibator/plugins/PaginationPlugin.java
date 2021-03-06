package org.apache.ibatis.ibator.plugins;

import java.util.List;

import org.apache.ibatis.ibator.api.IbatorPluginAdapter;
import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.apache.ibatis.ibator.api.dom.xml.Attribute;
import org.apache.ibatis.ibator.api.dom.xml.Document;
import org.apache.ibatis.ibator.api.dom.xml.XmlElement;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmap.elements.AbstractXmlElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmap.elements.MysqlPaginationLimitGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmap.elements.OraclePaginationHeadGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.sqlmap.elements.OraclePaginationTailGenerator;
import org.apache.ibatis.ibator.internal.util.StringUtility;

/**
 * 数据库分页插件
 * <p>
 * 
 * @param boolean enablePagination
 * @param String  databaseType :(mysql or oracle)
 * @author QQ:34847009
 * @date 2010-10-21 下午09:33:48
 */
public class PaginationPlugin extends IbatorPluginAdapter {
	/** 是否分页 */
	private String enablePagination;
	/** 数据库类型 */
	private String databaseType;

	@Override
	public boolean validate(List<String> warnings) {
		databaseType = properties.getProperty("databaseType");
		enablePagination = properties.getProperty("enablePagination");

		boolean valid = StringUtility.stringHasValue(databaseType) && StringUtility.stringHasValue(enablePagination);

		return valid;
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		XmlElement element = document.getRootElement();
		if (StringUtility.stringHasValue(enablePagination) && "true".equalsIgnoreCase(enablePagination)) {
			if ("oracle".equalsIgnoreCase(databaseType)) {
				// 增加手写的头sql
				AbstractXmlElementGenerator oraclePaginationTailGenerator = new OraclePaginationTailGenerator();
				oraclePaginationTailGenerator.setIbatorContext(ibatorContext);
				oraclePaginationTailGenerator.setIntrospectedTable(introspectedTable);
				oraclePaginationTailGenerator.addElements(element);

				// 增加手写的尾sql
				AbstractXmlElementGenerator oraclePaginationHeadGenerator = new OraclePaginationHeadGenerator();
				oraclePaginationHeadGenerator.setIbatorContext(ibatorContext);
				oraclePaginationHeadGenerator.setIntrospectedTable(introspectedTable);
				oraclePaginationHeadGenerator.addElements(element);
			} else if ("mysql".equalsIgnoreCase(databaseType)) {
				// 增加mysql
				AbstractXmlElementGenerator mysqlPaginationLimitGenerator = new MysqlPaginationLimitGenerator();
				mysqlPaginationLimitGenerator.setIbatorContext(ibatorContext);
				mysqlPaginationLimitGenerator.setIntrospectedTable(introspectedTable);
				mysqlPaginationLimitGenerator.addElements(element);
			}
		}

		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	@Override
	public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		if (StringUtility.stringHasValue(enablePagination) && "true".equalsIgnoreCase(enablePagination)) {
			if ("oracle".equalsIgnoreCase(databaseType)) {
				XmlElement oracleHeadIncludeElement = new XmlElement("include");
				oracleHeadIncludeElement.addAttribute(new Attribute("refid", introspectedTable.getIbatis2SqlMapNamespace() + "." + "Pagination_Head"));
				// 在第几个地方增加
				element.addElement(0, oracleHeadIncludeElement);

				XmlElement oracleTailIncludeElement = new XmlElement("include");
				oracleTailIncludeElement.addAttribute(new Attribute("refid", introspectedTable.getIbatis2SqlMapNamespace() + "." + "Pagination_Tail"));
				// 在最后增加
				element.addElement(element.getElements().size(), oracleTailIncludeElement);
			} else if ("mysql".equalsIgnoreCase(databaseType)) {
				XmlElement mysqlLimitIncludeElement = new XmlElement("include");
				mysqlLimitIncludeElement.addAttribute(new Attribute("refid", introspectedTable.getIbatis2SqlMapNamespace() + "." + "Pagination_Limit"));
				// 在最后增加
				element.addElement(element.getElements().size(), mysqlLimitIncludeElement);
			}
		}

		return super.sqlMapExampleWhereClauseElementGenerated(element, introspectedTable);
	}

	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		if (StringUtility.stringHasValue(enablePagination) && "true".equalsIgnoreCase(enablePagination)) {
			if ("oracle".equalsIgnoreCase(databaseType)) {
				XmlElement oracleHeadIncludeElement = new XmlElement("include");
				oracleHeadIncludeElement.addAttribute(new Attribute("refid", introspectedTable.getIbatis2SqlMapNamespace() + "." + "Pagination_Head"));
				// 在第一个地方增加
				element.addElement(0, oracleHeadIncludeElement);

				XmlElement oracleTailIncludeElement = new XmlElement("include");
				oracleTailIncludeElement.addAttribute(new Attribute("refid", introspectedTable.getIbatis2SqlMapNamespace() + "." + "Pagination_Tail"));
				// 在最后增加
				element.addElement(element.getElements().size(), oracleTailIncludeElement);
			} else if ("mysql".equalsIgnoreCase(databaseType)) {
				XmlElement mysqlLimitIncludeElement = new XmlElement("include");
				mysqlLimitIncludeElement.addAttribute(new Attribute("refid", introspectedTable.getIbatis2SqlMapNamespace() + "." + "Pagination_Limit"));
				// 在最后增加
				element.addElement(element.getElements().size(), mysqlLimitIncludeElement);
			}
		}

		return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
	}

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		if (StringUtility.stringHasValue(enablePagination) && "true".equalsIgnoreCase(enablePagination)) {
			if ("oracle".equalsIgnoreCase(databaseType)) {
				// 增加开始处
				// 增加firstResult、lastResult、mysqlOffset、mysqlLength
				// add field, getter, setter for firstResult clause
				Field field = new Field();
				field.setVisibility(JavaVisibility.PRIVATE);
				field.setType(FullyQualifiedJavaType.getInteger());
				field.setName("firstResult"); //$NON-NLS-1$
				topLevelClass.addField(field);

				Method method = new Method();
				method.setVisibility(JavaVisibility.PUBLIC);
				method.setName("setFirstResult"); //$NON-NLS-1$
				method.addParameter(new Parameter(FullyQualifiedJavaType.getInteger(), "firstResult")); //$NON-NLS-1$
				method.addBodyLine("this.firstResult = firstResult;"); //$NON-NLS-1$
				topLevelClass.addMethod(method);

				method = new Method();
				method.setVisibility(JavaVisibility.PUBLIC);
				method.setReturnType(FullyQualifiedJavaType.getInteger());
				method.setName("getFirstResult"); //$NON-NLS-1$
				method.addBodyLine("return firstResult;"); //$NON-NLS-1$
				topLevelClass.addMethod(method);
				// add field, getter, setter for lastResult clause
				field = new Field();
				field.setVisibility(JavaVisibility.PRIVATE);
				field.setType(FullyQualifiedJavaType.getInteger());
				field.setName("lastResult"); //$NON-NLS-1$
				topLevelClass.addField(field);

				method = new Method();
				method.setVisibility(JavaVisibility.PUBLIC);
				method.setName("setLastResult"); //$NON-NLS-1$
				method.addParameter(new Parameter(FullyQualifiedJavaType.getInteger(), "lastResult")); //$NON-NLS-1$
				method.addBodyLine("this.lastResult = lastResult;"); //$NON-NLS-1$
				topLevelClass.addMethod(method);

				method = new Method();
				method.setVisibility(JavaVisibility.PUBLIC);
				method.setReturnType(FullyQualifiedJavaType.getInteger());
				method.setName("getLastResult"); //$NON-NLS-1$
				method.addBodyLine("return lastResult;"); //$NON-NLS-1$
				topLevelClass.addMethod(method);

			} else if ("mysql".equalsIgnoreCase(databaseType)) {
				// add field, getter, setter for mysqlOffset clause
				Field field = new Field();
				field.setVisibility(JavaVisibility.PRIVATE);
				field.setType(FullyQualifiedJavaType.getInteger());
				field.setName("offset"); //$NON-NLS-1$
				topLevelClass.addField(field);

				Method method = new Method();
				method.setVisibility(JavaVisibility.PUBLIC);
				method.setName("setOffset"); //$NON-NLS-1$
				method.addParameter(new Parameter(FullyQualifiedJavaType.getInteger(), "offset")); //$NON-NLS-1$
				method.addBodyLine("this.offset = offset;"); //$NON-NLS-1$
				topLevelClass.addMethod(method);

				method = new Method();
				method.setVisibility(JavaVisibility.PUBLIC);
				method.setReturnType(FullyQualifiedJavaType.getInteger());
				method.setName("getOffset"); //$NON-NLS-1$
				method.addBodyLine("return offset;"); //$NON-NLS-1$
				topLevelClass.addMethod(method);
				// add field, getter, setter for mysqlLength clause
				field = new Field();
				field.setVisibility(JavaVisibility.PRIVATE);
				field.setType(FullyQualifiedJavaType.getInteger());
				field.setName("limit"); //$NON-NLS-1$
				topLevelClass.addField(field);

				method = new Method();
				method.setVisibility(JavaVisibility.PUBLIC);
				method.setName("setLimit"); //$NON-NLS-1$
				method.addParameter(new Parameter(FullyQualifiedJavaType.getInteger(), "limit")); //$NON-NLS-1$
				method.addBodyLine("this.limit = limit;"); //$NON-NLS-1$
				topLevelClass.addMethod(method);

				method = new Method();
				method.setVisibility(JavaVisibility.PUBLIC);
				method.setReturnType(FullyQualifiedJavaType.getInteger());
				method.setName("getLimit"); //$NON-NLS-1$
				method.addBodyLine("return limit;"); //$NON-NLS-1$
				topLevelClass.addMethod(method);
				// 增加结束处
			}
		}

		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

}
