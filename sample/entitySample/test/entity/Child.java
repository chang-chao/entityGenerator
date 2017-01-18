package test.entity;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import test.entity.base.BaseChild;

/**
 *
 * 子供
 */
@Entity
@Getter
@Setter
public class Child extends BaseChild{
    /**
     * コンストラクタ
     * 
     */
    public Child() {
    }

    /**
     * コンストラクタ
     * 
     * @param id
     *            ID
     */
    public Child(Long id) {
        super(id);
    }

    /**
     * 親データID
     */
    @ManyToOne
    @JoinColumn(name = "parent_data_id")
    private Parent parentData;
}
