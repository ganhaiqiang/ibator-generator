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
package org.apache.ibatis.ibator.generator.ibatis2.sqlmap.elements;

import org.apache.ibatis.ibator.api.dom.xml.Attribute;
import org.apache.ibatis.ibator.api.dom.xml.TextElement;
import org.apache.ibatis.ibator.api.dom.xml.XmlElement;

/**
 * @author matychen
 *
 */
public class MysqlPaginationLimitGenerator extends AbstractXmlElementGenerator {

	public MysqlPaginationLimitGenerator() {
		super();
	}

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

		answer.addAttribute(new Attribute("id", introspectedTable.getIbatis2SqlMapNamespace() + "." + "Pagination_Limit")); //$NON-NLS-1$

		ibatorContext.getCommentGenerator().addComment(answer);

		XmlElement dynamicElement = new XmlElement("dynamic");
		XmlElement outerisNotEmptyElement = new XmlElement("isNotEmpty");
		outerisNotEmptyElement.addAttribute(new Attribute("property", "offset"));
		XmlElement innerisNotEmptyElement = new XmlElement("isNotEmpty");
		innerisNotEmptyElement.addAttribute(new Attribute("property", "limit"));
		innerisNotEmptyElement.addElement(new TextElement("<![CDATA[ limit #offset# , #limit# ]]>"));
		outerisNotEmptyElement.addElement(innerisNotEmptyElement);
		dynamicElement.addElement(outerisNotEmptyElement);
		answer.addElement(dynamicElement);
		// 在第二个地方增加
		parentElement.addElement(2, answer);
		// 空一行
		parentElement.addElement(new TextElement(""));
	}
}
