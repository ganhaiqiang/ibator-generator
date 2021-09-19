package org.apache.ibatis.ibator.api.dom.java;

public class TopLevelServiceClass extends TopLevelClass {
	private FullyQualifiedJavaType daoType;

	public TopLevelServiceClass(FullyQualifiedJavaType type) {
		super(type);
	}

	public FullyQualifiedJavaType getDaoType() {
		return daoType;
	}

	public void setDaoType(FullyQualifiedJavaType daoType) {
		this.daoType = daoType;
	}

}
