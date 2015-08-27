package name.yumao.douyu.utils;
public class SttEncoder {
    public SttEncoder()
    {

    }
    private StringBuilder sb = new StringBuilder();
    ///获取编码后的String。
    public String GetResualt()
    {
        return sb.toString();
    }


    //清空已添加的数据项
    public void Clear()
    {
        sb.delete(0,sb.length());
    }

    ///添加Item
    public void AddItem(String value)
    {
        int sp = 0;
        char[] chars = value.toCharArray();

        while (sp < chars.length)
        {
            if (chars[sp] == '/')
            {
                sb.append("@S");
            }
            else if (chars[sp] == '@')
            {
                sb.append("@A");
            }
            else
            {
                sb.append(chars[sp]);
            }
            sp++;
        }
        sb.append('/');
    }
    public void AddItem(String key, String value)
    {

        int sp = 0;
        char[] chars = key.toCharArray();
        while (sp < chars.length)
        {
            if (chars[sp] == '/')
            {
                sb.append("@S");
            }
            else if (chars[sp] == '@')
            {
                sb.append("@A");
            }
            else
            {
                sb.append(chars[sp]);
            }
            sp++;
        }
        sb.append("@=");

        sp = 0;
        chars = value.toCharArray();
        while (sp < chars.length)
        {
            if (chars[sp] == '/')
            {
                sb.append("@S");
            }
            else if (chars[sp] == '@')
            {
                sb.append("@A");
            }
            else
            {
                sb.append(chars[sp]);
            }
            sp++;
        }
        sb.append('/');
   }


    ///添加Item，会将int类型的value自动转换为string。
    public void AddItem(int value)
    {
        this.AddItem(value+"");
    }
    public void AddItem(String key, int value)
    {
        this.AddItem(key, value+"");
    }


//    ///添加Item，会将int64类型的value自动转换为string。
//    public void AddItem(int64 value)
//    {
//        this.AddItem(value.ToString());
//    }
//    public void AddItem(string key, Int64 value)
//    {
//        this.AddItem(key, value.ToString());
//    }


    ///添加Item，会将double类型的value自动转换为string。
    public void AddItem(double value)
    {
        this.AddItem(value+"");
    }

    public void AddItem(String key, double value)
    {
        this.AddItem(key, value+"");
    }
}