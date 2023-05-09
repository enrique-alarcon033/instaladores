package com.yonusa.instaladores_plus.utilities.catalogs;

public class Mqtt_CMD {

    private Mqtt_CMD(){ }

    //Topico General
    public static  final String GENERAL_TOPIC = "yonusa_send_control/";

    //Control Id = 1
    public static final String CMD_FENCE_ON    = "@FENCE_ON";
    public static final String CMD_FENCE_OFF   = "@FENCE_OFF";

    //Control Id = 2
    public static final String CMD_PANIC_ON    = "@PANIC_ON";
    public static final String CMD_PANIC_OFF   = "@PANIC_OFF";

    //Control Id = 3
    public static final String CMD_DOOR_OPEN   = "@DOOR_ON";
    public static final String CMD_DOOR_CLOSE  = "@DOOR_OFF";

    //Control Id = 4
    public static final String CMD_LIGHT_ON    = "@LIGHT_ON";
    public static final String CMD_LIGHT_OFF   = "@LIGHT_OFF";

    //Control Id = 5
    public static final String CMD_AUX_1_ON    = "@AUX1_ON";
    public static final String CMD_AUX_1_OFF   = "@AUX1_OFF";
    //Control Id = 6
    public static final String CMD_AUX_2_ON    = "@AUX2_ON";
    public static final String CMD_AUX_2_OFF   = "@AUX2_OFF";

    //Control Id = ?? Depreciado posiblemente se utilice después. era para un producto llamado botón de vida.
    public static final String CMD_AUX_ON      = "@AUX_ON";
    public static final String CMD_AUX_OFF     = "@AUX_OFF";

    //Control Id = 7
    public static final String CMD_ZONE_ON     = "@ZONA_ON";
    public static final String CMD_ZONE_OFF    = "@ZONA_OFF";

    //Control Id = 8
    public static final String CMD_PANEL_ON    = "@PANEL_ON";
    public static final String CMD_PANEL_OFF   = "@PANEL_OFF";

    //Date id = 9
    public static final String CMD_DATE_SET = "@DATE_SET";
    public static final String CMD_DATE_ERASE = "@DATE_ERASE";


    //ALARM On
    public static final String ALARM_ON    = "@ALARM_ON";

}
