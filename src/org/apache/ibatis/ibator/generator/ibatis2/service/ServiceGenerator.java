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
package org.apache.ibatis.ibator.generator.ibatis2.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.ibator.api.CommentGenerator;
import org.apache.ibatis.ibator.api.FullyQualifiedTable;
import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.Interface;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.apache.ibatis.ibator.api.dom.java.TopLevelServiceClass;
import org.apache.ibatis.ibator.config.PropertyRegistry;
import org.apache.ibatis.ibator.generator.AbstractJavaGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.AbstractDAOElementGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.ServiceCountByExampleMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.ServiceDeleteByExampleMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.ServiceDeleteByPrimaryKeyMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.ServiceInsertMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.ServiceInsertSelectiveMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.ServiceSelectByExampleWithBLOBsMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.ServiceSelectByExampleWithoutBLOBsMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.ServiceSelectByPrimaryKeyMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.ServiceUpdateByExampleSelectiveMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.ServiceUpdateByExampleWithBLOBsMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.ServiceUpdateByExampleWithoutBLOBsMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.ServiceUpdateByPrimaryKeySelectiveMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.ServiceUpdateByPrimaryKeyWithBLOBsMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.elements.ServiceUpdateByPrimaryKeyWithoutBLOBsMethodGenerator;
import org.apache.ibatis.ibator.generator.ibatis2.dao.templates.AbstractDAOTemplate;
import org.apache.ibatis.ibator.internal.util.StringUtility;
import org.apache.ibatis.ibator.internal.util.messages.Messages;
import org.apache.ibatis.ibator.util.ClassNameUtils;

/**
 * 
 * @author Jeff Butler
 *
 */
public class ServiceGenerator extends AbstractJavaGenerator {

	private AbstractDAOTemplate serviceTemplate;
	private boolean generateForJava5;

	public ServiceGenerator(AbstractDAOTemplate daoTemplate, boolean generateForJava5) {
		super();
		this.serviceTemplate = daoTemplate;
		this.generateForJava5 = generateForJava5;
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
		progressCallback.startTask(Messages.getString("Progress.14", table.toString()));
		TopLevelServiceClass topLevelClass = getTopLevelClassShell();
		Interface interfaze = getInterfaceShell();

		addDaoInterface(topLevelClass, interfaze);
		addCountByExampleMethod(topLevelClass, interfaze);
		addDeleteByExampleMethod(topLevelClass, interfaze);
		addDeleteByPrimaryKeyMethod(topLevelClass, interfaze);
		addInsertMethod(topLevelClass, interfaze);
		addInsertSelectiveMethod(topLevelClass, interfaze);
		addSelectByExampleWithBLOBsMethod(topLevelClass, interfaze);
		addSelectByExampleWithoutBLOBsMethod(topLevelClass, interfaze);
		addSelectByPrimaryKeyMethod(topLevelClass, interfaze);
		addUpdateByExampleSelectiveMethod(topLevelClass, interfaze);
		addUpdateByExampleWithBLOBsMethod(topLevelClass, interfaze);
		addUpdateByExampleWithoutBLOBsMethod(topLevelClass, interfaze);
		addUpdateByPrimaryKeySelectiveMethod(topLevelClass, interfaze);
		addUpdateByPrimaryKeyWithBLOBsMethod(topLevelClass, interfaze);
		addUpdateByPrimaryKeyWithoutBLOBsMethod(topLevelClass, interfaze);

		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
		if (ibatorContext.getPlugins().daoImplementationGenerated(topLevelClass, introspectedTable)) {
			answer.add(topLevelClass);
		}

		if (ibatorContext.getPlugins().daoInterfaceGenerated(interfaze, introspectedTable)) {
			answer.add(interfaze);
		}

		return answer;
	}

