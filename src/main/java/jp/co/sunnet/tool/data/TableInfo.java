package jp.co.sunnet.tool.data;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CaseFormat;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * テーブル定義情報
 */
@Data
@RequiredArgsConstructor
public class TableInfo {
  /**
   * 物理名
   */
  private final String tableName;

  /**
   * 論理名
   */
  private final String description;

  /**
   * プロパティ定義
   */
  private final List<ColumnInfo> columns;

  /**
   * 外部キー定義
   */
  private final List<FkInfo> fks;

  /**
   * パッケージ
   */
  private String pkg;

  public Set<String> getImportPackages() {
    Set<String> importPkgs = new TreeSet<>();
    for (ColumnInfo entityPropertyInfo : columns) {
      importPkgs.addAll(entityPropertyInfo.getImportPackages());
    }
    return importPkgs;
  }

  /**
   * 指定したプロパティが存在するかの判定
   * 
   * @param name
   *          プロパティ名
   * @return 存在フラグ
   */
  public boolean isHaveProperty(String name) {
    for (ColumnInfo columnInfo : columns) {
      if (StringUtils.equals(columnInfo.getFieldName(), name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * クラス名を取得
   * 
   * @return クラス名
   */
  public String getClassName() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
  }

  /**
   * PKカラム定義を取得
   * 
   * @return PKカラム定義
   */
  public ColumnInfo getPkColumn() {
    for (ColumnInfo columnInfo : columns) {
      if (columnInfo.isId()) {
        return columnInfo;
      }
    }
    throw new RuntimeException("no PK in table " + tableName);
  }
}
