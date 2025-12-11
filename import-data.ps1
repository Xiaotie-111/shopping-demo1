# 导入商品数据的PowerShell脚本

$mysqlPath = "mysql"
$username = "root"
$password = "123456"
$database = "platform"

# 创建商品数据SQL语句数组
$productSqls = @(
    "INSERT INTO product (name, code, category_id, category_name, brand, description, price, stock, status, sales) VALUES ('iPhone 17 Pro Max', 'IPH17PM', 2, '手机', 'Apple', '全新iPhone 17 Pro Max，搭载A19芯片', 9999.00, 100, 1, 50);",
    "INSERT INTO product (name, code, category_id, category_name, brand, description, price, stock, status, sales) VALUES ('Apple Watch Series 10', 'AW10', 5, '配件', 'Apple', 'Apple Watch Series 10，健康监测新体验', 3999.00, 150, 1, 80);",
    "INSERT INTO product (name, code, category_id, category_name, brand, description, price, stock, status, sales) VALUES ('MacBook Air M3', 'MBA3', 3, '电脑', 'Apple', 'MacBook Air M3，轻薄性能强', 8999.00, 80, 1, 40);",
    "INSERT INTO product (name, code, category_id, category_name, brand, description, price, stock, status, sales) VALUES ('AirPods Pro 2', 'APP2', 5, '配件', 'Apple', 'AirPods Pro 2，主动降噪升级', 1899.00, 200, 1, 120);",
    "INSERT INTO product (name, code, category_id, category_name, brand, description, price, stock, status, sales) VALUES ('小米15 Ultra', 'XM15U', 2, '手机', '小米', '小米15 Ultra，徕卡影像', 5999.00, 120, 1, 60);",
    "INSERT INTO product (name, code, category_id, category_name, brand, description, price, stock, status, sales) VALUES ('华为Mate 70 Pro', 'HWM70P', 2, '手机', '华为', '华为Mate 70 Pro，鸿蒙OS 4.0', 6999.00, 90, 1, 55);",
    "INSERT INTO product (name, code, category_id, category_name, brand, description, price, stock, status, sales) VALUES ('联想拯救者Y9000P', 'LZ9000P', 3, '电脑', '联想', '联想拯救者Y9000P，游戏本首选', 7999.00, 70, 1, 35);",
    "INSERT INTO product (name, code, category_id, category_name, brand, description, price, stock, status, sales) VALUES ('三星Galaxy S24 Ultra', 'SGS24U', 2, '手机', '三星', '三星Galaxy S24 Ultra，S Pen内置', 8999.00, 110, 1, 45);"
)

# 执行每条SQL语句
foreach ($sql in $productSqls) {
    Write-Host "Executing: $sql"
    $command = "$mysqlPath -u $username -p$password -D $database -e `"$sql`""
    Invoke-Expression $command
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Error executing SQL: $sql"
    } else {
        Write-Host "Successfully executed SQL: $sql"
    }
}

Write-Host "Data import completed!"