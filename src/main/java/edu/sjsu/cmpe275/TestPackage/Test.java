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
		service.tweet("Vimal", "How are you and how is it going");
		service.follow("Prince", "Vimal");
		service.follow("Chunk", "Vimal");
		service.follow("Chunk", "Vimal");
//		System.out.println("Length of Longest Tweet: "+stats.getLengthOfLongestTweet());
//		System.out.println("Most Productive User: "+stats.getMostProductiveUser());
		System.out.println("Most Active Follower: "+stats.getMostActiveFollower());		
	}

}
