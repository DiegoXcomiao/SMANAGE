package org.manage.service;

/**
 * @author: Diego
 * @date: 2020/8/24 14:49
 * @Des:
 */
public class a {
    public static void main(String[] args) {
        int x = 0;
        int y = 0;
        int z = 0;

        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                for (int k = 0; k < 1000; k++) {
                    x = i;
                    y = j;
                    z = k;
                    if (x + y + z == 192) {
                        x = x - y;
                        y = y + y;
                        y = y - z;
                        z = z + z;
                        z = z - x;
                        x = x + x;
                        if (x == y && z == x) {
                            System.out.println(i);
                            System.out.println(j);
                            System.out.println(k);
                        }
                    }
                }
            }
        }
    }
}
