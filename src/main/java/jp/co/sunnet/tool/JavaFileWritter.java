package jp.co.sunnet.tool;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Charsets;

public class JavaFileWritter {

  public static void writeToFile(String javaFileContent, String outputDir, String pkg, String className) {
    try {
      String classFileName = className + ".java";
      String dir = outputDir + "/" + StringUtils.replaceChars(pkg, '.', '/') + "/";
      FileUtils.forceMkdir(new File(dir));
      FileUtils.writeStringToFile(new File(dir + classFileName), javaFileContent, Charsets.UTF_8);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
