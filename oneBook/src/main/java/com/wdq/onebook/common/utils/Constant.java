package com.wdq.onebook.common.utils;

/**
 * 常量
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";
	/**
	 * 菜单类型
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 数据库-菜单类型
     */
    public enum Menu {
        /**
         * 上级菜单默认值
         */
        PARENT_DEFAULT("默认值",-1);
        // 成员变量
        private String name;
        private int index;
        // 构造方法
        private Menu(String name, int index) {
            this.name = name;
            this.index = index;
        }
        // get set 方法
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
    }


//
//    /**
//     * 定时任务状态
//     */
//    public enum ScheduleStatus {
//        /**
//         * 正常
//         */
//    	NORMAL(0),
//        /**
//         * 暂停
//         */
//    	PAUSE(1);
//
//        private int value;
//
//        ScheduleStatus(int value) {
//            this.value = value;
//        }
//
//        public int getValue() {
//            return value;
//        }
//    }
//
//    /**
//     * 云服务商
//     */
//    public enum CloudService {
//        /**
//         * 七牛云
//         */
//        QINIU(1),
//        /**
//         * 阿里云
//         */
//        ALIYUN(2),
//        /**
//         * 腾讯云
//         */
//        QCLOUD(3);
//
//        private int value;
//
//        CloudService(int value) {
//            this.value = value;
//        }
//
//        public int getValue() {
//            return value;
//        }
//    }

}
