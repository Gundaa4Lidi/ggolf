var appConfig = {
    url:"/GGolfz/rest/",
    MSG_TYPE_SYS : "system",//系统类型信息
    MSG_TYPE_NO_SYS : "no_system",//不是系统类型信息
    MSG_TYPE_DYNAMIC : "dynamic",//动态类型信息
    MSG_TYPE_INVITED : "invited",//约球类型信息
    /**
     * 正则表达式
     */
    REG_PHONE : '/(^(\\d{3,4}-)?\\d{7,8})$|(1[34578]\\d{9})$/',
    REG_AryNum : '/^([1-9]\\d*(\\.\\d*)?|0\\.\\d*[1-9])$/',
    PUSH_BROADCAST : "Broadcast",
    PUSH_UNICAST : "unicast",
    PUSH_FILECAST : "filecast",
    PUSH_GROUPCAST : "groupcast",
    PUSH_LISTCAST : "listcasst",
}