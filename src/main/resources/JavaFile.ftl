package ${pkg};

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

<#list importPackages as pkg>
import ${pkg};
</#list>

/**
 *
 * ${description}
 */
@Entity
@Getter
@Setter
public class ${className} {
    /**
     * コンストラクタ
     * 
     */
    public ${className}() {
    }

    /**
     * コンストラクタ
     * 
     * @param id
     *            ID
     */
    public ${className}(${pkColumn.javaType} id) {
        this.${pkColumn.fieldName} = id;
    }
<#list columns as col>

    /**
     * ${col.description}
     */
  <#list col.annotations as annotation>
    ${annotation}
  </#list>
    private ${col.javaType} ${col.fieldName};
</#list>

    /**
     * データ作成時に、作成時刻を設定する。
     */
    @PrePersist
    public void beforeInsert() {
<#if isHaveProperty("createTime")>
        this.createTime = new Date();
</#if >
<#if isHaveProperty("updateTime")>
        this.updateTime = new Date();
</#if >
<#if isHaveProperty("delFlag")>
        this.delFlag = false;
</#if >
    }

    /**
     * データ更新時に、更新時刻を設定する。
     */
    @PreUpdate
    public void beforeUpdate() {
<#if isHaveProperty("updateTime")>
        this.updateTime = new Date();
</#if >
    }
}
