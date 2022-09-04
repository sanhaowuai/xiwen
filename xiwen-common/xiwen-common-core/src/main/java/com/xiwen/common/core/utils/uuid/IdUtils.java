package com.xiwen.common.core.utils.uuid;

/**
 * ID生成器工具类
 * 
 * @author xiwen
 */
public class IdUtils {

    private static SnowflakeId idWorker = new SnowflakeId(0, 0);


    /**
     * 获取随机UUID
     * 
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     * 
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     * 
     * @return 随机UUID
     */
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     * 
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }

    /**
     * @description:获取雪花算法的id
     * @author: cuiqichao
     * @date: 2022-08-29 22:18:37
     * @return: java.lang.String
    **/
    public static String getSnowflakeId(){
        return idWorker.nextId();
    }
}
