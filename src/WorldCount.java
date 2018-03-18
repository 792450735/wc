import java.io.*;
import java.util.ArrayList;

public class WorldCount {
	public static int words=0;
	public static int lines=1;
	public static int chars=0,t1=0,t2=0,t3=0;//t1,t2,t3分别为代码行，空行，注释行
	public static ArrayList<String> fname=new ArrayList<String>();
	public static String outname="result.txt";
	public static String stopList;
	
	public static void init(){
		words=1;
		lines=1;
		chars=0;
		t1=0;
		t2=0;
		t3=0;
	}
	
	public static void wc(String ff,int[] use) throws IOException{//计算字符数，字数，行数；包括停用词表功能
		int c=0;
		String fff=new String("src/"+ff);
		InputStream f=new FileInputStream(ff);////
		boolean lastNotWhite=false;
		boolean lastisword=false;
		String whiteSpace=" \t\n\r,";
		if(use[3]==1){
			StringBuilder list= new StringBuilder();
			StringBuilder newlist= new StringBuilder();
			ArrayList<String> stopwords = new ArrayList<String>();
			InputStream fs=new FileInputStream(stopList);
			while((c=fs.read())!=-1){
				if(whiteSpace.indexOf(c)==-1){
					list.append((char)c);
				}
				if(whiteSpace.indexOf(c)!=-1){
					if(lastNotWhite){
						stopwords.add(list.toString());
						list.delete(0, list.length());
						
					}
					lastisword=true;
					lastNotWhite=false;
				}
				else{
					lastisword=false;
					lastNotWhite=true;
				}
			}
			if(!lastisword){
				stopwords.add(list.toString());
			}
			lastisword=false;
			lastNotWhite=false;
			while((c=f.read())!=-1){
				chars++;
				if(c=='\n'){
					lines++;
					chars=chars-2;
				}
				if(whiteSpace.indexOf(c)==-1){
					newlist.append((char)c);
				}
				if(whiteSpace.indexOf(c)!=-1){
					if(lastNotWhite){
						words++;
						for(int j=0;j<stopwords.size();j++){
							if(newlist.toString().equals(stopwords.get(j)))
								words--;
						}
						newlist.delete(0, newlist.length());
					}
					lastNotWhite=false;
					lastisword=true;
				}
				else{
					lastisword=false;
					lastNotWhite=true;
				}
			}
			if(!lastisword){
				for(int j=0;j<stopwords.size();j++){
					if(newlist.toString().equals(stopwords.get(j)))
						words--;
				}
			}
		}
		else{
			while((c=f.read())!=-1){
				chars++;
				if(c=='\n'){
					lines++;
					chars=chars-2;
				}
				if(whiteSpace.indexOf(c)!=-1){
					if(lastNotWhite)
						words++;
					lastNotWhite=false;
					lastisword=true;
				}
				else{
					lastisword=false;
					lastNotWhite=true;
				}
			}
		}
		
		for(int i=0;i<use.length;i++){//按顺序输出
			if(use[i]==1){
				switch(i){
				case 0:System.out.println(ff+",字符数："+chars);break;
				case 1:System.out.println(ff+",单词数："+words);break;
				case 2:System.out.println(ff+",行数："+lines);break;
				default:
				}
			}
		}
	}
	
	public static void oout(String f,String ff,int[] use) throws IOException{//将结果输出到txt
		File file = new File(f);
		
			//2：准备输出流
			FileWriter out = new FileWriter(file,true);
			for(int i=0;i<use.length;i++){
				if(use[i]==1){
					switch(i){
					case 0:out.write(ff+",字符数："+chars+"\r\n");break;
					case 1:out.write(ff+",单词数："+words+"\r\n");break;
					case 2:out.write(ff+",行数："+lines+"\r\n");break;
					case 4:out.write(ff+",代码行/空行/注释行："+t1+"/"+t2+"/"+t3+"\r\n");break;
					default:
					}
				}
			}
			out.close();
	}
	
	public static void hard(String f) throws IOException{//计算更复杂的数据：代码行，空行，注释行
		File ff=new File(f);
		InputStreamReader fff=new InputStreamReader(new FileInputStream(f));
		BufferedReader freader=new BufferedReader(fff);
		String l=null;
		int isflag=0;//isflag=10为代码行，=11为注释行，=0为空行
			while((l=freader.readLine())!=null){
				isflag=0;
				for(int i=0;i<l.length();i++){
					if(l.charAt(i)!=' '&&l.charAt(i)!='/'&&isflag==0&&l.charAt(i)!='\t')
						{isflag=1;continue;}
					if(l.charAt(i)!=' '&&l.charAt(i)!='/'&&isflag==1)
						{isflag=10;break;}
					if(l.charAt(i)=='/'&&isflag==0)
						{isflag=2;continue;}
					if(l.charAt(i)=='/'&&isflag==1)
						{isflag=2;continue;}
					if(l.charAt(i)=='/'&&isflag==2)
						{isflag=11;break;}
					
				}
				if(isflag==10||isflag==1)
					t1++;
				else if(isflag==11)
					t3++;
				else
					t2++;
			}
			System.out.println(f+",代码行/空行/注释行："+t1+"/"+t2+"/"+t3);
		
	}
	
	public static void main(String[] args) throws IOException{
		int[] flags=new int[]{0,0,0,0,0};
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-c"))
				flags[0]=1;
			if(args[i].equals("-w"))
				flags[1]=1;
			if(args[i].equals("-l"))
				flags[2]=1;
			if(args[i].equals("-a"))
				flags[4]=1;
			if(!args[i].equals("-c")&&!args[i].equals("-w")&&!args[i].equals("-l")&&!args[i].equals("-o")&&!args[i].equals("-a")&&!args[i].equals("-e")&&!args[i].equals("-s")){
				fname.add(args[i]);
			}
			if(args[i].equals("-o")){
				outname=args[++i];
			}
			if(args[i].equals("-e")){
				stopList=args[++i];
				flags[3]=1;
			}
		}
		if(outname!=null){
			File file = new File(outname);
			if(!file.exists()){
			       try {
			            if(file.createNewFile()){
			             }else{
			                 System.out.println("文件创建失败！");
			             }
			         } catch (IOException e) {
			               e.printStackTrace();
			         }
			}
			else{
				FileWriter out = new FileWriter(file);
				out.write("");
				out.close();
			}
		}
		for(int i=0;i<fname.size();i++){
			init();
			wc(fname.get(i),flags);
			if(flags[4]==1)
				hard(fname.get(i));
			if(outname!=null)
				oout(outname,fname.get(i),flags);
		}
		return;
	}
}
