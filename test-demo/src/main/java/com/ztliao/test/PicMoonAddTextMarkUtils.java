package com.ztliao.test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class PicMoonAddTextMarkUtils {

    public static final String FONT_STYLE = "fonts/HarmonyOS_Sans_Naskh_Arabic_Black.ttf";

    public static final Color color = new Color(255, 244, 227, 255);
    //铭牌以四个汉字为基准的文字长度
    public static final int LEN_CHARACTERS_4 = 86;

    //铭牌以汉字为基准对应的x坐标
    public static final int MP_T_CHARACTER_LOCATION_X = 32;
    //铭牌以汉字为基准对应的y坐标
    public static final int MP_T_CHARACTER_LOCATION_Y = 93;

    public static BufferedImage addTextToPic(InputStream inputStream, String text) throws IOException {
        BufferedImage bufImg = null;
        try {
            Image srcImg = ImageIO.read(inputStream);
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 加水印
            bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_ARGB_PRE);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(color); //根据图片的背景设置水印颜色
            Font wordFontS = loadIconFont();
            g.setFont(wordFontS);              //设置字体
            //消除文字锯齿
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            //消除画图锯齿字符串
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //计算文字对应的坐标
            ImmutablePair<Integer, Integer> location = calculateLocation(text, wordFontS);
            g.drawString(text, location.getLeft(), location.getRight());
            g.dispose();
        } finally {
            if (inputStream != null) inputStream.close();
        }
        return bufImg;
    }


    /**
     * 计算徽章或铭牌坐标
     *
     * @param text
     * @return
     */
    public static ImmutablePair<Integer, Integer> calculateLocation(String text, Font fontS) {
        int x = (LEN_CHARACTERS_4 - getTextWidth(fontS, text)) / 2;
        return ImmutablePair.of(MP_T_CHARACTER_LOCATION_X + x, MP_T_CHARACTER_LOCATION_Y);
    }


    public static int getTextWidth(Font fontS, String content) {
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
        Rectangle rec = fontS.getStringBounds(content, frc).getBounds();
        return (int) rec.getWidth();
    }

    private static Font loadIconFont() throws IOException {
        Font font = null;
        InputStream inputStream = FileUtil.getInputStream(new File("/Users/liaozetao/IdeaProjects/peko-java/accompany-business/accompany-business-service/src/main/resources/fonts/HYWenHei-85W.ttf"));
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            inputStream.close();
        } catch (Exception ignored) {
        }
        assert font != null;
        return font.deriveFont(16f);
    }

    public static void main(String[] args) throws Exception {
        testAddTextToPic();
    }

    public static void testAddTextToPic() throws Exception {
        URL url = new URL("https://image.pekolive.com/qingtouyihe(2).png");
        URLConnection con = url.openConnection();
        con.setConnectTimeout(3 * 1000);
        try (InputStream input = con.getInputStream()) {
            InputStream uploadStream = null;
            FileOutputStream downloadFile = null;
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 ImageOutputStream imOut = ImageIO.createImageOutputStream(baos)) {
                BufferedImage bufImg = PicMoonAddTextMarkUtils.addTextToPic(input, "AAAAAA ");
                if (bufImg == null) return;
                ImageIO.write(bufImg, "png", imOut);
                uploadStream = new ByteArrayInputStream(baos.toByteArray());
                int index;
                byte[] bytes = new byte[1024];
                downloadFile = new FileOutputStream("/Users/liaozetao/Downloads/001.png");
                while ((index = uploadStream.read(bytes)) != -1) {
                    downloadFile.write(bytes, 0, index);
                    downloadFile.flush();
                }
            } finally {
                if (downloadFile != null) downloadFile.close();
                if (uploadStream != null) uploadStream.close();
            }
        }
        System.out.println("结束运行.....");
    }

}
