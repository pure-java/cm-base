package com.github.canglan.cm.common.util;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * @author bairitan
 * @since 2017-12-27 14:55
 **/
public class IpUtil {

  /**
   * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
   * 参考文章： http://developer.51cto.com/art/201111/305181.htm
   * <p>
   * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
   * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
   * <p>
   * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
   * 192.168.1.100
   * <p>
   * 用户真实IP为： 192.168.1.110
   *
   * @return ip地址
   */
  public static String getIpAddress(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
  }


  /**
   * 获取访问者IP
   * <p>
   * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
   * <p>
   * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
   * 如果还不存在则调用Request .getRemoteAddr()。
   *
   * @return ip地址
   */
  public static String getIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("X-Real-IP");
    String unknown = "unknown";
    if (!StringUtils.isBlank(ip) && !unknown.equalsIgnoreCase(ip)) {
      return ip;
    }
    ip = request.getHeader("X-Forwarded-For");
    if (!StringUtils.isBlank(ip) && !unknown.equalsIgnoreCase(ip)) {
      // 多次反向代理后会有多个IP值，第一个为真实IP。
      int index = ip.indexOf(',');
      if (index != -1) {
        return ip.substring(0, index);
      } else {
        return ip;
      }
    } else {
      return request.getRemoteAddr();
    }
  }

  //public static IpInfo getIpInfo(String ip) {
  //    HttpClient httpClient = new HttpClient();
  //    GetMethod getMethod = new GetMethod("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
  //    //使用默认的恢复策略
  //    getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
  //    IpInfo ipInfo = null;
  //    try {
  //        int statusCode = httpClient.executeMethod(getMethod);
  //        if (statusCode == HttpStatus.SC_OK) {
  //            String responseBody = getMethod.getResponseBodyAsString();
  //            JacksonUtil jacksonUtilZrxk = new JacksonUtil();
  //            Map<String, Object> map = jacksonUtilZrxk.readJsonToObject(responseBody, HashMap.class);
  //            if (Objects.equals(0, DataUtil.toInteger(map.get("code"), null))) {
  //                jacksonUtilZrxk.getObjectMapper().configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
  //                ipInfo = jacksonUtilZrxk.readJsonToObject(jacksonUtilZrxk.writeValueAsString(map.get("data")), IpInfo.class);
  //            }
  //        }
  //    } catch (IOException e) {
  //        //发生致命的异常，可能是协议不对或者返回的内容有问题
  //        e.printStackTrace();
  //    } finally {
  //        getMethod.releaseConnection();
  //        if (ipInfo == null) {
  //            ipInfo = new IpInfo();
  //            ipInfo.setCountry("获取IP来源失败");
  //            ipInfo.setCity("");
  //            ipInfo.setIp(ip);
  //            ipInfo.setIsp("");
  //            ipInfo.setRegion("");
  //        }
  //    }
  //    return ipInfo;
  //}
}
