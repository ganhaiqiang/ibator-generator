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

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.ibator.api.FullyQualifiedTable;
import org.apache.ibatis.ibator.api.IntrospectedColumn;

/**
 * @author liuzh
 * @since 3.4.5
 */
public class TableClass implements Serializable {
	private static final long serialVersionUID = -746251813735169289L;

	private IntrospectedColumn introspectedColumn;
	private String tableName;
	private String variableName;
	private String lowerCaseName;
	private String shortClassName;
	private String fullClassName;
	private String packageName;
	private String exampleType;
	private String exampleName;
	private String primaryKeyClassName;
	private String primaryKeyFullClassName;
	private String primaryKeyVariableName;
	private String javaModelPackage;
	private String daoPackage;
	private FullyQualifiedTable type;
	private boolean generatePrimaryKeyClass;
	private boolean hasBLOBColumns;
	private boolean hasPrimaryKeyColumns;

	private List<ColumnField> pkFields;
	private List<ColumnField> baseFields;
	private List<ColumnField> blobFields;
	private List<ColumnField> allFields;

	public List<ColumnField> getAllFields() {
		return allFields;
	}

	public void setAllFields(List<ColumnField> allFields) {
		this.allFields = allFields;
	}

	public List<ColumnField> getBaseFields() {
		return baseFields;
	}

	public void setBaseFields(List<ColumnField> baseFields) {
		this.baseFields = baseFields;
	}

	public List<ColumnField> getBlobFields() {
		return blobFields;
	}

	public void setBlobFields(List<ColumnField> blobFields) {
		this.blobFields = blobFields;
	}

	public String getFullClassName() {
		return fullClassName;
	}

	public void setFullClassName(String fullClassName) {
		this.fullClassName = fullClassName;
	}

	public boolean isHasBLOBColumns() {
		return hasBLOBColumns;
	}

	public void setHasBLOBColumns(boolean hasBLOBColumns) {
		this.hasBLOBColumns = hasBLOBColumns;
	}

	public boolean isHasPrimaryKeyColumns() {
		return hasPrimaryKeyColumns;
	}

	public void setHasPrimaryKeyColumns(boolean hasPrimaryKeyColumns) {
		this.hasPrimaryKeyColumns = hasPrimaryKeyColumns;
	}

	public IntrospectedColumn getIntrospectedColumn() {
		return introspectedColumn;
	}

	public void setIntrospectedColumn(IntrospectedColumn introspectedColumn) {
		this.introspectedColumn = introspectedColumn;
	}

	public String getJavaModelPackage() {
		return javaModelPackage;
	}

	public void setJavaModelPackage(String javaModelPackage) {
		this.javaModelPackage = javaModelPackage;
	}

	public String getDaoPackage() {
		return daoPackage;
	}

	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}

	public String getExampleName() {
		return exampleName;
	}

	public void setExampleName(String exampleName) {
		this.exampleName = exampleName;
	}

	public String getLowerCaseName() {
		return lowerCaseName;
	}

	public void setLowerCaseName(String lowerCaseName) {
		this.lowerCaseName = lowerCaseName;
	}

	public String getPrimaryKeyClassName() {
		return primaryKeyClassName;
	}

	public void setPrimaryKeyClassName(String primaryKeyClassName) {
		this.primaryKeyClassName = primaryKeyClassName;
	}

	public String getPrimaryKeyVariableName() {
		return primaryKeyVariableName;
	}

	public void setPrimaryKeyVariableName(String primaryKeyVariableName) {
		this.primaryKeyVariableName = primaryKeyVariableName;
	}

	public String getPrimaryKeyFullClassName() {
		return primaryKeyFullClassName;
	}

	public void setPrimaryKeyFullClassName(String primaryKeyFullClassName) {
		this.primaryKeyFullClassName = primaryKeyFullClassName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<ColumnField> getPkFields() {
		return pkFields;
	}

	public void setPkFields(List<ColumnField> pkFields) {
		this.pkFields = pkFields;
	}

	public boolean isGeneratePrimaryKeyClass() {
		return generatePrimaryKeyClass;
	}

	public void setGeneratePrimaryKeyClass(boolean generatePrimaryKeyClass) {
		this.generatePrimaryKeyClass = generatePrimaryKeyClass;
	}

	public String getShortClassName() {
		return shortClassName;
	}

	public void setShortClassName(String shortClassName) {
		this.shortClassName = shortClassName;
	}

	public String getExampleType() {
		return exampleType;
	}

	public void setExampleType(String exampleType) {
		this.exampleType = exampleType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public FullyQualifiedTable getType() {
		return type;
	}

	public void setType(FullyQualifiedTable type) {
		this.type = type;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
}
