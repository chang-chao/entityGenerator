package jp.co.sunnet.tool;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;

import jp.co.sunnet.tool.data.ColumnInfo;
import jp.co.sunnet.tool.data.FkInfo;
import jp.co.sunnet.tool.data.TableInfo;

public class ERMasterExcelReader {
  /**
   * 最初のテーブル定義のシート番号<br>
   * １番はER図、テーブル定義シートは２番目から
   */
  private static final int FIRST_TABLE_SHEET_NO = 1;
  /**
   * テーブル名セル位置
   */
  private static CellAddress TABLE_NAME = new CellAddress(1, 1);
  /**
   * テーブル説明（論理名）セル位置
   */
  private static CellAddress TABLE_DESCRIPTION = new CellAddress(0, 1);

  private static class ColumnDefPosition {
    /**
     * 列定義の最初行
     */
    public static final int COLUMN_DEF_START_ROW = 6;

    /**
     * 論理名列
     */
    public static final int LOGICAL_NAME_COLUMN = 0;

    /**
     * 物理名列
     */
    public static final int PHYSICAL_NAME_COLUMN = 1;

    /**
     * 型列
     */
    public static final int TYPE_COLUMN = 2;

    /**
     * PK列
     */
    public static final int PK_COLUMN = 5;

    /**
     * NOT NULL
     */
    public static final int NOT_NULL_COLUMN = 6;
  }

  private static class FkDefPosition {
    /**
     * カラム名 (物理名)
     */
    public static final int COLUMN_COLUMN = 1;

    /**
     * 参照テーブル
     */
    public static final int REF_TABLE_COLUMN = 2;

    /**
     * 参照キー
     */
    public static final int REF_COL_COLUMN = 3;

  }

  public static final String FK_TITLE = "外部キー";

  public static List<TableInfo> readTableInfo(String excelFile) {
    List<TableInfo> tables = new ArrayList<>();
    try (Workbook wb = WorkbookFactory.create(new FileInputStream(excelFile))) {
      for (int sheetNo = FIRST_TABLE_SHEET_NO; sheetNo < wb.getNumberOfSheets(); sheetNo++) {
        // テーブル定義を読む
        TableInfo tableDef = readTableInfo(wb.getSheetAt(sheetNo));
        tables.add(tableDef);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return tables;
  }

  private static TableInfo readTableInfo(Sheet tableSheet) {
    List<ColumnInfo> columns = new ArrayList<>();
    String tableName = tableSheet.getRow(TABLE_NAME.getRow()).getCell(TABLE_NAME.getColumn()).toString().trim();
    String tableDescription = tableSheet.getRow(TABLE_DESCRIPTION.getRow()).getCell(TABLE_DESCRIPTION.getColumn())
        .toString().trim();
    List<FkInfo> fks = new ArrayList<>();
    TableInfo tableDef = new TableInfo(tableName, tableDescription, columns, fks);

    // コラム情報を解析する
    readColumnInfo(tableSheet, tableDef);

    // FK情報を解析する
    readFkInfo(tableSheet, tableDef);
    return tableDef;

  }

  private static void readFkInfo(Sheet tableSheet, TableInfo tableDef) {
    int fkTitleRow = 0;
    for (; fkTitleRow < tableSheet.getLastRowNum(); fkTitleRow++) {
      Row row = tableSheet.getRow(fkTitleRow);
      if (row != null) {
        Cell cell = row.getCell(0);
        if (cell != null) {
          if (StringUtils.equals(cell.toString(), FK_TITLE)) {
            break;
          }

        }

      }
    }
    int fkStartRow = fkTitleRow + 2;
    for (int rowNo = fkStartRow; rowNo <= tableSheet.getLastRowNum(); rowNo++) {
      Row row = tableSheet.getRow(rowNo);
      if (row == null) {
        break;
      }
      Cell columnNameCell = row.getCell(FkDefPosition.COLUMN_COLUMN);
      if (columnNameCell == null) {
        break;
      }
      FkInfo fk = new FkInfo(columnNameCell.toString(), row.getCell(FkDefPosition.REF_TABLE_COLUMN).toString(),
          row.getCell(FkDefPosition.REF_COL_COLUMN).toString());
      tableDef.getFks().add(fk);

    }
  }

  private static void readColumnInfo(Sheet tableSheet, TableInfo tableDef) {
    List<ColumnInfo> columns = tableDef.getColumns();
    int rows = tableSheet.getLastRowNum();
    // コラム情報を解析する
    for (int rowNum = ColumnDefPosition.COLUMN_DEF_START_ROW; rowNum < rows; rowNum++) {
      Row columnDefRow = tableSheet.getRow(rowNum);
      if (columnDefRow == null) {
        break;
      }
      String columnLogicalName = columnDefRow.getCell(ColumnDefPosition.LOGICAL_NAME_COLUMN).toString().trim();
      String columnPhysicalName = columnDefRow.getCell(ColumnDefPosition.PHYSICAL_NAME_COLUMN).toString().trim();
      String columnType = columnDefRow.getCell(ColumnDefPosition.TYPE_COLUMN).toString().trim();
      boolean isPk = columnDefRow.getCell(ColumnDefPosition.PK_COLUMN) != null
          && StringUtils.equals(columnDefRow.getCell(ColumnDefPosition.PK_COLUMN).toString().trim(), "○");

      boolean notNull = columnDefRow.getCell(ColumnDefPosition.NOT_NULL_COLUMN) != null
          && StringUtils.equals(columnDefRow.getCell(ColumnDefPosition.NOT_NULL_COLUMN).toString().trim(), "○");
      columns.add(new ColumnInfo(tableDef, columnLogicalName, columnPhysicalName, columnType, isPk, notNull));

    }
  }
}
