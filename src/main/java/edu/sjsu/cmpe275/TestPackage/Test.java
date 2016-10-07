package edu.sjsu.cmpe275.TestPackage;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import edu.sjsu.cmpe275.aop.TweetService;
import edu.sjsu.cmpe275.aop.TweetStats;


public class Test {

	public static void main(String[] args) throws IllegalArgumentException, IOException {
		
		ApplicationContext context = new FileSystemXmlApplicationContext("beans.xml");
		TweetService service = context.getBean("tweetService",TweetService.class);
		TweetStats stats = context.getBean("tweetStats",TweetStats.class);
		service.tweet("Vimal", "How are you and how is it ggoing");
		service.tweet("Chunk", "How are you and how is it ggoing");
		service.follow("Chunk", "Prince");
		service.follow("Prince", "Vimal");
		System.out.println("Length of Longest Tweet: "+stats.getLengthOfLongestTweet());
		System.out.println("Most Productive User: "+stats.getMostProductiveUser());
		System.out.println("Most Active Follower: "+stats.getMostActiveFollower());		
		stats.resetStats();
	}

}
