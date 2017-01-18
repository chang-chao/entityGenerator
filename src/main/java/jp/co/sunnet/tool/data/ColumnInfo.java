package jp.co.sunnet.tool.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CaseFormat;

import jp.co.sunnet.tool.TypeConvertUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * エンティティクラス定義情報
 */
@Data
@AllArgsConstructor
public class ColumnInfo {
	/**
	 * テーブル名
	 */
	private TableInfo tableInfo;

	/**
	 * 論理名
	 */
	private String description;

	/**
	 * コラム名
	 */
	private String columnName;

	/**
	 * データタイプ
	 */
	private String sqlType;

	/**
	 * PKかどうか
	 */
	private boolean isId;

	/**
	 * NOT NULL
	 */
	private boolean notNull;

	/**
	 * アノテーション
	 */
	public List<String> getAnnotations() {
		List<String> annotations = new ArrayList<>();
		if (isId) {
			annotations.add("@Id");
			if (isSerial()) {
				String seqName = tableInfo.getTableName() + "_" + columnName + "_seq";
				annotations.add("@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = \"" + seqName + "\")");
				annotations.add("@SequenceGenerator(name = \"" + seqName + "\", sequenceName = \"" + seqName
						+ "\", allocationSize = 1)");
			}
		}

		if (isDate()) {
			annotations.add("@Temporal(TemporalType.TIMESTAMP)");
		}

		FkInfo fkInfo = getFkInfo();
		if (fkInfo != null) {
			annotations.add("@ManyToOne");
			annotations.add("@JoinColumn(name = \"" + columnName + "\")");
		}

		return annotations;
	}

	public String getJavaType() {
		FkInfo fkInfo = getFkInfo();
		if (fkInfo == null) {
			Class<?> javaClasType = TypeConvertUtils.convertSqlTypeToJavaType(sqlType);
			// IDでなく、かつ、NOT NULLの場合、primitiveデータタイプを使用する。
			if (!this.isId && this.notNull) {
				String primitiveType = TypeConvertUtils.getPrimitiveType(javaClasType);
				if (primitiveType != null) {
					return primitiveType;
				}
			}
			return javaClasType.getSimpleName();
		}

		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, fkInfo.getRefTable());
	}

	private FkInfo getFkInfo() {
		List<FkInfo> fks = tableInfo.getFks();
		for (FkInfo fkInfo : fks) {
			if (StringUtils.equals(fkInfo.getColumnName(), this.columnName)) {
				return fkInfo;
			}
		}
		return null;
	}

	public String getFieldName() {
		FkInfo fkInfo = getFkInfo();
		if (fkInfo == null) {
			return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName);
		}
		String columnNameWithoutId = StringUtils.removeEnd(columnName, "_id");
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnNameWithoutId);
	}

	public Set<String> getImportPackages() {
		Set<String> pkgs = new TreeSet<>();
		Class<?> javaType = TypeConvertUtils.convertSqlTypeToJavaType(sqlType);
		String pkgName = javaType.getPackage().getName();
		if (isPackageNeedImport(pkgName)) {
			pkgs.add(javaType.getName());
		}

		if (isId) {
			pkgs.add("javax.persistence.Id");
			if (isSerial()) {

				pkgs.add("javax.persistence.GeneratedValue");
				pkgs.add("javax.persistence.GenerationType");
				pkgs.add("javax.persistence.SequenceGenerator");

			}
		}

		if (isDate()) {
			pkgs.add("javax.persistence.TemporalType");
			pkgs.add("javax.persistence.Temporal");
		}

		FkInfo fkInfo = getFkInfo();
		if (fkInfo != null) {
			pkgs.add("javax.persistence.JoinColumn");
			pkgs.add("javax.persistence.ManyToOne");
		}

		return pkgs;
	}

	private boolean isPackageNeedImport(String pkgName) {
		return !StringUtils.equals(pkgName, Long.class.getPackage().getName());
	}

	private boolean isSerial() {
		return sqlType.equals("serial") || sqlType.equals("bigserial");
	}

	private boolean isDate() {
		return sqlType.equals("date") || sqlType.equals("timestamp");
	}
}
