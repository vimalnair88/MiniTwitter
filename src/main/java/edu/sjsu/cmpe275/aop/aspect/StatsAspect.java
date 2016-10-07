package edu.sjsu.cmpe275.aop.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class StatsAspect {
	
	
	/* AUTHOR: VIMAL NAIR
	 * Aspect for Checking the Tweet Message Length, The Allowed 
	 * length of a tweet message is 140 Characters. Tweeting will not
	 * happen if the length is greater than 140 Characters.
	 */
		
	@Before("execution(* tweet*(..,..))")
	public void countLengthAspect(JoinPoint joinpoint) throws Throwable{
		Object[] object = joinpoint.getArgs();
		String result = (String)object[1].toString();
		System.out.println("Tweet Message: "+result);
		if(result.length()<140){
		}else{
			throw new IllegalArgumentException("Tweet Message Greater than 140 Characters");
		}		
	}

}
