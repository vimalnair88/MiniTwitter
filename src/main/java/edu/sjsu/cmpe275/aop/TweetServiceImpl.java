package edu.sjsu.cmpe275.aop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TweetServiceImpl implements TweetService {

	
	public void tweet(String user, String message) throws IllegalArgumentException, IOException {

		System.out.println("Tweeting: "+user+":: "+message);
	}

	public void follow(String follower, String followee) throws IOException {
		
		System.out.println("Following: "+follower+":: "+followee);
		
	}

}
