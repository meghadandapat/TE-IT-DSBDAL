import java.io.IOException; 

import org.apache.hadoop.conf.Configuration ;
import org.apache.hadoop.fs.Path ;
import org.apache.hadoop.util.GenericOptionsParser ;
import org.apache.hadoop.io.*  ;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class Moviefile {
	public static void main (String[] args) throws Exception{
		Configuration c = new Configuration() ;
		String []files = new GenericOptionsParser(c,args).getRemainingArgs() ;
		Path input = new Path(files[0]) ;
		Path output = new Path(files[1]) ;
		Job j = new Job(c,"wordcount") ;
		j.setJarByClass(Moviefile.class);
		j.setMapperClass(MapforProg.class);
		j.setReducerClass(ReduceforProg.class);
		j.setOutputKeyClass(Text.class) ;
		j.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(j,input) ;
		FileOutputFormat.setOutputPath(j,output) ;
		System.exit(j.waitForCompletion(true)?0:1) ;
	}
	
	public static class MapforProg extends Mapper<LongWritable, Text,Text, IntWritable>{
		public void map(LongWritable key, Text value, Context con)throws IOException,InterruptedException{
			String lines = value.toString() ;
			String [] line = lines.split("\n");
			
			for(String words : line){
				String[] data = words.split(",") ;
				Text outputkey = new Text(data[1]) ;
				IntWritable outputkeyval = new IntWritable(Math.round(Float.parseFloat(data[2]))) ;
				con.write(outputkey, outputkeyval);
			}
		}
	}
	public static class ReduceforProg extends Reducer<Text, IntWritable, Text, IntWritable>{
		Text maxword = new Text() ;
		
		Text msg= new Text("Suggested movie with highest rating") ;
		int max = 0 ;
		public void reduce(Text word, Iterable<IntWritable>values, Context con)throws IOException,InterruptedException{
				int sum = 0 ;
				for(IntWritable value: values){
					sum+=value.get();
					
				}
				if(sum>max){
					max = sum ;
					maxword.set(word);
				}
				con.write(word, new IntWritable(sum));
				
		}
		
		protected void cleanup(Context con)throws IOException, InterruptedException,InterruptedException{
			System.out.println(msg);
			
			con.write(maxword, new IntWritable(max));
		}
	}
}