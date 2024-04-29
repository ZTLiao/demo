//package com.ztliao.test;
//
//import cn.hutool.core.io.resource.ClassPathResource;
//import cn.hutool.core.util.StrUtil;
//import com.maxmind.geoip2.DatabaseReader;
//import com.maxmind.geoip2.exception.GeoIp2Exception;
//import com.maxmind.geoip2.record.Location;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.IOUtils;
//
//import java.io.*;
//import java.net.InetAddress;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.util.Arrays;
//
///**
// * @author: liaozetao
// * @date: 2023/8/8 11:05
// * @description:
// */
//public class IPLocationTest {
//
//    private static DatabaseReader instance = null;
//
//    public synchronized static DatabaseReader getInstance() throws IOException {
//        if (instance == null) {
//            // 创建 GeoLite2 数据库
//            ClassPathResource resource = new ClassPathResource("geolite2cityV2.mmdb");
//            InputStream inputStream = resource.getStream();
//            File tempFile = File.createTempFile("temp_geolite2city", ".mmdb");
//            try {
//                FileUtils.copyInputStreamToFile(inputStream, tempFile);
//            } finally {
//                IOUtils.closeQuietly(inputStream);
//            }
//            instance = new DatabaseReader.Builder(tempFile).build();
//        }
//        return instance;
//    }
//
//    /**
//     * @param ip
//     * @return
//     * @throws Exception
//     * @description: 获得国家
//     */
//    public static String getCountry(String ip) throws IOException, GeoIp2Exception {
//        DatabaseReader reader = getInstance();
//        if (reader == null) {
//            return null;
//        }
//        String country = reader.city(InetAddress.getByName(ip)).getCountry().getNames().get("zh-CN");
//        return country;
//    }
//
//    /**
//     * @param ip
//     * @return
//     * @throws Exception
//     * @description: 获得省份
//     */
//    public static String getProvince(String ip) throws IOException {
//        DatabaseReader reader = getInstance();
//        if (reader == null) {
//            return null;
//        }
//        String province = null;
//        try {
//            province = reader.city(InetAddress.getByName(ip)).getMostSpecificSubdivision().getNames().get("zh-CN");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return province;
//    }
//
//    /**
//     * @param ip
//     * @return
//     * @throws Exception
//     * @description: 获得城市
//     */
//    public static String getCity(String ip) throws IOException {
//        DatabaseReader reader = getInstance();
//        if (reader == null) {
//            return null;
//        }
//        String city = null;
//        try {
//            city = reader.city(InetAddress.getByName(ip)).getCity().getNames().get("zh-CN");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return city;
//    }
//
//    /**
//     * @param ip
//     * @return
//     * @throws Exception
//     * @description: 获得经度
//     */
//    public static Double getLongitude(String ip) throws IOException {
//        DatabaseReader reader = getInstance();
//        if (reader == null) {
//            return null;
//        }
//        Double longitude = null;
//        try {
//            longitude = reader.city(InetAddress.getByName(ip)).getLocation().getLongitude();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return longitude;
//    }
//
//    /**
//     * @param ip
//     * @return
//     * @throws Exception
//     * @description: 获得纬度
//     */
//    public static Double getLatitude(String ip) throws IOException {
//        DatabaseReader reader = getInstance();
//        if (reader == null) {
//            return null;
//        }
//        Double latitude = null;
//        try {
//            latitude = reader.city(InetAddress.getByName(ip)).getLocation().getLatitude();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return latitude;
//    }
//
//    /**
//     * 获取ip的位置信息
//     *
//     * @param ip
//     * @return
//     * @throws Exception
//     */
//    public static Location getLocationByIp(String ip) throws IOException {
//        DatabaseReader reader = getInstance();
//        if (reader == null) {
//            return null;
//        }
//        Location location = null;
//        try {
//            location = reader.city(InetAddress.getByName(ip)).getLocation();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return location;
//    }
//
//    public static void main(String[] args) {
//        String inputPath = "/Users/liaozetao/Downloads/test.txt";
//        String outputPath = "/Users/liaozetao/Downloads/test1.txt";
//        File file = new File(inputPath);
//        try (PrintStream printStream = new PrintStream(outputPath);
//             InputStreamReader read = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
//             BufferedReader bufferedReader = new BufferedReader(read)) {
//            System.setOut(printStream);
//            if (file.isFile() && file.exists()) {
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    try {
//                        System.out.println(IPLocationTest.getCountry(line));
//                    } catch (Exception e) {
//                        System.out.println();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
