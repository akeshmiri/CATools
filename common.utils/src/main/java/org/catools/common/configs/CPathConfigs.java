package org.catools.common.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.common.utils.CConfigUtil;
import org.catools.common.utils.CFileUtil;

import java.io.File;

public class CPathConfigs {
  public static File getTmpResourcesFolder() {
    return getTempChildFolder("resources");
  }

  public static File getTmpDownloadFolder() {
    return getTempChildFolder("downloads");
  }

  public static File getTmpUploadFolder() {
    return getTempChildFolder("uploads");
  }

  public static File getLocalConfigFolder() {
    File properties = getStorageChildFolder("configs");
    properties.mkdirs();
    return properties;
  }

  public static File getOutputFolder() {
    File file =
        new File(
            CHocon.get(Configs.PATH_OUTPUT_DIRECTORY).asString("./test-output")
                + StringUtils.defaultString(CConfigUtil.getRunName()));
    file.mkdirs();
    return file;
  }

  public static String getOutputPath() {
    return CFileUtil.getCanonicalPath(getOutputFolder());
  }

  public static File getOutputRoot() {
    File output = new File(CHocon.get(Configs.PATH_OUTPUT_DIRECTORY).asString("./test-output"));
    CFileUtil.forceMkdir(output);
    return output;
  }

  /**
   * return a File which is pointing to the file in current folder.
   *
   * @param child file name to be referred to
   * @return a File which is pointing to the child
   */
  public static File fromCurrent(String child) {
    return new File(".", child);
  }

  /**
   * return a File which is pointing to the file in output folder.
   *
   * @param child file name to be referred to
   * @return a File which is pointing to the child
   */
  public static File fromOutput(String child) {
    return new File(CPathConfigs.getOutputPath(), child);
  }

  /**
   * return a File which is pointing to the file in current storage.
   *
   * @param child file name to be referred to
   * @return a File which is pointing to the file
   */
  public static File fromStorage(String child) {
    File file = new File(CPathConfigs.getStorageFolder(), child);
    file.mkdirs();
    return file;
  }

  /**
   * return a File which is pointing to the file in {@link CPathConfigs#getTempFolder()} folder.
   *
   * @param child file name to be referred to
   * @return a File which is pointing to the file
   */
  public static File fromTmp(String child) {
    return new File(CPathConfigs.getTempFolder(), child);
  }

  public static File getStorageFolder() {
    File file = new File(CHocon.get(Configs.PATH_STORAGE_DIRECTORY).asString("./test-output"));
    file.mkdirs();
    return file;
  }

  public static File getImagesFolder() {
    return fromOutput("images");
  }

  public static File getTempFolder() {
    return getOutputChildFolder("tmp");
  }

  public static File getOutputChildFolder(String childFolder) {
    File file = fromOutput(childFolder);
    file.mkdirs();
    return file;
  }

  public static File getTempChildFolder(String childFolder) {
    File file = fromTmp(childFolder);
    file.mkdirs();
    return file;
  }

  public static File getStorageChildFolder(String childFolder) {
    File file = fromStorage(childFolder);
    file.mkdirs();
    return file;
  }

  public static File getOutputChildFile(String childFolder) {
    return fromOutput(childFolder);
  }

  public static File getTempChildFile(String childFolder) {
    return fromTmp(childFolder);
  }

  public static File getStorageChildFile(String childFolder) {
    return fromStorage(childFolder);
  }

  public static File getActualImagesFolder() {
    return CFileUtil.getChildFolder(getImagesFolder(), "actual");
  }

  public static File getExpectedImagesFolder() {
    return CFileUtil.getChildFolder(getImagesFolder(), "expected");
  }

  public static File getDiffImagesFolder() {
    return CFileUtil.getChildFolder(getImagesFolder(), "diff");
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    PATH_STORAGE_DIRECTORY("catools.paths.storage_directory"),
    PATH_OUTPUT_DIRECTORY("catools.paths.output_directory");

    private final String path;
  }
}
