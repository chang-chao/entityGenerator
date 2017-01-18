package test.entity.base;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * 子供
 */
@Getter
@Setter
@MappedSuperclass
public class BaseChild {
    /**
     * コンストラクタ
     * 
     */
    public BaseChild() {
    }

    /**
     * コンストラクタ
     * 
     * @param id
     *            ID
     */
    public BaseChild(Long id) {
        this.childDataId = id;
    }

    /**
     * 子供ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "child_child_data_id_seq")
    @SequenceGenerator(name = "child_child_data_id_seq", sequenceName = "child_child_data_id_seq", allocationSize = 1)
    private Long childDataId;

    /**
     * 親データID
     */
    @ManyToOne
    @JoinColumn(name = "parent_data_id")
    private Parent parentData;

    /**
     * 子供名
     */
    private String name;

    /**
     * 性別
     */
    private Short sex;

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
