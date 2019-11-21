package com.android.constrainlayoutdemo.annotation;

@DBTable(name = "MEMBER")
public class Member {
    //主键id
    @SQLString(name = "ID", value = 50, constraint = @Constraints(primaryKey = true))
    private String id;

    @SQLString(name = "NAME", value = 30)
    private String name;

    @SQLInteger(name = "AGE")
    private int age;

    @SQLString(name = "DESCRIPTION", value = 150, constraint = @Constraints(allowNull = true))
    private String description;//个人描述
}
