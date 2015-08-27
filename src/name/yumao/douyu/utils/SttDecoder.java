package name.yumao.douyu.utils;

import java.util.HashMap;

import name.yumao.douyu.vo.SttEncodingItem;

public class SttDecoder {
	HashMap<String,String> itemsMap = new HashMap<String,String>();
    public int Parse(String str)
    {
        SttEncodingItem item = new SttEncodingItem();
        int sp = 0;
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        while (sp < chars.length)
        {
            if (chars[sp] == '/')    //结束符
            {
                if (item.Key == null)
                {
                    item.Key = "";
                }
                item.Value = sb.toString();
                sb.delete(0, sb.length());
                itemsMap.put(item.Key, item.Value);
                item = new SttEncodingItem();
            }
            else if (chars[sp] == '@')
            {
                sp++;
                if (sp >= chars.length)
                {

                }
                else if (chars[sp] == 'A')
                {
                    sb.append('@');
                }
                else if (chars[sp] == 'S')  //斜线
                {
                    sb.append('/');
                }
                else if (chars[sp] == '=')  //影射
                {
                    item.Key = sb.toString();;
                    sb.delete(0, sb.length());
                }
                else    //无非识别的转义符删掉，可以尽早发现bug。待定 \0会正常结束 
                {
                    //assert(0);
                };
            }
            else
            {
                sb.append(chars[sp]);
            }
            sp++;
        }

        if (sp > 0 && sp == chars.length && chars[sp-1] != '/')  //末尾漏掉斜线/的情况
        {
            if (item.Key == null)
            {
                item.Key = "";
            }
            item.Value = sb.toString();
            sb.delete(0, sb.length());
            itemsMap.put(item.Key, item.Value);
            item = new SttEncodingItem();
        }
        return itemsMap.size();
    }
    
    public void Clear()
    {
        this.itemsMap.clear();
    }

    public String GetItem(String key)
    {
        return itemsMap.get(key);
    }
}