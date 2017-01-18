package test.entity;

import javax.persistence.Entity;

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
}
