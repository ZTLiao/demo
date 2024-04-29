package com.ztliao.test;

import java.io.*;

/**
 * @author: liaozetao
 * @date: 2023/11/8 11:44
 * @description:
 */
public class VueScriptTest {

    public static void main(String[] args) {
        String sourcePath = "/Users/liaozetao/IdeaProjects/yinmeng-java/yinmeng-admin/yinmeng-admin-web/src/main/resources/static/html";
        String targetPath = "/Users/liaozetao/Downloads/views";
        File sourceDirectory = new File(sourcePath);
        File targetDirectory = new File(targetPath);
        if (!targetDirectory.exists()) {
            boolean isMkdir = targetDirectory.mkdirs();
        }
        processFiles(sourceDirectory, targetDirectory);
    }

    private static void processFiles(File sourceDirectory, File targetDirectory) {
        File[] files = sourceDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File newTargetDirectory = new File(targetDirectory, file.getName());
                    boolean isMkdir = newTargetDirectory.mkdirs();
                    processFiles(file, newTargetDirectory);
                } else if (file.getName().endsWith(".html")) {
                    try {
                        String newFileNameBase = toCamelCase(file.getName().replace(".html", "")).trim();
                        String componentName = upperFirst(newFileNameBase) + "View";
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        StringBuilder content = new StringBuilder();
                        StringBuilder styleContent = new StringBuilder();
                        StringBuilder scriptContent = new StringBuilder();
                        boolean inStyleTag = false;
                        boolean inScriptTag = false;
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (isNonEmptyNonCommentLine(line)) {
                                if (line.trim().startsWith("<style>")) {
                                    inStyleTag = true;
                                    line = line.replace("<style>", "<style scoped>");
                                }
                                if (line.trim().startsWith("<script>")) {
                                    inScriptTag = true;
                                }
                                if (inStyleTag) {
                                    styleContent.append(line).append("\n");
                                } else if (inScriptTag) {
                                    if (line.trim().startsWith("<script>")) {
                                        scriptContent.append(line).append("\n");
                                        scriptContent.append("export default {\n" + "  name: \"").append(componentName).append("\",\n").append("  setup() {},\n").append("  created() {\n").append("    this.$nextTick(function () {\n").append("      this.initData();\n").append("    });\n").append("  },\n").append("  methods: {\n").append("    initData() {\n");
                                    } else {
                                        if (line.contains(".bootstrapTable") && line.contains("destroy")) {
                                            scriptContent.append(line).append("\n");
                                            scriptContent.append("// 清空分页组件的容器\n");
                                            scriptContent.append("$('.fixed-table-pagination').empty();\n");
                                        } else {
                                            if (line.trim().contains("</script>")) {
                                                scriptContent.append("    }\n" + "  }\n" + "};\n");
                                            }
                                            scriptContent.append(line).append("\n");
                                        }
                                    }

                                } else {
                                    // 添加import语句
                                    if (line.contains("<script src=\"/static/js/admin/style.js\"></script>")) {
                                        // 在scriptContent中找到<script>的位置，下一行插入import语句
                                        int scriptIndex = scriptContent.indexOf("<script>");
                                        if (scriptIndex != -1) {
                                            scriptContent.insert(scriptIndex + "<script>".length(), "\nimport '@/utils/style.js';");
                                        }
                                    } else {
                                        content.append(line).append("\n");
                                    }
                                }
                            } else {
                                content.append(line).append("\n");
                            }
                            if (inStyleTag && line.trim().endsWith("</style>")) {
                                inStyleTag = false;
                            }
                            if (inScriptTag && line.trim().endsWith("</script>")) {
                                inScriptTag = false;
                            }
                        }
                        reader.close();
                        content.insert(0, "<template>\n");
                        content.append("</template>\n");
                        content = new StringBuilder(content.toString().replace("type=\"date\"", ""));
                        // 添加import语句
                        if (scriptContent.toString().contains("TableHelper")) {
                            // 在scriptContent中找到<script>的位置，下一行插入import语句
                            int scriptIndex = scriptContent.indexOf("<script>");
                            if (scriptIndex != -1) {
                                scriptContent.insert(scriptIndex + "<script>".length(), "\nimport TableHelper from '@/utils/bootstrap-table-helper';");
                            }
                        }
                        content.append(scriptContent).append("\n");
                        content.append(styleContent);
                        String newFileName = upperFirst(newFileNameBase) + "View.vue";
                        File newFile = new File(targetDirectory, newFileName);
                        FileWriter writer = new FileWriter(newFile);
                        writer.write(content.toString());
                        writer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static boolean isNonEmptyNonCommentLine(String line) {
        return !line.trim().isEmpty() && !line.trim().startsWith("<!--");
    }

    private static String toCamelCase(String fileName) {
        StringBuilder result = new StringBuilder();
        boolean nextUpperCase = false;
        for (char c : fileName.toCharArray()) {
            if (c == '-' || c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    result.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }
        return result.toString();
    }

    private static String upperFirst(String name) {
        char[] ch = name.toCharArray();
        ch[0] -= 32;
        return new String(ch);
    }
}
