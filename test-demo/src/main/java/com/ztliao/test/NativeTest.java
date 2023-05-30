package com.ztliao.test;

import com.qianyin.utils.QyCryptoUtil;
import com.qianyin.utils.SignUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: liaozetao
 * @date: 2023/5/29 16:48
 * @description:
 */
public class NativeTest {

    public static void main(String[] args) {
        //669F85F31128B5727BFDA7B369FFBECE
        String signPrivateKey = "f2m4pkxm5uz!45i3wu0#g01y9&99758uz70xc8216zt`55pr41g70868o:a155s4m+05f`0bmrm!1|0o4hp`fc63cihlf5ods2vzc9h1y/xz1zxmrklf2v473316072k1s7br92j7!dp07`c76twd|683p9dcvmef12vgkh25\"6z03m8r,z3bcyki390142bs3rzps6g1y6da<is2.1xt854i(2qr78431303e3`1nb`58tuw13610{bk9lyd6c0";
        Map<String, String> params = new HashMap<>();
        params.put("pub_timestamp", "1667460047000");
        params.put("roomUid", "90304899");
        params.put("uid", "90304899");
        String signKey = QyCryptoUtil.genSignQy(params, SignUtil.PUBLIC_PARAMETER_NAMES, signPrivateKey);
        System.out.println("signKey : " + signKey);
        String privateKey = "n'84iqu735tx6v;quskev6186%6sy29453pu;884r0ky5=xgqv375=cv1gii7xlgt1680r9g60e|>3te6vi8;kz25xw2v375l79s6ggxm74q515nv!vsb40p3vn(=2ku";
        String plainText = "1234567890";
        String base64Text = QyCryptoUtil.encryptDesQy(plainText, privateKey);
        System.out.println("des text : " + base64Text);
        System.out.println(QyCryptoUtil.decryptDesQy(base64Text, privateKey));
        base64Text = QyCryptoUtil.encryptAesQy(plainText, privateKey);
        System.out.println("aes text : " + base64Text);
        System.out.println(QyCryptoUtil.decryptAesQy(base64Text, privateKey));
    }
}
