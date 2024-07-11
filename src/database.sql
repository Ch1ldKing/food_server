-- 创建序列
CREATE SEQUENCE ingredient_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- 创建表结构并绑定序列到id列
CREATE TABLE Ingredients (
                             id INTEGER NOT NULL DEFAULT nextval('ingredient_id_seq'),
                             name VARCHAR(255) NOT NULL,
                             category VARCHAR(50) NOT NULL,
                             PRIMARY KEY (id)
);

-- 插入假数据
INSERT INTO Ingredients (name, category) VALUES
                                             ('Carrot', 'vegetable'),
                                             ('Potato', 'vegetable'),
                                             ('Chicken', 'meat'),
                                             ('Beef', 'meat'),
                                             ('Rice', 'grain'),
                                             ('Wheat', 'grain');
