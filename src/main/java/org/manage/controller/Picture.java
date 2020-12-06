package org.manage.controller;

import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * @author: Diego
 * @date: 2020/7/13 14:20
 * @Des:
 */
public class Picture {
    private static final Integer ARROW_HEIGHT = 30;
    private static final Integer ARROW_LENGTH = 5;

    public static Rectangle setTextStraightColumn(int x, int y, String str, Graphics2D g) {
        int strlength = str.length();
        int t = g.getFontMetrics().stringWidth("str");
        int o = y;
        for (int i = 0; i < strlength; i++) {
            g.drawString(String.valueOf(str.charAt(i)), x, y);
            y += t;
        }
        Rectangle rect = new Rectangle(x - 5, o - t, t + 10, y - o + t - 10);
        g.drawRect(x - 5, o - t, t + 6, y - o + t - 15);
        return rect;
    }

    public static Rectangle setTextLineUp(int x, int y, String str, Graphics2D g) {
        int strlength = str.length();
        int t = g.getFontMetrics().stringWidth("str");
        g.drawString(str, x, y);
        Rectangle rect = new Rectangle(x - 10, y - t, strlength * t, t + 8);
        g.drawRect(x - 10, y - t, strlength * t, t + 8);
        return rect;
    }

    public static void drawName(List<String> customers,
                                int srcImgWidth,
                                int y0, int t,
                                Graphics2D g,
                                Map<String, Rectangle> pos) {
        if (customers != null) {
            int n = customers.size();
            if (n > 0) {
                int swidth = srcImgWidth / n;
                int x0 = 0;
                int i = 0;
                for (String name : customers) {
                    x0 = (swidth / 2) - ((t + 6) / 2) + (i * swidth);
                    i++;
                    Rectangle rect1 = setTextStraightColumn(x0, y0, name, g);
                    pos.put(name, rect1);
                }
            }
        }
    }

    private static double[] rotateVec(int px, int py, double ang,
                                      boolean isChLen, double newLen) {
        double mathstr[] = new double[2];
        double vx = px * Math.cos(ang) - py * Math.sin(ang);
        double vy = px * Math.sin(ang) + py * Math.cos(ang);
        if (isChLen) {
            double d = Math.sqrt(vx * vx + vy * vy);
            vx = vx / d * newLen;
            vy = vy / d * newLen;
            mathstr[0] = vx;
            mathstr[1] = vy;
        }
        return mathstr;
    }

    private static void drawAL(int sx, int sy, int ex, int ey, Graphics2D g2) {
        double H = ARROW_HEIGHT;
        double L = ARROW_LENGTH;
        int x3 = 0;
        int y3 = 0;
        int x4 = 0;
        int y4 = 0;
        double awrad = Math.atan(L / H);
        double arraow_len = Math.sqrt(L * L + H * H);
        double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
        double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
        double x_3 = ex - arrXY_1[0];
        double y_3 = ey - arrXY_1[1];
        double x_4 = ex - arrXY_2[0];
        double y_4 = ey - arrXY_2[1];

        Double X3 = new Double(x_3);
        x3 = X3.intValue();
        Double Y3 = new Double(y_3);
        y3 = Y3.intValue();
        Double X4 = new Double(x_4);
        x4 = X4.intValue();
        Double Y4 = new Double(y_4);
        y4 = Y4.intValue();
        g2.drawLine(sx, sy, ex, ey);
        g2.drawLine(ex, ey, x3, y3);
        g2.drawLine(ex, ey, x4, y4);
    }

    public static void drawLine0(String name,
                                 Graphics2D g,
                                 Map<String, Rectangle> pos,
                                 Map<String, List<String>> map) {
        if (name != null) {
            List<String> toNames = map.get(name);
            if (toNames != null) {
                for (String toName : toNames) {
                    Rectangle fromPos = pos.get(name);
                    Rectangle toPos = pos.get(toName);
                    int formPosX = (int) (fromPos.getX() + fromPos.getWidth() / 2);
                    int formPosY = (int) (fromPos.getY() + fromPos.getHeight());
                    int toPosX = (int) (toPos.getX() + toPos.getWidth() / 2);
                    int toPosY = (int) (toPos.getY());
                    drawAL(formPosX, formPosY, toPosX, toPosY, g);
                }
            }
        }
    }

    public static void drawLine(List<String> customers,
                                 Graphics2D g,
                                 Map<String, Rectangle> pos,
                                 Map<String, List<String>> map) {
        if (customers != null) {
            int n = customers.size();
            if (n > 0) {
                for (String name : customers) {
                    List<String> toNames = map.get(name);
                    if (toNames != null) {
                        for (String toName : toNames) {
                            Rectangle fromPos = pos.get(name);
                            Rectangle toPos = pos.get(toName);
                            int fromX = (int) fromPos.getX();
                            int fromY = (int) fromPos.getY();
                            int toX = (int) toPos.getX();
                            int toY = (int) toPos.getY();

                            int formPosX = 0;
                            int formPosY = 0;
                            int toPosX = 0;
                            int toPosY = 0;
                            if (fromY < toY) {
                                formPosX = (int) (fromPos.getX() + fromPos.getWidth() / 2);
                                formPosY = (int) (fromPos.getY() + fromPos.getHeight());
                                toPosX = (int) (toPos.getX() + toPos.getWidth() / 2);
                                toPosY = (int) (toPos.getY());
                            } else if (fromY == toY) {
                                if (fromX < toX) {
                                    formPosX = (int) (fromPos.getX() + fromPos.getWidth());
                                    formPosY = (int) (fromPos.getY() + fromPos.getHeight() / 2);
                                    toPosX = (int) (toPos.getX());
                                    toPosY = (int) (toPos.getY());
                                } else {
                                    formPosX = (int) (fromPos.getX());
                                    formPosY = (int) (fromPos.getY() + fromPos.getHeight() / 2);
                                    toPosX = (int) (toPos.getX() + toPos.getWidth() / 2);
                                    toPosY = (int) (toPos.getY());
                                }
                            } else {
                                formPosX = (int) (fromPos.getX() + fromPos.getWidth() / 2);
                                formPosY = (int) (fromPos.getY());
                                toPosX = (int) (toPos.getX() + toPos.getWidth() / 2);
                                toPosY = (int) (toPos.getY() + toPos.getHeight());
                            }
                            drawAL(formPosX, formPosY, toPosX, toPosY, g);
                        }
                    }
                }
            }
        }
    }

