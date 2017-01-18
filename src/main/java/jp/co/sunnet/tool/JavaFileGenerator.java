package jp.co.sunnet.tool;

import java.io.StringWriter;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jp.co.sunnet.tool.data.TableInfo;

public class JavaFileGenerator {
  static Template baseEntityFileTemplate;
  static Template entityFileTemplate;
  static {
    initTemplate();
  }

  private static void initTemplate() {
    try {
      Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_25);
      freemarkerConfiguration.setClassForTemplateLoading(EntityGenerator.class, "/");
      baseEntityFileTemplate = freemarkerConfiguration.getTemplate("BaseEntity.ftl");
      entityFileTemplate = freemarkerConfiguration.getTemplate("Entity.ftl");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String generateJavaFileContent(Template template, TableInfo tableDef) {
    try {
      Writer writter = new StringWriter();
      template.process(tableDef, writter);
      return writter.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void outputJavaFile(String pkg, String outputDir, TableInfo tableDef) {
    // Javaファイル内容を生成
    String baseEntitycontent = generateJavaFileContent(baseEntityFileTemplate, tableDef);
    // ファイルに出力する。
    JavaFileWritter.writeToFile(baseEntitycontent, outputDir, pkg + ".base", "Base" + tableDef.getClassName());

    // Javaファイル内容を生成
    String content = generateJavaFileContent(entityFileTemplate, tableDef);
    // ファイルに出力する。
    JavaFileWritter.writeToFile(content, outputDir, pkg, tableDef.getClassName());

  }

}
