-- 插入初始管理员用户（密码：admin123）
INSERT INTO user (username, password, nickname, role) VALUES ('admin', '$2a$10$7JB720yubVSga0ZCAwQ3fu91p7l6ZdO2MBh7e/668.1GQ7wJ9h4E6', '管理员', 'admin');
-- 插入测试用户（密码：user123）
INSERT INTO user (username, password, nickname, role) VALUES ('user', '$2a$10$7JB720yubVSga0ZCAwQ3fu91p7l6ZdO2MBh7e/668.1GQ7wJ9h4E6', '测试用户', 'user');

-- 插入初始分类数据
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('电子产品', 0, 1, 1, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('手机', 1, 2, 1, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('电脑', 1, 2, 2, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('平板', 1, 2, 3, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('配件', 1, 2, 4, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('家用电器', 0, 1, 2, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('冰箱', 6, 2, 1, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('洗衣机', 6, 2, 2, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('空调', 6, 2, 3, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('电视', 6, 2, 4, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('家具', 0, 1, 3, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('沙发', 11, 2, 1, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('床', 11, 2, 2, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('衣柜', 11, 2, 3, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('书桌', 11, 2, 4, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('服装', 0, 1, 4, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('男装', 16, 2, 1, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('女装', 16, 2, 2, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('童装', 16, 2, 3, 1);
INSERT INTO category (name, parent_id, level, sort, status) VALUES ('鞋靴', 16, 2, 4, 1);

-- 插入热门推荐商品数据
INSERT INTO product (name, code, category_id, category_name, brand, description, price, stock, status, sales) VALUES 
('iPhone 17 Pro Max', 'IPH17PM', 2, '手机', 'Apple', '全新iPhone 17 Pro Max，搭载A19芯片', 9999.00, 100, 1, 50),
('Apple Watch Series 10', 'AW10', 5, '配件', 'Apple', 'Apple Watch Series 10，健康监测新体验', 3999.00, 150, 1, 80),
('MacBook Air M3', 'MBA3', 3, '电脑', 'Apple', 'MacBook Air M3，轻薄性能强', 8999.00, 80, 1, 40),
('AirPods Pro 2', 'APP2', 5, '配件', 'Apple', 'AirPods Pro 2，主动降噪升级', 1899.00, 200, 1, 120),
('小米15 Ultra', 'XM15U', 2, '手机', '小米', '小米15 Ultra，徕卡影像', 5999.00, 120, 1, 60),
('华为Mate 70 Pro', 'HWM70P', 2, '手机', '华为', '华为Mate 70 Pro，鸿蒙OS 4.0', 6999.00, 90, 1, 55),
('联想拯救者Y9000P', 'LZ9000P', 3, '电脑', '联想', '联想拯救者Y9000P，游戏本首选', 7999.00, 70, 1, 35),
('三星Galaxy S24 Ultra', 'SGS24U', 2, '手机', '三星', '三星Galaxy S24 Ultra，S Pen内置', 8999.00, 110, 1, 45);
