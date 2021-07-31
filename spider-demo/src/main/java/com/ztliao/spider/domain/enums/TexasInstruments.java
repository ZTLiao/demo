package com.ztliao.spider.domain.enums;

/**
 * @author: liaozetao
 * @date: 2021/7/30 12:58 PM
 * @description:
 */
public enum TexasInstruments {

    AMPLIFIER_CIRCUIT("amplifier-circuit", "放大器", 57),

    AUDIO_IC("audio-ic", "音频", 376),

    CLOCKS_TIMING("clocks-timing", "时钟和计时", 346),

    DATA_CONVERTERS("data-converters", "数据转换器", 82),

    DIE_WAFER_SERVICES("die-wafer-services", "裸片和晶圆服务", 3658),

    DLP_CHIP("dlp-chip", "DLP 产品", 1742),

    INTERFACE("interface", "接口", 361),

    ISOLATION("isolation", "隔离器件", 897),

    LOGIC_CIRCUIT("logic-circuit", "逻辑", 1),

    MICROCONTROLLERS_MCUS_PROCESSORS("microcontrollers-mcus-processors", "微控制器 (MCU) 和处理器", 4),

    MOTOR_DRIVERS("motor-drivers", "电机驱动器", 2004),

    POWER_MANAGEMENT("power-management", "电源管理", 64),

    RF_MICROWAVE("rf-microwave", "射频 & 微波", 367),

    SENSORS("sensors", "传感器", 353),

    SWITCHES_MULTIPLEXERS("switches-multiplexers", "开关和多路复用器", 727),

    WIRELESS_CONNECTIVITY("wireless-connectivity", "无线连接", 2003);

    private String enName;

    private String chName;

    private Integer familyId;

    TexasInstruments(String enName, String chName, Integer familyId) {
        this.enName = enName;
        this.chName = chName;
        this.familyId = familyId;
    }

    public String getEnName() {
        return enName;
    }

    public String getChName() {
        return chName;
    }

    public Integer getFamilyId() {
        return familyId;
    }
}
