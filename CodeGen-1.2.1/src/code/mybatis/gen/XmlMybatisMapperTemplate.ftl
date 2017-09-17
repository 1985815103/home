<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org/DTD Mapper 3.0" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${namespace}">	
	<#if get_enable || list_enable || listMap_enable>
	
	<!-- 自定义返回结果集 Create By CodeGen -->
	<resultMap id="BaseResultMap" type="${fullBeanName}"> 
    <#list fields as field>
    	<result column="${field.column}" property="${field.name}" javaType="${field.javaType}"></result>
    </#list>
	</resultMap>
	</#if>	
	<#if get_enable || list_enable || listMap_enable>
	
	<sql id="Base_Column_List" >
	 	${Base_Column_List}
	</sql>
	</#if>
	<#if insert_enable>
	
	<!-- 代码生成 -->
	<insert id="insert" parameterType="${fullBeanName}" useGeneratedKeys="false" keyProperty="${primaryProperty}">
		<#-- 注释
	    insert into ${tableName} 
	    	(<#list fields as field>${field.column}<#if field_has_next>, </#if><#if (field_index+1)%10=0>
			 </#if></#list>) 
	    values 
	    	(<#list fields as field>${r'#'}{${field.name}}<#if field_has_next>, </#if><#if (field_index+1)%10=0>
			 </#if></#list>)
		 -->
		 insert into ${tableName} 
			 (
			 	${insertSqlTemplate0}
			 ) values (
			 	${insertSqlTemplate1}
			 )
	</insert>
	</#if>
	<#if batchInsert_enable>
	
	<!-- 代码生成 -->
	<insert id="batchInsert" parameterType="java.util.List">
		 insert into ${tableName} 
			 (
			 	${insertSqlTemplate0}
			 ) values (
			 	${insertSqlTemplate1}
			 )
	</insert>
	</#if>
	<#if get_enable>
	
	<!-- 代码生成 -->
	<select id="get" resultMap="BaseResultMap">	
		select 
			  <include refid="Base_Column_List" />
		 from 
		 	  ${tableName} 
		where 
			  <#if primaryKey??>${primaryKey} = ${r'#'}{${primaryProperty}} <#else>1=0 </#if>
	</select>	
	</#if>
	<#if list_enable>
	
	<!-- 代码生成 -->
	<select id="list" parameterType="java.util.Map" resultMap="BaseResultMap">	
		select 
			  <include refid="Base_Column_List" />
		 from 
		 	  ${tableName}
	</select>	
	</#if>
	<#if listMap_enable>
	
	<!-- 代码生成 -->
	<select id="listMap" parameterType="java.util.Map" resultType="java.util.Map">	
		select 
			  <include refid="Base_Column_List" />
		 from 
		 	  ${tableName}
	</select>	
	</#if>
	<#if delete_enable>
	
	<!-- 代码生成 -->
	<delete id="delete">	
		delete from ${tableName} where <#if primaryKey??>${primaryKey} = ${r'#'}{${primaryProperty}} <#else>1=0 </#if>
	</delete>
	</#if>	
	<#if deleteByMap_enable>
	
	<!-- 代码生成 -->
	<delete id="deleteByMap" parameterType="java.util.Map">	
		delete from tb_asso_staff_inf_temp where <#if primaryKey??>${primaryKey} = ${r'$'}{${primaryProperty}} <#else>1=0 </#if>
	</delete>
	</#if>	
	<#if update_enable>
	
	<!-- 代码生成 -->
	<update id="update" parameterType="${fullBeanName}">
	    update 
	    		${tableName} 
	       set
	        	${updateSqlTemplate}
	      where 
	      		<#if primaryKey??>${primaryKey} = ${r'#'}{${primaryProperty}} <#else>1=0 </#if>
	</update>
	</#if>
	<#if updateByMap_enable>
	
	<!-- 代码生成 -->
	<update id="updateByMap" parameterType="java.util.Map">
	     update 
	     		${tableName} 
	     	set 
	    <#list fields as field>
	    	<#if field.name != primaryProperty && field_has_next>
	    	<if test="${field.name} != null">
	    		<#if field.javaType == "String">${field.column} = '${r'$'}{${field.name}}'<#else>${field.column} = ${r'$'}{${field.name}}</#if>,
	    	</if>
	    	<#elseif field.name != primaryProperty>
	    	<#if field.javaType == "String">${field.column} = '${r'$'}{${field.name}}'<#else>${field.column} = ${r'$'}{${field.name}}</#if>
	    	</#if>
	    </#list>
	   where 
	   		 <#if primaryKey??>${primaryKey} = ${r'#'}{${primaryProperty}} <#else>1=0 </#if>
	</update>
	</#if>
</mapper>
