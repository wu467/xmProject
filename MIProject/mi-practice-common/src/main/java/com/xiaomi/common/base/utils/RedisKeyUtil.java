package com.xiaomi.common.base.utils;


import org.springframework.stereotype.Component;

/**
 * redis key 工具类
 */
@Component
public class RedisKeyUtil {

    private  static final String BATTERY_MSG_PAGINATED_KEY_PREFIX = "battery:msg:%s";

    /**
     * 电池信息缓存key
     */
    private static final String BATTERY_MSG_PAGINATED_KEY = "battery:msg:%s:page:%d:size:%d";

    /**
     * 电池信息锁key
     */
    private static final String BATTERY_MSG_LOCK_KEY = "battery:msg:lock:%s";


    /**
     * 获取电池信息分页缓存key
     *
     * @param carId 车架id
     * @param pageNum 页数
     * @param pageSize 每页数量
     * @return
     */
    public static String getBatteryMsgPageKey(Long carId, int pageNum, int pageSize) {
        return String.format(BATTERY_MSG_PAGINATED_KEY, carId, pageNum, pageSize);
    }

    /**
     * 获取电池信息锁key
     *
     * @param carId 车架id
     * @return
     */
    public static String getBatteryMsgLockKey(Long carId) {
        return String.format(BATTERY_MSG_LOCK_KEY, carId);
    }

    /**
     * 获取电池信息分页key前缀
     *
     * @param carId 车架id
     */
    public static String getBatteryMsgPaginatedKeyPrefix (Long carId) {
        return String.format(BATTERY_MSG_PAGINATED_KEY_PREFIX, carId);
    }
}
