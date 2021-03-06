package test.entity.base;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * 親テーブル
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseParent {
    /**
     * コンストラクタ
     * 
     */
    public BaseParent() {
    }

    /**
     * コンストラクタ
     * 
     * @param id
     *            ID
     */
    public BaseParent(Integer id) {
        this.parentDataId = id;
    }

    /**
     * 親データID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parent_parent_data_id_seq")
    @SequenceGenerator(name = "parent_parent_data_id_seq", sequenceName = "parent_parent_data_id_seq", allocationSize = 1)
    private Integer parentDataId;

    /**
     * 名前
     */
    private String name;

    /**
     * 年齢
     */
    private Integer age;

    /**
     * データ作成時に、作成時刻を設定する。
     */
    @PrePersist
    public void beforeInsert() {
    }

    /**
     * データ更新時に、更新時刻を設定する。
     */
    @PreUpdate
    public void beforeUpdate() {
    }
}
