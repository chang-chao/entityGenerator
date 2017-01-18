package test.entity;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import test.entity.base.BaseParent;

/**
 *
 * 親テーブル
 */
@Entity
@Getter
@Setter
public class Parent extends BaseParent{
    /**
     * コンストラクタ
     * 
     */
    public Parent() {
    }

    /**
     * コンストラクタ
     * 
     * @param id
     *            ID
     */
    public Parent(Integer id) {
        super(id);
    }
}
