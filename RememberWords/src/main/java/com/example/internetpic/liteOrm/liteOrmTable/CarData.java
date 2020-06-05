package com.example.internetpic.liteOrm.liteOrmTable;


import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;


@tTable(\"noteDaa")
public class CarData implements Serializable {
    @PrimaryKey(AssignType.BY_MYSELF)
    private String word;//设置为主键，避免数据重复插入
    private String mobile;//

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
