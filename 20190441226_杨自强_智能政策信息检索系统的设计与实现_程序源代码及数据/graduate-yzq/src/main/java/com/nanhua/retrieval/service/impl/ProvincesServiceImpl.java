package com.nanhua.retrieval.service.impl;

import com.nanhua.retrieval.entity.Provinces;
import com.nanhua.retrieval.mapper.ProvincesMapper;
import com.nanhua.retrieval.service.IProvincesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanhua.retrieval.service.IProvincesService;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzq
 * @since 2023-05-02
 */
@Service
public class ProvincesServiceImpl extends ServiceImpl<ProvincesMapper, Provinces> implements IProvincesService {

    @Override
    public Map<Integer, String> getProvinces() {
        // 连接数据库
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/policy_info_retrieval", "root", "yyy77777");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // 查询数据
        String query = "SELECT province_id, province_name FROM provinces";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // 将数据装载进Map对象
        Map<Integer, String> provinceMap = new HashMap<>();
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            int id = 0;
            try {
                id = rs.getInt("province_id");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            String name = null;
            try {
                name = rs.getString("province_name");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            provinceMap.put(id, name);
        }

        // 打印Map对象内容（仅做示例）
        for (Map.Entry<Integer, String> entry : provinceMap.entrySet()) {
            System.out.println("Province ID: " + entry.getKey() + ", Province Name: " + entry.getValue());
        }

        // 关闭资源
        try {
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return provinceMap;
    }

}

