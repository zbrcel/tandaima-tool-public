package com.tandaima.tool.utils;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zbrcel@gmail.com
 * @Date 2021-11-09
 * @Description
 */
public class ParamUtils {
    private static final Logger log = LoggerFactory.getLogger(ParamUtils.class);

    /**
     * 获取组装后的手机号
     * @param phone 原手机号
     * @return 组装后的手机号
     */
    public static String getPhone(String phone){
        try{
            return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
        }catch (Exception e){
            return phone;
        }
    }

    /**
     * 脱敏邮箱
     * @param email 邮箱
     * @return 结果
     */
    public static String getEmail(String email){
        //*符号个数
        StringBuilder star = new StringBuilder();
        if(StringUtils.isNotEmpty(email) && email.length() >=3){
            int emailLength = email.length()-3;
            for (int i = 0; i < emailLength; i++) {
                star.append("*");
            }
            email = email.substring(0,3)+star;
        }
        return email;
    }
    /**
     * 获取组装后的手机号 传前7，隐藏后4位
     * @param phone 原手机号
     * @return 组装后的手机号
     */
    public static String getPhoneLast4(String phone){
        return phone.substring(0, 7) + "****";
    }

    /**
     * 获取组装后的身份证号码
     * @param idCard 原身份证号码
     * @return 组装后的身份证号码
     */
    public static String getIdCard(String idCard){
        if(StringUtils.isEmpty(idCard)){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(idCard, 0, 3);
        if(idCard.length() == 18){
            builder.append("***********");
        }else{
            builder.append("*********");
        }
        builder.append(idCard, idCard.length()-4, idCard.length());
        return builder.toString();
    }
    /**
     * 获取组装后的姓名
     * @param name 原姓名
     * @return 组装后的姓名
     */
    public static String getName(String name){
        StringBuilder builder = new StringBuilder();
        if(name.length()>2){
            builder.append(name.charAt(0));
            for(int i=3;i<=name.length();i++){
                builder.append("*");
            }
            builder.append(name.substring(name.length()-1));
        }else if(name.length() == 2){
            builder.append(name.charAt(0));
            builder.append("*");
        }else{
            builder.append(name);
        }
        return builder.toString();
    }
    /**
     * 获取组装后的姓名
     * @param name 原姓名
     * @return 组装后的姓名
     */
    public static String getFirstNameAndXing(String name){
        StringBuilder builder = new StringBuilder();
        if(name.length()>2){
            builder.append(name.charAt(0));
            for(int i=0;i<name.length()-1;i++){
                builder.append("*");
            }
        }else if(name.length() == 2){
            builder.append(name.charAt(0));
            builder.append("*");
        }else{
            builder.append(name);
        }
        return builder.toString();
    }

    /**
     * 获取组装后的姓名
     * @param name 原姓名
     * @param sex 性别(0男 1女)
     * @return 组装后的姓名
     */
    public static String getFirstName(String name,String sex){
        name = name.substring(0,1);
        name = name+(sex.equals("0")?"先生":"女士");
        return name;
    }

    public static String getFirstNameNick(String name,String sex){
        name = name.substring(0,1);
        name = name+(sex.equals("男")?"先生":"女士");
        return name;
    }
    /**
     * 获取组装后的姓名
     * @param name 原姓名
     * @return 组装后的姓名
     */
    public static String getFirstName(String name){
        if(StringUtils.isEmpty(name)){
            return "";
        }
        name = name.substring(0,1);
        return name;
    }

    /**
     * 获取http拼接参数
     * @param params map参数
     * @return 参数字符串
     */
    public static String getHttpParams(boolean isFirst,Map<String, String> params){
        String param = "";
        if(params != null && params.size() !=0){
            StringBuilder paramStr = new StringBuilder(isFirst?"?":"");
            params.forEach((key,value)->{
                paramStr.append(key).append("=").append(value).append("&");
            });
            String t = paramStr.toString();
            if(t.endsWith("&")){
                t = t.substring(0, t.length()-1);
            }
            param+=t;
        }
        return param;
    }

    /**
     * base64转化成 inputStream
     * @param base64 base64字符串
     * @return InputStream
     */
    public static InputStream base64ToInputStream(String base64) {
        ByteArrayInputStream stream = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            stream = new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream;
    }
    /**
     * MD5加密
     * @param plainText 加密内容
     * @return 加密字符串
     */
    public static String md5(String plainText) {
        byte[] secretBytes;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        StringBuilder md5code = new StringBuilder();
        for (byte t : secretBytes) {
            String s = Integer.toHexString(t & 0xFF);
            if (s.length() == 1) {
                s = "0" + s; // 注意此行，如果只有一位，在首位加0
            }
            md5code.append(s);
        }
        return md5code.toString();
    }

    /**
     * 获取当前时间戳
     * @param date 当前时间
     * @return 时间戳
     */
    public static int getTimestamp(Date date){
        if(date == null){
            date = new Date();
        }
        return (int) (date.getTime() / 1000);
    }

    /**
     * 获取随机字符串
     * @param length 多少位
     * @return 随机字符串
     */
    public static String getRandomStr(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     *  随机生成 6 位数字
     */
    public static String getRandomCode(){
        return String.valueOf((int)((Math.random()*9+1)*100000));
    }

    /**
     * 参数ASCII码从小到大排序（字典序）
     * @param param 参数
     * @return 排序后的参数
     */
    public static List<Map.Entry<String, Object>> getSortMapASCII(Map<String, Object> param){
        List<Map.Entry<String, Object>> infoIds = new ArrayList<>(param.entrySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        infoIds.sort(Map.Entry.comparingByKey());
        return infoIds;
    }

    /**
     * @param str Unicode字符串
     * @throws Exception
     */
    public static String getUnicodeToString(String str) throws Exception{
        try{
            Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
            Matcher matcher = pattern.matcher(str);
            char ch;
            while (matcher.find()) {
                ch = (char) Integer.parseInt(matcher.group(2), 16);
                str = str.replace(matcher.group(1), ch + "");
            }
            return str;
        }catch (Exception e){
            return str;
        }
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz){
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    /**
     * 百分比比较方法 第一个值大于等于第二个值
     * @param startRate 第一个百分比
     * @param endRate 第二个百分比
     * @return true/false
     */
    public static boolean startCompareWithEndRate(String startRate,String endRate){
        try{
            String startData =startRate.replace("%","");
            String endData =endRate.replace("%","");
            int startDateNumber = Integer.parseInt(startData);
            int endDataNumber = Integer.parseInt(endData);
            return startDateNumber >= endDataNumber;
        }catch (Exception e){
            log.error("百分比比较方法 异常",e);
            return false;
        }
    }

    /**
     * 根据身份证号码获取年龄
     * @param idCard 身份证号码
     * @return 年龄
     */
    public static int getIdCardAge(String idCard){
        int age = 0;
        if(idCard == null || "".equals(idCard) ){
            return age;
        }
        if (idCard.length() != 15 && idCard.length() != 18){
            return age;
        }
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH)+1;
        int dayNow = cal.get(Calendar.DATE);

        int year = Integer.parseInt(idCard.substring(6, 10));
        int month = Integer.parseInt(idCard.substring(10,12));
        int day = Integer.parseInt(idCard.substring(12,14));

        if ((month < monthNow) || (month == monthNow && day<= dayNow) ){
            age = yearNow - year;
        }else {
            age = (yearNow - year-1);
        }

        return age;
    }
    /**
     * 根据身份证号判断性别 0男 1女
     * @param idCard 身份证号码
     * @return 性别
     */
    public static String getSex(String idCard) {
        char c;
        if(idCard.length() == 18){
            //如果身份证号18位，取身份证号倒数第二位
            c = idCard.charAt(idCard.length() - 2);
        }else{
            //如果身份证号15位，取身份证号最后一位
            c = idCard.charAt(idCard.length() - 1);
        }
        int gender = Integer.parseInt(String.valueOf(c));
        if(gender % 2 == 1){
            return "0";
        }else{
            return "1";
        }
    }

    /**
     * 获取出生日期  yyyy年MM月dd日
     * @param idCard 身份证号码
     * @return 出生日期
     */
    public static String getBirthDay(String idCard){
        String birthday="";
        String year="";
        String month="";
        String day="";
        if (StringUtils.isNotBlank(idCard)){
            //15位身份证号
            if (idCard.length() == 15){
                // 身份证上的年份(15位身份证为1980年前的)
                year = "19" + idCard.substring(6, 8);
                //身份证上的月份
                month = idCard.substring(8, 10);
                //身份证上的日期
                day= idCard.substring(10, 12);
                //18位身份证号
            }else if(idCard.length() == 18){
                // 身份证上的年份
                year = idCard.substring(6).substring(0, 4);
                // 身份证上的月份
                month = idCard.substring(10).substring(0, 2);
                //身份证上的日期
                day=idCard.substring(12).substring(0,2);
            }
            birthday=year+"-"+month+"-"+day;
        }
        return birthday;
    }

    /**
     * 出生日期计算年龄
     * @param birthDayStr 出生日期
     * @return 年龄
     */
    public static  int getBirthAge(String birthDayStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDay = DateUtils.parse(format, birthDayStr);
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
           return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            }else{
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

    /**
     * 根据年龄计算出生日期
     * @param age 年龄
     * @return 出生日期
     */
    public static Date getBirthDate(int age) {
        String yearStr = DateUtils.parseDateToStr(DateUtils.YYYY, new Date());
        int year = Integer.parseInt(yearStr) - age;
        String monthDay = DateUtils.parseDateToStr(DateUtils.MM_DD, new Date());
        String birthDateStr = year + "-" + monthDay;
        return DateUtils.parse(DateUtils.YMD, birthDateStr);
    }

    /**
     * 获取文件名称
     * @param sig 文件标识
     * @return 文件名称
     */
    public static String getFilename(String sig){
        String newDate = DateUtils.format(DateUtils.YMD_OR,new Date());
        return newDate+"/"+sig+"/"+DateUtils.format(DateUtils.YMDHMS,new Date());
    }

    /**
     * 获取两个数之间的随机数
     * @param low 数字下限
     * @param high 数字上限
     * @return 随机数
     */
    public static int getRandom(int low,int high){
        return (int) (Math.random() * (high+1 - low)) + low;
    }
    /**
     * 获取大于等于min，小于max的随机小数
     * @param min
     * @param max
     * @return
     */
    public static String getRandomToString(Double min, Double max){
        return String.format("%.1f", (Math.random() * (max - min) + min));
    }

    /**
     * 获取随机数
     * @return 随机数
     */
    @SafeVarargs
    public static<T> T getRandomValue(T ...values){
        return values[(int) (Math.random() * (values.length))];
    }

    /**
     * String数组转Long数组
     * @param stringArray  String数组
     * @return Long数组
     */
    public static Long[] stringToLong(String[] stringArray){
        if(stringArray == null){
            return new Long[]{};
        }
        Long[] longArray=new Long[stringArray.length];
        for (int i=0;i<stringArray.length;i++) {
            try {
                longArray[i] = Long.parseLong(stringArray[i]);
            } catch (NumberFormatException ignored) {
            }
        }
        return longArray;
    }

    /**
     * 数组转string逗号拼接
     * @param ids 数字数组
     * @return 字符串
     */
    public static String longToString(Long[] ids){
        if(ids != null){
            StringBuilder idBuilder = new StringBuilder();
            for(long id:ids){
                idBuilder.append(id).append(",");
            }
            return idBuilder.deleteCharAt(idBuilder.length()-1).toString();
        }
        return "";
    }
    /**
     * 去除字符串中的html标签.
     * <p>
     * <pre>
     * StringUtils.replaceHtml(null)  = ""
     * StringUtils.replaceHtml("")    = ""
     * StringUtils.replaceHtml("<td>content</td>") = "content"
     * StringUtils.replaceHtml("<>content</td>") = ""
     * </pre>
     * @param html 要处理的字符串，可以为 null
     * @return String
     *
     */
    public static String replaceHtml(String html) {
        if (StringUtils.isBlank(html)) {
            return StringUtils.EMPTY;
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll(StringUtils.EMPTY);
        s = s.replaceAll("<[^>]*>", "");
        return s;
    }

    /**
     * 获取参数
     */
    public static <T> T getClassParam(String jsonParam, Class<T> clazz){
        T classParam;
        try{
            classParam = JSON.parseObject(jsonParam).toJavaObject(clazz);
            return classParam;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 获取用户授权协议
     */
    public static String getUserGranProtocol(String protocolContent,Boolean isLink,String name){
        if(isLink){
            return protocolContent;
        }
        Calendar now = Calendar.getInstance();
        return protocolContent
                .replace("#{name}",name)
                .replace("#{grantYear}", String.valueOf(now.get(Calendar.YEAR))
                .replace("#{grantMonth}",String.valueOf(now.get(Calendar.MONTH) + 1)))
                .replace("#{grantDay}",String.valueOf(now.get(Calendar.DAY_OF_MONTH)));
    }

    /**
     * 将驼峰转为下划线
     */
    public static String hump_(String str) {
        Pattern compile = Pattern.compile("[A-Z]");
        Matcher matcher = compile.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            matcher.appendReplacement(sb,  "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * InputStream转二进制
     * @param inStream 流
     * @return
     * @throws IOException
     */
    public static byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        return swapStream.toByteArray();
    }
    /***
     * 利用Apache的工具类实现SHA-256加密
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256Str(byte[] str){
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str);
            encdeStr = Hex.encodeHexString(hash);
        } catch (Exception e) {
            return null;
        }
        return encdeStr;
    }


    /**
     * java对象转Map
     * @param param 参数
     * @param <T> 类
     * @return 结果
     */
    public static<T>  Map<String,String> objectMap(T param){
        return JSON.parseObject(JSON.toJSONString(param),Map.class);
    }

    /**
     * 对象转化为 Map
     * @param param 要转换的对象
     * @return map 转换结果
     * @throws IllegalAccessException 访问权限的异常
     */
    public static<T> Map<String, String> objectToTreeMap(T param) throws IllegalAccessException {
        if (param == null) {
            return null;
        }
        Map<String, String> map = new TreeMap<>();
        Field[] declaredFields = param.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (null != field.get(param)) {
                Object value = field.get(param);
                map.put(field.getName(), value == null?null:String.valueOf(value));
            }
        }
        return map;
    }

    /**
     * 去除手机号格式
     * @param phone 源手机号
     * @return 去除格式的手机号
     */
    public static String getRemoveFormatPhone(String phone){
        if(StringUtils.isNotEmpty(phone)){
            if(phone.length() > 3){
                String prefix1 = phone.substring(0,3);
                String prefix2 = phone.substring(0,2);
                if(prefix1.equals("+86")){
                    phone = phone.substring(3);
                }
                if(prefix2.equals("86")){
                    phone = phone.substring(2);
                }
            }
            return phone.replaceAll(" ","").replaceAll("-","");
        }
        return phone;
    }

    /**
     * 分割list数据
     */
    public static  <T>List<List<T>> getExecSqlData(List<T> dataList, int dataSize){
        int totalCount = dataList.size();
        List<List<T>> execList = new ArrayList<>();
        //计算总共的页数
        int totalPage = (totalCount%dataSize)==0?(totalCount/dataSize):(totalCount/dataSize)+1;
        for(int i=0;i<totalPage;i++){
            int fromIndex = i*dataSize;
            int toIndex = dataSize+fromIndex;
            toIndex= Math.min(toIndex, totalCount);
            execList.add(dataList.subList(fromIndex,toIndex));
        }
        return execList;
    }

    /**
     * 替换参数
     * @param str 字符串
     * @param value 替换值
     * @return 替换后的字符串
     */
    public static String replaceParam(String str,String ...value){
        if(value == null){
            return str;
        }
        String content = str;
        for (int i=0;i<value.length;i++) {
            content = content.replace("#{"+i+"}",value[i]);
        }
        return content;
    }

    /**
     * 判断是否为数字
     */
    public static boolean isNumber(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

}
