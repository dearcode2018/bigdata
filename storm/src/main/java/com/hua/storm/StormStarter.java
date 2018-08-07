/**
  * @filename StormStarter.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

import com.hua.bolt.SplitSentenceBolt;
import com.hua.bolt.WordCoutBolt;
import com.hua.spout.SentenceSpout;

 /**
 * @type StormStarter
 * @description 
 * @author qianye.zheng
 */
public class StormStarter
{

	public static final String DEMO_SPOUT_ID = "sentence-spout01";
	
	public static final String DEMO_BOLT01_ID = "sentence-bolt-01";
	
	public static final String DEMO_BOLT02_ID = "sentence-bolt-02";
	
	/**
	 * @description 
	 * @param args
	 * @author qianye.zheng
	 */
	public static void main(String[] args)
	{
		try
		{
			// 定义拓扑
			TopologyBuilder builder = new TopologyBuilder();
			// 设置1个执行线程，默认一个
			//builder.setSpout(DEMO_SPOUT_ID, new SentenceSpout(), 1);
			// 设置2个执行线程
			builder.setSpout(DEMO_SPOUT_ID, new SentenceSpout(), 2);
			// 设置1个执行线程，2个任务，shuffle 表示随机分配 Spout的数据给Bolt
			builder.setBolt(DEMO_BOLT01_ID, new SplitSentenceBolt(), 1).setNumTasks(2).shuffleGrouping(DEMO_SPOUT_ID);
			// 数据源为 DEMO_BOLT01_ID
			builder.setBolt(DEMO_BOLT02_ID, new WordCoutBolt(), 1).setNumTasks(2).shuffleGrouping(DEMO_BOLT01_ID);
			
			Config config = new Config();
			config.put("test", "test");
			
			// 本地模式
			LocalCluster cluster = new LocalCluster();
			// 提交拓扑结构
			cluster.submitTopology("wordCount", config, builder.createTopology());
			
			Thread.sleep(20 * 1000);
			
			// 关闭本地-集群
			cluster.shutdown();
			
			/*
			 * 远程集群
			 * 在 config 对象中指定远程集群的地址端口等配置.
			 */
			//StormSubmitter.submitTopology("wordCount", config, builder.createTopology());
			Thread.sleep(20 * 1000);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
