package ${pkg};

import javax.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ${pkg}.base.Base${className};

/**
 * ${description}
 */
@NoArgsConstructor
@Entity
@Getter
@Setter
public class ${className} extends Base${className}{
    /**
     * コンストラクタ
     *
     * @param id
     *            ID
     */
    public ${className}(${pkColumn.javaType} id) {
        super(id);
    }
<#list columns as col>
<#if col.fk>

    /**
     * ${col.description}
     */
  <#list col.annotations as annotation>
    ${annotation}
  </#list>
    private ${col.javaType} ${col.fieldName};
</#if >
</#list>
}
