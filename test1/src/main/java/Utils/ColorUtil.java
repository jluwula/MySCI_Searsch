package Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class ColorUtil {

    private static String padZero(String str) {
        return str.length() == 1 ? "0" + str : str;
    }

    public static JSONObject getColor(){
        JSONObject color = new JSONObject();
        Random random = new Random();
        // 生成红色、绿色和蓝色的随机值
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // 将 RGB 值转换为 16 进制字符串
        String hexRed = Integer.toHexString(red);
        String hexGreen = Integer.toHexString(green);
        String hexBlue = Integer.toHexString(blue);

        // 确保字符串长度为2，并在需要时在前面添加0
        hexRed = padZero(hexRed);
        hexGreen = padZero(hexGreen);
        hexBlue = padZero(hexBlue);

        // 组合为最终的颜色字符串，例如 "#RRGGBB"
        String c = "#" + hexRed + hexGreen + hexBlue;
        color.put("color", c);
        return color;
    }
}