	protected TopLevelServiceClass getTopLevelClassShell() {
		FullyQualifiedJavaType interfaceType = new FullyQualifiedJavaType(introspectedTable.getServiceInterfaceType());
		FullyQualifiedJavaType implementationType = new FullyQualifiedJavaType(introspectedTable.getServiceImplementationType());
		FullyQualifiedJavaType daoInterfaceType = new FullyQualifiedJavaType(introspectedTable.getDAOInterfaceType());

		CommentGenerator commentGenerator = ibatorContext.getCommentGenerator();

		TopLevelServiceClass answer = new TopLevelServiceClass(implementationType);
		answer.setVisibility(JavaVisibility.PUBLIC);
		answer.addAnnotation("@Service");
		answer.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
//		answer.setSuperClass(serviceTemplate.getSuperClass());
//		answer.addImportedType(serviceTemplate.getSuperClass());
		answer.addSuperInterface(interfaceType);
		answer.addImportedType(interfaceType);
		answer.setDaoType(daoInterfaceType);

		for (FullyQualifiedJavaType fqjt : serviceTemplate.getImplementationImports()) {
			answer.addImportedType(fqjt);
		}

		commentGenerator.addJavaFileComment(answer);

		// add constructor from the template
		answer.addMethod(serviceTemplate.getConstructorClone(commentGenerator, implementationType, introspectedTable));

		// add any fields from the template
		for (Field field : serviceTemplate.getFieldClones(commentGenerator, introspectedTable)) {
			answer.addField(field);
		}

		// add any methods from the template
		for (Method method : serviceTemplate.getMethodClones(commentGenerator, introspectedTable)) {
			answer.addMethod(method);
		}

		return answer;
	}

	protected Interface getInterfaceShell() {
		Interface answer = new Interface(new FullyQualifiedJavaType(introspectedTable.getServiceInterfaceType()));
		answer.setVisibility(JavaVisibility.PUBLIC);

		String rootInterface = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
		if (rootInterface == null) {
			rootInterface = ibatorContext.getServiceGeneratorConfiguration().getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
		}

		if (StringUtility.stringHasValue(rootInterface)) {
			FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
			answer.addSuperInterface(fqjt);
			answer.addImportedType(fqjt);
		}

		for (FullyQualifiedJavaType fqjt : serviceTemplate.getInterfaceImports()) {
			answer.addImportedType(fqjt);
		}

		ibatorContext.getCommentGenerator().addJavaFileComment(answer);

		return answer;
	}

	protected void addDaoInterface(TopLevelServiceClass topLevelClass, Interface interfaze) {
		FullyQualifiedJavaType daoType = topLevelClass.getDaoType();
		Field field = new Field();
		field.addAnnotation("@Autowired");
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(daoType);
		field.setName(ClassNameUtils.captureName(daoType.getShortName()));
		topLevelClass.addField(field);
		topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
		topLevelClass.addImportedType(daoType);
	}

	protected void addCountByExampleMethod(TopLevelClass topLevelClass, Interface interfaze) {
		if (introspectedTable.getRules().generateCountByExample()) {
			AbstractDAOElementGenerator methodGenerator = new ServiceCountByExampleMethodGenerator(generateForJava5);
			initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
		}
	}

	protected void addDeleteByExampleMethod(TopLevelClass topLevelClass, Interface interfaze) {
		if (introspectedTable.getRules().generateDeleteByExample()) {
			AbstractDAOElementGenerator methodGenerator = new ServiceDeleteByExampleMethodGenerator();
			initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
		}
	}

	protected void addDeleteByPrimaryKeyMethod(TopLevelClass topLevelClass, Interface interfaze) {
		if (introspectedTable.getRules().generateDeleteByPrimaryKey()) {
			AbstractDAOElementGenerator methodGenerator = new ServiceDeleteByPrimaryKeyMethodGenerator();
			initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
		}
	}

	protected void addInsertMethod(TopLevelClass topLevelClass, Interface interfaze) {
		if (introspectedTable.getRules().generateInsert()) {
			AbstractDAOElementGenerator methodGenerator = new ServiceInsertMethodGenerator();
			initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
		}
	}

	protected void addInsertSelectiveMethod(TopLevelClass topLevelClass, Interface interfaze) {
		if (introspectedTable.getRules().generateInsertSelective()) {
			AbstractDAOElementGenerator methodGenerator = new ServiceInsertSelectiveMethodGenerator();
			initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
		}
	}

