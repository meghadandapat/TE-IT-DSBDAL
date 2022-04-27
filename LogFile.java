import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class AnalyzeLogs {
	public static void main(String [] args) throws Exception
	{
	Configuration c=new Configuration();
	String[] files=new GenericOptionsParser(c,args).getRemainingArgs();
	Path input=new Path(files[0]);
	Path output=new Path(files[1]);
	Job j=new Job(c,"analyzelogs");
	j.setJarByClass(AnalyzeLogs.class);
	j.setMapperClass(SaleMapper.class);
	j.setReducerClass(SaleReducer.class);
	j.setOutputKeyClass(Text.class);
	j.setOutputValueClass(IntWritable.class);
	FileInputFormat.addInputPath(j, input);
	FileOutputFormat.setOutputPath(j, output);
	System.exit(j.waitForCompletion(true)?0:1);
	}
	public static class SaleMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
		public void map(LongWritable key, Text value, Context con) throws IOException, InterruptedException {
			String data = value.toString();
			String[] lines = data.split("\n");
			for(String line:lines) {
				String[] singleData= line.split(",");
				Text outputKey = new Text(singleData[0]);
				IntWritable outputValue = new IntWritable(Integer.parseInt(singleData[1]));
				con.write(outputKey,  outputValue);
			}
		}
	}

    public static class SaleReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private Text MaxWord = new Text();
		private int max = 0;
		public void reduce(Text word, Iterable<IntWritable> values, Context con) throws IOException, InterruptedException {
			int sum = 0;
			for(IntWritable value: values){
				sum+= value.get();
			}
			if(sum > max){
				max = sum;
				MaxWord.set(word);
			}
		}
		protected void cleanup (Context con) throws IOException, InterruptedException{
			con.write(new Text(MaxWord), new IntWritable(max));
		}
	}
}
