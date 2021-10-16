/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.apache.ibatis.ibator.formatter.model;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.ibator.api.FullyQualifiedTable;
import org.apache.ibatis.ibator.api.IntrospectedColumn;
import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.config.IbatorContext;
import org.apache.ibatis.ibator.util.ClassNameUtils;

/**
 * @author liuzh
 * @since 3.4.5
 */
public class TableColumnBuilder {

	/**
	 * 创建 TableClass
	 *
	 * @param introspectedTable
	 * @return
	 */
	public static TableClass build(IntrospectedTable introspectedTable) {
		TableClass tableClass = new TableClass();

		tableClass.setHasBLOBColumns(introspectedTable.hasBLOBColumns());
		tableClass.setHasPrimaryKeyColumns(introspectedTable.hasPrimaryKeyColumns());

		IbatorContext ibatorContext = introspectedTable.getAllColumns().get(0).getIbatorContext();
		tableClass.setDaoPackage(ibatorContext.getDaoGeneratorConfiguration().getTargetPackage());
		tableClass.setJavaModelPackage(ibatorContext.getJavaModelGeneratorConfiguration().getTargetPackage());

		tableClass.setGeneratePrimaryKeyClass(introspectedTable.getRules().generatePrimaryKeyClass());
		FullyQualifiedJavaType primarykeyType = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
		tableClass.setPrimaryKeyClassName(primarykeyType.getShortName());
		tableClass.setPrimaryKeyVariableName(Introspector.decapitalize(primarykeyType.getShortName()));
		tableClass.setPrimaryKeyFullClassName(primarykeyType.getFullyQualifiedName());

		tableClass.setExampleType(introspectedTable.getExampleType());
		FullyQualifiedJavaType javaType = new FullyQualifiedJavaType(introspectedTable.getExampleType());
		tableClass.setExampleName(javaType.getShortName());

		FullyQualifiedTable fullyQualifiedTable = introspectedTable.getFullyQualifiedTable();
		tableClass.setTableName(fullyQualifiedTable.getIntrospectedTableName());

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		tableClass.setVariableName(ClassNameUtils.captureName(type.getShortName()));
		tableClass.setLowerCaseName(type.getShortName().toLowerCase());
		tableClass.setShortClassName(type.getShortName());
		tableClass.setFullClassName(type.getFullyQualifiedName());
		tableClass.setPackageName(type.getPackageName());

		List<ColumnField> pkFields = new ArrayList<ColumnField>();
		List<ColumnField> baseFields = new ArrayList<ColumnField>();
		List<ColumnField> blobFields = new ArrayList<ColumnField>();
		List<ColumnField> allFields = new ArrayList<ColumnField>();
		for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
			ColumnField field = build(column);
			field.setTableClass(tableClass);
			pkFields.add(field);
			allFields.add(field);
		}
		for (IntrospectedColumn column : introspectedTable.getBaseColumns()) {
			ColumnField field = build(column);
			field.setTableClass(tableClass);
			baseFields.add(field);
			allFields.add(field);
		}
		for (IntrospectedColumn column : introspectedTable.getBLOBColumns()) {
			ColumnField field = build(column);
			field.setTableClass(tableClass);
			blobFields.add(field);
			allFields.add(field);
		}
		tableClass.setPkFields(pkFields);
		tableClass.setBaseFields(baseFields);
		tableClass.setBlobFields(blobFields);
		tableClass.setAllFields(allFields);

		return tableClass;
	}

	/**
	 * 创建 ColumnField
	 *
	 * @param column
	 * @return
	 */
	public static ColumnField build(IntrospectedColumn column) {
		ColumnField field = new ColumnField();
		field.setColumnName(column.getActualColumnName());
		field.setJdbcType(column.getJdbcTypeName());
		field.setFieldName(column.getJavaProperty());
		field.setRemarks(column.getRemarks());
		FullyQualifiedJavaType type = column.getFullyQualifiedJavaType();
		field.setTypePackage(type.getPackageName());
		field.setShortTypeName(type.getShortName());
		field.setFullTypeName(type.getFullyQualifiedName());
		field.setIdentity(column.isIdentity());
		field.setNullable(column.isNullable());
		field.setBlobColumn(column.isBLOBColumn());
		field.setStringColumn(column.isStringColumn());
		field.setJdbcCharacterColumn(column.isJdbcCharacterColumn());
		field.setJdbcDateColumn(column.isJDBCDateColumn());
		field.setJdbcTimeColumn(column.isJDBCTimeColumn());
		field.setLength(column.getLength());
		field.setScale(column.getScale());
		return field;
	}

}
