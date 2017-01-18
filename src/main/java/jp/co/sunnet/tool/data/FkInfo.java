package jp.co.sunnet.tool.data;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 外部キー *
 */
@Data
@AllArgsConstructor
public class FkInfo {
	/**
	 * カラム名 (物理名)
	 */
	private String columnName;

	/**
	 * 参照テーブル
	 */
	private String refTable;

	/**
	 * 参照キー
	 */
	private String refKey;
}