    public static void drawPicture(String srcImgPath, String tarImgPath,
                                   String firstCustomer,
                                   List<String> scondCustomers,
                                   List<String> thirdCustomers,
                                   List<String> fourthCustomers,
                                   Map<String, List<String>> map) {
        Font font = new Font("微软雅黑", Font.PLAIN, 20);
        Color color = new Color(0, 0, 0, 255);
        Color colorLine = new Color(205, 85, 85, 255);

        try {
            File srcImgFile = new File(srcImgPath);
            Image srcImg = ImageIO.read(srcImgFile);
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);

            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(color);
            g.setFont(font);

            Map<String, Rectangle> pos = new HashMap<String, Rectangle>();

            int strlength = firstCustomer.length();
            int t = g.getFontMetrics().stringWidth("str");
            int x = srcImgWidth / 2 - strlength * t / 2 - 5;
            int y = 40;
            Rectangle rect0 = setTextLineUp(x, y, firstCustomer, g);
            pos.put(firstCustomer, rect0);

            drawName(scondCustomers, srcImgWidth, 200, t, g, pos);
            drawName(thirdCustomers, srcImgWidth, 600, t, g, pos);
            drawName(fourthCustomers, srcImgWidth, 1000, t, g, pos);

            g.setColor(colorLine);
            drawLine0(firstCustomer, g, pos, map);
            drawLine(scondCustomers, g, pos, map);
            drawLine(thirdCustomers, g, pos, map);
            drawLine(fourthCustomers, g, pos, map);

            g.dispose();

            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            System.out.println("画图完成");
            outImgStream.flush();
            outImgStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    Image srcImg = ImageIO.read(new FileInputStream(fnSrc) );//取源bai图
//    int width = 600; //假设要缩小du到600点像zhi素
//    int height = srcImg.getHeight(null)*600/srcImg.getWidth(null);//按比例，将高度dao缩减
//System.out.println("Width: "+srcImg.getWidth(null));// 这几行是调试用
//System.out.println("Height: "+srcImg.getHeight(null));
//System.out.println("Width2: "+width);
//System.out.println("Height2: "+height);
//    Image smallImg =srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);//缩小


    public static void main(String[] args) {
        String srcImgPath = "D:/pic.jpg";
        String tarImgPath = "D:/t.jpg";

        String firstCustomer = "测试总公司";
        List<String> scondCustomers = new ArrayList<String>();
        scondCustomers.add("A级测试子公司1");
        scondCustomers.add("A级测试子公司2");
        List<String> thirdCustomers = new ArrayList<String>();
        thirdCustomers.add("B级测试子公司1");
        thirdCustomers.add("B级测试子公司2");
        thirdCustomers.add("B级测试子公司3");
        thirdCustomers.add("B级测试子公司4");
        List<String> fourthCustomers = new ArrayList<String>();
        fourthCustomers.add("C级测试子公司1");
        fourthCustomers.add("C级测试子公司2");
        fourthCustomers.add("C级测试子公司3");
        fourthCustomers.add("C级测试子公司4");
        fourthCustomers.add("C级测试子公司5");
        fourthCustomers.add("C级测试子公司6");
        fourthCustomers.add("C级测试子公司7");
        fourthCustomers.add("C级测试子公司8");

        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> z0 = new ArrayList<String>();
        z0.add("A级测试子公司1");
        z0.add("A级测试子公司2");
        map.put("测试总公司", z0);

        List<String> a1 = new ArrayList<String>();
        a1.add("B级测试子公司1");
        a1.add("B级测试子公司2");
        map.put("A级测试子公司1", a1);

        List<String> a2 = new ArrayList<String>();
        a2.add("B级测试子公司3");
        a2.add("B级测试子公司4");
        map.put("A级测试子公司2", a2);

        List<String> b1 = new ArrayList<String>();
        b1.add("C级测试子公司1");
        b1.add("C级测试子公司2");
        map.put("B级测试子公司1", b1);

        List<String> b2 = new ArrayList<String>();
        b2.add("C级测试子公司3");
        b2.add("C级测试子公司4");
        map.put("B级测试子公司2", b2);

        List<String> b3 = new ArrayList<String>();
        b3.add("C级测试子公司5");
        b3.add("C级测试子公司6");
        map.put("B级测试子公司3", b3);

        List<String> b4 = new ArrayList<String>();
        b4.add("C级测试子公司7");
        b4.add("C级测试子公司8");
        map.put("B级测试子公司4", b4);

        List<String> c2 = new ArrayList<String>();
        c2.add("C级测试子公司1");
        c2.add("C级测试子公司6");
        c2.add("B级测试子公司4");
        map.put("C级测试子公司2", c2);

        List<String> c7 = new ArrayList<String>();
        c7.add("C级测试子公司5");
        c7.add("C级测试子公司6");
        c7.add("B级测试子公司2");
        map.put("C级测试子公司7", c7);

        drawPicture(srcImgPath, tarImgPath, firstCustomer, scondCustomers, thirdCustomers, fourthCustomers, map);
    }
}
