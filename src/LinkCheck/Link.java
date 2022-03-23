package LinkCheck;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Link {
	private String linkText;
	private int numberValid = -1;
	private int numberInvalid;
	private int total;
	private int numberOfThreads = 1;
	ExecutorService pool;

	public Link(String link, int numberOfThreads) {
		this.linkText = link;
		this.numberOfThreads = numberOfThreads;
		pool = Executors.newFixedThreadPool(this.numberOfThreads);
	}

	public void extractLinks(String link, int curDepth, int depth, String text) //Extracts all links from a given link with specified depth.
			throws IOException, MalformedURLException, InterruptedException, RejectedExecutionException {
		
			if (!validateLink(link) && !validatePDF(link)) { //Checks if the link is invalid
			System.out.println("Invalid: " + link); // prints the link URL and text.
			System.out.println("Text: " + text);
			System.out.println();
			this.numberInvalid++; // Increments number of invalid links.
			return;//Does not go on with extracting links since it's invalid.
		}
			else if(validateLink(link)) {
				System.out.println("Valid: " + link);// prints the link URL and text.
				System.out.println("Text: " + text);
				System.out.println();
				numberValid++;// Increments number of valid links.
			}
			else if(validatePDF(link)) {
				System.out.println("Valid: " + link);
				System.out.println("Text: " + text);
				System.out.println();
				this.numberValid++;
				return;//Exits the function if link is another type of file because no links exist in it.
				
			}
//		else if(!validatePDF(link)){ // in case it's valid
//			System.out.println("Invalid: " + link);// prints the link URL and text.
//			System.out.println("Text: " + text);
//			System.out.println();
//			this.numberInvalid++;// Increments number of valid links.
//			return;
//		}

		if (depth == curDepth) { // Stops the function if the specified depth is achieved.

			return;
		}
		Document doc = Jsoup.connect(link).get();
		Elements e = doc.select("a[href]");//Selects all links from the given link.
		URL url = new URL(link);

		String baseLink = url.getProtocol() + "://" + url.getHost();//Concatenates the URL in the standard form https://www.xxxxx.com/.
		this.total += e.size();//Increases the total number of links.
		int i;
		for (i = 0; i < e.size(); i++) {
			String x = e.get(i).attr("href"); // Stores the link address in a string.
			String y = e.get(i).text(); // Stores the link text in a string.
			if (!x.startsWith("http")) //If the links doesn't start with the protocol,it Concatenates the URL in the standard form. 
				x = baseLink + x;

			ValidateThread task = new ValidateThread(x, curDepth + 1, depth, y); // Creates an instance of the inner class ValidateThread.
			Runnable task2 = new ValidateThread(x,curDepth +1 ,depth,y);
			try {
				pool.execute(task);//pool begins executing the given task by calling the extractLinks() method.
			} catch (RejectedExecutionException ex) {

			}
		}

	}

	public int getNumberValid() {
		return numberValid;
	}

	public void setNumberValid(int numberValid) {
		this.numberValid = numberValid;
	}

	public int getNumberInvalid() {
		return numberInvalid;
	}

	public void setNumberInvalid(int numberInvalid) {
		this.numberInvalid = numberInvalid;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean validateLink(String link) { //If the link connects successfully or the link is other type of file,returns true.
		try {
			Document doc = Jsoup.connect(link).get();
			/*
			 * URL url = new URL(link); HttpURLConnection http =
			 * (HttpURLConnection)url.openConnection(); int responseCode =
			 * http.getResponseCode(); if(responseCode != 200) return false;
			 */
			return true;
		} catch (HttpStatusException ex) {
			return false;

		} catch (IOException e) {
			return false;

		}

	}
	
	public boolean validatePDF(String link) {//Returns true if the link is another type of file e.g. PDF
		try {
		
		URL u = new URL(link);
	    URLConnection x = u.openConnection();
	    x.getContentType();
	    if(x.getContentType().startsWith("application/"))
	    	return true;
	    else 
	    	return false;
		} catch(Exception e) {
			return false;
		}
	     
		
	}

	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {

		this.linkText = linkText;
	}

	public class ValidateThread implements Runnable { //Inner Class that implements threads.

		private String x;
		private int curDepth;
		private int depth;
		private String text;

		public ValidateThread(String x, int curDepth, int depth, String text) {
			super();
			this.x = x;
			this.curDepth = curDepth;
			this.depth = depth;
			this.text = text;
		}

		public void run() { //Threads in the thread pool execute the extractLinks() method.
			try {
				extractLinks(x, curDepth, depth, text);
			} catch (MalformedURLException e) {

			} catch (RejectedExecutionException e) {

			} catch (IOException e) {

			}

			catch (InterruptedException e) {

			} catch (IllegalArgumentException e) {

			}

		}

	}

}
