package xihu.chen.workboo;

import xihu.chen.workboo.down.FieldTitle;

public class StudentEntiy {
    @FieldTitle( "姓名")
    private String name;
    @FieldTitle("年龄")
    private Integer age;
    @FieldTitle("家庭地址")
    private String addr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
