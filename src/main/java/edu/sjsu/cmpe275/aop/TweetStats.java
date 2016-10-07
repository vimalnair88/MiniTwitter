package edu.sjsu.cmpe275.aop;

public interface TweetStats {
	void resetStats();
	int getLengthOfLongestTweet();
	String getMostActiveFollower();
	String getMostProductiveUser();
}
