package jp.co.sunnet.tool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TypeConvertUtils {
  private static Map<String, Class<?>> SQL_TYPE_TO_JAVA_TYPE = new TreeMap<>();
  static {
    SQL_TYPE_TO_JAVA_TYPE.put("timestamp", Date.class);
    SQL_TYPE_TO_JAVA_TYPE.put("bigint", Long.class);
    SQL_TYPE_TO_JAVA_TYPE.put("bigserial", Long.class);
    SQL_TYPE_TO_JAVA_TYPE.put("varchar(n)", String.class);
    SQL_TYPE_TO_JAVA_TYPE.put("smallint", Short.class);
    SQL_TYPE_TO_JAVA_TYPE.put("text", String.class);
    SQL_TYPE_TO_JAVA_TYPE.put("serial", Integer.class);
    SQL_TYPE_TO_JAVA_TYPE.put("integer", Integer.class);
    SQL_TYPE_TO_JAVA_TYPE.put("char", String.class);
    SQL_TYPE_TO_JAVA_TYPE.put("int", Integer.class);
    SQL_TYPE_TO_JAVA_TYPE.put("date", Date.class);
    SQL_TYPE_TO_JAVA_TYPE.put("boolean", Boolean.class);
    SQL_TYPE_TO_JAVA_TYPE.put("float", Float.class);
    SQL_TYPE_TO_JAVA_TYPE.put("double", Double.class);
    SQL_TYPE_TO_JAVA_TYPE.put("varchar", Double.class);

  }

  public static Class<?> convertSqlTypeToJavaType(String sqlType) {
    return SQL_TYPE_TO_JAVA_TYPE.get(sqlType);
  }

  private static Map<Class<?>, String> JAVA_CLASS_TO_PRIMITIVE_TYPE = new HashMap<>();
  static {
    JAVA_CLASS_TO_PRIMITIVE_TYPE.put(Short.class, "short");
    JAVA_CLASS_TO_PRIMITIVE_TYPE.put(Integer.class, "int");
    JAVA_CLASS_TO_PRIMITIVE_TYPE.put(Long.class, "long");

    JAVA_CLASS_TO_PRIMITIVE_TYPE.put(Boolean.class, "boolean");

    JAVA_CLASS_TO_PRIMITIVE_TYPE.put(Float.class, "float");
    JAVA_CLASS_TO_PRIMITIVE_TYPE.put(Double.class, "double");
  }

  public static String getPrimitiveType(Class<?> javaClasType) {
    return JAVA_CLASS_TO_PRIMITIVE_TYPE.get(javaClasType);
  }
}
