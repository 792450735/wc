import java.io.*;

public class WorldCount {
	public static int words=1;
	public static int lines=1;
	public static int chars=0;
	public static String fname;
	public static String outname;
	
	public static void wc(String ff,int[] use) throws IOException{
		int c=0;
		String fff=new String("src/"+ff);
		InputStream f=new FileInputStream(ff);////
		boolean lastNotWhite=false;
		String whiteSpace=" \t\n\r,";
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
			}
			else
				lastNotWhite=true;
		}
		for(int i=0;i<use.length;i++){
			if(use[i]==1){
				switch(i){
				case 0:System.out.println(fname+",字符数："+chars);break;
				case 1:System.out.println(fname+",单词数："+words);break;
				case 2:System.out.println(fname+",行数："+lines);break;
				default:
				}
			}
		}
	}
	
	public static void oout(String f,int[] use) throws IOException{
		File file = new File(f);
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
        //2：准备输出流
        FileWriter out = new FileWriter(file);
		for(int i=0;i<use.length;i++){
			if(use[i]==1){
				switch(i){
				case 0:out.write(fname+",字符数："+chars+"\r\n");break;
				case 1:out.write(fname+",单词数："+words+"\r\n");break;
				case 2:out.write(fname+",行数："+lines+"\r\n");break;
				default:
				}
			}
		}
        out.close();
	}
	
/*	public static boolean readfile(String filepath) throws FileNotFoundException, IOException {//读取文件或文件夹
        try {

                File file = new File(filepath);
                if (!file.isDirectory()) {
                        System.out.println("文件");
                        System.out.println("path=" + file.getPath());
                        System.out.println("absolutepath=" + file.getAbsolutePath());
                        System.out.println("name=" + file.getName());

                } else if (file.isDirectory()) {
                        System.out.println("文件夹");
                        String[] filelist = file.list();
                        for (int i = 0; i < filelist.length; i++) {
                                File readfile = new File(filepath + "\\" + filelist[i]);
                                if (!readfile.isDirectory()) {
                                        System.out.println("path=" + readfile.getPath());
                                        System.out.println("absolutepath="
                                                        + readfile.getAbsolutePath());
                                        System.out.println("name=" + readfile.getName());

                                } else if (readfile.isDirectory()) {
                                        readfile(filepath + "\\" + filelist[i]);
                                }
                        }

                }

        } catch (FileNotFoundException e) {
                System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return true;
}*/
	
	public static void main(String[] args) throws IOException{
		int[] flags=new int[]{0,0,0};
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-c"))
				flags[0]=1;
			if(args[i].equals("-w"))
				flags[1]=1;
			if(args[i].equals("-l"))
				flags[2]=1;
			if(!args[i].equals("-c")&&!args[i].equals("-w")&&!args[i].equals("-l")&&!args[i].equals("-o")){
				fname=args[i];
			}
			if(args[i].equals("-o")){
				outname=args[++i];
			}
		}
		wc(fname,flags);
		if(outname!=null)
			oout(outname,flags);
		return;
	}
}
