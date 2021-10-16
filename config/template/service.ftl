package ${package};

import java.util.List;
<#if tableClass.hasPrimaryKeyColumns && (tableClass.pkFields?? && tableClass.pkFields[0].shortTypeName=='BigDecimal')>import java.math.BigDecimal;</#if>
import ${tableClass.fullClassName};
import ${tableClass.exampleType};
<#if tableClass.generatePrimaryKeyClass>import ${tableClass.primaryKeyFullClassName};</#if>
<#if tableClass.hasBLOBColumns>import ${tableClass.fullClassName}WithBLOBs;</#if>

/**
 * 通用 Service 代码生成器
 *
 * @author mapper-generator
 */
public interface ${tableClass.shortClassName}${mapperSuffix} {
	
	int countByExample(${tableClass.exampleName} example);

    int deleteByExample(${tableClass.exampleName} example);
<#if tableClass.hasPrimaryKeyColumns>
	<#if tableClass.generatePrimaryKeyClass>
	
    int deleteByPrimaryKey(${tableClass.primaryKeyClassName} ${tableClass.primaryKeyVariableName});
    <#else>
    
    int deleteByPrimaryKey(${tableClass.pkFields[0].shortTypeName} ${tableClass.pkFields[0].fieldName});
    </#if>
</#if>
<#if tableClass.hasBLOBColumns>

    void insert(${tableClass.shortClassName}WithBLOBs record);
<#else>

    void insert(${tableClass.shortClassName} record);
</#if>
<#if tableClass.hasBLOBColumns>

    void insertSelective(${tableClass.shortClassName}WithBLOBs record);
<#else>

    void insertSelective(${tableClass.shortClassName} record);
</#if>
<#if tableClass.hasBLOBColumns>

    List<${tableClass.shortClassName}WithBLOBs> selectByExampleWithBLOBs(${tableClass.exampleName} example);

    List<${tableClass.shortClassName}> selectByExampleWithoutBLOBs(${tableClass.exampleName} example);
<#else>

    List<${tableClass.shortClassName}> selectByExample(${tableClass.exampleName} example);
</#if>   
<#if tableClass.hasPrimaryKeyColumns>
	<#if tableClass.generatePrimaryKeyClass>
		<#if tableClass.hasBLOBColumns>
		
    ${tableClass.shortClassName}WithBLOBs selectByPrimaryKey(${tableClass.primaryKeyClassName} ${tableClass.primaryKeyVariableName});
    	<#else>
    	
    ${tableClass.shortClassName} selectByPrimaryKey(${tableClass.primaryKeyClassName} ${tableClass.primaryKeyVariableName});
    	</#if>
    <#else>
    	<#if tableClass.hasBLOBColumns>
    	
    ${tableClass.shortClassName}WithBLOBs selectByPrimaryKey(${tableClass.pkFields[0].shortTypeName} ${tableClass.pkFields[0].fieldName});
    	<#else>
    	
    ${tableClass.shortClassName} selectByPrimaryKey(${tableClass.pkFields[0].shortTypeName} ${tableClass.pkFields[0].fieldName});
    	</#if>
    </#if>
</#if>
<#if tableClass.hasBLOBColumns>
 
    int updateByExampleSelective(${tableClass.shortClassName}WithBLOBs record, ${tableClass.exampleName} example);
<#else>
 
    int updateByExampleSelective(${tableClass.shortClassName} record, ${tableClass.exampleName} example);
</#if>
<#if tableClass.hasBLOBColumns>   
 
    int updateByExample(${tableClass.shortClassName}WithBLOBs record, ${tableClass.exampleName} example);
</#if>

    int updateByExample(${tableClass.shortClassName} record, ${tableClass.exampleName} example);
<#if tableClass.hasPrimaryKeyColumns>
	<#if tableClass.hasBLOBColumns>
	
    int updateByPrimaryKeySelective(${tableClass.shortClassName}WithBLOBs record);
    <#else>
    
    int updateByPrimaryKeySelective(${tableClass.shortClassName} record);
    </#if>
</#if>
<#if tableClass.hasPrimaryKeyColumns>
	<#if tableClass.hasBLOBColumns>

    int updateByPrimaryKey(${tableClass.shortClassName}WithBLOBs record);
    
    int updateByPrimaryKey(${tableClass.shortClassName} record);
    <#else>
    
    int updateByPrimaryKey(${tableClass.shortClassName} record);
    </#if>
</#if>
}




