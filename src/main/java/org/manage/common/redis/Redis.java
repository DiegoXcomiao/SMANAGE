package org.manage.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Diego
 * @date: 2020/7/2 14:13
 * @Des:
 */
@Component
public class Redis<C> {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String getName(C c) {
        String names[] = c.getClass().toString().split("\\.");
        return names[names.length - 1];
    }

    public synchronized String getIndex(C c) throws Exception {
        String name = getName(c);

        ValueOperations<String, String> ops1 = stringRedisTemplate.opsForValue();
        String index = ops1.get(name);
        if (index == null) {
            index = "1";
            ops1.set(name, index);
        } else {
            index = Integer.toString(Integer.parseInt(index) + 1);
            ops1.set(name, index);
        }
        return index;
    }

    private void setRole(C c, String id) throws Exception {
        String name = getName(c);

        ValueOperations ops2 = redisTemplate.opsForValue();
        List<String> l = (List<String>) ops2.get(name + "Array");
        if (l == null) {
            List<String> l0 = new ArrayList<String>();
            l0.add(id);
            ops2.set(name + "Array", l0);
        } else {
            l.add(id);
            ops2.set(name + "Array", l);
        }
    }

    private void deleteRole(C c, String id) throws Exception {
        String name = getName(c);

        ValueOperations ops2 = redisTemplate.opsForValue();
        List<String> l = (List<String>) ops2.get(name + "Array");
        if (l != null) {
            l.remove(id);
            ops2.set(name + "Array", l);
        }
    }

    public void insert(C c, String id) throws Exception {
        String name = getName(c);

        ValueOperations ops2 = redisTemplate.opsForValue();
        ops2.set(name + id, c);
        setRole(c, id);
    }

    public void delete(C c, String id) throws Exception {
        String name = getName(c);

        redisTemplate.delete(name + id);
        deleteRole(c, id);
    }

    public void update(C c, String id) throws Exception {
        String name = getName(c);

        ValueOperations ops2 = redisTemplate.opsForValue();
        C c1 = (C)ops2.get(name + id);
        if (c1 != null) {
            ops2.set(name + id, c);
        }
    }

    public List<Object> query(C c, Integer startIndex, Integer num) {
        if (startIndex < 1) {
            return null;
        }
        if (num < 1) {
            return null;
        }

        String name = getName(c);

        ValueOperations ops2 = redisTemplate.opsForValue();

        List<String> l = (List<String>) ops2.get(name + "Array");

        if (l == null) {
            return null;
        } else {
            List<Object> res = new ArrayList<Object>();
            for (int i = startIndex - 1; i < startIndex + num; i++) {
                if (i + 1 > l.size()) {
                    break;
                }
                Object o = ops2.get(name + l.get(i));
                if (o != null) {
                    res.add(o);
                }
            }
            return res;
        }
    }
}
