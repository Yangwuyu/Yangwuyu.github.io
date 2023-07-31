package com.nanhua.graduateyzq;

import com.nanhua.retrieval.entity.User;
import com.nanhua.retrieval.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class GraduateYzqApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void testMapper() {
      List<User> user = userMapper.selectList(null);
      user.forEach(System.out::println);
    }

}
