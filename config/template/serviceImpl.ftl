package ${package};

import java.util.List;
<#if tableClass.hasPrimaryKeyColumns && (tableClass.pkFields?? && tableClass.pkFields[0].shortTypeName=='BigDecimal')>
import java.math.BigDecimal;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import ${tableClass.daoPackage}.${tableClass.shortClassName}DAO;

import ${tableClass.fullClassName};
import ${tableClass.exampleType};
<#if tableClass.generatePrimaryKeyClass>
import ${tableClass.primaryKeyFullClassName};
</#if>
<#if tableClass.hasBLOBColumns>
import ${tableClass.fullClassName}WithBLOBs;
</#if>

/**
 * 通用 ServiceImpl 代码生成器
 *
 * @author mapper-generator
 */
public class ${tableClass.shortClassName}${mapperSuffix} implements ${tableClass.shortClassName}Service {

	@Autowired
	private ${tableClass.shortClassName}DAO ${tableClass.variableName}DAO;

	@Override
	public int countByExample(${tableClass.exampleName} example) {
		return ${tableClass.variableName}DAO.countByExample(example);
	}

	@Override
	public int deleteByExample(${tableClass.exampleName} example) {
		return ${tableClass.variableName}DAO.deleteByExample(example);
	}
<#if tableClass.hasPrimaryKeyColumns>
	<#if tableClass.generatePrimaryKeyClass>
	
	@Override
	public int deleteByPrimaryKey(${tableClass.primaryKeyClassName} ${tableClass.primaryKeyVariableName}) {
		return ${tableClass.variableName}DAO.deleteByPrimaryKey(${tableClass.primaryKeyVariableName});
	}
	<#else>
    
	
	@Override
	public int deleteByPrimaryKey(${tableClass.pkFields[0].shortTypeName} ${tableClass.pkFields[0].fieldName}) {
		return ${tableClass.variableName}DAO.deleteByPrimaryKey(${tableClass.pkFields[0].fieldName});
	}
	</#if>
</#if>
<#if tableClass.hasBLOBColumns>
    
	@Override
	public void insert(${tableClass.shortClassName}WithBLOBs record) {
		${tableClass.variableName}DAO.insert(record);
	}
<#else>
    
	@Override
	public void insert(${tableClass.shortClassName} record) {
		${tableClass.variableName}DAO.insert(record);
	}
</#if>
<#if tableClass.hasBLOBColumns>

	@Override
	public void insertSelective(${tableClass.shortClassName}WithBLOBs record) {
		${tableClass.variableName}DAO.insertSelective(record);
	}
<#else>

	@Override
	public void insertSelective(${tableClass.shortClassName} record) {
		${tableClass.variableName}DAO.insertSelective(record);
	}
</#if>
<#if tableClass.hasBLOBColumns>

	@Override
	public List<${tableClass.shortClassName}WithBLOBs> selectByExampleWithBLOBs(${tableClass.exampleName} example) {
		return ${tableClass.variableName}DAO.selectByExampleWithBLOBs(example);
	}

	@Override
	public List<${tableClass.shortClassName}> selectByExampleWithoutBLOBs(${tableClass.exampleName} example) {
		return ${tableClass.variableName}DAO.selectByExampleWithoutBLOBs(example);
	}
<#else>

	@Override
	public List<${tableClass.shortClassName}> selectByExample(${tableClass.exampleName} example) {
		return ${tableClass.variableName}DAO.selectByExample(example);
	}
</#if>
<#if tableClass.hasPrimaryKeyColumns>
	<#if tableClass.generatePrimaryKeyClass>
		<#if tableClass.hasBLOBColumns>
		
	@Override
	public ${tableClass.shortClassName}WithBLOBs selectByPrimaryKey(${tableClass.primaryKeyClassName} ${tableClass.primaryKeyVariableName}) {
		return ${tableClass.variableName}DAO.selectByPrimaryKey(${tableClass.primaryKeyVariableName});
	}
    	<#else>
    	
	@Override
	public ${tableClass.shortClassName} selectByPrimaryKey(${tableClass.primaryKeyClassName} ${tableClass.primaryKeyVariableName}) {
		return ${tableClass.variableName}DAO.selectByPrimaryKey(${tableClass.primaryKeyVariableName});
	}
    	</#if>
    <#else>
    	<#if tableClass.hasBLOBColumns>
    	
	@Override
	public ${tableClass.shortClassName}WithBLOBs selectByPrimaryKey(${tableClass.pkFields[0].shortTypeName} ${tableClass.pkFields[0].fieldName}) {
		return ${tableClass.variableName}DAO.selectByPrimaryKey(${tableClass.pkFields[0].fieldName});
	}
    	<#else>
    
	@Override
	public ${tableClass.shortClassName} selectByPrimaryKey(${tableClass.pkFields[0].shortTypeName} ${tableClass.pkFields[0].fieldName}) {
		return ${tableClass.variableName}DAO.selectByPrimaryKey(${tableClass.pkFields[0].fieldName});
	}
    	</#if>
    </#if>
</#if>
<#if tableClass.hasBLOBColumns>
 
	@Override
	public int updateByExampleSelective(${tableClass.shortClassName}WithBLOBs record, ${tableClass.exampleName} example) {
		return ${tableClass.variableName}DAO.updateByExampleSelective(record, example);
	}
<#else>
 
	@Override
	public int updateByExampleSelective(${tableClass.shortClassName} record, ${tableClass.exampleName} example) {
		return ${tableClass.variableName}DAO.updateByExampleSelective(record, example);
	}
</#if>
<#if tableClass.hasBLOBColumns>

	@Override
	public int updateByExample(${tableClass.shortClassName}WithBLOBs record, ${tableClass.exampleName} example) {
		return ${tableClass.variableName}DAO.updateByExample(record, example);
	}
</#if>

	@Override
	public int updateByExample(${tableClass.shortClassName} record, ${tableClass.exampleName} example) {
		return ${tableClass.variableName}DAO.updateByExample(record, example);
	}
<#if tableClass.hasPrimaryKeyColumns>
	<#if tableClass.hasBLOBColumns>
	
	@Override
	public int updateByPrimaryKeySelective(${tableClass.shortClassName}WithBLOBs record) {
		return ${tableClass.variableName}DAO.updateByPrimaryKeySelective(record);
	}
    <#else>
	
	@Override
	public int updateByPrimaryKeySelective(${tableClass.shortClassName} record) {
		return ${tableClass.variableName}DAO.updateByPrimaryKeySelective(record);
	}
    </#if>
</#if>
<#if tableClass.hasPrimaryKeyColumns>
	<#if tableClass.hasBLOBColumns>

	@Override
	public int updateByPrimaryKey(${tableClass.shortClassName}WithBLOBs record) {
		return ${tableClass.variableName}DAO.updateByPrimaryKey(record);
	}
    
	@Override
	public int updateByPrimaryKey(${tableClass.shortClassName} record) {
		return ${tableClass.variableName}DAO.updateByPrimaryKey(record);
	}
    <#else>
    
	@Override
	public int updateByPrimaryKey(${tableClass.shortClassName} record) {
		return ${tableClass.variableName}DAO.updateByPrimaryKey(record);
	}
    </#if>
</#if>
}
