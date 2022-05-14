import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;


public class MusicUnique {
	public static void main(String [] args) throws Exception{
		Configuration c = new Configuration();
		String[] files=new GenericOptionsParser(c,args).getRemainingArgs();
		Path input = new Path(files[0]);
		Path output = new Path(files[1]);
		Job j = new Job(c, "musicunique");
		j.setJarByClass(MusicUnique.class);
		j.setMapperClass(MapperForMusic.class);
		j.setReducerClass(ReducerForMusic.class);
		j.setOutputKeyClass(Text.class);
		j.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(j, input);
		FileOutputFormat.setOutputPath(j, output);
		System.exit(j.waitForCompletion(true)?0:1);
	}
	public static class MapperForMusic extends Mapper<LongWritable, Text, Text, IntWritable> {
		public void map(LongWritable key, Text values, Context con) throws IOException, InterruptedException {
			String lines = values.toString();
			String[] line = lines.split("\n");
			for(String words: line){
				String[] data = words.split(",");
				Text outputKey = new Text(data[0]);
				IntWritable outputValue = new IntWritable(1);
				con.write(outputKey, outputValue);
			}
		}
	}
	public static class ReducerForMusic extends Reducer<Text, IntWritable, Text, IntWritable> {
		private Text maxword = new Text("Th number of unique listeners are: ");
		int count = 0;
		public void reduce(Text key, Iterable<IntWritable> values, Context con) throws IOException, InterruptedException {
			int sum = 0;
			for(IntWritable value: values){
				sum+=value.get();
			}
			count++;
		}
		protected void cleanup(Context con) throws IOException, InterruptedException {
			con.write(maxword, new IntWritable(count));
		}
		
	}
}