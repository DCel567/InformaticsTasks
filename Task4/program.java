import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.util.ArrayList;

public class program{
    public static void main(String[] args) {
        try{
            //task 1--------------------------------
            //a
            Scanner in = new Scanner(System.in);
            String inputData = "";
    
            System.out.println("Input string and program will check if it is abcd111111102019");
            inputData = in.nextLine();
    
            Pattern pat = Pattern.compile("abcd1{7}02019");
            Matcher matcher = pat.matcher(inputData);
            if (matcher.matches()) System.out.println("It matches");
            else System.out.println("Doesn't matches.");

        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        try {
            ArrayList<Integer> numbers = new ArrayList<>();
            String inputData = "";
            Scanner in = new Scanner(System.in);
            System.out.println("Input another string and program will find all the numbers.");
            inputData = in.nextLine();

            Pattern pat = Pattern.compile("\\d+");
            Matcher matcher = pat.matcher(inputData);

            while(matcher.find()){
                numbers.add(Integer.parseInt(inputData.substring(matcher.start(), matcher.end())));
            }

            int max = 0, sum = 0, j = 0;
            for(int i = 0; i < numbers.size(); i++){
                if (numbers.get(i) >= max){
                    max = numbers.get(i);
                    j = i;
                }
                sum += numbers.get(i);
                System.out.print(numbers.get(i) + ", ");
            }
            System.out.println();
            System.out.println("Max element is " + max + ". And its's number is " + j);
            System.out.println("Sum of elements is " + sum + ".");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            ArrayList<Double> numbers = new ArrayList<>();
            String inputData = "";
            Scanner in = new Scanner(System.in);
            System.out.println("Input another string and program will find all the numbers.");
            inputData = in.nextLine();

            Pattern pat = Pattern.compile("\\d+[.,]?\\d*");
            Matcher matcher = pat.matcher(inputData);

            
            while(matcher.find()){
                String betterString = inputData;
                Pattern p = Pattern.compile(",");
                Matcher m = p.matcher(inputData.substring(matcher.start(), matcher.end()));
                if (m.find()){
                    betterString = inputData.replace(',', '.');
                }
                numbers.add(Double.parseDouble(betterString.substring(matcher.start(), matcher.end())));
            }
            
            for(int i = 0; i < numbers.size(); i++){
                System.out.print(numbers.get(i) + " ");
            }
            System.out.println();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

         try {
             String inputData = "";
             Scanner in = new Scanner(System.in);
             System.out.println("Input string where 10-chared and more words will be replaced with *:");
             inputData = in.nextLine();
             Pattern pat = Pattern.compile("\\S{10,}");
            //-------------------------------------
            Matcher matcher = pat.matcher(inputData);

            String rep1 = matcher.replaceAll("*");
            matcher.reset();
            System.out.println(rep1);
            //-------------------------------------

            String rep2 = inputData;
            StringBuffer sb = new StringBuffer();
            while (matcher.find()){
                char firstLetter = rep2.charAt(matcher.start());
                matcher.appendReplacement(sb, Character.toString(firstLetter));
            }
            matcher.appendTail(sb);
            System.out.println(sb);
            matcher.reset();
            //-------------------------------------
            
            String rep3 = inputData;
            sb.delete(0, sb.length());

            while (matcher.find()){
                char firstLetter = rep3.charAt(matcher.start());
                StringBuffer word = new StringBuffer();
                for(int i = 0; i < matcher.end()-matcher.start(); i++){
                    word.append(firstLetter);
                }
                matcher.appendReplacement(sb, word.toString());
            }

            matcher.appendTail(sb);
            System.out.println(sb);
            //-------------------------------------

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Input GUID string:");
            String inputData = in.nextLine();
    
            if (is_GUID(inputData)) System.out.println("This string is GUID");
            else System.out.println("Not a GUID.");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Input HTML color string:");
            String inputData = in.nextLine();
    
            if (is_html_color(inputData)) System.out.println("This string is HTML color");
            else System.out.println("Not an HTML color.");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Input folder directory:");
            String fileName = in.next();
            File pathDir = new File(fileName);
            String[] files = pathDir.list();
            for(int i = 0; i < files.length; i++){
                System.out.println(files[i]);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        try {

            Scanner in = new Scanner(System.in);
            System.out.println("Введите слова с 'ик':");
            StringBuffer inputData = new StringBuffer(in.nextLine());
            System.out.println(inputData);
            System.out.println(delete_ik(inputData.toString()));

        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{

            Scanner in = new Scanner(System.in);
            System.out.println("Input string with prices in USD, RUR, EU:");
            String inputData = in.nextLine();
            ArrayList<Double> usdPrices = new ArrayList<>();
            ArrayList<Double> rurPrices = new ArrayList<>();
            ArrayList<Double> euPrices = new ArrayList<>();
            parseInputData(inputData, usdPrices, rurPrices, euPrices);
            System.out.println();
            String oneValute = createOneValute(usdPrices, rurPrices, euPrices);
            System.out.println(oneValute);
        
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
            
        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Input MAC-address:");
            String mac = in.nextLine();
            if (is_mac(mac)) System.out.println("This mac is correct");
            else System.out.println("This mac is incorrect");
                
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Input address:");
            String address = in.nextLine();
            if (is_address(address)) System.out.println("This address is correct");
            else System.out.println("This address is incorrect");
                
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Input date:");
            String date = in.nextLine();
            if (is_date(date)) System.out.println("This date is correct");
            else System.out.println("This date is incorrect");
                
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Input email:");
            String mail = in.nextLine();
            if (is_mail(mail)) System.out.println("This mail is correct");
            else System.out.println("This mail is incorrect");
                
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Input ip:");
            String ip = in.nextLine();
            if (is_ip(ip)) System.out.println("This ip is correct");
            else System.out.println("This ip is incorrect");
                
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Input password:");
            String pass = in.nextLine();
            if (is_pass(pass)) System.out.println("This password is correct");
            else System.out.println("This password is incorrect");
                
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Input number with 6 numerals:");
            String num = in.nextLine();
            if (is_num(num)) System.out.println("This number is correct");
            else System.out.println("This number is incorrect");
                
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Input expression to check +:");
            String expr = in.nextLine();
            if (is_expr(expr)) System.out.println("This expression is correct");
            else System.out.println("This expression is incorrect");
                
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Input expression to check brackets:");
            String expr2 = in.nextLine();
            if (is_expr2(expr2)) System.out.println("This expression is correct");
            else System.out.println("This expression is incorrect");
                
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
            
    }
        
        
    public static boolean is_GUID(String str) {
        String regexINeed = "[a-f0-9]";
        Pattern pat = Pattern.compile(regexINeed + "{8}-" + regexINeed + "{4}-" + regexINeed + "{4}-" +
        regexINeed + "{4}-" + regexINeed + "{12}");
        Matcher matcher = pat.matcher(str);
        if (matcher.matches()) return true;
        else return false;
    }
        
    public static boolean is_html_color(String str){
        String regexINeed = "#[A-F0-9]{6}";
        Pattern pat = Pattern.compile(regexINeed);
        Matcher matcher = pat.matcher(str);
        if (matcher.matches()) return true;
        else return false;
    }

    public static boolean is_mac(String mac){
        Pattern pat = Pattern.compile("([a-fA-F0-9][a-fA-F0-9]:){5}([a-fA-F0-9][a-fA-F0-9])");
        Matcher matcher = pat.matcher(mac);
        if (matcher.matches()) return true;
        else return false;
    }

    public static boolean is_address(String adr){//https : /  /    subdomain    first letter   domain          last letter     . onion          port        /anything    get                       #yakor     /                                                          
        Pattern pat = Pattern.compile(           "(https?:\\/\\/)?([a-zA-z]\\.)?([\\da-zA-Z])([\\da-zA-Z\\.-]+)*([\\da-zA-Z])\\.([a-z\\.]{2,6})(:\\d{4})?([\\/\\w\\.-](\\?(([\\w]+=[\\w]+)&?)*)?)*(#[\\w]+)*\\/?");
        Matcher matcher = pat.matcher(adr);
        if (matcher.matches()) return true;
        else return false;
    }

    public static boolean is_date(String date){
        Pattern pat = Pattern.compile("(([0-2][0-9])|(3[01]))\\/((0[1-9])|(1[012]))\\/((1[6-9]\\d\\d)|([2-9]\\d\\d))");
        Matcher matcher = pat.matcher(date);
        if (matcher.matches()) return true;
        else return false;
    }

    public static boolean is_mail(String mail){
        Pattern pat = Pattern.compile("([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]+)");
        Matcher matcher = pat.matcher(mail);
        if (matcher.matches()) return true;
        else return false;
    }

    public static boolean is_ip(String ip){
        Pattern pat = Pattern.compile("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
        Matcher matcher = pat.matcher(ip);
        if (matcher.matches()) return true;
        else return false;
    }

    public static boolean is_pass(String pass){
        Pattern pat = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(\\w){8,}");
        Matcher matcher = pat.matcher(pass);
        if (matcher.matches()) return true;
        else return false;
    }

    public static boolean is_num(String num){
        Pattern pat = Pattern.compile("[1-9]\\d{5}");
        Matcher matcher = pat.matcher(num);
        if (matcher.matches()) return true;
        else return false;
    }

    public static boolean is_expr(String expr){
        Pattern pat = Pattern.compile(".*\\d\\+.*");
        Matcher matcher = pat.matcher(expr);
        if (matcher.matches()) return true;
        else return false;
    }

    public static boolean is_expr2(String expr){
        int br1 = 0, br2 = 0;
        Pattern pat1 = Pattern.compile("\\(");
        Pattern pat2 = Pattern.compile("\\)");
        Matcher matcher1 = pat1.matcher(expr);
        Matcher matcher2 = pat2.matcher(expr);
        while(matcher1.find()) br1++;
        while(matcher2.find()) br2++;
        if (br1 == br2) return true;
        else return false;
    }
        
    public static String delete_ik(String str){
        Pattern pat = Pattern.compile("ик");
        Matcher matcher = pat.matcher(str);
        str = matcher.replaceAll("");
        return str;
    }
    
    public static void parseInputData(String str, ArrayList<Double> usdPrices,
        ArrayList<Double> rurPrices, ArrayList<Double> euPrices){

        Pattern pat = Pattern.compile("\\d+\\.\\d{2}\\sUSD");
        Matcher matcher = pat.matcher(str);
        while (matcher.find()){
            String curr = str.substring(matcher.start(), matcher.end());
            usdPrices.add(Double.parseDouble(curr.substring(0, curr.length()-3)));
        }
        matcher.reset();
        
        pat = Pattern.compile("\\d+\\.\\d{2}\\sRUR");
        matcher = pat.matcher(str);
        while (matcher.find()){
            String curr = str.substring(matcher.start(), matcher.end());
            rurPrices.add(Double.parseDouble(curr.substring(0, curr.length()-3)));
        }
        matcher.reset();
        
        pat = Pattern.compile("\\d+\\.\\d{2}\\sEU");
        matcher = pat.matcher(str);
        while (matcher.find()){
            String curr = str.substring(matcher.start(), matcher.end());
            euPrices.add(Double.parseDouble(curr.substring(0, curr.length()-3)));
        }
        matcher.reset();
    }

    public static String createOneValute(ArrayList<Double> usdPrices, ArrayList<Double> rurPrices,
            ArrayList<Double> euPrices) {
        
        StringBuffer prices = new StringBuffer();
         
        Scanner in = new Scanner(System.in);
        System.out.println("Input valuta: ");
        String valuta = in.nextLine();

        if (valuta.equals("USD")){
            double rur = 0, eu = 0;
            System.out.println("Input RUR cost:");
            rur = in.nextDouble();
            System.out.println("Input EU cost:");
            eu = in.nextDouble();

            for(int i = 0; i < usdPrices.size(); i++){
                prices.append(String.format("%.2f", usdPrices.get(i)) + " ");
            }
            for(int i = 0; i < rurPrices.size(); i++){
                prices.append(String.format("%.2f", rurPrices.get(i)*rur).replace(',', '.') + " ");
            }
            for(int i = 0; i < euPrices.size(); i++){
                prices.append(String.format("%.2f", euPrices.get(i)*eu).replace(',', '.') + " ");
            }

        } else if (valuta.equals("RUR")){
            double usd = 0, eu = 0;
            System.out.println("Input USD cost:");
            usd = in.nextDouble();
            System.out.println("Input EU cost:");
            eu = in.nextDouble();

            for(int i = 0; i < rurPrices.size(); i++){
                prices.append(String.format("%.2f", rurPrices.get(i)).replace(',', '.') + " ");
            }
            for(int i = 0; i < usdPrices.size(); i++){
                prices.append(String.format("%.2f", usdPrices.get(i)*usd).replace(',', '.') + " ");
            }
            for(int i = 0; i < euPrices.size(); i++){
                prices.append(String.format("%.2f", euPrices.get(i)*eu).replace(',', '.') + " ");
            }

        } else if (valuta.equals("EU")){
            double rur = 0, usd = 0;
            System.out.println("Input USD cost:");
            usd = in.nextDouble();
            System.out.println("Input RUR cost:");
            rur = in.nextDouble();

            for(int i = 0; i < euPrices.size(); i++){
                prices.append(String.format("%.2f", euPrices.get(i)).replace(',', '.') + " ");
            }
            for(int i = 0; i < rurPrices.size(); i++){
                prices.append(String.format("%.2f", rurPrices.get(i)*rur).replace(',', '.') + " ");
            }
            for(int i = 0; i < usdPrices.size(); i++){
                prices.append(String.format("%.2f", usdPrices.get(i)*usd).replace(',', '.') + " ");
            }

        } else {
            System.out.println("No such valuta");
            return null;
        }

        return prices.toString();
    }

}