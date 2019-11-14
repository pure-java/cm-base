package com.github.canglan.cm.common.util;

import com.github.canglan.cm.common.util.date.DateUtil;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * 订单号生成辅助类
 *
 * @author bairitan
 * @date 2018/9/3
 */
public class SerialCode {

  private final SerialConfig SERIAL_CONFIG = new SerialConfig();

  private LongSerialGenerator longSerialGenerator;
  private ShortSerialGenerator shortSerialGenerator;

  public SerialCode(String prefix, String suffix) {
    SERIAL_CONFIG.setPrefix(prefix).setSuffix(suffix).setCapacity(100);
    longSerialGenerator = new LongSerialGenerator(this.SERIAL_CONFIG);
    shortSerialGenerator = new ShortSerialGenerator(this.SERIAL_CONFIG);
  }

  public SerialCode() {
    longSerialGenerator = new LongSerialGenerator(this.SERIAL_CONFIG);
    shortSerialGenerator = new ShortSerialGenerator(this.SERIAL_CONFIG);
  }

  public static void main(String[] args) {
    SerialCode serialCode = new SerialCode();
    for (int i = 0; i < 100; i++) {

      System.out.println(serialCode.getShortCode());
    }
  }

  /**
   * 短序号
   *
   * @return 序号码
   */
  public String getShortCode() {
    return this.shortSerialGenerator.getCode();
  }

  /**
   * 长序号
   *
   * @return 序号码
   */
  public String getCode() {
    return this.longSerialGenerator.getCode();
  }

  private static interface SerialGenerator {

    public String getCode();
  }

  /**
   * 短序号生产类
   */
  private static class ShortSerialGenerator implements SerialGenerator {

    private final AtomicRangeInteger atomicRangeInteger = new AtomicRangeInteger(1, 9999);


    private final SerialConfig SERIAL_CONFIG;
    /**
     * 存储流水号队列
     */
    private ArrayBlockingQueue<String> longSerialNumQueue;

    public ShortSerialGenerator(SerialConfig serialConfig) {
      this.SERIAL_CONFIG = serialConfig;
      longSerialNumQueue = new ArrayBlockingQueue<>(this.SERIAL_CONFIG.getCapacity());
    }

    private long getSerialId() {
      return atomicRangeInteger.getAndIncr();
    }

    public void produceLongSerialNum() {
      ArrayBlockingQueue<String> tempQueue = this.longSerialNumQueue;
      for (int i = 0; i < this.SERIAL_CONFIG.getCapacity(); i++) {
        if (this.size() < this.SERIAL_CONFIG.getCapacity()) {
          tempQueue.add(String.format("%04d" , this.getSerialId()));
        }
      }
    }

    @Override
    public String getCode() {
      if (this.size() == 0) {
        this.produceLongSerialNum();
      }
      try {
        return this.getLongSerialNumQueue().take();
      } catch (InterruptedException e) {
        throw new RuntimeException("获取序号发生错误！！");
      }
    }

    private int size() {
      return this.longSerialNumQueue.size();
    }

    private ArrayBlockingQueue<String> getLongSerialNumQueue() {
      return longSerialNumQueue;
    }
  }

  /**
   * 长序号生产类
   * 使用 前缀 + 时间戳加上16进制数 + 后缀,前缀后缀可选
   */
  private static class LongSerialGenerator implements SerialGenerator {

    private final AtomicRangeInteger atomicRangeInteger = new AtomicRangeInteger(1, 1 << 31 - 1);


    private final SerialConfig SERIAL_CONFIG;
    /**
     * 存储流水号队列
     */
    private ArrayBlockingQueue<String> longSerialNumQueue;

    public LongSerialGenerator(SerialConfig serialConfig) {
      this.SERIAL_CONFIG = serialConfig;
      longSerialNumQueue = new ArrayBlockingQueue<>(this.SERIAL_CONFIG.getCapacity());
    }

    private long getSerialId() {
      return atomicRangeInteger.getAndIncr();
    }

    public void produceLongSerialNum() {
      ArrayBlockingQueue<String> tempQueue = this.longSerialNumQueue;
      String currentDateTime = DateUtil.newIns().asLocalDateTime() + "";

      if (StringUtils.isBlank(this.SERIAL_CONFIG.getPrefix()) && StringUtils.isBlank(this.SERIAL_CONFIG.getSuffix())) {
        for (int i = 0; i < this.SERIAL_CONFIG.getCapacity(); i++) {
          if (this.longSize() < this.SERIAL_CONFIG.getCapacity()) {
            tempQueue.add(currentDateTime + Long.toHexString(this.getSerialId()));
          }
        }
      } else {
        for (int i = 0; i < this.SERIAL_CONFIG.getCapacity(); i++) {
          if (this.longSize() < this.SERIAL_CONFIG.getCapacity()) {
            String e =
                this.SERIAL_CONFIG.getPrefix()
                    + currentDateTime
                    + Long.toHexString(this.getSerialId())
                    + this.SERIAL_CONFIG.getSuffix();
            tempQueue.add(e);
          }
        }
      }
    }

    @Override
    public String getCode() {
      if (this.longSize() == 0) {
        this.produceLongSerialNum();
      }
      try {
        return this.getLongSerialNumQueue().take();
      } catch (InterruptedException e) {
        throw new RuntimeException("获取序号发生错误！！");
      }
    }

    private int longSize() {
      return this.longSerialNumQueue.size();
    }

    private ArrayBlockingQueue<String> getLongSerialNumQueue() {
      return longSerialNumQueue;
    }
  }


  /**
   * 生产原子数
   */
  private static class AtomicRangeInteger {

    private final int maxValue;
    private final AtomicInteger atomicInteger;

    private AtomicRangeInteger(final int minValue, final int maxValue) {
      this.atomicInteger = new AtomicInteger(minValue);
      this.maxValue = maxValue;
    }

    private Integer getAndIncr() {
      final int value = this.atomicInteger.getAndIncrement();
      if (value > maxValue) {
        this.atomicInteger.set(1);
        return this.atomicInteger.getAndIncrement();
      }
      return value;
    }
  }

  @Setter
  @Getter
  @Accessors(chain = true)
  @ToString
  private static class SerialConfig {

    private int capacity = 100;

    /**
     * 前缀
     */
    private String prefix = StringUtils.EMPTY;

    /**
     * 后缀
     */
    private String suffix = StringUtils.EMPTY;

  }

}
