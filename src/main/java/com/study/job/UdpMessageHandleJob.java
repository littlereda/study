package com.tiger.system.job;

import cn.hutool.core.date.DateUtil;
import com.tiger.common.enums.AlarmType;
import com.tiger.system.domain.*;
import com.tiger.system.domain.vo.PositionVo;
import com.tiger.system.service.InitService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author Ji linjie
 * @Date 2023/6/12 14:31
 * @Version 1.0
 */
@Slf4j
@Component
public class UdpMessageHandleJob extends Thread {

    public static Queue<String> udpData = new ConcurrentLinkedQueue<>();

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            if (udpData.size() > 0) {
                String udpMessage = udpData.poll();
//                log.warn("UdpMessageHandleJob-{}", udpData.size());
                // udp信息表
                TbUdpMessage tbUdpMessage = new TbUdpMessage();
                tbUdpMessage.setMsg(udpMessage);
                tbUdpMessage.setRecieveTime(new Date());
                tbUdpMessage.setCreateTime(new Date());

                // message存储
                UDPDataJob.udpData.offer(tbUdpMessage);
                String[] dataParts = udpMessage.split(",");
                // 确保数据长度符合预期
                if (dataParts.length > 0) {
                    // 区分位置信号类型
                    if (dataParts[0].equals("display")) {
                        PositionVo positionVo = new PositionVo();
                        positionVo.setAlarm(0);
                        if (dataParts.length != 9) {
                            // 数据长度不符合预期，进行相应的错误处理
                            System.out.println("UDP数据长度不正确！");
                            return;
                        }
                        try {
                            TbTrack tbTrack = new TbTrack();
                            TbLabel tbLabel = new TbLabel();
                            // 解析数据字段
                            tbTrack.setLabelId(dataParts[1]);
                            tbTrack.setLabelName("");
                            tbTrack.setPositionTime(dataParts[6]);
                            tbTrack.setLat(Double.valueOf(dataParts[3]));
                            tbTrack.setLon(Double.valueOf(dataParts[2]));
                            tbTrack.setHog(Double.valueOf(dataParts[4]));
                            tbTrack.setPileNumber("");
                            tbTrack.setRscp(0);
                            tbTrack.setPower(Integer.valueOf(dataParts[5]));
                            tbTrack.setCreateTime(new Date());
                            // 进行相应的处理或存储数据
                            UDPTrackJob.udpData.offer(tbTrack);
                            UDPTrackDealJob.udpData.offer(tbTrack);
                            // label表
                            tbLabel.setLabelId(dataParts[1]);
                            tbLabel.setLabelPower(dataParts[5]);
                            tbLabel.setLon(Double.valueOf(dataParts[2]));
                            tbLabel.setLat(Double.valueOf(dataParts[3]));
                            tbLabel.setIsOnline("1");
                            tbLabel.setGpsTime(DateUtil.parseDateTime(dataParts[6]));
                            tbLabel.setUpdateTime(DateUtil.parseDateTime(dataParts[6]));
                            if (monRegionJudge(Double.valueOf(dataParts[3]), Double.valueOf(dataParts[2])) == true) {
                                tbLabel.setGpsType("0");
                            } else {
                                tbLabel.setGpsType("1");
                            }
                            // 进行相应的处理或存储数据
                            UDPLabelJob.udpData.offer(tbLabel);
                            // 可以根据需要，将解析得到的数据存储到对象中或进行其他业务逻辑处理
                        } catch (NumberFormatException e) {
                            // 处理数据转换异常
                            e.printStackTrace();
                        }
                    } else if (dataParts[0].equals("$GNGGA")) {
                        PositionVo positionVo = new PositionVo();
                        positionVo.setAlarm(0);
                        if (dataParts.length != 21) {
                            // 数据长度不符合预期，进行相应的错误处理
                            System.out.println("UDP数据长度不正确！");
                            return;
                        }
                        try {
                            TbTrack tbTrack = new TbTrack();
                            tbTrack.setLabelId(dataParts[15]);
                            tbTrack.setLabelName("");
                            tbTrack.setPositionTime(DateUtil.formatDateTime(DateTimeFormat(dataParts[1])));
                            tbTrack.setLat(convertCoordinate("0" + dataParts[2], dataParts[3]));
                            tbTrack.setLon(convertCoordinate(dataParts[4], dataParts[5]));
                            tbTrack.setHog(0.0D);
                            tbTrack.setPileNumber("");
                            tbTrack.setRscp(0);
                            tbTrack.setPower(Integer.valueOf(dataParts[16]));
                            tbTrack.setCreateTime(new Date());
                            tbTrack.setRemark("2");
                            UDPTrackJob.udpData.offer(tbTrack);
                            UDPTrackDealJob.udpData.offer(tbTrack);
                            // label表
                            TbLabel tbLabel = new TbLabel();
                            tbLabel.setLabelId(dataParts[15]);
                            tbLabel.setLabelPower(dataParts[16]);
                            tbLabel.setLabelState(dataParts[10]);
                            tbLabel.setLat(convertCoordinate("0" + dataParts[2], dataParts[3]));
                            tbLabel.setLon(convertCoordinate(dataParts[4], dataParts[5]));
                            tbLabel.setIsOnline("1");
                            tbLabel.setGpsTime(DateTimeFormat(dataParts[1]));
                            tbLabel.setUpdateTime(DateTimeFormat(dataParts[1]));
                            if (monRegionJudge(convertCoordinate("0" + dataParts[2], dataParts[3]), convertCoordinate(dataParts[4], dataParts[5])) == true) {
                                tbLabel.setGpsType("0");
                            } else {
                                tbLabel.setGpsType("1");
                            }
                            UDPLabelJob.udpData.offer(tbLabel);
                        } catch (NumberFormatException e) {
                            // 处理数据转换异常
                            e.printStackTrace();
                        }
                    } else if (dataParts[0].split(":")[0].equals("warning")) {
                        try {
                            TbAlarm tbAlarm = new TbAlarm();
                            String type = "";
                            if (dataParts[1].equals("SOS0x01")) {
                                type = AlarmType.SOS.getValue();
                                tbAlarm.setAlarmTime(dataParts[3]);
                            } else if (dataParts[1].equals("SOS0x02")) {
                                type = AlarmType.TAG_STATIC.getValue();
                                tbAlarm.setAlarmTime(dataParts[3]);
                            } else if (dataParts[1].equals("SOS0x04")) {
                                type = AlarmType.TAG_LONG_STATIC_SLEEP.getValue();
                                tbAlarm.setAlarmTime(dataParts[3]);
                            } else if (dataParts[1].equals("SOS0x07")) {
                                type = AlarmType.WRIST_BAND_TAMPER.getValue();
                                tbAlarm.setAlarmTime(dataParts[3]);
                            } else if (dataParts[1].equals("SOS0x05")) {
                                type = AlarmType.TAG_STATIC_CHANGE.getValue();
                                tbAlarm.setAlarmTime(dataParts[3]);
                            } else if (dataParts[1].equals("SOS0x06")) {
                                type = AlarmType.TAG_LONG_STATIC_CHANGE.getValue();
                                tbAlarm.setAlarmTime(dataParts[3]);
                            } else if (dataParts[1].equals("OFFANCH")) {
                                type = AlarmType.BASE_STATION_OFFLINE.getValue();
                                tbAlarm.setAlarmTime(dataParts[3]);
                            } else if (dataParts[1].equals("OFF_TAG")) {
                                type = AlarmType.TAG_OFFLINE.getValue();
                                tbAlarm.setAlarmTime(dataParts[3]);
                            } else if (dataParts[1].equals("SOS0x08")) {
                                type = AlarmType.COLLISION.getValue();
                                tbAlarm.setAlarmTime(dataParts[3]);
                            } else {
                                type = AlarmType.OTHER.getValue();
                                tbAlarm.setAlarmTime(dataParts[3]);
                            }
                            tbAlarm.setLabelId(isConvertibleToLong(dataParts[2]) ? Long.valueOf(dataParts[2]) : 0L);
                            tbAlarm.setAlarmType(type);
                            tbAlarm.setCreateTime(new Date());
                            UDPAlarmJob.udpData.offer(tbAlarm);
                        } catch (NumberFormatException e) {
                            // 处理数据转换异常
                            e.printStackTrace();
                        }
                    } else if (dataParts[0].equals("door")) {
                        UdpDoorHandleJob.attendData.offer(TbAttendDoor.setTbAttendDoor(udpMessage));
                    }
                } else {
                    // 数据长度不符合预期，进行相应的错误处理
                    System.out.println("UDP数据长度不正确！");
                }

            }
//            Thread.sleep(100L);
        }
    }

    // 日期时间格式化
    public Date DateTimeFormat(String date) {
        // 解析原始时间字符串
        int hours = Integer.parseInt(date.substring(0, 2));
        int minutes = Integer.parseInt(date.substring(2, 4));
        int seconds = Integer.parseInt(date.substring(4, 6));
        int milliseconds = Integer.parseInt(date.substring(7, 10));

        // 创建 Calendar 对象并设置时间值
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);
        return calendar.getTime();
    }

    // 经纬度转化
    public static Double convertCoordinate(String coordinate, String direction) {
        // 获取度数的整数部分
        int dd = (int) (Double.parseDouble(coordinate) / 100);
        // 获取度分的小数部分
        double mm = Double.parseDouble(coordinate) % 100;
        // 计算度数表示
        double degree = dd + mm / 60.0;
        // 根据方向确定正负号
        if (direction.equals("S") || direction.equals("W")) {
            degree *= -1;
        }

        return degree;
    }

    // 是否能转化为long
    public static boolean isConvertibleToLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 判断洞内洞外
    public Boolean monRegionJudge(double latitude, double longitude) {
        for (Map s : InitService.monRegion) {
            Polygon polygon = createPolygon((String) s.get("polygon"));
            boolean locationInPolygon = isLocationInPolygon(latitude, longitude, polygon);
            if (locationInPolygon == true) {
                return true;
            }
        }
        return false;
    }

    // 创建多边形对象
    private static Polygon createPolygon(String regionCoordinates) {
        String[] points = regionCoordinates.split(",");
        int[] yPoints = new int[points.length];
        int[] xPoints = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            String[] coordinates = points[i].trim().split(" ");
            double latitude = Double.parseDouble(coordinates[0]);
            double longitude = Double.parseDouble(coordinates[1]);
            xPoints[i] = (int) (longitude * 1e6);
            yPoints[i] = (int) (latitude * 1e6);
        }

        return new Polygon(xPoints, yPoints, points.length);
    }

    // 判断经纬度位置是否在多边形区域内
    private static boolean isLocationInPolygon(double latitude, double longitude, Polygon polygon) {
        int x = (int) (longitude * 1e6);
        int y = (int) (latitude * 1e6);
        return polygon.contains(x, y);
    }
}