	protected void addSelectByExampleWithBLOBsMethod(TopLevelClass topLevelClass, Interface interfaze) {
		if (introspectedTable.getRules().generateSelectByExampleWithBLOBs()) {
			AbstractDAOElementGenerator methodGenerator = new ServiceSelectByExampleWithBLOBsMethodGenerator(generateForJava5);
			initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
		}
	}

	protected void addSelectByExampleWithoutBLOBsMethod(TopLevelClass topLevelClass, Interface interfaze) {
		if (introspectedTable.getRules().generateSelectByExampleWithoutBLOBs()) {
			AbstractDAOElementGenerator methodGenerator = new ServiceSelectByExampleWithoutBLOBsMethodGenerator(generateForJava5);
			initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
		}
	}

	protected void addSelectByPrimaryKeyMethod(TopLevelClass topLevelClass, Interface interfaze) {
		if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
			AbstractDAOElementGenerator methodGenerator = new ServiceSelectByPrimaryKeyMethodGenerator();
			initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
		}
	}

	protected void addUpdateByExampleSelectiveMethod(TopLevelClass topLevelClass, Interface interfaze) {
		if (introspectedTable.getRules().generateUpdateByExampleSelective()) {
			AbstractDAOElementGenerator methodGenerator = new ServiceUpdateByExampleSelectiveMethodGenerator();
			initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
		}
	}

	protected void addUpdateByExampleWithBLOBsMethod(TopLevelClass topLevelClass, Interface interfaze) {
		if (introspectedTable.getRules().generateUpdateByExampleWithBLOBs()) {
			AbstractDAOElementGenerator methodGenerator = new ServiceUpdateByExampleWithBLOBsMethodGenerator();
			initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
		}
	}

	protected void addUpdateByExampleWithoutBLOBsMethod(TopLevelClass topLevelClass, Interface interfaze) {
		if (introspectedTable.getRules().generateUpdateByExampleWithoutBLOBs()) {
			AbstractDAOElementGenerator methodGenerator = new ServiceUpdateByExampleWithoutBLOBsMethodGenerator();
			initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
		}
	}

	protected void addUpdateByPrimaryKeySelectiveMethod(TopLevelClass topLevelClass, Interface interfaze) {
		if (introspectedTable.getRules().generateUpdateByPrimaryKeySelective()) {
			AbstractDAOElementGenerator methodGenerator = new ServiceUpdateByPrimaryKeySelectiveMethodGenerator();
			initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
		}
	}

	protected void addUpdateByPrimaryKeyWithBLOBsMethod(TopLevelClass topLevelClass, Interface interfaze) {
		if (introspectedTable.getRules().generateUpdateByPrimaryKeyWithBLOBs()) {
			AbstractDAOElementGenerator methodGenerator = new ServiceUpdateByPrimaryKeyWithBLOBsMethodGenerator();
			initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
		}
	}

	protected void addUpdateByPrimaryKeyWithoutBLOBsMethod(TopLevelClass topLevelClass, Interface interfaze) {
		if (introspectedTable.getRules().generateUpdateByPrimaryKeyWithoutBLOBs()) {
			AbstractDAOElementGenerator methodGenerator = new ServiceUpdateByPrimaryKeyWithoutBLOBsMethodGenerator();
			initializeAndExecuteGenerator(methodGenerator, topLevelClass, interfaze);
		}
	}

	protected void initializeAndExecuteGenerator(AbstractDAOElementGenerator methodGenerator, TopLevelClass topLevelClass, Interface interfaze) {
		methodGenerator.setDAOTemplate(serviceTemplate);
		methodGenerator.setIbatorContext(ibatorContext);
		methodGenerator.setIntrospectedTable(introspectedTable);
		methodGenerator.setProgressCallback(progressCallback);
		methodGenerator.setWarnings(warnings);
		methodGenerator.addImplementationElements(topLevelClass);
		methodGenerator.addInterfaceElements(interfaze);
	}
}
