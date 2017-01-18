package jp.co.sunnet.tool;

import java.io.StringWriter;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jp.co.sunnet.tool.data.TableInfo;

public class JavaFileGenerator {
  static Template javaFileTemplate = initTemplate();

  private static Template initTemplate() {
    try {
      Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_25);
      freemarkerConfiguration.setClassForTemplateLoading(EntityGenerator.class, "/");
      return freemarkerConfiguration.getTemplate("JavaFile.ftl");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String generateJavaFileContent(TableInfo tableDef) {
    try {
      Writer writter = new StringWriter();
      javaFileTemplate.process(tableDef, writter);
      return writter.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void outputJavaFile(String pkg, String outputDir, TableInfo tableDef) {
    // Javaファイル内容を生成
    String content = generateJavaFileContent(tableDef);
    // ファイルに出力する。
    JavaFileWritter.writeToFile(content, outputDir, pkg, tableDef.getClassName());
  }

}
