/*
 *  Copyright 2009 The Apache Software Foundation
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
package org.apache.ibatis.ibator.generator.ibatis3.xmlmapper.elements;

import org.apache.ibatis.ibator.api.IntrospectedColumn;
import org.apache.ibatis.ibator.api.dom.xml.Attribute;
import org.apache.ibatis.ibator.api.dom.xml.TextElement;
import org.apache.ibatis.ibator.api.dom.xml.XmlElement;
import org.apache.ibatis.ibator.generator.ibatis3.Ibatis3FormattingUtilities;
import org.apache.ibatis.ibator.internal.util.StringUtility;

/**
 * 
 * @author Jeff Butler
 *
 */
public class SelectByPrimaryKeyElementGenerator extends AbstractXmlElementGenerator {

	public SelectByPrimaryKeyElementGenerator() {
		super();
	}

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

		answer.addAttribute(new Attribute("id", introspectedTable.getSelectByPrimaryKeyStatementId())); //$NON-NLS-1$
		if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
			answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
					introspectedTable.getResultMapWithBLOBsId()));
		} else {
			answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
					introspectedTable.getBaseResultMapId()));
		}

		String parameterType;
		if (introspectedTable.getRules().generatePrimaryKeyClass()) {
			parameterType = introspectedTable.getPrimaryKeyType();
		} else {
			// PK fields are in the base class. If more than on PK
			// field, then they are coming in a map.
			if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
				parameterType = "map"; //$NON-NLS-1$
			} else {
				parameterType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().toString();
			}
		}

		answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
				parameterType));

		ibatorContext.getCommentGenerator().addComment(answer);

		StringBuilder sb = new StringBuilder();
		sb.append("select "); //$NON-NLS-1$

		if (StringUtility.stringHasValue(introspectedTable.getSelectByPrimaryKeyQueryId())) {
			sb.append('\'');
			sb.append(introspectedTable.getSelectByPrimaryKeyQueryId());
			sb.append("' as QUERYID,"); //$NON-NLS-1$
		}
		answer.addElement(new TextElement(sb.toString()));
		answer.addElement(getBaseColumnListElement());
		if (introspectedTable.hasBLOBColumns()) {
			answer.addElement(new TextElement(",")); //$NON-NLS-1$
			answer.addElement(getBlobColumnListElement());
		}

		sb.setLength(0);
		sb.append("from "); //$NON-NLS-1$
		sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		answer.addElement(new TextElement(sb.toString()));

		boolean and = false;
		for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
			sb.setLength(0);
			if (and) {
				sb.append("  and "); //$NON-NLS-1$
			} else {
				sb.append("where "); //$NON-NLS-1$
				and = true;
			}

			sb.append(Ibatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
			sb.append(" = "); //$NON-NLS-1$
			sb.append(Ibatis3FormattingUtilities.getParameterClause(introspectedColumn));
			answer.addElement(new TextElement(sb.toString()));
		}

		if (ibatorContext.getPlugins().sqlMapSelectByPrimaryKeyElementGenerated(answer, introspectedTable)) {
			parentElement.addElement(answer);
		}
		// ?????????
		parentElement.addElement(new TextElement(""));
	}
}
