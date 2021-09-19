/*
 *  Copyright 2008 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.ibatis.ibator.generator.ibatis2.dao.elements;

import java.util.Set;
import java.util.TreeSet;

import org.apache.ibatis.ibator.api.IntrospectedColumn;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.Interface;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.apache.ibatis.ibator.api.dom.java.TopLevelServiceClass;
import org.apache.ibatis.ibator.util.ClassNameUtils;

/**
 * 
 * @author Jeff Butler
 *
 */
public class ServiceInsertSelectiveMethodGenerator extends AbstractDAOElementGenerator {

	public ServiceInsertSelectiveMethodGenerator() {
		super();
	}

	@Override
	public void addImplementationElements(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = getMethodShell(importedTypes);

		TopLevelServiceClass topLevelServiceClass = (TopLevelServiceClass) topLevelClass;
		FullyQualifiedJavaType daoType = topLevelServiceClass.getDaoType();
		String daoName = ClassNameUtils.captureName(daoType.getShortName());
		method.addBodyLine(daoName + ".insertSelective(" + method.getParameters().get(0).getName() + ");");

		if (ibatorContext.getPlugins().daoInsertSelectiveMethodGenerated(method, topLevelClass, introspectedTable)) {
			topLevelClass.addImportedTypes(importedTypes);
			topLevelClass.addMethod(method);
		}
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = getMethodShell(importedTypes);

		if (ibatorContext.getPlugins().daoInsertSelectiveMethodGenerated(method, interfaze, introspectedTable)) {
			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
	}

	private Method getMethodShell(Set<FullyQualifiedJavaType> importedTypes) {
		Method method = new Method();

		FullyQualifiedJavaType returnType;
		if (introspectedTable.getGeneratedKey() != null) {
			IntrospectedColumn introspectedColumn = introspectedTable.getColumn(introspectedTable.getGeneratedKey().getColumn());
			if (introspectedColumn == null) {
				// the specified column doesn't exist, so don't do the generated
				// key
				// (the warning has already been reported)
				returnType = null;
			} else {
				returnType = introspectedColumn.getFullyQualifiedJavaType();
				importedTypes.add(returnType);
			}
		} else {
			returnType = null;
		}
		method.setReturnType(returnType);
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName(getDAOMethodNameCalculator().getInsertSelectiveMethodName(introspectedTable));

		FullyQualifiedJavaType parameterType = introspectedTable.getRules().calculateAllFieldsClass();

		importedTypes.add(parameterType);
		method.addParameter(new Parameter(parameterType, "record")); //$NON-NLS-1$

		for (FullyQualifiedJavaType fqjt : daoTemplate.getCheckedExceptions()) {
			method.addException(fqjt);
			importedTypes.add(fqjt);
		}

		ibatorContext.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		return method;
	}
}
