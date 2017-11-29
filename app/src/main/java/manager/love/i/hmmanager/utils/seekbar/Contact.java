package manager.love.i.hmmanager.utils.seekbar;

/**
 * @Author: duke
 * @DateTime: 2016-08-12 14:55
 * @Description:
 */
public class Contact implements Comparable<Contact> {
    public String name;         //联系人名称
    public String pinYin;       //联系人拼音
    public String firstPinYin;  //联系人-姓拼音-首字母

    @Override
    public int compareTo(Contact another) {
        return firstPinYin.toUpperCase().compareTo(another.firstPinYin.toUpperCase());
    }
}