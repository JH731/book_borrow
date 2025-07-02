CREATE TABLE User (
    id INT PRIMARY KEY AUTO_INCREMENT,         -- 用户ID，主键，自增
    name VARCHAR(100),                            -- 姓名
    password VARCHAR(255),                        -- 加密后的密码
    phone VARCHAR(20),                            -- 手机号
    sex CHAR(1),                                  -- 性别，0 女，1 男
    avatar VARCHAR(255),                          -- 头像URL
    status INT DEFAULT 1,                         -- 用户状态，1=正常，0=禁用等
    create_Time DATETIME DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    update_Time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新时间
    update_User BIGINT,                            -- 最后修改人ID（管理员）
    Max_Borrow INT DEFAULT 3                       -- 最大可借阅数量，默认3本
);

CREATE TABLE Admin(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    password VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Back(
     id INT PRIMARY KEY AUTO_INCREMENT,
     brid INT,
     status INT
);

CREATE TABLE Borrow (
    id INT PRIMARY KEY AUTO_INCREMENT,    -- 主键ID，自增
    status TINYINT,
    user_Id INT,                         -- 借阅ID，外键可关联Borrow表
    book_Id INT,                           -- 书籍ID，外键可关联Book表
    start_Time DATETIME,
    end_Time DATETIME,
    return_Time DATETIME
);

CREATE TABLE Category (
    id INT PRIMARY KEY AUTO_INCREMENT,          -- 主键ID，自增
    type INT,                                      -- 类型: 1文学 2科学
    name VARCHAR(100),                             -- 分类名称
    sort INT DEFAULT 0,                            -- 顺序
    status INT DEFAULT 1,                          -- 分类状态: 0禁用 1启用
    create_Time DATETIME DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    update_Time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新时间
    create_User BIGINT,                             -- 创建人
    update_User BIGINT                              -- 修改人
);

CREATE TABLE Book (
    id INT PRIMARY KEY AUTO_INCREMENT,          -- 主键ID，自增
    name VARCHAR(200),                             -- 书籍名称
    categoryId INT,                             -- 分类ID（可关联Category表）
    author VARCHAR(100),                           -- 作者
    publish VARCHAR(100),                          -- 出版社
    edition VARCHAR(50),                           -- 版本（第几版）
    image VARCHAR(255),                            -- 图片链接                           -- 描述信息
    status INT DEFAULT 1,                          -- 借阅状态: 0=不可借阅, 1=可借阅
    stock INT DEFAULT 0,                           -- 库存数量
    create_Time DATETIME DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    update_Time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新时间
    create_User BIGINT,                             -- 创建人
    update_User BIGINT                              -- 修改人
);

CREATE TABLE Employee (
    id INT PRIMARY KEY AUTO_INCREMENT,          -- 主键ID，自增
    name VARCHAR(100),                             -- 姓名
    password VARCHAR(255),                         -- 密码（建议加密存储）
    sex CHAR(1),                                   -- 性别
    status INT DEFAULT 1,                          -- 状态（启用/禁用等）
    create_Time DATETIME DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    update_Time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新时间
    create_User BIGINT,                             -- 创建人ID
    update_User BIGINT                              -- 修改人ID
);

INSERT INTO admin (name, password) VALUES
    ('admin', 'bfd81ee3ed27ad31c95ca75e21365973')
