package jp.co.sunnet.tool;

import java.util.List;

import jp.co.sunnet.tool.data.TableInfo;

public class EntityGenerator {
  /**
   * @param args
   *          <br>
   *          args[0]：inputエクセルファイル <br>
   *          args[1]：パッケージ <br>
   *          args[2]：出力ディレクトリ
   */

  public static void main(String[] args) {
    if (args.length != 3) {
      printUsage();
      return;
    }

    try {
      // inputエクセルファイル
      String inputFile = args[0];
      // パッケージ
      String pkg = args[1];
      // 出力ディレクトリ
      String outputDir = args[2];
      generateEntityFiles(inputFile, pkg, outputDir);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void generateEntityFiles(String inputFile, String pkg, String outputDir) {
    System.out.println("ファイル作成開始 ");
    // ファイル内容を読み、テーブル定義に解析する。
    List<TableInfo> tables = ERMasterExcelReader.readTableInfo(inputFile);

    for (TableInfo tableDef : tables) {
      // パッケージ名はここに設定するのは、ちょっと汚いですが。。。
      tableDef.setPkg(pkg);
      JavaFileGenerator.outputJavaFile(pkg, outputDir, tableDef);
      System.out.println(tableDef.getClassName() + ".java生成完了");
    }
    System.out.println("ファイル生成完了 、ファイル数= " + tables.size());
  }

  private static void printUsage() {
    String usage = "usage: " + EntityGenerator.class.getSimpleName()
        + " inputExcelFile entityPackageName outputSourceDirectory";
    System.out.println(usage);
  }

}
